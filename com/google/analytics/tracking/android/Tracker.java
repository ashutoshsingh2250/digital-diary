package com.google.analytics.tracking.android;

import android.text.TextUtils;
import com.google.android.gms.common.util.VisibleForTesting;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class Tracker
{
  private static final DecimalFormat DF = new DecimalFormat("0.######", new DecimalFormatSymbols(Locale.US));
  static final long MAX_TOKENS = 120000L;
  static final long TIME_PER_TOKEN_MILLIS = 2000L;
  private volatile ExceptionParser mExceptionParser;
  private final TrackerHandler mHandler;
  private boolean mIsThrottlingEnabled = true;
  private volatile boolean mIsTrackerClosed = false;
  private volatile boolean mIsTrackingStarted = false;
  private long mLastTrackTime;
  private final SimpleModel mModel;
  private long mTokens = 120000L;

  Tracker()
  {
    this.mHandler = null;
    this.mModel = null;
  }

  Tracker(String paramString, TrackerHandler paramTrackerHandler)
  {
    if (paramString == null)
      throw new IllegalArgumentException("trackingId cannot be null");
    this.mHandler = paramTrackerHandler;
    this.mModel = new SimpleModel(null);
    this.mModel.set("trackingId", paramString);
    this.mModel.set("sampleRate", "100");
    this.mModel.setForNextHit("sessionControl", "start");
    this.mModel.set("useSecure", Boolean.toString(true));
  }

  private void assertTrackerOpen()
  {
    if (this.mIsTrackerClosed)
      throw new IllegalStateException("Tracker closed");
  }

  private Map<String, String> constructItem(Transaction.Item paramItem, Transaction paramTransaction)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("transactionId", paramTransaction.getTransactionId());
    localHashMap.put("currencyCode", paramTransaction.getCurrencyCode());
    localHashMap.put("itemCode", paramItem.getSKU());
    localHashMap.put("itemName", paramItem.getName());
    localHashMap.put("itemCategory", paramItem.getCategory());
    localHashMap.put("itemPrice", microsToCurrencyString(paramItem.getPriceInMicros()));
    localHashMap.put("itemQuantity", Long.toString(paramItem.getQuantity()));
    GAUsage.getInstance().setUsage(GAUsage.Field.CONSTRUCT_ITEM);
    return localHashMap;
  }

  private void internalSend(String paramString, Map<String, String> paramMap)
  {
    this.mIsTrackingStarted = true;
    if (paramMap == null)
      paramMap = new HashMap();
    paramMap.put("hitType", paramString);
    this.mModel.setAll(paramMap, Boolean.valueOf(true));
    if (!tokensAvailable())
      Log.wDebug("Too many hits sent too quickly, throttling invoked.");
    while (true)
    {
      this.mModel.clearTemporaryValues();
      return;
      this.mHandler.sendHit(this.mModel.getKeysAndValues());
    }
  }

  private static String microsToCurrencyString(long paramLong)
  {
    return DF.format(paramLong / 1000000.0D);
  }

  public void close()
  {
    this.mIsTrackerClosed = true;
    this.mHandler.closeTracker(this);
  }

  public Map<String, String> constructEvent(String paramString1, String paramString2, String paramString3, Long paramLong)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("eventCategory", paramString1);
    localHashMap.put("eventAction", paramString2);
    localHashMap.put("eventLabel", paramString3);
    if (paramLong != null)
      localHashMap.put("eventValue", Long.toString(paramLong.longValue()));
    GAUsage.getInstance().setUsage(GAUsage.Field.CONSTRUCT_EVENT);
    return localHashMap;
  }

  public Map<String, String> constructException(String paramString, boolean paramBoolean)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("exDescription", paramString);
    localHashMap.put("exFatal", Boolean.toString(paramBoolean));
    GAUsage.getInstance().setUsage(GAUsage.Field.CONSTRUCT_EXCEPTION);
    return localHashMap;
  }

  public Map<String, String> constructRawException(String paramString, Throwable paramThrowable, boolean paramBoolean)
    throws IOException
  {
    HashMap localHashMap = new HashMap();
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    ObjectOutputStream localObjectOutputStream = new ObjectOutputStream(localByteArrayOutputStream);
    localObjectOutputStream.writeObject(paramThrowable);
    localObjectOutputStream.close();
    localHashMap.put("rawException", Utils.hexEncode(localByteArrayOutputStream.toByteArray()));
    if (paramString != null)
      localHashMap.put("exceptionThreadName", paramString);
    localHashMap.put("exFatal", Boolean.toString(paramBoolean));
    GAUsage.getInstance().setUsage(GAUsage.Field.CONSTRUCT_RAW_EXCEPTION);
    return localHashMap;
  }

  public Map<String, String> constructSocial(String paramString1, String paramString2, String paramString3)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("socialNetwork", paramString1);
    localHashMap.put("socialAction", paramString2);
    localHashMap.put("socialTarget", paramString3);
    GAUsage.getInstance().setUsage(GAUsage.Field.CONSTRUCT_SOCIAL);
    return localHashMap;
  }

  public Map<String, String> constructTiming(String paramString1, long paramLong, String paramString2, String paramString3)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("timingCategory", paramString1);
    localHashMap.put("timingValue", Long.toString(paramLong));
    localHashMap.put("timingVar", paramString2);
    localHashMap.put("timingLabel", paramString3);
    GAUsage.getInstance().setUsage(GAUsage.Field.CONSTRUCT_TIMING);
    return localHashMap;
  }

  public Map<String, String> constructTransaction(Transaction paramTransaction)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("transactionId", paramTransaction.getTransactionId());
    localHashMap.put("transactionAffiliation", paramTransaction.getAffiliation());
    localHashMap.put("transactionShipping", microsToCurrencyString(paramTransaction.getShippingCostInMicros()));
    localHashMap.put("transactionTax", microsToCurrencyString(paramTransaction.getTotalTaxInMicros()));
    localHashMap.put("transactionTotal", microsToCurrencyString(paramTransaction.getTotalCostInMicros()));
    localHashMap.put("currencyCode", paramTransaction.getCurrencyCode());
    GAUsage.getInstance().setUsage(GAUsage.Field.CONSTRUCT_TRANSACTION);
    return localHashMap;
  }

  public String get(String paramString)
  {
    GAUsage.getInstance().setUsage(GAUsage.Field.GET);
    return this.mModel.get(paramString);
  }

  public String getAppId()
  {
    GAUsage.getInstance().setUsage(GAUsage.Field.GET_APP_ID);
    return this.mModel.get("appId");
  }

  public String getAppInstallerId()
  {
    GAUsage.getInstance().setUsage(GAUsage.Field.GET_APP_INSTALLER_ID);
    return this.mModel.get("appInstallerId");
  }

  public ExceptionParser getExceptionParser()
  {
    GAUsage.getInstance().setUsage(GAUsage.Field.GET_EXCEPTION_PARSER);
    return this.mExceptionParser;
  }

  public double getSampleRate()
  {
    GAUsage.getInstance().setUsage(GAUsage.Field.GET_SAMPLE_RATE);
    return Utils.safeParseDouble(this.mModel.get("sampleRate"));
  }

  public String getTrackingId()
  {
    GAUsage.getInstance().setUsage(GAUsage.Field.GET_TRACKING_ID);
    return this.mModel.get("trackingId");
  }

  public boolean isAnonymizeIpEnabled()
  {
    GAUsage.getInstance().setUsage(GAUsage.Field.GET_ANONYMIZE_IP);
    return Utils.safeParseBoolean(this.mModel.get("anonymizeIp"));
  }

  public boolean isUseSecure()
  {
    GAUsage.getInstance().setUsage(GAUsage.Field.GET_USE_SECURE);
    return Boolean.parseBoolean(this.mModel.get("useSecure"));
  }

  public void send(String paramString, Map<String, String> paramMap)
  {
    assertTrackerOpen();
    GAUsage.getInstance().setUsage(GAUsage.Field.SEND);
    internalSend(paramString, paramMap);
  }

  public void sendEvent(String paramString1, String paramString2, String paramString3, Long paramLong)
  {
    assertTrackerOpen();
    GAUsage.getInstance().setUsage(GAUsage.Field.TRACK_EVENT);
    GAUsage.getInstance().setDisableUsage(true);
    internalSend("event", constructEvent(paramString1, paramString2, paramString3, paramLong));
    GAUsage.getInstance().setDisableUsage(false);
  }

  public void sendException(String paramString, Throwable paramThrowable, boolean paramBoolean)
  {
    assertTrackerOpen();
    GAUsage.getInstance().setUsage(GAUsage.Field.TRACK_EXCEPTION_WITH_THROWABLE);
    String str;
    if (this.mExceptionParser != null)
      str = this.mExceptionParser.getDescription(paramString, paramThrowable);
    while (true)
    {
      GAUsage.getInstance().setDisableUsage(true);
      sendException(str, paramBoolean);
      GAUsage.getInstance().setDisableUsage(false);
      return;
      try
      {
        GAUsage.getInstance().setDisableUsage(true);
        internalSend("exception", constructRawException(paramString, paramThrowable, paramBoolean));
        GAUsage.getInstance().setDisableUsage(false);
        return;
      }
      catch (IOException localIOException)
      {
        Log.w("trackException: couldn't serialize, sending \"Unknown Exception\"");
        str = "Unknown Exception";
      }
    }
  }

  public void sendException(String paramString, boolean paramBoolean)
  {
    assertTrackerOpen();
    GAUsage.getInstance().setUsage(GAUsage.Field.TRACK_EXCEPTION_WITH_DESCRIPTION);
    GAUsage.getInstance().setDisableUsage(true);
    internalSend("exception", constructException(paramString, paramBoolean));
    GAUsage.getInstance().setDisableUsage(false);
  }

  public void sendSocial(String paramString1, String paramString2, String paramString3)
  {
    assertTrackerOpen();
    GAUsage.getInstance().setUsage(GAUsage.Field.TRACK_SOCIAL);
    GAUsage.getInstance().setDisableUsage(true);
    internalSend("social", constructSocial(paramString1, paramString2, paramString3));
    GAUsage.getInstance().setDisableUsage(false);
  }

  public void sendTiming(String paramString1, long paramLong, String paramString2, String paramString3)
  {
    assertTrackerOpen();
    GAUsage.getInstance().setUsage(GAUsage.Field.TRACK_TIMING);
    GAUsage.getInstance().setDisableUsage(true);
    internalSend("timing", constructTiming(paramString1, paramLong, paramString2, paramString3));
    GAUsage.getInstance().setDisableUsage(false);
  }

  public void sendTransaction(Transaction paramTransaction)
  {
    assertTrackerOpen();
    GAUsage.getInstance().setUsage(GAUsage.Field.TRACK_TRANSACTION);
    GAUsage.getInstance().setDisableUsage(true);
    internalSend("tran", constructTransaction(paramTransaction));
    Iterator localIterator = paramTransaction.getItems().iterator();
    while (localIterator.hasNext())
      internalSend("item", constructItem((Transaction.Item)localIterator.next(), paramTransaction));
    GAUsage.getInstance().setDisableUsage(false);
  }

  public void sendView()
  {
    assertTrackerOpen();
    if (TextUtils.isEmpty(this.mModel.get("description")))
      throw new IllegalStateException("trackView requires a appScreen to be set");
    GAUsage.getInstance().setUsage(GAUsage.Field.TRACK_VIEW);
    internalSend("appview", null);
  }

  public void sendView(String paramString)
  {
    assertTrackerOpen();
    if (TextUtils.isEmpty(paramString))
      throw new IllegalStateException("trackView requires a appScreen to be set");
    GAUsage.getInstance().setUsage(GAUsage.Field.TRACK_VIEW_WITH_APPSCREEN);
    this.mModel.set("description", paramString);
    internalSend("appview", null);
  }

  public void set(String paramString1, String paramString2)
  {
    GAUsage.getInstance().setUsage(GAUsage.Field.SET);
    this.mModel.set(paramString1, paramString2);
  }

  public void setAnonymizeIp(boolean paramBoolean)
  {
    GAUsage.getInstance().setUsage(GAUsage.Field.SET_ANONYMIZE_IP);
    this.mModel.set("anonymizeIp", Boolean.toString(paramBoolean));
  }

  public void setAppId(String paramString)
  {
    GAUsage.getInstance().setUsage(GAUsage.Field.SET_APP_ID);
    this.mModel.set("appId", paramString);
  }

  public void setAppInstallerId(String paramString)
  {
    GAUsage.getInstance().setUsage(GAUsage.Field.SET_APP_INSTALLER_ID);
    this.mModel.set("appInstallerId", paramString);
  }

  public void setAppName(String paramString)
  {
    if (this.mIsTrackingStarted)
    {
      Log.wDebug("Tracking already started, setAppName call ignored");
      return;
    }
    if (TextUtils.isEmpty(paramString))
    {
      Log.wDebug("setting appName to empty value not allowed, call ignored");
      return;
    }
    GAUsage.getInstance().setUsage(GAUsage.Field.SET_APP_NAME);
    this.mModel.set("appName", paramString);
  }

  public void setAppScreen(String paramString)
  {
    assertTrackerOpen();
    GAUsage.getInstance().setUsage(GAUsage.Field.SET_APP_SCREEN);
    this.mModel.set("description", paramString);
  }

  public void setAppVersion(String paramString)
  {
    if (this.mIsTrackingStarted)
    {
      Log.wDebug("Tracking already started, setAppVersion call ignored");
      return;
    }
    GAUsage.getInstance().setUsage(GAUsage.Field.SET_APP_VERSION);
    this.mModel.set("appVersion", paramString);
  }

  public void setCampaign(String paramString)
  {
    GAUsage.getInstance().setUsage(GAUsage.Field.SET_CAMPAIGN);
    this.mModel.setForNextHit("campaign", paramString);
  }

  public void setCustomDimension(int paramInt, String paramString)
  {
    if (paramInt < 1)
    {
      Log.w("index must be > 0, ignoring setCustomDimension call for " + paramInt + ", " + paramString);
      return;
    }
    this.mModel.setForNextHit(Utils.getSlottedModelField("customDimension", paramInt), paramString);
  }

  public void setCustomDimensionsAndMetrics(Map<Integer, String> paramMap, Map<Integer, Long> paramMap1)
  {
    if (paramMap != null)
    {
      Iterator localIterator2 = paramMap.keySet().iterator();
      while (localIterator2.hasNext())
      {
        Integer localInteger2 = (Integer)localIterator2.next();
        setCustomDimension(localInteger2.intValue(), (String)paramMap.get(localInteger2));
      }
    }
    if (paramMap1 != null)
    {
      Iterator localIterator1 = paramMap1.keySet().iterator();
      while (localIterator1.hasNext())
      {
        Integer localInteger1 = (Integer)localIterator1.next();
        setCustomMetric(localInteger1.intValue(), (Long)paramMap1.get(localInteger1));
      }
    }
  }

  public void setCustomMetric(int paramInt, Long paramLong)
  {
    if (paramInt < 1)
    {
      Log.w("index must be > 0, ignoring setCustomMetric call for " + paramInt + ", " + paramLong);
      return;
    }
    String str = null;
    if (paramLong != null)
      str = Long.toString(paramLong.longValue());
    this.mModel.setForNextHit(Utils.getSlottedModelField("customMetric", paramInt), str);
  }

  public void setExceptionParser(ExceptionParser paramExceptionParser)
  {
    GAUsage.getInstance().setUsage(GAUsage.Field.SET_EXCEPTION_PARSER);
    this.mExceptionParser = paramExceptionParser;
  }

  @VisibleForTesting
  void setLastTrackTime(long paramLong)
  {
    this.mLastTrackTime = paramLong;
  }

  public void setReferrer(String paramString)
  {
    GAUsage.getInstance().setUsage(GAUsage.Field.SET_REFERRER);
    this.mModel.setForNextHit("referrer", paramString);
  }

  public void setSampleRate(double paramDouble)
  {
    GAUsage.getInstance().setUsage(GAUsage.Field.SET_SAMPLE_RATE);
    this.mModel.set("sampleRate", Double.toString(paramDouble));
  }

  public void setStartSession(boolean paramBoolean)
  {
    assertTrackerOpen();
    GAUsage.getInstance().setUsage(GAUsage.Field.SET_START_SESSION);
    SimpleModel localSimpleModel = this.mModel;
    if (paramBoolean);
    for (String str = "start"; ; str = null)
    {
      localSimpleModel.setForNextHit("sessionControl", str);
      return;
    }
  }

  @VisibleForTesting
  public void setThrottlingEnabled(boolean paramBoolean)
  {
    this.mIsThrottlingEnabled = paramBoolean;
  }

  @VisibleForTesting
  void setTokens(long paramLong)
  {
    this.mTokens = paramLong;
  }

  public void setUseSecure(boolean paramBoolean)
  {
    GAUsage.getInstance().setUsage(GAUsage.Field.SET_USE_SECURE);
    this.mModel.set("useSecure", Boolean.toString(paramBoolean));
  }

  @VisibleForTesting
  boolean tokensAvailable()
  {
    int i = 1;
    monitorenter;
    while (true)
    {
      try
      {
        boolean bool = this.mIsThrottlingEnabled;
        if (!bool)
          return i;
        long l1 = System.currentTimeMillis();
        if (this.mTokens >= 120000L)
          continue;
        long l2 = l1 - this.mLastTrackTime;
        if (l2 <= 0L)
          continue;
        this.mTokens = Math.min(120000L, l2 + this.mTokens);
        this.mLastTrackTime = l1;
        if (this.mTokens >= 2000L)
        {
          this.mTokens -= 2000L;
          continue;
        }
      }
      finally
      {
        monitorexit;
      }
      Log.wDebug("Excessive tracking detected.  Tracking call ignored.");
      i = 0;
    }
  }

  @Deprecated
  public void trackEvent(String paramString1, String paramString2, String paramString3, Long paramLong)
  {
    sendEvent(paramString1, paramString2, paramString3, paramLong);
  }

  @Deprecated
  public void trackException(String paramString, Throwable paramThrowable, boolean paramBoolean)
  {
    sendException(paramString, paramThrowable, paramBoolean);
  }

  @Deprecated
  public void trackException(String paramString, boolean paramBoolean)
  {
    sendException(paramString, paramBoolean);
  }

  @Deprecated
  public void trackSocial(String paramString1, String paramString2, String paramString3)
  {
    sendSocial(paramString1, paramString2, paramString3);
  }

  @Deprecated
  public void trackTiming(String paramString1, long paramLong, String paramString2, String paramString3)
  {
    sendTiming(paramString1, paramLong, paramString2, paramString3);
  }

  @Deprecated
  public void trackTransaction(Transaction paramTransaction)
  {
    sendTransaction(paramTransaction);
  }

  @Deprecated
  public void trackView()
  {
    sendView();
  }

  @Deprecated
  public void trackView(String paramString)
  {
    sendView(paramString);
  }

  private static class SimpleModel
  {
    private Map<String, String> permanentMap = new HashMap();
    private Map<String, String> temporaryMap = new HashMap();

    public void clearTemporaryValues()
    {
      monitorenter;
      try
      {
        this.temporaryMap.clear();
        monitorexit;
        return;
      }
      finally
      {
        localObject = finally;
        monitorexit;
      }
      throw localObject;
    }

    public String get(String paramString)
    {
      monitorenter;
      try
      {
        Object localObject2 = (String)this.temporaryMap.get(paramString);
        if (localObject2 != null);
        while (true)
        {
          return localObject2;
          String str = (String)this.permanentMap.get(paramString);
          localObject2 = str;
        }
      }
      finally
      {
        monitorexit;
      }
      throw localObject1;
    }

    public Map<String, String> getKeysAndValues()
    {
      monitorenter;
      try
      {
        HashMap localHashMap = new HashMap(this.permanentMap);
        localHashMap.putAll(this.temporaryMap);
        monitorexit;
        return localHashMap;
      }
      finally
      {
        localObject = finally;
        monitorexit;
      }
      throw localObject;
    }

    public void set(String paramString1, String paramString2)
    {
      monitorenter;
      try
      {
        this.permanentMap.put(paramString1, paramString2);
        monitorexit;
        return;
      }
      finally
      {
        localObject = finally;
        monitorexit;
      }
      throw localObject;
    }

    public void setAll(Map<String, String> paramMap, Boolean paramBoolean)
    {
      monitorenter;
      try
      {
        if (paramBoolean.booleanValue())
          this.temporaryMap.putAll(paramMap);
        while (true)
        {
          return;
          this.permanentMap.putAll(paramMap);
        }
      }
      finally
      {
        monitorexit;
      }
      throw localObject;
    }

    public void setForNextHit(String paramString1, String paramString2)
    {
      monitorenter;
      try
      {
        this.temporaryMap.put(paramString1, paramString2);
        monitorexit;
        return;
      }
      finally
      {
        localObject = finally;
        monitorexit;
      }
      throw localObject;
    }
  }
}

/* Location:           C:\Users\Yash\Desktop\apk to src\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.google.analytics.tracking.android.Tracker
 * JD-Core Version:    0.6.0
 */
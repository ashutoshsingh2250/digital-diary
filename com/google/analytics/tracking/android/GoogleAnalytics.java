package com.google.analytics.tracking.android;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import com.google.android.gms.common.util.VisibleForTesting;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class GoogleAnalytics
  implements TrackerHandler
{
  private static GoogleAnalytics sInstance;
  private AdHitIdGenerator mAdHitIdGenerator;
  private volatile Boolean mAppOptOut;
  private volatile String mClientId;
  private Context mContext;
  private boolean mDebug;
  private Tracker mDefaultTracker;
  private String mLastTrackingId;
  private AnalyticsThread mThread;
  private final Map<String, Tracker> mTrackers = new HashMap();

  @VisibleForTesting
  GoogleAnalytics()
  {
  }

  private GoogleAnalytics(Context paramContext)
  {
    this(paramContext, GAThread.getInstance(paramContext));
  }

  private GoogleAnalytics(Context paramContext, AnalyticsThread paramAnalyticsThread)
  {
    if (paramContext == null)
      throw new IllegalArgumentException("context cannot be null");
    this.mContext = paramContext.getApplicationContext();
    this.mThread = paramAnalyticsThread;
    this.mAdHitIdGenerator = new AdHitIdGenerator();
    this.mThread.requestAppOptOut(new AppOptOutCallback()
    {
      public void reportAppOptOut(boolean paramBoolean)
      {
        GoogleAnalytics.access$002(GoogleAnalytics.this, Boolean.valueOf(paramBoolean));
      }
    });
    this.mThread.requestClientId(new AnalyticsThread.ClientIdCallback()
    {
      public void reportClientId(String paramString)
      {
        GoogleAnalytics.access$102(GoogleAnalytics.this, paramString);
      }
    });
  }

  @VisibleForTesting
  static void clearInstance()
  {
    monitorenter;
    try
    {
      sInstance = null;
      return;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  static GoogleAnalytics getInstance()
  {
    monitorenter;
    try
    {
      GoogleAnalytics localGoogleAnalytics = sInstance;
      return localGoogleAnalytics;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public static GoogleAnalytics getInstance(Context paramContext)
  {
    monitorenter;
    try
    {
      if (sInstance == null)
        sInstance = new GoogleAnalytics(paramContext);
      GoogleAnalytics localGoogleAnalytics = sInstance;
      return localGoogleAnalytics;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  @VisibleForTesting
  static GoogleAnalytics getNewInstance(Context paramContext, AnalyticsThread paramAnalyticsThread)
  {
    monitorenter;
    try
    {
      if (sInstance != null)
        sInstance.close();
      sInstance = new GoogleAnalytics(paramContext, paramAnalyticsThread);
      GoogleAnalytics localGoogleAnalytics = sInstance;
      return localGoogleAnalytics;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  @VisibleForTesting
  void close()
  {
  }

  public void closeTracker(Tracker paramTracker)
  {
    monitorenter;
    try
    {
      this.mTrackers.values().remove(paramTracker);
      if (paramTracker == this.mDefaultTracker)
        this.mDefaultTracker = null;
      return;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  @VisibleForTesting
  Boolean getAppOptOut()
  {
    return this.mAppOptOut;
  }

  String getClientIdForAds()
  {
    if (this.mClientId == null)
      return null;
    return this.mClientId.toString();
  }

  public Tracker getDefaultTracker()
  {
    monitorenter;
    try
    {
      GAUsage.getInstance().setUsage(GAUsage.Field.GET_DEFAULT_TRACKER);
      Tracker localTracker = this.mDefaultTracker;
      return localTracker;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public Tracker getTracker(String paramString)
  {
    monitorenter;
    if (paramString == null)
      try
      {
        throw new IllegalArgumentException("trackingId cannot be null");
      }
      finally
      {
        monitorexit;
      }
    Tracker localTracker = (Tracker)this.mTrackers.get(paramString);
    if (localTracker == null)
    {
      localTracker = new Tracker(paramString, this);
      this.mTrackers.put(paramString, localTracker);
      if (this.mDefaultTracker == null)
        this.mDefaultTracker = localTracker;
    }
    GAUsage.getInstance().setUsage(GAUsage.Field.GET_TRACKER);
    monitorexit;
    return localTracker;
  }

  String getTrackingIdForAds()
  {
    return this.mLastTrackingId;
  }

  public boolean isDebugEnabled()
  {
    GAUsage.getInstance().setUsage(GAUsage.Field.GET_DEBUG);
    return this.mDebug;
  }

  public void requestAppOptOut(AppOptOutCallback paramAppOptOutCallback)
  {
    GAUsage.getInstance().setUsage(GAUsage.Field.REQUEST_APP_OPT_OUT);
    if (this.mAppOptOut != null)
    {
      paramAppOptOutCallback.reportAppOptOut(this.mAppOptOut.booleanValue());
      return;
    }
    this.mThread.requestAppOptOut(paramAppOptOutCallback);
  }

  public void sendHit(Map<String, String> paramMap)
  {
    monitorenter;
    if (paramMap == null)
      try
      {
        throw new IllegalArgumentException("hit cannot be null");
      }
      finally
      {
        monitorexit;
      }
    paramMap.put("language", Utils.getLanguage(Locale.getDefault()));
    paramMap.put("adSenseAdMobHitId", Integer.toString(this.mAdHitIdGenerator.getAdHitId()));
    paramMap.put("screenResolution", this.mContext.getResources().getDisplayMetrics().widthPixels + "x" + this.mContext.getResources().getDisplayMetrics().heightPixels);
    paramMap.put("usage", GAUsage.getInstance().getAndClearSequence());
    GAUsage.getInstance().getAndClearUsage();
    this.mThread.sendHit(paramMap);
    this.mLastTrackingId = ((String)paramMap.get("trackingId"));
    monitorexit;
  }

  public void setAppOptOut(boolean paramBoolean)
  {
    GAUsage.getInstance().setUsage(GAUsage.Field.SET_APP_OPT_OUT);
    this.mAppOptOut = Boolean.valueOf(paramBoolean);
    this.mThread.setAppOptOut(paramBoolean);
  }

  public void setDebug(boolean paramBoolean)
  {
    GAUsage.getInstance().setUsage(GAUsage.Field.SET_DEBUG);
    this.mDebug = paramBoolean;
    Log.setDebug(paramBoolean);
  }

  public void setDefaultTracker(Tracker paramTracker)
  {
    monitorenter;
    try
    {
      GAUsage.getInstance().setUsage(GAUsage.Field.SET_DEFAULT_TRACKER);
      this.mDefaultTracker = paramTracker;
      return;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public static abstract interface AppOptOutCallback
  {
    public abstract void reportAppOptOut(boolean paramBoolean);
  }
}

/* Location:           C:\Users\Yash\Desktop\apk to src\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.google.analytics.tracking.android.GoogleAnalytics
 * JD-Core Version:    0.6.0
 */
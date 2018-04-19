package com.google.analytics.tracking.android;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.TextUtils;
import com.google.android.gms.analytics.internal.Command;
import com.google.android.gms.common.util.VisibleForTesting;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;

class GAThread extends Thread
  implements AnalyticsThread
{
  static final String API_VERSION = "1";
  private static final String CLIENT_VERSION = "ma1b5";
  private static final int MAX_SAMPLE_RATE = 100;
  private static final int SAMPLE_RATE_MODULO = 10000;
  private static final int SAMPLE_RATE_MULTIPLIER = 100;
  private static GAThread sInstance;
  private volatile boolean mAppOptOut;
  private volatile String mClientId;
  private volatile boolean mClosed = false;
  private volatile List<Command> mCommands;
  private final Context mContext;
  private volatile boolean mDisabled = false;
  private volatile String mInstallCampaign;
  private volatile MetaModel mMetaModel;
  private volatile ServiceProxy mServiceProxy;
  private final LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue();

  private GAThread(Context paramContext)
  {
    super("GAThread");
    if (paramContext != null);
    for (this.mContext = paramContext.getApplicationContext(); ; this.mContext = paramContext)
    {
      start();
      return;
    }
  }

  @VisibleForTesting
  GAThread(Context paramContext, ServiceProxy paramServiceProxy)
  {
    super("GAThread");
    if (paramContext != null);
    for (this.mContext = paramContext.getApplicationContext(); ; this.mContext = paramContext)
    {
      this.mServiceProxy = paramServiceProxy;
      start();
      return;
    }
  }

  private void fillAppParameters(Map<String, String> paramMap)
  {
    PackageManager localPackageManager = this.mContext.getPackageManager();
    String str1 = this.mContext.getPackageName();
    String str2 = localPackageManager.getInstallerPackageName(str1);
    String str3 = str1;
    try
    {
      PackageInfo localPackageInfo = localPackageManager.getPackageInfo(this.mContext.getPackageName(), 0);
      str4 = null;
      if (localPackageInfo != null)
      {
        str3 = localPackageManager.getApplicationLabel(localPackageInfo.applicationInfo).toString();
        str4 = localPackageInfo.versionName;
      }
      putIfAbsent(paramMap, "appName", str3);
      putIfAbsent(paramMap, "appVersion", str4);
      putIfAbsent(paramMap, "appId", str1);
      putIfAbsent(paramMap, "appInstallerId", str2);
      paramMap.put("apiVersion", "1");
      return;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      while (true)
      {
        Log.e("Error retrieving package info: appName set to " + str3);
        String str4 = null;
      }
    }
  }

  private void fillCampaignParameters(Map<String, String> paramMap)
  {
    String str = Utils.filterCampaign((String)paramMap.get("campaign"));
    if (TextUtils.isEmpty(str))
      return;
    Map localMap = Utils.parseURLParameters(str);
    paramMap.put("campaignContent", localMap.get("utm_content"));
    paramMap.put("campaignMedium", localMap.get("utm_medium"));
    paramMap.put("campaignName", localMap.get("utm_campaign"));
    paramMap.put("campaignSource", localMap.get("utm_source"));
    paramMap.put("campaignKeyword", localMap.get("utm_term"));
    paramMap.put("campaignId", localMap.get("utm_id"));
    paramMap.put("gclid", localMap.get("gclid"));
    paramMap.put("dclid", localMap.get("dclid"));
    paramMap.put("gmob_t", localMap.get("gmob_t"));
  }

  private void fillExceptionParameters(Map<String, String> paramMap)
  {
    String str = (String)paramMap.get("rawException");
    if (str == null);
    while (true)
    {
      return;
      paramMap.remove("rawException");
      ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(Utils.hexDecode(str));
      try
      {
        ObjectInputStream localObjectInputStream = new ObjectInputStream(localByteArrayInputStream);
        Object localObject = localObjectInputStream.readObject();
        localObjectInputStream.close();
        if (!(localObject instanceof Throwable))
          continue;
        Throwable localThrowable = (Throwable)localObject;
        ArrayList localArrayList = new ArrayList();
        paramMap.put("exDescription", new StandardExceptionParser(this.mContext, localArrayList).getDescription((String)paramMap.get("exceptionThreadName"), localThrowable));
        return;
      }
      catch (IOException localIOException)
      {
        Log.w("IOException reading exception");
        return;
      }
      catch (ClassNotFoundException localClassNotFoundException)
      {
        Log.w("ClassNotFoundException reading exception");
      }
    }
  }

  private String generateClientId()
  {
    String str = UUID.randomUUID().toString().toLowerCase();
    if (!storeClientId(str))
      str = "0";
    return str;
  }

  @VisibleForTesting
  static String getAndClearCampaign(Context paramContext)
  {
    try
    {
      FileInputStream localFileInputStream = paramContext.openFileInput("gaInstallData");
      byte[] arrayOfByte = new byte[8192];
      int i = localFileInputStream.read(arrayOfByte, 0, 8192);
      if (localFileInputStream.available() > 0)
      {
        Log.e("Too much campaign data, ignoring it.");
        localFileInputStream.close();
        paramContext.deleteFile("gaInstallData");
        return null;
      }
      localFileInputStream.close();
      paramContext.deleteFile("gaInstallData");
      if (i <= 0)
      {
        Log.w("Campaign file is empty.");
        return null;
      }
      String str = new String(arrayOfByte, 0, i);
      Log.i("Campaign found: " + str);
      return str;
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      Log.i("No campaign data found.");
      return null;
    }
    catch (IOException localIOException)
    {
      Log.e("Error reading campaign data.");
      paramContext.deleteFile("gaInstallData");
    }
    return null;
  }

  private String getHostUrl(Map<String, String> paramMap)
  {
    String str = (String)paramMap.get("internalHitUrl");
    if (str == null)
    {
      if (!paramMap.containsKey("useSecure"))
        break label57;
      if (Utils.safeParseBoolean((String)paramMap.get("useSecure")))
        str = "https://ssl.google-analytics.com/collect";
    }
    else
    {
      return str;
    }
    return "http://www.google-analytics.com/collect";
    label57: return "https://ssl.google-analytics.com/collect";
  }

  static GAThread getInstance(Context paramContext)
  {
    if (sInstance == null)
      sInstance = new GAThread(paramContext);
    return sInstance;
  }

  private void init()
  {
    this.mServiceProxy.createService();
    this.mCommands = new ArrayList();
    this.mCommands.add(new Command("appendVersion", "_v", "ma1b5"));
    this.mCommands.add(new Command("appendQueueTime", "qt", null));
    this.mCommands.add(new Command("appendCacheBuster", "z", null));
    this.mMetaModel = new MetaModel();
    MetaModelInitializer.set(this.mMetaModel);
  }

  private boolean isSampledOut(Map<String, String> paramMap)
  {
    if (paramMap.get("sampleRate") != null)
    {
      double d = Utils.safeParseDouble((String)paramMap.get("sampleRate"));
      if (d <= 0.0D)
        return true;
      if (d < 100.0D)
      {
        String str = (String)paramMap.get("clientId");
        if ((str != null) && (Math.abs(str.hashCode()) % 10000 >= 100.0D * d))
          return true;
      }
    }
    return false;
  }

  private boolean loadAppOptOut()
  {
    return this.mContext.getFileStreamPath("gaOptOut").exists();
  }

  private String printStackTrace(Throwable paramThrowable)
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    PrintStream localPrintStream = new PrintStream(localByteArrayOutputStream);
    paramThrowable.printStackTrace(localPrintStream);
    localPrintStream.flush();
    return new String(localByteArrayOutputStream.toByteArray());
  }

  private void putIfAbsent(Map<String, String> paramMap, String paramString1, String paramString2)
  {
    if (!paramMap.containsKey(paramString1))
      paramMap.put(paramString1, paramString2);
  }

  private void queueToThread(Runnable paramRunnable)
  {
    this.queue.add(paramRunnable);
  }

  private boolean storeClientId(String paramString)
  {
    try
    {
      FileOutputStream localFileOutputStream = this.mContext.openFileOutput("gaClientId", 0);
      localFileOutputStream.write(paramString.getBytes());
      localFileOutputStream.close();
      return true;
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      Log.e("Error creating clientId file.");
      return false;
    }
    catch (IOException localIOException)
    {
      Log.e("Error writing to clientId file.");
    }
    return false;
  }

  @VisibleForTesting
  void close()
  {
    this.mClosed = true;
    interrupt();
  }

  public void dispatch()
  {
    queueToThread(new Runnable()
    {
      public void run()
      {
        GAThread.this.mServiceProxy.dispatch();
      }
    });
  }

  public LinkedBlockingQueue<Runnable> getQueue()
  {
    return this.queue;
  }

  public Thread getThread()
  {
    return this;
  }

  // ERROR //
  @VisibleForTesting
  String initializeClientId()
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_1
    //   2: aload_0
    //   3: getfield 65	com/google/analytics/tracking/android/GAThread:mContext	Landroid/content/Context;
    //   6: ldc_w 482
    //   9: invokevirtual 336	android/content/Context:openFileInput	(Ljava/lang/String;)Ljava/io/FileInputStream;
    //   12: astore 9
    //   14: sipush 128
    //   17: newarray byte
    //   19: astore 10
    //   21: aload 9
    //   23: aload 10
    //   25: iconst_0
    //   26: sipush 128
    //   29: invokevirtual 342	java/io/FileInputStream:read	([BII)I
    //   32: istore 11
    //   34: aload 9
    //   36: invokevirtual 346	java/io/FileInputStream:available	()I
    //   39: ifle +26 -> 65
    //   42: ldc_w 519
    //   45: invokestatic 199	com/google/analytics/tracking/android/Log:e	(Ljava/lang/String;)I
    //   48: pop
    //   49: aload 9
    //   51: invokevirtual 349	java/io/FileInputStream:close	()V
    //   54: aload_0
    //   55: getfield 65	com/google/analytics/tracking/android/GAThread:mContext	Landroid/content/Context;
    //   58: ldc_w 332
    //   61: invokevirtual 352	android/content/Context:deleteFile	(Ljava/lang/String;)Z
    //   64: pop
    //   65: iload 11
    //   67: ifgt +37 -> 104
    //   70: ldc_w 521
    //   73: invokestatic 199	com/google/analytics/tracking/android/Log:e	(Ljava/lang/String;)I
    //   76: pop
    //   77: aload 9
    //   79: invokevirtual 349	java/io/FileInputStream:close	()V
    //   82: aload_0
    //   83: getfield 65	com/google/analytics/tracking/android/GAThread:mContext	Landroid/content/Context;
    //   86: ldc_w 332
    //   89: invokevirtual 352	android/content/Context:deleteFile	(Ljava/lang/String;)Z
    //   92: pop
    //   93: aload_1
    //   94: ifnonnull +8 -> 102
    //   97: aload_0
    //   98: invokespecial 523	com/google/analytics/tracking/android/GAThread:generateClientId	()Ljava/lang/String;
    //   101: astore_1
    //   102: aload_1
    //   103: areturn
    //   104: new 207	java/lang/String
    //   107: dup
    //   108: aload 10
    //   110: iconst_0
    //   111: iload 11
    //   113: invokespecial 357	java/lang/String:<init>	([BII)V
    //   116: astore 14
    //   118: aload 9
    //   120: invokevirtual 349	java/io/FileInputStream:close	()V
    //   123: aload 14
    //   125: astore_1
    //   126: goto -33 -> 93
    //   129: astore 6
    //   131: ldc_w 525
    //   134: invokestatic 199	com/google/analytics/tracking/android/Log:e	(Ljava/lang/String;)I
    //   137: pop
    //   138: aload_0
    //   139: getfield 65	com/google/analytics/tracking/android/GAThread:mContext	Landroid/content/Context;
    //   142: ldc_w 332
    //   145: invokevirtual 352	android/content/Context:deleteFile	(Ljava/lang/String;)Z
    //   148: pop
    //   149: goto -56 -> 93
    //   152: astore_3
    //   153: ldc_w 527
    //   156: invokestatic 199	com/google/analytics/tracking/android/Log:e	(Ljava/lang/String;)I
    //   159: pop
    //   160: aload_0
    //   161: getfield 65	com/google/analytics/tracking/android/GAThread:mContext	Landroid/content/Context;
    //   164: ldc_w 332
    //   167: invokevirtual 352	android/content/Context:deleteFile	(Ljava/lang/String;)Z
    //   170: pop
    //   171: goto -78 -> 93
    //   174: astore 17
    //   176: aload 14
    //   178: astore_1
    //   179: goto -26 -> 153
    //   182: astore 16
    //   184: aload 14
    //   186: astore_1
    //   187: goto -56 -> 131
    //   190: astore_2
    //   191: aconst_null
    //   192: astore_1
    //   193: goto -100 -> 93
    //   196: astore 15
    //   198: aload 14
    //   200: astore_1
    //   201: goto -108 -> 93
    //
    // Exception table:
    //   from	to	target	type
    //   2	65	129	java/io/IOException
    //   70	93	129	java/io/IOException
    //   104	118	129	java/io/IOException
    //   2	65	152	java/lang/NumberFormatException
    //   70	93	152	java/lang/NumberFormatException
    //   104	118	152	java/lang/NumberFormatException
    //   118	123	174	java/lang/NumberFormatException
    //   118	123	182	java/io/IOException
    //   2	65	190	java/io/FileNotFoundException
    //   70	93	190	java/io/FileNotFoundException
    //   104	118	190	java/io/FileNotFoundException
    //   118	123	196	java/io/FileNotFoundException
  }

  @VisibleForTesting
  boolean isDisabled()
  {
    return this.mDisabled;
  }

  public void requestAppOptOut(GoogleAnalytics.AppOptOutCallback paramAppOptOutCallback)
  {
    queueToThread(new Runnable(paramAppOptOutCallback)
    {
      public void run()
      {
        this.val$callback.reportAppOptOut(GAThread.this.mAppOptOut);
      }
    });
  }

  public void requestClientId(AnalyticsThread.ClientIdCallback paramClientIdCallback)
  {
    queueToThread(new Runnable(paramClientIdCallback)
    {
      public void run()
      {
        this.val$callback.reportClientId(GAThread.this.mClientId);
      }
    });
  }

  public void run()
  {
    try
    {
      Thread.sleep(5000L);
      if (this.mServiceProxy == null)
        this.mServiceProxy = new GAServiceProxy(this.mContext, this);
      init();
    }
    catch (InterruptedException localInterruptedException1)
    {
      try
      {
        this.mAppOptOut = loadAppOptOut();
        this.mClientId = initializeClientId();
        this.mInstallCampaign = getAndClearCampaign(this.mContext);
        while (!this.mClosed)
        {
          try
          {
            Runnable localRunnable = (Runnable)this.queue.take();
            if (this.mDisabled)
              continue;
            localRunnable.run();
          }
          catch (InterruptedException localInterruptedException2)
          {
            Log.i(localInterruptedException2.toString());
          }
          catch (Throwable localThrowable2)
          {
            Log.e("Error on GAThread: " + printStackTrace(localThrowable2));
            Log.e("Google Analytics is shutting down.");
            this.mDisabled = true;
          }
          continue;
          localInterruptedException1 = localInterruptedException1;
          Log.w("sleep interrupted in GAThread initialize");
        }
      }
      catch (Throwable localThrowable1)
      {
        while (true)
        {
          Log.e("Error initializing the GAThread: " + printStackTrace(localThrowable1));
          Log.e("Google Analytics will not start up.");
          this.mDisabled = true;
        }
      }
    }
  }

  public void sendHit(Map<String, String> paramMap)
  {
    HashMap localHashMap = new HashMap(paramMap);
    long l = System.currentTimeMillis();
    localHashMap.put("hitTime", Long.toString(l));
    queueToThread(new Runnable(localHashMap, l)
    {
      public void run()
      {
        this.val$hitCopy.put("clientId", GAThread.this.mClientId);
        if ((GAThread.this.mAppOptOut) || (GAThread.this.isSampledOut(this.val$hitCopy)))
          return;
        if (!TextUtils.isEmpty(GAThread.this.mInstallCampaign))
        {
          this.val$hitCopy.put("campaign", GAThread.this.mInstallCampaign);
          GAThread.access$302(GAThread.this, null);
        }
        GAThread.this.fillAppParameters(this.val$hitCopy);
        GAThread.this.fillCampaignParameters(this.val$hitCopy);
        GAThread.this.fillExceptionParameters(this.val$hitCopy);
        Map localMap = HitBuilder.generateHitParams(GAThread.this.mMetaModel, this.val$hitCopy);
        GAThread.this.mServiceProxy.putHit(localMap, this.val$hitTime, GAThread.this.getHostUrl(this.val$hitCopy), GAThread.this.mCommands);
      }
    });
  }

  public void setAppOptOut(boolean paramBoolean)
  {
    queueToThread(new Runnable(paramBoolean)
    {
      public void run()
      {
        if (GAThread.this.mAppOptOut == this.val$appOptOut)
          return;
        File localFile;
        if (this.val$appOptOut)
          localFile = GAThread.this.mContext.getFileStreamPath("gaOptOut");
        while (true)
        {
          try
          {
            localFile.createNewFile();
            GAThread.this.mServiceProxy.clearHits();
            GAThread.access$102(GAThread.this, this.val$appOptOut);
            return;
          }
          catch (IOException localIOException)
          {
            Log.w("Error creating optOut file.");
            continue;
          }
          GAThread.this.mContext.deleteFile("gaOptOut");
        }
      }
    });
  }
}

/* Location:           C:\Users\Yash\Desktop\apk to src\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.google.analytics.tracking.android.GAThread
 * JD-Core Version:    0.6.0
 */
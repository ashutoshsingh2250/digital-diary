package com.google.analytics.tracking.android;

import android.content.Context;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import com.google.android.gms.common.util.VisibleForTesting;

public class GAServiceManager
  implements ServiceManager
{
  private static final int MSG_KEY = 1;
  private static final Object MSG_OBJECT = new Object();
  private static GAServiceManager instance;
  private boolean connected = true;
  private Context ctx;
  private int dispatchPeriodInSeconds = 1800;
  private Handler handler;
  private boolean listenForNetwork = true;
  private AnalyticsStoreStateListener listener = new AnalyticsStoreStateListener()
  {
    public void reportStoreIsEmpty(boolean paramBoolean)
    {
      GAServiceManager.this.updatePowerSaveMode(paramBoolean, GAServiceManager.this.connected);
    }
  };
  private GANetworkReceiver networkReceiver;
  private boolean pendingDispatch = true;
  private AnalyticsStore store;
  private boolean storeIsEmpty = false;
  private volatile AnalyticsThread thread;

  private GAServiceManager()
  {
  }

  @VisibleForTesting
  GAServiceManager(Context paramContext, AnalyticsThread paramAnalyticsThread, AnalyticsStore paramAnalyticsStore, boolean paramBoolean)
  {
    this.store = paramAnalyticsStore;
    this.thread = paramAnalyticsThread;
    this.listenForNetwork = paramBoolean;
    initialize(paramContext, paramAnalyticsThread);
  }

  public static GAServiceManager getInstance()
  {
    if (instance == null)
      instance = new GAServiceManager();
    return instance;
  }

  private void initializeHandler()
  {
    this.handler = new Handler(this.ctx.getMainLooper(), new Handler.Callback()
    {
      public boolean handleMessage(Message paramMessage)
      {
        if ((1 == paramMessage.what) && (GAServiceManager.MSG_OBJECT.equals(paramMessage.obj)))
        {
          GAUsage.getInstance().setDisableUsage(true);
          GAServiceManager.this.dispatch();
          GAUsage.getInstance().setDisableUsage(false);
          if ((GAServiceManager.this.dispatchPeriodInSeconds > 0) && (!GAServiceManager.this.storeIsEmpty))
            GAServiceManager.this.handler.sendMessageDelayed(GAServiceManager.this.handler.obtainMessage(1, GAServiceManager.MSG_OBJECT), 1000 * GAServiceManager.this.dispatchPeriodInSeconds);
        }
        return true;
      }
    });
    if (this.dispatchPeriodInSeconds > 0)
      this.handler.sendMessageDelayed(this.handler.obtainMessage(1, MSG_OBJECT), 1000 * this.dispatchPeriodInSeconds);
  }

  private void initializeNetworkReceiver()
  {
    this.networkReceiver = new GANetworkReceiver(this);
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
    this.ctx.registerReceiver(this.networkReceiver, localIntentFilter);
  }

  public void dispatch()
  {
    monitorenter;
    try
    {
      if (this.thread == null)
      {
        Log.w("dispatch call queued.  Need to call GAServiceManager.getInstance().initialize().");
        this.pendingDispatch = true;
      }
      while (true)
      {
        return;
        GAUsage.getInstance().setUsage(GAUsage.Field.DISPATCH);
        this.thread.dispatch();
      }
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  @VisibleForTesting
  AnalyticsStoreStateListener getListener()
  {
    return this.listener;
  }

  AnalyticsStore getStore()
  {
    monitorenter;
    try
    {
      if (this.store != null)
        break label50;
      if (this.ctx == null)
        throw new IllegalStateException("Cant get a store unless we have a context");
    }
    finally
    {
      monitorexit;
    }
    this.store = new PersistentAnalyticsStore(this.listener, this.ctx);
    label50: if (this.handler == null)
      initializeHandler();
    if ((this.networkReceiver == null) && (this.listenForNetwork))
      initializeNetworkReceiver();
    AnalyticsStore localAnalyticsStore = this.store;
    monitorexit;
    return localAnalyticsStore;
  }

  void initialize(Context paramContext, AnalyticsThread paramAnalyticsThread)
  {
    monitorenter;
    try
    {
      Context localContext = this.ctx;
      if (localContext != null);
      while (true)
      {
        return;
        this.ctx = paramContext.getApplicationContext();
        if (this.thread != null)
          continue;
        this.thread = paramAnalyticsThread;
        if (!this.pendingDispatch)
          continue;
        paramAnalyticsThread.dispatch();
      }
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public void setDispatchPeriod(int paramInt)
  {
    monitorenter;
    try
    {
      if (this.handler == null)
      {
        Log.w("Need to call initialize() and be in fallback mode to start dispatch.");
        this.dispatchPeriodInSeconds = paramInt;
      }
      while (true)
      {
        return;
        GAUsage.getInstance().setUsage(GAUsage.Field.SET_DISPATCH_PERIOD);
        if ((!this.storeIsEmpty) && (this.connected) && (this.dispatchPeriodInSeconds > 0))
          this.handler.removeMessages(1, MSG_OBJECT);
        this.dispatchPeriodInSeconds = paramInt;
        if ((paramInt <= 0) || (this.storeIsEmpty) || (!this.connected))
          continue;
        this.handler.sendMessageDelayed(this.handler.obtainMessage(1, MSG_OBJECT), paramInt * 1000);
      }
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public void updateConnectivityStatus(boolean paramBoolean)
  {
    monitorenter;
    try
    {
      updatePowerSaveMode(this.storeIsEmpty, paramBoolean);
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

  @VisibleForTesting
  void updatePowerSaveMode(boolean paramBoolean1, boolean paramBoolean2)
  {
    monitorenter;
    while (true)
    {
      try
      {
        if (this.storeIsEmpty != paramBoolean1)
          continue;
        boolean bool = this.connected;
        if (bool == paramBoolean2)
          return;
        if (((!paramBoolean1) && (paramBoolean2)) || (this.dispatchPeriodInSeconds <= 0))
          continue;
        this.handler.removeMessages(1, MSG_OBJECT);
        if ((paramBoolean1) || (!paramBoolean2) || (this.dispatchPeriodInSeconds <= 0))
          continue;
        this.handler.sendMessageDelayed(this.handler.obtainMessage(1, MSG_OBJECT), 1000 * this.dispatchPeriodInSeconds);
        StringBuilder localStringBuilder = new StringBuilder().append("PowerSaveMode ");
        if (paramBoolean1)
          break label158;
        if (!paramBoolean2)
        {
          break label158;
          Log.iDebug(str);
          this.storeIsEmpty = paramBoolean1;
          this.connected = paramBoolean2;
          continue;
        }
      }
      finally
      {
        monitorexit;
      }
      String str = "terminated.";
      continue;
      label158: str = "initiated.";
    }
  }
}

/* Location:           C:\Users\Yash\Desktop\apk to src\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.google.analytics.tracking.android.GAServiceManager
 * JD-Core Version:    0.6.0
 */
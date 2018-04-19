package com.google.analytics.tracking.android;

import android.content.Context;
import android.content.Intent;
import com.google.android.gms.analytics.internal.Command;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

class GAServiceProxy
  implements ServiceProxy, AnalyticsGmsCoreClient.OnConnectedListener, AnalyticsGmsCoreClient.OnConnectionFailedListener
{
  private static final long FAILED_CONNECT_WAIT_TIME = 3000L;
  private static final int MAX_TRIES = 2;
  private static final long RECONNECT_WAIT_TIME = 5000L;
  private static final long SERVICE_CONNECTION_TIMEOUT = 300000L;
  private volatile AnalyticsClient client;
  private Clock clock;
  private volatile int connectTries;
  private final Context ctx;
  private volatile Timer disconnectCheckTimer;
  private volatile Timer failedConnectTimer;
  private long idleTimeout = 300000L;
  private volatile long lastRequestTime;
  private boolean pendingClearHits;
  private boolean pendingDispatch;
  private final Queue<HitParams> queue = new ConcurrentLinkedQueue();
  private volatile Timer reConnectTimer;
  private volatile ConnectState state;
  private AnalyticsStore store;
  private AnalyticsStore testStore;
  private final AnalyticsThread thread;

  GAServiceProxy(Context paramContext, AnalyticsThread paramAnalyticsThread)
  {
    this(paramContext, paramAnalyticsThread, null);
  }

  GAServiceProxy(Context paramContext, AnalyticsThread paramAnalyticsThread, AnalyticsStore paramAnalyticsStore)
  {
    this.testStore = paramAnalyticsStore;
    this.ctx = paramContext;
    this.thread = paramAnalyticsThread;
    this.clock = new Clock()
    {
      public long currentTimeMillis()
      {
        return System.currentTimeMillis();
      }
    };
    this.connectTries = 0;
    this.state = ConnectState.DISCONNECTED;
  }

  private Timer cancelTimer(Timer paramTimer)
  {
    if (paramTimer != null)
      paramTimer.cancel();
    return null;
  }

  private void clearAllTimers()
  {
    this.reConnectTimer = cancelTimer(this.reConnectTimer);
    this.failedConnectTimer = cancelTimer(this.failedConnectTimer);
    this.disconnectCheckTimer = cancelTimer(this.disconnectCheckTimer);
  }

  private void connectToService()
  {
    monitorenter;
    while (true)
    {
      try
      {
        if (this.client != null)
        {
          ConnectState localConnectState1 = this.state;
          ConnectState localConnectState2 = ConnectState.CONNECTED_LOCAL;
          if (localConnectState1 != localConnectState2)
            try
            {
              this.connectTries = (1 + this.connectTries);
              cancelTimer(this.failedConnectTimer);
              this.state = ConnectState.CONNECTING;
              this.failedConnectTimer = new Timer("Failed Connect");
              this.failedConnectTimer.schedule(new FailedConnectTask(null), 3000L);
              Log.iDebug("connecting to Analytics service");
              this.client.connect();
              monitorexit;
              return;
            }
            catch (SecurityException localSecurityException)
            {
              Log.w("security exception on connectToService");
              useStore();
              continue;
            }
        }
      }
      finally
      {
        monitorexit;
      }
      Log.w("client not initialized.");
      useStore();
    }
  }

  private void disconnectFromService()
  {
    monitorenter;
    try
    {
      if ((this.client != null) && (this.state == ConnectState.CONNECTED_SERVICE))
      {
        this.state = ConnectState.PENDING_DISCONNECT;
        this.client.disconnect();
      }
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

  private void dispatchToStore()
  {
    this.store.dispatch();
    this.pendingDispatch = false;
  }

  private void fireReconnectAttempt()
  {
    this.reConnectTimer = cancelTimer(this.reConnectTimer);
    this.reConnectTimer = new Timer("Service Reconnect");
    this.reConnectTimer.schedule(new ReconnectTask(null), 5000L);
  }

  private void sendQueue()
  {
    monitorenter;
    while (true)
    {
      try
      {
        if (Thread.currentThread().equals(this.thread.getThread()))
          continue;
        this.thread.getQueue().add(new Runnable()
        {
          public void run()
          {
            GAServiceProxy.this.sendQueue();
          }
        });
        return;
        if (!this.pendingClearHits)
          continue;
        clearHits();
        switch (3.$SwitchMap$com$google$analytics$tracking$android$GAServiceProxy$ConnectState[this.state.ordinal()])
        {
        case 1:
          if (!this.queue.isEmpty())
          {
            HitParams localHitParams2 = (HitParams)this.queue.poll();
            Log.iDebug("Sending hit to store");
            this.store.putHit(localHitParams2.getWireFormatParams(), localHitParams2.getHitTimeInMilliseconds(), localHitParams2.getPath(), localHitParams2.getCommands());
            continue;
          }
        case 2:
        case 3:
        }
      }
      finally
      {
        monitorexit;
      }
      if (!this.pendingDispatch)
        continue;
      dispatchToStore();
      continue;
      while (!this.queue.isEmpty())
      {
        HitParams localHitParams1 = (HitParams)this.queue.peek();
        Log.iDebug("Sending hit to service");
        this.client.sendHit(localHitParams1.getWireFormatParams(), localHitParams1.getHitTimeInMilliseconds(), localHitParams1.getPath(), localHitParams1.getCommands());
        this.queue.poll();
      }
      this.lastRequestTime = this.clock.currentTimeMillis();
      continue;
      Log.iDebug("Need to reconnect");
      if (this.queue.isEmpty())
        continue;
      connectToService();
      continue;
    }
  }

  private void useStore()
  {
    monitorenter;
    while (true)
    {
      try
      {
        ConnectState localConnectState1 = this.state;
        ConnectState localConnectState2 = ConnectState.CONNECTED_LOCAL;
        if (localConnectState1 == localConnectState2)
          return;
        clearAllTimers();
        Log.iDebug("falling back to local store");
        if (this.testStore != null)
        {
          this.store = this.testStore;
          this.state = ConnectState.CONNECTED_LOCAL;
          sendQueue();
          continue;
        }
      }
      finally
      {
        monitorexit;
      }
      GAServiceManager localGAServiceManager = GAServiceManager.getInstance();
      localGAServiceManager.initialize(this.ctx, this.thread);
      this.store = localGAServiceManager.getStore();
    }
  }

  public void clearHits()
  {
    Log.iDebug("clearHits called");
    this.queue.clear();
    switch (3.$SwitchMap$com$google$analytics$tracking$android$GAServiceProxy$ConnectState[this.state.ordinal()])
    {
    default:
      this.pendingClearHits = true;
      return;
    case 1:
      this.store.clearHits(0L);
      this.pendingClearHits = false;
      return;
    case 2:
    }
    this.client.clearHits();
    this.pendingClearHits = false;
  }

  public void createService()
  {
    if (this.client != null)
      return;
    this.client = new AnalyticsGmsCoreClient(this.ctx, this, this);
    connectToService();
  }

  void createService(AnalyticsClient paramAnalyticsClient)
  {
    if (this.client != null)
      return;
    this.client = paramAnalyticsClient;
    connectToService();
  }

  public void dispatch()
  {
    switch (3.$SwitchMap$com$google$analytics$tracking$android$GAServiceProxy$ConnectState[this.state.ordinal()])
    {
    default:
      this.pendingDispatch = true;
    case 2:
      return;
    case 1:
    }
    dispatchToStore();
  }

  public void onConnected()
  {
    monitorenter;
    try
    {
      this.failedConnectTimer = cancelTimer(this.failedConnectTimer);
      this.connectTries = 0;
      Log.iDebug("Connected to service");
      this.state = ConnectState.CONNECTED_SERVICE;
      sendQueue();
      this.disconnectCheckTimer = cancelTimer(this.disconnectCheckTimer);
      this.disconnectCheckTimer = new Timer("disconnect check");
      this.disconnectCheckTimer.schedule(new DisconnectCheckTask(null), this.idleTimeout);
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

  public void onConnectionFailed(int paramInt, Intent paramIntent)
  {
    monitorenter;
    try
    {
      this.state = ConnectState.PENDING_CONNECTION;
      if (this.connectTries < 2)
      {
        Log.w("Service unavailable (code=" + paramInt + "), will retry.");
        fireReconnectAttempt();
      }
      while (true)
      {
        return;
        Log.w("Service unavailable (code=" + paramInt + "), using local store.");
        useStore();
      }
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public void onDisconnected()
  {
    monitorenter;
    while (true)
    {
      try
      {
        if (this.state != ConnectState.PENDING_DISCONNECT)
          continue;
        Log.iDebug("Disconnected from service");
        clearAllTimers();
        this.state = ConnectState.DISCONNECTED;
        return;
        Log.iDebug("Unexpected disconnect.");
        this.state = ConnectState.PENDING_CONNECTION;
        if (this.connectTries < 2)
        {
          fireReconnectAttempt();
          continue;
        }
      }
      finally
      {
        monitorexit;
      }
      useStore();
    }
  }

  public void putHit(Map<String, String> paramMap, long paramLong, String paramString, List<Command> paramList)
  {
    Log.iDebug("putHit called");
    this.queue.add(new HitParams(paramMap, paramLong, paramString, paramList));
    sendQueue();
  }

  void setClock(Clock paramClock)
  {
    this.clock = paramClock;
  }

  public void setIdleTimeout(long paramLong)
  {
    this.idleTimeout = paramLong;
  }

  private static enum ConnectState
  {
    static
    {
      CONNECTED_SERVICE = new ConnectState("CONNECTED_SERVICE", 1);
      CONNECTED_LOCAL = new ConnectState("CONNECTED_LOCAL", 2);
      BLOCKED = new ConnectState("BLOCKED", 3);
      PENDING_CONNECTION = new ConnectState("PENDING_CONNECTION", 4);
      PENDING_DISCONNECT = new ConnectState("PENDING_DISCONNECT", 5);
      DISCONNECTED = new ConnectState("DISCONNECTED", 6);
      ConnectState[] arrayOfConnectState = new ConnectState[7];
      arrayOfConnectState[0] = CONNECTING;
      arrayOfConnectState[1] = CONNECTED_SERVICE;
      arrayOfConnectState[2] = CONNECTED_LOCAL;
      arrayOfConnectState[3] = BLOCKED;
      arrayOfConnectState[4] = PENDING_CONNECTION;
      arrayOfConnectState[5] = PENDING_DISCONNECT;
      arrayOfConnectState[6] = DISCONNECTED;
      $VALUES = arrayOfConnectState;
    }
  }

  private class DisconnectCheckTask extends TimerTask
  {
    private DisconnectCheckTask()
    {
    }

    public void run()
    {
      if ((GAServiceProxy.this.state == GAServiceProxy.ConnectState.CONNECTED_SERVICE) && (GAServiceProxy.this.queue.isEmpty()) && (GAServiceProxy.this.lastRequestTime + GAServiceProxy.this.idleTimeout < GAServiceProxy.this.clock.currentTimeMillis()))
      {
        Log.iDebug("Disconnecting due to inactivity");
        GAServiceProxy.this.disconnectFromService();
        return;
      }
      GAServiceProxy.this.disconnectCheckTimer.schedule(new DisconnectCheckTask(GAServiceProxy.this), GAServiceProxy.this.idleTimeout);
    }
  }

  private class FailedConnectTask extends TimerTask
  {
    private FailedConnectTask()
    {
    }

    public void run()
    {
      if (GAServiceProxy.this.state == GAServiceProxy.ConnectState.CONNECTING)
        GAServiceProxy.this.useStore();
    }
  }

  private static class HitParams
  {
    private final List<Command> commands;
    private final long hitTimeInMilliseconds;
    private final String path;
    private final Map<String, String> wireFormatParams;

    public HitParams(Map<String, String> paramMap, long paramLong, String paramString, List<Command> paramList)
    {
      this.wireFormatParams = paramMap;
      this.hitTimeInMilliseconds = paramLong;
      this.path = paramString;
      this.commands = paramList;
    }

    public List<Command> getCommands()
    {
      return this.commands;
    }

    public long getHitTimeInMilliseconds()
    {
      return this.hitTimeInMilliseconds;
    }

    public String getPath()
    {
      return this.path;
    }

    public Map<String, String> getWireFormatParams()
    {
      return this.wireFormatParams;
    }
  }

  private class ReconnectTask extends TimerTask
  {
    private ReconnectTask()
    {
    }

    public void run()
    {
      GAServiceProxy.this.connectToService();
    }
  }
}

/* Location:           C:\Users\Yash\Desktop\apk to src\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.google.analytics.tracking.android.GAServiceProxy
 * JD-Core Version:    0.6.0
 */
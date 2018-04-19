package com.google.analytics.tracking.android;

import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

abstract interface AnalyticsThread
{
  public abstract void dispatch();

  public abstract LinkedBlockingQueue<Runnable> getQueue();

  public abstract Thread getThread();

  public abstract void requestAppOptOut(GoogleAnalytics.AppOptOutCallback paramAppOptOutCallback);

  public abstract void requestClientId(ClientIdCallback paramClientIdCallback);

  public abstract void sendHit(Map<String, String> paramMap);

  public abstract void setAppOptOut(boolean paramBoolean);

  public static abstract interface ClientIdCallback
  {
    public abstract void reportClientId(String paramString);
  }
}

/* Location:           C:\Users\Yash\Desktop\apk to src\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.google.analytics.tracking.android.AnalyticsThread
 * JD-Core Version:    0.6.0
 */
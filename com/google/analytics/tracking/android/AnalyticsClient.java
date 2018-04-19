package com.google.analytics.tracking.android;

import com.google.android.gms.analytics.internal.Command;
import java.util.List;
import java.util.Map;

abstract interface AnalyticsClient
{
  public abstract void clearHits();

  public abstract void connect();

  public abstract void disconnect();

  public abstract void sendHit(Map<String, String> paramMap, long paramLong, String paramString, List<Command> paramList);
}

/* Location:           C:\Users\Yash\Desktop\apk to src\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.google.analytics.tracking.android.AnalyticsClient
 * JD-Core Version:    0.6.0
 */
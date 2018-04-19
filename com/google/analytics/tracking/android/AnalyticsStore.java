package com.google.analytics.tracking.android;

import com.google.android.gms.analytics.internal.Command;
import java.util.Collection;
import java.util.Map;

abstract interface AnalyticsStore
{
  public abstract void clearHits(long paramLong);

  public abstract void close();

  public abstract void dispatch();

  public abstract void putHit(Map<String, String> paramMap, long paramLong, String paramString, Collection<Command> paramCollection);

  public abstract void setDispatch(boolean paramBoolean);
}

/* Location:           C:\Users\Yash\Desktop\apk to src\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.google.analytics.tracking.android.AnalyticsStore
 * JD-Core Version:    0.6.0
 */
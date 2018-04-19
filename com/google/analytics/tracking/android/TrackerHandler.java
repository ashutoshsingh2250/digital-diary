package com.google.analytics.tracking.android;

import java.util.Map;

abstract interface TrackerHandler
{
  public abstract void closeTracker(Tracker paramTracker);

  public abstract void sendHit(Map<String, String> paramMap);
}

/* Location:           C:\Users\Yash\Desktop\apk to src\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.google.analytics.tracking.android.TrackerHandler
 * JD-Core Version:    0.6.0
 */
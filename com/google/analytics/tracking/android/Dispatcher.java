package com.google.analytics.tracking.android;

import java.util.List;

abstract interface Dispatcher
{
  public abstract int dispatchHits(List<Hit> paramList);

  public abstract boolean okToDispatch();
}

/* Location:           C:\Users\Yash\Desktop\apk to src\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.google.analytics.tracking.android.Dispatcher
 * JD-Core Version:    0.6.0
 */
package com.google.analytics.tracking.android;

public abstract interface ServiceManager
{
  public abstract void dispatch();

  public abstract void setDispatchPeriod(int paramInt);

  public abstract void updateConnectivityStatus(boolean paramBoolean);
}

/* Location:           C:\Users\Yash\Desktop\apk to src\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.google.analytics.tracking.android.ServiceManager
 * JD-Core Version:    0.6.0
 */
package com.google.analytics.tracking.android;

import com.google.android.gms.common.util.VisibleForTesting;

class AdHitIdGenerator
{
  private boolean mAdMobSdkInstalled;

  AdHitIdGenerator()
  {
    try
    {
      if (Class.forName("com.google.ads.AdRequest") != null);
      for (boolean bool = true; ; bool = false)
      {
        this.mAdMobSdkInstalled = bool;
        return;
      }
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      this.mAdMobSdkInstalled = false;
    }
  }

  @VisibleForTesting
  AdHitIdGenerator(boolean paramBoolean)
  {
    this.mAdMobSdkInstalled = paramBoolean;
  }

  int getAdHitId()
  {
    if (!this.mAdMobSdkInstalled)
      return 0;
    return AdMobInfo.getInstance().generateAdHitId();
  }
}

/* Location:           C:\Users\Yash\Desktop\apk to src\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.google.analytics.tracking.android.AdHitIdGenerator
 * JD-Core Version:    0.6.0
 */
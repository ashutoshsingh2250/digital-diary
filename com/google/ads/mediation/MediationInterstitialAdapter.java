package com.google.ads.mediation;

import android.app.Activity;

public abstract interface MediationInterstitialAdapter<ADDITIONAL_PARAMETERS extends NetworkExtras, SERVER_PARAMETERS extends MediationServerParameters> extends MediationAdapter<ADDITIONAL_PARAMETERS, SERVER_PARAMETERS>
{
  public abstract void requestInterstitialAd(MediationInterstitialListener paramMediationInterstitialListener, Activity paramActivity, SERVER_PARAMETERS paramSERVER_PARAMETERS, MediationAdRequest paramMediationAdRequest, ADDITIONAL_PARAMETERS paramADDITIONAL_PARAMETERS);

  public abstract void showInterstitial();
}

/* Location:           C:\Users\Yash\Desktop\apk to src\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.google.ads.mediation.MediationInterstitialAdapter
 * JD-Core Version:    0.6.0
 */
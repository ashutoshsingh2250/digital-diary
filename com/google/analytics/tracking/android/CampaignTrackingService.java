package com.google.analytics.tracking.android;

import android.app.IntentService;
import android.content.Intent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public final class CampaignTrackingService extends IntentService
{
  public CampaignTrackingService()
  {
    super("CampaignIntentService");
  }

  public CampaignTrackingService(String paramString)
  {
    super(paramString);
  }

  protected void onHandleIntent(Intent paramIntent)
  {
    String str = paramIntent.getStringExtra("referrer");
    try
    {
      FileOutputStream localFileOutputStream = openFileOutput("gaInstallData", 0);
      localFileOutputStream.write(str.getBytes());
      localFileOutputStream.close();
      return;
    }
    catch (IOException localIOException)
    {
      Log.e("Error storing install campaign.");
    }
  }
}

/* Location:           C:\Users\Yash\Desktop\apk to src\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.google.analytics.tracking.android.CampaignTrackingService
 * JD-Core Version:    0.6.0
 */
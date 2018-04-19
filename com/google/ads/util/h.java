package com.google.ads.util;

import android.annotation.TargetApi;
import android.webkit.WebSettings;
import com.google.ads.n;

@TargetApi(17)
public final class h
{
  public static void a(WebSettings paramWebSettings, n paramn)
  {
    g.a(paramWebSettings, paramn);
    paramWebSettings.setMediaPlaybackRequiresUserGesture(false);
  }
}

/* Location:           C:\Users\Yash\Desktop\apk to src\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.google.ads.util.h
 * JD-Core Version:    0.6.0
 */
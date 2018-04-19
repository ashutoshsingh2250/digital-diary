package com.google.analytics.tracking.android;

import com.google.android.gms.common.util.VisibleForTesting;

public class Log
{

  @VisibleForTesting
  static final String LOG_TAG = "GAV2";
  private static boolean sDebug;

  public static int d(String paramString)
  {
    return android.util.Log.d("GAV2", formatMessage(paramString));
  }

  public static int dDebug(String paramString)
  {
    if (sDebug)
      return d(paramString);
    return 0;
  }

  public static int e(String paramString)
  {
    return android.util.Log.e("GAV2", formatMessage(paramString));
  }

  public static int eDebug(String paramString)
  {
    if (sDebug)
      return e(paramString);
    return 0;
  }

  private static String formatMessage(String paramString)
  {
    return Thread.currentThread().toString() + ": " + paramString;
  }

  public static int i(String paramString)
  {
    return android.util.Log.i("GAV2", formatMessage(paramString));
  }

  public static int iDebug(String paramString)
  {
    if (sDebug)
      return i(paramString);
    return 0;
  }

  public static boolean isDebugEnabled()
  {
    return sDebug;
  }

  public static void setDebug(boolean paramBoolean)
  {
    sDebug = paramBoolean;
  }

  public static int v(String paramString)
  {
    return android.util.Log.v("GAV2", formatMessage(paramString));
  }

  public static int vDebug(String paramString)
  {
    if (sDebug)
      return v(paramString);
    return 0;
  }

  public static int w(String paramString)
  {
    return android.util.Log.w("GAV2", formatMessage(paramString));
  }

  public static int wDebug(String paramString)
  {
    if (sDebug)
      return w(paramString);
    return 0;
  }
}

/* Location:           C:\Users\Yash\Desktop\apk to src\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.google.analytics.tracking.android.Log
 * JD-Core Version:    0.6.0
 */
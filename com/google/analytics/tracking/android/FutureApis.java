package com.google.analytics.tracking.android;

import android.os.Build.VERSION;
import java.io.File;

class FutureApis
{
  static boolean setOwnerOnlyReadWrite(String paramString)
  {
    if (version() < 9)
      return false;
    File localFile = new File(paramString);
    localFile.setReadable(false, false);
    localFile.setWritable(false, false);
    localFile.setReadable(true, true);
    localFile.setWritable(true, true);
    return true;
  }

  public static int version()
  {
    try
    {
      int i = Integer.parseInt(Build.VERSION.SDK);
      return i;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      Log.e("Invalid version number: " + Build.VERSION.SDK);
    }
    return 0;
  }
}

/* Location:           C:\Users\Yash\Desktop\apk to src\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.google.analytics.tracking.android.FutureApis
 * JD-Core Version:    0.6.0
 */
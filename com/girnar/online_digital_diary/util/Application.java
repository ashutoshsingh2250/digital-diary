package com.girnar.online_digital_diary.util;

import android.app.AlertDialog.Builder;
import android.content.Context;

public class Application extends android.app.Application
{
  private static boolean activityVisible;

  public static void activityPaused()
  {
    activityVisible = false;
  }

  public static void activityResumed()
  {
    activityVisible = true;
  }

  public static boolean isActivityVisible()
  {
    return activityVisible;
  }

  public static void showOKAleart(Context paramContext, String paramString1, String paramString2)
  {
    new AlertDialog.Builder(paramContext).setTitle(paramString1).setMessage(paramString2).setNegativeButton("OK", null).show();
  }
}

/* Location:           C:\Users\Yash\Desktop\apk to src\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.girnar.online_digital_diary.util.Application
 * JD-Core Version:    0.6.0
 */
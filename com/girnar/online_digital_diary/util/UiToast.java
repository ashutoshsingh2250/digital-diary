package com.girnar.online_digital_diary.util;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

public class UiToast extends Toast
{
  Context context;

  public UiToast(Context paramContext)
  {
    super(paramContext);
    this.context = paramContext;
  }

  public void showToast(String paramString, int paramInt)
  {
    ((Activity)this.context).runOnUiThread(new Runnable(paramString, paramInt)
    {
      public void run()
      {
        Toast.makeText(UiToast.this.context, this.val$string, this.val$toastLength).show();
      }
    });
  }
}

/* Location:           C:\Users\Yash\Desktop\apk to src\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.girnar.online_digital_diary.util.UiToast
 * JD-Core Version:    0.6.0
 */
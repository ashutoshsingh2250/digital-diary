package com.google.analytics.tracking.android;

import android.text.TextUtils;
import java.util.List;

class NoopDispatcher
  implements Dispatcher
{
  public int dispatchHits(List<Hit> paramList)
  {
    if (paramList == null)
    {
      i = 0;
      return i;
    }
    Log.iDebug("Hits not actually being sent as dispatch is false...");
    int i = Math.min(paramList.size(), 40);
    int j = 0;
    label29: String str1;
    label65: String str2;
    if (j < i)
      if (Log.isDebugEnabled())
      {
        if (!TextUtils.isEmpty(((Hit)paramList.get(j)).getHitParams()))
          break label107;
        str1 = "";
        if (!TextUtils.isEmpty(str1))
          break label129;
        str2 = "Hit couldn't be read, wouldn't be sent:";
      }
    while (true)
    {
      Log.iDebug(str2 + str1);
      j++;
      break label29;
      break;
      label107: str1 = HitBuilder.postProcessHit((Hit)paramList.get(j), System.currentTimeMillis());
      break label65;
      label129: if (str1.length() <= 2036)
      {
        str2 = "GET would be sent:";
        continue;
      }
      if (str1.length() > 8192)
      {
        str2 = "Would be too big:";
        continue;
      }
      str2 = "POST would be sent:";
    }
  }

  public boolean okToDispatch()
  {
    return true;
  }
}

/* Location:           C:\Users\Yash\Desktop\apk to src\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.google.analytics.tracking.android.NoopDispatcher
 * JD-Core Version:    0.6.0
 */
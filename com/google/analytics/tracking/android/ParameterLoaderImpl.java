package com.google.analytics.tracking.android;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;

class ParameterLoaderImpl
  implements ParameterLoader
{
  private final Context mContext;

  public ParameterLoaderImpl(Context paramContext)
  {
    if (paramContext == null)
      throw new NullPointerException("Context cannot be null");
    this.mContext = paramContext.getApplicationContext();
  }

  private int getResourceIdForType(String paramString1, String paramString2)
  {
    if (this.mContext == null)
      return 0;
    return this.mContext.getResources().getIdentifier(paramString1, paramString2, this.mContext.getPackageName());
  }

  public boolean getBoolean(String paramString)
  {
    int i = getResourceIdForType(paramString, "bool");
    if (i == 0)
      return false;
    return "true".equalsIgnoreCase(this.mContext.getString(i));
  }

  public Double getDoubleFromString(String paramString)
  {
    String str = getString(paramString);
    if (TextUtils.isEmpty(str))
      return null;
    try
    {
      Double localDouble = Double.valueOf(Double.parseDouble(str));
      return localDouble;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      Log.w("NumberFormatException parsing " + str);
    }
    return null;
  }

  public int getInt(String paramString, int paramInt)
  {
    int i = getResourceIdForType(paramString, "integer");
    if (i == 0)
      return paramInt;
    try
    {
      int j = Integer.parseInt(this.mContext.getString(i));
      return j;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      Log.w("NumberFormatException parsing " + this.mContext.getString(i));
    }
    return paramInt;
  }

  public String getString(String paramString)
  {
    int i = getResourceIdForType(paramString, "string");
    if (i == 0)
      return null;
    return this.mContext.getString(i);
  }

  public boolean isBooleanKeyPresent(String paramString)
  {
    return getResourceIdForType(paramString, "bool") != 0;
  }
}

/* Location:           C:\Users\Yash\Desktop\apk to src\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.google.analytics.tracking.android.ParameterLoaderImpl
 * JD-Core Version:    0.6.0
 */
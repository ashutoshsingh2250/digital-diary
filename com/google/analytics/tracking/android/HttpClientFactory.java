package com.google.analytics.tracking.android;

import org.apache.http.client.HttpClient;

abstract interface HttpClientFactory
{
  public abstract HttpClient newInstance();
}

/* Location:           C:\Users\Yash\Desktop\apk to src\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.google.analytics.tracking.android.HttpClientFactory
 * JD-Core Version:    0.6.0
 */
package com.google.analytics.tracking.android;

import android.content.Context;
import java.util.ArrayList;

public class ExceptionReporter
  implements Thread.UncaughtExceptionHandler
{
  static final String DEFAULT_DESCRIPTION = "UncaughtException";
  private ExceptionParser mExceptionParser;
  private final Thread.UncaughtExceptionHandler mOriginalHandler;
  private final ServiceManager mServiceManager;
  private final Tracker mTracker;

  public ExceptionReporter(Tracker paramTracker, ServiceManager paramServiceManager, Thread.UncaughtExceptionHandler paramUncaughtExceptionHandler, Context paramContext)
  {
    if (paramTracker == null)
      throw new NullPointerException("tracker cannot be null");
    if (paramServiceManager == null)
      throw new NullPointerException("serviceManager cannot be null");
    this.mOriginalHandler = paramUncaughtExceptionHandler;
    this.mTracker = paramTracker;
    this.mServiceManager = paramServiceManager;
    this.mExceptionParser = new StandardExceptionParser(paramContext, new ArrayList());
    StringBuilder localStringBuilder = new StringBuilder().append("ExceptionReporter created, original handler is ");
    if (paramUncaughtExceptionHandler == null);
    for (String str = "null"; ; str = paramUncaughtExceptionHandler.getClass().getName())
    {
      Log.iDebug(str);
      return;
    }
  }

  public ExceptionParser getExceptionParser()
  {
    return this.mExceptionParser;
  }

  public void setExceptionParser(ExceptionParser paramExceptionParser)
  {
    this.mExceptionParser = paramExceptionParser;
  }

  public void uncaughtException(Thread paramThread, Throwable paramThrowable)
  {
    String str1 = "UncaughtException";
    if (this.mExceptionParser != null)
      if (paramThread == null)
        break label99;
    label99: for (String str2 = paramThread.getName(); ; str2 = null)
    {
      str1 = this.mExceptionParser.getDescription(str2, paramThrowable);
      Log.iDebug("Tracking Exception: " + str1);
      this.mTracker.sendException(str1, true);
      this.mServiceManager.dispatch();
      if (this.mOriginalHandler != null)
      {
        Log.iDebug("Passing exception to original handler.");
        this.mOriginalHandler.uncaughtException(paramThread, paramThrowable);
      }
      return;
    }
  }
}

/* Location:           C:\Users\Yash\Desktop\apk to src\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.google.analytics.tracking.android.ExceptionReporter
 * JD-Core Version:    0.6.0
 */
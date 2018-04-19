package com.girnar.online_digital_diary.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.TextView;
import com.girnar.online_digital_diary.interfaces.ImportantMethod;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Tracker;

public class SplashScreen extends Activity
  implements View.OnFocusChangeListener, ImportantMethod
{
  private final String TAG = "OnlineDigitalDiary";
  private int _splashTime = 3000;
  Handler handler = new Handler()
  {
    public void handleMessage(Message paramMessage)
    {
      SplashScreen.this.startActivity(new Intent(SplashScreen.this, LoginPageActivity.class).putExtra("auth", "simpleLogin"));
      SplashScreen.this.overridePendingTransition(2130968576, 2130968577);
      SplashScreen.this.finish();
    }
  };
  private TextView textView;
  private Tracker tracker;

  public void addListener()
  {
  }

  public void getIds()
  {
  }

  public TextView getTextView()
  {
    return this.textView;
  }

  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
    if (paramConfiguration.orientation == 2)
      setRequestedOrientation(1);
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Log.d("OnlineDigitalDiary", "on create called...");
    setContentView(2130903060);
    EasyTracker.getInstance().setContext(getApplicationContext());
    this.tracker = EasyTracker.getTracker();
    this.tracker.trackView("Splash Screen_Digital_Diary");
    getIds();
    addListener();
    new Thread()
    {
      // ERROR //
      public void run()
      {
        // Byte code:
        //   0: invokestatic 27	java/lang/System:currentTimeMillis	()J
        //   3: lstore 5
        //   5: invokestatic 27	java/lang/System:currentTimeMillis	()J
        //   8: lload 5
        //   10: lsub
        //   11: lstore 7
        //   13: lload 7
        //   15: aload_0
        //   16: getfield 15	com/girnar/online_digital_diary/ui/SplashScreen$2:this$0	Lcom/girnar/online_digital_diary/ui/SplashScreen;
        //   19: invokestatic 31	com/girnar/online_digital_diary/ui/SplashScreen:access$0	(Lcom/girnar/online_digital_diary/ui/SplashScreen;)I
        //   22: i2l
        //   23: lcmp
        //   24: ifge +17 -> 41
        //   27: aload_0
        //   28: getfield 15	com/girnar/online_digital_diary/ui/SplashScreen$2:this$0	Lcom/girnar/online_digital_diary/ui/SplashScreen;
        //   31: invokestatic 31	com/girnar/online_digital_diary/ui/SplashScreen:access$0	(Lcom/girnar/online_digital_diary/ui/SplashScreen;)I
        //   34: i2l
        //   35: lload 7
        //   37: lsub
        //   38: invokestatic 35	com/girnar/online_digital_diary/ui/SplashScreen$2:sleep	(J)V
        //   41: aload_0
        //   42: getfield 15	com/girnar/online_digital_diary/ui/SplashScreen$2:this$0	Lcom/girnar/online_digital_diary/ui/SplashScreen;
        //   45: getfield 39	com/girnar/online_digital_diary/ui/SplashScreen:handler	Landroid/os/Handler;
        //   48: new 41	android/os/Message
        //   51: dup
        //   52: invokespecial 42	android/os/Message:<init>	()V
        //   55: invokevirtual 48	android/os/Handler:sendMessage	(Landroid/os/Message;)Z
        //   58: pop
        //   59: return
        //   60: astore_3
        //   61: aload_0
        //   62: getfield 15	com/girnar/online_digital_diary/ui/SplashScreen$2:this$0	Lcom/girnar/online_digital_diary/ui/SplashScreen;
        //   65: getfield 39	com/girnar/online_digital_diary/ui/SplashScreen:handler	Landroid/os/Handler;
        //   68: new 41	android/os/Message
        //   71: dup
        //   72: invokespecial 42	android/os/Message:<init>	()V
        //   75: invokevirtual 48	android/os/Handler:sendMessage	(Landroid/os/Message;)Z
        //   78: pop
        //   79: return
        //   80: astore_1
        //   81: aload_0
        //   82: getfield 15	com/girnar/online_digital_diary/ui/SplashScreen$2:this$0	Lcom/girnar/online_digital_diary/ui/SplashScreen;
        //   85: getfield 39	com/girnar/online_digital_diary/ui/SplashScreen:handler	Landroid/os/Handler;
        //   88: new 41	android/os/Message
        //   91: dup
        //   92: invokespecial 42	android/os/Message:<init>	()V
        //   95: invokevirtual 48	android/os/Handler:sendMessage	(Landroid/os/Message;)Z
        //   98: pop
        //   99: aload_1
        //   100: athrow
        //
        // Exception table:
        //   from	to	target	type
        //   0	41	60	java/lang/InterruptedException
        //   0	41	80	finally
      }
    }
    .start();
  }

  public void onFocusChange(View paramView, boolean paramBoolean)
  {
  }

  public void setTextView(TextView paramTextView)
  {
    this.textView = paramTextView;
  }

  public boolean validate()
  {
    return false;
  }
}

/* Location:           C:\Users\Yash\Desktop\apk to src\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.girnar.online_digital_diary.ui.SplashScreen
 * JD-Core Version:    0.6.0
 */
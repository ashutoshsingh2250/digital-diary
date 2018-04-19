package com.girnar.online_digital_diary.recievers;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import com.girnar.online_digital_diary.ui.FrndListActivity;
import com.girnar.online_digital_diary.ui.LoginPageActivity;
import com.girnar.online_digital_diary.ui.SplashScreen;

public class NotificationReceiver extends BroadcastReceiver
{
  private static NotificationManager nm;
  PendingIntent contentIntent;
  MediaPlayer mediaPlayer;

  public static void cancelAllAlarm(Context paramContext, long paramLong, String paramString, int paramInt)
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("NotificationDesc", paramString);
    localBundle.putLong("NotificationTimeInMills", paramLong);
    localBundle.putInt("id", paramInt);
    ((AlarmManager)paramContext.getSystemService("alarm"));
    Intent localIntent = new Intent(paramContext, NotificationReceiver.class);
    localIntent.putExtra("NotificationBundle", localBundle);
    localIntent.putExtra("id", paramInt);
    localIntent.setAction("reminder");
    PendingIntent localPendingIntent = PendingIntent.getBroadcast(paramContext, paramInt, localIntent, 134217728);
    ((AlarmManager)paramContext.getSystemService("alarm")).cancel(localPendingIntent);
    if (nm == null)
      nm = (NotificationManager)paramContext.getSystemService("notification");
    nm.cancel(paramInt);
  }

  public static void cancelBirthdayAlarm(Context paramContext, long paramLong, String paramString, int paramInt)
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("NotificationDesc", paramString);
    localBundle.putLong("NotificationTimeInMills", paramLong);
    localBundle.putInt("id", paramInt);
    ((AlarmManager)paramContext.getSystemService("alarm"));
    Intent localIntent = new Intent(paramContext, NotificationReceiver.class);
    localIntent.putExtra("NotificationBundle", localBundle);
    localIntent.putExtra("id", paramInt);
    localIntent.setAction("birthday");
    PendingIntent localPendingIntent = PendingIntent.getBroadcast(paramContext, paramInt, localIntent, 134217728);
    ((AlarmManager)paramContext.getSystemService("alarm")).cancel(localPendingIntent);
    if (nm == null)
      nm = (NotificationManager)paramContext.getSystemService("notification");
    nm.cancel(paramInt);
  }

  public static void setAutoRepetNotification(Context paramContext, long paramLong1, long paramLong2)
  {
    AlarmManager localAlarmManager = (AlarmManager)paramContext.getSystemService("alarm");
    Intent localIntent = new Intent(paramContext, NotificationReceiver.class);
    localAlarmManager.setRepeating(0, paramLong1, paramLong2, PendingIntent.getBroadcast(paramContext, (int)paramLong1, localIntent, 0));
  }

  public static void setNotificationForBirthDay(Context paramContext, long paramLong, String paramString, int paramInt)
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("NotificationDesc", paramString);
    localBundle.putLong("NotificationTimeInMills", paramLong);
    localBundle.putInt("id", paramInt);
    AlarmManager localAlarmManager = (AlarmManager)paramContext.getSystemService("alarm");
    Intent localIntent = new Intent(paramContext, NotificationReceiver.class);
    localIntent.putExtra("NotificationBundle", localBundle);
    localIntent.putExtra("id", paramInt);
    localIntent.setAction("birthday");
    localAlarmManager.set(0, paramLong, PendingIntent.getBroadcast(paramContext, paramInt, localIntent, 134217728));
  }

  public static void setNotificationOnDateTime(Context paramContext, long paramLong)
  {
    AlarmManager localAlarmManager = (AlarmManager)paramContext.getSystemService("alarm");
    Intent localIntent = new Intent(paramContext, NotificationReceiver.class);
    localIntent.putExtra("id", (int)paramLong);
    localIntent.setData(Uri.parse(paramLong));
    localIntent.addCategory(paramLong);
    localAlarmManager.set(0, paramLong, PendingIntent.getBroadcast(paramContext, (int)paramLong, localIntent, 0));
  }

  public static void setNotificationOnDateTime(Context paramContext, long paramLong, String paramString, int paramInt)
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("NotificationDesc", paramString);
    localBundle.putLong("NotificationTimeInMills", paramLong);
    localBundle.putInt("id", paramInt);
    AlarmManager localAlarmManager = (AlarmManager)paramContext.getSystemService("alarm");
    Intent localIntent = new Intent(paramContext, NotificationReceiver.class);
    localIntent.putExtra("NotificationBundle", localBundle);
    localIntent.putExtra("id", paramInt);
    localIntent.setAction("reminder");
    localAlarmManager.set(0, paramLong, PendingIntent.getBroadcast(paramContext, paramInt, localIntent, 134217728));
  }

  public void onReceive(Context paramContext, Intent paramIntent)
  {
    Log.w("TAG", "Notification fired...");
    nm = (NotificationManager)paramContext.getSystemService("notification");
    ((NotificationManager)paramContext.getSystemService("notification"));
    Bundle localBundle = paramIntent.getExtras().getBundle("NotificationBundle");
    int i = paramIntent.getExtras().getInt("id");
    Log.w("ID", i);
    Notification localNotification;
    if (localBundle == null)
    {
      this.contentIntent = PendingIntent.getActivity(paramContext, 0, new Intent(paramContext, SplashScreen.class), 0);
      localNotification = new Notification(2130837543, "Sunscreen Reapplying Reminder", System.currentTimeMillis());
      localNotification.setLatestEventInfo(paramContext, "Reminder", "Sunscreen Reapplying Reminder", this.contentIntent);
      localNotification.flags = (0x10 | localNotification.flags);
      localNotification.vibrate = new long[] { 500L, 200L, 200L, 500L };
      localNotification.defaults = (0x1 | localNotification.defaults);
      nm.notify(i, localNotification);
      return;
    }
    if (paramIntent.getAction() == "reminder")
    {
      Log.w("Bundle", localBundle);
      this.contentIntent = PendingIntent.getActivity(paramContext, 0, new Intent(paramContext, LoginPageActivity.class).putExtra("NotificationBundle", localBundle).putExtra("auth", "reminder"), 134217728);
    }
    while (true)
    {
      localNotification = new Notification(2130837543, localBundle.getString("NotificationDesc"), System.currentTimeMillis());
      localNotification.setLatestEventInfo(paramContext, "Reminder", localBundle.getString("NotificationDesc"), this.contentIntent);
      localNotification.flags = (0x10 | localNotification.flags);
      localNotification.vibrate = new long[] { 500L, 200L, 200L, 500L };
      localNotification.defaults = (0x1 | localNotification.defaults);
      break;
      if (paramIntent.getAction() != "birthday")
        continue;
      Log.w("Bundle", localBundle);
      this.contentIntent = PendingIntent.getActivity(paramContext, 0, new Intent(paramContext, FrndListActivity.class).putExtra("NotificationBundle", localBundle).putExtra("auth", "friend"), 134217728);
    }
  }
}

/* Location:           C:\Users\Yash\Desktop\apk to src\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.girnar.online_digital_diary.recievers.NotificationReceiver
 * JD-Core Version:    0.6.0
 */
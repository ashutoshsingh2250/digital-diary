package com.girnar.online_digital_diary.recievers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.text.format.DateFormat;
import com.girnar.online_digital_diary.database.DbHelper;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ReminderCheckReciever extends BroadcastReceiver
{
  DbHelper db;
  private int hour;
  private int id;
  private ArrayList<String> ids = new ArrayList();
  private int mDay;
  private int mMonth;
  private int mYear;
  private int minute;

  public int getHour()
  {
    return this.hour;
  }

  public int getId()
  {
    return this.id;
  }

  public ArrayList<String> getIds()
  {
    return this.ids;
  }

  public int getMinute()
  {
    return this.minute;
  }

  public int getmDay()
  {
    return this.mDay;
  }

  public int getmMonth()
  {
    return this.mMonth;
  }

  public int getmYear()
  {
    return this.mYear;
  }

  public void onReceive(Context paramContext, Intent paramIntent)
  {
    this.db = new DbHelper(paramContext);
    DateFormat.format("MMMM d, yyyy ", new Date().getTime());
    this.db.open();
    int i = PreferenceManager.getDefaultSharedPreferences(paramContext).getInt("id", 0);
    Cursor localCursor = this.db.getReminderMethodInfo(i);
    int j;
    if (localCursor != null)
    {
      localCursor.moveToFirst();
      Calendar localCalendar = Calendar.getInstance();
      setmYear(localCalendar.get(1));
      setmMonth(localCalendar.get(2));
      setmDay(localCalendar.get(5));
      j = 0;
    }
    while (true)
    {
      int k = localCursor.getCount();
      if (j >= k)
      {
        this.db.close();
        return;
      }
      SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm");
      String str = localCursor.getString(1) + " " + localCursor.getString(2);
      Date localDate1 = null;
      try
      {
        localDate1 = localSimpleDateFormat.parse(str.trim());
        Date localDate3 = localSimpleDateFormat.parse(localSimpleDateFormat.format(Calendar.getInstance().getTime()));
        localDate2 = localDate3;
        long l = localDate1.getTime() - localDate2.getTime();
        if ((l > 0L) && (l > 0L))
        {
          String[] arrayOfString1 = localCursor.getString(1).split("-");
          String[] arrayOfString2 = localCursor.getString(2).split(":");
          String[] arrayOfString3 = arrayOfString2[1].split(" ");
          int m = Integer.parseInt(arrayOfString1[0]);
          int n = Integer.parseInt(arrayOfString1[1]);
          int i1 = Integer.parseInt(arrayOfString1[2].trim());
          int i2 = Integer.parseInt(arrayOfString2[0]);
          int i3 = Integer.parseInt(arrayOfString3[0]);
          if (arrayOfString3[1].equals("PM"))
            i2 += 12;
          NotificationReceiver.setNotificationOnDateTime(paramContext, timeInMills(i1, n - 1, m, i2, i3), "message", i);
        }
        localCursor.moveToNext();
        j++;
      }
      catch (ParseException localParseException)
      {
        while (true)
        {
          localParseException.printStackTrace();
          Date localDate2 = null;
        }
      }
    }
  }

  public void setHour(int paramInt)
  {
    this.hour = paramInt;
  }

  public void setId(int paramInt)
  {
    this.id = paramInt;
  }

  public void setIds(ArrayList<String> paramArrayList)
  {
    this.ids = paramArrayList;
  }

  public void setMinute(int paramInt)
  {
    this.minute = paramInt;
  }

  public void setmDay(int paramInt)
  {
    this.mDay = paramInt;
  }

  public void setmMonth(int paramInt)
  {
    this.mMonth = paramInt;
  }

  public void setmYear(int paramInt)
  {
    this.mYear = paramInt;
  }

  public long timeInMills(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    Date localDate = new Date();
    Calendar localCalendar1 = Calendar.getInstance();
    Calendar localCalendar2 = Calendar.getInstance();
    localCalendar2.setTime(localDate);
    localCalendar1.set(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, 0);
    if (localCalendar1.before(localCalendar2))
      localCalendar1.add(5, 1);
    return localCalendar1.getTimeInMillis();
  }
}

/* Location:           C:\Users\Yash\Desktop\apk to src\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.girnar.online_digital_diary.recievers.ReminderCheckReciever
 * JD-Core Version:    0.6.0
 */
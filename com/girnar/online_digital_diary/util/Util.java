package com.girnar.online_digital_diary.util;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.text.format.DateFormat;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.girnar.online_digital_diary.database.DbHelper;
import com.girnar.online_digital_diary.recievers.NotificationReceiver;
import com.girnar.online_digital_diary.ui.HomePageActivity;
import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest;
import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Util
{
  public static AdRequest adRequest;
  public static AdView adView;
  static DbHelper db;
  private static int hour;
  private static int mDay;
  private static int mMonth;
  private static int mYear;
  private static int minute;
  private int id;
  private ArrayList<String> ids = new ArrayList();

  public static void addGoogleAds(Activity paramActivity)
  {
    adView = new AdView(paramActivity, AdSize.SMART_BANNER, paramActivity.getResources().getString(2131034137));
    ((LinearLayout)paramActivity.findViewById(2131493046)).addView(adView);
    adRequest = new AdRequest();
    LinearLayout localLinearLayout = (LinearLayout)paramActivity.findViewById(2131493047);
    localLinearLayout.setVisibility(0);
    adView.setAdListener(new AdListener(localLinearLayout)
    {
      public void onDismissScreen(Ad paramAd)
      {
      }

      public void onFailedToReceiveAd(Ad paramAd, AdRequest.ErrorCode paramErrorCode)
      {
      }

      public void onLeaveApplication(Ad paramAd)
      {
      }

      public void onPresentScreen(Ad paramAd)
      {
      }

      public void onReceiveAd(Ad paramAd)
      {
        Util.this.setVisibility(8);
      }
    });
    adView.loadAd(adRequest);
  }

  public static void checkNotification(Activity paramActivity)
  {
    DbHelper localDbHelper = new DbHelper(paramActivity);
    db = localDbHelper;
    DateFormat.format("MMMM d, yyyy ", new Date().getTime());
    db.open();
    int i = PreferenceManager.getDefaultSharedPreferences(paramActivity).getInt("id", 0);
    Cursor localCursor = db.getReminderMethodInfo(i);
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
        db.close();
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
          NotificationReceiver.setNotificationOnDateTime(paramActivity, timeInMills(i1, n - 1, m, i2, i3), "message", i);
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

  public static void hideSoftKeyboard(Activity paramActivity)
  {
    InputMethodManager localInputMethodManager = (InputMethodManager)paramActivity.getSystemService("input_method");
    try
    {
      localInputMethodManager.hideSoftInputFromWindow(paramActivity.getCurrentFocus().getWindowToken(), 0);
      return;
    }
    catch (Exception localException)
    {
    }
  }

  public static void homeAnimation(Activity paramActivity)
  {
    paramActivity.startActivity(new Intent(paramActivity, HomePageActivity.class).setFlags(67108864));
    paramActivity.overridePendingTransition(2130968578, 2130968579);
  }

  public static void setmDay(int paramInt)
  {
    mDay = paramInt;
  }

  public static void setmMonth(int paramInt)
  {
    mMonth = paramInt;
  }

  public static void setmYear(int paramInt)
  {
    mYear = paramInt;
  }

  public static void setupUI(View paramView, Activity paramActivity)
  {
    if (!(paramView instanceof EditText))
      paramView.setOnTouchListener(new View.OnTouchListener(paramActivity)
      {
        public boolean onTouch(View paramView, MotionEvent paramMotionEvent)
        {
          Util.hideSoftKeyboard(Util.this);
          return false;
        }
      });
    if ((paramView instanceof ViewGroup));
    for (int i = 0; ; i++)
    {
      if (i >= ((ViewGroup)paramView).getChildCount())
        return;
      setupUI(((ViewGroup)paramView).getChildAt(i), paramActivity);
    }
  }

  public static long timeInMills(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
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

  public static long timeInMills1(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    Date localDate = new Date();
    Calendar localCalendar = Calendar.getInstance();
    Calendar.getInstance().setTime(localDate);
    localCalendar.set(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, 0);
    return localCalendar.getTimeInMillis();
  }

  public int getHour()
  {
    return hour;
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
    return minute;
  }

  public int getmDay()
  {
    return mDay;
  }

  public int getmMonth()
  {
    return mMonth;
  }

  public int getmYear()
  {
    return mYear;
  }

  public void setHour(int paramInt)
  {
    hour = paramInt;
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
    minute = paramInt;
  }
}

/* Location:           C:\Users\Yash\Desktop\apk to src\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.girnar.online_digital_diary.util.Util
 * JD-Core Version:    0.6.0
 */
package com.girnar.online_digital_diary.ui;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.girnar.online_digital_diary.database.DbHelper;
import com.girnar.online_digital_diary.interfaces.ImportantMethod;
import com.girnar.online_digital_diary.recievers.NotificationReceiver;
import com.girnar.online_digital_diary.util.Application;
import com.girnar.online_digital_diary.util.UiDatePicker;
import com.girnar.online_digital_diary.util.UiTimePicker;
import com.girnar.online_digital_diary.util.Util;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Tracker;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class UpdateReminderActivity extends Activity
  implements ImportantMethod, View.OnClickListener, DatePickerDialog.OnDateSetListener
{
  static final int DATE_DIALOG_ID = 0;
  static final int TIME_DIALOG_ID = 999;
  private final String TAG = "ReminderInfoActivity";
  LinearLayout back;
  EditText date;
  LinearLayout dateLayout;
  private String date_str;
  private ArrayList<String> dates = new ArrayList();
  int day;
  private DbHelper db = new DbHelper(this);
  private String desc_str;
  private EditText description;
  private TextView header_text;
  private ImageView home;
  private int hour;
  int hour1;
  private int id;
  private ArrayList<String> ids = new ArrayList();
  private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener()
  {
    public void onDateSet(DatePicker paramDatePicker, int paramInt1, int paramInt2, int paramInt3)
    {
      UpdateReminderActivity.this.mYear = paramInt1;
      UpdateReminderActivity.this.mMonth = paramInt2;
      UpdateReminderActivity.this.mDay = paramInt3;
      UpdateReminderActivity.this.date.setText(new StringBuilder().append(UpdateReminderActivity.this.mDay).append("/").append(1 + UpdateReminderActivity.this.mMonth).append("/").append(UpdateReminderActivity.this.mYear));
    }
  };
  private int mDay;
  private int mMonth;
  private int mYear;
  MediaPlayer mediaPlayer;
  private int minute;
  int minute1;
  int month;
  private ArrayList<Cursor> remList = new ArrayList();
  OtherReminderClickListner reminderClickListner = new OtherReminderClickListner()
  {
    public void onDateClick(View paramView)
    {
      UpdateReminderActivity.this.uiDatePicker.showDialog(paramView, UpdateReminderActivity.this.date, "-");
    }

    public void onDayClick()
    {
    }

    public void onDescriptionClick()
    {
    }

    public void onSetRemenderClick()
    {
      UpdateReminderActivity.this.setOtherReminder();
    }

    public void onTimeClick(View paramView)
    {
      UpdateReminderActivity.this.uiTimePicker.showDialog(paramView, UpdateReminderActivity.this.time);
    }
  };
  private Button save;
  private ImageButton setdate;
  private ImageButton settime;
  EditText time;
  LinearLayout timeLayout;
  private TimePickerDialog.OnTimeSetListener timePicker = new TimePickerDialog.OnTimeSetListener()
  {
    public void onTimeSet(TimePicker paramTimePicker, int paramInt1, int paramInt2)
    {
      UpdateReminderActivity.this.hour = paramInt1;
      UpdateReminderActivity.this.minute = paramInt2;
      UpdateReminderActivity.this.time.setText(new StringBuilder().append(UpdateReminderActivity.access$10(UpdateReminderActivity.this.hour)).append(":").append(UpdateReminderActivity.access$10(UpdateReminderActivity.this.minute)));
    }
  };
  private String time_str;
  private ArrayList<String> times = new ArrayList();
  private EditText title;
  private String title_str;
  private Tracker tracker;
  UiDatePicker uiDatePicker;
  UiTimePicker uiTimePicker;
  int year;

  private static String pad(int paramInt)
  {
    if (paramInt >= 10)
      return String.valueOf(paramInt);
    return "0" + String.valueOf(paramInt);
  }

  private void setItemsText()
  {
    Bundle localBundle = getIntent().getBundleExtra("NotificationBundle");
    if (localBundle == null)
      return;
    this.description.setText(localBundle.getString("NotificationDesc"));
    this.uiTimePicker.setTime(localBundle.getLong("NotificationTimeInMills"), this.settime);
    this.uiDatePicker.setDate(localBundle.getLong("NotificationTimeInMills"), this.setdate, "-");
  }

  private void setOtherReminder()
  {
    boolean bool = validate();
    long l1 = 0L;
    if (bool)
      l1 = databaseHelper();
    long l2 = timeInMills(this.year, -1 + this.month, this.day, this.hour, this.minute);
    NotificationReceiver.cancelAllAlarm(getApplicationContext(), l2, this.desc_str, (int)l1);
    if (this.uiDatePicker.mYear == 0)
      if (this.uiTimePicker.mHour == 0)
        NotificationReceiver.setNotificationOnDateTime(this, l2, this.description.getText().toString(), (int)l1);
    while (true)
    {
      if (bool)
      {
        startActivity(new Intent(this, ShowReminderActivity.class).setFlags(67108864));
        overridePendingTransition(2130968578, 2130968579);
        finish();
      }
      return;
      NotificationReceiver.setNotificationOnDateTime(this, timeInMills(this.year, -1 + this.month, this.day, this.uiTimePicker.mHour, this.uiTimePicker.mMinute), this.description.getText().toString(), (int)l1);
      continue;
      if (this.uiTimePicker.mHour == 0)
      {
        NotificationReceiver.setNotificationOnDateTime(this, timeInMills(this.uiDatePicker.mYear, 1 + this.uiDatePicker.mMonth, this.uiDatePicker.mDay, this.hour, this.minute), this.description.getText().toString(), (int)l1);
        continue;
      }
      NotificationReceiver.setNotificationOnDateTime(this, this.uiTimePicker.timeInMills(this.uiDatePicker.mYear, 1 + this.uiDatePicker.mMonth, this.uiDatePicker.mDay), this.description.getText().toString(), (int)l1);
    }
  }

  public void addListener()
  {
    this.save.setOnClickListener(this.reminderClickListner);
    this.setdate.setOnClickListener(this.reminderClickListner);
    this.settime.setOnClickListener(this.reminderClickListner);
    this.back.setOnClickListener(this);
    this.home.setOnClickListener(this);
  }

  public long databaseHelper()
  {
    String str1 = this.title.getText().toString();
    String str2 = this.description.getText().toString();
    String str3 = this.date.getText().toString();
    String str4 = this.time.getText().toString();
    this.db.open();
    int i = PreferenceManager.getDefaultSharedPreferences(this).getInt("id", 0);
    long l = this.db.updateinfoReminder(i, str1, str2, str3, str4, this.id);
    this.db.close();
    return l;
  }

  public ArrayList<String> getDate()
  {
    return this.dates;
  }

  public void getIds()
  {
    this.date = ((EditText)findViewById(2131492997));
    this.time = ((EditText)findViewById(2131492999));
    this.save = ((Button)findViewById(2131493001));
    this.setdate = ((ImageButton)findViewById(2131492998));
    this.back = ((LinearLayout)findViewById(2131493049));
    this.settime = ((ImageButton)findViewById(2131493000));
    this.home = ((ImageView)findViewById(2131493054));
    this.title = ((EditText)findViewById(2131492992));
    this.description = ((EditText)findViewById(2131492994));
    this.header_text = ((TextView)findViewById(2131493052));
    this.header_text.setText("Reminder Information");
  }

  public ArrayList<Cursor> getRemList()
  {
    return this.remList;
  }

  public ArrayList<String> getTime()
  {
    return this.times;
  }

  public void onBackPressed()
  {
    startActivity(new Intent(this, ShowReminderActivity.class).setFlags(67108864));
    overridePendingTransition(2130968578, 2130968579);
    finish();
  }

  public void onClick(View paramView)
  {
    if (paramView == this.back)
    {
      startActivity(new Intent(this, ShowReminderActivity.class).setFlags(67108864));
      overridePendingTransition(2130968578, 2130968579);
      finish();
    }
    if (paramView == this.home)
    {
      Util.homeAnimation(this);
      finish();
    }
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Log.d("ReminderInfoActivity", "on create called...");
    setContentView(2130903053);
    Util.setupUI(findViewById(2131492987), this);
    EasyTracker.getInstance().setContext(getApplicationContext());
    this.tracker = EasyTracker.getTracker();
    this.tracker.trackView("Update Reminders Digital_Diary");
    getIds();
    addListener();
    setItemsText();
    Util.addGoogleAds(this);
    Calendar localCalendar = Calendar.getInstance();
    this.db.open();
    this.id = getIntent().getExtras().getInt("id");
    Cursor localCursor = this.db.getInfoReminderMethod(this.id);
    this.db.close();
    if (localCursor != null)
      localCursor.moveToFirst();
    for (int i = 0; ; i++)
    {
      if (i >= localCursor.getCount())
      {
        this.mYear = localCalendar.get(1);
        this.mMonth = localCalendar.get(2);
        this.mDay = localCalendar.get(5);
        Application.isActivityVisible();
        this.uiDatePicker = new UiDatePicker(this, false);
        this.uiTimePicker = new UiTimePicker(this, false);
        return;
      }
      this.title_str = localCursor.getString(0);
      this.desc_str = localCursor.getString(1);
      this.date_str = localCursor.getString(2);
      this.time_str = localCursor.getString(3);
      String[] arrayOfString1 = localCursor.getString(2).split("-");
      String[] arrayOfString2 = localCursor.getString(3).split(":");
      String[] arrayOfString3 = arrayOfString2[1].split(" ");
      this.day = Integer.parseInt(arrayOfString1[0]);
      this.month = Integer.parseInt(arrayOfString1[1]);
      this.year = Integer.parseInt(arrayOfString1[2].trim());
      this.hour1 = Integer.parseInt(arrayOfString2[0]);
      this.minute1 = Integer.parseInt(arrayOfString3[0]);
      if (arrayOfString3[1].equals("PM"))
        this.hour1 = (12 + this.hour1);
      this.title.setText(this.title_str);
      this.description.setText(this.desc_str);
      this.date.setText(this.date_str);
      this.time.setText(this.time_str);
      localCursor.moveToNext();
    }
  }

  protected Dialog onCreateDialog(int paramInt)
  {
    switch (paramInt)
    {
    default:
      switch (paramInt)
      {
      default:
        return null;
      case 999:
      }
    case 0:
    }
    return new DatePickerDialog(this, this.mDateSetListener, this.mYear, this.mMonth, this.mDay);
    return new TimePickerDialog(this, this.timePicker, this.hour, this.minute, false);
  }

  public void onDateSet(DatePicker paramDatePicker, int paramInt1, int paramInt2, int paramInt3)
  {
  }

  public void setDate(ArrayList<String> paramArrayList)
  {
    this.dates = paramArrayList;
  }

  public void setIds(ArrayList<String> paramArrayList)
  {
    this.ids = paramArrayList;
  }

  public void setRemList(ArrayList<Cursor> paramArrayList)
  {
    this.remList = paramArrayList;
  }

  public void setTime(ArrayList<String> paramArrayList)
  {
    this.times = paramArrayList;
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

  public boolean validate()
  {
    if (this.title.getText().toString().length() == 0)
    {
      Toast.makeText(this, "Please fill title", 0).show();
      return false;
    }
    if (this.description.getText().toString().length() == 0)
    {
      Toast.makeText(this, "Please fill description", 0).show();
      return false;
    }
    if (this.date.getText().toString().length() == 0)
    {
      Toast.makeText(this, "Please set date", 0).show();
      return false;
    }
    if (this.time.getText().toString().length() == 0)
    {
      Toast.makeText(this, "Please set time", 0).show();
      return false;
    }
    return true;
  }
}

/* Location:           C:\Users\Yash\Desktop\apk to src\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.girnar.online_digital_diary.ui.UpdateReminderActivity
 * JD-Core Version:    0.6.0
 */
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

public class ReminderInfoActivity extends Activity
  implements ImportantMethod, View.OnClickListener, DatePickerDialog.OnDateSetListener
{
  static final int DATE_DIALOG_ID = 0;
  static final int TIME_DIALOG_ID = 999;
  private final String TAG = "ReminderInfoActivity";
  LinearLayout back;
  EditText date;
  LinearLayout dateLayout;
  private ArrayList<String> dates = new ArrayList();
  private DbHelper db = new DbHelper(this);
  private EditText description;
  private TextView header_text;
  private ImageView home;
  private int hour;
  private ArrayList<String> ids = new ArrayList();
  private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener()
  {
    public void onDateSet(DatePicker paramDatePicker, int paramInt1, int paramInt2, int paramInt3)
    {
      ReminderInfoActivity.this.mYear = paramInt1;
      ReminderInfoActivity.this.mMonth = paramInt2;
      ReminderInfoActivity.this.mDay = paramInt3;
      ReminderInfoActivity.this.date.setText(new StringBuilder().append(ReminderInfoActivity.this.mDay).append("/").append(1 + ReminderInfoActivity.this.mMonth).append("/").append(ReminderInfoActivity.this.mYear));
    }
  };
  private int mDay;
  private int mMonth;
  private int mYear;
  MediaPlayer mediaPlayer;
  private int minute;
  private ArrayList<Cursor> remList = new ArrayList();
  OtherReminderClickListner reminderClickListner = new OtherReminderClickListner()
  {
    public void onDateClick(View paramView)
    {
      ReminderInfoActivity.this.uiDatePicker.showDialog(paramView, ReminderInfoActivity.this.date, "-");
    }

    public void onDayClick()
    {
    }

    public void onDescriptionClick()
    {
    }

    public void onSetRemenderClick()
    {
      ReminderInfoActivity.this.setOtherReminder();
    }

    public void onTimeClick(View paramView)
    {
      ReminderInfoActivity.this.uiTimePicker.showDialog(paramView, ReminderInfoActivity.this.time);
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
      ReminderInfoActivity.this.hour = paramInt1;
      ReminderInfoActivity.this.minute = paramInt2;
      ReminderInfoActivity.this.time.setText(new StringBuilder().append(ReminderInfoActivity.access$10(ReminderInfoActivity.this.hour)).append(":").append(ReminderInfoActivity.access$10(ReminderInfoActivity.this.minute)));
    }
  };
  private ArrayList<String> times = new ArrayList();
  private EditText title;
  private Tracker tracker;
  UiDatePicker uiDatePicker;
  UiTimePicker uiTimePicker;

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
    long l = 0L;
    if (bool)
      l = databaseHelper();
    NotificationReceiver.setNotificationOnDateTime(this, this.uiTimePicker.timeInMills(this.uiDatePicker.mYear, this.uiDatePicker.mMonth, this.uiDatePicker.mDay), this.description.getText().toString(), (int)l);
    if (bool)
    {
      startActivity(new Intent(this, ShowReminderActivity.class).setFlags(67108864));
      overridePendingTransition(2130968578, 2130968579);
      finish();
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
    long l = this.db.insertinfoReminder(str1, str2, str3, str4, i);
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
    this.tracker.trackView("Reminders Digital_Diary");
    getIds();
    addListener();
    setItemsText();
    Util.addGoogleAds(this);
    Calendar localCalendar = Calendar.getInstance();
    this.mYear = localCalendar.get(1);
    this.mMonth = localCalendar.get(2);
    this.mDay = localCalendar.get(5);
    Application.isActivityVisible();
    this.uiDatePicker = new UiDatePicker(this, false);
    this.uiTimePicker = new UiTimePicker(this, false);
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
 * Qualified Name:     com.girnar.online_digital_diary.ui.ReminderInfoActivity
 * JD-Core Version:    0.6.0
 */
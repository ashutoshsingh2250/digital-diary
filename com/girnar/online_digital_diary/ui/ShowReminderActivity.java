package com.girnar.online_digital_diary.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.girnar.online_digital_diary.adapters.ReminderAdapter;
import com.girnar.online_digital_diary.database.DbHelper;
import com.girnar.online_digital_diary.interfaces.ImportantMethod;
import com.girnar.online_digital_diary.recievers.NotificationReceiver;
import com.girnar.online_digital_diary.util.Util;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Tracker;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ShowReminderActivity extends Activity
  implements AdapterView.OnItemClickListener, ImportantMethod, View.OnClickListener
{
  public static ShowReminderActivity activity;
  private Button New;
  private final String TAG = "ShowReminderActivity";
  LinearLayout back;
  private ArrayList<String> data = new ArrayList();
  private DbHelper db = new DbHelper(this);
  private ArrayList<String> descs = new ArrayList();
  private EditText editTextSearch;
  long firetime;
  private View footerView;
  private TextView header_text;
  private ImageView home;
  private ArrayList<String> ids = new ArrayList();
  private ListView listView;
  private ArrayList<Long> millis = new ArrayList();
  private TextView no_acc;
  NotificationReceiver notificationReceiver = new NotificationReceiver();
  ImageButton search;
  private Tracker tracker;

  public void addListener()
  {
    this.New.setOnClickListener(this);
    this.back.setOnClickListener(this);
    this.home.setOnClickListener(this);
    this.editTextSearch.addTextChangedListener(new TextWatcher()
    {
      public void afterTextChanged(Editable paramEditable)
      {
      }

      public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
      {
      }

      public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
      {
        ShowReminderActivity.this.db.open();
        Cursor localCursor = ShowReminderActivity.this.db.searchInfoReminder(ShowReminderActivity.this.editTextSearch.getText().toString());
        ShowReminderActivity.this.db.close();
        ShowReminderActivity.this.ids.clear();
        ShowReminderActivity.this.data.clear();
        ShowReminderActivity.this.millis.clear();
        ShowReminderActivity.this.descs.clear();
        int i;
        if (localCursor != null)
        {
          localCursor.moveToFirst();
          i = 0;
          int j = localCursor.getCount();
          if (i < j);
        }
        else
        {
          ReminderAdapter localReminderAdapter = new ReminderAdapter(ShowReminderActivity.this, ShowReminderActivity.this.data, ShowReminderActivity.this.ids, "REMINDER", "reminder_id", ShowReminderActivity.this.millis, ShowReminderActivity.this.descs);
          ShowReminderActivity.this.listView.setAdapter(localReminderAdapter);
          localReminderAdapter.notifyDataSetChanged();
          if (localCursor.getCount() != 0)
            break label428;
          ShowReminderActivity.this.no_acc.setVisibility(0);
          ShowReminderActivity.this.listView.setVisibility(8);
        }
        label428: 
        do
        {
          return;
          ShowReminderActivity.this.ids.add(localCursor.getString(0));
          ShowReminderActivity.this.data.add(localCursor.getString(1));
          ShowReminderActivity.this.descs.add(localCursor.getString(4));
          String[] arrayOfString1 = localCursor.getString(2).split("-");
          String[] arrayOfString2 = localCursor.getString(3).split(":");
          String[] arrayOfString3 = arrayOfString2[1].split(" ");
          int k = Integer.parseInt(arrayOfString1[0]);
          int m = Integer.parseInt(arrayOfString1[1]);
          int n = Integer.parseInt(arrayOfString1[2].trim());
          int i1 = Integer.parseInt(arrayOfString2[0]);
          int i2 = Integer.parseInt(arrayOfString3[0]);
          if (arrayOfString3[1].equals("PM"))
            i1 += 12;
          long l = ShowReminderActivity.this.timeInMills(n, m - 1, k, i1, i2);
          ShowReminderActivity.this.millis.add(Long.valueOf(l));
          localCursor.moveToNext();
          i++;
          break;
        }
        while (localCursor.getCount() <= 0);
        ShowReminderActivity.this.no_acc.setVisibility(8);
        ShowReminderActivity.this.listView.setVisibility(0);
      }
    });
  }

  public void getIds()
  {
    this.New = ((Button)findViewById(2131493017));
    this.listView = ((ListView)findViewById(2131493019));
    this.back = ((LinearLayout)findViewById(2131493049));
    this.home = ((ImageView)findViewById(2131493054));
    this.no_acc = ((TextView)findViewById(2131492871));
    this.header_text = ((TextView)findViewById(2131493052));
    this.header_text.setText("Reminders List");
    this.editTextSearch = ((EditText)findViewById(2131493018));
    this.footerView = getLayoutInflater().inflate(2130903069, null);
    this.listView.addFooterView(this.footerView);
  }

  public void onBackPressed()
  {
    finish();
  }

  public void onClick(View paramView)
  {
    if (paramView == this.New)
    {
      startActivity(new Intent(this, ReminderInfoActivity.class));
      overridePendingTransition(2130968576, 2130968577);
    }
    if (paramView == this.back)
      finish();
    if (paramView == this.home)
      finish();
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Log.d("ShowReminderActivity", "on create called...");
    activity = this;
    setContentView(2130903059);
    Util.setupUI(findViewById(2131493016), this);
    EasyTracker.getInstance().setContext(getApplicationContext());
    this.tracker = EasyTracker.getTracker();
    this.tracker.trackView("List of Reminders Digital_Diary");
    getIds();
    addListener();
    Util.addGoogleAds(this);
  }

  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
  }

  protected void onStart()
  {
    super.onStart();
    this.ids.clear();
    this.data.clear();
    this.descs.clear();
    this.millis.clear();
    this.db.open();
    int i = PreferenceManager.getDefaultSharedPreferences(this).getInt("id", 0);
    Cursor localCursor = this.db.getInfoReminder(i);
    this.db.close();
    int j;
    if (localCursor != null)
    {
      localCursor.moveToFirst();
      j = 0;
      int k = localCursor.getCount();
      if (j < k);
    }
    else
    {
      ReminderAdapter localReminderAdapter = new ReminderAdapter(this, this.data, this.ids, "REMINDER", "reminder_id", this.millis, this.descs);
      this.listView.setAdapter(localReminderAdapter);
      if (localCursor.getCount() != 0)
        break label362;
      this.no_acc.setVisibility(0);
      this.listView.setVisibility(8);
    }
    label362: 
    do
    {
      return;
      this.ids.add(localCursor.getString(0));
      this.data.add(localCursor.getString(1));
      this.descs.add(localCursor.getString(4));
      String[] arrayOfString1 = localCursor.getString(2).split("-");
      String[] arrayOfString2 = localCursor.getString(3).split(":");
      String[] arrayOfString3 = arrayOfString2[1].split(" ");
      int m = Integer.parseInt(arrayOfString1[0]);
      int n = Integer.parseInt(arrayOfString1[1]);
      int i1 = Integer.parseInt(arrayOfString1[2].trim());
      int i2 = Integer.parseInt(arrayOfString2[0]);
      int i3 = Integer.parseInt(arrayOfString3[0]);
      if (arrayOfString3[1].equals("PM"))
        i2 += 12;
      long l = timeInMills(i1, n - 1, m, i2, i3);
      this.millis.add(Long.valueOf(l));
      localCursor.moveToNext();
      j++;
      break;
    }
    while (localCursor.getCount() <= 0);
    this.no_acc.setVisibility(8);
    this.listView.setVisibility(0);
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
    return false;
  }
}

/* Location:           C:\Users\Yash\Desktop\apk to src\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.girnar.online_digital_diary.ui.ShowReminderActivity
 * JD-Core Version:    0.6.0
 */
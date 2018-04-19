package com.girnar.online_digital_diary.ui;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.girnar.online_digital_diary.database.DbHelper;
import com.girnar.online_digital_diary.interfaces.ImportantMethod;
import com.girnar.online_digital_diary.util.Util;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Tracker;

public class HomePageActivity extends Activity
  implements ImportantMethod, View.OnClickListener
{
  private final String TAG = "HomePageActivity";
  String WelcomeName;
  ImageView back;
  private LinearLayout bookmark;
  private Button buttonbookmark;
  private Button buttonotherInfo;
  private Button buttonpersonInfo;
  private Button buttonreminder;
  private Button buttonsendInfo;
  private DbHelper db = new DbHelper(this);
  private TextView header;
  ImageView home;
  ImageView image;
  private LinearLayout otherInfo;
  private LinearLayout personInfo;
  private LinearLayout reminder;
  private LinearLayout sendInfo;
  private Tracker tracker;
  private TextView userName;

  public void addListener()
  {
    this.personInfo.setOnClickListener(this);
    this.otherInfo.setOnClickListener(this);
    this.reminder.setOnClickListener(this);
    this.bookmark.setOnClickListener(this);
    this.sendInfo.setOnClickListener(this);
    this.buttonpersonInfo.setOnClickListener(this);
    this.buttonotherInfo.setOnClickListener(this);
    this.buttonreminder.setOnClickListener(this);
    this.buttonbookmark.setOnClickListener(this);
    this.buttonsendInfo.setOnClickListener(this);
  }

  public void getIds()
  {
    this.personInfo = ((LinearLayout)findViewById(2131492906));
    this.otherInfo = ((LinearLayout)findViewById(2131492904));
    this.reminder = ((LinearLayout)findViewById(2131492910));
    this.bookmark = ((LinearLayout)findViewById(2131492908));
    this.sendInfo = ((LinearLayout)findViewById(2131492912));
    this.buttonpersonInfo = ((Button)findViewById(2131492907));
    this.buttonotherInfo = ((Button)findViewById(2131492905));
    this.buttonreminder = ((Button)findViewById(2131492911));
    this.buttonbookmark = ((Button)findViewById(2131492909));
    this.buttonsendInfo = ((Button)findViewById(2131492913));
    this.back = ((ImageView)findViewById(2131493050));
    this.home = ((ImageView)findViewById(2131493054));
    this.image = ((ImageView)findViewById(2131493051));
    this.header = ((TextView)findViewById(2131493052));
    this.userName = ((TextView)findViewById(2131492902));
    this.back.setVisibility(4);
    this.home.setVisibility(4);
    this.header.setText("DIGITAL DIARY");
  }

  public void onBackPressed()
  {
    LayoutInflater.from(this).inflate(2130903044, null);
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
    localBuilder.setMessage("Do you want to Exit").setCancelable(false).setPositiveButton("Cancel", new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        paramDialogInterface.cancel();
      }
    }).setNegativeButton("Exit", new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        HomePageActivity.this.finish();
      }
    });
    localBuilder.create();
    localBuilder.show();
  }

  public void onClick(View paramView)
  {
    if ((paramView == this.personInfo) || (paramView == this.buttonpersonInfo))
    {
      startActivity(new Intent(this, FrndListActivity.class));
      overridePendingTransition(2130968576, 2130968577);
    }
    if ((paramView == this.otherInfo) || (paramView == this.buttonotherInfo))
    {
      startActivity(new Intent(this, OtherListviewInfoActivity.class));
      overridePendingTransition(2130968576, 2130968577);
    }
    if ((paramView == this.reminder) || (paramView == this.buttonreminder))
    {
      startActivity(new Intent(this, ShowReminderActivity.class));
      overridePendingTransition(2130968576, 2130968577);
    }
    if ((paramView == this.bookmark) || (paramView == this.buttonbookmark))
    {
      startActivity(new Intent(this, BookmarkActivity.class));
      overridePendingTransition(2130968576, 2130968577);
    }
    if ((paramView == this.sendInfo) || (paramView == this.buttonsendInfo))
    {
      startActivity(new Intent(this, SendButtonActivity.class));
      overridePendingTransition(2130968576, 2130968577);
    }
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    EasyTracker.getInstance().setContext(getApplicationContext());
    this.tracker = EasyTracker.getTracker();
    this.tracker.trackView("Home page_Digital_Diary");
    Log.d("HomePageActivity", "on create called...");
    setContentView(2130903044);
    getIds();
    addListener();
    Util.addGoogleAds(this);
    this.db.open();
    int i = PreferenceManager.getDefaultSharedPreferences(this).getInt("id", 0);
    Cursor localCursor = this.db.getName(i);
    if (localCursor != null);
    for (int j = 0; ; j++)
    {
      if (j >= localCursor.getCount())
      {
        this.db.close();
        this.userName.setText(this.WelcomeName);
        return;
      }
      this.WelcomeName = localCursor.getString(0);
      localCursor.moveToNext();
    }
  }

  protected void onRestart()
  {
    super.onRestart();
    overridePendingTransition(2130968578, 2130968579);
  }

  public boolean validate()
  {
    return false;
  }
}

/* Location:           C:\Users\Yash\Desktop\apk to src\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.girnar.online_digital_diary.ui.HomePageActivity
 * JD-Core Version:    0.6.0
 */
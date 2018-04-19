package com.girnar.online_digital_diary.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.girnar.online_digital_diary.database.DbHelper;
import com.girnar.online_digital_diary.interfaces.ImportantMethod;
import com.girnar.online_digital_diary.util.Util;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Tracker;

public class SendButtonActivity extends Activity
  implements ImportantMethod, View.OnClickListener
{
  private final String TAG = "SendAccountInfoActivity";
  private LinearLayout accountinfo;
  private LinearLayout back;
  private Button buttonaccountinfo;
  private Button buttonfriendinfo;
  private Button buttonotherinfo;
  private DbHelper db = new DbHelper(this);
  private LinearLayout friendinfo;
  private TextView header_text;
  private ImageView home;
  private LinearLayout otherinfo;
  private Tracker tracker;

  public void addListener()
  {
    this.friendinfo.setOnClickListener(this);
    this.back.setOnClickListener(this);
    this.home.setOnClickListener(this);
    this.accountinfo.setOnClickListener(this);
    this.otherinfo.setOnClickListener(this);
    this.buttonfriendinfo.setOnClickListener(this);
    this.buttonaccountinfo.setOnClickListener(this);
    this.buttonotherinfo.setOnClickListener(this);
  }

  public LinearLayout getAccountinfo()
  {
    return this.accountinfo;
  }

  public void getIds()
  {
    this.friendinfo = ((LinearLayout)findViewById(2131493012));
    setAccountinfo((LinearLayout)findViewById(2131493011));
    setOtherinfo((LinearLayout)findViewById(2131493013));
    this.back = ((LinearLayout)findViewById(2131493049));
    this.home = ((ImageView)findViewById(2131493054));
    this.buttonfriendinfo = ((Button)findViewById(2131492907));
    this.buttonaccountinfo = ((Button)findViewById(2131492905));
    this.buttonotherinfo = ((Button)findViewById(2131492909));
    this.header_text = ((TextView)findViewById(2131493052));
    this.header_text.setText("Send Information");
  }

  public LinearLayout getOtherinfo()
  {
    return this.otherinfo;
  }

  public void onBackPressed()
  {
    finish();
  }

  public void onClick(View paramView)
  {
    if ((paramView == this.buttonfriendinfo) || (paramView == this.friendinfo))
    {
      this.db.open();
      int i = PreferenceManager.getDefaultSharedPreferences(getApplication()).getInt("id", 0);
      Cursor localCursor1 = this.db.getInfoperson(i);
      this.db.close();
      if (localCursor1.getCount() == 0)
        Toast.makeText(this, "Friends information list is empty", 0).show();
    }
    do
    {
      return;
      startActivity(new Intent(getApplicationContext(), SendFriendInformationActivity.class));
      overridePendingTransition(2130968576, 2130968577);
      return;
      if ((paramView == this.buttonaccountinfo) || (paramView == this.accountinfo))
      {
        this.db.open();
        int j = PreferenceManager.getDefaultSharedPreferences(getApplication()).getInt("id", 0);
        Cursor localCursor2 = this.db.getInfoOther(j);
        this.db.close();
        if (localCursor2.getCount() == 0)
        {
          Toast.makeText(this, "Bank Account information list is empty", 0).show();
          return;
        }
        startActivity(new Intent(getApplicationContext(), SendAccountInfoActivity.class));
        overridePendingTransition(2130968576, 2130968577);
        return;
      }
      if ((paramView == this.buttonotherinfo) || (paramView == this.otherinfo))
      {
        this.db.open();
        int k = PreferenceManager.getDefaultSharedPreferences(getApplication()).getInt("id", 0);
        Cursor localCursor3 = this.db.getInfoBookmark(k);
        this.db.close();
        if (localCursor3.getCount() == 0)
        {
          Toast.makeText(this, "other information list is empty", 0).show();
          return;
        }
        startActivity(new Intent(getApplicationContext(), SendOtherInfoActivity.class));
        overridePendingTransition(2130968576, 2130968577);
        return;
      }
      if (paramView != this.back)
        continue;
      finish();
      return;
    }
    while (paramView != this.home);
    finish();
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Log.d("SendAccountInfoActivity", "on create called...");
    setContentView(2130903055);
    EasyTracker.getInstance().setContext(getApplicationContext());
    this.tracker = EasyTracker.getTracker();
    this.tracker.trackView("List of Send Buttons Digital_Diary");
    Util.addGoogleAds(this);
    getIds();
    addListener();
    validate();
  }

  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    getMenuInflater().inflate(2131427329, paramMenu);
    return true;
  }

  protected void onRestart()
  {
    super.onRestart();
    overridePendingTransition(2130968578, 2130968579);
  }

  public void setAccountinfo(LinearLayout paramLinearLayout)
  {
    this.accountinfo = paramLinearLayout;
  }

  public void setOtherinfo(LinearLayout paramLinearLayout)
  {
    this.otherinfo = paramLinearLayout;
  }

  public boolean validate()
  {
    return false;
  }
}

/* Location:           C:\Users\Yash\Desktop\apk to src\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.girnar.online_digital_diary.ui.SendButtonActivity
 * JD-Core Version:    0.6.0
 */
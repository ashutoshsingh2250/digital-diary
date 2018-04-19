package com.girnar.online_digital_diary.ui;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.girnar.online_digital_diary.database.DbHelper;
import com.girnar.online_digital_diary.interfaces.ImportantMethod;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Tracker;

public class OtherInfoShowActivity extends Activity
  implements ImportantMethod, View.OnClickListener
{
  private final String TAG = "OtherInfoShowActivity";
  private Button back;
  private String[] data;
  private DbHelper db = new DbHelper(this);
  private int id;
  private TextView person_account_no;
  private TextView person_banks_name;
  private TextView person_holders_name;
  private TextView person_location;
  private Tracker tracker;

  public void addListener()
  {
    this.back.setOnClickListener(this);
  }

  public String[] getData()
  {
    return this.data;
  }

  public void getIds()
  {
    this.person_holders_name = ((TextView)findViewById(2131492930));
    this.person_account_no = ((TextView)findViewById(2131492931));
    this.person_banks_name = ((TextView)findViewById(2131492932));
    this.person_location = ((TextView)findViewById(2131492933));
    this.back = ((Button)findViewById(2131492934));
  }

  public void onBackPressed()
  {
    super.onBackPressed();
    finish();
  }

  public void onClick(View paramView)
  {
    if (paramView == this.back)
      finish();
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Log.d("OtherInfoShowActivity", "on create called...");
    requestWindowFeature(1);
    setContentView(2130903047);
    EasyTracker.getInstance().setContext(getApplicationContext());
    this.tracker = EasyTracker.getTracker();
    this.tracker.trackView("Show the Account Infomation Digital_Diary");
    getIds();
    addListener();
    this.db.open();
    this.id = getIntent().getExtras().getInt("id");
    Cursor localCursor = this.db.getInfoOtherMethod(this.id);
    this.db.close();
    if (localCursor != null)
      setData(new String[localCursor.getCount()]);
    for (int i = 0; ; i++)
    {
      if (i >= localCursor.getCount())
        return;
      String str1 = localCursor.getString(0);
      String str2 = localCursor.getString(1);
      String str3 = localCursor.getString(2);
      String str4 = localCursor.getString(3);
      this.person_holders_name.setText(str1);
      this.person_account_no.setText(str2);
      this.person_banks_name.setText(str3);
      this.person_location.setText(str4);
      localCursor.moveToNext();
    }
  }

  public void setData(String[] paramArrayOfString)
  {
    this.data = paramArrayOfString;
  }

  public boolean validate()
  {
    return false;
  }
}

/* Location:           C:\Users\Yash\Desktop\apk to src\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.girnar.online_digital_diary.ui.OtherInfoShowActivity
 * JD-Core Version:    0.6.0
 */
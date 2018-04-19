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

public class RemShowInfoActivity extends Activity
  implements ImportantMethod, View.OnClickListener
{
  private final String TAG = "RemShowInfoActivity";
  private Button back;
  private String[] data;
  private DbHelper db = new DbHelper(this);
  private int id;
  private TextView person_description;
  private TextView person_set_date;
  private TextView person_set_time;
  private TextView person_title;
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
    this.person_title = ((TextView)findViewById(2131492982));
    this.person_description = ((TextView)findViewById(2131492983));
    this.person_set_date = ((TextView)findViewById(2131492984));
    this.person_set_time = ((TextView)findViewById(2131492985));
    this.back = ((Button)findViewById(2131492986));
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
    Log.d("RemShowInfoActivity", "on create called...");
    setContentView(2130903052);
    EasyTracker.getInstance().setContext(getApplicationContext());
    this.tracker = EasyTracker.getTracker();
    this.tracker.trackView("Show information of reminders Digital_Diary");
    getIds();
    addListener();
    this.db.open();
    this.id = getIntent().getExtras().getInt("id");
    Cursor localCursor = this.db.getInfoReminderMethod(this.id);
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
      this.person_title.setText(str1);
      this.person_description.setText(str2);
      this.person_set_date.setText(str3);
      this.person_set_time.setText(str4);
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
 * Qualified Name:     com.girnar.online_digital_diary.ui.RemShowInfoActivity
 * JD-Core Version:    0.6.0
 */
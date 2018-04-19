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

public class PersonShowActivity extends Activity
  implements ImportantMethod, View.OnClickListener
{
  private final String TAG = "PersonShowActivity";
  private String[] data;
  private DbHelper db = new DbHelper(this);
  private int id;
  private TextView person_address;
  private TextView person_age;
  private TextView person_aniversary;
  private TextView person_birthday;
  private TextView person_email;
  private TextView person_gender;
  private TextView person_mobile_no;
  private TextView person_name;
  private Tracker tracker;
  private Button update;

  public void addListener()
  {
    this.update.setOnClickListener(this);
  }

  public String[] getData()
  {
    return this.data;
  }

  public void getIds()
  {
    this.person_name = ((TextView)findViewById(2131492939));
    this.person_address = ((TextView)findViewById(2131492940));
    this.person_birthday = ((TextView)findViewById(2131492941));
    this.person_mobile_no = ((TextView)findViewById(2131492942));
    this.person_aniversary = ((TextView)findViewById(2131492943));
    this.person_email = ((TextView)findViewById(2131492944));
    this.person_age = ((TextView)findViewById(2131492945));
    this.person_gender = ((TextView)findViewById(2131492946));
    this.update = ((Button)findViewById(2131492947));
  }

  public void onBackPressed()
  {
    super.onBackPressed();
    finish();
  }

  public void onClick(View paramView)
  {
    if (paramView == this.update)
      finish();
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Log.d("PersonShowActivity", "on create called...");
    setContentView(2130903049);
    EasyTracker.getInstance().setContext(getApplicationContext());
    this.tracker = EasyTracker.getTracker();
    this.tracker.trackView("Show the personal Information Digital_Diary");
    getIds();
    addListener();
    this.db.open();
    this.id = getIntent().getExtras().getInt("id");
    Cursor localCursor = this.db.getInfopersonMethod(this.id);
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
      String str5 = localCursor.getString(4);
      String str6 = localCursor.getString(5);
      String str7 = localCursor.getString(6);
      String str8 = localCursor.getString(8);
      this.person_name.setText(str1);
      this.person_address.setText(str2);
      this.person_birthday.setText(str3);
      this.person_mobile_no.setText(str4);
      this.person_aniversary.setText(str5);
      this.person_email.setText(str6);
      this.person_age.setText(str7);
      this.person_gender.setText(str8);
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
 * Qualified Name:     com.girnar.online_digital_diary.ui.PersonShowActivity
 * JD-Core Version:    0.6.0
 */
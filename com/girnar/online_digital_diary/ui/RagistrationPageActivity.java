package com.girnar.online_digital_diary.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.girnar.online_digital_diary.database.DbHelper;
import com.girnar.online_digital_diary.interfaces.ImportantMethod;
import com.girnar.online_digital_diary.util.Util;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Tracker;

public class RagistrationPageActivity extends Activity
  implements ImportantMethod, View.OnClickListener
{
  static final int DATE_DIALOG_ID;
  private final String TAG = "RagistrationPageActivity";
  private EditText answer;
  private LinearLayout back;
  private DatePicker datePicker;
  private DbHelper dbHelper1 = new DbHelper(this);
  private EditText fname;
  private TextView header;
  private ImageView home;
  private long id;
  private EditText password;
  private EditText question;
  private Button submit;
  private TextView textView;
  private Tracker tracker;
  private EditText username;

  public void addListener()
  {
    this.fname.setOnClickListener(this);
    this.username.setOnClickListener(this);
    this.password.setOnClickListener(this);
    this.submit.setOnClickListener(this);
    this.back.setOnClickListener(this);
  }

  public boolean checkRegistration()
  {
    String str = this.username.getText().toString();
    this.dbHelper1.open();
    Cursor localCursor = this.dbHelper1.checkUserName(str);
    this.dbHelper1.close();
    if ((localCursor == null) || (localCursor.getCount() == 0))
      return false;
    if (localCursor.getString(0).equalsIgnoreCase(str));
    Toast.makeText(this, "username already exist", 1).show();
    return true;
  }

  public long databaseHelper()
  {
    String str1 = this.fname.getText().toString();
    String str2 = this.username.getText().toString();
    String str3 = this.password.getText().toString();
    String str4 = this.question.getText().toString();
    String str5 = this.answer.getText().toString();
    this.dbHelper1.open();
    this.id = this.dbHelper1.insertinfo(str1, str2, str3, str4, str5);
    this.dbHelper1.close();
    return this.id;
  }

  public DatePicker getDatePicker()
  {
    return this.datePicker;
  }

  public void getIds()
  {
    this.fname = ((EditText)findViewById(2131492973));
    this.username = ((EditText)findViewById(2131492975));
    this.password = ((EditText)findViewById(2131492977));
    this.submit = ((Button)findViewById(2131492981));
    this.question = ((EditText)findViewById(2131492978));
    this.answer = ((EditText)findViewById(2131492979));
    this.back = ((LinearLayout)findViewById(2131493049));
    this.home = ((ImageView)findViewById(2131493054));
    this.home.setVisibility(4);
    this.header = ((TextView)findViewById(2131493052));
    this.header.setText("DIGITAL DIARY");
  }

  public TextView getTextView()
  {
    return this.textView;
  }

  public void onBackPressed()
  {
    finish();
  }

  public void onClick(View paramView)
  {
    if (paramView == this.submit)
    {
      boolean bool1 = validate();
      boolean bool2 = checkRegistration();
      if ((bool1) && (!bool2))
      {
        long l = databaseHelper();
        SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        localEditor.putInt("id", (int)l);
        localEditor.commit();
        Intent localIntent = new Intent(this, HomePageActivity.class).setFlags(67108864);
        localIntent.putExtra("userName", this.fname.getText().toString());
        startActivity(localIntent);
        overridePendingTransition(2130968576, 2130968577);
        LoginPageActivity.instance.finish();
        finish();
      }
    }
    do
      return;
    while (paramView != this.back);
    finish();
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Log.d("RagistrationPageActivity", "on create called...");
    setContentView(2130903051);
    setupUI(findViewById(2131492970));
    EasyTracker.getInstance().setContext(getApplicationContext());
    this.tracker = EasyTracker.getTracker();
    this.tracker.trackView("registration page Digital_Diary");
    getIds();
    addListener();
    Util.addGoogleAds(this);
  }

  public void setDatePicker(DatePicker paramDatePicker)
  {
    this.datePicker = paramDatePicker;
  }

  public void setTextView(TextView paramTextView)
  {
    this.textView = paramTextView;
  }

  public void setupUI(View paramView)
  {
    if (!(paramView instanceof EditText))
      paramView.setOnTouchListener(new View.OnTouchListener()
      {
        public boolean onTouch(View paramView, MotionEvent paramMotionEvent)
        {
          Util.hideSoftKeyboard(RagistrationPageActivity.this);
          return false;
        }
      });
    if ((paramView instanceof ViewGroup));
    for (int i = 0; ; i++)
    {
      if (i >= ((ViewGroup)paramView).getChildCount())
        return;
      setupUI(((ViewGroup)paramView).getChildAt(i));
    }
  }

  public boolean validate()
  {
    if (this.fname.getText().toString().length() == 0)
    {
      Toast.makeText(this, "Please fill  name", 0).show();
      return false;
    }
    if (this.username.getText().toString().length() == 0)
    {
      Toast.makeText(this, "Plese fill User Name", 0).show();
      return false;
    }
    if (this.password.getText().toString().length() == 0)
    {
      Toast.makeText(this, "Please fill password", 0).show();
      return false;
    }
    if (this.question.getText().toString().length() == 0)
    {
      Toast.makeText(this, "Please Enter Any Security Question", 0).show();
      return false;
    }
    if (this.answer.getText().toString().length() == 0)
    {
      Toast.makeText(this, "Please Enter Your answer", 0).show();
      return false;
    }
    return true;
  }
}

/* Location:           C:\Users\Yash\Desktop\apk to src\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.girnar.online_digital_diary.ui.RagistrationPageActivity
 * JD-Core Version:    0.6.0
 */
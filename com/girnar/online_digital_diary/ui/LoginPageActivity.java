package com.girnar.online_digital_diary.ui;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.girnar.online_digital_diary.database.DbHelper;
import com.girnar.online_digital_diary.interfaces.ImportantMethod;
import com.girnar.online_digital_diary.util.Util;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Tracker;

public class LoginPageActivity extends Activity
  implements View.OnClickListener, ImportantMethod
{
  public static Activity instance;
  private final String TAG = "LoginActivity";
  String ans;
  private ImageView back;
  private DbHelper db;
  public CheckBox dontShowAgain;
  private TextView forgot;
  private TextView header;
  private ImageView home;
  private int id;
  int id1;
  String name;
  private EditText password;
  String ques;
  private TextView registration;
  private Button submit;
  private Tracker tracker;
  private EditText userName;
  View view;

  public void addListener()
  {
    this.registration.setOnClickListener(this);
    this.submit.setOnClickListener(this);
    this.forgot.setOnClickListener(this);
  }

  public boolean checkLogin()
  {
    String str1 = this.userName.getText().toString();
    String str2 = this.password.getText().toString();
    this.db.open();
    Cursor localCursor = this.db.checkLogin(str1, str2);
    this.db.close();
    if (localCursor.getCount() == 0)
      return false;
    this.id = localCursor.getInt(0);
    this.name = localCursor.getString(1);
    return true;
  }

  public void getIds()
  {
    this.registration = ((TextView)findViewById(2131492921));
    this.submit = ((Button)findViewById(2131492917));
    this.userName = ((EditText)findViewById(2131492915));
    this.password = ((EditText)findViewById(2131492916));
    this.forgot = ((TextView)findViewById(2131492919));
    this.back = ((ImageView)findViewById(2131493050));
    this.home = ((ImageView)findViewById(2131493054));
    this.back.setVisibility(4);
    this.home.setVisibility(4);
    this.header = ((TextView)findViewById(2131493052));
    this.header.setText("DIGITAL DIARY");
  }

  public void onClick(View paramView)
  {
    if (paramView == this.registration)
    {
      startActivity(new Intent(this, RagistrationPageActivity.class));
      overridePendingTransition(2130968576, 2130968577);
    }
    do
    {
      return;
      if (paramView != this.submit)
        continue;
      if (checkLogin())
      {
        if (getIntent().getExtras().getString("auth").equals("reminder"))
        {
          SharedPreferences.Editor localEditor3 = PreferenceManager.getDefaultSharedPreferences(this).edit();
          localEditor3.putInt("id", this.id);
          localEditor3.commit();
          Intent localIntent4 = new Intent(this, ShowReminderActivity.class);
          localIntent4.putExtra("userName", this.name);
          startActivity(localIntent4);
          overridePendingTransition(2130968576, 2130968577);
          finish();
          return;
        }
        if (getIntent().getExtras().getString("auth").equals("friend"))
        {
          SharedPreferences.Editor localEditor2 = PreferenceManager.getDefaultSharedPreferences(this).edit();
          localEditor2.putInt("id", this.id);
          localEditor2.commit();
          Intent localIntent3 = new Intent(this, FrndListActivity.class);
          localIntent3.putExtra("userName", this.name);
          startActivity(localIntent3);
          overridePendingTransition(2130968576, 2130968577);
          finish();
          return;
        }
        SharedPreferences.Editor localEditor1 = PreferenceManager.getDefaultSharedPreferences(this).edit();
        localEditor1.putInt("id", this.id);
        localEditor1.commit();
        Intent localIntent2 = new Intent(this, HomePageActivity.class);
        localIntent2.putExtra("userName", this.name);
        startActivity(localIntent2);
        overridePendingTransition(2130968576, 2130968577);
        finish();
        return;
      }
      Toast.makeText(this, "Please enter correct user name and password", 1).show();
      this.userName.setText("");
      this.password.setText("");
      return;
    }
    while (paramView != this.forgot);
    if (this.userName.getText().toString().length() == 0)
    {
      Toast.makeText(this, "First Fill Username", 1).show();
      return;
    }
    this.db.open();
    Cursor localCursor = this.db.getQuestion(this.userName.getText().toString());
    this.db.close();
    if (localCursor.getCount() == 0)
    {
      Toast.makeText(this, "Please enter correct username", 1).show();
      return;
    }
    if (localCursor != null);
    for (int i = 0; ; i++)
    {
      if (i >= localCursor.getCount())
      {
        Intent localIntent1 = new Intent(this, ForgotActivity.class);
        localIntent1.putExtra("username", this.userName.getText().toString());
        localIntent1.putExtra("ques", this.ques);
        localIntent1.putExtra("ans", this.ans);
        localIntent1.putExtra("id", this.id1);
        startActivity(localIntent1);
        overridePendingTransition(2130968576, 2130968577);
        return;
      }
      this.ques = localCursor.getString(0);
      this.ans = localCursor.getString(1);
      this.id1 = localCursor.getInt(2);
      localCursor.moveToNext();
    }
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Log.d("LoginActivity", "on create called...");
    setContentView(2130903045);
    setupUI(findViewById(2131492914));
    if (getPreferences(0).getInt("old_user", 0) == 0)
    {
      Util.checkNotification(this);
      showDailogOldUser();
    }
    instance = this;
    this.db = new DbHelper(this);
    EasyTracker.getInstance().setContext(getApplicationContext());
    this.tracker = EasyTracker.getTracker();
    this.tracker.trackView("login page  Digital_Diary");
    getIds();
    addListener();
    Util.addGoogleAds(this);
  }

  protected void onRestart()
  {
    super.onRestart();
    overridePendingTransition(2130968578, 2130968579);
  }

  public void setupUI(View paramView)
  {
    if (!(paramView instanceof EditText))
      paramView.setOnTouchListener(new View.OnTouchListener()
      {
        public boolean onTouch(View paramView, MotionEvent paramMotionEvent)
        {
          Util.hideSoftKeyboard(LoginPageActivity.this);
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

  public void showDailogOldUser()
  {
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
    View localView = LayoutInflater.from(this).inflate(2130903064, null);
    this.dontShowAgain = ((CheckBox)localView.findViewById(2131493041));
    localBuilder.setView(localView);
    localBuilder.setTitle("Attention");
    localBuilder.setMessage(getString(2131034278));
    localBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        if (LoginPageActivity.this.dontShowAgain.isChecked())
        {
          SharedPreferences.Editor localEditor = LoginPageActivity.this.getPreferences(0).edit();
          localEditor.putInt("old_user", 1);
          localEditor.commit();
        }
      }
    });
    localBuilder.show();
  }

  public boolean validate()
  {
    return false;
  }
}

/* Location:           C:\Users\Yash\Desktop\apk to src\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.girnar.online_digital_diary.ui.LoginPageActivity
 * JD-Core Version:    0.6.0
 */
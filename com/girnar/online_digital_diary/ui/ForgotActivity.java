package com.girnar.online_digital_diary.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.girnar.online_digital_diary.database.DbHelper;
import com.girnar.online_digital_diary.util.Util;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Tracker;

public class ForgotActivity extends Activity
  implements View.OnClickListener
{
  String ans;
  EditText answer;
  private LinearLayout back;
  Button changePass;
  private String check;
  private DbHelper db = new DbHelper(this);
  private TextView header;
  private ImageView home;
  int id;
  EditText newAnswer;
  EditText newPass;
  EditText newQuestion;
  String ques;
  EditText question;
  private TextView subHeader;
  Button submit;
  Button submitQuestion;
  private Tracker tracker;
  String userName;

  public void onBackPressed()
  {
    finish();
  }

  public void onClick(View paramView)
  {
    if (paramView == this.changePass)
      if (this.changePass.getText().toString().equals(getString(2131034277)))
        if (validate())
        {
          if (!this.answer.getText().toString().equals(this.ans))
            break label135;
          this.answer.setFocusable(false);
          this.changePass.setClickable(false);
          this.changePass.setFocusable(false);
          this.changePass.setBackgroundDrawable(getResources().getDrawable(2130837518));
          this.newQuestion.setVisibility(0);
          this.newAnswer.setVisibility(0);
          this.submitQuestion.setVisibility(0);
          this.subHeader.setText("Change Security Question");
        }
    label135: 
    do
    {
      do
      {
        return;
        Toast.makeText(this, "Incorrect Answer", 1).show();
        this.answer.setText("");
        return;
      }
      while (!validate());
      if (this.answer.getText().toString().equals(this.ans))
      {
        this.answer.setFocusable(false);
        this.changePass.setClickable(false);
        this.changePass.setFocusable(false);
        this.changePass.setBackgroundDrawable(getResources().getDrawable(2130837518));
        this.newPass.setVisibility(0);
        this.submit.setVisibility(0);
        this.subHeader.setText("Change Password");
        return;
      }
      Toast.makeText(this, "Incorrect Answer", 1).show();
      this.answer.setText("");
      return;
      if (paramView == this.submit)
      {
        if (this.newPass.getText().toString().length() == 0)
        {
          Toast.makeText(this, "Please enter password", 0).show();
          return;
        }
        this.db.open();
        this.db.UpdatePassword(this.id, this.newPass.getText().toString());
        this.db.close();
        SharedPreferences.Editor localEditor2 = PreferenceManager.getDefaultSharedPreferences(this).edit();
        localEditor2.putInt("id", this.id);
        localEditor2.commit();
        startActivity(new Intent(this, HomePageActivity.class).setFlags(67108864));
        overridePendingTransition(2130968576, 2130968577);
        LoginPageActivity.instance.finish();
        Toast.makeText(this, "Password successfully changed", 0).show();
        finish();
        return;
      }
      if (paramView != this.back)
        continue;
      finish();
      return;
    }
    while (paramView != this.submitQuestion);
    if (this.newQuestion.getText().toString().length() == 0)
    {
      Toast.makeText(this, "Please enter Question", 0).show();
      return;
    }
    if (this.newAnswer.getText().toString().length() == 0)
    {
      Toast.makeText(this, "Please enter Answer", 0).show();
      return;
    }
    this.db.open();
    this.db.UpdateSecurityQuestion(this.newQuestion.getText().toString(), this.newAnswer.getText().toString(), this.id);
    this.db.close();
    SharedPreferences.Editor localEditor1 = PreferenceManager.getDefaultSharedPreferences(this).edit();
    localEditor1.putInt("id", this.id);
    localEditor1.commit();
    startActivity(new Intent(this, HomePageActivity.class).setFlags(67108864));
    overridePendingTransition(2130968576, 2130968577);
    LoginPageActivity.instance.finish();
    Toast.makeText(this, "Password successfully changed", 0).show();
    finish();
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903042);
    Util.setupUI(findViewById(2131492876), this);
    EasyTracker.getInstance().setContext(getApplicationContext());
    this.tracker = EasyTracker.getTracker();
    this.tracker.trackView("Forgot Password page Digital_Diary");
    this.ques = getIntent().getExtras().getString("ques");
    this.ans = getIntent().getExtras().getString("ans");
    this.id = getIntent().getExtras().getInt("id");
    this.question = ((EditText)findViewById(2131492880));
    this.newQuestion = ((EditText)findViewById(2131492890));
    this.newAnswer = ((EditText)findViewById(2131492892));
    this.answer = ((EditText)findViewById(2131492882));
    this.newPass = ((EditText)findViewById(2131492886));
    this.question.setText(this.ques);
    this.changePass = ((Button)findViewById(2131492884));
    this.changePass.setOnClickListener(this);
    this.submit = ((Button)findViewById(2131492888));
    this.submit.setOnClickListener(this);
    this.submitQuestion = ((Button)findViewById(2131492894));
    this.submitQuestion.setOnClickListener(this);
    this.back = ((LinearLayout)findViewById(2131493049));
    this.home = ((ImageView)findViewById(2131493054));
    this.home.setVisibility(4);
    this.header = ((TextView)findViewById(2131493052));
    this.subHeader = ((TextView)findViewById(2131492879));
    this.subHeader.setText("Security Question");
    this.header.setText("DIGITAL DIARY");
    this.back.setOnClickListener(this);
    Util.addGoogleAds(this);
    this.newQuestion.setVisibility(8);
    this.newAnswer.setVisibility(8);
    this.submitQuestion.setVisibility(8);
    this.newPass.setVisibility(8);
    this.submit.setVisibility(8);
    this.db.open();
    Cursor localCursor = this.db.checkOldUser(getIntent().getExtras().getString("username"));
    this.db.close();
    if (localCursor != null);
    for (int i = 0; ; i++)
    {
      if (i >= localCursor.getCount())
      {
        if (this.check.equals("old"))
          this.changePass.setText(getString(2131034277));
        return;
      }
      this.check = localCursor.getString(0);
      localCursor.moveToNext();
    }
  }

  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    getMenuInflater().inflate(2131427332, paramMenu);
    return true;
  }

  public boolean validate()
  {
    if (this.answer.getText().toString().length() == 0)
    {
      Toast.makeText(this, "Please enter answer", 0).show();
      return false;
    }
    return true;
  }
}

/* Location:           C:\Users\Yash\Desktop\apk to src\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.girnar.online_digital_diary.ui.ForgotActivity
 * JD-Core Version:    0.6.0
 */
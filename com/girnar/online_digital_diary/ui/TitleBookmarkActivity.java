package com.girnar.online_digital_diary.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
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

public class TitleBookmarkActivity extends Activity
  implements ImportantMethod, View.OnClickListener
{
  private final String TAG = "TitleBookmarkActivity";
  LinearLayout back;
  private DbHelper db = new DbHelper(this);
  private EditText description;
  private TextView header_text;
  private ImageView home;
  private Button save;
  private EditText title;
  private Tracker tracker;

  public void addListener()
  {
    this.back.setOnClickListener(this);
    this.save.setOnClickListener(this);
    this.home.setOnClickListener(this);
  }

  public void databaseHelper()
  {
    String str1 = this.title.getText().toString();
    String str2 = this.description.getText().toString();
    this.db.open();
    int i = PreferenceManager.getDefaultSharedPreferences(this).getInt("id", 0);
    this.db.insertinfoBookmark(str1, str2, i);
    this.db.close();
  }

  public void getIds()
  {
    this.back = ((LinearLayout)findViewById(2131493049));
    this.home = ((ImageView)findViewById(2131493054));
    this.save = ((Button)findViewById(2131493025));
    this.title = ((EditText)findViewById(2131493022));
    this.description = ((EditText)findViewById(2131493024));
    this.header_text = ((TextView)findViewById(2131493052));
    this.header_text.setText("Other Information");
  }

  public void onBackPressed()
  {
    startActivity(new Intent(this, BookmarkActivity.class).setFlags(67108864));
    overridePendingTransition(2130968578, 2130968579);
    finish();
  }

  public void onClick(View paramView)
  {
    if (paramView == this.back)
    {
      startActivity(new Intent(this, BookmarkActivity.class).setFlags(67108864));
      overridePendingTransition(2130968578, 2130968579);
      finish();
    }
    while (true)
    {
      if (paramView == this.home)
      {
        Util.homeAnimation(this);
        finish();
      }
      return;
      if ((paramView != this.save) || (!validate()))
        continue;
      databaseHelper();
      startActivity(new Intent(this, BookmarkActivity.class).setFlags(67108864));
      overridePendingTransition(2130968578, 2130968579);
      finish();
    }
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Log.d("TitleBookmarkActivity", "on create called...");
    setContentView(2130903061);
    Util.setupUI(findViewById(2131493020), this);
    EasyTracker.getInstance().setContext(getApplicationContext());
    this.tracker = EasyTracker.getTracker();
    this.tracker.trackView("Other Information Digital_Diary");
    getIds();
    addListener();
    Util.addGoogleAds(this);
  }

  public boolean validate()
  {
    if (this.title.getText().toString().length() == 0)
    {
      Toast.makeText(this, "Please fill Title name", 0).show();
      return false;
    }
    if (this.description.getText().toString().length() == 0)
    {
      Toast.makeText(this, "Please fill Description", 0).show();
      return false;
    }
    return true;
  }
}

/* Location:           C:\Users\Yash\Desktop\apk to src\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.girnar.online_digital_diary.ui.TitleBookmarkActivity
 * JD-Core Version:    0.6.0
 */
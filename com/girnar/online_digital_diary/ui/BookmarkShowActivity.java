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

public class BookmarkShowActivity extends Activity
  implements ImportantMethod, View.OnClickListener
{
  private final String TAG = "BookmarkShowActivity";
  private Button back;
  private String[] data;
  private DbHelper db = new DbHelper(this);
  private int id;
  private TextView person_description;
  private TextView person_title;
  private Tracker tracker;

  public void addListener()
  {
    this.back.setOnClickListener(this);
  }

  public void getIds()
  {
    this.person_title = ((TextView)findViewById(2131492873));
    this.person_description = ((TextView)findViewById(2131492874));
    this.back = ((Button)findViewById(2131492875));
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
    Log.d("BookmarkShowActivity", "on create called...");
    setContentView(2130903041);
    EasyTracker.getInstance().setContext(getApplicationContext());
    this.tracker = EasyTracker.getTracker();
    this.tracker.trackView("List of Other information_Digital_Diary");
    getIds();
    addListener();
    this.db.open();
    this.id = getIntent().getExtras().getInt("id");
    Cursor localCursor = this.db.getInfoBookmarkMethod(this.id);
    this.db.close();
    if (localCursor != null)
      this.data = new String[localCursor.getCount()];
    for (int i = 0; ; i++)
    {
      if (i >= localCursor.getCount())
        return;
      String str1 = localCursor.getString(0);
      String str2 = localCursor.getString(1);
      this.person_title.setText(str1);
      this.person_description.setText(str2);
      localCursor.moveToNext();
    }
  }

  public boolean validate()
  {
    return false;
  }
}

/* Location:           C:\Users\Yash\Desktop\apk to src\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.girnar.online_digital_diary.ui.BookmarkShowActivity
 * JD-Core Version:    0.6.0
 */
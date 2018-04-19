package com.girnar.online_digital_diary.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.girnar.online_digital_diary.adapters.CustomAdapter;
import com.girnar.online_digital_diary.database.DbHelper;
import com.girnar.online_digital_diary.interfaces.ImportantMethod;
import com.girnar.online_digital_diary.util.Util;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Tracker;
import java.util.ArrayList;

public class BookmarkActivity extends Activity
  implements AdapterView.OnItemClickListener, ImportantMethod, View.OnClickListener
{
  public static BookmarkActivity activity;
  private Button New;
  private final String TAG = "BookmarkActivity";
  private LinearLayout back;
  private ArrayList<String> data = new ArrayList();
  private DbHelper db = new DbHelper(this);
  private EditText editTextSearch;
  private View footerView;
  private TextView header_text;
  private ImageView home;
  private ArrayList<String> ids = new ArrayList();
  private ListView listView;
  private TextView no_acc;
  ImageButton search;
  private Tracker tracker;

  public void addListener()
  {
    this.New.setOnClickListener(this);
    this.back.setOnClickListener(this);
    this.listView.setOnItemClickListener(this);
    this.home.setOnClickListener(this);
    this.editTextSearch.addTextChangedListener(new TextWatcher()
    {
      public void afterTextChanged(Editable paramEditable)
      {
      }

      public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
      {
      }

      public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
      {
        BookmarkActivity.this.db.open();
        int i = PreferenceManager.getDefaultSharedPreferences(BookmarkActivity.this).getInt("id", 0);
        Cursor localCursor = BookmarkActivity.this.db.searchInfoBookmark(BookmarkActivity.this.editTextSearch.getText().toString(), i);
        BookmarkActivity.this.db.close();
        BookmarkActivity.this.ids.clear();
        BookmarkActivity.this.data.clear();
        int j;
        if (localCursor != null)
        {
          localCursor.moveToFirst();
          j = 0;
          if (j < localCursor.getCount());
        }
        else
        {
          CustomAdapter localCustomAdapter = new CustomAdapter(BookmarkActivity.this, BookmarkActivity.this.data, BookmarkActivity.this.ids, "PERSONALINFORMATION", "person_id", null, null);
          BookmarkActivity.this.listView.setAdapter(localCustomAdapter);
          localCustomAdapter.notifyDataSetChanged();
          if (localCursor.getCount() != 0)
            break label251;
          BookmarkActivity.this.no_acc.setVisibility(0);
          BookmarkActivity.this.listView.setVisibility(8);
        }
        label251: 
        do
        {
          return;
          BookmarkActivity.this.ids.add(localCursor.getString(0));
          BookmarkActivity.this.data.add(localCursor.getString(1));
          localCursor.moveToNext();
          j++;
          break;
        }
        while (localCursor.getCount() <= 0);
        BookmarkActivity.this.no_acc.setVisibility(8);
        BookmarkActivity.this.listView.setVisibility(0);
      }
    });
  }

  public void getIds()
  {
    this.New = ((Button)findViewById(2131492868));
    this.back = ((LinearLayout)findViewById(2131493049));
    this.home = ((ImageView)findViewById(2131493054));
    this.no_acc = ((TextView)findViewById(2131492871));
    this.no_acc.setText("No Other Information");
    this.listView = ((ListView)findViewById(2131492872));
    this.header_text = ((TextView)findViewById(2131493052));
    this.header_text.setText("Other Information List");
    this.editTextSearch = ((EditText)findViewById(2131492870));
    this.footerView = getLayoutInflater().inflate(2130903069, null);
    this.listView.addFooterView(this.footerView);
  }

  public void onBackPressed()
  {
    finish();
  }

  public void onClick(View paramView)
  {
    if (paramView == this.New)
    {
      startActivity(new Intent(this, TitleBookmarkActivity.class));
      overridePendingTransition(2130968576, 2130968577);
    }
    if (paramView == this.back)
      finish();
    if (paramView == this.home)
      finish();
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Log.d("BookmarkActivity", "on create called...");
    activity = this;
    setContentView(2130903040);
    EasyTracker.getInstance().setContext(getApplicationContext());
    this.tracker = EasyTracker.getTracker();
    this.tracker.trackView("Other information_Digital_Diary");
    Util.setupUI(findViewById(2131492864), this);
    getIds();
    addListener();
    Util.addGoogleAds(this);
    this.db.open();
    int i = PreferenceManager.getDefaultSharedPreferences(this).getInt("id", 0);
    Cursor localCursor = this.db.getInfoBookmark(i);
    this.db.close();
    if (localCursor != null)
      localCursor.moveToFirst();
    for (int j = 0; ; j++)
    {
      if (j >= localCursor.getCount())
      {
        CustomAdapter localCustomAdapter = new CustomAdapter(this, this.data, this.ids, "BOOKMARK", "bookmark_id", null, null);
        this.listView.setAdapter(localCustomAdapter);
        if (localCursor.getCount() == 0)
        {
          this.no_acc.setVisibility(0);
          this.listView.setVisibility(8);
        }
        return;
      }
      this.ids.add(localCursor.getString(0));
      this.data.add(localCursor.getString(1));
      localCursor.moveToNext();
    }
  }

  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    ((String)this.data.get(paramInt));
    String str = (String)this.ids.get(paramInt);
    Intent localIntent = new Intent(this, BookmarkShowActivity.class);
    localIntent.putExtra("id", Integer.parseInt(str));
    startActivity(localIntent);
  }

  protected void onRestart()
  {
    super.onRestart();
    this.ids.clear();
    this.data.clear();
    this.db.open();
    int i = PreferenceManager.getDefaultSharedPreferences(this).getInt("id", 0);
    Cursor localCursor = this.db.getInfoBookmark(i);
    this.db.close();
    int j;
    if (localCursor != null)
    {
      localCursor.moveToFirst();
      j = 0;
      if (j < localCursor.getCount());
    }
    else
    {
      CustomAdapter localCustomAdapter = new CustomAdapter(this, this.data, this.ids, "BOOKMARK", "bookmark_id", null, null);
      this.listView.setAdapter(localCustomAdapter);
      if (localCursor.getCount() != 0)
        break label185;
      this.no_acc.setVisibility(0);
      this.listView.setVisibility(8);
    }
    label185: 
    do
    {
      return;
      this.ids.add(localCursor.getString(0));
      this.data.add(localCursor.getString(1));
      localCursor.moveToNext();
      j++;
      break;
    }
    while (localCursor.getCount() <= 0);
    this.no_acc.setVisibility(8);
    this.listView.setVisibility(0);
  }

  public boolean validate()
  {
    return false;
  }
}

/* Location:           C:\Users\Yash\Desktop\apk to src\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.girnar.online_digital_diary.ui.BookmarkActivity
 * JD-Core Version:    0.6.0
 */
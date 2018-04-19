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

public class OtherListviewInfoActivity extends Activity
  implements ImportantMethod, View.OnClickListener, AdapterView.OnItemClickListener
{
  public static OtherListviewInfoActivity activity;
  private Button New;
  private final String TAG = "OtherListviewInfoActivity";
  LinearLayout back;
  private ArrayList<String> data = new ArrayList();
  private DbHelper db = new DbHelper(this);
  EditText editTextSearch;
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
    this.home.setOnClickListener(this);
    this.listView.setOnItemClickListener(this);
    this.search.setOnClickListener(this);
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
        OtherListviewInfoActivity.this.db.open();
        int i = PreferenceManager.getDefaultSharedPreferences(OtherListviewInfoActivity.this).getInt("id", 0);
        Cursor localCursor = OtherListviewInfoActivity.this.db.searchInfoOther(OtherListviewInfoActivity.this.editTextSearch.getText().toString(), i);
        OtherListviewInfoActivity.this.db.close();
        OtherListviewInfoActivity.this.ids.clear();
        OtherListviewInfoActivity.this.data.clear();
        int j;
        if (localCursor != null)
        {
          localCursor.moveToFirst();
          j = 0;
          if (j < localCursor.getCount());
        }
        else
        {
          CustomAdapter localCustomAdapter = new CustomAdapter(OtherListviewInfoActivity.this, OtherListviewInfoActivity.this.data, OtherListviewInfoActivity.this.ids, "OTHERINFO", "account_no_id", null, null);
          OtherListviewInfoActivity.this.listView.setAdapter(localCustomAdapter);
          localCustomAdapter.notifyDataSetChanged();
          if (localCursor.getCount() != 0)
            break label251;
          OtherListviewInfoActivity.this.no_acc.setVisibility(0);
          OtherListviewInfoActivity.this.listView.setVisibility(8);
        }
        label251: 
        do
        {
          return;
          OtherListviewInfoActivity.this.ids.add(localCursor.getString(0));
          OtherListviewInfoActivity.this.data.add(localCursor.getString(1));
          localCursor.moveToNext();
          j++;
          break;
        }
        while (localCursor.getCount() <= 0);
        OtherListviewInfoActivity.this.no_acc.setVisibility(8);
        OtherListviewInfoActivity.this.listView.setVisibility(0);
      }
    });
  }

  public void getIds()
  {
    this.New = ((Button)findViewById(2131492936));
    this.back = ((LinearLayout)findViewById(2131493049));
    this.home = ((ImageView)findViewById(2131493054));
    this.listView = ((ListView)findViewById(2131492938));
    this.no_acc = ((TextView)findViewById(2131492871));
    this.header_text = ((TextView)findViewById(2131493052));
    this.header_text.setText("Bank Accounts List");
    this.search = ((ImageButton)findViewById(2131492869));
    this.editTextSearch = ((EditText)findViewById(2131492937));
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
      startActivity(new Intent(this, OtherInfoActivity.class));
      overridePendingTransition(2130968576, 2130968577);
    }
    do
    {
      return;
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
    Log.d("OtherListviewInfoActivity", "on create called...");
    activity = this;
    setContentView(2130903048);
    Util.setupUI(findViewById(2131492935), this);
    EasyTracker.getInstance().setContext(getApplicationContext());
    this.tracker = EasyTracker.getTracker();
    this.tracker.trackView("List of Account information Digital_Diary");
    getIds();
    addListener();
    Util.addGoogleAds(this);
    this.db.open();
    int i = PreferenceManager.getDefaultSharedPreferences(this).getInt("id", 0);
    Cursor localCursor = this.db.getInfoOther(i);
    this.db.close();
    if (localCursor != null)
      localCursor.moveToFirst();
    for (int j = 0; ; j++)
    {
      if (j >= localCursor.getCount())
      {
        CustomAdapter localCustomAdapter = new CustomAdapter(this, this.data, this.ids, "OTHERINFO", "account_no_id", null, null);
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
  }

  protected void onRestart()
  {
    super.onRestart();
    this.ids.clear();
    this.data.clear();
    this.db.open();
    int i = PreferenceManager.getDefaultSharedPreferences(this).getInt("id", 0);
    Cursor localCursor = this.db.getInfoOther(i);
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
      CustomAdapter localCustomAdapter = new CustomAdapter(this, this.data, this.ids, "OTHERINFO", "account_no_id", null, null);
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
 * Qualified Name:     com.girnar.online_digital_diary.ui.OtherListviewInfoActivity
 * JD-Core Version:    0.6.0
 */
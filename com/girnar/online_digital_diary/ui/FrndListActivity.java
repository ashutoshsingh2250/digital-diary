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
import android.widget.AdapterView.OnItemLongClickListener;
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
import com.girnar.online_digital_diary.util.UiDatePicker;
import com.girnar.online_digital_diary.util.UiTimePicker;
import com.girnar.online_digital_diary.util.Util;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Tracker;
import java.util.ArrayList;

public class FrndListActivity extends Activity
  implements ImportantMethod, View.OnClickListener, AdapterView.OnItemLongClickListener
{
  public static FrndListActivity activity;
  private Button New;
  private final String TAG = "FrndListActivity";
  private ArrayList<Long> aniv_millis = new ArrayList();
  LinearLayout back;
  private ArrayList<Long> bithday_millis = new ArrayList();
  private ArrayList<String> data = new ArrayList();
  private DbHelper db = new DbHelper(this);
  private EditText editTextSearch;
  View footerView;
  private TextView header_text;
  private ImageView home;
  private ArrayList<String> ids = new ArrayList();
  private ListView listView;
  private TextView no_acc;
  ImageButton search;
  private Tracker tracker;
  UiDatePicker uiDatePicker;
  UiTimePicker uiTimePicker;

  public void addListener()
  {
    this.New.setOnClickListener(this);
    this.back.setOnClickListener(this);
    this.home.setOnClickListener(this);
    this.listView.setOnItemLongClickListener(this);
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
        FrndListActivity.this.db.open();
        int i = PreferenceManager.getDefaultSharedPreferences(FrndListActivity.this).getInt("id", 0);
        Cursor localCursor = FrndListActivity.this.db.searchInfopersonMethod(FrndListActivity.this.editTextSearch.getText().toString(), i);
        FrndListActivity.this.db.close();
        FrndListActivity.this.ids.clear();
        FrndListActivity.this.data.clear();
        FrndListActivity.this.bithday_millis.clear();
        FrndListActivity.this.aniv_millis.clear();
        int j;
        if (localCursor != null)
        {
          localCursor.moveToFirst();
          j = 0;
          int k = localCursor.getCount();
          if (j < k);
        }
        else
        {
          CustomAdapter localCustomAdapter = new CustomAdapter(FrndListActivity.this, FrndListActivity.this.data, FrndListActivity.this.ids, "PERSONALINFORMATION", "person_id", FrndListActivity.this.bithday_millis, FrndListActivity.this.aniv_millis);
          FrndListActivity.this.listView.setAdapter(localCustomAdapter);
          localCustomAdapter.notifyDataSetChanged();
          if (localCursor.getCount() != 0)
            break label464;
          FrndListActivity.this.no_acc.setVisibility(0);
          FrndListActivity.this.listView.setVisibility(8);
        }
        label464: 
        do
        {
          return;
          FrndListActivity.this.ids.add(localCursor.getString(0));
          FrndListActivity.this.data.add(localCursor.getString(1));
          String[] arrayOfString1 = localCursor.getString(3).split("/");
          int m = Integer.parseInt(arrayOfString1[0]);
          int n = Integer.parseInt(arrayOfString1[1]);
          long l1 = Util.timeInMills1(Integer.parseInt(arrayOfString1[2].trim()), n - 1, m, 6, 0);
          FrndListActivity.this.bithday_millis.add(Long.valueOf(l1));
          if (localCursor.getString(5).equals(""))
            FrndListActivity.this.aniv_millis.add(null);
          while (true)
          {
            localCursor.moveToNext();
            j++;
            break;
            String[] arrayOfString2 = localCursor.getString(5).split("/");
            int i1 = Integer.parseInt(arrayOfString2[0]);
            int i2 = Integer.parseInt(arrayOfString2[1]);
            long l2 = Util.timeInMills1(Integer.parseInt(arrayOfString2[2].trim()), i2 - 1, i1, 6, 0);
            FrndListActivity.this.aniv_millis.add(Long.valueOf(l2));
          }
        }
        while (localCursor.getCount() <= 0);
        FrndListActivity.this.no_acc.setVisibility(8);
        FrndListActivity.this.listView.setVisibility(0);
      }
    });
  }

  public void getIds()
  {
    this.New = ((Button)findViewById(2131492897));
    this.back = ((LinearLayout)findViewById(2131493049));
    this.home = ((ImageView)findViewById(2131493054));
    this.no_acc = ((TextView)findViewById(2131492871));
    this.listView = ((ListView)findViewById(2131492899));
    this.header_text = ((TextView)findViewById(2131493052));
    this.header_text.setText("Friends List");
    this.editTextSearch = ((EditText)findViewById(2131492898));
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
      startActivity(new Intent(this, PersonalInformationActivity.class));
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
    Log.d("FrndListActivity", "on create called...");
    activity = this;
    setContentView(2130903043);
    Util.setupUI(findViewById(2131492896), this);
    EasyTracker.getInstance().setContext(getApplicationContext());
    this.tracker = EasyTracker.getTracker();
    this.tracker.trackView("Friend information_Digital_Diary");
    getIds();
    addListener();
    Util.addGoogleAds(this);
    int i = PreferenceManager.getDefaultSharedPreferences(this).getInt("id", 0);
    this.db.open();
    Cursor localCursor = this.db.getInfoperson(i);
    this.db.close();
    int j;
    if (localCursor != null)
    {
      localCursor.moveToFirst();
      j = 0;
      int k = localCursor.getCount();
      if (j < k);
    }
    else
    {
      CustomAdapter localCustomAdapter = new CustomAdapter(this, this.data, this.ids, "PERSONALINFORMATION", "person_id", this.bithday_millis, this.aniv_millis);
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
    String[] arrayOfString1 = localCursor.getString(3).split("/");
    int m = Integer.parseInt(arrayOfString1[0]);
    int n = Integer.parseInt(arrayOfString1[1]);
    long l1 = Util.timeInMills1(Integer.parseInt(arrayOfString1[2].trim()), n - 1, m, 6, 0);
    this.bithday_millis.add(Long.valueOf(l1));
    if (localCursor.getString(5).equals(""))
      this.aniv_millis.add(null);
    while (true)
    {
      localCursor.moveToNext();
      j++;
      break;
      String[] arrayOfString2 = localCursor.getString(5).split("/");
      int i1 = Integer.parseInt(arrayOfString2[0]);
      int i2 = Integer.parseInt(arrayOfString2[1]);
      long l2 = Util.timeInMills1(Integer.parseInt(arrayOfString2[2].trim()), i2 - 1, i1, 6, 0);
      this.aniv_millis.add(Long.valueOf(l2));
    }
  }

  public boolean onItemLongClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    return true;
  }

  protected void onRestart()
  {
    super.onRestart();
    this.ids.clear();
    this.data.clear();
    this.bithday_millis.clear();
    this.aniv_millis.clear();
    this.db.open();
    int i = PreferenceManager.getDefaultSharedPreferences(this).getInt("id", 0);
    Cursor localCursor = this.db.getInfoperson(i);
    this.db.close();
    int j;
    if (localCursor != null)
    {
      localCursor.moveToFirst();
      j = 0;
      int k = localCursor.getCount();
      if (j < k);
    }
    else
    {
      CustomAdapter localCustomAdapter = new CustomAdapter(this, this.data, this.ids, "PERSONALINFORMATION", "person_id", this.bithday_millis, this.aniv_millis);
      this.listView.setAdapter(localCustomAdapter);
      if (localCursor.getCount() != 0)
        break label378;
      this.no_acc.setVisibility(0);
      this.listView.setVisibility(8);
    }
    label378: 
    do
    {
      return;
      this.ids.add(localCursor.getString(0));
      this.data.add(localCursor.getString(1));
      String[] arrayOfString1 = localCursor.getString(3).split("/");
      int m = Integer.parseInt(arrayOfString1[0]);
      int n = Integer.parseInt(arrayOfString1[1]);
      long l1 = Util.timeInMills1(Integer.parseInt(arrayOfString1[2].trim()), n - 1, m, 6, 0);
      this.bithday_millis.add(Long.valueOf(l1));
      if (localCursor.getString(5).equals(""))
        this.aniv_millis.add(null);
      while (true)
      {
        localCursor.moveToNext();
        j++;
        break;
        String[] arrayOfString2 = localCursor.getString(5).split("/");
        int i1 = Integer.parseInt(arrayOfString2[0]);
        int i2 = Integer.parseInt(arrayOfString2[1]);
        long l2 = Util.timeInMills1(Integer.parseInt(arrayOfString2[2].trim()), i2 - 1, i1, 6, 0);
        this.aniv_millis.add(Long.valueOf(l2));
      }
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
 * Qualified Name:     com.girnar.online_digital_diary.ui.FrndListActivity
 * JD-Core Version:    0.6.0
 */
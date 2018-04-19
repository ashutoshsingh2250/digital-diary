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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.girnar.online_digital_diary.adapters.SendOtherInfoAdapter;
import com.girnar.online_digital_diary.beans.Items;
import com.girnar.online_digital_diary.database.DbHelper;
import com.girnar.online_digital_diary.interfaces.ImportantMethod;
import com.girnar.online_digital_diary.util.Util;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Tracker;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SendOtherInfoActivity extends Activity
  implements ImportantMethod, AdapterView.OnItemClickListener, CompoundButton.OnCheckedChangeListener, View.OnClickListener
{
  public static ArrayList<String> checked_ids;
  public static ArrayList<Integer> opened_ids;
  private static CheckBox selectAll;
  public static int select_all_flag = 0;
  public static ArrayList<Integer> send_ids;
  private final String TAG = "SendOtherInfoActivity";
  private LinearLayout back;
  private Button btn_sendAccountInfo;
  private DbHelper db = new DbHelper(this);
  EditText editTextSearch;
  private TextView header_text;
  private ImageView home;
  private int id;
  int index;
  ArrayList<Items> items = new ArrayList();
  ArrayList<Items> items_temp;
  HashMap<String, List<String>> listDataChild = new HashMap();
  private ListView listView;
  ArrayList<Integer> selected_ids_for_send = new ArrayList();
  String selected_text_for_send = "";
  private Tracker tracker;

  public static void clearCheckedIds()
  {
    checked_ids.clear();
  }

  public static void clearSendInfoIds()
  {
    send_ids.clear();
  }

  public static void removeSendInfoIds(int paramInt, boolean paramBoolean)
  {
    if (paramBoolean)
      checked_ids.remove(checked_ids.indexOf(paramInt + "checked"));
    send_ids.remove(send_ids.indexOf(Integer.valueOf(paramInt)));
  }

  public static void removeSendOpenedIds(int paramInt)
  {
    opened_ids.remove(opened_ids.indexOf(Integer.valueOf(paramInt)));
  }

  public static void setSendInfoIds(int paramInt, boolean paramBoolean)
  {
    if (paramBoolean)
      checked_ids.add(paramInt + "checked");
    System.out.println("id is " + paramInt);
    send_ids.add(Integer.valueOf(paramInt));
  }

  public static void setSendOpendIds(int paramInt)
  {
    opened_ids.add(Integer.valueOf(paramInt));
  }

  public static void uncheckedSelectAllButton()
  {
    selectAll.setChecked(false);
    select_all_flag = 0;
  }

  public void addListener()
  {
    this.listView.setOnItemClickListener(this);
    selectAll.setOnClickListener(this);
    this.back.setOnClickListener(this);
    this.home.setOnClickListener(this);
    this.btn_sendAccountInfo.setOnClickListener(this);
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
        SendOtherInfoActivity.clearSendInfoIds();
        SendOtherInfoActivity.this.db.open();
        int i = PreferenceManager.getDefaultSharedPreferences(SendOtherInfoActivity.this).getInt("id", 0);
        Cursor localCursor = SendOtherInfoActivity.this.db.searchInfoBookmark(SendOtherInfoActivity.this.editTextSearch.getText().toString(), i);
        SendOtherInfoActivity.this.db.close();
        SendOtherInfoActivity.this.listDataChild.clear();
        SendOtherInfoActivity.this.items.clear();
        int j;
        if (localCursor != null)
        {
          localCursor.moveToFirst();
          j = 0;
          if (j < localCursor.getCount());
        }
        else
        {
          SendOtherInfoAdapter localSendOtherInfoAdapter = new SendOtherInfoAdapter(SendOtherInfoActivity.this, SendOtherInfoActivity.this.items, SendOtherInfoActivity.this.listDataChild, false);
          SendOtherInfoActivity.this.listView.setAdapter(localSendOtherInfoAdapter);
          localSendOtherInfoAdapter.notifyDataSetChanged();
          return;
        }
        Items localItems = new Items();
        localItems.setName(localCursor.getString(1));
        localItems.setId(localCursor.getString(0));
        if (SendOtherInfoActivity.checked_ids.contains(localItems.getId() + "checked"))
        {
          SendOtherInfoActivity.setSendInfoIds(Integer.parseInt(localItems.getId()), false);
          localItems.setChecked(true);
        }
        while (true)
        {
          localItems.setVisible(8);
          ArrayList localArrayList = new ArrayList();
          localArrayList.add(localCursor.getString(2));
          SendOtherInfoActivity.this.listDataChild.put(localItems.getName(), localArrayList);
          SendOtherInfoActivity.this.items.add(localItems);
          localCursor.moveToNext();
          j++;
          break;
          localItems.setChecked(false);
        }
      }
    });
  }

  public int getId()
  {
    return this.id;
  }

  public void getIds()
  {
    this.listView = ((ListView)findViewById(2131493010));
    selectAll = (CheckBox)findViewById(2131493009);
    this.back = ((LinearLayout)findViewById(2131493049));
    this.home = ((ImageView)findViewById(2131493054));
    this.header_text = ((TextView)findViewById(2131493052));
    this.header_text.setText("Send Other Info");
    this.btn_sendAccountInfo = ((Button)findViewById(2131493006));
    this.editTextSearch = ((EditText)findViewById(2131493007));
  }

  public void onBackPressed()
  {
    super.onBackPressed();
    finish();
  }

  public void onCheckedChanged(CompoundButton paramCompoundButton, boolean paramBoolean)
  {
  }

  public void onClick(View paramView)
  {
    if (paramView == this.back)
      finish();
    label143: label250: 
    do
    {
      return;
      if (paramView == this.home)
      {
        Util.homeAnimation(this);
        finish();
        return;
      }
      if (paramView != this.btn_sendAccountInfo)
        continue;
      int i2;
      if (select_all_flag == 1)
      {
        i2 = 0;
        if (i2 < this.items.size());
      }
      for (int i1 = 0; ; i1++)
      {
        if (i1 >= this.selected_ids_for_send.size())
        {
          if (this.selected_ids_for_send.size() != 0)
            break label402;
          Toast.makeText(this, "Please select other informations for send", 1).show();
          return;
          this.index = i2;
          this.selected_ids_for_send.add(Integer.valueOf(Integer.parseInt(((Items)this.items.get(this.index)).getId())));
          i2++;
          break;
          int m = 0;
          if (m < send_ids.size());
          for (int n = 0; ; n++)
          {
            if (n >= this.items.size());
            while (true)
            {
              this.selected_ids_for_send.add(Integer.valueOf(Integer.parseInt(((Items)this.items.get(this.index)).getId())));
              m++;
              break label143;
              break;
              if (Integer.parseInt(((Items)this.items.get(n)).getId()) != ((Integer)send_ids.get(m)).intValue())
                break label250;
              this.index = n;
            }
          }
        }
        this.db.open();
        Cursor localCursor2 = this.db.getInfoBookmarkMethod(((Integer)this.selected_ids_for_send.get(i1)).intValue());
        this.db.close();
        String str1 = localCursor2.getString(0);
        String str2 = localCursor2.getString(1);
        String str3 = i1 + 1 + ". \tTitle := " + str1 + "\n\tDescription :=" + str2 + "\n";
        String str4 = this.selected_text_for_send;
        StringBuilder localStringBuilder = new StringBuilder(String.valueOf(str4));
        this.selected_text_for_send = str3;
      }
      Intent localIntent = new Intent("android.intent.action.SEND");
      localIntent.setType("plain/text");
      localIntent.putExtra("id", this.id);
      localIntent.putExtra("android.intent.extra.EMAIL", new String[] { "to@email.com" });
      localIntent.putExtra("android.intent.extra.SUBJECT", "Subject");
      localIntent.putExtra("android.intent.extra.TEXT", this.selected_text_for_send);
      localIntent.setType("message/rfc822");
      startActivity(Intent.createChooser(localIntent, "Send mail..."));
      this.selected_ids_for_send.clear();
      this.selected_text_for_send = "";
      return;
    }
    while (paramView != selectAll);
    label402: if (((CheckBox)paramView).isChecked())
    {
      clearSendInfoIds();
      select_all_flag = 1;
      this.db.open();
      int j = PreferenceManager.getDefaultSharedPreferences(this).getInt("id", 0);
      Cursor localCursor1 = this.db.getInfoBookmark(j);
      this.db.close();
      ArrayList localArrayList1 = new ArrayList();
      this.listDataChild.clear();
      localArrayList1.clear();
      int k;
      if (localCursor1 != null)
      {
        localCursor1.moveToFirst();
        k = 0;
        if (k < localCursor1.getCount());
      }
      else
      {
        SendOtherInfoAdapter localSendOtherInfoAdapter2 = new SendOtherInfoAdapter(this, localArrayList1, this.listDataChild, false);
        this.listView.setAdapter(localSendOtherInfoAdapter2);
        localSendOtherInfoAdapter2.notifyDataSetChanged();
        return;
      }
      Items localItems = new Items();
      localItems.setName(localCursor1.getString(1));
      localItems.setId(localCursor1.getString(0));
      localItems.setChecked(true);
      if (opened_ids.contains(Integer.valueOf(Integer.parseInt(localItems.getId()))))
        localItems.setVisible(0);
      while (true)
      {
        ArrayList localArrayList2 = new ArrayList();
        localArrayList2.add(localCursor1.getString(2));
        this.listDataChild.put(localItems.getName(), localArrayList2);
        localArrayList1.add(localItems);
        setSendInfoIds(Integer.parseInt(localItems.getId()), true);
        localCursor1.moveToNext();
        k++;
        break;
        localItems.setVisible(8);
      }
    }
    select_all_flag = 2;
    clearSendInfoIds();
    clearCheckedIds();
    int i = 0;
    if (i >= this.items.size())
    {
      SendOtherInfoAdapter localSendOtherInfoAdapter1 = new SendOtherInfoAdapter(this, this.items, this.listDataChild, false);
      this.listView.setAdapter(localSendOtherInfoAdapter1);
      localSendOtherInfoAdapter1.notifyDataSetChanged();
      return;
    }
    if (opened_ids.contains(Integer.valueOf(Integer.parseInt(((Items)this.items.get(i)).getId()))))
      ((Items)this.items.get(i)).setVisible(0);
    while (true)
    {
      i++;
      break;
      ((Items)this.items.get(i)).setVisible(8);
    }
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Log.d("SendOtherInfoActivity", "on create called...");
    setContentView(2130903054);
    send_ids = new ArrayList();
    opened_ids = new ArrayList();
    checked_ids = new ArrayList();
    Util.setupUI(findViewById(2131493002), this);
    EasyTracker.getInstance().setContext(getApplicationContext());
    this.tracker = EasyTracker.getTracker();
    this.tracker.trackView("Send the Other Information Digital_Diary");
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
        SendOtherInfoAdapter localSendOtherInfoAdapter = new SendOtherInfoAdapter(this, this.items, this.listDataChild, true);
        this.listView.setAdapter(localSendOtherInfoAdapter);
        localSendOtherInfoAdapter.notifyDataSetChanged();
        return;
      }
      Items localItems = new Items();
      localItems.setName(localCursor.getString(1));
      localItems.setId(localCursor.getString(0));
      localItems.setChecked(false);
      localItems.setVisible(8);
      ArrayList localArrayList = new ArrayList();
      localArrayList.add(localCursor.getString(2));
      this.listDataChild.put(localItems.getName(), localArrayList);
      this.items.add(localItems);
      localCursor.moveToNext();
    }
  }

  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    getMenuInflater().inflate(2131427330, paramMenu);
    return true;
  }

  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
  }

  protected void onRestart()
  {
    super.onRestart();
    this.selected_text_for_send = "";
  }

  protected void onStop()
  {
    super.onStop();
    select_all_flag = 0;
  }

  public void setId(int paramInt)
  {
    this.id = paramInt;
  }

  public boolean validate()
  {
    return false;
  }
}

/* Location:           C:\Users\Yash\Desktop\apk to src\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.girnar.online_digital_diary.ui.SendOtherInfoActivity
 * JD-Core Version:    0.6.0
 */
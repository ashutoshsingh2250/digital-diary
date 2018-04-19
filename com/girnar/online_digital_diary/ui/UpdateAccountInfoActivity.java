package com.girnar.online_digital_diary.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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

public class UpdateAccountInfoActivity extends Activity
  implements ImportantMethod, View.OnClickListener
{
  private EditText account_no;
  LinearLayout back;
  private String[] bank = { "Punjab National Bank", "State Bank Of Bikaner", "State Bank Of India", "Union Bank", "Allahabad Bank", "Andhra Bank", "Bank of Baroda", "Bank of India", "Bank of Maharashtra", "Canara Bank", "Central Bank of India", "Corporation Bank", "Dena Bank", "IDBI Bank", "Indian Bank", "Indian Overseas Bank", "Oriental Bank of Commerce", "Punjab and Sind Bank", "Syndicate Bank", "UCO Bank", "Union Bank of India", "United Bank of India", "Vijaya Bank", "Punjab & Sind Bank", "UCO_Bank", "ECGC", "State Bank of Bikaner & Jaipur", "State Bank of Hyderabad", "State Bank of Mysore", "State Bank of Patiala", "State Bank of Travancore", "State Bank of Saurashtra", "State bank of Indore", "Axis Bank", "Federal Bank", "Karnataka Bank", "South Indian Bank", "ABN Amro Bank", "HDFC Bank", "Karur Vysya Bank", "YES Bank", "Catholic Syrian Bank", "ICICI Bank", "Kotak Mahindra Bank", "IndusInd Bank", "Lakshmi Vilas Bank", "Dhanlaxmi Bank", "ING Vysya Bank", "Tamilnadu Mercantile Bank", "Development Credit Bank", "Abu Dhabi Commercial Bank", "Australia and New Zealand Bank", "Bank Internasional Indonesia", "Bank of America NA", "Bank of Bahrain and Kuwait", "Bank of Ceylon", "Bank of Nova Scotia", "Bank of Tokyo Mitsubishi UFJ", "Barclays Bank PLC", "BNP Paribas", "Calyon Bank", "Chinatrust Commercial Bank", "Citibank N.A.", "Credit Suisse", "Commonwealth Bank of Australia", "DBS Bank", "DCB Bank now RHB Bank", "Deutsche Bank AG", "FirstRand Bank", "HSBC", "JPMorgan Chase Bank", "Krung Thai Bank", "Mashreq Bank psc", "Mizuho Corporate Bank", "Royal Bank of Scotland", "Shinhan Bank", "Société Générale", "Sonali Bank", "Standard Chartered Bank", "State Bank of Mauritius", "UBS", "Woori Bank", "ABN AMRO Bank N.V. - Royal Bank of Scotland", "National Australia Bank" };
  private AutoCompleteTextView banks_name;
  private DbHelper db = new DbHelper(this);
  private TextView header_text;
  private EditText holders_name;
  private ImageView home;
  private int id;
  private EditText location;
  private Button save;
  private String selected_acc_no;
  private String selected_bank_name;
  private String selected_holderName;
  private String selected_location;
  private Tracker tracker;

  public void addListener()
  {
    this.save.setOnClickListener(this);
    this.back.setOnClickListener(this);
    this.home.setOnClickListener(this);
  }

  public void databaseHelper()
  {
    String str1 = this.account_no.getText().toString();
    String str2 = this.holders_name.getText().toString();
    String str3 = this.banks_name.getEditableText().toString();
    String str4 = this.location.getText().toString();
    this.db.open();
    int i = PreferenceManager.getDefaultSharedPreferences(this).getInt("id", 0);
    this.db.UpdateBankAccountInfo(this.id, str2, str1, str3, str4, i);
    this.db.close();
  }

  public void getIds()
  {
    this.banks_name = ((AutoCompleteTextView)findViewById(2131492927));
    this.back = ((LinearLayout)findViewById(2131493049));
    this.home = ((ImageView)findViewById(2131493054));
    this.save = ((Button)findViewById(2131492929));
    this.account_no = ((EditText)findViewById(2131492926));
    this.holders_name = ((EditText)findViewById(2131492925));
    this.location = ((EditText)findViewById(2131492928));
    this.header_text = ((TextView)findViewById(2131493052));
    this.header_text.setText("Update Information");
  }

  public void onBackPressed()
  {
    startActivity(new Intent(this, OtherListviewInfoActivity.class).setFlags(67108864));
    overridePendingTransition(2130968578, 2130968579);
    finish();
  }

  public void onClick(View paramView)
  {
    if (paramView == this.save)
      if (validate())
      {
        databaseHelper();
        startActivity(new Intent(this, OtherListviewInfoActivity.class).setFlags(67108864));
        overridePendingTransition(2130968578, 2130968579);
        finish();
      }
    do
    {
      return;
      if (paramView != this.back)
        continue;
      startActivity(new Intent(this, OtherListviewInfoActivity.class).setFlags(67108864));
      overridePendingTransition(2130968578, 2130968579);
      finish();
      return;
    }
    while (paramView != this.home);
    Util.homeAnimation(this);
    finish();
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903046);
    Util.setupUI(findViewById(2131492922), this);
    EasyTracker.getInstance().setContext(getApplicationContext());
    this.tracker = EasyTracker.getTracker();
    this.tracker.trackView("Update Account Information Digital_Diary");
    getIds();
    addListener();
    Util.addGoogleAds(this);
    this.db.open();
    this.id = getIntent().getExtras().getInt("id");
    Cursor localCursor = this.db.getInfoOtherMethod(this.id);
    this.db.close();
    if (localCursor != null)
      localCursor.moveToFirst();
    for (int i = 0; ; i++)
    {
      if (i >= localCursor.getCount())
      {
        ArrayAdapter localArrayAdapter = new ArrayAdapter(getApplicationContext(), 2130903066, this.bank);
        this.banks_name.setAdapter(localArrayAdapter);
        this.banks_name.setThreshold(1);
        this.banks_name.setText(this.selected_bank_name);
        return;
      }
      this.selected_holderName = localCursor.getString(0);
      this.selected_acc_no = localCursor.getString(1);
      this.selected_bank_name = localCursor.getString(2);
      this.selected_location = localCursor.getString(3);
      this.holders_name.setText(this.selected_holderName);
      this.account_no.setText(this.selected_acc_no);
      this.location.setText(this.selected_location);
      localCursor.moveToNext();
    }
  }

  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    getMenuInflater().inflate(2131427334, paramMenu);
    return true;
  }

  public boolean validate()
  {
    if (this.account_no.getText().toString().length() == 0)
    {
      Toast.makeText(this, "Please fill account no.", 0).show();
      return false;
    }
    if (this.holders_name.getText().toString().length() == 0)
    {
      Toast.makeText(this, "Please fill holder name", 0).show();
      return false;
    }
    if (this.banks_name.getText().toString().length() == 0)
    {
      Toast.makeText(this, "Please fill bank name", 0).show();
      return false;
    }
    if (this.location.getText().toString().length() == 0)
    {
      Toast.makeText(this, "Please fill location", 0).show();
      return false;
    }
    return true;
  }
}

/* Location:           C:\Users\Yash\Desktop\apk to src\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.girnar.online_digital_diary.ui.UpdateAccountInfoActivity
 * JD-Core Version:    0.6.0
 */
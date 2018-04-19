package com.girnar.online_digital_diary.ui;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.girnar.online_digital_diary.database.DbHelper;
import com.girnar.online_digital_diary.interfaces.ImportantMethod;
import com.girnar.online_digital_diary.recievers.NotificationReceiver;
import com.girnar.online_digital_diary.util.Util;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Tracker;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PersonalInformationActivity extends Activity
  implements View.OnClickListener, ImportantMethod
{
  static final int DATE_DIALOG_ID = 0;
  static final int DATE_DIALOG_ID1 = 1;
  private static final int SELECT_PICTURE = 1;
  byte[] Image;
  private final String TAG = "PersonalInformationActivity";
  private EditText address;
  private EditText age;
  private boolean ageIsNotCorrect = true;
  private EditText aniversary;
  LinearLayout back;
  private EditText birthday;
  Bitmap bitmap;
  private Calendar c = Calendar.getInstance();
  private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
  {
    public void onDateSet(DatePicker paramDatePicker, int paramInt1, int paramInt2, int paramInt3)
    {
      PersonalInformationActivity.this.mYear = paramInt1;
      PersonalInformationActivity.this.mMonth = paramInt2;
      PersonalInformationActivity.this.mDay = paramInt3;
      PersonalInformationActivity.this.aniversary.setText(new StringBuilder().append(PersonalInformationActivity.this.mDay).append("/").append(1 + PersonalInformationActivity.this.mMonth).append("/").append(PersonalInformationActivity.this.mYear));
    }
  };
  private int day;
  private DbHelper db = new DbHelper(this);
  private EditText email;
  private RadioGroup gender;
  private TextView header_text;
  ImageView home;
  ImageView image;
  private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener()
  {
    public void onDateSet(DatePicker paramDatePicker, int paramInt1, int paramInt2, int paramInt3)
    {
      PersonalInformationActivity.this.mYear = paramInt1;
      PersonalInformationActivity.this.mMonth = paramInt2;
      PersonalInformationActivity.this.mDay = paramInt3;
      PersonalInformationActivity.this.birthday.setText(new StringBuilder().append(PersonalInformationActivity.this.mDay).append("/").append(1 + PersonalInformationActivity.this.mMonth).append("/").append(PersonalInformationActivity.this.mYear));
      String str1 = PersonalInformationActivity.this.mMonth + "-" + PersonalInformationActivity.this.mDay + "-" + PersonalInformationActivity.this.mYear;
      SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("MM-dd-yyyy");
      Date localDate1 = new Date();
      System.out.println(localSimpleDateFormat.format(localDate1));
      String str2 = localSimpleDateFormat.format(localDate1);
      try
      {
        GregorianCalendar localGregorianCalendar1 = new GregorianCalendar();
        GregorianCalendar localGregorianCalendar2 = new GregorianCalendar();
        Date localDate2 = new SimpleDateFormat("MM-dd-yyyy").parse(str1);
        Date localDate3 = new SimpleDateFormat("MM-dd-yyyy").parse(str2);
        localGregorianCalendar1.setTime(localDate2);
        localGregorianCalendar2.setTime(localDate3);
        int i = localGregorianCalendar2.get(6);
        int j = localGregorianCalendar1.get(6);
        int k = 0;
        if (i < j)
          k = -1;
        int m = k + (localGregorianCalendar2.get(1) - localGregorianCalendar1.get(1));
        System.out.println("Your age is: " + m);
        if (m < 0)
        {
          Toast.makeText(PersonalInformationActivity.this, 2131034285, 0).show();
          PersonalInformationActivity.this.ageIsNotCorrect = false;
          return;
        }
        PersonalInformationActivity.this.ageIsNotCorrect = true;
        PersonalInformationActivity.this.age.setText(m);
        return;
      }
      catch (android.net.ParseException localParseException1)
      {
        System.out.println(localParseException1);
        return;
      }
      catch (java.text.ParseException localParseException)
      {
        localParseException.printStackTrace();
      }
    }
  };
  private int mDay;
  private int mMonth;
  private int mYear;
  private EditText mobile_no;
  private int month;
  private EditText name;
  private Button save;
  private String selectedImagePath;
  private TextView textView;
  private Tracker tracker;
  private int year;

  private boolean validateMobileNumber()
  {
    String str = this.mobile_no.getText().toString();
    if (Pattern.compile("^[0-9]{10}$", 2).matcher(str).matches());
    do
      return true;
    while (this.mobile_no.getText().toString().length() == 0);
    return false;
  }

  public void addListener()
  {
    this.save.setOnClickListener(this);
    this.mYear = this.c.get(1);
    this.mMonth = this.c.get(2);
    this.mDay = this.c.get(5);
    this.birthday.setOnClickListener(this);
    this.birthday.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        PersonalInformationActivity.this.showDialog(0);
      }
    });
    this.gender.setOnClickListener(this);
    this.mYear = this.c.get(1);
    this.mMonth = this.c.get(2);
    this.mDay = this.c.get(5);
    this.aniversary.setOnClickListener(this);
    this.aniversary.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        PersonalInformationActivity.this.showDialog(1);
      }
    });
    this.back.setOnClickListener(this);
    this.home.setOnClickListener(this);
    this.image.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        Intent localIntent = new Intent();
        localIntent.setType("image/*");
        localIntent.setAction("android.intent.action.GET_CONTENT");
        localIntent.addCategory("android.intent.category.OPENABLE");
        PersonalInformationActivity.this.startActivityForResult(localIntent, 1);
      }
    });
  }

  public void databaseHelper()
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    if (this.bitmap == null)
      this.bitmap = ((BitmapDrawable)this.image.getDrawable()).getBitmap();
    this.bitmap.compress(Bitmap.CompressFormat.PNG, 100, localByteArrayOutputStream);
    byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
    String str1 = this.name.getText().toString();
    String str2 = this.address.getText().toString();
    String str3 = this.birthday.getText().toString();
    String str4 = this.mobile_no.getText().toString();
    String str5 = this.aniversary.getText().toString();
    String str6 = this.email.getText().toString();
    String str7 = this.age.getText().toString();
    RadioButton localRadioButton = (RadioButton)findViewById(this.gender.getCheckedRadioButtonId());
    if (localRadioButton == null)
      System.out.println("gender is null...");
    String str8 = localRadioButton.getText().toString();
    this.db.open();
    int i = PreferenceManager.getDefaultSharedPreferences(this).getInt("id", 0);
    long l = this.db.insertinfoperson(str1, str2, str3, str4, str5, str6, str7, arrayOfByte, i, str8);
    this.db.close();
    String[] arrayOfString1 = str3.split("/");
    int j = Integer.parseInt(arrayOfString1[0]);
    int k = Integer.parseInt(arrayOfString1[1]);
    setBirthDayReminder(Integer.parseInt(arrayOfString1[2].trim()), k, j, (int)l, this.name.getText().toString());
    if (!str5.equals(""))
    {
      String[] arrayOfString2 = str5.split("/");
      int m = Integer.parseInt(arrayOfString2[0]);
      int n = Integer.parseInt(arrayOfString2[1]);
      setAnivReminder(Integer.parseInt(arrayOfString2[2].trim()), n, m, (int)l, this.name.getText().toString());
    }
  }

  public int getDay()
  {
    return this.day;
  }

  public void getIds()
  {
    this.image = ((ImageView)findViewById(2131492950));
    this.name = ((EditText)findViewById(2131492951));
    this.address = ((EditText)findViewById(2131492953));
    this.birthday = ((EditText)findViewById(2131492955));
    this.mobile_no = ((EditText)findViewById(2131492957));
    this.aniversary = ((EditText)findViewById(2131492959));
    this.email = ((EditText)findViewById(2131492961));
    this.gender = ((RadioGroup)findViewById(2131492965));
    this.age = ((EditText)findViewById(2131492963));
    this.save = ((Button)findViewById(2131492968));
    this.back = ((LinearLayout)findViewById(2131493049));
    this.home = ((ImageView)findViewById(2131493054));
    this.header_text = ((TextView)findViewById(2131493052));
    this.header_text.setText("Friends Information");
  }

  public int getMonth()
  {
    return this.month;
  }

  public String getPath(Uri paramUri)
  {
    Cursor localCursor = managedQuery(paramUri, new String[] { "_data" }, null, null, null);
    int i = localCursor.getColumnIndexOrThrow("_data");
    localCursor.moveToFirst();
    return localCursor.getString(i);
  }

  public String getSelectedImagePath()
  {
    return this.selectedImagePath;
  }

  public TextView getTextView()
  {
    return this.textView;
  }

  public int getYear()
  {
    return this.year;
  }

  // ERROR //
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 4
    //   3: iload_1
    //   4: iconst_1
    //   5: if_icmpne +92 -> 97
    //   8: iload_2
    //   9: iconst_m1
    //   10: if_icmpne +87 -> 97
    //   13: aload_0
    //   14: getfield 197	com/girnar/online_digital_diary/ui/PersonalInformationActivity:image	Landroid/widget/ImageView;
    //   17: iconst_0
    //   18: invokevirtual 390	android/widget/ImageView:setVisibility	(I)V
    //   21: aload_0
    //   22: getfield 206	com/girnar/online_digital_diary/ui/PersonalInformationActivity:bitmap	Landroid/graphics/Bitmap;
    //   25: astore 9
    //   27: aconst_null
    //   28: astore 4
    //   30: aload 9
    //   32: ifnull +10 -> 42
    //   35: aload_0
    //   36: getfield 206	com/girnar/online_digital_diary/ui/PersonalInformationActivity:bitmap	Landroid/graphics/Bitmap;
    //   39: invokevirtual 393	android/graphics/Bitmap:recycle	()V
    //   42: aload_0
    //   43: invokevirtual 397	com/girnar/online_digital_diary/ui/PersonalInformationActivity:getContentResolver	()Landroid/content/ContentResolver;
    //   46: aload_3
    //   47: invokevirtual 403	android/content/Intent:getData	()Landroid/net/Uri;
    //   50: invokevirtual 409	android/content/ContentResolver:openInputStream	(Landroid/net/Uri;)Ljava/io/InputStream;
    //   53: astore 4
    //   55: aload_0
    //   56: aload 4
    //   58: invokestatic 415	android/graphics/BitmapFactory:decodeStream	(Ljava/io/InputStream;)Landroid/graphics/Bitmap;
    //   61: putfield 206	com/girnar/online_digital_diary/ui/PersonalInformationActivity:bitmap	Landroid/graphics/Bitmap;
    //   64: aload_0
    //   65: aload_0
    //   66: aload_3
    //   67: invokevirtual 403	android/content/Intent:getData	()Landroid/net/Uri;
    //   70: invokevirtual 417	com/girnar/online_digital_diary/ui/PersonalInformationActivity:getPath	(Landroid/net/Uri;)Ljava/lang/String;
    //   73: invokevirtual 420	com/girnar/online_digital_diary/ui/PersonalInformationActivity:setSelectedImagePath	(Ljava/lang/String;)V
    //   76: aload_0
    //   77: getfield 197	com/girnar/online_digital_diary/ui/PersonalInformationActivity:image	Landroid/widget/ImageView;
    //   80: aload_0
    //   81: getfield 206	com/girnar/online_digital_diary/ui/PersonalInformationActivity:bitmap	Landroid/graphics/Bitmap;
    //   84: invokevirtual 424	android/widget/ImageView:setImageBitmap	(Landroid/graphics/Bitmap;)V
    //   87: aload 4
    //   89: ifnull +8 -> 97
    //   92: aload 4
    //   94: invokevirtual 427	java/io/InputStream:close	()V
    //   97: return
    //   98: astore 7
    //   100: aload 7
    //   102: invokevirtual 430	java/io/FileNotFoundException:printStackTrace	()V
    //   105: aload 4
    //   107: ifnull -10 -> 97
    //   110: aload 4
    //   112: invokevirtual 427	java/io/InputStream:close	()V
    //   115: return
    //   116: astore 8
    //   118: aload 8
    //   120: invokevirtual 431	java/io/IOException:printStackTrace	()V
    //   123: return
    //   124: astore 5
    //   126: aload 4
    //   128: ifnull +8 -> 136
    //   131: aload 4
    //   133: invokevirtual 427	java/io/InputStream:close	()V
    //   136: aload 5
    //   138: athrow
    //   139: astore 6
    //   141: aload 6
    //   143: invokevirtual 431	java/io/IOException:printStackTrace	()V
    //   146: goto -10 -> 136
    //   149: astore 10
    //   151: aload 10
    //   153: invokevirtual 431	java/io/IOException:printStackTrace	()V
    //   156: return
    //
    // Exception table:
    //   from	to	target	type
    //   21	27	98	java/io/FileNotFoundException
    //   35	42	98	java/io/FileNotFoundException
    //   42	87	98	java/io/FileNotFoundException
    //   110	115	116	java/io/IOException
    //   21	27	124	finally
    //   35	42	124	finally
    //   42	87	124	finally
    //   100	105	124	finally
    //   131	136	139	java/io/IOException
    //   92	97	149	java/io/IOException
  }

  public void onBackPressed()
  {
    startActivity(new Intent(this, FrndListActivity.class).setFlags(67108864));
    overridePendingTransition(2130968578, 2130968579);
    finish();
  }

  public void onClick(View paramView)
  {
    if (paramView == this.save)
      if (validate())
      {
        databaseHelper();
        startActivity(new Intent(this, FrndListActivity.class).setFlags(67108864));
        overridePendingTransition(2130968578, 2130968579);
        finish();
      }
    do
    {
      return;
      if (paramView != this.back)
        continue;
      startActivity(new Intent(this, FrndListActivity.class).setFlags(67108864));
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
    Log.d("PersonalInformationActivity", "on create called...");
    setContentView(2130903050);
    Util.setupUI(findViewById(2131492948), this);
    EasyTracker.getInstance().setContext(getApplicationContext());
    this.tracker = EasyTracker.getTracker();
    this.tracker.trackView("Personal Information Digital_Diary");
    getIds();
    addListener();
    Util.addGoogleAds(this);
  }

  protected Dialog onCreateDialog(int paramInt)
  {
    switch (paramInt)
    {
    default:
      return null;
    case 0:
      return new DatePickerDialog(this, this.mDateSetListener, this.mYear, this.mMonth, this.mDay);
    case 1:
    }
    return new DatePickerDialog(this, this.dateSetListener, this.mYear, this.mMonth, this.mDay);
  }

  public void setAnivReminder(int paramInt1, int paramInt2, int paramInt3, int paramInt4, String paramString)
  {
    NotificationReceiver.setNotificationForBirthDay(this, Util.timeInMills1(paramInt1, paramInt2 - 1, paramInt3, 6, 0), paramString + "'s Anniversary today", paramInt4 * 1000);
  }

  public void setBirthDayReminder(int paramInt1, int paramInt2, int paramInt3, int paramInt4, String paramString)
  {
    NotificationReceiver.setNotificationForBirthDay(this, Util.timeInMills1(paramInt1, paramInt2 - 1, paramInt3, 6, 0), paramString + "'s Birthday today", paramInt4);
  }

  public void setDay(int paramInt)
  {
    this.day = paramInt;
  }

  public void setMonth(int paramInt)
  {
    this.month = paramInt;
  }

  public void setSelectedImagePath(String paramString)
  {
    this.selectedImagePath = paramString;
  }

  public void setTextView(TextView paramTextView)
  {
    this.textView = paramTextView;
  }

  public void setYear(int paramInt)
  {
    this.year = paramInt;
  }

  public boolean validate()
  {
    int i = this.gender.getCheckedRadioButtonId();
    boolean bool = validateMobileNumber();
    String str = this.email.getText().toString();
    Matcher localMatcher = Pattern.compile("^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$", 2).matcher(str);
    if (this.name.getText().toString().length() == 0)
    {
      Toast.makeText(this, "Please enter name", 0).show();
      return false;
    }
    if (this.address.getText().toString().length() == 0)
    {
      Toast.makeText(this, "Please enter address", 0).show();
      return false;
    }
    if (this.birthday.getText().toString().length() == 0)
    {
      Toast.makeText(this, "Plese enter birthday", 0).show();
      return false;
    }
    if (!bool)
    {
      Toast.makeText(this, "Please enter the correct mobile no.", 0).show();
      return false;
    }
    if (this.email.equals(this.email.getText().toString()))
    {
      Toast.makeText(getApplicationContext(), "Invalid Email", 0).show();
      return false;
    }
    if (this.age.getText().toString().length() == 0)
    {
      Toast.makeText(this, "Please enter the Age", 0).show();
      return false;
    }
    if (!this.ageIsNotCorrect)
    {
      Toast.makeText(this, 2131034285, 0).show();
      return false;
    }
    if (!localMatcher.matches())
    {
      Toast.makeText(this, "Please enter valid email", 0).show();
      return false;
    }
    if (i == -1)
    {
      Toast.makeText(this, "Please select gender", 0).show();
      return false;
    }
    return true;
  }
}

/* Location:           C:\Users\Yash\Desktop\apk to src\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.girnar.online_digital_diary.ui.PersonalInformationActivity
 * JD-Core Version:    0.6.0
 */
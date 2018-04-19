package com.girnar.online_digital_diary.ui;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.os.Bundle;
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
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdatePersonActivity extends Activity
  implements ImportantMethod, View.OnClickListener
{
  static final int DATE_DIALOG_ID = 0;
  static final int DATE_DIALOG_ID1 = 1;
  private static final int SELECT_PICTURE = 1;
  private final String TAG = "UpdatePersonActivity";
  private String address;
  private String age;
  long aniv_l;
  private String aniversary;
  LinearLayout back;
  private String birthday;
  Bitmap bitmap;
  private Calendar c = Calendar.getInstance();
  private String[] data;
  private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
  {
    public void onDateSet(DatePicker paramDatePicker, int paramInt1, int paramInt2, int paramInt3)
    {
      UpdatePersonActivity.this.mYear = paramInt1;
      UpdatePersonActivity.this.mMonth = paramInt2;
      UpdatePersonActivity.this.mDay = paramInt3;
      UpdatePersonActivity.this.person_aniversary.setText(new StringBuilder().append(UpdatePersonActivity.this.mDay).append("/").append(1 + UpdatePersonActivity.this.mMonth).append("/").append(UpdatePersonActivity.this.mYear));
    }
  };
  private int day;
  private DbHelper db = new DbHelper(this);
  private String email;
  private String gender;
  private TextView header_text;
  ImageView home;
  private int id;
  ImageView image;
  byte[] image_byteArr;
  long l;
  private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener()
  {
    public void onDateSet(DatePicker paramDatePicker, int paramInt1, int paramInt2, int paramInt3)
    {
      UpdatePersonActivity.this.mYear = paramInt1;
      UpdatePersonActivity.this.mMonth = paramInt2;
      UpdatePersonActivity.this.mDay = paramInt3;
      UpdatePersonActivity.this.person_birthday.setText(new StringBuilder().append(UpdatePersonActivity.this.mDay).append("/").append(1 + UpdatePersonActivity.this.mMonth).append("/").append(UpdatePersonActivity.this.mYear));
    }
  };
  private int mDay;
  private int mMonth;
  private int mYear;
  private String mobile_no;
  private int month;
  private String name;
  private EditText person_address;
  private EditText person_age;
  private EditText person_aniversary;
  private EditText person_birthday;
  private EditText person_email;
  private EditText person_mobile_no;
  private EditText person_name;
  private RadioGroup preson_gender;
  private RadioButton rb_male;
  private RadioButton rd_female;
  private Button save;
  private String selectedImagePath;
  private Tracker tracker;
  private int year;

  private boolean validateMobileNumber()
  {
    String str = this.person_mobile_no.getText().toString();
    if (Pattern.compile("^[0-9]{10}$", 2).matcher(str).matches());
    do
      return true;
    while (this.person_mobile_no.getText().toString().length() == 0);
    return false;
  }

  public void addListener()
  {
    this.person_birthday.setOnClickListener(this);
    this.person_birthday.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        UpdatePersonActivity.this.showDialog(0);
      }
    });
    this.mYear = this.c.get(1);
    this.mMonth = this.c.get(2);
    this.mDay = this.c.get(5);
    this.person_aniversary.setOnClickListener(this);
    this.person_aniversary.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        UpdatePersonActivity.this.showDialog(1);
      }
    });
    this.save.setOnClickListener(this);
    this.back.setOnClickListener(this);
    this.home.setOnClickListener(this);
  }

  public String[] getData()
  {
    return this.data;
  }

  public int getDay()
  {
    return this.day;
  }

  public void getIds()
  {
    this.image = ((ImageView)findViewById(2131492950));
    this.person_name = ((EditText)findViewById(2131492951));
    this.person_address = ((EditText)findViewById(2131492953));
    this.person_birthday = ((EditText)findViewById(2131492955));
    this.person_mobile_no = ((EditText)findViewById(2131492957));
    this.person_aniversary = ((EditText)findViewById(2131492959));
    this.person_email = ((EditText)findViewById(2131492961));
    this.person_age = ((EditText)findViewById(2131492963));
    this.preson_gender = ((RadioGroup)findViewById(2131492965));
    this.save = ((Button)findViewById(2131492968));
    this.back = ((LinearLayout)findViewById(2131493049));
    this.home = ((ImageView)findViewById(2131493054));
    this.header_text = ((TextView)findViewById(2131493052));
    this.header_text.setText("Update Friend Information");
    this.rb_male = ((RadioButton)findViewById(2131492966));
    this.rd_female = ((RadioButton)findViewById(2131492967));
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
    //   14: getfield 212	com/girnar/online_digital_diary/ui/UpdatePersonActivity:image	Landroid/widget/ImageView;
    //   17: iconst_0
    //   18: invokevirtual 294	android/widget/ImageView:setVisibility	(I)V
    //   21: aload_0
    //   22: getfield 296	com/girnar/online_digital_diary/ui/UpdatePersonActivity:bitmap	Landroid/graphics/Bitmap;
    //   25: astore 9
    //   27: aconst_null
    //   28: astore 4
    //   30: aload 9
    //   32: ifnull +10 -> 42
    //   35: aload_0
    //   36: getfield 296	com/girnar/online_digital_diary/ui/UpdatePersonActivity:bitmap	Landroid/graphics/Bitmap;
    //   39: invokevirtual 301	android/graphics/Bitmap:recycle	()V
    //   42: aload_0
    //   43: invokevirtual 305	com/girnar/online_digital_diary/ui/UpdatePersonActivity:getContentResolver	()Landroid/content/ContentResolver;
    //   46: aload_3
    //   47: invokevirtual 310	android/content/Intent:getData	()Landroid/net/Uri;
    //   50: invokevirtual 316	android/content/ContentResolver:openInputStream	(Landroid/net/Uri;)Ljava/io/InputStream;
    //   53: astore 4
    //   55: aload_0
    //   56: aload 4
    //   58: invokestatic 322	android/graphics/BitmapFactory:decodeStream	(Ljava/io/InputStream;)Landroid/graphics/Bitmap;
    //   61: putfield 296	com/girnar/online_digital_diary/ui/UpdatePersonActivity:bitmap	Landroid/graphics/Bitmap;
    //   64: aload_0
    //   65: aload_0
    //   66: aload_3
    //   67: invokevirtual 310	android/content/Intent:getData	()Landroid/net/Uri;
    //   70: invokevirtual 324	com/girnar/online_digital_diary/ui/UpdatePersonActivity:getPath	(Landroid/net/Uri;)Ljava/lang/String;
    //   73: invokevirtual 328	com/girnar/online_digital_diary/ui/UpdatePersonActivity:setSelectedImagePath	(Ljava/lang/String;)V
    //   76: aload_0
    //   77: getfield 212	com/girnar/online_digital_diary/ui/UpdatePersonActivity:image	Landroid/widget/ImageView;
    //   80: aload_0
    //   81: getfield 296	com/girnar/online_digital_diary/ui/UpdatePersonActivity:bitmap	Landroid/graphics/Bitmap;
    //   84: invokevirtual 332	android/widget/ImageView:setImageBitmap	(Landroid/graphics/Bitmap;)V
    //   87: aload 4
    //   89: ifnull +8 -> 97
    //   92: aload 4
    //   94: invokevirtual 337	java/io/InputStream:close	()V
    //   97: return
    //   98: astore 7
    //   100: aload 7
    //   102: invokevirtual 340	java/io/FileNotFoundException:printStackTrace	()V
    //   105: aload 4
    //   107: ifnull -10 -> 97
    //   110: aload 4
    //   112: invokevirtual 337	java/io/InputStream:close	()V
    //   115: return
    //   116: astore 8
    //   118: aload 8
    //   120: invokevirtual 341	java/io/IOException:printStackTrace	()V
    //   123: return
    //   124: astore 5
    //   126: aload 4
    //   128: ifnull +8 -> 136
    //   131: aload 4
    //   133: invokevirtual 337	java/io/InputStream:close	()V
    //   136: aload 5
    //   138: athrow
    //   139: astore 6
    //   141: aload 6
    //   143: invokevirtual 341	java/io/IOException:printStackTrace	()V
    //   146: goto -10 -> 136
    //   149: astore 10
    //   151: aload 10
    //   153: invokevirtual 341	java/io/IOException:printStackTrace	()V
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
    {
      String str1 = this.person_name.getText().toString();
      String str2 = this.person_address.getText().toString();
      String str3 = this.person_birthday.getText().toString();
      String str4 = this.person_mobile_no.getText().toString();
      String str5 = this.person_aniversary.getText().toString();
      String str6 = this.person_email.getText().toString();
      String str7 = this.person_age.getText().toString();
      ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
      this.bitmap.compress(Bitmap.CompressFormat.PNG, 100, localByteArrayOutputStream);
      byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
      RadioButton localRadioButton = (RadioButton)findViewById(this.preson_gender.getCheckedRadioButtonId());
      if (localRadioButton == null)
        System.out.println("gender is null...");
      String str8 = localRadioButton.getText().toString();
      this.db.open();
      long l1 = this.db.UpdateMethod(this.id, str1, str2, str3, str4, str5, str6, str7, arrayOfByte, str8);
      this.db.close();
      NotificationReceiver.cancelBirthdayAlarm(this, this.l, str1 + "'s Birthday today", this.id);
      NotificationReceiver.cancelBirthdayAlarm(this, this.aniv_l, str1 + "'s Anniversary today", 1000 * this.id);
      String[] arrayOfString1 = str3.split("/");
      int i = Integer.parseInt(arrayOfString1[0]);
      int j = Integer.parseInt(arrayOfString1[1]);
      setBirthDayReminder(Integer.parseInt(arrayOfString1[2].trim()), j, i, (int)l1, str1);
      if (!str5.equals(""))
      {
        String[] arrayOfString2 = str5.split("/");
        int k = Integer.parseInt(arrayOfString2[0]);
        int m = Integer.parseInt(arrayOfString2[1]);
        setAnivReminder(Integer.parseInt(arrayOfString2[2].trim()), m, k, (int)l1, str1);
      }
      startActivity(new Intent(this, FrndListActivity.class).setFlags(67108864));
      overridePendingTransition(2130968578, 2130968579);
      finish();
    }
    if (paramView == this.back)
    {
      startActivity(new Intent(this, FrndListActivity.class).setFlags(67108864));
      overridePendingTransition(2130968578, 2130968579);
      finish();
    }
    if (paramView == this.home)
    {
      Util.homeAnimation(this);
      finish();
    }
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Log.d("UpdatePersonActivity", "on create called...");
    setContentView(2130903050);
    Util.setupUI(findViewById(2131492948), this);
    EasyTracker.getInstance().setContext(getApplicationContext());
    this.tracker = EasyTracker.getTracker();
    this.tracker.trackView("Update friend Information Digital_Diary");
    getIds();
    addListener();
    Util.addGoogleAds(this);
    this.db.open();
    this.id = getIntent().getExtras().getInt("id");
    Cursor localCursor = this.db.getInfopersonMethod(this.id);
    this.db.close();
    int i;
    if (localCursor != null)
    {
      setData(new String[localCursor.getCount()]);
      localCursor.moveToFirst();
      i = 0;
      if (i < localCursor.getCount());
    }
    else
    {
      ImageView localImageView = this.image;
      3 local3 = new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          Intent localIntent = new Intent();
          localIntent.setType("image/*");
          localIntent.setAction("android.intent.action.GET_CONTENT");
          localIntent.addCategory("android.intent.category.OPENABLE");
          UpdatePersonActivity.this.startActivityForResult(localIntent, 1);
        }
      };
      localImageView.setOnClickListener(local3);
      return;
    }
    this.name = localCursor.getString(0);
    this.address = localCursor.getString(1);
    this.birthday = localCursor.getString(2);
    this.mobile_no = localCursor.getString(3);
    this.aniversary = localCursor.getString(4);
    this.email = localCursor.getString(5);
    this.age = localCursor.getString(6);
    this.image_byteArr = localCursor.getBlob(7);
    this.gender = localCursor.getString(8);
    this.person_name.setText(this.name);
    this.person_address.setText(this.address);
    this.person_birthday.setText(this.birthday);
    this.person_mobile_no.setText(this.mobile_no);
    this.person_aniversary.setText(this.aniversary);
    this.person_email.setText(this.email);
    this.person_age.setText(this.age);
    if (this.gender.equals("male"))
      this.rb_male.setChecked(true);
    while (true)
    {
      BitmapFactory.Options localOptions = new BitmapFactory.Options();
      this.bitmap = BitmapFactory.decodeByteArray(this.image_byteArr, 0, this.image_byteArr.length, localOptions);
      this.image.setImageBitmap(this.bitmap);
      String[] arrayOfString1 = this.birthday.split("/");
      int j = Integer.parseInt(arrayOfString1[0]);
      int k = Integer.parseInt(arrayOfString1[1]);
      this.l = Util.timeInMills1(Integer.parseInt(arrayOfString1[2].trim()), k - 1, j, 6, 0);
      if (!this.aniversary.equals(""))
      {
        String[] arrayOfString2 = this.aniversary.split("/");
        int m = Integer.parseInt(arrayOfString2[0]);
        int n = Integer.parseInt(arrayOfString2[1]);
        this.aniv_l = Util.timeInMills1(Integer.parseInt(arrayOfString2[2].trim()), n - 1, m, 6, 0);
      }
      localCursor.moveToNext();
      i++;
      break;
      this.rd_female.setChecked(true);
    }
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

  public void setData(String[] paramArrayOfString)
  {
    this.data = paramArrayOfString;
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

  public void setYear(int paramInt)
  {
    this.year = paramInt;
  }

  public boolean validate()
  {
    boolean bool = validateMobileNumber();
    String str = this.person_email.getText().toString();
    Matcher localMatcher = Pattern.compile("^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$", 2).matcher(str);
    if (this.person_name.getText().toString().length() == 0)
    {
      Toast.makeText(this, "Please fill name", 0).show();
      return false;
    }
    if (this.person_address.getText().toString().length() == 0)
    {
      Toast.makeText(this, "Please fill address", 0).show();
      return false;
    }
    if (this.person_birthday.getText().toString().length() == 0)
    {
      Toast.makeText(this, "Plese fill birthday", 0).show();
      return false;
    }
    if (!bool)
    {
      Toast.makeText(this, "Please fill the correct mobile no.", 0).show();
      return false;
    }
    if (this.person_email.equals(this.person_email.getText().toString()))
    {
      Toast.makeText(getApplicationContext(), "Invalid Email", 0).show();
      return false;
    }
    if (this.person_age.getText().toString().length() == 0)
    {
      Toast.makeText(this, "Please fill the Age", 0).show();
      return false;
    }
    if (!localMatcher.matches())
    {
      Toast.makeText(this, "Please fill valid email", 0).show();
      return false;
    }
    return true;
  }
}

/* Location:           C:\Users\Yash\Desktop\apk to src\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.girnar.online_digital_diary.ui.UpdatePersonActivity
 * JD-Core Version:    0.6.0
 */
package com.girnar.online_digital_diary.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper
{
  public static String account_no;
  public static String account_no_id;
  public static String banks_name;
  public static String bookmark_id;
  public static Context context;
  public static String create_table;
  public static String create_table1;
  public static String create_table2;
  public static String create_table3;
  public static String create_table4;
  private static String database_name = "DatabaseOnline";
  private static int database_version = 13;
  public static Helper helper;
  public static String holers_name;
  public static String location;
  public static String login = "LOGIN";
  public static String login_answer;
  public static String login_dob;
  public static String login_emailid;
  public static String login_fname;
  public static String login_gender;
  public static String login_id = "login_id";
  public static String login_lname;
  public static String login_password;
  public static String login_question;
  public static String login_username;
  public static String otherInfo;
  public static String person;
  public static String person_address;
  public static String person_age;
  public static String person_aniversary;
  public static String person_birthday;
  public static String person_bookmark;
  public static String person_description;
  public static String person_email;
  public static String person_id;
  public static String person_image;
  public static String person_mobile_no;
  public static String person_name;
  public static String person_reminder;
  public static String person_reminder_description;
  public static String person_reminder_set_date;
  public static String person_reminder_set_time;
  public static String person_reminder_title;
  public static String person_title;
  public static String reminder_id;
  public static String status;
  public Context context1;
  public SQLiteDatabase db;

  static
  {
    login_fname = "login_fname";
    login_lname = "login_lname";
    login_emailid = "login_emailid";
    login_password = "login_password";
    login_gender = "login_gender";
    login_username = "login_username";
    login_question = "question";
    login_answer = "answer";
    login_dob = "login_dob";
    person = "PERSONALINFORMATION";
    person_image = "person_image";
    person_id = "person_id";
    person_name = "person_name";
    person_address = "person_address";
    person_birthday = "person_birthday";
    person_mobile_no = "person_mobile_no";
    person_aniversary = "person_aniversary";
    person_email = "person_email";
    person_age = "person_age";
    otherInfo = "OTHERINFO";
    account_no_id = "account_no_id";
    holers_name = "holders_name";
    account_no = "account_no";
    banks_name = "banks_name";
    location = "location";
    person_reminder = "REMINDER";
    reminder_id = "reminder_id";
    person_reminder_title = "person_reminder_title";
    person_reminder_description = "person_reminder_description";
    person_reminder_set_date = "person_reminder_set_date";
    person_reminder_set_time = "person_reminder_set_time";
    status = "status";
    person_bookmark = "BOOKMARK";
    bookmark_id = "bookmark_id";
    person_title = "person_title";
    person_description = "person_description";
    create_table = "CREATE TABLE LOGIN(login_id integer primary key autoincrement,login_fname text not null,login_lname text not null,login_username string not null,login_password string not null,question string not null,answer string not null,old_user string not null,login_gender string not null,login_dob string not null);";
    create_table1 = "CREATE TABLE PERSONALINFORMATION(person_id integer primary key autoincrement,person_name text not null,person_address text not null,person_birthday string not null,person_mobile_no string not null,person_aniversary string not null,person_email string not null,person_age string not null,person_image BLOB,user_id integer,gender string not null);";
    create_table2 = "CREATE TABLE OTHERINFO(account_no_id integer primary key autoincrement,account_no text not null,holders_name text not null,banks_name text not null,location text not null,user_id integer);";
    create_table3 = "CREATE TABLE BOOKMARK(bookmark_id integer primary key autoincrement,person_title text not null,person_description text not null,user_id integer);";
    create_table4 = "CREATE TABLE REMINDER(reminder_id integer primary key autoincrement,person_reminder_title text not null,person_reminder_description text not null,person_reminder_set_date text not null,person_reminder_set_time text not null,status text not null,user_id integer );";
  }

  public DbHelper(Context paramContext)
  {
    context = paramContext;
    helper = new Helper(context);
  }

  public void UpdateBankAccountInfo(int paramInt1, String paramString1, String paramString2, String paramString3, String paramString4, int paramInt2)
  {
    ContentValues localContentValues = new ContentValues();
    localContentValues.put(holers_name, paramString1);
    localContentValues.put(account_no, paramString2);
    localContentValues.put(banks_name, paramString3);
    localContentValues.put(location, paramString4);
    localContentValues.put("user_id", Integer.valueOf(paramInt2));
    this.db.update(otherInfo, localContentValues, account_no_id + "=" + paramInt1, null);
  }

  public long UpdateMethod(int paramInt, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, byte[] paramArrayOfByte, String paramString8)
  {
    ContentValues localContentValues = new ContentValues();
    localContentValues.put(person_name, paramString1);
    localContentValues.put(person_address, paramString2);
    localContentValues.put(person_birthday, paramString3);
    localContentValues.put(person_mobile_no, paramString4);
    localContentValues.put(person_aniversary, paramString5);
    localContentValues.put(person_email, paramString6);
    localContentValues.put(person_age, paramString7);
    localContentValues.put(person_image, paramArrayOfByte);
    localContentValues.put("gender", paramString8);
    return this.db.update(person, localContentValues, person_id + "=" + paramInt, null);
  }

  public void UpdateOtherInfo(int paramInt1, String paramString1, String paramString2, int paramInt2)
  {
    ContentValues localContentValues = new ContentValues();
    localContentValues.put(person_title, paramString1);
    localContentValues.put(person_description, paramString2);
    localContentValues.put("user_id", Integer.valueOf(paramInt2));
    this.db.update(person_bookmark, localContentValues, bookmark_id + "=" + paramInt1, null);
  }

  public void UpdatePassword(int paramInt, String paramString)
  {
    ContentValues localContentValues = new ContentValues();
    localContentValues.put(login_password, paramString);
    this.db.update(login, localContentValues, login_id + "=" + paramInt, null);
  }

  public void UpdateSecurityQuestion(String paramString1, String paramString2, int paramInt)
  {
    ContentValues localContentValues = new ContentValues();
    localContentValues.put(login_question, paramString1);
    localContentValues.put(login_answer, paramString2);
    localContentValues.put("old_user", "changed");
    this.db.update(login, localContentValues, login_id + "=" + paramInt, null);
  }

  public Cursor checkLogin(String paramString1, String paramString2)
  {
    SQLiteDatabase localSQLiteDatabase = this.db;
    String str = login;
    String[] arrayOfString = new String[2];
    arrayOfString[0] = login_id;
    arrayOfString[1] = login_fname;
    Cursor localCursor = localSQLiteDatabase.query(true, str, arrayOfString, login_username + " = '" + paramString1 + "' AND " + login_password + " = '" + paramString2 + "'", null, null, null, null, null);
    if (localCursor != null)
      localCursor.moveToFirst();
    return localCursor;
  }

  public Cursor checkOldUser(String paramString)
  {
    Cursor localCursor = this.db.query(true, login, new String[] { "old_user" }, login_username + " = '" + paramString + "'", null, null, null, null, null);
    if (localCursor != null)
      localCursor.moveToFirst();
    return localCursor;
  }

  public Cursor checkTest(String paramString)
  {
    Cursor localCursor = this.db.query(true, login, new String[] { "test" }, null, null, null, null, null, null);
    if ((localCursor != null) && (localCursor.getCount() > 0))
      localCursor.moveToFirst();
    return localCursor;
  }

  public Cursor checkUserName(String paramString)
  {
    SQLiteDatabase localSQLiteDatabase = this.db;
    String str = login;
    String[] arrayOfString = new String[1];
    arrayOfString[0] = login_id;
    Cursor localCursor = localSQLiteDatabase.query(true, str, arrayOfString, login_username + " = '" + paramString + "'", null, null, null, null, null);
    if ((localCursor != null) && (localCursor.getCount() > 0))
      localCursor.moveToFirst();
    return localCursor;
  }

  public void close()
  {
    helper.close();
  }

  public void deleteMethod(int paramInt, String paramString1, String paramString2)
  {
    this.db.delete(paramString1, paramString2 + "=" + paramInt, null);
  }

  public Cursor getDateOfPerson()
  {
    SQLiteDatabase localSQLiteDatabase = this.db;
    String str = person;
    String[] arrayOfString = new String[3];
    arrayOfString[0] = person_id;
    arrayOfString[1] = person_birthday;
    arrayOfString[2] = person_aniversary;
    Cursor localCursor = localSQLiteDatabase.query(true, str, arrayOfString, null, null, null, null, null, null);
    if (localCursor != null)
      localCursor.moveToFirst();
    return localCursor;
  }

  public Cursor getInfoBookmark(int paramInt)
  {
    SQLiteDatabase localSQLiteDatabase = this.db;
    String str = person_bookmark;
    String[] arrayOfString = new String[3];
    arrayOfString[0] = bookmark_id;
    arrayOfString[1] = person_title;
    arrayOfString[2] = person_description;
    Cursor localCursor = localSQLiteDatabase.query(true, str, arrayOfString, "user_id = " + paramInt, null, null, null, null, null);
    if (localCursor != null)
      localCursor.moveToFirst();
    return localCursor;
  }

  public Cursor getInfoBookmarkMethod(int paramInt)
  {
    SQLiteDatabase localSQLiteDatabase = this.db;
    String str = person_bookmark;
    String[] arrayOfString = new String[2];
    arrayOfString[0] = person_title;
    arrayOfString[1] = person_description;
    Cursor localCursor = localSQLiteDatabase.query(true, str, arrayOfString, bookmark_id + "=" + paramInt, null, null, null, null, null);
    if (localCursor != null)
      localCursor.moveToFirst();
    return localCursor;
  }

  public Cursor getInfoOther(int paramInt)
  {
    SQLiteDatabase localSQLiteDatabase = this.db;
    String str = otherInfo;
    String[] arrayOfString = new String[5];
    arrayOfString[0] = account_no_id;
    arrayOfString[1] = holers_name;
    arrayOfString[2] = account_no;
    arrayOfString[3] = banks_name;
    arrayOfString[4] = location;
    Cursor localCursor = localSQLiteDatabase.query(true, str, arrayOfString, "user_id = " + paramInt, null, null, null, null, null);
    if (localCursor != null)
      localCursor.moveToFirst();
    return localCursor;
  }

  public Cursor getInfoOtherMethod(int paramInt)
  {
    SQLiteDatabase localSQLiteDatabase = this.db;
    String str = otherInfo;
    String[] arrayOfString = new String[4];
    arrayOfString[0] = holers_name;
    arrayOfString[1] = account_no;
    arrayOfString[2] = banks_name;
    arrayOfString[3] = location;
    Cursor localCursor = localSQLiteDatabase.query(true, str, arrayOfString, account_no_id + "=" + paramInt, null, null, null, null, null);
    if (localCursor != null)
      localCursor.moveToFirst();
    return localCursor;
  }

  public Cursor getInfoReminder(int paramInt)
  {
    SQLiteDatabase localSQLiteDatabase = this.db;
    String str = person_reminder;
    String[] arrayOfString = new String[5];
    arrayOfString[0] = reminder_id;
    arrayOfString[1] = person_reminder_title;
    arrayOfString[2] = person_reminder_set_date;
    arrayOfString[3] = person_reminder_set_time;
    arrayOfString[4] = person_reminder_description;
    Cursor localCursor = localSQLiteDatabase.query(true, str, arrayOfString, "user_id = " + paramInt, null, null, null, null, null);
    if (localCursor != null)
      localCursor.moveToFirst();
    return localCursor;
  }

  public Cursor getInfoReminderMethod(int paramInt)
  {
    SQLiteDatabase localSQLiteDatabase = this.db;
    String str = person_reminder;
    String[] arrayOfString = new String[4];
    arrayOfString[0] = person_reminder_title;
    arrayOfString[1] = person_reminder_description;
    arrayOfString[2] = person_reminder_set_date;
    arrayOfString[3] = person_reminder_set_time;
    Cursor localCursor = localSQLiteDatabase.query(true, str, arrayOfString, reminder_id + "=" + paramInt, null, null, null, null, null);
    if (localCursor != null)
      localCursor.moveToFirst();
    return localCursor;
  }

  public Cursor getInfoperson(int paramInt)
  {
    SQLiteDatabase localSQLiteDatabase = this.db;
    String str = person;
    String[] arrayOfString = new String[10];
    arrayOfString[0] = person_id;
    arrayOfString[1] = person_name;
    arrayOfString[2] = person_address;
    arrayOfString[3] = person_birthday;
    arrayOfString[4] = person_mobile_no;
    arrayOfString[5] = person_aniversary;
    arrayOfString[6] = person_email;
    arrayOfString[7] = person_age;
    arrayOfString[8] = person_image;
    arrayOfString[9] = "gender";
    Cursor localCursor = localSQLiteDatabase.query(true, str, arrayOfString, "user_id = " + paramInt, null, null, null, null, null);
    if (localCursor != null)
      localCursor.moveToFirst();
    return localCursor;
  }

  public Cursor getInfopersonMethod(int paramInt)
  {
    SQLiteDatabase localSQLiteDatabase = this.db;
    String str = person;
    String[] arrayOfString = new String[9];
    arrayOfString[0] = person_name;
    arrayOfString[1] = person_address;
    arrayOfString[2] = person_birthday;
    arrayOfString[3] = person_mobile_no;
    arrayOfString[4] = person_aniversary;
    arrayOfString[5] = person_email;
    arrayOfString[6] = person_age;
    arrayOfString[7] = person_image;
    arrayOfString[8] = "gender";
    Cursor localCursor = localSQLiteDatabase.query(true, str, arrayOfString, person_id + "=" + paramInt, null, null, null, null, null);
    if (localCursor != null)
      localCursor.moveToFirst();
    return localCursor;
  }

  public Cursor getName(int paramInt)
  {
    SQLiteDatabase localSQLiteDatabase = this.db;
    String str = login;
    String[] arrayOfString = new String[1];
    arrayOfString[0] = login_fname;
    Cursor localCursor = localSQLiteDatabase.query(true, str, arrayOfString, login_id + " = " + paramInt, null, null, null, null, null);
    if (localCursor != null)
      localCursor.moveToFirst();
    return localCursor;
  }

  public Cursor getQuestion(String paramString)
  {
    SQLiteDatabase localSQLiteDatabase = this.db;
    String str = login;
    String[] arrayOfString = new String[3];
    arrayOfString[0] = login_question;
    arrayOfString[1] = login_answer;
    arrayOfString[2] = login_id;
    Cursor localCursor = localSQLiteDatabase.query(true, str, arrayOfString, login_username + " = '" + paramString + "'", null, null, null, null, null);
    if (localCursor != null)
      localCursor.moveToFirst();
    return localCursor;
  }

  public Cursor getReminderMethodInfo(int paramInt)
  {
    SQLiteDatabase localSQLiteDatabase = this.db;
    String str = person_reminder;
    String[] arrayOfString = new String[3];
    arrayOfString[0] = reminder_id;
    arrayOfString[1] = person_reminder_set_date;
    arrayOfString[2] = person_reminder_set_time;
    Cursor localCursor = localSQLiteDatabase.query(true, str, arrayOfString, null, null, null, null, null, null);
    if (localCursor != null)
      localCursor.moveToFirst();
    return localCursor;
  }

  public long insertinfo(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    ContentValues localContentValues = new ContentValues();
    localContentValues.put(login_fname, paramString1);
    localContentValues.put(login_lname, "");
    localContentValues.put(login_username, paramString2);
    localContentValues.put(login_password, paramString3);
    localContentValues.put(login_gender, "");
    localContentValues.put(login_dob, "");
    localContentValues.put(login_question, paramString4);
    localContentValues.put(login_answer, paramString5);
    localContentValues.put("old_user", "new");
    return this.db.insert(login, null, localContentValues);
  }

  public long insertinfoBookmark(String paramString1, String paramString2, int paramInt)
  {
    ContentValues localContentValues = new ContentValues();
    localContentValues.put(person_title, paramString1);
    localContentValues.put(person_description, paramString2);
    localContentValues.put("user_id", Integer.valueOf(paramInt));
    return this.db.insert(person_bookmark, null, localContentValues);
  }

  public long insertinfoOther(String paramString1, String paramString2, String paramString3, String paramString4, int paramInt)
  {
    ContentValues localContentValues = new ContentValues();
    localContentValues.put(holers_name, paramString1);
    localContentValues.put(account_no, paramString2);
    localContentValues.put(banks_name, paramString3);
    localContentValues.put(location, paramString4);
    localContentValues.put("user_id", Integer.valueOf(paramInt));
    return this.db.insert(otherInfo, null, localContentValues);
  }

  public long insertinfoReminder(String paramString1, String paramString2, String paramString3, String paramString4, int paramInt)
  {
    ContentValues localContentValues = new ContentValues();
    localContentValues.put(person_reminder_title, paramString1);
    localContentValues.put(person_reminder_description, paramString2);
    localContentValues.put(person_reminder_set_date, paramString3);
    localContentValues.put(person_reminder_set_time, paramString4);
    localContentValues.put(status, "pending...");
    localContentValues.put("user_id", Integer.valueOf(paramInt));
    return this.db.insert(person_reminder, null, localContentValues);
  }

  public long insertinfoperson(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, byte[] paramArrayOfByte, int paramInt, String paramString8)
  {
    ContentValues localContentValues = new ContentValues();
    localContentValues.put(person_name, paramString1);
    localContentValues.put(person_address, paramString2);
    localContentValues.put(person_birthday, paramString3);
    localContentValues.put(person_mobile_no, paramString4);
    localContentValues.put(person_aniversary, paramString5);
    localContentValues.put(person_email, paramString6);
    localContentValues.put(person_age, paramString7);
    localContentValues.put(person_image, paramArrayOfByte);
    localContentValues.put("user_id", Integer.valueOf(paramInt));
    localContentValues.put("gender", paramString8);
    return this.db.insert(person, null, localContentValues);
  }

  public DbHelper open()
    throws SQLException
  {
    this.db = helper.getWritableDatabase();
    return this;
  }

  public Cursor searchInfoBookmark(String paramString, int paramInt)
  {
    SQLiteDatabase localSQLiteDatabase = this.db;
    String str = person_bookmark;
    String[] arrayOfString = new String[3];
    arrayOfString[0] = bookmark_id;
    arrayOfString[1] = person_title;
    arrayOfString[2] = person_description;
    Cursor localCursor = localSQLiteDatabase.query(true, str, arrayOfString, person_title + " LIKE '%" + paramString + "%' AND " + "user_id = " + paramInt, null, null, null, null, null);
    if (localCursor != null)
      localCursor.moveToFirst();
    return localCursor;
  }

  public Cursor searchInfoOther(String paramString, int paramInt)
  {
    SQLiteDatabase localSQLiteDatabase = this.db;
    String str = otherInfo;
    String[] arrayOfString = new String[5];
    arrayOfString[0] = account_no_id;
    arrayOfString[1] = holers_name;
    arrayOfString[2] = account_no;
    arrayOfString[3] = banks_name;
    arrayOfString[4] = location;
    Cursor localCursor = localSQLiteDatabase.query(true, str, arrayOfString, holers_name + " LIKE '%" + paramString + "%' AND " + "user_id = " + paramInt, null, null, null, null, null);
    if (localCursor != null)
      localCursor.moveToFirst();
    return localCursor;
  }

  public Cursor searchInfoReminder(String paramString)
  {
    SQLiteDatabase localSQLiteDatabase = this.db;
    String str = person_reminder;
    String[] arrayOfString = new String[5];
    arrayOfString[0] = reminder_id;
    arrayOfString[1] = person_reminder_title;
    arrayOfString[2] = person_reminder_set_date;
    arrayOfString[3] = person_reminder_set_time;
    arrayOfString[4] = person_reminder_description;
    Cursor localCursor = localSQLiteDatabase.query(true, str, arrayOfString, person_reminder_title + " LIKE '%" + paramString + "%'", null, null, null, null, null);
    if (localCursor != null)
      localCursor.moveToFirst();
    return localCursor;
  }

  public Cursor searchInfopersonMethod(String paramString, int paramInt)
  {
    SQLiteDatabase localSQLiteDatabase = this.db;
    String str = person;
    String[] arrayOfString = new String[10];
    arrayOfString[0] = person_id;
    arrayOfString[1] = person_name;
    arrayOfString[2] = person_address;
    arrayOfString[3] = person_birthday;
    arrayOfString[4] = person_mobile_no;
    arrayOfString[5] = person_aniversary;
    arrayOfString[6] = person_email;
    arrayOfString[7] = person_age;
    arrayOfString[8] = person_image;
    arrayOfString[9] = "gender";
    Cursor localCursor = localSQLiteDatabase.query(true, str, arrayOfString, person_name + " LIKE '%" + paramString + "%' AND " + "user_id = " + paramInt, null, null, null, null, null);
    if (localCursor != null)
      localCursor.moveToFirst();
    return localCursor;
  }

  public long updateinfoReminder(int paramInt1, String paramString1, String paramString2, String paramString3, String paramString4, int paramInt2)
  {
    ContentValues localContentValues = new ContentValues();
    localContentValues.put(person_reminder_title, paramString1);
    localContentValues.put(person_reminder_description, paramString2);
    localContentValues.put(person_reminder_set_date, paramString3);
    localContentValues.put(person_reminder_set_time, paramString4);
    localContentValues.put(status, "pending...");
    localContentValues.put("user_id", Integer.valueOf(paramInt1));
    return this.db.update(person_reminder, localContentValues, reminder_id + " = " + paramInt2, null);
  }

  public static class Helper extends SQLiteOpenHelper
  {
    public Helper(Context paramContext)
    {
      super(DbHelper.database_name, null, DbHelper.database_version);
    }

    public void onCreate(SQLiteDatabase paramSQLiteDatabase)
    {
      paramSQLiteDatabase.execSQL(DbHelper.create_table);
      paramSQLiteDatabase.execSQL(DbHelper.create_table1);
      paramSQLiteDatabase.execSQL(DbHelper.create_table2);
      paramSQLiteDatabase.execSQL(DbHelper.create_table3);
      paramSQLiteDatabase.execSQL(DbHelper.create_table4);
    }

    public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
    {
      paramSQLiteDatabase.execSQL("ALTER TABLE LOGIN ADD COLUMN question string not null DEFAULT test");
      paramSQLiteDatabase.execSQL("ALTER TABLE LOGIN ADD COLUMN answer string not null DEFAULT test");
      paramSQLiteDatabase.execSQL("ALTER TABLE LOGIN ADD COLUMN old_user string not null DEFAULT old");
      paramSQLiteDatabase.execSQL("ALTER TABLE PERSONALINFORMATION ADD COLUMN gender string not null DEFAULT 'not mensioned'");
    }
  }
}

/* Location:           C:\Users\Yash\Desktop\apk to src\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.girnar.online_digital_diary.database.DbHelper
 * JD-Core Version:    0.6.0
 */
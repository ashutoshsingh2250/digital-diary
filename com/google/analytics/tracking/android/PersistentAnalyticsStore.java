package com.google.analytics.tracking.android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.google.android.gms.analytics.internal.Command;
import com.google.android.gms.common.util.VisibleForTesting;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

class PersistentAnalyticsStore
  implements AnalyticsStore
{

  @VisibleForTesting
  static final String BACKEND_LIBRARY_VERSION = "";
  private static final String CREATE_HITS_TABLE = String.format("CREATE TABLE IF NOT EXISTS %s ( '%s' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '%s' INTEGER NOT NULL, '%s' TEXT NOT NULL, '%s' TEXT NOT NULL, '%s' INTEGER);", new Object[] { "hits2", "hit_id", "hit_time", "hit_url", "hit_string", "hit_app_id" });
  private static final String DATABASE_FILENAME = "google_analytics_v2.db";

  @VisibleForTesting
  static final String HITS_TABLE = "hits2";

  @VisibleForTesting
  static final String HIT_APP_ID = "hit_app_id";

  @VisibleForTesting
  static final String HIT_ID = "hit_id";

  @VisibleForTesting
  static final String HIT_STRING = "hit_string";

  @VisibleForTesting
  static final String HIT_TIME = "hit_time";

  @VisibleForTesting
  static final String HIT_URL = "hit_url";
  private Clock mClock;
  private final Context mContext;
  private final String mDatabaseName;
  private final AnalyticsDatabaseHelper mDbHelper;
  private volatile Dispatcher mDispatcher;
  private long mLastDeleteStaleHitsTime;
  private final AnalyticsStoreStateListener mListener;

  PersistentAnalyticsStore(AnalyticsStoreStateListener paramAnalyticsStoreStateListener, Context paramContext)
  {
    this(paramAnalyticsStoreStateListener, paramContext, "google_analytics_v2.db");
  }

  @VisibleForTesting
  PersistentAnalyticsStore(AnalyticsStoreStateListener paramAnalyticsStoreStateListener, Context paramContext, String paramString)
  {
    this.mContext = paramContext.getApplicationContext();
    this.mDatabaseName = paramString;
    this.mListener = paramAnalyticsStoreStateListener;
    this.mClock = new Clock()
    {
      public long currentTimeMillis()
      {
        return System.currentTimeMillis();
      }
    };
    this.mDbHelper = new AnalyticsDatabaseHelper(this.mContext, this.mDatabaseName);
    this.mDispatcher = new SimpleNetworkDispatcher(this, createDefaultHttpClientFactory(), this.mContext);
    this.mLastDeleteStaleHitsTime = 0L;
  }

  private HttpClientFactory createDefaultHttpClientFactory()
  {
    return new HttpClientFactory()
    {
      public HttpClient newInstance()
      {
        return new DefaultHttpClient();
      }
    };
  }

  private void fillVersionParametersIfNecessary(Map<String, String> paramMap, Collection<Command> paramCollection)
  {
    Iterator localIterator = paramCollection.iterator();
    while (localIterator.hasNext())
    {
      Command localCommand = (Command)localIterator.next();
      if (!localCommand.getId().equals("appendVersion"))
        continue;
      String str = localCommand.getValue();
      storeVersion(paramMap, localCommand.getUrlParam(), str);
    }
  }

  public static String generateHitString(Map<String, String> paramMap)
  {
    ArrayList localArrayList = new ArrayList(paramMap.size());
    Iterator localIterator = paramMap.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      localArrayList.add((String)localEntry.getKey() + "=" + HitBuilder.encode((String)localEntry.getValue()));
    }
    return TextUtils.join("&", localArrayList);
  }

  private SQLiteDatabase getWritableDatabase(String paramString)
  {
    try
    {
      SQLiteDatabase localSQLiteDatabase = this.mDbHelper.getWritableDatabase();
      return localSQLiteDatabase;
    }
    catch (SQLiteException localSQLiteException)
    {
      Log.w(paramString);
    }
    return null;
  }

  private void removeOldHitIfFull()
  {
    int i = 1 + (-2000 + getNumStoredHits());
    if (i > 0)
    {
      List localList = peekHits(i);
      Log.wDebug("Store full, deleting " + localList.size() + " hits to make room");
      deleteHits(localList);
    }
  }

  private void storeVersion(Map<String, String> paramMap, String paramString1, String paramString2)
  {
    if (paramString2 == null);
    for (String str = ""; ; str = paramString2 + "")
    {
      if (paramString1 != null)
        paramMap.put(paramString1, str);
      return;
    }
  }

  private void writeHitToDatabase(Map<String, String> paramMap, long paramLong, String paramString)
  {
    SQLiteDatabase localSQLiteDatabase = getWritableDatabase("Error opening database for putHit");
    if (localSQLiteDatabase == null)
      return;
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("hit_string", generateHitString(paramMap));
    localContentValues.put("hit_time", Long.valueOf(paramLong));
    long l1 = 0L;
    if (paramMap.containsKey("AppUID"));
    try
    {
      long l2 = Long.parseLong((String)paramMap.get("AppUID"));
      l1 = l2;
      label82: localContentValues.put("hit_app_id", Long.valueOf(l1));
      if (paramString == null)
        paramString = "http://www.google-analytics.com/collect";
      if (paramString.length() == 0)
      {
        Log.w("empty path: not sending hit");
        return;
      }
      localContentValues.put("hit_url", paramString);
      try
      {
        localSQLiteDatabase.insert("hits2", null, localContentValues);
        this.mListener.reportStoreIsEmpty(false);
        return;
      }
      catch (SQLiteException localSQLiteException)
      {
        Log.w("Error storing hit");
        return;
      }
    }
    catch (NumberFormatException localNumberFormatException)
    {
      break label82;
    }
  }

  public void clearHits(long paramLong)
  {
    boolean bool = true;
    SQLiteDatabase localSQLiteDatabase = getWritableDatabase("Error opening database for clearHits");
    AnalyticsStoreStateListener localAnalyticsStoreStateListener;
    if (localSQLiteDatabase != null)
    {
      if (paramLong != 0L)
        break label54;
      localSQLiteDatabase.delete("hits2", null, null);
      localAnalyticsStoreStateListener = this.mListener;
      if (getNumStoredHits() != 0)
        break label87;
    }
    while (true)
    {
      localAnalyticsStoreStateListener.reportStoreIsEmpty(bool);
      return;
      label54: String[] arrayOfString = new String[bool];
      arrayOfString[0] = Long.valueOf(paramLong).toString();
      localSQLiteDatabase.delete("hits2", "hit_app_id = ?", arrayOfString);
      break;
      label87: bool = false;
    }
  }

  public void close()
  {
    try
    {
      this.mDbHelper.getWritableDatabase().close();
      return;
    }
    catch (SQLiteException localSQLiteException)
    {
      Log.w("Error opening database for close");
    }
  }

  public void deleteHits(Collection<Hit> paramCollection)
  {
    if (paramCollection == null)
      throw new NullPointerException("hits cannot be null");
    if (paramCollection.isEmpty());
    SQLiteDatabase localSQLiteDatabase;
    do
    {
      return;
      localSQLiteDatabase = getWritableDatabase("Error opening database for deleteHit");
    }
    while (localSQLiteDatabase == null);
    String[] arrayOfString = new String[paramCollection.size()];
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = TextUtils.join(",", Collections.nCopies(arrayOfString.length, "?"));
    String str = String.format("HIT_ID in (%s)", arrayOfObject);
    int i = 0;
    Iterator localIterator = paramCollection.iterator();
    while (localIterator.hasNext())
    {
      Hit localHit = (Hit)localIterator.next();
      int j = i + 1;
      arrayOfString[i] = Long.toString(localHit.getHitId());
      i = j;
    }
    while (true)
    {
      try
      {
        localSQLiteDatabase.delete("hits2", str, arrayOfString);
        AnalyticsStoreStateListener localAnalyticsStoreStateListener = this.mListener;
        if (getNumStoredHits() == 0)
        {
          bool = true;
          localAnalyticsStoreStateListener.reportStoreIsEmpty(bool);
          return;
        }
      }
      catch (SQLiteException localSQLiteException)
      {
        Log.w("Error deleting hit " + paramCollection);
        return;
      }
      boolean bool = false;
    }
  }

  int deleteStaleHits()
  {
    boolean bool = true;
    long l1 = this.mClock.currentTimeMillis();
    if (l1 <= 86400000L + this.mLastDeleteStaleHitsTime);
    SQLiteDatabase localSQLiteDatabase;
    do
    {
      return 0;
      this.mLastDeleteStaleHitsTime = l1;
      localSQLiteDatabase = getWritableDatabase("Error opening database for deleteStaleHits");
    }
    while (localSQLiteDatabase == null);
    long l2 = this.mClock.currentTimeMillis() - 2592000000L;
    String[] arrayOfString = new String[bool];
    arrayOfString[0] = Long.toString(l2);
    int i = localSQLiteDatabase.delete("hits2", "HIT_TIME < ?", arrayOfString);
    AnalyticsStoreStateListener localAnalyticsStoreStateListener = this.mListener;
    if (getNumStoredHits() == 0);
    while (true)
    {
      localAnalyticsStoreStateListener.reportStoreIsEmpty(bool);
      return i;
      bool = false;
    }
  }

  public void dispatch()
  {
    Log.vDebug("dispatch running...");
    if (!this.mDispatcher.okToDispatch());
    List localList;
    int i;
    do
    {
      return;
      localList = peekHits(40);
      if (localList.isEmpty())
      {
        Log.vDebug("...nothing to dispatch");
        this.mListener.reportStoreIsEmpty(true);
        return;
      }
      i = this.mDispatcher.dispatchHits(localList);
      Log.vDebug("sent " + i + " of " + localList.size() + " hits");
      deleteHits(localList.subList(0, Math.min(i, localList.size())));
    }
    while ((i != localList.size()) || (getNumStoredHits() <= 0));
    GAServiceManager.getInstance().dispatch();
  }

  @VisibleForTesting
  public AnalyticsDatabaseHelper getDbHelper()
  {
    return this.mDbHelper;
  }

  @VisibleForTesting
  AnalyticsDatabaseHelper getHelper()
  {
    return this.mDbHelper;
  }

  int getNumStoredHits()
  {
    SQLiteDatabase localSQLiteDatabase = getWritableDatabase("Error opening database for requestNumHitsPending");
    if (localSQLiteDatabase == null)
      return 0;
    Cursor localCursor = null;
    try
    {
      localCursor = localSQLiteDatabase.rawQuery("SELECT COUNT(*) from hits2", null);
      boolean bool = localCursor.moveToFirst();
      i = 0;
      if (bool)
      {
        long l = localCursor.getLong(0);
        i = (int)l;
      }
      return i;
    }
    catch (SQLiteException localSQLiteException)
    {
      while (true)
      {
        Log.w("Error getting numStoredHits");
        int i = 0;
        if (localCursor == null)
          continue;
        localCursor.close();
        i = 0;
      }
    }
    finally
    {
      if (localCursor != null)
        localCursor.close();
    }
    throw localObject;
  }

  // ERROR //
  public List<Hit> peekHits(int paramInt)
  {
    // Byte code:
    //   0: aload_0
    //   1: ldc_w 447
    //   4: invokespecial 260	com/google/analytics/tracking/android/PersistentAnalyticsStore:getWritableDatabase	(Ljava/lang/String;)Landroid/database/sqlite/SQLiteDatabase;
    //   7: astore_2
    //   8: aload_2
    //   9: ifnonnull +13 -> 22
    //   12: new 158	java/util/ArrayList
    //   15: dup
    //   16: invokespecial 448	java/util/ArrayList:<init>	()V
    //   19: astore_3
    //   20: aload_3
    //   21: areturn
    //   22: aconst_null
    //   23: astore 4
    //   25: new 158	java/util/ArrayList
    //   28: dup
    //   29: invokespecial 448	java/util/ArrayList:<init>	()V
    //   32: pop
    //   33: aload_2
    //   34: ldc 18
    //   36: iconst_3
    //   37: anewarray 52	java/lang/String
    //   40: dup
    //   41: iconst_0
    //   42: ldc 24
    //   44: aastore
    //   45: dup
    //   46: iconst_1
    //   47: ldc 30
    //   49: aastore
    //   50: dup
    //   51: iconst_2
    //   52: ldc 33
    //   54: aastore
    //   55: aconst_null
    //   56: aconst_null
    //   57: aconst_null
    //   58: aconst_null
    //   59: ldc_w 450
    //   62: iconst_2
    //   63: anewarray 4	java/lang/Object
    //   66: dup
    //   67: iconst_0
    //   68: ldc 33
    //   70: aastore
    //   71: dup
    //   72: iconst_1
    //   73: ldc 24
    //   75: aastore
    //   76: invokestatic 56	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   79: iload_1
    //   80: invokestatic 455	java/lang/Integer:toString	(I)Ljava/lang/String;
    //   83: invokevirtual 459	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   86: astore 4
    //   88: new 158	java/util/ArrayList
    //   91: dup
    //   92: invokespecial 448	java/util/ArrayList:<init>	()V
    //   95: astore_3
    //   96: aload 4
    //   98: invokeinterface 438 1 0
    //   103: ifeq +65 -> 168
    //   106: new 354	com/google/analytics/tracking/android/Hit
    //   109: dup
    //   110: aconst_null
    //   111: aload 4
    //   113: iconst_0
    //   114: invokeinterface 442 2 0
    //   119: aload 4
    //   121: iconst_1
    //   122: invokeinterface 442 2 0
    //   127: invokespecial 462	com/google/analytics/tracking/android/Hit:<init>	(Ljava/lang/String;JJ)V
    //   130: astore 11
    //   132: aload 11
    //   134: aload 4
    //   136: iconst_2
    //   137: invokeinterface 465 2 0
    //   142: invokevirtual 468	com/google/analytics/tracking/android/Hit:setHitUrl	(Ljava/lang/String;)V
    //   145: aload_3
    //   146: aload 11
    //   148: invokeinterface 204 2 0
    //   153: pop
    //   154: aload 4
    //   156: invokeinterface 471 1 0
    //   161: istore 13
    //   163: iload 13
    //   165: ifne -59 -> 106
    //   168: aload 4
    //   170: ifnull +10 -> 180
    //   173: aload 4
    //   175: invokeinterface 443 1 0
    //   180: aload_2
    //   181: ldc 18
    //   183: iconst_2
    //   184: anewarray 52	java/lang/String
    //   187: dup
    //   188: iconst_0
    //   189: ldc 24
    //   191: aastore
    //   192: dup
    //   193: iconst_1
    //   194: ldc 27
    //   196: aastore
    //   197: aconst_null
    //   198: aconst_null
    //   199: aconst_null
    //   200: aconst_null
    //   201: ldc_w 473
    //   204: iconst_1
    //   205: anewarray 4	java/lang/Object
    //   208: dup
    //   209: iconst_0
    //   210: ldc 24
    //   212: aastore
    //   213: invokestatic 56	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   216: iload_1
    //   217: invokestatic 455	java/lang/Integer:toString	(I)Ljava/lang/String;
    //   220: invokevirtual 459	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   223: astore 4
    //   225: aload 4
    //   227: invokeinterface 438 1 0
    //   232: istore 23
    //   234: iconst_0
    //   235: istore 24
    //   237: iload 23
    //   239: ifeq +64 -> 303
    //   242: aload 4
    //   244: instanceof 475
    //   247: ifeq +292 -> 539
    //   250: aload 4
    //   252: checkcast 475	android/database/sqlite/SQLiteCursor
    //   255: invokevirtual 479	android/database/sqlite/SQLiteCursor:getWindow	()Landroid/database/CursorWindow;
    //   258: invokevirtual 484	android/database/CursorWindow:getNumRows	()I
    //   261: ifle +125 -> 386
    //   264: aload_3
    //   265: iload 24
    //   267: invokeinterface 487 2 0
    //   272: checkcast 354	com/google/analytics/tracking/android/Hit
    //   275: aload 4
    //   277: iconst_1
    //   278: invokeinterface 465 2 0
    //   283: invokevirtual 490	com/google/analytics/tracking/android/Hit:setHitString	(Ljava/lang/String;)V
    //   286: iinc 24 1
    //   289: aload 4
    //   291: invokeinterface 471 1 0
    //   296: istore 25
    //   298: iload 25
    //   300: ifne -58 -> 242
    //   303: aload 4
    //   305: ifnull -285 -> 20
    //   308: aload 4
    //   310: invokeinterface 443 1 0
    //   315: aload_3
    //   316: areturn
    //   317: astore 7
    //   319: new 178	java/lang/StringBuilder
    //   322: dup
    //   323: invokespecial 179	java/lang/StringBuilder:<init>	()V
    //   326: ldc_w 492
    //   329: invokevirtual 186	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   332: aload 7
    //   334: invokevirtual 495	android/database/sqlite/SQLiteException:getMessage	()Ljava/lang/String;
    //   337: invokevirtual 186	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   340: invokevirtual 199	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   343: invokestatic 225	com/google/analytics/tracking/android/Log:w	(Ljava/lang/String;)I
    //   346: pop
    //   347: new 158	java/util/ArrayList
    //   350: dup
    //   351: invokespecial 448	java/util/ArrayList:<init>	()V
    //   354: astore_3
    //   355: aload 4
    //   357: ifnull -337 -> 20
    //   360: aload 4
    //   362: invokeinterface 443 1 0
    //   367: aload_3
    //   368: areturn
    //   369: astore 6
    //   371: aload 4
    //   373: ifnull +10 -> 383
    //   376: aload 4
    //   378: invokeinterface 443 1 0
    //   383: aload 6
    //   385: athrow
    //   386: new 178	java/lang/StringBuilder
    //   389: dup
    //   390: invokespecial 179	java/lang/StringBuilder:<init>	()V
    //   393: ldc_w 497
    //   396: invokevirtual 186	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   399: aload_3
    //   400: iload 24
    //   402: invokeinterface 487 2 0
    //   407: checkcast 354	com/google/analytics/tracking/android/Hit
    //   410: invokevirtual 358	com/google/analytics/tracking/android/Hit:getHitId	()J
    //   413: invokevirtual 500	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   416: ldc_w 502
    //   419: invokevirtual 186	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   422: invokevirtual 199	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   425: invokestatic 225	com/google/analytics/tracking/android/Log:w	(Ljava/lang/String;)I
    //   428: pop
    //   429: goto -143 -> 286
    //   432: astore 15
    //   434: new 178	java/lang/StringBuilder
    //   437: dup
    //   438: invokespecial 179	java/lang/StringBuilder:<init>	()V
    //   441: ldc_w 504
    //   444: invokevirtual 186	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   447: aload 15
    //   449: invokevirtual 495	android/database/sqlite/SQLiteException:getMessage	()Ljava/lang/String;
    //   452: invokevirtual 186	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   455: invokevirtual 199	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   458: invokestatic 225	com/google/analytics/tracking/android/Log:w	(Ljava/lang/String;)I
    //   461: pop
    //   462: new 158	java/util/ArrayList
    //   465: dup
    //   466: invokespecial 448	java/util/ArrayList:<init>	()V
    //   469: astore 17
    //   471: iconst_0
    //   472: istore 18
    //   474: aload_3
    //   475: invokeinterface 505 1 0
    //   480: astore 19
    //   482: aload 19
    //   484: invokeinterface 129 1 0
    //   489: ifeq +35 -> 524
    //   492: aload 19
    //   494: invokeinterface 133 1 0
    //   499: checkcast 354	com/google/analytics/tracking/android/Hit
    //   502: astore 20
    //   504: aload 20
    //   506: invokevirtual 508	com/google/analytics/tracking/android/Hit:getHitParams	()Ljava/lang/String;
    //   509: invokestatic 511	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   512: istore 21
    //   514: iload 21
    //   516: ifeq +68 -> 584
    //   519: iload 18
    //   521: ifeq +60 -> 581
    //   524: aload 4
    //   526: ifnull +10 -> 536
    //   529: aload 4
    //   531: invokeinterface 443 1 0
    //   536: aload 17
    //   538: areturn
    //   539: aload_3
    //   540: iload 24
    //   542: invokeinterface 487 2 0
    //   547: checkcast 354	com/google/analytics/tracking/android/Hit
    //   550: aload 4
    //   552: iconst_1
    //   553: invokeinterface 465 2 0
    //   558: invokevirtual 490	com/google/analytics/tracking/android/Hit:setHitString	(Ljava/lang/String;)V
    //   561: goto -275 -> 286
    //   564: astore 14
    //   566: aload 4
    //   568: ifnull +10 -> 578
    //   571: aload 4
    //   573: invokeinterface 443 1 0
    //   578: aload 14
    //   580: athrow
    //   581: iconst_1
    //   582: istore 18
    //   584: aload 17
    //   586: aload 20
    //   588: invokeinterface 204 2 0
    //   593: pop
    //   594: goto -112 -> 482
    //   597: astore 6
    //   599: aload_3
    //   600: pop
    //   601: goto -230 -> 371
    //   604: astore 7
    //   606: aload_3
    //   607: pop
    //   608: goto -289 -> 319
    //
    // Exception table:
    //   from	to	target	type
    //   33	96	317	android/database/sqlite/SQLiteException
    //   33	96	369	finally
    //   319	355	369	finally
    //   180	234	432	android/database/sqlite/SQLiteException
    //   242	286	432	android/database/sqlite/SQLiteException
    //   289	298	432	android/database/sqlite/SQLiteException
    //   386	429	432	android/database/sqlite/SQLiteException
    //   539	561	432	android/database/sqlite/SQLiteException
    //   180	234	564	finally
    //   242	286	564	finally
    //   289	298	564	finally
    //   386	429	564	finally
    //   434	471	564	finally
    //   474	482	564	finally
    //   482	514	564	finally
    //   539	561	564	finally
    //   584	594	564	finally
    //   96	106	597	finally
    //   106	163	597	finally
    //   96	106	604	android/database/sqlite/SQLiteException
    //   106	163	604	android/database/sqlite/SQLiteException
  }

  public void putHit(Map<String, String> paramMap, long paramLong, String paramString, Collection<Command> paramCollection)
  {
    deleteStaleHits();
    fillVersionParametersIfNecessary(paramMap, paramCollection);
    removeOldHitIfFull();
    writeHitToDatabase(paramMap, paramLong, paramString);
  }

  @VisibleForTesting
  public void setClock(Clock paramClock)
  {
    this.mClock = paramClock;
  }

  public void setDispatch(boolean paramBoolean)
  {
    if (paramBoolean);
    for (Object localObject = new SimpleNetworkDispatcher(this, createDefaultHttpClientFactory(), this.mContext); ; localObject = new NoopDispatcher())
    {
      this.mDispatcher = ((Dispatcher)localObject);
      return;
    }
  }

  @VisibleForTesting
  void setDispatcher(Dispatcher paramDispatcher)
  {
    this.mDispatcher = paramDispatcher;
  }

  @VisibleForTesting
  void setLastDeleteStaleHitsTime(long paramLong)
  {
    this.mLastDeleteStaleHitsTime = paramLong;
  }

  @VisibleForTesting
  class AnalyticsDatabaseHelper extends SQLiteOpenHelper
  {
    private boolean mBadDatabase;
    private long mLastDatabaseCheckTime = 0L;

    AnalyticsDatabaseHelper(Context paramString, String arg3)
    {
      super(str, null, 1);
    }

    private boolean tablePresent(String paramString, SQLiteDatabase paramSQLiteDatabase)
    {
      Cursor localCursor = null;
      try
      {
        localCursor = paramSQLiteDatabase.query("SQLITE_MASTER", new String[] { "name" }, "name=?", new String[] { paramString }, null, null, null);
        boolean bool = localCursor.moveToFirst();
        return bool;
      }
      catch (SQLiteException localSQLiteException)
      {
        Log.w("error querying for table " + paramString);
        return false;
      }
      finally
      {
        if (localCursor != null)
          localCursor.close();
      }
      throw localObject;
    }

    private void validateColumnsPresent(SQLiteDatabase paramSQLiteDatabase)
    {
      Cursor localCursor = paramSQLiteDatabase.rawQuery("SELECT * FROM hits2 WHERE 0", null);
      HashSet localHashSet = new HashSet();
      try
      {
        String[] arrayOfString = localCursor.getColumnNames();
        for (int i = 0; i < arrayOfString.length; i++)
          localHashSet.add(arrayOfString[i]);
        localCursor.close();
        if ((!localHashSet.remove("hit_id")) || (!localHashSet.remove("hit_url")) || (!localHashSet.remove("hit_string")) || (!localHashSet.remove("hit_time")))
          throw new SQLiteException("Database column missing");
      }
      finally
      {
        localCursor.close();
      }
      if (!localHashSet.remove("hit_app_id"));
      for (int j = 1; !localHashSet.isEmpty(); j = 0)
        throw new SQLiteException("Database has extra columns");
      if (j != 0)
        paramSQLiteDatabase.execSQL("ALTER TABLE hits2 ADD COLUMN hit_app_id");
    }

    public SQLiteDatabase getWritableDatabase()
    {
      if ((this.mBadDatabase) && (3600000L + this.mLastDatabaseCheckTime > PersistentAnalyticsStore.this.mClock.currentTimeMillis()))
        throw new SQLiteException("Database creation failed");
      this.mBadDatabase = true;
      this.mLastDatabaseCheckTime = PersistentAnalyticsStore.this.mClock.currentTimeMillis();
      try
      {
        SQLiteDatabase localSQLiteDatabase2 = super.getWritableDatabase();
        localSQLiteDatabase1 = localSQLiteDatabase2;
        if (localSQLiteDatabase1 == null)
          localSQLiteDatabase1 = super.getWritableDatabase();
        this.mBadDatabase = false;
        return localSQLiteDatabase1;
      }
      catch (SQLiteException localSQLiteException)
      {
        while (true)
        {
          PersistentAnalyticsStore.this.mContext.getDatabasePath(PersistentAnalyticsStore.this.mDatabaseName).delete();
          SQLiteDatabase localSQLiteDatabase1 = null;
        }
      }
    }

    boolean isBadDatabase()
    {
      return this.mBadDatabase;
    }

    public void onCreate(SQLiteDatabase paramSQLiteDatabase)
    {
      FutureApis.setOwnerOnlyReadWrite(paramSQLiteDatabase.getPath());
    }

    public void onOpen(SQLiteDatabase paramSQLiteDatabase)
    {
      Cursor localCursor;
      if (Build.VERSION.SDK_INT < 15)
        localCursor = paramSQLiteDatabase.rawQuery("PRAGMA journal_mode=memory", null);
      try
      {
        localCursor.moveToFirst();
        localCursor.close();
        if (!tablePresent("hits2", paramSQLiteDatabase))
        {
          paramSQLiteDatabase.execSQL(PersistentAnalyticsStore.CREATE_HITS_TABLE);
          return;
        }
      }
      finally
      {
        localCursor.close();
      }
      validateColumnsPresent(paramSQLiteDatabase);
    }

    public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
    {
    }

    void setBadDatabase(boolean paramBoolean)
    {
      this.mBadDatabase = paramBoolean;
    }
  }
}

/* Location:           C:\Users\Yash\Desktop\apk to src\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.google.analytics.tracking.android.PersistentAnalyticsStore
 * JD-Core Version:    0.6.0
 */
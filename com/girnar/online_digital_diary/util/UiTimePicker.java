package com.girnar.online_digital_diary.util;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class UiTimePicker extends Dialog
{
  public Context context;
  EditText editText;
  private boolean isMultiNeed;
  public ArrayList<ArrayList<Integer>> listOfTime;
  public int mHour;
  public int mMinute;
  TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener()
  {
    public void onTimeSet(TimePicker paramTimePicker, int paramInt1, int paramInt2)
    {
      UiTimePicker.this.mHour = paramInt1;
      UiTimePicker.this.mMinute = paramInt2;
      if (UiTimePicker.this.isMultiNeed)
      {
        ArrayList localArrayList = new ArrayList();
        localArrayList.add(Integer.valueOf(UiTimePicker.this.mHour));
        localArrayList.add(Integer.valueOf(UiTimePicker.this.mMinute));
        localArrayList.add(Integer.valueOf(0));
        UiTimePicker.this.listOfTime.add(localArrayList);
      }
      UiTimePicker.this.setTime(paramInt1, paramInt2, UiTimePicker.this.view);
    }
  };
  public View view;

  public UiTimePicker(Context paramContext, boolean paramBoolean)
  {
    super(paramContext);
    this.context = paramContext;
    this.isMultiNeed = paramBoolean;
    Calendar localCalendar = Calendar.getInstance();
    this.mHour = localCalendar.get(11);
    this.mMinute = localCalendar.get(12);
    this.listOfTime = new ArrayList();
  }

  public void addTime(long paramLong)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTimeInMillis(paramLong);
    Log.w("Row", "hour " + localCalendar.get(11) + " minute " + localCalendar.get(12));
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(Integer.valueOf(localCalendar.get(11)));
    localArrayList.add(Integer.valueOf(localCalendar.get(12)));
    localArrayList.add(Integer.valueOf(0));
    this.listOfTime.add(localArrayList);
  }

  public void removeTime(View paramView)
  {
    if (!this.listOfTime.isEmpty())
    {
      this.listOfTime.remove(-1 + this.listOfTime.size());
      setTimeText(paramView);
    }
  }

  public void setTime(int paramInt1, int paramInt2, View paramView)
  {
    this.mHour = paramInt1;
    this.mMinute = paramInt2;
    TextView localTextView;
    if ((paramView instanceof TextView))
    {
      localTextView = (TextView)paramView;
      if ((localTextView.getTag() == null) || ((localTextView.getTag() != null) && (!localTextView.getTag().toString().equals("useNormalFont"))))
      {
        if (!this.isMultiNeed)
          break label103;
        localTextView.setText(localTextView.getText().toString() + setTimeFormet() + ",");
      }
    }
    label103: EditText localEditText;
    do
    {
      do
      {
        return;
        localTextView.setText(setTimeFormet());
        return;
      }
      while (!(paramView instanceof ImageButton));
      localEditText = this.editText;
    }
    while ((localEditText.getTag() != null) && ((localEditText.getTag() == null) || (localEditText.getTag().toString().equals("useNormalFont"))));
    if (this.isMultiNeed)
    {
      localEditText.setText(localEditText.getText().toString() + setTimeFormet() + ",");
      return;
    }
    localEditText.setText(setTimeFormet());
  }

  public void setTime(long paramLong, View paramView)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTimeInMillis(paramLong);
    this.mHour = localCalendar.get(11);
    this.mMinute = localCalendar.get(12);
    if ((paramView instanceof TextView))
    {
      TextView localTextView = (TextView)paramView;
      if ((localTextView.getTag() == null) || ((localTextView.getTag() != null) && (!localTextView.getTag().toString().equals("useNormalFont"))))
        localTextView.setText(setTimeFormet());
    }
    EditText localEditText;
    do
    {
      do
        return;
      while (!(paramView instanceof EditText));
      localEditText = (EditText)paramView;
    }
    while ((localEditText.getTag() != null) && ((localEditText.getTag() == null) || (localEditText.getTag().toString().equals("useNormalFont"))));
    localEditText.setText(setTimeFormet());
  }

  public String setTimeFormet()
  {
    if (this.mHour > 12)
    {
      Object[] arrayOfObject4 = new Object[1];
      arrayOfObject4[0] = Integer.valueOf(-12 + this.mHour);
      StringBuilder localStringBuilder3 = new StringBuilder(String.valueOf(String.format("%02d", arrayOfObject4))).append(":");
      Object[] arrayOfObject5 = new Object[1];
      arrayOfObject5[0] = Integer.valueOf(this.mMinute);
      return String.valueOf(String.valueOf(new StringBuilder(String.valueOf(String.format("%02d", arrayOfObject5))).append(" PM").toString()));
    }
    if (this.mHour == 12)
    {
      StringBuilder localStringBuilder1 = new StringBuilder("12:");
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = Integer.valueOf(this.mMinute);
      return String.valueOf(new StringBuilder(String.valueOf(String.format("%02d", arrayOfObject1))).append(" PM").toString());
    }
    if (this.mHour < 12)
    {
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = Integer.valueOf(this.mHour);
      StringBuilder localStringBuilder2 = new StringBuilder(String.valueOf(String.format("%02d", arrayOfObject2))).append(":");
      Object[] arrayOfObject3 = new Object[1];
      arrayOfObject3[0] = Integer.valueOf(this.mMinute);
      return String.valueOf(String.valueOf(new StringBuilder(String.valueOf(String.format("%02d", arrayOfObject3))).append(" AM").toString()));
    }
    return "";
  }

  public void setTimeText(View paramView)
  {
    for (int i = 0; ; i++)
    {
      if (i >= this.listOfTime.size())
        return;
      Log.w("Time Picker", "Hour: " + ((ArrayList)this.listOfTime.get(i)).get(0) + " Minutes: " + ((ArrayList)this.listOfTime.get(i)).get(1));
      setTime(((Integer)((ArrayList)this.listOfTime.get(i)).get(0)).intValue(), ((Integer)((ArrayList)this.listOfTime.get(i)).get(1)).intValue(), paramView);
    }
  }

  public void showDialog(View paramView, EditText paramEditText)
  {
    this.view = paramView;
    this.editText = paramEditText;
    new TimePickerDialog(this.context, this.mTimeSetListener, this.mHour, this.mMinute, false).show();
  }

  public long timeInMills()
  {
    Date localDate = new Date();
    Calendar localCalendar1 = Calendar.getInstance();
    Calendar localCalendar2 = Calendar.getInstance();
    localCalendar2.setTime(localDate);
    localCalendar1.setTime(localDate);
    localCalendar1.set(11, this.mHour);
    localCalendar1.set(12, this.mMinute);
    localCalendar1.set(13, 0);
    if (localCalendar1.before(localCalendar2))
      localCalendar1.add(5, 1);
    return localCalendar1.getTimeInMillis();
  }

  public long timeInMills(int paramInt1, int paramInt2)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.set(paramInt1, paramInt2, 0);
    return localCalendar.getTimeInMillis();
  }

  public long timeInMills(int paramInt1, int paramInt2, int paramInt3)
  {
    Date localDate = new Date();
    Calendar localCalendar1 = Calendar.getInstance();
    Calendar localCalendar2 = Calendar.getInstance();
    localCalendar2.setTime(localDate);
    localCalendar1.set(paramInt1, paramInt2, paramInt3, this.mHour, this.mMinute, 0);
    if (localCalendar1.before(localCalendar2))
      localCalendar1.add(5, 1);
    return localCalendar1.getTimeInMillis();
  }
}

/* Location:           C:\Users\Yash\Desktop\apk to src\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.girnar.online_digital_diary.util.UiTimePicker
 * JD-Core Version:    0.6.0
 */
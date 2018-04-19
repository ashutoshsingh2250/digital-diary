package com.girnar.online_digital_diary.util;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Calendar;

public class UiDatePicker extends Dialog
{
  private Context context;
  EditText editText;
  private boolean isMultiNeed;
  public ArrayList<ArrayList<Integer>> listOfDate;
  DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener()
  {
    public void onDateSet(DatePicker paramDatePicker, int paramInt1, int paramInt2, int paramInt3)
    {
      UiDatePicker.this.mYear = paramInt1;
      UiDatePicker.this.mMonth = paramInt2;
      UiDatePicker.this.mDay = paramInt3;
      if (UiDatePicker.this.isMultiNeed)
      {
        ArrayList localArrayList = new ArrayList();
        localArrayList.add(Integer.valueOf(UiDatePicker.this.mYear));
        localArrayList.add(Integer.valueOf(UiDatePicker.this.mMonth));
        localArrayList.add(Integer.valueOf(UiDatePicker.this.mDay));
        UiDatePicker.this.listOfDate.add(localArrayList);
      }
      UiDatePicker.this.setDate();
    }
  };
  public int mDay;
  public int mMonth;
  public int mYear;
  private String pattern = "/";
  private UiToast uiToast;
  private View view;

  public UiDatePicker(Context paramContext, boolean paramBoolean)
  {
    super(paramContext);
    this.context = paramContext;
    this.uiToast = new UiToast(paramContext);
    this.isMultiNeed = paramBoolean;
    this.listOfDate = new ArrayList();
  }

  private void checkPattern(String paramString)
  {
    String[] arrayOfString = { "/", "-", "." };
    if (paramString.matches(arrayOfString[0]))
    {
      this.pattern = paramString;
      return;
    }
    if (paramString.matches(arrayOfString[1]))
    {
      this.pattern = paramString;
      return;
    }
    if (paramString.matches(arrayOfString[2]))
    {
      this.pattern = paramString;
      return;
    }
    this.uiToast.showToast("Invalid Pattern please choose form these /n / - .", 1);
  }

  private void setDate()
  {
    Object[] arrayOfObject1 = new Object[1];
    arrayOfObject1[0] = Integer.valueOf(this.mDay);
    StringBuilder localStringBuilder1 = new StringBuilder(String.valueOf(String.format("%02d", arrayOfObject1))).append(this.pattern);
    Object[] arrayOfObject2 = new Object[1];
    arrayOfObject2[0] = Integer.valueOf(1 + this.mMonth);
    StringBuilder localStringBuilder2 = localStringBuilder1.append(String.format("%02d", arrayOfObject2)).append(this.pattern);
    Object[] arrayOfObject3 = new Object[1];
    arrayOfObject3[0] = Integer.valueOf(this.mYear);
    String str = String.format("%02d", arrayOfObject3) + " ";
    TextView localTextView;
    if ((this.view instanceof TextView))
    {
      localTextView = (TextView)this.view;
      if ((localTextView.getTag() == null) || ((localTextView.getTag() != null) && (!localTextView.getTag().toString().equals("useNormalFont"))))
      {
        if (!this.isMultiNeed)
          break label211;
        localTextView.setText(localTextView.getText().toString() + str + " ");
      }
    }
    label211: EditText localEditText;
    do
    {
      do
      {
        return;
        localTextView.setText(str);
        return;
      }
      while (!(this.view instanceof ImageButton));
      localEditText = this.editText;
    }
    while ((localEditText.getTag() != null) && ((localEditText.getTag() == null) || (localEditText.getTag().toString().equals("useNormalFont"))));
    if (this.isMultiNeed)
    {
      localEditText.setText(localEditText.getText().toString() + str + " ");
      return;
    }
    localEditText.setText(str);
  }

  private void setDate(int paramInt1, int paramInt2, int paramInt3, View paramView)
  {
    Object[] arrayOfObject1 = new Object[1];
    arrayOfObject1[0] = Integer.valueOf(paramInt3);
    StringBuilder localStringBuilder1 = new StringBuilder(String.valueOf(String.format("%02d", arrayOfObject1))).append(this.pattern);
    Object[] arrayOfObject2 = new Object[1];
    arrayOfObject2[0] = Integer.valueOf(paramInt2 + 1);
    StringBuilder localStringBuilder2 = localStringBuilder1.append(String.format("%02d", arrayOfObject2)).append(this.pattern);
    Object[] arrayOfObject3 = new Object[1];
    arrayOfObject3[0] = Integer.valueOf(paramInt1);
    String str = String.format("%02d", arrayOfObject3) + " ";
    TextView localTextView;
    if ((paramView instanceof TextView))
    {
      localTextView = (TextView)paramView;
      if ((localTextView.getTag() == null) || ((localTextView.getTag() != null) && (!localTextView.getTag().toString().equals("useNormalFont"))))
      {
        if (!this.isMultiNeed)
          break label206;
        localTextView.setText(localTextView.getText().toString() + str + " ");
      }
    }
    label206: EditText localEditText;
    do
    {
      do
      {
        return;
        localTextView.setText(str);
        return;
      }
      while (!(paramView instanceof EditText));
      localEditText = (EditText)paramView;
    }
    while ((localEditText.getTag() != null) && ((localEditText.getTag() == null) || (localEditText.getTag().toString().equals("useNormalFont"))));
    if (this.isMultiNeed)
    {
      localEditText.setText(localEditText.getText().toString() + str + " ");
      return;
    }
    localEditText.setText(str);
  }

  private void setDate(View paramView)
  {
    Object[] arrayOfObject1 = new Object[1];
    arrayOfObject1[0] = Integer.valueOf(this.mDay);
    StringBuilder localStringBuilder1 = new StringBuilder(String.valueOf(String.format("%02d", arrayOfObject1))).append(this.pattern);
    Object[] arrayOfObject2 = new Object[1];
    arrayOfObject2[0] = Integer.valueOf(1 + this.mMonth);
    StringBuilder localStringBuilder2 = localStringBuilder1.append(String.format("%02d", arrayOfObject2)).append(this.pattern);
    Object[] arrayOfObject3 = new Object[1];
    arrayOfObject3[0] = Integer.valueOf(this.mYear);
    String str = String.format("%02d", arrayOfObject3);
    TextView localTextView;
    if ((paramView instanceof TextView))
    {
      localTextView = (TextView)paramView;
      if ((localTextView.getTag() == null) || ((localTextView.getTag() != null) && (!localTextView.getTag().toString().equals("useNormalFont"))))
      {
        if (!this.isMultiNeed)
          break label203;
        localTextView.setText(localTextView.getText().toString() + str + " ");
      }
    }
    label203: EditText localEditText;
    do
    {
      do
      {
        return;
        localTextView.setText(str);
        return;
      }
      while (!(paramView instanceof EditText));
      localEditText = (EditText)paramView;
    }
    while ((localEditText.getTag() != null) && ((localEditText.getTag() == null) || (localEditText.getTag().toString().equals("useNormalFont"))));
    if (this.isMultiNeed)
    {
      localEditText.setText(localEditText.getText().toString() + str + " ");
      return;
    }
    localEditText.setText(str);
  }

  public void addDate(long paramLong)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTimeInMillis(paramLong);
    Log.w("Row", "Year " + localCalendar.get(1) + " Month " + localCalendar.get(2) + " Day " + localCalendar.get(5));
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(Integer.valueOf(localCalendar.get(1)));
    localArrayList.add(Integer.valueOf(localCalendar.get(2)));
    localArrayList.add(Integer.valueOf(localCalendar.get(5)));
    this.listOfDate.add(localArrayList);
  }

  public void removeDate(View paramView)
  {
    if (this.listOfDate.size() > 0)
    {
      this.listOfDate.remove(-1 + this.listOfDate.size());
      setDateText(paramView);
    }
  }

  public void setDate(long paramLong, View paramView, String paramString)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTimeInMillis(paramLong);
    this.mYear = localCalendar.get(1);
    this.mMonth = localCalendar.get(2);
    this.mDay = localCalendar.get(5);
    this.pattern = paramString;
    setDate(paramView);
  }

  public void setDateText(View paramView)
  {
    for (int i = 0; ; i++)
    {
      if (i >= this.listOfDate.size())
        return;
      setDate(((Integer)((ArrayList)this.listOfDate.get(i)).get(0)).intValue(), ((Integer)((ArrayList)this.listOfDate.get(i)).get(1)).intValue(), ((Integer)((ArrayList)this.listOfDate.get(i)).get(2)).intValue(), paramView);
    }
  }

  public void showDialog(View paramView, EditText paramEditText, String paramString)
  {
    this.view = paramView;
    this.pattern = paramString;
    this.editText = paramEditText;
    Calendar localCalendar = Calendar.getInstance();
    this.mYear = localCalendar.get(1);
    this.mMonth = localCalendar.get(2);
    this.mDay = localCalendar.get(5);
    new DatePickerDialog(this.context, this.mDateSetListener, this.mYear, this.mMonth, this.mDay).show();
  }
}

/* Location:           C:\Users\Yash\Desktop\apk to src\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.girnar.online_digital_diary.util.UiDatePicker
 * JD-Core Version:    0.6.0
 */
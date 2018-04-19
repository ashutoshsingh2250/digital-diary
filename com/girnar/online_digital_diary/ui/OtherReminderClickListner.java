package com.girnar.online_digital_diary.ui;

import android.view.View;
import android.view.View.OnClickListener;

public abstract class OtherReminderClickListner
  implements View.OnClickListener
{
  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    case 2131492999:
    default:
      return;
    case 2131493000:
      onTimeClick(paramView);
      return;
    case 2131492998:
      onDateClick(paramView);
      return;
    case 2131493001:
    }
    onSetRemenderClick();
  }

  public abstract void onDateClick(View paramView);

  public abstract void onDayClick();

  public abstract void onDescriptionClick();

  public abstract void onSetRemenderClick();

  public abstract void onTimeClick(View paramView);
}

/* Location:           C:\Users\Yash\Desktop\apk to src\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.girnar.online_digital_diary.ui.OtherReminderClickListner
 * JD-Core Version:    0.6.0
 */
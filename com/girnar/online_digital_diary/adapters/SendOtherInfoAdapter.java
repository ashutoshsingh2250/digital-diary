package com.girnar.online_digital_diary.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.girnar.online_digital_diary.beans.Items;
import com.girnar.online_digital_diary.ui.SendOtherInfoActivity;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SendOtherInfoAdapter extends ArrayAdapter<Items>
{
  public static LinearLayout layout;
  private static ArrayList<Integer> selected;
  private final Activity activity;
  private HashMap<String, List<String>> childData;
  private final ArrayList<Items> data;

  public SendOtherInfoAdapter(Activity paramActivity, ArrayList<Items> paramArrayList, HashMap<String, List<String>> paramHashMap, boolean paramBoolean)
  {
    super(paramActivity, 2130903070, paramArrayList);
    this.activity = paramActivity;
    this.data = paramArrayList;
    this.childData = paramHashMap;
    if (paramBoolean)
      selected = new ArrayList();
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    View localView;
    Holder localHolder2;
    if (paramView == null)
    {
      Holder localHolder1 = new Holder();
      localView = this.activity.getLayoutInflater().inflate(2130903071, null);
      localHolder1.textView = ((TextView)localView.findViewById(2131493008));
      localHolder1.description = ((TextView)localView.findViewById(2131493062));
      localHolder1.layout = ((LinearLayout)localView.findViewById(2131493058));
      localHolder1.button = ((ImageButton)localView.findViewById(2131493056));
      localHolder1.checkBox = ((CheckBox)localView.findViewById(2131493057));
      localHolder1.checkBox.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          CheckBox localCheckBox = (CheckBox)paramView;
          Items localItems = (Items)localCheckBox.getTag();
          int i = SendOtherInfoAdapter.this.data.indexOf(localItems);
          System.out.println("postion in onCLick is " + i);
          if (localCheckBox.isChecked())
            if (SendOtherInfoActivity.select_all_flag != 1)
              SendOtherInfoActivity.setSendInfoIds(Integer.parseInt(((Items)SendOtherInfoAdapter.this.data.get(SendOtherInfoAdapter.this.data.indexOf(localItems))).getId()), true);
          while (true)
          {
            if ((!localCheckBox.isChecked()) && (SendOtherInfoActivity.select_all_flag == 1))
              SendOtherInfoActivity.uncheckedSelectAllButton();
            localItems.setChecked(localCheckBox.isChecked());
            return;
            System.out.println("ind is " + ((Items)SendOtherInfoAdapter.this.data.get(SendOtherInfoAdapter.this.data.indexOf(localItems))).getId());
            SendOtherInfoActivity.removeSendInfoIds(Integer.parseInt(((Items)SendOtherInfoAdapter.this.data.get(SendOtherInfoAdapter.this.data.indexOf(localItems))).getId()), true);
          }
        }
      });
      localHolder1.button.setOnClickListener(new View.OnClickListener(localHolder1)
      {
        public void onClick(View paramView)
        {
          Items localItems = (Items)paramView.getTag();
          int i = SendOtherInfoAdapter.this.data.indexOf(localItems);
          if (SendOtherInfoAdapter.selected.contains(Integer.valueOf(Integer.parseInt(((Items)SendOtherInfoAdapter.this.data.get(i)).getId()))))
          {
            SendOtherInfoAdapter.selected.remove(SendOtherInfoAdapter.selected.indexOf(Integer.valueOf(Integer.parseInt(((Items)SendOtherInfoAdapter.this.data.get(i)).getId()))));
            SendOtherInfoActivity.opened_ids.remove(SendOtherInfoActivity.opened_ids.indexOf(Integer.valueOf(Integer.parseInt(((Items)SendOtherInfoAdapter.this.data.get(i)).getId()))));
            ((Items)SendOtherInfoAdapter.this.data.get(i)).setVisible(8);
            this.val$holder.layout.setVisibility(8);
            return;
          }
          SendOtherInfoAdapter.selected.add(Integer.valueOf(Integer.parseInt(((Items)SendOtherInfoAdapter.this.data.get(i)).getId())));
          SendOtherInfoActivity.opened_ids.add(Integer.valueOf(Integer.parseInt(((Items)SendOtherInfoAdapter.this.data.get(i)).getId())));
          System.out.println("in onClick else par " + ((Items)SendOtherInfoAdapter.this.data.get(i)).getName());
          ((Items)SendOtherInfoAdapter.this.data.get(i)).setVisible(0);
          this.val$holder.layout.setVisibility(0);
        }
      });
      localView.setTag(localHolder1);
      localHolder1.checkBox.setTag(this.data.get(paramInt));
      localHolder1.button.setTag(this.data.get(paramInt));
      localHolder2 = (Holder)localView.getTag();
      localHolder2.textView.setText(((Items)this.data.get(paramInt)).getName());
      localHolder2.description.setText((CharSequence)((List)this.childData.get(((Items)this.data.get(paramInt)).getName())).get(0));
      if (SendOtherInfoActivity.select_all_flag != 1)
        break label446;
      ((Items)this.data.get(paramInt)).setChecked(true);
      localHolder2.checkBox.setChecked(true);
    }
    while (true)
    {
      System.out.println("position is " + paramInt);
      System.out.println(((Items)this.data.get(paramInt)).getVisible());
      localHolder2.layout.setVisibility(8);
      if (selected.contains(Integer.valueOf(Integer.parseInt(((Items)this.data.get(paramInt)).getId()))))
      {
        System.out.println("in mamala...");
        ((Items)this.data.get(paramInt)).setVisible(0);
        localHolder2.layout.setVisibility(0);
      }
      return localView;
      localView = paramView;
      ((Holder)localView.getTag()).checkBox.setTag(this.data.get(paramInt));
      ((Holder)localView.getTag()).button.setTag(this.data.get(paramInt));
      break;
      label446: if (SendOtherInfoActivity.select_all_flag == 2)
      {
        if (paramInt == -1 + this.data.size())
          SendOtherInfoActivity.select_all_flag = 0;
        ((Items)this.data.get(paramInt)).setChecked(false);
        localHolder2.checkBox.setChecked(false);
        continue;
      }
      localHolder2.checkBox.setChecked(((Items)this.data.get(paramInt)).isChecked());
    }
  }

  static class Holder
  {
    public ImageButton button;
    private CheckBox checkBox;
    public TextView description;
    public LinearLayout layout;
    public TextView textView;
  }
}

/* Location:           C:\Users\Yash\Desktop\apk to src\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.girnar.online_digital_diary.adapters.SendOtherInfoAdapter
 * JD-Core Version:    0.6.0
 */
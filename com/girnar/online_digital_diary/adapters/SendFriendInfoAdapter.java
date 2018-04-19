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
import com.girnar.online_digital_diary.ui.SendFriendInformationActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SendFriendInfoAdapter extends ArrayAdapter<Items>
{
  public static LinearLayout layout;
  private static ArrayList<Integer> selected;
  private final Activity activity;
  private HashMap<String, List<String>> childData;
  private final ArrayList<Items> data;

  public SendFriendInfoAdapter(Activity paramActivity, ArrayList<Items> paramArrayList, HashMap<String, List<String>> paramHashMap, boolean paramBoolean)
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
      localView = this.activity.getLayoutInflater().inflate(2130903072, null);
      localHolder1.textView = ((TextView)localView.findViewById(2131493008));
      localHolder1.mobileNo = ((TextView)localView.findViewById(2131493064));
      localHolder1.email = ((TextView)localView.findViewById(2131493065));
      localHolder1.birthday = ((TextView)localView.findViewById(2131493067));
      localHolder1.anniversary = ((TextView)localView.findViewById(2131493068));
      localHolder1.address = ((TextView)localView.findViewById(2131493070));
      localHolder1.gender = ((TextView)localView.findViewById(2131493071));
      localHolder1.layout1 = ((LinearLayout)localView.findViewById(2131493063));
      localHolder1.layout2 = ((LinearLayout)localView.findViewById(2131493066));
      localHolder1.layout3 = ((LinearLayout)localView.findViewById(2131493069));
      localHolder1.button = ((ImageButton)localView.findViewById(2131493056));
      localHolder1.mobileNo.setText((CharSequence)((List)this.childData.get(((Items)this.data.get(paramInt)).getName())).get(0));
      localHolder1.email.setText((CharSequence)((List)this.childData.get(((Items)this.data.get(paramInt)).getName())).get(1));
      localHolder1.birthday.setText((CharSequence)((List)this.childData.get(((Items)this.data.get(paramInt)).getName())).get(2));
      localHolder1.anniversary.setText((CharSequence)((List)this.childData.get(((Items)this.data.get(paramInt)).getName())).get(3));
      localHolder1.address.setText((CharSequence)((List)this.childData.get(((Items)this.data.get(paramInt)).getName())).get(4));
      localHolder1.gender.setText((CharSequence)((List)this.childData.get(((Items)this.data.get(paramInt)).getName())).get(5));
      localHolder1.checkBox = ((CheckBox)localView.findViewById(2131493057));
      localHolder1.checkBox.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          CheckBox localCheckBox = (CheckBox)paramView;
          Items localItems = (Items)localCheckBox.getTag();
          if (localCheckBox.isChecked())
            if (SendFriendInformationActivity.select_all_flag != 1)
              SendFriendInformationActivity.setSendInfoIds(Integer.parseInt(((Items)SendFriendInfoAdapter.this.data.get(SendFriendInfoAdapter.this.data.indexOf(localItems))).getId()), true);
          while (true)
          {
            if ((!localCheckBox.isChecked()) && (SendFriendInformationActivity.select_all_flag == 1))
              SendFriendInformationActivity.uncheckedSelectAllButton();
            localItems.setChecked(localCheckBox.isChecked());
            return;
            SendFriendInformationActivity.removeSendInfoIds(Integer.parseInt(((Items)SendFriendInfoAdapter.this.data.get(SendFriendInfoAdapter.this.data.indexOf(localItems))).getId()), true);
          }
        }
      });
      localHolder1.button.setOnClickListener(new View.OnClickListener(localHolder1)
      {
        public void onClick(View paramView)
        {
          Items localItems = (Items)paramView.getTag();
          int i = SendFriendInfoAdapter.this.data.indexOf(localItems);
          if (SendFriendInfoAdapter.selected.contains(Integer.valueOf(Integer.parseInt(((Items)SendFriendInfoAdapter.this.data.get(i)).getId()))))
          {
            SendFriendInfoAdapter.selected.remove(SendFriendInfoAdapter.selected.indexOf(Integer.valueOf(Integer.parseInt(((Items)SendFriendInfoAdapter.this.data.get(i)).getId()))));
            SendFriendInformationActivity.opened_ids.remove(SendFriendInformationActivity.opened_ids.indexOf(Integer.valueOf(Integer.parseInt(((Items)SendFriendInfoAdapter.this.data.get(i)).getId()))));
            ((Items)SendFriendInfoAdapter.this.data.get(i)).setVisible(8);
            this.val$holder.layout1.setVisibility(8);
            this.val$holder.layout2.setVisibility(8);
            this.val$holder.layout3.setVisibility(8);
            return;
          }
          SendFriendInfoAdapter.selected.add(Integer.valueOf(Integer.parseInt(((Items)SendFriendInfoAdapter.this.data.get(i)).getId())));
          SendFriendInformationActivity.opened_ids.add(Integer.valueOf(Integer.parseInt(((Items)SendFriendInfoAdapter.this.data.get(i)).getId())));
          ((Items)SendFriendInfoAdapter.this.data.get(i)).setVisible(0);
          this.val$holder.layout1.setVisibility(0);
          this.val$holder.layout2.setVisibility(0);
          this.val$holder.layout3.setVisibility(0);
        }
      });
      localView.setTag(localHolder1);
      localHolder1.checkBox.setTag(this.data.get(paramInt));
      localHolder1.button.setTag(this.data.get(paramInt));
      localHolder2 = (Holder)localView.getTag();
      localHolder2.textView.setText(((Items)this.data.get(paramInt)).getName());
      if (SendFriendInformationActivity.select_all_flag != 1)
        break label744;
      ((Items)this.data.get(paramInt)).setChecked(true);
      localHolder2.checkBox.setChecked(true);
    }
    while (true)
    {
      localHolder2.layout1.setVisibility(8);
      localHolder2.layout2.setVisibility(8);
      localHolder2.layout3.setVisibility(8);
      if (selected.contains(Integer.valueOf(Integer.parseInt(((Items)this.data.get(paramInt)).getId()))))
      {
        ((Items)this.data.get(paramInt)).setVisible(0);
        localHolder2.layout1.setVisibility(0);
        localHolder2.layout2.setVisibility(0);
        localHolder2.layout3.setVisibility(0);
      }
      return localView;
      localView = paramView;
      ((Holder)localView.getTag()).checkBox.setTag(this.data.get(paramInt));
      ((Holder)localView.getTag()).button.setTag(this.data.get(paramInt));
      break;
      label744: if (SendFriendInformationActivity.select_all_flag == 2)
      {
        if (paramInt == -1 + this.data.size())
          SendFriendInformationActivity.select_all_flag = 0;
        ((Items)this.data.get(paramInt)).setChecked(false);
        localHolder2.checkBox.setChecked(false);
        continue;
      }
      localHolder2.checkBox.setChecked(((Items)this.data.get(paramInt)).isChecked());
    }
  }

  static class Holder
  {
    public TextView address;
    public TextView anniversary;
    public TextView birthday;
    public ImageButton button;
    private CheckBox checkBox;
    public TextView email;
    public TextView gender;
    public LinearLayout layout1;
    public LinearLayout layout2;
    public LinearLayout layout3;
    public TextView mobileNo;
    public TextView textView;
  }
}

/* Location:           C:\Users\Yash\Desktop\apk to src\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.girnar.online_digital_diary.adapters.SendFriendInfoAdapter
 * JD-Core Version:    0.6.0
 */
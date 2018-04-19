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
import com.girnar.online_digital_diary.ui.SendAccountInfoActivity;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SendAdapter extends ArrayAdapter<Items>
{
  private static ArrayList<Integer> selected = new ArrayList();
  private final Activity activity;
  private HashMap<String, List<String>> childData;
  private final ArrayList<Items> data;
  public LinearLayout layout;

  public SendAdapter(Activity paramActivity, ArrayList<Items> paramArrayList, HashMap<String, List<String>> paramHashMap, boolean paramBoolean)
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
      localView = this.activity.getLayoutInflater().inflate(2130903070, null);
      localHolder1.textView = ((TextView)localView.findViewById(2131493008));
      localHolder1.accountNo = ((TextView)localView.findViewById(2131493059));
      localHolder1.bankName = ((TextView)localView.findViewById(2131493060));
      localHolder1.location = ((TextView)localView.findViewById(2131493061));
      localHolder1.layout = ((LinearLayout)localView.findViewById(2131493058));
      localHolder1.button = ((ImageButton)localView.findViewById(2131493056));
      localHolder1.checkBox = ((CheckBox)localView.findViewById(2131493057));
      localHolder1.checkBox.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          CheckBox localCheckBox = (CheckBox)paramView;
          Items localItems = (Items)localCheckBox.getTag();
          int i = SendAdapter.this.data.indexOf(localItems);
          System.out.println("postion in onCLick is " + i);
          if (localCheckBox.isChecked())
            if (SendAccountInfoActivity.select_all_flag != 1)
              SendAccountInfoActivity.setSendInfoIds(Integer.parseInt(((Items)SendAdapter.this.data.get(SendAdapter.this.data.indexOf(localItems))).getId()), true);
          while (true)
          {
            if ((!localCheckBox.isChecked()) && (SendAccountInfoActivity.select_all_flag == 1))
              SendAccountInfoActivity.uncheckedSelectAllButton();
            localItems.setChecked(localCheckBox.isChecked());
            return;
            System.out.println("ind is " + ((Items)SendAdapter.this.data.get(SendAdapter.this.data.indexOf(localItems))).getId());
            SendAccountInfoActivity.removeSendInfoIds(Integer.parseInt(((Items)SendAdapter.this.data.get(SendAdapter.this.data.indexOf(localItems))).getId()), true);
          }
        }
      });
      localHolder1.button.setOnClickListener(new View.OnClickListener(localHolder1, paramInt)
      {
        public void onClick(View paramView)
        {
          Items localItems = (Items)paramView.getTag();
          int i = SendAdapter.this.data.indexOf(localItems);
          System.out.println("position in button is " + i);
          if (SendAdapter.selected.contains(Integer.valueOf(Integer.parseInt(((Items)SendAdapter.this.data.get(i)).getId()))))
          {
            SendAdapter.selected.remove(SendAdapter.selected.indexOf(Integer.valueOf(Integer.parseInt(((Items)SendAdapter.this.data.get(i)).getId()))));
            SendAccountInfoActivity.opened_ids.remove(SendAccountInfoActivity.opened_ids.indexOf(Integer.valueOf(Integer.parseInt(((Items)SendAdapter.this.data.get(i)).getId()))));
            ((Items)SendAdapter.this.data.get(i)).setVisible(8);
            this.val$holder.layout.setVisibility(8);
            return;
          }
          SendAdapter.selected.add(Integer.valueOf(Integer.parseInt(((Items)SendAdapter.this.data.get(i)).getId())));
          SendAccountInfoActivity.opened_ids.add(Integer.valueOf(Integer.parseInt(((Items)SendAdapter.this.data.get(i)).getId())));
          ((Items)SendAdapter.this.data.get(this.val$position)).setVisible(0);
          this.val$holder.layout.setVisibility(0);
          System.out.println("in onClick else part");
        }
      });
      localView.setTag(localHolder1);
      localHolder1.checkBox.setTag(this.data.get(paramInt));
      localHolder1.button.setTag(this.data.get(paramInt));
      localHolder2 = (Holder)localView.getTag();
      localHolder2.textView.setText(((Items)this.data.get(paramInt)).getName());
      localHolder2.accountNo.setText((CharSequence)((List)this.childData.get(((Items)this.data.get(paramInt)).getName())).get(0));
      localHolder2.bankName.setText((CharSequence)((List)this.childData.get(((Items)this.data.get(paramInt)).getName())).get(1));
      localHolder2.location.setText((CharSequence)((List)this.childData.get(((Items)this.data.get(paramInt)).getName())).get(2));
      if (SendAccountInfoActivity.select_all_flag != 1)
        break label509;
      ((Items)this.data.get(paramInt)).setChecked(true);
      localHolder2.checkBox.setChecked(true);
    }
    while (true)
    {
      localHolder2.layout.setVisibility(8);
      if (selected.contains(Integer.valueOf(Integer.parseInt(((Items)this.data.get(paramInt)).getId()))))
      {
        ((Items)this.data.get(paramInt)).setVisible(0);
        localHolder2.layout.setVisibility(0);
      }
      return localView;
      localView = paramView;
      ((Holder)localView.getTag()).checkBox.setTag(this.data.get(paramInt));
      ((Holder)localView.getTag()).button.setTag(this.data.get(paramInt));
      break;
      label509: if (SendAccountInfoActivity.select_all_flag == 2)
      {
        if (paramInt == -1 + this.data.size())
          SendAccountInfoActivity.select_all_flag = 0;
        ((Items)this.data.get(paramInt)).setChecked(false);
        localHolder2.checkBox.setChecked(false);
        continue;
      }
      localHolder2.checkBox.setChecked(((Items)this.data.get(paramInt)).isChecked());
    }
  }

  static class Holder
  {
    public TextView accountNo;
    public TextView bankName;
    public ImageButton button;
    private CheckBox checkBox;
    public LinearLayout layout;
    public TextView location;
    public TextView textView;
  }
}

/* Location:           C:\Users\Yash\Desktop\apk to src\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.girnar.online_digital_diary.adapters.SendAdapter
 * JD-Core Version:    0.6.0
 */
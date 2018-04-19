package com.girnar.online_digital_diary.adapters;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.girnar.online_digital_diary.database.DbHelper;
import com.girnar.online_digital_diary.recievers.NotificationReceiver;
import com.girnar.online_digital_diary.ui.BookmarkShowActivity;
import com.girnar.online_digital_diary.ui.OtherInfoShowActivity;
import com.girnar.online_digital_diary.ui.PersonShowActivity;
import com.girnar.online_digital_diary.ui.UpdateAccountInfoActivity;
import com.girnar.online_digital_diary.ui.UpdateOtherInformationActivity;
import com.girnar.online_digital_diary.ui.UpdatePersonActivity;
import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<String>
  implements View.OnClickListener
{
  public static ImageView imageView;
  public static ImageView imageView_delete;
  private static int selected_id;
  private final Activity activity;
  private ArrayList<Long> aniv_millis = new ArrayList();
  private final ArrayList<String> data;
  private final DbHelper dbHelper;
  private final ArrayList<String> ids;
  private ArrayList<Long> millis = new ArrayList();
  private final String table_id;
  private final String table_name;

  public CustomAdapter(Activity paramActivity, ArrayList<String> paramArrayList1, ArrayList<String> paramArrayList2, String paramString1, String paramString2, ArrayList<Long> paramArrayList3, ArrayList<Long> paramArrayList4)
  {
    super(paramActivity, 2130903065, paramArrayList1);
    this.activity = paramActivity;
    this.data = paramArrayList1;
    this.ids = paramArrayList2;
    this.table_name = paramString1;
    this.table_id = paramString2;
    this.millis = paramArrayList3;
    this.aniv_millis = paramArrayList4;
    this.dbHelper = new DbHelper(paramActivity);
    selected_id = -1;
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    View localView = paramView;
    if (localView == null)
    {
      Holder localHolder1 = new Holder();
      localView = this.activity.getLayoutInflater().inflate(2130903065, null);
      localHolder1.textView = ((TextView)localView.findViewById(2131493044));
      localHolder1.imageView = ((ImageView)localView.findViewById(2131493043));
      localHolder1.imageView_delete = ((ImageView)localView.findViewById(2131493042));
      localView.setTag(localHolder1);
    }
    Holder localHolder2 = (Holder)localView.getTag();
    localHolder2.textView.setText((CharSequence)this.data.get(paramInt));
    localHolder2.textView.setTag(Integer.valueOf(paramInt));
    localHolder2.imageView.setTag(Integer.valueOf(paramInt));
    localHolder2.imageView_delete.setTag(Integer.valueOf(paramInt));
    localHolder2.textView.setOnLongClickListener(new View.OnLongClickListener(localHolder2)
    {
      public boolean onLongClick(View paramView)
      {
        int i = ((Integer)this.val$holder1.imageView.getTag()).intValue();
        String str = (String)CustomAdapter.this.ids.get(i);
        if (CustomAdapter.this.table_name == "OTHERINFO")
        {
          Intent localIntent1 = new Intent(CustomAdapter.this.activity, OtherInfoShowActivity.class);
          localIntent1.putExtra("id", Integer.parseInt(str));
          CustomAdapter.this.activity.startActivity(localIntent1);
        }
        while (true)
        {
          return true;
          if (CustomAdapter.this.table_name == "PERSONALINFORMATION")
          {
            Intent localIntent2 = new Intent(CustomAdapter.this.activity, PersonShowActivity.class);
            localIntent2.putExtra("id", Integer.parseInt(str));
            CustomAdapter.this.activity.startActivity(localIntent2);
            continue;
          }
          if (CustomAdapter.this.table_name != "BOOKMARK")
            continue;
          Intent localIntent3 = new Intent(CustomAdapter.this.activity, BookmarkShowActivity.class);
          localIntent3.putExtra("id", Integer.parseInt(str));
          CustomAdapter.this.activity.startActivity(localIntent3);
        }
      }
    });
    localHolder2.textView.setOnClickListener(new View.OnClickListener(localHolder2)
    {
      public void onClick(View paramView)
      {
        CustomAdapter.selected_id = ((Integer)this.val$holder1.textView.getTag()).intValue();
        if (CustomAdapter.imageView != null)
          CustomAdapter.imageView.setVisibility(8);
        if (CustomAdapter.imageView_delete != null)
          CustomAdapter.imageView_delete.setVisibility(8);
        this.val$holder1.imageView.setVisibility(0);
        this.val$holder1.imageView_delete.setVisibility(0);
        this.val$holder1.imageView_delete.setOnClickListener(CustomAdapter.this);
        this.val$holder1.imageView.setOnClickListener(new View.OnClickListener(this.val$holder1)
        {
          public void onClick(View paramView)
          {
            int i = ((Integer)this.val$holder1.imageView.getTag()).intValue();
            if (CustomAdapter.this.table_name == "OTHERINFO")
            {
              Intent localIntent1 = new Intent(CustomAdapter.this.activity, UpdateAccountInfoActivity.class);
              localIntent1.putExtra("id", Integer.parseInt((String)CustomAdapter.this.ids.get(i)));
              CustomAdapter.this.activity.startActivity(localIntent1);
              CustomAdapter.this.activity.overridePendingTransition(2130968576, 2130968577);
            }
            do
            {
              return;
              if (CustomAdapter.this.table_name != "PERSONALINFORMATION")
                continue;
              Intent localIntent2 = new Intent(CustomAdapter.this.activity, UpdatePersonActivity.class);
              localIntent2.putExtra("id", Integer.parseInt((String)CustomAdapter.this.ids.get(i)));
              CustomAdapter.this.activity.startActivity(localIntent2);
              CustomAdapter.this.activity.overridePendingTransition(2130968576, 2130968577);
              return;
            }
            while (CustomAdapter.this.table_name != "BOOKMARK");
            Intent localIntent3 = new Intent(CustomAdapter.this.activity, UpdateOtherInformationActivity.class);
            localIntent3.putExtra("id", Integer.parseInt((String)CustomAdapter.this.ids.get(i)));
            CustomAdapter.this.activity.startActivity(localIntent3);
            CustomAdapter.this.activity.overridePendingTransition(2130968576, 2130968577);
          }
        });
        CustomAdapter.imageView = this.val$holder1.imageView;
        CustomAdapter.imageView_delete = this.val$holder1.imageView_delete;
      }
    });
    if (selected_id == paramInt)
    {
      localHolder2.imageView.setVisibility(0);
      localHolder2.imageView_delete.setVisibility(0);
      return localView;
    }
    localHolder2.imageView.setVisibility(8);
    localHolder2.imageView_delete.setVisibility(8);
    return localView;
  }

  public void onClick(View paramView)
  {
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(this.activity);
    localBuilder.setMessage(this.activity.getString(2131034279)).setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener(paramView)
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        int i = ((Integer)this.val$v.getTag()).intValue();
        int j = Integer.parseInt((String)CustomAdapter.this.ids.get(i));
        ((String)CustomAdapter.this.data.get(i));
        CustomAdapter.this.dbHelper.open();
        CustomAdapter.this.dbHelper.deleteMethod(j, CustomAdapter.this.table_name, CustomAdapter.this.table_id);
        CustomAdapter.this.dbHelper.close();
        if (CustomAdapter.this.table_name == "PERSONALINFORMATION")
        {
          NotificationReceiver.cancelBirthdayAlarm(CustomAdapter.this.activity, ((Long)CustomAdapter.this.millis.get(i)).longValue(), (String)CustomAdapter.this.data.get(i) + "'s Birthday today", j);
          if (CustomAdapter.this.aniv_millis.get(i) != null)
            NotificationReceiver.cancelBirthdayAlarm(CustomAdapter.this.activity, ((Long)CustomAdapter.this.aniv_millis.get(i)).longValue(), (String)CustomAdapter.this.data.get(i) + "'s Anniversary today", j * 1000);
          CustomAdapter.this.millis.remove(i);
          CustomAdapter.this.aniv_millis.remove(i);
        }
        CustomAdapter.this.ids.remove(i);
        CustomAdapter.this.data.remove(i);
        CustomAdapter.this.notifyDataSetChanged();
      }
    }).setNegativeButton("No", new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        paramDialogInterface.cancel();
      }
    });
    localBuilder.create();
    localBuilder.show();
  }

  static class Holder
  {
    public ImageView imageView;
    public ImageView imageView_delete;
    public TextView textView;
  }
}

/* Location:           C:\Users\Yash\Desktop\apk to src\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.girnar.online_digital_diary.adapters.CustomAdapter
 * JD-Core Version:    0.6.0
 */
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
import com.girnar.online_digital_diary.ui.RemShowInfoActivity;
import com.girnar.online_digital_diary.ui.UpdateReminderActivity;
import java.util.ArrayList;

public class ReminderAdapter extends ArrayAdapter<String>
  implements View.OnClickListener
{
  public static ImageView imageView;
  public static ImageView imageView_delete;
  private static int selected_id;
  private final Activity activity;
  private final ArrayList<String> data;
  private final DbHelper dbHelper;
  private ArrayList<String> descs = new ArrayList();
  private final ArrayList<String> ids;
  private ArrayList<Long> millis = new ArrayList();
  private final String table_id;
  private final String table_name;

  public ReminderAdapter(Activity paramActivity, ArrayList<String> paramArrayList1, ArrayList<String> paramArrayList2, String paramString1, String paramString2, ArrayList<Long> paramArrayList, ArrayList<String> paramArrayList3)
  {
    super(paramActivity, 2130903065, paramArrayList1);
    this.activity = paramActivity;
    this.data = paramArrayList1;
    this.ids = paramArrayList2;
    this.table_name = paramString1;
    this.table_id = paramString2;
    this.millis = paramArrayList;
    this.descs = paramArrayList3;
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
        String str = (String)ReminderAdapter.this.ids.get(i);
        Intent localIntent = new Intent(ReminderAdapter.this.activity, RemShowInfoActivity.class);
        localIntent.putExtra("id", Integer.parseInt(str));
        ReminderAdapter.this.activity.startActivity(localIntent);
        return true;
      }
    });
    localHolder2.textView.setOnClickListener(new View.OnClickListener(localHolder2)
    {
      public void onClick(View paramView)
      {
        ReminderAdapter.selected_id = ((Integer)this.val$holder1.textView.getTag()).intValue();
        if (ReminderAdapter.imageView != null)
          ReminderAdapter.imageView.setVisibility(8);
        if (ReminderAdapter.imageView_delete != null)
          ReminderAdapter.imageView_delete.setVisibility(8);
        this.val$holder1.imageView.setVisibility(0);
        this.val$holder1.imageView_delete.setVisibility(0);
        this.val$holder1.imageView.setOnClickListener(new View.OnClickListener(this.val$holder1)
        {
          public void onClick(View paramView)
          {
            int i = ((Integer)this.val$holder1.imageView.getTag()).intValue();
            Intent localIntent = new Intent(ReminderAdapter.this.activity, UpdateReminderActivity.class);
            localIntent.putExtra("id", Integer.parseInt((String)ReminderAdapter.this.ids.get(i)));
            ReminderAdapter.this.activity.startActivity(localIntent);
            ReminderAdapter.this.activity.overridePendingTransition(2130968576, 2130968577);
          }
        });
        this.val$holder1.imageView_delete.setOnClickListener(ReminderAdapter.this);
        ReminderAdapter.imageView = this.val$holder1.imageView;
        ReminderAdapter.imageView_delete = this.val$holder1.imageView_delete;
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
        int j = Integer.parseInt((String)ReminderAdapter.this.ids.get(i));
        ((String)ReminderAdapter.this.data.get(i));
        ReminderAdapter.this.dbHelper.open();
        ReminderAdapter.this.dbHelper.deleteMethod(j, ReminderAdapter.this.table_name, ReminderAdapter.this.table_id);
        ReminderAdapter.this.dbHelper.close();
        NotificationReceiver.cancelAllAlarm(ReminderAdapter.this.activity, ((Long)ReminderAdapter.this.millis.get(i)).longValue(), (String)ReminderAdapter.this.descs.get(i), j);
        ReminderAdapter.this.ids.remove(i);
        ReminderAdapter.this.data.remove(i);
        ReminderAdapter.this.millis.remove(i);
        ReminderAdapter.this.descs.remove(i);
        ReminderAdapter.this.notifyDataSetChanged();
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
 * Qualified Name:     com.girnar.online_digital_diary.adapters.ReminderAdapter
 * JD-Core Version:    0.6.0
 */
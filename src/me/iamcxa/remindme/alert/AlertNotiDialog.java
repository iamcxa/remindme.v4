package me.iamcxa.remindme.alert;

import common.MyCalendar;
import common.MyPreferences;

import me.iamcxa.remindme.R;
import me.iamcxa.remindme.RemindmeMainActivity;
import me.iamcxa.remindme.R.drawable;
import me.iamcxa.remindme.R.layout;
import me.iamcxa.remindme.editor.CommonEditorVar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TabHost.OnTabChangeListener;

/**
 * @author iamcxa 提醒方法
 */
public class AlertNotiDialog extends Activity {

	private static NotificationManager nm;
	private static Notification noti;
	public static final int ID = 1;
	
	String text;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alertdialoglayout);

//		Intent intentmain = new Intent(this, RemindmeMainActivity.class);
//		
//		PendingIntent contentIntentmain = PendingIntent.getActivity(this, 0,
//				intentmain, Intent.FLAG_ACTIVITY_NEW_TASK);
//		
//		MyPreferences.mPreferences= PreferenceManager.getDefaultSharedPreferences(this);
//				
//		long[] tVibrate = {0,100,200,300,400,500};
//		
//		Bitmap bm = BitmapFactory.decodeResource(getResources(),
//				R.drawable.ic_action_alarms);
//		
//		Notification noti = new Notification.Builder(this)
//		.setContentTitle("待辦任務到期：")
//		.setContentText("點這裡查看")
//		.setContentInfo("ContentInfo")
//        .addAction(R.drawable.ic_action_alarms, "延遲", contentIntentmain)
//        .addAction(R.drawable.ic_action_accept, "完成", null)
//		.setNumber(1)
//		.setAutoCancel(true)
//		.setSmallIcon(R.drawable.remindme_logo)
//		.setLargeIcon(bm)
//		.setWhen(System.currentTimeMillis())
//		//.setFullScreenIntent(contentIntent, false)
//		.setContentIntent(contentIntentmain)
//		.setVibrate(tVibrate)
//		.setSound(Uri.parse(MyPreferences.mPreferences.getString("ringtonePref", getFilesDir().getAbsolutePath()+"/fallbackring.ogg")))
//		.build();
//		
//        NotificationManager nNotificationManager = 
//        		(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//		nNotificationManager.notify(1, noti);
		
//		new AlertDialog.Builder(this)
//		.setMessage("收到訊息!")
//		.setPositiveButton("確定", new DialogInterface.OnClickListener(){
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				// TODO Auto-generated method stub
//				finish();
//			}
//		})
//		.show();
		
		//new CustomDialog(getApplicationContext()).show();
	}

}
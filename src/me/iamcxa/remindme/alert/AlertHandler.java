package me.iamcxa.remindme.alert;

import tw.remindme.common.function.MyDebug;
import tw.remindme.common.function.MyPreferences;
import me.iamcxa.remindme.R;
import me.iamcxa.remindme.RemindmeMainActivity;
import me.iamcxa.remindme.database.columns.ColumnTask;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class AlertHandler extends IntentService {


	public static AlertHandler alertHandler = new AlertHandler();

	public static AlertHandler getInstance(){
		return alertHandler;
	}

	public AlertHandler() {
		super(null);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub

		Bundle b = intent.getExtras();

		String taskID=b.getString("taskID");

		MyDebug.MakeLog(2,"@alertHandler taskID="+ taskID);

		setNotification(this,taskID);
	}

	//
	public String getTaskName(Context context,String taskID){
		String args[]={ taskID };
		Cursor c= context.getContentResolver().
				query(ColumnTask.URI, ColumnTask.PROJECTION
						, "_id = "+taskID,  null
						, ColumnTask.DEFAULT_SORT_ORDER);		

		String data = null;
		if (c != null) {
			c.moveToFirst();
			data = c.getString(ColumnTask.KEY.INDEX.title);
			MyDebug.MakeLog(2,"getTaskName="+data);
			c.close();
		}
		
		return data;		
	}

	//
	public void setNotification(Context context, String taskID){

		Intent intentMain = new Intent(context, RemindmeMainActivity.class);
		intentMain.putExtra("taskID", taskID);
		PendingIntent pedingIntentMain = PendingIntent.getActivity(context, 0,
				intentMain, Intent.FLAG_ACTIVITY_NEW_TASK);

		Intent intentDialog = new Intent(context, AlertNotiDialog.class);
		intentDialog.putExtra("taskID", taskID);
		PendingIntent pedingIntentDialog = PendingIntent.getActivity(context, 0,
				intentDialog, Intent.FLAG_ACTIVITY_NEW_TASK);

		Intent intentDelay = new Intent(context, DelayTheAlert.class);
		intentDelay.putExtra("taskID", taskID);
		PendingIntent pedingIntentDelay = PendingIntent.getService(context, 0,
				intentDelay, Intent.FLAG_ACTIVITY_NEW_TASK);

		Intent intentFinish = new Intent(context, FinishTheAlert.class);
		intentFinish.putExtra("taskID", taskID);
		PendingIntent pedingIntentFinish = PendingIntent.getService(context, 0,
				intentFinish, Intent.FLAG_ACTIVITY_NEW_TASK);
		
		MyPreferences.mPreferences= PreferenceManager.getDefaultSharedPreferences(context);

		long[] tVibrate = {0,100,200,300,400,500};

		Bitmap bm = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.ic_action_alarms);

		Notification noti = new Notification.Builder(context)
		.setContentTitle("待辦任務到期："+getTaskName(context,taskID))
		.setContentText("點這裡查看")
		//.setContentInfo("ContentInfo")
		.addAction(R.drawable.ic_action_alarms, "延遲", pedingIntentDelay)
		.addAction(R.drawable.ic_action_accept, "完成", pedingIntentFinish)
		.setNumber(1)
		.setAutoCancel(true)
		.setSmallIcon(R.drawable.remindme_logo)
		.setLargeIcon(bm)
		.setWhen(System.currentTimeMillis())
		//.setFullScreenIntent(pedingIntentDialog, true)
		.setContentIntent(pedingIntentMain)
		.setVibrate(tVibrate)
		.setSound(Uri.parse(MyPreferences.mPreferences.getString("ringtonePref", context.getFilesDir().getAbsolutePath()+"/fallbackring.ogg")))
		.build();

		NotificationManager nm = 
				(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		nm.notify("remindme",Integer.valueOf(taskID), noti);

	}
	
	public void ShowToastInIntentService(final String sText)
	{  final Context MyContext = this;
	new Handler(Looper.getMainLooper()).post(new Runnable()
	{  @Override public void run()
	{  Toast toast1 = Toast.makeText(MyContext, sText, Toast.LENGTH_LONG);
	toast1.show(); 
	}
	});
	};
	
}

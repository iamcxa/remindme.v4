package me.iamcxa.remindme.alert;

import java.io.ObjectInputStream.GetField;

import me.iamcxa.remindme.R;
import me.iamcxa.remindme.RemindmeMainActivity;
import me.iamcxa.remindme.database.ColumnTask;
import me.iamcxa.remindme.editor.CommonEditorVar;

import common.MyDebug;
import common.MyPreferences;

import android.R.integer;
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
import android.preference.PreferenceManager;

public class AlertHandler extends IntentService {


	public static AlertHandler alertHandler = new AlertHandler();

	public static AlertHandler GetInstance(){
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


		setNotification(0,taskID);
	}

	//
	private String getTaskName(String taskID){
		String args[]={ taskID };
		Cursor c= getApplicationContext().getContentResolver().
				query(ColumnTask.URI, ColumnTask.PROJECTION
						, "_id = ?", args 
						, ColumnTask.DEFAULT_SORT_ORDER);		

		String data = null;
		if (c != null) {
			c.moveToFirst();
			data = c.getString(ColumnTask.KEY_INDEX.title);
			MyDebug.MakeLog(2,"getTaskName="+data);
			c.close();
		}
		
		return data;		
	}

	//
	public void setNotification(int delayTime, String taskID){

		Intent intentMain = new Intent(this, RemindmeMainActivity.class);
		intentMain.putExtra("taskID", taskID);
		PendingIntent pedingIntentMain = PendingIntent.getActivity(this, 0,
				intentMain, Intent.FLAG_ACTIVITY_NEW_TASK);

		Intent intentDelay = new Intent(this, DelayTheAlert.class);
		intentDelay.putExtra("taskID", taskID);
		PendingIntent pedingIntentDelay = PendingIntent.getActivity(this, 0,
				intentDelay, Intent.FLAG_ACTIVITY_NEW_TASK);

		Intent intentFinish = new Intent(this, FinishTheAlert.class);
		intentFinish.putExtra("taskID", taskID);
		PendingIntent pedingIntentFinish = PendingIntent.getActivity(this, 0,
				intentFinish, Intent.FLAG_ACTIVITY_NEW_TASK);

		Intent intentDialog = new Intent(this, AlertNotiDialog.class);
		intentFinish.putExtra("taskID", taskID);
		PendingIntent pedingIntentDialog = PendingIntent.getActivity(this, 0,
				intentDialog, Intent.FLAG_ACTIVITY_NEW_TASK);

		MyPreferences.mPreferences= PreferenceManager.getDefaultSharedPreferences(this);

		long[] tVibrate = {0,100,200,300,400,500};

		Bitmap bm = BitmapFactory.decodeResource(getResources(),
				R.drawable.ic_action_alarms);

		Notification noti = new Notification.Builder(this)
		.setContentTitle("待辦任務到期：")
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
		.setSound(Uri.parse(MyPreferences.mPreferences.getString("ringtonePref", getFilesDir().getAbsolutePath()+"/fallbackring.ogg")))
		.build();

		NotificationManager nNotificationManager = 
				(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		nNotificationManager.notify(Integer.valueOf(taskID), noti);
	}
}

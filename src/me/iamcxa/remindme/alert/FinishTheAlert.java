package me.iamcxa.remindme.alert;

import me.iamcxa.remindme.R;
import me.iamcxa.remindme.RemindmeMainActivity;

import common.MyPreferences;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class FinishTheAlert extends IntentService {

	public FinishTheAlert() {
		super(null);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		
		Bundle b = intent.getExtras();
		
		String taskID=b.getString("taskID");   
		
        NotificationManager nm = 
        		(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        
        nm.cancel("remindme",Integer.valueOf(taskID));	

		AlertHandler alertHandler=AlertHandler.getInstance();
		
        ShowToastInIntentService("任務 "+alertHandler.getTaskName(this, taskID) +"完成！");
   	
	}
	
	public void ShowToastInIntentService(final String sText)
	{  final Context MyContext = this;
	new Handler(Looper.getMainLooper()).post(new Runnable()
	{  @Override public void run()
	{  Toast toast1 = Toast.makeText(MyContext, sText, 5);
	toast1.show(); 
	}
	});
	};
	
}

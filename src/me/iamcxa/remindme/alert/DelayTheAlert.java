package me.iamcxa.remindme.alert;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class DelayTheAlert extends IntentService {

	public DelayTheAlert() {
		super(null);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub

		Bundle b = intent.getExtras();
		
		String taskID=b.getString("taskID");
		
        NotificationManager nNotificationManager = 
        		(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nNotificationManager.cancel(Integer.valueOf(taskID));
        
		//AlertHandler alertHandler=AlertHandler.GetInstance();
		
        AlertHandler alertHandler=new AlertHandler();
        
		alertHandler.setNotification(300, taskID);
		
	}

}

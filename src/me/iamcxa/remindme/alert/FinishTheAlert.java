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
import android.preference.PreferenceManager;

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
		
        NotificationManager nNotificationManager = 
        		(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nNotificationManager.cancel(Integer.valueOf(taskID));
		
	}

}

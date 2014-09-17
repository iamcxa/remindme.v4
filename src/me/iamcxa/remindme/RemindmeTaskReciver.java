package me.iamcxa.remindme;

import me.iamcxa.remindme.provider.AlarmMaker;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * @author iamcxa �w�ɴ����s��
 */
public class RemindmeTaskReciver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {

		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		intent.setClass(context, AlarmMaker.class);

		context.startActivity(intent);
		
		

	}
}
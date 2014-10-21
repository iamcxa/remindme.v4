package me.iamcxa.remindme.editor;

import java.util.Calendar;

import me.iamcxa.remindme.RemindmeReceiver_TaskAlert;
import common.MyDebug;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class Act_SetAlarm {
	Context context;
	int mYear;
	int mMonth;
	int mDay;
	int mHour;
	int mMinute;
	long alertTime,taskID;

	public Act_SetAlarm(Context context
						,long alertTime
						,long taskID) {
		// TODO Auto-generated constructor stub
		super();
		this.context = context;
		this.alertTime=alertTime;
		this.taskID=taskID;
	}

	// �]�w�q������
	public void SetIt() {
		MyDebug.MakeLog(2, "@SetAlarm");

		// �O�s���e�B����P�ɶ��r��
		//String content = null;

		final String BC_ACTION = "me.iamcxa.remindme.TaskReceiver";

		// ���oAlarmManager���
		final AlarmManager am = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);

		// ��Ҥ�Intent
		Intent intent = new Intent(context,RemindmeReceiver_TaskAlert.class);

		
		// �]�wIntent action�ݩ�
		intent.setAction(BC_ACTION);
		intent.putExtra("msg", BC_ACTION);
		intent.putExtra("taskID", taskID);

		// ��Ҥ�PendingIntent
		final PendingIntent pi =
				PendingIntent.getBroadcast(context, 1, intent,PendingIntent.FLAG_ONE_SHOT);

		
		Calendar cal = Calendar.getInstance();
		// �]�w�� 3 ���������
		cal.add(Calendar.SECOND, 10);
	
		
		// ���o�t�ήɶ�
		final long time1 = System.currentTimeMillis();
		//Calendar c = Calendar.getInstance();

		//c.set(mYear, mMonth, mDay, mHour, mMinute);
		//long time2 = c.getTimeInMillis();
		if ( (alertTime - time1) > 0) {
			
			am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pi);

			//MyDebug.MakeLog(2, "@SetAlarm set="+alertTime);
			MyDebug.MakeLog(2, "@SetAlarm alertTime="+alertTime);
			MyDebug.MakeLog(2, "@SetAlarm cal 1min="+cal.getTimeInMillis());
		} else {
			MyDebug.MakeLog(2, "@SetAlarm set failed");
			am.cancel(pi);
		}

	}

}




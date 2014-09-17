package me.iamcxa.remindme.editor;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class SetAlarm {
	Context context;
	int mYear;
	int mMonth;
	int mDay;
	int mHour;
	int mMinute;

	public SetAlarm(Context context) {
		// TODO Auto-generated constructor stub
		super();
		this.context = context;
	}

	// �]�w�q������
	public void SetIt(boolean flag) {

		// �O�s���e�B����P�ɶ��r��
		String content = null;

		final String BC_ACTION = "me.iamcxa.remindme.TaskReceiver";

		// ���oAlarmManager���
		final AlarmManager am = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);

		// ��Ҥ�Intent
		Intent intent = new Intent();

		// �]�wIntent action�ݩ�
		intent.setAction(BC_ACTION);
		intent.putExtra("msg", content);

		// ��Ҥ�PendingIntent
		final PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent,
				0);

		// ���o�t�ήɶ�
		final long time1 = System.currentTimeMillis();
		Calendar c = Calendar.getInstance();

		c.set(mYear, mMonth, mDay, mHour, mMinute);
		long time2 = c.getTimeInMillis();
		if (flag && (time2 - time1) > 0) {
			am.set(AlarmManager.RTC_WAKEUP, time2, pi);
		} else {
			am.cancel(pi);
		}

	}

}

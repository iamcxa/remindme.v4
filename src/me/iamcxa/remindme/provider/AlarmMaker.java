package me.iamcxa.remindme.provider;

import common.MyDebug;
import common.CommonVar;
import common.MyPreferences;

import me.iamcxa.remindme.R;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author iamcxa ������k
 */
public class AlarmMaker extends Activity {
	public static final int ID = 1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


		setContentView(R.layout.alertdialoglayout);
		// ���oButton��TextView���
		Button btn = (Button) findViewById(R.id.buttonSubmit);
		TextView tv = (TextView) findViewById(R.id.textView1);

		// ���oNotificationManager���
		String service = Context.NOTIFICATION_SERVICE;
		final NotificationManager nm = (NotificationManager) getSystemService(service);
		// ��Ҥ�Notification
		Notification n = new Notification();
		// �]�w��ܴ��ܰT���A�P����ܦb���A�C
		String msg = getIntent().getStringExtra("msg");
		// ��ܮɶ�
		n.tickerText = msg;
		tv.setText(msg);

		// �]�w�y������
		MyPreferences.mPreferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		MyDebug.MakeLog(0, this.getFilesDir().getAbsolutePath()+"/fallbackring.ogg");
		n.sound = Uri.parse(MyPreferences.mPreferences.getString("ringtonePref", this.getFilesDir().getAbsolutePath()+"/fallbackring.ogg"));
		
		// �o�X�q��
		nm.notify(ID, n);
		// �����q��
		btn.setOnClickListener(new OnClickListener() {
			// @Override
			@Override
			public void onClick(View v) {
				nm.cancel(ID);
				finish();
			}
		});

	}

}

package me.iamcxa.remindme;


import common.MyDebug;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

/**
 * @author iamcxa �w�ɴ����s��
 */
public class RemindmeReceiver_BootCompleted extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		
	
		 Bundle bData = intent.getExtras();
//		 
	        if(bData.get("msg").equals("android.intent.action.BOOT_COMPLETED")) {

	    		MyDebug.MakeLog(2, "���z�����I�}���Ұʧ����I");
	    		
	        }else if(bData.get("msg").equals("android.net.wifi.supplicant.STATE_CHANGE")) {

	    		MyDebug.MakeLog(2,  "���z�����Iwifi���A���ܡI");
	        	
			}else if(bData.get("msg").equals("android.net.nsd.STATE_CHANGED")) {

				MyDebug.MakeLog(2,  "���z�����I�����s�u���A���ܡI");
				
			}
//	        
//
//		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

//		intent.setClass(context, TaskSortingService.class);
//		
//		MyDebug.MakeLog(0,"�ǳƱҰ�TaskSortingService");
//		try {
//			context.startActivity(intent);
//
//			MyDebug.MakeLog(0,"TaskSortingService �Ұʧ���");
//		} catch (Exception e) {
//			// TODO: handle exception
//			MyDebug.MakeLog(0,"�Ұ�TaskSortingService���ѡIerror="+e.toString());
//		}
	}
}

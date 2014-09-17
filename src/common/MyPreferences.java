package common;

import android.content.SharedPreferences;

public class MyPreferences {
	
	private MyPreferences(){}

	// SharedPreferences preferences;
	public static SharedPreferences mPreferences;

	// isServiceOn
	public static boolean IS_SERVICE_ON() {
		return mPreferences.getBoolean("isServiceOn", true);
	};

	// isServiceOn
	public static boolean IS_SORTING_ON() {
		return mPreferences.getBoolean("isSortingOn", true);
	};

	// isServiceOn
	public static String getUpdatePeriod() {
		return mPreferences.getString("GetPriorityPeriod", "5000");
	};	
	
	// debug msg on/off, read from Shared Preferences XML file
	public static boolean IS_DEBUG_MSG_ON() {
		return mPreferences.getBoolean("isDebugMsgOn", true);
	}
	

	
	public static class GpsSetting {
		// GPS�W���������wifi
		public static final int TIMEOUT_SEC = 5;
		// Gps���A
		public static boolean GpsStatus = false;

		// ���ʶZ��
		public static final double GpsTolerateErrorDistance = 1.5;

	}
}

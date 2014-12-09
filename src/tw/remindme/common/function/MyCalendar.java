package tw.remindme.common.function;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MyCalendar {

	private MyCalendar() {
	}

	/**
	 * @return ���o���ѻP��J����۶Z�h�[
	 * @param {TaskDate} �n��諸���
	 * @param {Option} ��J�O�_�t�� HH:mm
	 */
	public static long getDaysLeft(String TaskDate, int Option) {
		// �w�q�ɶ��榡
		// java.text.SimpleDateFormat sdf = new
		SimpleDateFormat sdf = null;
		if (Option == 1) {
			sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm",Locale.getDefault());
		} else if (Option == 2) {
			sdf = new SimpleDateFormat("yyyy/MM/dd",Locale.getDefault());
		}

		// ���o�{�b�ɶ�
		//Date now = new Date();
		String nowDate = sdf.format(getNow());
		MyDebug.MakeLog(0, "now:" + nowDate + ", task:" + TaskDate);
		try {
			// ���o�ƥ�ɶ��P�{�b�ɶ�
			Date dt1 = sdf.parse(nowDate);
			//Date dt2 = sdf.parse(TaskDate);

			// ���o��Ӯɶ���Unix�ɶ�
			Long ut1 = dt1.getTime();
			//Long ut2 = dt2.getTime();

			Long timeP = Long.valueOf(TaskDate) - ut1;// �@���t
			// �۴���o��Ӯɶ��t�Z���@��
			// Long sec = timeP / 1000;// ���t
			// Long min = timeP / 1000 * 60;// ���t
			// Long hr = timeP / 1000 * 60 * 60;// �ɮt
			Long day = timeP / (1000 * 60 * 60 * 24);// ��t
			MyDebug.MakeLog(0, "Get days left Sucessed! " + day);
			return day;
		} catch (Exception e) {
			// TODO: handle exception
			MyDebug.MakeLog(999, e.toString());
			return -1;
		}
	}
	
	/**
	 * @return ���o����n�Ѫ��@����
	 * @param {Days} ���ӤѼ�, 0�Y������
	 */
	public static long getNextFewDays(int Days) {
		long NextFewDays=System.currentTimeMillis()+( 60 * 60 * 24 * 1000 * Days);//N�Ѫ��@����;
		return NextFewDays;

	}
	
	/**
	 * @return ���o�{�b���U���@����
	 */
	public static long getNow() {
		long curDate=System.currentTimeMillis();
		return curDate;
	}
	
	/**
	 * @return �N��J�����@�����ഫ����r YYYY/MM/DD ���X 
	 * @param {A_Time_In_Milliseconds} �n�Q�ഫ���@����
	 * @param {IsNeedHourAndMins} �O�_�ݭn��X�p��/����
	 */
	public static String getDate_From_TimeMillis(boolean IsNeedHourAndMins,long A_Time_In_Milliseconds ) {
		SimpleDateFormat sdf = null;
		if (IsNeedHourAndMins) {
			sdf = new SimpleDateFormat("yyyy/MM/dd_HH:mm",getDefaultLocal());
		} else  {
			sdf = new SimpleDateFormat("yyyy/MM/dd",getDefaultLocal());
		}
		String nowDate = sdf.format(A_Time_In_Milliseconds);
		MyDebug.MakeLog(1, "MyCalendar Millis->Date function\n,start with raw long="+A_Time_In_Milliseconds+", result="+nowDate);
		return nowDate;
	}
	
	/**
	 * @return �ѿ�J�� YYYY/MM/DD �ഫ���@���ƿ�X
	 * @param {A_Date} �n�Q�ഫ���@����
	 */
	public static long getTimeMillis_From_Date(String A_Date) {
		String[] mTimeMillis={""};
		MyDebug.MakeLog(0, "A_Date="+A_Date);
		if (A_Date.contains("_")) {
			String[] nTimeMillis = A_Date.split("_");
			MyDebug.MakeLog(0,"nTimeMillis="+ nTimeMillis[0]);
			mTimeMillis=nTimeMillis[0].split("/");
		}else {
		 mTimeMillis=A_Date.split("/");
		}
		MyDebug.MakeLog(0, "mTimeMillis[]='"+mTimeMillis[0]+","+mTimeMillis[1]+","+mTimeMillis[2]+"'");
		Calendar calendar=Calendar.getInstance();
		calendar.set(Integer.valueOf(mTimeMillis[0]),
				Integer.valueOf(mTimeMillis[1]), 
				Integer.valueOf(mTimeMillis[2]),
				0,0,0);
		
		return (calendar.getTimeInMillis())/1000;
	}
	
	public static String getTodayString(int ExtraDaysIfNeeded) {
		Calendar today = Calendar.getInstance();
		today.add(Calendar.DAY_OF_MONTH, ExtraDaysIfNeeded);
		int dayOfMonth = today.get(Calendar.DAY_OF_MONTH);
		String month = String.valueOf(today.get(Calendar.MONTH)+1);
		int year=today.get(Calendar.YEAR);
		if (String.valueOf(month).length()==1){
			month="0"+month;
		}
		return year+"/"+month+"/"+dayOfMonth;
	}	
	
	public static String getThisMonth() {
		Calendar today = Calendar.getInstance();
		String month = String.valueOf(today.get(Calendar.MONTH)+1);
		if (String.valueOf(month).length()==1){
			month="0"+month;
		}
		return month;
	}
	
	public static String getThisYear() {
		Calendar today = Calendar.getInstance();
		int year=today.get(Calendar.YEAR);
		return String.valueOf(year);
	}	
	
	public static String getThisDay() {
		Calendar today = Calendar.getInstance();
		int dayOfMonth = today.get(Calendar.DAY_OF_MONTH);
		return String.valueOf(dayOfMonth);
	}
	
	public static String getThisHour() {
		Calendar today = Calendar.getInstance();
		int dayOfMonth = today.get(Calendar.HOUR_OF_DAY);
		return String.valueOf(dayOfMonth);
	}

	public static String getThisMinutes() {
		Calendar today = Calendar.getInstance();
		int dayOfMonth = today.get(Calendar.MINUTE);
		return String.valueOf(dayOfMonth);
	}
	
	// Get the defaulf Local of android
	public static  Locale getDefaultLocal(){
		CommonVar.DEFAULT_LOCAL=Locale.getDefault();
		return CommonVar.DEFAULT_LOCAL;	
	}
}
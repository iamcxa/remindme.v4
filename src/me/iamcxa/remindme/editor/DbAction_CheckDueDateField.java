package me.iamcxa.remindme.editor;

import common.MyDebug;

import common.CommonVar;

public class DbAction_CheckDueDateField {

	private DbAction_CheckDueDateField(){
	}	

	private  String[] basicStringArray=CommonVar.TASKEDITOR_DUEDATE_BASIC_STRING_ARRAY;
	private  String[] extraStringArray=CommonVar.TASKEDITOR_DUEDATE_EXTRA_STRING_ARRAY;
	private static  String rawTaskDueDateString="null";
	private static  String processedTaskDueDateString="null";
	private  boolean dailyEvent=false;
	private  boolean weelkyEvent=false;

	//-------------------- Public method --------------------//
	public String getProcessedTaskDueDateString(String rawTaskDueDateString){
		switch (checkDueDateFormat(rawTaskDueDateString)) {
		case 0:// �¤���Gyyyy/mm/dd
			
			break;
		
		case 1:// �Gyyyy/mm/dd
			
			break;

		default:
			break;
		}checkDueDateFormat(rawTaskDueDateString);


		return processedTaskDueDateString;
	}

	//-------------------- Private method --------------------//
	private  boolean hasDueDateEmpty(String rawTaskDueDateString){
		MyDebug.MakeLog(0,"hasDueDateEmpty:"+ rawTaskDueDateString.isEmpty());
		if (rawTaskDueDateString.isEmpty()){
			return true;
		}else{
			return false;
		}
	}

	private  int checkDueDateFormat(String rawTaskDueDateString) {
		int rawTaskDueDateStringFormat=0;

		if(!hasDueDateEmpty(rawTaskDueDateString)){
			getWordMeaning(rawTaskDueDateString);

		}


		return rawTaskDueDateStringFormat;
	}

	private  void getWordMeaning(String rawTaskDueDateString){
		checkRepeatability(rawTaskDueDateString);

	}

	private  void checkRepeatability(String rawTaskDueDateString) {
		for (int j = 0; j < extraStringArray.length; j++) {
			// �ˬd�r�q�O�_�t������flag
			// [0]="�U"�]�g�@,�g�G...�^
			// [1]="�C"
			if(rawTaskDueDateString.startsWith(extraStringArray[0])){
				if(j==0)weelkyEvent=true;
				else if (j==1) dailyEvent=true;
			}




			for (String string : extraStringArray) {

			}
		}
	}

	public  String getRawTaskDueDateString() {
		return rawTaskDueDateString;
	}

	public static void setRawTaskDueDateString(String rawTaskDueDateString) {
		DbAction_CheckDueDateField.rawTaskDueDateString = rawTaskDueDateString;
	}

	public static String getProcessedTaskDueDateString() {
		return processedTaskDueDateString;
	}

	public static void setProcessedTaskDueDateString(
			String processedTaskDueDateString) {
		DbAction_CheckDueDateField.processedTaskDueDateString = processedTaskDueDateString;
	}
}

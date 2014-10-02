package me.iamcxa.remindme.editor;

import java.util.Calendar;

import me.iamcxa.remindme.database.ColumnTask;
import common.MyCalendar;
import common.MyDebug;
import android.R.integer;
import android.app.AlarmManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

/**
 * @author Kent
 * @version 20140930
 */
public class Act_SaveToDb {
	private static CommonEditorVar mEditorVar=CommonEditorVar.GetInstance();
	private Uri taskUri=ColumnTask.URI;
	private Act_SetAlarm mSetAlarm;
	private Context context;
	private int taskId=0;
	private String TaskField_Main="";
	private String TaskField_Location="";
	private String TaskField_Alert="";
	private String TaskField_Type="";
	private String TaskField_Other="";

	public Act_SaveToDb(Context context) {
		super();
		this.context = context;
		mSetAlarm = new Act_SetAlarm(context);
		StartOver();
	}

	// ���涶��
	private void StartOver(){
		getDataFromView();
		prepareAddDataToDatabase(
				TaskField_Main,
				TaskField_Location,
				TaskField_Alert,
				TaskField_Type,
				TaskField_Other
				);
	}

	//��view������o��J��T
	private void getDataFromView(){
		// ���D�r��
		mEditorVar.Task.setTitle(TaskEditorMain.getTaskTitle());	

		// ���Ѧr��]���Ȼ����^
		mEditorVar.Task.setContent(TaskEditorMain.getTaskContent());

		// �]�w��Ʈw���(�r��)���
		mEditorVar.Task.setDueDateString(TaskEditorMain.getTaskDueDate());

		// �]�w��Ʈw���(�@��)���
		mEditorVar.TaskDate.setmDatePulsTimeMillis(getTaskDueDateTime());

		// �a�I�r�� - ID
		//mEditorVar.TaskLocation.setLocationName(locationName);
	}

	// ���o����P�ɶ��[�`�������@��
	private long getTaskDueDateTime(){
		//Act_CheckDueDateField.setRawTaskDueDateString(TaskEditorMain.getTaskDueDate());
		// ��l��
		long taskDueDateTime=0;

		// 
		int mYear=mEditorVar.TaskDate.getmYear();
		int mMonth=mEditorVar.TaskDate.getmMonth();
		int mDay=mEditorVar.TaskDate.getmDay();
		int mHour=mEditorVar.TaskDate.getmHour();
		int mMinute=mEditorVar.TaskDate.getmMinute();

		Calendar c = Calendar.getInstance();
		c.set(mYear, mMonth, mDay, mHour, mMinute);

		taskDueDateTime= c.getTimeInMillis();

		return taskDueDateTime;
	}


	//
	private void setTaskDateGroup(ContentValues values){

		//
		TaskField_Main=
						mEditorVar.Task.getTaskId()+","+		//0	  
						mEditorVar.Task.getTitle()+","+			//1
						mEditorVar.Task.getContent()+","+		//2
						mEditorVar.Task.getCreated()+","+		//3
						mEditorVar.Task.getDueDateString()+","+	//4
						mEditorVar.Task.getDueDateTime();		//5
		MyDebug.MakeLog(0,"TaskField_Main="+ TaskField_Main);

		// �s�J���ȼ��D
		String[] Split_TaskField_Main = TaskField_Main.split(",");
		taskId=Integer.valueOf(Split_TaskField_Main[0]);
		values.put(ColumnTask.KEY.title, Split_TaskField_Main[1]);
		values.put(ColumnTask.KEY.content, Split_TaskField_Main[2]);
		values.put(ColumnTask.KEY.created, Split_TaskField_Main[3]);
		values.put(ColumnTask.KEY.
				due_date_string,String.valueOf(Split_TaskField_Main[4]));
		


		TaskField_Other=
				mEditorVar.TaskOther.getCollaborator()+","+					//0
						mEditorVar.TaskOther.getGoogle_cal_sync_id()+","+	//1
						mEditorVar.TaskOther.getTask_color();				//2
		MyDebug.MakeLog(0,"TaskField_Other="+ TaskField_Other);
		
		

		TaskField_Type=
				mEditorVar.TaskType.getPriority()+","+						//0
						mEditorVar.TaskType.getCategory()+","+				//1
						mEditorVar.TaskType.getTag()+","+					//2
						mEditorVar.TaskType.getLevel();						//3
		MyDebug.MakeLog(0,"TaskField_Type="+ TaskField_Type);
		


		TaskField_Location=
				mEditorVar.TaskLocation.getCoordinate()+","+				//0
						mEditorVar.TaskLocation.getLocationName()+","+		//1
						mEditorVar.TaskLocation.getDistance();				//2
		MyDebug.MakeLog(0,"TaskField_Location="+ TaskField_Location);
		


		TaskField_Alert=
				mEditorVar.TaskAlert .getAlertInterval()+","+	
						mEditorVar.TaskAlert.getAlertTime();
		MyDebug.MakeLog(0,"TaskField_Alert="+ TaskField_Alert);
	}


	// �N�r��}�C���
	private void prepareAddDataToDatabase(
			String TaskField_Main,
			String TaskField_Location,
			String TaskField_Alert,
			String TaskField_Type,
			String TaskField_Other) {

		ContentValues values = new ContentValues();
		values.clear();

		setTaskDateGroup(values);

		isSaveOrUpdate(values, taskId);
	}

	// �P�_�����ާ@�O�g�J�s��ƩΧ�s�w�s�b���
	private boolean isSaveOrUpdate(ContentValues values, int taskId) {
		if (taskId != 0) {
			return UpdateIt(values, taskId);
		} else {
			return SaveIt(values);
		}		
	}

	// �g�J�s���
	private boolean SaveIt(ContentValues values) {
		try {			
			values.put(ColumnTask.KEY.created, String.valueOf(MyCalendar.getToday()));
			context.getContentResolver().insert(taskUri, values);
			Toast.makeText(context, "�s�ƶ��w�g�x�s", Toast.LENGTH_SHORT).show();
			mSetAlarm.SetIt(true);
			return true;
		} catch (Exception e) {
			Toast.makeText(context, "�x�s�X���I", Toast.LENGTH_SHORT).show();
			MyDebug.MakeLog(0, "SaveOrUpdate SaveIt error=" + e);
			return false;
		}
	}

	// ��s�w�s�b���
	private boolean UpdateIt(ContentValues values, int taskId) {
		try {
			Uri uri = ContentUris.withAppendedId(taskUri,
					taskId);
			context.getContentResolver().update(uri, values, null, null);
			Toast.makeText(context, "�ƶ���s���\�I", Toast.LENGTH_SHORT).show();
			mSetAlarm.SetIt(true);
			return true;
		} catch (Exception e) {
			Toast.makeText(context, "�x�s�X���I", Toast.LENGTH_SHORT).show();
			MyDebug.MakeLog(0, "SaveOrUpdate UpdateIt error=" + e);
			return false;
		}
	}

	// ���� //
}

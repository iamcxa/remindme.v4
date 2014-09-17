package me.iamcxa.remindme.editor;

import me.iamcxa.remindme.database.ColumnTask;
import common.MyCalendar;
import common.MyDebug;
import common.CommonVar;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

/**
 * @author cxa
 * 
 */
public class DbAction_SaveOrUpdate {
	private static CommonEditorVar mEditorVar=CommonEditorVar.GetInstance();
	private Uri taskUri=ColumnTask.URI;
	private SetAlarm mSetAlarm;
	private Context context;
	private int taskId=0;
	private String TaskField_Main="";
	private String TaskField_Location="";
	private String TaskField_Alert="";
	private String TaskField_Type="";
	private String TaskField_Other="";

	public DbAction_SaveOrUpdate(Context context) {
		super();
		this.context = context;
		mSetAlarm = new SetAlarm(context);
		StartOver();
	}

	private void StartOver(){
		getDataFromView();
		getDataFromEditorVar();
		prepareAddDataToDatabase(
				TaskField_Main,
				TaskField_Location,
				TaskField_Alert,
				TaskField_Type,
				TaskField_Other
				);
	}

	/*
	 * 
	 */
	// ��view������o��J��T
	private void getDataFromView(){
		mEditorVar.Task.setTitle(TaskEditorMain.getTaskTitle());	
		mEditorVar.Task.setContent(TaskEditorMain.getTaskTitle());	
		
		// �]�w��Ʈw���(�r��)���
		mEditorVar.Task.setDueDateString(MyCalendar.getTodayString(0));
		// �]�w��Ʈw���(�@��)���
		DbAction_CheckDueDateField.setRawTaskDueDateString(TaskEditorMain.getTaskDueDate());



	}

	// �N�@�P�ܼƤ����ȦX�֬��ƭӦr��}�C
	private void getDataFromEditorVar(){
		TaskField_Main=
				mEditorVar.Task.getTaskId()+","+		
						mEditorVar.Task.getTitle()+","+		
						mEditorVar.Task.getContent()+","+		
						mEditorVar.Task.getCreated()+","+		
						mEditorVar.Task.getDueDateString()+","+	
						mEditorVar.Task.getDueDateTime();
		MyDebug.MakeLog(0,"TaskField_Main="+ TaskField_Main);

		TaskField_Location=
				mEditorVar.TaskLocation.getCoordinate()+","+	
						mEditorVar.TaskLocation.getLocationName()+","+
						mEditorVar.TaskLocation.getDistance();
		MyDebug.MakeLog(0,"TaskField_Location="+ TaskField_Location);

		TaskField_Alert=
				mEditorVar.TaskAlert .getAlertInterval()+","+	
						mEditorVar.TaskAlert.getAlertTime();
		MyDebug.MakeLog(0,"TaskField_Alert="+ TaskField_Alert);

		TaskField_Type=
				mEditorVar.TaskType.getPriority()+","+		
						mEditorVar.TaskType.getCategory()+","+	
						mEditorVar.TaskType.getTag()+","+
						mEditorVar.TaskType.getLevel();			
		MyDebug.MakeLog(0,"TaskField_Type="+ TaskField_Type);	

		TaskField_Other=
				mEditorVar.TaskOther.getCollaborator()+","+		
						mEditorVar.TaskOther.getGoogle_cal_sync_id()+","+	
						mEditorVar.TaskOther.getTask_color();			
		MyDebug.MakeLog(0,"TaskField_Other="+ TaskField_Other);
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

		// �s�J���ȼ��D
		String[] Split_TaskField_Main = TaskField_Main.split(",");
		//	String TaskField_Main=
		//		mEditorVar.Task.getTaskId()+","+			0
		//		mEditorVar.Task.getTittle()+","+			1
		//		mEditorVar.Task.getContent()+","+			2
		//		mEditorVar.Task.getCreated()+","+			3
		//		mEditorVar.Task.getDueDateString()+","+		4
		//		mEditorVar.Task.getDueDateTime();			5
		taskId=Integer.valueOf(Split_TaskField_Main[0]);
		values.put(ColumnTask.KEY.title, Split_TaskField_Main[1]);
		values.put(ColumnTask.KEY.content, Split_TaskField_Main[2]);
		values.put(ColumnTask.KEY.created, Split_TaskField_Main[3]);
		// �T�ODueDate���O�Ʀr
		if((Split_TaskField_Main[4].contains("null"))){
			String mDueDate="";
			values.put(ColumnTask.KEY.due_date_string,String.valueOf(mDueDate));
		}else {
			values.put(ColumnTask.KEY.due_date_string,String.valueOf(Split_TaskField_Main[4]));
		}

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

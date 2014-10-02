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

	// 執行順序
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

	//由view物件取得輸入資訊
	private void getDataFromView(){
		// 標題字串
		mEditorVar.Task.setTitle(TaskEditorMain.getTaskTitle());	

		// 註解字串（任務說明）
		mEditorVar.Task.setContent(TaskEditorMain.getTaskContent());

		// 設定資料庫日期(字串)欄位
		mEditorVar.Task.setDueDateString(TaskEditorMain.getTaskDueDate());

		// 設定資料庫日期(毫秒)欄位
		mEditorVar.TaskDate.setmDatePulsTimeMillis(getTaskDueDateTime());

		// 地點字串 - ID
		//mEditorVar.TaskLocation.setLocationName(locationName);
	}

	// 取得日期與時間加總的到期日毫秒
	private long getTaskDueDateTime(){
		//Act_CheckDueDateField.setRawTaskDueDateString(TaskEditorMain.getTaskDueDate());
		// 初始化
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

		// 存入任務標題
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


	// 將字串陣列拆解
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

	// 判斷本次操作是寫入新資料或更新已存在資料
	private boolean isSaveOrUpdate(ContentValues values, int taskId) {
		if (taskId != 0) {
			return UpdateIt(values, taskId);
		} else {
			return SaveIt(values);
		}		
	}

	// 寫入新資料
	private boolean SaveIt(ContentValues values) {
		try {			
			values.put(ColumnTask.KEY.created, String.valueOf(MyCalendar.getToday()));
			context.getContentResolver().insert(taskUri, values);
			Toast.makeText(context, "新事項已經儲存", Toast.LENGTH_SHORT).show();
			mSetAlarm.SetIt(true);
			return true;
		} catch (Exception e) {
			Toast.makeText(context, "儲存出錯！", Toast.LENGTH_SHORT).show();
			MyDebug.MakeLog(0, "SaveOrUpdate SaveIt error=" + e);
			return false;
		}
	}

	// 更新已存在資料
	private boolean UpdateIt(ContentValues values, int taskId) {
		try {
			Uri uri = ContentUris.withAppendedId(taskUri,
					taskId);
			context.getContentResolver().update(uri, values, null, null);
			Toast.makeText(context, "事項更新成功！", Toast.LENGTH_SHORT).show();
			mSetAlarm.SetIt(true);
			return true;
		} catch (Exception e) {
			Toast.makeText(context, "儲存出錯！", Toast.LENGTH_SHORT).show();
			MyDebug.MakeLog(0, "SaveOrUpdate UpdateIt error=" + e);
			return false;
		}
	}

	// 結束 //
}

package me.iamcxa.remindme.editor;

import java.util.Calendar;

import tw.remindme.common.function.MyCalendar;
import tw.remindme.common.function.MyDebug;

import me.iamcxa.remindme.database.columns.ColumnAlert;
import me.iamcxa.remindme.database.columns.ColumnLocation;
import me.iamcxa.remindme.database.columns.ColumnTask;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

/**
 * @author Kent
 * @version 20141007
 */
public class Act_SaveToDb {
	private static CommonEditorVar mEditorVar=CommonEditorVar.GetInstance();
	private Uri mUri;
	private Act_SetAlarm mSetAlarm;
	private Context context;
	private ContentValues values= new ContentValues();
	private int taskId=0,alertId=0,locId=0,alertSelected=0,locSelected=0;
	private int lastTaskID,lastLocID;

	protected setTableTasks setTableTasks;
	protected setTableAlert setTableAlert;
	protected setTableLocation setTableLocation;
	//private readDB readDB;

	public Act_SaveToDb(Context context,int thisTaskID,int lastTaskID,int lastLocID) {
		super();
		this.context = context;
		this.lastTaskID=lastTaskID;
		this.lastLocID=lastLocID;

		// ���o����P�ɶ���ܾ��ƭȥ[�`�᪺�@���
		getTaskDueDateTime();

		// �g�J�Χ�s��Ʈw
		saveTableTasks();
		saveTableAlert();
		//saveTableLocation();

		
	}

	// ���o����P�ɶ��[�`�������@��
	private static long getTaskDueDateTime(){
		// ��l��
		long taskDueDateTime=0;

		// 
		int mYear=mEditorVar.TaskDate.getmYear();
		int mMonth=mEditorVar.TaskDate.getmMonth();
		int mDay=mEditorVar.TaskDate.getmDay();
		int mHour=mEditorVar.TaskDate.getmHour();
		int mMinute=mEditorVar.TaskDate.getmMinute();
		MyDebug.MakeLog(2,"@selected Date plus time="+mYear+"/"+mMonth+"/"+mDay+"/"+mHour+":"+mMinute);

		Calendar c = Calendar.getInstance();
		c.clear();
		c.set(mYear, mMonth-1, mDay, mHour, mMinute);	
	
		taskDueDateTime= c.getTimeInMillis();

		mEditorVar.TaskDate.setmDatePulsTimeMillis(taskDueDateTime);

		return taskDueDateTime;
	}

	private void saveTableTasks() {
		values.clear();
		// �]�w���� URI, ���� SQL �R�O
		mUri=ColumnTask.URI;
		setTableTasks=new setTableTasks(values);
		isSaveOrUpdate(values, taskId);
	}

	private void saveTableAlert() {
		values.clear();
		// �]�w���� URI, ���� SQL �R�O
		mUri=ColumnAlert.URI;
		setTableAlert=new setTableAlert(values,lastTaskID,lastLocID);
		if (isSaveOrUpdate(values, alertId)){
			mSetAlarm = new Act_SetAlarm(context
					,mEditorVar.TaskDate.getmDatePulsTimeMillis()
					,lastTaskID+1
					);
			mSetAlarm.SetIt();
		}
	}

	private void saveTableLocation() {
		values.clear();
		// �]�w���� URI, ���� SQL �R�O
		mUri=ColumnLocation.URI;
		setTableLocation=new setTableLocation(values);
		isSaveOrUpdate(values, locId);
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
			
			
			context.getContentResolver().insert(mUri, values);
			Toast.makeText(context, "�s�ƶ��w�g�x�s", Toast.LENGTH_SHORT).show();
			
			
			return true;
		} catch (Exception e) {
			Toast.makeText(context, "�x�s�X���I", Toast.LENGTH_SHORT).show();
			MyDebug.MakeLog(2, "SaveOrUpdate SaveIt error=" + e);
			return false;
		}
	}

	// ��s�w�s�b���
	private boolean UpdateIt(ContentValues values, int taskId) {
		try {
			Uri uri = ContentUris.withAppendedId(mUri,
					taskId);
			context.getContentResolver().update(uri, values, null, null);
			Toast.makeText(context, "�ƶ���s���\�I", Toast.LENGTH_SHORT).show();
			//if(alertSelected==1) mSetAlarm.SetIt(true);
			mEditorVar.Task.setTaskId(0);
			return true;
		} catch (Exception e) {
			Toast.makeText(context, "�x�s�X���I", Toast.LENGTH_SHORT).show();
			MyDebug.MakeLog(2, "SaveOrUpdate UpdateIt error=" + e);
			return false;
		}
	}
	// ���� //
}

/*
 * 
 */
class setTableTasks{
	private static CommonEditorVar mEditorVar=CommonEditorVar.GetInstance();

	public setTableTasks(ContentValues values) {
		super();
		getTaskDataFields();
		setTasksValues(values);
	}

	//TODO�@��view������o��J��T
	private static void getTaskDataFields(){

		//---------------------------- �r�� -------------------------//
		// ���D�r��
		mEditorVar.Task.setTitle(TaskEditorMain.getTaskTitle());	
		// �T�O���Ȫ��A��"������"
		mEditorVar.Task.setStatus(mEditorVar.Task.TASK_STATUS_UNFINISHED);
		// ���Ѧr��]���Ȼ����^
		mEditorVar.Task.setContent(TaskEditorMain.getTaskContent());

		//---------------------------- �ɶ� -------------------------//
		// �]�w�ӥ��ȫإ߮ɶ�
		mEditorVar.Task.setCreated(MyCalendar.getNow());
		// �]�w��Ʈw���(�r��)���
		mEditorVar.Task.setDue_date_string(TaskEditorMain.getTaskDueDateString());
		// �]�w��Ʈw���(�@��)���
		mEditorVar.Task.setDue_date_millis(mEditorVar.TaskDate.getmDatePulsTimeMillis());

		//---------------------------- IDs -------------------------//
		// TODO "����"�P"�M��"�������M�w
		// TODO �]�w�u���v  - spinner - �w�p������ index 0(��)~4�]�C)
		mEditorVar.Task.setPriority(TaskEditorMain.getTaskPriority().getSelectedItemPosition());
		// TODO �]�w����id  - spinner - �w�p������ index(0~�̫�)
		mEditorVar.Task.setCategory_id(TaskEditorMain.getTaskCategory().getSelectedItemPosition());
		// TODO �]�w�M��  - spinner - �w�p������ index(0~�̫�) 
		mEditorVar.Task.setPriority(TaskEditorMain.getTaskPriority().getSelectedItemPosition());
		// TODO �]�w�d���C�� - �w�p�P�����αM�׷f�t
		mEditorVar.Task.setColor(mEditorVar.TaskCardColor.getTaskDefaultColor());
		// TODO �]�w����  - ���T�w
		mEditorVar.Task.setTag_id("null");

		// TODO �]�w��@�H��id
		mEditorVar.Task.setCollaborator_id("null");
		// TODO �]�w���ȦP�Bid
		mEditorVar.Task.setSync_id(0);
	}



	// �s�J��ƪ� - tasks
	public void setTasksValues(ContentValues values){
		// 1 - ID
		//values.put(ColumnTask.KEY._id, mEditorVar.Task.getTaskId());
		// 2 - ���D 
		values.put(ColumnTask.KEY.title, mEditorVar.Task.getTitle());
		// 3 - ���A  - 0������ - 1����
		values.put(ColumnTask.KEY.status, mEditorVar.Task.getStatus());
		// 4 - �Ƶ�
		values.put(ColumnTask.KEY.content, mEditorVar.Task.getContent());
		// 5 - ����� - �@��
		values.put(ColumnTask.KEY.due_date_millis, mEditorVar.Task.getDue_date_millis());
		// 6 - �����  - �r��
		values.put(ColumnTask.KEY.due_date_string,mEditorVar.Task.getDue_date_string());
		// 7 - ���ȥd���C��N��
		values.put(ColumnTask.KEY.color, mEditorVar.Task.getColor());
		// 8 - �u���v
		values.put(ColumnTask.KEY.priority, mEditorVar.Task.getPriority());
		// 9 - �����ȷs�W�ɶ� 
		values.put(ColumnTask.KEY.created, mEditorVar.Task.getCreated());
		// 10- ����id 
		values.put(ColumnTask.KEY.category_id, mEditorVar.Task.getCategory_id());
		// 11- �M��id
		values.put(ColumnTask.KEY.project_id, mEditorVar.Task.getProject_id());
		// 12- ��@��uid 
		values.put(ColumnTask.KEY.collaborator_id, mEditorVar.Task.getCollaborator_id());
		// 13- �P�B����id
		values.put(ColumnTask.KEY.sync_id, mEditorVar.Task.getSync_id());
		// 14- ���Ȧa�Iid
		values.put(ColumnTask.KEY.location_id,mEditorVar.Task.getLocation_id());
		// 15- ����id
		values.put(ColumnTask.KEY.tag_id,mEditorVar.Task.getTag_id());
	}
}

/*
 * TODO
 */
class setTableLocation{
	private static CommonEditorVar mEditorVar=CommonEditorVar.GetInstance();

	public setTableLocation(ContentValues values) {
		super();
		getLocationFields();
		setTaskLocation(values);
	}

	private void getLocationFields() {
		// TODO �a�I�P�_�P���o������Ƴ�����������

		if(TaskEditorMain.getTaskLocation().getSelectedItemPosition()!=-1){
			// �]�w�a�I�W��
			mEditorVar.TaskLocation.setName("null");

			// �]�w�g�n��
			mEditorVar.TaskLocation.setLat(0.0);
			mEditorVar.TaskLocation.setLon(0.0);

			// �]�w�ت��a�P�{�b�a�I�Z��
			mEditorVar.TaskLocation.setDistance(0.0);

			// �]�w�Ӧa�I�̪�ϥήɶ����{�b
			mEditorVar.TaskLocation.setLastUsedTime(MyCalendar.getNow());
		}
	}



	// �s�J��ƨ��ƪ� - task_location
	public void setTaskLocation(ContentValues values){
		// 1 - ���Ȧa�Iid
		//values.put(ColumnLocation.KEY._id,mEditorVar.TaskLocation.getLocationId());
		// 2 - �a�I�W�٦r��
		values.put(ColumnLocation.KEY.name,mEditorVar.TaskLocation.getName());
		// 3 - 4 - �g�n��
		values.put(ColumnLocation.KEY.lat,mEditorVar.TaskLocation.getLat());
		values.put(ColumnLocation.KEY.lon,mEditorVar.TaskLocation.getLon());
		// 5 - �P�W�������a�I���Z��
		values.put(ColumnLocation.KEY.dintance,mEditorVar.TaskLocation.getDistance());
		// 6 - �W���ϥήɶ�
		values.put(ColumnLocation.KEY.lastUsedTime,mEditorVar.TaskLocation.getLastUsedTime());

	}
}

/*
 * TODO
 */
class setTableAlert{
	private static CommonEditorVar mEditorVar=CommonEditorVar.GetInstance();

	int newTaskId=0;
	int newLocId=0;

	public setTableAlert(ContentValues values, int lastTaskID,int lastLocID) {
		super();
		this.newTaskId=lastTaskID+1;
		this.newLocId=lastLocID;
		MyDebug.MakeLog(2, "@newTaskId="+newTaskId+", selectedLocId="+newLocId);
		getAlertFields();
		setTaskAlert(values);
	}

	private void getAlertFields() {
		// �]�w�����ɶ��@���
		mEditorVar.TaskAlert.setDue_date_millis(mEditorVar.TaskDate.getmDatePulsTimeMillis());
		// �]�w�]�P�B�^�����ɶ��r��
		mEditorVar.TaskAlert.setDue_date_string(mEditorVar.Task.getDue_date_string());
		// �]�w�������j
		mEditorVar.TaskAlert.setInterval(0);
		// �]�w�ɶ�����
		mEditorVar.TaskAlert.setTime_offset(0);
		// �]�w�������� - 0�w���� - 1�ɶ��촣��
		mEditorVar.TaskAlert.setType(1);
		// �]�w��������id
		mEditorVar.TaskAlert.setTask_id(newTaskId);
		//
		mEditorVar.TaskAlert.setLoc_id(newLocId);

		// �]�w�O�_�ϥξa��a�I���� - ���o�a�Iid/�a�I�}��/�a�I�b�|
		setAlertByLocation();
	}

	private void setAlertByLocation() {
		//		if(loc){
		//
		//		}else{
		//			// �]�w�����a�IID
		//			mEditorVar.TaskAlert.setLoc_id(newLocId);
		//			mEditorVar.TaskAlert.setLoc_on(0);
		//			mEditorVar.TaskAlert.setLoc_radius(0);
		//		}
	}

	// �s�J��ƨ��ƪ� - task_alerts
	public void setTaskAlert(ContentValues values){
		// 1 - ����id
		//values.put(ColumnAlert.KEY._id,mEditorVar.TaskAlert.getAlertID());
		// 2 - ����� - �@��
		values.put(ColumnAlert.KEY.due_date_millis,mEditorVar.TaskAlert.getDue_date_millis());
		// 3 - ����� - �r��
		values.put(ColumnAlert.KEY.due_date_string,mEditorVar.TaskAlert.getDue_date_string());
		// 4 - Ĳ�o���j
		values.put(ColumnAlert.KEY.interval,mEditorVar.TaskAlert.getInterval());
		// 5 - �����ƥ�]�t���a�Iid
		values.put(ColumnAlert.KEY.loc_id,mEditorVar.TaskAlert.getLoc_id());
		MyDebug.MakeLog(2, "@set newTaskId="+mEditorVar.TaskAlert.getLoc_id());
		
		// 6 - �O�_�}�Ҿa��a�I����
		values.put(ColumnAlert.KEY.loc_on,mEditorVar.TaskAlert.getLoc_on());
		// 7 - �a�I�a�񴣿��b�|
		values.put(ColumnAlert.KEY.loc_radius,mEditorVar.TaskAlert.getLoc_radius());
		// 8 - �ƥ����
		values.put(ColumnAlert.KEY.other,mEditorVar.TaskAlert.getOther());
		// 9 - ��������id
		values.put(ColumnAlert.KEY.task_id,mEditorVar.TaskAlert.getTask_id());
		// 10- �ɶ��ץ�
		values.put(ColumnAlert.KEY.time_offset,mEditorVar.TaskAlert.getTime_offset());
		// 11- ��������
		values.put(ColumnAlert.KEY.type,mEditorVar.TaskAlert.getType());
	}
}

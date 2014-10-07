package me.iamcxa.remindme.editor;

import common.CommonVar;
import me.iamcxa.remindme.R;
import me.iamcxa.remindme.database.ColumnLocation;
import me.iamcxa.remindme.database.ColumnTask;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;

public class TaskEditorMain extends Fragment 
implements
OnClickListener,
LoaderManager.LoaderCallbacks<Cursor>
{

	//private static CustomDialog_DueDate cDialog;
	// static SimpleCursorAdapter mAdapter;

	private static MultiAutoCompleteTextView taskTitle; 	// 任務標題
	private static EditText taskDueDate;					// 任務到期日
	private static ImageButton taskBtnDueDate;

	private static Spinner tasklocation;		
	private static ImageButton taskBtnLocation;

	private static EditText taskContent;
	private static Spinner taskCategory;					// 類別
	private static Spinner taskPriority;					// 優先
	private static Spinner taskTag;							// tag
	private static Spinner taskProject;					  	// 專案
	private static Button btnMore;							// more按鈕
	private Handler mHandler;

	private static String nullString="null";

	public static TaskEditorMain newInstance() {
		TaskEditorMain fragment = new TaskEditorMain();
		return fragment;
	}

	private static  CommonEditorVar mEditorVar=CommonEditorVar.GetInstance();	

	private Runnable mShowContentRunnable = new Runnable() {
		@Override
		public void run() {
			setupViewComponent();
			setupStringArray();
			init(getActivity().getIntent());
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateView(inflater, container, savedInstanceState);
		return inflater.inflate(R.layout.activity_task_editor_tab_main, container,false);
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		obtainData();
		if(savedInstanceState==null){

		}
	}

	private void setupStringArray(){
		String[] BasicStringArray =
				getResources().getStringArray(R.array.Array_Task_Editor_Date_Basic_Meaning_String);
		String[] RepeatStringArray =
				getResources().getStringArray(R.array.Array_Task_Editor_Date_Repeat_Meaning_String);
		CommonVar.TASKEDITOR_DUEDATE_BASIC_STRING_ARRAY=BasicStringArray;
		CommonVar.TASKEDITOR_DUEDATE_EXTRA_STRING_ARRAY=RepeatStringArray;
	}

	private void setupViewComponent(){
		// 標題 - 輸入框
		taskTitle =(MultiAutoCompleteTextView)getView().
				findViewById(R.id.multiAutoCompleteTextViewTitle);
		taskTitle.setHint(getResources().getString(R.string.TaskEditor_Field_Title_Hint));
		//taskTitle.setText(mEditorVar.Task.getTitle());

		// 期限 - 輸入框
		taskDueDate =(EditText)getView().findViewById(R.id.editTextDueDate);
		taskDueDate.setHint(getResources().getString(R.string.TaskEditor_Field_DueDate_Hint));
		//taskDueDate.setText(mEditorVar.Task.getDueDate());
		taskDueDate.setEnabled(false);  // 關閉欄位暫時避免輸入偵測判斷
		taskDueDate.setClickable(false);// 關閉選取暫時避免輸入偵測判斷
		// 期限 - 選擇按鈕
		taskBtnDueDate=(ImageButton)getView().findViewById(R.id.imageButtonResetDate);
		taskBtnDueDate.setOnClickListener(this);

		// taskContent
		taskContent=(EditText)getView().findViewById(R.id.editTextContent);
		taskContent.setHint(getResources().getString(R.string.TaskEditor_Field_Content_Hint));

		// 地點 
		tasklocation=(Spinner)getView().findViewById(R.id.spinnerTextLocation);
		taskBtnLocation=(ImageButton)getView().findViewById(R.id.imageButtonSetLocation);
		taskBtnLocation.setOnClickListener(this);

		// btnMore 
		btnMore=(Button)getView().findViewById(R.id.btnMore);
		btnMore.setEnabled(true);
		btnMore.setOnClickListener(this);

		/*
		 * 
		 */

		// 類別選擇框
		taskCategory=(Spinner)getActivity().findViewById(R.id.spinnerCategory);
		taskCategory.setPrompt(getResources().getString(R.string.TaskEditor_Field_Category_Hint));
		taskCategory.setVisibility(View.GONE);

		// 優先度選擇
		taskPriority=(Spinner)getView().findViewById(R.id.spinnerPriority);
		taskPriority.setPrompt(getResources().getString(R.string.TaskEditor_Field_Priority_Hint));
		taskPriority.setVisibility(View.GONE);

		// tag
		taskTag=(Spinner)getView().findViewById(R.id.spinnerTag);
		taskTag.setPrompt(getResources().getString(R.string.TaskEditor_Field_Tag_Hint));
		taskTag.setVisibility(View.GONE);

		// project
		taskProject=(Spinner)getView().findViewById(R.id.spinnerProject);
		taskProject.setPrompt(getResources().getString(R.string.TaskEditor_Field_Project_Hint));
		taskProject.setVisibility(View.GONE);

		getLoaderManager().initLoader(0, null, this);
	}

	//------------------------------------- 由資料庫初始化變數
	public static void init(Intent intent) {
		Bundle b = intent.getBundleExtra(CommonVar.BundleName);
		if (b != null) {
			//參照 底部之TaskFieldContents/RemindmeVar.class等處, 確保變數欄位與順序都相同
			mEditorVar.Task.setTaskId(b.getInt(ColumnTask.KEY._id));
			mEditorVar.Task.setTitle(b.getString(ColumnTask.KEY.title));
			mEditorVar.Task.setContent(b.getString(ColumnTask.KEY.content));
			mEditorVar.Task.setCreated(b.getLong(ColumnTask.KEY.created));
			mEditorVar.Task.setDue_date_millis(b.getLong(ColumnTask.KEY.due_date_millis));
			mEditorVar.Task.setDue_date_string(b.getString(ColumnTask.KEY.due_date_string));

			mEditorVar.Task.setCategory_id(b.getInt(ColumnTask.KEY.category_id));
			mEditorVar.Task.setPriority(b.getInt(ColumnTask.KEY.priority));
			mEditorVar.Task.setTag_id(b.getString(ColumnTask.KEY.tag_id));



			mEditorVar.Task.setTaskId(b.getInt(ColumnTask.KEY._id));
			TaskEditorMain.setTaskTitle(b.getString(ColumnTask.KEY.title));
			TaskEditorMain.setTaskDueDate(b.getString(ColumnTask.KEY.due_date_string));
			TaskEditorMain.setTaskContent(b.getString(ColumnTask.KEY.content));
		}
	}

	//	//--------------btnClcikListener---------------//
	//	private OnClickListener btnClcikListener=new OnClickListener() {
	//		@Override
	//		public void onClick(View v) {
	//			// TODO Auto-generated method stub
	//			
	//		}
	//	};

	//	//--------------任務到期日選單---------------//
	//	@SuppressLint("InflateParams")
	//	private TaskEditorMain ShowTaskDueDateSelectMenu() {
	//		LayoutInflater inflater = LayoutInflater.from(getActivity());
	//		View mview = inflater.inflate(
	//				R.layout.custom_dialog_duedate,
	//				null);
	//		new AlertDialog.Builder(getActivity())
	//		.setTitle(getResources().getString(R.string.TaskEditor_btnTaskDueDate_Title))
	//		.setView(mview)
	//		.setItems(R.array.Array_TaskEditor_btnTaskDueDate_String,
	//				new DialogInterface.OnClickListener() {
	//			public void onClick(DialogInterface dialog,
	//					int which) {
	//				switch (which) {
	//				case 0:// 今天
	//					taskDueDate.setText(
	//							MyCalendar.getTodayString(0));
	//					break;
	//				case 1:// 明天
	//					taskDueDate.setText(
	//							MyCalendar.getTodayString(1));
	//					break;
	//				case 2:// 下週
	//					taskDueDate.setText(
	//							MyCalendar.getTodayString(7));
	//					break;
	//				case 3:// 一個月內
	//					taskDueDate.setText(
	//							MyCalendar.getTodayString(30));
	//					break;
	//				case 4:// 選擇日期
	//					showDataPicker();
	//
	//
	//
	//
	//
	//					break;
	//					//				case 5:// 說明
	//					//
	//					//					break;
	//				}
	//			}
	//		}).show();
	//		return this;
	//	}

	//--------------任務地點選擇器---------------//
	@SuppressLint("InflateParams")
	private TaskEditorMain ShowTaskLocationSelectMenu() {
		LayoutInflater inflater = LayoutInflater.from(getActivity());
		View mview = inflater.inflate(
				R.layout.activity_task_editor_tab_location,	null);
		new AlertDialog.Builder(getActivity())
		.setTitle(getResources().getString(R.string.TaskEditor_Field_Location_Tittle))
		.setView(mview)

		//		.setItems(R.array.Array_TaskEditor_btnTaskDueDate_String,
		//				new DialogInterface.OnClickListener() {
		//			public void onClick(DialogInterface dialog,
		//					int which) {
		//				
		//			}})

		.show();
		return this;
	}

	//-----------------obtainData------------------//
	private void obtainData() {
		// Show indeterminate progress
		mHandler = new Handler();
		mHandler.postDelayed(mShowContentRunnable, 5);
	}

	//-----------------TaskTitle------------------//
	public static String getTaskTitle() {
		String TaskTitleString = nullString;
		// 如果欄位不為空則放入使用者輸入數值
		if (!(taskTitle.getText().toString().isEmpty())){
			TaskTitleString= taskTitle.getText().toString().trim();
		}
		//MyDebug.MakeLog(0,"getTaskTitle:"+ TaskTitleString+"" +",TaskTitle.len="+TaskTitleString.length());
		return TaskTitleString;
	}
	public static void setTaskTitle(String taskTitle) {
		TaskEditorMain.taskTitle.setText(taskTitle);
	}

	//-----------------TaskDueDate------------------//
	// 取得文字
	public static String getTaskDueDateString() {
		String taskDueDateString=nullString;
		// 如果欄位不為空則放入使用者輸入數值
		if (!(taskDueDate.getText().toString().isEmpty())){
			taskDueDateString= taskDueDate.getText().toString().trim();
		}
		return taskDueDateString;
	}	
	// 取得長度
	public static int getTaskDueDateStringLength() {
		int taskDueDateStringLength=0;
		// 如果欄位不為空則放入使用者輸入數值
		if (!(taskDueDate.getText().toString().isEmpty())){
			taskDueDateStringLength= taskDueDate.getText().length();
		}
		return taskDueDateStringLength;
	}
	// 設定文字
	public static void setTaskDueDate(String taskDueDateString) {
		TaskEditorMain.taskDueDate.setText(taskDueDateString);
	}

	//-----------------TaskContent------------------//
	public static String getTaskContent() {
		String taskContentString=nullString;
		// 如果欄位不為空則放入使用者輸入數值
		if (!(taskContent.getText().toString().isEmpty())){
			taskContentString= taskContent.getText().toString().trim();
		}
		return taskContentString;
	}	
	public static void setTaskContent(String taskContentString) {
		TaskEditorMain.taskContent.setText(taskContentString);
	}

	//-----------------TaskCategory------------------//
	//TODO 處理spinner對應資料
	public static Spinner getTaskCategory() {
		return taskCategory;
	}
	public static void setTaskCategory(Spinner taskCategory) {
		TaskEditorMain.taskCategory = taskCategory;
	}	
	
	//-----------------TaskTag------------------//
	//TODO 處理spinner對應資料
	public static Spinner getTaskTag() {
		return taskTag;
	}
	public static void setTaskTag(Spinner taskTag) {
		TaskEditorMain.taskTag = taskTag;
	}

	//-----------------TaskPriority------------------//
	//TODO 處理spinner對應資料
	public static Spinner getTaskPriority() {
		return taskPriority;
	}
	public static void setTaskPriority(Spinner taskPriority) {
		TaskEditorMain.taskPriority = taskPriority;
	}
	
	//-----------------TaskLocation------------------//
	//TODO 
	public static Spinner getTaskLocation() {
		return tasklocation;
	}
	public static void setTaskLocation(Spinner taskLocation) {
		TaskEditorMain.tasklocation = taskLocation;
	}
	//	//-----------------DataPicker------------------//
	//	private TaskEditorMain showDataPicker() {
	//		String[] dataString=MyCalendar.getTodayString(0).split("/");
	//
	//		int year=Integer.valueOf(dataString[0]);
	//		int month=Integer.valueOf(dataString[1])-1;
	//		int	day=Integer.valueOf(dataString[2]);
	//
	//		new DatePickerDialog(getActivity(),
	//				mDateSetListener, 
	//				year,month, day
	//				).show();
	//
	//		return this;
	//	}
	//
	//	//-----------------TimePicker------------------//
	//	private TaskEditorMain showTimePicker() {
	//		String[] dataString=MyCalendar.getTodayString(0).split("/");
	//
	//		int min=Integer.valueOf(dataString[0]);
	//		int hour=Integer.valueOf(dataString[1])-1;
	//
	//		new TimePickerDialog(getActivity(),
	//				mTimeSetListener, 
	//				hour,min, false
	//				).show();
	//		return this;
	//	}
	//
	//	//-----------------時間選擇對話方塊------------------//
	//	private TimePickerDialog.OnTimeSetListener mTimeSetListener =
	//			new TimePickerDialog.OnTimeSetListener() {
	//		@Override
	//		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
	//			mEditorVar.TaskDate.mHour = hourOfDay;
	//			mEditorVar.TaskDate.mMinute = minute;
	//			//timeDesc.setText(EditorVar.mHour + ":" + EditorVar.mMinute);
	//		}
	//	};
	//
	//	//-----------------日期選擇對話方塊------------------//
	//	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
	//		@Override
	//		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
	//			//mEditorVar.TaskDate.setmYear(year);
	//			//mEditorVar.TaskDate.setmMonth(monthOfYear);
	//			//mEditorVar.TaskDate.setmDay(dayOfMonth);
	//			taskDueDate.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
	//		}
	//	};

	//-----------------設定任務地點陣列------------------//
	private ArrayAdapter<String> setLocationArray(Cursor data){
		ArrayAdapter<String> adapter =
				new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		if(data!=null){
			if (data.getCount()>0){
				data.moveToFirst();
				adapter.add(getResources().getString(R.string.TaskEditor_Field_Location_Spinner_Hint).toString());
				adapter.add(data.getString(data.getColumnIndex("name")));
				if(tasklocation!=null) tasklocation.setEnabled(true);
			}else{
				adapter.add(getResources().getString(R.string.TaskEditor_Field_Location_Is_Empty).toString());
				if(tasklocation!=null) tasklocation.setEnabled(false);
			}
		}
		return adapter;
	}

	//-----------------LoaderManager------------------//
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		String[] projectionLoc = ColumnLocation.PROJECTION;
		String LocSelection = "name is not 'null'"; 
		String LocSortOrder = ColumnLocation.DEFAULT_SORT_ORDER;
		String[] selectionArgs =null;
		Loader<Cursor> loader =  new CursorLoader(getView().getContext(),
				ColumnLocation.URI,
				projectionLoc, LocSelection, selectionArgs, LocSortOrder);
		return loader;
	}
	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		if(tasklocation!=null) tasklocation.setAdapter(setLocationArray(data));
	}
	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		if(tasklocation!=null) tasklocation.setAdapter(setLocationArray(null));
	}

	/*
	 * (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.imageButtonResetDate:
			new CustomDialog_DueDate(getView().getContext()).show();
			break;

		case R.id.imageButtonSetLocation:
			new CustomLocationDialog().show(getFragmentManager() , "dialog");
			break;

		case R.id.btnMore:
			if(taskCategory.getVisibility()==View.GONE){
				taskCategory.setVisibility(View.VISIBLE);
				taskPriority.setVisibility(View.VISIBLE);
				taskProject.setVisibility(View.VISIBLE);
				taskTag.setVisibility(View.VISIBLE);
				btnMore.setText(getResources().getString(R.string.btnMore_Less));
			}else {
				taskCategory.setVisibility(View.GONE);
				taskPriority.setVisibility(View.GONE);
				taskProject.setVisibility(View.GONE);
				taskTag.setVisibility(View.GONE);
				btnMore.setText(getResources().getString(R.string.btnMore_More));
			}
			break;

		default:
			break;
		}
	}

}
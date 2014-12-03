package me.iamcxa.remindme.editor;

import tw.remindme.common.function.CommonVar;
import tw.remindme.common.function.MyDebug;
import me.iamcxa.remindme.R;
import me.iamcxa.remindme.database.columns.ColumnLocation;
import me.iamcxa.remindme.database.columns.ColumnTask;
import me.iamcxa.remindme.database.helper.DBLocationHelper;
import me.iamcxa.remindme.database.helper.DBTasksHelper;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

public class TaskEditorMain extends Fragment implements
OnClickListener
{
	private static MultiAutoCompleteTextView taskTitle; 	// ���ȼ��D
	private static EditText taskDueDate;					// ���Ȩ����
	private static ImageButton taskBtnDueDate;

	private static Spinner tasklocation;		
	private static ImageButton taskBtnLocation;

	private static EditText taskContent;
	private static Spinner taskCategory;					// ���O
	private static Spinner taskPriority;					// �u��
	private static Spinner taskTag;							// tag
	private static Spinner taskProject;					  	// �M��
	private static Button btnMore;							// more���s
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
		// ���D - ��J��
		taskTitle =(MultiAutoCompleteTextView)getView().
				findViewById(R.id.multiAutoCompleteTextViewTitle);
		taskTitle.setHint(getResources().getString(R.string.TaskEditor_Field_Title_Hint));
		//taskTitle.setText(mEditorVar.Task.getTitle());

		// ���� - ��J��
		taskDueDate =(EditText)getView().findViewById(R.id.editTextDueDate);
		taskDueDate.setHint(getResources().getString(R.string.TaskEditor_Field_DueDate_Hint));
		//taskDueDate.setText(mEditorVar.Task.getDueDate());
		//taskDueDate.setEnabled(false);  // �������Ȯ��קK��J�����P�_
		//taskDueDate.setClickable(false);// ��������Ȯ��קK��J�����P�_
		// ���� - ��ܫ��s
		taskBtnDueDate=(ImageButton)getView().findViewById(R.id.imageButtonResetDate);
		taskBtnDueDate.setOnClickListener(this);

		// taskContent
		taskContent=(EditText)getView().findViewById(R.id.editTextContent);
		taskContent.setHint(getResources().getString(R.string.TaskEditor_Field_Content_Hint));

		// �a�I 
		Cursor c= getActivity().getContentResolver().
				query(ColumnLocation.URI, ColumnLocation.PROJECTION, null, null, 
						ColumnLocation.DEFAULT_SORT_ORDER);
		tasklocation=(Spinner)getView().findViewById(R.id.spinnerTextLocation);
		tasklocation.setAdapter(setLocationArray(c));
		tasklocation.setOnItemSelectedListener(test);
		tasklocation.setEnabled(true);

		taskBtnLocation=(ImageButton)getView().findViewById(R.id.imageButtonSetLocation);
		taskBtnLocation.setOnClickListener(this);
		taskBtnLocation.setEnabled(true);

		// btnMore 
		btnMore=(Button)getView().findViewById(R.id.btnMore);
		btnMore.setOnClickListener(this);
		btnMore.setEnabled(true);

		// ���O��ܮ�
		taskCategory=(Spinner)getActivity().findViewById(R.id.spinnerCategory);
		taskCategory.setPrompt(getResources().getString(R.string.TaskEditor_Field_Category_Hint));
		taskCategory.setVisibility(View.GONE);

		// �u���׿��
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

		//TODO ����d��
		exampleOfDBLocationHelper();
	}

	//-------------------------------------------------------//
	//                     									 //
	//                 DBLocationHelper �d��					 //
	//                     									 //
	//-------------------------------------------------------//
	private void exampleOfDBLocationHelper(){
		DBLocationHelper dbLocationHelper=new DBLocationHelper(getActivity());

		// �W�[����
		dbLocationHelper.addItem("����", "1.0", "2.0", 333.0, 444.0, 5,0,"null");
		dbLocationHelper.addItem("����2", "1.0", "2.0", 333.0, 444.0, 5,0,"null");
		dbLocationHelper.addItem("����3", "1.0", "2.0", 333.0, 444.0, 5,0,"null");
		
		// �R������
		dbLocationHelper.deleteItem(4);
		
		// �ק磌��
		dbLocationHelper.setItem(2, ColumnLocation.KEY.dintance,123123.2);
		
		// ���o����
		// �w�]cursor
		Cursor defaultDbLocCursor=dbLocationHelper.getCursor();
		// �S����wcursor
		String[] projection = null,selectionArgs = null;
		String selection = null,shortOrder = null;
		Cursor customDbLocCursor=dbLocationHelper.getCursor(projection, selection, selectionArgs, shortOrder);
		
		// ���oid=0��줧�a�I�W��
		String locNameString=dbLocationHelper.getItemString(0, ColumnLocation.KEY.name);
		// ���oid=0��줧�a�I�Z��
		Double locDistance=dbLocationHelper.getItemDouble(0, ColumnLocation.KEY.dintance);
		// ���oid=0��줧�a�I����
		int locType=dbLocationHelper.getItemInt(0, ColumnLocation.KEY.type);
		
		// ���o�`�ƶq
		int count=dbLocationHelper.getCount();
		MyDebug.MakeLog(2, "dbLocationHelper.getCount="+count);
	}

	private OnItemSelectedListener test=new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			// TODO Auto-generated method stub
			String aa[]={ getTaskLocation().getSelectedItem().toString() };

			Cursor c= getActivity().getContentResolver().
					query(ColumnLocation.URI, ColumnLocation.PROJECTION, "name = ?",aa, 
							ColumnLocation.DEFAULT_SORT_ORDER);

			if (c.moveToFirst()) {
				MyDebug.MakeLog(2,"�a�Iid="+c.getInt(0));
				MyDebug.MakeLog(2,"�a�I�W��="+c.getString(1));
				Toast.makeText(getActivity()
						,"�a�Iid="+c.getInt(0)+"\n�a�I�W��="+c.getString(1)

						, Toast.LENGTH_SHORT).show();


				c.close();
			}



		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub

		}
	};

	//------------------------------------- �Ѹ�Ʈw��l���ܼ�
	public static void init(Intent intent) {
		Bundle b = intent.getBundleExtra(CommonVar.BundleName);
		if (b != null) {
			//�ѷ� ������TaskFieldContents/RemindmeVar.class���B, �T�O�ܼ����P���ǳ��ۦP
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
			MyDebug.MakeLog(2, "@edit main id="+b.getInt(ColumnTask.KEY._id));
			TaskEditorMain.setTaskTitle(b.getString(ColumnTask.KEY.title));
			TaskEditorMain.setTaskDueDate(b.getString(ColumnTask.KEY.due_date_string));
			if(!b.getString(ColumnTask.KEY.content).equalsIgnoreCase("null")){
				TaskEditorMain.setTaskContent(b.getString(ColumnTask.KEY.content));
			}
		}
	}

	//--------------���Ȧa�I��ܾ�---------------//
	//	@SuppressLint("InflateParams")
	//	private TaskEditorMain ShowTaskLocationSelectMenu() {
	//		LayoutInflater inflater = LayoutInflater.from(getActivity());
	//		View mview = inflater.inflate(
	//				R.layout.activity_task_editor_tab_location,	null);
	//		new AlertDialog.Builder(getActivity())
	//		.setTitle(getResources().getString(R.string.TaskEditor_Field_Location_Tittle))
	//		.setView(mview)
	//
	//		//		.setItems(R.array.Array_TaskEditor_btnTaskDueDate_String,
	//		//				new DialogInterface.OnClickListener() {
	//		//			public void onClick(DialogInterface dialog,
	//		//					int which) {
	//		//				
	//		//			}})
	//
	//		.show();
	//		return this;
	//	}

	//-----------------obtainData------------------//
	private void obtainData() {
		// Show indeterminate progress
		mHandler = new Handler();
		mHandler.postDelayed(mShowContentRunnable, 5);
		//getLoaderManager().initLoader(0, null, this);
	}

	//-----------------TaskTitle------------------//
	public static String getTaskTitle() {
		String TaskTitleString = nullString;
		// �p�G��줣���ūh��J�ϥΪ̿�J�ƭ�
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
	// ���o��r
	public static String getTaskDueDateString() {
		String taskDueDateString=nullString;
		// �p�G��줣���ūh��J�ϥΪ̿�J�ƭ�
		if (!(taskDueDate.getText().toString().isEmpty())){
			taskDueDateString= taskDueDate.getText().toString().trim();
		}
		return taskDueDateString;
	}	
	// ���o����
	public static int getTaskDueDateStringLength() {
		int taskDueDateStringLength=0;
		// �p�G��줣���ūh��J�ϥΪ̿�J�ƭ�
		if (!(taskDueDate.getText().toString().isEmpty())){
			taskDueDateStringLength= taskDueDate.getText().length();
		}
		return taskDueDateStringLength;
	}
	// �]�w��r
	public static void setTaskDueDate(String taskDueDateString) {
		TaskEditorMain.taskDueDate.setText(taskDueDateString);
	}

	//-----------------TaskContent------------------//
	public static String getTaskContent() {
		String taskContentString=nullString;
		// �p�G��줣���ūh��J�ϥΪ̿�J�ƭ�
		if (!(taskContent.getText().toString().isEmpty())){
			taskContentString= taskContent.getText().toString().trim();
		}
		return taskContentString;
	}	
	public static void setTaskContent(String taskContentString) {
		TaskEditorMain.taskContent.setText(taskContentString);
	}

	//-----------------TaskCategory------------------//
	//TODO �B�zspinner�������
	public static Spinner getTaskCategory() {
		return taskCategory;
	}
	public static void setTaskCategory(Spinner taskCategory) {
		TaskEditorMain.taskCategory = taskCategory;
	}	

	//-----------------TaskTag------------------//
	//TODO �B�zspinner�������
	public static Spinner getTaskTag() {
		return taskTag;
	}
	public static void setTaskTag(Spinner taskTag) {
		TaskEditorMain.taskTag = taskTag;
	}

	//-----------------TaskPriority------------------//
	//TODO �B�zspinner�������
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


	//----------------- onClick ------------------//
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


	//-----------------�]�w���Ȧa�I�}�C------------------//
	private ArrayAdapter<String> setLocationArray(Cursor data){
		ArrayAdapter<String> adapter =
				new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		if(data!=null){
			if (data.getCount()>0){
				data.moveToFirst();
				adapter.add(getResources().getString(R.string.TaskEditor_Field_Location_Spinner_Hint).toString());
				do{
					adapter.add(data.getString(data.getColumnIndex("name")));
				}while (data.moveToNext());
				if(tasklocation!=null) tasklocation.setEnabled(true);
			}else{
				adapter.add(getResources().getString(R.string.TaskEditor_Field_Location_Is_Empty).toString());
				if(tasklocation!=null) tasklocation.setEnabled(false);
			}
		}
		return adapter;
	}

}
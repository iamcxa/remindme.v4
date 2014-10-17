package me.iamcxa.remindme.editor;
import java.lang.reflect.Field;
import java.util.Calendar;

import common.MyCalendar;
import common.MyDebug;

import me.iamcxa.remindme.R;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.os.Build;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TimePicker;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This is a custom dialog class that will hold a tab view with 2 tabs.
 * Tab 1 will be a list view. Tab 2 will be a list view.
 * 
 */
public class CustomDialog_DueDate extends AlertDialog 
implements 
DialogInterface.OnClickListener,
OnDateChangeListener,
OnTimeChangedListener,
OnShowListener,
OnTabChangeListener

{
	private static CommonEditorVar mEditorVar=CommonEditorVar.GetInstance();

	private String selectedDate="";

	private String selectedTime="";

	//private ViewGroup viewGroup=(ViewGroup) getWindow().getDecorView().findViewById(android.R.id.content);

	private LayoutInflater inflater = getWindow().getLayoutInflater();

	@SuppressLint("InflateParams")
	private View dialoglayout = inflater.inflate(R.layout.custom_dialog_duedate, null);

	private TabHost tabs = (TabHost)dialoglayout.findViewById(R.id.TabHost01);

	/**
	 * Our custom list view adapter for tab 1 listView (listView01).
	 */
	ListView01Adapter listView01Adapter = null;

	/**
	 * Default constructor.
	 * 
	 * @param context
	 */
	public CustomDialog_DueDate(Context context)
	{
		super(context);

		// remove window title 
		this.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

		// get this window's layout parameters so we can change the position
		WindowManager.LayoutParams params = getWindow().getAttributes(); 

		// change the position. 0,0 is center
		params.x = 0;
		params.y = 50;
		params.height=-2;
		params.width=-2;
		this.getWindow().setAttributes(params); 

		// set custom dialog layout
		setView(dialoglayout);

		// get our tabHost from the xml
		tabs.setup();

		// create tab 1 - calendar - a date picker
		String tab1_Title=
				MyCalendar.getThisMonth()
				+getContext().getResources().getString(R.string.String_Task_Editor_Date_Month).toString()
				+MyCalendar.getThisDay()
				+getContext().getResources().getString(R.string.String_Task_Editor_Date_Day).toString();
		TabHost.TabSpec tab1 = tabs.newTabSpec("tab1");
		tab1.setContent(R.id.calendarView01);
		tab1.setIndicator(tab1_Title);
		tabs.addTab(tab1);

		// create tab 2	- time picker
		String tab2_Title=getContext().getResources().getString(R.string.String_Task_Editor_Dialog_Pick_A_Time).toString();
		TabHost.TabSpec tab2 = tabs.newTabSpec("tab2");
		tab2.setContent(R.id.timePicker01);
		tab2.setIndicator(tab2_Title);
		tabs.addTab(tab2);

		// set listview and tab3- disable 
		//setListViews(context);
		ListView listView01 = (ListView)dialoglayout.findViewById(R.id.listView01);
		listView01.setVisibility(View.GONE);

		// set tab host Tab Changed Listener - to add/remove button dynamically.
		tabs.setOnTabChangedListener(this);

		// set dialog Buttons
		this.setButton(BUTTON_POSITIVE,
				getContext().getResources()
				.getString(R.string.String_Task_Editor_Dialog_BUTTON_POSITIVE), this);
		this.setButton(BUTTON_NEUTRAL,
				getContext().getResources()
				.getString(R.string.String_Task_Editor_Dialog_BUTTON_NEUTRAL), this);
		this.setButton(BUTTON_NEGATIVE,
				getContext().getResources()
				.getString(R.string.String_Task_Editor_Dialog_BUTTON_NEGATIVE), this);
		this.setCanceledOnTouchOutside(false);

		// set Show Listener - in case to hide BUTTON_NEUTRAL.
		this.setOnShowListener(this);

		// Calendar - data picker
		CalendarView cal = (CalendarView)dialoglayout.findViewById(R.id.calendarView01);
		cal.setOnDateChangeListener(this);

		// TimePicker
		TimePicker tPicker=(TimePicker)dialoglayout.findViewById(R.id.timePicker01);
		tPicker.setOnTimeChangedListener(this);
		tPicker.setIs24HourView(true);
	}

	private void setListViews(Context context) {
		// TODO Auto-generated method stub
		// instantiate our list views for each tab
		ListView listView01 = (ListView)dialoglayout.findViewById(R.id.listView01);

		// register a context menu for all our listView02 items
		registerForContextMenu(listView01);

		// instantiate and set our custom list view adapters
		listView01Adapter = new ListView01Adapter(context);
		listView01.setAdapter(listView01Adapter);

		// bind a click listener to the listView01 list
		listView01.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			public void onItemClick(AdapterView<?> parentView, View childView, int position, long id)
			{                   
				// will dismiss the dialog
				dismiss();
			}           
		});

		// create tab 3
		TabHost.TabSpec tab3 = tabs.newTabSpec("tab3");
		tab3.setContent(R.id.listView01);
		tab3.setIndicator("jj");
		tabs.addTab(tab3);
	}

	/**
	 * 
	 */
	private void getSelectedDate(){
		// get millisecond from calendar selected.
		mEditorVar.TaskDate.setmOnlyDateMillis(getDatePicker().getDate());

		// transform Millisecond to MMYYDD  
		String YYMMDD=MyCalendar.getDate_From_TimeMillis
				(false, mEditorVar.TaskDate.getmOnlyDateMillis());
		String YYMMDD_Array[]=YYMMDD.split("/");

		int mYear=Integer.valueOf(YYMMDD_Array[0]);
		int mMonth=Integer.valueOf(YYMMDD_Array[1]);
		int mDay=Integer.valueOf(YYMMDD_Array[2]);

		// �]�wcalendar view���~/��/��/�@���mEditorVar���O�s
		mEditorVar.TaskDate.setmYear(mYear);
		mEditorVar.TaskDate.setmMonth(mMonth);
		mEditorVar.TaskDate.setmDay(mDay);
		mEditorVar.TaskDate.setmOnlyDateMillis(getDatePicker().getDate());

		selectedDate=YYMMDD;

		MyDebug.MakeLog(0, this.toString()+" The date you Selected="+YYMMDD);
	}

	/**
	 * 
	 */
	private void getSelectedTime(){
		if((getBtnNutral().getVisibility())==(View.VISIBLE)){
			// get millisecond from calendar selected.
			mEditorVar.TaskDate.setmHour(getTimePicker().getCurrentHour());
			mEditorVar.TaskDate.setmMinute(getTimePicker().getCurrentMinute());

			MyDebug.MakeLog(0, 
					"The time you Selected="
							+mEditorVar.TaskDate.getmHour()+":"
							+mEditorVar.TaskDate.getmMinute());
		}
	}

	private TimePicker getTimePicker(){
		final TimePicker tPicker=(TimePicker)dialoglayout.findViewById(R.id.timePicker01);
		return tPicker;
	}

	private CalendarView getDatePicker(){
		final CalendarView cal = (CalendarView)dialoglayout.findViewById(R.id.calendarView01);
		return cal;
	}

	private TextView getTab1Title(){
		TextView tab1Title =
				(TextView)tabs.getTabWidget().getChildAt(0).findViewById(android.R.id.title);
		return tab1Title;
	}

	private TextView getTab2Title(){
		TextView tab2Title =
				(TextView)tabs.getTabWidget().getChildAt(1).findViewById(android.R.id.title);
		return tab2Title;
	}

	private Button getBtnNutral(){
		Button nutralButton = getButton(AlertDialog.BUTTON_NEUTRAL);
		return nutralButton;
	}

	private Button getBtnPositive(){
		Button positiveButton = getButton(AlertDialog.BUTTON_POSITIVE);
		//Button negativeButton = getButton(AlertDialog.BUTTON_NEGATIVE);
		return positiveButton;
	}

	private Button getBtnNegative(){
		Button negativeButton = getButton(AlertDialog.BUTTON_NEGATIVE);
		return negativeButton;
	}

	private void setTab1Title(int year,int month,int dayOfMonth){
		// give a new title with selected date.
		String optionalYear="";

		if((year)!=Integer.valueOf(MyCalendar.getThisYear()))
			optionalYear=String.valueOf(year)
			+getContext().getResources().
			getString(R.string.String_Task_Editor_Date_Year).toString();

		String newTab1Title=
				optionalYear			
				+month
				+getContext().getResources().getString(R.string.String_Task_Editor_Date_Month).toString()
				+dayOfMonth
				+getContext().getResources().getString(R.string.String_Task_Editor_Date_Day).toString();

		getTab1Title().setText(newTab1Title);
	}

	private void setTab2Title(String newTab2Title){
		// give a new title with selected time.
		getTab2Title().setText(newTab2Title);
	}

	private void setDialogShowing(DialogInterface dialog){
		try {
			//������
			Field field = dialog.getClass().getSuperclass().getSuperclass().getDeclaredField("mShowing");
			field.setAccessible(true);
			field.set(dialog, false);
			MyDebug.MakeLog(1, "setDialogShowing");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setDialogDismiss(DialogInterface dialog){
		try {
			//������
			Field field = dialog.getClass().getSuperclass().getSuperclass().getDeclaredField("mShowing");
			field.setAccessible(true);
			field.set(dialog, true);
			MyDebug.MakeLog(1, "setDialogDismiss");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//TODO
	private void setBtnAction_Positive(DialogInterface dialog){
		// �M�� TaskDueDate ���
		TaskEditorMain.setTaskDueDate(null);
		try {
			// ���o�ҿ���
			getSelectedDate();

			// ���o�ҿ�ɶ�
			getSelectedTime();

			// �p�G���ɶ����s"��"�s�b -> �u������JTaskDueDate���
			if((getBtnNutral().getVisibility())==(View.GONE))	
				TaskEditorMain.setTaskDueDate(selectedDate);

			if((getBtnNutral().getVisibility())==(View.VISIBLE))
				TaskEditorMain.setTaskDueDate(selectedDate+"��"+selectedTime);

			setDialogDismiss(dialog);
		} catch (Exception e) {
			Toast.makeText(getContext(), 
					"error msg="
							+e.toString(), Toast.LENGTH_SHORT).show();
			MyDebug.MakeLog(2,this.toString()+" error msg="+e.toString());
		}
	}


	private void setBtnAction_Nutral(){
		selectedTime="";

		setTab2Title(getContext().getResources()
				.getString(R.string.String_Task_Editor_Dialog_Pick_A_Time));

		// ���ë��s
		getBtnNutral().setVisibility(View.GONE);

		// ����tabhost����
		tabs.setCurrentTab(0);

		// mEditorVar
		mEditorVar.TaskDate.setmDatePulsTimeMillis(0);
		mEditorVar.TaskDate.setmHour(0);
		mEditorVar.TaskDate.setmMinute(0);
	}

	private void fixUpDatePickerCalendarView(Calendar date) {
		// Workaround for CalendarView bug relating to setMinDate():
		// https://code.google.com/p/android/issues/detail?id=42750
		// Set then reset the date on the calendar so that it properly
		// shows today's date. The choice of 24 months is arbitrary.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			final CalendarView cal =getDatePicker();
			if (cal != null) {
				date.add(Calendar.MONTH, 24);
				cal.setDate(date.getTimeInMillis(), false, true);
				date.add(Calendar.MONTH, -24);
				cal.setDate(date.getTimeInMillis(), false, true);
			}
		}
	}

	/**
	 *  Data Picker On-Click-Listener
	 */
//	private OnDateChangeListener dateChangeListener=new OnDateChangeListener() {
//		@Override
//		public void onSelectedDayChange(CalendarView view, int year, int month,
//				int dayOfMonth) {
//			// TODO Auto-generated method stub	
//
//			//Calendar calendar= Calendar.getInstance();
//			//fixUpDatePickerCalendarView(calendar);
//
//			int mMonth=month+1;
//			setTab1Title(year, mMonth, dayOfMonth);
//			selectedDate=year+"/"+mMonth+"/"+dayOfMonth;
//			MyDebug.MakeLog(0, "The date you Selected="+selectedDate);
//		}
//	};

	/**
	 * Time Picker On-Time-Changed-Listener
	 */
	private OnTimeChangedListener timeChangedListener = new OnTimeChangedListener(){
		@Override
		public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
			// TODO Auto-generated method stub
//			// get millisecond from calendar selected.
//			mEditorVar.TaskDate.setmHour(view.getCurrentHour());
//			mEditorVar.TaskDate.setmMinute(view.getCurrentMinute());			
//
//			// give a new title with selected date.
//			String newTab2Title=view.getCurrentHour()+":"+view.getCurrentMinute();
//			setTab2Title(newTab2Title);
//
//			selectedTime=mEditorVar.TaskDate.getmHour()+":"+mEditorVar.TaskDate.getmMinute();
//			MyDebug.MakeLog(0, "The time you Selected="+selectedTime);
//		
		}
	};

	/**
	 * �إߤT�ӫ��s����ť��
	 */
//	private DialogInterface.OnClickListener btnClickListener = new DialogInterface.OnClickListener()
//	{
//		@Override
//		public void onClick(DialogInterface dialog, int which) {
//			//which�i�H�ΨӤ���O���U���@�ӫ��s
//			switch (which) {
//			case Dialog.BUTTON_POSITIVE:	// save selected date/time
//
//				setBtnAction_Positive(dialog);
//
//				break;
//			case Dialog.BUTTON_NEUTRAL:		// �����ɶ�
//				setDialogShowing(dialog);
//				setBtnAction_Nutral();
//
//				break; 
//			case Dialog.BUTTON_NEGATIVE:	// ��������
//
//				setDialogDismiss(dialog);
//
//				break;
//			}
//		}
//	};

	/**
	 * Dialog On-Show-Listener 
	 * �q TaskEditorMain Ū������ɶ�  / ���é��ɶ����s
	 */
//	private OnShowListener dialogShowListener=new OnShowListener() {
//		@Override
//		public void onShow(DialogInterface dialog) {
//			// �Ұʥ����é��ɶ����s
//			getBtnNutral().setVisibility(ViewGroup.GONE);
//
//			// �ˬdTaskEditorMain����TaskDueDate������
//			if(TaskEditorMain.getTaskDueDateStringLength()>0){
//				// �p�G�����פ~Ū�X�����
//				String existDueDate=TaskEditorMain.getTaskDueDate();
//
//				// �P�_ TaskEditorMain.getTaskDueDate() �O�_��"/"�Ÿ�
//				if(existDueDate.contains("/")){
//					// �H"��"�Ÿ����j����P�ɶ� - YYYY/MM/DD��HH:MM - �H[0]�T�O�@�w�O�����
//					String[] arrayExistDueDate=existDueDate.split("��");
//
//					// �H"/"�Ÿ����j�~��� - YYYY/MM/DD
//					String[] arrayExistDueDateDetail=arrayExistDueDate[0].split("/");
//
//					// ��YYYY/MM/DD������J�ܼ�selectedDate.
//					selectedDate=arrayExistDueDate[0];
//
//					//  ��mEditorVarŪ�X�ҿ�ܤ�����@���T
//					long dueDateMillis=mEditorVar.TaskDate.getmOnlyDateMillis();
//
//					// �N���⧹���᪺�@���Jcalendar view��.
//					getDatePicker().setDate(dueDateMillis);
//
//					// �N�����Ʃ�� tab1���D�W
//					setTab1Title(
//							Integer.valueOf(arrayExistDueDateDetail[0]), 
//							Integer.valueOf(arrayExistDueDateDetail[1]), 
//							Integer.valueOf(arrayExistDueDateDetail[2]));
//
//
//					// Ū�ɶ���ƨ� tab 2 title.
//					// �p�G������즳�ɶ�����, �h�N���JselectedTime.
//					if(existDueDate.contains("��")) {
//						getBtnNutral().setVisibility(View.VISIBLE);
//						selectedTime=arrayExistDueDate[1];
//						setTab2Title(selectedTime);
//
//						// �]�w�ҿ�ܮɶ���time picker
//						String[] arrayTimeStrings=arrayExistDueDate[1].split(":");
//						getTimePicker().setCurrentHour(Integer.valueOf(arrayTimeStrings[0]));
//						getTimePicker().setCurrentMinute(Integer.valueOf(arrayTimeStrings[1]));
//
//					}
//
//
//					// log
//					MyDebug.MakeLog(0, "arrayExistDueDate[0]="+arrayExistDueDate[0]);
//					MyDebug.MakeLog(0, "existDueDate="+existDueDate);
//					if(existDueDate.contains("��")) MyDebug.MakeLog(0, "arrayExistDueDate[1]="+arrayExistDueDate[1]);
//					MyDebug.MakeLog(0, "dueDateMillis="+dueDateMillis);
//
//				}else {
//					// �w�]��ܤ���
//					selectedDate=String.valueOf(MyCalendar.getTodayString(0));
//					getDatePicker().setDate(MyCalendar.getNextFewDays(0));
//				}
//			}
//		}
//	};

	/**
	 * TabHost On-Tab-ChangeListener
	 */	
//	private TabHost.OnTabChangeListener tabsChangedListener = new OnTabChangeListener() {
//		@Override
//		public void onTabChanged(String tabId) {
//			// tab2=�ɶ�
//			if(tabId=="tab2") { 
//				selectedTime=getTimePicker().getCurrentHour()+":"+getTimePicker().getCurrentMinute();
//
//				getBtnNutral().setVisibility(View.VISIBLE);
//
//				setTab2Title(selectedTime);
//			}
//		}
//	};

	/**
	 * A custom list adapter for the listView01
	 */
	private class ListView01Adapter extends BaseAdapter
	{        
		public ListView01Adapter(Context context)
		{

		}

		/**
		 * This is used to return how many rows are in the list view
		 */
		public int getCount()
		{
			// add code here to determine how many results we have, hard coded for now

			return 2;
		}

		/**
		 * Should return whatever object represents one row in the
		 * list.
		 */
		public Object getItem(int position)
		{
			return position;
		}

		/**
		 * Used to return the id of any custom data object.
		 */
		public long getItemId(int position)
		{
			return position;
		}

		/**
		 * This is used to define each row in the list view.
		 */
		public View getView(int position, View convertView, ViewGroup parent)
		{            
			View row = convertView;

			// our custom holder will represent the view on each row. See class below.
			ListView01Holder holder = null;

			if(row == null)
			{                                                   
				LayoutInflater inflater = getLayoutInflater();

				// inflate our row from xml
				row = inflater.inflate(R.layout.custom_dialog_list_view_01_row, parent, false);

				// instantiate our holder
				holder = new ListView01Holder(row);

				// set our holder to the row
				row.setTag(holder);

			}
			else
			{
				holder = (ListView01Holder)row.getTag();
			}
			return row;
		}

		// our custom holder
		class ListView01Holder
		{
			// text view
			private TextView text = null;

			// image view
			private ImageView image = null;

			ListView01Holder(View row)
			{    
				// get out text view from xml
				text = (TextView)row.findViewById(R.id.list_view_01_row_text_view);

				// add code here to set the text
				//text.setText(getContext().getResources().getString(R.array.Array_TaskEditor_btnTaskDueDate_String));

				// get our image view from xml
				image = (ImageView)row.findViewById(R.id.list_view_01_row_image_view);

				// add code here to determine which image to load, hard coded for now
				image.setImageResource(R.drawable.map_marker);
			}
		}
	}

	/**
	 * This is called when a long press occurs on our listView02 items.
	 */
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo)
	{
		super.onCreateContextMenu(menu, v, menuInfo);

		menu.setHeaderTitle("Context Menu");  
		menu.add(0, v.getId(), 0, "Delete");  
	}

	/**
	 * This is called when an item in our context menu is clicked.
	 */
	public boolean onContextItemSelected(MenuItem item)
	{  
		if(item.getTitle() == "Delete")
		{

		}  
		else
		{
			return false;
		}

		return true;  
	}

	/**
	 * TabHost On-Tab-ChangeListener
	 */	
	@Override
	public void onTabChanged(String tabId) {
		// TODO Auto-generated method stub
		// tab2=�ɶ�
		if(tabId=="tab2") { 
			selectedTime=getTimePicker().getCurrentHour()+":"+getTimePicker().getCurrentMinute();

			getBtnNutral().setVisibility(View.VISIBLE);

			setTab2Title(selectedTime);
		}
	}

	/**
	 * Dialog On-Show-Listener 
	 * �q TaskEditorMain Ū������ɶ�  / ���é��ɶ����s
	 */
	@Override
	public void onShow(DialogInterface dialog) {
		// TODO Auto-generated method stub
		// �Ұʥ����é��ɶ����s
		getBtnNutral().setVisibility(ViewGroup.GONE);

		// �ˬdTaskEditorMain����TaskDueDate������
		if(TaskEditorMain.getTaskDueDateStringLength()>0){
			// �p�G�����פ~Ū�X�����
			String existDueDate=TaskEditorMain.getTaskDueDateString();

			// �P�_ TaskEditorMain.getTaskDueDate() �O�_��"/"�Ÿ�
			if(existDueDate.contains("/")){
				// �H"��"�Ÿ����j����P�ɶ� - YYYY/MM/DD��HH:MM - �H[0]�T�O�@�w�O�����
				String[] arrayExistDueDate=existDueDate.split("��");

				// �H"/"�Ÿ����j�~��� - YYYY/MM/DD
				String[] arrayExistDueDateDetail=arrayExistDueDate[0].split("/");

				// ��YYYY/MM/DD������J�ܼ�selectedDate.
				selectedDate=arrayExistDueDate[0];

				//  ��mEditorVarŪ�X�ҿ�ܤ�����@���T
				long dueDateMillis=mEditorVar.TaskDate.getmOnlyDateMillis();

				// �N���⧹���᪺�@���Jcalendar view��.
				getDatePicker().setDate(dueDateMillis);

				// �N�����Ʃ�� tab1���D�W
				setTab1Title(
						Integer.valueOf(arrayExistDueDateDetail[0]), 
						Integer.valueOf(arrayExistDueDateDetail[1]), 
						Integer.valueOf(arrayExistDueDateDetail[2]));


				// Ū�ɶ���ƨ� tab 2 title.
				// �p�G������즳�ɶ�����, �h�N���JselectedTime.
				if(existDueDate.contains("��")) {
					getBtnNutral().setVisibility(View.VISIBLE);
					selectedTime=arrayExistDueDate[1];
					setTab2Title(selectedTime);

					// �]�w�ҿ�ܮɶ���time picker
					String[] arrayTimeStrings=arrayExistDueDate[1].split(":");
					getTimePicker().setCurrentHour(Integer.valueOf(arrayTimeStrings[0]));
					getTimePicker().setCurrentMinute(Integer.valueOf(arrayTimeStrings[1]));

				}


				// log
				MyDebug.MakeLog(0, "arrayExistDueDate[0]="+arrayExistDueDate[0]);
				MyDebug.MakeLog(0, "existDueDate="+existDueDate);
				if(existDueDate.contains("��")) MyDebug.MakeLog(0, "arrayExistDueDate[1]="+arrayExistDueDate[1]);
				MyDebug.MakeLog(0, "dueDateMillis="+dueDateMillis);

			}else {
				// �w�]��ܤ���
				selectedDate=String.valueOf(MyCalendar.getTodayString(0));
				getDatePicker().setDate(MyCalendar.getNextFewDays(0));
			}
		}
	}

	/**
	 * Time Picker On-Time-Changed-Listener
	 */
	@Override
	public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
		// TODO Auto-generated method stub
		// get millisecond from calendar selected.
		mEditorVar.TaskDate.setmHour(view.getCurrentHour());
		mEditorVar.TaskDate.setmMinute(view.getCurrentMinute());			

		// give a new title with selected date.
		String newTab2Title=view.getCurrentHour()+":"+view.getCurrentMinute();
		setTab2Title(newTab2Title);

		selectedTime=mEditorVar.TaskDate.getmHour()+":"+mEditorVar.TaskDate.getmMinute();
		MyDebug.MakeLog(0, "The time you Selected="+selectedTime);
	
	}

	/**
	 *  Data Picker On-Click-Listener
	 */
	@Override
	public void onSelectedDayChange(CalendarView view, int year, int month,
			int dayOfMonth) {
		// TODO Auto-generated method stub
		//Calendar calendar= Calendar.getInstance();
		//fixUpDatePickerCalendarView(calendar);

		int mMonth=month+1;
		setTab1Title(year, mMonth, dayOfMonth);
		selectedDate=year+"/"+mMonth+"/"+dayOfMonth;
		MyDebug.MakeLog(0, "The date you Selected="+selectedDate);
	}

	/**
	 * �إߤT�ӫ��s����ť��
	 */
	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		//which�i�H�ΨӤ���O���U���@�ӫ��s
		switch (which) {
		case Dialog.BUTTON_POSITIVE:	// save selected date/time

			setBtnAction_Positive(dialog);

			break;
		case Dialog.BUTTON_NEUTRAL:		// �����ɶ�
			setDialogShowing(dialog);
			setBtnAction_Nutral();

			break; 
		case Dialog.BUTTON_NEGATIVE:	// ��������

			setDialogDismiss(dialog);

			break;
		}
	}  
}
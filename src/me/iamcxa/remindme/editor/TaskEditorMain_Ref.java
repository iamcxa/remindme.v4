///**
// * 
// */
//package me.iamcxa.remindme.editor;
//
//import java.util.Calendar;
//import java.util.Date;
//
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.model.CameraPosition;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.MarkerOptions;
//
//import me.iamcxa.remindme.R;
//import me.iamcxa.remindme.RemindmeVar;
//import me.iamcxa.remindme.provider.GPSCallback;
//import me.iamcxa.remindme.provider.GPSManager;
//import me.iamcxa.remindme.provider.GeocodingAPI;
//import android.app.ActionBar;
//import android.app.AlarmManager;
//import android.app.AlertDialog;
//import android.app.DatePickerDialog;
//import android.app.Dialog;
//import android.app.PendingIntent;
//import android.app.TimePickerDialog;
//import android.content.ContentUris;
//import android.content.ContentValues;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.graphics.Color;
//import android.location.Location;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Handler;
//import android.support.v4.app.FragmentActivity;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AbsListView;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.BaseAdapter;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.CheckedTextView;
//import android.widget.DatePicker;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.ListView;
//import android.widget.ScrollView;
//import android.widget.TextView;
//import android.widget.TimePicker;
//import android.widget.Toast;
//
///**
// * @author cxa
// * 
// */
//
//public class TaskEditorMain_Ref extends FragmentActivity implements GPSCallback {
//
//	private static SaveOrUpdate mSaveOrUpdate;
//
//	// �ŧiGPS�Ҳ�
//	private static GPSManager gpsManager = null;
//
//	// �ŧipick
//	private static GoogleMap map;
//
//	// �Ƨѿ��T���C��
//	private static ListView listView;
//
//
//	private static EditText EditTextTittle;
//	private static EditText SearchText;
//	private static EditText datePicker, timePicker, contentBox, locationBox;
//
//	// ������TextView
//	private static TextView dateTittle;
//	private static TextView dateDesc, locationDesc;
//	// �ɶ����TextView
//	private static TextView timeTittle;
//	private static TextView timeDesc;
//	// �������eTextView
//	private static TextView contentTittle;
//	private static TextView contentDesc;
//	private static TextView locationTittle;
//
//	private static Button Search;
//
//	private static ImageButton OK;
//
//	private static ScrollView main_scrollview;
//
//	private static CheckBox checkBoxIsFixed;
//
//	private Handler GpsTimehandler = new Handler();
//
//
//	// �h���
//	private CheckedTextView ctv1, ctv2;
//	// �s���G�����
//	private static LayoutInflater li;
//
//
//	private EditorVar mEditorVar ;
//
//	// ��l�Ƥ�k
//	private void init(Intent intent) {
//		Bundle b = intent.getBundleExtra("b");
//		if (b != null) {
//			mEditorVar.Editor.taskId = b.getInt("taskId");
//			mEditorVar.Editor.tittle = b.getString("tittle");
//			mEditorVar.Editor.created = b.getString("created");
//			mEditorVar.Editor.dueDate = b.getString("dueDate");
//			mEditorVar.Editor.alertTime = b.getString("alertTime");
//			mEditorVar.Editor.content = b.getString("content");
//			mEditorVar.Editor.alertCycle = b.getString("alertCycle");
//			mEditorVar.Editor.locationName = b.getString("locationName");
//			mEditorVar.Editor.coordinate = b.getString("coordinate");
//
//			if (mEditorVar.Editor.dueDate != null && mEditorVar.Editor.dueDate.length() > 0) {
//				String[] strs = mEditorVar.Editor.dueDate.split("/");
//				mEditorVar.Date.setmYear( Integer.parseInt(strs[0]));
//				mEditorVar.Date.setmMonth (Integer.parseInt(strs[1]) - 1);
//				mEditorVar.Date.setmDay (Integer.parseInt(strs[2]));
//			}
//
//			if (mEditorVar.Editor.alertTime != null && mEditorVar.Editor.alertTime.length() > 0) {
//				String[] strs = mEditorVar.Editor.alertTime.split(":");
//				mEditorVar.Date.setmHour(Integer.parseInt(strs[0]));
//				mEditorVar.Date.setmMinute ( Integer.parseInt(strs[1]));
//			}
//
//			EditTextTittle.setText(mEditorVar.Editor.tittle);
//
//		}
//
//		Toast.makeText(getApplicationContext(),
//				mEditorVar.Editor.taskId + "," +
//						mEditorVar.Editor.content + "," +
//						mEditorVar.Editor.dueDate + "," +
//						mEditorVar.Editor.alertTime,
//						Toast.LENGTH_LONG).show();
//
//	}
//
//	public View vv;
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_task_editor);
//		//checkBoxIsFixed = (CheckBox) findViewById(R.id.checkBoxIsFixed);
//		SearchText = (EditText) findViewById(R.id.SearchText);
//		Search = (Button) findViewById(R.id.Search);
//		OK = (ImageButton) findViewById(R.id.OK);
//		//Search.setOnClickListener(SearchPlace);
//		//OK.setOnClickListener(SearchPlace);
//		//gpsManager = new GPSManager();
//		//gpsManager.startGpsListening(getApplicationContext());
//		//gpsManager.setGPSCallback(RemindmeTaskEditor.this);
//		RemindmeVar.GpsSetting.GpsStatus = true;
//		mEditorVar.Location.GpsUseTime = 0;
//		GpsTimehandler.post(GpsTime);
//
//		// map = ((MapFragment) getFragmentManager()
//		// .findFragmentById(R.id.map)).getMap();
//		//map = ((WorkaroundMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
//		main_scrollview = (ScrollView) findViewById(R.id.main_scrollview);
//
//		//		((WorkaroundMapFragment) getSupportFragmentManager().findFragmentById(
//		//				R.id.map))
//		//				.setListener(new WorkaroundMapFragment.OnTouchListener() {
//		//					@Override
//		//					public void onTouch() {
//		//						main_scrollview
//		//						.requestDisallowInterceptTouchEvent(true);
//		//					}
//		//				});
//		//map.setMyLocationEnabled(true);
//		//map.clear();
//		//		LatLng nowLoacation;
//		//		if (gpsManager.LastLocation() != null) {
//		//			nowLoacation = new LatLng(gpsManager.LastLocation().getLatitude(),
//		//					gpsManager.LastLocation().getLongitude());
//		//			map.moveCamera((CameraUpdateFactory.newLatLngZoom(nowLoacation,
//		//					map.getMaxZoomLevel() - 4)));
//		//		} else {
//		//			nowLoacation = new LatLng(23.6978, 120.961);
//		//			map.moveCamera((CameraUpdateFactory.newLatLngZoom(nowLoacation,
//		//					map.getMinZoomLevel() + 7)));
//		//		}
//		//		map.addMarker(new MarkerOptions().title("��e��m").draggable(true)
//		//				.position(nowLoacation));
//		//
//		//		map.setOnCameraChangeListener(listener);
//		// ���oIntent
//		final Intent intent = getIntent();
//		// �]�wUri
//		if (intent.getData() == null) {
//			intent.setData(RemindmeVar.CONTENT_URI);
//		}
//
//		/*
//		 * //String[] ops={"�Ȧ�","���","�ʪ�","����","�S��"};
//		 * 
//		 * // spinner spinnerTag=(Spinner)findViewById(R.id.spinnerTag);
//		 * ArrayAdapter<String> spinnerAdapter=new ArrayAdapter<String>(this,
//		 * android.R.layout.simple_spinner_item,ops);
//		 * spinnerTag.setAdapter(spinnerAdapter); spinnerTag.setPrompt("�������");
//		 */
//
//		// ���D��J���
//		EditTextTittle = (EditText) findViewById(R.id.multiAutoCompleteTextViewTittle);
//		// EditTextTittle.setHint("�z���J\"123 9. �P�ڧJ �ˤ�C \"�ֳt�]�w");
//		// EditTextTittle.setTextSize(textsize(5));
//		// EditTextTittle.setHintTextColor(R.color.background_window);
//
//		// ���oCalendar���
//		final Calendar c = Calendar.getInstance();
//
//		// ���o�ثe����B�ɶ�
//		mEditorVar.Date.setmYear (c.get(Calendar.YEAR));
//		mEditorVar.Date.setmMonth ( (c.get(Calendar.MONTH)));
//		mEditorVar.Date.setmDay ( c.get(Calendar.DAY_OF_MONTH));
//		mEditorVar.Date.setmHour ( c.get(Calendar.HOUR_OF_DAY));
//		mEditorVar.Date.setmMinute( c.get(Calendar.MINUTE));
//
//		// ���oListView
//		//listView = (ListView) (findViewById(R.id.listView1));
//		// ��Ҥ�LayoutInflater
//		li = getLayoutInflater();
//		// �]�wListView Adapter
//		try {
//			listView.setAdapter(new ViewAdapter());
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		// �i�h��
//		//listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
//
//		// �^���C������ƥ�
//		//listView.setOnItemClickListener(ViewAdapterClickListener);
//	}
//
//	@Override
//	protected void onResume() {
//		super.onResume();
//		// ��l�ƦC��
//		init(getIntent());
//	}
//
//	// ListView Adatper�A�����O��@�C���C�@���z�L�۩w���Ϲ�{
//	static class ViewAdapter extends BaseAdapter {
//		// �C���e
//		String[] strs = { "�I���", "�����ɶ�", "�Ƶ�" };
//
//		// ���o�C��ƶq
//		// @Override
//		@Override
//		public int getCount() {
//			return strs.length;
//		}
//
//		// ���o�C����
//		// @Override
//		@Override
//		public Object getItem(int position) {
//			return position;
//		}
//
//		// ��^�C��ID
//		// @Override 
//		@Override
//		public long getItemId(int position) {
//			return position;
//		}
//
//		// ���o�ثe�C���ص���
//		// @Override
//		@Override
//		public View getView(int position, View convertView, ViewGroup parent) {
//			// �۩w�ܹ�layout
//			View textView = li.inflate(
//					R.layout.activity_task_editor_parts_textview, null);
//			// View editView =
//			// li.inflate(R.layout.activity_event_editor_parts_editview, null);
//			switch (position) {
//			// �O�_�}�Ҹӵ��Ƨѿ�
//			/*
//			 * case 0: ctv1 = (CheckedTextView) li .inflate(
//			 * android.R.layout.simple_list_item_multiple_choice, null);
//			 * ctv1.setText(strs[position]); if (on_off == 0) {
//			 * ctv1.setChecked(false); } else { ctv1.setChecked(true); } return
//			 * ctv1;
//			 */
//			// �������
//			case 0:
//				// datePicker=(EditText)v.findViewById(R.id.editTextbox);
//				// datePicker.setHint("��Ĳ�H��ܤ��");
//				// datePicker.setHintTextColor(R.color.background_window);
//				// datePicker.setText(mYear + "/" + mMonth + "/" + mDay);
//
//				dateTittle = (TextView) textView.findViewById(R.id.name);
//				dateDesc = (TextView) textView.findViewById(R.id.desc);
//				dateTittle.setText(strs[position]);
//				//dateDesc.setText(mYear + "/" + mMonth + 1 + "/" + mDay);
//				return textView;
//				// �����ɶ�
//			case 1:
//				// timePicker=(EditText)v.findViewById(R.id.editTextbox);
//				// timePicker.setHint("��Ĳ�H��ܮɶ�");
//				// timePicker.setHintTextColor(R.color.background_window);
//				// timePicker.setText(mHour + ":" + mMinute);
//				timeTittle = (TextView) textView.findViewById(R.id.name);
//				timeDesc = (TextView) textView.findViewById(R.id.desc);
//				timeTittle.setText(strs[position]);
//				//timeDesc.setText(mHour + ":" + mMinute);
//				return textView;
//				// �������e
//			case 2:
//				// contentBox=(EditText)
//				// editView.findViewById(R.id.editTextbox);
//				// contentBox.setHint("��Ĳ�H��J���e");
//				// contentBox.setHintTextColor(R.color.background_window);
//				// contentBox.setText(content);
//
//				contentTittle = (TextView) textView.findViewById(R.id.name);
//				contentDesc = (TextView) textView.findViewById(R.id.desc);
//				contentTittle.setText(strs[position]);
//				String mEditorVarEditorContent=null;
//				contentDesc.setText(mEditorVarEditorContent);
//
//				contentDesc.setTextColor(Color.GRAY);
//				return textView;
//				// �a�I��ܻP��J
//				// �O�_�}�Ҵ���
//				/*
//				 * case 5: ctv2 = (CheckedTextView) li .inflate(
//				 * android.R.layout.simple_list_item_multiple_choice, null);
//				 * ctv2.setText(strs[position]);
//				 * 
//				 * if (alarm == 0) { ctv2.setChecked(false); } else {
//				 * ctv2.setChecked(true); } return ctv2;
//				 */
//			default:
//				break;
//			}
//
//			return null;
//		}
//	}
//
//	private OnItemClickListener ViewAdapterClickListener = new OnItemClickListener() {
//		@Override
//		public void onItemClick(AdapterView<?> av, View v, int position, long id) {
//
//			switch (position) {
//			// �]�w�O�_�}�Ҵ���
//			/*
//			 * case 0:
//			 * 
//			 * ctv1 = (CheckedTextView) v; if (ctv1.isChecked()) { on_off = 0; }
//			 * else { on_off = 1; }
//			 * 
//			 * break;
//			 */
//			// �]�w�������
//			case 0:
//				showDialog(mEditorVar.DATE_DIALOG_ID);
//				break;
//				// �]�w�����ɶ�
//			case 1:
//				showDialog(mEditorVar.TIME_DIALOG_ID);
//				break;
//				// �]�w�������e
//			case 2:
//				showDialog1("�п�J���e�G", "���e", position);
//				break;
//
//				// �]�w�O�_�}�һy������
//			default:
//				break;
//			}
//		}
//
//	};
//
//	// ��ܹ�ܤ��
//	@Override
//	protected Dialog onCreateDialog(int id) {
//		switch (id) {
//		// ��ܤ����ܤ��
//		case mEditorVar.DATE_DIALOG_ID:
//			return new DatePickerDialog(this,
//					mDateSetListener, mEditorVar.Date.getmYear(),
//					mEditorVar.Date.getmMonth() - 1, mEditorVar.Date.getmDay());
//			// ��ܮɶ���ܤ��
//		case mEditorVar.TIME_DIALOG_ID:
//			return new TimePickerDialog(this, 
//					mTimeSetListener, mEditorVar.Date.getmHour(),
//					mEditorVar.Date.getmMinute(),
//					false);
//		}
//		return null;
//	}
//
//	// �]�w�q������
//	private void setAlarm(boolean flag) {
//		final String BC_ACTION = "com.amaker.ch17.TaskReceiver";
//		// ���oAlarmManager���
//		final AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
//		// ��Ҥ�Intent
//		Intent intent = new Intent();
//		// �]�wIntent action�ݩ�
//		intent.setAction(BC_ACTION);
//		intent.putExtra("msg", mEditorVar.content);
//		// ��Ҥ�PendingIntent
//		final PendingIntent pi = PendingIntent.getBroadcast(
//				getApplicationContext(), 0, intent, 0);
//		// ���o�t�ήɶ�
//		final long time1 = System.currentTimeMillis();
//		Calendar c = Calendar.getInstance();
//		c.set(mEditorVar.mYear, mEditorVar.mMonth, mEditorVar.mDay, mEditorVar.mHour, mEditorVar.mMinute);
//		long time2 = c.getTimeInMillis();
//		if (flag && (time2 - time1) > 0 && mEditorVar.on_off == 1) {
//			am.set(AlarmManager.RTC_WAKEUP, time2, pi);
//		} else {
//			am.cancel(pi);
//		}
//	}
//
//	/*
//	 * �]�w���ܤ����ܤ��
//	 */
//	private void showDialog1(String msg, String tittle, int target) {
//		View v = li.inflate(R.layout.activity_task_editor_parts_textedit, null);
//		final TextView editTextTittle = (TextView) v.findViewById(R.id.name);
//		final EditText editTextbox = (EditText) v.findViewById(R.id.editTexbox);
//		editTextTittle.setText(tittle + target);
//
//		switch (target) {
//		case 2:
//			mEditorVar.switcher = mEditorVar.content;
//			break;
//		default:
//			break;
//		}
//
//		editTextbox.setText(mEditorVar.switcher);
//
//		new AlertDialog.Builder(this).setView(v).setMessage(msg)
//		.setCancelable(false)
//		.setPositiveButton("�T�w", new DialogInterface.OnClickListener() {
//			@Override
//			public void onClick(DialogInterface dialog, int id) {
//				mEditorVar.content = editTextbox.getText().toString();
//
//				contentDesc.setText(mEditorVar.switcher);
//				// locationDesc.setText(switcher);
//			}
//		}).show();
//
//	}
//
//	// �ɶ���ܹ�ܤ��
//	private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
//		@Override
//		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//			mEditorVar.mHour = hourOfDay;
//			mEditorVar.mMinute = minute;
//			timeDesc.setText(mEditorVar.mHour + ":" + mEditorVar.mMinute);
//		}
//	};
//	// �����ܹ�ܤ��
//	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
//		@Override
//		public void onDateSet(DatePicker view, int year, int monthOfYear,
//				int dayOfMonth) {
//			mEditorVar.mYear = year;
//			mEditorVar.mMonth = monthOfYear;
//			mEditorVar.mDay = dayOfMonth;
//			dateDesc.setText(mEditorVar.mYear + "/" + (mEditorVar.mMonth + 1) + "/" + mEditorVar.mDay);
//		}
//	};
//
//	// �x�s�έק�Ƨѿ���T
//	@Override
//	protected void onPause() {
//		super.onPause();
//		mEditorVar.locationName = null;
//		mEditorVar.content = null;
//	};
//
//	// This is the action bar menu
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// ���editor_activity_actionbar.xml���e
//		getMenuInflater().inflate(R.menu.editor_activity_actionbar, menu);
//
//		// �ҥ�actionbar��^�����b�Y
//		ActionBar actionBar = getActionBar();
//		actionBar.setDisplayHomeAsUpEnabled(true);
//		actionBar.setDisplayUseLogoEnabled(true);
//		actionBar.setDisplayShowTitleEnabled(true);
//		actionBar.setTitle("�إ߫ݿ�ƶ�");
//
//		// actionAdd
//		MenuItem actionAdd = menu.findItem(R.id.action_add);
//		actionAdd.setOnMenuItemClickListener(btnActionAddClick);
//
//		// actionCancel
//		MenuItem actionCancel = menu.findItem(R.id.action_cancel);
//		actionCancel.setOnMenuItemClickListener(btnActionCancelClick);
//
//		return true;
//	}
//
//	// actionbar�b�Y��^�����ʧ@
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		switch (item.getItemId()) {
//		// Respond to the action bar's Up/Home button
//		case android.R.id.home:
//			this.finish();
//			return true;
//		}
//		return super.onOptionsItemSelected(item);
//	}
//
//	/*
//	 * 
//	 */
//	private MenuItem.OnMenuItemClickListener btnActionAddClick = new MenuItem.OnMenuItemClickListener() {
//		@Override
//		public boolean onMenuItemClick(MenuItem item) {
//			Toast.makeText(getApplicationContext(),
//					dateDesc.getText()+"2"+timeDesc.getText(),
//					Toast.LENGTH_SHORT).show();
//			if (dateDesc.getText().equals("") &&
//					timeDesc.getText().equals("")
//					&& contentDesc.getText().equals("")
//					&& SearchText.getText().toString().equals("")) {
//				String[] StringArray = EditTextTittle.getText().toString()
//						.split(" ");
//				try {
//					int i = Integer.parseInt(StringArray[0]);
//					// System.out.println(i);
//				} catch (Exception e) {
//					EditTextTittle.setText("3 " + StringArray[0]);
//				}
//				String[] QuickTitle =
//						QuickInput.QuickInput(EditTextTittle.getText().toString());
//				for (int a=0 ;a<QuickTitle.length;a++) {
//					if(QuickTitle[a]!=null){
//						switch (a) {
//						case 1:
//							String[] Time =QuickInput.TimeQuickInput(QuickTitle[1]);
//							try {
//								mHour = Integer.parseInt(Time[0]);
//								mMinute = Integer.parseInt(Time[1]);
//								timeDesc.setText(mHour + ":" + mMinute);
//
//							} catch (Exception e) {
//								Toast.makeText(getApplicationContext(), e.toString(),
//										Toast.LENGTH_SHORT).show();
//							}
//							break;
//						case 2:
//							SearchText.setText(QuickTitle[2]);
//							break;
//						case 3:
//							EditTextTittle.setText(QuickTitle[3]);
//							break;
//						case 4:
//							contentDesc.setText(QuickTitle[4]);
//							break;
//						default:
//							break;
//						}
//					}
//				}
//			}
//
//			if (!mEditorVar.isdidSearch && !SearchText.getText().toString().equals("")) {
//				// SearchPlace();
//				GeocodingAPI LoacationAddress = new GeocodingAPI(
//						getApplicationContext(), SearchText.getText()
//						.toString());
//				if (LoacationAddress.GeocodingApiLatLngGet() != null) {
//					mEditorVar.Longitude = LoacationAddress.GeocodingApiLatLngGet().longitude;
//					mEditorVar.Latitude = LoacationAddress.GeocodingApiLatLngGet().latitude;
//				}
//			}
//			if (mEditorVar.isDraped && !SearchText.getText().toString().equals("")) {
//				mEditorVar.Longitude = map.getCameraPosition().target.longitude;
//				mEditorVar.Latitude = map.getCameraPosition().target.latitude;
//				GeocodingAPI LoacationAddress = new GeocodingAPI(
//						getApplicationContext(), mEditorVar.Latitude + "," + mEditorVar.Longitude);
//				if (LoacationAddress.GeocodingApiAddressGet() != null) {
//					SearchText.setText(LoacationAddress
//							.GeocodingApiAddressGet());
//				}
//			}
//
//
//			//			// �s�J���D
//			//			values.put(TaskCursor.KeyColumns.Tittle, EditTextTittle.getText()
//			//					.toString());
//			//			// �s�J���
//			//			values.put(TaskCursor.KeyColumns.StartDate, curDate.toString());
//			//			values.put(TaskCursor.KeyColumns.Editor.dueDate, dateDesc.getText()
//			//					.toString());
//			//			// save the selected value of time
//			//			values.put(TaskCursor.KeyColumns.StartTime, curDate.toString());
//			//			values.put(TaskCursor.KeyColumns.Editor.alertTime, timeDesc.getText()
//			//					.toString());
//			//			// save contents
//			//			values.put(TaskCursor.KeyColumns.CONTENT, contentDesc.getText()
//			//					.toString());
//			//			// save the name string of location
//			//			values.put(TaskCursor.KeyColumns.LocationName, SearchText.getText()
//			//					.toString());
//			//			values.put(TaskCursor.KeyColumns.Coordinate, Latitude + ","
//			//					+ Longitude);
//			//			values.put(TaskCursor.KeyColumns.Priority, 1000);
//
//			if (checkBoxIsFixed != null) {
//				mEditorVar.is_Fixed = String.valueOf(checkBoxIsFixed.isChecked());
//				mEditorVar.Editor.dueDate = dateDesc.getText().toString();
//				//Editor.alertTime = timeDesc.getText().toString();
//				//content = contentDesc.getText().toString();
//				mEditorVar.tittle = EditTextTittle.getText().toString();
//				mEditorVar.coordinate = mEditorVar.Latitude + "," + mEditorVar.Longitude;
//				mEditorVar.locationName=SearchText.getText()
//						.toString();
//			}
//
//			mSaveOrUpdate = new SaveOrUpdate(getApplicationContext());
//			mSaveOrUpdate.DoTaskEditorAdding(mEditorVar.taskId, mEditorVar.tittle, mEditorVar.Editor.dueDate, mEditorVar.Editor.alertTime,
//					mEditorVar.content, mEditorVar.locationName, mEditorVar.coordinate, "1", mEditorVar.is_Fixed, "1");
//			finish();
//			return true;
//		}
//
//	};
//
//	/*
//	 * 
//	 */
//	private MenuItem.OnMenuItemClickListener btnActionCancelClick = new MenuItem.OnMenuItemClickListener() {
//
//		@Override
//		public boolean onMenuItemClick(MenuItem item) {
//			// TODO Auto-generated method stub
//
//			// Intent EventEditor = new Intent();
//			// EventEditor.setClass(getApplication(),RemindmeTaskEditorActivity.class);
//			// EventEditor.setClass(getApplication(), IconRequest.class);
//			// startActivity(EventEditor);
//
//			// saveOrUpdate();
//			finish();
//
//			return false;
//		}
//
//	};
//
//	// GPS��m���ɷ|��s��m
//	@Override
//	public void onGPSUpdate(Location location) {
//		// TODO Auto-generated method stub
//		Double Longitude = location.getLongitude();
//		// �n��
//		Double Latitude = location.getLatitude();
//
//		// textView1.setText("�g�n��:"+Latitude+","+Longitude);
//		// ����g�n�׫ᰨ�W����
//		// Toast.makeText(getApplicationContext(), "����GPS"+location,
//		// Toast.LENGTH_LONG).show();
//
//		if (RemindmeVar.GpsSetting.GpsStatus) {
//			RemindmeVar.GpsSetting.GpsStatus = false;
//			gpsManager.stopListening();
//			gpsManager.setGPSCallback(null);
//			gpsManager = null;
//		} else {
//			RemindmeVar.GpsSetting.GpsStatus = false;
//		}
//		LatLng nowLoacation = new LatLng(Latitude, Longitude);
//
//		map.setMyLocationEnabled(true);
//
//		map.clear();
//
//		map.animateCamera((CameraUpdateFactory.newLatLngZoom(nowLoacation,
//				map.getMaxZoomLevel() - 4)));
//
//		map.addMarker(new MarkerOptions().title("�ثe��m").position(nowLoacation));
//
//		// GeocodingAPI LoacationAddress = new
//		// GeocodingAPI(getApplicationContext(),Latitude+","+Longitude);
//		// textView2.setText(textView2.getText()+" "+LoacationAddress.GeocodingApiAddressGet());
//	}
//
//	private Button.OnClickListener SearchPlace = new Button.OnClickListener() {
//		@Override
//		public void onClick(View v) {
//			// �ŧiGPSManager
//			switch (v.getId()) {
//			case R.id.OK:
//				// textView2.setText(textView2.getText()+"\n"+LoacationAddress.GeocodingApiAddressGet());
//				// //����a�}
//				// textView2.setText(textView2.getText()+"\n"+LoacationAddress.GeocodingApiLatLngGet());
//				// //����g�n��
//				if (!mEditorVar.isdidSearch
//						|| !SearchText.getText().toString()
//						.equals(mEditorVar.LastTimeSearchName)) {
//					SearchPlace();
//					mEditorVar.isdidSearch = true;
//					mEditorVar.LastTimeSearchName = SearchText.getText().toString();
//				}
//				GeocodingAPI LoacationAddress = new GeocodingAPI(
//						getApplicationContext(),
//						map.getCameraPosition().target.latitude + ","
//								+ map.getCameraPosition().target.longitude);
//				if (LoacationAddress.GeocodingApiLatLngGet() != null) {
//					mEditorVar.Longitude = LoacationAddress.GeocodingApiLatLngGet().longitude;
//					mEditorVar.Latitude = LoacationAddress.GeocodingApiLatLngGet().latitude;
//				}
//				// locationDesc = LoacationAddress.GeocodingApiAddressGet();
//				// Toast.makeText(getApplicationContext(),
//				// "����g�n��"+map.getCameraPosition().target.latitude+","+map.getCameraPosition().target.longitude+"\n�a�}:"+locationName,
//				// Toast.LENGTH_SHORT).show();
//
//				break;
//			case R.id.Search:
//				// textView2.setText(map.getMyLocation().toString());
//				// //�i�κ������GPS��m
//				if (!SearchText.getText().toString().equals(mEditorVar.LastTimeSearchName)) {
//					SearchPlace();
//					mEditorVar.isdidSearch = true;
//					mEditorVar.LastTimeSearchName = SearchText.getText().toString();
//				}
//				break;
//
//			default:
//				break;
//			}
//		}
//	};
//
//	// �a�ϲ��ʮɧ�s���w��m
//	private GoogleMap.OnCameraChangeListener listener = new GoogleMap.OnCameraChangeListener() {
//
//		@Override
//		public void onCameraChange(CameraPosition position) {
//			// TODO Auto-generated method stub
//			map.clear();
//			LatLng now = new LatLng(position.target.latitude,
//					position.target.longitude);
//			map.addMarker(new MarkerOptions().title("�ت��a").position(now));
//			if (mEditorVar.isdidSearch)
//				mEditorVar.isDraped = true;
//		}
//	};
//
//	private Runnable GpsTime = new Runnable() {
//		@Override
//		public void run() {
//			mEditorVar.GpsUseTime++;
//			// Timeout Sec, �W�LTIMEOUT�]�w�ɶ���,�����]�wFLAG�ϱogetCurrentLocation���
//			// lastlocation.
//			if (mEditorVar.GpsUseTime > RemindmeVar.GpsSetting.TIMEOUT_SEC) {
//				if (RemindmeVar.GpsSetting.GpsStatus) {
//					gpsManager.stopListening();
//					gpsManager.startNetWorkListening(getApplicationContext());
//					RemindmeVar.GpsSetting.GpsStatus = true;
//					// Toast.makeText(getApplicationContext(), "����GPS",
//					// Toast.LENGTH_LONG).show();
//				}
//			} else {
//				GpsTimehandler.postDelayed(this, 1000);
//			}
//		}
//	};
//
//	private void SearchPlace() {
//		if (!SearchText.getText().toString().equals("")) {
//			GeocodingAPI LoacationAddress2 = null;
//			LatLng SearchLocation = null;
//			LoacationAddress2 = new GeocodingAPI(getApplicationContext(),
//					SearchText.getText().toString());
//			// textView2.setText("");
//			// locationName=LoacationAddress2.GeocodingApiAddressGet();
//			// textView2.setText(textView2.getText()+"\n"+Address);
//			SearchLocation = LoacationAddress2.GeocodingApiLatLngGet();
//			// textView2.setText(textView2.getText()+"\n"+SearchLocation);
//			if (SearchLocation != null) {
//				map.animateCamera((CameraUpdateFactory.newLatLngZoom(
//						SearchLocation, map.getMaxZoomLevel() - 4)));
//				map.addMarker(new MarkerOptions().title("�j�M����m")
//						.snippet(mEditorVar.locationName).position(SearchLocation));
//			} else {
//				Toast.makeText(getApplicationContext(), "�d�L�a�I�@,���ӵ��ոլ�",
//						Toast.LENGTH_SHORT).show();
//			}
//		}
//	}
//
//}
//
//// * CLASS JUST FOR THE CUSTOM ALERT DIALOG
//class CustomAlertDialog extends AlertDialog {
//	public CustomAlertDialog(Context context) {
//		super(context);
//	}
//}

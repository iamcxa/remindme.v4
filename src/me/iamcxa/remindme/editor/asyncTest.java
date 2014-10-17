package me.iamcxa.remindme.editor;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.database.Cursor;

public class asyncTest extends AsyncTaskLoader<Cursor> {

	public asyncTest(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Cursor loadInBackground() {
		// TODO Auto-generated method stub
		return null;
	}

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

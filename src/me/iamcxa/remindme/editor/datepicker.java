package me.iamcxa.remindme.editor;

import me.iamcxa.remindme.R;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

public class datepicker {

	/*
	 * �]�w���ܤ����ܤ��
	 */
//	private void showDialog1(String msg, String tittle, int target) {
//		View v = li.inflate(R.layout.activity_task_editor_parts_textedit, null);
//		final TextView editTextTittle = (TextView) v.findViewById(R.id.name);
//		final EditText editTextbox = (EditText) v.findViewById(R.id.editTexbox);
//		editTextTittle.setText(tittle + target);
//
//		switch (target) {
//		case 2:
//			EditorVar.switcher = EditorVar.content;
//			break;
//		default:
//			break;
//		}
//
//		editTextbox.setText(EditorVar.switcher);
//
//		new AlertDialog.Builder(this).setView(v).setMessage(msg)
//		.setCancelable(false)
//		.setPositiveButton("�T�w", new DialogInterface.OnClickListener() {
//			@Override
//			public void onClick(DialogInterface dialog, int id) {
//				EditorVar.content = editTextbox.getText().toString();
//
//				contentDesc.setText(EditorVar.switcher);
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
//			EditorVar.mHour = hourOfDay;
//			EditorVar.mMinute = minute;
//			timeDesc.setText(EditorVar.mHour + ":" + EditorVar.mMinute);
//		}
//	};
//	// �����ܹ�ܤ��
//	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
//		@Override
//		public void onDateSet(DatePicker view, int year, int monthOfYear,
//				int dayOfMonth) {
//			EditorVar.mYear = year;
//			EditorVar.mMonth = monthOfYear;
//			EditorVar.mDay = dayOfMonth;
//			dateDesc.setText(EditorVar.mYear + "/" + (EditorVar.mMonth + 1) + "/" + EditorVar.mDay);
//		}
//	};
//	
}

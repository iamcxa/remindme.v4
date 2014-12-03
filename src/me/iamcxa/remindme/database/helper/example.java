/**
 * 
 */
package me.iamcxa.remindme.database.helper;

import me.iamcxa.remindme.database.columns.ColumnLocation;
import tw.remindme.common.function.MyDebug;
import android.R.integer;
import android.app.Activity;
import android.database.Cursor;

/**
 * @author Kent
 *
 */
public class example extends Activity {
	

	//-------------------------------------------------------//
	//                     									 //
	//                 DBLocationHelper �d��					 //
	//                     									 //
	//-------------------------------------------------------//
	/*
	 * �`�N�Gsqlite����ƥu��������� TEXT/INTEGER�A�иԷ�addItem��k
	 * ���������w�]����TaskEditorMain.java������154��A�е����p���ѱ�
	 */
	private void exampleOfDBLocationHelper(){
		DBLocationHelper dbLocationHelper=new DBLocationHelper(getApplicationContext());

		// �W�[����
		dbLocationHelper.addItem("����", "1.0", "2.0", 333.0, 444.0, 5,0,"null");
		dbLocationHelper.addItem("����2", "1.0", "2.0", 333.0, 444.0, 5,0,"null");
		
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
}

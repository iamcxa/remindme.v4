package me.iamcxa.remindme.cardfragment;


import common.MyCalendar;
import common.MyDebug;
import common.CommonVar;

import it.gmariotti.cardslib.library.internal.Card;
import me.iamcxa.remindme.database.ColumnTask;
import me.iamcxa.remindme.editor.TaskEditorTab;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

public class OnClickCard {
	Context context;
	Card card;
	Cursor cursor;
	MyCursorCardAdapter mMyCursorCardAdapter;

	public MyCursorCardAdapter getMyCursorCardAdapter() {
		return mMyCursorCardAdapter;
	}

	public void setMyCursorCardAdapter(MyCursorCardAdapter mMyCursorCardAdapter) {
		this.mMyCursorCardAdapter = mMyCursorCardAdapter;
	}

	public OnClickCard(Context context, Cursor cursor, Card card) {
		super();
		this.context = context;
		this.cursor = cursor;
		this.card = card;
	}

	public void readIt(String cardPosition) {
		try {

			MyDebug.MakeLog(0,
					"ReadCardonClick cursor moveToPosition cardIDfromclcikevent="
							+ cardPosition);
			//String cardID = mMyCursorCardAdapter.getCardFromCursor(cursor).getId();
			//String cardID2=mMyCursorCardAdapter.getItem(Integer.parseInt(card.getId())).getId();		

			//long cardID3=mMyCursorCardAdapter.getItemId(Integer.parseInt(cardID));



			cursor.moveToPosition(Integer.parseInt(cardPosition));

			ReadDatefromCursor();

		} catch (Exception e) {

			MyDebug.MakeLog(0, "ReadCardonClick error=" + e);
		}
	}

	private void ReadDatefromCursor() {
		int taskId = cursor.getInt(0);
		String[] DatefromCursor={""};
		int i=0;
		for (String element : ColumnTask.PROJECTION) {
			
			DatefromCursor[i]=element;
			i+=1;
		}
		MyDebug.MakeLog(0, DatefromCursor);
		//		String[] DatefromCursor={
		//				//�D�n���e
		//				cursor.getString(ColumnTask.KEY_INDEX.title),				//0		
		//				cursor.getString(ColumnTask.KEY_INDEX.content),				//1	
		//				cursor.getString(ColumnTask.KEY_INDEX.created),				//2
		//				cursor.getString(ColumnTask.KEY_INDEX.due_date_string),		//3
		//				cursor.getString(ColumnTask.KEY_INDEX.due_date_string),		//4
		//				//����
		//				cursor.getString(ColumnTask.KEY_INDEX.ALERT_Interval),		//5
		//				cursor.getString(ColumnTask.KEY_INDEX.ALERT_TIME),			//6
		//				//��m
		//				cursor.getString(ColumnTask.KEY_INDEX.LOCATION_NAME),		//7
		//				cursor.getString(ColumnTask.KEY_INDEX.COORDINATE),			//8
		//				cursor.getString(ColumnTask.KEY_INDEX.DISTANCE),			//9
		//				//����,���һP�u��
		//				cursor.getString(ColumnTask.KEY_INDEX.CATEGORY),			//10
		//				cursor.getString(ColumnTask.KEY_INDEX.PRIORITY),			//11
		//				cursor.getString(ColumnTask.KEY_INDEX.TAG),					//12
		//				cursor.getString(ColumnTask.KEY_INDEX.LEVEL),				//13
		//				//��L
		//				cursor.getString(ColumnTask.KEY_INDEX.COLLABORATOR),		//14
		//				cursor.getString(ColumnTask.KEY_INDEX.GOOGOLE_CAL_SYNC_ID),	//15
		//				cursor.getString(ColumnTask.KEY_INDEX.TASK_COLOR)			//16
		//		};

		Bundle b = new Bundle();
		//�D�n���e
		b.putInt(ColumnTask.KEY._id,taskId);
		b.putString(ColumnTask.KEY.title,DatefromCursor[0]);
		b.putString(ColumnTask.KEY.content,DatefromCursor[1]);
		b.putString(ColumnTask.KEY.created,DatefromCursor[2]);
		// duedate �ݭn�Ѹ�Ʈw���@���A�ഫ������榡 yyyy/mm/dd.
		String mDueDate="";
		MyDebug.MakeLog(0, "ReadCardonClick DatefromCursor3=" + DatefromCursor[3]);
		if(!DatefromCursor[3].contentEquals("null")){
			mDueDate=MyCalendar.getDate_From_TimeMillis(false, 
					Long.valueOf(DatefromCursor[3]));
		}//�ഫ����
		b.putString(ColumnTask.KEY.due_date_string,mDueDate);

		// �N�Ƨѿ���T�K�[��Intent
		Intent intent = new Intent();
		intent.putExtra(CommonVar.BundleName, b);
		// �ҰʳƧѿ��ԲӸ�TActivity
		intent.setClass(context, TaskEditorTab.class);
		context.startActivity(intent);
	}
}

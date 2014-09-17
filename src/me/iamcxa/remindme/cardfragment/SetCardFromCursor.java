package me.iamcxa.remindme.cardfragment;

import common.MyCalendar;
import common.MyDebug;
import me.iamcxa.remindme.R;
import me.iamcxa.remindme.cardfragment.MyCursorCardAdapter.MyCursorCard;
import me.iamcxa.remindme.database.ColumnLocation;
import me.iamcxa.remindme.database.ColumnTask;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;

public class SetCardFromCursor {

	private Context context;
	private Cursor cursor;
	private MyCursorCard card;

	public SetCardFromCursor(Context context, Cursor cursor, MyCursorCard card) {
		this.context = context;
		this.cursor = cursor;
		this.card = card;
	}

	public void setIt() {
		// �ǳƱ`��
		boolean Extrainfo = cursor
				.isNull(ColumnTask.KEY_INDEX.tag_id);
		int CID = cursor.getInt(ColumnTask.KEY_INDEX._id);		
		//�D�n���e
		String title=cursor.getString(ColumnTask.KEY_INDEX.title);				
		String status=cursor.getString(ColumnTask.KEY_INDEX.status);		
		String content=cursor.getString(ColumnTask.KEY_INDEX.content);			
		int due_date_millis=cursor.getInt(ColumnTask.KEY_INDEX.due_date_millis);			
		String due_date_string=cursor.getString(ColumnTask.KEY_INDEX.due_date_string);		
		int color=cursor.getInt(ColumnTask.KEY_INDEX.color);	
		int priority=cursor.getInt(ColumnTask.KEY_INDEX.priority);		
		int created=cursor.getInt(ColumnTask.KEY_INDEX.created);	
		//����,���һP�u��
		int category_id=cursor.getInt(ColumnTask.KEY_INDEX.category_id);
		int tag_id=cursor.getInt(ColumnTask.KEY_INDEX.tag_id);
		int project_id=cursor.getInt(ColumnTask.KEY_INDEX.project_id);
		int collaborator_id=cursor.getInt(ColumnTask.KEY_INDEX.collaborator_id);
		int sync_id=cursor.getInt(ColumnTask.KEY_INDEX.sync_id);	
		int location_id=cursor.getInt(ColumnTask.KEY_INDEX.location_id);

		long dayLeft = MyCalendar.getDaysLeft(due_date_string, 2);
		// int dayLeft = Integer.parseInt("" + dayLeftLong);

		// give a ID.
		card.setId(String.valueOf(cursor.getPosition()));

		// �d�����D - first line
		MyDebug.MakeLog(0, "cardID="+CID + " set Tittle="+title);	
		card.mainHeader = title;

		// �ɶ���� - sec line
		MyDebug.MakeLog(0, CID + " set Date/Time...");
		card.DateTime=due_date_string;
		MyDebug.MakeLog(0, CID + " dayleft=" + dayLeft);
//		if ((180 > dayLeft) && (dayLeft > 14)) {
//			card.DateTime = "�A " + (int) Math.floor(dayLeft) / 30 + " �Ӥ� - "
//					+ due_date_string + " - " + due_date_millis;
//		} else if ((14 > dayLeft) && (dayLeft > 0)) {
//			card.DateTime = "�A " + dayLeft + " �� - " + due_date_string + " - "
//					+ due_date_string;
//		} else if ((2 > dayLeft) && (dayLeft > 0)) {
//			card.DateTime = "�A " + (int) Math.floor(dayLeft * 24) + "�p�ɫ� - "
//					+ due_date_string + " - " + due_date_millis;
//		} else if (dayLeft == 0) {
//			card.DateTime = "���� - " + due_date_string + " - " + due_date_millis;
//		} else {
//			card.DateTime = due_date_string + " - " + due_date_millis;
//		}

		// �p�ϼ���� - �P�_�O�_�s���a�I��T
		MyDebug.MakeLog(0, "Location=\"" + location_id + "\"");

		if (location_id != 0) {
			card.resourceIdThumb = R.drawable.map_marker;
		} else {
			card.resourceIdThumb = R.drawable.tear_of_calendar;
			card.LocationName = "�S�����Ȧa�I";
		}


		// �Z���P�a�I��T	
		ContentResolver resolverLocation =context.getContentResolver();

		Cursor cursorLocation=resolverLocation.query(ColumnLocation.URI, 
				null, 
				ColumnLocation.KEY._id+" = ? ",
				new String[] { ColumnTask.KEY.location_id },
				ColumnLocation.DEFAULT_SORT_ORDER);
		String dintence;
		if(cursorLocation.getCount()>0){
			 dintence=String.valueOf(cursorLocation.getLong(ColumnLocation.KEY_INDEX.dintance));
		}else {
			 dintence="null";
		}
		MyDebug.MakeLog(0, "dintence=" +dintence);
		if (dintence == "") {
			card.LocationName = cursorLocation.getString(ColumnLocation.KEY_INDEX.name);
		} else {
			//			if (Double.valueOf(dintence) < 1) {
			//				card.LocationName = LocationName + " - �Z�� "
			//						+ Double.valueOf(dintence) * 1000 + " ����";
			//			} else {
			card.LocationName = location_id + " - �Z�� " + dintence + " ����";

			//			}
		}

		// �i�i�}�B�~��T���
		MyDebug.MakeLog(0, "isExtrainfo=" + Extrainfo);
		 card.Notifications = "dbId="
		 + cursor.getString(0)
		 + ",priority="
		 + cursor.getString(ColumnTask.KEY_INDEX.priority);
		if (!Extrainfo) {
			card.resourceIdThumb = R.drawable.outline_star_act;
			// �B�~��T���� - �ĥ|��

		}
		card.Notifications = cursor.getString(0);

		// �̷��v�������d���C��
		if (cursor.getInt(ColumnTask.KEY_INDEX.priority) > 6000) {
			card.setBackgroundResourceId(R.drawable.demo_card_selector_color5);
		} else if (cursor.getInt(ColumnTask.KEY_INDEX.priority) > 3000) {
			card.setBackgroundResourceId(R.drawable.demo_card_selector_color3);
		}
	}

}

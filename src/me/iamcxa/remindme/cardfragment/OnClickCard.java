package me.iamcxa.remindme.cardfragment;


import tw.remindme.common.function.CommonVar;
import tw.remindme.common.function.MyDebug;

import it.gmariotti.cardslib.library.internal.Card;
import me.iamcxa.remindme.database.columns.ColumnTask;
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

			// 取得該卡片任務在tasks表之id
			//Integer cardID=(int) mMyCursorCardAdapter.getItemId(Integer.parseInt(cardPosition));

			cursor.moveToPosition(Integer.parseInt(cardPosition));

			ReadDatefromCursor();

		} catch (Exception e) {

			MyDebug.MakeLog(0, "ReadCardonClick error=" + e.toString());
		}
	}

	private void ReadDatefromCursor() {
		int taskId = cursor.getInt(0);
		//		String[] DatefromCursor={""};
		//		int i=0;
		//		for (String element : ColumnTask.PROJECTION) {
		//			DatefromCursor[i]=element;
		//			i+=1;
		//		}

		Bundle b = new Bundle();
		//主要內容
		b.putInt(ColumnTask.KEY._id,taskId);
		b.putString(ColumnTask.KEY.title, cursor.getString(ColumnTask.KEY.INDEX.title));
		b.putString(ColumnTask.KEY.content, cursor.getString(ColumnTask.KEY.INDEX.content));
		b.putString(ColumnTask.KEY.due_date_millis, cursor.getString(ColumnTask.KEY.INDEX.due_date_millis));
		b.putString(ColumnTask.KEY.due_date_string, cursor.getString(ColumnTask.KEY.INDEX.due_date_string));
		

		// 將備忘錄資訊添加到Intent
		Intent intent = new Intent();
		intent.putExtra(CommonVar.BundleName, b);
		// 啟動備忘錄詳細資訊Activity
		intent.setClass(context, TaskEditorTab.class);
		context.startActivity(intent);
	}
}

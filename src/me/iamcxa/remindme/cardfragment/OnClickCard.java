package me.iamcxa.remindme.cardfragment;


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

			// ���o�ӥd�����Ȧbtasks��id
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
		//�D�n���e
				b.putInt(ColumnTask.KEY._id,taskId);
				b.putString(ColumnTask.KEY.title,cursor.getString(ColumnTask.KEY_INDEX.title));

		// �N�Ƨѿ���T�K�[��Intent
		Intent intent = new Intent();
		intent.putExtra(CommonVar.BundleName, b);
		// �ҰʳƧѿ��ԲӸ�TActivity
		intent.setClass(context, TaskEditorTab.class);
		context.startActivity(intent);
	}
}

package me.iamcxa.remindme.database;

import common.CommonVar;

import android.net.Uri;
import android.provider.BaseColumns;

public final class ColumnLocation implements BaseColumns {

	private ColumnLocation() {
	}

	// �w�]�ƧǱ`��
	public static final String DEFAULT_SORT_ORDER = "_id DESC";

	// ��ƪ�W�ٱ`��
	public static final String TABLE_NAME = "task_locations";

	// �s��Uri
	public static final Uri URI =
			Uri.parse("content://" + CommonVar.AUTHORITY + "/" + TABLE_NAME);
	
	public static final String exec_SQL_Statment=
			"CREATE TABLE "
					+ TABLE_NAME
					+ " ("
					+KEY._id  + " INTEGER PRIMARY KEY autoincrement,"
					+KEY.name  + " TEXT,"
					+KEY.lat + " TEXT,"
					+KEY.lon + " TEXT,"
					+KEY.dintance + " INTEGER"
					+ ");";
	// �d�����}�C
	public static final String[] PROJECTION = new String[] { 
		KEY._id ,
		KEY.name ,
		KEY.lat,
		KEY.lon,
		KEY.dintance 
	};

	// ������
	public static class KEY_INDEX {
		public static final int _id = 0;
		public static final int name = 1;
		public static final int lat = 2;
		public static final int lon = 3;
		public static final int dintance = 4;
	}

	// ���W��
	public static class KEY {
		public static final String _id = "_id";
		public static final String name = "name";
		public static final String lat = "lat";
		public static final String lon = "lon";
		public static final String dintance = "dintance";

	}
}
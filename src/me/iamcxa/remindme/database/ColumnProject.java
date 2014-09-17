package me.iamcxa.remindme.database;

import android.provider.BaseColumns;

	public final class ColumnProject implements BaseColumns {

		private ColumnProject() {
		}

		// �w�]�ƧǱ`��
		public static final String DEFAULT_SORT_ORDER = "id DESC";

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
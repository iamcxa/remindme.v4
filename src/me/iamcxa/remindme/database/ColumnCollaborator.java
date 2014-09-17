package me.iamcxa.remindme.database;

import android.provider.BaseColumns;

//public class MyCursor {
//
//	private MyCursor(){}
//

// �������O
public final class ColumnCollaborator implements BaseColumns {

	private ColumnCollaborator() {
	}

	// �w�]�ƧǱ`��
	public static final String DEFAULT_SORT_ORDER = "created DESC";

	// �d�����}�C
	public static final String[] PROJECTION = new String[] { 
		KEY._id,
		//�D�n���e
		KEY.title,
		KEY.status,
		KEY.content,
		KEY.due_date_millis,
		KEY.due_date_string,
		KEY.color,
		KEY.priority,
		KEY.created,
		//����,���һP�u��
		KEY.category_id,
		KEY.tag_id,
		KEY.project_id,
		KEY.collaborator_id,
		KEY.sync_id,
	};

	public static class KEY_INDEX {
		public static final int _id = 0;
		//�D�n���e
		public static final int title = 1;
		public static final int status = 2;
		public static final int content = 3;
		public static final int due_date_millis = 4;
		public static final int due_date_string = 5;
		public static final int color = 6;
		public static final int priority = 7;
		public static final int created = 8;
		//����,���һP�u��
		public static final int category_id= 9;
		public static final int tag_id = 10;
		public static final int project_id = 11;
		public static final int collaborator_id = 12;
		public static final int sync_id = 13;
	}

	// ��L���`��
	public static class KEY {
		public static final String _id = "_id";
		//�D�n���e
		public static final String title = "title";
		public static final String status = "status";
		public static final String content = "status";
		public static final String due_date_millis = "due_date_millis";
		public static final String due_date_string = "due_date_string";
		public static final String color = "color";
		public static final String priority = "priority";
		public static final String created = "created";
		//����,���һP�u��
		public static final String category_id= "category_id";
		public static final String tag_id = "tag_id";
		public static final String project_id = "project_id";
		public static final String collaborator_id = "collaborator_id";
		public static final String sync_id = "sync_id";
	}
}

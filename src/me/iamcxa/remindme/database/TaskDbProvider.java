/**
 * 
 */
package me.iamcxa.remindme.database;

import java.util.HashMap;
import common.CommonVar;
import common.MyDebug;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;

/**
 * @author cxa ��Ʈw�ާ@��k
 * 
 */

public class TaskDbProvider extends ContentProvider {
	// ��Ʈw�W�ٱ`��
	public static final String DATABASE_NAME = "Remindme_Task.db";
	// ��Ʈw�����`��
	public static final int DATABASE_VERSION = 3;
	// �d�ߡB��s����
	private static final int URI_TASKS = 101;
	private static final int URI_TASK_ID = 102;
	//
	private static final int URI_ALERTS = 201;
	private static final int URI_ALERT_ID = 202;
	//
	private static final int URI_LOCATIONS = 301;
	private static final int URI_LOCATION_ID = 302;
	//
	private static final String TABLE_TASK= ColumnTask.TABLE_NAME;
	private static final String TABLE_ALERT= ColumnAlert.TABLE_NAME;
	private static final String TABLE_LOCATION= ColumnLocation.TABLE_NAME;
	//-----------------------------------------------------//
	// Uri�u�����O
	private static final UriMatcher sUriMatcher;
	// ��Ʈw�u�����O���
	private DatabaseHelper mOpenHelper;
	// �d����춰�X
	private static HashMap<String, String> sTaskProjectionMap;
	private static HashMap<String, String> sTaskAlertProjectionMap;
	private static HashMap<String, String> sTaskLocationProjectionMap;

	// �����u�����O�A�إߩζ}�Ҹ�Ʈw�B�إߩΧR����ƪ�
	public static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		// �إ߸�ƪ�
		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(ColumnTask.exec_SQL_Statment);
			db.execSQL(ColumnAlert.exec_SQL_Statment);
			db.execSQL(ColumnLocation.exec_SQL_Statment);
		}

		// �R����ƪ�
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS" + TABLE_TASK);
			db.execSQL("DROP TABLE IF EXISTS" + TABLE_ALERT);
			db.execSQL("DROP TABLE IF EXISTS" + TABLE_LOCATION);
			onCreate(db);
		}
	}

	// �إߩζ}�Ҹ�Ʈw
	@Override
	public boolean onCreate() {
		mOpenHelper = new DatabaseHelper(getContext());
		return true;
	}

	// �d��
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		String orderBy=sortOrder;

		switch (sUriMatcher.match(uri)) {
		// TASK
		case URI_TASKS:// �d�ߩҦ�����
			orderBy = ColumnTask.DEFAULT_SORT_ORDER;
			qb.setTables(TABLE_TASK);
			qb.setProjectionMap(sTaskProjectionMap);
			break;
		case URI_TASK_ID:// �ھڥ���ID�d��
			orderBy = ColumnTask.DEFAULT_SORT_ORDER;
			qb.setTables(TABLE_TASK);
			qb.setProjectionMap(sTaskProjectionMap);
			qb.appendWhere(BaseColumns._ID + "=" + uri.getPathSegments().get(1));
			break;

			// ALERT	
		case URI_ALERTS: //�d�ߩҦ�����
			orderBy = ColumnAlert.DEFAULT_SORT_ORDER;
			qb.setTables(TABLE_ALERT);
			qb.setProjectionMap(sTaskAlertProjectionMap);
			break;
		case URI_ALERT_ID: //�ھڴ���ID�d��
			orderBy = ColumnAlert.DEFAULT_SORT_ORDER;
			qb.setTables(TABLE_ALERT);
			qb.setProjectionMap(sTaskAlertProjectionMap);
			qb.appendWhere(BaseColumns._ID + "=" + uri.getPathSegments().get(1));
			break;

			// Location	
		case URI_LOCATIONS: //�d�ߩҦ�����
			orderBy = ColumnLocation.DEFAULT_SORT_ORDER;
			qb.setTables(TABLE_LOCATION);
			qb.setProjectionMap(sTaskLocationProjectionMap);
			break;
		case URI_LOCATION_ID: //�ھڴ���ID�d��
			orderBy = ColumnLocation.DEFAULT_SORT_ORDER;
			qb.setTables(TABLE_LOCATION);
			qb.setProjectionMap(sTaskLocationProjectionMap);
			qb.appendWhere(BaseColumns._ID + "=" + uri.getPathSegments().get(1));
			break;

		default:
			throw new IllegalArgumentException("Uri���~�I " + uri + "/"
					+ sUriMatcher.match(uri));
		}

		if (!TextUtils.isEmpty(sortOrder)) orderBy = sortOrder;

		// ���o��Ʈw���
		SQLiteDatabase db = mOpenHelper.getReadableDatabase();
		// ��^��ж��X
		Cursor c = qb.query(db, projection, selection, selectionArgs, null,
				null, 	null);
		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	}

	// ���o����
	@Override
	public String getType(Uri uri) {
		switch (sUriMatcher.match(uri)) {
		case URI_TASKS :
			return CommonVar.CONTENT_TYPE;
		case URI_TASK_ID:
			return CommonVar.CONTENT_ITEM_TYPE;

		case URI_ALERTS :
			return CommonVar.CONTENT_TYPE;
		case URI_ALERT_ID:
			return CommonVar.CONTENT_ITEM_TYPE;

		case URI_LOCATIONS :
			return CommonVar.CONTENT_TYPE;
		case URI_LOCATION_ID:
			return CommonVar.CONTENT_ITEM_TYPE;

		default:
			throw new IllegalArgumentException("���~�� URI�I " + uri);
		}
	}

	// �O�s���
	@Override
	public Uri insert(Uri uri, ContentValues initialValues) {
		ContentValues values;
		SQLiteDatabase db;
		long rowId;

		if (initialValues != null) {
			values = new ContentValues(initialValues);
		} else {
			values = new ContentValues();
		}

		switch (sUriMatcher.match(uri)) {
		//----------------------------- TASKS -----------------------------// 
		case URI_TASKS:
			// ���o��Ʈw���
			db = mOpenHelper.getWritableDatabase();
			// �O�s��ƪ�^���ID
			rowId = db.insert(TABLE_TASK, ColumnTask.KEY._id,
					values);
			if (rowId > 0) {
				Uri thisUri = ContentUris.withAppendedId(ColumnTask.URI,
						rowId);
				getContext().getContentResolver().notifyChange(thisUri, null);
				return thisUri;
			}else {
				throw new SQLException("���J��ƪ�TASKS���� " + uri);	
			}

			//----------------------------- ALERTS -----------------------------// 
		case URI_ALERTS:
			// ���o��Ʈw���
			db = mOpenHelper.getWritableDatabase();
			// �O�s��ƪ�^���ID
			rowId = db.insert(TABLE_ALERT, ColumnAlert.KEY._id,
					values);
			if (rowId > 0) {
				Uri thisUri = ContentUris.withAppendedId(ColumnAlert.URI,
						rowId);
				getContext().getContentResolver().notifyChange(thisUri, null);
				return thisUri;
			}else {
				throw new SQLException("���J��ƪ�ALERTS���� " + uri);	
			}

			//--------------------------- LOCATIONS ---------------------------//
		case URI_LOCATIONS:
			// ���o��Ʈw���
			db = mOpenHelper.getWritableDatabase();
			// �O�s��ƪ�^���ID
			rowId = db.insert(TABLE_ALERT, ColumnAlert.KEY._id,
					values);
			if (rowId > 0) {
				Uri thisUri = ContentUris.withAppendedId(ColumnLocation.URI,
						rowId);
				getContext().getContentResolver().notifyChange(thisUri, null);
				return thisUri;
			}else {
				throw new SQLException("���J��ƪ�LOCATIONS���� " + uri);	
			}

		default:
			throw new IllegalArgumentException("���~�� URI�I " + uri);
		}


	}

	// �R�����
	@Override
	public int delete(Uri uri, String where, String[] whereArgs) {
		// ���o��Ʈw���
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		int count;
		String itemId;

		switch (sUriMatcher.match(uri)) {
		//--------------------------- TASKS ---------------------------// 
		case URI_TASKS:	// �ھګ��w����R��
			count = db.delete(TABLE_TASK, where, whereArgs);
			break;
		case URI_TASK_ID:// �ھګ��w����MID�R��
			itemId = uri.getPathSegments().get(1);
			count = db.delete(TABLE_TASK,
					BaseColumns._ID
					+ "="
					+ itemId
					+ (!TextUtils.isEmpty(where) ? " AND (" + where
							+ ')' : ""), whereArgs);
			break;

			//----------------------------- ALERTS -----------------------------//		
		case URI_ALERTS:	// �ھګ��w����R��
			count = db.delete(TABLE_ALERT, where, whereArgs);
			break;
		case URI_ALERT_ID:// �ھګ��w����MID�R��
			itemId = uri.getPathSegments().get(1);
			count = db.delete(TABLE_ALERT,
					BaseColumns._ID
					+ "="
					+ itemId
					+ (!TextUtils.isEmpty(where) ? " AND (" + where
							+ ')' : ""), whereArgs);
			break;

			//--------------------------- LOCATIONS ---------------------------//
		case URI_LOCATIONS:	// �ھګ��w����R��
			count = db.delete(TABLE_LOCATION, where, whereArgs);
			break;
		case URI_LOCATION_ID:// �ھګ��w����MID�R��
			itemId = uri.getPathSegments().get(1);
			count = db.delete(TABLE_LOCATION,
					BaseColumns._ID
					+ "="
					+ itemId
					+ (!TextUtils.isEmpty(where) ? " AND (" + where
							+ ')' : ""), whereArgs);
			break;

		default:
			throw new IllegalArgumentException("���~�� URI " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	// ��s���
	@Override
	public int update(Uri uri, ContentValues values, String where,
			String[] whereArgs) {
		// ���o��Ʈw���
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		int count;
		String itemId;

		switch (sUriMatcher.match(uri)) {
		//--------------------------- TASKS ---------------------------// 
		// �ھګ��w�����s
		case URI_TASKS:
			count = db.update(TABLE_TASK, values, where, whereArgs);
			break;
			// �ھګ��w����MID��s
		case URI_TASK_ID:
			itemId = uri.getPathSegments().get(1);
			count = db.update(TABLE_TASK, values,
					BaseColumns._ID
					+ "="
					+ itemId
					+ (!TextUtils.isEmpty(where) ? " AND (" + where
							+ ')' : ""), whereArgs);
			break;

			//------------------------- ALERTS ------------------------//
			// �ھګ��w�����s
		case URI_ALERTS:
			count = db.update(TABLE_ALERT, values, where, whereArgs);
			break;
			// �ھګ��w����MID��s
		case URI_ALERT_ID:
			itemId = uri.getPathSegments().get(1);
			count = db.update(TABLE_ALERT, values,
					BaseColumns._ID
					+ "="
					+ itemId
					+ (!TextUtils.isEmpty(where) ? " AND (" + where
							+ ')' : ""), whereArgs);
			break;

			//----------------------- LOCATIONS -----------------------//
			// �ھګ��w�����s
		case URI_LOCATIONS:
			count = db.update(TABLE_LOCATION, values, where, whereArgs);
			break;
			// �ھګ��w����MID��s
		case URI_LOCATION_ID:
			itemId = uri.getPathSegments().get(1);
			count = db.update(TABLE_LOCATION, values,
					BaseColumns._ID
					+ "="
					+ itemId
					+ (!TextUtils.isEmpty(where) ? " AND (" + where
							+ ')' : ""), whereArgs);
			break;

		default:
			throw new IllegalArgumentException("���~�� URI " + uri);
		}

		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	static {
		// Uriƥ�ǰt�u�����O
		// Task
		sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		sUriMatcher.addURI(CommonVar.AUTHORITY, TABLE_TASK, URI_TASKS);
		sUriMatcher.addURI(CommonVar.AUTHORITY, TABLE_TASK + "/#",
				URI_TASK_ID);
		// Alert
		sUriMatcher.addURI(CommonVar.AUTHORITY,TABLE_ALERT,URI_ALERTS);
		sUriMatcher.addURI(CommonVar.AUTHORITY,TABLE_ALERT + "/#",
				URI_ALERT_ID);
		// Location
		sUriMatcher.addURI(CommonVar.AUTHORITY, TABLE_LOCATION,URI_LOCATIONS);
		sUriMatcher.addURI(CommonVar.AUTHORITY, TABLE_LOCATION+ "/#",
				URI_LOCATION_ID);

		// ��ҤƬd����춰�X/�K�[�d�����		
		// Task
		sTaskProjectionMap = new HashMap<String, String>();
		for (String element : ColumnTask.PROJECTION) {
			sTaskProjectionMap.put(element, element);
		}
		// Alert
		sTaskAlertProjectionMap = new HashMap<String, String>();
		for (String element : ColumnAlert.PROJECTION) {
			sTaskAlertProjectionMap.put(element, element);
		}
		// Location
		sTaskLocationProjectionMap = new HashMap<String, String>();
		for (String element : ColumnLocation.PROJECTION) {
			sTaskLocationProjectionMap.put(element, element);
		}
	}
}

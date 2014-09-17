package me.iamcxa.remindme.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class LocationDbEditor {
	private Context context;
	private LocationDbHelper DH = null;
	private SQLiteDatabase db;
	public LocationDbEditor(Context context){
		this.context=context;
	}
	
	public void openDB(){
    	DH = new LocationDbHelper(context);  
    }
	
    public void closeDB(){
    	DH.close();    	
    }
    
    public void add(String Json,float lat,float lng,String keyword,String type){
        db = DH.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("_lat", lat);
        values.put("_lng", lng);
        values.put("_keyword", keyword);
        values.put("_type", type);
        values.put("_ApiJson", Json);
        db.insert(LocationDbHelper._TableName, null, values);
    }
    
    public Cursor getAll() {
    	db = DH.getWritableDatabase();
		return db.query(LocationDbHelper._TableName,		//��ƪ�W��
		new String[] {"_ID", "_lat", "_lng","_keyword","_type","_ApiJson"},	//���W��
		null, // WHERE
		null, // WHERE ���Ѽ�
		null, // GROUP BY
		null, // HAVING
		null  // ORDOR BY
		);
	}
    
    public Cursor get(long rowId) {
    	db = DH.getWritableDatabase();
		Cursor cursor = db.query(true,
		LocationDbHelper._TableName,				//��ƪ�W��
		new String[] {"_ID", "_lat", "_lng","_keyword","_type","_ApiJson"},	//���W��
		"_ID="+rowId,				//WHERE
		null, // WHERE ���Ѽ�
		null, // GROUP BY
		null, // HAVING
		null, // ORDOR BY
		null  // ����^�Ǫ�rows�ƶq
		);
 
		// �`�N�G���g�|�X��
		if (cursor != null) {
			cursor.moveToFirst();	//�N���в���Ĥ@�����
		}
		return cursor;
	}
   
    public Cursor getKeywordLocation(String keyword) {
    	db = DH.getWritableDatabase();
		Cursor cursor = db.query(true,
		LocationDbHelper._TableName,				//��ƪ�W��
		new String[] {"_ID", "_lat", "_lng","_keyword","_type","_ApiJson"},	//���W��
		"_keyword=?",				//WHERE
		new String[] {keyword}, // WHERE ���Ѽ�
		null, // GROUP BY
		null, // HAVING
		null, // ORDOR BY
		null  // ����^�Ǫ�rows�ƶq
		);
 
		// �`�N�G���g�|�X��
		if (cursor != null) {
			cursor.moveToFirst();	//�N���в���Ĥ@�����
		}
		return cursor;
	}
    
    public Cursor getTypedLocation(String type) {
    	db = DH.getWritableDatabase();
		Cursor cursor = db.query(true,
		LocationDbHelper._TableName,				//��ƪ�W��
		new String[] {"_ID", "_lat", "_lng","_keyword","_type","_ApiJson"},	//���W��
		"_type=?",				//WHERE
		new String[] {type}, // WHERE ���Ѽ�
		null, // GROUP BY
		null, // HAVING
		null, // ORDOR BY
		null  // ����^�Ǫ�rows�ƶq
		);
 
		// �`�N�G���g�|�X��
		if (cursor != null) {
			cursor.moveToFirst();	//�N���в���Ĥ@�����
		}
		return cursor;
	}
   
}

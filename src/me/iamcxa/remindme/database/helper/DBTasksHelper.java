
/**
 * @author Kent
 *
 */
package me.iamcxa.remindme.database.helper;

import tw.remindme.common.function.MyDebug;
import me.iamcxa.remindme.database.columns.ColumnLocation;
import me.iamcxa.remindme.database.columns.ColumnTask;
import android.R.string;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
/**
 *@version 0.0
 *@since 20141203
 */
public class DBTasksHelper {

	//	private Cursor cursor;
	private Context context;

	//
	private String[] projection, selectionArgs;
	private String shortOrder, selection;

	/**
	 * �D�n���c
	 *@param Context => getActivity()���Y�i���o
	 *@return �L
	 *@throws �L
	 */
	public DBTasksHelper(Context context){

		this.context=context;
	}

	/**
	 * ����k�Ψӳ]�w�ǳƬd�ߪ���ƽd��C
	 *@param ���ǤJ�ѼƧY���w�]����Ҧ��G<br>
	 *1.�����ƪ�Tasks�Ҧ����.<br>
	 *2.selection�MselectionArgs��null.<br>
	 *3.sortOrder��"created DESC".
	 *@return �^�Ǥ@�ӥi�H�M�i�U��Adapter��Cursor��ƪ���C
	 *@throws null
	 */
	public Cursor getCursor(){

		return context.getContentResolver().
				query(ColumnTask.URI, 
						ColumnTask.PROJECTION, null, null, 
						ColumnTask.DEFAULT_SORT_ORDER);
	}

	/**
	 * ����k�Ψӳ]�w�ǳƬd�ߪ���ƽd��C
	 *@param projection 
	 *������String[]�C��ܸ�ƪ�����ܪ����--�ǤJnull�i�^�ǥ���--���L�p�ݦ^�ǥ�����ĳ���ǥΧڪ��w�]�ȡA�ζפJ"ColumnTask.PROJECTION"�o�էڹw���w�q������}�C�C
	 *@param selection 
	 *������String�C�YSQL��(where=..)�y�y�A�p�����ǤJ5�Y����(where==5)�F�i�ǤJ��?�f�tselectionArgs�ϥΡC�Ҧp: <br>
	 *selection="City=����";
	 *@param selectionArgs 
	 *������String[]�C�f�tselection�P?�ϥΡA��ܱ��d�ߪ�����(s)�C�Ҧp�G<br>
	 *selection="City=?"; <br>
	 *selectionArgs={����,�x�_};
	 *@param shortOrder 
	 *������String�C��ܱ��ƧǪ��̾ڻP���ǡCshortOrder="���W [ASC,DESC]";
	 *@return �^�Ǥ@�ӥi�H�M�i�U��Adapter��Cursor��ƪ���C
	 *@throws null
	 */
	public Cursor getCursor(String[] projection,
			String selection,
			String[] selectionArgs, 
			String shortOrder){

		return context.getContentResolver().
				query(ColumnTask.URI, 
						projection, 
						selection, 
						selectionArgs, 
						shortOrder);
	}


	/**
	 * ����k�i���o�S�w�s����Ʀ椧�S�w��쪺�r��C�ݶǤJ���޽s���P���W�١C<br>
	 * �`�N�G���޽s���i��|�H�۸�Ʈw�ק�ӳQ�����C
	 *@param objId �������椧����ID
	 *@param columnName �����OString�C���W�٥�ColumnTask.KEY��k���ѡC�Ҧp�G<br>
	 *getItemString(10, ColumnTask.KEY.title)�i���oid=10��ƪ�title�r��C
	 *@return String
	 *@throws Exception e.toString() or String "null".
	 */
	public String getItemString(int objId, String columnName){
		
		String[] projection={ "_id",columnName };
		String[] argStrings={ String.valueOf(objId) };
		Cursor cursor = getCursor(projection,"_id=?",argStrings,"_id DESC");
		cursor.moveToFirst();
		if (cursor.getCount()>0) {
			try {
	
				return cursor.getString(1).toString();
			} catch (Exception e) {
				// TODO: handle exception
				return e.toString();
			}
		}else {
			return "null";
		}
	}


	/**
	 * ����k�i���o�S�w�s����Ʀ椧�S�w��쪺�ƭȡC�ݶǤJ���޽s���P���W�١C<br>
	 * �`�N�G���޽s���i��|�H�۸�Ʈw�ק�ӳQ�����C
	 *@param objId �������椧����ID
	 *@param columnName �����OString�C���W�٥�ColumnTask.KEY��k���ѡC�Ҧp�G<br>
	 *getItemInt(10, ColumnTask.KEY.title)�i���oid=10��ƪ�title�r��C
	 *@return String
	 *@throws "-1".
	 */
	public int getItemInt(int objId, String columnName){
		
		String[] projection={ "_id",columnName };
		String[] argStrings={ String.valueOf(objId) };
		Cursor cursor = getCursor(projection,"_id=?",argStrings,"_id DESC");
		cursor.moveToFirst();
		if (cursor.getCount()>0) {
			
			return cursor.getInt(1);
		}else {
			
			return -1;
		}
	}
}


package me.iamcxa.remindme.database.helper;

/**
 * @author Kent
 *
 */
import tw.remindme.common.function.MyDebug;
import me.iamcxa.remindme.database.columns.ColumnLocation;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

/**
 *@version 0.1
 *@since 20141203
 */
public class DBLocationHelper {

	private Context context;
	private Uri mUri=ColumnLocation.URI;

	/**
	 * �D�n���c
	 *@param Context => getContext(),getActivity(),getApplicationContext(),...
	 *@return �L
	 *@throws �L
	 */
	public DBLocationHelper(Context context){

		this.context=context;
	}

	/**
	 * ����k�Ψӳ]�w�ǳƬd�ߪ���ƽd��C
	 *@param ���ǤJ�ѼƧY���w�]����Ҧ��G<br>
	 *1.�����ƪ�task_location�Ҧ����.<br>
	 *2.selection�MselectionArgs��null.<br>
	 *3.sortOrder��"created DESC".
	 *@return �^�Ǥ@�ӥi�H�M�i�U��Adapter��Cursor��ƪ���C
	 *@throws null
	 */
	public Cursor getCursor(){

		return context.getContentResolver().
				query(mUri, 
						ColumnLocation.PROJECTION, null, null, 
						ColumnLocation.DEFAULT_SORT_ORDER);
	}

	/**
	 * ����k�Ψӳ]�w�ǳƬd�ߪ���ƽd��C
	 *@param projection 
	 *���O��String[]�C��ܸ�ƪ�����ܪ����--�ǤJnull�i�^�ǥ���--���L�p�ݦ^�ǥ�����ĳ���ǥΧڪ��w�]�ȡA�ζפJ"ColumnLocation.PROJECTION"�o�էڹw���w�q������}�C�C
	 *@param selection 
	 *���O��String�C�YSQL��(where=..)�y�y�A�p�����ǤJ5�Y����(where==5)�F�i�ǤJ��?�f�tselectionArgs�ϥΡC�Ҧp: <br>
	 *selection="City=����";
	 *@param selectionArgs 
	 *���O��String[]�C�f�tselection�P?�ϥΡA��ܱ��d�ߪ�����(s)�C�Ҧp�G<br>
	 *selection="City=?"; <br>
	 *selectionArgs={����,�x�_};
	 *@param shortOrder 
	 *���O��String�C��ܱ��ƧǪ��̾ڻP���ǡCshortOrder="���W [ASC,DESC]";
	 *@return �^�Ǥ@�ӥi�H�M�i�U��Adapter��Cursor��ƪ���C
	 *@throws null
	 */
	public Cursor getCursor(String[] projection,
			String selection,
			String[] selectionArgs, 
			String shortOrder){

		return context.getContentResolver().
				query(mUri, 
						projection, 
						selection, 
						selectionArgs, 
						shortOrder);
	}


	/**
	 * ����k�i���o��ƪ�task_location������`�ƶq�C
	 *@param �L
	 *@return int
	 *@throws null
	 */
	public int getCount(){

		return getCursor().getCount();
	}


	/**
	 * ����k�i���o�S�w�s����Ʀ椧�S�w��쪺�r��C�ݶǤJ���޽s���P���W�١C<br>
	 * �`�N�G���޽s���i��|�H�۸�Ʈw�ק�ӳQ�����C
	 *@param objId ���Oint�F�������椧����ID�C
	 *@param columnName ���O�OString�C���W�٥�ColumnLocation.KEY��k���ѡC�Ҧp�G<br>
	 *getItemString(10, ColumnLocation.KEY.title)�i���oid=10��ƪ�title�r��C
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
	 *@param itemId ���Oint�F�������椧����ID�C
	 *@param columnName ���O�OString�C���W�٥�ColumnLocation.KEY��k���ѡC�Ҧp�G<br>
	 *getItemInt(10, ColumnLocation.KEY.title)�i���oid=10��ƪ�title�r��C
	 *@return String
	 *@throws "-1".
	 */
	public int getItemInt(int itemId, String columnName){

		String[] projection={ "_id",columnName };
		String[] argStrings={ String.valueOf(itemId) };
		Cursor cursor = getCursor(projection,"_id=?",argStrings,"_id DESC");
		cursor.moveToFirst();
		if (cursor.getCount()>0) {

			return cursor.getInt(1);
		}else {

			return -1;
		}
	}


	/**
	 * ����k�i���o�S�w�s����Ʀ椧�S�w��쪺�ƭȡC�ݶǤJ���޽s���P���W�١C<br>
	 * �`�N�G���޽s���i��|�H�۸�Ʈw�ק�ӳQ�����C
	 *@param itemId ���Oint�F�������椧����ID�C
	 *@param columnName ���O�OString�C���W�٥�ColumnLocation.KEY��k���ѡC�Ҧp�G<br>
	 *getItemInt(10, ColumnLocation.KEY.title)�i���oid=10��ƪ�title�r��C
	 *@return String
	 *@throws 0.0.
	 */
	public Double getItemDouble(int itemId, String columnName){

		String[] projection={ "_id",columnName };
		String[] argStrings={ String.valueOf(itemId) };
		Cursor cursor = getCursor(projection,"_id=?",argStrings,"_id DESC");
		cursor.moveToFirst();
		if (cursor.getCount()>0) {

			return cursor.getDouble(1);
		}else {

			return 0.0;
		}
	}


	/**
	 * ����k�i�s�W�@����ƨ��ƪ�task_locations�C<br>
	 *@param locName ���OString�F�a�I�W�١C
	 *@param lat ���OString�F�n�סC
	 *@param lon ���OString�F�g�סC
	 *@param distance ���ODouble�F�Z���C
	 *@param lastUsedTime ���ODouble�F�W���ϥήɶ��C
	 *@param weight ���Oint�F�ϥΪ̳y�X���n(�a�I�v��)�C
	 *@param type ���Oint�F�a�I�����C
	 *@param tag ���OString�F�a�Itag�C
	 *@return True.
	 *@throws False, also logcat will output "DBLocationHelpr additem method error=..."�C
	 */
	public boolean addItem(String locName, String lat,String lon,
			Double distance,Double lastUsedTime,
			int weight,int type,
			String tag){

		ContentValues values=new ContentValues();
		// 0 - ���Ȧa�Iid
		// 1 - �a�I�W�٦r��
		values.put(ColumnLocation.KEY.name,locName);
		// 2 - 3 - �g�n��
		values.put(ColumnLocation.KEY.lat,lat);
		values.put(ColumnLocation.KEY.lon,lon);
		// 4 - �P�W�������a�I���Z��
		values.put(ColumnLocation.KEY.dintance,distance);
		// 5 - �W���ϥήɶ�
		values.put(ColumnLocation.KEY.lastUsedTime,lastUsedTime);
		// 6 - �W���ϥήɶ�
		values.put(ColumnLocation.KEY.weight,weight);
		// 7 - �a�I����
		values.put(ColumnLocation.KEY.type,type);
		// 8 - �a�I����r
		values.put(ColumnLocation.KEY.tag,tag);

		try {
			context.getContentResolver().insert(mUri, values);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			MyDebug.MakeLog(2, "DBLocationHelpr additem method error="+e.toString());
			return false;
		}
	}

	/**
	 * ����k�i��s�@����Ƹ�ƪ�task_locations�����r���ơC<br>
	 *@param itemId ���OString�F�a�I�W�١C
	 *@param targetKey ���OString�F�ؼ����W�١A��ColumnLocation.Key���ѡC
	 *@param newValue ���OString�F�ؼ���쪺�s�ȡC
	 *@return True.
	 *@throws False, also logcat will output "DBLocationHelpr setItem method error=..."�C
	 */
	public boolean setItem(int itemId, String targetKey, String newValue) {

		try {

			Uri thisUri = ContentUris.withAppendedId(mUri,itemId);

			ContentValues values=new ContentValues();

			values.put(targetKey, newValue);

			context.getContentResolver().update(thisUri, values, null, null);

			return true;
		} catch (Exception e) {

			MyDebug.MakeLog(2, "DBLocationHelpr setItem method error="+e.toString());
			return false;
		}
	}

	/**
	 * ����k�i��s�@����Ƹ�ƪ�task_locations�����ƭȸ�ơC<br>
	 *@param itemId ���OString�F�a�I�W�١C
	 *@param targetKey ���OString�F�ؼ����W�١A��ColumnLocation.Key���ѡC
	 *@param newValue ���ODouble�F�ؼ���쪺�s�ȡC
	 *@return True.
	 *@throws False, also logcat will output "DBLocationHelpr setItem method error=..."�C
	 */
	public boolean setItem(int itemId, String targetKey, Double newValue) {

		try {

			Uri thisUri = ContentUris.withAppendedId(mUri,itemId);

			ContentValues values=new ContentValues();

			values.put(targetKey, newValue);

			context.getContentResolver().update(thisUri, values, null, null);

			return true;
		} catch (Exception e) {

			MyDebug.MakeLog(2, "DBLocationHelpr setItem method error="+e.toString());
			return false;
		}
	}
}
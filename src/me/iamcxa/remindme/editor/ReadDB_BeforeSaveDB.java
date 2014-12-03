package me.iamcxa.remindme.editor;

import java.util.ArrayList;

import tw.remindme.common.function.MyDebug;

import me.iamcxa.remindme.R;
import me.iamcxa.remindme.database.columns.ColumnLocation;
import me.iamcxa.remindme.database.columns.ColumnTask;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


public class ReadDB_BeforeSaveDB {

	public final int LOADER_ID_LOCATION = 1;
	public final int LOADER_ID_TASKS = 2;

	private Spinner tasklocation;
	private Context context;
	private String selection="";
	private int lastLocID=-1;
	private int lastTaskID=-1;
	
 	private Cursor data;

	public ReadDB_BeforeSaveDB(Context context){
		super();
		this.context=context;
	}

	private ArrayAdapter<String> setLocationArray(){
		MyDebug.MakeLog(2, "@setLocationArray");


		ArrayAdapter<String> adapter =
				new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		if(data!=null){
			if(tasklocation==null) MyDebug.MakeLog(2, "tasklocation=null");
			if (data.getCount()>0){
				data.moveToFirst();
				adapter.add(context.getResources().getString(R.string.TaskEditor_Field_Location_Spinner_Hint).toString());
				adapter.add(data.getString(data.getColumnIndex("name")));
				if(tasklocation!=null) tasklocation.setEnabled(true);
			}else{
				adapter.add(context.getResources().getString(R.string.TaskEditor_Field_Location_Is_Empty).toString());
				if(tasklocation!=null) tasklocation.setEnabled(false);
			}
		}else {
			MyDebug.MakeLog(2, "data=null");
		}
		return adapter;
	}

	// �NŪ�X����ഫ��  ArrayList ���X
	private ArrayList<String> getContents(Cursor data) {
		data.moveToFirst();
		ArrayList<String> contents = new ArrayList<String>();
		while(!data.isAfterLast()) {
			contents.add(data.getString(data.getColumnIndex("_id")));
			data.moveToNext();
		}
		data.close();
		String content[] = contents.toArray(new String[0]);
		MyDebug.MakeLog(2, content);

		return contents;
	}

	// ���o�� task_location �����S�w���
	public LoaderCallbacks<Cursor>getDBLocID = new LoaderCallbacks<Cursor>(){
		@Override
		public Loader<Cursor> onCreateLoader(int id, Bundle args) {	
			Loader<Cursor> loader = null;
			String selection = null;
			String sortOrder = null;
			String[] selectionArgs = null;
			String[] projection = null;
			switch (id) {
			case LOADER_ID_LOCATION: // location
				selection = "name is not 'null'"; 
				sortOrder = ColumnLocation.DEFAULT_SORT_ORDER;
				selectionArgs = null;
				projection = ColumnLocation.PROJECTION;
				loader = new CursorLoader(context,
						ColumnLocation.URI,
						projection, selection, selectionArgs, sortOrder);
			}
			return loader;
		}
		@Override
		public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
			ArrayList<String> idList=getContents(data);
			lastLocID=Integer.valueOf(idList.get(idList.size()-1));
			MyDebug.MakeLog(2, "getDBLocID lastLocID="+lastLocID);
		}
		@Override
		public void onLoaderReset(Loader<Cursor> loader) {

		}
	};

	// ���o�� tasks �����S�w���
	public LoaderCallbacks<Cursor>getDBTaskID = new LoaderCallbacks<Cursor>(){
		@Override
		public Loader<Cursor> onCreateLoader(int id, Bundle args) {	
			Loader<Cursor> loader = null;
			String selection = null;
			String sortOrder = null;
			String[] selectionArgs = null;
			String[] projection = null;
			switch (id) {
			case LOADER_ID_TASKS: // tasks
				selection = ColumnTask.KEY._id; 
				sortOrder = ColumnTask.DEFAULT_SORT_ORDER;
				selectionArgs = null;
				projection = ColumnTask.PROJECTION;
				loader = new CursorLoader(context,
						ColumnTask.URI,
						projection, selection, selectionArgs, sortOrder);
				MyDebug.MakeLog(2, "@loaderTask.getId="+loader.getId());
				//break;
			}
			return loader;
		}
		@Override
		public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
			ArrayList<String> idList=getContents(data);
			lastTaskID=Integer.valueOf(idList.get(idList.size()-1));
			MyDebug.MakeLog(2, "getDBTaskID lastTaskID="+lastTaskID);
		}
		@Override
		public void onLoaderReset(Loader<Cursor> loader) {

		}
	};

	

}

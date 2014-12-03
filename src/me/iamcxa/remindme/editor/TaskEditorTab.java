package me.iamcxa.remindme.editor;

import java.util.ArrayList;

import tw.remindme.common.function.MyDebug;
import tw.remindme.common.function.MyTabListener;
import tw.remindme.common.view.SlidingTabLayout;
import tw.remindme.taskeditor.adapter.TaskEditorFragmentPagerAdapter;

import me.iamcxa.remindme.R;
import me.iamcxa.remindme.database.columns.ColumnLocation;
import me.iamcxa.remindme.database.columns.ColumnTask;
import android.database.Cursor;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.Toast;

public class TaskEditorTab extends ActionBarActivity 
implements
OnMenuItemClickListener{

	private static CommonEditorVar mEditorVar=CommonEditorVar.GetInstance();
	protected static Act_SaveToDb mSaveOrUpdate;
	private ReadDB_BeforeSaveDB readDB;

	private static Toolbar toolbar;
	private static SlidingTabLayout slidingTabLayout;
	private static ViewPager viewPager;

	private ArrayList<Fragment> fragments;
	private TaskEditorFragmentPagerAdapter viewPager_Adapter;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_editor_tab);
		setupViewComponent();
		//init(this.getIntent());
	}


	// This is the action bar menu
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// ���editor_activity_actionbar.xml���e
		getMenuInflater().inflate(R.menu.editor_activity_actionbar, menu);

		// �ҥ�action bar��^�����b�Y
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayUseLogoEnabled(true);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(getResources().getString(R.string.TaskEditor_ActionBar_Title));

		// actionAdd
		MenuItem actionAdd = menu.findItem(R.id.action_add);
		actionAdd.setOnMenuItemClickListener(this);

		return true;
	}

	// actionbar�b�Y��^�����ʧ@
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// Respond to the action bar's Up/Home button
		case android.R.id.home:
			this.finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}	

	//�]�wtab
	private void setupViewComponent() {

		// ��ҤƤ�������
		toolbar = (Toolbar) findViewById(R.id.taskeditor_toolbar);
		slidingTabLayout = (SlidingTabLayout) findViewById(R.id.taskeditor_tab);
		viewPager = (ViewPager) findViewById(R.id.taskeditor_viewpage);

		// �]�m ViewPager
		fragments = new ArrayList<Fragment>();
		fragments.add(new TaskEditorMain());
		fragments.add(new TaskEditorLocation());
		viewPager_Adapter = new TaskEditorFragmentPagerAdapter(
				getSupportFragmentManager()
				,fragments);
		viewPager.setOffscreenPageLimit(fragments.size());
		viewPager.setAdapter(viewPager_Adapter);
		// �]�mSlidingTab
		slidingTabLayout.setViewPager(viewPager);
		setSupportActionBar(toolbar);  



		//	final ActionBar actBar = getSupportActionBar();
		//	actBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);


		//final ActionBar toolbar = getSupportActionBar();

		//ReadDB_BeforeSaveDB readDB=new ReadDB_BeforeSaveDB(getApplicationContext());
		//getLoaderManager().initLoader(readDB.LOADER_ID_TASKS, null, readDB.getDBTaskID);
		//getLoaderManager().initLoader(readDB.LOADER_ID_LOCATION, null, readDB.getDBLocID);

		//Fragment fragMarriSug = TaskEditorMain.newInstance();


		//		actBar.addTab(actBar.newTab()
		//				.setText("")
		//				.setIcon(getResources().getDrawable(R.drawable.tear_of_calendar)));
		//.setTabListener(new MyTabListener(fragMarriSug)));
		//.setTabListener(new MyTabListener(fragMarriSug)));

		//		Fragment fragGame =  TaskEditorLocation.newInstance();
		//		actBar.addTab(actBar.newTab()
		//				.setText("")
		//				.setIcon(getResources().getDrawable(R.drawable.map_marker))
		//				.setTabListener(new MyTabListener(fragGame)));
		//
		//		Fragment fragVideo = new TaskEditorMain();
		//		actBar.addTab(actBar.newTab()
		//				.setText("")
		//				.setIcon(getResources().getDrawable(android.R.drawable.ic_media_play))
		//				.setTabListener(new MyTabListener(fragVideo)));

	}

	private void btnActionAdd(){
		//�ˬdtitle�O�_����
		boolean isEmpty=(TaskEditorMain.getTaskTitle().contentEquals("null"));
		if(!isEmpty){
			try {

				// ���o��

				mSaveOrUpdate=new Act_SaveToDb(
						getApplicationContext()
						,mEditorVar.Task.getTaskId()
						,lastTaskID(),lastLocID());

				finish();
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(),"ERROR:"+e.toString() , Toast.LENGTH_SHORT).show();
			}
		}else {
			String EmptyMsg=getString(R.string.TaskEditor_Title_Is_Empty);
			Toast.makeText(getApplicationContext(),EmptyMsg , Toast.LENGTH_SHORT).show();
		}

	}

	@Override
	public boolean onMenuItemClick(MenuItem item) {
		// TODO Auto-generated method stub

		String itemName=String.valueOf(item.getTitle());

		if (itemName.contentEquals( "action_add")){

			btnActionAdd();

			lastTaskID();
			lastLocID();

		}else if (itemName.contentEquals("action_refresh")) {
			//btnActionCancel();//�Ȯɨ������\��

		}

		return false;
	}

	// ��a�Iid
	private int lastLocID(){
		Cursor c= getApplicationContext().getContentResolver().
				query(ColumnLocation.URI, ColumnLocation.PROJECTION, null, null, 
						ColumnLocation.DEFAULT_SORT_ORDER);
		int data=0;
		if (c != null) {
			if(c.moveToLast()){
				data = c.getInt(0);
				MyDebug.MakeLog(2,"lastLocID="+data);
			}
			c.close();
		}
		return data;
	}

	// �����id
	private int lastTaskID(){	
		Cursor c= getApplicationContext().getContentResolver().
				query(ColumnTask.URI, ColumnTask.PROJECTION, null, null, 
						ColumnTask.DEFAULT_SORT_ORDER);		
		int data = 0;
		if (c != null) {
			if(c.moveToLast()){
				if(!c.isNull(0)) data = c.getInt(0);
				MyDebug.MakeLog(2,"lastTaskID="+data);
			}
			c.close();
		}

		return data;
	}

	// �NŪ�X����ഫ��  ArrayList ���X
	private ArrayList<String> getContents(Cursor data) {

		data.moveToFirst();

		ArrayList<String> contents = new ArrayList<String>();

		while(!data.isAfterLast()) {
			contents.add(data.getString(data.getColumnIndex("_id")));
			//contents.add(data.getString(data.getColumnIndex("title")));
			//contents.add(data.getString(data.getColumnIndex("title")));
			data.moveToLast();


			String content =  contents.toArray().toString();
			MyDebug.MakeLog(2, "content="+contents);

			return contents;
		}

		data.close();

		return null;

	}


}

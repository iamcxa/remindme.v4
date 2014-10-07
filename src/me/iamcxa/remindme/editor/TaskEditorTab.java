package me.iamcxa.remindme.editor;

import java.util.ArrayList;

import common.MyDebug;
import common.MyTabListener;

import me.iamcxa.remindme.R;
import me.iamcxa.remindme.database.ColumnLocation;
import me.iamcxa.remindme.database.ColumnTask;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class TaskEditorTab extends FragmentActivity 
implements
OnMenuItemClickListener{

	protected static Act_SaveToDb mSaveOrUpdate;


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
		// 抓取editor_activity_actionbar.xml內容
		getMenuInflater().inflate(R.menu.editor_activity_actionbar, menu);

		// 啟用action bar返回首頁箭頭
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayUseLogoEnabled(true);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(getResources().getString(R.string.TaskEditor_ActionBar_Title));

		// actionAdd
		MenuItem actionAdd = menu.findItem(R.id.action_add);
		actionAdd.setOnMenuItemClickListener(this);

		return true;
	}

	// actionbar箭頭返回首頁動作
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

	//設定tab
	private void setupViewComponent() {
		final ActionBar actBar = getActionBar();
		actBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);


		Fragment fragMarriSug = TaskEditorMain.newInstance();
		actBar.addTab(actBar.newTab()
				.setText("")
				.setIcon(getResources().getDrawable(R.drawable.tear_of_calendar))
				.setTabListener(new MyTabListener(fragMarriSug)));

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
		//檢查title是否為空
		boolean isEmpty=(TaskEditorMain.getTaskTitle().contentEquals("null"));
		if(!isEmpty){
			try {
				
				mSaveOrUpdate=new Act_SaveToDb(getApplicationContext());
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

		}else if (itemName.contentEquals( "action_cancel")) {
			//btnActionCancel();//暫時取消此功能
		}

		return false;
	}



}

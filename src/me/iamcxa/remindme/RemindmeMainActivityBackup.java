///*
// * 
// */
//package me.iamcxa.remindme;
//
//
//import me.iamcxa.remindme.cardfragment.ListCursorCardFragment;
//import me.iamcxa.remindme.editor.TaskEditorTab;
//import me.iamcxa.remindme.provider.LocationGetter;
//import me.iamcxa.remindme.service.TaskSortingService;
//import android.app.Fragment;
//import android.app.FragmentManager;
//import android.app.FragmentTransaction;
//import android.app.ProgressDialog;
//import android.app.SearchManager;
//import android.content.Intent;   
//import android.content.res.Configuration;
//import android.os.Bundle;
//import android.preference.PreferenceManager;
//import android.support.v4.app.ActionBarDrawerToggle;
//import android.support.v4.app.FragmentActivity;
//import android.support.v4.view.GravityCompat;
//import android.support.v4.widget.DrawerLayout;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.ListView;
//import android.widget.ProgressBar;
//import android.widget.ShareActionProvider;
//import android.widget.Toast;
//
//
///**
// * @author cxa Main Activity
// */
//public class RemindmeMainActivityBackup extends FragmentActivity {
//	/**********************/
//	/** Variables LOCALE **/
//	/**********************/
//	// Used in savedInstanceState
//	private static ProgressDialog psDialog;
//	private int threadID;
//	private static android.app.FragmentManager fragmentManager;
//	public static int layoutID = 0;
//
//	//ListCursorCardFragment cardview = new ListCursorCardFragment();
//
//	private DrawerLayout mDrawerLayout;
//	private ListView mDrawerList;
//	private ActionBarDrawerToggle mDrawerToggle;
//
//	private CharSequence mDrawerTitle;
//	private CharSequence mTitle;
//	private String[] mPlanetTitles;
//
//	/**********************/
//	/** onCreate LOCALE **/
//	/**********************/
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setLoding("ON");
//
//		RemindmeVar.mPreferences = PreferenceManager
//				.getDefaultSharedPreferences(getApplicationContext());
//
//		// �]�w�Glayout����
//		setContentView(R.layout.activity_main);
//		setNavagationDrawer(savedInstanceState);
//		
//		RemindmeVar.debugMsg(0, "=========================");
//		threadID = android.os.Process.getThreadPriority(android.os.Process
//				.myTid());
//		RemindmeVar.debugMsg(1, threadID + " onCreate");
//
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				threadID = android.os.Process
//						.getThreadPriority(android.os.Process.myTid());
//				RemindmeVar.debugMsg(1, threadID + " pre-setViewComponent");
//				// �]�w�G��������
//				setViewComponent();
//			}
//		}).start();
//
//		StartService();
//
//		if (savedInstanceState != null) {
//			setFragment(3, 0);
//			setLoding("OFF");
//		}
//	}
//
//	/**********************/
//	/** onResume LOCALE **/
//	/**********************/
//	@Override
//	protected void onResume() {
//		// TODO Auto-generated method stub
//		super.onResume();
//
//	}
//
//	/**********************/
//	/** onSaveInstanceState **/
//	/**********************/
//	@Override
//	protected void onSaveInstanceState(Bundle outState) {
//		super.onSaveInstanceState(outState);
//	}
//	
//	private void setNavagationDrawer(Bundle savedInstanceState){
//		mTitle = mDrawerTitle = getTitle();
//		mPlanetTitles = getResources().getStringArray(R.array.drawer_array_CHT);
//		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//		mDrawerList = (ListView) findViewById(R.id.left_drawer);
//
//		// set a custom shadow that overlays the main content when the drawer opens
//		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
//		// set up the drawer's list view with items and click listener
//		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
//				R.layout.drawer_list_item, mPlanetTitles));
//		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
//
//		// enable ActionBar app icon to behave as action to toggle nav drawer
//		getActionBar().setDisplayHomeAsUpEnabled(true);
//		getActionBar().setHomeButtonEnabled(true);
//
//		// ActionBarDrawerToggle ties together the the proper interactions
//		// between the sliding drawer and the action bar app icon
//		mDrawerToggle = new ActionBarDrawerToggle(
//				this,                  /* host Activity */
//				mDrawerLayout,         /* DrawerLayout object */
//				R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
//				R.string.drawer_open,  /* "open drawer" description for accessibility */
//				R.string.drawer_close  /* "close drawer" description for accessibility */
//				) {
//			public void onDrawerClosed(View view) {
//				getActionBar().setTitle(mTitle);
//				invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
//			}
//
//			public void onDrawerOpened(View drawerView) {
//				getActionBar().setTitle(mDrawerTitle);
//				invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
//			}
//		};
//		mDrawerLayout.setDrawerListener(mDrawerToggle);
//
//		if (savedInstanceState == null) {
//			selectItem(0);
//		}
//	}
//
//	/**********************/
//	/** StartService LOCALE **/
//	/**********************/
//	public void StartService() {
//		if (RemindmeVar.IS_SERVICE_ON()) {
//			Intent intent = new Intent(this, TaskSortingService.class);
//			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			startService(intent);
//		}
//	}
//
//	/*************************/
//	/** onCreateOptionsMenu **/
//	/*************************/
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// ��main_activity_actionbar.xmlŪ�����s��T
//		getMenuInflater().inflate(R.menu.main_activity_actionbar, menu);
//
//		// �w�q�G�j�M���s
//		MenuItem actionSearch = menu.findItem(R.id.action_search);
//		actionSearch.setVisible(false);
//		// �]�w�G�I��������
//		// mSearchView = (SearchView) actionSearch.getActionView();
//		// setSearchView(actionSearch);
//
//		// �w�q�G�s�W���s
//		MenuItem actionAdd = menu.findItem(R.id.action_add);
//		// �]�w�G�I��������
//		actionAdd.setOnMenuItemClickListener(btnActionAddClick);
//
//		// �w�q�G������s
//		MenuItem actionRefresh = menu.findItem(R.id.action_refresh);
//		// �]�w�G�I��������
//		actionRefresh.setOnMenuItemClickListener(btnRefreshClick);
//
//		/*
//		 * // �w�q�G���ɫ��s MenuItem actionShare = menu.findItem(R.id.action_share);
//		 * �]�w�Gplay�W��app�s��/���ɵ��B�ͪ���ܦr�� String playStoreLink =
//		 * " https://play.google.com/store/apps/details?id=" + getPackageName();
//		 * String yourShareText = getString(R.string.share_this) +
//		 * playStoreLink; // �]�w�G����intent Intent shareIntent =
//		 * ShareCompat.IntentBuilder.from(this)
//		 * .setType("text/plain").setText(yourShareText).getIntent(); //
//		 * �]�w�G�I�����Ѫ� mShareActionProvider = (ShareActionProvider) actionShare
//		 * .getActionProvider();
//		 * mShareActionProvider.setShareIntent(shareIntent);
//		 */
//
//		// �w�q�G�]�m���s
//		MenuItem actionPref = menu.findItem(R.id.action_settings);
//		// �]�w�G�I��������
//		actionPref.setOnMenuItemClickListener(btnPrefClick);
//		MenuInflater inflater = getMenuInflater();
//		inflater.inflate(R.menu.main, menu);
//		return super.onCreateOptionsMenu(menu);
//	}
//	
//	/* Called whenever we call invalidateOptionsMenu() */
//	@Override
//	public boolean onPrepareOptionsMenu(Menu menu) {
//		// If the nav drawer is open, hide action items related to the content view
//		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
//		menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
//		return super.onPrepareOptionsMenu(menu);
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		// The action bar home/up action should open or close the drawer.
//		// ActionBarDrawerToggle will take care of this.
//		if (mDrawerToggle.onOptionsItemSelected(item)) {
//			return true;
//		}
//		// Handle action buttons
//		switch(item.getItemId()) {
//		case R.id.action_websearch:
//			// create intent to perform web search for this planet
//			Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
//			intent.putExtra(SearchManager.QUERY, getActionBar().getTitle());
//			// catch event that there's no activity to handle intent
//			if (intent.resolveActivity(getPackageManager()) != null) {
//				startActivity(intent);
//			} else {
//				Toast.makeText(this, R.string.app_not_available, Toast.LENGTH_LONG).show();
//			}
//			return true;
//		default:
//			return super.onOptionsItemSelected(item);
//		}
//	}
//	/**
//	 * When using the ActionBarDrawerToggle, you must call it during
//	 * onPostCreate() and onConfigurationChanged()...
//	 */
//
//	@Override
//	protected void onPostCreate(Bundle savedInstanceState) {
//		super.onPostCreate(savedInstanceState);
//		// Sync the toggle state after onRestoreInstanceState has occurred.
//		mDrawerToggle.syncState();
//	}
//
//	@Override
//	public void onConfigurationChanged(Configuration newConfig) {
//		super.onConfigurationChanged(newConfig);
//		// Pass any configuration change to the drawer toggls
//		mDrawerToggle.onConfigurationChanged(newConfig);
//	}
//
//	/**********************/
//	/** setViewComponent **/
//	/**********************/
//	private void setViewComponent() {
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				threadID = android.os.Process
//						.getThreadPriority(android.os.Process.myTid());
//				RemindmeVar.debugMsg(1, threadID + " setViewComponent");
//				// �w�q�Gtabs
//				// tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
//				// pager = (ViewPager) findViewById(R.id.pager);
//				// adapter = new MyPagerAdapter(getSupportFragmentManager());
//				// CommonUtils.debugMsg(0,
//				// "adapter.getCount=" + adapter.getCount());
//				// �]�w�G����margin
//				// final int pageMargin = (int) TypedValue.applyDimension(
//				// TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
//				// .getDisplayMetrics());
//				// pager.setPageMargin(pageMargin);
//				// // �T�O���|�������|�Q�N�~�M��
//				// pager.setOffscreenPageLimit(3);
//				//
//				// // �]�w�Gtab�Ppage��adapter
//				// pager.setAdapter(adapter);
//				// tabs.setViewPager(pager);
//
//				setLoding("OFF");
//			}
//		}).start();
//	}
//
//	/**********************/
//	/** setFragment **/
//	/**********************/
//	// To ensure the target frame will show the right fragment.
//	public void setFragment(final int MODE, final int FragmentPosition) {
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				threadID = android.os.Process
//						.getThreadPriority(android.os.Process.myTid());
//				RemindmeVar.debugMsg(1, threadID + " setFragment "
//						+ FragmentPosition);
//
//				fragmentManager = getFragmentManager();
//				FragmentTransaction fragmentTransaction = fragmentManager
//						.beginTransaction();
//
//				// �]�w�d��
//				ListCursorCardFragment.setSelection( RemindmeVar.TaskCursor.KEY.PRIORITY
//						+ " DESC");
//				//	ListCursorCardFragmentTime.sortOrder = CommonUtils.TaskCursor.KeyColumns.EndDate;
//				//ListCursorCardFragmentLocal.sortOrder = CommonUtils.TaskCursor.KeyColumns.Distance;
//				ListCursorCardFragment.setSelection(null);
//				//ListCursorCardFragmentTime.selection = "TaskLocationName = \"\"";
//				//ListCursorCardFragmentLocal.selection = "TaskLocationName <> \"\"";
//
//				// �Ҧ� - �����ηs�WFragment
//				switch (MODE) {
//				case 0:
//					// Remove Fragment
//					switch (FragmentPosition) {
//					case 0:
//						//fragmentTransaction.remove(cardview);
//						break;
//					case 1:
//						//	fragmentTransaction.remove(cardview1);
//						break;
//					case 2:
//						//fragmentTransaction.remove(cardview2);
//						break;
//					}
//					fragmentTransaction.commit();
//					break;
//
//				case 1:
//					// Replace Fragment
//					switch (FragmentPosition) {
//					case 0:
//						//						fragmentTransaction.replace(R.id.fragment_local,
//						//								cardview2, "cardview2");
//						break;
//					case 1:
//						//						fragmentTransaction.replace(R.id.fragment_time,
//						//								cardview1, "cardview1");
//						break;
//					case 2:
//						//fragmentTransaction.replace(R.id.fragment_main,cardview, "cardview");
//						break;
//					}
//					fragmentTransaction.commit();
//					break;
//				case 3:
//					// int i;
//					// for (i = 0; i < (adapter.getCount())-1; i++) {
//					// setFragment(0, i);
//					// setFragment(1, i);
//					// }
//					break;
//				}
//
//				setLoding("OFF");
//			}
//		}).start();
//
//	}
//
//	/**********************/
//	/** setViewComponent **/
//	/**********************/
//	private void setDataToEditor() {
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				threadID = android.os.Process
//						.getThreadPriority(android.os.Process.myTid());
//				RemindmeVar.debugMsg(1, +threadID + " setDatatoEditor");
//
//			}
//		}).start();
//	}
//
//	/************************/
//	/** set Lode dialog On **/
//	/************************/
//	private void setLoding(String MODE) {
//		if (MODE == "ON") {
//			psDialog = ProgressDialog.show(this, "", "......");
//			/*
//			 * if (!(pBar == null)) { pBar.setVisibility(View.VISIBLE); }
//			 */
//		} else if (MODE == "OFF") {
//			psDialog.dismiss();
//			/*
//			 * if (!(pBar == null)) { pBar.setVisibility(View.GONE); }
//			 */
//		}
//
//	}
//
//	/* The click listner for ListView in the navigation drawer */
//	private class DrawerItemClickListener implements ListView.OnItemClickListener {
//		@Override
//		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//			selectItem(position);
//		}
//	}
//	
//	private void selectItem(int position) {
//		// update the main content by replacing fragments
//		Fragment fragment = new RemindmeFragment();
//		Bundle args = new Bundle();
//		args.putInt(RemindmeFragment.MyFragment.ARG_PLANET_NUMBER, position);
//		fragment.setArguments(args);
//
//		FragmentManager fragmentManager = getFragmentManager();
//		fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
//
//		// update selected item and title, then close the drawer
//		mDrawerList.setItemChecked(position, true);
//		setTitle(mPlanetTitles[position]);
//		mDrawerLayout.closeDrawer(mDrawerList);
//		
//	}
//	
//	/**********************/
//	/** btnActionAddClick **/
//	/**********************/
//	private MenuItem.OnMenuItemClickListener btnActionAddClick = new MenuItem.OnMenuItemClickListener() {
//		@Override
//		public boolean onMenuItemClick(MenuItem item) {
//			// Toast.makeText(getApplication(), item.getTitle(),
//			// Toast.LENGTH_SHORT).show();
//			Intent intent = new Intent();
//			intent.setClass(getApplication(), TaskEditorTab.class);
//			startActivity(intent);
//			return false;
//		}
//	};
//	/************************/
//	/** btnRefreshAddClick **/
//	/************************/
//	private MenuItem.OnMenuItemClickListener btnRefreshClick = new MenuItem.OnMenuItemClickListener() {
//		@Override
//		public boolean onMenuItemClick(MenuItem item) {
//			Toast.makeText(getApplication(), item.getTitle(),
//					Toast.LENGTH_SHORT).show();
//
//			LocationGetter UpdataLocation = new LocationGetter(getApplicationContext());
//			UpdataLocation.UpdateOncePriority();
//
//			ListCursorCardFragment.getmAdapter().notifyDataSetChanged();
//
//			return false;
//		}
//	};
//
//	/**********************/
//	/** menuActionPrefClick **/
//	/**********************/
//	private MenuItem.OnMenuItemClickListener btnPrefClick = new MenuItem.OnMenuItemClickListener() {
//		@Override
//		public boolean onMenuItemClick(MenuItem item) {
//			// Display the fragment as the main content.
//
//			Toast.makeText(getApplication(), item.getTitle(),
//					Toast.LENGTH_SHORT).show();
//
//			Intent intent = new Intent();
//			intent.setClass(getApplicationContext(), RemindmePreference.class);
//			startActivity(intent);
//
//			return false;
//		}
//	};
//
//	/**************************/
//	/** Class MyPagerAdapter **/
//	/**************************/
//	// public class MyPagerAdapter extends FragmentStatePagerAdapter {
//	// // �w�q�Utab�W��
//	// private final int[] TITLES = { R.string.tab1, R.string.tab2,
//	// R.string.tab3 };
//	//
//	// public MyPagerAdapter(FragmentManager fm) {
//	// super(fm);
//	// }
//	//
//	// // This displays tab names and how to switch, if you remove a tab,
//	// // remove its case below but make sure to keep the order (0,1,2)
//	// @Override
//	// public CharSequence getPageTitle(int position) {
//	// Locale l = Locale.getDefault();
//	// switch (position) {
//	// case 0:
//	// return getString(R.string.tab1).toUpperCase(l);
//	// case 1:
//	// return getString(R.string.tab2).toUpperCase(l);
//	// case 2:
//	// return getString(R.string.tab3).toUpperCase(l);
//	// }
//	// return null;
//	// }
//	//
//	// // These are the tabs in the main display, if you remove a tab
//	// // (fragment) you must remove it from below. also, ensure you keep the
//	// // cases in order or it will not know what to do
//	// @Override
//	// public Fragment getItem(int position) {
//	// Fragment f = new Fragment();
//	// switch (position) {
//	// case 0:
//	// f = new CardFragmentLoader0();
//	// setFragment(1, 0);
//	// break;
//	// case 1:
//	// f = new CardFragmentLoader1();
//	// setFragment(1, 1);
//	// break;
//	// case 2:
//	// f = new CardFragmentLoader2();
//	// setFragment(1, 2);
//	// break;
//	// }
//	// return f;
//	// }
//	//
//	// @Override
//	// public int getCount() {
//	// return TITLES.length;
//	// }
//	// };
//}
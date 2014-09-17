/*
 * ******************************************************************************
 *   Copyright (c) 2013-2014 Gabriele Mariotti.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *  *****************************************************************************
 */

package me.iamcxa.remindme.cardfragment;

import common.CommonVar;
import common.MyCalendar;
import common.MyDebug;

import it.gmariotti.cardslib.library.view.CardListView;
import me.iamcxa.remindme.R;
import me.iamcxa.remindme.RemindmeFragment;
import me.iamcxa.remindme.RemindmeMainActivity;
import me.iamcxa.remindme.database.ColumnAlert;
import me.iamcxa.remindme.database.ColumnLocation;
import me.iamcxa.remindme.database.ColumnTask;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * List with Cursor Example
 * 
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class ListCursorCardFragment extends BaseFragment implements
LoaderManager.LoaderCallbacks<Cursor> {
	// getArguments().getInt(FILTER_STRING)
	public static final String FILTER_STRING="FILTER_STRING";
	private static int position;
	private Handler mHandler ;
	private static MyCursorCardAdapter mAdapter;
	private static CardListView mListView;
	private static String[] projectionTask = ColumnTask.PROJECTION;
	private static String[] projectionAlert = ColumnAlert.PROJECTION;
	private static String[] projectionLoc = ColumnLocation.PROJECTION;
	private static String taskSelection = null;
	private static String taskSortOrder = ColumnTask.DEFAULT_SORT_ORDER;
	//
	private static String alertSelection = null;
	private static String alertSortOrder = ColumnAlert.DEFAULT_SORT_ORDER;
	//
	private static String LocSelection = null;
	private static String LocSortOrder = ColumnLocation.DEFAULT_SORT_ORDER;
	private static String[] selectionArgs;
	private static String todayString=MyCalendar.getTodayString(0);

	public static ListCursorCardFragment newInstance() {
		ListCursorCardFragment fragment = new ListCursorCardFragment();
		return fragment;
	}


	/********************/
	/** Initialization **/
	/********************/
	private void init() {

		//int filter = getArguments().getInt(FILTER_STRING);
		int filter =position;

		MyDebug.MakeLog(0, "init�Q����");

		switch (filter) {
		case 0:// ���Ȳ�
			setTaskSelection("due_date_string = 'null'");
			break;
		case 1:// ����
			Toast.makeText(getActivity(), 
					"Today="+todayString, Toast.LENGTH_SHORT).show();
			setTaskSelection("due_date_string = '"+todayString+"'");
			break;
		case 2://���ӤC��
			setTaskSelection("due_date_string IS NOT 'null'");
			break;
		case 3://�M��

			break;
		case 4://�Z���˵�
			//setProjection(projection)
			setTaskSelection("distance IS NOT '0'");
			break;
		case 5://�a���˵�

			break;
		case 6://����
			setTaskSelection("TAG IS NOT 'null'");
			break;
		default:
			break;
		}



		mAdapter = MyCursorCardAdapter.newInstance(getActivity());
		mListView = (CardListView) getActivity().findViewById(
				R.id.carddemo_list_cursor);
		if (mListView != null)mListView.setAdapter(mAdapter);
		
		// Force start background query to load sessions
		getLoaderManager();
		getLoaderManager().restartLoader(101, null, this);
		//getLoaderManager().restartLoader(201, null, this);
		//getLoaderManager().restartLoader(301, null, this);
		LoaderManager.enableDebugLogging(true);
	}



	@Override
	public int getTitleResourceId() {
		return  R.string.app_name;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceStat) {
		View root = inflater.inflate(R.layout.card_fragment_list_cursor,
				container, false);

		return root;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		getLoaderManager().initLoader(101, null, this);
		//getLoaderManager().initLoader(201, null, this);
		//getLoaderManager().initLoader(301, null, this);
		init();
	}


	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {

		Loader<Cursor> loader = null;

		switch (id) {
		case 101:
			loader = new CursorLoader(getActivity(), ColumnTask.URI,
					projectionTask, taskSelection, selectionArgs, taskSortOrder);
			break;
		case 201:
			loader = new CursorLoader(getActivity(), ColumnAlert.URI,
					projectionAlert, alertSelection, selectionArgs, alertSortOrder);
			break;
		case 301:
			loader = new CursorLoader(getActivity(), ColumnLocation.URI,
					projectionLoc, LocSelection, selectionArgs, LocSortOrder);
			break;

		default:
			break;
		}

		return loader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		if (getActivity() == null) {
			return;
		}
		
		mAdapter.swapCursor(data);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		mAdapter.swapCursor(null);
	}

	private Runnable newInit = new Runnable() {

		@Override
		public void run() {
			//setContentShown(true);

			init();

		};
	};
	//-------------------------------------------------//
	public static MyCursorCardAdapter getmAdapter() {
		return mAdapter;
	}

	public static void setmAdapter(MyCursorCardAdapter mAdapter) {
		ListCursorCardFragment.mAdapter = mAdapter;
	}

	public static String getTaskSelection() {
		return taskSelection;
	}

	public static void setTaskSelection(String taskSelection) {
		ListCursorCardFragment.taskSelection = taskSelection;
	}

	public static String getAlertSelection() {
		return alertSelection;
	}

	public static void setAlertSelection(String alertSelection) {
		ListCursorCardFragment.alertSelection = alertSelection;
	}

	public static String getLocSelection() {
		return LocSelection;
	}

	public static void setLocSelection(String locSelection) {
		LocSelection = locSelection;
	}


	/**
	 * @return the position
	 */
	public static int getPosition() {
		return position;
	}


	/**
	 * @param position the position to set
	 */
	public static void setPosition(int position) {
		ListCursorCardFragment.position = position;
	}


}


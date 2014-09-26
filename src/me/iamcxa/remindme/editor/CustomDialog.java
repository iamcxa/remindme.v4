package me.iamcxa.remindme.editor;
import common.MyCalendar;

import me.iamcxa.remindme.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TimePicker;

/**
 * This is a custom dialog class that will hold a tab view with 2 tabs.
 * Tab 1 will be a list view. Tab 2 will be a list view.
 * 
 */
public class CustomDialog extends Dialog
{
	private static  CommonEditorVar mEditorVar=CommonEditorVar.GetInstance();

	/**
	 * Our custom list view adapter for tab 1 listView (listView01).
	 */
	ListView01Adapter listView01Adapter = null;

	/**
	 * Our custom list view adapter for tab2 listView (listView02).
	 */
	ListView02Adapter listView02Adapter = null;


	/**
	 * Default constructor.
	 * 
	 * @param context
	 */
	public CustomDialog(Context context)
	{
		super(context);

		// get this window's layout parameters so we can change the position
		WindowManager.LayoutParams params = getWindow().getAttributes(); 

		// change the position. 0,0 is center
		params.x = 0;
		params.y = 0;
		params.height=-2;
		this.getWindow().setAttributes(params); 
		//this.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.custom_dialog);

		// instantiate our list views for each tab
		ListView listView01 = (ListView)findViewById(R.id.listView01);
		ListView listView02 = (ListView)findViewById(R.id.listView02);

		// register a context menu for all our listView02 items
		registerForContextMenu(listView02);

		// instantiate and set our custom list view adapters
		listView01Adapter = new ListView01Adapter(context);
		listView01.setAdapter(listView01Adapter);

		listView02Adapter = new ListView02Adapter(context);
		listView02.setAdapter(listView02Adapter);

		// bind a click listener to the listView01 list
		listView01.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			public void onItemClick(AdapterView<?> parentView, View childView, int position, long id)
			{                   
				// will dismiss the dialog
				dismiss();
			}           
		});

		// bind a click listener to the listView02 list
		listView02.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			public void onItemClick(AdapterView<?> parentView, View childView, int position, long id)
			{                   
				// will dismiss the dialog
				dismiss();          
			}           
		});

		// get our tabHost from the xml
		TabHost tabs = (TabHost)findViewById(R.id.TabHost01);
		tabs.setup();

		// create tab 1
		String tab1_Title=
				MyCalendar.getThisMonth()
				+getContext().getResources().getString(R.string.String_Task_Editor_Date_Month).toString()
				+MyCalendar.getThisDay()
				+getContext().getResources().getString(R.string.String_Task_Editor_Date_Day).toString();
		TabHost.TabSpec tab1 = tabs.newTabSpec("tab1");
		tab1.setContent(R.id.listView01);
		tab1.setIndicator(tab1_Title);
		tabs.addTab(tab1);

		// create tab 2	
		String tab2_Title=getContext().getResources().getString(R.string.String_Task_Editor_Dialog_Pick_A_Time).toString();
		TabHost.TabSpec tab2 = tabs.newTabSpec("tab2");
		tab2.setContent(R.id.listView02);
		tab2.setIndicator(tab2_Title);
		tabs.addTab(tab2);
		
		// tab3
		TabHost.TabSpec tab3 = tabs.newTabSpec("tab3");
		tab3.setContent(R.id.calendarView1);
		CalendarView cal = (CalendarView)findViewById(R.id.calendarView1);
		tab3.setIndicator("jj");
		tabs.addTab(tab3);
	}

	/**
	 * A custom list adapter for the listView01
	 */
	private class ListView01Adapter extends BaseAdapter
	{        
		public ListView01Adapter(Context context)
		{

		}

		/**
		 * This is used to return how many rows are in the list view
		 */
		public int getCount()
		{
			// add code here to determine how many results we have, hard coded for now

			return 2;
		}

		/**
		 * Should return whatever object represents one row in the
		 * list.
		 */
		public Object getItem(int position)
		{
			return position;
		}

		/**
		 * Used to return the id of any custom data object.
		 */
		public long getItemId(int position)
		{
			return position;
		}

		/**
		 * This is used to define each row in the list view.
		 */
		public View getView(int position, View convertView, ViewGroup parent)
		{            
			View row = convertView;

			// our custom holder will represent the view on each row. See class below.
			ListView01Holder holder = null;

			if(row == null)
			{                                                   
				LayoutInflater inflater = getLayoutInflater();

				// inflate our row from xml
				row = inflater.inflate(R.layout.custom_dialog_list_view_01_row, parent, false);

				// instantiate our holder
				holder = new ListView01Holder(row);

				// set our holder to the row
				row.setTag(holder);


			}
			else
			{
				holder = (ListView01Holder)row.getTag();
			}

			return row;
		}

		// our custom holder
		class ListView01Holder
		{
			// text view
			private TextView text = null;

			// image view
			private ImageView image = null;

			ListView01Holder(View row)
			{    
				// get out text view from xml
				text = (TextView)row.findViewById(R.id.list_view_01_row_text_view);

				// add code here to set the text
				text.setText("ddd");

				// get our image view from xml
				image = (ImageView)row.findViewById(R.id.list_view_01_row_image_view);

				// add code here to determine which image to load, hard coded for now
				image.setImageResource(R.drawable.map_marker);
			}
		}
	}

	/**
	 * A custom list adapter for listView02
	 */
	private class ListView02Adapter extends BaseAdapter
	{        
		public ListView02Adapter(Context context)
		{

		}

		/**
		 * This is used to return how many rows are in the list view
		 */
		public int getCount()
		{
			// add code here to determine how many results we have, hard coded for now

			return 3;
		}

		/**
		 * Should return whatever object represents one row in the
		 * list.
		 */
		public Object getItem(int position)
		{
			return position;
		}

		/**
		 * Used to return the id of any custom data object.
		 */
		public long getItemId(int position)
		{
			return position;
		}

		/**
		 * This is used to define each row in the list view.
		 */
		public View getView(int position, View convertView, ViewGroup parent)
		{            
			View row = convertView;
			ListView02Holder holder = null;

			if(row == null)
			{                                                   
				LayoutInflater inflater = getLayoutInflater();

				row=inflater.inflate(R.layout.custom_dialog_list_view_02_row, parent, false);
				holder = new ListView02Holder(row);
				row.setTag(holder);
			}
			else
			{
				holder = (ListView02Holder)row.getTag();
			}

			return row;
		}

		class ListView02Holder
		{
			private TextView text = null;

			ListView02Holder(View row)
			{
				text = (TextView)row.findViewById(R.id.list_view_02_row_text_view);
				text.setText("sdsda");
			}
		}
	}

	/**
	 * This is called when a long press occurs on our listView02 items.
	 */
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo)
	{
		super.onCreateContextMenu(menu, v, menuInfo);

		menu.setHeaderTitle("Context Menu");  
		menu.add(0, v.getId(), 0, "Delete");  
	}

	/**
	 * This is called when an item in our context menu is clicked.
	 */
	public boolean onContextItemSelected(MenuItem item)
	{  
		if(item.getTitle() == "Delete")
		{

		}  
		else
		{
			return false;
		}

		return true;  
	}  
}
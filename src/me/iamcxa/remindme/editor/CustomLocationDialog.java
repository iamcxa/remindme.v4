package me.iamcxa.remindme.editor;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import common.MyCalendar;

import me.iamcxa.remindme.R;
import me.iamcxa.remindme.provider.GeocodingAPI;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
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
import android.widget.Toast;

/**
 * This is a custom dialog class that will hold a tab view with 2 tabs.
 * Tab 1 will be a list view. Tab 2 will be a list view.
 * 
 */
public class CustomLocationDialog extends DialogFragment implements
	OnMarkerClickListener,
	OnInfoWindowClickListener,
	OnMarkerDragListener
{
	private static  CommonEditorVar mEditorVar=CommonEditorVar.GetInstance();

	/**
	 * Default constructor.
	 * 
	 * @param context
	 */
	private static GoogleMap map;
	private static MapView mapView;
	private View mContentView;
	private static TextView PlaceName;
	
	public CustomLocationDialog newInstance() {
		return new CustomLocationDialog();
		
    }
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	    }

	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	    	getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
	    	mContentView= inflater.inflate(R.layout.activity_task_editor_tab_location, container, false);
	    	PlaceName =(TextView)mContentView.findViewById(R.id.PlaceName);
	    	 MapsInitializer.initialize(getActivity());
	    	 
	    	 switch (GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity()) )
	         {
	             case ConnectionResult.SUCCESS:
//	                 Toast.makeText(getActivity(), "SUCCESS", Toast.LENGTH_SHORT).show();
	                 mapView = (MapView) mContentView.findViewById(R.id.map);
	                 mapView.onCreate(savedInstanceState);
	                 // Gets to GoogleMap from the MapView and does initialization stuff
	                 if(mapView!=null)
	                 {
	                	 mapView.onResume();
	                	 setUpMapIfNeeded();
	                 }
	                 break;
	             case ConnectionResult.SERVICE_MISSING: 
//	                 Toast.makeText(getActivity(), "SERVICE MISSING", Toast.LENGTH_SHORT).show();
	                 break;
	             case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED: 
//	                 Toast.makeText(getActivity(), "UPDATE REQUIRED", Toast.LENGTH_SHORT).show();
	                 break;
	             default: Toast.makeText(getActivity(), GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity()), Toast.LENGTH_SHORT).show();
	         }

//    	     map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
	    	

	        return mContentView;
	    }
	     
	@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onActivityCreated(savedInstanceState);
//			PlaceName = (TextView) mContentView.findViewById(R.id.PlaceName);
		}

	private void setUpMapIfNeeded()
	{
//	    if(map == null)
//	    {
	    	map = mapView.getMap();
	        if(map == null)
	        {
	            Log.d("", "googleMap is null !!!!!!!!!!!!!!!");
	        }else
	        {
	        	map.setMyLocationEnabled(true);
	        	map.getUiSettings().setZoomControlsEnabled(false);
	        	map.setMyLocationEnabled(true);
	        	map.setOnCameraChangeListener(listener);
	        	map.setOnMapClickListener(mapclickListener);
//	        	map.setOnMarkerClickListener(MarkerClickListener);
	    		LatLng nowLoacation;
//	    		if (gpsManager.LastLocation() != null) {
//	    			nowLoacation = new LatLng(gpsManager.LastLocation().getLatitude(),
//	    					gpsManager.LastLocation().getLongitude());
//	    			map.moveCamera((CameraUpdateFactory.newLatLngZoom(nowLoacation,
//	    					map.getMaxZoomLevel() - 4)));
//	    		} else {
	    			nowLoacation = new LatLng(23.6978, 120.961);
	    			map.moveCamera((CameraUpdateFactory.newLatLngZoom(nowLoacation,
	    					map.getMinZoomLevel() + 7)));
//	    		}
	    		map.addMarker(new MarkerOptions().title("當前位置").draggable(true)
	    				.position(nowLoacation));
	        }
//	    }
	}

	private GoogleMap.OnCameraChangeListener listener = new GoogleMap.OnCameraChangeListener() {
		
		@Override
		public void onCameraChange(CameraPosition position) {
			// TODO Auto-generated method stub
//			map.clear();
//			LatLng now = new LatLng(position.target.latitude, position.target.longitude);
//			map.addMarker(new MarkerOptions()
//            .title("目的地")
//            .position(now));
		}
	};
	
	private GoogleMap.OnMapClickListener mapclickListener = new GoogleMap.OnMapClickListener() {
		
		@Override
		public void onMapClick(LatLng point) {
			// TODO Auto-generated method stub
			map.clear();
			LatLng now = new LatLng(point.latitude, point.longitude);
			map.addMarker(new MarkerOptions()
            .title("目的地")
            .position(now)
            .draggable(true));
		}
	};
	
	
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

	@Override
	public void onMarkerDrag(Marker marker) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMarkerDragEnd(Marker marker) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMarkerDragStart(Marker marker) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onInfoWindowClick(Marker marker) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onMarkerClick(Marker marker) {
		// TODO Auto-generated method stub
		return false;
	}  
}
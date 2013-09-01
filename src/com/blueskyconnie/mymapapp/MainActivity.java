package com.blueskyconnie.mymapapp;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class MainActivity extends FragmentActivity {

	private GoogleMap gMap;
	private static LatLng CSW_LATLNG = new LatLng(22.337314,114.153761); 
	private static LatLng WC_LATLNG = new LatLng(22.278315,114.174618); 
	private static LatLng WC_MTR_LATLNG = new LatLng(22.277719,114.173327);
	private static LatLng CSW_MTR_LATLNG = new LatLng(22.335766,114.155437);
	                                                  
	private final static int imgPin = R.drawable.pin;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		gMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
											.getMap();
		
		// add four markers
		Resources resources = this.getResources();
		
		if (gMap != null) {
			
			gMap.moveCamera(CameraUpdateFactory.newLatLng(CSW_LATLNG));
			gMap.animateCamera(CameraUpdateFactory.zoomTo(17));

			gMap.addMarker(new MarkerOptions().title(resources.getString(R.string.csw_title))
					.position(CSW_LATLNG)
					.snippet(resources.getString(R.string.csw_address))
					.icon(BitmapDescriptorFactory.fromResource(imgPin)));

			gMap.addMarker(new MarkerOptions().title(resources.getString(R.string.nearest_mtr_exit))
					.position(CSW_MTR_LATLNG)
					.snippet(resources.getString(R.string.csw_mtr_address))
					.icon(BitmapDescriptorFactory.fromResource(imgPin)));
			
			gMap.addMarker(new MarkerOptions().title(resources.getString(R.string.wc_title))
					.position(WC_LATLNG)
					.snippet(resources.getString(R.string.wc_address))
					.icon(BitmapDescriptorFactory.fromResource(imgPin)));

			gMap.addMarker(new MarkerOptions().title(resources.getString(R.string.nearest_mtr_exit))
					.position(WC_MTR_LATLNG)
					.snippet(resources.getString(R.string.wc_mtr_address))
					.icon(BitmapDescriptorFactory.fromResource(imgPin)));
			
			gMap.setMyLocationEnabled(true);
			gMap.setOnMarkerClickListener(new OnMarkerClickListener(){
				public boolean onMarkerClick(Marker marker) {
					marker.showInfoWindow();
					return false;
				}
			});	
			
			gMap.setInfoWindowAdapter(new InfoWindowAdapter(){

				@Override
				public View getInfoContents(Marker marker) {
					View view = (View) getLayoutInflater().inflate(R.layout.layout_main_popup, null);
					ImageView imgView = (ImageView) view.findViewById(R.id.imageView1);
					TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
					TextView tvSnippet = (TextView) view.findViewById(R.id.tvSnippet);
					
					String marker_title = marker.getTitle();
					String marker_snippet = marker.getSnippet();
					
					Resources res = MainActivity.this.getResources();
					if (marker_title.equals(res.getString(R.string.csw_title))) {
						imgView.setImageResource(R.drawable.tradesquare);
						tvTitle.setText(marker_title);
						tvSnippet.setText(marker_snippet);
					} else if (marker_title.equals(res.getString(R.string.wc_title))) {
						imgView.setImageResource(R.drawable.feva);
						tvTitle.setText(marker_title);
						tvSnippet.setText(marker_snippet);
					} else if (marker_title.equals(res.getString(R.string.nearest_mtr_exit))) {
						imgView.setImageResource(R.drawable.mtr);
						tvTitle.setText(marker_title);
						tvSnippet.setText(marker_snippet);
					}
					return view;
				}

				public View getInfoWindow(Marker marker) {
					return null;
				}
			});
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		if (item.getItemId() == R.id.menu_csw) {
			gMap.moveCamera(CameraUpdateFactory.newLatLng(CSW_LATLNG));
		} else if (item.getItemId() == R.id.menu_wc) {
			gMap.moveCamera(CameraUpdateFactory.newLatLng(WC_LATLNG));
		}
		return super.onMenuItemSelected(featureId, item);
	}
}

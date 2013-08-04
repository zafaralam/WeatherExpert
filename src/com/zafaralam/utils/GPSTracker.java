package com.zafaralam.utils;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class GPSTracker extends Service implements LocationListener{

	private final String TAG = "GPSTracker";
	
	private final Context mContext;
	
	//flag for GPS status
	private boolean isGPSEnabled = false;
	
	//flag for network status
	private boolean isNetworkEnabled = false;
	
	private boolean canGetLocation = false;
	
	private Location location; //location
	private double latitude;
	private double longitude;
	//String locationName; //this will hold the location information like city name
	
	//minimum distance to change Updates in meters
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; //10 meters
	
	//minimum time between updates in milliseconds
	private static final long MIN_TIME_BTW_UPDATES = 1000 * 60 * 1; //1 minute
	
	// Declaring a Location Manager
	protected LocationManager locationManager;
	
	public GPSTracker(Context context){
		this.mContext = context;
		trackLocation();
	}

	private void trackLocation() {
		
		try{
			locationManager = (LocationManager) mContext
					.getSystemService(LOCATION_SERVICE);
			
			//getting GPS Status
			isGPSEnabled = locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER);
			
			isNetworkEnabled = locationManager
					.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
			
			if(!isGPSEnabled && !isNetworkEnabled){
				//load from ip;
			}else{
				this.canGetLocation = true;
				//First get location from Network Provider
				if(isNetworkEnabled){
					locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER
							, MIN_TIME_BTW_UPDATES
							, MIN_DISTANCE_CHANGE_FOR_UPDATES
							, this);
					Log.d(TAG,"Network");
					if(locationManager != null){
						location = locationManager
								.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
						if(location != null){
							setLatitude(location.getLatitude());
							setLongitude(location.getLongitude());
						}
					}
				}
				// if GPS Enabled get lat/lng using GPS Services
				else if(isGPSEnabled){
					if(location == null){
						locationManager.requestLocationUpdates(
								LocationManager.GPS_PROVIDER
								,MIN_TIME_BTW_UPDATES
								,MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
						Log.d(TAG, "GPS Enabled");
						if(locationManager != null){
							location = locationManager
									.getLastKnownLocation(LocationManager.GPS_PROVIDER);
							if(location != null){
								setLatitude(location.getLatitude());
								setLongitude(location.getLongitude());
							}
						}
					}
				}
			}
		}catch (Exception e){
			Log.d(TAG, e.getMessage());
		}
		
		//return location;
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private void setLatitude(double latitude){
		this.latitude = latitude;
	}
	
	private void setLongitude(double longitude){
		this.longitude = longitude;
	}

	public double getLatitude() {
		if(location != null)
			latitude = location.getLatitude();
		return latitude;
	}

	public double getLongitude() {
		if(location != null)
			longitude = location.getLongitude();
		return longitude;
	}
	
	public Location getLocation(){
		return location;
	}
	
	/*
	 * Stop using GPS listener
	 * Calling this function will stop using GPS in your app
	 *
	 */
	public void stopUsingGPS(){
		if(locationManager != null)
			locationManager.removeUpdates(GPSTracker.this);
	}
	
	public boolean isGPSEnabled() {
		return isGPSEnabled;
	}

	public void setGPSEnabled(boolean isGPSEnabled) {
		this.isGPSEnabled = isGPSEnabled;
	}

	public boolean isNetworkEnabled() {
		return isNetworkEnabled;
	}

	public void setNetworkEnabled(boolean isNetworkEnabled) {
		this.isNetworkEnabled = isNetworkEnabled;
	}

	public boolean isCanGetLocation() {
		return canGetLocation;
	}

	public void setCanGetLocation(boolean canGetLocation) {
		this.canGetLocation = canGetLocation;
	}
}

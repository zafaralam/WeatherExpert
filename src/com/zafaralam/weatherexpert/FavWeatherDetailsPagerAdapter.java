package com.zafaralam.weatherexpert;

import java.util.ArrayList;

import com.zafaralam.weatherexpert.contentprovider.WeatherExpertAdapter;
import com.zafaralam.weatherexpert.contentprovider.WeatherExpertContract.FavouritesEnrty;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

public class FavWeatherDetailsPagerAdapter extends FragmentPagerAdapter{

	private final String TAG = "FavWeatherDetailsPagerAdapter";
	private ArrayList<Fragment> mfragments;
	
	public FavWeatherDetailsPagerAdapter(FragmentManager fm,Context context) {
		super(fm);
		
		Log.d(TAG, "Inside Constructor");
		// TODO Auto-generated constructor stub
		mfragments = new ArrayList<Fragment>();
		//Here go through all the favorites in the db and pass them.
		
		WeatherExpertAdapter dbHelper = new WeatherExpertAdapter(context);
		dbHelper.open();
		Cursor cursor;
		String[] projection = {FavouritesEnrty.KEY_FAV_ID};
		
		cursor = dbHelper.query(FavouritesEnrty.TABLE_NAME, projection, null, null, null, null, null);
		
		boolean isBegin = true;
		if(cursor != null){
			if(cursor.getCount() == 1){
				mfragments.add(new TestFragment(cursor.getInt(0)));
			}else
			{
				while(cursor.moveToNext()){
					if(isBegin){
						cursor.moveToPrevious();
						isBegin = false;
					}
					int favid = cursor.getInt(0);
					
					mfragments.add(new TestFragment(favid));
					
				}
			}
			
		}
		else
			Log.d(TAG, "Cursor Is Null");
	}

	@Override
	public Fragment getItem(int position) {
		// TODO Auto-generated method stub
		return mfragments.get(position);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mfragments.size();
	}
	

}

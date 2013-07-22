package com.zafaralam.weatherexpert;

import com.zafaralam.weatherexpert.contentprovider.WeatherExpertAdapter;
import com.zafaralam.weatherexpert.contentprovider.WeatherExpertContract.FavouritesEnrty;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

public class PreferencesActivity extends PreferenceActivity {

	private static final String TAG = "PreferencesActivity";
	public static final String USER_PREFERENCES = "USER_PREFERENCES";
	public static final String PREF_AUTO_UPDATE = "PREF_AUTO_UPDATE";
	public static final String PREF_UPDATE_FREQ = "PREF_UPDATE_FREQ";
	public static final String DEFAULT_LOCATION = "DEFAULT_LOCATION";
	public static final String DEFAULT_PAGE = "DEFAULT_PAGE";

	SharedPreferences prefs;
	private WeatherExpertAdapter dbHelper;
	private ListPreference lpFavorites;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		// setContentView(R.layout.preferences);
		addPreferencesFromResource(R.xml.userpreferences);
		init();
		lpFavorites.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
			
			@Override
			public boolean onPreferenceChange(Preference arg0, Object arg1) {
				// TODO Auto-generated method stub
				String oldId = arg0.getSharedPreferences().getString(DEFAULT_LOCATION, null);
				String newId = String.valueOf(arg1);
				Log.d(TAG,oldId+" : "+ String.valueOf(arg1));
				
				changeDafualtLocation(oldId, newId);

				return true;
			}
		});
		dbHelper.open();
		loadFavourites();
	}

	protected void changeDafualtLocation(String oldId, String newId) {
		// TODO Auto-generated method stub
		String where;
		ContentValues cv = new ContentValues();
		
		/**************************************
		 * Update the old favorites to the new one
		 **************************************/
		cv.put(FavouritesEnrty.KEY_DEFAULT_LOC,0);
		
		where = FavouritesEnrty.KEY_FAV_ID + " = " + oldId;
		
		dbHelper.update(FavouritesEnrty.TABLE_NAME, cv, where, null);
		
		where = FavouritesEnrty.KEY_FAV_ID + " = " + newId;
		cv.clear();
		
		cv.put(FavouritesEnrty.KEY_DEFAULT_LOC,1);
		
		dbHelper.update(FavouritesEnrty.TABLE_NAME, cv, where, null);
		
		loadFavourites();
	}

	private void init() {
		// TODO Auto-generated method stub
		dbHelper = new WeatherExpertAdapter(getApplicationContext());
		lpFavorites = (ListPreference) findPreference(DEFAULT_LOCATION);
	}

	private void loadFavourites() {
		String[] projection = { FavouritesEnrty.KEY_FAV_ID,
				FavouritesEnrty.KEY_CITYNAME, FavouritesEnrty.KEY_DEFAULT_LOC };
		String default_location_id = null;
		Cursor c = dbHelper.query(FavouritesEnrty.TABLE_NAME, projection, null,
				null, null, null, null);
		CharSequence[] entrites = new CharSequence[c.getCount()];
		CharSequence[] entryValues = new CharSequence[c.getCount()];

		boolean isBegin = true;

		if (c != null) {
			// Load into the listprefrence
			int i = 0;
			if (c.getCount() == 1) {
				entryValues[i] = String.valueOf(c.getInt(0));
				entrites[i] = c.getString(1);
				if (c.getInt(2) == 1) {
					default_location_id = c.getString(1);
				}
			} else {

				while (c.moveToNext()) {
					if (isBegin) {
						c.moveToPrevious();
						isBegin = false;
					}
					entryValues[i] = String.valueOf(c.getInt(0));
					entrites[i] = c.getString(1);
					if (c.getInt(2) == 1) {
						default_location_id = c.getString(1);// c.getInt(0);
					}
					// do enrty here.
//					Log.d(TAG,c.getString(1)+" : " + String.valueOf(c.getInt(0))
//							+ " : " +String.valueOf(c.getInt(2)));
					i++;
				}
			}

			lpFavorites.setEntries(entrites);
			lpFavorites.setEntryValues(entryValues);
			if (default_location_id != null)
				lpFavorites.setDefaultValue(default_location_id);
			else
				lpFavorites.setDefaultValue(null);

		}
	}

}

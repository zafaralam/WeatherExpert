/*
 * Tasks to be completed for the service to operational
 * 
 * 1) Get if user has select auto refresh
 * 2) Get the refresh frequency from the preferences.
 * 3) Add alarm to run the service in specific times.
 * 4) Modify weather fragment to read data of the DB when app starts.
 * 5) Allow user to force refresh data.
 * 6)Add error handling to the service so that it gracefully terminates the service
 * 		and does not hinder with the user experience.
 * 7) Also think about speed of the update as its going to be a problem if more
 * 		than 5 favorites are added by the user.
 * 
 * 8) Avoid restricting the user form not adding favorites because the app cannot handle the data.
 * 9) Add functionality to only force refresh the data for the particular weather when hit refresh.
 * 10) Remove data from the weatherdetails table where favId is not in favorites.
 * 
 */

package com.zafaralam.weatherexpert.service;

import java.util.List;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;

import com.zafaralam.modal.CurrentWeather;
import com.zafaralam.modal.DayWeather;
import com.zafaralam.modal.Weather;
import com.zafaralam.utils.NetworkDetails;
import com.zafaralam.utils.ParserType;
import com.zafaralam.weatherexpert.PreferencesActivity;
import com.zafaralam.weatherexpert.WeatherExpertAlarmReceiver;
import com.zafaralam.weatherexpert.contentprovider.WeatherExpertAdapter;
import com.zafaralam.weatherexpert.contentprovider.WeatherExpertContract.FavouritesEnrty;
import com.zafaralam.weatherexpert.contentprovider.WeatherExpertContract.WeatherDetailsEntry;
import com.zafaralam.xmlparser.FeedParser;
import com.zafaralam.xmlparser.FeedParserFactory;

public class WeatherDetailsUpdateService extends IntentService {

	private static String TAG = "WeatherDetailsUpdateService";
	private AlarmManager alarmManager;
	private PendingIntent alarmIntent;

	public WeatherDetailsUpdateService() {
		super("WeatherDetailsUpdateService");
		// TODO Auto-generated constructor stub
	}

	public WeatherDetailsUpdateService(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		Log.i(TAG, "Inside the onHandleIntent(Intent intent)");
		getWeatherDetailsForFavourites();
	}
	
	
	protected void onHandleIntent(Intent intent, int favId){
		getWeatherDetailsForFavourites(favId);
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
		
		String ALARM_ACTION = WeatherExpertAlarmReceiver.ACTION_REFRESH_WEATHERDETAILS_ALARM;
		Intent intentToFire = new Intent(ALARM_ACTION);
		alarmIntent = PendingIntent.getBroadcast(this, 0, intentToFire, 0);

	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		
		Context context = getApplicationContext();
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		int updateFreq = Integer.parseInt(prefs.getString(PreferencesActivity.PREF_UPDATE_FREQ, "240"));
		boolean autoUpdateChecked = prefs.getBoolean(PreferencesActivity.PREF_AUTO_UPDATE, false);
		
		if(autoUpdateChecked){
			int alarmType = AlarmManager.ELAPSED_REALTIME_WAKEUP;
			long timeToRefresh = SystemClock.elapsedRealtime()+
									updateFreq*60*1000;
			alarmManager.setInexactRepeating(alarmType, timeToRefresh, 
													updateFreq*60*1000, alarmIntent);
			
		}else
			alarmManager.cancel(alarmIntent);
		
		return super.onStartCommand(intent, flags, startId);
	};

	private void getWeatherDetailsForFavourites() {

		WeatherExpertAdapter dbHelper = new WeatherExpertAdapter(
				getApplicationContext());
		dbHelper.open();

		/* Condition (10) to be done here */
		String sqlDeleteWeatherItems = "DELETE FROM " + WeatherDetailsEntry.TABLE_NAME
				+ " WHERE " + WeatherDetailsEntry.KEY_WD_FAV_ID
				+ " NOT IN ( SELECT "+ FavouritesEnrty.KEY_FAV_ID
				+ " FROM " + FavouritesEnrty.TABLE_NAME + " )";
		
		//dbHelper.delete(WeatherDetailsEntry.TABLE_NAME, sqlDeleteWeatherItems, null);
		Cursor delC = dbHelper.query(sqlDeleteWeatherItems, null);
		//Log.d(TAG, String.valueOf(delC.getCount()));
		String feedUrl = null;
		int favId = 0;
		String locationName = null;

		String[] projection = { FavouritesEnrty.KEY_FAV_ID,
				FavouritesEnrty.KEY_CITYNAME, FavouritesEnrty.KEY_LAT,
				FavouritesEnrty.KEY_LNG };

		Cursor c = dbHelper.query(FavouritesEnrty.TABLE_NAME, projection, null,
				null, null, null, null);

		// Closing the cursor as its going to be opened again.
		dbHelper.close();

		boolean isBegin = true;
		if (c != null) {
			if (c.getCount() != 0) {
				if (c.getCount() == 1) {
					// build the feedUrl here
					favId = c.getInt(0);
					locationName = c.getString(1);
					feedUrl = "http://api.worldweatheronline.com/free/v1/weather.ashx?q="
							+ c.getString(2)// Latitude
							+ ","
							+ c.getString(3)// Longitude
							+ "&format=xml&num_of_days=5&key=hkk8gqf7n85dhzns5xmhxa5f";
					saveWeatherDetailsDataForLocation(ParserType.XML_PULL,
							feedUrl, favId, locationName);
				} else {
					while (c.moveToNext()) {
						if (isBegin) {
							c.moveToPrevious();
							isBegin = false;
						}

						// build the feedUrl here
						favId = c.getInt(0);
						locationName = c.getString(1);
						feedUrl = "http://api.worldweatheronline.com/free/v1/weather.ashx?q="
								+ c.getString(2)// Latitude
								+ ","
								+ c.getString(3)// Longitude
								+ "&format=xml&num_of_days=5&key=hkk8gqf7n85dhzns5xmhxa5f";
						saveWeatherDetailsDataForLocation(ParserType.XML_PULL,
								feedUrl, favId, locationName);
					}

				}
			}
		}
	}
	
	private void getWeatherDetailsForFavourites(int favId) {

		WeatherExpertAdapter dbHelper = new WeatherExpertAdapter(
				getApplicationContext());
		dbHelper.open();

		/* Condition (10) to be done here */
		String sqlDeleteWeatherItems = "DELETE FROM " + WeatherDetailsEntry.TABLE_NAME
				+ " WHERE " + WeatherDetailsEntry.KEY_WD_FAV_ID
				+ " NOT IN ( SELECT "+ FavouritesEnrty.KEY_FAV_ID
				+ " FROM " + FavouritesEnrty.TABLE_NAME + " )";
		
		//dbHelper.delete(WeatherDetailsEntry.TABLE_NAME, sqlDeleteWeatherItems, null);
		Cursor delC = dbHelper.query(sqlDeleteWeatherItems, null);
		//Log.d(TAG, String.valueOf(delC.getCount()));
		String feedUrl = null;
		//int favId = 0;
		String locationName = null;

		String[] projection = { FavouritesEnrty.KEY_FAV_ID,
				FavouritesEnrty.KEY_CITYNAME, FavouritesEnrty.KEY_LAT,
				FavouritesEnrty.KEY_LNG };
		
		String where = FavouritesEnrty.KEY_FAV_ID + " = " + favId;

		Cursor c = dbHelper.query(FavouritesEnrty.TABLE_NAME, projection, null,
				null, null, null, null);

		// Closing the cursor as its going to be opened again.
		dbHelper.close();

		boolean isBegin = true;
		if (c != null) {
			if (c.getCount() != 0) {
				if (c.getCount() == 1) {
					// build the feedUrl here
					favId = c.getInt(0);
					locationName = c.getString(1);
					feedUrl = "http://api.worldweatheronline.com/free/v1/weather.ashx?q="
							+ c.getString(2)// Latitude
							+ ","
							+ c.getString(3)// Longitude
							+ "&format=xml&num_of_days=5&key=hkk8gqf7n85dhzns5xmhxa5f";
					saveWeatherDetailsDataForLocation(ParserType.XML_PULL,
							feedUrl, favId, locationName);
				} else {
					while (c.moveToNext()) {
						if (isBegin) {
							c.moveToPrevious();
							isBegin = false;
						}

						// build the feedUrl here
						favId = c.getInt(0);
						locationName = c.getString(1);
						feedUrl = "http://api.worldweatheronline.com/free/v1/weather.ashx?q="
								+ c.getString(2)// Latitude
								+ ","
								+ c.getString(3)// Longitude
								+ "&format=xml&num_of_days=5&key=hkk8gqf7n85dhzns5xmhxa5f";
						saveWeatherDetailsDataForLocation(ParserType.XML_PULL,
								feedUrl, favId, locationName);
					}

				}
			}
		}
	}

	private void saveWeatherDetailsDataForLocation(ParserType type,
			String feedUrl, int favId, String locationName) {

		if (NetworkDetails.isNetworkAvailable(getApplicationContext())) {
			try {
				List<Weather> weathers;

				FeedParser parser = FeedParserFactory.getParser(type, feedUrl);
				weathers = parser.parseWeather();
				saveWeatherDeatils(weathers, favId, locationName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			Log.d(TAG, "Network Not Available");
		}

	}

	private void saveWeatherDeatils(List<Weather> weathers, int favId,
			String locationName) {
		// TODO Auto-generated method stub
		WeatherExpertAdapter dbHelper = new WeatherExpertAdapter(
				getApplicationContext());
		dbHelper.open();

		// Delete from weatherdetails table any records that exist for the favId
		// String rawSQL = "DELETE FROM " + WeatherDetailsEntry.TABLE_NAME +
		// " WHERE "
		// + WeatherDetailsEntry.KEY_WD_FAV_ID + " = " + String.valueOf(favId);
		/*
		 * Deleting the weather deatails before adding the new details
		 */
		String where = WeatherDetailsEntry.KEY_WD_FAV_ID + " = "
				+ String.valueOf(favId);
		dbHelper.delete(WeatherDetailsEntry.TABLE_NAME, where, null);

		/*
		 * Add functionality to only get data items that are for the day.
		 * 
		 * some checks are required for the different days of weather data.
		 */

		for (Weather wd : weathers) {
			ContentValues cv = new ContentValues();

			if(wd instanceof CurrentWeather){
				cv.put(WeatherDetailsEntry.KEY_LOCATION, locationName);
				cv.put(WeatherDetailsEntry.KEY_OBSERVATION_TIME,
						((CurrentWeather) wd).getObservationTime());
				cv.put(WeatherDetailsEntry.KEY_TEMP_C, ((CurrentWeather) wd).getTemp_C());
				cv.put(WeatherDetailsEntry.KEY_TEMP_F, ((CurrentWeather) wd).getTemp_F());
				cv.put(WeatherDetailsEntry.KEY_HUMIDITY, ((CurrentWeather) wd).getHumidity());
				cv.put(WeatherDetailsEntry.KEY_VISIBILITY, ((CurrentWeather) wd).getVisibility());
				cv.put(WeatherDetailsEntry.KEY_PRESSURE, ((CurrentWeather) wd).getPressure());
				cv.put(WeatherDetailsEntry.KEY_CLOUDCOVER, ((CurrentWeather) wd).getCloudCover());
			}
			else{
				cv.put(WeatherDetailsEntry.KEY_TEMPMAX_C, ((DayWeather) wd).getTempMax_C());
				cv.put(WeatherDetailsEntry.KEY_TEMPMAX_F, ((DayWeather) wd).getTempMax_F());
				cv.put(WeatherDetailsEntry.KEY_TEMPMIN_C, ((DayWeather) wd).getTempMin_C());
				cv.put(WeatherDetailsEntry.KEY_TEMPMIN_F, ((DayWeather) wd).getTempMin_F());
				cv.put(WeatherDetailsEntry.KEY_WINDDIRECTION, ((DayWeather) wd).getWindDirection());
			}
			cv.put(WeatherDetailsEntry.KEY_WD_FAV_ID, favId);
			
			
			cv.put(WeatherDetailsEntry.KEY_WEATHER_CONDITION,
					wd.getWeather_condition());
			cv.put(WeatherDetailsEntry.KEY_DATE, String.valueOf(wd.getDate()));
			cv.put(WeatherDetailsEntry.KEY_WINDSPEEDMILES,
					wd.getWindSpeedMiles());
			cv.put(WeatherDetailsEntry.KEY_KEY_WINDSPEEDKMPH,
					wd.getWindSpeedKmph());
			cv.put(WeatherDetailsEntry.KEY_WINDDIRDEGREE, wd.getWindDirDegree());
			cv.put(WeatherDetailsEntry.KEY_WINDDIR16POINT,
					wd.getWindDir16Point());
			cv.put(WeatherDetailsEntry.KEY_WEATHERICONURL,
					wd.getWeatherIconUrl());
			cv.put(WeatherDetailsEntry.KEY_WEATHERDESC, wd.getWeatherDesc());
			cv.put(WeatherDetailsEntry.KEY_PRECIPMM, wd.getPrecipMM());
			
			
			//cv.put(WeatherDetailsEntry.KEY_WEATHERTYPE, "");

			dbHelper.insert(WeatherDetailsEntry.TABLE_NAME, null, cv);

		}

		dbHelper.close();

	}
}

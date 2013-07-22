package com.zafaralam.weatherexpert.contentprovider;

import com.zafaralam.weatherexpert.contentprovider.WeatherExpertContract.FavouritesEnrty;
import com.zafaralam.weatherexpert.contentprovider.WeatherExpertContract.RecentLocationsEntry;
import com.zafaralam.weatherexpert.contentprovider.WeatherExpertContract.WeatherDetailsEntry;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class WeatherExpertDatabaseHelper extends SQLiteOpenHelper{
		private static final String TAG = "WeatherExpertDBHelper";

		public static final String DATABASE_NAME = "weatherexpert.db";
		public static final int DATABASE_VERSION = 2;
		
		private static final String DATABASE_CREATE_WEATHER_DETAILS =
				"CREATE TABLE IF NOT EXISTS " + WeatherDetailsEntry.TABLE_NAME + " ("
						+ WeatherDetailsEntry.KEY_WD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
						+ WeatherDetailsEntry.KEY_WD_FAV_ID + " INTEGER, "
						+ WeatherDetailsEntry.KEY_LOCATION + " TEXT, "
						+ WeatherDetailsEntry.KEY_OBSERVATION_TIME + " TEXT, "
						+ WeatherDetailsEntry.KEY_TEMP_C + " INTEGER, "
						+ WeatherDetailsEntry.KEY_TEMP_F + " INTEGER, "
						+ WeatherDetailsEntry.KEY_WEATHER_CONDITION + " INT, "
						+ WeatherDetailsEntry.KEY_DATE + " TEXT, "
						+ WeatherDetailsEntry.KEY_WINDSPEEDMILES + " INTEGER, "
						+ WeatherDetailsEntry.KEY_KEY_WINDSPEEDKMPH + " INTEGER, "
						+ WeatherDetailsEntry.KEY_WINDDIRDEGREE + " INTEGER, "
						+ WeatherDetailsEntry.KEY_WINDDIR16POINT + " TEXT, "
						+ WeatherDetailsEntry.KEY_WEATHERICONURL + " TEXT, " 
						+ WeatherDetailsEntry.KEY_WEATHERDESC + " TEXT, "
						+ WeatherDetailsEntry.KEY_PRECIPMM + " FLOAT, " 
						+ WeatherDetailsEntry.KEY_HUMIDITY + " INTEGER, "
						+ WeatherDetailsEntry.KEY_VISIBILITY + " INTEGER, " 
						+ WeatherDetailsEntry.KEY_PRESSURE + " INTEGER, "
						+ WeatherDetailsEntry.KEY_CLOUDCOVER + " INTEGER, " 
						+ WeatherDetailsEntry.KEY_TEMPMAX_C + " INTEGER, "
						+ WeatherDetailsEntry.KEY_TEMPMAX_F + " INTEGER, " 
						+ WeatherDetailsEntry.KEY_TEMPMIN_C + " INTEGER, "
						+ WeatherDetailsEntry.KEY_TEMPMIN_F + " INTEGER, " 
						+ WeatherDetailsEntry.KEY_WINDDIRECTION + " TEXT, "
						+ WeatherDetailsEntry.KEY_WEATHERTYPE + " INTEGER );";

		
		private static final String DATABASE_DROP_WEATHER_DETAILS = "DROP TABLE IF EXISTS " + WeatherDetailsEntry.TABLE_NAME;
		
		private static final String DATABASE_CREATE_FAVOURITES =
						"CREATE TABLE IF NOT EXISTS " + FavouritesEnrty.TABLE_NAME + " ("
						+ FavouritesEnrty.KEY_FAV_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
						+ FavouritesEnrty.KEY_CITYNAME + " TEXT, "
						+ FavouritesEnrty.KEY_LAT + " TEXT, "
						+ FavouritesEnrty.KEY_LNG + " TEXT, "
						+ FavouritesEnrty.KEY_POSTCODE + " INTEGER, "
						+ FavouritesEnrty.KEY_IPADDRESS + " TEXT, "
						+ FavouritesEnrty.KEY_DEFAULT_LOC + " INTEGER)"
						+";";
		
		private static final String DATABASE_DROP_FAVOURITES = "DROP TABLE IF EXISTS " + FavouritesEnrty.TABLE_NAME;
		
		private static final String DATABASE_CREATE_RECENTLOCATIONS =
				"CREATE TABLE IF NOT EXISTS " + RecentLocationsEntry.TABLE_NAME + "("
				+ RecentLocationsEntry.KEY_RECLOC_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ RecentLocationsEntry.KEY_AREANAME + " TEXT, "
				+ RecentLocationsEntry.KEY_COUNTRY + " TEXT, "
				+ RecentLocationsEntry.KEY_REGION + " TEXT, "
				+ RecentLocationsEntry.KEY_LAT + " TEXT, "
				+ RecentLocationsEntry.KEY_LNG + " TEXT)"
				+";";
		
		private static final String DAATABASE_DROP_RECENTLOCATIONS = "DROP TABLE IF EXISTS " + RecentLocationsEntry.TABLE_NAME;
		
		public WeatherExpertDatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL(DATABASE_CREATE_WEATHER_DETAILS);
			db.execSQL(DATABASE_CREATE_FAVOURITES);
			db.execSQL(DATABASE_CREATE_RECENTLOCATIONS);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
					+ newVersion + ", which will destroy all old data");
			
			/*************************************************************
			 * Before dropping table save to variable of the type
			 *************************************************************/

			//db.execSQL(DATABASE_DROP_WEATHER_DETAILS);
			//db.execSQL(DATABASE_DROP_FAVOURITES);
			onCreate(db);
			
			/************************************************************
			 * After recreating the table, add data into the tables again.
			 ************************************************************/
		}
		
		@Override
		public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			Log.w(TAG, "Downgrading database from version " + oldVersion + " to "
					+ newVersion + ", which will destroy all old data");
			onUpgrade(db, oldVersion, newVersion);
		}

	}
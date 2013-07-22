package com.zafaralam.weatherexpert.contentprovider;


import com.zafaralam.weatherexpert.contentprovider.WeatherExpertContract.WeatherDetailsEntry;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;


public class WeatherExpertProvider extends ContentProvider {

	private static final String PROVIDER_NAME= "com.zafaralam.weatherexpertprovider";
	private static final String WEATHERDETAILS_PATH = "weatherdetails";
	
	public static final Uri CONTENT_URI_WD = 
			Uri.parse("content://"+ PROVIDER_NAME +"/" + WEATHERDETAILS_PATH);
	

	WeatherExpertDatabaseHelper dbHelper;

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		Context context = getContext();

		dbHelper = new WeatherExpertDatabaseHelper(context);

		return true;
	}

	private static final int WEATHERDETAILS = 1;
	private static final int WEATHERDETAILS_ID = 2;
	private static final int WEATHERDETAILS_FAV_ID = 3;


	private static final UriMatcher uriMatcher;

	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(PROVIDER_NAME, WEATHERDETAILS_PATH, WEATHERDETAILS);
		uriMatcher.addURI(PROVIDER_NAME, WEATHERDETAILS_PATH + "/#", WEATHERDETAILS_ID);
		uriMatcher.addURI(PROVIDER_NAME, SearchManager.SUGGEST_URI_PATH_QUERY, WEATHERDETAILS_FAV_ID);
	};

	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri arg0, ContentValues arg1) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Uri uri = null;

		switch (uriMatcher.match(arg0)) {
		
		case WEATHERDETAILS:
			long rowID2 = db.insert(
					WeatherDetailsEntry.TABLE_NAME, "weatherDetails", arg1);
			// Return a URI to the newly inserted row on success.
			if (rowID2 > 0) {
				uri = ContentUris.withAppendedId(CONTENT_URI_WD, rowID2);
				getContext().getContentResolver().notifyChange(uri, null);
			}else
			{
				throw new SQLException("Failed to insert row into " + arg0);
			}
			break;

		default:
			break;
		}

		return null;
	}

	@Override
	public Cursor query(Uri arg0, String[] arg1, String arg2, String[] arg3,
			String arg4) {
				return null;
		// TODO Auto-generated method stub
		
	}

	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		return 0;
	}

	

}

package com.zafaralam.weatherexpert.contentprovider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;



public class WeatherExpertAdapter {
	
	private final static String TAG = "WeatherExpertAdapter";
	
	private WeatherExpertDatabaseHelper mDbHelper;
	private SQLiteDatabase db;
	private Context context;
	
	public WeatherExpertAdapter(Context context) {
		// TODO Auto-generated constructor stub
		super();
		this.context = context;
	}
	
	public WeatherExpertAdapter open(){
		mDbHelper = new WeatherExpertDatabaseHelper(context);
		db = mDbHelper.getWritableDatabase();
		//mDbHelper.onUpgrade(db, 1, 2);
		//mDbHelper.onDowngrade(db, 2, 1);
		return this;
	}
	
	public void close() {
		  if (mDbHelper != null) {
		   mDbHelper.close();
		  }
		 }
	
	public long insert(String table,String nullColumnHack,ContentValues values){
		//SQLiteDatabase db = mDbHelper.getWritableDatabase();
		
		long newRowId;
		newRowId = db.insert(table, nullColumnHack, values);
		Log.d(TAG,String.valueOf(newRowId));
		return newRowId;
	}
	
	/*************************************************************
	 * Below query function to be implemented.
	 *************************************************************/
	public Cursor query(String sqlQuery,String[] selectionArgs){
		Cursor cursor = null;
		if(sqlQuery != null)
		{
			cursor = db.rawQuery(sqlQuery, selectionArgs);
			//cursor = db.execSQL(sqlQuery);
		}
		return cursor;
	}
	
	public Cursor query(String table,String[] projection,String selection,String[] selectionArgs,
			String groupBy,String having,String sortOrder){
		
		//SQLiteDatabase db = mDbHelper.getWritableDatabase();
		
		Cursor cursor;
		cursor = db.query(
			    table,  			// The table to query
			    projection,         // The columns to return
			    selection,          // The columns for the WHERE clause
			    selectionArgs,      // The values for the WHERE clause
			    groupBy,            // don't group the rows
			    having,             // don't filter by row groups
			    sortOrder           // The sort order
			    );
		
		if(cursor != null){
			cursor.moveToFirst();
			Log.d(TAG, "Cursor is loaded");
		}
		return cursor;
	}
	
	public long getNoOfRecordsInTable(String tableName){
		return DatabaseUtils.queryNumEntries(db, tableName);
	}
	
	public int delete(String table,String whereClause, String[] whereArgs){
		//SQLiteDatabase db = mDbHelper.getWritableDatabase();
		
		int count;
		count = db.delete(table, whereClause, whereArgs);
		
		return count;
	}
	
	public int update(String table, ContentValues values,String whereClause,String[] whereArgs){
		//SQLiteDatabase db = mDbHelper.getWritableDatabase();
		
		int count;
		count = db.update(table, values, whereClause, whereArgs);
		
		return count;
	}

}

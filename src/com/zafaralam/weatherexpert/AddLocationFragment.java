package com.zafaralam.weatherexpert;

import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.crypto.spec.IvParameterSpec;

import com.zafaralam.modal.WeatherLocation;
import com.zafaralam.utils.NetworkDetails;
import com.zafaralam.utils.ParserType;
import com.zafaralam.weatherexpert.contentprovider.WeatherExpertAdapter;
import com.zafaralam.weatherexpert.contentprovider.WeatherExpertContract.FavouritesEnrty;
import com.zafaralam.weatherexpert.contentprovider.WeatherExpertContract.RecentLocationsEntry;
import com.zafaralam.xmlparser.FeedParser;
import com.zafaralam.xmlparser.FeedParserFactory;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class AddLocationFragment extends Fragment {

	private static final String TAG = "AddLocationFragment";
	private EditText etSearch;
	private ListView lvAvailableLocations;
	private AddLocationAdapter locAdapter;
	private ProgressBar pbSearchProg;
	private List<WeatherLocation> locations;
	private WeatherExpertAdapter dbHelper;
	private ListView lvRecentLocations;
	private AddLocationAdapter recentLocationAdapter;

	public AddLocationFragment() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onDetach() {
		super.onDetach();
		dbHelper.close();
		// hideKeyboard(etSearch);
		Log.d(TAG, "Detaching fragment");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.add_location, container, false);
		init(v);

		dbHelper.open();

		/*
		 * InputMethodManager imm =
		 * (InputMethodManager)getActivity().getSystemService
		 * (Context.INPUT_METHOD_SERVICE);
		 * imm.hideSoftInputFromWindow(etSearch.getWindowToken(), 0);
		 */
		loadRecentLocations();
		lvRecentLocations.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				String[] tag = ((GridLayout) view).getTag().toString().split(";");
				String location_name = tag[0] + ";"+tag[1]+";"+tag[2];
				switchWeatherLocation(location_name);
			}
		});
		
		lvRecentLocations.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent arg1) {
				// TODO Auto-generated method stub
				hideKeyboard(view);
				return false;
			}
		});

		lvAvailableLocations.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent arg1) {
				// TODO Auto-generated method stub
				hideKeyboard(view);
				return false;
			}
		});

		lvAvailableLocations.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

				String[] tag = ((GridLayout) view).getTag().toString().split(";");
				String location_name = tag[0] + ";"+tag[1]+";"+tag[2];
				addRecentLocation(tag);
				switchWeatherLocation(location_name);

			}
		});

		etSearch.addTextChangedListener(new TextWatcher() {

			public void afterTextChanged(Editable s) {
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

				if (s.length() > 2) {
					if (NetworkDetails.isNetworkAvailable(getActivity()
							.getApplicationContext()))
						downloadLocations(s);
					else
						Toast.makeText(getActivity().getApplicationContext(),
								"Network Not Available", Toast.LENGTH_SHORT)
								.show();
					// displayLocations();
					// locAdapter.getFilter().filter(s.toString());
				} else if (s.length() < 3) {
					locAdapter.clear();
				}
			}
		});
		return v;
	}
	
	private void addRecentLocation(String[] tag) {
		String[] projection = {
			RecentLocationsEntry.KEY_RECLOC_ID	
		};
		String areaName = tag[0];
		String lat = tag[1];
		String lng = tag[2];
		String country = tag[3];
		String region = tag[4];
		Cursor c = null;
		long noOfRows = 0;
		ContentValues cv = new ContentValues();
		
		String where = RecentLocationsEntry.KEY_LAT + " = "
				+ lat + " AND " + RecentLocationsEntry.KEY_LNG
				+ " = " + lng;
		
		cv.put(RecentLocationsEntry.KEY_AREANAME, areaName);
		cv.put(RecentLocationsEntry.KEY_COUNTRY, country);
		cv.put(RecentLocationsEntry.KEY_REGION, region);
		cv.put(RecentLocationsEntry.KEY_LAT, lat);
		cv.put(RecentLocationsEntry.KEY_LNG, lng);
		
		String rawQueryGetMinRecId = "SELECT MIN("+RecentLocationsEntry.KEY_RECLOC_ID+") FROM " + RecentLocationsEntry.TABLE_NAME;
		//String rawQStringGetRecordsInRec = "SELECT COUNT(*) FROM" + RecentLocationsEntry.TABLE_NAME;
		
		
		
		c = dbHelper.query(RecentLocationsEntry.TABLE_NAME, projection, where, null, null, null, null);
		
		if(c.getCount() == 0){
			
//			c = dbHelper.query(rawQStringGetRecordsInRec, null);
//			
//			c.moveToFirst();
//			noOfRows = c.getInt(0);
			noOfRows = dbHelper.getNoOfRecordsInTable(RecentLocationsEntry.TABLE_NAME);
			
			//Log.d(TAG,String.valueOf(noOfRows));
			if(noOfRows >= 10)
			{
				//String[] proj = {RecentLocationsEntry.KEY_RECLOC_ID};
				
				String rawQueryForTopRecs = "SELECT " + RecentLocationsEntry.KEY_RECLOC_ID + " FROM " 
				+ RecentLocationsEntry.TABLE_NAME + " LIMIT "+ String.valueOf(noOfRows-9);
				
				c = dbHelper.query(rawQueryForTopRecs, null);
				c.moveToFirst();
				for(int i=0;i<=c.getCount();i++){
					//c.move(i);
					
					int minId = c.getInt(0);
					
					String whereArgs = RecentLocationsEntry.KEY_RECLOC_ID + " = " + String.valueOf(minId);
					int noOfDeletedRows = dbHelper.delete(RecentLocationsEntry.TABLE_NAME, whereArgs, null);
					//Log.d(TAG,"Deleted "+String.valueOf(minId)+" Record");
					if(!c.isLast())
						c.moveToNext();
					else
						i++;
				}
			}
			
			long rowId = dbHelper.insert(RecentLocationsEntry.TABLE_NAME, null, cv);
		}
		
	}

	private void loadRecentLocations() {
		// TODO Auto-generated method stub
		recentLocationAdapter.clear();
		String[] projection = new String[] {RecentLocationsEntry.KEY_RECLOC_ID, RecentLocationsEntry.KEY_AREANAME,
				RecentLocationsEntry.KEY_COUNTRY, RecentLocationsEntry.KEY_REGION,RecentLocationsEntry.KEY_LAT,
				RecentLocationsEntry.KEY_LNG };

		// String where = FavouritesEnrty.KEY_DEFAULT_LOC + " < " + "2";

		Cursor cursor = dbHelper.query(RecentLocationsEntry.TABLE_NAME, projection,
				null, null, null, null, RecentLocationsEntry.KEY_RECLOC_ID + " DESC");
		boolean isBegin = true;
		if (cursor != null) {
			if (cursor.getCount() == 1)
				recentLocationAdapter.add(new LocationItem(cursor.getString(1), cursor.getString(2),cursor.getString(3), cursor
						.getFloat(4), cursor.getFloat(5)));
			else {
				while (cursor.moveToNext()) {
					if (isBegin) {
						cursor.moveToPrevious();
						isBegin = false;
					}
					// Log.e("cc", data[1]);
					// catalogueData.add(data);
					// Log.d(TAG, cursor.getString(1)+" "+cursor.getFloat(2)+
					// " " +
					// cursor.getFloat(3)+ " " +cursor.getInt(0));
					recentLocationAdapter.add(new LocationItem(cursor.getString(1), cursor.getString(2),cursor.getString(3), cursor
							.getFloat(4), cursor.getFloat(5)));
				}
			}
			cursor.close();
			lvRecentLocations.setAdapter(recentLocationAdapter);
		}
	}
	
	

	private void hideKeyboard(View view) {
		// TODO Auto-generated method stub
		InputMethodManager in = (InputMethodManager) getActivity()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		in.hideSoftInputFromWindow(view.getWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);
	}

	private void downloadLocations(CharSequence s) {

		String feedUrl = "http://api.worldweatheronline.com/free/v1/search.ashx?q="
				+ s + "&format=xml&timezone=yes&key=hkk8gqf7n85dhzns5xmhxa5f";

		// DownloadLocations dl = new
		// DownloadLocations(ParserType.XML_PULL,feedUrl,this);
		// dl.execute("Download locations of entered location");
		this.locations = null;
		if (NetworkDetails.isNetworkAvailable(getActivity()
				.getApplicationContext()))
			new DownloadLocations(ParserType.XML_PULL, feedUrl)
					.execute("Start downloading locations");

	}

	private void displayLocations() {
		locAdapter.clear();
		if (this.locations != null) {

			// locAdapter = new AddLocationAdapter(getActivity());
			for (WeatherLocation weatherLocation : this.locations) {
				String areaName, country, region;
				float lat, log;
				areaName = weatherLocation.getAreaName();
				country = weatherLocation.getCountry();
				region = weatherLocation.getRegion();
				lat = weatherLocation.getLatitude();
				log = weatherLocation.getLongitude();

				locAdapter.add(new LocationItem(areaName, country, region, lat,
						log));

				// }
				lvAvailableLocations.setAdapter(locAdapter);
			}

		}
	}

	private void init(View v) {
		// TODO Auto-generated method stub
		etSearch = (EditText) v.findViewById(R.id.etSearch);
		lvAvailableLocations = (ListView) v
				.findViewById(R.id.lvAvailableLocations);
		pbSearchProg = (ProgressBar) v.findViewById(R.id.pbSearchProg);
		locAdapter = new AddLocationAdapter(getActivity());
		dbHelper = new WeatherExpertAdapter(getActivity());
		
		recentLocationAdapter = new AddLocationAdapter(getActivity());
		lvRecentLocations = (ListView) v.findViewById(R.id.lvRecentLocations);

	}

	private class LocationItem {
		public String getAreaName() {
			return areaName;
		}

		public void setAreaName(String areaName) {
			this.areaName = areaName;
		}

		public String getCountry() {
			return country;
		}

		public void setCountry(String country) {
			this.country = country;
		}

		public String getRegion() {
			return region;
		}

		public void setRegion(String region) {
			this.region = region;
		}

		private String areaName;
		private String country;
		private String region;
		private float latitude;
		private float longitude;

		public LocationItem(String areaName, String country, String region,
				float latitude, float longitude) {
			super();
			this.areaName = areaName;
			this.country = country;
			this.region = region;
			this.latitude = latitude;
			this.longitude = longitude;
		}
	}

	private class AddLocationAdapter extends ArrayAdapter<LocationItem> {

		public AddLocationAdapter(Context context) {
			super(context, 0);
			// TODO Auto-generated constructor stub
		}

		public AddLocationAdapter(Context context, int textViewResourceId) {
			super(context, textViewResourceId);
			// TODO Auto-generated constructor stub
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(
						R.layout.add_location_list_row, null);
			}

			TextView tvAreaName, tvCountry, tvRegion;
			ImageView ibAddToFavs;
			GridLayout glAddLoc;

			tvAreaName = (TextView) convertView.findViewById(R.id.tvAreaName);
			tvCountry = (TextView) convertView.findViewById(R.id.tvCountry);
			tvRegion = (TextView) convertView.findViewById(R.id.tvRegion);
			ibAddToFavs = (ImageView) convertView
					.findViewById(R.id.ibAddToFavs);
			glAddLoc = (GridLayout) convertView.findViewById(R.id.glAddLoc);

			tvAreaName.setText(getItem(position).areaName);
			tvCountry.setText(getItem(position).country);
			tvRegion.setText(getItem(position).region);

			String tag = getItem(position).areaName + ";"
					+ getItem(position).latitude + ";"
					+ getItem(position).longitude;
			String tag2 = getItem(position).areaName + ";"
					+ getItem(position).latitude + ";"
					+ getItem(position).longitude + ";"
					+ getItem(position).country + ";"
					+ getItem(position).areaName;
			ibAddToFavs.setTag(tag);

			glAddLoc.setTag(tag2);

			/*
			 * glAddLoc.setOnClickListener(new OnClickListener() {
			 * 
			 * @Override public void onClick(View view) { // TODO Auto-generated
			 * method stub String location_name = ((GridLayout)
			 * view).getTag().toString(); switchWeatherLocation(location_name);
			 * 
			 * } });
			 */

			ibAddToFavs.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					// TODO Auto-generated method stub

					Cursor c = null;
					// Check if this location is in the database.
					String tagData = v.getTag().toString();
					// Log.d(TAG, tagData);
					String[] splitData = tagData.split(";");
					// Log.d(TAG, splitData[0] + " " + splitData[1] + " "
					// +splitData[2]);

					String[] projection = { FavouritesEnrty.KEY_FAV_ID };
					String where = FavouritesEnrty.KEY_LAT + " = "
							+ splitData[1] + " AND " + FavouritesEnrty.KEY_LNG
							+ " = " + splitData[2];

					c = dbHelper.query(FavouritesEnrty.TABLE_NAME, projection,
							where, null, null, null, null);
					Log.d(TAG, String.valueOf(c.getCount()));
					if (c.getCount() == 0) {
						ContentValues cv = new ContentValues();

						cv.put(FavouritesEnrty.KEY_CITYNAME, splitData[0]);
						cv.put(FavouritesEnrty.KEY_LAT, splitData[1]);
						cv.put(FavouritesEnrty.KEY_LNG, splitData[2]);
						cv.put(FavouritesEnrty.KEY_POSTCODE, "");
						cv.put(FavouritesEnrty.KEY_IPADDRESS, "");
						cv.put(FavouritesEnrty.KEY_DEFAULT_LOC, "0");

						long rowNum = 0;

						rowNum = dbHelper.insert(FavouritesEnrty.TABLE_NAME,
								null, cv);

						if (rowNum > 0)
							Toast.makeText(getActivity(),
									splitData[0] + " added to favourites",
									Toast.LENGTH_SHORT).show();

					} else {
						Toast.makeText(getActivity(),
								"Already added as Favourite.",
								Toast.LENGTH_SHORT).show();
					}

				}
			});

			return convertView;
		}

	}

	private void switchWeatherLocation(String location) {
		if (getActivity() == null) {
			return;
		}

		if (getActivity() instanceof BaseActivity) {
			BaseActivity ba = (BaseActivity) getActivity();
			ba.switchLocation(location);
		}
	}

	private class DownloadLocations extends AsyncTask<String, Integer, String> {

		private static final String TAG = "Download Locations";
		private ParserType type;
		// private List<WeatherLocation> locations;
		private String feedUrl;
		private ProgressDialog dialog;

		// private AddLocationAdapter locAdapter;
		// private ListView lvAvailableLocations;

		public DownloadLocations(ParserType type, String feedUrl) {
			super();
			this.type = type;
			// this.locations = null;
			this.feedUrl = feedUrl;
			dialog = new ProgressDialog(getActivity());
			// locAdapter = new AddLocationAdapter(getActivity());
			// lvAvailableLocations =
			// (ListView)getActivity().findViewById(R.id.lvAvailableLocations);
		}

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub

			try {
				FeedParser parser = FeedParserFactory.getParser(type, feedUrl);
				long start = System.currentTimeMillis();
				locations = parser.parseLocations();// new code
				// displayLocations();
				long duration = System.currentTimeMillis() - start;

				Log.i(TAG, "Parser duration=" + duration);

			} catch (Throwable t) {
				Log.e(TAG, t.getMessage(), t);
			}

			return "Locations loaded";
		}

		@Override
		protected void onPostExecute(String result) {

			// Feed has been Downloaded and Parsed, Display Data to User
			// displayStuff(weathers);
			// this.locations = locations;
			// if(pbSearchProg.isActivated())
			pbSearchProg.setVisibility(View.GONE);
			pbSearchProg.setActivated(false);
			displayLocations();
			// if (dialog.isShowing()) {
			// dialog.dismiss();
			// }

		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			// this.dialog.setMessage("Locating...");
			// this.dialog.show();
			pbSearchProg.setVisibility(View.VISIBLE);
			pbSearchProg.setActivated(true);
		}

	}
	
	
	/**************************************************
	 * Below classes are for the list of recent searches
	 **************************************************/
	 

}

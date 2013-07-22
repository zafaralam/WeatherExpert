package com.zafaralam.weatherexpert;

import java.util.ArrayList;

import com.zafaralam.weatherexpert.contentprovider.WeatherExpertAdapter;
import com.zafaralam.weatherexpert.contentprovider.WeatherExpertContract.FavouritesEnrty;
import android.content.Context;
//import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class FavouritesFragment extends Fragment {

	private static final String TAG = "FavouritesFragment";

	// ListFragment lFragment;

	private WeatherExpertAdapter dbHelper;
	private ListView lvFavList;
	private FavouritesListAdapter favAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.d(TAG, "Inside OnCreate");

	}

	@Override
	public void onDetach() {
		super.onDetach();
		dbHelper.close();
		Log.d(TAG, "Detaching fragment");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.d(TAG, "Inside on CreateView");
		View v = null;
		try {
			v = inflater.inflate(R.layout.favourites_list, container, false);
			init(v);

			dbHelper.open();
			// lFragment = new FavouriteListFragment();
			// FragmentTransaction tran =
			// getChildFragmentManager().beginTransaction();
			// //tran.remove(R.id.favListFragment);
			// tran.replace(R.id.favListFragment, lFragment).commit();

			// insertTempFavourites();
			displayFavouritesList();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return v;
	}

	private void init(View v) {
		// TODO Auto-generated method stub
		lvFavList = (ListView) v.findViewById(R.id.listView1);
		dbHelper = new WeatherExpertAdapter(getActivity());
		favAdapter = new FavouritesListAdapter(getActivity());

	}

	private void displayFavouritesList() {
		// TODO Auto-generated method stub

		String[] projection = new String[] { FavouritesEnrty.KEY_FAV_ID,
				FavouritesEnrty.KEY_CITYNAME, FavouritesEnrty.KEY_LAT,
				FavouritesEnrty.KEY_LNG };

		// String where = FavouritesEnrty.KEY_DEFAULT_LOC + " < " + "2";

		Cursor cursor = dbHelper.query(FavouritesEnrty.TABLE_NAME, projection,
				null, null, null, null, null);
		boolean isBegin = true;
		if (cursor != null) {
			if (cursor.getCount() == 1)
				favAdapter.add(new FavouritesItem(cursor.getString(1), cursor
						.getFloat(2), cursor.getFloat(3), cursor.getInt(0)));
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
					favAdapter.add(new FavouritesItem(cursor.getString(1),
							cursor.getFloat(2), cursor.getFloat(3), cursor
									.getInt(0)));
				}
			}
			cursor.close();

			lvFavList.setAdapter(favAdapter);

			lvFavList.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> listView, View view,
						int position, long id) {

					String location_name = ((GridLayout) view).getTag()
							.toString();
					// String[] splitData = location_name.split(";");
					switchWeatherLocation(location_name);
				}
			});
			
		}
		Log.d(TAG, "Favourites List has been populated");
	}

	public FavouritesFragment() {
		super();
		// TODO Auto-generated constructor stub

		Log.d(TAG, "Inside the constructor");
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

	/*
	 * 
	 * private void insertTempFavourites(){
	 * 
	 * //getActivity().getApplicationContext().deleteDatabase("weatherexpert.db")
	 * ; //dbHelper.delete(FavouritesEnrty.TABLE_NAME,null,null); String[]
	 * temp_favs = getResources().getStringArray(R.array.temp_favs);
	 * 
	 * for(int i=0;i<temp_favs.length;i++){ ContentValues cv = new
	 * ContentValues();
	 * 
	 * cv.put(FavouritesEnrty.KEY_CITYNAME, temp_favs[i]);
	 * cv.put(FavouritesEnrty.KEY_LAT, ""); cv.put(FavouritesEnrty.KEY_LNG, "");
	 * cv.put(FavouritesEnrty.KEY_POSTCODE, "");
	 * cv.put(FavouritesEnrty.KEY_IPADDRESS, ""); if(i == 0)
	 * cv.put(FavouritesEnrty.KEY_DEFAULT_LOC, "1"); else
	 * cv.put(FavouritesEnrty.KEY_DEFAULT_LOC, "0");
	 * 
	 * 
	 * dbHelper.insert(FavouritesEnrty.TABLE_NAME, null, cv);
	 * 
	 * } }
	 */

	/*
	 * Internal classes to manage menu items
	 */

	private class FavouritesItem {
		private String location_name;
		private float lat;
		private float lng;
		private int id;

		public FavouritesItem(String location_name, float lat, float lng, int id) {
			super();
			this.location_name = location_name;
			this.lat = lat;
			this.lng = lng;
			this.id = id;
		}

		public String getLocation_name() {
			return location_name;
		}

		public void setLocation_name(String location_name) {
			this.location_name = location_name;
		}

		public float getLat() {
			return lat;
		}

		public void setLat(float lat) {
			this.lat = lat;
		}

		public float getLng() {
			return lng;
		}

		public void setLng(float lng) {
			this.lng = lng;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

	}

	private class FavouritesListAdapter extends ArrayAdapter<FavouritesItem> {

		public FavouritesListAdapter(Context context, int textViewResourceId) {
			super(context, textViewResourceId);
			// TODO Auto-generated constructor stub
		}

		public FavouritesListAdapter(Context context) {
			super(context, 0);
			// TODO Auto-generated constructor stub
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub

			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(
						R.layout.favourites_item, null);
			}

			TextView tvFavId, tvLocation;
			ImageView ibDelete;

			/*
			 * New section
			 */

			GridLayout glFavItem;

			glFavItem = (GridLayout) convertView.findViewById(R.id.glFavItem);

			tvFavId = (TextView) convertView.findViewById(R.id.fav_id);
			tvLocation = (TextView) convertView
					.findViewById(R.id.location_name);
			ibDelete = (ImageView) convertView.findViewById(R.id.ibDelete);

			tvFavId.setText(String.valueOf(getItem(position).id));

			tvLocation.setText(getItem(position).location_name);
			tvLocation.setTag(getItem(position).location_name + ";"
					+ getItem(position).lat + ";" + getItem(position).lng);

			String tag = String.valueOf(getItem(position).id) + ";"
					+ getItem(position).lat + ";" + getItem(position).lng;
			ibDelete.setTag(tag);

			/* New Section */
			glFavItem.setTag(getItem(position).location_name + ";"
					+ getItem(position).lat + ";" + getItem(position).lng);

						
			ibDelete.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String tagData = v.getTag().toString();
					String[] splitData = tagData.split(";");
					String where = FavouritesEnrty.KEY_FAV_ID + " = "
							+ splitData[0];

					dbHelper.delete(FavouritesEnrty.TABLE_NAME, where, null);

					// need to reload the data;
					favAdapter.clear();
					displayFavouritesList();
				}
			});

			/*
			 * tvLocation.setOnClickListener(new OnClickListener() {
			 * 
			 * @Override public void onClick(View v) { // TODO Auto-generated
			 * method stub String location_name = v.getTag().toString();
			 * //String[] splitData = location_name.split(";");
			 * switchWeatherLocation(location_name); } });
			 */

			return convertView;
		}

	}
	
	

}

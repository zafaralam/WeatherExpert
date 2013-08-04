package com.zafaralam.weatherexpert;

import java.util.ArrayList;

import com.zafaralam.utils.WeatherExpertIcons;
import com.zafaralam.utils.WeatherExpertIcons.IconDesc;
import com.zafaralam.weatherexpert.contentprovider.WeatherExpertAdapter;
import com.zafaralam.weatherexpert.contentprovider.WeatherExpertContract.FavouritesEnrty;
import com.zafaralam.weatherexpert.contentprovider.WeatherExpertContract.WeatherDetailsEntry;

import android.content.Context;
//import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Typeface;
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
			if (cursor.getCount() == 1){
				//Get the weather data for current.
				loadWDForFav(cursor.getString(1), cursor
						.getFloat(2), cursor.getFloat(3), cursor.getInt(0));
				
			}else {
				while (cursor.moveToNext()) {
					if (isBegin) {
						cursor.moveToPrevious();
						isBegin = false;
					}
					//Get the weather data for current.
					loadWDForFav(cursor.getString(1), cursor
							.getFloat(2), cursor.getFloat(3), cursor.getInt(0));
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

	private void loadWDForFav(String location,float lat, float lng, int favId) {
		// TODO Auto-generated method stub
		String[] projection = {WeatherDetailsEntry.KEY_LOCATION
				,WeatherDetailsEntry.KEY_TEMP_C
				,WeatherDetailsEntry.KEY_WEATHERDESC
				,WeatherDetailsEntry.KEY_WEATHERICONURL
				,WeatherDetailsEntry.KEY_WEATHER_CONDITION
		};
		//cursor.move(pos);
		String where = WeatherDetailsEntry.KEY_WD_FAV_ID + " = " + favId;
		Cursor cursor1 = dbHelper.query(WeatherDetailsEntry.TABLE_NAME
				, projection, where, null, null, null, WeatherDetailsEntry.KEY_WD_ID);
		WeatherExpertIcons wei = new WeatherExpertIcons();
		
		if(cursor1.getCount() > 0 && cursor1 != null){
			boolean isBegin = true;
			if(cursor1.getCount() == 1){
				//write code here
				if(cursor1.getString(0) != null){
					//get data
					IconDesc ids = wei.getWeatherIcon(cursor1.getString(3),
							cursor1.getInt(4), getActivity());
					
					favAdapter.add(new FavouritesItem(location, lat, lng, favId
							,cursor1.getString(1)+"\u2103, "+ids.getDesc(),ids.getStrIcon(),ids.getIconCharList(),ids.getIconCharColorList()));
				}
				
			}else{
				while(cursor1.moveToNext()){
					if(isBegin){
						isBegin = false;
						cursor1.moveToPrevious();
					}
					//write code here
					if(cursor1.getString(0) != null){
						//get data
						IconDesc ids = wei.getWeatherIcon(cursor1.getString(3),
								cursor1.getInt(4), getActivity());
						favAdapter.add(new FavouritesItem(location, lat, lng, favId
								,cursor1.getString(1)+"\u2103, "+ids.getDesc(),ids.getStrIcon(),ids.getIconCharList(),ids.getIconCharColorList()));
					}
				}
			}
		}else{
			favAdapter.add(new FavouritesItem(location,
					lat, lng, favId));
		}
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
	 * Internal classes to manage menu items
	 */

	private class FavouritesItem {
		

		private String location_name;
		private float lat;
		private float lng;
		private int id;
		private String weatherDesc;
		private String weatherIcon;//this will use the metacons-webfont.
		private String[] iconCharList;
		private int[] iconColorList;

		public FavouritesItem(String location_name, float lat, float lng, int id) {
			super();
			this.location_name = location_name;
			this.lat = lat;
			this.lng = lng;
			this.id = id;
		}
		
		public FavouritesItem(String location_name, float lat, float lng, int id,
				String weatherDesc, String weatherIcon) {
			super();
			this.location_name = location_name;
			this.lat = lat;
			this.lng = lng;
			this.id = id;
			this.weatherDesc = weatherDesc;
			this.weatherIcon = weatherIcon;
		}
		
		public FavouritesItem(String location_name, float lat, float lng,
				int id, String weatherDesc, String weatherIcon,
				String[] iconCharList, int[] iconColorList) {
			super();
			this.location_name = location_name;
			this.lat = lat;
			this.lng = lng;
			this.id = id;
			this.weatherDesc = weatherDesc;
			this.weatherIcon = weatherIcon;
			this.setIconCharList(iconCharList);
			this.setIconColorList(iconColorList);
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

		public String[] getIconCharList() {
			return iconCharList;
		}

		public void setIconCharList(String[] iconCharList) {
			this.iconCharList = iconCharList;
		}

		public int[] getIconColorList() {
			return iconColorList;
		}

		public void setIconColorList(int[] iconColorList) {
			this.iconColorList = iconColorList;
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

			TextView tvFavId, tvLocation, tvFavWeatherDeatails;
			ImageView ibDelete;
			TextView vIconView;
			TextView vIconView1;
			TextView vIconView2;
			
			vIconView = (TextView) convertView.findViewById(R.id.vIconView);
			vIconView1 = (TextView) convertView.findViewById(R.id.vIconView1);
			vIconView2 = (TextView) convertView.findViewById(R.id.vIconView2);
			
			

			/*
			 * New section
			 */

			GridLayout glFavItem;
			
			tvFavWeatherDeatails = (TextView) convertView.findViewById(R.id.tvFavWeatherDeatails);

			glFavItem = (GridLayout) convertView.findViewById(R.id.glFavItem);

			tvFavId = (TextView) convertView.findViewById(R.id.fav_id);
			tvLocation = (TextView) convertView
					.findViewById(R.id.location_name);
			ibDelete = (ImageView) convertView.findViewById(R.id.ibDelete);
			
			Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "iconvault_forecastfont.ttf");
			
			vIconView.setTypeface(font);
			vIconView1.setTypeface(font);
			vIconView2.setTypeface(font);
			int[] iconColorList = getItem(position).getIconColorList();
			String[] iconCharList = getItem(position).getIconCharList();
			
			vIconView.setText(iconCharList[0]);
			vIconView1.setText(iconCharList[1]);
			vIconView2.setText(iconCharList[2]);
			
			vIconView.setTextColor(iconColorList[0]);
			vIconView1.setTextColor(iconColorList[1]);
			vIconView2.setTextColor(iconColorList[2]);

			tvFavId.setTag(String.valueOf(getItem(position).id));
			//tvFavId.setText(getItem(position).weatherIcon);
			//tvFavId.setTypeface(font);

			tvLocation.setText(getItem(position).location_name);
			tvLocation.setTag(getItem(position).location_name + ";"
					+ getItem(position).lat + ";" + getItem(position).lng);

			String tag = String.valueOf(getItem(position).id) + ";"
					+ getItem(position).lat + ";" + getItem(position).lng;
			ibDelete.setTag(tag);

			/* New Section */
			glFavItem.setTag(getItem(position).location_name + ";"
					+ getItem(position).lat + ";" + getItem(position).lng + ";" + getItem(position).id);

			tvFavWeatherDeatails.setText(getItem(position).weatherDesc);			
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

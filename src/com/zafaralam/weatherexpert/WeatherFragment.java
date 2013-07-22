/*
 * Task to be completed for the WeatherFragment
 * 
 *  1)Add paging for the different favorites saved by the user
 *  	-Creating a WeatherFramentAdapter Class.
 *  	-The WeatherFramentAdapter class with then create each page of the weather fragment.
 *  2)Add pop up for the different days.
 *  3)Make app responsive when looking for location.
 *  4)If location is not avaiable then search for a devices ip address to get the location.
 *  
 */
package com.zafaralam.weatherexpert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.zafaralam.modal.WeatherDetails;
import com.zafaralam.utils.DaysOfTheWeek;
import com.zafaralam.utils.NetworkDetails;
import com.zafaralam.utils.ParserType;
import com.zafaralam.utils.WeatherExpertIcons;
import com.zafaralam.utils.WeatherTypes;
import com.zafaralam.utils.WeatherExpertIcons.IconDesc;
import com.zafaralam.weatherexpert.contentprovider.WeatherExpertAdapter;
import com.zafaralam.weatherexpert.contentprovider.WeatherExpertContract.FavouritesEnrty;
import com.zafaralam.xmlparser.FeedParser;
import com.zafaralam.xmlparser.FeedParserFactory;

//import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

@SuppressLint("ValidFragment")
public class WeatherFragment extends Fragment implements OnClickListener {

	/* Current weather condition GUI variables */
	private final String TAG = "Weather Fragment";
	private TextView tvLocation;
	private TextView tvCurrentTime;
	private TextView tvCurrentTemp;
	private TextView tvTodayMaxTemp;
	private TextView tvTodayMinTemp;
	private ImageView ivCurrentWeatherIcon;
	private LinearLayout llFiveDayWeather;
	private WeatherExpertAdapter dbHelper;

	/* Grid item GUI variables for detailed weather */
	private TextView tvCurrentWeatherConditionDesc;
	private TextView tvWindSpeed;
	private TextView tvHumidity;
	private TextView tvVisibility;
	private TextView tvWindDirection;
	private TextView tvPressure;
	private TextView tvCloudCover;
	private TextView tvPrecipitation;

	/* Multi day weather GUI variables */

	// Location Variables
	private LocationManager locationManager;
	private String main_location;

	/* Popup window variables */
	private PopupWindow pwindo;

	public String getMain_location() {
		return main_location;
	}

	public void setMain_location(String main_location) {
		this.main_location = main_location;
	}

	private Location l;
	private Criteria criteria;
	private String provider;

	/* API variables */
	private String feedUrl;

	/* Local variables */
	private List<WeatherDetails> weathers;
	private Calendar calander;

	// private ProgressDialog dialog;
	private ProgressBar pbWDload;

	public WeatherFragment(String _location) {
		super();
		// TODO Auto-generated constructor stub
		main_location = _location;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.weather_details, container, false);
		init(v);

		// Open connection to database helper
		dbHelper.open();

		loadFeed(ParserType.XML_PULL);
		// tvCurrentTemp.setText("100");
		return v;
	}

	private void init(View v) {
		tvCurrentTime = (TextView) v.findViewById(R.id.tvCurrentTime);
		tvLocation = (TextView) v.findViewById(R.id.tvLocation);
		tvCurrentTemp = (TextView) v.findViewById(R.id.tvCurrentTemp);
		tvTodayMaxTemp = (TextView) v.findViewById(R.id.tvToadayMaxTemp);
		tvTodayMinTemp = (TextView) v.findViewById(R.id.tvToadayMinTemp);
		ivCurrentWeatherIcon = (ImageView) v
				.findViewById(R.id.ivCurrentWeatherIcon);
		llFiveDayWeather = (LinearLayout) v.findViewById(R.id.llFiveDayWeather);
		tvCurrentWeatherConditionDesc = (TextView) v
				.findViewById(R.id.tvCurrentWeatherConditionDesc);
		tvWindSpeed = (TextView) v.findViewById(R.id.tvWindSpeed);
		tvHumidity = (TextView) v.findViewById(R.id.tvHumidity);
		tvVisibility = (TextView) v.findViewById(R.id.tvVisibility);
		tvWindDirection = (TextView) v.findViewById(R.id.tvWindDirection);
		tvPressure = (TextView) v.findViewById(R.id.tvPressure);
		tvCloudCover = (TextView) v.findViewById(R.id.tvCloudCover);
		tvPrecipitation = (TextView) v.findViewById(R.id.tvPrecipitation);

		pbWDload = (ProgressBar) v.findViewById(R.id.pbWDload);

		// dialog = new ProgressDialog(getActivity());
		calander = new GregorianCalendar();

		// Database helper for querying
		dbHelper = new WeatherExpertAdapter(getActivity());

	}

	private void loadFeed(ParserType type) {

		if (NetworkDetails.isNetworkAvailable(getActivity()
				.getApplicationContext())) {
			// System.out.println("1");

			this.weathers = null;

			if (main_location.compareToIgnoreCase("Current") == 0) {

				boolean isLocationAvil = false;
				isLocationAvil = loadLocation();

				if (!isLocationAvil) {
					//getIpaddress
					String ip = NetworkDetails.getLocalIpAddress(getActivity());
					if(ip != null){
						
						/*
						 * Perform address retrieval and set location name
						 */
						feedUrl = "http://api.worldweatheronline.com/free/v1/weather.ashx?q="
								+ ip// IP Address
								+ "&format=xml&num_of_days=5&key=hkk8gqf7n85dhzns5xmhxa5f";
						Log.d(TAG, "IP Adress: " + ip);
						
					}else{
						// perform alternate action
						String[] projection = new String[] {
								FavouritesEnrty.KEY_FAV_ID,
								FavouritesEnrty.KEY_CITYNAME,
								FavouritesEnrty.KEY_LAT, FavouritesEnrty.KEY_LNG };
						Cursor cursor = dbHelper.query(FavouritesEnrty.TABLE_NAME,
								projection, null, null, null, null, null);
						if (cursor != null) {
							if (cursor.getCount() > 0) {
								// Display favorites
								displayAlertnativeFragment("Favorites");
							} else {
								// Display Search location
								displayAlertnativeFragment("Search Location");
							}
						}
					}

					

				}

			} else {
				// if(main_location.compareTo("Current")!=0)
				String[] splitData = main_location.split(";");
				// feedUrl =
				// "http://api.worldweatheronline.com/free/v1/weather.ashx?q="+main_location+"&format=xml&num_of_days=5&key=hkk8gqf7n85dhzns5xmhxa5f";
				feedUrl = "http://api.worldweatheronline.com/free/v1/weather.ashx?q="
						+ splitData[1]// Latitude
						+ ","
						+ splitData[2]// Longitude
						+ "&format=xml&num_of_days=5&key=hkk8gqf7n85dhzns5xmhxa5f";
				tvLocation.setText(splitData[0]);
			}

			if (feedUrl != null)
				new DownloadWeather(type, feedUrl)
						.execute("Download weather details for location");

		} else {
			Toast.makeText(getActivity().getApplicationContext(),
					"Network Not Available", Toast.LENGTH_SHORT).show();
		}

	}

	/*
	 * @Override public void onResume() { // TODO Auto-generated method stub
	 * super.onResume(); try { if(weathers!=null) displayStuff(); } catch
	 * (ParseException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } catch (Exception e) { // TODO: handle exception
	 * e.printStackTrace(); } }
	 */

	public boolean loadLocation() {
		/*
		 * Get location stuff
		 */
		try {

			String svcName = Context.LOCATION_SERVICE;

			Log.d(TAG, "Inside Load location");

			locationManager = (LocationManager) getActivity().getSystemService(
					svcName);

			if (locationManager == null)
				Log.d(TAG, "Location Manager is null");
			criteria = new Criteria();
			criteria.setAccuracy(Criteria.ACCURACY_FINE);
			criteria.setPowerRequirement(Criteria.POWER_LOW);
			criteria.setAltitudeRequired(false);
			criteria.setBearingRequired(false);
			criteria.setSpeedRequired(false);
			criteria.setCostAllowed(true);
			provider = locationManager.getBestProvider(criteria, true);

			if (locationManager.isProviderEnabled(provider)) {
				Log.d(TAG, "Provider available");

				l = locationManager.getLastKnownLocation(provider);

				updateWithNewLocation(l);

				if (l != null) {
					locationManager.requestLocationUpdates(provider, 2000, 10,
							locationListener);

					Log.d(TAG,
							"Done with loading the location" + l.getLatitude()
									+ " " + l.getLongitude());
					return true;
				} else
					return false;
			}
			return false;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/*
	 * Find the current location of the user.
	 */

	private void updateWithNewLocation(Location location) {

		String latLongString = "No location found";
		String addressString = "No address found";

		Log.d(TAG, "Inside 'updateWithNewLocation(Location location)'");

		if (location != null) {
			Log.d(TAG, "location IS not NULL");
			if (!pbWDload.isActivated()) {
				pbWDload.setVisibility(View.VISIBLE);
				pbWDload.setActivated(true);
			}
			// dialog.setMessage("Aquiring Current location...");
			// dialog.show();
			// Log.d(TAG, "location IS not NULL");
			double lat = location.getLatitude();
			double lng = location.getLongitude();
			latLongString = "Lat:" + lat + "\nLong:" + lng;
			feedUrl = "http://api.worldweatheronline.com/free/v1/weather.ashx?q="
					+ lat
					+ ","
					+ lng
					+ "&format=xml&num_of_days=5&key=hkk8gqf7n85dhzns5xmhxa5f";
			// feedUrl =
			// "http://free.worldweatheronline.com/feed/weather.ashx?q="+lat+","+lng+"&format=xml&num_of_days=5&key=8cb1d7878b154559122412";
			// feedUrl =
			// "http://api.worldweatheronline.com/free/v1/weather.ashx?q=London&format=xml&num_of_days=5&key=hkk8gqf7n85dhzns5xmhxa5f";
			double latitude = location.getLatitude();
			double longitude = location.getLongitude();
			Geocoder gc;
			// System.out.println(latLongString);
			try {
				gc = new Geocoder(getActivity().getApplicationContext(),
						Locale.getDefault());

				List<Address> addresses = gc.getFromLocation(latitude,
						longitude, 1);
				StringBuilder sb = new StringBuilder();
				if (addresses.size() > 0) {
					Address address = addresses.get(0);
					// for (int i = 0; i < address.getMaxAddressLineIndex();
					// i++)
					// sb.append(address.getAddressLine(i)).append("\n");
					sb.append(address.getLocality()).append(" ");
					// sb.append(address.getPostalCode()).append(" ");
					sb.append("(" + address.getCountryName() + ")");
				}
				addressString = sb.toString();
				// System.out.println(addressString);
				// tvLocation.setText("Your Current Position is:\n" +
				// latLongString + "\n\n" + addressString);

				tvLocation.setText(addressString);
				// dialog.dismiss();

				if (pbWDload.isActivated()) {
					pbWDload.setVisibility(View.INVISIBLE);
					pbWDload.setActivated(false);
				}

			} catch (NullPointerException e) {
				// TODO: handle exception
				e.printStackTrace();
			} catch (Exception e) {

				e.printStackTrace();
			}

		} else {
			Log.d(TAG, "location IS NULL");
			feedUrl = null;
		}

	}

	private void displayAlertnativeFragment(String menuItem) {
		// TODO Auto-generated method stub
		if (getActivity() == null) {
			return;
		}

		if (getActivity() instanceof BaseActivity) {
			BaseActivity ba = (BaseActivity) getActivity();
			ba.switchContent(menuItem);
		}

	}

	public void displayStuff() throws ParseException {
		WeatherExpertIcons wei = new WeatherExpertIcons();
		// Log.d(TAG, "Inside the display");
		llFiveDayWeather.removeAllViewsInLayout();
		// tvCurrentWeather.setText(weathers.get(0).getTemp_C().toString());

		for (WeatherDetails wd : this.weathers) {
			Log.d(TAG, wd.getDate()+" "+String.valueOf(wd.getWeather_condition()));
			Log.d(TAG, wd.getWeatherIconUrl());
			if (wd.getWeatherType() == WeatherTypes.CURRENT_WEATHER.ordinal()) {
				String curr_temp = String.valueOf(wd.getTemp_C());

				if (curr_temp.length() == 1)
					curr_temp = " " + curr_temp;
				tvCurrentTemp.setText(curr_temp + "\u2103");

				String TIME_FORMAT = "hh:mm aa";
				SimpleDateFormat TimeFormat = new SimpleDateFormat(TIME_FORMAT);

				Calendar ATime = Calendar.getInstance();
				String Timein12hourFormat = TimeFormat.format(ATime.getTime());

				Time today = new Time(Time.getCurrentTimezone());
				today.setToNow();
				tvCurrentTime.setText(Timein12hourFormat);
				// tvCurrentTime.setText(wd.getObservation_time());

				tvCurrentWeatherConditionDesc.setText(wd.getWeatherDesc());
				tvCloudCover.setText(wd.getCloudCover() + " %");
				tvHumidity.setText(wd.getHumidity() + " %");
				tvWindSpeed.setText(wd.getWindSpeedKmph() + " kmph");
				tvWindDirection.setText(wd.getWindDir16Point().toString());
				tvVisibility.setText(wd.getVisibility() + " km");
				tvPressure.setText(wd.getPressure() + " mbar");
				tvPrecipitation.setText(String.valueOf(wd.getPrecipMM())
						+ " mm");

				IconDesc ids = wei.getWeatherIcon(wd.getWeatherIconUrl(),
						wd.getWeather_condition(), getActivity());

				if (ids != null) {
					ivCurrentWeatherIcon.setImageResource(ids.getIcon());
					tvCurrentWeatherConditionDesc.setText(ids.getDesc());
				}

				/*
				 * if(wd.getWeather_condition() == 116){
				 * UrlImageViewHelper.setUrlDrawable(ivCurrentWeatherImage,
				 * "Cloudy", getResources().getDrawable(R.drawable.cloudy)); }
				 * else{
				 * UrlImageViewHelper.setUrlDrawable(ivCurrentWeatherImage,
				 * wd.getWeatherIconUrl()); }
				 */

				// Adding fade In animation
				// llCurrentWeatherImage.setAlpha(0);
				// float dest = 1;
				//
				// ObjectAnimator animation3 =
				// ObjectAnimator.ofFloat(llCurrentWeatherImage,
				// "alpha", dest);
				// animation3.setDuration(3000);
				// animation3.start();
			} else {

				String max_temp, min_temp;

				max_temp = String.valueOf(wd.getTempMax_C());
				min_temp = String.valueOf(wd.getTempMin_C());
				if (max_temp.length() == 1)
					max_temp = " " + max_temp;
				if (min_temp.length() == 1)
					min_temp = " " + min_temp;
				Date today = new Date();

				calander.setTime(wd.getDate());
				// Log.d(TAG,String.valueOf(today.compareTo(wd.getDate())));
				if (today.compareTo(wd.getDate()) == 1) {
					tvTodayMaxTemp.setText("" + max_temp + "\u2103");
					tvTodayMinTemp.setText("" + min_temp + "\u2103");
				} else {

					LayoutInflater factory = LayoutInflater.from(getActivity()
							.getApplicationContext());
					View weatherItem = factory.inflate(R.layout.weather_item,
							null);

					ImageView ivWeatherPic = (ImageView) weatherItem
							.findViewById(R.id.ivWeatherPic);

					IconDesc ids = wei.getWeatherIcon(wd.getWeatherIconUrl(),
							wd.getWeather_condition(), getActivity());

					if (ids != null)
						ivWeatherPic.setImageResource(ids.getIcon());

					TextView tvDate = (TextView) weatherItem
							.findViewById(R.id.tvDate);

					// Log.d(TAG,String.valueOf(
					// calander.get(Calendar.DAY_OF_WEEK)));
					switch (calander.get(Calendar.DAY_OF_WEEK)) {
					case DaysOfTheWeek.SUNDAY_INT:
						tvDate.setText("SUN");
						break;
					case DaysOfTheWeek.MONDAY_INT:
						tvDate.setText("MON");
						break;
					case DaysOfTheWeek.TUESDAY_INT:
						tvDate.setText("TUE");
						break;
					case DaysOfTheWeek.WEDNESDAY_INT:
						tvDate.setText("WED");
						break;
					case DaysOfTheWeek.THURSDAY_INT:
						tvDate.setText("THU");
						break;
					case DaysOfTheWeek.FRIDAY_INT:
						tvDate.setText("FRI");
						break;
					case DaysOfTheWeek.SATUERDAY_INT:
						tvDate.setText("SAT");
						break;
					default:
						break;
					}

					TextView tvmax = (TextView) weatherItem
							.findViewById(R.id.tvMaxTemp);
					tvmax.setText(max_temp + "\u2103");
					TextView tvmin = (TextView) weatherItem
							.findViewById(R.id.tvMinTemp);
					tvmin.setText(min_temp + "\u2103");
					// TextView tvWeatherDesc = (TextView) weatherItem
					// .findViewById(R.id.tvWeatherDesc);
					// tvWeatherDesc.setText(wd.getWeatherDesc());
					weatherItem.setId(calander.get(Calendar.DAY_OF_WEEK));
					weatherItem.setOnClickListener(this); // setting the onclick
															// listener for each
															// item.
					llFiveDayWeather.addView(weatherItem);
					weatherItem.destroyDrawingCache();
				}
			}

		}
	}

	@SuppressLint("NewApi")
	public void onClick(View v) {

		switch (v.getId()) {
		// case R.id.llpopup:
		// llpopup.removeAllViews();
		// break;
		case DaysOfTheWeek.SUNDAY_INT:
			// initiatePopupWindow();
			Toast.makeText(getActivity().getApplicationContext(), "Sun",
					Toast.LENGTH_SHORT).show();
			break;
		case DaysOfTheWeek.MONDAY_INT:
			// initiatePopupWindow();
			Toast.makeText(getActivity().getApplicationContext(), "Mon",
					Toast.LENGTH_SHORT).show();

			// llpopup.removeAllViews();
			// LayoutInflater factory =
			// LayoutInflater.from(getActivity().getApplicationContext());
			// View vpopup = factory.inflate(R.layout.weather_popup, null);
			//
			// llpopup.addView(vpopup);
			// llpopup.setOnClickListener(this);
			break;
		case DaysOfTheWeek.TUESDAY_INT:
			Toast.makeText(getActivity().getApplicationContext(), "Tue",
					Toast.LENGTH_SHORT).show();
			break;
		case DaysOfTheWeek.WEDNESDAY_INT:
			Toast.makeText(getActivity().getApplicationContext(), "Wed",
					Toast.LENGTH_SHORT).show();
			break;
		case DaysOfTheWeek.THURSDAY_INT:
			Toast.makeText(getActivity().getApplicationContext(), "Thu",
					Toast.LENGTH_SHORT).show();
			break;
		case DaysOfTheWeek.FRIDAY_INT:
			Toast.makeText(getActivity().getApplicationContext(), "Fri",
					Toast.LENGTH_SHORT).show();
			break;
		case DaysOfTheWeek.SATUERDAY_INT:
			Toast.makeText(getActivity().getApplicationContext(), "Sat",
					Toast.LENGTH_SHORT).show();
			break;
		default:
			// llpopup.removeAllViews();
			// LayoutInflater factory = LayoutInflater.from(this);
			// View vpopup = factory.inflate(R.layout.weather_popup, null);
			//
			// llpopup.addView(vpopup);
			break;
		}
		// TODO Auto-generated method stub

		// LayoutInflater inflater = getLayoutInflater();
		// View layout = inflater.inflate(R.layout.weather_popup,
		// (ViewGroup) findViewById(R.id.toast_layout_root));
		//
		// TextView text = (TextView) layout.findViewById(R.id.text);
		// text.setText("This is a custom toast");
		//
		// Toast toast = new Toast(getApplicationContext());
		// toast.setGravity(Gravity.CENTER_HORIZONTAL, 200, 0);
		// toast.setDuration(Toast.LENGTH_LONG);
		// toast.setView(layout);
		// toast.show();

	}

	private final LocationListener locationListener = new LocationListener() {
		public void onLocationChanged(Location location) {
			updateWithNewLocation(location);
		}

		public void onProviderDisabled(String provider) {
		}

		public void onProviderEnabled(String provider) {
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
	};

	private class DownloadWeather extends AsyncTask<String, String, String> {

		private static final String TAG = "DownloadWeather";
		private ParserType type;
		// private List<WeatherDetails> weathers;
		private String feedUrl;
		private ProgressDialog wdDialog;

		public DownloadWeather(ParserType type, String feedUrl) {
			super();
			this.type = type;
			// weathers = null;//new code
			this.feedUrl = feedUrl;
			this.wdDialog = new ProgressDialog(getActivity());
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			// publishProgress("Loading Weather Details...");

			// if(dialog.isShowing())
			// dialog.dismiss();
			if (!pbWDload.isActivated()) {
				pbWDload.setVisibility(View.VISIBLE);
				pbWDload.setActivated(true);
			}

			wdDialog.setMessage("Conneting to server...");
			wdDialog.show();

		}

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			try {

				// loadLocation();
				FeedParser parser = FeedParserFactory.getParser(type, feedUrl);

				publishProgress("Loading Weather Detials...");

				long start = System.currentTimeMillis();
				weathers = parser.parseWeather();// new code

				publishProgress("Rendering GUI...");
				long duration = System.currentTimeMillis() - start;
				Log.i(TAG, "Parser duration=" + duration);

			} catch (Throwable t) {
				Log.e(TAG, t.getMessage(), t);
			}
			// return weathers;
			// return this.weathers;//new code
			return "Completed";
		}

		@Override
		protected void onPostExecute(String result) {

			// Feed has been Downloaded and Parsed, Display Data to User
			// displayStuff(weathers);

			// weathers = weathers;
			try {
				displayStuff();

				if (pbWDload.isActivated()) {
					pbWDload.setVisibility(View.INVISIBLE);
					pbWDload.setActivated(false);
				}

				if (wdDialog.isShowing()) {
					wdDialog.dismiss();
				}

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		protected void onProgressUpdate(String... progress) {
			// you need to cast the params to the type you want!
			String tmpStr = (String) progress[0];
			// now update the progress dialog
			wdDialog.setMessage(tmpStr);
		}

	}

	/* Pop window method */
	private void initiatePopupWindow() {
		try {

			DisplayMetrics metrics = getActivity().getResources()
					.getDisplayMetrics();
			int width = metrics.widthPixels;
			int height = metrics.heightPixels;

			width = (int) (width * 0.95);
			height = (int) (height * 0.60);
			// We need to get the instance of the LayoutInflater
			LayoutInflater inflater = (LayoutInflater) getActivity()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			// View weatherItem = factory.inflate(R.layout.weather_item, null);

			View layout = inflater.inflate(R.layout.weather_item_popup,
					(ViewGroup) getActivity().findViewById(R.id.wdPopupWindow));
			// inflater.inflate(R.layout.weather_item_popup,
			// (ViewGroup) findViewById(R.id.wdPopupWindow));
			pwindo = new PopupWindow(layout, width, height, true);
			pwindo.setAnimationStyle(R.style.PopupWindowAnimation);
			pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);

			ImageButton btnClosePopup;

			btnClosePopup = (ImageButton) layout
					.findViewById(R.id.btnClosePopup);
			btnClosePopup.setOnClickListener(cancel_button_click_listener);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private OnClickListener cancel_button_click_listener = new OnClickListener() {
		public void onClick(View v) {
			pwindo.dismiss();

		}
	};

}
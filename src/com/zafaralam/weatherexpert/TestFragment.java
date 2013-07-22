package com.zafaralam.weatherexpert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import com.zafaralam.modal.WeatherDetails;
import com.zafaralam.utils.DaysOfTheWeek;
import com.zafaralam.utils.MonthsOfTheYear;
import com.zafaralam.utils.WeatherExpertIcons;
import com.zafaralam.utils.WeatherTypes;
import com.zafaralam.utils.WeatherExpertIcons.IconDesc;
import com.zafaralam.weatherexpert.contentprovider.WeatherExpertAdapter;
import com.zafaralam.weatherexpert.contentprovider.WeatherExpertContract.WeatherDetailsEntry;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint("ValidFragment")
public class TestFragment extends Fragment {
	
	private final String TAG = "TestFragment";
	
	public TestFragment() {
		// TODO Auto-generated constructor stub
	}
	private int favId;
	
	public TestFragment(int favId) {
		// TODO Auto-generated constructor stub
		Log.d(TAG,String.valueOf(favId));
		this.favId = favId;
	}
	WeatherExpertAdapter dbHelper;
	//TextView tvTest;
	//private List<WeatherDetails> weathers;
	private Calendar calander;
	
	private TextView tvLocation;
	private TextView tvCurrentTime;
	private TextView tvCurrentTemp;
	private TextView tvTodayMaxTemp;
	private TextView tvTodayMinTemp;
	private ImageView ivCurrentWeatherIcon;
	private LinearLayout llFiveDayWeather;

	/* Grid item GUI variables for detailed weather */
	private TextView tvCurrentWeatherConditionDesc;
	private TextView tvWindSpeed;
	private TextView tvHumidity;
	private TextView tvVisibility;
	private TextView tvWindDirection;
	private TextView tvPressure;
	private TextView tvCloudCover;
	private TextView tvPrecipitation;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.weather_details, container, false);
		init(v);
		dbHelper.open();
		try {
			displayFromDb();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return v;
	}

	private void displayFromDb() throws ParseException {
		// TODO Auto-generated method stub

		String[] projection = { WeatherDetailsEntry.KEY_LOCATION,
				WeatherDetailsEntry.KEY_TEMP_C,
				WeatherDetailsEntry.KEY_TEMPMAX_C,
				WeatherDetailsEntry.KEY_TEMPMIN_C,
				WeatherDetailsEntry.KEY_DATE,
				WeatherDetailsEntry.KEY_HUMIDITY,
				WeatherDetailsEntry.KEY_CLOUDCOVER,
				WeatherDetailsEntry.KEY_PRECIPMM,
				WeatherDetailsEntry.KEY_PRESSURE,
				WeatherDetailsEntry.KEY_WINDDIRECTION,
				WeatherDetailsEntry.KEY_WINDDIR16POINT,
				WeatherDetailsEntry.KEY_WINDDIRDEGREE,
				WeatherDetailsEntry.KEY_WEATHER_CONDITION,
				WeatherDetailsEntry.KEY_WEATHERDESC,
				WeatherDetailsEntry.KEY_WEATHERICONURL,
				WeatherDetailsEntry.KEY_OBSERVATION_TIME,
				WeatherDetailsEntry.KEY_KEY_WINDSPEEDKMPH,
				WeatherDetailsEntry.KEY_VISIBILITY,
				WeatherDetailsEntry.KEY_WEATHERTYPE
				
				};

		String where = WeatherDetailsEntry.KEY_WD_FAV_ID + " = " + favId;

		Cursor c = dbHelper.query(WeatherDetailsEntry.TABLE_NAME, projection,
				where, null, null, null, null);
		List<WeatherDetails> weathers = null;
		WeatherDetails weather;
		boolean isBegin = true;
		if (c != null && c.getCount() != 0) {
			if (c.getCount() == 1) {
				//tvTest.setText(c.getString(0) + " | " + c.getString(1));
			} else {
				weathers  = new ArrayList<WeatherDetails>();
				String allData = "";
				while (c.moveToNext()) {
					if (isBegin) {
						c.moveToPrevious();

						isBegin = false;
					}
					try{
						
						weather = new WeatherDetails();
						weather.setLocation(c.getString(0));
						weather.setTemp_C(Integer.valueOf(c.getString(1)));
						
						weather.setTempMax_C(Integer.valueOf(c.getString(2)));
						
						weather.setTempMin_C(Integer.valueOf(c.getString(3)));
						
						/*The below work around needs to be standardized*/
						String strDate = c.getString(4);
						int lenStrDate = strDate.length();
						String monthName = strDate.substring(4,4+3);
						int monthNumber = 0;
						
						if(monthName.compareTo(MonthsOfTheYear.JAN) == 0)
							monthNumber = 1;
						if(monthName.compareTo(MonthsOfTheYear.FEB) == 0)
							monthNumber = 2;
						if(monthName.compareTo(MonthsOfTheYear.MAR) == 0)
							monthNumber = 3;
						if(monthName.compareTo(MonthsOfTheYear.APR) == 0)
							monthNumber = 4;
						if(monthName.compareTo(MonthsOfTheYear.MAY) == 0)
							monthNumber = 5;
						if(monthName.compareTo(MonthsOfTheYear.JUN) == 0)
							monthNumber = 6;
						if(monthName.compareTo(MonthsOfTheYear.JUL) == 0)
							monthNumber = 7;
						if(monthName.compareTo(MonthsOfTheYear.AUG) == 0)
							monthNumber = 8;
						if(monthName.compareTo(MonthsOfTheYear.SEP) == 0)
							monthNumber = 9;
						if(monthName.compareTo(MonthsOfTheYear.OCT) == 0)
							monthNumber = 10;
						if(monthName.compareTo(MonthsOfTheYear.NOV) == 0)
							monthNumber = 11;
						if(monthName.compareTo(MonthsOfTheYear.DEC) == 0)
							monthNumber = 12;
						
						String newStrDate = strDate.substring(lenStrDate-4)
								+ "-" + String.valueOf(monthNumber) + "-" +
								strDate.substring(8,8+2);
						
						weather.setDate(newStrDate);
						
						weather.setHumidity(Integer.valueOf(c.getString(5)));
						
						weather.setCloudCover(Integer.valueOf(c.getString(6)));
						
						weather.setPrecipMM(Float.valueOf(c.getString(7)));
						
						weather.setPressure(Integer.valueOf(c.getString(8)));
						
						//weather.setWindDirection(c.getString(9));
						
						weather.setWindDir16Point(c.getString(10));
						
						weather.setWindDirDegree(Integer.valueOf(c.getString(11)));
						
						weather.setWeather_condition(Integer.valueOf(c.getString(12)));
						
						weather.setWeatherDesc(c.getString(13));
						
						weather.setWeatherIconUrl(c.getString(14));
						
						weather.setObservation_time(c.getString(15));
						
						weather.setWindSpeedKmph(Integer.valueOf(c.getString(16)));
						
						weather.setVisibility(Integer.valueOf(c.getString(17)));
						
						weather.setWeatherType(Integer.valueOf(c.getString(18)));
						
						
						weathers.add(weather);
						
					}catch(Exception e){
						Log.d(TAG, e.getMessage().toString());
					}
					//allData = allData + c.getString(0) + c.getString(1) + "\n";
					
				}
				//tvTest.setText(allData);
				try {
					Log.d(TAG, "I'm OK..TOO");
					displayStuff(weathers);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}catch (Exception e) {
					// TODO: handle exception
					Log.d(TAG,e.getMessage().toString());
				}
				
			}
			
			
		}
	}

	private void init(View v) {
		// TODO Auto-generated method stub

		dbHelper = new WeatherExpertAdapter(getActivity());
		//tvTest = (TextView) v.findViewById(R.id.tvTest);
		
		//
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
		
		calander = new GregorianCalendar();

	}
	
	
	public void displayStuff(List<WeatherDetails> weathers) throws ParseException {
		WeatherExpertIcons wei = new WeatherExpertIcons();
		Log.d(TAG, "Inside the display");
		llFiveDayWeather.removeAllViewsInLayout();
		// tvCurrentWeather.setText(weathers.get(0).getTemp_C().toString());

		for (WeatherDetails wd : weathers) {
			//Log.d(TAG, wd.getDate()+" "+String.valueOf(wd.getWeather_condition()));
			//Log.d(TAG, wd.getWeatherIconUrl());
			if (wd.getWeatherType() == WeatherTypes.CURRENT_WEATHER.ordinal()) {
				tvLocation.setText(wd.getLocation());
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
				
				//Log.d(TAG,dateString.toString() + " | " +wd.getDate());
				calander.setTime(wd.getDate());
				// Log.d(TAG,String.valueOf(today.compareTo(wd.getDate())));
				if (today.compareTo(wd.getDate()) == 1) {
					tvTodayMaxTemp.setText("" + max_temp + "\u2103");
					tvTodayMinTemp.setText("" + min_temp + "\u2103");
				} 
				else {
//
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
					//weatherItem.setOnClickListener(this); // setting the onclick
															// listener for each
															// item.
					llFiveDayWeather.addView(weatherItem);
					weatherItem.destroyDrawingCache();
				}
			}

		}
	}


}

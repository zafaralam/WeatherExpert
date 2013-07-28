/*This is a abstract class for comman weather fragment methods and varibles*/
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

import com.zafaralam.modal.Weather;
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



public abstract class AbstractWeatherFragment extends Fragment implements OnClickListener {

	/* Current weather conditions GUI widgets*/
	private final String TAG = "Weather Fragment";
	private TextView tvLocation;
	private TextView tvCurrentTime;
	private TextView tvCurrentTemp;
	private TextView tvTodayMaxTemp;
	private TextView tvTodayMinTemp;
	private ImageView ivCurrentWeatherIcon;
	private LinearLayout llFiveDayWeather;
	private WeatherExpertAdapter dbHelper;

	/* Grid item GUI widgets for details of current weather */
	private TextView tvCurrentWeatherConditionDesc;
	private TextView tvWindSpeed;
	private TextView tvHumidity;
	private TextView tvVisibility;
	private TextView tvWindDirection;
	private TextView tvPressure;
	private TextView tvCloudCover;
	private TextView tvPrecipitation;

	// Location Variables
	private String main_location;

	/* Popup window variables */
	private PopupWindow pwDayWeather;

	/* Local variables */
	private List<Weather> weathers;
	private Calendar calander;

	// private ProgressDialog dialog;
	private ProgressBar pbWDload;

	public abstract void init();
	
}

package com.zafaralam.modal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


public abstract class WeatherDetails_old {
	
	private final SimpleDateFormat DATEFORMATTER = 
			new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
	private String date;
	private int windSpeedMiles;
	private int windSpeedKmph;
	private String windDir16Point;
	private int windDirDegree;
	private int weather_condition;//weatherCode
	private String weatherIconUrl;
	private String weatherDesc;
	private long precipMM;
	
	public int getWeather_condition() {
		return weather_condition;
	}

	public void setWeather_condition(int weather_condition) {
		this.weather_condition = weather_condition;
	}

	public int getWindSpeedMiles() {
		return windSpeedMiles;
	}

	public void setWindSpeedMiles(int windSpeedMiles) {
		this.windSpeedMiles = windSpeedMiles;
	}

	public int getWindSpeedKmph() {
		return windSpeedKmph;
	}

	public void setWindSpeedKmph(int windSpeedKmph) {
		this.windSpeedKmph = windSpeedKmph;
	}

	public int getWindDirDegree() {
		return windDirDegree;
	}

	public void setWindDirDegree(int windDirDegree) {
		this.windDirDegree = windDirDegree;
	}

	public String getWindDir16Point() {
		return windDir16Point;
	}

	public void setWindDir16Point(String windDir16Point) {
		this.windDir16Point = windDir16Point;
	}

	public String getWeatherIconUrl() {
		return weatherIconUrl;
	}

	public void setWeatherIconUrl(String weatherIconUrl) {
		this.weatherIconUrl = weatherIconUrl;
	}

	public String getWeatherDesc() {
		return weatherDesc;
	}

	public void setWeatherDesc(String weatherDesc) {
		this.weatherDesc = weatherDesc;
	}

	public long getPrecipMM() {
		return precipMM;
	}

	public void setPrecipMM(long precipMM) {
		this.precipMM = precipMM;
	}

	public Date getDate() {
			try {
				Calendar cal = Calendar.getInstance();
				TimeZone tz = cal.getTimeZone();
				DATEFORMATTER.setTimeZone(tz);
				return DATEFORMATTER.parse(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

	public void setDate(String date) {
			// pad the date if necessary
			this.date = date;
		}
}

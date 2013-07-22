package com.zafaralam.modal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Weather {
	
	static SimpleDateFormat FORMATTER = 
			new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z");
	private Date date;
	private int weatherCode; 
	private int windSpeedMiles;
	private int windSpeedKmph;
	private int windDirDegree;
	private String windDir16Point;
	private String weatherIconUrl;
	private String weatherDesc;
	private long precipMM;
	
	public int getWeatherCode() {
		return weatherCode;
	}

	public void setWeatherCode(int weatherCode) {
		this.weatherCode = weatherCode;
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

	public String getDate() {
		return FORMATTER.format(this.date);
	}

	public void setDate(String date) {
		// pad the date if necessary
		while (!date.endsWith("00")){
			date += "0";
		}
		try {
			this.date = FORMATTER.parse(date.trim());
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
}

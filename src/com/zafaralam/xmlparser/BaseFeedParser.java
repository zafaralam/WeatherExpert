package com.zafaralam.xmlparser;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;


public abstract class BaseFeedParser implements FeedParser {
	
	// names for the XML tags for www.worldweatheronline.com LOCAL WEATHER
	protected static final String DATA = "data";
	protected static final String REQUEST = "request";
	protected static final String CURRENT_CONDITION = "current_condition";
	protected static final String WEATHER = "weather";
	protected static final String TYPE = "type";
	protected static final String QUERY = "query";
	protected static final String OBSERVATION_TIME = "observation_time";
	protected static final String TEMP_C = "temp_C";
	protected static final String TEMP_F = "temp_F";
	protected static final String WINDSPEEDMILES = "windspeedMiles";
	protected static final String WINDSPEEDKMPH = "windspeedKmph";
	protected static final String WINDDIRDEGREE = "winddirDegree";
	protected static final String WINDDIR16POINT = "winddir16Point";
	protected static final String WEATHERCODE = "weatherCode";
	protected static final String WEATHERDESC = "weatherDesc";
	protected static final String WEATHERICONURL = "weatherIconUrl";
	protected static final String PRECIPMM = "precipMM";
	protected static final String HUMIDITY = "humidity";
	protected static final String VISIBILITY = "visibility";
	protected static final String PRESSURE = "pressure";
	protected static final String CLOUDCOVER = "cloudcover";
	protected static final String DATE = "date";
	protected static final String TEMPMAXC = "tempMaxC";
	protected static final String TEMPMAXF = "tempMaxF";
	protected static final String TEMPMINC = "tempMinC";
	protected static final String TEMPMINF = "tempMinF";
//		static final String = "windspeedMiles";
//		static final String = "windspeedKmph";
//		static final String = "winddirDegree";
	protected static final String WINDDIRECTION = "winddirection";
//		static final String = "weatherCode";
//		static final String = "weatherIconUrl";
//		static final String = "weatherDesc";
//		static final String = "precipMM";
		
		
	//Location search xml tags	
	protected static final String SEARCH_API = "search_api";
	protected static final String RESULT = "result";
	protected static final String AREANAME = "areaName";
	protected static final String COUNTRY = "country";
	protected static final String REGION = "region";
	protected static final String LATITUDE = "latitude";
	protected static final String LONGITUDE = "longitude";
	protected static final String POPULATION = "population";
	protected static final String TIMEZONE = "timezone";
	protected static final String OFFSET = "offset";
	
	/* GeoLocation xml Tags*/
	protected static final String RESPONSE = "Response";
	protected static final String IP = "Ip";
	protected static final String COUNTRYCODE = "CountryCode";
	protected static final String COUNTRYNAME = "CountryName";
	protected static final String REGIONNAME = "RegionName";
	protected static final String REGIONCODE = "RegionCode";
	protected static final String CITY = "City";
	protected static final String ZIPCODE = "ZipCode";
	protected static final String IP_LATITUDE = "Latitude";
	protected static final String IP_LONGITUDE = "Longitude";
	protected static final String METROCODE = "MetroCode";
	protected static final String AREACODE = "AreaCode";


	//Local Time xml Tags
	/*
	 * Use REQUEST,	QUERY, TYPE, TIMEZONE, DATA * 
	 */
	protected static final String LOCALTIME = "localtime";
	protected static final String UTCOFFSET = "utcoffset";
		
	private final URL feedUrl;

		protected BaseFeedParser(String feedUrl){
			try {
				this.feedUrl = new URL(feedUrl);
			} catch (MalformedURLException e) {
				throw new RuntimeException(e);
			}
		}

		protected InputStream getInputStream() {
			try {
				//Log.d("WeatherExpert:BaseFeedParser:", "1");
				return feedUrl.openConnection().getInputStream();
			} catch (IOException e) {
				//Log.d("WeatherExpert:BaseFeedParser:", "2");
				//throw new RuntimeException(e);
				throw new RuntimeException(e.getMessage());
			}
		}
}

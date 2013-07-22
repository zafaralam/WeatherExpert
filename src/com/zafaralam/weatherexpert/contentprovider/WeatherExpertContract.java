package com.zafaralam.weatherexpert.contentprovider;

import android.provider.BaseColumns;

public final class WeatherExpertContract {
	
	public WeatherExpertContract(){}
	
	public static abstract class RecentLocationsEntry implements BaseColumns{
		public static final String TABLE_NAME = "recentlocations";
		public static final String KEY_RECLOC_ID = "_id";
		public static final String KEY_AREANAME = "areaname";
		public static final String KEY_COUNTRY = "country";
		public static final String KEY_REGION = "region";
		public static final String KEY_LAT = "latitude";
		public static final String KEY_LNG = "longitude";
	}
	
	public static abstract class FavouritesEnrty implements BaseColumns{
		
		public static final String TABLE_NAME = "favourites";
		public static final String KEY_FAV_ID = "_id";
		public static final String KEY_CITYNAME = "name";
		public static final String KEY_LAT = "latitude";
		public static final String KEY_LNG = "longitude";
		public static final String KEY_POSTCODE = "postcode";
		public static final String KEY_IPADDRESS = "ipaddress";
		public static final String KEY_DEFAULT_LOC = "isDefault";
	}
	
	public static abstract class WeatherDetailsEntry implements BaseColumns {
		
		public static final String TABLE_NAME = "weatherdetails";
		public static final String KEY_WD_ID = "_id";
		public static final String KEY_WD_FAV_ID = "favouritesId";
		public static final String KEY_LOCATION = "location"; // Get from xml tag query
		public static final String KEY_OBSERVATION_TIME = "observationTime";
		public static final String KEY_TEMP_C = "tempC";
		public static final String KEY_TEMP_F = "tempF";
		public static final String KEY_WEATHER_CONDITION = "weatherCondition"; //weatherCode
		public static final String KEY_DATE = "date";
		public static final String KEY_WINDSPEEDMILES = "windSpeedMiles";
		public static final String KEY_KEY_WINDSPEEDKMPH = "windSpeedKmph";
		public static final String KEY_WINDDIRDEGREE = "windDirDegree";
		public static final String KEY_WINDDIR16POINT = "windDir16Point";
		public static final String KEY_WEATHERICONURL = "weatherIconUrl";
		public static final String KEY_WEATHERDESC = "weatherDesc";
		public static final String KEY_PRECIPMM = "precipMM";
		public static final String KEY_HUMIDITY = "humidity";
		public static final String KEY_VISIBILITY = "visibility";
		public static final String KEY_PRESSURE = "pressure";
		public static final String KEY_CLOUDCOVER = "cloudCover";
		public static final String KEY_TEMPMAX_C = "tempMaxC";
		public static final String KEY_TEMPMAX_F = "tempMaxF";
		public static final String KEY_TEMPMIN_C = "tempMinC";
		public static final String KEY_TEMPMIN_F = "tempMinF";
		public static final String KEY_WINDDIRECTION = "windDirection";
		public static final String KEY_WEATHERTYPE = "weatherType";
		
	}

}

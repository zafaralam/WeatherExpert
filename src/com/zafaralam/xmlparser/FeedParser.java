package com.zafaralam.xmlparser;

import java.util.List;

import com.zafaralam.modal.GeoLocation;
import com.zafaralam.modal.Weather;
import com.zafaralam.modal.WeatherLocation;
import com.zafaralam.modal.Message;

public interface FeedParser {

	//List<Message> parse();
	
	List<Weather> parseWeather();
	List<WeatherLocation> parseLocations();
	GeoLocation parseGeoLocation();
}

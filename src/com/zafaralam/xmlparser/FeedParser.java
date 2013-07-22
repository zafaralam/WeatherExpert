package com.zafaralam.xmlparser;

import java.util.List;

import com.zafaralam.modal.WeatherLocation;
import com.zafaralam.modal.Message;
import com.zafaralam.modal.WeatherDetails;

public interface FeedParser {

	//List<Message> parse();
	
	List<WeatherDetails> parseWeather();
	List<WeatherLocation> parseLocations();
}

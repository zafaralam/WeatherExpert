package com.zafaralam.xmlparser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import com.zafaralam.modal.WeatherLocation;
import com.zafaralam.modal.Message;
import com.zafaralam.modal.WeatherDetails;
import com.zafaralam.utils.WeatherTypes;

import android.text.format.DateFormat;
import android.util.Log;
import android.util.Xml;

public class XmlPullFeedParser extends BaseFeedParser {

	private static final String TAG = "XmlPullFeedParser";
	private XmlPullParser parser;
	
	public XmlPullFeedParser(String feedUrl) {
		super(feedUrl);
		this.parser = Xml.newPullParser();
	}
	
	
	@Override
	public List<WeatherLocation> parseLocations() {
		// TODO Auto-generated method stub
		List<WeatherLocation> locations = null;
		try {
			
			parser.setInput(this.getInputStream(),null);
			int eventType = parser.getEventType();
			WeatherLocation loc = null;
			boolean done = false;
			while (eventType != XmlPullParser.END_DOCUMENT && !done){
				String tagName = null;
				switch (eventType){
				case XmlPullParser.START_DOCUMENT:
					locations = new ArrayList<WeatherLocation>();
					break;
				case XmlPullParser.START_TAG:
					tagName = parser.getName();
					
					if (tagName.equalsIgnoreCase(RESULT)){
						loc = new WeatherLocation();
					}else if (loc != null){
						//Log.d("Debug: ","enterning wd!=null");
						if (tagName.equalsIgnoreCase(AREANAME)){
							loc.setAreaName(parser.nextText());
						} else if (tagName.equalsIgnoreCase(COUNTRY)){
							loc.setCountry(parser.nextText());
						} else if (tagName.equalsIgnoreCase(REGION)){
							loc.setRegion(parser.nextText());
						} else if (tagName.equalsIgnoreCase(LATITUDE)){
							loc.setLatitude(Float.parseFloat(parser.nextText()));
						} else if (tagName.equalsIgnoreCase(LONGITUDE)){
							loc.setLongitude(Float.parseFloat(parser.nextText()));
						} else if (tagName.equalsIgnoreCase(POPULATION)){
							loc.setPopulation((Integer.parseInt(parser.nextText())));
						} else if (tagName.equalsIgnoreCase(OFFSET)){
							loc.setTimezone_offset((Float.parseFloat(parser.nextText())));
						} 
					}
					break;
				case XmlPullParser.END_TAG:
					tagName = parser.getName();
					if (tagName.equalsIgnoreCase(RESULT) && loc != null){
						//Log.d(TAG,"closing current codition");
						locations.add(loc);
					}else if (tagName.equalsIgnoreCase(RESULT)){
						done = true;
					}
					break;
			}
			eventType = parser.next();
			}
			
		}catch(Exception e){
			Log.e(TAG, e.getMessage(), e);
			throw new RuntimeException(e);
		}
		
		return locations;
	}
	
	public List<WeatherDetails> parseWeather() {
		// TODO Auto-generated method stub
		List<WeatherDetails> weather = null;
		try {
			// auto-detect the encoding from the stream
			//Log.d("WeatherExpert:XmlPullFeedParser:", "1");
			parser.setInput(this.getInputStream(), null);
			int eventType = parser.getEventType();
			WeatherDetails wd = null;
			boolean done = false;
			//Log.d("WeatherExpert:XmlPullFeedParser:", "2");
			//int eventType = parser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT && !done){
				String name = null;
				//Log.d("WeatherExpert:XmlPullFeedParser:", "Entered while loop");
				switch (eventType){
					case XmlPullParser.START_DOCUMENT:
						weather = new ArrayList<WeatherDetails>();
						break;
					case XmlPullParser.START_TAG:
						name = parser.getName();
						
						if (name.equalsIgnoreCase(CURRENT_CONDITION)){
							wd = new WeatherDetails();
							wd.setWeatherType(WeatherTypes.CURRENT_WEATHER.ordinal());
							Calendar tDate = Calendar.getInstance();
							//tDate.getTime();
							String DATE_FORMATER = "yyyy-MM-dd";
							CharSequence dateString = DateFormat.format(DATE_FORMATER, tDate);
							wd.setDate(dateString.toString());
						} else if(name.equalsIgnoreCase(WEATHER)){
							wd = new WeatherDetails();
							wd.setWeatherType(WeatherTypes.DAY_WEATHER.ordinal());
						}else if (wd != null){
							//Log.d("Debug: ","enterning wd!=null");
							if (name.equalsIgnoreCase(OBSERVATION_TIME)){
								wd.setObservation_time(parser.nextText());
							} else if (name.equalsIgnoreCase(TEMP_C)){
								wd.setTemp_C(Integer.parseInt(parser.nextText()));
							} else if (name.equalsIgnoreCase(TEMP_F)){
								wd.setTemp_F(Integer.parseInt(parser.nextText()));
							} else if (name.equalsIgnoreCase(HUMIDITY)){
								wd.setHumidity((Integer.parseInt(parser.nextText())));
							} else if (name.equalsIgnoreCase(VISIBILITY)){
								wd.setVisibility((Integer.parseInt(parser.nextText())));
							} else if (name.equalsIgnoreCase(PRESSURE)){
								wd.setPressure((Integer.parseInt(parser.nextText())));
							} else if (name.equalsIgnoreCase(CLOUDCOVER)){
								wd.setCloudCover((Integer.parseInt(parser.nextText())));
							} else if (name.equalsIgnoreCase(WEATHERCODE)){
								wd.setWeather_condition(Integer.parseInt(parser.nextText()));
							} else if (name.equalsIgnoreCase(DATE)){
								wd.setDate(parser.nextText());
							}  else if (name.equalsIgnoreCase(TEMPMAXC)){
								wd.setTempMax_C(Integer.parseInt(parser.nextText()));
							} else if (name.equalsIgnoreCase(TEMPMAXF)){
								wd.setTempMax_F(Integer.parseInt(parser.nextText()));
							}  else if (name.equalsIgnoreCase(TEMPMINC)){
								wd.setTempMin_C(Integer.parseInt(parser.nextText()));
							} else if (name.equalsIgnoreCase(TEMPMINF)){
								wd.setTempMin_F(Integer.parseInt(parser.nextText()));
							} else if (name.equalsIgnoreCase(WEATHERICONURL)){
								wd.setWeatherIconUrl(parser.nextText());
							} else if (name.equalsIgnoreCase(WEATHERDESC)){
								wd.setWeatherDesc(parser.nextText());
							} else if (name.equalsIgnoreCase(WINDSPEEDKMPH)){
								wd.setWindSpeedKmph(Integer.parseInt(parser.nextText()));
							} else if (name.equalsIgnoreCase(WINDSPEEDMILES)){
								wd.setWindSpeedMiles(Integer.parseInt(parser.nextText()));
							} else if (name.equalsIgnoreCase(WINDDIRDEGREE)){
								wd.setWindDirDegree(Integer.parseInt(parser.nextText()));
							} else if (name.equalsIgnoreCase(WINDDIR16POINT)){
								wd.setWindDir16Point(parser.nextText());
							} else if (name.equalsIgnoreCase(PRECIPMM)){
								wd.setPrecipMM(Float.parseFloat(parser.nextText()));
							} else if (name.equalsIgnoreCase(WINDDIRECTION)){
								wd.setWindDirection(parser.nextText());
							}
						}
						break;
					case XmlPullParser.END_TAG:
						name = parser.getName();
						if (name.equalsIgnoreCase(CURRENT_CONDITION) && wd != null){
							//Log.d(TAG,"closing current codition");
							weather.add(wd);
						} else if(name.equalsIgnoreCase(WEATHER) && wd != null){
							weather.add(wd);
						}else if (name.equalsIgnoreCase(DATA)){
							done = true;
						}
						break;
				}
				eventType = parser.next();
			}
		} catch (Exception e) {
			Log.e(TAG, e.getMessage(), e);
			throw new RuntimeException(e);
		}
		return weather;
	}
}
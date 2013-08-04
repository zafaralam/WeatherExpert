package com.zafaralam.xmlparser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import com.zafaralam.modal.CurrentWeather;
import com.zafaralam.modal.DayWeather;
import com.zafaralam.modal.GeoLocation;
import com.zafaralam.modal.Weather;
import com.zafaralam.modal.WeatherLocation;
import com.zafaralam.modal.Message;
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
	
	public List<Weather> parseWeather() {
		// TODO Auto-generated method stub
		List<Weather> weather = null;
		try {
			// auto-detect the encoding from the stream
			//Log.d("WeatherExpert:XmlPullFeedParser:", "1");
			parser.setInput(this.getInputStream(), null);
			int eventType = parser.getEventType();
			Weather wd = null;
			boolean done = false;
			//Log.d("WeatherExpert:XmlPullFeedParser:", "2");
			//int eventType = parser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT && !done){
				String name = null;
				//Log.d("WeatherExpert:XmlPullFeedParser:", "Entered while loop");
				switch (eventType){
					case XmlPullParser.START_DOCUMENT:
						weather = new ArrayList<Weather>();
						break;
					case XmlPullParser.START_TAG:
						name = parser.getName();
						
						if (name.equalsIgnoreCase(CURRENT_CONDITION)){
							wd = new CurrentWeather();
							//wd.setWeatherType(WeatherTypes.CURRENT_WEATHER.ordinal());
							Calendar tDate = Calendar.getInstance();
							//tDate.getTime();
							String DATE_FORMATER = "yyyy-MM-dd";
							CharSequence dateString = DateFormat.format(DATE_FORMATER, tDate);
							wd.setDate(dateString.toString());
						} else if(name.equalsIgnoreCase(WEATHER)){
							wd = new DayWeather();
							//wd.setWeatherType(WeatherTypes.DAY_WEATHER.ordinal());
						}else if (wd != null){
							//Log.d("Debug: ","enterning wd!=null");
							if (name.equalsIgnoreCase(OBSERVATION_TIME)){
								((CurrentWeather) wd).setObservationTime(parser.nextText());
							} else if (name.equalsIgnoreCase(TEMP_C)){
								((CurrentWeather) wd).setTemp_C(Integer.parseInt(parser.nextText()));
							} else if (name.equalsIgnoreCase(TEMP_F)){
								((CurrentWeather) wd).setTemp_F(Integer.parseInt(parser.nextText()));
							} else if (name.equalsIgnoreCase(HUMIDITY)){
								((CurrentWeather) wd).setHumidity((Integer.parseInt(parser.nextText())));
							} else if (name.equalsIgnoreCase(VISIBILITY)){
								((CurrentWeather) wd).setVisibility((Integer.parseInt(parser.nextText())));
							} else if (name.equalsIgnoreCase(PRESSURE)){
								((CurrentWeather) wd).setPressure((Integer.parseInt(parser.nextText())));
							} else if (name.equalsIgnoreCase(CLOUDCOVER)){
								((CurrentWeather) wd).setCloudCover((Integer.parseInt(parser.nextText())));
							} else if (name.equalsIgnoreCase(WEATHERCODE)){
								wd.setWeather_condition(Integer.parseInt(parser.nextText()));
							} else if (name.equalsIgnoreCase(DATE)){
								wd.setDate(parser.nextText());
							}  else if (name.equalsIgnoreCase(TEMPMAXC)){
								((DayWeather)wd).setTempMax_C(Integer.parseInt(parser.nextText()));
							} else if (name.equalsIgnoreCase(TEMPMAXF)){
								((DayWeather)wd).setTempMax_F(Integer.parseInt(parser.nextText()));
							}  else if (name.equalsIgnoreCase(TEMPMINC)){
								((DayWeather)wd).setTempMin_C(Integer.parseInt(parser.nextText()));
							} else if (name.equalsIgnoreCase(TEMPMINF)){
								((DayWeather)wd).setTempMin_F(Integer.parseInt(parser.nextText()));
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
								((DayWeather)wd).setWindDirection(parser.nextText());
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


	@Override
	public GeoLocation parseGeoLocation() {
		// TODO Auto-generated method stub

		GeoLocation geoLocation = null;

		try{
			parser.setInput(this.getInputStream(), null);
			int eventType = parser.getEventType();
			boolean done = false;

			while(eventType != XmlPullParser.END_DOCUMENT && !done)
			{
				String tagName = null;
				switch (eventType)
				{
					case XmlPullParser.START_DOCUMENT:
						geoLocation = new GeoLocation();
						break;
					case XmlPullParser.START_TAG:
						tagName = parser.getName();
							if (tagName.equalsIgnoreCase(IP)){
								String ip = parser.nextText();
								//Log.d(TAG, ip);
								geoLocation.setIp(ip);
							} else if (tagName.equalsIgnoreCase(COUNTRYCODE)){
								geoLocation.setCountryCode(parser.nextText());
							} else if (tagName.equalsIgnoreCase(COUNTRYNAME)){
								geoLocation.setCountryName(parser.nextText());
							} else if (tagName.equalsIgnoreCase(REGIONCODE)){
								geoLocation.setRegionCode(parser.nextText());
							} else if (tagName.equalsIgnoreCase(REGIONNAME)){
								geoLocation.setRegionName(parser.nextText());
							} else if (tagName.equalsIgnoreCase(CITY)){
								geoLocation.setCity(parser.nextText());
							} else if (tagName.equalsIgnoreCase(ZIPCODE)){
								geoLocation.setLongitude(parser.nextText());
							} else if (tagName.equalsIgnoreCase(IP_LATITUDE)){
								geoLocation.setLatitude(parser.nextText());
							} else if (tagName.equalsIgnoreCase(IP_LONGITUDE)){
								geoLocation.setLongitude(parser.nextText());
							} else if (tagName.equalsIgnoreCase(METROCODE)){
								geoLocation.setLongitude(parser.nextText());
							} else if (tagName.equalsIgnoreCase(AREACODE)){
								geoLocation.setLongitude(parser.nextText());
							} 
						break;
					case XmlPullParser.END_TAG:
						tagName = parser.getName();
						if (tagName.equalsIgnoreCase(RESPONSE)){
							done = true;
						}
						break;
				}
				eventType = parser.next();
			}

		}
		catch (Exception e){
			Log.e(TAG, e.getMessage(), e);
			throw new RuntimeException(e);
		}

		return geoLocation;
	}
}

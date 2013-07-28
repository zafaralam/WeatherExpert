package com.zafaralam.modal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

public class Weather_old implements Comparable<Weather_old>{
	
		//static SimpleTimeZone TIMEFORMATTER = new SimpleTimeZone(0, "EST");
		static SimpleDateFormat DATEFORMATTER = 
			new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		private String location; // Get from xml tag query
		private String observation_time;
		private int temp_C;
		private int temp_F;
		private int weather_condition; //weatherCode
		private String date;
		private int windSpeedMiles;
		private int windSpeedKmph;
		private int windDirDegree;
		private String windDir16Point;
		private String weatherIconUrl;
		private String weatherDesc;
		private float precipMM;
		private int humidity;
		private int visibility;
		private int pressure;
		private int cloudCover;
		private int tempMax_C;
		private int tempMax_F;
		private int tempMin_C;
		private int tempMin_F;
		private String windDirection;
		private int weatherType;
		
		public int getWeatherType() {
			return weatherType;
		}

		public void setWeatherType(int weatherType) {
			this.weatherType = weatherType;
		}

		public String getObservation_time() {
			return observation_time;
		}

		public void setObservation_time(String observation_time) {
			this.observation_time = observation_time;
		}

		public String getLocation() {
			return location;
		}

		public void setLocation(String location) {
			this.location = location;
		}

		public int getTemp_C() {
			return temp_C;
		}

		public void setTemp_C(int temp_C) {
			this.temp_C = temp_C;
		}

		public int getTemp_F() {
			return temp_F;
		}

		public void setTemp_F(int temp_F) {
			this.temp_F = temp_F;
		}

		public int getWeather_condition() {
			return weather_condition;
		}

		public void setWeather_condition(int weather_condition) {
			this.weather_condition = weather_condition;
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

		public float getPrecipMM() {
			return precipMM;
		}

		public void setPrecipMM(float precipMM) {
			this.precipMM = precipMM;
		}

		public int getHumidity() {
			return humidity;
		}

		public void setHumidity(int humidity) {
			this.humidity = humidity;
		}

		public int getVisibility() {
			return visibility;
		}

		public void setVisibility(int visibility) {
			this.visibility = visibility;
		}

		public int getPressure() {
			return pressure;
		}

		public void setPressure(int pressure) {
			this.pressure = pressure;
		}

		public int getCloudCover() {
			return cloudCover;
		}

		public void setCloudCover(int cloudCover) {
			this.cloudCover = cloudCover;
		}

		public int getTempMax_C() {
			return tempMax_C;
		}

		public void setTempMax_C(int tempMax_C) {
			this.tempMax_C = tempMax_C;
		}

		public int getTempMax_F() {
			return tempMax_F;
		}

		public void setTempMax_F(int tempMax_F) {
			this.tempMax_F = tempMax_F;
		}

		public int getTempMin_C() {
			return tempMin_C;
		}

		public void setTempMin_C(int tempMin_C) {
			this.tempMin_C = tempMin_C;
		}

		public int getTempMin_F() {
			return tempMin_F;
		}

		public void setTempMin_F(int tempMin_F) {
			this.tempMin_F = tempMin_F;
		}

		public String getWindDirection() {
			return windDirection;
		}

		public void setWindDirection(String windDirection) {
			this.windDirection = windDirection;
		}
		
//	public int compareTo(Weather_old another) {
//		// TODO Auto-generated method stub
//		if (another == null) return 1;
//		
//		return another.observation_time.compareToIgnoreCase(this.observation_time);
//	}

	@Override
	public int compareTo(Weather_old arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/*
	
	public WeatherDetails copy(){
		WeatherDetails copy = new WeatherDetails();
		copy.location = this.location;
		copy.observation_time = this.observation_time;
		copy.temp_C = this.temp_C;
		copy.temp_F = this.temp_F;
		copy.weather_condition = this.weather_condition;
		
		return copy;
	}
	
@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	
	WeatherDetails other = (WeatherDetails) obj;
	
	if (location == null) {
		if (other.location != null)
			return false;
	} else if (!location.equals(other.location))
		return false;
	
	if (observation_time == null) {
		if (other.observation_time != null)
			return false;
	} else if (!observation_time.equals(other.observation_time))
		return false;
	
	if (temp_C == null) {
		if (other.temp_C != null)
			return false;
	} else if (!temp_C.equals(other.temp_C))
		return false;
	
	if (temp_F == null) {
		if (other.temp_F != null)
			return false;
	} else if (!temp_F.equals(other.temp_F))
		return false;
	
	if (weather_condition == null) {
		if (other.weather_condition != null)
			return false;
	} else if (!weather_condition.equals(other.weather_condition))
		return false;
	
	return true;
}

*/

}

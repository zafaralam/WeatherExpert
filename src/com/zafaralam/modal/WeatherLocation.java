package com.zafaralam.modal;

public class WeatherLocation {
	private String areaName;
	private String country;
	private String region;
	private float latitude;
	private float longitude;
	private int population;
	private String weatherUrl;
	private float timezone_offset;
	
	public WeatherLocation() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public int getPopulation() {
		return population;
	}

	public void setPopulation(int population) {
		this.population = population;
	}

	public String getWeatherUrl() {
		return weatherUrl;
	}

	public void setWeatherUrl(String weatherUrl) {
		this.weatherUrl = weatherUrl;
	}

	public float getTimezone_offset() {
		return timezone_offset;
	}

	public void setTimezone_offset(float timezone_offset) {
		this.timezone_offset = timezone_offset;
	}
	
	
}

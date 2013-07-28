package com.zafaralam.modal;


public class CurrentWeather extends Weather{
	
	private String location;
	private String observationTime;
	private int temp_C;
	private int temp_F;
	private int humidity;
	private int visibility;
	private int pressure;
	private int cloudCover;

	public CurrentWeather() {
		super();
		// TODO Auto-generated constructor stub
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
	
	public String getObservationTime() {
		return observationTime;
	}

	public void setObservationTime(String observationTime) {
		this.observationTime = observationTime;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
}

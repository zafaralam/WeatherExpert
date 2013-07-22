package com.zafaralam.modal;


public class DayWeather extends Weather{

	private int tempMax_C;
	private int tempMax_F;
	private int tempMin_C;
	private int tempMin_F;
	private String windDirection;
	
	public DayWeather() {
		super();
		// TODO Auto-generated constructor stub
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
}

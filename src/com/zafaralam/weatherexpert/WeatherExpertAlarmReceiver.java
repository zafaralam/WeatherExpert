package com.zafaralam.weatherexpert;

import com.zafaralam.weatherexpert.service.WeatherDetailsUpdateService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class WeatherExpertAlarmReceiver extends BroadcastReceiver{

	public static final String ACTION_REFRESH_WEATHERDETAILS_ALARM = 
		      "com.zafaralam.weatherexpert.ACTION_REFRESH_WEATHERDETAILS_ALARM";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Intent startIntent = new Intent(context, WeatherDetailsUpdateService.class);
		context.startService(startIntent);
	}
	
	

}

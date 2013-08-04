package com.zafaralam.utils;

import com.zafaralam.weatherexpert.R;

import android.content.Context;

public class WeatherExpertIcons {

	public class IconDesc {
		private int icon;
		private String desc;
		private String strIcon;
		private String[] iconCharList;
		private int[] iconCharColorList;

		public IconDesc(int icon, String desc) {
			super();
			this.icon = icon;
			this.desc = desc;
		}
		
		public IconDesc(int icon, String desc,String strIcon) {
			super();
			this.icon = icon;
			this.desc = desc;
			this.setStrIcon(strIcon);
		}
		

		public IconDesc(int icon, String desc, String strIcon,
				String[] iconCharList, int[] iconCharColorList) {
			super();
			this.icon = icon;
			this.desc = desc;
			this.strIcon = strIcon;
			this.setIconCharList(iconCharList);
			this.setIconCharColorList(iconCharColorList);
		}

		public int getIcon() {
			return icon;
		}

		public void setIcon(int icon) {
			this.icon = icon;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}

		public String getStrIcon() {
			return strIcon;
		}

		public void setStrIcon(String strIcon) {
			this.strIcon = strIcon;
		}

		public String[] getIconCharList() {
			return iconCharList;
		}

		public void setIconCharList(String[] iconCharList) {
			this.iconCharList = iconCharList;
		}

		public int[] getIconCharColorList() {
			return iconCharColorList;
		}

		public void setIconCharColorList(int[] iconCharColorList) {
			this.iconCharColorList = iconCharColorList;
		}

	}

	public IconDesc getWeatherIcon(String weatherIconUrl, int weatherCode,
			Context contex) {
		String description = null;
		int icon = 0;
		String _strIcon = null;
		String[] _iconCharList = new String[3];
		int[] _iconCharColorList = new int[3];
		if (weatherCode == 395)
			description = "Moderate/Heavy snow";

		if (weatherCode == 392)
			description = "Light snow with thunder";

		if (weatherCode == 389)
			description = "Moderate/Heavy rain with thunder";

		if (weatherCode == 386)
			description = "Light rain with thunder";

		if (weatherCode == 377)
			description = "Moderate/heavy showers of ice pellets";

		if (weatherCode == 374)
			description = "Light showers of ice pellets";

		if (weatherCode == 371)
			description = "Moderate/heavy snow showers";

		if (weatherCode == 368)
			description = "Light snow showers";

		if (weatherCode == 365)
			description = "Moderate/Heavy sleet showers";

		if (weatherCode == 362)
			description = "Light sleet showers";

		if (weatherCode == 359)
			description = "Torrential rain shower";

		if (weatherCode == 356)
			description = "Moderate/Heavy rain shower";

		if (weatherCode == 353)
			description = "Light rain shower";

		if (weatherCode == 350)
			description = "Ice pellets";

		if (weatherCode == 338)
			description = "Heavy Snow";

		if (weatherCode == 335)
			description = "Patchy heavy Snow";

		if (weatherCode == 332)
			description = "Moderate snow";

		if (weatherCode == 329)
			description = "Patchy moderate snow";

		if (weatherCode == 326)
			description = "Light snow";

		if (weatherCode == 323)
			description = "Patchy light snow";

		if (weatherCode == 320)
			description = "Moderate/Heavy Sleet";

		if (weatherCode == 317)
			description = "Light Sleet";

		if (weatherCode == 314)
			description = "Moderate/Heavy freezing rain";

		if (weatherCode == 311)
			description = "Light freezing rain";

		if (weatherCode == 308)
			description = "Heavy rain";

		if (weatherCode == 305)
			description = "Heavy rain at times";

		if (weatherCode == 302)
			description = "Moderate rain";

		if (weatherCode == 299)
			description = "Moderate rain at times";

		if (weatherCode == 296)
			description = "Light Rain";

		if (weatherCode == 293)
			description = "Light Rain";

		if (weatherCode == 284)
			description = "Heavy freezing drizzle";

		if (weatherCode == 281)
			description = "Freezing drizzle";

		if (weatherCode == 266)
			description = "Light drizzle";

		if (weatherCode == 263)
			description = "Light drizzle";

		if (weatherCode == 260)
			description = "Freezing Fog";

		if (weatherCode == 248)
			description = "Fog";

		if (weatherCode == 230)
			description = "Blizzard";

		if (weatherCode == 227)
			description = "Blowing snow";

		if (weatherCode == 200)
			description = "Thundery Outbreaks";

		if (weatherCode == 185)
			description = "Freezing drizzle";

		if (weatherCode == 182)
			description = "Patchy sleet";

		if (weatherCode == 179)
			description = "Patchy Snow";

		if (weatherCode == 176)
			description = "Patchy rain";

		if (weatherCode == 143)
			description = "Mist";

		if (weatherCode == 122)
			description = "Overcast";

		if (weatherCode == 119)
			description = "Cloudy";

		if (weatherCode == 116)
			description = "Partly Cloudy";

		if (weatherCode == 113)
			description = "Clear/Sunny";
			

		if (weatherIconUrl.contains("wsymbol_0001_sunny")){
			icon = contex.getResources().getIdentifier("sunny", "drawable",
					contex.getPackageName());
			_strIcon = "B";
			_iconCharList[0] = contex.getResources().getString(R.string.sun);
			_iconCharList[1] = contex.getResources().getString(R.string.sun);
			_iconCharList[2] = contex.getResources().getString(R.string.sun);
			
			_iconCharColorList[0] = contex.getResources().getColor(R.color.sun);
			_iconCharColorList[1] = contex.getResources().getColor(R.color.sun);
			_iconCharColorList[2] = contex.getResources().getColor(R.color.sun);
		}
		if (weatherIconUrl.contains("wsymbol_0002_sunny_intervals")){
			icon = contex.getResources().getIdentifier("fair", "drawable",
					contex.getPackageName());
			_strIcon = "H";
			
			_iconCharList[0] = contex.getResources().getString(R.string.cloud);
			_iconCharList[1] = contex.getResources().getString(R.string.sunny);
			_iconCharList[2] = contex.getResources().getString(R.string.sunny);
			
			_iconCharColorList[0] = contex.getResources().getColor(R.color.cloud);
			_iconCharColorList[1] = contex.getResources().getColor(R.color.sunny);
			_iconCharColorList[2] = contex.getResources().getColor(R.color.sunny);
		}
		if (weatherIconUrl.contains("wsymbol_0003_white_cloud")){
			icon = contex.getResources().getIdentifier("cloudy", "drawable",
					contex.getPackageName());
			_strIcon = "Y";
			_iconCharList[0] = contex.getResources().getString(R.string.cloud);
			_iconCharList[1] = contex.getResources().getString(R.string.cloud);
			_iconCharList[2] = contex.getResources().getString(R.string.cloud);
			
			_iconCharColorList[0] = contex.getResources().getColor(R.color.cloud);
			_iconCharColorList[1] = contex.getResources().getColor(R.color.cloud);
			_iconCharColorList[2] = contex.getResources().getColor(R.color.cloud);
		}
		if (weatherIconUrl.contains("wsymbol_0004_black_low_cloud")){
			icon = contex.getResources().getIdentifier("cloudy", "drawable",
					contex.getPackageName());
			_strIcon = "%";
			_iconCharList[0] = contex.getResources().getString(R.string.cloud);
			_iconCharList[1] = contex.getResources().getString(R.string.cloud);
			_iconCharList[2] = contex.getResources().getString(R.string.cloud);
			
			_iconCharColorList[0] = contex.getResources().getColor(R.color.base_thunder_cloud);
			_iconCharColorList[1] = contex.getResources().getColor(R.color.base_thunder_cloud);
			_iconCharColorList[2] = contex.getResources().getColor(R.color.base_thunder_cloud);
		}
		if (weatherIconUrl.contains("wsymbol_0006_mist")){
			icon = contex.getResources().getIdentifier("smoky", "drawable",
					contex.getPackageName());
			_strIcon = "M";
			_iconCharList[0] = contex.getResources().getString(R.string.mist);
			_iconCharList[1] = contex.getResources().getString(R.string.mist);
			_iconCharList[2] = contex.getResources().getString(R.string.mist);
			
			_iconCharColorList[0] = contex.getResources().getColor(R.color.mist);
			_iconCharColorList[1] = contex.getResources().getColor(R.color.mist);
			_iconCharColorList[2] = contex.getResources().getColor(R.color.mist);
		}
		if (weatherIconUrl.contains("wsymbol_0007_fog")){
			icon = contex.getResources().getIdentifier("foggy", "drawable",
					contex.getPackageName());
			_strIcon = "E";
			_iconCharList[0] = contex.getResources().getString(R.string.mist);
			_iconCharList[1] = contex.getResources().getString(R.string.mist);
			_iconCharList[2] = contex.getResources().getString(R.string.mist);
			
			_iconCharColorList[0] = contex.getResources().getColor(R.color.mist);
			_iconCharColorList[1] = contex.getResources().getColor(R.color.mist);
			_iconCharColorList[2] = contex.getResources().getColor(R.color.mist);
		}
		if (weatherIconUrl.contains("wsymbol_0008_clear_sky_night")){
			icon = contex.getResources().getIdentifier("night_fair",
					"drawable", contex.getPackageName());
			_strIcon = "2";
			_iconCharList[0] = contex.getResources().getString(R.string.moon);
			_iconCharList[1] = contex.getResources().getString(R.string.moon);
			_iconCharList[2] = contex.getResources().getString(R.string.moon);
			
			_iconCharColorList[0] = contex.getResources().getColor(R.color.moon);
			_iconCharColorList[1] = contex.getResources().getColor(R.color.moon);
			_iconCharColorList[2] = contex.getResources().getColor(R.color.moon);
		}
		if (weatherIconUrl.contains("wsymbol_0009_light_rain_showers")){
			icon = contex.getResources().getIdentifier("showers", "drawable",
					contex.getPackageName());
			_strIcon = "Q";
			_iconCharList[0] = contex.getResources().getString(R.string.base_cloud);
			_iconCharList[1] = contex.getResources().getString(R.string.showers);
			_iconCharList[2] = contex.getResources().getString(R.string.showers);
			
			_iconCharColorList[0] = contex.getResources().getColor(R.color.base_cloud);
			_iconCharColorList[1] = contex.getResources().getColor(R.color.showers);
			_iconCharColorList[2] = contex.getResources().getColor(R.color.showers);
		}
		if (weatherIconUrl.contains("wsymbol_0010_heavy_rain_showers")){
			icon = contex.getResources().getIdentifier("rain", "drawable",
					contex.getPackageName());
			_strIcon = "R";
			_iconCharList[0] = contex.getResources().getString(R.string.base_cloud);
			_iconCharList[1] = contex.getResources().getString(R.string.rainy);
			_iconCharList[2] = contex.getResources().getString(R.string.rainy);
			
			_iconCharColorList[0] = contex.getResources().getColor(R.color.base_cloud);
			_iconCharColorList[1] = contex.getResources().getColor(R.color.rainy);
			_iconCharColorList[2] = contex.getResources().getColor(R.color.rainy);
		}
		if (weatherIconUrl.contains("wsymbol_0011_light_snow_showers")){
			icon = contex.getResources().getIdentifier("snow", "drawable",
					contex.getPackageName());
			_strIcon = "U";
			_iconCharList[0] = contex.getResources().getString(R.string.base_cloud);
			_iconCharList[1] = contex.getResources().getString(R.string.snow);
			_iconCharList[2] = contex.getResources().getString(R.string.snow);
			
			_iconCharColorList[0] = contex.getResources().getColor(R.color.base_cloud);
			_iconCharColorList[1] = contex.getResources().getColor(R.color.snow);
			_iconCharColorList[2] = contex.getResources().getColor(R.color.snow);
		}
		if (weatherIconUrl.contains("wsymbol_0012_heavy_snow_showers")){
			icon = contex.getResources().getIdentifier("heavy_snow",
					"drawable", contex.getPackageName());
			_strIcon = "W";
			_iconCharList[0] = contex.getResources().getString(R.string.base_cloud);
			_iconCharList[1] = contex.getResources().getString(R.string.snow);
			_iconCharList[2] = contex.getResources().getString(R.string.snow);
			
			_iconCharColorList[0] = contex.getResources().getColor(R.color.base_cloud);
			_iconCharColorList[1] = contex.getResources().getColor(R.color.snow);
			_iconCharColorList[2] = contex.getResources().getColor(R.color.snow);
		}
		if (weatherIconUrl.contains("wsymbol_0013_sleet_showers")){
			icon = contex.getResources().getIdentifier("showers", "drawable",
					contex.getPackageName());
			_strIcon = "R";
			_iconCharList[0] = contex.getResources().getString(R.string.base_cloud);
			_iconCharList[1] = contex.getResources().getString(R.string.sleet);
			_iconCharList[2] = contex.getResources().getString(R.string.sleet);
			
			_iconCharColorList[0] = contex.getResources().getColor(R.color.base_cloud);
			_iconCharColorList[1] = contex.getResources().getColor(R.color.sleet);
			_iconCharColorList[2] = contex.getResources().getColor(R.color.sleet);
		}
		if (weatherIconUrl.contains("wsymbol_0016_thundery_showers")){
			icon = contex.getResources().getIdentifier(
					"scattered_thunderstorms", "drawable",
					contex.getPackageName());
			_strIcon = "&";
			_iconCharList[0] = contex.getResources().getString(R.string.base_cloud);
			_iconCharList[1] = contex.getResources().getString(R.string.thunder);
			_iconCharList[2] = contex.getResources().getString(R.string.thunder);
			
			_iconCharColorList[0] = contex.getResources().getColor(R.color.base_thunder_cloud);
			_iconCharColorList[1] = contex.getResources().getColor(R.color.thunder);
			_iconCharColorList[2] = contex.getResources().getColor(R.color.thunder);
		}
		if (weatherIconUrl.contains("wsymbol_0017_cloudy_with_light_rain")){
			icon = contex.getResources().getIdentifier("rain", "drawable",
					contex.getPackageName());
			_strIcon = "R";
			_iconCharList[0] = contex.getResources().getString(R.string.base_cloud);
			_iconCharList[1] = contex.getResources().getString(R.string.rainy);
			_iconCharList[2] = contex.getResources().getString(R.string.rainy);
			
			_iconCharColorList[0] = contex.getResources().getColor(R.color.base_cloud);
			_iconCharColorList[1] = contex.getResources().getColor(R.color.rainy);
			_iconCharColorList[2] = contex.getResources().getColor(R.color.rainy);
		}
		if (weatherIconUrl.contains("wsymbol_0018_cloudy_with_heavy_rain")){
			icon = contex.getResources().getIdentifier("rain",//heavy_rain
					"drawable", contex.getPackageName());
			_strIcon = "8";
			_iconCharList[0] = contex.getResources().getString(R.string.base_cloud);
			_iconCharList[1] = contex.getResources().getString(R.string.rainy);
			_iconCharList[2] = contex.getResources().getString(R.string.rainy);
			
			_iconCharColorList[0] = contex.getResources().getColor(R.color.base_thunder_cloud);
			_iconCharColorList[1] = contex.getResources().getColor(R.color.rainy);
			_iconCharColorList[2] = contex.getResources().getColor(R.color.rainy);
		}
		if (weatherIconUrl.contains("wsymbol_0019_cloudy_with_light_snow")){
			icon = contex.getResources().getIdentifier("snow", "drawable",
					contex.getPackageName());
			_strIcon = "U";
			_iconCharList[0] = contex.getResources().getString(R.string.base_cloud);
			_iconCharList[1] = contex.getResources().getString(R.string.snow);
			_iconCharList[2] = contex.getResources().getString(R.string.snow);
			
			_iconCharColorList[0] = contex.getResources().getColor(R.color.base_cloud);
			_iconCharColorList[1] = contex.getResources().getColor(R.color.snow);
			_iconCharColorList[2] = contex.getResources().getColor(R.color.snow);
		}
		if (weatherIconUrl.contains("wsymbol_0020_cloudy_with_heavy_snow")){
			icon = contex.getResources().getIdentifier("heavy_snow",
					"drawable", contex.getPackageName());
			_strIcon = "#";
			_iconCharList[0] = contex.getResources().getString(R.string.base_cloud);
			_iconCharList[1] = contex.getResources().getString(R.string.snow);
			_iconCharList[2] = contex.getResources().getString(R.string.snow);
			
			_iconCharColorList[0] = contex.getResources().getColor(R.color.base_cloud);
			_iconCharColorList[1] = contex.getResources().getColor(R.color.snow);
			_iconCharColorList[2] = contex.getResources().getColor(R.color.snow);
		}
		if (weatherIconUrl.contains("wsymbol_0021_cloudy_with_sleet")){
			icon = contex.getResources().getIdentifier("drizzle", "drawable",
					contex.getPackageName());
			_strIcon = "Q";
			_iconCharList[0] = contex.getResources().getString(R.string.base_cloud);
			_iconCharList[1] = contex.getResources().getString(R.string.sleet);
			_iconCharList[2] = contex.getResources().getString(R.string.sleet);
			
			_iconCharColorList[0] = contex.getResources().getColor(R.color.base_cloud);
			_iconCharColorList[1] = contex.getResources().getColor(R.color.sleet);
			_iconCharColorList[2] = contex.getResources().getColor(R.color.sleet);
		}
		if (weatherIconUrl.contains("wsymbol_0024_thunderstorms")){
			icon = contex.getResources().getIdentifier("thunderstorms",
					"drawable", contex.getPackageName());
			_strIcon = "6";
			_iconCharList[0] = contex.getResources().getString(R.string.base_cloud);
			_iconCharList[1] = contex.getResources().getString(R.string.thunder);
			_iconCharList[2] = contex.getResources().getString(R.string.thunder);
			
			_iconCharColorList[0] = contex.getResources().getColor(R.color.base_thunder_cloud);
			_iconCharColorList[1] = contex.getResources().getColor(R.color.thunder);
			_iconCharColorList[2] = contex.getResources().getColor(R.color.thunder);
		}
		if (weatherIconUrl.contains("wsymbol_0025_light_rain_showers_night")){
			icon = contex.getResources().getIdentifier("night_drizzle",
					"drawable", contex.getPackageName());
			_strIcon = "7";
			_iconCharList[0] = contex.getResources().getString(R.string.base_cloud);
			_iconCharList[1] = contex.getResources().getString(R.string.drizzle);
			_iconCharList[2] = contex.getResources().getString(R.string.drizzle);
			
			_iconCharColorList[0] = contex.getResources().getColor(R.color.base_cloud);
			_iconCharColorList[1] = contex.getResources().getColor(R.color.drizzle);
			_iconCharColorList[2] = contex.getResources().getColor(R.color.drizzle);
		}
		if (weatherIconUrl.contains("wsymbol_0026_heavy_rain_showers_night")){
			icon = contex.getResources().getIdentifier("night_light_rain",
					"drawable", contex.getPackageName());
			_strIcon = "8";
			_iconCharList[0] = contex.getResources().getString(R.string.base_cloud);
			_iconCharList[1] = contex.getResources().getString(R.string.showers);
			_iconCharList[2] = contex.getResources().getString(R.string.night);
			
			_iconCharColorList[0] = contex.getResources().getColor(R.color.base_cloud);
			_iconCharColorList[1] = contex.getResources().getColor(R.color.showers);
			_iconCharColorList[2] = contex.getResources().getColor(R.color.night);
		}
		if (weatherIconUrl.contains("wsymbol_0027_light_snow_showers_night")){
			icon = contex.getResources().getIdentifier("snow", "drawable",
					contex.getPackageName());
			_strIcon = "$";
			_iconCharList[0] = contex.getResources().getString(R.string.base_cloud);
			_iconCharList[1] = contex.getResources().getString(R.string.snow);
			_iconCharList[2] = contex.getResources().getString(R.string.night);
			
			_iconCharColorList[0] = contex.getResources().getColor(R.color.base_cloud);
			_iconCharColorList[1] = contex.getResources().getColor(R.color.snow);
			_iconCharColorList[2] = contex.getResources().getColor(R.color.night);
		}
		if (weatherIconUrl.contains("wsymbol_0028_heavy_snow_showers_night")){
			icon = contex.getResources().getIdentifier("heavy_snow",
					"drawable", contex.getPackageName());
			_strIcon = "$";
			_iconCharList[0] = contex.getResources().getString(R.string.base_cloud);
			_iconCharList[1] = contex.getResources().getString(R.string.snow);
			_iconCharList[2] = contex.getResources().getString(R.string.night);
			
			_iconCharColorList[0] = contex.getResources().getColor(R.color.base_cloud);
			_iconCharColorList[1] = contex.getResources().getColor(R.color.snow);
			_iconCharColorList[2] = contex.getResources().getColor(R.color.night);
		}
		if (weatherIconUrl.contains("wsymbol_0029_sleet_showers_night")){
			icon = contex.getResources().getIdentifier("night_light_rain",
					"drawable", contex.getPackageName());
			_strIcon = "8";
			_iconCharList[0] = contex.getResources().getString(R.string.base_cloud);
			_iconCharList[1] = contex.getResources().getString(R.string.sleet);
			_iconCharList[2] = contex.getResources().getString(R.string.night);
			
			_iconCharColorList[0] = contex.getResources().getColor(R.color.base_cloud);
			_iconCharColorList[1] = contex.getResources().getColor(R.color.sleet);
			_iconCharColorList[2] = contex.getResources().getColor(R.color.night);
		}
		if (weatherIconUrl.contains("wsymbol_0032_thundery_showers_night")){
			icon = contex.getResources().getIdentifier(
					"scattered_thunderstorms", "drawable",
					contex.getPackageName());
			_strIcon = "&";
			_iconCharList[0] = contex.getResources().getString(R.string.base_cloud);
			_iconCharList[1] = contex.getResources().getString(R.string.thunder);
			_iconCharList[2] = contex.getResources().getString(R.string.night);
			
			_iconCharColorList[0] = contex.getResources().getColor(R.color.base_thunder_cloud);
			_iconCharColorList[1] = contex.getResources().getColor(R.color.thunder);
			_iconCharColorList[2] = contex.getResources().getColor(R.color.night);
		}
		if (weatherIconUrl.contains("wsymbol_0033_cloudy_with_light_rain_night")){
			icon = contex.getResources().getIdentifier("night_light_rain",
					"drawable", contex.getPackageName());
			_strIcon = "7";
			_iconCharList[0] = contex.getResources().getString(R.string.base_cloud);
			_iconCharList[1] = contex.getResources().getString(R.string.rainy);
			_iconCharList[2] = contex.getResources().getString(R.string.night);
			
			_iconCharColorList[0] = contex.getResources().getColor(R.color.base_cloud);
			_iconCharColorList[1] = contex.getResources().getColor(R.color.rainy);
			_iconCharColorList[2] = contex.getResources().getColor(R.color.night);
		}
		if (weatherIconUrl
				.contains("wsymbol_0034_cloudy_with_heavy_rain_night")){
			icon = contex.getResources().getIdentifier("night_rain",
					"drawable", contex.getPackageName());
			_strIcon = "8";
			_iconCharList[0] = contex.getResources().getString(R.string.base_cloud);
			_iconCharList[1] = contex.getResources().getString(R.string.rainy);
			_iconCharList[2] = contex.getResources().getString(R.string.night);
			
			_iconCharColorList[0] = contex.getResources().getColor(R.color.base_cloud);
			_iconCharColorList[1] = contex.getResources().getColor(R.color.rainy);
			_iconCharColorList[2] = contex.getResources().getColor(R.color.night);
		}
		if (weatherIconUrl
				.contains("wsymbol_0035_cloudy_with_light_snow_night")){
			icon = contex.getResources().getIdentifier("snow", "drawable",
					contex.getPackageName());
			_strIcon = "U";
			_iconCharList[0] = contex.getResources().getString(R.string.base_cloud);
			_iconCharList[1] = contex.getResources().getString(R.string.snow);
			_iconCharList[2] = contex.getResources().getString(R.string.night);
			
			_iconCharColorList[0] = contex.getResources().getColor(R.color.base_cloud);
			_iconCharColorList[1] = contex.getResources().getColor(R.color.snow);
			_iconCharColorList[2] = contex.getResources().getColor(R.color.night);
		}
		if (weatherIconUrl
				.contains("wsymbol_0036_cloudy_with_heavy_snow_night")){
			icon = contex.getResources().getIdentifier("heavy_snow",
					"drawable", contex.getPackageName());
			_strIcon = "W";
			_iconCharList[0] = contex.getResources().getString(R.string.base_cloud);
			_iconCharList[1] = contex.getResources().getString(R.string.snow);
			_iconCharList[2] = contex.getResources().getString(R.string.night);
			
			_iconCharColorList[0] = contex.getResources().getColor(R.color.base_cloud);
			_iconCharColorList[1] = contex.getResources().getColor(R.color.snow);
			_iconCharColorList[2] = contex.getResources().getColor(R.color.night);
		}
		if (weatherIconUrl.contains("wsymbol_0037_cloudy_with_sleet_night")){
			icon = contex.getResources().getIdentifier("night_rain",
					"drawable", contex.getPackageName());
			_strIcon = "7";
			_iconCharList[0] = contex.getResources().getString(R.string.base_cloud);
			_iconCharList[1] = contex.getResources().getString(R.string.sleet);
			_iconCharList[2] = contex.getResources().getString(R.string.night);
			
			_iconCharColorList[0] = contex.getResources().getColor(R.color.base_cloud);
			_iconCharColorList[1] = contex.getResources().getColor(R.color.sleet);
			_iconCharColorList[2] = contex.getResources().getColor(R.color.night);
		}
		if (weatherIconUrl.contains("wsymbol_0040_thunderstorms_night")){
			icon = contex.getResources().getIdentifier("thunderstorms",
					"drawable", contex.getPackageName());
			_strIcon = "6";
			_iconCharList[0] = contex.getResources().getString(R.string.base_cloud);
			_iconCharList[1] = contex.getResources().getString(R.string.thunder);
			_iconCharList[2] = contex.getResources().getString(R.string.night);
			
			_iconCharColorList[0] = contex.getResources().getColor(R.color.base_thunder_cloud);
			_iconCharColorList[1] = contex.getResources().getColor(R.color.thunder);
			_iconCharColorList[2] = contex.getResources().getColor(R.color.night);
		}

		if(icon == 0)
			icon = contex.getResources().getIdentifier("no_weather",
					"drawable", contex.getPackageName());
		
		if(description == null)
			description = "NO DESC";
		
		
		return new IconDesc(icon, description, _strIcon, _iconCharList, _iconCharColorList);
		//return new IconDesc(icon, description, _strIcon);

	}

	public WeatherExpertIcons() {
	}

}

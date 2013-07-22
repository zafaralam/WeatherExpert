package com.zafaralam.utils;

import android.content.Context;

public class WeatherExpertIcons {

	public class IconDesc {
		private int icon;
		private String desc;

		public IconDesc(int icon, String desc) {
			super();
			this.icon = icon;
			this.desc = desc;
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

	}

	public IconDesc getWeatherIcon(String weatherIconUrl, int weatherCode,
			Context contex) {
		String description = null;
		int icon = 0;
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

		if (weatherIconUrl.contains("wsymbol_0001_sunny"))
			icon = contex.getResources().getIdentifier("sunny", "drawable",
					contex.getPackageName());
		if (weatherIconUrl.contains("wsymbol_0002_sunny_intervals"))
			icon = contex.getResources().getIdentifier("fair", "drawable",
					contex.getPackageName());
		if (weatherIconUrl.contains("wsymbol_0003_white_cloud"))
			icon = contex.getResources().getIdentifier("cloudy", "drawable",
					contex.getPackageName());
		if (weatherIconUrl.contains("wsymbol_0004_black_low_cloud"))
			icon = contex.getResources().getIdentifier("cloudy", "drawable",
					contex.getPackageName());
		if (weatherIconUrl.contains("wsymbol_0006_mist"))
			icon = contex.getResources().getIdentifier("smoky", "drawable",
					contex.getPackageName());
		if (weatherIconUrl.contains("wsymbol_0007_fog"))
			icon = contex.getResources().getIdentifier("foggy", "drawable",
					contex.getPackageName());
		if (weatherIconUrl.contains("wsymbol_0008_clear_sky_night"))
			icon = contex.getResources().getIdentifier("night_fair",
					"drawable", contex.getPackageName());
		if (weatherIconUrl.contains("wsymbol_0009_light_rain_showers"))
			icon = contex.getResources().getIdentifier("showers", "drawable",
					contex.getPackageName());
		if (weatherIconUrl.contains("wsymbol_0010_heavy_rain_showers"))
			icon = contex.getResources().getIdentifier("rain", "drawable",
					contex.getPackageName());
		if (weatherIconUrl.contains("wsymbol_0011_light_snow_showers"))
			icon = contex.getResources().getIdentifier("snow", "drawable",
					contex.getPackageName());
		if (weatherIconUrl.contains("wsymbol_0012_heavy_snow_showers"))
			icon = contex.getResources().getIdentifier("heavy_snow",
					"drawable", contex.getPackageName());
		if (weatherIconUrl.contains("wsymbol_0013_sleet_showers"))
			icon = contex.getResources().getIdentifier("showers", "drawable",
					contex.getPackageName());
		if (weatherIconUrl.contains("wsymbol_0016_thundery_showers"))
			icon = contex.getResources().getIdentifier(
					"scattered_thunderstorms", "drawable",
					contex.getPackageName());
		if (weatherIconUrl.contains("wsymbol_0017_cloudy_with_light_rain"))
			icon = contex.getResources().getIdentifier("rain", "drawable",
					contex.getPackageName());
		if (weatherIconUrl.contains("wsymbol_0018_cloudy_with_heavy_rain"))
			icon = contex.getResources().getIdentifier("rain",//heavy_rain
					"drawable", contex.getPackageName());
		if (weatherIconUrl.contains("wsymbol_0019_cloudy_with_light_snow"))
			icon = contex.getResources().getIdentifier("snow", "drawable",
					contex.getPackageName());
		if (weatherIconUrl.contains("wsymbol_0020_cloudy_with_heavy_snow"))
			icon = contex.getResources().getIdentifier("heavy_snow",
					"drawable", contex.getPackageName());
		if (weatherIconUrl.contains("wsymbol_0021_cloudy_with_sleet"))
			icon = contex.getResources().getIdentifier("drizzle", "drawable",
					contex.getPackageName());
		if (weatherIconUrl.contains("wsymbol_0024_thunderstorms"))
			icon = contex.getResources().getIdentifier("thunderstorms",
					"drawable", contex.getPackageName());
		if (weatherIconUrl.contains("wsymbol_0025_light_rain_showers_night"))
			icon = contex.getResources().getIdentifier("night_drizzle",
					"drawable", contex.getPackageName());
		if (weatherIconUrl.contains("wsymbol_0026_heavy_rain_showers_night"))
			icon = contex.getResources().getIdentifier("night_light_rain",
					"drawable", contex.getPackageName());
		if (weatherIconUrl.contains("wsymbol_0027_light_snow_showers_night"))
			icon = contex.getResources().getIdentifier("snow", "drawable",
					contex.getPackageName());
		if (weatherIconUrl.contains("wsymbol_0028_heavy_snow_showers_night"))
			icon = contex.getResources().getIdentifier("heavy_snow",
					"drawable", contex.getPackageName());
		if (weatherIconUrl.contains("wsymbol_0029_sleet_showers_night"))
			icon = contex.getResources().getIdentifier("night_light_rain",
					"drawable", contex.getPackageName());
		if (weatherIconUrl.contains("wsymbol_0032_thundery_showers_night"))
			icon = contex.getResources().getIdentifier(
					"scattered_thunderstorms", "drawable",
					contex.getPackageName());
		if (weatherIconUrl
				.contains("wsymbol_0033_cloudy_with_light_rain_night"))
			icon = contex.getResources().getIdentifier("night_light_rain",
					"drawable", contex.getPackageName());
		if (weatherIconUrl
				.contains("wsymbol_0034_cloudy_with_heavy_rain_night"))
			icon = contex.getResources().getIdentifier("night_rain",
					"drawable", contex.getPackageName());
		if (weatherIconUrl
				.contains("wsymbol_0035_cloudy_with_light_snow_night"))
			icon = contex.getResources().getIdentifier("snow", "drawable",
					contex.getPackageName());
		if (weatherIconUrl
				.contains("wsymbol_0036_cloudy_with_heavy_snow_night"))
			icon = contex.getResources().getIdentifier("heavy_snow",
					"drawable", contex.getPackageName());
		if (weatherIconUrl.contains("wsymbol_0037_cloudy_with_sleet_night"))
			icon = contex.getResources().getIdentifier("night_rain",
					"drawable", contex.getPackageName());
		if (weatherIconUrl.contains("wsymbol_0040_thunderstorms_night"))
			icon = contex.getResources().getIdentifier("thunderstorms",
					"drawable", contex.getPackageName());

		if(icon == 0)
			icon = contex.getResources().getIdentifier("no_weather",
					"drawable", contex.getPackageName());
		
		if(description == null)
			description = "NO DESC";
		
		return new IconDesc(icon, description);

	}

	public WeatherExpertIcons() {
	}

}

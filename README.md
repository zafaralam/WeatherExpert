# An Android app project

This is a weather application and is still a work in progress. 

-> Features implemented
	- Add favorites
	- Delete favorites
	- select default location
	- Current location using location service
	- Current location using the ip address
	- Search locations
	- 5 day weather
	- Auto refresh data using background service
	- font based weather icons
	- show current weather in the favorites list
	- paged data for all favorite location.

-> Features to be implement
	- Add sunrise and sunset timing.
	- Add about page data.
	- When search show the current countries results at top.
	- Add savedInstance state to WeatherFragment.
	- Who was born today.
	- Add dots to pager fragment to show the current pager.
	- When clicking refresh call the service to reload all data.
	- Add low resolution versions of weather details fragment.

Google Console Sender id link
https://code.google.com/apis/console/?pli=1#project:358661061117:services
Sender Id: 358661061117

API Key: AIzaSyCwduQ42lfgwf1eBgg2MKFu7CULuMoilNU

Database Details:
$mysql_host = "mysql10.000webhost.com";
$mysql_database = "a3382353_gcm";
$mysql_user = "a3382353_gcm";
$mysql_password = "abc123";

/*****************************************************************************/
#Inner working of the WeatherFragment.java
	- A variable(main_location) is used to hold the	location details.
	- this could either be a delimited variable or it could have a string with the value of "Current".
	- If delimited then it can have 3 or 4 parts.
		- If 3 parts, then they are x[0] = {location name}, x[1] = {latitude}, x[2] = {longitude}.
		- If 4 parts x[3] = {favId}
	- When the fragment starts, the main_location variable is checked for its content, if it has a favId then data is loaded from the database. If data is not avaiable from the db then details are loaded through the api.
	- When the favId is not provided, data is loaded from the based on the lat and lng.
	- if "Current" is the content of the main_location variable then the location is either obtained base on geo location or the ip address.



/*
 * Tasks to completed on activity start
 */
package com.zafaralam.weatherexpert;

import android.content.Context;
import android.content.SharedPreferences;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.InflateException;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingFragmentActivity;
import com.zafaralam.utils.NetworkDetails;
import com.zafaralam.weatherexpert.contentprovider.WeatherExpertAdapter;
import com.zafaralam.weatherexpert.contentprovider.WeatherExpertContract.FavouritesEnrty;
import com.zafaralam.weatherexpert.service.WeatherDetailsUpdateService;

public class BaseActivity extends SlidingFragmentActivity {

	private Fragment fragment;
	private final String TAG = "Base Activity";
	private static final int SHOW_PREFERENCES = 1;

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.i(TAG, "onResume()");

		/*
		 * Check what type of fragment is it
		 */

		// if(fragment instanceof WeatherFragment)
		// refreshData(((WeatherFragment) fragment).getMain_location());
		//
		// if(fragment instanceof AddLocationFragment)
		// addLocation();
		// if(fragment instanceof FavouritesFragment)
		// displayFavourites();

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		try {
			super.onCreate(savedInstanceState);
			// setting up the main content area.

			setContentView(R.layout.main_content_frame);

			// setup sliding menu
			if (findViewById(R.id.menu_frame) == null) {
				setBehindContentView(R.layout.menu_frame);
				getSlidingMenu().setSlidingEnabled(true);
				getSlidingMenu().setTouchModeAbove(
						SlidingMenu.TOUCHMODE_FULLSCREEN);
				// show home as up so we can toggle
				getActionBar().setDisplayHomeAsUpEnabled(true);
			}

			// if(savedInstanceState != null){
			// fragment =
			// getSupportFragmentManager().getFragment(savedInstanceState,
			// "mContent");
			// }

			SlidingMenu sm = getSlidingMenu();
			// sm.setMode(SlidingMenu.RIGHT);
			sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
			sm.setShadowWidthRes(R.dimen.shadow_width);
			sm.setShadowDrawable(R.drawable.shadow);
			sm.setBehindScrollScale(0.75f);
			sm.setFadeDegree(0.30f);
			setSlidingActionBarEnabled(false);

			try {
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.menu_frame, new MenuListFragment())
						.commit();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

			/*
			 * 
			 * //The below code is only for testing purposes
			 * 
			 * String ip =
			 * NetworkDetails.getLocalIpAddress(getApplicationContext()); if(ip
			 * != null){
			 * 
			 * Log.d(TAG, "IP Adress: " + ip); }
			 */

			startService(new Intent(this, WeatherDetailsUpdateService.class));
			// boolean isDefaultLocation = false;
			String defaultLocationId = null;

			SharedPreferences pref = PreferenceManager
					.getDefaultSharedPreferences(getApplicationContext());
			// getApplication().getSharedPreferences(PreferencesActivity.USER_PREFERENCES,
			// (Integer) null);
			String defaultPage = pref.getString(
					PreferencesActivity.DEFAULT_PAGE, "");
			if (defaultPage.compareToIgnoreCase("Default Location") == 0) {
				defaultLocationId = pref.getString(
						PreferencesActivity.DEFAULT_LOCATION, "");
				if (defaultLocationId != "") {
					loadDefaultLocation(defaultLocationId);
				}
				Log.d(TAG, defaultLocationId);

			} else if (defaultPage.compareToIgnoreCase("Favorites") == 0) {
				displayFavourites();
			} else if (defaultPage.compareToIgnoreCase("Search Location") == 0) {
				addLocation();
			} else
				refreshData(null);
			//
			//
		} catch (InflateException e) {
			// TODO: handle exception
			Log.d(TAG, "Unable To infalet in BaseActivity\n" + e.toString());
		} catch (Exception e) {
			// TODO: handle exception
			Log.d(TAG, e.toString());
		}

	}

	private void loadDefaultLocation(String defaultLocationId) {
		// TODO Auto-generated method stub
		WeatherExpertAdapter dbHelper = new WeatherExpertAdapter(
				getApplicationContext());
		dbHelper.open();
		String[] projection = { FavouritesEnrty.KEY_CITYNAME,
				FavouritesEnrty.KEY_LAT, FavouritesEnrty.KEY_LNG };

		String where = FavouritesEnrty.KEY_FAV_ID + " = " + defaultLocationId;

		Cursor c = dbHelper.query(FavouritesEnrty.TABLE_NAME, projection,
				where, null, null, null, null);
		dbHelper.close();
		if (c != null) {
			if (c.getCount() > 0)
				refreshData(c.getString(0) + ";" + c.getString(1) + ";"
						+ c.getString(2));
			else
				refreshData(null);
		} else {
			refreshData(null);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		// menu.add(Menu.NONE, ParserType.ANDROID_SAX.ordinal(),
		// ParserType.ANDROID_SAX.ordinal(), R.string.android_sax);
		// menu.add(Menu.NONE, ParserType.SAX.ordinal(),
		// ParserType.SAX.ordinal(),
		// R.string.sax);
		// menu.add(Menu.NONE, ParserType.DOM.ordinal(),
		// ParserType.DOM.ordinal(),
		// R.string.dom);
		// menu.add(Menu.NONE, ParserType.XML_PULL.ordinal(),
		// ParserType.XML_PULL.ordinal(), R.string.pull);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		super.onMenuItemSelected(featureId, item);
		switch (item.getItemId()) {
		case android.R.id.home:
			hideKeyboard(getSlidingMenu());
			toggle();
			return true;
		case R.id.menu_settings:
			displaySetting();
			return true;
			/*
			 * case R.id.menu_search: Toast.makeText(getApplicationContext(),
			 * "You have selected Search", Toast.LENGTH_SHORT).show(); return
			 * false;
			 */
		case R.id.menu_refresh:
			// loadFeed(ParserType.XML_PULL);
			refreshData(null);
			return false;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void hideKeyboard(View view) {
		// TODO Auto-generated method stub
		InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		in.hideSoftInputFromWindow(view.getWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);
	}

	private void refreshData(String location) {
		// TODO Auto-generated method stub

		if (location == null) {
			if (fragment instanceof WeatherFragment) {
				location = ((WeatherFragment) fragment).getMain_location();
			} else {
				location = "Current";
			}
		}

		/*
		 * if (location == null && fragment != null && !(fragment instanceof
		 * WeatherFragment)) { location = "Current"; Log.d(TAG, "1:" +
		 * location); } else {
		 * 
		 * if (location == null && fragment != null && (fragment instanceof
		 * WeatherFragment)) { location = "Current"; Log.d(TAG, "2:" +
		 * location); } else {
		 * 
		 * if (location != null && fragment != null && (fragment instanceof
		 * WeatherFragment)) { if (location.compareTo("Current") != 0) location
		 * = ((WeatherFragment) fragment) .getMain_location(); Log.d(TAG, "3:" +
		 * location); } } } String old_location = null; if (fragment instanceof
		 * WeatherFragment) { old_location = ((WeatherFragment)
		 * fragment).getMain_location(); // Log.d(TAG,old_location); }
		 * Log.d(TAG, "final:" + location);
		 */
		// else
		// {location="Current";}
		//
		// if(fragment != null && location.compareTo("Current")!= 0){
		//
		// //fragment = null;
		// }

		fragment = new WeatherFragment(location);
		replaceFragment(fragment);
	}

	private void replaceFragment(Fragment fragment) {
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.inner_content, fragment).commit();
	}

	public void switchContent(final String menuItemName) {
		// System.out.println(menuItemName);
		// removeFragment();
		// getSlidingMenu().showContent();
		// Log.i(TAG,String.valueOf(getSlidingMenu().isShown()));
		// if(getSlidingMenu().isShown())
		// toggle();
		showContent();

		Handler h = new Handler();
		h.postDelayed(new Runnable() {
			public void run() {
				if (menuItemName.compareTo("Current Location") == 0)
					refreshData("Current");
				if (menuItemName.compareTo("Search Location") == 0) {
					// System.out.println("Inside Switch Content");
					addLocation();
				}
				if (menuItemName.compareTo("Favorites") == 0) {
					// hideKeyboard(getCurrentFocus());
					displayFavourites();
				}

				if (menuItemName.compareTo("Settings") == 0) {
					displaySetting();

				}

				if (menuItemName.compareToIgnoreCase("About") == 0) {
					displayAbout();
				}

			}

		}, 500);
	}

	private void displayAbout() {
		// TODO Auto-generated method stub
		if (fragment != null) {
			fragment = null;
		}
		fragment = new PagerFragment(this);

		if (fragment != null)
			replaceFragment(fragment);
		else
			Log.d(TAG, "Fragment not created");
	}

	protected void displayFavourites() {
		// TODO Auto-generated method stub

		if (fragment != null) {
			fragment = null;
		}

		fragment = new FavouritesFragment();

		if (fragment != null)
			replaceFragment(fragment);
		else
			Log.d(TAG, "Fragment not created");
	}

	private void displaySetting() {
		// TODO Auto-generated method stub

		// Toast.makeText(getApplicationContext(),
		// "To be implemented in upcoming version", Toast.LENGTH_SHORT).show();

		Intent i = new Intent(this, PreferencesActivity.class);
		startActivityForResult(i, SHOW_PREFERENCES);

	}

	public void switchLocation(String location) {
		// TODO Auto-generated method stub
		refreshData(location);
	}

	private void addLocation() {
		// TODO Auto-generated method stub
		try {
			if (fragment != null) {
				fragment = null;
			}

			fragment = new AddLocationFragment();

			replaceFragment(fragment);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}
}

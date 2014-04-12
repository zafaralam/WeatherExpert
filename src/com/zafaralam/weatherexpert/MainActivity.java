package com.zafaralam.weatherexpert;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private final String TAG = "Main Activity";
	private Fragment fragment; 
	private DrawerLayout mDrawerLayout;
	    private ListView mDrawerList;
	    private ActionBarDrawerToggle mDrawerToggle;

	    private CharSequence mDrawerTitle;
	    private CharSequence mTitle;
	    private String[] mMenuTitles;

	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.main_activity);

	        mTitle = mDrawerTitle = getTitle();
	        mMenuTitles = getResources().getStringArray(R.array.menus);
	        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
	        mDrawerList = (ListView) findViewById(R.id.left_drawer);

	        // set a custom shadow that overlays the main content when the drawer opens
	        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
	        // set up the drawer's list view with items and click listener
	        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
	                R.layout.drawer_list_item, mMenuTitles));
	        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

	        // enable ActionBar app icon to behave as action to toggle nav drawer
	        getActionBar().setDisplayHomeAsUpEnabled(true);
	        getActionBar().setHomeButtonEnabled(true);

	        // ActionBarDrawerToggle ties together the the proper interactions
	        // between the sliding drawer and the action bar app icon
	        mDrawerToggle = new ActionBarDrawerToggle(
	                this,                  /* host Activity */
	                mDrawerLayout,         /* DrawerLayout object */
	                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
	                R.string.drawer_open,  /* "open drawer" description for accessibility */
	                R.string.drawer_close  /* "close drawer" description for accessibility */
	                ) {
	            public void onDrawerClosed(View view) {
	                getActionBar().setTitle(mTitle);
	                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
	            }

	            public void onDrawerOpened(View drawerView) {
	                getActionBar().setTitle(mDrawerTitle);
	                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
	            }
	        };
	        mDrawerLayout.setDrawerListener(mDrawerToggle);
	        refreshData(null);

	        if (savedInstanceState == null) {
	            selectItem(0);
	        }
	    }

	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        MenuInflater inflater = getMenuInflater();
	        inflater.inflate(R.menu.activity_main, menu);
	        return super.onCreateOptionsMenu(menu);
	    }

	    /* Called whenever we call invalidateOptionsMenu() */
	    @Override
	    public boolean onPrepareOptionsMenu(Menu menu) {
	        // If the nav drawer is open, hide action items related to the content view
	        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
	        menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
	        return super.onPrepareOptionsMenu(menu);
	    }

	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	         // The action bar home/up action should open or close the drawer.
	         // ActionBarDrawerToggle will take care of this.
	        if (mDrawerToggle.onOptionsItemSelected(item)) {
	            return true;
	        }
	        // Handle action buttons
	        switch(item.getItemId()) {
	        case R.id.action_websearch:
	            // create intent to perform web search for this planet
	            Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
	            intent.putExtra(SearchManager.QUERY, getActionBar().getTitle());
	            // catch event that there's no activity to handle intent
	            if (intent.resolveActivity(getPackageManager()) != null) {
	                startActivity(intent);
	            } else {
	                Toast.makeText(this, R.string.app_not_available, Toast.LENGTH_LONG).show();
	            }
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	        }
	    }

	    /* The click listner for ListView in the navigation drawer */
	    private class DrawerItemClickListener implements ListView.OnItemClickListener {
	        @Override
	        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	            selectItem(position);
	        }
	    }

	    private void selectItem(int position) {
	        // update the main content by replacing fragments
	    	String menuItemSelected = mMenuTitles[position];
	    	closeDrawerAfterItemSelection(position);
	    	if(menuItemSelected.compareTo("Current Location") == 0){
	    		
	    	}else if(menuItemSelected.compareTo("Search Location") == 0){
	    		if(!(fragment instanceof AddLocationFragment))
					addLocation();
	    		
	    	}else if(menuItemSelected.compareTo("Favorites") == 0){
	    		if(!(fragment instanceof FavouritesFragment))
	    			displayFavourites();
	    		
//	    	}else if(menuItemSelected.compareTo("Settings") == 0){
//	    		displaySetting();
	    	}else if(menuItemSelected.compareTo("About") == 0){
//	    		if(!(fragment instanceof PagerFragment))
//					displayAbout();
	    		
	    	}
//	        Fragment fragment = new PlanetFragment();
//	        Bundle args = new Bundle();
//	        args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
//	        fragment.setArguments(args);

	        FragmentManager fragmentManager = getFragmentManager();
	        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

	        // update selected item and title, then close the drawer
	        
	    }
	    
	    private void closeDrawerAfterItemSelection(int position){
	    	mDrawerList.setItemChecked(position, true);
	        setTitle(mMenuTitles[position]);
	        mDrawerLayout.closeDrawer(mDrawerList);
	    }

	    @Override
	    public void setTitle(CharSequence title) {
	        mTitle = title;
	        getActionBar().setTitle(mTitle);
	    }

	    /**
	     * When using the ActionBarDrawerToggle, you must call it during
	     * onPostCreate() and onConfigurationChanged()...
	     */

	    @Override
	    protected void onPostCreate(Bundle savedInstanceState) {
	        super.onPostCreate(savedInstanceState);
	        // Sync the toggle state after onRestoreInstanceState has occurred.
	        mDrawerToggle.syncState();
	    }

	    @Override
	    public void onConfigurationChanged(Configuration newConfig) {
	        super.onConfigurationChanged(newConfig);
	        // Pass any configuration change to the drawer toggls
	        mDrawerToggle.onConfigurationChanged(newConfig);
	    }
	    
	    private void replaceFragment(Fragment fragment) {
	    	FragmentManager fragmentManager = getFragmentManager();
	        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
		}
	    
	    public void switchContent(String menuItem){
	    	for(int position = 0; position < mMenuTitles.length; position++){
	    		if(mMenuTitles[position].compareTo(menuItem) == 0)
	    			selectItem(position);
	    	}
	    }
	    
	    public void switchLocation(String location) {
			// TODO Auto-generated method stub
			refreshData(location);
		}
	    
	    private void refreshData(String location) {
			// TODO Auto-generated method stub

			//Log.d(TAG,location);
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
	    
	    private void displayAbout() {
			if (fragment != null){
				fragment = null;
			}
			
			//fragment = new PagerFragment(this);
			//replaceFragment(fragment);
			Log.d(TAG, "PagerFragment not created");
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
}

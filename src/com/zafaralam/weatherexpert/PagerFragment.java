package com.zafaralam.weatherexpert;

import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

@SuppressLint("ValidFragment")
public class PagerFragment extends Fragment {

	SlidingFragmentActivity activity;
	ViewPager vp;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.pager_frag, container, false);
		vp = (ViewPager) v.findViewById(R.id.pager);
		// vp.setId("VP".hashCode());
		vp.setAdapter(new FavWeatherDetailsPagerAdapter(
				getChildFragmentManager(), activity.getApplicationContext()));
		// setContentView(vp);

		vp.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageScrollStateChanged(int arg0) {
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageSelected(int position) {
				switch (position) {
				case 0:
					activity.getSlidingMenu().setTouchModeAbove(
							SlidingMenu.TOUCHMODE_FULLSCREEN);
					break;
				default:
					activity.getSlidingMenu().setTouchModeAbove(
							SlidingMenu.TOUCHMODE_MARGIN);
					break;
				}
			}

		});

		vp.setCurrentItem(0);
		activity.getSlidingMenu().setTouchModeAbove(
				SlidingMenu.TOUCHMODE_FULLSCREEN);
		return v;
	}

	public PagerFragment(SlidingFragmentActivity activity) {
		super();
		this.activity = activity;
	}

	public PagerFragment() {
		super();
		// TODO Auto-generated constructor stub
	}

}

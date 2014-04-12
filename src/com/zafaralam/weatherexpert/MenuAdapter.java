package com.zafaralam.weatherexpert;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MenuAdapter extends BaseAdapter {
	
	private List<MenuItem> menuItemList;

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return menuItemList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return menuItemList.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null){
			LayoutInflater inflator = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflator.inflate(R.layout.menu_item, parent,false);
			
		}
		return convertView;
	}

}

package com.zafaralam.weatherexpert;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MenuListFragment 
//extends ListFragment
{

//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		return inflater.inflate(R.layout.menu_list, null);
//		
//	}
//	
//	@SuppressLint("Recycle")
//	public void onActivityCreated(Bundle savedInstanceState) {
//		super.onActivityCreated(savedInstanceState);
//		
//		String[] menus = getResources().getStringArray(R.array.menus);
//		TypedArray menu_img = getResources().obtainTypedArray(R.array.menus_img);
//		int menu_len = menus.length;
//		MenuAdapter adapter = new MenuAdapter(getActivity());
//		int menu_img_res;
//		
//		for(int i = 0; i < menu_len; i++){
//			String menu_str = menus[i];
//			menu_img_res = menu_img.getResourceId(i, -1);
//			
//			adapter.add(new MenuItem(menu_str, menu_img_res));
//		}
//		setListAdapter(adapter);
//	}
//	
//	
//	
//	@Override
//	public void onListItemClick(ListView l, View v, int position, long id) {
//		// TODO Auto-generated method stub
//		super.onListItemClick(l, v, position, id);
//		MenuItem mi = (MenuItem)l.getItemAtPosition(position);
//		//Toast.makeText(getActivity(), mi.getMenuName(), Toast.LENGTH_LONG).show();
//		switchFragment(mi);
//	}
//
//	private void switchFragment(MenuItem m){
//		if(getActivity() == null){
//			return;
//		}
//		
//		if(getActivity() instanceof BaseActivity){
//			BaseActivity ba = (BaseActivity) getActivity();
//			ba.switchContent(m.getMenuName());
//		}
//		
//	}
//
//
//	private class MenuItem {
//		private String menuName;
//		private int iconRes;
//		
//		public String getMenuName() {
//			return menuName;
//		}
//
//		public void setMenuName(String menuName) {
//			this.menuName = menuName;
//		}
//
//		public int getIconRes() {
//			return iconRes;
//		}
//
//		public void setIconRes(int iconRes) {
//			this.iconRes = iconRes;
//		}
//
//		public MenuItem(String menuName, int iconRes) {
//			super();
//			this.menuName = menuName;
//			this.iconRes = iconRes;
//		}
//	}
//	
//	public class MenuAdapter extends ArrayAdapter<MenuItem>{
//
//		public MenuAdapter(Context context){
//			super(context,0);
//		}
//		
//		public MenuAdapter(Context context, int textViewResourceId) {
//			super(context, textViewResourceId);
//			// TODO Auto-generated constructor stub
//		}
//		
//		@Override
//		public View getView(int position, View convertView, ViewGroup parent) {
//			// TODO Auto-generated method stub
//			
//			if (convertView == null) {
//				convertView = LayoutInflater.from(getContext()).inflate(R.layout.row, null);
//			}
//			ImageView icon = (ImageView) convertView.findViewById(R.id.row_icon);
//			icon.setImageResource(getItem(position).iconRes);
//			TextView title = (TextView) convertView.findViewById(R.id.row_title);
//			title.setText(getItem(position).menuName);
//
//			return convertView;
//			//return super.getView(position, convertView, parent);
//		}
//		
//		
//	}

}

package com.zafaralam.weatherexpert;

public class MenuItem {
	private String menuName;
	private int menuIcon;
	
	public MenuItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MenuItem(String menuName, int menuIcon) {
		super();
		this.menuName = menuName;
		this.menuIcon = menuIcon;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public int getMenuIcon() {
		return menuIcon;
	}

	public void setMenuIcon(int menuIcon) {
		this.menuIcon = menuIcon;
	}
	
	

}

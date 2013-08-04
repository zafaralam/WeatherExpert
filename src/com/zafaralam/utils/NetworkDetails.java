package com.zafaralam.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import android.content.Context;
import android.net.ConnectivityManager;
import android.text.format.Formatter;
import android.util.Log;

public class NetworkDetails {

	public final static int WIFI = 1;
	public final static int MOBILE = 0;
	public final static int NETWORK_NOT_AVALABLE = 2;
	public final static String TAG = "NetworkDetails";

	public static boolean isNetworkAvailable(Context ctx) {
		ConnectivityManager connMgr = (ConnectivityManager) ctx
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected()
				|| connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
						.isConnected()) {
			return true;
		}

		return false;
	}

	public static int whatNetworkAvailable(Context ctx) {

		ConnectivityManager connMgr = (ConnectivityManager) ctx
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected())
			return WIFI;

		if (connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
				.isConnected())
			return MOBILE;

		return NETWORK_NOT_AVALABLE;
	}
	
	public static String getLocalIpAddress(Context ctx) {
		try {
	        for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
	            NetworkInterface intf = en.nextElement();
	            for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
	                InetAddress inetAddress = enumIpAddr.nextElement();
	                if (!inetAddress.isLoopbackAddress()) {
	                	Log.d(TAG, Formatter.formatIpAddress(inetAddress.getHostAddress().hashCode()));
	                    return Formatter.formatIpAddress(inetAddress.getHostAddress().hashCode());
	                }
	            }
	        }
	    } catch (SocketException ex) {
	        Log.e(TAG, ex.toString());
	    }
	    return null;
//	    try {
//	        for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
//	            NetworkInterface intf = en.nextElement();
//	            for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
//	                InetAddress inetAddress = enumIpAddr.nextElement();
//	                Log.d(TAG, inetAddress.getHostAddress().);
//	                if (!inetAddress.isLoopbackAddress()) {
//	                    String ip = Formatter.formatIpAddress(inetAddress.getHostAddress().hashCode());
//	                	Log.i(TAG, "***** IP="+ ip);
//	                    return ip;
//	                }
//	            }
//	        }
//	    } catch (SocketException ex) {
//	        Log.e(TAG, ex.toString());
//	    }
//	    return null;
	}

}

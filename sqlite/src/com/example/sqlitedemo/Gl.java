/**
 * Gl.java
 * com.hiwifi
 * hiwifiKoala
 * shunping.liu create at 20142014年8月1日下午12:18:29
 */
package com.example.sqlitedemo;

import android.app.Application;
import android.content.Context;

/**
 * @author shunping.liu@hiwifi.tw
 *
 */
public class Gl extends Application {

	
	/**
	 * 
	 */
	public Gl() {
	}
	
	
	@Override
	public void onCreate() {
		super.onCreate();
		context = getApplicationContext();
	}


	public static Context context;
	public static Context getContext()
	{
		return context;
	}

	public static String getVersioncode()
	{
		return "33";
	}
	
	public static String getVersionName()
	{
		return "4.1.2";
	}
}

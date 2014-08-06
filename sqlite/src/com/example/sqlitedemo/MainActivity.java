package com.example.sqlitedemo;

import java.util.concurrent.CountDownLatch;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.R.integer;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.provider.SyncStateContract.Constants;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
		
		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
//			testPureVersion();
//			testAddLock();
			testCountedVersion();
		}
		
		public void testAddLock()
		{
			KeyValueDbMgr.shareInstance().clear();
			Log.e("testAddLock", "before test:"+KeyValueDbMgr.shareInstance().total()+"");
			long startTime = System.currentTimeMillis();
			final int testAmount = 100;
			final CountDownLatch latch = new CountDownLatch(testAmount);
			for (int i = 0; i < testAmount; i++) {
				final int j = i;
				new Thread(new Runnable() {
					@Override
					public void run() {
						KeyValueModel.setIntValue(j+"", j);
						latch.countDown();
					}
				}).start();
			}
			try {
				latch.await();
				Log.e("testAddLock", "after test:"+KeyValueDbMgr.shareInstance().total()+"");
				Log.e("testAddLock", "use time " + (System.currentTimeMillis()-startTime) + "毫秒");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		public void testPureVersion()
		{
			PureKeyValueDbMgr.shareInstance().clear();
			Log.e("testPureVersion", "before test:"+PureKeyValueDbMgr.shareInstance().total()+"");
			long startTime = System.currentTimeMillis();
			final int testAmount = 100;
			final CountDownLatch latch = new CountDownLatch(testAmount);
			for (int i = 0; i < testAmount; i++) {
				final int j = i;
				new Thread(new Runnable() {
					@Override
					public void run() {
						PureKeyValueModel.setIntValue(j+"", j);
						latch.countDown();
					}
				}).start();
			}
			try {
				latch.await();
				Log.e("testPureVersion", "after test:"+PureKeyValueDbMgr.shareInstance().total()+"");
				Log.e("testPureVersion", "use time " + (System.currentTimeMillis()-startTime) + "毫秒");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		public void testCountedVersion()
		{
			CountedKeyValueDbMgr.shareInstance().clear();
			Log.e("testCountedVersion", "before test:"+CountedKeyValueDbMgr.shareInstance().total()+"");
			long startTime = System.currentTimeMillis();
			final int testAmount = 100;
			final CountDownLatch latch = new CountDownLatch(testAmount);
			for (int i = 0; i < testAmount; i++) {
				final int j = i;
				new Thread(new Runnable() {
					@Override
					public void run() {
							CountedKeyValueModel.setIntValue(j+"", j);
							latch.countDown();
					}
				}).start();
			}
			try {
				latch.await();
				Log.e("testCountedVersion", "after test:"+CountedKeyValueDbMgr.shareInstance().total()+"");
				Log.e("testCountedVersion", "use time " + (System.currentTimeMillis()-startTime) + "毫秒");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}

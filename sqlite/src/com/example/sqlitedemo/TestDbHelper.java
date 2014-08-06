package com.example.sqlitedemo;

import java.util.ArrayList;
import java.util.List;



import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TestDbHelper extends SQLiteOpenHelper {

	private static final String DB_NAME = "AccessPoints.db";
	private static final int DB_VERSION = 1;
	public final static byte[] DB_Lock = new byte[0];
	private List<Table> tableList;

	public List<Table> getTables() {
		if (tableList == null) {
			tableList = new ArrayList<Table>();
			tableList.add(KeyValueDbMgr.shareInstance());
			tableList.add(PureKeyValueDbMgr.shareInstance());
			tableList.add(CountedKeyValueDbMgr.shareInstance());
		}
		return tableList;
	}
	public TestDbHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// db.beginTransaction();
		for (Table table : getTables()) {
			table.onCreate(db);
		}
		// db.endTransaction();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		for (Table table : getTables()) {
			table.onUpgrade(db, oldVersion, newVersion);
		}
	}

	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		for (Table table : getTables()) {
			table.onDowngrade(db, oldVersion, newVersion);
		}
	}

	/*****/
	int openCount = 0;
	SQLiteDatabase dbInstance = null;
	public synchronized SQLiteDatabase getDatabase() {
		if(dbInstance==null)
		{
			dbInstance = super.getWritableDatabase();
		}
		openCount++;
		Log.e("debug", ""+openCount);
		return dbInstance;
	}
	
	public synchronized void closeDb() {
		openCount--;
		Log.e("debug", ""+openCount);
		if(openCount<=0)
		{
			dbInstance.close();
			dbInstance = null;
		}
	}

}

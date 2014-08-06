package com.example.sqlitedemo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CountedKeyValueDbMgr implements Table {

	private static final String TAG = CountedKeyValueDbMgr.class.getSimpleName();

	private static TestDbHelper mDbHelper;
	private static CountedKeyValueDbMgr instance;

	public static class CountedKeyValueTable {
		public static final String TBL_NAME = "CountedKeyValueConfig";
		public static final String FLD_key = "key";
		public static final String FLD_value = "value";
		public static final String CREATESQL_STRING = "create table "
				+ TBL_NAME + "(" + FLD_key + " varchar(256) ," + FLD_value
				+ " TEXT" + ")";
	}

	private CountedKeyValueDbMgr() {
		mDbHelper = new TestDbHelper(Gl.getContext());
	}

	public static CountedKeyValueDbMgr shareInstance() {
		if (instance == null) {
			instance = new CountedKeyValueDbMgr();
		}
		return instance;
	}

	public int insert(CountedKeyValueModel keyValueModel) {
		SQLiteDatabase db = mDbHelper.getDatabase();
		ContentValues values = new ContentValues();
		values.put(CountedKeyValueTable.FLD_key, keyValueModel.key);
		values.put(CountedKeyValueTable.FLD_value, keyValueModel.value);
		int newid = (int) db.insert(CountedKeyValueTable.TBL_NAME, null, values);
		mDbHelper.closeDb();
		mDbHelper.close();
		return newid;
	}

	public int delete(String key) {
		SQLiteDatabase db = mDbHelper.getDatabase();
		int res = db.delete(CountedKeyValueTable.TBL_NAME,
				CountedKeyValueTable.FLD_key + "=?", new String[] { key });
		mDbHelper.closeDb();
		mDbHelper.close();
		return res;
	}

	public int update(CountedKeyValueModel keyValueModel) {
		SQLiteDatabase db = mDbHelper.getDatabase();
		ContentValues values = new ContentValues();
		values.put(CountedKeyValueTable.FLD_value, keyValueModel.value);
		int ret = db.update(CountedKeyValueTable.TBL_NAME, values,
				CountedKeyValueTable.FLD_key + "=?",
				new String[] { keyValueModel.key });
		mDbHelper.closeDb();
		mDbHelper.close();
		return ret;
	}

	public CountedKeyValueModel getModel(String key) {
		SQLiteDatabase db = mDbHelper.getDatabase();
		Cursor cursor = db.query(CountedKeyValueTable.TBL_NAME, null,
				CountedKeyValueTable.FLD_key + "=?", new String[] { key + "" },
				null, null, null, "1");
		CountedKeyValueModel model = null;
		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			model = new CountedKeyValueModel();
			model.key = key;
			model.value = cursor.getString(1);
			cursor.close();
		}
		mDbHelper.closeDb();
		mDbHelper.close();
		return model;
	}

	public void clear() {
		SQLiteDatabase db = mDbHelper.getDatabase();
		db.execSQL("delete from " + CountedKeyValueTable.TBL_NAME);
		mDbHelper.closeDb();
		mDbHelper.close();
	}

	public int total() {
		int result = 0;
		SQLiteDatabase db = mDbHelper.getDatabase();
		Cursor cursor = db.rawQuery("select count(*) as c from "
				+ CountedKeyValueTable.TBL_NAME, null);
		cursor.moveToFirst();
		result = cursor.getInt(0);
		cursor.close();
		mDbHelper.closeDb();
		mDbHelper.close();
		return result;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CountedKeyValueTable.CREATESQL_STRING);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}

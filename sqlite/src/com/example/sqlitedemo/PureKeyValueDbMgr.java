package com.example.sqlitedemo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class PureKeyValueDbMgr implements Table {

	private static final String TAG = PureKeyValueDbMgr.class.getSimpleName();

	private static TestDbHelper mDbHelper;
	private static PureKeyValueDbMgr instance;

	public static class PureKeyValueTable {
		public static final String TBL_NAME = "PureKeyValueConfig";
		public static final String FLD_key = "key";
		public static final String FLD_value = "value";
		public static final String CREATESQL_STRING = "create table "
				+ TBL_NAME + "(" + FLD_key + " varchar(256) ," + FLD_value
				+ " TEXT" + ")";
	}

	private PureKeyValueDbMgr() {
		mDbHelper = new TestDbHelper(Gl.getContext());
	}

	public static PureKeyValueDbMgr shareInstance() {
		if (instance == null) {
			instance = new PureKeyValueDbMgr();
		}
		return instance;
	}

	public int insert(PureKeyValueModel keyValueModel) {
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(PureKeyValueTable.FLD_key, keyValueModel.key);
		values.put(PureKeyValueTable.FLD_value, keyValueModel.value);
		int newid = (int) db.insert(PureKeyValueTable.TBL_NAME, null, values);
		db.close();
		mDbHelper.close();
		return newid;
	}

	public int delete(String key) {
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		int res = db.delete(PureKeyValueTable.TBL_NAME,
				PureKeyValueTable.FLD_key + "=?", new String[] { key });
		db.close();
		mDbHelper.close();
		return res;
	}

	public int update(PureKeyValueModel keyValueModel) {
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(PureKeyValueTable.FLD_value, keyValueModel.value);
		int ret = db.update(PureKeyValueTable.TBL_NAME, values,
				PureKeyValueTable.FLD_key + "=?",
				new String[] { keyValueModel.key });
		db.close();
		mDbHelper.close();
		return ret;
	}

	public PureKeyValueModel getModel(String key) {
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		Cursor cursor = db.query(PureKeyValueTable.TBL_NAME, null,
				PureKeyValueTable.FLD_key + "=?", new String[] { key + "" },
				null, null, null, "1");
		PureKeyValueModel model = null;
		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			model = new PureKeyValueModel();
			model.key = key;
			model.value = cursor.getString(1);
		}
		db.close();
		return model;
	}

	public void clear() {
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		db.execSQL("delete from " + PureKeyValueTable.TBL_NAME);
		db.close();
	}

	public int total() {
		int result = 0;
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select count(*) as c from "
				+ PureKeyValueTable.TBL_NAME, null);
		cursor.moveToFirst();
		result = cursor.getInt(0);
		cursor.close();
		db.close();
		return result;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(PureKeyValueTable.CREATESQL_STRING);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}

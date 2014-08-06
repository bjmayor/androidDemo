package com.example.sqlitedemo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class KeyValueDbMgr implements Table {

	private static final String TAG = KeyValueDbMgr.class.getSimpleName();

	private static TestDbHelper mDbHelper;
	private static KeyValueDbMgr instance;

	public static class KeyValueTable {
		public static final String TBL_NAME = "KeyValueConfig";
		public static final String FLD_key = "key";
		public static final String FLD_value = "value";
		public static final String CREATESQL_STRING = "create table "
				+ TBL_NAME + "(" + FLD_key + " varchar(256) ," + FLD_value
				+ " TEXT" + ")";
	}

	private KeyValueDbMgr() {
		mDbHelper = new TestDbHelper(Gl.getContext());
	}

	public static KeyValueDbMgr shareInstance() {
		if (instance == null) {
			instance = new KeyValueDbMgr();
		}
		return instance;
	}

	public int insert(KeyValueModel keyValueModel) {
		synchronized (TestDbHelper.DB_Lock) {
			try {
				SQLiteDatabase db = mDbHelper.getWritableDatabase();
				ContentValues values = new ContentValues();
				values.put(KeyValueTable.FLD_key, keyValueModel.key);
				values.put(KeyValueTable.FLD_value, keyValueModel.value);
				int newid = (int) db.insert(KeyValueTable.TBL_NAME, null,
						values);
				db.close();
				mDbHelper.close();
				return newid;
			} catch (Exception e) {
				e.printStackTrace();
				return -1;
			}

		}
	}

	public int delete(String key) {
		synchronized (TestDbHelper.DB_Lock) {
			try {
				SQLiteDatabase db = mDbHelper.getWritableDatabase();
				int res = db.delete(KeyValueTable.TBL_NAME,
						KeyValueTable.FLD_key + "=?", new String[] { key });
				db.close();
				mDbHelper.close();
				return res;
			} catch (Exception e) {
				e.printStackTrace();
				return -1;
			}

		}
	}

	public int update(KeyValueModel keyValueModel) {
		synchronized (TestDbHelper.DB_Lock) {
			try {
				SQLiteDatabase db = mDbHelper.getWritableDatabase();
				ContentValues values = new ContentValues();
				values.put(KeyValueTable.FLD_value, keyValueModel.value);
				int ret = db.update(KeyValueTable.TBL_NAME, values,
						KeyValueTable.FLD_key + "=?",
						new String[] { keyValueModel.key });
				db.close();
				mDbHelper.close();
				return ret;
			} catch (Exception e) {
				e.printStackTrace();
				return -1;
			}

		}
	}

	public KeyValueModel getModel(String key) {
		synchronized (TestDbHelper.DB_Lock) {
			try {
				SQLiteDatabase db = mDbHelper.getReadableDatabase();
				Cursor cursor = db.query(KeyValueTable.TBL_NAME, null,
						KeyValueTable.FLD_key + "=?",
						new String[] { key + "" }, null, null, null, "1");
				KeyValueModel model = null;
				if (cursor.getCount() > 0) {
					cursor.moveToFirst();
					model = new KeyValueModel();
					model.key = key;
					model.value = cursor.getString(1);
				}
				db.close();
				return model;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}

		}
	}
	
	public void clear()
	{
		synchronized (TestDbHelper.DB_Lock) {
			try {
				SQLiteDatabase db = mDbHelper.getReadableDatabase();
				db.execSQL("delete from "+KeyValueTable.TBL_NAME);
				db.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public int total()
	{
		int result = 0;
		synchronized (TestDbHelper.DB_Lock) {
			try {
				SQLiteDatabase db = mDbHelper.getReadableDatabase();
				Cursor cursor =  db.rawQuery("select count(*) as c from "+KeyValueTable.TBL_NAME,null);
				cursor.moveToFirst();
				result = cursor.getInt(0);
				cursor.close();
				db.close();
				return result;
			} catch (Exception e) {
				e.printStackTrace();
				return result;
			}
		}
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(KeyValueTable.CREATESQL_STRING);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}

package com.example.sqlitedemo;

public class CountedKeyValueModel {

	public String key=null;
	public String value=null;

	public static String getStringValue(String key, String defaultValue) {
		CountedKeyValueModel model = get(key);
		try {
			return model == null ? defaultValue : model.value;
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public static void setStringValue(String key, String value) {
		update(key, value);
	}

	public static int getIntValue(String key, int defaultValue) {
		CountedKeyValueModel model = get(key);
		try {
			return model == null ? defaultValue : Integer.parseInt(model.value);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public static void setIntValue(String key, int value) {
		update(key, value + "");
	}

	public static Boolean getBooleanValue(String key, Boolean defaultValue) {
		CountedKeyValueModel model = get(key);
		try {
			return model == null ? defaultValue : Boolean.parseBoolean(model.value);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public static void setBooleanValue(String key, Boolean value) {
		update(key, value + "");
	}

	public static void delete(String key) {
		KeyValueDbMgr.shareInstance().delete(key);
	}

	private static CountedKeyValueModel get(String key) {
		return CountedKeyValueDbMgr.shareInstance().getModel(key);
	}

	private static void add(String key, String value) {
		CountedKeyValueModel model = new CountedKeyValueModel();
		model.key = key;
		model.value = value;
		CountedKeyValueDbMgr.shareInstance().insert(model);
	}

	private static void update(String key, String value) {
		CountedKeyValueModel model = CountedKeyValueDbMgr.shareInstance().getModel(key);
		if (model == null) {
			add(key, value);
		} else {
			model.value =  value;
			CountedKeyValueDbMgr.shareInstance().update(model);
		}
	}
}

package com.example.sqlitedemo;

public class PureKeyValueModel {

	public String key=null;
	public String value=null;

	public static String getStringValue(String key, String defaultValue) {
		PureKeyValueModel model = get(key);
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
		PureKeyValueModel model = get(key);
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
		PureKeyValueModel model = get(key);
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

	private static PureKeyValueModel get(String key) {
		return PureKeyValueDbMgr.shareInstance().getModel(key);
	}

	private static void add(String key, String value) {
		PureKeyValueModel model = new PureKeyValueModel();
		model.key = key;
		model.value = value;
		PureKeyValueDbMgr.shareInstance().insert(model);
	}

	private static void update(String key, String value) {
		PureKeyValueModel model = PureKeyValueDbMgr.shareInstance().getModel(key);
		if (model == null) {
			add(key, value);
		} else {
			model.value =  value;
			PureKeyValueDbMgr.shareInstance().update(model);
		}
	}
}

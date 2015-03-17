package com.cn.ouan.xunchat.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

 

public class DBManager {
	private static final String TAG = "DBManager";
	private DBHelper helper;
	private SQLiteDatabase db;
	@SuppressWarnings("unused")
	private Object person;
	public DBManager(Context context) {
		helper = new DBHelper(context);
 		db = helper.getWritableDatabase();
	}
 

	 
 

}

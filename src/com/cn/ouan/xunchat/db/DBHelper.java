package com.cn.ouan.xunchat.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{
	private static final String DATABASE_NAME = "supermarket_36.db";
	private static final int DATABASE_VERSION = 30;
	public DBHelper(Context context  ) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION ); 
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub                                 
	}

	public void createTable(SQLiteDatabase db){
		db.execSQL("CREATE TABLE IF NOT EXISTS user(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
				")");
	}
}

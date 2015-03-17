package com.cn.ouan.xunchat.dao;

import com.cn.ouan.xunchat.db.DBHelper;

import android.content.Context;

public class DBDao {
	private Context context;
	private DBHelper db;
	public DBDao(Context context) {
		this.context = context;
		db=new DBHelper(context);
	}
}

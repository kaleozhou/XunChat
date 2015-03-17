package com.cn.ouan.xunchat.view;

import com.cn.ouan.xunchat.BaseActivity;
import com.cn.ouan.xunchat.R;
import com.cn.ouan.xunchat.R.layout;
import com.cn.ouan.xunchat.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class HelpActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.help, menu);
		return true;
	}

}

package com.cn.ouan.xunchat.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cn.ouan.xunchat.R;
import com.cn.ouan.xunchat.R.layout;
import com.cn.ouan.xunchat.R.menu;
import com.cn.ouan.xunchat.biz.UserBiz;
import com.cn.ouan.xunchat.util.Constants;

import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SelfUpdateActivity extends Activity implements OnClickListener {

	private EditText _et_nick_name;
	private EditText _et_email;
	private EditText _et_address;
	private EditText _et_age;
	private EditText _et_phone_number;
	private Button _btn_right;
	private UpdateUserReceiver receiver;
	private Button _btn_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_self_update);
		setUpView();
		setListener();
		setData();
	}
	
	private void setData() {
		receiver = new UpdateUserReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Constants.ACTION_UPDATA_USER_INFO);
		registerReceiver(receiver, filter);
		Map<String, String> usermap = (Map<String, String>) getIntent().getSerializableExtra("map");
//		_et_nick_name.setText(usermap.get("name"));
//		_et_age.setText(usermap.get("age"));
//		_et_phone_number.setText(usermap.get("phone_number"));
//		_et_address.setText(usermap.get("address"));
//		_et_email.setText(usermap.get("email"));
	}

	/**
	 * 初始化控件
	 */
	private void setUpView() {
		_et_nick_name = (EditText) findViewById(R.id.et_nick_name);
		_et_phone_number = (EditText) findViewById(R.id.et_phone_number);
		_et_age = (EditText) findViewById(R.id.et_age);
		_et_address = (EditText) findViewById(R.id.et_address);
		_et_email = (EditText) findViewById(R.id.et_meail);

		_btn_right = (Button) findViewById(R.id.btn_right);
		_btn_right.setVisibility(View.VISIBLE);
		_btn_right.setText("保存");
		_btn_back= (Button) findViewById(R.id.btn_back);
		_btn_back.setVisibility(View.VISIBLE);
		_btn_back.setText("取消");
	}

	private void setListener() {
		_btn_right.setOnClickListener(this);
		_btn_back.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_right:
			UpdateInfo();
			break;
		case R.id.btn_back:
			onKeyDown(KeyEvent.KEYCODE_BACK, null);
			break;
		}
	}

	private void UpdateInfo() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("uid", TApplication.user_sps.getString("username", null));
		map.put("name", _et_nick_name.getText().toString());
		map.put("age", _et_age.getText().toString());
		map.put("address", _et_address.getText().toString());
		map.put("email", _et_email.getText().toString());
		map.put("phone_number", _et_phone_number.getText().toString());
		new UserBiz(this).updateUserInfo(map);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		receiver.clearAbortBroadcast();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
		finish();
		overridePendingTransition(R.anim.out_from_right, R.anim.out_to_left);
		}
		return true;
	}
	class UpdateUserReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(Constants.ACTION_UPDATA_USER_INFO)) {
				String flag = intent.getStringExtra("flag");
				String mag = intent.getStringExtra("mag");
				if (flag.equals("SUCCESS")) {
					SelfUpdateActivity.this.finish();
				}else{
					Toast.makeText(context, mag, Toast.LENGTH_LONG).show();
				}
			}
		}
	}
}

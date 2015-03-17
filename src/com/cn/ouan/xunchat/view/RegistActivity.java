package com.cn.ouan.xunchat.view;

import java.util.HashMap;
import java.util.Map;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cn.ouan.xunchat.BaseActivity;
import com.cn.ouan.xunchat.R;
import com.cn.ouan.xunchat.bean.UserBean;
import com.cn.ouan.xunchat.biz.UserBiz;
import com.cn.ouan.xunchat.util.Constants;
import com.cn.ouan.xunchat.util.HttpUtil;

public class RegistActivity extends BaseActivity implements OnClickListener {

	/**注册信息*/
	private EditText _et_regist_username;
	private EditText _et_regist_password;
	private EditText _et_regist_password1;
	private EditText _et_regist_gender;
	private EditText _et_regist_nickname;
	private EditText _et_verify;
	private EditText _et_Teleph;

	private Button _btn_regist_regist;

	private RegistReceiver mReceiver;
	/** 标题 */
	private TextView _tv_top_title;
	private Button _btn_get_Verification;

	int number=60;
	Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				_btn_get_Verification.setText(""+number);
				number-=1;
				if (number==0) {
					
				}else{
					decreaseNumber();
				}
				break;

			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_regist);
		setUpView();
		setListener();
		mReceiver = new RegistReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Constants.ACTION_REGIST);
		filter.addAction(Constants.ACTION_VERIFY);
		registerReceiver(mReceiver, filter);
	}

	/**
	 * 初始化控件
	 */
	private void setUpView() {
		
		_tv_top_title = (TextView) findViewById(R.id.tv_top_title);
		_tv_top_title.setText("注册");
		
		_et_regist_username = (EditText) findViewById(R.id.et_regist_username);
		_et_regist_password = (EditText) findViewById(R.id.et_regist_password);
		_et_regist_password1 = (EditText) findViewById(R.id.et_regist_password1);
		_et_Teleph = (EditText) findViewById(R.id.et_Teleph);
		_et_verify = (EditText) findViewById(R.id.et_verify);
		_btn_regist_regist = (Button) findViewById(R.id.btn_regist_regist);
		_et_regist_nickname=(EditText)findViewById(R.id.et_regist_nickname);
		_btn_get_Verification=(Button)findViewById(R.id.btn_get_Verification);
		
	}

	/**
	 * 监听
	 */
	private void setListener() {
		_btn_regist_regist.setOnClickListener(this);
		_btn_get_Verification.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.regist, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		if (HttpUtil.isIntent(this)) {
		} else {
			showToast("网络不可用");
		}
		switch (v.getId()) {
		case R.id.btn_regist_regist:
			regist();// 注册
			break;
		case R.id.btn_get_Verification:
			getVerification();
			
		}
	}

	/**
	 * 请求验证码
	 */
	private void getVerification() {
		UserBiz user=new UserBiz(this);
		String Teleph=_et_Teleph.getText().toString();
		user.getVerification(Teleph, "0");
		decreaseNumber();
	}

	
	private void decreaseNumber() {
		new Thread(){
			@Override
			public void run() {
				handler.sendEmptyMessage(0);
			}
		}.start();
	}

	/**
	 * 注册
	 */
	private void regist() {
		String username = _et_regist_username.getText().toString();
		String password = _et_regist_password.getText().toString();
		String nickname = _et_regist_nickname.getText().toString();
		String gender = _et_regist_gender.getText().toString();
		String password1 = _et_regist_password1.getText().toString();
		String verify=_et_verify.getText().toString();
		if (!password.equals(password1)) {
			Toast.makeText(this, "密码不一致", Toast.LENGTH_SHORT).show();
		} else if (username.length() > 15 || username.length() < 3) {
			Toast.makeText(this, "用户名不正确", Toast.LENGTH_SHORT).show();
		} else {
			Map<String, String> map=new HashMap<String, String>();
			map.put("FuId", username);
			map.put("FuNickName", nickname);
			map.put("FuPassword", password);
			map.put("Verification", verify);
			map.put("Verification", verify);
			UserBiz biz = new UserBiz(this);
			UserBean user = new UserBean();
			user.setName(username);
			user.setPassword(password);
			user.setGender(gender);
			biz.regist(map);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			overridePendingTransition(R.anim.out_from_right, R.anim.out_to_left);
		}
		return true;
	}

	class RegistReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(Constants.ACTION_REGIST)) {
				String flag = intent.getStringExtra("flag");
				String mag = intent.getStringExtra("mag");
				if (flag.equals("SUCCESS")) {
					Toast.makeText(context, mag, Toast.LENGTH_SHORT).show();
					context.startActivity(new Intent(context,
							LoginActivity.class));
					RegistActivity.this.finish();
					overridePendingTransition(R.anim.out_from_right, R.anim.out_to_left);
				} else if (action.equals(Constants.ACTION_VERIFY)) {					
					Toast.makeText(context, mag, Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(context, mag, Toast.LENGTH_SHORT).show();
				}
			}
		}
	}
}
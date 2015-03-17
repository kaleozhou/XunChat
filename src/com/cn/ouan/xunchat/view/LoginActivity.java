package com.cn.ouan.xunchat.view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.digest.DigestUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cn.ouan.xunchat.BaseActivity;
import com.cn.ouan.xunchat.FindPasswordActivity;
import com.cn.ouan.xunchat.R;
import com.cn.ouan.xunchat.biz.UserBiz;
import com.cn.ouan.xunchat.util.Constants;
import com.cn.ouan.xunchat.util.HttpUtil;
import com.cn.ouan.xunchat.util.Tools;

/**
 * @功能 用户登录
 * @author 刘徐良
 * @date 2015-3-9
 * @category 
 */
public class LoginActivity extends BaseActivity implements OnClickListener {

	private EditText _et_username;
	private EditText _et_password;
	private Button _btn_login;

	LoginReceiver mReceiver;

	private int USER_NAME_LONG = 15;// 用户名最大位数
	private int USER_NAME_SHORT = 3;// 用户名最小位数
	private int USER_PASSWORD_LONG = 15;// 用户密码最大位数
	private int USER_PASSWORD_SHORT = 2;// 用户密码最小位数

	private String USER_NAME_TYPE = "\\w{" + USER_NAME_SHORT + ","
			+ USER_NAME_LONG + "}";// 用户名类型
	private String USER_PASSWORD_TYPE = "\\w{" + USER_PASSWORD_SHORT + ","
			+ USER_PASSWORD_LONG + "}";// 密码类型
	/**标题*/
	private TextView _tv_top_title;
	private Button _btn_right;
	private RelativeLayout _top_bar;
	private TextView _tv_find_password;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		setUpView();
		setListener();
		mReceiver = new LoginReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Constants.ACTION_LOGIN);
		registerReceiver(mReceiver, filter);
	}

	/**
	 * 初始化控件
	 */
	private void setUpView() {
		 _top_bar=(RelativeLayout)findViewById(R.id.top_bar);
		 _top_bar.setBackgroundColor(0x00000000);
		 _tv_top_title=(TextView)findViewById(R.id.tv_top_title);
		_tv_top_title.setText("");
		_btn_right=(Button)findViewById(R.id.btn_right);
		_btn_right.setVisibility(View.VISIBLE);
		_btn_right.setText("注册");
		
		_et_username = (EditText) findViewById(R.id.et_username);
		_et_password = (EditText) findViewById(R.id.et_password);
		_et_password.setText(TApplication.user_sps.getString("password", null));
		_et_username.setText(TApplication.user_sps.getString("username", null));
		_btn_login = (Button) findViewById(R.id.btn_login);
		_tv_find_password=(TextView)findViewById(R.id.tv_find_password);
	}

	/**
	 * 监听
	 */
	private void setListener() {
		_btn_login.setOnClickListener(this);
		_btn_right.setOnClickListener(this);
		_tv_find_password.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.self, menu);
		return true;
	}

	@Override
	public void onClick(View v) { 
		switch (v.getId()) {
		case R.id.btn_login:
			if (HttpUtil.isIntent(this)) {
				login();// 登录				
			}else{
				showToast("网络不可用");
			}
			break;

		case R.id.btn_right:
			if (HttpUtil.isIntent(this)) {
				startActivity(new Intent(LoginActivity.this, RegistActivity.class));
				overridePendingTransition(R.anim.in_from_right, R.anim.in_to_left);
			}else{
				showToast("网络不可用");
			}
			break;
		case R.id.tv_find_password:
			startActivity(new Intent(LoginActivity.this, FindPasswordActivity.class));
			overridePendingTransition(R.anim.in_from_right, R.anim.in_to_left);
			break;
		}
			
	}

	/**
	 * 登录方法
	 */
	private void login() {
		String username = _et_username.getText().toString().trim();
		String password = _et_password.getText().toString();
		String verification = null;
		if (loginMatcher(username, password) && verificationCode(verification)) {
			TApplication.openProgressDialog(this, "登录", false);
			UserBiz userBiz = new UserBiz(this);
			userBiz.Login(username, password);
			
		}else{
			startActivity(new Intent(this,FragmentMainActivity.class));
		}
	}

	/**
	 * 验证码验证
	 * @param verification
	 */
	private boolean verificationCode(String verification) {
		
		return true;
	}

	/**
	 * 用户名密码匹配
	 * @param username
	 * @param password
	 */
	private boolean loginMatcher(String username, String password) {
		Pattern pu = null;
		Pattern pp = null;
		pu = Pattern.compile(USER_NAME_TYPE);
		pp = Pattern.compile(USER_PASSWORD_TYPE);
		Matcher mu = pu.matcher(username);
		Matcher mp = pp.matcher(password);
		if (!mu.matches() ) {
			Tools.toastShow(this, "用户名格式不正确，格式为6-15位，由字母或数字组成", 0);
			return false;
		}else if ( !mp.matches()) {
			Tools.toastShow(this, "密码格式不正确，格式为6-15位，由字母、符号或数字组成", 0);
			return false;
		}
		return true;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	class LoginReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(Constants.ACTION_LOGIN)) {
				TApplication.cancelDilog();
				String Ret = intent.getStringExtra("Ret");
				String Msg = intent.getStringExtra("Msg");
				if (Ret.equals("1")) {
					Toast.makeText(context, Msg, Toast.LENGTH_SHORT).show();
					context.startActivity(new Intent(context,
							FragmentMainActivity.class));
					LoginActivity.this.finish();
					overridePendingTransition(R.anim.in_from_right, R.anim.in_to_left);
				} else {
					Toast.makeText(context, Msg, Toast.LENGTH_SHORT).show();
				}
			}
		}
	}
}

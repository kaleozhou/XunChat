package com.cn.ouan.xunchat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.widget.Toast;

import com.cn.ouan.xunchat.service.PushService;
import com.cn.ouan.xunchat.util.HttpUtil;
import com.cn.ouan.xunchat.util.Tools;
import com.cn.ouan.xunchat.view.FragmentMainActivity;
import com.cn.ouan.xunchat.view.LoginActivity;
import com.cn.ouan.xunchat.view.TApplication;

public class WelcomeActivity extends Activity {
	private int flag = 0;
	private boolean isLogin = false;
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				// 用户名或密码为空时跳转登录界面
				if (flag == 0) {
					Toast.makeText(WelcomeActivity.this, "登录成功",
							Toast.LENGTH_LONG).show();
					startActivity(new Intent(WelcomeActivity.this,
							LoginActivity.class));
					finish();
				} else if (flag == 1) {
					Toast.makeText(WelcomeActivity.this, "登录失败",
							Toast.LENGTH_LONG).show();
					startActivity(new Intent(WelcomeActivity.this,
							LoginActivity.class));
					finish();
				} else if (flag == 2) {
					Toast.makeText(WelcomeActivity.this, "网络不可用",
							Toast.LENGTH_LONG).show();
					startActivity(new Intent(WelcomeActivity.this,
							LoginActivity.class));
					finish();
				}
				if (isLogin) {
					startActivity(new Intent(WelcomeActivity.this,
							FragmentMainActivity.class));
					finish();
				}
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		if (HttpUtil.isIntent(WelcomeActivity.this)) {
			PushService.actionStart(getApplicationContext());
			String password = TApplication.user_sps.getString("password",
					"null");
			String username = TApplication.user_sps.getString("username",
					"null");
			// 用户名或密码为空时跳转登录界面
			if (!Tools.isNull(password) && !Tools.isNull(username)) {
				isLogin = true;
				flag = 0;
			} else {
				isLogin = false;
				flag = 1;
			}
		} else {
			isLogin = false;
			flag = 2;
		}
		new Thread() {
			public void run() {
				try {
					sleep(3000);
					handler.sendEmptyMessage(0);

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.welcome, menu);
		return true;
	}

}

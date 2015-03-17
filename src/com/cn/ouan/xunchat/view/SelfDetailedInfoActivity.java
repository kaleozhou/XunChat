package com.cn.ouan.xunchat.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.cn.ouan.xunchat.BaseActivity;
import com.cn.ouan.xunchat.R;
import com.cn.ouan.xunchat.util.Constants;
import com.cn.ouan.xunchat.view.SelfUpdateActivity.UpdateUserReceiver;

public class SelfDetailedInfoActivity extends BaseActivity implements OnClickListener{
	DetailedInfoReceiver receiver;
	private Button _btn_right;
	private Button _btn_back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_self_detailed_info);
		setUpView();
		setListener();
		setData();
	}

	private void setData() {
		receiver = new DetailedInfoReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Constants.ACTION_UPDATA_USER_INFO);
		registerReceiver(receiver, filter);
	}

	private void setUpView() {
		 _btn_right=(Button)findViewById(R.id.btn_right);
		 _btn_back=(Button)findViewById(R.id.btn_back);
	}

	private void setListener() {
		
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		receiver.clearAbortBroadcast();
	}
	class DetailedInfoReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(Constants.ACTION_UPDATA_USER_INFO)) {
				String flag = intent.getStringExtra("flag");
				String mag = intent.getStringExtra("mag");
				if (flag.equals("SUCCESS")) {
				}else{
					Toast.makeText(context, mag, Toast.LENGTH_LONG).show();
				}
			}
		}
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			finish();
			overridePendingTransition(R.anim.out_from_right, R.anim.out_to_left);
			break;

		default:
			break;
		}
	}
}

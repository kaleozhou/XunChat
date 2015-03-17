package com.cn.ouan.xunchat.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cn.ouan.xunchat.R;
import com.cn.ouan.xunchat.R.id;
import com.cn.ouan.xunchat.R.layout;
import com.cn.ouan.xunchat.R.menu;
import com.cn.ouan.xunchat.biz.CircleBiz;
import com.cn.ouan.xunchat.util.Constants;

import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
/**
 * 创建圈子界面
 * @author Administrator
 *
 */
public class CreateCircleActivity extends Activity implements OnClickListener {

	private Button _button6;
	private Button _button5;
	private Button _button4;
	private Button _button3;
	private Button _button2;
	private Button _button1;
	private EditText _et_name;
	private EditText _et_address;
	private EditText _et_note;
	private Button _btn_butmit;

	
	private String type;
	
	private CreateCircleReceiver mRecirver;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_circle);
		setUpView();
		Log.i("into", "createcircleActivity_ondestroy");
		setListener();
		mRecirver= new CreateCircleReceiver();
		IntentFilter filter=new IntentFilter();
		filter.addAction(Constants.ACTION_CREATE_CIRCLE);
		registerReceiver(mRecirver, filter);
	}

	/**
	 * 初始化空间
	 */
	private void setUpView() {
		_button1 = (Button) findViewById(R.id.button1);
		_button2 = (Button) findViewById(R.id.button2);
		_button3 = (Button) findViewById(R.id.button3);
		_button4 = (Button) findViewById(R.id.button4);
		_button5 = (Button) findViewById(R.id.button5);
		_button6 = (Button) findViewById(R.id.button6);

		_btn_butmit = (Button) findViewById(R.id.btn_butmit);

		_et_name = (EditText) findViewById(R.id.et_name);
		_et_address = (EditText) findViewById(R.id.et_address);
		_et_note = (EditText) findViewById(R.id.et_note);
	}

	/**
	 * 添加监听
	 */
	private void setListener() {
		_button1.setOnClickListener(this);
		_button2.setOnClickListener(this);
		_button3.setOnClickListener(this);
		_button4.setOnClickListener(this);
		_button5.setOnClickListener(this);
		_button6.setOnClickListener(this);
		_btn_butmit.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_circle, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) { 
		case R.id.button1:
			type="情感";
			break;
		case R.id.button2:
			type="娱乐";
			break;
		case R.id.button3:
			type="校园";
			break;
		case R.id.button4:
			type="游戏";
			break;
		case R.id.button5:
			type="其他";
			break;
		case R.id.button6:

			break;
		case R.id.btn_butmit:
			Map<String, String> map=new HashMap<String, String>();
			map.put("type", type);
			map.put("nick", _et_name.getText().toString());
			map.put("name", TApplication.user_sps.getString("username", null));
			map.put("address", _et_address.getText().toString());
			map.put("note",_et_note.getText().toString());
			map.put("longitude", ""	);
			map.put("latitude","");
			CircleBiz biz=new CircleBiz(this);
			biz.createCircle(map);
			break;
		}
	}
	@Override
	protected void onDestroy() {
		Log.i("into", "createcircleActivity_ondestroy");
		super.onDestroy();
		mRecirver.clearAbortBroadcast();
	}
	/**
	 * 圈子创建广播
	 * @author Administrator
	 */
	class CreateCircleReceiver extends BroadcastReceiver{
		@Override
		public void onReceive(Context context, Intent intent) {
			String action=intent.getAction();
			if (action.equals(Constants.ACTION_CREATE_CIRCLE)) {
				String flag=intent.getStringExtra("flag");
				Toast.makeText(context, flag, Toast.LENGTH_SHORT).show();
			}
		}
	}
}

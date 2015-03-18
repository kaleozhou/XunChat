package com.cn.ouan.xunchat.view;

import java.util.HashMap;
import java.util.Map;

import com.cn.ouan.xunchat.R;
import com.cn.ouan.xunchat.R.layout;
import com.cn.ouan.xunchat.R.menu;
import com.cn.ouan.xunchat.biz.CircleBiz;
import com.cn.ouan.xunchat.util.Constants;

import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent; 
import android.content.IntentFilter;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class CircleInfoActivity extends Activity implements OnClickListener{

	private Map<String, String> cirmap;
	private Button _btn_address;
	private Button _btn_add_circle;
	private Button _btn_cir_lor_name;
	private Button _btn_cir_note;
	
	
	private AddCircleReceiver receiver;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_circle_info);
		setData();
		setUpView();
		setListener();
		IntentFilter filter=new IntentFilter();
		filter.addAction(Constants.ACTION_ADD_CIRCLE);
		receiver=new AddCircleReceiver();
		
		
		
		
		
		registerReceiver(receiver, filter);
	}
	/**
	 * get circle data
	 */
	private void setData(){
		cirmap=new HashMap<String, String>();
		Intent i=getIntent();
		cirmap.put("name", i.getStringExtra("name"));
		cirmap.put("address", i.getStringExtra("address"));
		cirmap.put("note", i.getStringExtra("note"));
		cirmap.put("cid", i.getStringExtra("cid"));
		cirmap.put("count", i.getStringExtra("count"));
	}
	/**
	 * init view 
	 */
	private void setUpView() {
		 _btn_address=(Button)findViewById(R.id.btn_address);
		 _btn_add_circle=(Button)findViewById(R.id.btn_add_circle);
		 _btn_cir_lor_name=(Button)findViewById(R.id.btn_cir_lor_name);
		 _btn_cir_note=(Button)findViewById(R.id.btn_cir_note);
	}
	
	/**
	 * control listener
	 */
	private void setListener() { 
		_btn_add_circle.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_add_circle:
			addCircle();
			break;

		default:
			break;
		}
	}
	/**
	 * add circle
	 */
	private void addCircle() {
		CircleBiz biz=new CircleBiz(this);
		biz.addCircle(cirmap.get("cid"));
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		receiver.clearAbortBroadcast();
	}
	/**
	 * 圈子创建广播
	 * @author Administrator
	 */
	class AddCircleReceiver extends BroadcastReceiver{
		@Override
		public void onReceive(Context context, Intent intent) {
			String action=intent.getAction();
			if (action.equals(Constants.ACTION_ADD_CIRCLE)) {
				String flag=intent.getStringExtra("flag");
				Intent i=new Intent(CircleInfoActivity.this,ChatActivity.class);
				i.putExtra("type", "1");
				i.putExtra("cid", cirmap.get("cid"));
				startActivity(i);
				Toast.makeText(context, flag, Toast.LENGTH_SHORT).show();
			}
		}
	}
}

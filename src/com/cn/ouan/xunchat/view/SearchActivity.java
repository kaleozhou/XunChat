package com.cn.ouan.xunchat.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.PageTransformer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cn.ouan.xunchat.BaseActivity;
import com.cn.ouan.xunchat.R;
import com.cn.ouan.xunchat.R.id;
import com.cn.ouan.xunchat.R.layout;
import com.cn.ouan.xunchat.adapter.VPNearbyAdapter;
import com.cn.ouan.xunchat.biz.CircleBiz;
import com.cn.ouan.xunchat.util.Constants; 

public class SearchActivity extends BaseActivity implements OnClickListener{

	private ListView _lv_search;
	private ViewPager _vp_search;
	private List<Map<String, String>> lists;
	private TextView _tv_state;
	private Button _btn_start_search;
	SearchReceiver receiver;
	private Button _btn_start_service;
	private Button _btn_nearby_user;
	private Button _btn_nearby_circle;
	private VPNearbyAdapter vpnearby;
	private Button _btn_create_circle;
	private Button _btn_filfer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		setUpView();
		setListener();
		/*
		 * 注册广播
		 */
		IntentFilter filter=new IntentFilter();
//        filter.addAction(Constants.ACTION_BLUETOOTH_FINISHED);
//        filter.addAction(Constants.ACTION_BLUETOOTH_STARTED);
//        filter.addAction(Constants.ACTION_BLUETOOTH_FOUND);
//        mReceiver=new BluetoothReceiver();
//        registerReceiver(mReceiver, filter);
//        Intent discoverableIntent = new Intent(
//        		android.bluetooth.BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
//		discoverableIntent.putExtra(
//				android.bluetooth.BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
//		 startActivity(discoverableIntent);
		filter.addAction(Constants.ACTION_NEARBY_CIRLCE);
        receiver=new SearchReceiver();
        registerReceiver(receiver, filter);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(receiver);
	}
	private void setData() {
		CircleBiz circles=new CircleBiz(this);
		circles.getusers();
		circles.getcircles();
		
//		Intent intent1 = new Intent("enarby_user");
//		sendBroadcast(intent1);
	}
	/**
	 * 初始化控件
	 */
	private void setUpView() {
		
		_btn_nearby_user=(Button)findViewById(R.id.btn_nearby_user);
		_btn_nearby_circle=(Button)findViewById(R.id.btn_nearby_circle);
		_btn_create_circle=(Button)findViewById(R.id.btn_create_circle);
		_btn_filfer=(Button)findViewById(R.id.btn_filfer);
		
		_lv_search=(ListView)findViewById(R.id.lv_search);
		setVPNearby(); 
		_vp_search=(ViewPager)findViewById(R.id.vp_search);
		_vp_search.setAdapter(vpnearby);
		setData();
	}
	
	/**
	 * 初始化附近用户，圈子
	 */
	private void setVPNearby() {
		View v0=LayoutInflater.from(this).inflate(R.layout.item_view_user_search, null);
		View v1=LayoutInflater.from(this).inflate(R.layout.item_view_user_search, null);
		List<View> views=new ArrayList<View>();
		views.add(v0);
		views.add(v1);
		vpnearby=new VPNearbyAdapter(this, views);
	}

	/**
	 * 监听
	 */
	private void setListener() {
		_btn_nearby_circle.setOnClickListener(this);
		_btn_nearby_user.setOnClickListener(this);
		_btn_create_circle.setOnClickListener(this);
		_btn_filfer.setOnClickListener(this);
	}
 
 
	
//	class BluetoothReceiver extends BroadcastReceiver{
//		@Override
//		public void onReceive(Context context, Intent intent) {
//			String action=intent.getAction();
//			//得到搜索用户
//			if(action.equals(Constants.ACTION_BLUETOOTH_FOUND)){
//				lists=(List<Map<String,String>>)intent.getSerializableExtra("maps");
//				adapter=new NearbyUserSearchAdapter(SearchActivity.this, lists);
//				_lv_search.setAdapter(adapter);
//			//开始搜索
//			}else if (action.equals(Constants.ACTION_BLUETOOTH_STARTED)) {				
//				_tv_state.setText("开始搜索...");
//			//停止搜索
//			}else if (action.equals(Constants.ACTION_BLUETOOTH_FINISHED)) {
//				_tv_state.setText("搜索结束");
//			}
//		}
//	}
	class SearchReceiver extends BroadcastReceiver{
		@Override
		public void onReceive(Context context, Intent intent) {
			String action=intent.getAction();
			//得到搜索用户
			if(action.equals(Constants.ACTION_NEARBY_CIRLCE)){
				lists=(List<Map<String,String>>)intent.getSerializableExtra("maps");
				vpnearby.setNearbylist(lists, intent.getIntExtra("type", 0));
 				//开始搜索
			}else if (action.equals(Constants.ACTION_NEARBY_USER)) {			
				 
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_create_circle:
			startActivity(new Intent(this,CreateCircleActivity.class));
			break;
		case R.id.btn_filfer:
			break;
		}
	}
}

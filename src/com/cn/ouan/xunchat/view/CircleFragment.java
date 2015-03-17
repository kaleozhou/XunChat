package com.cn.ouan.xunchat.view;

import java.util.List;
import java.util.Map;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.cn.ouan.xunchat.R;
import com.cn.ouan.xunchat.adapter.CirclePushAdapter;
import com.cn.ouan.xunchat.biz.CircleBiz;
import com.cn.ouan.xunchat.custom.CustomViewPager;
import com.cn.ouan.xunchat.util.Constants;

public class CircleFragment extends Fragment implements OnClickListener{

	private ListView _lv_circle_push;
	/** push circle list adapter */
	private CirclePushAdapter pushadapter;
	SearchReceiver receiver;
	private Button _btn_mik_fir_circle;
	private Button _btn_interest_circle;
	private Button _btn_game_circle;
	private CustomViewPager _vp_main;
 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v=LayoutInflater.from(getActivity()).inflate(R.layout.activity_circle, null);
		setUpView(v);
		setListener();
		setData();
		setViewPagerCurrent(0);
		_btn_mik_fir_circle.setTextColor(0xff000000);
		_btn_mik_fir_circle.setBackgroundResource(R.color.verify);
		return v;
	}

		private void setData() {
			IntentFilter filter=new IntentFilter();
			filter.addAction(Constants.ACTION_NEARBY_CIRLCE);
	        receiver=new SearchReceiver();
	        getActivity().registerReceiver(receiver, filter);
//			CircleBiz circles=new CircleBiz(getActivity());
//			circles.getusers();
		}		
	/**
	 * 初始化控件
	 */
	private void setUpView(View v) {
		_lv_circle_push=(ListView)v.findViewById(R.id.lv_circle_push);
		_btn_mik_fir_circle=(Button)v.findViewById(R.id.btn_mik_fir_circle);
		_btn_interest_circle=(Button)v.findViewById(R.id.btn_interest_circle);
		_btn_game_circle=(Button)v.findViewById(R.id.btn_game_circle);
		
		_vp_main=(CustomViewPager)v.findViewById(R.id.vp_main);
		 pushadapter=new CirclePushAdapter(getActivity(), null);
		 _lv_circle_push.setAdapter(pushadapter);
	}
	/**
	 * 添加监听
	 */
	private void setListener() {
		_btn_mik_fir_circle.setOnClickListener(this);
		_btn_interest_circle.setOnClickListener(this);
		_btn_game_circle.setOnClickListener(this);
		
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		receiver.clearAbortBroadcast();
	}
	/**
	 * circlefriend broadcast
	 * @author Administrator
	 *
	 */
	class SearchReceiver extends BroadcastReceiver{
		@Override
		public void onReceive(Context context, Intent intent) {
			String action=intent.getAction();
			//得到搜索用户
			if(action.equals(Constants.ACTION_NEARBY_CIRLCE)){
				List<Map<String, String>> lists=(List<Map<String,String>>)intent.getSerializableExtra("maps");
				pushadapter.setFriendPushlist(lists);
 				//开始搜索
			}else if (action.equals(Constants.ACTION_NEARBY_USER)) {			
				 
			}
		}
	}
	/**
	 * 设置viewpager的下标
	 * @param i
	 */
	public void setViewPagerCurrent(int i){
		_btn_mik_fir_circle.setTextColor(0xdddddddd);
		_btn_interest_circle.setTextColor(0xdddddddd);
		_btn_game_circle.setTextColor(0xdddddddd);
		_btn_mik_fir_circle.setBackgroundResource(R.color.white);
		_btn_interest_circle.setBackgroundResource(R.color.white);
		_btn_game_circle.setBackgroundResource(R.color.white);
		_vp_main.setCurrentItem(i, false);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_mik_fir_circle:
			setViewPagerCurrent(0);
			_btn_mik_fir_circle.setTextColor(0xff000000);
			_btn_mik_fir_circle.setBackgroundResource(R.color.verify);
			break;
		case R.id.btn_interest_circle:
			setViewPagerCurrent(1);
			_btn_interest_circle.setTextColor(0xff000000);
			_btn_interest_circle.setBackgroundResource(R.color.verify);
			break;
		case R.id.btn_game_circle:
			setViewPagerCurrent(2);
			_btn_game_circle.setTextColor(0xff000000);
			_btn_game_circle.setBackgroundResource(R.color.verify);
			break;
		}
	}
}

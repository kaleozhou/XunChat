package com.cn.ouan.xunchat.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.cn.ouan.xunchat.R;
import com.cn.ouan.xunchat.adapter.CircleFriendPushAdapter;
import com.cn.ouan.xunchat.adapter.VPMainPager;
import com.cn.ouan.xunchat.biz.CircleBiz;
import com.cn.ouan.xunchat.biz.FriendBiz;
import com.cn.ouan.xunchat.custom.CustomViewPager;
import com.cn.ouan.xunchat.util.Constants;

@SuppressLint("NewApi")
public class CircleFriendFragment extends Fragment implements OnClickListener {
	/** push friend list */
	private ListView _lv_friend_push;
	private Button _btn_friend_group;
	private Button _btn_stranger_group;
	private Button _btn_blacklist_group;

	private CustomViewPager _vp_circle_firend;

	/** push friend list adapter */
	private CircleFriendPushAdapter pushadapter;

	SearchReceiver receiver;
	private View _item_v1;
	private View _item_v3;
	private View _item_v2;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = LayoutInflater.from(getActivity()).inflate(
				R.layout.activity_circle_friend, null);
		setUpView(view);
		setListener();
		setData();
		setViewPagerCurrent(0);
		_btn_friend_group.setBackgroundColor(0xff681900);
		return view;
	}

	private void setData() {
		IntentFilter filter = new IntentFilter();
		filter.addAction(Constants.ACTION_NEARBY_CIRLCE);

		receiver = new SearchReceiver();
		getActivity().registerReceiver(receiver, filter);
		TApplication.openProgressDialog(getActivity(), "朋友", false);
//		CircleBiz circles = new CircleBiz(getActivity());
//		circles.getusers();
//		FriendBiz friends = new FriendBiz(getActivity());
//		friends.AllFriend();
	}

	/**
	 * 初始化控件
	 */
	private void setUpView(View view) {
		_lv_friend_push = (ListView) view.findViewById(R.id.lv_friend_push);

		_btn_friend_group = (Button) view.findViewById(R.id.btn_friend_group);
		_btn_stranger_group = (Button) view
				.findViewById(R.id.btn_stranger_group);
		_btn_blacklist_group = (Button) view
				.findViewById(R.id.btn_blacklist_group);

		pushadapter = new CircleFriendPushAdapter(getActivity(), null);
		_lv_friend_push.setAdapter(pushadapter);
		_item_v1 = LayoutInflater.from(getActivity()).inflate(
				R.layout.item_lv_lv, null);
		_item_v2 = LayoutInflater.from(getActivity()).inflate(
				R.layout.item_lv_lv, null);
		_item_v3 = LayoutInflater.from(getActivity()).inflate(
				R.layout.item_lv_lv, null);
		_vp_circle_firend = (CustomViewPager) view
				.findViewById(R.id.vp_circle_firend);
		List<View> views = new ArrayList<View>();
		views.add(_item_v1);
		views.add(_item_v2);
		views.add(_item_v3);
		VPMainPager adapter = new VPMainPager(getActivity(), views);
		_vp_circle_firend.setAdapter(adapter);
	}

	/**
	 * 添加监听
	 */
	private void setListener() {
		_btn_blacklist_group.setOnClickListener(this);
		_btn_friend_group.setOnClickListener(this);
		_btn_stranger_group.setOnClickListener(this);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		receiver.clearAbortBroadcast();
	}

	/**
	 * circlefriend broadcast
	 * 
	 * @author Administrator
	 * 
	 */
	class SearchReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			TApplication.cancelDilog();
			// 得到搜索用户
			if (action.equals(Constants.ACTION_NEARBY_CIRLCE)) {
				List<Map<String, String>> lists = (List<Map<String, String>>) intent
						.getSerializableExtra("maps");
				pushadapter.setFriendPushlist(lists);
				// 开始搜索
			} else if (action.equals(Constants.ACTION_ALL_STRANGER)) {

			}
		}
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_friend_group:
			setViewPagerCurrent(0);
			_btn_friend_group.setTextColor(0xff000000);
			_btn_friend_group.setBackgroundResource(R.color.verify);
			break;
		case R.id.btn_stranger_group:
			setViewPagerCurrent(1);
			_btn_stranger_group.setTextColor(0xff000000);
			_btn_stranger_group.setBackgroundResource(R.color.verify);
			break;
		case R.id.btn_blacklist_group:
			setViewPagerCurrent(2);
			_btn_blacklist_group.setTextColor(0xff000000);
			_btn_blacklist_group.setBackgroundResource(R.color.verify);
			break;
		}
	}
	/**
	 * 设置viewpager的下标
	 * @param i
	 */
	public void setViewPagerCurrent(int i){
		_btn_friend_group.setTextColor(0xdddddddd);
		_btn_stranger_group.setTextColor(0xdddddddd);
		_btn_blacklist_group.setTextColor(0xdddddddd);
		_btn_friend_group.setBackgroundResource(R.color.white);
		_btn_stranger_group.setBackgroundResource(R.color.white);
		_btn_blacklist_group.setBackgroundResource(R.color.white);
		_vp_circle_firend.setCurrentItem(i, false);
	}
}

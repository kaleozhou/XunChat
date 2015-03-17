package com.cn.ouan.xunchat.adapter;

import java.util.List;
import java.util.Map;

import com.cn.ouan.xunchat.R;
import com.cn.ouan.xunchat.biz.FriendBiz;
import com.cn.ouan.xunchat.util.Constants;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ListView;

public class VPMainPager extends PagerAdapter  implements OnTouchListener{

	SearchReceiver receiver;
	private List<View> views;
	private Context context;
	private ListView _lv_friend;
	private ListView _lv_stranger;
	private ListView _lv_blacklist;
	private FriendListAdapter friendAdapter;
	private FriendListAdapter strangerAdapter;
	private View v0;
	private View v1;
	private View v2;

 
	public VPMainPager(Context context, List<View> views) {
		this.context = context;
		this.views = views;
		v0 = views.get(0);
		this._lv_friend = (ListView) v0.findViewById(R.id.lv_lv);
		this.friendAdapter = new FriendListAdapter(context, null);
		_lv_friend.setAdapter(friendAdapter);
		v1 = views.get(1);
		this._lv_stranger = (ListView) v1.findViewById(R.id.lv_lv);
		this.strangerAdapter = new FriendListAdapter(context, null);
		_lv_stranger.setAdapter(strangerAdapter);
		v2 = views.get(2);
		this._lv_blacklist = (ListView) v2.findViewById(R.id.lv_lv);
//		this.friendAdapter = new FriendListAdapter(context, null);
//		_lv_blacklist.setAdapter(friendAdapter);
		receiver = new SearchReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Constants.ACTION_ALL_FRIEND);
		filter.addAction(Constants.ACTION_ALL_STRANGER);
		context.registerReceiver(receiver, filter);
//		new FriendBiz(context).AllFriend();	
//		new FriendBiz(context).AllStranger();
	}

	@Override
	public int getCount() {
		return views.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object arg1) {
		// TODO Auto-generated method stub
		return view == (arg1);
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(views.get(position));
	}

	@Override
	public Object instantiateItem(View container, int position) {
		View v = null;
		if (position==0) {
			v=v0;
		}else if (position==1) {
			v=v1;
		}else if (position==2) {
			v=v2;
		}
		((ViewPager) container).addView(v, 0); 
		return v;
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
			// 得到搜索用户
			if (action.equals(Constants.ACTION_ALL_FRIEND)) {
				List<Map<String, String>> lists = (List<Map<String, String>>) intent
						.getSerializableExtra("maps");
				friendAdapter.setUpdata(lists);
				// 开始搜索
			} else if (action.equals(Constants.ACTION_ALL_STRANGER)) {
				Log.i("into", "接受陌生人广播");
				List<Map<String, String>> lists = (List<Map<String, String>>) intent
						.getSerializableExtra("maps");
				strangerAdapter.setUpdata(lists);
			}
		}
	} 
		@Override
		public boolean onTouch(View arg0, MotionEvent arg1) {
			// TODO Auto-generated method stub
			return true;
		}
}

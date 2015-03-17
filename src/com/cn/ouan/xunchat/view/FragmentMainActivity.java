package com.cn.ouan.xunchat.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cn.ouan.xunchat.GameFragment;
import com.cn.ouan.xunchat.R;
import com.cn.ouan.xunchat.StoreFragment;
import com.cn.ouan.xunchat.adapter.FragmentMainPagerAdapter;
import com.cn.ouan.xunchat.custom.CustomViewPager;
import com.cn.ouan.xunchat.service.BluetoothService;
import com.cn.ouan.xunchat.service.HeartbeatService;
import com.cn.ouan.xunchat.util.Constants;
import com.cn.ouan.xunchat.view.FragmentMainActivity.GVMainAdapter.ViewHodler;
import com.slidingmenu.lib.SlidingMenu;

/**
 * 2015-1-26 主界面
 * 
 * @author Administrator
 */
public class FragmentMainActivity extends FragmentActivity implements
		OnClickListener {

	private SlidingMenu menu;
	private ListView _lv_menu;
	private ViewPager _vp_main;

	private List<ViewHodler> hodlers = new ArrayList<ViewHodler>();
	private List<Map<String, String>> lists;
	private List<Map<String, String>> flists;
	private Button _btn_message;
	private Button _btn_frienda;
	private Button _btn_circle;
	private int currentIndex = 0;
	private View _v_message;
	private View _v_circle;
	private View _v_friends;

	private mainBroadReceiver receiver;
	private FragmentTransaction fragmentTransaction;
	private CustomViewPager _vp_fragment_main;
	private Button _btn_back;
	private ListView _lv_sliding_menu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		startService(new Intent(FragmentMainActivity.this,
				BluetoothService.class));
		startService(new Intent(FragmentMainActivity.this,
				HeartbeatService.class));
		setData();
		setUpView();
		setListener();
		setSlidingMenu();

		receiver = new mainBroadReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Constants.ACTION_EXIT);
		filter.addAction(Constants.ACTION_SWITCH);
		registerReceiver(receiver, filter);
	}

	/**
	 * listener
	 */
	private void setListener() {

	}

	/**
	 * 侧滑栏
	 */
	private void setSlidingMenu() {
		// menu = new SlidingMenu(this);
		// menu.setMode(SlidingMenu.LEFT);// 设置左滑菜单
		// menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);//
		// 设置滑动的屏幕范围，该设置为全屏区域都可以滑动
		// menu.setBehindWidth(400);// 设置SlidingMenu菜单的宽度
		// menu.setFadeDegree(0.35f);// SlidingMenu滑动时的渐变程度
		// menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);//
		// 使SlidingMenu附加在Activity上
		// menu.setMenu(R.layout.menu_sliding);// 设置menu的布局文件
		// menu.toggle();// 动态判断自动关闭或开启SlidingMenu
		// menu.showMenu();// 显示SlidingMenu
		// menu.showContent();// 显示内容
		// _lv_menu = (ListView) menu.findViewById(R.id.lv_menu);
		GVMainAdapter adapter = new GVMainAdapter(this, lists);
		// _lv_menu.setAdapter(adapter);
		_lv_sliding_menu.setAdapter(adapter);
	}

	/**
	 * 
	 */
	private void setUpView() {
		_vp_fragment_main = (CustomViewPager) findViewById(R.id.vp_fragment_main);
		_lv_sliding_menu = (ListView) findViewById(R.id.lv_sliding_menu);
		List<Fragment> lists = new ArrayList<Fragment>();
		FacilityFragment faci_fragment = new FacilityFragment();
		SelfFragment self_fragment = new SelfFragment();
		CircleFriendFragment cir_fri_fragment = new CircleFriendFragment();
		CircleFragment cir_fragment = new CircleFragment();
		GameFragment game_fragment = new GameFragment();
		StoreFragment Store_fragment = new StoreFragment();
		lists.add(faci_fragment);
		lists.add(self_fragment);
		lists.add(cir_fri_fragment);
		lists.add(cir_fragment);
		lists.add(game_fragment);
		lists.add(Store_fragment);
		FragmentMainPagerAdapter adapter = new FragmentMainPagerAdapter(
				getSupportFragmentManager(), lists);
		_vp_fragment_main.setAdapter(adapter);

	}

	private void setData() {
		String[] strs = { this.getString(R.string.slid_menu_name_1),
				this.getString(R.string.slid_menu_name_2),
				this.getString(R.string.slid_menu_name_3),
				this.getString(R.string.slid_menu_name_4),
				this.getString(R.string.slid_menu_name_5),
				this.getString(R.string.slid_menu_name_6) };
		lists = new ArrayList<Map<String, String>>();
		for (int i = 0; i < strs.length; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("name", strs[i]);
			lists.add(map);
		}

	}

	/**
	 * 
	 */
	class mainBroadReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(Constants.ACTION_EXIT)) {
				FragmentMainActivity.this.finish();
			} 
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			menu.toggle();
			break;

		default:
			break;
		}
	}

	class GVMainAdapter extends BaseAdapter {
		private Context context;
		LayoutInflater layoutInflater;
		List<Map<String, String>> lists;
		Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 0:
					Toast.makeText(context, "未开放", Toast.LENGTH_SHORT).show();
					break;
				case 1:

					break;
				case 2:

					break;

				default:
					break;
				}
			};
		};

		public GVMainAdapter(Context context, List<Map<String, String>> lists) {
			this.context = context;
			this.layoutInflater = LayoutInflater.from(context);
			this.lists = lists;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return lists.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View view, ViewGroup parent) {
			ViewHodler h = null;
			if (view == null) {
				h = new ViewHodler();
				view = layoutInflater.inflate(R.layout.item_gv_main, null);
				h.tv_name = (TextView) view.findViewById(R.id.tv_name);
				h.iv_pic = (ImageView) view.findViewById(R.id.iv_pic);
				h._ll_main_menu = (LinearLayout) view
						.findViewById(R.id.ll_main_menu);
				view.setTag(h);
				hodlers.add(h);
				if (hodlers.size() == 7) {
					setVPFragmentCurrent(1);
				}
			} else {
				h = (ViewHodler) view.getTag();
			}
			String path = lists.get(position).get("name");
			if (path.equals(context.getString(R.string.slid_menu_name_1))) {
				h.iv_pic.setBackgroundResource(R.drawable.blue);
			} else if (path
					.equals(context.getString(R.string.slid_menu_name_1))) {
				h.iv_pic.setBackgroundResource(R.drawable.blue);
			} else if (path
					.equals(context.getString(R.string.slid_menu_name_2))) {
				h.iv_pic.setBackgroundResource(R.drawable.set);
			} else if (path
					.equals(context.getString(R.string.slid_menu_name_3))) {
				h.iv_pic.setBackgroundResource(R.drawable.search);
			} else if (path
					.equals(context.getString(R.string.slid_menu_name_4))) {
				h.iv_pic.setBackgroundResource(R.drawable.chat);
			} else if (path
					.equals(context.getString(R.string.slid_menu_name_5))) {
				h.iv_pic.setBackgroundResource(R.drawable.blue);
			} else if (path
					.equals(context.getString(R.string.slid_menu_name_6))) {
				h.iv_pic.setBackgroundResource(R.drawable.user);
			}
			h.tv_name.setText(lists.get(position).get("name"));

			final String name = lists.get(position).get("name");
			view.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// menu.toggle();
					if (name.equals(context
							.getString(R.string.slid_menu_name_6))) {
						// context.startActivity(new Intent(context,
						// HelpActivity.class));
						Editor edit = TApplication.user_sps.edit();
						edit.putString("username", null);
						edit.putString("password", null);
						edit.commit();

						context.sendBroadcast(new Intent(Constants.ACTION_EXIT));
						// handler.obtainMessage(0).sendToTarget();
					} else if (name.equals(context
							.getString(R.string.slid_menu_name_1))) {
						setVPFragmentCurrent(0);
					} else if (name.equals(context
							.getString(R.string.slid_menu_name_2))) {
						setVPFragmentCurrent(1);
					} else if (name.equals(context
							.getString(R.string.slid_menu_name_3))) {
						setVPFragmentCurrent(2);
					} else if (name.equals(context
							.getString(R.string.slid_menu_name_4))) {
						setVPFragmentCurrent(3);
					} else if (name.equals(context
							.getString(R.string.slid_menu_name_5))) {
						setVPFragmentCurrent(4);
					}
				}
			});
			return view;
		}

		class ViewHodler {
			TextView tv_name;
			ImageView iv_pic;
			LinearLayout _ll_main_menu;
		}
	}

	public void setVPFragmentCurrent(int i) {
		for (int j = 0; j < hodlers.size(); j++) {
			hodlers.get(j)._ll_main_menu.setBackgroundColor(0x00000000);
		}
		hodlers.get(i)._ll_main_menu.setBackgroundColor(0xffeecc77);
		_vp_fragment_main.setCurrentItem(i, false);
	}

}

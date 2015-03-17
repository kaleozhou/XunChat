package com.cn.ouan.xunchat.view;

import java.util.HashMap;
import java.util.Map;

import com.cn.ouan.xunchat.R;
import com.cn.ouan.xunchat.R.layout;
import com.cn.ouan.xunchat.R.menu;
import com.cn.ouan.xunchat.biz.FriendBiz;
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
import android.widget.Toast;

public class UserInfoActivity extends Activity implements OnClickListener{

	/**加好友*/
	private Button _btn_add_friend;
	AddFriendReceiver receiver;
	private Map<String, String> userInfoMap;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_info);
		setUpView();
		setListener();
		setData();
	}
	/**
	 * 
	 */
	private void setUpView() {
		 _btn_add_friend=(Button)findViewById(R.id.btn_add_friend);
	}
	/**
	 * 
	 */
	private void setListener() {
		_btn_add_friend.setOnClickListener(this);
	}
	/**
	 * 
	 */
	private void setData() {
		IntentFilter filter=new IntentFilter();
		filter.addAction(Constants.ACTION_ADD_FRIEND);
		 receiver=new AddFriendReceiver();
		registerReceiver( receiver, filter);
		userInfoMap=new HashMap<String, String>();
		userInfoMap.put("address", getIntent().getStringExtra("address"));
		userInfoMap.put("friend_id", getIntent().getStringExtra("friend_id"));
		userInfoMap.put("name", getIntent().getStringExtra("name"));
		userInfoMap.put("age", getIntent().getStringExtra("age"));
		userInfoMap.put("gender", getIntent().getStringExtra("gender"));
	} 
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_add_friend:
			addFriend();
			break;

		}
	}
	/**
	 * 添加好友
	 */
	private void addFriend() {
		Log.i("friend_id", userInfoMap.get("friend_id"));
		new FriendBiz(this).addFriend(userInfoMap.get("friend_id"));
	}
	
	
	/**
	 * 好友添加广播
	 * @author Administrator
	 */
	class AddFriendReceiver extends BroadcastReceiver{
		@Override
		public void onReceive(Context context, Intent intent) {
			Log.i("dddddddd", "1111111111111");
			String action=intent.getAction();
			if (action.equals(Constants.ACTION_ADD_FRIEND)) {
				String flag=intent.getStringExtra("flag");
				if (flag.equals("SUCCESS")) {
				}
				Intent i=new Intent(UserInfoActivity.this,ChatActivity.class);
				i.putExtra("type", "2");
				i.putExtra("friend_id", userInfoMap.get("friend_id"));
				i.putExtra("name", userInfoMap.get("name"));
				startActivity(i);
				Toast.makeText(context, flag, Toast.LENGTH_SHORT).show();
			}
		}
	}
}

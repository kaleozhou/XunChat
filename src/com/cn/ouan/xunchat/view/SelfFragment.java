package com.cn.ouan.xunchat.view;

import java.util.HashMap;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cn.ouan.xunchat.R;
import com.cn.ouan.xunchat.biz.UserBiz;
import com.cn.ouan.xunchat.util.Constants;

public class SelfFragment extends Fragment implements OnClickListener{

	private TextView _tv_email;
	private TextView _tv_phone_number;
	private TextView _tv_address;
	private TextView _tv_age;
	private TextView _tv_nick_name;
	private Button _btn_update_user_info;
	private UserSelfReceiver receiver;
	private HashMap<String, String> usermap;
	/**打开个人基本资料控件*/
	private RelativeLayout _rl_self_info;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v= LayoutInflater.from(getActivity()).inflate(R.layout.activity_self, null);
		setUpView(v);
		setListener();
		setData();
		return v;
		
	}
	
	private void setData() {
		receiver = new UserSelfReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Constants.ACTION_USER_INFO);
		getActivity().registerReceiver(receiver, filter);
		TApplication.openProgressDialog(getActivity(), "个人中心", false);
//		UserBiz biz=new UserBiz(getActivity());
//		biz.userInfo(TApplication.user_sps.getString("username", null));
	}
	/**
	 * 初始化控件
	 * @param v
	 */
	private void setUpView(View v) {
		_tv_nick_name=(TextView)v.findViewById(R.id.tv_nick_name);
		_tv_age=(TextView)v.findViewById(R.id.tv_age);
		_tv_address=(TextView)v.findViewById(R.id.tv_address);
		_tv_phone_number=(TextView)v.findViewById(R.id.tv_phone_nubmer);
		_tv_email=(TextView)v.findViewById(R.id.tv_email);
		
		_rl_self_info=(RelativeLayout)v.findViewById(R.id.rl_self_info);
		
		_btn_update_user_info=(Button)v.findViewById(R.id.btn_update_user_info);
	}
	/**
	 * 监听
	 */
	private void setListener() {
		_btn_update_user_info.setOnClickListener(this);
		_rl_self_info.setOnClickListener(this);
	}

	class UserSelfReceiver extends BroadcastReceiver {
		

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			TApplication.cancelDilog();
			if (action.equals(Constants.ACTION_USER_INFO)) {
				usermap=(HashMap<String, String>) intent.getSerializableExtra("map");

				_tv_nick_name.setText(usermap.get("name"));
				_tv_age.setText(usermap.get("age"));
				_tv_phone_number.setText(usermap.get("phone_number"));
				_tv_address.setText(usermap.get("address"));
				_tv_email.setText(usermap.get("email"));
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_update_user_info:
			Intent i=new Intent(getActivity(),SelfUpdateActivity.class);
			i.putExtra("map", usermap);
			getActivity().startActivity(i);
			getActivity().overridePendingTransition(R.anim.in_from_right, R.anim.in_to_left);
			break;
		case R.id.rl_self_info:
			Intent i1=new Intent(getActivity(),SelfDetailedInfoActivity.class);
			getActivity().startActivity(i1);
			getActivity().overridePendingTransition(R.anim.in_from_right, R.anim.in_to_left);
			break;
		}
	}
}
package com.cn.ouan.xunchat.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cn.ouan.xunchat.R;
import com.cn.ouan.xunchat.view.ChatActivity;
import com.cn.ouan.xunchat.view.UserInfoActivity;

public class FriendListAdapter extends BaseAdapter{

	private Context context;
	private List<Map<String, String>> lists;
	private LayoutInflater layoutInflater;

	public FriendListAdapter(Context context,List<Map<String, String>> lists){
		this.context=context;
		this.layoutInflater=LayoutInflater.from(context);
		if (lists==null) {
			lists=new ArrayList<Map<String,String>>();
		}
		this.lists=lists;
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
	public View getView(final int position, View v, ViewGroup arg2) {
		ViewHodler h = null;
		if (v == null) {
			h = new ViewHodler();
			v = layoutInflater.inflate(R.layout.item_lv_user_search, null);
			h._tv_name = (TextView) v.findViewById(R.id.tv_name);
			h._iv_head = (ImageView) v.findViewById(R.id.iv_head);
			h._tv_age = (TextView) v.findViewById(R.id.tv_age);
			h._tv_gender = (TextView) v.findViewById(R.id.tv_gender);
			v.setTag(h);
		} else {
			h = (ViewHodler) v.getTag();
		}
		h._tv_name.setText(lists.get(position).get("name"));
		h._tv_age.setText(lists.get(position).get("age"));
		h._tv_gender.setText(lists.get(position).get("gender"));
		v.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i=new Intent(context,UserInfoActivity.class);
				i.putExtra("friend_id", lists.get(position).get("friend_id"));
				i.putExtra("address", lists.get(position).get("address"));
				i.putExtra("gender", lists.get(position).get("gender"));
				context.startActivity(i);
			}
		});
		return v;
	}

	class ViewHodler {
		TextView _tv_name;
		TextView _tv_age;
		TextView _tv_gender;
		ImageView _iv_head;

	}
	/**
	 * update friend list of data
	 * @param lists
	 */
public void setUpdata(List<Map<String, String>> lists){
	this.lists=lists;
	notifyDataSetChanged();
}
}

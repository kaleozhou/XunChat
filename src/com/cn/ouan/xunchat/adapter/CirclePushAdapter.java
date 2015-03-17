package com.cn.ouan.xunchat.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cn.ouan.xunchat.R;
import com.cn.ouan.xunchat.view.CircleInfoActivity;

public class CirclePushAdapter extends BaseAdapter{
	private Context context;
	private LayoutInflater layoutInflater;
	private List<Map<String, String>> lists;

	public CirclePushAdapter(Context context,List<Map<String, String>> lists){
		this.context=context;
		this.layoutInflater=LayoutInflater.from(context);
		if (lists==null) {
			this.lists=new ArrayList<Map<String,String>>();
		}else{
			this.lists=lists;			
		}
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
			v = layoutInflater.inflate(R.layout.item_lv_circle_search, null);
			h._tv_name = (TextView) v.findViewById(R.id.tv_name);
			h._iv_head = (ImageView) v.findViewById(R.id.iv_head);
			h._tv_count = (TextView) v.findViewById(R.id.tv_count);
			h._tv_type = (TextView) v.findViewById(R.id.tv_type);
			h._tv_note= (TextView) v.findViewById(R.id.tv_note);
			v.setTag(h);
		} else {
			h = (ViewHodler) v.getTag();
		}
		h._tv_name.setText(lists.get(position).get("name"));
		h._tv_count.setText(lists.get(position).get("count"));
		h._tv_type.setText(lists.get(position).get("type"));
		h._tv_note.setText(lists.get(position).get("note"));
		v.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, CircleInfoActivity.class);
				intent.putExtra("address", lists.get(position).get("address"));
				intent.putExtra("cid", lists.get(position).get("cid")); 
				intent.putExtra("name", lists.get(position).get("name"));
				intent.putExtra("note", lists.get(position).get("note"));
				intent.putExtra("count", lists.get(position).get("count"));
				context.startActivity(intent);
			}
		});
		return v;
	}
	class ViewHodler {
		TextView _tv_name;
		TextView _tv_type;
		TextView _tv_count;
		TextView _tv_note;
		ImageView _iv_head;
		
	}
	/**
	 * get push friend list
	 * @param list 内容
	 * @param type 0用户1圈子
	 */
	public void setFriendPushlist(List<Map<String, String>> lists){
		 this.lists=lists;
		 notifyDataSetChanged();
		}
	
}

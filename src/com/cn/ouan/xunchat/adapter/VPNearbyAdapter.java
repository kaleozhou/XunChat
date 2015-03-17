package com.cn.ouan.xunchat.adapter;

import java.util.List;
import java.util.Map;

import com.cn.ouan.xunchat.R; 
import com.cn.ouan.xunchat.view.ChatActivity;
import com.cn.ouan.xunchat.view.CircleInfoActivity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager; 
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class VPNearbyAdapter extends PagerAdapter{

	private List<View> views;
	private Context context;
	private NearbyUserAdapter nearbyUser;
	private NearbyCircleAdapter nearbyCircle;
	private ListView _lv_near_user;
	List<Map<String, String>> userlist;
	List<Map<String, String>> circlelist;
	private ListView _lv_near_circle;
	private View v0;
	private View v1;
	
	public VPNearbyAdapter(Context context,List<View> views){
		this.context=context;
		this.views=views;
		 v0=views.get(0);
		_lv_near_user=(ListView)v0.findViewById(R.id.lv_search);
		  v1=views.get(1);
		_lv_near_circle=(ListView)v1.findViewById(R.id.lv_search);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
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
		View v;
		if (position==0) {
			v=v0;
		}else{
			v=v1;
		}
		((ViewPager) container).addView(v, 0); 
		return v;
	}
	
	/**
	 * 更新附近用户或圈子的内容
	 * @param list 内容
	 * @param type 0用户1圈子
	 */
	public void setNearbylist(List<Map<String, String>> list,int type){
		 
		if(type==0){
			if (nearbyUser==null) {
				nearbyUser=new NearbyUserAdapter(list);
				_lv_near_user.setAdapter(nearbyUser);
			}else{
				nearbyUser.updateUser(list);
			}
		}else{
			if (nearbyCircle==null) {
				nearbyCircle=new NearbyCircleAdapter(list);
				_lv_near_circle.setAdapter(nearbyCircle);
			}
			nearbyCircle.updateCircle(list);
			
		}
	}
	
	
	
	
	class NearbyUserAdapter extends BaseAdapter{
 
		private LayoutInflater layoutInflater;
		private List<Map<String, String>> lists;

		public NearbyUserAdapter(List<Map<String, String>> lists) {
			this.layoutInflater = LayoutInflater.from(context);
			Log.i("nearby",""+ lists.size());
			this.lists = lists;
		}

		public void updateUser(List<Map<String, String>> list) {
			this.lists=list;
			notifyDataSetChanged();
			
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
		public View getView(final int position, View v, ViewGroup parsent) {
			ViewHodler h = null;
			Log.i("nearby", "进入getView");
			if (v == null) {
				Log.i("nearby", "创建一个item");
				h = new ViewHodler();
				v = layoutInflater.inflate(R.layout.item_lv_user_search, null);
				h._tv_name = (TextView) v.findViewById(R.id.tv_name);
				h._iv_head = (ImageView) v.findViewById(R.id.iv_head);
				h._tv_age = (TextView) v.findViewById(R.id.tv_age);
				h._tv_gender = (TextView) v.findViewById(R.id.tv_gender);
				v.setTag(h);
			} else {
				Log.i("nearby", "重用一个item");
				h = (ViewHodler) v.getTag();
			}
			Log.i("nearby", "item赋值");
			h._tv_name.setText(lists.get(position).get("name"));
			h._tv_age.setText(lists.get(position).get("age"));
			h._tv_gender.setText(lists.get(position).get("gender"));
			Log.i("nearby", "item添加监听");
			v.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(context, ChatActivity.class);
					intent.putExtra("address", lists.get(position).get("address"));
					intent.putExtra("name", lists.get(position).get("name"));
					intent.putExtra("age", lists.get(position).get("age"));
					intent.putExtra("gender", lists.get(position).get("gender"));
					intent.putExtra("client_id", lists.get(position).get("client_id"));
					intent.putExtra("type", "1");
					context.startActivity(intent);
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
		
	}
	class NearbyCircleAdapter extends BaseAdapter{
 
		private LayoutInflater layoutInflater;
		private List<Map<String, String>> lists;

		public NearbyCircleAdapter(List<Map<String, String>> lists) {
			this.layoutInflater = LayoutInflater.from(context);
			Log.i("nearby",""+ lists.size());
			this.lists = lists;
		}
		/**
		 * 更新附近圈子
		 * @param list 数据
		 */
		public void updateCircle(List<Map<String, String>> list) {
			this.lists=list;
			notifyDataSetChanged(); 
		}

		public void updateUser(List<Map<String, String>> list) {
			this.lists=list;
			notifyDataSetChanged();
			
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
		public View getView(final int position, View v, ViewGroup parsent) {
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
		
	}
}

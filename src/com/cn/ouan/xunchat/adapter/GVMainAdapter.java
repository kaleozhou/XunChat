package com.cn.ouan.xunchat.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cn.ouan.xunchat.R;
import com.cn.ouan.xunchat.util.Constants;
import com.cn.ouan.xunchat.view.CircleFragment;
import com.cn.ouan.xunchat.view.CircleFriendFragment;
import com.cn.ouan.xunchat.view.FacilityFragment;
import com.cn.ouan.xunchat.view.HelpActivity;
import com.cn.ouan.xunchat.view.LoginActivity;
import com.cn.ouan.xunchat.view.SearchActivity;
import com.cn.ouan.xunchat.view.SelfFragment;
import com.cn.ouan.xunchat.view.TApplication;

public class GVMainAdapter extends BaseAdapter {
	private Context context;
	LayoutInflater layoutInflater;
	List<Map<String, String>> lists;
	Handler handler=new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				Toast.makeText(context, "Î´¿ª·Å", Toast.LENGTH_SHORT).show();
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
			view.setTag(h);
		} else {
			h = (ViewHodler) view.getTag();
		}
		String path = lists.get(position).get("name");
		if (path.equals(context.getString(R.string.slid_menu_name_1))) {
			h.iv_pic.setBackgroundResource(R.drawable.blue);

		} else if (path.equals(context.getString(R.string.slid_menu_name_1))) {
			h.iv_pic.setBackgroundResource(R.drawable.blue);
		} else if (path.equals(context.getString(R.string.slid_menu_name_2))) {
			h.iv_pic.setBackgroundResource(R.drawable.set);
		} else if (path.equals(context.getString(R.string.slid_menu_name_3))) {
			h.iv_pic.setBackgroundResource(R.drawable.search);
		} else if (path.equals(context.getString(R.string.slid_menu_name_4))) {
			h.iv_pic.setBackgroundResource(R.drawable.chat);
		} else if (path.equals(context.getString(R.string.slid_menu_name_5))) {
			h.iv_pic.setBackgroundResource(R.drawable.blue);
		} else if (path.equals(context.getString(R.string.slid_menu_name_6))) {
			h.iv_pic.setBackgroundResource(R.drawable.user);
		}
		h.tv_name.setText(lists.get(position).get("name"));

		final String name = lists.get(position).get("name");
		view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				 if (name.equals(context.getString(R.string.slid_menu_name_6))) {
//					context.startActivity(new Intent(context,
//							HelpActivity.class));
					Editor edit=TApplication.user_sps.edit();
					edit.putString("username", null);
					edit.putString("password", null);
					edit.commit();
					context.sendBroadcast(new Intent(Constants.ACTION_EXIT));
//					handler.obtainMessage(0).sendToTarget();
				} else if (name.equals(context.getString(R.string.slid_menu_name_1))) {
					context.startActivity(new Intent(context,
							FacilityFragment.class));
				} else if (name.equals(context.getString(R.string.slid_menu_name_2))) {
					context.startActivity(new Intent(context,
							SelfFragment.class));
				} else if (name.equals(context.getString(R.string.slid_menu_name_3))) {
					context.startActivity(new Intent(context,
							CircleFriendFragment.class));
				} else if (name.equals(context.getString(R.string.slid_menu_name_4))) {
					context.startActivity(new Intent(context,
							CircleFragment.class)); 
				} else if (name.equals(context.getString(R.string.slid_menu_name_5))) {


					context.startActivity(new Intent(context,
							HelpActivity.class));
				}
			}
		});
		return view;
	}

	class ViewHodler {
		TextView tv_name;
		ImageView iv_pic;
	}

}

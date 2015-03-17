package com.cn.ouan.xunchat.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cn.ouan.xunchat.R;
import com.cn.ouan.xunchat.view.ChatActivity;
/**
 *  
 * @author Administrator
 *
 */
public class ChatAdapter extends BaseAdapter{

	private Context context;
	private LayoutInflater layoutInflater;
	private List<Map<String, String>> lists;

	public ChatAdapter(Context context,List<Map<String, String>> lists){
		this.context=context;
		this.layoutInflater=LayoutInflater.from(context);
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
	public View getView(final int position, View v, ViewGroup parsent) {
		ViewHodler h=null;
			h=new ViewHodler();
			if(lists.get(position).get("user_type").equals("from")){
				v=layoutInflater.inflate(R.layout.item_chat_left, null);			
			}else if(lists.get(position).get("user_type").equals("to")){
				v=layoutInflater.inflate(R.layout.item_chat_right, null);				
			}
			h._tv_message=(TextView)v.findViewById(R.id.tv_message); 
			h._iv_head=(ImageView)v.findViewById(R.id.iv_head);
//			v.setTag(h);
//		else {
//			h=(ViewHodler) v.getTag();
//		}
			if(lists.get(position).get("message_type").equals("phiz")){
				final String path=lists.get(position).get("path");
				String m="a";
				SpannableStringBuilder sp=new SpannableStringBuilder(m);
				ImageSpan imageSpan = new ImageSpan(context,
						R.drawable.chat_from_ghiz_static);
				sp.setSpan(imageSpan, 0, m.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				h._tv_message.setText(sp);
				v.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent intent=new Intent("start_music");
						intent.putExtra("music_path", path);
						context.sendBroadcast(intent);
					}
				});
			}else if(lists.get(position).get("message_type").equals("message")){				
				h._tv_message.setText(lists.get(position).get("message"));
			}
//		h._iv_head.setImageResource(R.drawable.user);
		return v;
	}
	/**
	 * update chat list
	 * @param lists
	 */
	public void setUpdateAdapter(List<Map<String, String>> lists) {
		this.lists=lists;
		notifyDataSetChanged();
	}
	class ViewHodler{
		ImageView _iv_head;
		TextView _tv_message; 
	}
	
}

package com.cn.ouan.xunchat.biz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;

import com.cn.ouan.xunchat.listener.CallBackListener;
import com.cn.ouan.xunchat.util.Constants;
import com.cn.ouan.xunchat.util.FriendUtil;
import com.cn.ouan.xunchat.view.TApplication;

/**
 * 好友操作类
 * 
 * @author Administrator
 */
public class FriendBiz {
	private Context context;

	public FriendBiz(Context context) {
		this.context = context;

	}

	/**
	 * 添加好友
	 * 
	 * @param friend_id
	 */
	public void addFriend(String friend_id) {
		FriendUtil.addFriend(friend_id, new CallBackListener() {

			@Override
			public void callBackListener(Object result) {
				try {
					TApplication.cancelDilog();
					JSONObject json = (JSONObject) result;
					Intent i = new Intent(Constants.ACTION_ADD_FRIEND);
					i.putExtra("flag", json.getString("flag"));
					i.putExtra("mag", json.getString("mag"));
					i.putExtra("chat_type", "2");
					context.sendBroadcast(i);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * 所有好友
	 */
	public void AllFriend() {
		FriendUtil.AllFriend(new CallBackListener() {

			@Override
			public void callBackListener(Object result) {
				ArrayList<Map<String, String>> lists = new ArrayList<Map<String, String>>();
				try {
					TApplication.cancelDilog();
					JSONArray jsonarray = (JSONArray) result;
					Map<String, String> map = null;
					for (int i = 0; i < jsonarray.length(); i++) {
						map = new HashMap<String, String>();
						JSONObject json = jsonarray.getJSONObject(i);
						map.put("friend_id", json.getString("friend_id"));
						map.put("age", json.getString("age"));
						map.put("name", json.getString("username"));
						map.put("gender", json.getString("gender"));
 
						lists.add(map);
					}
					Intent i=new Intent(Constants.ACTION_ALL_FRIEND);
					i.putExtra("maps", lists);
					context.sendBroadcast(i);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * 所有陌生人
	 */
	public void AllStranger() {
		FriendUtil.AllStranger(new CallBackListener() {
			
			@Override
			public void callBackListener(Object result) {
				ArrayList<Map<String, String>> lists = new ArrayList<Map<String, String>>();
				try {
					TApplication.cancelDilog();
					JSONArray jsonarray = (JSONArray) result;
					Map<String, String> map = null;
					for (int i = 0; i < jsonarray.length(); i++) {
						map = new HashMap<String, String>();
						JSONObject json = jsonarray.getJSONObject(i);
						map.put("friend_id", json.getString("friend_id"));
						map.put("name", json.getString("name"));
						map.put("age", json.getString("age"));
						map.put("gender", json.getString("gender"));
						lists.add(map);
					}
					Intent i=new Intent(Constants.ACTION_ALL_STRANGER);
					i.putExtra("maps", lists);
					context.sendBroadcast(i);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

}

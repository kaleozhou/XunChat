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
import android.util.Log;

import com.cn.ouan.xunchat.listener.CallBackListener;
import com.cn.ouan.xunchat.util.CircleUtil;
import com.cn.ouan.xunchat.util.Constants;
import com.cn.ouan.xunchat.view.TApplication;

public class CircleBiz {

	private Context context;

	public CircleBiz(Context context) {
		this.context = context;
	}

	/**
	 * get nearby user info
	 */
	public void getusers() {
		List<Map<String, String>> maps = null;
		CircleUtil circle = new CircleUtil();
		circle.users(new CallBackListener() {
			@Override
			public void callBackListener(Object result) {
				Log.i("post-开始----", result.toString());
				ArrayList<Map<String, String>> lists = new ArrayList<Map<String, String>>();
				Map<String, String> map = null;
				JSONArray jsonarray;
				try {
					TApplication.cancelDilog();
					jsonarray = ( JSONArray)result;

					Log.i("post-结束----", "" + jsonarray.length());
					for (int i = 0; i < jsonarray.length(); i++) {
						JSONObject json = jsonarray.getJSONObject(i);
						map = new HashMap<String, String>();
						map.put("name", json.getString("name"));
						map.put("age", json.getString("age"));
						map.put("gender", json.getString("gender"));
						map.put("friend_id", json.getString("friend_id"));
						lists.add(map);
					}
					Intent i = new Intent(Constants.ACTION_NEARBY_CIRLCE);
					i.putExtra("maps", lists);
					i.putExtra("type", 0);
					context.sendBroadcast(i);
					Log.i("post-结束----", result.toString());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * get nearby circle info
	 */
	public void getcircles() {
		List<Map<String, String>> maps = null;
		CircleUtil circle = new CircleUtil();
		circle.circles(new CallBackListener() {
			@Override
			public void callBackListener(Object result) {
				Log.i("post-开始----", result.toString());
				ArrayList<Map<String, String>> lists = new ArrayList<Map<String, String>>();
				Map<String, String> map = null;
				JSONArray jsonarray;
				try {
					TApplication.cancelDilog();
					jsonarray = (JSONArray)result;

					Log.i("post-结束----", "" + jsonarray.length());
					for (int i = 0; i < jsonarray.length(); i++) {
						JSONObject json = jsonarray.getJSONObject(i);
						map = new HashMap<String, String>();
						map.put("name", json.getString("nick"));
						map.put("cid", json.getString("cid"));
						Log.i("cid-1", json.getString("cid"));
						map.put("type", json.getString("type"));
						map.put("address", json.getString("address"));
						map.put("count", json.getString("count"));
						map.put("note", json.getString("note"));
						lists.add(map);
					}
					Intent i = new Intent(Constants.ACTION_NEARBY_CIRLCE);
					i.putExtra("maps", lists);
					i.putExtra("type", 1);
					context.sendBroadcast(i);
					Log.i("post-结束----", result.toString());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * create a circle
	 * @param map
	 */
	public void createCircle(Map<String, String> map) {
		CircleUtil circle = new CircleUtil();
		circle.createCircle(map, new CallBackListener() {

			@Override
			public void callBackListener(Object result) {
				JSONObject json;
				try {
					TApplication.cancelDilog();
					json = (JSONObject)result;
					Intent i = new Intent(Constants.ACTION_CREATE_CIRCLE);
					i.putExtra("flag", json.getString("flag"));
					i.putExtra("mag", json.getString("mag"));
					context.sendBroadcast(i);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
	}
	
	public void addCircle(String cid){
		CircleUtil circle=new CircleUtil();
		circle.addCircle(cid,new CallBackListener() {
			
			@Override
			public void callBackListener(Object result) {
				JSONObject json;
				try {
					TApplication.cancelDilog();
					json = (JSONObject)result;
					Intent i = new Intent(Constants.ACTION_ADD_CIRCLE);
					i.putExtra("flag", json.getString("flag"));
					i.putExtra("mag", json.getString("mag"));
					context.sendBroadcast(i);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
	}

}

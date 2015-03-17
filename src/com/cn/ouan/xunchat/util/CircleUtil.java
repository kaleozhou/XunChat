package com.cn.ouan.xunchat.util;

import java.util.Map;

import android.util.Log;

import com.cn.ouan.xunchat.listener.CallBackListener;
import com.cn.ouan.xunchat.view.TApplication;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class CircleUtil {

	/**
	 * 
	 * @param callback
	 * @return 
	 */
	public void circles(final CallBackListener callback){
		TApplication.httpclient.post(Constants.URL_CIRCLE_CIRCLE, new  AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, String content) { 
				callback.callBackListener(content);
			}
			@Override
			public void onFailure(Throwable error, String content) {
				Log.i("post---------", content);
				callback.callBackListener(content);
			}
		});
	}	/**
	 * 
	 * @param callback
	 * @return
	 */
	public void users(final CallBackListener callback){
		TApplication.httpclient.post(Constants.URL_CIRCLE_FRIEND, new  AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, String content) { 
				callback.callBackListener(content);
			}
			@Override
			public void onFailure(Throwable error, String content) {
				Log.i("post---------", content);
				callback.callBackListener(content);
			}
		});
	}
	public void createCircle(Map<String, String> map,final CallBackListener callback){
		RequestParams params=new RequestParams();
		params.put("uid", map.get("uid"));
		params.put("name", map.get("name"));
		params.put("address", map.get("address"));
		params.put("note", map.get("note"));
		params.put("nick", map.get("nick"));
		params.put("type", map.get("type"));
		params.put("longitude", map.get("longitude"));
		params.put("latitude",map.get("latitude"));
		Log.i("creation circle", map.get("name")+"  "+map.get("address"));
		TApplication.httpclient.post(Constants.URL_CIRCLE_CREATE, params,new  AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, String content) { 
				callback.callBackListener(content);
			}
			@Override
			public void onFailure(Throwable error, String content) {
				Log.i("post---------", content);
				callback.callBackListener(content);
			}
		});
	}
	/**
	 * 添加到圈子成员
	 * @param cid 圈子id 
	 * @param callback 回调
	 */
	public void addCircle(String cid,final CallBackListener callback){
		RequestParams params=new RequestParams();
		params.put("cid", cid);
		params.put("name", TApplication.user_sps.getString("username", null));
		TApplication.httpclient.post(Constants.URL_CIRCLE_ADD_MEMBER, params, new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, String content) { 
				callback.callBackListener(content);
			}
			@Override
			public void onFailure(Throwable error, String content) {
				Log.i("post---------", content);
				callback.callBackListener(content);
			}
		});
	}
	
}

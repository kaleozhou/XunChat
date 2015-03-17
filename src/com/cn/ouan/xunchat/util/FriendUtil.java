package com.cn.ouan.xunchat.util;

import android.util.Log;

import com.cn.ouan.xunchat.listener.CallBackListener;
import com.cn.ouan.xunchat.service.PushService;
import com.cn.ouan.xunchat.view.TApplication;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class FriendUtil {

	public static void addFriend(String friend_id,final CallBackListener callback){
		RequestParams params=new RequestParams();
		params.put("uid", TApplication.user_sps.getString("username", null));
		params.put("friend_id", friend_id);
		TApplication.httpclient.post(Constants.URL_FRIEND_ADD, params, new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, String content) {
				Log.i("post---------", content);
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
	 * 得到用户所有好友
	 * @param callback
	 */
	public static void AllFriend(final CallBackListener callback) {
		RequestParams params=new RequestParams();
		params.put("uid", TApplication.user_sps.getString("username", null));
		TApplication.httpclient.post(Constants.URL_FRIEND_ALL, params, new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, String content) {
				Log.i("post---------", content);
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
	 * 得到用户所有陌生人
	 * @param callback
	 */
	public static void AllStranger(final CallBackListener callback) {
		RequestParams params=new RequestParams();
		params.put("uid", TApplication.user_sps.getString("username", null));
		TApplication.httpclient.post(Constants.URL_STRANGER_ALL, params, new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, String content) {
				Log.i("post---------", content);
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

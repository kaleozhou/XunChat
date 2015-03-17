package com.cn.ouan.xunchat.util;

import android.util.Log;

import com.cn.ouan.xunchat.bean.UserBean;
import com.cn.ouan.xunchat.listener.CallBackListener;
import com.cn.ouan.xunchat.view.TApplication;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class MessageUtil {
	/**
	 * ∑¢ÀÕ–≈œ¢
	 * @param friend
	 * @param friend_client_id
	 * @param text
	 * @param callback
	 */
	public void sendMessage(String friendId,  
			String text, final CallBackListener callback) {
		UserBean user=TApplication.userBean;
		RequestParams params=new RequestParams(); 
		params.put("friend_uid", friendId);
		params.put("uid", user.getUid());
		params.put("name", user.getName());
		params.put("age", user.getAge());
		params.put("gender", user.getGender());
		params.put("message", text);
		TApplication.httpclient.post(Constants.URL_MESSAGE_SEND,params, new AsyncHttpResponseHandler(){
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
	 * send circle message
	 * @param cid
	 * @param name
	 * @param message
	 * @param callback
	 */
	public void sendMessageCircle(String cid,String name,String message,final CallBackListener callback){
		RequestParams params=new RequestParams();
		params.put("cid", cid);
		params.put("name", name);
		params.put("message", message);
		TApplication.httpclient.post(Constants.URL_MESSAGE_CIRCLE_SEND, params,new AsyncHttpResponseHandler(){
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

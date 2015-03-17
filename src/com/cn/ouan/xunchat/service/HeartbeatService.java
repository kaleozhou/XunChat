package com.cn.ouan.xunchat.service;

import com.cn.ouan.xunchat.util.Constants;
import com.cn.ouan.xunchat.view.TApplication;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

public class HeartbeatService extends Service{

	Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				sendHeartbeat();				
				break;
			}
		}
	};
	@Override
	public void onCreate() {
		sendHeartbeat();
	}
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void sendHeartbeat(){
		new Thread(){
			@Override
			public void run() {
				RequestParams params=new RequestParams();
				params.put("name", TApplication.user_sps.getString("username", null));
				params.put("client_id", TApplication.user_sps.getString(PushService.PREF_DEVICE_ID, null));
				TApplication.httpclient.post(Constants.URL_HEARTBEAT, params,new AsyncHttpResponseHandler(){
					@Override
					public void onSuccess(int statusCode, String content) {
						try {
							sleep(30000);
							handler.sendEmptyMessage(0);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
					@Override
					public void onFailure(Throwable error, String content) {
						Log.i("post---------", content);
					}
				});
			}
		}.start();
	}
	
}

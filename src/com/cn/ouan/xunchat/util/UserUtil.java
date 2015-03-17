package com.cn.ouan.xunchat.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

import com.cn.ouan.xunchat.listener.CallBackListener;
import com.cn.ouan.xunchat.view.TApplication;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * �û�������
 * 
 * @author Administrator
 */
public class UserUtil {
	/**
	 * �û���¼
	 * 
	 * @param username
	 *            �û���
	 * @param password
	 *            �û�����
	 * @param callback
	 *            �ص�
	 */
	public static void Login(String username, String password,
			final CallBackListener callback) {
		RequestParams params = new RequestParams();
		Map<String, String> map = new HashMap<String, String>();
		map.put("FuName", username);
		map.put("FuPassword", password);
		params.put("value", HttpUtil.dataMap2Str(map));
		Log.i("login", "url: " + Constants.URL_LOGIN + "  username: "
				+ password);
		TApplication.httpclient.post(Constants.URL_LOGIN, params,
				new JsonHttpResponseHandler() {
		 
					@Override
					public void onSuccess(int statusCode, JSONObject content) {
						Log.i("post��ȷ---------", content.toString());
						callback.callBackListener(content);
					}

					@Override
					public void onFailure(Throwable error, JSONObject content) {
						Log.i("post����---------", content.toString());
						callback.callBackListener(content);
					}
					
				});
	}

	/**
	 * �û�ע��
	 * 
	 * @param map
	 *            �û���Ϣʵ����
	 * @param callback
	 *            �ص�
	 */
	public static void Regist(Map<String, String> map,
			final CallBackListener callback) {
		RequestParams params = new RequestParams();
		params.put("value", HttpUtil.dataMap2Str(map));
		TApplication.httpclient.post(Constants.URL_REGIST, params,
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, JSONObject content) {
						callback.callBackListener(content);
					}

					@Override
					public void onFailure(Throwable error, JSONObject content) {
						callback.callBackListener(content);
					}
				});
	}

	/**
	 * ��ȡ�û���Ϣ
	 * 
	 * @param Uid
	 * @param sessionid
	 *            * @param callback �ص�
	 */
	public static void UserInfo(String uid, final CallBackListener callback) {
		RequestParams params = new RequestParams();
		params.put("uid", uid);
		TApplication.httpclient.post(Constants.URL_USER_INFO, params,
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, JSONObject content) {
						callback.callBackListener(content);
					}

					@Override
					public void onFailure(Throwable error, JSONObject content) {
						callback.callBackListener(content);
					}
				});
	}

	/**
	 * �޸��û���Ϣ
	 * 
	 * @param map
	 * @param callback
	 */
	public static void updateUserInfo(Map<String, String> map,
			final CallBackListener callback) {
		RequestParams params = new RequestParams();
		params.put("uid", map.get("uid"));
		params.put("name", map.get("name"));
		params.put("age", map.get("age"));
		params.put("address", map.get("address"));
		params.put("phone_number", map.get("phone_number"));
		params.put("email", map.get("email"));
		TApplication.httpclient.post(Constants.URL_USER_UPDATE_INFO, params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, String content) {
						Log.i("post---udateinfo--", content);
						callback.callBackListener(content);
					}

					@Override
					public void onFailure(Throwable error, String content) {
						Log.i("post---udateinfo--", content);
						callback.callBackListener(content);
					}
				});
	}

	/**
	 * �ϴ�ͷ��
	 * 
	 * @param path
	 * @param name
	 * @param callBackListener
	 */
	public static void uploadimg(String path, String name,
			CallBackListener callBackListener) {
		String url = "https://ajax.googleapis.com/ajax/services/search/images";
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("q", "android");
		params.put("rsz", "8");
		client.get(url, params, new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, JSONObject response) {
				// Handle resulting parsed JSON response here
			}

			@Override
			public void onSuccess(int statusCode, JSONArray response) {
				// Handle resulting parsed JSON response here
			}
		});

	}

	/**
	 * ������֤��
	 * 
	 * @param Teleph
	 * @param type
	 */
	public static void getVerification(String teleph, String type) {
		RequestParams params = new RequestParams();
		Map<String, String> map = new HashMap<String, String>();
		map.put("FuTeleph", teleph);
		map.put("Type", type);
		params.put("value", HttpUtil.dataMap2Str(map));
		TApplication.httpclient.post(Constants.URL_VERIFY, params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, String content) {
						Log.i("post---udateinfo--", content);
					}

					@Override
					public void onFailure(Throwable error, String content) {
						Log.i("post---udateinfo--", content);
					}
				});

	}

}

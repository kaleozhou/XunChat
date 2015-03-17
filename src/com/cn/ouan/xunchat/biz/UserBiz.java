package com.cn.ouan.xunchat.biz;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.cn.ouan.xunchat.bean.UserBean;
import com.cn.ouan.xunchat.listener.CallBackListener;
import com.cn.ouan.xunchat.util.Constants;
import com.cn.ouan.xunchat.util.UserUtil;
import com.cn.ouan.xunchat.view.TApplication;

public class UserBiz {

	private Context context;
	String password;
	String username;

	public UserBiz(Context context) {
		this.context = context;
	}

	/**
	 * 用户登录
	 */
	public void Login() {
		UserUtil.Login(username, password, new CallBackListener() {
			@Override
			public void callBackListener(Object result) {
				try {
					Intent i = new Intent(Constants.ACTION_LOGIN);
					JSONObject json = (JSONObject) result;
					TApplication.cancelDilog();
					
					String ret = json.getString("Ret");
					if (ret.equals("1")) {
						i.putExtra("Ret", json.getString("Ret"));
						i.putExtra("Msg", json.getString("Msg"));
						JSONObject date=new JSONObject(json.getString("Data"));
						TApplication.user_sps
						.edit()
						.putString("Session_id",
								date.getString("Session_Id"))
						.putString("username", username)
						.putString("password", password).commit();
						context.sendBroadcast(i);
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 用户登录
	 * 
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 */
	public void Login(String username, String password) {
		this.password = password;
		this.username = username;
		Login();

	}

	/**
	 * 注册
	 * 
	 * @param user
	 */
	public void regist(Map<String, String> map) {
		UserUtil.Regist(map, new CallBackListener() {

			@Override
			public void callBackListener(Object result) {
				try {
					TApplication.cancelDilog();
					Log.i("regist_result", result.toString());
					JSONObject json = (JSONObject) result;
					String Ret = json.getString("Ret");
					String msg = json.getString("Msg");
					Intent i = new Intent(Constants.ACTION_REGIST);
					i.putExtra("Ret", Ret);
					i.putExtra("Msg", msg);
					context.sendBroadcast(i);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
	}

	/**
	 * 上传图片
	 * 
	 * @param path
	 * @param name
	 */
	public void uploadimg(String path, String name) {
		UserUtil.uploadimg(path, name, new CallBackListener() {

			@Override
			public void callBackListener(Object result) {
				// TODO Auto-generated method stub

			}

		});
	}

	public void userInfo(String uid) {
		UserUtil.UserInfo(uid, new CallBackListener() {

			@Override
			public void callBackListener(Object result) {
				TApplication.cancelDilog();
				Intent i = new Intent(Constants.ACTION_USER_INFO);
				i.putExtra("jsonResult", result.toString());
				context.sendBroadcast(i);
			}
		});
	}

	/**
	 * 修改用户信息
	 * 
	 * @param map
	 */
	public void updateUserInfo(Map<String, String> map) {
		UserUtil.updateUserInfo(map, new CallBackListener() {
			@Override
			public void callBackListener(Object result) {
				try {
					TApplication.cancelDilog();
					JSONObject json = (JSONObject) result;
					Intent i = new Intent(Constants.ACTION_UPDATA_USER_INFO);
					i.putExtra("flag", json.getString("flag"));
					i.putExtra("mag", json.getString("mag"));
					context.sendBroadcast(i);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 请求验证码
	 * 
	 * @param Teleph
	 * @param type
	 */
	public void getVerification(String Teleph, String type) {
		UserUtil.getVerification(Teleph, type);
	}
}

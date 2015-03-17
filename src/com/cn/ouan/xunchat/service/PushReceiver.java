package com.cn.ouan.xunchat.service;

import java.util.ArrayList; 
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler; 
import android.util.Log;

import com.cn.ouan.xunchat.R;
import com.cn.ouan.xunchat.db.DBManager; 

public class PushReceiver extends BroadcastReceiver {

	// private static final String TAG = "MyReceiver";

	private static final int RELOGIN = 0;

	private static final String TAG = "PushReceiver";

	private Handler handler;
	private String push_id;

	private Dialog dialog;
	private Context dContext;
	private String time, dTime;
	private String body;
	private SharedPreferences preferences;
	private Editor editor;
	private Map<String, String> map = null;
	private ArrayList<Map<String, String>> maps = new ArrayList<Map<String, String>>();
	private DBManager mgr;

	@Override
	public void onReceive(Context context, Intent intent) {

		if (intent != null) {
			Bundle bundle = intent.getExtras(); // call
																	// service
																	// for
																	// MusicReceiverService.class

			dContext = context;
			mgr = new DBManager(dContext);
 
			if (bundle != null) {
				try {
					String s = bundle.getString("op");
					Log.i("json", s);
					JSONObject jObject = new JSONObject(s);

					// if
					// ("FORCE_OUTLINE".equals(jObject.get("flag").toString())
					// && !"".equals(SupermarketAppliction.getSessionId())) {
					//
					// // 被迫下线；
					// time = jObject.get("outline_time").toString();
					// // ConfigPrams.isLogin = false;
					// // dialog_logout();
					//
					// }
 
					// 获取地址信息
					preferences = dContext.getSharedPreferences("address",
							dContext.MODE_PRIVATE);
					String date = preferences.getString("push_time",
							"1970-01-01");
					if("ADD_FRIENDS".equals(jObject.getString("flag"))){ }
					if (!"IMMEDIATELY_PUSH".equals(jObject.getString("flag"))) {
						Log.i("json-add1", "ADD_FRIENDS == "+ jObject.getString("flag"));
						editor = preferences.edit();// 获取编辑器
						editor.putString("push_time", jObject.getString("tag")); // 进入首页次数
						editor.commit();// 提交修改
					}
					if ("INTERACTION_MESS".equals(jObject.getString("flag"))) { } 
					if ("ANNOUNCEMENT".equals(jObject.getString("flag"))) {// 公告
 
						// dContext.startActivity(itAnnountment);
						//
						// 接收成功，返回服务器信息
						// pushThread pThread = new pushThread();
						// pThread.start();

					} else if ("IMMEDIATELY_PUSH".equals(jObject
							.getString("flag"))) { } else if ("SEND_MESSAGE".equals(jObject.getString("flag"))) {// 发送短信

						dTime = jObject.getString("datetime");
						// Log.d(TAG, "dTime=========" + dTime);
						body = jObject.getString("content");
						ContentValues values = new ContentValues();
						values.put("date", dTime);
						values.put("read", 0);
						values.put("type", 1);
						values.put("body", body);
						values.put("address", "3+6您身边的超市");
						dContext.getContentResolver().insert(
								Uri.parse("content://sms"), values);
						notifyUser();

						// 接收成功，返回服务器信息
						pushThread pThread = new pushThread();
						pThread.start();
					} else { }

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			// 重新登录；
			// handler = new Handler() {
			//
			// public void handleMessage(Message msg) {
			// switch (msg.what) {
			// case RELOGIN:
			// try {
			// JSONObject jObject = new JSONObject(
			// msg.obj.toString());
			// if ("SUCCESS"
			// .equals(jObject.get("flag").toString())) {
			// ConfigPrams.isLogin = true;
			// }
			// } catch (JSONException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			// break;
			//
			// default:
			// break;
			// }
			// }
			// };
		}
	}

	// public void dialog_logout() {
	//
	// // 退出的提示弹框；
	// dialog = new Dialog(dContext, R.style.DialogStyleTrans);
	// dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	// dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	// WindowManager.LayoutParams.FLAG_FULLSCREEN);
	// dialog.setContentView(R.layout.pop_prom_window);
	// dialog.getWindow()
	// .setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
	// dialog.setCancelable(false);
	//
	// TextView tv_prom_tit = (TextView) dialog.findViewById(R.id.tv_prom_tit);
	// tv_prom_tit.setText("3+6网上超市友情提示：");
	// TextView tv_prom_mess = (TextView) dialog
	// .findViewById(R.id.tv_prom_mess);
	// tv_prom_mess.setText("您的账号于" + time
	// + "在另一台 Android 手机登录。如非本人操作，则密码可能已泄露，建议及时修改密码。");
	// LinearLayout ll_btn_two_box = (LinearLayout) dialog
	// .findViewById(R.id.ll_btn_two_box);
	// ll_btn_two_box.setVisibility(View.VISIBLE);
	// Button btn_two_first = (Button) dialog.findViewById(R.id.btn_two_first);
	// btn_two_first.setText("退 出");
	// btn_two_first.setOnClickListener(new OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// // TODO Auto-generated method stub
	// dialog.cancel();
	// PushService.actionStop(dContext);
	// ExitApplication.getInstance().exit();
	// }
	// });
	// Button btn_two_second = (Button) dialog
	// .findViewById(R.id.btn_two_second);
	// btn_two_second.setText("重新登录");
	// btn_two_second.setOnClickListener(new OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// // TODO Auto-generated method stub
	// GetAddressThread aThread = new GetAddressThread();
	// aThread.start();
	// dialog.cancel();
	// }
	// });
	// dialog.show();
	// }
	//
	// /*
	// * 重新登录；
	// */
	// public class GetAddressThread extends Thread {
	//
	// public void run() {
	// System.out.println();
	// Message pMessage = new Message();
	// HttpClients httpClient = new HttpClients(dContext);
	// List<NameValuePair> pairs = new ArrayList<NameValuePair>();
	// pairs.add(new BasicNameValuePair("username", ConfigPrams.username));
	// pairs.add(new BasicNameValuePair("password", ConfigPrams.password));
	// pairs.add(new BasicNameValuePair("clientId", ConfigPrams.clientid));
	// pairs.add(new BasicNameValuePair("relogin", "1"));
	// String str = httpClient.doPost(ConfigPrams.APP_PATH + "login/login.php",
	// pairs);
	// pMessage.obj = str;
	// pMessage.what = RELOGIN;
	// System.out.println(str);
	// handler.sendMessage(pMessage);
	// }
	// }

	public void notifyUser() {
		NotificationManager nm = (NotificationManager) dContext
				.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification n = new Notification(R.drawable.icon, "3+6您身边的的超市",
				System.currentTimeMillis());
		CharSequence contentTitle = "3+6您身边的超市";
		CharSequence contentText = body;
		n.flags = Notification.FLAG_AUTO_CANCEL;
		n.icon = R.drawable.icon;
		n.defaults = Notification.DEFAULT_ALL;
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.setType("vnd.android-dir/mms-sms");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent contentIntent = PendingIntent.getActivity(dContext, 0,
				intent, 0);
		n.setLatestEventInfo(dContext, contentTitle, contentText, contentIntent);
		// n.setLatestEventInfo(dContext, contentTitle, contentText,
		// contentIntent);
		nm.notify(0, n);
		// Notification n1;
		// Builder builder=new Notification.Builder(dContext);
		// builder.setContentIntent(contentIntent)
		// .setContentText(body)
		// .setContentTitle("3+6网上超市服务信息")
		// .setDefaults(Notification.DEFAULT_ALL)
		// .setSmallIcon(R.drawable.icon_home)
		// .setLargeIcon(BitmapFactory.decodeResource(dContext.getResources(),R.drawable.ic_launcher))
		// .setAutoCancel(true)
		// .setWhen(System.currentTimeMillis());
		// n1=builder.getNotification();
		// nm.notify(0, n1);
	}

	/** 加入购物车线程 **/
	private class pushThread extends Thread { }
}

package com.cn.ouan.xunchat.view;

import android.R.string;
import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.provider.Settings.Secure;

import com.baidu.mapapi.BMapManager;
import com.cn.ouan.xunchat.bean.UserBean;
import com.cn.ouan.xunchat.custom.CustomShowProgressDialog;
import com.cn.ouan.xunchat.service.PushService;
import com.cn.ouan.xunchat.util.Constants;
import com.loopj.android.http.AsyncHttpClient;

/**
 * chat
 * 
 * @author Administrator
 * 
 */
public class TApplication extends Application {
	public static String bluetoothName; // 蓝牙设备名
	public static String bluetoothAddress;// 蓝牙设备地址

	public static BMapManager mapManager;// 百度地图

	public static SharedPreferences user_sps;// 偏好

	public static UserBean userBean;// 用户实体类

	public static AsyncHttpClient httpclient = new AsyncHttpClient();
	public static String client_id;
	public static CustomShowProgressDialog showProgreesDialog;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		// 初始化蓝牙
		BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
		bluetoothName = adapter.getName();
		bluetoothAddress = adapter.getAddress();
		// 初始化偏好
		user_sps = getSharedPreferences(Constants.KEY_SHARED_USER, MODE_PRIVATE);
		String clientId = Secure.getString(getContentResolver(),
				Secure.ANDROID_ID);
		client_id = clientId;
	}

	/**
	 * 得到百度地图
	 */
	public void getBMapManager() {
		mapManager = new BMapManager(getApplicationContext());
	}

	/**
	 * 得到用户信息
	 * 
	 * @return
	 */
	public UserBean getUserBean(){
		if (this.userBean==null) {
			this.userBean=new UserBean();
		}
		return this.userBean;
	}

	/**
	 * 设置用户信息
	 * 
	 * @param userBean
	 */
	public void setUserBean(UserBean userBean) {
		if (userBean == null) {
			this.userBean = new UserBean();
		}
		this.userBean = userBean;
	}
	/**
	 * 打开
	 * @param context
	 * @param name
	 * @param b
	 */
	public static void openProgressDialog(Context context, String name,
			boolean b) {
		if (showProgreesDialog != null) {
			showProgreesDialog.cancelDilog();
		}
		showProgreesDialog = new CustomShowProgressDialog(context, name, b);
	}

	public static void cancelDilog() {
		if (showProgreesDialog != null) {
			showProgreesDialog.cancelDilog();
		}
	}
	
public static String getSession(){
	return user_sps.getString("Session", null);
}
	
	
}

// layout转bitmap
// LayoutInflater factory = LayoutInflater.from(this);
// View textEntryView = factory.inflate(R.layout.overlay_view, null);
// ////把视图转换成Bitmap 再转换成Drawable
// textEntryView.setDrawingCacheEnabled(true);
// textEntryView.measure(
// MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
// MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
// textEntryView.layout(0, 0, textEntryView.getMeasuredWidth(),
// textEntryView.getMeasuredHeight());
// textEntryView.buildDrawingCache();
// Bitmap newbmp = textEntryView.getDrawingCache();
// BitmapDrawable bd =new BitmapDrawable(newbmp);
// 这个bd 就可以是Drawable格式的覆盖物了
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
	public static String bluetoothName; // �����豸��
	public static String bluetoothAddress;// �����豸��ַ

	public static BMapManager mapManager;// �ٶȵ�ͼ

	public static SharedPreferences user_sps;// ƫ��

	public static UserBean userBean;// �û�ʵ����

	public static AsyncHttpClient httpclient = new AsyncHttpClient();
	public static String client_id;
	public static CustomShowProgressDialog showProgreesDialog;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		// ��ʼ������
		BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
		bluetoothName = adapter.getName();
		bluetoothAddress = adapter.getAddress();
		// ��ʼ��ƫ��
		user_sps = getSharedPreferences(Constants.KEY_SHARED_USER, MODE_PRIVATE);
		String clientId = Secure.getString(getContentResolver(),
				Secure.ANDROID_ID);
		client_id = clientId;
	}

	/**
	 * �õ��ٶȵ�ͼ
	 */
	public void getBMapManager() {
		mapManager = new BMapManager(getApplicationContext());
	}

	/**
	 * �õ��û���Ϣ
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
	 * �����û���Ϣ
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
	 * ��
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

// layoutתbitmap
// LayoutInflater factory = LayoutInflater.from(this);
// View textEntryView = factory.inflate(R.layout.overlay_view, null);
// ////����ͼת����Bitmap ��ת����Drawable
// textEntryView.setDrawingCacheEnabled(true);
// textEntryView.measure(
// MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
// MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
// textEntryView.layout(0, 0, textEntryView.getMeasuredWidth(),
// textEntryView.getMeasuredHeight());
// textEntryView.buildDrawingCache();
// Bitmap newbmp = textEntryView.getDrawingCache();
// BitmapDrawable bd =new BitmapDrawable(newbmp);
// ���bd �Ϳ�����Drawable��ʽ�ĸ�������
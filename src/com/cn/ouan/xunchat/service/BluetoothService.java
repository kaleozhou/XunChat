package com.cn.ouan.xunchat.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import android.app.Service;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import com.cn.ouan.xunchat.util.Bluetooth;
import com.cn.ouan.xunchat.util.ClsUtils;
import com.cn.ouan.xunchat.util.Constants;

public class BluetoothService extends Service {

	private Bluetooth bluetooth;
	private List<Map<String, String>> maps;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onDestroy() {
		bluetooth.close();
		bluetooth=null;
		
	};

	@Override
	public void onCreate() {
		super.onCreate();
		bluetooth = new Bluetooth(getApplicationContext());
		IntentFilter filter=new IntentFilter();
		MyReceiver m=new MyReceiver();
		filter.addAction("message");
		filter.addAction(Constants.ACTION_BLUETOOTH_START_SERVICE);
		filter.addAction(Constants.ACTION_BLUETOOTH_CLOSE_SERVICE);
		filter.addAction(Constants.ACTION_BLUETOOTH_START_SEARCH);
		filter.addAction(Constants.ACTION_BLUETOOTH_CLOSE_SEARCH);
		filter.addAction("enarby_user");
		filter.addAction("send_message");
		filter.addAction("start_client");
		getApplicationContext().registerReceiver(m, filter);
	}
	/**
	 * 启动客户端发送信息
	 * @param str
	 * @throws IOException 
	 */
	private void startClient(String message,String address) {
		bluetooth.startClient(message, address);
	}
	/**
	 * 打开服务器
	 */
	private void openService(){
		bluetooth.openService();
	}
	/**
	 * 关闭服务器
	 */
	private void closeService() {
		bluetooth.closeServicce();
	}
	/**
	 * guan关闭搜索
	 */
	private void closeSearch(){
		bluetooth.closeSearch();
	}
	/**
	 * 开始搜索
	 */
	private void startSearch(){
		bluetooth.startSearch();
	}
	private void getEnarbyUser(){
		bluetooth.getEnarbyUser();		
	}
	/**
	 * 
	 * @param message
	 * @return 
	 */
	private void sendMessage(String message){
		bluetooth.sendMessage(message);
	}
	class MyReceiver extends BroadcastReceiver{
		@Override
		public void onReceive(Context context, Intent intent) {
			String strPsw = "0";
			String action=intent.getAction(); 
			if (action.equals("start_client")) {//启动客户端发送信息
				startClient("",intent.getStringExtra("address"));
			}else if(action.equals(Constants.ACTION_BLUETOOTH_START_SERVICE)){//打开服务器
				openService();
			}else if(action.equals(Constants.ACTION_BLUETOOTH_CLOSE_SERVICE)){//关闭服务器
				closeService();
			}else if(action.equals(Constants.ACTION_BLUETOOTH_START_SEARCH)){//开始搜索
				startSearch();
			}else if(action.equals(Constants.ACTION_BLUETOOTH_CLOSE_SEARCH)){//关闭搜索
				closeSearch();
			}else if(action.equals("enarby_user")){//得到历史蓝牙用户
				getEnarbyUser();
			}else if(action.equals("send_message")){//得到历史蓝牙用户
				String message = intent.getStringExtra("message");
				sendMessage(message);
			}else if(action.equals("android.bluetooth.device.action.PAIRING_REQUEST")){
				BluetoothDevice device=intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//				 byte[] pinBytes = BluetoothDevice.convertPinToBytes("1234");
//				 device.setPin(pinBytes);
				try {
					ClsUtils.setPin(device.getClass(), device, strPsw);
					ClsUtils.createBond(device.getClass(), device);
					ClsUtils.cancelPairingUserInput(device.getClass(), device);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
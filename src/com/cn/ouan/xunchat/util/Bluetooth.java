package com.cn.ouan.xunchat.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.text.style.LineHeightSpan.WithDensity;
import android.util.Log;
import android.widget.Toast;

public class Bluetooth {

	public ServerThread mServerThread = null;
	public ClientThread mClientThread = null;
	public ReadThread readThread = null;
	public BluetoothDevice device = null;
	public PrintStream os = null;
	public BluetoothServerSocket mServierSocket = null;
	public BluetoothSocket mSocket = null;

	private String MY_UUIN = "6f1eb543-0363-4f4b-968f-4928e5969400";
	// private String MY_UUIN="00001101-0000-1000-8000-00805F9B34FB";
	// private String MY_UUIN="00000000-2527-eef3-ffff-ffffe3160865";
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				Toast.makeText(context, "" + msg.obj, Toast.LENGTH_SHORT)
						.show();
				break;
			case 2:
				Toast.makeText(context, "���ӷ������쳣", Toast.LENGTH_SHORT).show();
				break;
			case 3:
				Toast.makeText(context, "���ͳɹ�", Toast.LENGTH_SHORT).show();
			}
		}
	};
	/*
	 * 0-�豸��֧�� 1-����δ�� 2-������ 3-������ʼ���� 4-�����õ� 5-������������
	 */
	public int isState;
	private BluetoothAdapter bluetoothAdapter;
	private Context context;

	List<Map<String, String>> maps = new ArrayList<Map<String, String>>();
	private String address;

	public Bluetooth(Context context) {
		this.context = context;
		// �õ�����������
		bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (bluetoothAdapter == null) {
			isState = 0;
			return;
		}
		if (!bluetoothAdapter.isEnabled()) {
			isState = 1;
			bluetoothAdapter.enable();
			bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
			if (bluetoothAdapter == null) {
				isState = 0;
			}
			if (!bluetoothAdapter.isEnabled()) {
				isState = 1;
			}
		} else {
			isState = 2;
		}
		Log.i("isState", "" + isState);
		if (isState != 2) {
			return;
		}

		Set<BluetoothDevice> devices = bluetoothAdapter.getBondedDevices();
		for (BluetoothDevice bluetoothDevice : devices) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("name", bluetoothDevice.getName());
			map.put("address", bluetoothDevice.getAddress());
			maps.add(map);
		}
		Intent intent1 = new Intent(Constants.ACTION_BLUETOOTH_FOUND);
		ArrayList<Map<String, String>> m = (ArrayList<Map<String, String>>) maps;
		Toast.makeText(context, "maps" + m.size(), Toast.LENGTH_SHORT).show();
		intent1.putExtra("maps", m);
		context.sendBroadcast(intent1);
		IntentFilter filter = new IntentFilter();
		filter.addAction(BluetoothDevice.ACTION_FOUND);
		filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
		mReceiver receiver = new mReceiver();
		context.registerReceiver(receiver, filter);

	}

	/**
	 * �õ���ʷ�û�������Ϣ
	 * 
	 * @return list����
	 */
	public void getEnarbyUser() {
		maps = new ArrayList<Map<String, String>>();
		Set<BluetoothDevice> devices = bluetoothAdapter.getBondedDevices();
		for (BluetoothDevice bluetoothDevice : devices) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("name", bluetoothDevice.getName());
			map.put("address", bluetoothDevice.getAddress());
			maps.add(map);
		}
		Intent intent1 = new Intent(Constants.ACTION_BLUETOOTH_FOUND);
		ArrayList<Map<String, String>> m = (ArrayList<Map<String, String>>) maps;
		intent1.putExtra("maps", m);
		context.sendBroadcast(intent1);
	}

	/**
	 * �����ͻ���
	 * 
	 * @param address
	 * @param str
	 */
	public void startClient(String message, String address) {
		Log.i("into", "�����ͻ��˷��� address: " + address);
		this.address=address;
		device = bluetoothAdapter.getRemoteDevice(address);
		mClientThread = new ClientThread();
		mClientThread.start();
	}

	/**
	 * ����������
	 */
	public void openService() {

		mServerThread = new ServerThread();
		mServerThread.start();
	}
	/**
	 * �رշ�����
	 */
	public void closeServicce() {
		close();
		mServierSocket = null;
	}
	/**
	 * ��������
	 */
	public void closeSearch() {
		Toast.makeText(context, "��������", Toast.LENGTH_LONG).show();
		bluetoothAdapter.cancelDiscovery();
	}

	/**
	 * ��ʼ����
	 */
	public void startSearch() {
		Toast.makeText(context, "��ʼ����", Toast.LENGTH_LONG).show();
		bluetoothAdapter.cancelDiscovery();
		bluetoothAdapter.startDiscovery();
	}
	/**
	 * ������Ϣ
	 * @param message ��Ϣ
	 */
	public void sendMessage(String message) {
		if (mServierSocket != null || mSocket != null) {
			os.println(message);
		}
	}

	/**
	 * �������չ㲥
	 * @author Administrator
	 * 
	 */
	class mReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (BluetoothDevice.ACTION_FOUND.equals(action)) {
				BluetoothDevice device = intent
						.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				Map<String, String> map = new HashMap<String, String>();
				map.put("name", device.getName());
				map.put("address", device.getAddress());
				Log.i("devicee", "name " + device.getName() + "  address "
						+ device.getAddress());
				maps.add(map);
				Intent intent1 = new Intent(Constants.ACTION_BLUETOOTH_FOUND);
				ArrayList<Map<String, String>> m = (ArrayList<Map<String, String>>) maps;
				intent1.putExtra("maps", m);
				context.sendBroadcast(intent1);
			} else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED
					.equals(action)) {
				Intent intent1 = new Intent(Constants.ACTION_BLUETOOTH_FINISHED);
				context.sendBroadcast(intent1);
				isState = 5;
			} else if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
				Intent intent1 = new Intent(Constants.ACTION_BLUETOOTH_STARTED);
				context.sendBroadcast(intent1);
				isState = 3;
			}
		}
	}

	/**
	 * �����������߳�
	 * 
	 * @author Administrator
	 * 
	 */
	class ServerThread extends Thread {
		@Override
		public void run() {
			try {
				if (mServierSocket != null) {
					mServierSocket = null;
				}
				bluetoothAdapter.cancelDiscovery();
				mServierSocket = bluetoothAdapter
						.listenUsingRfcommWithServiceRecord("btspp",
								UUID.fromString(MY_UUIN));
				Intent intent=new Intent("listener_start_service");
				intent.putExtra("type", "0");
				context.sendBroadcast(intent);
				Log.i("into", " ������ �ѿ���");
				while (true) {
					mSocket = mServierSocket.accept();
//					Intent intent1=new Intent("listener_start_service");
//					intent1.putExtra("address", ) 
//					intent.putExtra("type", "1");
//					context.sendBroadcast(intent1);
					Log.i("into", "�������ɹ����տͻ���");
					os = new PrintStream(mSocket.getOutputStream(), true, "utf-8");
					readThread = new ReadThread();
					readThread.start();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * �����ͻ����߳�
	 * 
	 * @author Administrator
	 * 
	 */
	class ClientThread extends Thread {
		@SuppressLint("NewApi")
		@Override
		public void run() {
			try {
				Log.i("into", "�����ͻ��� ��ʼ");
				if (mSocket != null) {
					mSocket = null;
				}
				BluetoothSocket tmp = null;
				Method method;
				try {
					// method=device.getClass().getMethod("createRfcommSocket",
					// new Class[]{int.class});
					// tmp=(BluetoothSocket) method.invoke(device, 1);
					tmp = device.createInsecureRfcommSocketToServiceRecord(UUID
							.fromString(MY_UUIN));
				} catch (Exception e) {
					// TODO: handle exception
				}
				mSocket = tmp;
				if (mSocket == null) {
					return;
				}
				bluetoothAdapter.cancelDiscovery();
				mSocket.connect();
				Intent intent=new Intent("listener_start_client");
				intent.putExtra("address", Bluetooth.this.address);
				context.sendBroadcast(intent);
				Log.i("into", "�����ͻ��˳ɹ�");
				os = new PrintStream(mSocket.getOutputStream(), true, "utf-8");
				readThread = new ReadThread();
				readThread.start();
//				Log.i("into", "���� 2" + str);
				handler.sendEmptyMessage(3);
			} catch (IOException e) {
				// try {
				// mSocket.close();
				// } catch (IOException e1) {
				// // TODO Auto-generated catch block
				// e1.printStackTrace();
				// }
				// TODO Auto-generated catch block
				handler.sendEmptyMessage(2);
				Log.i("into", "���ӷ������쳣");
				e.printStackTrace();
				Log.i("into", e.toString());
			}
		}
	}

	/**
	 * ��ȡ�����߳�
	 * @author Administrator
	 * 
	 */
	class ReadThread extends Thread {
		@Override
		public void run() {
			byte[] buffer = new byte[1024];
			int bytes;
			BufferedInputStream in = null;
			try {
				in = new BufferedInputStream(mSocket.getInputStream());
				while ((bytes=in.read(buffer))!=-1) {
					 byte[] buf_data = new byte[bytes];
				    	for(int i=0; i<bytes; i++)
				    	{
				    		buf_data[i] = buffer[i];
				    	}
						String s = new String(buf_data);						
						Log.i("into", "��ɷ���������  " + s);
						Intent intent=new Intent("chat_message");
						intent.putExtra("message", s);
						context.sendBroadcast(intent);
						handler.obtainMessage(1,s).sendToTarget();
				}
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void close() {
		mServerThread = null;
		mClientThread = null;
		readThread = null;
		device = null;
		os = null;
		mServierSocket = null;
		mSocket = null;
	}
}

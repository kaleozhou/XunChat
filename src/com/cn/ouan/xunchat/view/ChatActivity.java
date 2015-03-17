package com.cn.ouan.xunchat.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.Settings.Secure;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cn.ouan.xunchat.BaseActivity;
import com.cn.ouan.xunchat.R;
import com.cn.ouan.xunchat.adapter.ChatAdapter;
import com.cn.ouan.xunchat.adapter.GVphizAdapter;
import com.cn.ouan.xunchat.bean.UserBean;
import com.cn.ouan.xunchat.biz.ChatBiz;
import com.cn.ouan.xunchat.util.Constants;
import com.cn.ouan.xunchat.util.FileUtils;
import com.cn.ouan.xunchat.util.SoundMeter;

/**
 * 聊天界面
 * 
 * @author Administrator
 * 
 */
public class ChatActivity extends BaseActivity implements OnClickListener,
		OnTouchListener, OnItemClickListener {

	private TextView _tv_user_name;
	private Button _btn_send;
	private Button _btn_switch;
	private EditText _et_message;
	private ListView _lv_chat;
	private GridView _gv_chat;
	private ImageView _iv_chat_ghiz;
	private FrameLayout _fl_chat_ghiz;

	BluetoothReceiver mReceiver;
	ChatReceiver receiver;
	ChatAdapter chatAdapter;
	String userName;
	String chat_type;

	UserBean userBean;
	Map<String, String> circleMap;
	List<Map<String, String>> lists;

	private SoundMeter mSensor;
	// 切换录音与文本
	private boolean isStartGhiz = false;
	private int flag = 1;// 录音步骤监控

	private boolean isPhizOpen = false;

	private long startVoiceT;
	private String voiceName;
	private Button _btn_phiz;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		receiver = new ChatReceiver();
		IntentFilter filter = new IntentFilter();
		// filter.addAction("listener_start_service");
		// filter.addAction("listener_start_client");
		// filter.addAction("chat_message");
		// filter.addAction("chat_phiz");
		// filter.addAction("start_music");
		filter.addAction(Constants.ACTION_PUSH_CHAT_MESSAGE);
		registerReceiver(receiver, filter);
		Log.i("into", "chatActivity_oncreate");
		mSensor = new SoundMeter();
		setData();
		setUpView();
		setListener();
	}

	/**
	 * 
	 */
	private void setData() {
		lists = new ArrayList<Map<String, String>>();
		circleMap = new HashMap<String, String>();
		chat_type = getIntent().getStringExtra("type");
		Log.i("chatactivity", "chat_type: "+chat_type);
		
		if (chat_type.equals("0")) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("message", "正在启动服务器...");
			map.put("user_name", TApplication.bluetoothName);
			map.put("address", TApplication.bluetoothAddress);
			lists.add(map);
			sendBroadcast(new Intent(Constants.ACTION_BLUETOOTH_START_SERVICE));
			// } else if (type.equals("1")) {
			// userName = getIntent().getStringExtra("name");
			// String address = getIntent().getStringExtra("address");
			// Intent intent = new Intent("start_client");
			// intent.putExtra("address", address);
			// sendBroadcast(intent);
		} else if (chat_type.equals("1")) {
			circleMap.put("cid", getIntent().getStringExtra("cid")); 
			circleMap.put("name", getIntent().getStringExtra("name"));
			circleMap.put("note", getIntent().getStringExtra("note"));
			circleMap.put("count", getIntent().getStringExtra("count"));
		}else if (chat_type.equals("2")) {
			Log.i("chatactivity", "friend_id: "+ getIntent().getStringExtra("friend_id"));
			Log.i("chatactivity", "name: "+ getIntent().getStringExtra("name"));
			circleMap.put("friend_id", getIntent().getStringExtra("friend_id")); 
			circleMap.put("name", getIntent().getStringExtra("name"));
		}

	}

	/**
	 * init control view
	 */
	private void setUpView() {
		_btn_send = (Button) findViewById(R.id.btn_send);

		_et_message = (EditText) findViewById(R.id.et_message);

		_lv_chat = (ListView) findViewById(R.id.lv_chat);

		_gv_chat = (GridView) findViewById(R.id.gridView1);
		GVphizAdapter adapter = new GVphizAdapter(this);
		_gv_chat.setAdapter(adapter);
		chatAdapter = new ChatAdapter(this, lists);
		_lv_chat.setAdapter(chatAdapter);

		_btn_switch = (Button) findViewById(R.id.btn_swtich);

		_btn_phiz = (Button) findViewById(R.id.btn_phiz);

		// 中间录音
		_fl_chat_ghiz = (FrameLayout) findViewById(R.id.fl_chat_ghiz);
		_iv_chat_ghiz = (ImageView) findViewById(R.id.iv_chat_ghiz);

	}

	/**
	 * 
	 */
	private void setListener() {
		_btn_send.setOnClickListener(this);
		_btn_send.setOnTouchListener(this);
		_btn_switch.setOnClickListener(this);
		_btn_phiz.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_send:
			// String message=_et_message.getText().toString();
			// Intent intent=new Intent("send_message");
			// intent.putExtra("message", message);
			// sendBroadcast(intent);
			send(0, "qw");
			break;
		case R.id.btn_swtich:
			if (isStartGhiz) {
				isStartGhiz = false;
				_btn_switch.setText("语音");
				_btn_send.setText("发送");
			} else {
				isStartGhiz = true;
				_btn_switch.setText("文字");
				_btn_send.setText("按住录音");
			}
			break;
		case R.id.btn_phiz:
			StartAnimation();
			break;
		}
	}

	/**
	 * 表情框弹出动画
	 */
	private void StartAnimation() {
		if (isPhizOpen) {
			_gv_chat.setVisibility(View.GONE);
			isPhizOpen = false;
		} else {
			_gv_chat.setVisibility(View.VISIBLE);
			Animation animation = AnimationUtils.loadAnimation(this,
					R.anim.set_ghiz);
			_gv_chat.setAnimation(animation);
			isPhizOpen = true;

		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (!Environment.getExternalStorageDirectory().exists()) {
			Toast.makeText(this, "No SDCard", Toast.LENGTH_LONG).show();
			return false;
		}
		if (isStartGhiz) {
			if (event.getY() > 100 || event.getY() < 0) {
				_iv_chat_ghiz.setBackgroundResource(R.drawable.ghiz_cancel);
			} else {
				_iv_chat_ghiz.setBackgroundResource(R.drawable.ghiz_start);
			}
			Log.i("json", "录音中。。。");
			if (event.getAction() == MotionEvent.ACTION_DOWN && flag == 1) {
				if (!Environment.getExternalStorageDirectory().exists()) {
					Toast.makeText(this, "No SDCard", Toast.LENGTH_LONG).show();
					return false;
				}
				Log.i("json", "2");
				_btn_send.setText("松开结束");
				_fl_chat_ghiz.setVisibility(View.VISIBLE);
				_iv_chat_ghiz.setBackgroundResource(R.drawable.ghiz_start);
				startVoiceT = SystemClock.currentThreadTimeMillis();
				voiceName = startVoiceT + ".amr";
				start(voiceName);
				flag = 2;
				Log.i("json", "开始");
			} else if (event.getAction() == MotionEvent.ACTION_UP && flag == 2) {// 松开手势时执行录制完成
				if (event.getY() > 100 || event.getY() < 0) {
					_fl_chat_ghiz.setVisibility(View.GONE);
					_btn_send.setText("按住开始");
					Log.i("json", "录音失败");
					return false;
				}
				_fl_chat_ghiz.setVisibility(View.GONE);
				_btn_send.setText("按住开始");
				flag = 1;
				String path = new FileUtils().getSDPATH() + "/Video/"
						+ voiceName;
				// File file = new File(path);
				stop();
				send(1, path);
				Log.i("json", "录音完成");
			}
		} else {
			return false;
		}
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position,
			long id) {

	}

	/**
	 * 开始录音
	 * 
	 * @param name
	 *            录音文件名
	 */
	private void start(String name) {
		mSensor.start(name);
	}

	/**
	 * 停止
	 */
	private void stop() {
		mSensor.stop();
	}

	private void send(int type, String path) {
		String message = _et_message.getText().toString();
		if (chat_type == "2") {
			ChatBiz chatBiz = new ChatBiz(this);
			chatBiz.sendMessage(circleMap.get("uid"),   message);
		} else {
			ChatBiz biz = new ChatBiz(this);
			Log.i("sendcircle", TApplication.user_sps.getString("username", null));
			Log.i("sendcircle", message);
			Log.i("sendcircle", " cid: "+circleMap.get("cid"));
			biz.sendMessageCirlce(circleMap.get("cid"), TApplication.user_sps.getString("username", null), message);
		}
		Map<String, String> map = new HashMap<String, String>();
		// map.put(Constants.KEY_FROM_USERNAME,
		// SupermarketAppliction.getUsername());
		// map.put(Constants.KEY_TO_USERNAME,
		// (String) paramsMap.get("to_username"));
		map.put("message", message);
		// map.put("type", Constants.CHAT_RUN);
		map.put("user_type", "from");
		if (type == 1) {
			map.put("message_type", "phiz");
			map.put("path", path);
		} else {
			map.put("message_type", "message");
		}
		SimpleDateFormat formatter = new SimpleDateFormat("MM月dd日 HH:mm:ss·");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		map.put("date", formatter.format(curDate));
		lists.add(map);
		chatAdapter.notifyDataSetChanged();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.i("into", "chatActivity_ondestroy");
		receiver.clearAbortBroadcast();
	}
	class BluetoothReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals("listener_start_service")) {

				Map<String, String> map = new HashMap<String, String>();
				if (intent.getStringExtra("type").equals(1)) {
					map.put("message", "已启动服务器，等待用户连接");
				} else {
					map.put("message", "已启动服务器，等待用户连接");
					// map.put("message",
					// "用户："+intent.getStringExtra("address")+" 进入房间");
				}
				map.put("user_name", TApplication.bluetoothName);
				map.put("address", TApplication.bluetoothAddress);
				lists.add(map);
				chatAdapter = new ChatAdapter(ChatActivity.this, lists);
				_lv_chat.setAdapter(chatAdapter);
			} else if (action.equals("listener_start_client")) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("message",
						"已连接服务器: " + intent.getStringExtra("address"));
				map.put("address", intent.getStringExtra("address"));
				lists.add(map);
				chatAdapter = new ChatAdapter(ChatActivity.this, lists);
				_lv_chat.setAdapter(chatAdapter);
			} else if (action.equals("chat_message")) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("message", intent.getStringExtra("message"));
				lists.add(map);
				chatAdapter = new ChatAdapter(ChatActivity.this, lists);
				_lv_chat.setAdapter(chatAdapter);

			} else if (action.equals("chat_phiz")) {
				SpannableStringBuilder sb = new SpannableStringBuilder();
				// 21308376690
				String path = intent.getStringExtra("chat_message");
				sb.append(path);
				ImageSpan imageSpan = new ImageSpan(context,
						Integer.parseInt(path));
				sb.setSpan(imageSpan, 0, sb.length(),
						Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				_et_message.append(sb);
			} else if (action.equals("start_music")) {
				mSensor.playerStart(intent.getStringExtra("music_path"));
			}
		}
	}

	/**
	 * get push info broadcast
	 * 
	 * @author Administrator
	 * 
	 */
	class ChatReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(Constants.ACTION_PUSH_CHAT_MESSAGE)) {
				Bundle b = intent.getExtras();
				JSONObject json = new JSONObject()
						.fromString(b.getString("op"));
				Map<String, String> map = new HashMap<String, String>();
				map.put("message", json.getString("message"));
				map.put("name", json.getString("name"));
				lists.add(map);
				chatAdapter = new ChatAdapter(ChatActivity.this, lists);
				_lv_chat.setAdapter(chatAdapter);
			}
		}
	}
}

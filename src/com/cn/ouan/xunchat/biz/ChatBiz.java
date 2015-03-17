package com.cn.ouan.xunchat.biz;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.cn.ouan.xunchat.listener.CallBackListener;
import com.cn.ouan.xunchat.util.Constants;
import com.cn.ouan.xunchat.util.MessageUtil;

public class ChatBiz { 
	
	private Context context;
	public ChatBiz(Context context){
		this.context=context;
	}
	
	
	public void sendMessage(String friend ,String text){
		MessageUtil message=new MessageUtil();
		message.sendMessage(friend , text, new CallBackListener() {
			@Override
			public void callBackListener(Object result) {
				
			}
		});
	}
	public void sendMessage(String friend,String friend_client_id,byte[] text){
		
	}
	
	
	/**
	 * send circle info
	 * @param cid
	 * @param name
	 * @param message
	 */
	public void sendMessageCirlce(String cid,String name,String message){
		MessageUtil util=new MessageUtil();
		 util.sendMessageCircle(cid,name,message,new CallBackListener() {
			@Override
			public void callBackListener(Object result) {
				Toast.makeText(context, result.toString(), Toast.LENGTH_LONG).show();
			}
		});
	}
}

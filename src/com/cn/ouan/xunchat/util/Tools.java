package com.cn.ouan.xunchat.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.widget.Toast;

public class Tools {
	
	public static boolean isNull(String str){
		if(str.equals("")||str.equals("null")||str==""||str==null){
			return true;
		}
		return false;
	}
	
	 
	public static void toastShow(Context context,String mag,int type){
		if (type>1) {
			type=1;
		}else if (type<0) {
			type=0;
		}
		Toast.makeText(context, mag, type).show();
	}
	
	private static String getTodayString()
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd-hh:mm:ss");
		return df.format(new Date());
	}
}

package com.cn.ouan.xunchat.util;

public class Constants {
	
	public static final String KEY_SHARED_USER="user";//开启服务器
	
	
	
	/**
	 * 蓝牙接口广播
	 */
	
	public static final String ACTION_BLUETOOTH_START_SERVICE="start_service";//开启服务器
	public static final String ACTION_BLUETOOTH_CLOSE_SERVICE="close_service";//关闭服务器
	public static final String ACTION_BLUETOOTH_START_SEARCH="start_search";//开始搜索
	public static final String ACTION_BLUETOOTH_CLOSE_SEARCH="close_search";//关闭搜索
	public static final String ACTION_BLUETOOTH_FOUND="found";//得到搜索用户监听
	public static final String ACTION_BLUETOOTH_FINISHED="finish";//关闭搜索监听
	public static final String ACTION_BLUETOOTH_STARTED="start";//开始搜索监听

/*-----------------------------广播-------------------------------------*/
	public static final String ACTION_SWITCH="com.cn.ouan.xunchat.switch";
	
	/**
	 * 用户广播
	 */
	public static final String ACTION_LOGIN="com.cn.ouan.xunchat.userlogin";
	public static final String ACTION_REGIST="com.cn.ouan.xunchat.userregist";
	public static final String ACTION_UPDATA_USER_INFO="com.cn.ouan.xunchat.updateuserinfo";
	public static final String ACTION_USER_INFO="com.cn.ouan.xunchat.userinfo";
	public static final String ACTION_VERIFY ="com.cn.ouan.xunchat.verify";
	/**
	 * 附近用户，圈子广播
	 */
	public static final String ACTION_NEARBY_CIRLCE="com.cn.ouan.xunchat.circle";
	public static final String ACTION_NEARBY_USER="com.cn.ouan.xunchat.user";
	public static final String ACTION_CREATE_CIRCLE="con.cn.ouan.xunchat.create.circle";
	public static final String ACTION_ADD_CIRCLE="com.cn.ouan.xunchat.add.circle";
	/**
	 * 推送消息广播
	 */
	public static final String ACTION_PUSH_CHAT_MESSAGE="com.cn.ouan.xunchat.chatmessage";
	
	/**
	 * 好友广播
	 */
	public static final String ACTION_ADD_FRIEND="com.cn.ouan.xunchat.add.friend";
	public static final String ACTION_ALL_FRIEND="com.cn.ouan.xunchat.all.friend";
	public static final String ACTION_ALL_STRANGER="com.cn.ouan.xunchat.all.stranger";
	/**
	 * 完全退出广播
	 */
	public static final String ACTION_EXIT="exit";
	/**
	 * url 路径
	 */
//	static String path="http://192.168.1.109:8080/chat";
	static String path="http://192.168.1.112:80/api";
	public static final String URL_HEARTBEAT=path+"/heartbeat/updateheartbeat";//heartbeat
	public static final String URL_LOGIN=path+"/user/login";//登录
	public static final String URL_REGIST=path+"/user/regist";//注册
	public static final String URL_USER_UPDATE_INFO=path+"/user/updateuserinfo";//修改用户信息
	public static final String URL_USER_INFO=path+"/user/userinfo";//得到用户信息
	public static final String URL_VERIFY=path+"/user/Verification";//得到用户信息
	
	public static final String URL_MESSAGE_SEND=path+"/message/send";//发送信息
	public static final String URL_MESSAGE_CIRCLE_SEND=path+"/message/sendcircle";//发送圈子信息
	
	public static final String URL_FRIEND_ADD=path+"/friend/addfriend";//添加好友
	public static final String URL_FRIEND_ALL=path+"/friend/friendlist";//所有好友
	public static final String URL_STRANGER_ALL=path+"/user/strangerinfo";//所有陌生人
	public static final String URL_FRIEND_DEL=path+"/friend/delfriend";//删除好友
	public static final String URL_FRIEND_REPETITION=path+"/friend/friendlist";//是否好友

	public static final String URL_CIRCLE_FRIEND=path+"/user/nearbyusers";//附近用户
	public static final String URL_CIRCLE_CIRCLE=path+"/circle/circles";//附近圈子
	public static final String URL_CIRCLE_CREATE=path+"/circle/createcircle";//创建圈子
	public static final String URL_CIRCLE_LIST=path+"/circle/circlelist";//得到圈子列表
	public static final String URL_CIRCLE_ADD_MEMBER=path+"/circle/addcircle";//添加圈子成员
}

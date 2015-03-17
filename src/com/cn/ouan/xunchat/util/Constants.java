package com.cn.ouan.xunchat.util;

public class Constants {
	
	public static final String KEY_SHARED_USER="user";//����������
	
	
	
	/**
	 * �����ӿڹ㲥
	 */
	
	public static final String ACTION_BLUETOOTH_START_SERVICE="start_service";//����������
	public static final String ACTION_BLUETOOTH_CLOSE_SERVICE="close_service";//�رշ�����
	public static final String ACTION_BLUETOOTH_START_SEARCH="start_search";//��ʼ����
	public static final String ACTION_BLUETOOTH_CLOSE_SEARCH="close_search";//�ر�����
	public static final String ACTION_BLUETOOTH_FOUND="found";//�õ������û�����
	public static final String ACTION_BLUETOOTH_FINISHED="finish";//�ر���������
	public static final String ACTION_BLUETOOTH_STARTED="start";//��ʼ��������

/*-----------------------------�㲥-------------------------------------*/
	public static final String ACTION_SWITCH="com.cn.ouan.xunchat.switch";
	
	/**
	 * �û��㲥
	 */
	public static final String ACTION_LOGIN="com.cn.ouan.xunchat.userlogin";
	public static final String ACTION_REGIST="com.cn.ouan.xunchat.userregist";
	public static final String ACTION_UPDATA_USER_INFO="com.cn.ouan.xunchat.updateuserinfo";
	public static final String ACTION_USER_INFO="com.cn.ouan.xunchat.userinfo";
	public static final String ACTION_VERIFY ="com.cn.ouan.xunchat.verify";
	/**
	 * �����û���Ȧ�ӹ㲥
	 */
	public static final String ACTION_NEARBY_CIRLCE="com.cn.ouan.xunchat.circle";
	public static final String ACTION_NEARBY_USER="com.cn.ouan.xunchat.user";
	public static final String ACTION_CREATE_CIRCLE="con.cn.ouan.xunchat.create.circle";
	public static final String ACTION_ADD_CIRCLE="com.cn.ouan.xunchat.add.circle";
	/**
	 * ������Ϣ�㲥
	 */
	public static final String ACTION_PUSH_CHAT_MESSAGE="com.cn.ouan.xunchat.chatmessage";
	
	/**
	 * ���ѹ㲥
	 */
	public static final String ACTION_ADD_FRIEND="com.cn.ouan.xunchat.add.friend";
	public static final String ACTION_ALL_FRIEND="com.cn.ouan.xunchat.all.friend";
	public static final String ACTION_ALL_STRANGER="com.cn.ouan.xunchat.all.stranger";
	/**
	 * ��ȫ�˳��㲥
	 */
	public static final String ACTION_EXIT="exit";
	/**
	 * url ·��
	 */
//	static String path="http://192.168.1.109:8080/chat";
	static String path="http://192.168.1.112:80/api";
	public static final String URL_HEARTBEAT=path+"/heartbeat/updateheartbeat";//heartbeat
	public static final String URL_LOGIN=path+"/user/login";//��¼
	public static final String URL_REGIST=path+"/user/regist";//ע��
	public static final String URL_USER_UPDATE_INFO=path+"/user/updateuserinfo";//�޸��û���Ϣ
	public static final String URL_USER_INFO=path+"/user/userinfo";//�õ��û���Ϣ
	public static final String URL_VERIFY=path+"/user/Verification";//�õ��û���Ϣ
	
	public static final String URL_MESSAGE_SEND=path+"/message/send";//������Ϣ
	public static final String URL_MESSAGE_CIRCLE_SEND=path+"/message/sendcircle";//����Ȧ����Ϣ
	
	public static final String URL_FRIEND_ADD=path+"/friend/addfriend";//��Ӻ���
	public static final String URL_FRIEND_ALL=path+"/friend/friendlist";//���к���
	public static final String URL_STRANGER_ALL=path+"/user/strangerinfo";//����İ����
	public static final String URL_FRIEND_DEL=path+"/friend/delfriend";//ɾ������
	public static final String URL_FRIEND_REPETITION=path+"/friend/friendlist";//�Ƿ����

	public static final String URL_CIRCLE_FRIEND=path+"/user/nearbyusers";//�����û�
	public static final String URL_CIRCLE_CIRCLE=path+"/circle/circles";//����Ȧ��
	public static final String URL_CIRCLE_CREATE=path+"/circle/createcircle";//����Ȧ��
	public static final String URL_CIRCLE_LIST=path+"/circle/circlelist";//�õ�Ȧ���б�
	public static final String URL_CIRCLE_ADD_MEMBER=path+"/circle/addcircle";//���Ȧ�ӳ�Ա
}

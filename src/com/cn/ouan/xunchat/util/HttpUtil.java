package com.cn.ouan.xunchat.util;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLHandshakeException;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;


public class HttpUtil {

	
	private static final String TAG = "HttpUtil";
	@SuppressWarnings("unused")
	private static HttpURLConnection getGETConn(URL url) throws IOException{
//		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
//			conn.setConnectTimeout(1000*60*2);
//			conn.setRequestMethod("GET");
//			conn.setDoInput(true);
//			conn.connect();
		return getGETConn(url, 20000);
	} 
	
	private static HttpURLConnection getGETConn(URL url,int time) throws IOException{
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setConnectTimeout(time);
			conn.setRequestMethod("GET");
			conn.setDoInput(true);
			conn.connect();
		return conn;
	} 
	
	
	/**
	 * 
	 * @param pathUrl  url��ַ
	 * @param params   url����
	 * @return
	 */
	public static byte[] getData(String pathUrl,String params,Error error) {
		
		return getData(pathUrl, params, 20000, error);
	}
	
	

	/**
	 * 
	 * @param pathUrl  url��ַ
	 * @param params   url����
	 * @return
	 */
	public static byte[] getData(String pathUrl,String params,int time,Error error) {
		if(TextUtils.isEmpty(pathUrl)){
			return null;
		}
		InputStream inStream = null;
		HttpURLConnection conn = null;
		byte[] data = null;
			try {
				String str=//URLEncoder.encode(params.toString(), "UTF-8");
						new String((pathUrl+params).toString().getBytes(),"UTF-8");
				URL url = new URL(str);
				conn = getGETConn(url,time);
				int responseCode = conn.getResponseCode();
				if(responseCode==HttpURLConnection.HTTP_OK){
					inStream = conn.getInputStream();
					data = IOUtils.readStream(inStream);
					
				}else if(responseCode==HttpURLConnection.HTTP_CLIENT_TIMEOUT){
					error.setError("�������ӳ�ʱ");
				}else{
					error.setError(String.format("error:%d", responseCode));
				}
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				error.setError("url��ʽ����ȷ");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				error.setError("��֧�ֵı���");
			}catch (SocketTimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				error.setError("�������ӳ�ʱ");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				error.setError("�������ӷ�������");
			}finally{
				if(inStream!=null){
					try {
						inStream.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(conn!=null){
					conn.disconnect();
				}
			}
		return data;
	}
	
	
	/**
	 * 
	 * @param pathUrl  url��ַ
	 * @param params   url����
	 * @return
	 */
	public static String getString(String pathUrl,String params,int time,Error error) {
		String data = null;
		byte[] buffer = null;
		try {
			buffer = getData(pathUrl, params,time, error);
			if(buffer!=null){
				data =  new String(buffer,"UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return data;
	}
	
	/**
	 * 
	 * @param pathUrl  url��ַ
	 * @param params   url����
	 * @return
	 */
	public static String getString(String pathUrl,String params,Error error) {
		String data = null;
		byte[] buffer = null;
		try {
			buffer = getData(pathUrl, params, error);
			if(buffer!=null){
				data =  new String(buffer,"UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return data;
	}
	
	
	
	private static HttpURLConnection getPOSTConn(URL url) throws IOException{
		return getPOSTConn(url, 20000);
	} 
	
	
	public static HttpURLConnection getPOSTConn(URL url,int initTime) throws IOException{
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setConnectTimeout(initTime);
				conn.setReadTimeout(initTime);
				conn.setDoOutput(true);
				conn.setDoInput(true);
				conn.setRequestMethod("POST");
				conn.setUseCaches(false);
				conn.setInstanceFollowRedirects(true);
				conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");    //���������ĳ�ʱʱ�䣨��λ�����룩  
				conn.connect();
		return conn;
	} 
	
	
	/**
	 * 
	 * @param url   	url��ַ
	 * @param data		post������
	 * @param error		������Ϣ
	 * @return
	 */
	public static byte[] PostData(String url, String data,Error error) {
		if(TextUtils.isEmpty(url)){
			return null;
		}
		InputStream  inStream = null;
		HttpURLConnection conn = null;
		BufferedOutputStream out = null;
		byte[] returnData = null;
			try {
				URL postUrl = new URL(url);
				
				conn = getPOSTConn(postUrl);
				if(!TextUtils.isEmpty(data)){
					out = new BufferedOutputStream(conn.getOutputStream(),1024);
					out.write(data.getBytes("UTF-8"));
					out.flush();
				}
				int responseCode = conn.getResponseCode();
				if(responseCode==HttpURLConnection.HTTP_OK){
					inStream = conn.getInputStream();
					returnData = IOUtils.readStream(inStream);
				}else if(responseCode==HttpURLConnection.HTTP_CLIENT_TIMEOUT){
					error.setError("�������ӳ�ʱ");
				}else{
					error.setError(String.format("error:%d", responseCode));
				}
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				error.setError("url��ʽ����ȷ");
			} catch (SocketTimeoutException e){
				e.printStackTrace();
				error.setError("�������ӳ�ʱ");
          }catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				error.setError("��֧�ֵı���");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				error.setError("�������ӷ�������");
			}
			finally{
				if(out!=null){
					try {
						out.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(conn!=null){
					conn.disconnect();
				}
			}
		return returnData;
	}

	/**
	 * @param url   	url��ַ
	 * @param data		post������
	 * @return
	 */
	public static String postString(String url, String data,Error error) {
		String returnStr = null;
		byte[] buffer = null;
		try {
			buffer = PostData(url, data, error);
			if(buffer!=null){
				returnStr =  new String(buffer,"UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnStr;
	}
	
	/**
	 * ��map��ϳ�get�Ĳ���
	 * @param paramsMap  ������
	 * @return
	 */
	public static String paramsMap2Str(Map<String, Object> paramsMap){
		if(paramsMap==null||paramsMap.isEmpty()){
			return null;
		}
		StringBuilder params = new StringBuilder("?");
		for (Map.Entry<String,Object> entry : paramsMap.entrySet()) {
			params.append(entry.getKey()).append("=")
			.append(String.valueOf(entry.getValue()))
			.append("&");
		}
		params.deleteCharAt(params.length()-1);
		return params.toString();
	}

	/**
	 * �쳣�Զ��ָ�����, ʹ��HttpRequestRetryHandler�ӿ�ʵ��������쳣�ָ�
	 */
	private static HttpRequestRetryHandler requestRetryHandler = new HttpRequestRetryHandler() {
		// �Զ���Ļָ�����
		public boolean retryRequest(IOException exception, int executionCount,
				HttpContext context) {
			// ���ûָ����ԣ��ڷ����쳣ʱ���Զ�����N��
			if (executionCount >= 3) {
				// �������������Դ�������ô�Ͳ�Ҫ������
				return false;
			}
			if (exception instanceof NoHttpResponseException) {
				// ������������������ӣ���ô������
				return true;
			}
			if (exception instanceof SSLHandshakeException) {
				// ��Ҫ����SSL�����쳣
				return false;
			}
			HttpRequest request = (HttpRequest) context
					.getAttribute(ExecutionContext.HTTP_REQUEST);
			boolean idempotent = (request instanceof HttpEntityEnclosingRequest);
			if (!idempotent) {
				// ���������Ϊ���ݵȵģ���ô������
				return true;
			}
			return false;
		}
	};

	
	/**
	 * ��map��ϳ�get�Ĳ���
	 * @param paramsMap  ������
	 * @return
	 */
	public static String paramsMap2StrPost(Map<String, Object> paramsMap){
		if(paramsMap==null||paramsMap.isEmpty()){
			return null;
		}
		StringBuilder params = new StringBuilder("");
		for (Map.Entry<String,Object> entry : paramsMap.entrySet()) {
			params.append(entry.getKey()).append("=")
			.append(String.valueOf(entry.getValue()))
			.append("&");
		}
		params.deleteCharAt(params.length()-1);
		return params.toString();
	}
	
	/**
	 * ��mapת����json String  post���
	 * @param dataMap  map
	 * @return
	 */
	public static String dataMap2Str(Map<String, String> dataMap){
		if(dataMap==null||dataMap.isEmpty()){
			return null;
		}
		StringBuilder params = new StringBuilder("{");
		for (Map.Entry<String,String> entry : dataMap.entrySet()) {
			params.append("\"").append(entry.getKey()).append("\":");
			Object value = entry.getValue();
			if(value instanceof String){
				params.append("\"").append(value).append("\"");
			}else if(value instanceof List){
				@SuppressWarnings("unchecked")
				List<HashMap<String, String>> list = (List<HashMap<String, String>>) value;
				if(!list.contains(dataMap)){
					params.append(dataList2Str(list));
				}
			}
			else{
				params.append(String.valueOf(value));
			}
			params.append(",");
		}
		params.replace(params.length()-1, params.length(), "}");
		return params.toString();
	}
	
	
	
	
	
	/**
	 * ��Listת����json ����  post���
	 * @param List  list
	 * @return
	 */
	public static String dataList2Str(List<HashMap<String, String>> list){
		if(list==null||list.isEmpty()){
			return null;
		}
		StringBuilder params = new StringBuilder("[");
		for (Map<String, String> map : list) {
			String param = dataMap2Str(map);
			if(param==null){
				continue;
			}
			params.append(param).append(",");
		}
		params.replace(params.length()-1, params.length(), "]");
		return params.toString();
	}
	
	public static Drawable loadImageFromUrl(String url) {
        URL m;
        InputStream i = null;
        try {
            m = new URL(url);
            i = (InputStream) m.getContent();
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Drawable d = Drawable.createFromStream(i, "src");
        return d;
    }


	/**
	 * ������������
	 * 
	 * @return
	 */
	public static boolean isIntent(Context context) {
		ConnectivityManager con = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkinfo = con.getActiveNetworkInfo();
		if (networkinfo == null || !networkinfo.isConnected()) { // || !networkinfo.isAvailable()
			// ��ǰ���粻����
			return false;
		}
		return true;
	}
	
	private static long lastClickTime=0; 
	/**
	 * ��ֹ�ظ����
	 * @return
	 */
    public static boolean isFastDoubleClick() {  
        long time = System.currentTimeMillis();  
        Log.d(TAG, "time:"+time);
        long timeD = time - lastClickTime;  
        if ( 0 < timeD && timeD < 900) {
            return true;
        }
        lastClickTime = time;
        return false;
    } 
	
	
}

package com.cn.ouan.xunchat.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
/**
 * 文件帮助
 * @author WXP
 *
 */
public class IOUtils {
	/**
	 * 将inputStream转为byte[]数组
	 * @param inStream
	 * @return
	 * @throws IOException
	 */
	public static byte[] readStream(InputStream inStream){
		if(inStream==null){
			return null;
		}
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		byte[] data = null;
			try {
				while((len=inStream.read(buffer,0,1024))!=-1){
					outStream.write(buffer, 0, len);
				}
				data = outStream.toByteArray();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				if(outStream!=null){
					try {
						outStream.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(inStream!=null){
					try {
						inStream.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		
		return data;
	}
	
	/**
	 * 写入数据
	 * @param data
	 * @param file
	 */
	public static void writeToFile(byte[] data,File file){
		if(data==null){
			return;
		}
		BufferedOutputStream outputStream = null;
		try {
			 outputStream = new BufferedOutputStream(
					new FileOutputStream(file),1024);
			outputStream.write(data);
			outputStream.flush();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
				if(outputStream!=null){
					try {
						outputStream.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		}
	}
	/**
	 * 读取文件
	 * @param file
	 * @return
	 */
	public static byte[] readFile(File file){
		FileInputStream inStream = null;
		ByteArrayOutputStream outStream = null;
		byte[] data = null;
		try {
			inStream = new FileInputStream(file);
			outStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while((len=inStream.read(buffer))!=-1){
				outStream.write(buffer, 0, len);
			}
			data = outStream.toByteArray();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			if(outStream!=null){
				try {
					outStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(inStream!=null){
				try {
					inStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		return data;
	}
	
}

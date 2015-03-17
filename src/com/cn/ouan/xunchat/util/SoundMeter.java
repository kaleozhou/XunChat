package com.cn.ouan.xunchat.util;

import java.io.IOException;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Environment;
import android.util.Log;
public class SoundMeter implements OnCompletionListener{
	static final private double EMA_FILTER = 0.6;  
	//播放
	private MediaPlayer mPlayer = null; 
	//录音
    private MediaRecorder mRecorder = null;  
    private double mEMA = 0.0;  
    
    //�?��播放 播放新音�?
    public void playerStart(String path){
    	if (!Environment.getExternalStorageState().equals(  
    			android.os.Environment.MEDIA_MOUNTED)) {  
    		return;  
    	}
    	 if(mPlayer==null){    		  
    		 mPlayer=new MediaPlayer();
    	 }
    	 //播放  
    	 try {
    		 Log.i("json-sdc", path);
    		 mPlayer.reset();  
    		 mPlayer.setDataSource(path);  
    		 mPlayer.prepare();
    		 mPlayer.start();
    	 } catch (IllegalStateException e) { 
    		 e.printStackTrace();
    	 } catch (IOException e) { 
    		 e.printStackTrace();
    	 }  
    }
    //继续播放
    public void playerStart(){
    	if(mPlayer!=null){
    		mPlayer.start();
    	}
    }
    //停止
    public void playerStop(){
    	if(mPlayer!=null){
    		mPlayer.stop();
    		mPlayer.release();
    		mPlayer=null;
    	}
    }
    //暂停
    public void playerPause(){
    	if(mPlayer!=null){
    		mPlayer.stop();
    	}
    }
    //�?��录音 第一次录音时执行 �?��录音文件存放路径
    public void start(String name) {  
        if (!Environment.getExternalStorageState().equals(  
                android.os.Environment.MEDIA_MOUNTED)) {  
            return;  
        }
        if (mRecorder == null) {  
            mRecorder = new MediaRecorder();  
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC); 
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);  
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);  
            mRecorder.setOutputFile(new FileUtils().getSDPATH()+"/Video/"+name);
            try {
                mRecorder.prepare();
                mRecorder.start();
                android.util.Log.i("json", "-mRecorder-�?��-");
                mEMA = 0.0;  
            } catch (IllegalStateException e) {
                System.out.print(e.getMessage());  
            } catch (IOException e) {  
                System.out.print(e.getMessage());  
            }  
  
        }  
    }
    //关闭录音
    public void stop() {  
        if (mRecorder != null) {  
            mRecorder.stop();  
            android.util.Log.i("json", "-mRecorder-结束-");
            mRecorder.release();  
            mRecorder = null;  
        }
    }
   //暂停录音
    public void pause() {  
        if (mRecorder != null) {  
            mRecorder.stop();  
        }  
    }  
    //继续录音
    public void start() {  
        if (mRecorder != null) {  
            mRecorder.start(); 
        }  
    }  
  
    public double getAmplitude() {  
        if (mRecorder != null)  
            return (mRecorder.getMaxAmplitude() / 2700.0);
        else  
            return 0;  
  
    }  
  
    public double getAmplitudeEMA() {  
        double amp = getAmplitude();  
        mEMA = EMA_FILTER * amp + (1.0 - EMA_FILTER) * mEMA;  
        return mEMA;  
    }
	@Override
	public void onCompletion(MediaPlayer player) { 
		  playerStop();
	}  
}

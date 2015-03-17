package com.cn.ouan.xunchat.adapter;

 
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.cn.ouan.xunchat.R;

public class GVphizAdapter extends BaseAdapter{

	private int[] phizs;
	private Context context;

	public GVphizAdapter(Context context) {
		this.context=context;
		int a = 0;
		Field[] fields = R.drawable.class.getDeclaredFields();
		phizs = new int[200];
		for (Field field : fields) {
			if (field.getName().length() > 4) {
				if ("griz".equals(field.getName().substring(0, 4))) {
					try {
						phizs[a] = field.getInt(field.getName());
						Log.i("josn¡ªphizs", "" + phizs[a]);
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					a += 1;
				}
				Log.i("josn", "" + field.getName().substring(0, 4));
			}
		}
	}

	@Override
	public int getCount() {
		return phizs.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View view, ViewGroup parent) {
		ViewHodler hodler = null;
		if (view == null) {
			hodler = new ViewHodler();
			view = LayoutInflater.from(context).inflate(
					R.layout.chat_sendmsg_emotion, null);
			view.setTag(hodler);
		} else {
			hodler = (ViewHodler) view.getTag();
		}
		hodler._iv_sendmsg_emotion = (ImageView) view
				.findViewById(R.id.blog_sendmsg_emotion);
		if (phizs[position] != 0) {
			Bitmap bitmap = BitmapFactory.decodeResource(
					context.getResources(), phizs[position]); 
			Matrix matrix = new Matrix();
			int width = bitmap.getWidth();
			int height = bitmap.getHeight();
			int newWidth = 50;
			int newHeight = 50;
			float scaleWidth = ((float) newWidth) / width;
			float scaleHeight = ((float) newHeight) / height;
			matrix.postScale(scaleWidth, scaleHeight);
			bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height,
					matrix, true);
			hodler._iv_sendmsg_emotion.setImageBitmap(bitmap);

			view.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
//					SpannableStringBuilder sb = new SpannableStringBuilder();
//					// 21308376690
//					String dummyText = "dummy" + phizs[position];
//					sb.append(dummyText);
//					ImageSpan imageSpan = new ImageSpan(context,
//							phizs[position]);
//					sb.setSpan(imageSpan, 0, sb.length(),  Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
					Intent intent=new Intent("chat_phiz");
					intent.putExtra("chat_message", ""+phizs[position]);
					context.sendBroadcast(intent);
				}
			});
		}
		return view;
	}

class ViewHodler{
	ImageView _iv_sendmsg_emotion;
}
}

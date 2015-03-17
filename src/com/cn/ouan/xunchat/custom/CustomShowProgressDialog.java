package com.cn.ouan.xunchat.custom;

import com.cn.ouan.xunchat.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
 
/**
 * �Զ���dialog
 * @author Mr.Xu
 *
 */
public class CustomShowProgressDialog extends Dialog {
	//����ص��¼�������dialog�ĵ���¼�
	public interface OnCustomDialogListener{
		public void back(String name);
	}
	
	public CustomShowProgressDialog(Context context,String name,Boolean b) {
		super(context, R.style.loading_logo_small);
		CustomShowProgressDialog.this.setCanceledOnTouchOutside(b);
		CustomShowProgressDialog.this.getWindow().setBackgroundDrawableResource(R.color.transparent);
		CustomShowProgressDialog.this.show();
//		this.customDialogListener = customDialogListener;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		getWindow().setBackgroundDrawableResource(R.color.transparent);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_loading_logo);
		//���ñ���
//		setTitle(name); 
//		etName = (EditText)findViewById(R.id.edit);
//		Button clickBtn = (Button) findViewById(R.id.clickbtn);
//		clickBtn.setOnClickListener(clickListener);
	}
	
	private View.OnClickListener clickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
//			customDialogListener.back(String.valueOf(etName.getText()));
		    CustomShowProgressDialog.this.dismiss();
		}
	};

	public void cancelDilog() {
		CustomShowProgressDialog.this.cancel(); 
	}
}

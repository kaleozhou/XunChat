package com.cn.ouan.xunchat;
  
import android.app.Activity; 
import android.os.Bundle; 
import android.view.Menu;
import android.widget.Toast;

public class BaseActivity extends Activity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_base);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.base, menu);
		return true;
	}

	protected void showToast(String name) {
		Toast.makeText(this, name, Toast.LENGTH_LONG).show();
	}
}

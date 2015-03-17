package com.cn.ouan.xunchat.view;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cn.ouan.xunchat.R;
 
public class FacilityFragment extends Fragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v=LayoutInflater.from(getActivity()).inflate(R.layout.activity_facility, null);
		setUpView(v);
		setListener();
		return v;
	}
	/**
	 *  init control;
	 * @param v
	 */
	private void setUpView(View v) {
		
		
	}

	private void setListener() {
		
	}

}

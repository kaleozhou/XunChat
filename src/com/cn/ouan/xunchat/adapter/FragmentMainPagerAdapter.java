package com.cn.ouan.xunchat.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

 
 
public class FragmentMainPagerAdapter extends FragmentPagerAdapter{

	private List<Fragment> lists;

	public FragmentMainPagerAdapter(FragmentManager fm,List<Fragment> lists) {
		super(fm);
	 this.lists=lists;
	}

	 

	@Override
	public Fragment getItem(int position) {
		// TODO Auto-generated method stub
		return lists.get(position);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lists.size();
	} }

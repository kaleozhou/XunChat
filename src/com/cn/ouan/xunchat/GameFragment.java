package com.cn.ouan.xunchat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class GameFragment extends Fragment {


@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	View v=LayoutInflater.from(getActivity()).inflate(R.layout.activity_game_fragment, null);
	return v;
}

}

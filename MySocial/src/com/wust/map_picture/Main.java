package com.wust.map_picture;


import com.example.mysocial.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TabHost.TabSpec;

public class Main extends FragmentActivity {
	private FragmentTabHost mTabHost;
	private LayoutInflater layoutInflater;


	private Class<?> fragmentArray[] = { map.class,share.class,
			mymap.class, };


	private int mImageViewArray[] = { R.drawable.tab_map,R.drawable.tab_share,
			R.drawable.tab_mymap};


	private int mTextviewArray[] = { R.string.tab_map, R.string.tab_share,
			R.string.tab_mymap};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}


	private void initView() {

		layoutInflater = LayoutInflater.from(this);


		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.container);

		for (int i = 0; i < fragmentArray.length; i++) {

			TabSpec taSpec = mTabHost.newTabSpec(getString(mTextviewArray[i]))
					.setIndicator(getTabItemView(i));

			mTabHost.addTab(taSpec, fragmentArray[i], null);
		}
	}


	private View getTabItemView(int index) {
		View view = layoutInflater.inflate(R.layout.tab_view, null);
		ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
		imageView.setImageResource(mImageViewArray[index]);

		TextView textView = (TextView) view.findViewById(R.id.textview);
		textView.setText(getString(mTextviewArray[index]));

		return view;
	}


}

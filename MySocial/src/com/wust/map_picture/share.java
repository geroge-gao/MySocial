package com.wust.map_picture;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.example.adapter.SayingAdapter;
import com.example.entity.Saying;
import com.example.mysocial.R;
import com.example.ui.CommentActivity;
import com.example.ui.Editctivity;
import com.example.ui.MainActivity;
import com.example.ui.PersonEditActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class share extends Fragment implements OnClickListener{
	
	private ImageView rightMenu;
	private ImageView leftMenu;
	private TextView topbar;
	ListView saying_list;
	Intent intent;
	SayingAdapter sAdapter;
	ProgressBar progress;
	TextView progressText;
	List<Saying> list = new ArrayList<Saying>();
	View view;
	Saying saying;
	
	//添加评论和点赞的监听
	
	TextView item_action_love;
	TextView item_action_comment;
	TextView item_action_share;

	// 缓存Fragment的View
	private View rootView;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		rightMenu.setOnClickListener(this);
		leftMenu.setOnClickListener(this);	
		saying_list.setCacheColorHint(0);
		saying_list.setScrollingCacheEnabled(false);
		saying_list.setScrollContainer(false);
		saying_list.setFastScrollEnabled(true);
		saying_list.setSmoothScrollbarEnabled(true);
		
		// 每当重新进入Activity时，加载数据
		showSaying();
		//点击后跳转到该界面	
		
		
		saying_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
		
				Intent intent=new Intent();
				saying=(Saying) sAdapter.getDataList().get(arg2);
				intent.putExtra("data", saying);
				intent.setClass(getActivity(), CommentActivity.class);
				startActivity(intent);
			}
		});
		
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (rootView == null) {
			rootView = inflater.inflate(R.layout.center_frame, container,
					false);
			rightMenu = (ImageView) rootView.findViewById(R.id.topbar_menu_right);
			leftMenu = (ImageView) rootView.findViewById(R.id.topbar_menu_left);
			saying_list = (ListView) rootView.findViewById(R.id.saying_list);
			topbar = (TextView) rootView.findViewById(R.id.topbar_title);
			progress = (ProgressBar) rootView.findViewById(R.id.progess);
			progressText = (TextView) rootView.findViewById(R.id.progressText);		
			
		} else {

			ViewGroup parent = (ViewGroup) rootView.getParent();
			if (parent != null) {
				parent.removeView(rootView);
			}
		}
		return rootView;
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.topbar_menu_left:
            intent=new Intent();
            intent.setClass(getActivity(), PersonEditActivity.class);
            startActivity(intent);
			break;
		case R.id.topbar_menu_right:
			intent = new Intent(getActivity(), Editctivity.class);
			startActivity(intent);
		case R.id.topbar_title:
			showSaying();
			break;
		case R.id.item_action_love:
			int love=saying.getLove();
			Log.e("Love","当前点赞数为"+love);
			saying.setLove(love+1);
			item_action_love.setText(love+1+"");
			showSaying();
		break;
		case R.id.item_action_comment:
			break;
		case R.id.item_action_share:
			
		}
	}
public void showSaying() {
		
		progress.setVisibility(View.VISIBLE);
		progressText.setVisibility(View.VISIBLE);
		BmobQuery<Saying> query = new BmobQuery<Saying>();
		query.include("author");
		query.order("-createdAt");// 按照时间降序获取对应的结果
		query.findObjects(getActivity(), new FindListener<Saying>() {

			@Override
			public void onSuccess(List<Saying> data) {
				// TODO Auto-generated method stub				
				sAdapter = new SayingAdapter(getActivity(), data);
				saying_list.setAdapter(sAdapter);
				progress.setVisibility(View.GONE);
				progressText.setVisibility(View.GONE);
			}

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				Toast.makeText(getActivity(), "获取动态失败" + arg1,
						Toast.LENGTH_LONG).show();
			}
		});
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		showSaying();
	}

}

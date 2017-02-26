package com.example.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.example.adapter.SayingAdapter;
import com.example.entity.Saying;
import com.example.mysocial.R;

public class MainActivity extends Activity implements OnClickListener {

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
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.center_frame);
		rightMenu = (ImageView) findViewById(R.id.topbar_menu_right);
		leftMenu = (ImageView) findViewById(R.id.topbar_menu_left);
		rightMenu.setOnClickListener(this);
		leftMenu.setOnClickListener(this);
		saying_list = (ListView) findViewById(R.id.saying_list);
		topbar = (TextView) findViewById(R.id.topbar_title);
		progress = (ProgressBar) findViewById(R.id.progess);
		progressText = (TextView) findViewById(R.id.progressText);		
		
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
				intent.setClass(MainActivity.this, CommentActivity.class);
				startActivity(intent);
			}
		});
		
	};

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.topbar_menu_left:
            intent=new Intent();
            intent.setClass(MainActivity.this, PersonEditActivity.class);
            startActivity(intent);
			break;
		case R.id.topbar_menu_right:
			intent = new Intent(MainActivity.this, Editctivity.class);
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

	// 加载动态
	public void showSaying() {
		
		progress.setVisibility(View.VISIBLE);
		progressText.setVisibility(View.VISIBLE);
		BmobQuery<Saying> query = new BmobQuery<Saying>();
		query.include("author");
		query.order("-createdAt");// 按照时间降序获取对应的结果
		query.findObjects(MainActivity.this, new FindListener<Saying>() {

			@Override
			public void onSuccess(List<Saying> data) {
				// TODO Auto-generated method stub				
				sAdapter = new SayingAdapter(MainActivity.this, data);
				saying_list.setAdapter(sAdapter);
				progress.setVisibility(View.GONE);
				progressText.setVisibility(View.GONE);
			}

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				Toast.makeText(MainActivity.this, "获取动态失败" + arg1,
						Toast.LENGTH_LONG).show();
			}
		});
	}
	
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		showSaying();
	}

}

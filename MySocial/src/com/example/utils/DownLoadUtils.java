package com.example.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.example.entity.Picture;
import com.example.entity.User;

public class DownLoadUtils {
	
	private Context context;
	public DownLoadUtils(Context context) {
		// TODO Auto-generated constructor stub
		this.context=context;
	}
	
	//获取所有的图片
	public  List<Picture> getdata(){

		List<Picture> list=new ArrayList<Picture>();
		BmobQuery<Picture> query=new BmobQuery<Picture>();
		query.findObjects(context, new FindListener<Picture>() {

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(List<Picture> data) {
				// TODO Auto-generated method stub
				
			}
		});
		
		return list;
	}
	
	//获取个人图片
	public List<Picture> getPerson(){
		List<Picture> list=new ArrayList<Picture>();
		User user=User.getCurrentUser(context, User.class);
		list=user.getPics();		
		return list;
	}	

}
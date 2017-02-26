package com.example.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

import com.example.entity.Picture;
import com.example.entity.User;
import com.example.ui.PersonEditActivity;

public class UploadUtils {
	
	private double longitude;
	private double latitude;
	private  Context context;
	private  String path;
	private List<Picture> list;
	
	public UploadUtils(double longitude,double latitude,Context context,String path) {
		// TODO Auto-generated constructor stub
		this.longitude=longitude;
		this.latitude=latitude;
		this.context=context;
		this.path=path;
	}
	
	public  void upload(){
		File file =new File(path);
		Log.e("error", path);
		final BmobFile bf=new BmobFile(file);
		
		bf.upload(context, new UploadFileListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				Picture p=new Picture();
				p.setLongitude(longitude);
				p.setLatitude(latitude);
				p.setPic(bf);
				final User user=User.getCurrentUser(context, User.class);
				list=new ArrayList<Picture>();
				if(user.getPics()!=null)
					list=user.getPics();
				
				list.add(p);
				p.save(context,new SaveListener() {
					
					@Override
					public void onSuccess() {
						// TODO Auto-generated method stub
						Toast.makeText(context, "Picture保存成功", Toast.LENGTH_LONG).show();
						user.setPics(list);
						user.update(context,new UpdateListener() {
							
							@Override
							public void onSuccess() {
								// TODO Auto-generated method stub
								Toast.makeText(context, "信息保存成功", Toast.LENGTH_LONG).show();
							}
							
							@Override
							public void onFailure(int arg0, String arg1) {
								// TODO Auto-generated method stub
								Toast.makeText(context, "信息保存失败"+arg1, Toast.LENGTH_LONG).show();
							}
						});
					}
					
					@Override
					public void onFailure(int arg0, String arg1) {
						// TODO Auto-generated method stub
						
					}
				});
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				Toast.makeText(context, "文件上传失败"+arg1, Toast.LENGTH_LONG).show();
			}
		});
		
			
		}
	
}
			
	
		
	


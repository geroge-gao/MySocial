package com.example.adapter;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UpdateListener;

import com.example.entity.Saying;
import com.example.entity.User;
import com.example.mysocial.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class SayingAdapter extends BaseContentAdapter {

	Saying saying;
	private AnimateFirstDisplayListener listener=new AnimateFirstDisplayListener();
	private ImageLoader imageLoader=ImageLoader.getInstance();
	private DisplayImageOptions options=new DisplayImageOptions.Builder().cacheInMemory().cacheOnDisc(true).build();
	
	@SuppressWarnings("unchecked")
	public SayingAdapter(Context context, List list) {
		super(context, list);
		// TODO Auto-generated constructor stub
	}
	
	
	// 装载进来的东西
	@SuppressWarnings("null")
	@Override
	public View getConvertView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		
		
		ViewHolder mHolder = null;
		if (convertView == null) {
			mHolder = new ViewHolder();
			convertView=mInflater.inflate(R.layout.ai_item, null);

			// 将动态的所有组件装载到ViewHolder里面
			mHolder.user_logo=(ImageView)convertView.findViewById(R.id.user_logo);
		    mHolder.user_name = (TextView) convertView
					.findViewById(R.id.user_name);
			mHolder.content_image = (ImageView) convertView
					.findViewById(R.id.content_image);
			mHolder.content_text = (TextView) convertView
					.findViewById(R.id.content_text);
			mHolder.item_action_comment = (TextView) convertView
					.findViewById(R.id.item_action_comment);
			mHolder.item_action_love = (TextView) convertView
					.findViewById(R.id.item_action_love);
			mHolder.item_action_share = (TextView) convertView
					.findViewById(R.id.item_action_share);
			convertView.setTag(mHolder);
			mHolder.createAt=(TextView)convertView.findViewById(R.id.createTiem);

		} else {
			mHolder = (ViewHolder) convertView.getTag();
			Log.e("SayingAdapter", "动态获取成功");
		}

		// 获取当前的Saying
		final Saying saying = (Saying) dataList.get(position);
		if (saying.getAuthor() != null) {
			String nickname;
			User author=saying.getAuthor();
			nickname=author.getNickName();
			if(!"".equals(nickname)){
				mHolder.user_name.setText(nickname);
			}else{
				mHolder.user_name.setText(author.getUsername());
			}
			
			//加载用户的图像
			BmobFile bf=author.getIcon();
			if(bf!=null){
				String url="http://file.bmob.cn/"+bf.getUrl();
				imageLoader.displayImage(url, mHolder.user_logo,options,listener);
			}
			
		} else {
			
			mHolder.user_name.setText("佚名");
		}
		
		if(saying.getContent()!=null){
			mHolder.content_text.setText(saying.getContent());
		}else{
			mHolder.content_text.setText("");
		}
		
		//使用ImageLoader加载图片的缓存，避免ListView发生闪烁
		if(saying.getContentfigureurl()!=null){			
			BmobFile bf=saying.getContentfigureurl();
			Log.e("Bmob", bf.getUrl());
			String url="http://file.bmob.cn/"+bf.getUrl();
			imageLoader.displayImage(url, mHolder.content_image, options, listener);
		}
		
		if(saying.getLove()!=0){
			saying.setMyLove(true);
		}
		
		if(saying.isMyLove()){
			mHolder.item_action_love.setTextColor(Color.parseColor("#D95555"));
			mHolder.item_action_love.setText(saying.getLove()+"");
		}else{
			mHolder.item_action_love.setTextColor(Color.parseColor("#000000"));
			mHolder.item_action_love.setText(saying.getLove()+"");
		}
		
		
		//在Adapter内里面对对应的时间进行绑定监听
		mHolder.item_action_love.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				int love=saying.getLove();
				love+=1;
				saying.setLove(love);
				
				saying.update(mContext,new UpdateListener() {
					
					@Override
					public void onSuccess() {
						// TODO Auto-generated method stub
						notifyDataSetChanged();
						Toast.makeText(mContext, "点赞成功", Toast.LENGTH_LONG).show();						
					}
					
					@Override
					public void onFailure(int arg0, String arg1) {
						// TODO Auto-generated method stub
						Toast.makeText(mContext, "点赞失败"+arg1, Toast.LENGTH_LONG).show();
					}
				});
			}
		});
		
		
		mHolder.createAt.setText(saying.getCreatedAt());	
		return convertView;
	}
	

	
	 private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {  
         
	        static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());  
	  
	        @Override  
	        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {  
	            if (loadedImage != null) {  
	                ImageView imageView = (ImageView) view;  
	                // 是否第一次显示  
	                boolean firstDisplay = !displayedImages.contains(imageUri);  
	                if (firstDisplay) {  
	                    // 图片淡入效果  
	                    FadeInBitmapDisplayer.animate(imageView, 500);  
	                    displayedImages.add(imageUri);  
	                }  
	            }  
	        }  
	    }  

	public static class ViewHolder {
		public TextView user_name;
		public ImageView user_logo;
		public TextView content_text;
		public ImageView content_image;
		public TextView item_action_comment;
		public TextView item_action_share;
		public TextView item_action_love;
		public TextView createAt;

	}

}

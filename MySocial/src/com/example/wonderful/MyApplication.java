package com.example.wonderful;

import java.io.File;

import android.app.Activity;
import android.app.Application;
import android.graphics.Bitmap;
import cn.bmob.v3.BmobUser;

import com.example.entity.Saying;
import com.example.entity.User;
import com.example.utils.ActivityManagerUtils;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.utils.StorageUtils;

public class MyApplication extends Application {

	public static String TAG;

	private static MyApplication myApplication = null;

	private Saying currentSaying = null;

	public static MyApplication getInstance() {
		return myApplication;
	}

	public User getCurrentUser() {
		User user = BmobUser.getCurrentUser(myApplication, User.class);
		if (user != null) {
			return user;
		}
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		TAG = this.getClass().getSimpleName();
		// ����Application�౾���Ѿ�����������ֱ�Ӱ����´����ɡ�
		myApplication = this;
		initImageLoader();
	}

	public Saying getCurrentSaying() {
		return currentSaying;
	}

	public void setCurrentSaying(Saying currentSaying) {
		this.currentSaying = currentSaying;
	}

	public void addActivity(Activity ac) {
		ActivityManagerUtils.getInstance().addActivity(ac);
	}

	public void exit() {
		ActivityManagerUtils.getInstance().removeActivity();
	}

	public Activity getTopActivity() {
		return ActivityManagerUtils.getInstance().getTopActivity();
	}

	/**
	 * ��ʼ��imageLoader
	 */
	public void initImageLoader() {
		File cacheDir = StorageUtils.getCacheDirectory(getApplicationContext());
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getApplicationContext())
				.memoryCache(new LruMemoryCache(5 * 1024 * 1024))
				.memoryCacheSize(10 * 1024 * 1024)
				.discCache(new UnlimitedDiscCache(cacheDir))
				.discCacheFileNameGenerator(new HashCodeFileNameGenerator())
				.build();
		ImageLoader.getInstance().init(config);
	}

	public DisplayImageOptions getOptions(int drawableId) {
		return new DisplayImageOptions.Builder().showImageOnLoading(drawableId)
				.showImageForEmptyUri(drawableId).showImageOnFail(drawableId)
				.resetViewBeforeLoading(true).cacheInMemory(true)
				.cacheOnDisc(true).imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.RGB_565).build();
	}

}

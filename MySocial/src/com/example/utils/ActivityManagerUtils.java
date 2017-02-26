package com.example.utils;

import java.util.ArrayList;

import android.app.Activity;


/*
 * Activity的收集一直释放
 */

public class ActivityManagerUtils {
	
	private ArrayList<Activity> list=new ArrayList<Activity>();
	private static ActivityManagerUtils activityManagerUtils;
	
	
	public ActivityManagerUtils() {
		// TODO Auto-generated constructor stub
	}
	
	public static ActivityManagerUtils getInstance(){
		
		if(activityManagerUtils==null){
			activityManagerUtils=new ActivityManagerUtils();
		}		
		return activityManagerUtils;
	}
	
	public Activity getTopActivity(){
		return list.get(list.size()-1);
	}
	
	public void addActivity(Activity activity){
		list.add(activity);
	}
	
	public void removeActivity(){
		for(Activity activity:list){
			if(activity!=null){
				if(!activity.isFinishing()){
					activity.finish();
				}				
				activity=null;
			}
		}
		
		list.clear();
	}

}

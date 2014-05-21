package com.general.mediaplayer.Utils;

import android.app.Activity;
import android.app.Application;

import java.util.LinkedList;
import java.util.List;

public class ExitApplication extends Application {
	private List<Activity> activityList=new LinkedList<Activity>();
	private static ExitApplication instance;
	
	private ExitApplication(){
		
	}

    // get instance of the singleton
	public static ExitApplication getInstance()
	{
		if(null == instance)
		{
			instance = new ExitApplication();
		}
		return instance;
	}	

    // add activity to list
	public void addActivity(Activity activity)
	{
		activityList.add(activity);
	}
	
	public void removeActivity(Activity activity){
		for(Activity activity1:activityList)
		{
			if(activity1==activity){
				activityList.remove(activity1);
				return;
			}
		}
	}
	
	// traverse any Activity and finish
	public void exit()
	{
		for(Activity activity:activityList)
		{
			activity.finish();
		}
		System.exit(0);
	}
}

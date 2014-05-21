package com.general.mediaplayer.Utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

public class StartCsrBroadcastReceiver {
    public static final String ACTION="com.android.intent.CSR";
    private final static String SUPER_MANAGER_MODE_IN_ACTION="com.general.mediaplayer.startsupermode";
    private final static String SEND_APP_RUN_ACTION="com.general.mediaplayer.sendapprun";
    private final static String RECEIVER_APP_RUN_ACTION="com.general.mediaplayer.receiverapprun";

    private Context mContext;
	
	public StartCsrBroadcastReceiver(Context context){
		mContext=context;
	}
	
	private BroadcastReceiver mStartcsrReceiver= new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if(intent.getAction().equalsIgnoreCase(ACTION)){
				Log.v("com.general.mediaplayer. Receiver", "==com.general.mediaplayer.startcsr==");
				ExitApplication.getInstance().exit();
			}
			else if(intent.getAction().equalsIgnoreCase(SUPER_MANAGER_MODE_IN_ACTION)){
				//Log.v("com.general.mediaplayer. Receiver", "==com.general.mediaplayer.startsupermode==");
				ExitApplication.getInstance().exit();
			}
			else if(intent.getAction().equalsIgnoreCase(SEND_APP_RUN_ACTION)){
				//Log.v("com.general.mediaplayer. Receiver", "== Receiver=="+intent.getAction());
				Intent intent1= new Intent();
				intent1.setAction(RECEIVER_APP_RUN_ACTION);
				mContext.sendBroadcast(intent1);
			}
		}
	};
	
	public void registerStartCsrBroadcastReceiver(){
		IntentFilter filter=new IntentFilter();
		filter.addAction(ACTION);
		filter.addAction(SUPER_MANAGER_MODE_IN_ACTION);
		filter.addAction(SEND_APP_RUN_ACTION);
		filter.setPriority(905);
		mContext.registerReceiver(mStartcsrReceiver, filter);
	}
	
	public void unregisterStartCsrBroadcastReceiver(){
		mContext.unregisterReceiver(mStartcsrReceiver);
	}
}

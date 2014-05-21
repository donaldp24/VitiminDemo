package com.general.mediaplayer.Utils;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by donald on 4/17/14.
 */
public class CsrActivity extends Activity {

    protected StartCsrBroadcastReceiver mStartCsrBroadcastReceiver;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // added to exitapplication
        ExitApplication.getInstance().addActivity(this);
        mStartCsrBroadcastReceiver= new StartCsrBroadcastReceiver(this);
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        if(mStartCsrBroadcastReceiver !=null){
            mStartCsrBroadcastReceiver.unregisterStartCsrBroadcastReceiver();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        if(mStartCsrBroadcastReceiver !=null){
            mStartCsrBroadcastReceiver.registerStartCsrBroadcastReceiver();
        }
        super.onResume();
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        ExitApplication.getInstance().removeActivity(this);
        super.onDestroy();
    }
}
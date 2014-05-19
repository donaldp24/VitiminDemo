package com.general.mediaplayer.VitiminDemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: Donald Pae
 * Date: 1/17/14
 * Time: 11:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class BaseActivity extends Activity {

    protected Date lastInteractionTime = new Date();
    //private Handler myHandler;
    private boolean bRunTimeCount = true;
    private Thread myThread;
    protected AppPreferences _appPrefs;
    protected boolean isMainScreen = false;

    protected Runnable PlayVideo = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent(BaseActivity.this, VideoActivity.class);
            startActivity(intent);
            overridePendingTransition(TransformManager.GetVideoInAnim(), TransformManager.GetVideoOutAnim());
            finish();
        }
    };

    protected Runnable BackToMain = new Runnable() {
        @Override
        public void run() {
            if (isMainScreen == false)
            {
                Intent intent = new Intent(BaseActivity.this, ScanMediaActivity.class);
                startActivity(intent);
                overridePendingTransition(TransformManager.GetVideoInAnim(), TransformManager.GetVideoOutAnim());
                finish();
            }
        }
    };

    protected Runnable GotoSleep = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent(BaseActivity.this, BlankActivity.class);
            startActivity(intent);
            overridePendingTransition(TransformManager.GetVideoInAnim(), TransformManager.GetVideoOutAnim());
        }
    };


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _appPrefs = new AppPreferences(getApplicationContext());
    }

    @Override
    public void onStart()
    {
        super.onStart();

        lastInteractionTime = new Date();
        bRunTimeCount = true;

        myThread = new Thread(new Runnable() {
            @Override
            public void run() {
                lastInteractionTime = new Date();
                while(bRunTimeCount)
                {
                    Date currTime = new Date();
                    long diffInMs = currTime.getTime() - lastInteractionTime.getTime();
                    long diffInSec = TimeUnit.MILLISECONDS.toSeconds(diffInMs);
                    if (diffInSec >= CommonData.TIME_ACTION_NOTAP)
                    {
                        if (CommonData.ACTION_NOTAP == CommonData.ACTION_VIDEOPLAY)
                            runOnUiThread(PlayVideo);
                        else if (CommonData.ACTION_NOTAP == CommonData.ACTION_BACKTOMAIN)
                            runOnUiThread(BackToMain);
                        else if (CommonData.ACTION_NOTAP == CommonData.ACTION_SLEEP)
                            runOnUiThread(GotoSleep);
                        // else CommonData.ACTION_NOTAP == CommonData.ACTION_NONE
                        break;
                    }
                    else
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                        }
                }
                bRunTimeCount = false;
            }
        });
        myThread.start();
    }

    @Override
    public void onStop()
    {
        super.onStop();

        bRunTimeCount = false;

        try {
            //myThread.join();
            myThread.interrupt();
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    @Override
    public void onUserInteraction()
    {
        super.onUserInteraction();
        lastInteractionTime = new Date();
    }
}
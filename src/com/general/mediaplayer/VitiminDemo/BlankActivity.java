package com.general.mediaplayer.VitiminDemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;

/**
 * Created by donald on 4/18/14.
 */
public class BlankActivity extends Activity {

    float orgBrightness = 0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.flags |= WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
        orgBrightness = params.screenBrightness;
        params.screenBrightness = 0;
        getWindow().setAttributes(params);
    }

    @Override
    public void onStop() {
        super.onStop();

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.flags |= WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
        params.screenBrightness = orgBrightness;
        getWindow().setAttributes(params);
    }

    @Override
    public void onUserInteraction()
    {
        super.onUserInteraction();

        finish();
        overridePendingTransition(TransformManager.GetVideoInAnim(), TransformManager.GetVideoOutAnim());

    }
}
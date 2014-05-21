package com.general.mediaplayer.VitiminDemo;

import android.graphics.Rect;

/**
 * Created with IntelliJ IDEA.
 * User: Donald Pae
 * Date: 1/17/14
 * Time: 9:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class CommonData {

    public static final int START_SERVER = 1;

    // no taps for a long time, type of action
    public static final int ACTION_NONE = -1;
    public static final int ACTION_VIDEOPLAY = 0;
    public static final int ACTION_BACKTOMAIN = 1;
    public static final int ACTION_SLEEP = 2;

    public static final int ACTION_NOTAP = ACTION_BACKTOMAIN;
    public static final int TIME_ACTION_NOTAP = 60; //60 seconds

    // playing video time from no interaction (in seconds)
    public static final int VIDEO_LOOPING = 1;


    // You have to set this flag as 1 to be enable lighting.
    public static final int LIGHT_NONE = -1;
    public static final int LIGHT_COMM = 0;
    public static final int LIGHT_USBHID = 1;

    public static final int LIGHT_MODE = LIGHT_COMM;


    //enum for transform
    public static final int TRANSFORM_ALPHA_FADE = 0;
    public static final int TRANSFORM_TRANSFORM_SLIDE = 1;
    public static final int TRANSFORM_ALPHA_SLIDE = 2;

    public static final int transformAnimation = TRANSFORM_ALPHA_SLIDE; //trans_0

    // these are subids for lights of every kind-------------------------------------------------------
   public static final int SUBID_HERBAL = 1;
    public static final int SUBID_MINERALS = 2;
    public static final int SUBID_VITIMIN = 3;
    public static final int SUBID_ORGANIC = 4;
    public static final int SUBID_MULTI = 5;
    public static final int SUBID_SUPPLEMENTS = 6;

    // keys of result info(screen positions and images)
    public static final int KEY_CHILDREN = 0;
    public static final int KEY_MEN = 1;
    public static final int KEY_WOMEN = 2;

    public static final int KEY_HERBAL = 0;
    public static final int KEY_MINERALS = 1;
    public static final int KEY_VITIMIN = 2;
    public static final int KEY_ORGANIC = 3;
    public static final int KEY_MULTI = 4;
    public static final int KEY_SUPPLEMENTS = 5;

    public static final String PARAM_KEY = "param_key";
    public static final String PARAM_SUBID = "param_subid";

    // result screen arranger
    public static ResultArranger arranger = null;

    // initialize Suitable option and ...
    public static void initialize()
    {
        /*
        ResultInfo info;

        // architecture result
        info = new ResultInfo();
        info.key = KEY_ARCHITECTURE;
        info.idIcon = R.drawable.architecture_resulticon;
        info.rtIcon = new Rect(171, 134, 524, 364);
        info.idText = R.drawable.architecture_resulttext;
        info.rtText = new Rect(373, 357, 410, 80);
        info.subId = SUBID_ARCHITECTURE;
        ResultInfo.registerResultInfo(info);

        // exclusives result
        info = new ResultInfo();
        info.key = KEY_EXCLUSIVE;
        info.idIcon = R.drawable.exclusives_icon;
        info.rtIcon = new Rect(33, 133, 664, 268);
        info.idText = R.drawable.exclusives_text;
        info.rtText = new Rect(566, 407, 156, 51);
        info.subId =  SUBID_EXCLUSIVE;
        ResultInfo.registerResultInfo(info);

        // agerange0 result
        info = new ResultInfo();
        info.key = KEY_AGERANGE0;
        info.idIcon = R.drawable.agerange_resulticon;
        info.rtIcon = new Rect(56, 118, 348, 340);
        info.idText = R.drawable.agerange_resulttext;
        info.rtText = new Rect(426, 383, 220, 62);
        info.subId =  SUBID_AGERANGE0;
        ResultInfo.registerResultInfo(info);


        // castle result
        info = new ResultInfo();
        info.key = KEY_CASTLE;
        info.idIcon = R.drawable.architecture_resulticon;
        info.rtIcon = new Rect(171, 134, 524, 364);
        info.idText = R.drawable.architecture_resulttext;
        info.rtText = new Rect(373, 357, 410, 80);
        info.subId = SUBID_CASTLE;
        ResultInfo.registerResultInfo(info);

        // city result
        info = new ResultInfo();
        info.key = KEY_CITY;
        info.idIcon = R.drawable.architecture_resulticon;
        info.rtIcon = new Rect(171, 134, 524, 364);
        info.idText = R.drawable.architecture_resulttext;
        info.rtText = new Rect(373, 357, 410, 80);
        info.subId = SUBID_CITY;
        ResultInfo.registerResultInfo(info);

        // galaxy result
        info = new ResultInfo();
        info.key = KEY_GALAXY;
        info.idIcon = R.drawable.architecture_resulticon;
        info.rtIcon = new Rect(171, 134, 524, 364);
        info.idText = R.drawable.architecture_resulttext;
        info.rtText = new Rect(373, 357, 410, 80);
        info.subId = SUBID_GALAXY;
        ResultInfo.registerResultInfo(info);

        // juniors result
        info = new ResultInfo();
        info.key = KEY_JUNIORS;
        info.idIcon = R.drawable.architecture_resulticon;
        info.rtIcon = new Rect(171, 134, 524, 364);
        info.idText = R.drawable.architecture_resulttext;
        info.rtText = new Rect(373, 357, 410, 80);
        info.subId = SUBID_JUNIORS;
        ResultInfo.registerResultInfo(info);

        // starwars result
        info = new ResultInfo();
        info.key = KEY_STARWAS;
        info.idIcon = R.drawable.architecture_resulticon;
        info.rtIcon = new Rect(171, 134, 524, 364);
        info.idText = R.drawable.architecture_resulttext;
        info.rtText = new Rect(373, 357, 410, 80);
        info.subId = SUBID_STARWARS;
        ResultInfo.registerResultInfo(info);
        */
        // arranger
        arranger = new ResultArranger();

    }
}

package com.general.mediaplayer.VitiminDemo;

import android.graphics.Rect;
import android.support.v4.util.SimpleArrayMap;


/**
 * Created by donald on 4/4/14.
 */
public class ResultInfo implements Cloneable {
    public Integer key;
    public int idIcon; // icon resource
    public Rect rtIcon; // icon rectangle

    public int idText; // text resource
    public Rect rtText; // text rectangle

    public int subId; // light id

    public static SimpleArrayMap<Integer, ResultInfo> infoMap = new SimpleArrayMap<Integer, ResultInfo>();

    public static void registerResultInfo(ResultInfo info)
    {
        infoMap.put(info.key, info);
    }

    public static ResultInfo getResultInfo(Integer key)
    {
        ResultInfo info = infoMap.get(key);
        return info;
    }
}

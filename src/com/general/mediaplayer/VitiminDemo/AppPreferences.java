package com.general.mediaplayer.VitiminDemo;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created with IntelliJ IDEA.
 * User: Donald Pae
 * Date: 1/18/14
 * Time: 4:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class AppPreferences {
    // define keys for preferences
    private static final String APP_SHARED_PREFS = "FindFormula";

    private static final String KEY_BASIC = "Basic";
    private static final String KEY_BREED = "Breed";
    private static final String KEY_LIFESTYLE = "Lifestyle";
    private static final String KEY_FOOD = "Food";

    private SharedPreferences shared_preferences;
    private SharedPreferences.Editor shared_preferences_editor;

    public AppPreferences(Context context) {
        this.shared_preferences = context.getSharedPreferences(APP_SHARED_PREFS, Activity.MODE_PRIVATE);
        this.shared_preferences_editor = shared_preferences.edit();
    }
/*
    public int getSelectedBasic() {
        return shared_preferences.getInt(KEY_BASIC, CommonData.getDefaultBasic());
    }

    public void setSelectedBasic(int selectedBasic) {
        shared_preferences_editor.putInt(KEY_BASIC, selectedBasic);
        shared_preferences_editor.commit();
    }
*/
}

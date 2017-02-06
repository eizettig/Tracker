package com.zettig.tracker.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by zettig on 06.02.17.
 */

public class ManagerShared {
    private Context context;
    private SharedPreferences settings;

    public ManagerShared(Context c) {
        context = c;
        setSettings(context.getSharedPreferences("mode", context.MODE_PRIVATE));
    }

    public void putKeyString(String key, String value) {
        SharedPreferences.Editor editor = getSettings().edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void putKeyBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = getSettings().edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public void putKeyInteger(String key, int value) {
        SharedPreferences.Editor editor = getSettings().edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public String getValueString(String key) {
        return getSettings().getString(key, "");
    }


    public boolean getValueBoolean(String key) {
        return getSettings().getBoolean(key, false);
    }

    public int getValueInteger(String key) {
        return getSettings().getInt(key, 0);
    }

    public void removeKey(String key) {
        if (getSettings().contains(key)) {
            SharedPreferences.Editor editor = getSettings().edit();
            editor.remove(key);
            editor.apply();
        }
    }

    public void putKeyLong(String key, long value) {
        SharedPreferences.Editor editor = getSettings().edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public long getValueLong(String key) {
        return getSettings().getLong(key, 0);
    }

    public SharedPreferences getSettings() {
        if (settings == null) {
            settings = context.getSharedPreferences("mode", context.MODE_PRIVATE);
        }
        return settings;
    }

    public void setSettings(SharedPreferences settings) {
        this.settings = settings;
    }
}
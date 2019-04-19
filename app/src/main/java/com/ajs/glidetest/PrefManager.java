package com.ajs.glidetest;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/**
 * Manage App settings stored in {@link SharedPreferences}
 * Created by DungNV.
 */
public class PrefManager {

    public static void saveSetting(Context context, String key, String value) {
        SharedPreferences pref = context.getSharedPreferences(UrlApp.PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void saveSetting(Context context, String key, int value) {
        SharedPreferences pref = context.getSharedPreferences(UrlApp.PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putInt(key, value);
        editor.commit();
    }

    public static void saveSetting(Context context, String key, boolean value) {
        SharedPreferences pref = context.getSharedPreferences(UrlApp.PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putBoolean(key, value);
        editor.commit();
    }

    public static void saveSetting(Context context, String key, float value) {
        SharedPreferences pref = context.getSharedPreferences(UrlApp.PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putFloat(key, value);
        editor.commit();
    }

    public static void saveSetting(Context context, String key, long value) {
        SharedPreferences pref = context.getSharedPreferences(UrlApp.PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putLong(key, value);
        editor.commit();
    }

    public static void saveSetting(Context context, String key, Set<String> value) {
        SharedPreferences pref = context.getSharedPreferences(UrlApp.PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putStringSet(key, value);
        editor.commit();
    }


    public static void saveString(Context context, String key, String value) {
        SharedPreferences pref = context.getSharedPreferences(UrlApp.PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void saveBoolean(Context context, String key, boolean value) {
        SharedPreferences pref = context.getSharedPreferences(UrlApp.PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static void saveInt(Context context, String key, int value) {
        SharedPreferences pref = context.getSharedPreferences(UrlApp.PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static void saveLong(Context context, String key, long value) {
        SharedPreferences pref = context.getSharedPreferences(UrlApp.PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    /**
     * ============== get method =====================
     */
    public static String getString(Context context, String key) {
        SharedPreferences pref = context.getSharedPreferences(UrlApp.PREF_FILE_NAME, Context.MODE_PRIVATE);
        return pref.getString(key, "");
    }

    public static String getString(Context context, String key, String defaultValue) {
        SharedPreferences pref = context.getSharedPreferences(UrlApp.PREF_FILE_NAME, Context.MODE_PRIVATE);
        return pref.getString(key, defaultValue);
    }

    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        SharedPreferences pref = context.getSharedPreferences(UrlApp.PREF_FILE_NAME, Context.MODE_PRIVATE);
        return pref.getBoolean(key, defaultValue);
    }

    public static long getLong(Context context, String key, long defaultValue) {
        SharedPreferences pref = context.getSharedPreferences(UrlApp.PREF_FILE_NAME, Context.MODE_PRIVATE);
        return pref.getLong(key, defaultValue);
    }

    public static int getInt(Context context, String key, int defaultValue) {
        SharedPreferences pref = context.getSharedPreferences(UrlApp.PREF_FILE_NAME, Context.MODE_PRIVATE);
        return pref.getInt(key, defaultValue);
    }
}

package com.youlubei.youlubei.utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;

/**
 * @function sharedPreference 工具类
 */
public final class SharedPreferenceUtil {

    private static final String FILE_NAME = "sp"; //文件名
    private static SharedPreferenceUtil mInstance;

    private SharedPreferenceUtil(){}

    public static SharedPreferenceUtil getInstance(){
        if(mInstance == null){
            synchronized (SharedPreferenceUtil.class){
                if(mInstance == null){
                    mInstance = new SharedPreferenceUtil();
                }
            }
        }
        return mInstance;
    }

    /**
     * 存入键值对
     * @param context
     * @param key
     * @param value
     */

    public void put(Context context, String key, Object value){
        //判断类型
        String type = value.getClass().getSimpleName();
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        switch (type) {
            case "Integer":
                editor.putInt(key, (Integer) value);
                break;
            case "Boolean":
                editor.putBoolean(key, (Boolean) value);
                break;
            case "Float":
                editor.putFloat(key, (Float) value);
                break;
            case "Long":
                editor.putLong(key, (Long) value);
                break;
            case "String":
                editor.putString(key, (String) value);
                break;
        }
        editor.apply();
    }

    /**
     * 读取键的值，若无则返回默认值
     * @param context
     * @param key
     * @param defValue 默认值
     * @return
     */

    public Object get(Context context, String key, Object defValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        String type = defValue.getClass().getSimpleName();
        switch (type) {
            case "Integer":
                return sharedPreferences.getInt(key, (Integer) defValue);
            case "Boolean":
                return sharedPreferences.getBoolean(key, (Boolean) defValue);
            case "Float":
                return sharedPreferences.getFloat(key, (Float) defValue);
            case "Long":
                return sharedPreferences.getLong(key, (Long) defValue);
            case "String":
                return sharedPreferences.getString(key, (String) defValue);
        }
        return defValue;
    }
}

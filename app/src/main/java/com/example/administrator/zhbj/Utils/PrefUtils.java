package com.example.administrator.zhbj.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2017/3/1.
 */

public class PrefUtils {
    public static boolean getBoolean(Context context,String key,boolean defaule){
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        return  sp.getBoolean(key,defaule);
    }
    public static void setBoolean(Context context,String key,boolean value){
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();
    }
    public static String getString(Context context,String key,String defaule){
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        return  sp.getString(key,defaule);
    }
    public static void setString(Context context,String key,String value){
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
    }

    public static int getInt(Context context,String key,int defaule){
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        return  sp.getInt(key,defaule);
    }
    public static void setString(Context context,String key,int value){
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        sp.edit().putInt(key, value).commit();
    }
}

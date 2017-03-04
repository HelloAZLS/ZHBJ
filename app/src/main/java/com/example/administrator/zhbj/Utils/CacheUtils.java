package com.example.administrator.zhbj.Utils;

import android.content.Context;

/**
 * Created by Administrator on 2017/3/4.
 */

public class CacheUtils {
    public static void setCache(String url, Context context, String result) {
        PrefUtils.setString(context, url, result);
    }
    public static String getCache(String url,Context context){
        return  PrefUtils.getString(context,url,null);
    }
}

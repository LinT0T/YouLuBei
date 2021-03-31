package com.youlubei.youlubei.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;

import java.util.Calendar;
import java.util.TimeZone;

public class Utils {


    public static int dp2px(Context context, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels - 100;
    }

    /**
     * 获取时间差
     */
    public static Long getSecondsNextEarlyMorning(int num, int min) {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        cal.setTimeInMillis(System.currentTimeMillis());
//        if (cal.get(Calendar.HOUR_OF_DAY) - num >= 0) {
//            //如果当前时间大于等于8点 就计算第二天的8点的
//            cal.add(Calendar.DAY_OF_YEAR, 1);
//        } else {
//            cal.add(Calendar.DAY_OF_YEAR, 0);
//        }
        //设置在几点提醒 设置的为13点

        cal.set(Calendar.HOUR_OF_DAY, num);

        //设置在几分提醒 设置的为25分

        cal.set(Calendar.MINUTE, min);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTimeInMillis() - System.currentTimeMillis();
    }


}
package com.youlubei.youlubei.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.gyf.immersionbar.ImmersionBar;
import com.youlubei.youlubei.R;

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

    public static void initBar(Activity activity) {
        ImmersionBar.with(activity).init();
        ImmersionBar.with(activity)
                .transparentStatusBar()
                .transparentNavigationBar()
                .fullScreen(true)
                .statusBarDarkFont(true)
                .navigationBarDarkIcon(true)
                .statusBarColor("#FBFBFB")
                .navigationBarColor("#FBFBFB");
    }

    public static void showToast(Context context, String text) {
        Toast toast = new Toast(context);

        //创建一个填充物,用于填充Toast
        LayoutInflater inflater = LayoutInflater.from(context);

        //填充物来自的xml文件,在这个改成一个view
        //实现xml到view的转变哦
        View view = inflater.inflate(R.layout.toast, null);

        //不一定需要，找到xml里面的组件，设置组件里面的具体内容
        TextView textView1 = view.findViewById(R.id.tv_toast);
        textView1.setText(text);
        //把填充物放进toast
        toast.setView(view);
        toast.setDuration(Toast.LENGTH_SHORT);

        //展示toast
        toast.show();
    }

}
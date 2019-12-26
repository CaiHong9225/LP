package com.ccc.locationprovider.utils;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;

/**
 * Created by Administrator on 2018/3/13.
 */

public class YstenDensityUtils {

    private YstenDensityUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");

    }

    /**
     * dp转px
     *
     * @param context
     * @param dpVal
     * @return
     */
    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());

    }

    /**
     * sp转px
     *
     * @param context
     * @param spVal
     * @return
     */
    public static int sp2px(Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());

    }

    /**
     * px转dp
     *
     * @param context
     * @param pxVal
     * @return
     */
    public static float px2dp(Context context, float pxVal) {

        final float scale = context.getResources().getDisplayMetrics().density;

        return (pxVal / scale);

    }

    /**
     * px转sp
     *
     * @param context
     * @param pxVal
     * @return
     */
    public static float px2sp(Context context, float pxVal) {
        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
    }


    /**
     * 屏幕尺寸大于7英寸即为pad屏幕
     *
     * @param context
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static boolean isPadScreen(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (wm != null) {
            Display display = wm.getDefaultDisplay();
            DisplayMetrics dm = new DisplayMetrics();
            display.getRealMetrics(dm);
            double x = Math.pow(dm.widthPixels / dm.xdpi, 2);
            double y = Math.pow(dm.heightPixels / dm.ydpi, 2);
            // 屏幕尺寸
            double screenInches = Math.sqrt(x + y);
            // 大于7尺寸则为Pad
            if (screenInches >= 7.0) {
                return true;
            }
        }
        return false;
    }

    public static int getWidthPixels() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getHeightPixels() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }
}

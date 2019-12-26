package com.ccc.locationprovider.utils;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ccc.locationprovider.R;


/**
 * Created by liuhq on 2016/12/21.
 * 对话框显示样式Toast
 */

public class ToastUtil {
    private static Toast toast = null;
    private static final String TAG = ToastUtil.class.getSimpleName();


    public static void showMessage(final Context context, final String msg) {
        showMessage(context, msg, Toast.LENGTH_SHORT);
    }

    public static void showMessage(final Context context, final String msg, final int len) {
        showMessage(context, msg, len, -1);
    }

    public static void showMessage(final Context context, final String msg, final int len, final
    int gravity) {
        Toast toast = Toast.makeText(context.getApplicationContext(), msg, len);
        if (gravity > -1) {
            toast.setGravity(gravity, 0, 0);
        }
        toast.show();
    }


}
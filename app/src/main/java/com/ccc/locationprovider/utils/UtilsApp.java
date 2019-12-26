package com.ccc.locationprovider.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.ccc.locationprovider.view.GpsActivity;
import com.tencent.smtt.sdk.QbSdk;


public class UtilsApp {
    private static final String TAG ="UtilsApp";
    @SuppressLint("StaticFieldLeak")
    private static Context sApplication;


    /**
     * debug标志
     */
    public static boolean isDebug = true;


    public static GpsActivity getCurrentActivity() {
        return currentActivity;
    }

    @SuppressLint("StaticFieldLeak")
    private static GpsActivity currentActivity;


    /**
     * 初始化工具类
     *
     * @param app 应用
     */
    public static void init(@NonNull final Context app) {
        UtilsApp.sApplication = app;
        //初始化x5内核
        QbSdk.initX5Environment(app, new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
                Log.d(TAG,"x5 onCoreInitFinished");
            }

            @Override
            public void onViewInitFinished(boolean b) {
                Log.d(TAG,"x5 onViewInitFinished:"+b);
            }
        });
    }

    /**
     * 获取 Application
     *
     * @return Application
     */
    public static Context getApp() {
        if (sApplication != null) return sApplication;
        throw new NullPointerException("u should init first");
    }


    public static void setCurrentActivity(GpsActivity activity) {
        UtilsApp.currentActivity = activity;
    }
}

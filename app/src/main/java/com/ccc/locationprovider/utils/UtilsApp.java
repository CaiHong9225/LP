package com.ccc.locationprovider.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

import com.ccc.locationprovider.view.GpsActivity;


public class UtilsApp {
    @SuppressLint("StaticFieldLeak")
    private static Context sApplication;


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

package com.ccc.locationprovider;

import android.app.Application;

import com.ccc.locationprovider.utils.UtilsApp;


public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        UtilsApp.init(this);

    }
}

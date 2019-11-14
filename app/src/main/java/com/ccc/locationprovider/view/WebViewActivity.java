package com.ccc.locationprovider.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.ccc.locationprovider.R;
import com.ccc.locationprovider.base.BaseActivity;
import com.ccc.locationprovider.utils.CallUtils;
import com.ccc.locationprovider.utils.LocationUtils;
import com.ccc.locationprovider.utils.LogUtils;
import com.ccc.locationprovider.utils.UtilsApp;
import com.ccc.locationprovider.widget.X5WebView;


public class WebViewActivity extends BaseActivity {
    private String TAG = "WebViewActivity";
    private X5WebView mActivityWebview;

    private String url = "http://www.baidu.com";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        initView();
//        getData();
        loadUrl();
    }

    private void initView() {
        mActivityWebview = (X5WebView) findViewById(R.id.activity_webview);
    }

    private void loadUrl() {
        if (!TextUtils.isEmpty(url)) {
            mActivityWebview.loadUrl(url);
        }

    }

    private void getData() {
        String url = getIntent().getStringExtra("url");
        LogUtils.d(TAG, "url:" + url);
    }


}

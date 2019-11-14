package com.ccc.locationprovider.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ccc.locationprovider.R;
import com.ccc.locationprovider.base.BaseActivity;
import com.ccc.locationprovider.utils.CallUtils;
import com.ccc.locationprovider.utils.GPSUtils;
import com.ccc.locationprovider.utils.LocationUtil;
import com.ccc.locationprovider.utils.LocationUtils;
import com.ccc.locationprovider.utils.UtilsApp;
import com.ccc.locationprovider.widget.X5WebView;

public class GpsActivity extends BaseActivity {
    private String TAG = "GpsActivity";
    private X5WebView webView;

    private String url = "http://www.163.com";
    private TextView textView;
    //js调用示例
//    function dealAndroid(type, params) {
//        var browser = {
//                versions: function () {
//            var u = navigator.userAgent,
//                    app = navigator.appVersion;
//            return { //移动终端浏览器版本信息
//                    trident: u.indexOf('Trident') > -1, //IE内核
//                    presto: u.indexOf('Presto') > -1, //opera内核
//                    webKit: u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核
//                    gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //火狐内核
//                    mobile: !!u.match(/AppleWebKit.*Mobile.*/) || !!u.match(/AppleWebKit/), //是否为移动终端
//            ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
//            android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或者uc浏览器
//                    iPhone: u.indexOf('iPhone') > -1 || u.indexOf('Mac') > -1, //是否为iPhone或者QQHD浏览器
//                    iPad: u.indexOf('iPad') > -1, //是否iPad
//                    webApp: u.indexOf('Safari') == -1 //是否web应该程序，没有头部与底部
//            };
//        }(),
//                language: (navigator.browserLanguage || navigator.language).toLowerCase()
//    };
//        if (browser.versions.android) {
//            if (window.APP_NATIVE && window.APP_NATIVE.getLatitudeAndLongitude) {
//                params = JSON.stringify(params);
//                window.APP_NATIVE.getLatitudeAndLongitude()
//            }
//            return;
//        }
//        if(browser.versions.ios){
//            if (window.webkit && window.webkit.messageHandlers && window.webkit.messageHandlers.getLatitudeAndLongitude) {
//                //vue调用iOS方法
//                params = JSON.stringify(params);
//                window.webkit.messageHandlers.getLatitudeAndLongitude.postMessage()
//            }
//        }
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);
        UtilsApp.setCurrentActivity(this);
        initView();
        loadUrl();
    }
    private void loadUrl() {
        if (!TextUtils.isEmpty(url)) {
            webView.loadUrl(url);
        }

    }
    private void initView() {
        webView = (X5WebView) findViewById(R.id.activity_webview);
        textView = findViewById(R.id.call);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double[] doubles = requestGpsPermission();
                Log.d(TAG,"ladtidute:"+doubles[0]+";long:"+doubles[1]);
//                CallUtils.callPhone("13222222222");
            }
        });
    }

    double[] lat = new double[2];

    /**
     *
     * 请求定位
     * @return
     */
    public double[] requestGpsPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            //如果用户并没有同意该权限
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //申请权限
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
            } else {
                Location location = GPSUtils.getInstance(GpsActivity.this).getLocation();
                Log.d(TAG,"ladtidute11:"+location.getLatitude()+";long:"+location.getLongitude());
                if(location!=null){
                    lat[0]=location.getLatitude();
                    lat[1]=location.getLongitude();
                }
//                lat = LocationUtil.getCurrentLocation(GpsActivity.this, callBack);
            }
        }
        return lat;
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private LocationUtil.LocationCallBack callBack = new LocationUtil.LocationCallBack() {
        @Override
        public void onSuccess(Location location) {
            textView.append("经度: " + location.getLongitude() + " 纬度: " + location.getLatitude() + "\n");
        }

        @Override
        public void onFail(String msg) {
            textView.append(msg + "\n");
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                lat = LocationUtil.getCurrentLocation(GpsActivity.this, callBack);
                Location location = GPSUtils.getInstance(GpsActivity.this).getLocation();
                if(location!=null){
                    lat[0]=location.getLatitude();
                    lat[1]=location.getLongitude();
                }
                Log.d(TAG,"ladtidute12:"+location.getLatitude()+";long:"+location.getLongitude());
            } else {
                textView.append("权限没获取！！！" + "\n");
            }
        }
    }
}

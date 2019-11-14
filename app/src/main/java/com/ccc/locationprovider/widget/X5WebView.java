package com.ccc.locationprovider.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.AbsoluteLayout;
import android.widget.ProgressBar;

import com.ccc.locationprovider.R;
import com.ccc.locationprovider.utils.CallUtils;
import com.ccc.locationprovider.utils.LocationUtils;
import com.ccc.locationprovider.utils.UtilsApp;
import com.google.gson.Gson;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebSettings.LayoutAlgorithm;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class X5WebView extends WebView {
    private static final String TAG = X5WebView.class.getSimpleName();
    public ProgressBar progressbar;
    private Context mContext;
    private boolean isShowProgressBar = true;
//    WebViewClient mWebviewclient = new WebViewClient() {
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            return super.shouldOverrideUrlLoading(view, url);
//        }
//
//        @Override
//        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//            handler.proceed();
//        }
//
//        @Override
//        public void onReceivedError(WebView view, int errorCode,
//                                    String description, String failingUrl) {
//            // TODO Auto-generated method stub
//            super.onReceivedError(view, errorCode, description, failingUrl);
//        }
//    };
    private WebViewClient client = new WebViewClient() {
        /**
         *
         * 防止加载网页时调起系统浏览器
         */
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (!TextUtils.isEmpty(url) && (url.startsWith("http:") || url.startsWith("https:"))) {
                view.loadUrl(url);
            } else {
                try {
                    final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    mContext.startActivity(intent);
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return false;
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
            super.onReceivedSslError(view, handler, error);
        }
    };

    private WebChromeClient chromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (isShowProgressBar) {
                showProgress(newProgress);
            }
        }

    };

    /**
     * 显示网页加载进度条
     *
     * @param newProgress
     */
    public void showProgress(int newProgress) {
        if (newProgress == 100) {
            progressbar.setVisibility(View.GONE);
//            this.setVisibility(View.VISIBLE);
            this.setBackgroundColor(getResources().getColor(R.color.color_160D36));
        } else {
            if (progressbar.getVisibility() == View.GONE) {
                progressbar.setVisibility(View.VISIBLE);
            }
            progressbar.setProgress(newProgress);
//            this.setVisibility(View.GONE);
        }
    }

    /**
     * 初始化webview
     *
     * @param context
     */
    private void init(Context context) {
        this.mContext = context;
        progressbar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
        progressbar.setLayoutParams(new AbsoluteLayout.LayoutParams(AbsoluteLayout.LayoutParams.FILL_PARENT, 5, 0, 0));
        progressbar.setProgressDrawable(this.getResources().getDrawable(R.drawable.progress_bar));
        progressbar.setMax(100);
        this.setBackgroundColor(context.getResources().getColor(R.color.color_160D36));
        this.addView(progressbar);

        this.setWebViewClient(client);//拦截
        this.setWebChromeClient(chromeClient);

        //初始化通用设置
        initWebViewSettings();
        this.getView().setClickable(true);
        //初始化jsFunc
        initJsFunc();
        /* bugly report */
        this.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String downloadUrl, String userAgent, String contentDisposition, String mimeType, long contentLength) {
                Log.i(TAG, "onDownloadStart--》" + downloadUrl);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(downloadUrl));
                mContext.startActivity(intent);
            }
        });
    }

    /**
     * 是否展示上部进度条
     *
     * @param isShow
     */
    public void setIsShowProgressBar(boolean isShow) {
        this.isShowProgressBar = isShow;
    }

    /**
     * 这只h5调用的方法
     */
    private void initJsFunc() {

        this.addJavascriptInterface(new WebViewJavaScriptFunction() {
            @JavascriptInterface
            @Override
            public void openPage(String type, String params) {
                Log.i(TAG, "openPage() type = " + type + ",params = " + params);
            }



            @Override
            public String getLatitudeAndLongitude() {
                double[] doubles1 = new double[2];
                if (UtilsApp.getCurrentActivity() != null) {
                    doubles1 = UtilsApp.getCurrentActivity().requestGpsPermission();
                }
                String s = doubles1[0] + ";" + doubles1[1];
                return s;
            }

            @Override
            public void call(String number) {
                CallUtils.callPhone(number);
            }


        }, "APP_NATIVE");
    }

    @SuppressLint("SetJavaScriptEnabled")
    public X5WebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    @Override
    public void destroy() {
        super.destroy();
        progressbar = null;
    }

    /**
     * webview配置
     */
    private void initWebViewSettings() {
        WebSettings webSetting = this.getSettings();
        webSetting.setDefaultTextEncodingName("UTF-8");
        webSetting.setLoadWithOverviewMode(true);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setAllowFileAccess(true);
        webSetting.setAllowFileAccessFromFileURLs(false);
        webSetting.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(false);
        webSetting.setAppCacheEnabled(true);
        // webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        // webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSetting.setDomStorageEnabled(true);
        //this.getSettingsExtension().setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);//extension
        // settings 的设计

        //允许加载http和https混合的页面（5.0一下默认允许）
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSetting.setMixedContentMode(android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            webSetting.setAllowUniversalAccessFromFileURLs(true);
        } else {
            try {
                Class<?> clazz = webSetting.getClass();
                Method method = clazz.getMethod("setAllowUniversalAccessFromFileURLs", boolean.class);
                if (method != null) {
                    method.invoke(webSetting, true);
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

    }

    public X5WebView(Context context) {
        super(context);
        init(context);
    }


}

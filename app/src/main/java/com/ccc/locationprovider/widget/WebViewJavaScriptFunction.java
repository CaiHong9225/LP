package com.ccc.locationprovider.widget;

public interface WebViewJavaScriptFunction {


    /**
     * 大开native界面
     * @param type
     * @param params
     */
    void openPage(String type, String params);

    /**
     * 获取经纬度
     * @return
     */
    String getLatitudeAndLongitude();

    /**
     * 拨出电话
     * @param number
     */
    void call(String number);

}

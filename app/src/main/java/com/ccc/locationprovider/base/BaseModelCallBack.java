package com.ccc.locationprovider.base;

/**
 * 作者：Picasso on 2016/9/1 10:46
 * 详情：model层逻辑结束回调
 */
public interface BaseModelCallBack<T> {
    void onResponse(T response);

    void onFailure(String error);
}

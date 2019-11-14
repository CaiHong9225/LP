package com.ccc.locationprovider.utils;

import android.content.Intent;
import android.net.Uri;

/**
 * @ProjectName: LocationProvider
 * @Package: com.ccc.locationprovider.utils
 * @ClassName: CallUtils
 * @Description: java类作用描述
 * @Author: admin
 * @CreateDate: 2019/11/12 14:38
 * @UpdateUser: admin
 * @UpdateDate: 2019/11/12 14:38
 * @UpdateRemark:
 * @Version: 1.0
 */
public class CallUtils {
    public static void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        UtilsApp.getApp().startActivity(intent);
    }
}

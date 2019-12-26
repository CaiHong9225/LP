package com.ccc.locationprovider.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.EditText;

import com.ccc.locationprovider.R;
import com.ccc.locationprovider.widget.X5WebView;

/**
 * @ProjectName: LocationProvider
 * @Package: com.ccc.locationprovider.utils
 * @ClassName: LpDebugUtils
 * @Description: java类作用描述
 * @Author: admin
 * @CreateDate: 2019/12/26 11:51
 * @UpdateUser: admin
 * @UpdateDate: 2019/12/26 11:51
 * @UpdateRemark:
 * @Version: 1.0
 */
public class LpDebugUtils {
    private static final LpDebugUtils ourInstance = new LpDebugUtils();

    public static LpDebugUtils getInstance() {
        return ourInstance;
    }

    private LpDebugUtils() {
    }

    /**
     * 弹窗进入调试模式
     */
    public void showInputDialog(final Context context, final X5WebView webView) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_edit_alert_dialog, null);
        final EditText mEditUrl = (EditText) view.findViewById(R.id.et_input_url);
        View mBtCancel = view.findViewById(R.id.bt_cancel);
        View mBtDefine = view.findViewById(R.id.bt_define);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        mBtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                YstenUtils.hideKeyBoard(context, mEditUrl);
            }
        });
        mBtDefine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                YstenUtils.hideKeyBoard(context, mEditUrl);
                String htmlUrl = mEditUrl.getText().toString();
                if (!TextUtils.isEmpty(htmlUrl) && !(htmlUrl.startsWith("http://") || htmlUrl.startsWith("https://"))) {
                    ToastUtil.showMessage(context, "url格式不正确");
                    return;
                }
                webView.loadUrl(htmlUrl);
            }
        });
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        alertDialog.show();
        Window dialogWindow = alertDialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.height = YstenDensityUtils.dp2px(context, 300);
        dialogWindow.setAttributes(lp);
    }
}

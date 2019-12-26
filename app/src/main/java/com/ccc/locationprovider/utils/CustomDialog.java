package com.ccc.locationprovider.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ccc.locationprovider.R;

/**
 * @author lixx
 * @brief 自定义dialog
 * @createTime 2016/12/9
 */

public class CustomDialog extends Dialog {

    public CustomDialog(Context context) {
        super(context);
    }

    public CustomDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected CustomDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public static class Builder {
        private Context context;
        private String title = "";
        private boolean showTitle = false;
        private String content = "";
        private int contentImgId = 0;
        private String contentImgpath = "";
        private Bitmap contentImgbitmap;
        private boolean showImage = false;
        private boolean showContent = true;

        private DialogInterface.OnClickListener leftClickListener, rightClickListener;
        private boolean showLeftBtn, showRightBtn;
        private String leftBtnString, rightBtnString;
        private float mScale = 0.75f;
        private int leftColor=-1,rightColor = -1;

        private int orientation = 1;

        //        public static final int ORIENTATION_LANDSCAPE = 2;
//        public static final int ORIENTATION_PORTRAIT = 1;
        public Builder(Context context) {
            this.context = context;
        }

        public Builder(Context context, float scale) {
            this.context = context;
            mScale = scale;
        }

        public Builder setOrientation(int orientation) {
            this.orientation = orientation;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setTitle(int titleId) {
            this.title = context.getString(titleId);
            return this;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public Builder setContent(int contentId) {
            if (context != null) {
                this.content = context.getString(contentId);
            } else {
                this.content = UtilsApp.getApp().getString(contentId);
            }
            return this;
        }

        public Builder setImageContent(int contentId) {
            showImage = true;
            showContent = false;
            this.contentImgId = contentId;
            return this;
        }

        public Builder setImageContent(String contentpath) {
            showImage = true;
            showContent = true;
            this.contentImgpath = contentpath;
            return this;
        }

        public Builder setImageContent(Bitmap contentbitmap) {
            showImage = true;
            showContent = true;
            this.contentImgbitmap = contentbitmap;
            return this;
        }

        public Builder setLeftBtnVisible(boolean show) {
            this.showLeftBtn = show;
            return this;
        }

        public Builder setLeftBtn(boolean show, int resId, DialogInterface.OnClickListener
                listener) {
            this.showLeftBtn = show;
            this.leftBtnString = context.getString(resId);
            this.leftClickListener = listener;
            return this;
        }

        public Builder setLeftBtn(boolean show, int resId, int leftColor , DialogInterface.OnClickListener listener) {
            this.showLeftBtn = show;
            this.leftColor = context.getResources().getColor(leftColor);
            this.leftBtnString = context.getString(resId);
            this.leftClickListener = listener;
            return this;
        }
        public Builder setRightBtn(boolean show, int resId, DialogInterface.OnClickListener
                listener) {
            this.showRightBtn = show;
            this.rightBtnString = context.getString(resId);
            this.rightClickListener = listener;
            return this;
        }
        public Builder setRightBtn(boolean show, int resId,int rightColor, DialogInterface.OnClickListener
                listener) {
            this.showRightBtn = show;
            this.rightColor = context.getResources().getColor(rightColor);
            this.rightBtnString = context.getString(resId);
            this.rightClickListener = listener;
            return this;
        }
        public Builder setTitleVisibility(boolean showTitle) {
            this.showTitle = showTitle;
            return this;
        }

        public CustomDialog create() {
            final CustomDialog dialog = new CustomDialog(context, R.style.dialog_theme);
            View view = LayoutInflater.from(context).inflate(R.layout.layout_dialog, null);
            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            LinearLayout.LayoutParams layoutParams;
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                layoutParams = new LinearLayout.LayoutParams((int) (windowManager.getDefaultDisplay().getWidth() * mScale), LinearLayout.LayoutParams
                        .WRAP_CONTENT);
            } else {
                layoutParams = new LinearLayout.LayoutParams((int) (windowManager.getDefaultDisplay().getHeight() * mScale), LinearLayout.LayoutParams
                        .WRAP_CONTENT);
            }


            if (showTitle) {
                ((TextView) view.findViewById(R.id.dialog_title)).setVisibility(View.VISIBLE);
                ((TextView) view.findViewById(R.id.dialog_title)).setText(Html.fromHtml(title));
            } else {
                ((TextView) view.findViewById(R.id.dialog_title)).setVisibility(View.GONE);
            }

            ((TextView) view.findViewById(R.id.dialog_content)).setText(content);
            //判断是否显示图片
            if (showImage) {
                ImageView imageView = (ImageView) view.findViewById(R.id.dialog_img_content);
                imageView.setVisibility(View.VISIBLE);
                //显示dialog_content的情况
                if (showContent) {
                    view.findViewById(R.id.dialog_content).setVisibility(View.VISIBLE);
                    Log.i("mmsg", "contentImgpath:" + contentImgpath);
                    //图片通过url path显示
                    if (!TextUtils.isEmpty(contentImgpath)) {
                        ImageLoader.getInstance().showImage(context, contentImgpath,
                                R.drawable.img_share_default, imageView);
                    }
                    //图片通过bitmap显示
                    else {
                        imageView.setImageBitmap(contentImgbitmap);
                    }
                }
                //不显示dialog_content
                else {
                    view.findViewById(R.id.dialog_content).setVisibility(View.GONE);
                    imageView.setImageResource(contentImgId);
                }
            }

            ((Button) view.findViewById(R.id.dialog_btn_left)).setText(leftBtnString);
            if(leftColor!=-1){
                ((Button) view.findViewById(R.id.dialog_btn_left)).setTextColor(leftColor);
            }
            ((Button) view.findViewById(R.id.dialog_btn_left)).setOnClickListener
                    (new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                            leftClickListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
                        }
                    });
            if (showLeftBtn) {
                ((Button) view.findViewById(R.id.dialog_btn_left)).setVisibility(View.VISIBLE);
            } else {
                ((Button) view.findViewById(R.id.dialog_btn_left)).setVisibility(View.GONE);
            }

            ((Button) view.findViewById(R.id.dialog_btn_right)).setText(rightBtnString);
            if(rightColor!=-1){
                ((Button) view.findViewById(R.id.dialog_btn_right)).setTextColor(rightColor);
            }
            ((Button) view.findViewById(R.id.dialog_btn_right)).setOnClickListener
                    (new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                            rightClickListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                        }
                    });
            if (showRightBtn) {
                ((Button) view.findViewById(R.id.dialog_btn_right)).setVisibility(View.VISIBLE);
            } else {
                ((Button) view.findViewById(R.id.dialog_btn_right)).setVisibility(View.GONE);
            }

            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.getWindow().setContentView(view, layoutParams);

            return dialog;
        }
    }
}

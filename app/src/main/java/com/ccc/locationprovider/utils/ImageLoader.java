package com.ccc.locationprovider.utils;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;


import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.ccc.locationprovider.R;

import java.util.concurrent.ExecutionException;

import jp.wasabeef.glide.transformations.BlurTransformation;


/**
 * 作者：Picasso on 2016/8/31 16:23
 * 详情：
 */
public class ImageLoader {

    private static ImageLoader imageLoader = null;

    public static ImageLoader getInstance() {
        if (imageLoader == null) {
            imageLoader = new ImageLoader();
        }
        return imageLoader;
    }


    public Bitmap downloadImage(Context context, String url, int place_holder) throws ExecutionException, InterruptedException {
        RequestOptions options = new RequestOptions();
        options.placeholder(R.mipmap.icon_head);

        return Glide.with(context)
                .asBitmap()
                .load(url)
                .apply(options)
                .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();

    }

    //默认占位图place_holder_vertical
    public void showImage(Context context, String url, ImageView imageView) {
        showImage(context, url, R.drawable.place_holder_vertical, imageView);
    }

    public void showImage(Fragment fragment, String url, ImageView imageView) {
        showImage(fragment, url, R.drawable.place_holder_vertical, imageView);
    }

    //头像
    public void showHeadImage(Context context, String url, ImageView imageView) {
        showImageCircle(context, url, R.mipmap.icon_head, imageView);
    }

    //头像 fragment
    public void showHeadImage(final Fragment context, String url, final ImageView imageView) {
        RequestOptions options = new RequestOptions();
        options.placeholder(R.mipmap.icon_head).error(R.mipmap.icon_head).circleCrop();
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView);
    }

    public void glideShowImage(final Context context, String type, String url, ImageView
            imageView, int place_holder) {
        showImage(context, url, place_holder, imageView);
    }

    /**
     * 加载图片为空的情况下不显示缩略图
     */
    public void showImageColor(Context context, String url, ImageView imageView) {
        showImage(context, url, R.color.white, imageView);
    }

    public void showImage(Fragment fragment, String url, int placeholderResId, ImageView imageView) {
        if (!TextUtils.isEmpty(url)) {
            RequestOptions options = new RequestOptions();
            options.placeholder(placeholderResId);
            options.error(placeholderResId);
            Glide.with(fragment)
                    .load(url)
                    .apply(options)
                    .into(imageView);

        } else {
            imageView.setImageResource(placeholderResId);
        }
    }


    //网络图片加载 placeholder
    public void showImage(Context context, String url, int placeholderResId, ImageView imageView) {
        if (!TextUtils.isEmpty(url)) {
            RequestOptions options = new RequestOptions();
            options.dontAnimate();
            options.placeholder(placeholderResId);
            options.error(placeholderResId);
            Glide.with(context)
                    .load(url)
                    .apply(options)
                    .into(imageView);

        } else {
            imageView.setImageResource(placeholderResId);
        }
    }

    public void showImagePriority(Context context, String url, int placeholderResId, ImageView imageView, Priority priority) {
        if (!TextUtils.isEmpty(url)) {
            RequestOptions options = new RequestOptions();
            options.placeholder(placeholderResId);
            options.error(placeholderResId);
            options.priority(priority);
            Glide.with(context)
                    .load(url)
                    .apply(options)
                    .thumbnail(0.1f)
                    .into(imageView);

        } else {
            imageView.setImageResource(placeholderResId);
        }
    }



    public void showImageCircle(final Context context, String url, int placeholderResId, final
    ImageView imageView) {
        RequestOptions options = new RequestOptions();
        options.placeholder(placeholderResId);
        options.error(placeholderResId);
        options.centerCrop();
        Glide.with(context)
                .asBitmap()
                .load(url)
                .apply(options)
                .into(imageView);

    }




    /**
     * 设置圆角图place_holder_vertical
     *
     * @param context
     * @param url
     * @param imageView
     * @param round
     */
    public void showRoundImage(Context context, String url, ImageView imageView, int round) {
        if (!TextUtils.isEmpty(url)) {
            RequestOptions options = new RequestOptions();
            options.placeholder(R.drawable.place_holder_vertical);
            options.error(R.drawable.place_holder_vertical);
            options.centerCrop();
            Glide.with(context)
                    .load(url)
                    .apply(options)
                    .into(imageView);
        } else {
            imageView.setImageResource(R.drawable.place_holder_vertical);
        }
    }

    //默认占位图place_holder_horizontal
    public void showImageHorzation(Context context, String url, ImageView imageView) {
        showImage(context, url, R.drawable.place_holder_horizontal, imageView);
    }

    public void showImageByHighPriority(Context context, String url, Drawable placeholderResId,
                                        ImageView imageView) {
        if (!TextUtils.isEmpty(url)) {
            RequestOptions options = new RequestOptions();
            options.placeholder(placeholderResId);
            options.error(placeholderResId);
            options.priority(Priority.HIGH);
            Glide.with(context)
                    .load(url)
                    .apply(options)
                    .into(imageView);

        } else {
            imageView.setImageDrawable(placeholderResId);
        }
    }

    public void showImageNoPlaceHolder(Context context, String url, ImageView imageView) {

        if (!TextUtils.isEmpty(url)) {
            Glide.with(context)
                    .load(url)
                    .thumbnail(0.1f)
                    .into(imageView);

        } else {
            imageView.setVisibility(View.GONE);
        }
    }

    /**
     * 展示Gif图
     *
     * @param context
     * @param url
     */
    public void showGifImage(Context context, String type, String url, ImageView imageView, int placeholderResId) {
        if (!TextUtils.isEmpty(url)) {
            if (url.endsWith("gif") || url.endsWith("GIF")) {
                RequestOptions options = new RequestOptions();
                options.placeholder(placeholderResId)
                        .error(placeholderResId)
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
                Glide.with(context)
                        .asGif()
                        .load(url)
                        .apply(options)
                        .into(imageView);
            } else {
                Glide.with(context)
                        .load(url)
                        .into(imageView);
            }
        } else {
            imageView.setImageResource(placeholderResId);
        }
    }

    /**
     * 展示Gif图
     *
     * @param context
     * @param url
     */
    public void showGifImageThumbnail(Context context, String url, ImageView imageView) {
        if (!TextUtils.isEmpty(url)) {
            if (url.endsWith("gif") || url.endsWith("GIF")) {
                Glide.with(context)
                        .asGif()
                        .load(url)
                        .thumbnail(0.1f)
                        .into(imageView);
            } else {
                Glide.with(context)
                        .load(url)
                        .thumbnail(0.1f)
                        .into(imageView);
            }
        }
    }

    /**
     * 展示在LinearLayout 布局背景
     * @param context 上下文
     * @param url 图片地址
     * @param imageView target
     */
    public void showImageInTarget(Context context, String url, final View imageView) {
        Glide.with(context)
                .asBitmap()
                .load(url)
                .into(new SimpleTarget<Bitmap>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        Drawable drawable = new BitmapDrawable(resource);
                        imageView.setBackground(drawable);    //设置背景
                    }
                });
    }


    /**
     * 高斯模糊
     *
     * @param context
     * @param url
     */
    public void showBiurFormation(Context context, String url, ImageView imageView) {
        if (!TextUtils.isEmpty(url)) {
            Glide.with(context)
                    .load(url)
                    .transition(new DrawableTransitionOptions().crossFade(1000))
                    .apply(RequestOptions.bitmapTransform(new BlurTransformation(22, 6)).dontAnimate().error(R.drawable.place_holder_horizontal))
                    .into(imageView);
        }
    }
}

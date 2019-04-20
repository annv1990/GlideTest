package com.ajs.glidetest;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomViewTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.signature.ObjectKey;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    static String TEST_KEY = "test";

    RelativeLayout clMain;
    Button btnLoad;
    Key key;
    Handler mHandler;
    Runnable mRunnable;

    String[] mListImage = new String[]{
            "https://i.ibb.co/HqJtF17/test2.jpg",
            "https://i.ibb.co/dbCDP3D/test1.png"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        clMain = findViewById(R.id.clMain);
        boolean isDownloadOK = PrefManager.getBoolean(this, "DOWNLOAD_OK", false);

        btnLoad = findViewById(R.id.btnLoad);
//        String sign = PrefManager.getString(this, TEST_KEY);
//        if (sign.isEmpty()) {
//            key = getNewKeySign();
//        } else {
//            key = new ObjectKey(sign);
//        }
//        key = new ObjectKey(String.valueOf(new Random().nextInt()));
//        getImage(key, MainActivity.this, mListImage[0]);
        int currentVersion = PrefManager.getInt(this, "VERSION", -1);
        mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                goToOne();
            }
        };
        int version = Math.abs(new Random().nextInt(2));
        mHandler.postDelayed(mRunnable, 5000);
        if (currentVersion < 0) {
            // init
//            clMain.setBackground(getDrawable(R.drawable.bg_splash));
            Glide.with(this)
                    .load(getDrawable(R.drawable.bg_splash)).into(new CustomViewTarget<RelativeLayout, Drawable>(clMain) {
                @Override
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                    clMain.setBackground(resource);
                }

                @Override
                public void onLoadFailed(@Nullable Drawable errorDrawable) {

                }

                @Override
                protected void onResourceCleared(@Nullable Drawable placeholder) {

                }
            });
            runService(mListImage[0], 0);
        } else if (currentVersion != version) {
//            clMain.setBackground(loadImage());
            Glide.with(this).load(loadPathImage())
                    .into(new CustomViewTarget<RelativeLayout, Drawable>(clMain) {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            clMain.setBackground(resource);
                        }

                        @Override
                        public void onLoadFailed(@Nullable Drawable errorDrawable) {

                        }

                        @Override
                        protected void onResourceCleared(@Nullable Drawable placeholder) {

                        }
                    });

            runService(mListImage[version], version);
        } else {
            Glide.with(this).load(loadPathImage())
                    .into(new CustomViewTarget<RelativeLayout, Drawable>(clMain) {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            clMain.setBackground(resource);
                        }

                        @Override
                        public void onLoadFailed(@Nullable Drawable errorDrawable) {

                        }

                        @Override
                        protected void onResourceCleared(@Nullable Drawable placeholder) {

                        }
                    });
//            clMain.setBackground(loadImage());
        }
//        getImage();
    }

    /*private void getImage(Key key, Context context, String url) {
        //Create option for get image of Coupon
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        requestOptions.signature(key);

        Glide.with(context)
//                .setDefaultRequestOptions(requestOptions)
                .asBitmap()
                .load(url)
                .into(new Target<Bitmap>(100,100) {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                    }

                    @Override
                    public void onLoadStarted(@Nullable Drawable placeholder) {

                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {

                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }

                    ge
                });
    }*/

    private Key getNewKeySign() {
        Date currentTime = Calendar.getInstance().getTime();
        String sign = String.valueOf(currentTime.getTime());
        Key key = new ObjectKey(sign);
        PrefManager.saveSetting(this, TEST_KEY, sign);
        return key;
    }

    private void saveImage(Bitmap bitmap) {
        ContextWrapper wrapper = new ContextWrapper(getApplicationContext());
        File file = wrapper.getDir("Images", MODE_PRIVATE);
        file = new File(file, "Splash" + ".jpg");
        try {

            OutputStream stream = null;
            stream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            stream.flush();
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void goToOne() {
        Intent i = new Intent(MainActivity.this, OneActivity.class);
        startActivity(i);
    }

    private void runService(String url, int version) {
        Intent i = new Intent(MainActivity.this,
                DownloadService.class);
        i.putExtra("image_url", url);
        i.putExtra("version", version);
        startService(i);
    }

    private Drawable loadImage() {
        ContextWrapper cw = new ContextWrapper(MainActivity.this);

        //path to /data/data/yourapp/app_data/dirName
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File mypath = new File(directory, "profile.jpg");
        Drawable drawable = Drawable.createFromPath(mypath.toString());

        return drawable;
    }

    private Uri loadPathImage() {
        ContextWrapper cw = new ContextWrapper(MainActivity.this);

        //path to /data/data/yourapp/app_data/dirName
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File mypath = new File(directory, "profile.jpg");
        return Uri.fromFile(mypath);
    }
}

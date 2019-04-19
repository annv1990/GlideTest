package com.ajs.glidetest;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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

        /*btnLoad = findViewById(R.id.btnLoad);
        String sign = PrefManager.getString(this, TEST_KEY);
        if (sign.isEmpty()) {
            key = getNewKeySign();
        } else {
            key = new ObjectKey(sign);
        }*/

//        getImage(key, MainActivity.this, mListImage[0]);

        mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                goToOne();
            }
        };
        mHandler.postDelayed(mRunnable, 3000);
        if(!isDownloadOK) {
            runService(mListImage[0]);
        } else {
            clMain.setBackground(loadImage());
        }
    }

    private void getImage(Key key, Context context, String url) {
        //Create option for get image of Coupon
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        requestOptions.signature(key);

        Glide.with(context)
                .setDefaultRequestOptions(requestOptions)
                .load(url)
                .into(new Target<Drawable>() {
                    @Override
                    public void onLoadStarted(@Nullable Drawable placeholder) {

                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {

                    }

                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }

                    @Override
                    public void getSize(@NonNull SizeReadyCallback cb) {

                    }

                    @Override
                    public void removeCallback(@NonNull SizeReadyCallback cb) {

                    }

                    @Override
                    public void setRequest(@Nullable Request request) {

                    }

                    @Nullable
                    @Override
                    public Request getRequest() {
                        return null;
                    }

                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onStop() {

                    }

                    @Override
                    public void onDestroy() {

                    }
                });
    }

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

    private void runService(String url) {
        Intent i = new Intent(MainActivity.this,
                DownloadService.class);
        i.putExtra("image_url", url);
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
}

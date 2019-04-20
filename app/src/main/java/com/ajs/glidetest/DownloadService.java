package com.ajs.glidetest;

import android.app.IntentService;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadService extends IntentService {

    public DownloadService() {
        super(DownloadService.class.getName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String imageUrl = intent.getStringExtra("image_url");
        int version = intent.getIntExtra("version", -1);

        if (!TextUtils.isEmpty(imageUrl)) {
            downloadImage(imageUrl, version);
        }
    }

    private void downloadImage(String url, int version) {
//        File downloadFile = new File("/sdcard/IntentService_Example.png");
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File myPath = new File(directory, "profile.jpg");
        try {
            URL downloadURL = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) downloadURL.openConnection();
            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                throw new Exception("Error in connection");
            }
            InputStream is = conn.getInputStream();
            FileOutputStream os = new FileOutputStream(myPath);
            byte[] buffer = new byte[1024];
            int byteCount;
            while ((byteCount = is.read(buffer)) != -1) {
                os.write(buffer, 0, byteCount);
            }
            os.close();
            is.close();
            if (version > -1) {
                PrefManager.saveInt(DownloadService.this, "VERSION", version);
            }
                Log.d("com.ajs.glidetest", "download complete " + version);
            PrefManager.saveBoolean(DownloadService.this, "DOWNLOAD_OK", true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

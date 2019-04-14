package com.thmub.newbook;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;

import com.thmub.newbook.service.BookDownloadService;
import com.thmub.newbook.service.DownloadService;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;
import cn.bmob.v3.Bmob;

/**
 * Created by zhouas666 on 18-1-23.
 * 自定义application
 */

public class App extends MultiDexApplication {

    public final static String channelIdDownload = "channel_download";
    public final static String channelIdReadAloud = "channel_read_aloud";

    private static App sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        //默认初始化Bmob
        Bmob.initialize(this, "3f3b7628bf00914940a6919da16b33bf");
        //初始化下载服务
        startService(new Intent(getContext(), BookDownloadService.class));
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static Context getContext() {
        return sInstance;
    }

    public static App getInstance() {
        return sInstance;
    }

    public static Resources getAppResources() {
        return getInstance().getResources();
    }


}
package com.thmub.newbook.service;

import android.content.Intent;
import android.os.IBinder;

import com.thmub.newbook.base.BaseService;

import androidx.annotation.Nullable;

/**
 * Created by Zhouas666 on 2019-04-14
 * Github: https://github.com/zas023
 */
public class BookDownloadService extends BaseService {

    static final int START_DOWNLOAD = 1;            //标志位：开始下载
    static final int FAILED__DOWNLOAD = 2;          //标志位：下载失败
    static final int SUCCESS_DOWNLOAD = 3;          //标志位：下载完成
    static final int CANCEL_DOWNLOAD = 4;           //标志位：取消下载
    static final int PAUSE_DOWNLOAD = 5;            //标志位：暂停下载
    static final int UPDATE_PROGRESS = 6;           //标志位：更新进度

    private boolean isStarted = false;            //操作子线程的标志状态：是否运行
    private boolean isCanceled = false;           //操作子线程的标志状态：是否取消
    private boolean isPaused = false;             //操作子线程的标志状态：是否暂停


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

package com.thmub.newbook.model;

import com.thmub.newbook.bean.BookChapterBean;
import com.thmub.newbook.bean.DownloadBookBean;

import io.reactivex.Scheduler;

/**
 * Created by Zhouas666 on 2019-04-14
 * Github: https://github.com/zas023
 */
public interface IDownloadTask {

    int getId();

    void startDownload(Scheduler scheduler);

    void stopDownload();

    boolean isDownloading();

    boolean isFinishing();

    DownloadBookBean getDownloadBook();

    void onDownloadPrepared(DownloadBookBean downloadBook);

    void onDownloadProgress(BookChapterBean chapterBean);

    void onDownloadChange(DownloadBookBean downloadBook);

    void onDownloadError(DownloadBookBean downloadBook);

    void onDownloadComplete(DownloadBookBean downloadBook);
}

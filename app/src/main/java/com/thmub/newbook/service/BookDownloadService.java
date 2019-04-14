package com.thmub.newbook.service;

import android.content.Intent;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import com.thmub.newbook.base.BaseService;
import com.thmub.newbook.bean.BookChapterBean;
import com.thmub.newbook.bean.BookContentBean;
import com.thmub.newbook.bean.DownloadBookBean;
import com.thmub.newbook.bean.ShelfBookBean;
import com.thmub.newbook.bean.event.DownloadEvent;
import com.thmub.newbook.manager.BookManager;
import com.thmub.newbook.model.SourceModel;
import com.thmub.newbook.model.local.BookShelfRepository;
import com.thmub.newbook.utils.SharedPreUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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


    public static List<DownloadBookBean> downloadQueues = new ArrayList<>();

    private ExecutorService executor;
    private Scheduler scheduler;

    public boolean isBusy = false; // 当前是否有下载任务在进行
    public static boolean canceled = false;


    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);

        executor = Executors.newFixedThreadPool(SharedPreUtils.getInstance().getInt("threads_num", 4));
        scheduler = Schedulers.from(executor);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }


    public static void post(DownloadBookBean downloadBookBean) {
        EventBus.getDefault().post(downloadBookBean);
    }

    private void post(DownloadEvent message) {
        EventBus.getDefault().post(message);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public synchronized void addToDownloadQueue(DownloadBookBean taskBook) {
        if (!TextUtils.isEmpty(taskBook.getNoteUrl())) {
            boolean exists = false;
            // 判断当前书籍缓存任务是否存在
            for (int i = 0; i < downloadQueues.size(); i++) {
                if (downloadQueues.get(i).getNoteUrl().equals(taskBook.getNoteUrl())) {
                    exists = true;
                    break;
                }
            }
            if (exists) {
                //post(new DownloadEvent(taskBook.getNoteUrl(), "当前缓存任务已存在", FAILED__DOWNLOAD));
                return;
            }

            // 添加到下载队列
            downloadQueues.add(taskBook);
            //post(new DownloadEvent(taskBook.getNoteUrl(), "成功加入缓存队列", START_DOWNLOAD));
        }
        // 从队列顺序取出第一条下载
        if (downloadQueues.size() > 0 && !isBusy) {
            isBusy = true;
            downloadBook(downloadQueues.get(0));
        }
    }

    /**
     * 下载一个任务书籍中的章节
     *
     * @param downloadBook
     */
    public synchronized void downloadBook(final DownloadBookBean downloadBook) {
        Log.i("DownLoadService",downloadBook.toString());
        //查找需要下载的书籍章节
        List<BookChapterBean> downloadChapters = new ArrayList<>();
        ShelfBookBean book = BookShelfRepository.getInstance().getShelfBook(downloadBook.getNoteUrl());
        if (book != null) {
            Log.i("DownLoadService",book.toString());
            if (!book.getBookChapterList().isEmpty()) {
                for (int i = downloadBook.getStart(); i <= downloadBook.getEnd(); i++) {
                    BookChapterBean chapter = book.getChapter(i);
                    if (!BookManager.isChapterCached(book.getTitle(), book.getSourceName(),
                            BookManager.formatFileName(chapter.getChapterIndex(), chapter.getChapterTitle()))) {
                        downloadChapters.add(chapter);
                        downloaChapter(chapter);
                    }
                }
            }
            downloadBook.setDownloadCount(downloadChapters.size());
        } else {
            downloadBook.setValid(false);
        }
        isBusy = false;
        post(new DownloadBookBean());
    }

    /**
     * 下载章节
     */
    private synchronized void downloaChapter(BookChapterBean chapter) {
        Log.i("DownLoadService",chapter.toString());
        SourceModel.getInstance(chapter.getTag()).parseContent(chapter)
                .timeout(30, TimeUnit.SECONDS)
                .subscribeOn(scheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BookContentBean>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(BookContentBean bookContentBean) {
                        //保存章节内容
                        BookManager.getInstance().saveChapter(chapter.getBookTitle() + File.separator + chapter.getTag()
                                , chapter.getChapterIndex(), chapter.getChapterTitle()
                                , bookContentBean.getChapterContent());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}

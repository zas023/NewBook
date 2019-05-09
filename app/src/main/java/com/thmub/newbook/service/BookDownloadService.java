package com.thmub.newbook.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import com.thmub.newbook.App;
import com.thmub.newbook.R;
import com.thmub.newbook.base.BaseService;
import com.thmub.newbook.bean.BookChapterBean;
import com.thmub.newbook.bean.BookContentBean;
import com.thmub.newbook.bean.DownloadBookBean;
import com.thmub.newbook.bean.ShelfBookBean;
import com.thmub.newbook.bean.event.DownloadEvent;
import com.thmub.newbook.manager.BookManager;
import com.thmub.newbook.manager.RxBusManager;
import com.thmub.newbook.model.SourceModel;
import com.thmub.newbook.model.local.BookShelfRepository;
import com.thmub.newbook.ui.activity.DownloadActivity;
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
import androidx.core.app.NotificationCompat;
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

    private NotificationManager notificationManager;
    private Notification notification; //下载通知进度提示
    private NotificationCompat.Builder builder;

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);

        executor = Executors.newFixedThreadPool(SharedPreUtils.getInstance().getInt("threads_num", 4));
        scheduler = Schedulers.from(executor);

        notificationManager = (NotificationManager) App.getContext().getSystemService(Context.NOTIFICATION_SERVICE);

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
        RxBusManager.getInstance().post(message);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public synchronized void addToDownloadQueue(DownloadBookBean taskBook) {
        if (!TextUtils.isEmpty(taskBook.getBookLink())) {
            boolean exists = false;
            // 判断当前书籍缓存任务是否存在
            for (int i = 0; i < downloadQueues.size(); i++) {
                if (downloadQueues.get(i).getBookLink().equals(taskBook.getBookLink())) {
                    exists = true;
                    break;
                }
            }
            if (exists) {
                post(new DownloadEvent(taskBook.getBookLink(), "当前缓存任务已存在", FAILED__DOWNLOAD));
                return;
            }

            // 添加到下载队列
            downloadQueues.add(taskBook);
            post(new DownloadEvent(taskBook.getBookLink(), "成功加入缓存队列", START_DOWNLOAD));
        }
        // 从队列顺序取出第一条下载
        if (downloadQueues.size() > 0 && !isBusy) {
            isBusy = true;
            downloadBook(downloadQueues.remove(0));
        }
    }

    /**
     * 下载一个任务书籍中的章节
     *
     * @param downloadBook
     */
    public synchronized void downloadBook(DownloadBookBean downloadBook) {
        Log.i("DownLoadService", downloadBook.toString());

        //查找需要下载的书籍章节
        List<BookChapterBean> downloadChapters = new ArrayList<>();
        ShelfBookBean book = BookShelfRepository.getInstance().getShelfBookWithChapters(downloadBook.getBookLink());
        if (book != null) {
            Log.i("DownLoadService", book.toString());
            if (!book.getBookChapterList().isEmpty()) {
                for (int i = downloadBook.getStart(); i <= downloadBook.getEnd(); i++) {
                    BookChapterBean chapter = book.getChapter(i);
                    if (!BookManager.isChapterCached(book.getTitle(), book.getSourceTag(),
                            BookManager.formatFileName(chapter.getChapterIndex(), chapter.getChapterTitle()))) {
                        downloadChapters.add(chapter);

                        //通知
                        Intent mainIntent = new Intent(this, DownloadActivity.class);
                        PendingIntent mainPendingIntent = PendingIntent.getActivity(this, 0, mainIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                        //创建 Notification.Builder 对象
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, App.channelIdDownload)
                                .setSmallIcon(R.mipmap.ic_download)
                                //通知栏大图标
                                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                                //点击通知后自动清除
                                .setAutoCancel(true)
                                .setContentTitle("正在下载：" + chapter.getBookTitle())
                                .setContentText(chapter.getChapterTitle() == null ? "  " : chapter.getChapterTitle())
                                .setContentIntent(mainPendingIntent);
                        //builder.addAction(R.mipmap.ic_stop, "取消", getChancelPendingIntent());
                        notificationManager.notify(1, builder.build());
                        //下载
                        downloadChapter(chapter);
                    }
                }
            }
            downloadBook.setDownloadCount(downloadChapters.size());
        } else {
            downloadBook.setValid(false);
        }
        isBusy = false;
        //轮询
        post(new DownloadBookBean());
    }

    /**
     * 下载章节
     */
    private synchronized void downloadChapter(BookChapterBean chapter) {
        Log.i("DownLoadService", chapter.toString());
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

    /****************************************************************/
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}

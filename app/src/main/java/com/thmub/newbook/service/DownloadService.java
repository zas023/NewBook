package com.thmub.newbook.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Toast;

import com.thmub.newbook.App;
import com.thmub.newbook.R;
import com.thmub.newbook.bean.BookChapterBean;
import com.thmub.newbook.bean.DownloadBookBean;
import com.thmub.newbook.model.DownloadTaskImpl;
import com.thmub.newbook.model.IDownloadTask;
import com.thmub.newbook.ui.activity.DownloadActivity;
import com.thmub.newbook.utils.SharedPreUtils;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Zhouas666 on 2019-04-14
 * Github: https://github.com/zas023
 */
public class DownloadService extends Service {
    public static final String cancelAction = "cancelAction";
    public static final String addDownloadAction = "addDownload";
    public static final String removeDownloadAction = "removeDownloadAction";
    public static final String progressDownloadAction = "progressDownloadAction";
    public static final String obtainDownloadListAction = "obtainDownloadListAction";
    public static final String finishDownloadAction = "finishDownloadAction";
    private int notificationId = 19901122;
    private int downloadTaskId = 0;
    private long currentTime;

    public static boolean isRunning = false;

    private ExecutorService executor;
    private Scheduler scheduler;
    private int threadsNum;
    private Handler handler = new Handler(Looper.getMainLooper());

    private SparseArray<IDownloadTask> downloadTasks = new SparseArray<>();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("DownService","=======onCreate");
        isRunning = true;
        //创建 Notification.Builder 对象
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, App.channelIdDownload)
                .setSmallIcon(R.mipmap.ic_download)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setOngoing(false)
                .setContentTitle("离线下载")
                .setContentText("下载选择的章节到本地");
        //发送通知
        Notification notification = builder.build();
        startForeground(notificationId, notification);

        threadsNum = SharedPreUtils.getInstance().getInt("threads_num", 4);
        executor = Executors.newFixedThreadPool(threadsNum);
        scheduler = Schedulers.from(executor);
    }

    @Override
    public void onDestroy() {
        cancelDownload();
        isRunning = false;
        executor.shutdown();
        stopForeground(true);
        super.onDestroy();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("DownService","=======onStartCommand");
        if (intent != null) {
            String action = intent.getAction();
            if (action == null) {
                finishSelf();
            } else {
                switch (action) {
                    case addDownloadAction:
                        DownloadBookBean downloadBook = intent.getParcelableExtra("downloadBook");
                        if (downloadBook != null) {
                            addDownload(downloadBook);
                        }
                        break;
                    case removeDownloadAction:
                        String noteUrl = intent.getStringExtra("noteUrl");
                        removeDownload(noteUrl);
                        break;
                    case cancelAction:
                        finishSelf();
                        break;
                    case obtainDownloadListAction:
                        refreshDownloadList();
                        break;

                }
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 添加下载任务
     *
     * @param downloadBook
     */
    private synchronized void addDownload(DownloadBookBean downloadBook) {

        Log.i("DownService","=======addDownload");
        if (checkDownloadTaskExist(downloadBook)) {
            return;
        }
        downloadTaskId++;
        new DownloadTaskImpl(downloadTaskId, downloadBook) {
            @Override
            public void onDownloadPrepared(DownloadBookBean downloadBook) {
                if (canStartNextTask()) {
                    startDownload(scheduler);
                }
                downloadTasks.put(getId(), this);
                sendUpDownloadBook(addDownloadAction, downloadBook);
            }

            @Override
            public void onDownloadProgress(BookChapterBean chapterBean) {
                isProgress(chapterBean);
            }

            @Override
            public void onDownloadChange(DownloadBookBean downloadBook) {
                sendUpDownloadBook(progressDownloadAction, downloadBook);
            }

            @Override
            public void onDownloadError(DownloadBookBean downloadBook) {
                if (downloadTasks.indexOfValue(this) >= 0) {
                    downloadTasks.remove(getId());
                }

                toast(String.format(Locale.getDefault(), "%s：下载失败", downloadBook.getName()));

                startNextTaskAfterRemove(downloadBook);
            }

            @Override
            public void onDownloadComplete(DownloadBookBean downloadBook) {
                if (downloadTasks.indexOfValue(this) >= 0) {
                    downloadTasks.remove(getId());
                }
                startNextTaskAfterRemove(downloadBook);
            }
        };
    }

    private void cancelDownload() {
        for (int i = downloadTasks.size() - 1; i >= 0; i--) {
            IDownloadTask downloadTask = downloadTasks.valueAt(i);
            downloadTask.stopDownload();
        }
    }

    private void removeDownload(String noteUrl) {
        if (noteUrl == null) {
            return;
        }

        for (int i = downloadTasks.size() - 1; i >= 0; i--) {
            IDownloadTask downloadTask = downloadTasks.valueAt(i);
            DownloadBookBean downloadBook = downloadTask.getDownloadBook();
            if (downloadBook != null && TextUtils.equals(noteUrl, downloadBook.getNoteUrl())) {
                downloadTask.stopDownload();
                break;
            }
        }
    }

    private void refreshDownloadList() {
        ArrayList<DownloadBookBean> downloadBookBeans = new ArrayList<>();
        for (int i = downloadTasks.size() - 1; i >= 0; i--) {
            IDownloadTask downloadTask = downloadTasks.valueAt(i);
            DownloadBookBean downloadBook = downloadTask.getDownloadBook();
            if (downloadBook != null) {
                downloadBookBeans.add(downloadBook);
            }
        }
        if (!downloadBookBeans.isEmpty()) {
            sendUpDownloadBooks(downloadBookBeans);
        }
    }

    private void startNextTaskAfterRemove(DownloadBookBean downloadBook) {
        sendUpDownloadBook(removeDownloadAction, downloadBook);
        handler.postDelayed(() -> {
            if (downloadTasks.size() == 0) {
                finishSelf();
            } else {
                startNextTask();
            }
        }, 1000);
    }

    private void startNextTask() {
        if (!canStartNextTask()) {
            return;
        }
        for (int i = 0; i < downloadTasks.size(); i++) {
            IDownloadTask downloadTask = downloadTasks.valueAt(i);
            if (!downloadTask.isDownloading()) {
                downloadTask.startDownload(scheduler);
                break;
            }
        }
    }


    private boolean canStartNextTask() {
        int downloading = 0;
        for (int i = downloadTasks.size() - 1; i >= 0; i--) {
            IDownloadTask downloadTask = downloadTasks.valueAt(i);
            if (downloadTask.isDownloading()) {
                downloading += 1;
            }
        }
        return downloading < threadsNum;
    }


    private synchronized boolean checkDownloadTaskExist(DownloadBookBean downloadBook) {
        for (int i = downloadTasks.size() - 1; i >= 0; i--) {
            IDownloadTask downloadTask = downloadTasks.valueAt(i);
            if (Objects.equals(downloadTask.getDownloadBook(), downloadBook)) {
                return true;
            }
        }
        return false;
    }


    private void sendUpDownloadBook(String action, DownloadBookBean downloadBook) {
        Intent intent = new Intent(action);
        intent.putExtra("downloadBook", downloadBook);
        sendBroadcast(intent);
    }

    private void sendUpDownloadBooks(ArrayList<DownloadBookBean> downloadBooks) {
        Intent intent = new Intent(obtainDownloadListAction);
        intent.putParcelableArrayListExtra("downloadBooks", downloadBooks);
        sendBroadcast(intent);
    }

    private void toast(String msg) {
        Toast.makeText(DownloadService.this, msg, Toast.LENGTH_SHORT).show();
    }

    private PendingIntent getChancelPendingIntent() {
        Intent intent = new Intent(this, DownloadService.class);
        intent.setAction(DownloadService.cancelAction);
        return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private synchronized void isProgress(BookChapterBean downloadChapterBean) {
        Log.i("DownService","=======isProgress");
        if (!isRunning) {
            return;
        }

        if (System.currentTimeMillis() - currentTime < 1000) {//更新太快无法取消
            return;
        }

        currentTime = System.currentTimeMillis();

        Intent mainIntent = new Intent(this, DownloadActivity.class);
        PendingIntent mainPendingIntent = PendingIntent.getActivity(this, 0, mainIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        //创建 Notification.Builder 对象
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, App.channelIdDownload)
                .setSmallIcon(R.mipmap.ic_download)
                //通知栏大图标
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                //点击通知后自动清除
                .setAutoCancel(true)
                .setContentTitle("正在下载：" + downloadChapterBean.getBookTitle())
                .setContentText(downloadChapterBean.getChapterTitle() == null ? "  " : downloadChapterBean.getChapterTitle())
                .setContentIntent(mainPendingIntent);
        builder.addAction(R.mipmap.ic_stop, "取消", getChancelPendingIntent());
        //发送通知
        startForeground(notificationId, builder.build());
    }

    private void finishSelf() {
        sendBroadcast(new Intent(finishDownloadAction));
        stopSelf();
    }

    /**
     * 添加下载任务的公共方法
     * @param context
     * @param downloadBook
     */
    public static void addDownload(Context context, DownloadBookBean downloadBook) {
        if (context == null || downloadBook == null) {
            return;
        }
        Intent intent = new Intent(context, DownloadService.class);
        intent.setAction(addDownloadAction);
        intent.putExtra("downloadBook", downloadBook);
        context.startService(intent);
        Log.i("DownService","=======addDownload");
    }

    public static void removeDownload(Context context, String noteUrl) {
        if (noteUrl == null || !isRunning) {
            return;
        }
        Intent intent = new Intent(context, DownloadService.class);
        intent.setAction(removeDownloadAction);
        intent.putExtra("noteUrl", noteUrl);
        context.startService(intent);
    }

    public static void cancelDownload(Context context) {
        if (!isRunning) {
            return;
        }
        Intent intent = new Intent(context, DownloadService.class);
        intent.setAction(cancelAction);
        context.startService(intent);
    }

    public static void obtainDownloadList(Context context) {
        if (!isRunning) {
            return;
        }
        Intent intent = new Intent(context, DownloadService.class);
        intent.setAction(obtainDownloadListAction);
        context.startService(intent);
    }

}

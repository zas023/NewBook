package com.thmub.newbook.model;

import android.text.TextUtils;

import com.thmub.newbook.bean.BookChapterBean;
import com.thmub.newbook.bean.BookContentBean;
import com.thmub.newbook.bean.DownloadBookBean;
import com.thmub.newbook.bean.ShelfBookBean;
import com.thmub.newbook.manager.BookManager;
import com.thmub.newbook.model.local.BookShelfRepository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Zhouas666 on 2019-04-14
 * Github: https://github.com/zas023
 */
public abstract class DownloadTaskImpl implements IDownloadTask {

    private int id;

    private boolean isDownloading = false;

    private DownloadBookBean downloadBook;
    private List<BookChapterBean> downloadChapters;

    private boolean isLocked = false;

    private CompositeDisposable disposables;

    protected DownloadTaskImpl(int id, DownloadBookBean downloadBook) {
        this.id = id;
        this.downloadBook = downloadBook;
        downloadChapters = new ArrayList<>();
        disposables = new CompositeDisposable();

        Observable.create((ObservableOnSubscribe<DownloadBookBean>) emitter -> {
            ShelfBookBean book = BookShelfRepository.getInstance().getShelfBookWithChapters(downloadBook.getBookLink());
            if (book != null) {
                if (!book.getBookChapterList().isEmpty()) {
                    for (int i = downloadBook.getStart(); i <= downloadBook.getEnd(); i++) {
                        BookChapterBean chapter = book.getChapter(i);
                        if (!BookManager.isChapterCached(book.getTitle(), book.getSourceTag(),
                                BookManager.formatFileName(chapter.getChapterIndex(), chapter.getChapterTitle()))) {
                            downloadChapters.add(chapter);
                        }
                    }
                }
                downloadBook.setDownloadCount(downloadChapters.size());
            } else {
                downloadBook.setValid(false);
            }
            emitter.onNext(downloadBook);
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DownloadBookBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onNext(DownloadBookBean downloadBook) {
                        if (downloadBook.isValid()) {
                            onDownloadPrepared(downloadBook);
                            whenProgress(downloadChapters.get(0));
                        } else {
                            onDownloadComplete(downloadBook);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        downloadBook.setValid(false);
                        onDownloadError(downloadBook);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void startDownload(Scheduler scheduler) {
        if (isFinishing()) return;

        if (disposables.isDisposed()) {
            disposables = new CompositeDisposable();
        }

        isDownloading = true;

        toDownload(scheduler);
    }

    @Override
    public void stopDownload() {
        if (!disposables.isDisposed()) {
            disposables.dispose();
        }

        if (isDownloading) {
            isDownloading = false;
            onDownloadComplete(downloadBook);
        }

        if (!isFinishing()) {
            downloadChapters.clear();
        }

    }

    @Override
    public boolean isDownloading() {
        return isDownloading;
    }

    @Override
    public boolean isFinishing() {
        return downloadChapters.isEmpty();
    }

    @Override
    public DownloadBookBean getDownloadBook() {
        return downloadBook;
    }

    private synchronized void toDownload(Scheduler scheduler) {
        if (isFinishing()) {
            return;
        }

        if (!isLocked) getDownloadingChapter()
                .subscribe(new Observer<BookChapterBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                    }

                    public void onNext(BookChapterBean chapterBean) {
                        if (chapterBean != null) {
                            downloading(chapterBean, scheduler);
                        } else {
                            isLocked = true;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        onDownloadError(downloadBook);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * @return 章节下载信息
     */
    private Observable<BookChapterBean> getDownloadingChapter() {
        return Observable.create(emitter -> {
            BookChapterBean next = null;
            List<BookChapterBean> temp = new ArrayList<>(downloadChapters);
            for (BookChapterBean data : temp) {
                boolean cached = BookManager.isChapterCached(data.getBookTitle() + File.separator + data.getTag(),
                        BookManager.formatFileName(data.getChapterIndex(), data.getChapterTitle()));
                if (cached) {
                    removeFromDownloadList(data);
                } else {
                    next = data;
                    break;
                }
            }
            emitter.onNext(next);
        });
    }

    /**
     * 下载
     */
    private synchronized void downloading(BookChapterBean chapter, Scheduler scheduler) {
        whenProgress(chapter);
        Observable.create((ObservableOnSubscribe<BookChapterBean>) e -> {
            if (!BookManager.isChapterCached(chapter.getBookTitle(), chapter.getTag(),
                    BookManager.formatFileName(chapter.getChapterIndex(), chapter.getChapterTitle())
            )) {
                e.onNext(chapter);
            } else {
                e.onError(new Exception("cached"));
            }
            e.onComplete();
        })
                .flatMap(result -> SourceModel.getInstance(chapter.getTag()).parseContent(chapter))
                .timeout(30, TimeUnit.SECONDS)
                .subscribeOn(scheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BookContentBean>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onNext(BookContentBean bookContentBean) {
                        //RxBus.get().post(RxBusTag.CHAPTER_CHANGE, bookContentBean);
                        removeFromDownloadList(chapter);
                        whenNext(scheduler, true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        removeFromDownloadList(chapter);
                        if (TextUtils.equals(e.getMessage(), "cached")) {
                            whenNext(scheduler, false);
                        } else {
                            whenError(scheduler);
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 从下载列表移除
     */
    private synchronized void removeFromDownloadList(BookChapterBean chapterBean) {
        downloadChapters.remove(chapterBean);
    }

    private void whenNext(Scheduler scheduler, boolean success) {
        if (!isDownloading) {
            return;
        }

        if (success) {
            downloadBook.successCountAdd();
        }
        if (isFinishing()) {
            stopDownload();
            onDownloadComplete(downloadBook);
        } else {
            onDownloadChange(downloadBook);
            toDownload(scheduler);
        }
    }

    private void whenError(Scheduler scheduler) {
        if (!isDownloading) {
            return;
        }

        if (isFinishing()) {
            stopDownload();
            if (downloadBook.getSuccessCount() == 0) {
                onDownloadError(downloadBook);
            } else {
                onDownloadComplete(downloadBook);
            }
        } else {
            toDownload(scheduler);
        }
    }

    private void whenProgress(BookChapterBean chapterBean) {
        if (!isDownloading) {
            return;
        }
        onDownloadProgress(chapterBean);
    }
}

package com.thmub.newbook.widget.page;

import android.annotation.SuppressLint;
import android.widget.Toast;

import com.thmub.newbook.bean.BookChapterBean;
import com.thmub.newbook.bean.BookContentBean;
import com.thmub.newbook.bean.ShelfBookBean;
import com.thmub.newbook.constant.Constant;
import com.thmub.newbook.model.SourceModel;
import com.thmub.newbook.utils.BookShelfUtils;
import com.thmub.newbook.utils.FileUtils;
import com.thmub.newbook.utils.NetworkUtil;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 网络章节页面加载器
 */

public class NetPageLoader extends PageLoader {

    private static final String TAG = "NetPageLoader";

    private List<String> downloadingChapterList = new ArrayList<>();
    private ExecutorService executorService;
    private Scheduler scheduler;

    NetPageLoader(PageView pageView, ShelfBookBean bookShelfBean) {
        super(pageView, bookShelfBean);
        executorService = Executors.newFixedThreadPool(20);
        scheduler = Schedulers.from(executorService);
    }

    /**
     * 刷新章节列表
     */
    @Override
    public void refreshChapterList() {
        if (bookShelfBean.getBookChapterListSize() > 0) {
            isChapterListPrepare = true;

            // 目录加载完成，执行回调操作。
            if (mPageChangeListener != null) {
                mPageChangeListener.onCategoryFinish(bookShelfBean.getBookChapterList());
            }

            // 打开章节
            skipToChapter(bookShelfBean.getCurChapter(), bookShelfBean.getCurChapterPage());
        } else {
            //如果shelfBook中没有章节列表，则从网络加载
            SourceModel.getInstance(bookShelfBean.getSource())
                    .parseCatalog(bookShelfBean.getLink())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<BookChapterBean>>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            compositeDisposable.add(d);
                        }

                        @Override
                        public void onNext(List<BookChapterBean> chapterBeans) {
                            isChapterListPrepare = true;
                            bookShelfBean.setBookChapterList(chapterBeans);
                            // 目录加载完成
                            if (mPageChangeListener != null) {
                                mPageChangeListener.onCategoryFinish(bookShelfBean.getBookChapterList());
                            }

                            // 加载并显示当前章节
                            skipToChapter(bookShelfBean.getCurChapter(), bookShelfBean.getCurChapterPage());
                        }

                        @Override
                        public void onError(Throwable e) {
                            showChapterError(e.getMessage());
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }

    /**
     * 加载章节内容
     *
     * @param chapterIndex
     */
    private synchronized void loadChapterContent(final int chapterIndex) {
        if (downloadingChapterList.size() >= 20) return;
        if (dealDownloadingList(listHandle.CHECK, bookShelfBean.getChapter(chapterIndex).getChapterLink()))
            return;
        if (null != bookShelfBean && bookShelfBean.getBookChapterListSize() > 0) {
            Observable.create((ObservableOnSubscribe<Integer>) e -> {
                if (shouldRequestChapter(chapterIndex)) {
                    dealDownloadingList(listHandle.ADD, bookShelfBean.getChapter(chapterIndex).getChapterLink());
                    e.onNext(chapterIndex);
                }
                e.onComplete();
            })
                    .flatMap(index -> SourceModel.getInstance(bookShelfBean.getSource())
                            .parseContent(bookShelfBean.getChapter(chapterIndex)))
                    .timeout(30, TimeUnit.SECONDS)
                    .subscribeOn(scheduler)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BookContentBean>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            compositeDisposable.add(d);
                        }

                        @SuppressLint("DefaultLocale")
                        @Override
                        public void onNext(BookContentBean bookContentBean) {
                            BookShelfUtils.saveChapterInfo(bookShelfBean.getTitle() + "-" + bookShelfBean.getSource()
                                    , bookShelfBean.getChapter(chapterIndex).getChapterIndex()
                                    , bookShelfBean.getChapter(chapterIndex).getChapterTitle()
                                    , bookContentBean.getChapterContent());
                            dealDownloadingList(listHandle.REMOVE, bookContentBean.getChapterLink());
                            finishContent(bookContentBean.getChapterIndex());
                        }

                        @Override
                        public void onError(Throwable e) {
                            dealDownloadingList(listHandle.REMOVE, bookShelfBean.getChapter(chapterIndex).getChapterLink());
                            if (chapterIndex == mCurChapterPos) {
                                showChapterError(e.getMessage());
                            }
                        }

                        @Override
                        public void onComplete() {
                        }
                    });
        }
    }

    /**
     * 编辑下载列表
     */
    private synchronized boolean dealDownloadingList(listHandle editType, String value) {
        if (editType == listHandle.ADD) {
            downloadingChapterList.add(value);
            return true;
        } else if (editType == listHandle.REMOVE) {
            downloadingChapterList.remove(value);
            return true;
        } else {
            return downloadingChapterList.indexOf(value) != -1;
        }
    }

    /**
     * 章节下载完成
     */
    private void finishContent(int chapterIndex) {
        if (chapterIndex == mCurChapterPos) {
            super.parseCurChapter();
        }
        if (chapterIndex == mCurChapterPos - 1) {
            super.parsePrevChapter();
        }
        if (chapterIndex == mCurChapterPos + 1) {
            super.parseNextChapter();
        }
    }

    @Override
    protected String getChapterContent(BookChapterBean chapter) throws Exception {

        File file = BookShelfUtils.getBookFile(bookShelfBean.getTitle() + "-" + bookShelfBean.getSource(),
                chapter.getChapterIndex(), chapter.getChapterTitle());
        if (!file.exists()) return null;
        byte[] contentByte = FileUtils.getBytes(file);
        return new String(contentByte, StandardCharsets.UTF_8);
    }

    @Override
    protected boolean noChapterData(BookChapterBean chapter) {
        File file = new File(Constant.BOOK_CACHE_PATH +File.separator
                + bookShelfBean.getTitle() + "-" + bookShelfBean.getSource() + File.separator
                + BookShelfUtils.formatFileName(chapter.getChapterIndex(),chapter.getChapterTitle())
                + FileUtils.SUFFIX_CB);
        return !file.exists();
    }

    private boolean shouldRequestChapter(Integer chapterIndex) {
        return NetworkUtil.isNetWorkAvailable() && noChapterData(bookShelfBean.getChapter(chapterIndex));
    }

    // 装载上一章节的内容
    @Override
    void parsePrevChapter() {
        if (mPageChangeListener != null && mCurChapterPos >= 1) {
            loadChapterContent(mCurChapterPos - 1);
        }
        super.parsePrevChapter();
    }

    /**
     * 解析当前章内容
     */
    @Override
    void parseCurChapter() {
        for (int i = mCurChapterPos; i < Math.min(mCurChapterPos + 5, bookShelfBean.getBookChapterListSize()); i++) {
            loadChapterContent(i);
        }
        super.parseCurChapter();
    }

    /**
     * 解析下一章节内容
     */
    @Override
    void parseNextChapter() {
        for (int i = mCurChapterPos; i < Math.min(mCurChapterPos + 5, bookShelfBean.getBookChapterListSize()); i++) {
            loadChapterContent(i);
        }
        super.parseNextChapter();
    }

    /**
     * 更新目录
     */
    @Override
    public void updateChapter() {
        Toast.makeText(mPageView.getActivity(), "目录更新中", Toast.LENGTH_SHORT).show();
        SourceModel.getInstance(bookShelfBean.getSource())
                .parseCatalog(bookShelfBean.getLink())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<BookChapterBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(List<BookChapterBean> bookChapterBeans) {
                        isChapterListPrepare = true;
                        if (bookChapterBeans.size() > bookShelfBean.getBookChapterListSize()) {
                            Toast.makeText(mPageView.getActivity(), "更新完成,有新章节", Toast.LENGTH_SHORT).show();
                            bookShelfBean.setBookChapterList(bookChapterBeans);
                        } else {
                            Toast.makeText(mPageView.getActivity(), "更新完成,无新章节", Toast.LENGTH_SHORT).show();
                        }

                        // 目录加载完成
                        if (mPageChangeListener != null) {
                            mPageChangeListener.onCategoryFinish(bookShelfBean.getBookChapterList());
                        }

                        // 加载并显示当前章节
                        skipToChapter(bookShelfBean.getCurChapter(), bookShelfBean.getCurChapterPage());
                    }

                    @Override
                    public void onError(Throwable e) {
                        showChapterError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void closeBook() {
        super.closeBook();
        executorService.shutdown();
    }

    public enum listHandle {
        ADD, REMOVE, CHECK
    }
}

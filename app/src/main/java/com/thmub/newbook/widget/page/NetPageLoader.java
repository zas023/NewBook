package com.thmub.newbook.widget.page;

import android.util.Log;
import android.widget.Toast;

import com.thmub.newbook.bean.BookChapterBean;
import com.thmub.newbook.bean.BookContentBean;
import com.thmub.newbook.bean.ShelfBookBean;
import com.thmub.newbook.manager.BookManager;
import com.thmub.newbook.model.SourceModel;
import com.thmub.newbook.utils.FileUtils;
import com.thmub.newbook.utils.NetworkUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    private ExecutorService executorService;
    private Scheduler scheduler;

    NetPageLoader(PageView pageView, ShelfBookBean bookShelfBean) {
        super(pageView, bookShelfBean);
        executorService = Executors.newFixedThreadPool(5);
        scheduler = Schedulers.from(executorService);
    }

    /**
     * 刷新章节列表
     */
    @Override
    public void refreshChapterList() {
        if (bookShelfBean != null && bookShelfBean.getBookChapterListSize() > 0) {
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
        if (null == bookShelfBean || bookShelfBean.getBookChapterListSize() <= 0) {
            if (chapterIndex == mCurChapterPos) {
                showChapterError("当前目录为空");
            }
            return;
        }
        //判断是否需要从网络请求章节
        if (NetworkUtils.isNetWorkAvailable() && !hasChapterData(bookShelfBean.getChapter(chapterIndex))) {
            SourceModel.getInstance(bookShelfBean.getSource())
                    .parseContent(bookShelfBean.getChapter(chapterIndex))
                    .subscribeOn(scheduler)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BookContentBean>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            compositeDisposable.add(d);
                        }

                        @Override
                        public void onNext(BookContentBean bookContentBean) {
                            //保存章节内容
                            BookManager.getInstance()
                                    .saveChapter(bookShelfBean.getTitle()
                                                    + File.separator + bookShelfBean.getSource()
                                            , bookShelfBean.getChapter(chapterIndex).getChapterIndex()
                                            , bookShelfBean.getChapter(chapterIndex).getChapterTitle()
                                            , bookContentBean.getChapterContent());
                            Log.i(TAG, chapterIndex + "保存章节内容完成");
                            finishLoadContent(bookContentBean.getChapterIndex());
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (chapterIndex == mCurChapterPos) {
                                showChapterError(e.getMessage());
                            }
                        }

                        @Override
                        public void onComplete() {
                        }
                    });
        } else {
            finishLoadContent(chapterIndex);
        }
    }

    /**
     * 章节下载完成
     */
    private void finishLoadContent(int chapterIndex) {
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

    /**
     * 获取章节内容
     *
     * @param chapter
     * @return
     * @throws Exception
     */
    @Override
    protected String getChapterContent(BookChapterBean chapter) throws Exception {

        File file = BookManager.getBookFile(bookShelfBean.getTitle() + File.separator + bookShelfBean.getSource(),
                BookManager.formatFileName(chapter.getChapterIndex(), chapter.getChapterTitle()));
        if (!file.exists()) return null;
        byte[] contentByte = FileUtils.getBytes(file);
        return new String(contentByte, StandardCharsets.UTF_8);
    }

    /**
     * 判断章节是否缓存
     *
     * @param chapter
     * @return
     */
    @Override
    protected boolean hasChapterData(BookChapterBean chapter) {
        return BookManager.isChapterCached(bookShelfBean.getTitle() + File.separator + bookShelfBean.getSource(),
                BookManager.formatFileName(chapter.getChapterIndex(), chapter.getChapterTitle()));
    }

    /**
     * 解析上一章节的内容
     */
    @Override
    void parsePrevChapter() {
        if (mPageChangeListener != null && mCurChapterPos >= 1) {
            loadChapterContent(mCurChapterPos - 1);
        }
    }

    /**
     * 解析当前章内容
     */
    @Override
    void parseCurChapter() {
        loadChapterContent(mCurChapterPos);
    }

    /**
     * 解析下一章节内容
     */
    @Override
    void parseNextChapter() {
        //加载接下来的两章节
//        for (int i=mCurChapterPos+1;i<Math.min(mCurChapterPos + 3, bookShelfBean.getBookChapterListSize());i++)
//            loadChapterContent(i);
        loadChapterContent(Math.min(mCurChapterPos + 1, bookShelfBean.getBookChapterListSize()));
    }

    /**
     * 更新目录
     */
    @Override
    public void updateCatalog() {
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
}

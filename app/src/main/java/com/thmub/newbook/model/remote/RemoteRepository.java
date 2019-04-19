package com.thmub.newbook.model.remote;

import com.thmub.newbook.bean.zhui.BookBean;
import com.thmub.newbook.bean.zhui.DiscoverRankBean;
import com.thmub.newbook.bean.zhui.DiscoverSortBean;
import com.thmub.newbook.bean.zhui.StoreBannerBean;
import com.thmub.newbook.bean.zhui.StoreNodeBookBean;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Retrofit;

/**
 * Created by Zhouas666 on 2019-04-13
 * Github: https://github.com/zas023
 */
public class RemoteRepository {
    private static final String TAG = "RemoteRepository";

    private static RemoteRepository sInstance;
    private Retrofit mRetrofit;
    private BookApi mBookApi;

    private RemoteRepository() {
        mRetrofit = RemoteHelper.getInstance().getRetrofit();

        mBookApi = mRetrofit.create(BookApi.class);
    }

    public static RemoteRepository getInstance() {
        if (sInstance == null) {
            synchronized (RemoteHelper.class) {
                if (sInstance == null) {
                    sInstance = new RemoteRepository();
                }
            }
        }
        return sInstance;
    }

    /****************************Store************************/

    /**
     * 书籍详情
     *
     * @return
     */
    public Single<BookBean> getBookDetail(String bookId) {
        return mBookApi.getBookDetail(bookId);
    }


    /**
     * 商城顶部banner
     *
     * @return
     */
    public Single<List<StoreBannerBean>> getStoreBanner() {
        return mBookApi.getStoreBannerList()
                .map(bean -> bean.getData());
    }

    /**
     * 精选分类图书
     *
     * @param nodeId
     * @return
     */
    public Single<List<StoreNodeBookBean>> getStoreNodeBooks(String nodeId) {
        return mBookApi.getStoreNodeBookList(nodeId)
                .map(bean -> bean.getData());
    }

    /****************************Discover************************/
    /**
     * 书籍分类
     *
     * @return
     */
    public Single<DiscoverSortBean> getDiscoverSort() {
        return mBookApi.getDiscoverSort();
    }

    /**
     * 分类书籍列表
     *
     * @param gender
     * @param type
     * @param major
     * @param minor
     * @param start
     * @param limit
     * @return
     */
    public Single<List<BookBean>> getSortBooks(String gender, String type, String major, String minor, int start, int limit) {
        return mBookApi.getSortBookList(gender, type, major, minor, start, limit)
                .map(bean -> bean.getBooks());
    }

    /**
     * 书籍排行
     *
     * @return
     */
    public Single<DiscoverRankBean> getDiscoverRank() {
        return mBookApi.getDiscoverRank();
    }

    /**
     * 分类书籍列表
     *
     * @param rankId
     * @return
     */
    public Single<List<BookBean>> getRankBooks(String rankId) {
        return mBookApi.getRankBookList(rankId)
                .map(bean -> bean.getRanking().getBooks());
    }
}

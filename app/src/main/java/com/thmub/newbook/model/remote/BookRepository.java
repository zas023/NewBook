package com.thmub.newbook.model.remote;

import com.thmub.newbook.bean.zhui.BookBean;
import com.thmub.newbook.bean.zhui.ThemeBean;
import com.thmub.newbook.bean.zhui.DiscoverRankBean;
import com.thmub.newbook.bean.zhui.DiscoverSortBean;
import com.thmub.newbook.bean.zhui.StoreBannerBean;
import com.thmub.newbook.bean.zhui.StoreNodeBookBean;
import com.thmub.newbook.bean.zhui.ThemeBookBean;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Retrofit;

/**
 * Created by Zhouas666 on 2019-04-13
 * Github: https://github.com/zas023
 */
public class BookRepository {
    private static final String TAG = "BookRepository";

    private static BookRepository sInstance;
    private Retrofit mRetrofit;
    private BookApi mBookApi;

    private BookRepository() {
        mRetrofit = RemoteHelper.getInstance().getRetrofit();

        mBookApi = mRetrofit.create(BookApi.class);
    }

    public static BookRepository getInstance() {
        if (sInstance == null) {
            synchronized (RemoteHelper.class) {
                if (sInstance == null) {
                    sInstance = new BookRepository();
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

    /**
     * 主题书单
     *
     * @param duration
     * @param sort
     * @param start
     * @param limit
     * @param tag
     * @param gender
     * @return
     */
    public Single<List<ThemeBean>> getDiscoverTheme(String duration, String sort, int start, int limit, String tag, String gender) {
        return mBookApi.getDiscoverTheme(duration, sort, start, limit, tag, gender)
                .map(bean -> bean.getBookLists());
    }

    /**
     * 主题书单书籍
     *
     * @param bookListId
     * @return
     */
    public Single<List<ThemeBookBean>> getThemeBooks(String bookListId) {
        return mBookApi.getThemeBookList(bookListId)
                .map(bean -> bean.getBookList().getBooks());
    }


}

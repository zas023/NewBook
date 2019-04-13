package com.thmub.newbook.model.remote;

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

}

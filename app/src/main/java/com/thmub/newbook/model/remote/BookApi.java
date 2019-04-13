package com.thmub.newbook.model.remote;

import com.thmub.newbook.bean.zhui.BookBean;
import com.thmub.newbook.bean.zhui.StoreBannerListBean;
import com.thmub.newbook.bean.zhui.StoreNodeBookListBean;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Zhouas666 on 2019-04-13
 * Github: https://github.com/zas023
 */
public interface BookApi {


    /****************************Book***********************************/
    /**
     * 书籍详情
     *
     * @param bookId
     * @return
     */
    @GET("/book/{bookId}")
    Single<BookBean> getBookDetail(@Path("bookId") String bookId);


    /****************************Store***********************************/
    @GET("/recommendPage/node/spread/575f74f27a4a60dc78a435a3?pl=ios")
    Single<StoreBannerListBean> getStoreBannerList();

    @GET("/recommendPage/node/books/all/{nodeId}")
    Single<StoreNodeBookListBean> getStoreNodeBookList(@Path("nodeId") String nodeId);

}

package com.thmub.newbook.model.remote;

import com.thmub.newbook.bean.zhui.BookBean;
import com.thmub.newbook.bean.zhui.DiscoverThemeBean;
import com.thmub.newbook.bean.zhui.DiscoverRankBean;
import com.thmub.newbook.bean.zhui.DiscoverSortBean;
import com.thmub.newbook.bean.zhui.RankBookListBean;
import com.thmub.newbook.bean.zhui.SortBookListBean;
import com.thmub.newbook.bean.zhui.StoreBannerListBean;
import com.thmub.newbook.bean.zhui.StoreNodeBookListBean;
import com.thmub.newbook.bean.zhui.ThemeBookListBean;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

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

    /****************************Sort***********************************/
    /**
     * 获取分类
     *
     * @return
     */
    @GET("/cats/lv2/statistics")
    Single<DiscoverSortBean> getDiscoverSort();

    /**
     * 按分类获取书籍列表
     *
     * @param gender male、female
     * @param type   hot(热门)、new(新书)、reputation(好评)、over(完结)
     * @param major  玄幻
     * @param minor  东方玄幻、异界大陆、异界争霸、远古神话
     * @param limit  50
     * @return
     */
    @GET("/book/by-categories")
    Single<SortBookListBean> getSortBookList(@Query("gender") String gender, @Query("type") String type,
                                             @Query("major") String major, @Query("minor") String minor,
                                             @Query("start") int start, @Query("limit") int limit);

    /****************************Rank***********************************/
    /**
     * 获取所有排行榜
     *
     * @return
     */
    @GET("/ranking/gender")
    Single<DiscoverRankBean> getDiscoverRank();

    /**
     * 获取单一排行榜
     * 周榜：rankingId-> _id
     * 月榜：rankingId-> monthRank
     * 总榜：rankingId-> totalRank
     *
     * @return
     */
    @GET("/ranking/{rankingId}")
    Single<RankBookListBean> getRankBookList(@Path("rankingId") String rankingId);

    /****************************BookList***********************************/
    /**
     * 获取主题书单列表
     * 本周最热：duration=last-seven-days&sort=collectorCount
     * 最新发布：duration=all&sort=created
     * 最多收藏：duration=all&sort=collectorCount
     * <p>
     * 如:http://api.zhuishushenqi.com/book-list?duration=last-seven-days&sort=collectorCount&start=0&limit=20&tag=%E9%83%BD%E5%B8%82&gender=male
     *
     * @param tag    都市、古代、架空、重生、玄幻、网游
     * @param gender male、female
     * @param limit  20
     * @return
     */
    @GET("/book-list")
    Single<DiscoverThemeBean> getDiscoverTheme(@Query("duration") String duration, @Query("sort") String sort,
                                           @Query("start") int start, @Query("limit") int limit,
                                           @Query("tag") String tag, @Query("gender") String gender);

    /**
     * 获取书单详情
     *
     * @return
     */
    @GET("/book-list/{bookListId}")
    Single<ThemeBookListBean> getThemeBookList(@Path("bookListId") String bookListId);
}

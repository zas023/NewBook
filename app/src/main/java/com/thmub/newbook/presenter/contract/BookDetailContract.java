package com.thmub.newbook.presenter.contract;

import com.thmub.newbook.base.BaseContract;
import com.thmub.newbook.bean.BookChapterBean;
import com.thmub.newbook.bean.BookDetailBean;
import com.thmub.newbook.bean.BookSearchBean;
import com.thmub.newbook.bean.ShelfBookBean;

import java.util.List;

/**
 * Created by Zhouas666 on 2019-03-28
 * Github: https://github.com/zas023
 */
public interface BookDetailContract {

    interface View extends BaseContract.BaseView {

        void finishLoadDetailBook(BookDetailBean item);

        void finishLoadCatalogs(List<BookChapterBean> items);

        void finishLoadFindBooks(List<BookSearchBean> items);

        void finishRemoveBook(Integer i);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        //移出书架
        void removeShelfBook(ShelfBookBean bookBean);

        //加载书籍
        void loadDetailBook(BookSearchBean bookBean);

        //加载目录
        void loadCatalogs(ShelfBookBean bookBean);

        //加载同类书籍
        void loadFindBooks(BookDetailBean bookBean);
    }

}

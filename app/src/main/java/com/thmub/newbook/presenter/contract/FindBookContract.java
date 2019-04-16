package com.thmub.newbook.presenter.contract;

import com.thmub.newbook.base.BaseContract;
import com.thmub.newbook.bean.BookDetailBean;
import com.thmub.newbook.bean.BookSearchBean;

import java.util.List;

/**
 * Created by Zhouas666 on 2019-04-15
 * Github: https://github.com/zas023
 * <p>
 * 发现书籍contract
 */
public interface FindBookContract {

    interface View extends BaseContract.BaseView {
        void finishLoadFindBooks(List<BookSearchBean> items);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void loadFindBooks(BookDetailBean book);
    }

}

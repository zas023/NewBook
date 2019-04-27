package com.thmub.newbook.presenter.contract;

import com.thmub.newbook.base.BaseContract;
import com.thmub.newbook.bean.BookDetailBean;
import com.thmub.newbook.bean.BookSearchBean;

/**
 * Created by Zhouas666 on 2019-03-28
 * Github: https://github.com/zas023
 */
public interface ReadContract {

    interface View extends BaseContract.BaseView {
        void finishLoadDetailBook(BookDetailBean book);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        void loadDetailBook(BookSearchBean bookBean);
    }

}

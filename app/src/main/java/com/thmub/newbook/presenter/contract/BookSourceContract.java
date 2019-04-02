package com.thmub.newbook.presenter.contract;

import com.thmub.newbook.base.BaseContract;
import com.thmub.newbook.bean.BookSourceBean;

import java.util.List;

/**
 * Created by Zhouas666 on 2019-03-30
 * Github: https://github.com/zas023
 */
public interface BookSourceContract {

    interface View extends BaseContract.BaseView {
        void finishLoadBookSource(List<BookSourceBean> items);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        //添加到书架上
        void loadBookSource();
    }

}

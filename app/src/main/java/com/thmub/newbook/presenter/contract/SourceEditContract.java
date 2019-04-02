package com.thmub.newbook.presenter.contract;

import com.thmub.newbook.base.BaseContract;
import com.thmub.newbook.bean.BookSourceBean;


/**
 * Created by Zhouas666 on 2019-03-30
 * Github: https://github.com/zas023
 */
public interface SourceEditContract {

    interface View extends BaseContract.BaseView {
        void saveSuccess(Long i);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void saveBookSource(BookSourceBean bean);
    }

}

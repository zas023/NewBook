package com.thmub.newbook.presenter.contract;

import com.thmub.newbook.base.BaseContract;

/**
 * Created by Zhouas666 on 2019-03-28
 * Github: https://github.com/zas023
 */
public interface ReadContract {

    interface View extends BaseContract.BaseView {
        void finishLoadContent(String content);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void loadContent(String chapter, String sourceId);
    }

}

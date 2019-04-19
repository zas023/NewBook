package com.thmub.newbook.presenter.contract;

import com.thmub.newbook.base.BaseContract;
import com.thmub.newbook.bean.zhui.DiscoverSortBean;

/**
 * Created by Zhouas666 on 2019-04-18
 * Github: https://github.com/zas023
 */
public interface BookSortContract {
    interface View extends BaseContract.BaseView {
        void finishLoadBookSort(DiscoverSortBean bean);

    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void loadBookSort();
    }
}

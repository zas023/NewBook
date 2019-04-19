package com.thmub.newbook.presenter.contract;

import com.thmub.newbook.base.BaseContract;
import com.thmub.newbook.bean.zhui.DiscoverRankBean;

/**
 * Created by Zhouas666 on 2019-04-19
 * Github: https://github.com/zas023
 */
public interface BookRankContract {
    interface View extends BaseContract.BaseView {
        void finishLoadBookRank(DiscoverRankBean bean);

    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void loadBookRank();
    }
}

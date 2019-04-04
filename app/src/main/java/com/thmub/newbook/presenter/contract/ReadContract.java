package com.thmub.newbook.presenter.contract;

import com.thmub.newbook.base.BaseContract;
import com.thmub.newbook.bean.ShelfBookBean;

/**
 * Created by Zhouas666 on 2019-03-28
 * Github: https://github.com/zas023
 */
public interface ReadContract {

    interface View extends BaseContract.BaseView {
        void finishSaveRecord();
    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void saveReadRecord(ShelfBookBean bookBean);
    }

}

package com.thmub.newbook.presenter.contract;

import com.thmub.newbook.base.BaseContract;
import com.thmub.newbook.bean.zhui.StoreNodeBookBean;

import java.util.List;

/**
 * Created by Zhouas666 on 2019-04-13
 * Github: https://github.com/zas023
 */
public interface StoreNodeContract {

    interface View extends BaseContract.BaseView {
        void finishStoreNodeBook(List<StoreNodeBookBean> items);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void loadStoreNodeBook(String id);
    }

}

package com.thmub.newbook.presenter.contract;

import com.thmub.newbook.base.BaseContract;
import com.thmub.newbook.bean.ShelfBookBean;

import java.util.List;

/**
 * Created by Zhouas666 on 2019-04-02
 * Github: https://github.com/zas023
 */
public interface BookShelfContract {

    interface View extends BaseContract.BaseView {
        void finishLoadShelfBook(List<ShelfBookBean> items);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        //加载目录
        void loadShelfBook();

        //检查更新
        void checkBookUpdate(List<ShelfBookBean> items);
    }

}

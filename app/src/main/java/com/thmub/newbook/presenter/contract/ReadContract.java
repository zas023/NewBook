package com.thmub.newbook.presenter.contract;

import com.thmub.newbook.base.BaseContract;
import com.thmub.newbook.bean.BookChapterBean;
import com.thmub.newbook.bean.ShelfBookBean;

import java.util.List;

/**
 * Created by Zhouas666 on 2019-03-28
 * Github: https://github.com/zas023
 */
public interface ReadContract {

    interface View extends BaseContract.BaseView {
        void finishLoadCatalogs(List<BookChapterBean> items);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        //加载目录
        void loadCatalogs(ShelfBookBean bookBean);
    }

}

package com.thmub.newbook.presenter.contract;

import com.thmub.newbook.base.BaseContract;
import com.thmub.newbook.bean.BookSearchBean;
import com.thmub.newbook.bean.zhui.StoreBannerBean;

import java.util.List;

/**
 * Created by Zhouas666 on 2019-04-13
 * Github: https://github.com/zas023
 */
public interface BookStoreContract {

    interface View extends BaseContract.BaseView {
        void finishStoreBanner(List<StoreBannerBean> items);

        void finishBookSearchBean(BookSearchBean bean);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        //加载商城
        void loadStoreBanner();

        //
        void loadBookSearchBean(String id);
    }

}

package com.thmub.newbook.presenter.contract;

import com.thmub.newbook.base.BaseContract;
import com.thmub.newbook.bean.zhui.BookBean;

import java.util.List;

/**
 * Created by Zhouas666 on 2019-04-18
 * Github: https://github.com/zas023
 * <p>
 * 分类书籍详情contract
 */
public interface BookSortDetailContract {
    interface View extends BaseContract.BaseView {
        void finishLoadSortBooks(List<BookBean> items);

    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void loadSortBooks(String gender, String type, String major, String minor, int start, int limit);
    }
}

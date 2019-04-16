package com.thmub.newbook.presenter.contract;

import com.thmub.newbook.base.BaseContract;
import com.thmub.newbook.bean.BookChapterBean;
import com.thmub.newbook.bean.ShelfBookBean;

import java.util.List;

/**
 * Created by Zhouas666 on 2019-04-15
 * Github: https://github.com/zas023
 * <p>
 * 发现书籍contract
 */
public interface CatalogContract {

    interface View extends BaseContract.BaseView {
        void finishLoadCatalog(List<BookChapterBean> items);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void loadCatalog(ShelfBookBean book);
    }

}

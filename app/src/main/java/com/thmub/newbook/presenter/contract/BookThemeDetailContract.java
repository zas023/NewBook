package com.thmub.newbook.presenter.contract;

import com.thmub.newbook.base.BaseContract;
import com.thmub.newbook.bean.zhui.ThemeBookBean;

import java.util.List;

/**
 * Created by Zhouas666 on 2019-04-27
 * Github: https://github.com/zas023
 * <p>
 * 分类书籍详情contract
 */
public interface BookThemeDetailContract {
    interface View extends BaseContract.BaseView {
        void finishLoadThemeBooks(List<ThemeBookBean> items);

    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void loadThemeBooks(String id);
    }
}

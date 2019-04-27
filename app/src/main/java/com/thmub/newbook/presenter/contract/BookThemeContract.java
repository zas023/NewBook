package com.thmub.newbook.presenter.contract;

import com.thmub.newbook.base.BaseContract;
import com.thmub.newbook.bean.zhui.ThemeBean;

import java.util.List;

/**
 * Created by Zhouas666 on 2019-04-27
 * Github: https://github.com/zas023
 * <p>
 * 主题书单列表contract
 */
public interface BookThemeContract {
    interface View extends BaseContract.BaseView {
        void finishLoadBookLists(List<ThemeBean> items);

    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void loadBookLists(String duration, String sort, int start, int limit, String tag, String gender);
    }
}

package com.thmub.newbook.presenter.contract;

import com.thmub.newbook.base.BaseContract;
import com.thmub.newbook.bean.bmob.MyUser;


/**
 * Created by Zhouas666 on 2019-04-17
 * Github: https://github.com/zas023
 */
public interface UserLandContract {

    interface View extends BaseContract.BaseView {
        void saveSuccess(String s);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void saveUser(MyUser user);
    }

}

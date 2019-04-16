package com.thmub.newbook.base;

/**
 * Created by zhouas666 on 17-4-26.
 */

public interface BaseContract {

    interface BasePresenter<T> {

        void attachView(T view);

        void detachView();
    }

    interface BaseView {

        void showError(Throwable e);

        void complete();
    }
}

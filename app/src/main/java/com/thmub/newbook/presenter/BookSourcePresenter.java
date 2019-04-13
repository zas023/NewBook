package com.thmub.newbook.presenter;

import com.thmub.newbook.base.RxPresenter;
import com.thmub.newbook.model.local.BookSourceRepository;
import com.thmub.newbook.presenter.contract.BookSourceContract;

/**
 * Created by Zhouas666 on 2019-03-30
 * Github: https://github.com/zas023
 *
 * 书源管理presenter
 */
public class BookSourcePresenter extends RxPresenter<BookSourceContract.View>
        implements BookSourceContract.Presenter {

    @Override
    public void loadBookSource() {
        mView.finishLoadBookSource(BookSourceRepository.getInstance()
                .getAllBookSource());
    }
}

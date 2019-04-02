package com.thmub.newbook.presenter;

import com.thmub.newbook.base.RxPresenter;
import com.thmub.newbook.model.repo.BookShelfRepository;
import com.thmub.newbook.presenter.contract.BookShelfContract;

/**
 * Created by Zhouas666 on 2019-04-02
 * Github: https://github.com/zas023
 */
public class BookShelfPresenter extends RxPresenter<BookShelfContract.View>
        implements BookShelfContract.Presenter {
    @Override
    public void loadShelfBook() {
        mView.finishLoadShelfBook(BookShelfRepository.getInstance().getAllShelfBooks());
    }
}

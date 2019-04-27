package com.thmub.newbook.presenter;

import com.thmub.newbook.base.RxPresenter;
import com.thmub.newbook.model.remote.BookRepository;
import com.thmub.newbook.presenter.contract.BookSortDetailContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Zhouas666 on 2019-04-18
 * Github: https://github.com/zas023
 */
public class BookSortDetailPresenter extends RxPresenter<BookSortDetailContract.View>
        implements BookSortDetailContract.Presenter {

    @Override
    public void loadSortBooks(String gender, String type, String major, String minor, int start, int limit) {
        addDisposable(BookRepository.getInstance()
                .getSortBooks(gender, type, major, minor, start, limit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((beans, throwable) -> {
                    if (throwable == null)
                        mView.finishLoadSortBooks(beans);
                    else
                        mView.showError(throwable);
                    mView.complete();
                }));
    }
}

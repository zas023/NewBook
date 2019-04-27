package com.thmub.newbook.presenter;

import com.thmub.newbook.base.RxPresenter;
import com.thmub.newbook.model.remote.BookRepository;
import com.thmub.newbook.presenter.contract.BookSortContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Zhouas666 on 2019-04-18
 * Github: https://github.com/zas023
 */
public class BookSortPresenter extends RxPresenter<BookSortContract.View>
        implements BookSortContract.Presenter {
    @Override
    public void loadBookSort() {
        addDisposable(BookRepository.getInstance()
                .getDiscoverSort()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((discoverSortBean, throwable) -> {
                    if (throwable == null)
                        mView.finishLoadBookSort(discoverSortBean);
                    else
                        mView.showError(throwable);
                }));
    }
}

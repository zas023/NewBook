package com.thmub.newbook.presenter;

import com.thmub.newbook.base.RxPresenter;
import com.thmub.newbook.model.remote.BookRepository;
import com.thmub.newbook.presenter.contract.BookThemeContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Zhouas666 on 2019-04-18
 * Github: https://github.com/zas023
 */
public class BookThemePresenter extends RxPresenter<BookThemeContract.View>
        implements BookThemeContract.Presenter {

    @Override
    public void loadBookLists(String duration, String sort, int start, int limit, String tag, String gender) {
        addDisposable(BookRepository.getInstance()
                .getDiscoverTheme(duration, sort, start, limit, tag, gender)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((beans, throwable) -> {
                    if (throwable == null)
                        mView.finishLoadBookLists(beans);
                    else
                        mView.showError(throwable);
                    mView.complete();
                }));
    }
}

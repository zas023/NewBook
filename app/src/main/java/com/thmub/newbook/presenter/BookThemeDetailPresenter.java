package com.thmub.newbook.presenter;

import com.thmub.newbook.base.RxPresenter;
import com.thmub.newbook.model.remote.BookRepository;
import com.thmub.newbook.presenter.contract.BookThemeDetailContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Zhouas666 on 2019-04-18
 * Github: https://github.com/zas023
 */
public class BookThemeDetailPresenter extends RxPresenter<BookThemeDetailContract.View>
        implements BookThemeDetailContract.Presenter {

    @Override
    public void loadThemeBooks(String id) {
        addDisposable(BookRepository.getInstance()
                .getThemeBooks(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((beans, throwable) -> {
                    if (throwable == null)
                        mView.finishLoadThemeBooks(beans);
                    else
                        mView.showError(throwable);
                    mView.complete();
                }));
    }
}

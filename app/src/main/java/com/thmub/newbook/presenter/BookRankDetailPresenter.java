package com.thmub.newbook.presenter;

import com.thmub.newbook.base.RxPresenter;
import com.thmub.newbook.model.remote.BookRepository;
import com.thmub.newbook.presenter.contract.BookRankDetailContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Zhouas666 on 2019-04-18
 * Github: https://github.com/zas023
 */
public class BookRankDetailPresenter extends RxPresenter<BookRankDetailContract.View>
        implements BookRankDetailContract.Presenter {

    @Override
    public void loadRankBooks(String rankId) {
        addDisposable(BookRepository.getInstance()
                .getRankBooks(rankId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((beans, throwable) -> {
                    if (throwable == null)
                        mView.finishLoadRankBooks(beans);
                    else
                        mView.showError(throwable);
                    mView.complete();
                }));
    }
}

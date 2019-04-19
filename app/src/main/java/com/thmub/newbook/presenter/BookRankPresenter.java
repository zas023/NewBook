package com.thmub.newbook.presenter;

import com.thmub.newbook.base.RxPresenter;
import com.thmub.newbook.model.remote.RemoteRepository;
import com.thmub.newbook.presenter.contract.BookRankContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Zhouas666 on 2019-04-19
 * Github: https://github.com/zas023
 */
public class BookRankPresenter extends RxPresenter<BookRankContract.View>
        implements BookRankContract.Presenter {

    @Override
    public void loadBookRank() {
        addDisposable(RemoteRepository.getInstance()
                .getDiscoverRank()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((discoverRankBean, throwable) -> {
                    if (throwable == null)
                        mView.finishLoadBookRank(discoverRankBean);
                    else
                        mView.showError(throwable);
                }));
    }
}

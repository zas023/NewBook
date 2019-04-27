package com.thmub.newbook.presenter;

import com.thmub.newbook.base.RxPresenter;
import com.thmub.newbook.model.remote.BookRepository;
import com.thmub.newbook.presenter.contract.StoreNodeContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Zhouas666 on 2019-04-13
 * Github: https://github.com/zas023
 */
public class StoreNodePresenter extends RxPresenter<StoreNodeContract.View>
        implements StoreNodeContract.Presenter {

    @Override
    public void loadStoreNodeBook(String id) {
        addDisposable(BookRepository.getInstance()
                .getStoreNodeBooks(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(beans -> {
                    mView.finishStoreNodeBook(beans);
                }));
    }
}

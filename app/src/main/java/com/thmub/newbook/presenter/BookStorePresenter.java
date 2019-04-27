package com.thmub.newbook.presenter;

import com.thmub.newbook.base.RxPresenter;
import com.thmub.newbook.manager.BookManager;
import com.thmub.newbook.model.remote.BookRepository;
import com.thmub.newbook.presenter.contract.BookStoreContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Zhouas666 on 2019-04-13
 * Github: https://github.com/zas023
 */
public class BookStorePresenter extends RxPresenter<BookStoreContract.View>
        implements BookStoreContract.Presenter {

    @Override
    public void loadStoreBanner() {
        addDisposable(BookRepository.getInstance()
                .getStoreBanner()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(beans -> {
                    mView.finishStoreBanner(beans);
                }));
    }

    @Override
    public void loadBookSearchBean(String id) {
        addDisposable(BookRepository.getInstance()
                .getBookDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    mView.finishBookSearchBean(BookManager.getSearchBook(bean));
                }));
    }
}

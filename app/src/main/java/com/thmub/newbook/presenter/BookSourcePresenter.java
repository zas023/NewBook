package com.thmub.newbook.presenter;

import com.thmub.newbook.base.RxPresenter;
import com.thmub.newbook.bean.BookChapterBean;
import com.thmub.newbook.bean.BookSourceBean;
import com.thmub.newbook.manager.BookSourceManager;
import com.thmub.newbook.model.local.BookSourceRepository;
import com.thmub.newbook.presenter.contract.BookSourceContract;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Zhouas666 on 2019-03-30
 * Github: https://github.com/zas023
 * <p>
 * 书源管理presenter
 */
public class BookSourcePresenter extends RxPresenter<BookSourceContract.View>
        implements BookSourceContract.Presenter {

    @Override
    public void loadBookSource() {
        mView.finishLoadBookSource(BookSourceRepository.getInstance()
                .getAllBookSource());
    }


    @Override
    public void importLocalSource(String str) {
        BookSourceManager.getInstance().importSourceFromLocal(str)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<BookSourceBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(List<BookSourceBean> bookSourceBeans) {
                        mView.finishImportBookSource(bookSourceBeans);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void importNetSource(String url) {
        BookSourceManager.getInstance().importSourceFromNet(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<BookSourceBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(List<BookSourceBean> bookSourceBeans) {
                        mView.finishImportBookSource(bookSourceBeans);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}

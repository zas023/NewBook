package com.thmub.newbook.presenter;

import com.thmub.newbook.base.RxPresenter;
import com.thmub.newbook.bean.ShelfBookBean;
import com.thmub.newbook.bean.BookChapterBean;
import com.thmub.newbook.model.SourceModel;
import com.thmub.newbook.model.repo.BookShelfRepository;
import com.thmub.newbook.presenter.contract.BookDetailContract;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Zhouas666 on 2019-03-28
 * Github: https://github.com/zas023
 */
public class BookDetailPresenter extends RxPresenter<BookDetailContract.View>
        implements BookDetailContract.Presenter {

    private static final String TAG = "BookDetailPresenter";

    @Override
    public void removeShelfBook(ShelfBookBean bookBean) {
        BookShelfRepository.getInstance()
                .removeShelfBookInRx(bookBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(Integer i) {
                        mView.finishRemoveBook(i);
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
    public void loadCatalogs(ShelfBookBean bookBean) {
        SourceModel.getInstance(bookBean.getSource())
                .parseCatalog(bookBean.getLink())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<BookChapterBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(List<BookChapterBean> bookChapterBeans) {
                        mView.finishLoadCatalogs(bookChapterBeans);
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

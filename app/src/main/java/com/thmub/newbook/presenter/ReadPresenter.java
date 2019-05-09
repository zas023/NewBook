package com.thmub.newbook.presenter;

import com.thmub.newbook.base.RxPresenter;
import com.thmub.newbook.bean.BookDetailBean;
import com.thmub.newbook.bean.BookSearchBean;
import com.thmub.newbook.model.SourceModel;
import com.thmub.newbook.presenter.contract.ReadContract;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Zhouas666 on 2019-03-28
 * Github: https://github.com/zas023
 */
public class ReadPresenter extends RxPresenter<ReadContract.View>
        implements ReadContract.Presenter {

    private static final String TAG = "ReadPresenter";


    @Override
    public void loadDetailBook(BookSearchBean bookBean) {
        SourceModel.getInstance(bookBean.getSourceTag())
                .parseBook(bookBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BookDetailBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(BookDetailBean bookDetailBean) {
                        mView.finishLoadDetailBook(bookDetailBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError(e);
                    }

                    @Override
                    public void onComplete() {
                        mView.complete();
                    }
                });
    }
}

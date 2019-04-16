package com.thmub.newbook.presenter;

import com.thmub.newbook.base.RxPresenter;
import com.thmub.newbook.bean.BookDetailBean;
import com.thmub.newbook.bean.BookSearchBean;
import com.thmub.newbook.model.SourceModel;
import com.thmub.newbook.presenter.contract.FindBookContract;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Zhouas666 on 2019-04-15
 * Github: https://github.com/zas023
 */
public class FindBookPresenter extends RxPresenter<FindBookContract.View>
        implements FindBookContract.Presenter {

    @Override
    public void loadFindBooks(BookDetailBean book) {
        if (book == null)
            mView.showError(new Throwable("书籍不能为空"));

        SourceModel.getInstance(book.getSourceTag())
                .findBook(book.getFindLink())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<BookSearchBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(List<BookSearchBean> bookSearchBeans) {
                        mView.finishLoadFindBooks(bookSearchBeans);
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

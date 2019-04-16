package com.thmub.newbook.presenter;

import com.thmub.newbook.base.RxPresenter;
import com.thmub.newbook.bean.BookChapterBean;
import com.thmub.newbook.bean.ShelfBookBean;
import com.thmub.newbook.model.SourceModel;
import com.thmub.newbook.presenter.contract.CatalogContract;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
/**
 * Created by Zhouas666 on 2019-04-15
 * Github: https://github.com/zas023
 */
public class CatalogPresenter extends RxPresenter<CatalogContract.View>
        implements CatalogContract.Presenter {

    @Override
    public void loadCatalog(ShelfBookBean book) {
        if (book == null)
            mView.showError(new Throwable("书籍不能为空"));

        SourceModel.getInstance(book.getSourceTag())
                .parseCatalog(book)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<BookChapterBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(List<BookChapterBean> bookChapterBeans) {
                        mView.finishLoadCatalog(bookChapterBeans);
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

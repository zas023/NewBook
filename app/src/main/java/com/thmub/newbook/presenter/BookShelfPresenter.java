package com.thmub.newbook.presenter;

import com.thmub.newbook.base.RxPresenter;
import com.thmub.newbook.bean.BookChapterBean;
import com.thmub.newbook.bean.ShelfBookBean;
import com.thmub.newbook.model.SourceModel;
import com.thmub.newbook.model.local.BookShelfRepository;
import com.thmub.newbook.presenter.contract.BookShelfContract;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Zhouas666 on 2019-04-02
 * Github: https://github.com/zas023
 */
public class BookShelfPresenter extends RxPresenter<BookShelfContract.View>
        implements BookShelfContract.Presenter {
    @Override
    public void loadShelfBook() {
        mView.finishLoadShelfBook(BookShelfRepository.getInstance().getAllShelfBooks());
    }

    @Override
    public void checkBookUpdate(List<ShelfBookBean> items) {
        if (items == null || items.isEmpty()) return;
        for (ShelfBookBean book: items){
            SourceModel.getInstance(book.getSourceName())
                    .parseCatalog(book)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<BookChapterBean>>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            addDisposable(d);
                        }

                        @Override
                        public void onNext(List<BookChapterBean> beans) {

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
}

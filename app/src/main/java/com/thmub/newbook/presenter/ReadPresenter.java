package com.thmub.newbook.presenter;

import com.thmub.newbook.base.RxPresenter;
import com.thmub.newbook.bean.ShelfBookBean;
import com.thmub.newbook.constant.Constant;
import com.thmub.newbook.model.repo.BookShelfRepository;
import com.thmub.newbook.presenter.contract.ReadContract;
import com.thmub.newbook.utils.StringUtils;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
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
    public void saveReadRecord(ShelfBookBean bookBean) {
        if (bookBean != null) {
            Observable.create((ObservableOnSubscribe<ShelfBookBean>) e -> {
                bookBean.setLastRead(StringUtils.
                        dateConvert(System.currentTimeMillis(), Constant.FORMAT_BOOK_DATE));
                bookBean.setIsUpdate(false);
                BookShelfRepository.getInstance().saveShelfBook(bookBean);
                e.onNext(bookBean);
                e.onComplete();
            }).subscribeOn(Schedulers.newThread())
                    .subscribe(new Observer<ShelfBookBean>() {

                        @Override
                        public void onSubscribe(Disposable d) {
                            addDisposable(d);
                        }

                        @Override
                        public void onNext(ShelfBookBean shelfBookBean) {
                            mView.finishSaveRecord();
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }
}

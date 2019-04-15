package com.thmub.newbook.presenter;

import com.thmub.newbook.base.RxPresenter;
import com.thmub.newbook.bean.BookChapterBean;
import com.thmub.newbook.bean.DownloadBookBean;
import com.thmub.newbook.bean.ShelfBookBean;
import com.thmub.newbook.model.SourceModel;
import com.thmub.newbook.model.local.BookShelfRepository;
import com.thmub.newbook.presenter.contract.ReadContract;
import com.thmub.newbook.service.DownloadService;

import java.util.List;

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
    public void loadCatalogs(ShelfBookBean bookBean, boolean fromNet) {
        if (fromNet) {
            SourceModel.getInstance(bookBean.getSourceTag())
                    .parseCatalog(bookBean)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<BookChapterBean>>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            addDisposable(d);
                        }

                        @Override
                        public void onNext(List<BookChapterBean> bookChapterBeans) {
                            mView.finishLoadCatalogs(bookChapterBeans, true);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } else {
            mView.finishLoadCatalogs(BookShelfRepository.getInstance().getChapters(bookBean)
                    , false);
        }
    }
}

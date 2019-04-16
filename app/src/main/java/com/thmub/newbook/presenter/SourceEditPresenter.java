package com.thmub.newbook.presenter;

import com.thmub.newbook.base.RxPresenter;
import com.thmub.newbook.bean.BookSourceBean;
import com.thmub.newbook.model.local.BookSourceRepository;
import com.thmub.newbook.presenter.contract.SourceEditContract;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Zhouas666 on 2019-03-30
 * Github: https://github.com/zas023
 * <p>
 * 书源编辑presenter
 */
public class SourceEditPresenter extends RxPresenter<SourceEditContract.View>
        implements SourceEditContract.Presenter {

    @Override
    public void saveBookSource(BookSourceBean bean) {

        BookSourceRepository.getInstance()
                .saveBookSourceWithAsync(bean)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(Long l) {
                        mView.saveSuccess(l);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError(new Throwable("保存失败"));
                    }

                    @Override
                    public void onComplete() {
                        mView.complete();
                    }
                });
    }
}

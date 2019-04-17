package com.thmub.newbook.presenter;

import com.thmub.newbook.base.RxPresenter;
import com.thmub.newbook.bean.bmob.MyUser;
import com.thmub.newbook.presenter.contract.UserLandContract;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Zhouas666 on 2019-04-15
 * Github: https://github.com/zas023
 */
public class UserLandPresenter extends RxPresenter<UserLandContract.View>
        implements UserLandContract.Presenter {

    @Override
    public void saveUser(MyUser user) {
        user.saveObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(String s) {
                        mView.saveSuccess(s);
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

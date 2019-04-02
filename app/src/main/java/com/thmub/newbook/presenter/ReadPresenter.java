package com.thmub.newbook.presenter;

import com.thmub.newbook.base.RxPresenter;
import com.thmub.newbook.presenter.contract.ReadContract;

/**
 * Created by Zhouas666 on 2019-03-28
 * Github: https://github.com/zas023
 */
public class ReadPresenter extends RxPresenter<ReadContract.View>
        implements ReadContract.Presenter {

    private static final String TAG = "ReadPresenter";

    @Override
    public void loadContent(String bookLink, String sourceId) {

    }
}

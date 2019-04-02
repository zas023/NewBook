package com.thmub.newbook.ui.fragment;

import android.os.Bundle;
import android.util.Log;

import com.thmub.newbook.R;
import com.thmub.newbook.base.BaseMVPFragment;
import com.thmub.newbook.bean.ShelfBookBean;
import com.thmub.newbook.presenter.BookShelfPresenter;
import com.thmub.newbook.presenter.contract.BookShelfContract;
import com.thmub.newbook.ui.adapter.BookShelfAdapter;
import com.thmub.newbook.widget.refresh.ScrollRefreshRecyclerView;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import butterknife.BindView;

/**
 * Created by Zhouas666 on 2019-03-28
 * Github: https://github.com/zas023
 * <p>
 * 书架fragment
 */
public class BookShelfFragment extends BaseMVPFragment<BookShelfContract.Presenter>
        implements BookShelfContract.View {


    @BindView(R.id.shelf_rv_content)
    ScrollRefreshRecyclerView shelfRvContent;

    private BookShelfAdapter mAdapter;

    /************************initialization*****************************/
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_book_shelf;
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        mAdapter = new BookShelfAdapter();
        shelfRvContent.setLayoutManager(new LinearLayoutManager(mContext));
        shelfRvContent.setAdapter(mAdapter);
        shelfRvContent.setRefreshEnabled(true);
    }

    @Override
    protected void initClick() {
        super.initClick();
        shelfRvContent.setOnRefreshListener(() -> {
            mAdapter.clear();
            mPresenter.loadShelfBook();
            shelfRvContent.setRefreshEnabled(true);
        });
    }

    @Override
    protected void processLogic() {
        super.processLogic();
        mPresenter.loadShelfBook();
    }

    /************************Transaction*****************************/
    @Override
    protected BookShelfContract.Presenter bindPresenter() {
        return new BookShelfPresenter();
    }

    @Override
    public void finishLoadShelfBook(List<ShelfBookBean> items) {
        shelfRvContent.setRefreshEnabled(false);
        mAdapter.addItems(items);
    }

    @Override
    public void showError() {

    }

    @Override
    public void complete() {

    }
}

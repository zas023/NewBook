package com.thmub.newbook.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import com.thmub.newbook.R;
import com.thmub.newbook.base.BaseMVPFragment;
import com.thmub.newbook.bean.ShelfBookBean;
import com.thmub.newbook.model.repo.BookShelfRepository;
import com.thmub.newbook.presenter.BookShelfPresenter;
import com.thmub.newbook.presenter.contract.BookShelfContract;
import com.thmub.newbook.ui.activity.ReadActivity;
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
        shelfRvContent.startRefresh();
    }

    @Override
    protected void initClick() {
        super.initClick();
        //监听下拉刷新
        shelfRvContent.setOnRefreshListener(() -> {
            mAdapter.clear();
            mPresenter.loadShelfBook();
        });
        //监听recycler点击事件
        mAdapter.setOnItemClickListener((view, pos) -> {
            //greenDao的大坑
            //多表联查需要设置  book__setDaoSession(DaoSession);
            ShelfBookBean book = mAdapter.getItem(pos);
            book.setBookChapterList(BookShelfRepository.getInstance().getChapters(book));
            startActivity(new Intent(mContext, ReadActivity.class)
                    .putExtra(ReadActivity.EXTRA_BOOK, book)
                    .putExtra(ReadActivity.EXTRA_IS_COLLECTED, true));
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
        shelfRvContent.finishRefresh();
        mAdapter.addItems(items);
    }

    @Override
    public void showError() {

    }

    @Override
    public void complete() {

    }

    /*************************Life Cycle*******************************/
    /**
     * 实现从阅读界面返回时刷新书架
     */
    @Override
    public void onResume() {
        super.onResume();
        mAdapter.clear();
        shelfRvContent.startRefresh();
        mPresenter.loadShelfBook();
    }
}

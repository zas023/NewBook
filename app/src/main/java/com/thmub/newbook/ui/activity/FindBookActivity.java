package com.thmub.newbook.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.futuremind.recyclerviewfastscroll.FastScroller;
import com.thmub.newbook.R;
import com.thmub.newbook.base.BaseMVPActivity;
import com.thmub.newbook.bean.BookDetailBean;
import com.thmub.newbook.bean.BookSearchBean;
import com.thmub.newbook.presenter.FindBookPresenter;
import com.thmub.newbook.presenter.contract.FindBookContract;
import com.thmub.newbook.ui.adapter.FindBookListAdapter;
import com.thmub.newbook.utils.ToastUtils;

import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * Created by Zhouas666 on 2019-04-16
 * Github: https://github.com/zas023
 */
public class FindBookActivity extends BaseMVPActivity<FindBookContract.Presenter> implements FindBookContract.View {

    public static final String EXTRA_BOOK = "extra_book";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_content)
    RecyclerView rvContent;


    private BookDetailBean mBook;

    private FindBookListAdapter mAdapter;

    /*********************Initialization****************************/
    @Override
    protected int getLayoutId() {
        return R.layout.activity_base_list;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mBook = getIntent().getParcelableExtra(EXTRA_BOOK);
    }

    @Override
    protected void setUpToolbar(Toolbar toolbar) {
        super.setUpToolbar(toolbar);
        getSupportActionBar().setTitle("发现书籍");
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mAdapter = new FindBookListAdapter();
        rvContent.setLayoutManager(new LinearLayoutManager(mContext));
        rvContent.setAdapter(mAdapter);
    }

    @Override
    protected void initClick() {
        super.initClick();
        //recycler
        mAdapter.setOnItemClickListener((view, pos) -> {
            startActivity(new Intent(mContext, BookDetailActivity.class)
                    .putExtra(BookDetailActivity.EXTRA_BOOK, mAdapter.getItem(pos)));
            //关闭本层视图
            finish();
        });
    }

    @Override
    protected void processLogic() {
        super.processLogic();
        mPresenter.loadFindBooks(mBook);
    }

    /*********************Initialization****************************/
    @Override
    public void finishLoadFindBooks(List<BookSearchBean> items) {
        //先清空内容
        mAdapter.clear();
        mAdapter.addItems(items);
    }

    @Override
    public void showError(Throwable e) {
        ToastUtils.showError(mContext, e.getMessage());
    }

    @Override
    public void complete() {
    }

    @Override
    protected FindBookContract.Presenter bindPresenter() {
        return new FindBookPresenter();
    }

}

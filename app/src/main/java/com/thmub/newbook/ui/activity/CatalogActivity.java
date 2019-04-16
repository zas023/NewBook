package com.thmub.newbook.ui.activity;

import android.os.Bundle;

import com.futuremind.recyclerviewfastscroll.FastScroller;
import com.thmub.newbook.R;
import com.thmub.newbook.base.BaseMVPActivity;
import com.thmub.newbook.bean.BookChapterBean;
import com.thmub.newbook.bean.BookDetailBean;
import com.thmub.newbook.presenter.CatalogPresenter;
import com.thmub.newbook.presenter.contract.CatalogContract;
import com.thmub.newbook.ui.adapter.DetailCatalogAdapter;
import com.thmub.newbook.utils.ToastUtils;
import com.thmub.newbook.widget.DashlineItemDivider;

import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * Created by Zhouas666 on 2019-04-16
 * Github: https://github.com/zas023
 * <p>
 * 书籍目录activity
 */
public class CatalogActivity extends BaseMVPActivity<CatalogContract.Presenter>
        implements CatalogContract.View {

    public static final String EXTRA_BOOK = "extra_book";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_content)
    RecyclerView rvContent;
    @BindView(R.id.fast_scroller)
    FastScroller fastScroller;


    private BookDetailBean mBook;

    private DetailCatalogAdapter mAdapter;

    /*********************Initialization****************************/
    @Override
    protected int getLayoutId() {
        return R.layout.activity_catalog;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mBook = getIntent().getParcelableExtra(EXTRA_BOOK);
    }

    @Override
    protected void setUpToolbar(Toolbar toolbar) {
        super.setUpToolbar(toolbar);
        getSupportActionBar().setTitle(mBook.getTitle() + "目录");
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mAdapter = new DetailCatalogAdapter();
        rvContent.setLayoutManager(new LinearLayoutManager(mContext));
        rvContent.setAdapter(mAdapter);
        rvContent.addItemDecoration(new DashlineItemDivider());

        fastScroller.setRecyclerView(rvContent);
    }

    @Override
    protected void initClick() {
        super.initClick();
    }

    @Override
    protected void processLogic() {
        super.processLogic();
        mPresenter.loadCatalog(mBook);
    }

    /*********************Initialization****************************/
    @Override
    public void finishLoadCatalog(List<BookChapterBean> items) {
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
    protected CatalogContract.Presenter bindPresenter() {
        return new CatalogPresenter();
    }

}

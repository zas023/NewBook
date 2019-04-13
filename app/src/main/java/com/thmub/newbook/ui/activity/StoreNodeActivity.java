package com.thmub.newbook.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.thmub.newbook.R;
import com.thmub.newbook.base.BaseMVPActivity;
import com.thmub.newbook.bean.zhui.StoreNodeBean;
import com.thmub.newbook.bean.zhui.StoreNodeBookBean;
import com.thmub.newbook.manager.BookManager;
import com.thmub.newbook.presenter.StoreNodePresenter;
import com.thmub.newbook.presenter.contract.StoreNodeContract;
import com.thmub.newbook.ui.adapter.StoreBookListAdapter;
import com.thmub.newbook.widget.refresh.ScrollRefreshRecyclerView;

import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import butterknife.BindView;

/**
 * Created by Zhouas666 on 2019-04-13
 * Github: https://github.com/zas023
 * <p>
 * 书城书籍列表activity
 */
public class StoreNodeActivity extends BaseMVPActivity<StoreNodeContract.Presenter>
        implements StoreNodeContract.View {

    public static final String EXTRA_STORE_NODE = "extra_store_node";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.node_rv_content)
    ScrollRefreshRecyclerView nodeRvContent;

    private StoreBookListAdapter mAdapter;
    private StoreNodeBean mStoreNode;

    /**********************Initialization***************************/
    @Override
    protected int getLayoutId() {
        return R.layout.activity_store_node;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mStoreNode = getIntent().getParcelableExtra(EXTRA_STORE_NODE);
    }

    @Override
    protected void setUpToolbar(Toolbar toolbar) {
        super.setUpToolbar(toolbar);
        getSupportActionBar().setTitle(mStoreNode.getTitle());
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mAdapter = new StoreBookListAdapter();
        nodeRvContent.setLayoutManager(new LinearLayoutManager(mContext));
        nodeRvContent.setAdapter(mAdapter);
    }

    @Override
    protected void initClick() {
        super.initClick();
        //刷新事件
        nodeRvContent.setOnRefreshListener(() -> {
            mAdapter.clear();
            nodeRvContent.startRefresh();
            mPresenter.loadStoreNodeBook(mStoreNode.get_id());
        });
        //点击书籍事件
        mAdapter.setOnItemClickListener((view, pos) ->
                startActivity(new Intent(mContext, BookDetailActivity.class)
                        .putExtra(BookDetailActivity.EXTRA_BOOK
                                , BookManager.getSearchBook(mAdapter.getItem(pos).getBook())))
        );
    }

    @Override
    protected void processLogic() {
        super.processLogic();
        nodeRvContent.startRefresh();
        mPresenter.loadStoreNodeBook(mStoreNode.get_id());
    }

    /********************Initialization*******************************/
    @Override
    protected StoreNodeContract.Presenter bindPresenter() {
        return new StoreNodePresenter();
    }

    @Override
    public void finishStoreNodeBook(List<StoreNodeBookBean> items) {
        mAdapter.addItems(items);
        nodeRvContent.finishRefresh();
    }

    @Override
    public void showError() {

    }

    @Override
    public void complete() {
    }
}

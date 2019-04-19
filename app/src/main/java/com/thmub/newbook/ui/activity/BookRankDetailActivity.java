package com.thmub.newbook.ui.activity;


import android.content.Intent;
import android.os.Bundle;

import com.thmub.newbook.R;
import com.thmub.newbook.base.BaseActivity;
import com.thmub.newbook.base.BaseMVPActivity;
import com.thmub.newbook.bean.type.BookSortListType;
import com.thmub.newbook.bean.zhui.BookBean;
import com.thmub.newbook.bean.zhui.DiscoverRankBean;
import com.thmub.newbook.manager.BookManager;
import com.thmub.newbook.presenter.BookRankDetailPresenter;
import com.thmub.newbook.presenter.contract.BookRankContract;
import com.thmub.newbook.presenter.contract.BookRankDetailContract;
import com.thmub.newbook.ui.adapter.BookBeanAdapter;
import com.thmub.newbook.ui.adapter.TabFragmentPageAdapter;
import com.thmub.newbook.ui.fragment.BookSortDetailFragment;
import com.thmub.newbook.utils.ToastUtils;

import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Zhouas666 on 2019-04-19
 * Github: https://github.com/zas023
 * <p>
 * 书籍排行书籍列表activity
 */
public class BookRankDetailActivity extends BaseMVPActivity<BookRankDetailContract.Presenter>
        implements BookRankDetailContract.View {

    /*****************************Constant********************************/
    public static final String EXTRA_RANK_ID = "extra_rank_id";
    public static final String EXTRA_RANK_TITLE = "extra_rank_title";


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_content)
    RecyclerView rvContent;

    /*****************************Variable********************************/
    private BookBeanAdapter mAdapter;
    private String mRankId;
    private String mRankTitle;

    /*****************************Initialization********************************/
    @Override
    protected int getLayoutId() {
        return R.layout.activity_base_list;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        mRankId = getIntent().getStringExtra(EXTRA_RANK_ID);
        mRankTitle = getIntent().getStringExtra(EXTRA_RANK_TITLE);

    }

    @Override
    protected void setUpToolbar(Toolbar toolbar) {
        super.setUpToolbar(toolbar);
        getSupportActionBar().setTitle(mRankTitle);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mAdapter = new BookBeanAdapter();
        rvContent.setLayoutManager(new LinearLayoutManager(mContext));
        rvContent.setAdapter(mAdapter);
    }

    @Override
    protected void initClick() {
        super.initClick();
        mAdapter.setOnItemClickListener((view, pos) ->
                startActivity(new Intent(mContext, BookDetailActivity.class)
                        .putExtra(BookDetailActivity.EXTRA_BOOK
                                , BookManager.getSearchBook(mAdapter.getItem(pos))))
        );
    }

    @Override
    protected void processLogic() {
        super.processLogic();
        mPresenter.loadRankBooks(mRankId);
    }

    /****************************Transaction*******************************/
    @Override
    protected BookRankDetailContract.Presenter bindPresenter() {
        return new BookRankDetailPresenter();
    }

    @Override
    public void finishLoadRankBooks(List<BookBean> items) {
        mAdapter.addItems(items);
    }

    @Override
    public void showError(Throwable e) {
        ToastUtils.showError(mContext, e.getMessage());
    }

    @Override
    public void complete() {

    }
}

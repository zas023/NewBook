package com.thmub.newbook.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import com.thmub.newbook.R;
import com.thmub.newbook.base.BaseMVPFragment;
import com.thmub.newbook.base.adapter.QuickAdapter;
import com.thmub.newbook.bean.type.BookSortListType;
import com.thmub.newbook.bean.zhui.BookBean;
import com.thmub.newbook.manager.BookManager;
import com.thmub.newbook.presenter.BookSortDetailPresenter;
import com.thmub.newbook.presenter.contract.BookSortDetailContract;
import com.thmub.newbook.ui.activity.BookDetailActivity;
import com.thmub.newbook.ui.adapter.BookBeanAdapter;
import com.thmub.newbook.utils.ToastUtils;
import com.thmub.newbook.widget.refresh.ScrollRefreshRecyclerView;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import butterknife.BindView;

/**
 * Created by Zhouas666 on 2019-04-18
 * Github: https://github.com/zas023
 * <p>
 * 书籍分类书籍列表fragment
 */
public class BookSortDetailFragment extends BaseMVPFragment<BookSortDetailContract.Presenter>
        implements BookSortDetailContract.View {

    /***************************Constant********************************/
    private static final String EXTRA_GENDER = "extra_gender";
    private static final String EXTRA_TYPE = "extra_type";
    private static final String EXTRA_MAJOR = "extra_major";

    @BindView(R.id.rv_content)
    ScrollRefreshRecyclerView rvContent;

    private BookBeanAdapter mAdapter;
    /***************************Variable********************************/
    private String mGender;
    private String mMajor;
    private BookSortListType mType;
    private String mMinor = "";
    private int mStart = 0;
    private int mLimit = 20;

    public static Fragment newInstance(String gender, String major, BookSortListType type) {
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_GENDER, gender);
        bundle.putString(EXTRA_MAJOR, major);
        bundle.putSerializable(EXTRA_TYPE, type);
        Fragment fragment = new BookSortDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(EXTRA_GENDER, mGender);
        outState.putString(EXTRA_MAJOR, mMajor);
        outState.putSerializable(EXTRA_TYPE, mType);
    }

    /******************************Initialization*****************************/
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_refresh_list;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        if (savedInstanceState != null) {
            mGender = savedInstanceState.getString(EXTRA_GENDER);
            mMajor = savedInstanceState.getString(EXTRA_MAJOR);
            mType = (BookSortListType) savedInstanceState.getSerializable(EXTRA_TYPE);
        } else {
            mGender = getArguments().getString(EXTRA_GENDER);
            mMajor = getArguments().getString(EXTRA_MAJOR);
            mType = (BookSortListType) getArguments().getSerializable(EXTRA_TYPE);
        }
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        mAdapter = new BookBeanAdapter(mContext,new QuickAdapter.Options());
        rvContent.setLayoutManager(new LinearLayoutManager(mContext));
        rvContent.setAdapter(mAdapter);
    }

    @Override
    protected void initClick() {
        super.initClick();
        //点击书籍
        mAdapter.setOnItemClickListener((view, pos) ->
                startActivity(new Intent(mContext, BookDetailActivity.class)
                        .putExtra(BookDetailActivity.EXTRA_BOOK
                                , BookManager.getSearchBook(mAdapter.getItem(pos))))
        );
        //加载更多
        mAdapter.setOnLoadMoreListener(() -> {
            mPresenter.loadSortBooks(mGender, mType.getNetName(), mMajor, mMinor, mStart, mLimit);
        });
        //刷新
        rvContent.setOnRefreshListener(() -> {
            rvContent.startRefresh();
            mStart = 0;
            mPresenter.loadSortBooks(mGender, mType.getNetName(), mMajor, mMinor, mStart, mLimit);
        });
    }

    @Override
    protected void processLogic() {
        super.processLogic();
        rvContent.startRefresh();
        mPresenter.loadSortBooks(mGender, mType.getNetName(), mMajor, mMinor, mStart, mLimit);
    }

    /***************************Transaction************************************/
    @Override
    protected BookSortDetailContract.Presenter bindPresenter() {
        return new BookSortDetailPresenter();
    }

    @Override
    public void finishLoadSortBooks(List<BookBean> items) {
        mAdapter.addItems(items);
        mStart += items.size();
    }

    @Override
    public void showError(Throwable e) {
        ToastUtils.showError(mContext, e.getMessage());
    }

    @Override
    public void complete() {
        rvContent.finishRefresh();
    }
}

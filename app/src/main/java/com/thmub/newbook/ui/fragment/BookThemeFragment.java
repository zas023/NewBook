package com.thmub.newbook.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import com.thmub.newbook.R;
import com.thmub.newbook.base.BaseMVPFragment;
import com.thmub.newbook.base.adapter.QuickAdapter;
import com.thmub.newbook.bean.type.BookListType;
import com.thmub.newbook.bean.zhui.ThemeBean;
import com.thmub.newbook.manager.BookManager;
import com.thmub.newbook.presenter.BookThemePresenter;
import com.thmub.newbook.presenter.contract.BookThemeContract;
import com.thmub.newbook.ui.activity.BookDetailActivity;
import com.thmub.newbook.ui.activity.BookThemeDetailActivity;
import com.thmub.newbook.ui.adapter.BookThemeAdapter;
import com.thmub.newbook.utils.ToastUtils;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;


/**
 * Created by Zhouas666 on 2019-04-19
 * Github: https://github.com/zas023
 * <p>
 * 主题书单列表fragment
 */
public class BookThemeFragment extends BaseMVPFragment<BookThemeContract.Presenter>
        implements BookThemeContract.View {

    /***************************Constant********************************/
    private static final String EXTRA_TYPE = "extra_type";

    @BindView(R.id.rv_content)
    RecyclerView rvContent;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipeLayout;

    private BookThemeAdapter mAdapter;
    private BookListType mType;

    private int mStart = 0;
    private int mLimit = 20;

    public static Fragment newInstance(BookListType type) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_TYPE, type);
        Fragment fragment = new BookThemeFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(EXTRA_TYPE, mType);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_refresh_list;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        if (savedInstanceState != null) {
            mType = (BookListType) savedInstanceState.getSerializable(EXTRA_TYPE);
        } else {
            mType = (BookListType) getArguments().getSerializable(EXTRA_TYPE);
        }
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        mAdapter = new BookThemeAdapter(mContext, new QuickAdapter.Options());
        rvContent.setLayoutManager(new LinearLayoutManager(mContext));
        rvContent.setAdapter(mAdapter);
    }

    @Override
    protected void initClick() {
        super.initClick();
        mAdapter.setOnItemClickListener((view, pos) ->
                startActivity(new Intent(mContext, BookThemeDetailActivity.class)
                        .putExtra(BookThemeDetailActivity.EXTRA_THEME_ID, mAdapter.getItem(pos).get_id())
                        .putExtra(BookThemeDetailActivity.EXTRA_THEME_TITLE, mAdapter.getItem(pos).getTitle()))
        );
        //加载更多
        mAdapter.setOnLoadMoreListener(
                () -> mPresenter.loadBookLists(mType.getNetName(), mType.getSortName(), mStart, mLimit, "", "")
        );
        //刷新
        swipeLayout.setOnRefreshListener(() -> {
            mStart = 0;
            mPresenter.loadBookLists(mType.getNetName(), mType.getSortName(), mStart, mLimit, "", "");
        });
    }

    @Override
    protected void processLogic() {
        super.processLogic();
        swipeLayout.setRefreshing(true);
        mPresenter.loadBookLists(mType.getNetName(), mType.getSortName(), mStart, mLimit, "", "");
    }

    /*************************Transaction**********************************/
    @Override
    protected BookThemeContract.Presenter bindPresenter() {
        return new BookThemePresenter();
    }

    @Override
    public void finishLoadBookLists(List<ThemeBean> items) {
        if (mStart == 0) {
            mStart = items.size();
            //refreshItems会设置自动调用一次加载更多，所需需要先赋mStart的值
            mAdapter.refreshItems(items);
        } else {
            mAdapter.addItems(items);
            mStart += items.size();
        }
    }

    @Override
    public void showError(Throwable e) {
        ToastUtils.showError(mContext, e.getMessage());
    }

    @Override
    public void complete() {
        swipeLayout.setRefreshing(false);
    }
}

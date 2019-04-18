package com.thmub.newbook.ui.activity;


import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.thmub.newbook.R;
import com.thmub.newbook.base.BaseMVPActivity;
import com.thmub.newbook.bean.zhui.DiscoverSortBean;
import com.thmub.newbook.presenter.BookSortPresenter;
import com.thmub.newbook.presenter.contract.BookSortContract;
import com.thmub.newbook.ui.adapter.TabFragmentPageAdapter;
import com.thmub.newbook.ui.fragment.BookSortFragment;
import com.thmub.newbook.utils.ToastUtils;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

/**
 * Created by Zhouas666 on 2019-04-18
 * Github: https://github.com/zas023
 * <p>
 * 书籍分类activity
 */
public class BookSortActivity extends BaseMVPActivity<BookSortContract.Presenter> implements BookSortContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab_tl)
    TabLayout tabTl;
    @BindView(R.id.tab_vp)
    ViewPager tabVp;

    private TabFragmentPageAdapter mAdapter;
    private BookSortFragment maleFragment;
    private BookSortFragment femaleFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_base_tab;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mAdapter = new TabFragmentPageAdapter(getSupportFragmentManager());
        maleFragment = new BookSortFragment();
        femaleFragment = new BookSortFragment();
        mAdapter.addFragment(maleFragment, "男生");
        mAdapter.addFragment(femaleFragment, "女生");
    }

    @Override
    protected void setUpToolbar(Toolbar toolbar) {
        super.setUpToolbar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.activity_book_sort));
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        tabVp.setAdapter(mAdapter);
        tabVp.setOffscreenPageLimit(3);
        tabTl.setupWithViewPager(tabVp);
    }

    @Override
    protected void processLogic() {
        super.processLogic();
        mPresenter.loadBookSort();
    }

    /**************************Transaction*****************************/
    @Override
    protected BookSortContract.Presenter bindPresenter() {
        return new BookSortPresenter();
    }

    @Override
    public void finishLoadBookSort(DiscoverSortBean bean) {
        maleFragment.setSortBeans("male",bean.getMale());
        femaleFragment.setSortBeans("female",bean.getFemale());
    }

    @Override
    public void showError(Throwable e) {
        ToastUtils.showError(mContext, e.getMessage());
    }

    @Override
    public void complete() {

    }
}

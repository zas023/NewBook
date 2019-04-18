package com.thmub.newbook.ui.activity;


import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.thmub.newbook.R;
import com.thmub.newbook.base.BaseActivity;
import com.thmub.newbook.base.BaseMVPActivity;
import com.thmub.newbook.bean.type.BookSortListType;
import com.thmub.newbook.bean.zhui.DiscoverSortBean;
import com.thmub.newbook.presenter.BookSortPresenter;
import com.thmub.newbook.presenter.contract.BookSortContract;
import com.thmub.newbook.ui.adapter.TabFragmentPageAdapter;
import com.thmub.newbook.ui.fragment.BookSortDetailFragment;
import com.thmub.newbook.ui.fragment.BookSortFragment;
import com.thmub.newbook.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

/**
 * Created by Zhouas666 on 2019-04-18
 * Github: https://github.com/zas023
 * <p>
 * 书籍分类书籍列表activity
 */
public class BookSortDetailActivity extends BaseActivity {

    /*****************************Constant********************************/
    public static final String EXTRA_GENDER = "extra_gender";
    public static final String EXTRA_SORT = "extra_sort";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab_tl)
    TabLayout tabTl;
    @BindView(R.id.tab_vp)
    ViewPager tabVp;

    private TabFragmentPageAdapter mAdapter;

    private String mGender;
    private String mSort;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_base_tab;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        mGender = getIntent().getStringExtra(EXTRA_GENDER);
        mSort = getIntent().getStringExtra(EXTRA_SORT);

        mAdapter = new TabFragmentPageAdapter(getSupportFragmentManager());
        for (BookSortListType type : BookSortListType.values()) {
            mAdapter.addFragment(BookSortDetailFragment.newInstance(mGender, mSort, type), type.getTypeName());
        }
    }

    @Override
    protected void setUpToolbar(Toolbar toolbar) {
        super.setUpToolbar(toolbar);
        getSupportActionBar().setTitle(mSort);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        tabVp.setAdapter(mAdapter);
        tabVp.setOffscreenPageLimit(4);
        tabTl.setupWithViewPager(tabVp);
    }


}

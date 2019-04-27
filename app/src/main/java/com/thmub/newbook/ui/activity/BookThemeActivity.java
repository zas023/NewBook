package com.thmub.newbook.ui.activity;


import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.thmub.newbook.R;
import com.thmub.newbook.base.BaseActivity;
import com.thmub.newbook.bean.type.BookListType;
import com.thmub.newbook.ui.adapter.TabFragmentPageAdapter;
import com.thmub.newbook.ui.fragment.BookThemeFragment;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

/**
 * Created by Zhouas666 on 2019-04-27
 * Github: https://github.com/zas023
 * <p>
 * 主题书单activity
 */
public class BookThemeActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab_tl)
    TabLayout tabTl;
    @BindView(R.id.tab_vp)
    ViewPager tabVp;

    private TabFragmentPageAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_base_tab;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        mAdapter = new TabFragmentPageAdapter(getSupportFragmentManager());
        for (BookListType type : BookListType.values()) {
            mAdapter.addFragment(BookThemeFragment.newInstance(type), type.getTypeName());
        }
    }

    @Override
    protected void setUpToolbar(Toolbar toolbar) {
        super.setUpToolbar(toolbar);
        getSupportActionBar().setTitle("主题书单");
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        tabVp.setAdapter(mAdapter);
        tabVp.setOffscreenPageLimit(3);
        tabTl.setupWithViewPager(tabVp);
    }
}

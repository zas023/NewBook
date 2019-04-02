package com.thmub.newbook.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.thmub.newbook.R;
import com.thmub.newbook.base.BaseActivity;
import com.thmub.newbook.manager.BookSourceManager;
import com.thmub.newbook.model.repo.BookSourceRepository;
import com.thmub.newbook.ui.adapter.TabFragmentPageAdapter;
import com.thmub.newbook.ui.fragment.BookShelfFragment;
import com.thmub.newbook.ui.fragment.BookStoreFragment;
import com.thmub.newbook.utils.UiUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Zhouas666 on 2019-03-26
 * Github: https://github.com/zas023
 * <p>
 * 主界面activity
 */
public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    /*************************Constant**************************/


    /*****************************View********************************/

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.main_tab_title)
    TabLayout mainTabTitle;
    @BindView(R.id.main_vp_content)
    ViewPager mainVpContent;

    private View drawerHeader;
    private ImageView drawerIv;
    private TextView drawerTvAccount, drawerTvMail;


    /*****************************Variable********************************/

    private TabFragmentPageAdapter tabAdapter;

    /*****************************Initialization********************************/
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected boolean initSwipeBackEnable() {
        return false;
    }

    @Override
    protected void setUpToolbar(Toolbar toolbar) {
        super.setUpToolbar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle(UiUtils.getString(R.string.app_name));
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //drawer.setDrawerListener(toggle);
        //上述方法在androidx中废弃
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        drawerHeader = navigationView.inflateHeaderView(R.layout.app_drawer_header);
        drawerIv =  drawerHeader.findViewById(R.id.drawer_iv);
        drawerTvAccount =  drawerHeader.findViewById(R.id.drawer_tv_name);
        drawerTvMail =  drawerHeader.findViewById(R.id.drawer_tv_mail);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        //第一次进入App初始化数据库
//        if (SharedPreUtils.getInstance().getBoolean("shared_app_first_in", true)) {
//            BookSourceRepository.getInstance()
//                    .saveBookSourceWithAsync(BookSourceManager.getInstance().getBookSourceList());
//            SharedPreUtils.getInstance().putBoolean("shared_app_first_in", false);
//        }
        //如果没有书源，则添加默认书源
        if(BookSourceRepository.getInstance().getAllBookSource().size()==0)
            BookSourceRepository.getInstance()
                    .saveBookSourceWithAsync(BookSourceManager.getInstance().getBookSourceList());

        tabAdapter = new TabFragmentPageAdapter(getSupportFragmentManager());
        tabAdapter.addFragment(new BookShelfFragment(),"书架");
        tabAdapter.addFragment(new BookStoreFragment(),"书城");
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mainVpContent.setAdapter(tabAdapter);
        mainVpContent.setOffscreenPageLimit(3);
        mainTabTitle.setupWithViewPager(mainVpContent);
    }

    @Override
    protected void initClick() {
        super.initClick();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }

    @OnClick({R.id.toolbar})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar:
                startActivity(new Intent(mContext, SearchActivity.class));
                break;
        }
    }


    /*****************************Event********************************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_bar, menu);
        return true;
    }

    /**
     * 导航栏菜单点击事件
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_download:
                startActivity(new Intent(this, SearchActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 侧滑菜单点击事件
     * @param item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_source:
                startActivity(new Intent(this, BookSourceActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

package com.thmub.newbook.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.gyf.barlibrary.ImmersionBar;
import com.thmub.newbook.R;
import com.thmub.newbook.base.BaseMVPActivity;
import com.thmub.newbook.bean.BookChapterBean;
import com.thmub.newbook.bean.ShelfBookBean;
import com.thmub.newbook.manager.ReadBookControl;
import com.thmub.newbook.manager.ReadSettingManager;
import com.thmub.newbook.presenter.ReadPresenter;
import com.thmub.newbook.presenter.contract.ReadContract;
import com.thmub.newbook.ui.adapter.CatalogAdapter;
import com.thmub.newbook.utils.BatteryUtils;
import com.thmub.newbook.utils.SystemBarUtils;
import com.thmub.newbook.utils.UiUtils;
import com.thmub.newbook.widget.page.PageLoader;
import com.thmub.newbook.widget.page.PageView;

import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by Zhouas666 on 2019-03-28
 * Github: https://github.com/zas023
 * <p>
 * 书籍详情activity
 */
public class ReadActivity extends BaseMVPActivity<ReadContract.Presenter>
        implements ReadContract.View {

    /****************************Constant*********************************/
    public static final String EXTRA_BOOK = "extra_book";

    /*****************************View***********************************/
    @BindView(R.id.read_drawer)
    DrawerLayout readDrawer;
    @BindView(R.id.read_rv_catalog)
    RecyclerView readRvCatalog;
    //顶部菜单
    @BindView(R.id.read_tv_brief)
    TextView readTvBrief;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.read_abl_top_menu)
    AppBarLayout readAblTopMenu;
    @BindView(R.id.read_pv_content)
    PageView pageView;
    @BindView(R.id.read_tv_page_tip)
    TextView readTvPageTip;
    @BindView(R.id.read_tv_pre_chapter)
    TextView readTvPreChapter;
    @BindView(R.id.read_sb_chapter_progress)
    SeekBar readSbChapterProgress;
    @BindView(R.id.read_tv_next_chapter)
    TextView readTvNextChapter;
    @BindView(R.id.read_tv_category)
    TextView readTvCategory;
    @BindView(R.id.read_tv_night_mode)
    TextView readTvNightMode;
    @BindView(R.id.read_tv_download)
    TextView readTvDownload;
    @BindView(R.id.read_tv_setting)
    TextView readTvSetting;
    @BindView(R.id.read_ll_bottom_menu)
    LinearLayout readLlBottomMenu;


    /****************************Variable*********************************/
    private ShelfBookBean mShelfBook;

    private PageLoader mPageLoader;
    private Animation mTopInAnim;
    private Animation mTopOutAnim;
    private Animation mBottomInAnim;
    private Animation mBottomOutAnim;

    private ReadBookControl readBookControl = ReadBookControl.getInstance();

    private CatalogAdapter mAdapter;

    private boolean isFullScreen=true;

    /*************************Public Method*******************************/


    /**************************Initialization******************************/
    @Override
    protected int getLayoutId() {
        return R.layout.activity_read;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mShelfBook = getIntent().getParcelableExtra(EXTRA_BOOK);
        mPageLoader = pageView.getPageLoader(this, mShelfBook);
    }

    /**
     * 禁用左滑返回
     *
     * @param enable
     */
    @Override
    public void setSwipeBackEnable(boolean enable) {
        super.setSwipeBackEnable(false);
    }

    @Override
    protected void setUpToolbar(Toolbar toolbar) {
        super.setUpToolbar(toolbar);
//        getActionBar().setTitle(mShelfBook.getTitle());
        ImmersionBar.with(this).init();
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        // Catalog
        mAdapter = new CatalogAdapter();
        readRvCatalog.setLayoutManager(new LinearLayoutManager(mContext));
        readRvCatalog.setAdapter(mAdapter);
        readRvCatalog.setBackground(readBookControl.getTextBackground(this));
        // PageView
        pageView.setBackground(readBookControl.getTextBackground(this));
        mPageLoader.updateBattery(BatteryUtils.getLevel(this));

        // Menu
        initTopMenu();
        initBottomMenu();
    }

    /**
     * 初始化顶部菜单
     */
    private void initTopMenu() {
        if (Build.VERSION.SDK_INT >= 19) {
            readAblTopMenu.setPadding(0, UiUtils.getStatusBarHeight(), 0, 0);
        }
    }

    /**
     * 初始化底部菜单
     */
    private void initBottomMenu() {
        //判断是否全屏
        if (ReadSettingManager.getInstance().isFullScreen()) {
            //还需要设置mBottomMenu的底部高度
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) readLlBottomMenu.getLayoutParams();
            params.bottomMargin = UiUtils.getNavigationBarHeight();
            readLlBottomMenu.setLayoutParams(params);
        } else {
            //设置mBottomMenu的底部距离
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) readLlBottomMenu.getLayoutParams();
            params.bottomMargin = 0;
            readLlBottomMenu.setLayoutParams(params);
        }
    }


    @Override
    protected void initClick() {
        super.initClick();
        mPageLoader.setOnPageChangeListener(new PageLoader.OnPageChangeListener() {
            @Override
            public void onChapterChange(int pos) {
                mAdapter.setChapter(pos);
            }

            @Override
            public void onCategoryFinish(List<BookChapterBean> chapters) {
                mAdapter.addItems(chapters);
            }

            @Override
            public void onPageCountChange(int count) {

            }

            @Override
            public void onPageChange(int chapterIndex, int pageIndex, boolean resetReadAloud) {

            }
        });

        pageView.setTouchListener(new PageView.TouchListener() {
            @Override
            public boolean onTouch() {
                return !hideReadMenu();
            }

            @Override
            public void center() {
                toggleMenu(true);
            }
        });

    }

    /**
     * 隐藏阅读界面的菜单显示
     *
     * @return 是否隐藏成功
     */
    private boolean hideReadMenu() {
        hideSystemBar();
        if (readAblTopMenu.getVisibility() == VISIBLE) {
            toggleMenu(true);
            return true;
        }
//        else if (mSettingDialog.isShowing()) {
//            mSettingDialog.dismiss();
//            return true;
//        }
        return false;
    }

    /**
     * 切换菜单栏的可视状态
     * 默认是隐藏的
     */
    private void toggleMenu(boolean hideStatusBar) {

        initMenuAnim();

        if (readAblTopMenu.getVisibility() == View.VISIBLE) {
            //关闭
            readAblTopMenu.startAnimation(mTopOutAnim);
            readLlBottomMenu.startAnimation(mBottomOutAnim);
            readAblTopMenu.setVisibility(GONE);
            readLlBottomMenu.setVisibility(GONE);
            readTvPageTip.setVisibility(GONE);

            if (hideStatusBar) {
                hideSystemBar();
            }
        } else {
            readAblTopMenu.setVisibility(View.VISIBLE);
            readLlBottomMenu.setVisibility(View.VISIBLE);
            readAblTopMenu.startAnimation(mTopInAnim);
            readLlBottomMenu.startAnimation(mBottomInAnim);

            showSystemBar();
        }
    }

    //初始化菜单动画
    private void initMenuAnim() {
        if (mTopInAnim != null) return;

        mTopInAnim = AnimationUtils.loadAnimation(mContext, R.anim.slide_top_in);
        mTopOutAnim = AnimationUtils.loadAnimation(mContext, R.anim.slide_top_out);
        mBottomInAnim = AnimationUtils.loadAnimation(mContext, R.anim.slide_bottom_in);
        mBottomOutAnim = AnimationUtils.loadAnimation(mContext, R.anim.slide_bottom_out);
    }


    private void showSystemBar() {
        //显示
        SystemBarUtils.showUnStableStatusBar(this);
        if (isFullScreen) {
            SystemBarUtils.showUnStableNavBar(this);
        }
    }

    private void hideSystemBar() {
        //隐藏
        SystemBarUtils.hideStableStatusBar(this);
        if (isFullScreen) {
            SystemBarUtils.hideStableNavBar(this);
        }
    }


    @Override
    protected void processLogic() {
        super.processLogic();
        mPageLoader.refreshChapterList();
    }


    /**************************Transaction********************************/
    @Override
    protected ReadContract.Presenter bindPresenter() {
        return new ReadPresenter();
    }

    @Override
    public void finishLoadContent(String content) {
    }

    @Override
    public void showError() {

    }

    @Override
    public void complete() {

    }
}

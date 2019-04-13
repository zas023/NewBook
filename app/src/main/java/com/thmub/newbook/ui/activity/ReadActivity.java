package com.thmub.newbook.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
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
import com.thmub.newbook.constant.Constant;
import com.thmub.newbook.manager.ReadSettingManager;
import com.thmub.newbook.model.local.BookShelfRepository;
import com.thmub.newbook.presenter.ReadPresenter;
import com.thmub.newbook.presenter.contract.ReadContract;
import com.thmub.newbook.ui.adapter.CatalogAdapter;
import com.thmub.newbook.ui.dialog.SourceExchangeDialog;
import com.thmub.newbook.ui.dialog.ReadSettingDialog;
import com.thmub.newbook.utils.BatteryUtils;
import com.thmub.newbook.utils.StringUtils;
import com.thmub.newbook.utils.SystemBarUtils;
import com.thmub.newbook.utils.UiUtils;
import com.thmub.newbook.widget.animation.PageAnimation;
import com.thmub.newbook.widget.page.PageLoader;
import com.thmub.newbook.widget.page.PageView;
import com.thmub.newbook.widget.page.TxtChapter;

import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

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
    public static final String EXTRA_IS_COLLECTED = "extra_is_collected";

    /*****************************View***********************************/
    @BindView(R.id.read_drawer)
    DrawerLayout readDrawer;
    @BindView(R.id.read_rv_catalog)
    RecyclerView readRvCatalog;
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


    private ReadSettingDialog mSettingDialog;
    private SourceExchangeDialog mSourceDialog;
    /****************************Variable*********************************/
    private ShelfBookBean mShelfBook;

    private PageLoader mPageLoader;
    private Animation mTopInAnim;
    private Animation mTopOutAnim;
    private Animation mBottomInAnim;
    private Animation mBottomOutAnim;

    private ReadSettingManager readSettingManager = ReadSettingManager.getInstance();

    private CatalogAdapter mAdapter;

    private boolean isFullScreen = true;
    private boolean isCollected;

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
        isCollected = getIntent().getBooleanExtra(EXTRA_IS_COLLECTED, false);
        mPageLoader = pageView.getPageLoader(this, mShelfBook);
        //重置书籍更新提示状态
        mShelfBook.setIsUpdate(false);
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
        getSupportActionBar().setTitle(mShelfBook.getTitle());
        ImmersionBar.with(this).init();
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        // Catalog
        mAdapter = new CatalogAdapter(mShelfBook);
        readRvCatalog.setLayoutManager(new LinearLayoutManager(mContext));
        readRvCatalog.getLayoutManager().scrollToPosition(mShelfBook.getCurChapter());
        readRvCatalog.setAdapter(mAdapter);
        readRvCatalog.setBackground(readSettingManager.getTextBackground(this));

        // PageView
        pageView.setBackground(readSettingManager.getTextBackground(this));
        mPageLoader.updateBattery(BatteryUtils.getLevel(this));

        //Dialog
        mSettingDialog = new ReadSettingDialog(this);
        mSourceDialog = new SourceExchangeDialog(this, mShelfBook);
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
        if (ReadSettingManager.getInstance().getHideStatusBar()) {
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
        //pageLoader
        mPageLoader.setOnPageChangeListener(new PageLoader.OnPageChangeListener() {
            @Override
            public void onChapterChange(int pos) {
                mAdapter.setSelectedChapter(pos);
                readRvCatalog.getLayoutManager().scrollToPosition(pos);
            }

            @Override
            public void onCategoryFinish(List<BookChapterBean> chapters) {
                mShelfBook.setBookChapterList(chapters);
            }

            @Override
            public void onPageCountChange(int count) {
            }

            @Override
            public void onPageChange(int chapterIndex, int pageIndex, boolean resetReadAloud) {
                mShelfBook.setCurChapter(chapterIndex);
                mShelfBook.setCurChapterPage(pageIndex);
            }
        });
        //pageView
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

        //catalog
        mAdapter.setListener((index, page) -> mPageLoader.skipToChapter(index, 0));

        //监听底部设置菜单
        mSettingDialog.setOnDismissListener(
                dialog -> hideSystemBar()
        );

        //dialog
        //底部设置菜单
        mSettingDialog.setOnChangeListener(new ReadSettingDialog.OnSettingChangeListener() {
            @Override
            public void onPageModeChange() {
                if (mPageLoader != null) {
                    mPageLoader.setPageMode(PageAnimation.Mode.getPageMode(readSettingManager.getPageMode()));
                }
            }

            @Override
            public void OnTextSizeChange() {
                if (mPageLoader != null) {
                    mPageLoader.setTextSize();
                }
            }

            @Override
            public void OnMarginChange() {

            }


            @Override
            public void onBgChange() {
                readSettingManager.initTextDrawableIndex();
                pageView.setBackground(readSettingManager.getTextBackground(mContext));
                if (mPageLoader != null) {
                    mPageLoader.refreshUi();
                }
            }

            @Override
            public void refresh() {
                if (mPageLoader != null) {
                    mPageLoader.refreshUi();
                }
            }
        });
        //换源
        mSourceDialog.setListener(bean -> {
            mPageLoader.setStatus(TxtChapter.Status.CHANGE_SOURCE);
            //交换数据
            ShelfBookBean oldBook = mShelfBook;
            mShelfBook = bean.getShelfBook();
            mShelfBook.setCurChapter(oldBook.getCurChapter());
            mShelfBook.setCurChapterPage(oldBook.getCurChapterPage());

            BookShelfRepository.getInstance().deleteShelfBook(oldBook);
            BookShelfRepository.getInstance().saveShelfBook(mShelfBook);
            //更新目录
            mPresenter.loadCatalogs(mShelfBook, true);
            mSourceDialog.dismiss();
        });
    }

    @Override
    protected void processLogic() {
        super.processLogic();
        //加载目录
        mPageLoader.refreshChapterList();
    }

    /**************************Transaction********************************/
    @Override
    protected ReadContract.Presenter bindPresenter() {
        return new ReadPresenter();
    }

    @Override
    public void finishLoadCatalogs(List<BookChapterBean> items, boolean fromNet) {
        mShelfBook.setBookChapterList(items);
        if (fromNet) {
            mPageLoader.changeSourceFinish(mShelfBook);
        } else {
            mPageLoader.skipToChapter(mShelfBook.getCurChapter(), mShelfBook.getCurChapterPage());
        }
    }

    @Override
    public void showError() {

    }

    @Override
    public void complete() {

    }

    /*********************************Event*************************************/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_read, menu);
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
            case android.R.id.home:    //左侧返回键
                finish();
            case R.id.action_change_source:  //换源
                mSourceDialog.show();
                break;
            case R.id.action_refresh_chapter:  //刷新章节
                mPageLoader.refreshCurChapter();
                break;
            case R.id.action_add_bookmark:  //添加书签
                break;
            case R.id.action_copy_content:  //复制内容
                break;
            case R.id.action_refresh_catalog:  //刷新目录
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 监听按键
     * 退出前检提示，以便保存记录
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //if (event.getRepeatCount() > 0) return false;
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (readAblTopMenu.getVisibility() == View.VISIBLE) {
                    //非全屏下才收缩，全屏下直接退出
                    toggleMenu(true);
                    return true;
                } else if (mSettingDialog.isShowing()) {
                    mSettingDialog.dismiss();
                    return true;
                } else if (readDrawer.isDrawerOpen(GravityCompat.START)) {
                    readDrawer.closeDrawer(GravityCompat.START);
                    return true;
                }
                if (!isCollected) {
                    new AlertDialog.Builder(this)
                            .setTitle("加入书架")
                            .setMessage("喜欢本书就加入书架吧")
                            .setPositiveButton("确定", (dialog, which) -> {
                                //设置为已收藏
                                isCollected = true;
                                finish();
                            })
                            .setNegativeButton("取消", (dialog, which) -> finish())
                            .create().show();
                    return true;
                } else {
                    finish();
                    return true;
                }
            case KeyEvent.KEYCODE_VOLUME_UP:  //音量键
                mPageLoader.skipToPrePage();
                break;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                mPageLoader.skipToNextPage();
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick({R.id.read_tv_category, R.id.read_tv_setting, R.id.read_tv_pre_chapter
            , R.id.read_tv_next_chapter, R.id.read_tv_night_mode, R.id.read_tv_download})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.read_tv_category: //目录
                //移动到指定位置

                //切换菜单
                toggleMenu(true);
                //打开侧滑动栏
                readDrawer.openDrawer(GravityCompat.START);
                break;
            case R.id.read_tv_download:  //下载
                break;
            case R.id.read_tv_setting:  //设置
                toggleMenu(false);
                mSettingDialog.show();
                break;
            case R.id.read_tv_pre_chapter:  //前一章
                mPageLoader.skipToPreChapter();
                break;
            case R.id.read_tv_next_chapter:  //后一章
                mPageLoader.skipToNextChapter();
                break;
            case R.id.read_tv_night_mode:  //夜间模式

                break;
        }
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
        } else if (mSettingDialog.isShowing()) {
            mSettingDialog.dismiss();
            return true;
        }
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


    /******************************Life Cycle********************************/
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPageLoader != null) {
            mPageLoader.closeBook();
            mPageLoader = null;
        }
    }

    /**
     * 结束
     */
    @Override
    public void finish() {
        if (isCollected) {
            //设置阅读时间
            mShelfBook.setLastRead(StringUtils.
                    dateConvert(System.currentTimeMillis(), Constant.FORMAT_BOOK_DATE));
            //阅读章节名称
            mShelfBook.setCurChapterTitle(mShelfBook.getChapter(mShelfBook.getCurChapter()).getChapterTitle());
            BookShelfRepository.getInstance().saveShelfBook(mShelfBook);
        }
        //返回给BookDetail。
        setResult(Activity.RESULT_OK, new Intent().putExtra(BookDetailActivity.RESULT_IS_COLLECTED, isCollected));
        super.finish();
    }

}

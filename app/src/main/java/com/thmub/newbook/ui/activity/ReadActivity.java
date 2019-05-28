package com.thmub.newbook.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.gyf.barlibrary.ImmersionBar;
import com.thmub.newbook.R;
import com.thmub.newbook.base.BaseMVPActivity;
import com.thmub.newbook.bean.BookDetailBean;
import com.thmub.newbook.bean.BookMarkBean;
import com.thmub.newbook.bean.BookSearchBean;
import com.thmub.newbook.bean.DownloadBookBean;
import com.thmub.newbook.bean.ShelfBookBean;
import com.thmub.newbook.bean.event.ChapterExchangeEvent;
import com.thmub.newbook.bean.event.DownloadEvent;
import com.thmub.newbook.manager.ReadSettingManager;
import com.thmub.newbook.manager.RxBusManager;
import com.thmub.newbook.model.local.BookMarkRepository;
import com.thmub.newbook.model.local.BookShelfRepository;
import com.thmub.newbook.presenter.ReadPresenter;
import com.thmub.newbook.presenter.contract.ReadContract;
import com.thmub.newbook.service.BookDownloadService;
import com.thmub.newbook.ui.dialog.CopyContentDialog;
import com.thmub.newbook.ui.dialog.ReadSettingDialog;
import com.thmub.newbook.ui.dialog.SourceExchangeDialog;
import com.thmub.newbook.utils.BatteryUtils;
import com.thmub.newbook.utils.NetworkUtils;
import com.thmub.newbook.utils.SystemBarUtils;
import com.thmub.newbook.utils.ToastUtils;
import com.thmub.newbook.utils.UiUtils;
import com.thmub.newbook.widget.animation.PageAnimation;
import com.thmub.newbook.widget.page.PageLoader;
import com.thmub.newbook.widget.page.PageView;
import com.thmub.newbook.widget.page.TxtChapter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;

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

    private boolean isFullScreen = true;

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
        //设置正在阅读
        mShelfBook.setReading(true);
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

            }

            @Override
            public void onPageCountChange(int count) {
            }

            @Override
            public void onPageChange(int chapterIndex, int pageIndex, boolean resetReadAloud) {

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
                if (mPageLoader != null) {
                    mPageLoader.refreshUi();
                }
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
        mSourceDialog.setOnSourceChangeListener(bean -> {
            mPageLoader.setStatus(TxtChapter.Status.CHANGE_SOURCE);
            //加载书籍
            mPresenter.loadDetailBook(bean);
            mSourceDialog.dismiss();
        });
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        //处理从目录页面传回的消息
        addDisposable(RxBusManager.getInstance()
                .toObservable(ChapterExchangeEvent.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(event -> mPageLoader.skipToChapter(event.getChapterIndex(), event.getPageIndex())));
        //处理下载消息
        addDisposable(RxBusManager.getInstance()
                .toObservable(DownloadEvent.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(event -> ToastUtils.show(mContext,event.message)));
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
    public void finishLoadDetailBook(BookDetailBean book) {
        //交换数据
        ShelfBookBean oldBook = mShelfBook;
        mShelfBook = book.getShelfBook();
        mShelfBook.setCurChapter(oldBook.getCurChapter());
        mShelfBook.setCurChapterPage(oldBook.getCurChapterPage());

        BookShelfRepository.getInstance().deleteShelfBook(oldBook);
        BookShelfRepository.getInstance().saveShelfBook(mShelfBook);

        //换源
        mPageLoader.changeSourceFinish(mShelfBook);
        mSourceDialog.dismiss();
    }

    @Override
    public void showError(Throwable e) {

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
                BookMarkBean bean=new BookMarkBean();
                bean.setBookLink(mShelfBook.getLink());
                bean.setBookTitle(mShelfBook.getTitle());
                bean.setChapterIndex(mShelfBook.getCurChapter());
                bean.setChapterLink(mShelfBook.getChapter(mShelfBook.getCurChapter()).getChapterLink());
                bean.setChapterTitle(mShelfBook.getChapter(mShelfBook.getCurChapter()).getChapterTitle());
                bean.setChapterPage(mShelfBook.getCurChapterPage());
                bean.setContent(mPageLoader.getContent());
                BookMarkRepository.getInstance().saveBookMark(bean);
                ToastUtils.showSuccess(mContext,"已成功添加书签");
                break;
            case R.id.action_copy_content:  //复制内容
                new CopyContentDialog(this, mPageLoader.getContent()).show();
                break;
            case R.id.action_refresh_catalog:  //刷新目录
                mPageLoader.refreshChapterList();
                break;
            case R.id.action_open_link:  //打开链接
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse(mShelfBook.getChapter(mShelfBook.getCurChapter()).getChapterLink())));
                break;
            case R.id.action_open_detail:  //查看详情
                startActivity(new Intent(mContext, BookDetailActivity.class)
                        .putExtra(BookDetailActivity.EXTRA_BOOK, new BookSearchBean(mShelfBook)));
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
                }
                if (!mShelfBook.isCollected()) {
                    new AlertDialog.Builder(this)
                            .setTitle("加入书架")
                            .setMessage("喜欢本书就加入书架吧")
                            .setPositiveButton("确定", (dialog, which) -> {
                                //设置为已收藏=
                                mShelfBook.setCollected(true);
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

    @OnClick({R.id.read_tv_setting, R.id.read_tv_pre_chapter
            , R.id.read_tv_next_chapter, R.id.read_tv_night_mode})
    protected void onClick(View view) {
        switch (view.getId()) {
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
                setNightTheme(!isNightTheme());
                mPageLoader.refreshUi();
                break;
        }
    }

    /**
     * 跳转到目录
     */
    @OnClick(R.id.read_tv_category)
    protected void goToCatalog() {
        //切换菜单
        toggleMenu(true);
        //跳转
        //ShelfBookBean book=mShelfBook;
        //book.setBookChapterList(new ArrayList<>());
        //注意章节太大，不适合使用序列化传递
        startActivity(new Intent(mContext, CatalogActivity.class)
                .putExtra(CatalogActivity.EXTRA_BOOK, new ShelfBookBean(mShelfBook)));
    }

    private int selectedIndex;

    /**
     * 下载
     */
    @OnClick(R.id.read_tv_download)
    protected void downloadBook() {
        if (!NetworkUtils.isNetWorkAvailable()) {
            Toast.makeText(mContext, "无网络连接", Toast.LENGTH_SHORT).show();
            return;
        }

        new AlertDialog.Builder(this)
                .setTitle("下载")
                .setSingleChoiceItems(R.array.dialog_download, selectedIndex, (dialog, which) -> {
                    selectedIndex = which;
                })
                .setNegativeButton("取消", ((dialog, which) -> dialog.dismiss()))
                .setPositiveButton("确定",
                        (dialog, which) -> {
                            switch (selectedIndex) {
                                case 0:
                                    addDownload(mShelfBook.getCurChapter(), mShelfBook.getCurChapter() + 50);
                                    break;
                                case 1:
                                    addDownload(mShelfBook.getCurChapter() - 50, mShelfBook.getCurChapter() + 50);
                                    break;
                                case 2:
                                    addDownload(mShelfBook.getCurChapter(), mShelfBook.getBookChapterListSize());
                                    break;
                                case 3:
                                    addDownload(0, mShelfBook.getBookChapterListSize());
                                    break;
                            }
                        }).show();
    }

    /**
     * 添加下载任务
     * @param start
     * @param end
     */
    private void addDownload(int start, int end) {

        //先加入书架
        if(!mShelfBook.isCollected()){
            mShelfBook.setCollected(true);
            mPageLoader.saveRecord();
        }

        //计算断点章节
        start = Math.max(0, start);
        end = Math.min(end, mShelfBook.getBookChapterListSize());

        DownloadBookBean downloadBook = new DownloadBookBean();
        downloadBook.setName(mShelfBook.getTitle());
        downloadBook.setBookLink(mShelfBook.getLink());
        downloadBook.setCoverLink(mShelfBook.getCover());
        downloadBook.setStart(start);
        downloadBook.setEnd(end);
        downloadBook.setFinalDate(System.currentTimeMillis());
//        DownloadService.addDownload(mContext, downloadBook);
        BookDownloadService.post(downloadBook);
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

    @Override
    protected void onPause() {
        super.onPause();
        //保存记录
        mPageLoader.saveRecord();
    }

    /**
     * 结束
     */
    @Override
    public void finish() {
        //返回给BookDetail。
        setResult(Activity.RESULT_OK, new Intent().putExtra(BookDetailActivity.RESULT_IS_COLLECTED, mShelfBook.isCollected()));
        super.finish();
    }
}

package com.thmub.newbook.base;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;
import com.gyf.barlibrary.ImmersionBar;
import com.thmub.newbook.R;
import com.thmub.newbook.manager.ReadSettingManager;
import com.thmub.newbook.utils.SharedPreUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * Created by PC on 2016/9/8.
 * 继承自SwipeBackActivity,默认开启左滑手势
 */
public abstract class BaseActivity extends SwipeBackActivity {
    protected static String TAG;

    protected Activity mContext;

    //RxJava
    protected CompositeDisposable mDisposable;

    //ButterKnife
    protected Unbinder unbinder;

    protected Toolbar mToolbar;

    /****************************Abstract area*************************************/

    @LayoutRes
    protected abstract int getLayoutId();


    /************************Initialization************************************/
    protected void addDisposable(Disposable d) {
        if (mDisposable == null) {
            mDisposable = new CompositeDisposable();
        }
        mDisposable.add(d);
    }

    /**
     * 配置Toolbar
     */
    protected void setUpToolbar(Toolbar toolbar) {
    }

    /**
     * 初始化数据
     *
     * @param savedInstanceState
     */
    protected void initData(Bundle savedInstanceState) {
    }

    /**
     * 初始化零件
     */
    protected void initWidget() {
    }

    /**
     * 初始化事件
     */
    protected void initEvent() {
    }

    /**
     * 初始化点击事件
     */
    protected void initClick() {
    }

    /**
     * 逻辑使用区
     */
    protected void processLogic() {
    }

    /**
     * 是否开启左滑手势
     *
     * @return
     */
    protected boolean initSwipeBackEnable() {
        return true;
    }

    /**
     * @return 是否夜间模式
     */
    protected boolean isNightTheme() {
        return SharedPreUtils.getInstance().getBoolean(getString(R.string.pref_night_model),false);
    }

    /**
     * 设置夜间模式
     * @param isNightMode
     */
    protected void setNightTheme(boolean isNightMode) {
        SharedPreUtils.getInstance().putBoolean(getString(R.string.pref_night_model), isNightMode);
        initTheme();
    }

    /*************************Lifecycle*****************************************************/

    /**
     * 初始化主题
     */
    public void initTheme() {
        if (isNightTheme()) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTheme();
        setContentView(getLayoutId());
        mContext = this;
        //设置 TAG
        TAG = this.getClass().getSimpleName();
        //注册ButterKnife注解
        unbinder = ButterKnife.bind(this);
        //左滑手势
        setSwipeBackEnable(initSwipeBackEnable());
        //状态栏
//        ImmersionBar.with(this)
//                .statusBarColor(R.color.colorPrimary)
//                .autoStatusBarDarkModeEnable(true,0.2f) //自动状态栏字体变色
//                .fitsSystemWindows(true) //解决状态栏和布局重叠问题
//                .init();
        //init
        initData(savedInstanceState);
        initToolbar();
        initWidget();
        initEvent();
        initClick();
        processLogic();
    }

    private void initToolbar() {
        //更严谨是通过反射判断是否存在Toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            supportActionBar(mToolbar);
            setUpToolbar(mToolbar);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }

    /**************************Used method*******************************************/

    protected void startActivity(Class<? extends AppCompatActivity> activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

    protected ActionBar supportActionBar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
        mToolbar.setNavigationOnClickListener(
                (v) -> finish()
        );
        return actionBar;
    }
}

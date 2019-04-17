package com.thmub.newbook.ui.activity;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.thmub.newbook.R;
import com.thmub.newbook.base.BaseActivity;
import com.thmub.newbook.ui.fragment.SettingFragment;
import com.thmub.newbook.utils.UiUtils;

import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;

/**
 * Created by Zhouas666 on 2019-04-13
 * Github: https://github.com/zas023
 * <p>
 * 设置activity
 */
public class SettingActivity extends BaseActivity {

    public static final String EXTRA_SETTING_TYPE = "extra_setting_type";
    public static final int SETTING_TYPE_APP = 0;
    public static final int SETTING_TYPE_READ = 1;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pref_container)
    FrameLayout prefContainer;

    private int type;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        type = getIntent().getIntExtra(EXTRA_SETTING_TYPE, SETTING_TYPE_APP);
    }

    @Override
    protected void setUpToolbar(Toolbar toolbar) {
        super.setUpToolbar(toolbar);
        if (type == SETTING_TYPE_APP)
            getSupportActionBar().setTitle(UiUtils.getString(R.string.fragment_setting));
        else
            getSupportActionBar().setTitle(UiUtils.getString(R.string.fragment_read_setting));
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.pref_container, new SettingFragment(), "settings")
                .commit();
    }
}

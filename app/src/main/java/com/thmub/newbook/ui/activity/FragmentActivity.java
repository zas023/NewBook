package com.thmub.newbook.ui.activity;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.thmub.newbook.R;
import com.thmub.newbook.base.BaseActivity;
import com.thmub.newbook.base.BaseFragment;
import com.thmub.newbook.ui.fragment.SettingFragment;
import com.thmub.newbook.ui.fragment.UserInfoFragment;
import com.thmub.newbook.utils.UiUtils;

import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;

/**
 * Created by Zhouas666 on 2019-04-13
 * Github: https://github.com/zas023
 * <p>
 * 嵌套Fragment的通用activity
 */
public class FragmentActivity extends BaseActivity {

    public static final String EXTRA_FRAGMENT_TYPE = "extra_fragment_type";
    public static final int FRAGMENT_TYPE_SETTING = 0;
    public static final int FRAGMENT_TYPE_USER_INFO = 1;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.container)
    FrameLayout container;

    private int type;
    private BaseFragment fragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        type = getIntent().getIntExtra(EXTRA_FRAGMENT_TYPE, FRAGMENT_TYPE_SETTING);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        switch (type) {
            case FRAGMENT_TYPE_SETTING:
                getSupportActionBar().setTitle(UiUtils.getString(R.string.fragment_setting));
                fragment = new SettingFragment();
                break;
            case FRAGMENT_TYPE_USER_INFO:
                getSupportActionBar().setTitle(UiUtils.getString(R.string.fragment_user_info));
                fragment = new UserInfoFragment();
                break;
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }
}

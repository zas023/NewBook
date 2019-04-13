package com.thmub.newbook.ui.activity;

import com.thmub.newbook.R;
import com.thmub.newbook.base.BaseActivity;
import com.thmub.newbook.utils.UiUtils;

import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;

/**
 * Created by Zhouas666 on 2019-04-13
 * Github: https://github.com/zas023
 * <p>
 * 替换管理activity
 */
public class ReplacementActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_replace;
    }

    @Override
    protected void setUpToolbar(Toolbar toolbar) {
        super.setUpToolbar(toolbar);
        getSupportActionBar().setTitle(UiUtils.getString(R.string.activity_replace));
    }

}

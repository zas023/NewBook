package com.thmub.newbook.ui.activity;

import com.thmub.newbook.R;
import com.thmub.newbook.base.BaseActivity;
import com.thmub.newbook.utils.UiUtils;
import com.thmub.newbook.widget.refresh.ScrollRefreshRecyclerView;

import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;

/**
 * Created by Zhouas666 on 2019-04-13
 * Github: https://github.com/zas023
 * <p>
 * 下载管理activity
 */
public class DownloadActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.download_rv_content)
    ScrollRefreshRecyclerView downloadRvContent;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_download;
    }

    @Override
    protected void setUpToolbar(Toolbar toolbar) {
        super.setUpToolbar(toolbar);
        getSupportActionBar().setTitle(UiUtils.getString(R.string.activity_download));
    }

}

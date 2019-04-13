package com.thmub.newbook.ui.activity;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.thmub.newbook.R;
import com.thmub.newbook.base.BaseActivity;
import com.thmub.newbook.utils.ShareUtils;
import com.thmub.newbook.utils.UiUtils;

import androidx.appcompat.widget.Toolbar;

/**
 * Created by Zhouas666 on 2019-04-12
 * Github: https://github.com/zas023
 * <p>
 * 关于页面activity
 */
public class AboutActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected void setUpToolbar(Toolbar toolbar) {
        super.setUpToolbar(toolbar);
        getSupportActionBar().setTitle(UiUtils.getString(R.string.activity_about));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_about, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                ShareUtils.share(this, R.string.share_text);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

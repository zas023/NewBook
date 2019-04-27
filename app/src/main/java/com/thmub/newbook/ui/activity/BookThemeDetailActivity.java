package com.thmub.newbook.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.thmub.newbook.R;
import com.thmub.newbook.base.BaseMVPActivity;
import com.thmub.newbook.bean.zhui.ThemeBookBean;
import com.thmub.newbook.manager.BookManager;
import com.thmub.newbook.presenter.BookThemeDetailPresenter;
import com.thmub.newbook.presenter.contract.BookThemeDetailContract;
import com.thmub.newbook.ui.adapter.ThemeBookAdapter;
import com.thmub.newbook.utils.ToastUtils;

import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * Created by Zhouas666 on 2019-04-27
 * Github: https://github.com/zas023
 * <p>
 * 主题书单书籍列表activity
 */
public class BookThemeDetailActivity extends BaseMVPActivity<BookThemeDetailContract.Presenter>
        implements BookThemeDetailContract.View {

    /*****************************Constant********************************/
    public static final String EXTRA_THEME_ID = "extra_theme_id";
    public static final String EXTRA_THEME_TITLE = "extra_theme_title";


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_content)
    RecyclerView rvContent;

    /*****************************Variable********************************/
    private ThemeBookAdapter mAdapter;
    private String mThemeId;
    private String mThemeTitle;

    /*****************************Initialization********************************/
    @Override
    protected int getLayoutId() {
        return R.layout.activity_base_list;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        mThemeId = getIntent().getStringExtra(EXTRA_THEME_ID);
        mThemeTitle = getIntent().getStringExtra(EXTRA_THEME_TITLE);

    }

    @Override
    protected void setUpToolbar(Toolbar toolbar) {
        super.setUpToolbar(toolbar);
        getSupportActionBar().setTitle(mThemeTitle);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mAdapter = new ThemeBookAdapter();
        rvContent.setLayoutManager(new LinearLayoutManager(mContext));
        rvContent.setAdapter(mAdapter);
    }

    @Override
    protected void initClick() {
        super.initClick();
        mAdapter.setOnItemClickListener((view, pos) ->
                startActivity(new Intent(mContext, BookDetailActivity.class)
                        .putExtra(BookDetailActivity.EXTRA_BOOK
                                , BookManager.getSearchBook(mAdapter.getItem(pos).getBook())))
        );
    }

    @Override
    protected void processLogic() {
        super.processLogic();
        mPresenter.loadThemeBooks(mThemeId);
    }

    /****************************Transaction*******************************/

    @Override
    protected BookThemeDetailContract.Presenter bindPresenter() {
        return new BookThemeDetailPresenter();
    }

    @Override
    public void finishLoadThemeBooks(List<ThemeBookBean> items) {
        mAdapter.addItems(items);
    }

    @Override
    public void showError(Throwable e) {
        ToastUtils.showError(mContext,e.getMessage());
    }

    @Override
    public void complete() {

    }

}

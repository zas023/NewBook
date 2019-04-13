package com.thmub.newbook.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.textfield.TextInputLayout;
import com.thmub.newbook.R;
import com.thmub.newbook.base.BaseMVPActivity;
import com.thmub.newbook.bean.BookSourceBean;
import com.thmub.newbook.presenter.SourceEditPresenter;
import com.thmub.newbook.presenter.contract.SourceEditContract;
import com.thmub.newbook.utils.SnackbarUtils;

import org.apache.commons.lang3.StringUtils;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;

import static android.text.TextUtils.isEmpty;

/**
 * Created by Zhouas666 on 2019-03-30
 * Github: https://github.com/zas023
 * <p>
 * 书源编辑activity
 */
public class SourceEditActivity extends BaseMVPActivity<SourceEditContract.Presenter>
        implements SourceEditContract.View {

    /****************************Constant*********************************/
    public static final String EXTRA_BOOK_SOURCE = "extra_book_source";

    /*****************************View***********************************/
    @BindView(R.id.source_et_rootLink)
    AppCompatEditText sourceEtRootLink;
    @BindView(R.id.source_til_rootLink)
    TextInputLayout sourceTilRootLink;
    @BindView(R.id.source_et_sourceName)
    AppCompatEditText sourceEtSourceName;
    @BindView(R.id.source_til_sourceName)
    TextInputLayout sourceTilSourceName;
    @BindView(R.id.source_et_encodeType)
    AppCompatEditText sourceEtEncodeType;
    @BindView(R.id.source_tile_encodeType)
    TextInputLayout sourceTileEncodeType;
    @BindView(R.id.source_et_searchLink)
    AppCompatEditText sourceEtSearchLink;
    @BindView(R.id.source_tile_searchLink)
    TextInputLayout sourceTileSearchLink;
    @BindView(R.id.source_et_ruleSearchTitle)
    AppCompatEditText sourceEtRuleSearchTitle;
    @BindView(R.id.source_tile_ruleSearchTitle)
    TextInputLayout sourceTileRuleSearchTitle;
    @BindView(R.id.source_et_ruleSearchAuthor)
    AppCompatEditText sourceEtRuleSearchAuthor;
    @BindView(R.id.source_tile_ruleSearchAuthor)
    TextInputLayout sourceTileRuleSearchAuthor;
    @BindView(R.id.source_et_ruleSearchDesc)
    AppCompatEditText sourceEtRuleSearchDesc;
    @BindView(R.id.source_tile_ruleSearchDesc)
    TextInputLayout sourceTileRuleSearchDesc;
    @BindView(R.id.source_et_ruleSearchCover)
    AppCompatEditText sourceEtRuleSearchCover;
    @BindView(R.id.source_tile_ruleSearchCover)
    TextInputLayout sourceTileRuleSearchCover;
    @BindView(R.id.source_et_ruleSearchLink)
    AppCompatEditText sourceEtRuleSearchLink;
    @BindView(R.id.source_tile_ruleSearchLink)
    TextInputLayout sourceTileRuleSearchLink;
    @BindView(R.id.source_et_ruleCatalogTitle)
    AppCompatEditText sourceEtRuleCatalogTitle;
    @BindView(R.id.source_tile_ruleCatalogTitle)
    TextInputLayout sourceTileRuleCatalogTitle;
    @BindView(R.id.source_et_ruleCatalogLink)
    AppCompatEditText sourceEtRuleCatalogLink;
    @BindView(R.id.source_tile_ruleCatalogLink)
    TextInputLayout sourceTileRuleCatalogLink;
    @BindView(R.id.source_et_ruleChapterContent)
    AppCompatEditText sourceEtRuleChapterContent;
    @BindView(R.id.source_tile_ruleChapterContent)
    TextInputLayout sourceTileRuleChapterContent;
    @BindView(R.id.source_et_sourceType)
    AppCompatEditText sourceEtSourceType;
    @BindView(R.id.source_til_sourceType)
    TextInputLayout sourceTilSourceType;
    @BindView(R.id.source_et_ruleSearchBook)
    AppCompatEditText sourceEtRuleSearchBook;
    @BindView(R.id.source_tile_ruleSearchBook)
    TextInputLayout sourceTileRuleSearchBook;
    @BindView(R.id.source_et_ruleCatalogChapter)
    AppCompatEditText sourceEtRuleCatalogChapter;
    @BindView(R.id.source_tile_ruleCatalogChapter)
    TextInputLayout sourceTileRuleCatalogChapter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;


    private BookSourceBean mBookSource;
    private boolean isEdit;

    /****************************Initialization***********************/
    @Override
    protected int getLayoutId() {
        return R.layout.activity_source_edit;
    }

    /**
     * 是否开启左滑返回上一级
     *
     * @param enable
     */
    @Override
    public void setSwipeBackEnable(boolean enable) {
        //关闭左滑返回
        super.setSwipeBackEnable(false);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mBookSource = getIntent().getParcelableExtra(EXTRA_BOOK_SOURCE);
    }

    @Override
    protected void setUpToolbar(Toolbar toolbar) {
        super.setUpToolbar(toolbar);
        getSupportActionBar().setTitle(null == mBookSource ? "新建书源" : "编辑书源");
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        //判断上层activity是否传值过来
        //书源不为空，说明传值了，配置布局
        if (mBookSource != null) {
            isEdit = true;
            sourceEtRootLink.setText(mBookSource.getRootLink());
            sourceEtSourceName.setText(mBookSource.getSourceName());
            sourceEtSourceType.setText(mBookSource.getSourceType());
            sourceEtEncodeType.setText(mBookSource.getEncodeType());
            sourceEtSearchLink.setText(mBookSource.getSearchLink());
            sourceEtRuleSearchBook.setText(mBookSource.getRuleSearchBook());
            sourceEtRuleSearchTitle.setText(mBookSource.getRuleSearchTitle());
            sourceEtRuleSearchAuthor.setText(mBookSource.getRuleSearchAuthor());
            sourceEtRuleSearchDesc.setText(mBookSource.getRuleSearchDesc());
            sourceEtRuleSearchCover.setText(mBookSource.getRuleSearchCover());
            sourceEtRuleSearchLink.setText(mBookSource.getRuleSearchLink());
            sourceEtRuleCatalogChapter.setText(mBookSource.getRuleCatalogChapter());
            sourceEtRuleCatalogTitle.setText(mBookSource.getRuleCatalogTitle());
            sourceEtRuleCatalogLink.setText(mBookSource.getRuleCatalogLink());
            sourceEtRuleChapterContent.setText(mBookSource.getRuleChapterContent());
        } else {
            mBookSource = new BookSourceBean();
        }
    }

    private void saveBookSource() {
        if (isEmpty(trim(sourceEtRootLink.getText()))) {
            SnackbarUtils.show(this, "书源根地址不能为空");
            return;
        } else
            mBookSource.setRootLink(trim(sourceEtRootLink.getText()));
        //名称
        if (isEmpty(trim(sourceEtSourceName.getText()))) {
            SnackbarUtils.show(this, "书源名称不能为空");
            return;
        } else
            mBookSource.setSourceName(trim(sourceEtSourceName.getText()));
        //类型
        if (isEmpty(trim(sourceEtSourceType.getText()))) {
            SnackbarUtils.show(this, "书源类型不能为空");
            return;
        } else
            mBookSource.setSourceType(trim(sourceEtSourceType.getText()));
        //编码：默认utf-8
        if (isEmpty(trim(sourceEtEncodeType.getText()))) {
            mBookSource.setEncodeType("utf-8");
        } else
            mBookSource.setEncodeType(trim(sourceEtEncodeType.getText()));
        //
        if (isEmpty(trim(sourceEtSearchLink.getText()))) {
            SnackbarUtils.show(this, "书源搜索地址不能为空");
            return;
        } else
            mBookSource.setSearchLink(trim(sourceEtSearchLink.getText()));
        //
        if (isEmpty(trim(sourceEtRuleSearchBook.getText()))) {
            SnackbarUtils.show(this, "书源搜索结果列表规则不能为空");
            return;
        } else
            mBookSource.setRuleSearchBook(trim(sourceEtRuleSearchBook.getText()));
        //
        if (isEmpty(trim(sourceEtRuleSearchTitle.getText()))) {
            SnackbarUtils.show(this, "书源搜索结果书名规则不能为空");
            return;
        } else
            mBookSource.setRuleSearchTitle(trim(sourceEtRuleSearchTitle.getText()));
        //
        if (isEmpty(trim(sourceEtRuleSearchAuthor.getText()))) {
            SnackbarUtils.show(this, "书源搜索结果作者规则不能为空");
            return;
        } else
            mBookSource.setRuleSearchAuthor(trim(sourceEtRuleSearchAuthor.getText()));
        //
        if (isEmpty(trim(sourceEtRuleSearchDesc.getText()))) {
            SnackbarUtils.show(this, "书源搜索结果简介规则不能为空");
            return;
        } else
            mBookSource.setRuleSearchDesc(trim(sourceEtRuleSearchDesc.getText()));
        //
        if (isEmpty(trim(sourceEtRuleSearchCover.getText()))) {
            SnackbarUtils.show(this, "书源搜索结果封面规则不能为空");
            return;
        } else
            mBookSource.setRuleSearchCover(trim(sourceEtRuleSearchCover.getText()));
        //
        if (isEmpty(trim(sourceEtRuleSearchLink.getText()))) {
            SnackbarUtils.show(this, "书源搜索结果链接规则不能为空");
            return;
        } else
            mBookSource.setRuleSearchLink(trim(sourceEtRuleSearchLink.getText()));
        //
        if (isEmpty(trim(sourceEtRuleCatalogChapter.getText()))) {
            SnackbarUtils.show(this, "书源目录列表规则不能为空");
            return;
        } else
            mBookSource.setRuleCatalogChapter(trim(sourceEtRuleCatalogChapter.getText()));
        //
        if (isEmpty(trim(sourceEtRuleCatalogTitle.getText()))) {
            SnackbarUtils.show(this, "书源目录标题规则不能为空");
            return;
        } else
            mBookSource.setRuleCatalogTitle(trim(sourceEtRuleCatalogTitle.getText()));
        //
        if (isEmpty(trim(sourceEtRuleCatalogLink.getText()))) {
            SnackbarUtils.show(this, "书源目录链接规则不能为空");
            return;
        } else
            mBookSource.setRuleCatalogLink(trim(sourceEtRuleCatalogLink.getText()));

        //
        if (isEmpty(trim(sourceEtRuleChapterContent.getText()))) {
            SnackbarUtils.show(this, "书源章节内容规则不能为空");
            return;
        } else
            mBookSource.setRuleChapterContent(trim(sourceEtRuleChapterContent.getText()));

        if (!isEdit) {
            //新建则添加权重
            mBookSource.setOrderNumber(0);
            mBookSource.setWeight(0);
        }

        mPresenter.saveBookSource(mBookSource);
    }

    private String trim(Editable editable) {
        if (editable == null) {
            return null;
        }
        return StringUtils.trim(editable.toString());
    }

    /*********************************Transaction************************/
    @Override
    protected SourceEditContract.Presenter bindPresenter() {
        return new SourceEditPresenter();
    }

    @Override
    public void saveSuccess(Long i) {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void showError() {
        SnackbarUtils.show(this, "保存失败");
    }

    @Override
    public void complete() {

    }

    /****************************Event****************************/


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_source_edit, menu);
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

            case R.id.action_source_save:
                saveBookSource();
                break;
            case R.id.action_source_test:
                break;
            case R.id.action_source_rule:
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

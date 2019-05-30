package com.thmub.newbook.ui.activity;

import android.content.Intent;
import android.net.Uri;
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
import com.thmub.newbook.utils.ToastUtils;

import org.apache.commons.lang3.StringUtils;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;

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
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.source_et_rootLink)
    AppCompatEditText sourceEtRootLink;
    @BindView(R.id.source_til_rootLink)
    TextInputLayout sourceTilRootLink;
    @BindView(R.id.source_et_sourceName)
    AppCompatEditText sourceEtSourceName;
    @BindView(R.id.source_til_sourceName)
    TextInputLayout sourceTilSourceName;
    @BindView(R.id.source_et_sourceType)
    AppCompatEditText sourceEtSourceType;
    @BindView(R.id.source_til_sourceType)
    TextInputLayout sourceTilSourceType;
    @BindView(R.id.source_et_encodeType)
    AppCompatEditText sourceEtEncodeType;
    @BindView(R.id.source_tile_encodeType)
    TextInputLayout sourceTileEncodeType;
    @BindView(R.id.source_et_searchLink)
    AppCompatEditText sourceEtSearchLink;
    @BindView(R.id.source_tile_searchLink)
    TextInputLayout sourceTileSearchLink;
    @BindView(R.id.source_et_ruleSearchBooks)
    AppCompatEditText sourceEtRuleSearchBooks;
    @BindView(R.id.source_tile_ruleSearchBooks)
    TextInputLayout sourceTileRuleSearchBooks;
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
    @BindView(R.id.source_et_ruleDetailBook)
    AppCompatEditText sourceEtRuleDetailBook;
    @BindView(R.id.source_tile_ruleDetailBook)
    TextInputLayout sourceTileRuleDetailBook;
    @BindView(R.id.source_et_ruleDetailTitle)
    AppCompatEditText sourceEtRuleDetailTitle;
    @BindView(R.id.source_tile_ruleDetailTitle)
    TextInputLayout sourceTileRuleDetailTitle;
    @BindView(R.id.source_et_ruleDetailAuthor)
    AppCompatEditText sourceEtRuleDetailAuthor;
    @BindView(R.id.source_tile_ruleDetailAuthor)
    TextInputLayout sourceTileRuleDetailAuthor;
    @BindView(R.id.source_et_ruleDetailDesc)
    AppCompatEditText sourceEtRuleDetailDesc;
    @BindView(R.id.source_tile_ruleDetailDesc)
    TextInputLayout sourceTileRuleDetailDesc;
    @BindView(R.id.source_et_ruleDetailCover)
    AppCompatEditText sourceEtRuleDetailCover;
    @BindView(R.id.source_tile_ruleDetailCover)
    TextInputLayout sourceTileRuleDetailCover;
    @BindView(R.id.source_et_ruleFindLink)
    AppCompatEditText sourceEtRuleFindLink;
    @BindView(R.id.source_tile_ruleFindLink)
    TextInputLayout sourceTileRuleFindLink;
    @BindView(R.id.source_et_ruleCatalogLink)
    AppCompatEditText sourceEtRuleCatalogLink;
    @BindView(R.id.source_tile_ruleCatalogLink)
    TextInputLayout sourceTileRuleCatalogLink;
    @BindView(R.id.source_et_ruleChapters)
    AppCompatEditText sourceEtRuleChapters;
    @BindView(R.id.source_tile_ruleChapters)
    TextInputLayout sourceTileRuleChapters;
    @BindView(R.id.source_et_ruleChapterTitle)
    AppCompatEditText sourceEtRuleChapterTitle;
    @BindView(R.id.source_tile_ruleChapterTitle)
    TextInputLayout sourceTileRuleChapterTitle;
    @BindView(R.id.source_et_ruleChapterLink)
    AppCompatEditText sourceEtRuleChapterLink;
    @BindView(R.id.source_tile_ruleChapterLink)
    TextInputLayout sourceTileRuleChapterLink;
    @BindView(R.id.source_et_ruleChapterContent)
    AppCompatEditText sourceEtRuleChapterContent;
    @BindView(R.id.source_tile_ruleChapterContent)
    TextInputLayout sourceTileRuleChapterContent;
    @BindView(R.id.source_et_ruleFindBooks)
    AppCompatEditText sourceEtRuleFindBooks;
    @BindView(R.id.source_tile_ruleFindBooks)
    TextInputLayout sourceTileRuleFindBooks;
    @BindView(R.id.source_et_ruleFindCover)
    AppCompatEditText sourceEtRuleFindCover;
    @BindView(R.id.source_tile_ruleFindCover)
    TextInputLayout sourceTileRuleFindCover;
    @BindView(R.id.source_et_ruleFindBookLink)
    AppCompatEditText sourceEtRuleFindBookLink;
    @BindView(R.id.source_tile_ruleFindBookLink)
    TextInputLayout sourceTileRuleFindBookLink;
    @BindView(R.id.source_et_ruleFindBookTitle)
    AppCompatEditText sourceEtRuleFindBookTitle;
    @BindView(R.id.source_tile_ruleFindBookTitle)
    TextInputLayout sourceTileRuleFindBookTitle;


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
            //书源定义
            sourceEtRootLink.setText(mBookSource.getRootLink());
            sourceEtSourceName.setText(mBookSource.getSourceName());
            sourceEtSourceType.setText(mBookSource.getSourceType());
            sourceEtEncodeType.setText(mBookSource.getEncodeType());
            sourceEtSearchLink.setText(mBookSource.getSearchLink());
            //搜索规则
            sourceEtRuleSearchBooks.setText(mBookSource.getRuleSearchBooks());
            sourceEtRuleSearchTitle.setText(mBookSource.getRuleSearchTitle());
            sourceEtRuleSearchAuthor.setText(mBookSource.getRuleSearchAuthor());
            sourceEtRuleSearchDesc.setText(stringNotNull(mBookSource.getRuleSearchDesc()));//null
            sourceEtRuleSearchCover.setText(stringNotNull(mBookSource.getRuleSearchCover()));//null
            sourceEtRuleSearchLink.setText(mBookSource.getRuleSearchLink());
            //详情规则
            sourceEtRuleDetailBook.setText(stringNotNull(mBookSource.getRuleDetailBook()));//null
            sourceEtRuleDetailTitle.setText(stringNotNull(mBookSource.getRuleDetailTitle()));//null
            sourceEtRuleDetailAuthor.setText(stringNotNull(mBookSource.getRuleDetailAuthor()));//null
            sourceEtRuleDetailDesc.setText(stringNotNull(mBookSource.getRuleDetailAuthor()));//null
            sourceEtRuleDetailCover.setText(stringNotNull(mBookSource.getRuleDetailCover()));//null
            sourceEtRuleFindLink.setText(stringNotNull(mBookSource.getRuleFindLink()));//null
            sourceEtRuleCatalogLink.setText(stringNotNull(mBookSource.getRuleCatalogLink()));//null
            //目录规则
            sourceEtRuleChapters.setText(mBookSource.getRuleChapters());
            sourceEtRuleChapterTitle.setText(mBookSource.getRuleChapterTitle());
            sourceEtRuleChapterLink.setText(mBookSource.getRuleChapterLink());
            //章节内容
            sourceEtRuleChapterContent.setText(mBookSource.getRuleChapterContent());
            //发现规则
            sourceEtRuleFindBooks.setText(stringNotNull(mBookSource.getRuleFindBooks()));//null
            sourceEtRuleFindCover.setText(stringNotNull(mBookSource.getRuleFindCover()));//null
            sourceEtRuleFindBookLink.setText(stringNotNull(mBookSource.getRuleFindBookLink()));//null
            sourceEtRuleFindBookTitle.setText(stringNotNull(mBookSource.getRuleFindBookTitle()));//null

        } else {
            mBookSource = new BookSourceBean();
        }
    }

    private String stringNotNull(String str) {
        return str == null ? "" : str;
    }

    /**
     * 保存书源
     */
    private void saveBookSource() {
        //书源地址
        if (isEmpty(trim(sourceEtRootLink.getText()))) {
            //sourceTilRootLink.setError("书源根地址不能为空");
            ToastUtils.showError(mContext, "书源根地址不能为空");
            return;
        } else
            mBookSource.setRootLink(trim(sourceEtRootLink.getText()));
        //名称
        if (isEmpty(trim(sourceEtSourceName.getText()))) {
            ToastUtils.showError(mContext, "书源名称不能为空");
            return;
        } else
            mBookSource.setSourceName(trim(sourceEtSourceName.getText()));
        //类型
        if (isEmpty(trim(sourceEtSourceType.getText()))) {
            ToastUtils.showError(mContext, "书源类型不能为空");
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
            ToastUtils.showError(mContext, "书源搜索地址不能为空");
            return;
        } else
            mBookSource.setSearchLink(trim(sourceEtSearchLink.getText()));
        //*******************************************************************
        //搜索书籍列表
        if (isEmpty(trim(sourceEtRuleSearchBooks.getText()))) {
            ToastUtils.showError(mContext, "书源搜索结果列表规则不能为空");
            return;
        } else
            mBookSource.setRuleSearchBooks(trim(sourceEtRuleSearchBooks.getText()));
        //
        if (isEmpty(trim(sourceEtRuleSearchTitle.getText()))) {
            ToastUtils.showError(mContext, "书源搜索结果书名规则不能为空");
            return;
        } else
            mBookSource.setRuleSearchTitle(trim(sourceEtRuleSearchTitle.getText()));
        //
        if (isEmpty(trim(sourceEtRuleSearchAuthor.getText()))) {
            ToastUtils.showError(mContext, "书源搜索结果作者规则不能为空");
            return;
        } else
            mBookSource.setRuleSearchAuthor(trim(sourceEtRuleSearchAuthor.getText()));
        //搜索结果简介
        mBookSource.setRuleSearchDesc(trim(sourceEtRuleSearchDesc.getText()));
        //搜索结果封面
        mBookSource.setRuleSearchCover(trim(sourceEtRuleSearchCover.getText()));
        //
        if (isEmpty(trim(sourceEtRuleSearchLink.getText()))) {
            ToastUtils.showError(mContext, "书源搜索结果地址规则不能为空");
            return;
        } else
            mBookSource.setRuleSearchLink(trim(sourceEtRuleSearchLink.getText()));
        //*******************************************************************
        //书籍详情列表
        mBookSource.setRuleDetailBook(trim(sourceEtRuleDetailBook.getText()));
        mBookSource.setRuleDetailTitle(trim(sourceEtRuleDetailTitle.getText()));
        mBookSource.setRuleDetailAuthor(trim(sourceEtRuleDetailAuthor.getText()));
        mBookSource.setRuleDetailAuthor(trim(sourceEtRuleDetailDesc.getText()));
        mBookSource.setRuleDetailCover(trim(sourceEtRuleDetailCover.getText()));
        mBookSource.setRuleFindLink(trim(sourceEtRuleFindLink.getText()));
        mBookSource.setRuleCatalogLink(trim(sourceEtRuleCatalogLink.getText()));
        //*******************************************************************
        //书籍目录
        if (isEmpty(trim(sourceEtRuleChapters.getText()))) {
            ToastUtils.showError(mContext, "书源目录列表规则不能为空");
            return;
        } else
            mBookSource.setRuleCatalogLink(trim(sourceEtRuleChapters.getText()));
        //
        if (isEmpty(trim(sourceEtRuleChapterTitle.getText()))) {
            ToastUtils.showError(mContext, "书源目录标题规则不能为空");
            return;
        } else
            mBookSource.setRuleChapterTitle(trim(sourceEtRuleChapterTitle.getText()));
        //
        if (isEmpty(trim(sourceEtRuleChapterLink.getText()))) {
            ToastUtils.showError(mContext, "书源目录地址规则不能为空");
            return;
        } else
            mBookSource.setRuleCatalogLink(trim(sourceEtRuleChapterLink.getText()));
        //*******************************************************************
        //章节内容
        if (isEmpty(trim(sourceEtRuleChapterContent.getText()))) {
            ToastUtils.showError(mContext, "书源章节内容规则不能为空");
            return;
        } else
            mBookSource.setRuleChapterContent(trim(sourceEtRuleChapterContent.getText()));
        //*******************************************************************
        //书籍发现
        mBookSource.setRuleFindBooks(trim(sourceEtRuleFindBooks.getText()));
        mBookSource.setRuleFindCover(trim(sourceEtRuleFindCover.getText()));
        mBookSource.setRuleFindBookLink(trim(sourceEtRuleFindBookLink.getText()));
        mBookSource.setRuleFindBookTitle(trim(sourceEtRuleFindBookTitle.getText()));

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
    public void showError(Throwable e) {
        ToastUtils.showError(mContext, e.getMessage());
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
                Uri uri = Uri.parse("https://blog.thmub.com/2019/05/31/SourceRule/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

package com.thmub.newbook.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.thmub.newbook.R;
import com.thmub.newbook.base.BaseMVPActivity;
import com.thmub.newbook.bean.BookSearchBean;
import com.thmub.newbook.bean.BookChapterBean;
import com.thmub.newbook.bean.ShelfBookBean;
import com.thmub.newbook.presenter.BookDetailPresenter;
import com.thmub.newbook.presenter.contract.BookDetailContract;
import com.thmub.newbook.ui.adapter.CatalogAdapter;

import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * Created by Zhouas666 on 2019-03-28
 * Github: https://github.com/zas023
 * <p>
 * 书籍详情activity
 */
public class BookDetailActivity extends BaseMVPActivity<BookDetailContract.Presenter>
        implements BookDetailContract.View {

    /****************************Constant*********************************/
    public static final String EXTRA_BOOK = "extra_book";

    /*****************************View***********************************/
    @BindView(R.id.book_detail_iv_cover)
    ImageView mIvCover;
    @BindView(R.id.book_detail_tv_author)
    TextView mTvAuthor;
    @BindView(R.id.book_detail_tv_type)
    TextView mTvType;
    @BindView(R.id.book_detail_tv_word_count)
    TextView mTvWordCount;
    @BindView(R.id.book_detail_rv_catalog)
    RecyclerView mRvCatalog;

    /****************************Variable*********************************/
    private BookSearchBean bookBean;
    private ShelfBookBean mShelfBook;

    private CatalogAdapter mCatalogAdapter;

    /*************************Public Method*******************************/


    /**************************Initialization******************************/
    @Override
    protected int getLayoutId() {
        return R.layout.activity_book_detail;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        bookBean = getIntent().getParcelableExtra(EXTRA_BOOK);
        mShelfBook = bookBean.getShelfBook();
    }

    @Override
    protected void setUpToolbar(Toolbar toolbar) {
        super.setUpToolbar(toolbar);
        getSupportActionBar().setTitle(bookBean.getTitle());
    }


    @Override
    protected void initWidget() {
        super.initWidget();
        Glide.with(mContext).load(bookBean.getCover()).into(mIvCover);
        mTvAuthor.setText(bookBean.getAuthor());
        mTvType.setText(bookBean.getLink());
        mTvWordCount.setText(bookBean.getSourceUrls().toString());
//        mTvDesc.setText(bookBean.getShortIntro());

        mCatalogAdapter = new CatalogAdapter();
        mRvCatalog.setLayoutManager(new LinearLayoutManager(mContext));
        mRvCatalog.setAdapter(mCatalogAdapter);
    }

    @Override
    protected void initClick() {
        super.initClick();
        mCatalogAdapter.setOnItemClickListener((view, pos) -> {
            mShelfBook.setCurChapter(pos);
            startActivity(new Intent(mContext, ReadActivity.class)
                    .putExtra(ReadActivity.EXTRA_BOOK, mShelfBook));
        });
    }

    /**************************Transaction********************************/
    @Override
    protected BookDetailContract.Presenter bindPresenter() {
        return new BookDetailPresenter();
    }

    @Override
    protected void processLogic() {
        super.processLogic();
        mPresenter.loadCatalogs(mShelfBook);
    }

    @Override
    public void finishLoadCatalogs(List<BookChapterBean> items) {
        mShelfBook.setBookChapterList(items);
        mCatalogAdapter.addItems(items);
    }

    @Override
    public void showError() {

    }

    @Override
    public void complete() {

    }
}

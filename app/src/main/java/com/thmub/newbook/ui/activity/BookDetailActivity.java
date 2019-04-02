package com.thmub.newbook.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.thmub.newbook.R;
import com.thmub.newbook.base.BaseMVPActivity;
import com.thmub.newbook.bean.BookChapterBean;
import com.thmub.newbook.bean.BookSearchBean;
import com.thmub.newbook.bean.ShelfBookBean;
import com.thmub.newbook.model.repo.BookShelfRepository;
import com.thmub.newbook.presenter.BookDetailPresenter;
import com.thmub.newbook.presenter.contract.BookDetailContract;
import com.thmub.newbook.ui.adapter.BookDetailAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

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
    @BindView(R.id.book_detail_rv_content)
    RecyclerView mRvContent;
    @BindView(R.id.book_detail_tv_add)
    TextView bookDetailTvAdd;
    @BindView(R.id.book_detail_tv_open)
    TextView bookDetailTvOpen;

    /****************************Variable*********************************/
    private BookSearchBean bookBean;
    private ShelfBookBean mShelfBook;

    private boolean isCollected;

    private BookDetailAdapter mAdapter;


    /**************************Initialization******************************/
    @Override
    protected int getLayoutId() {
        return R.layout.activity_book_detail;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        bookBean = getIntent().getParcelableExtra(EXTRA_BOOK);
        //查找书架，判断是否是已收藏
        mShelfBook = BookShelfRepository.getInstance().getShelfBook(bookBean.getTitle(), bookBean.getAuthor());
        if (mShelfBook == null)
            mShelfBook = bookBean.getShelfBook();
        else
            isCollected = true;
    }

    @Override
    protected void setUpToolbar(Toolbar toolbar) {
        super.setUpToolbar(toolbar);
        getSupportActionBar().setTitle(bookBean.getTitle());
    }


    @Override
    protected void initWidget() {
        super.initWidget();
        //Title Info
        Glide.with(mContext).load(bookBean.getCover()).into(mIvCover);
        mTvAuthor.setText(bookBean.getAuthor());
        mTvType.setText(bookBean.getLink());
        mTvWordCount.setText(bookBean.getSourceUrls().toString());

        //Recycler
        mAdapter = new BookDetailAdapter();
        mRvContent.setLayoutManager(new LinearLayoutManager(mContext));
        mRvContent.setAdapter(mAdapter);

        //Button
        if (isCollected) {
            bookDetailTvAdd.setText("移除书架");
            bookDetailTvOpen.setText("继续阅读");
        }
    }

    @Override
    protected void initClick() {
        super.initClick();

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

        //设置详情页
        List<BookChapterBean> list = new ArrayList<>();
        list.add(new BookChapterBean(bookBean.getDesc()));
        list.addAll(items.subList(0, 10));

        mAdapter.addItems(list);
    }

    @Override
    public void finishRemoveBook(Integer i) {

    }

    @Override
    public void showError() {

    }

    @Override
    public void complete() {

    }

    /********************************Event***************************************/
    /**
     * 监听点击事件
     *
     * @param view
     */
    @OnClick({R.id.fl_add_bookcase, R.id.fl_open_book})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fl_add_bookcase:  //加入书架
                addToShelf();
                break;
            case R.id.fl_open_book:  //开始阅读
                startActivity(new Intent(mContext, ReadActivity.class)
                        .putExtra(ReadActivity.EXTRA_BOOK, mShelfBook));
                break;
        }
    }

    /**
     * 加入书架
     */
    private void addToShelf() {
        if (isCollected) {
            //放弃点击
            //因为需要同时删除数据库中的章节和缓存的文件，所以采用异步的方式
            mPresenter.removeShelfBook(mShelfBook);
            bookDetailTvAdd.setText("加入书架");
            bookDetailTvOpen.setText("开始阅读");
            isCollected = false;
        } else {
            BookShelfRepository.getInstance().saveShelfBook(mShelfBook);
            bookDetailTvAdd.setText("移除书架");
            bookDetailTvOpen.setText("继续阅读");
            isCollected = true;
        }
    }
}

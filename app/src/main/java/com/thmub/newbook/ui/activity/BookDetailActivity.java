package com.thmub.newbook.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
import com.thmub.newbook.ui.dialog.ChangeSourceDialog;

import java.util.ArrayList;
import java.util.Collections;
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
    public static final String RESULT_IS_COLLECTED = "result_is_collected";
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

    private ChangeSourceDialog mSourceDialog;
    /****************************Variable*********************************/
    private BookSearchBean mSearchBook;
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
        mSearchBook = getIntent().getParcelableExtra(EXTRA_BOOK);
        //查找书架，判断是否是已收藏
        mShelfBook = BookShelfRepository.getInstance().getShelfBook(mSearchBook.getTitle(), mSearchBook.getAuthor());
        if (mShelfBook == null)
            mShelfBook = mSearchBook.getShelfBook();
        else
            isCollected = true;
    }

    @Override
    protected void setUpToolbar(Toolbar toolbar) {
        super.setUpToolbar(toolbar);
        getSupportActionBar().setTitle(mSearchBook.getTitle());
    }


    @Override
    protected void initWidget() {
        super.initWidget();

        initBookInfo();

        //Recycler
        mAdapter = new BookDetailAdapter();
        mAdapter.addItem(new BookChapterBean(mSearchBook.getDesc()));
        mRvContent.setLayoutManager(new LinearLayoutManager(mContext));
        mRvContent.setAdapter(mAdapter);

        //Button
        if (isCollected) {
            bookDetailTvAdd.setText("移除书架");
            bookDetailTvOpen.setText("继续阅读");
        }

        //Dialog
        mSourceDialog = new ChangeSourceDialog(this, mShelfBook);

    }

    //Book title info
    private void initBookInfo() {
        Glide.with(mContext).load(mSearchBook.getCover()).into(mIvCover);
        mTvAuthor.setText(mSearchBook.getAuthor());
        mTvType.setText(mSearchBook.getLink());
        mTvWordCount.setText(mSearchBook.getSource());
    }

    @Override
    protected void initClick() {
        super.initClick();
        mSourceDialog.setListener(bean -> {
            mSearchBook = bean;
            mShelfBook=mSearchBook.getShelfBook();
            initBookInfo();
            mPresenter.loadCatalogs(mShelfBook);
            mSourceDialog.dismiss();
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

        //设置详情页
        List<BookChapterBean> list = new ArrayList<>();
        //最新十章
        int size = items.size();
        if (size > 10)
            //subList中有些坑，取值范围是 fromIndex <= field < toIndex
            list.addAll(items.subList(size - 10, size));
        else
            list.addAll(items);
        Collections.reverse(list);

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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_book_detail, menu);
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
            case R.id.action_change_source:  //换源
                mSourceDialog.show();
                break;
            case R.id.action_edit_source:  //编辑源

                break;
            case R.id.action_reload:  //重新加载

                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //如果进入阅读页面收藏了，就需要返回改变收藏按钮
        if (resultCode == RESULT_OK) {

            if (data == null) return;

            isCollected = data.getBooleanExtra(RESULT_IS_COLLECTED, false);

            if (isCollected) {
                bookDetailTvAdd.setText("移除书架");
                bookDetailTvOpen.setText("继续阅读");
            }
        }
    }

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
                        .putExtra(ReadActivity.EXTRA_BOOK, mShelfBook)
                        .putExtra(ReadActivity.EXTRA_IS_COLLECTED, isCollected));
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

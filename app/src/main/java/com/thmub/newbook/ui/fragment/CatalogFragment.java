package com.thmub.newbook.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.futuremind.recyclerviewfastscroll.FastScroller;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.thmub.newbook.R;
import com.thmub.newbook.base.BaseMVPFragment;
import com.thmub.newbook.bean.BookChapterBean;
import com.thmub.newbook.bean.ShelfBookBean;
import com.thmub.newbook.bean.event.ChapterExchangeEvent;
import com.thmub.newbook.manager.RxBusManager;
import com.thmub.newbook.model.local.BookShelfRepository;
import com.thmub.newbook.presenter.CatalogPresenter;
import com.thmub.newbook.presenter.contract.CatalogContract;
import com.thmub.newbook.ui.activity.CatalogActivity;
import com.thmub.newbook.ui.activity.ReadActivity;
import com.thmub.newbook.ui.adapter.CatalogAdapter;
import com.thmub.newbook.utils.ToastUtils;
import com.thmub.newbook.widget.DashlineItemDivider;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * Created by Zhouas666 on 2019-04-16
 * Github: https://github.com/zas023
 * <p>
 * 目录fragment
 */
public class CatalogFragment extends BaseMVPFragment<CatalogContract.Presenter>
        implements CatalogContract.View {

    /***************************View*****************************/
    @BindView(R.id.rv_content)
    RecyclerView rvContent;
    @BindView(R.id.fast_scroller)
    FastScroller fastScroller;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    /***************************Variable*****************************/
    private ShelfBookBean mBook;
    private CatalogAdapter mAdapter;
    private LinearLayoutManager layoutManager;

    private boolean isReversed;

    /***************************Public*****************************/
    public void startSearch(String query) {
        mAdapter.getFilter().filter(query);
    }

    /***************************Initialization*****************************/
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_catalog;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mBook = getFatherActivity().getShelfBook();
        //
        isReversed = false;
        //adapter
        mAdapter = new CatalogAdapter(mContext);
        //layoutManager
        layoutManager = new LinearLayoutManager(mContext);
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);

        //recycler
        rvContent.setLayoutManager(layoutManager);
        rvContent.setAdapter(mAdapter);
        rvContent.addItemDecoration(new DashlineItemDivider());
        //scroller
        fastScroller.setRecyclerView(rvContent);
    }

    @Override
    protected void initClick() {
        super.initClick();
        //前往章节
        mAdapter.setListener((index, page) -> {
            if (mBook.isReading()) {
                RxBusManager.getInstance().post(new ChapterExchangeEvent(index, page));
                getFatherActivity().finish();
            } else {
                mBook.setCurChapter(index);
                mBook.setCurChapterPage(page);
                startActivity(new Intent(mContext, ReadActivity.class).putExtra(ReadActivity.EXTRA_BOOK, mBook));
                getActivity().finish();
            }
        });
        //倒叙
        fab.setOnClickListener(v -> {
            isReversed = !isReversed;
            //列表再底部开始展示，反转后由上面开始展示
            layoutManager.setStackFromEnd(isReversed);
            //列表翻转
            layoutManager.setReverseLayout(isReversed);
        });
    }

    @Override
    protected void processLogic() {
        super.processLogic();
        if (mBook.isCollected())
            finishLoadCatalog(BookShelfRepository.getInstance().getChapters(mBook));
        else
            mPresenter.loadCatalog(mBook);
    }

    private CatalogActivity getFatherActivity() {
        return (CatalogActivity) getActivity();
    }

    /*****************************Transaction******************************/
    @Override
    protected CatalogContract.Presenter bindPresenter() {
        return new CatalogPresenter();
    }

    @Override
    public void finishLoadCatalog(List<BookChapterBean> items) {
        mBook.setBookChapterList(items);
        mAdapter.setShelfBook(mBook);
        //置顶当前章节位置
        rvContent.getLayoutManager().scrollToPosition(mBook.getCurChapter());
    }

    @Override
    public void showError(Throwable e) {
        ToastUtils.showError(mContext, e.getMessage());
    }

    @Override
    public void complete() {

    }

}

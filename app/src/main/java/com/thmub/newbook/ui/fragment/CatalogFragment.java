package com.thmub.newbook.ui.fragment;

import android.os.Bundle;

import com.futuremind.recyclerviewfastscroll.FastScroller;
import com.thmub.newbook.R;
import com.thmub.newbook.base.BaseMVPFragment;
import com.thmub.newbook.bean.BookChapterBean;
import com.thmub.newbook.bean.ShelfBookBean;
import com.thmub.newbook.bean.event.ChapterExchangeEvent;
import com.thmub.newbook.manager.RxBusManager;
import com.thmub.newbook.presenter.CatalogPresenter;
import com.thmub.newbook.presenter.contract.CatalogContract;
import com.thmub.newbook.ui.activity.CatalogActivity;
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
 * 书城fragment
 */
public class CatalogFragment extends BaseMVPFragment<CatalogContract.Presenter>
        implements CatalogContract.View {

    /***************************View*****************************/
    @BindView(R.id.rv_content)
    RecyclerView rvContent;
    @BindView(R.id.fast_scroller)
    FastScroller fastScroller;

    /***************************Variable*****************************/
    private ShelfBookBean mBook;
    private CatalogAdapter mAdapter;

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
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        //adapter
        mAdapter = new CatalogAdapter();
        //recycler
        rvContent.setLayoutManager(new LinearLayoutManager(mContext));
        rvContent.setAdapter(mAdapter);
        rvContent.addItemDecoration(new DashlineItemDivider());
        //scroller
        fastScroller.setRecyclerView(rvContent);
    }

    @Override
    protected void initClick() {
        super.initClick();
        mAdapter.setListener((index, page) -> {
            if (mBook.isReading()) {
                RxBusManager.getInstance().post(new ChapterExchangeEvent(index, page));
                getFatherActivity().finish();
            }
        });
    }

    @Override
    protected void processLogic() {
        super.processLogic();
        if (mBook.getBookChapterListSize() > 0) {
            mAdapter.setShelfBook(mBook);
            //置顶当前章节位置
            rvContent.getLayoutManager().scrollToPosition(mBook.getCurChapter());
        } else
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
    }

    @Override
    public void showError(Throwable e) {
        ToastUtils.showError(mContext, e.getMessage());
    }

    @Override
    public void complete() {

    }

}

package com.thmub.newbook.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.thmub.newbook.R;
import com.thmub.newbook.base.BaseFragment;
import com.thmub.newbook.base.adapter.BaseRecyclerAdapter;
import com.thmub.newbook.bean.BookChapterBean;
import com.thmub.newbook.bean.BookMarkBean;
import com.thmub.newbook.bean.ShelfBookBean;
import com.thmub.newbook.bean.event.ChapterExchangeEvent;
import com.thmub.newbook.manager.RxBusManager;
import com.thmub.newbook.model.local.BookMarkRepository;
import com.thmub.newbook.ui.activity.CatalogActivity;
import com.thmub.newbook.ui.activity.ReadActivity;
import com.thmub.newbook.ui.adapter.BookMarkAdapter;
import com.thmub.newbook.ui.adapter.DetailCatalogAdapter;
import com.thmub.newbook.widget.DashlineItemDivider;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * Created by Zhouas666 on 2019-04-16
 * Github: https://github.com/zas023
 * <p>
 * 书签fragment
 */
public class BookMarkFragment extends BaseFragment {

    /***************************View*****************************/
    @BindView(R.id.rv_content)
    RecyclerView rvContent;
    /***************************Variable*****************************/
    private ShelfBookBean mBook;
    private BookMarkAdapter mAdapter;

    /***************************Public*****************************/
    public void startSearch(String query) {
        mAdapter.getFilter().filter(query);
    }

    /***************************Initialization*****************************/
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_book_mark;
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
        mAdapter = new BookMarkAdapter();
        //recycler
        rvContent.setLayoutManager(new LinearLayoutManager(mContext));
        rvContent.setAdapter(mAdapter);
        rvContent.addItemDecoration(new DashlineItemDivider());
    }

    @Override
    protected void initClick() {
        super.initClick();
        //前往章节
        mAdapter.setOnItemClickListener((view, pos) -> {
            BookMarkBean bean=mAdapter.getItem(pos);
            if (mBook.isReading()) {
                RxBusManager.getInstance().post(new ChapterExchangeEvent(bean.getChapterIndex(), bean.getChapterPage()));
                getFatherActivity().finish();
            } else {
                mBook.setCurChapter(bean.getChapterIndex());
                mBook.setCurChapterPage(bean.getChapterPage());
                startActivity(new Intent(mContext, ReadActivity.class).putExtra(ReadActivity.EXTRA_BOOK, mBook));
                getActivity().finish();
            }
        });
    }

    @Override
    protected void processLogic() {
        super.processLogic();
        mAdapter.addItems(BookMarkRepository.getInstance().getBookMarkByLink(mBook.getLink()));
    }


    private CatalogActivity getFatherActivity() {
        return (CatalogActivity) getActivity();
    }

    /*****************************Transaction******************************/


}

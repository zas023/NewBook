package com.thmub.newbook.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.thmub.newbook.R;
import com.thmub.newbook.base.BaseActivity;
import com.thmub.newbook.bean.BookSearchBean;
import com.thmub.newbook.model.SearchEngine;
import com.thmub.newbook.model.repo.BookSourceRepository;
import com.thmub.newbook.ui.adapter.SearchBookAdapter;
import com.thmub.newbook.widget.refresh.ScrollRefreshRecyclerView;

import java.util.List;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import butterknife.BindView;

/**
 * Created by Zhouas666 on 2019-03-26
 * Github: https://github.com/zas023
 * <p>
 * 搜索activity
 */
public class SearchActivity extends BaseActivity {

    @BindView(R.id.search_rv_content)
    ScrollRefreshRecyclerView searchRvContent;
    @BindView(R.id.search_sv)
    SearchView searchView;

    private SearchBookAdapter mAdapter;

    private SearchEngine mSearchEngine;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void setUpToolbar(Toolbar toolbar) {
        super.setUpToolbar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mSearchEngine = new SearchEngine();
        mSearchEngine.initSearchEngine(BookSourceRepository.getInstance().getAllSelectedBookSource());
    }

    @Override
    protected void initWidget() {
        super.initWidget();

        //初始化RecyclerView
        mAdapter = new SearchBookAdapter(this);
        searchRvContent.setLayoutManager(new LinearLayoutManager(mContext));
        searchRvContent.setAdapter(mAdapter);

    }

    @Override
    protected void initClick() {
        super.initClick();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mAdapter.clear();
                searchRvContent.startRefresh();
                mSearchEngine.search(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchRvContent.setOnRefreshListener(() -> {
            mSearchEngine.search(searchView.getQuery().toString());
        });

        mAdapter.setOnItemClickListener((view, pos) -> {
            startActivity(new Intent(mContext, BookDetailActivity.class)
                    .putExtra(BookDetailActivity.EXTRA_BOOK, mAdapter.getItem(pos)));
        });
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mSearchEngine.setOnSearchListener(new SearchEngine.OnSearchListener() {

            @Override
            public void refreshFinish(Boolean isAll) {

            }

            @Override
            public void loadMoreFinish(Boolean isAll) {
                //ScrollRefreshRecyclerView会被多个线程操作
                //需要加锁
                synchronized (ScrollRefreshRecyclerView.class){
                    searchRvContent.finishRefresh();
                }
            }

            @Override
            public void loadMoreSearchBook(List<BookSearchBean> items) {
                mAdapter.addItems(items);
            }
        });
    }

    @Override
    protected void processLogic() {
        super.processLogic();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_book_search, menu);
        return true;
    }


    /************************************Life Cycle*********************/
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSearchEngine=null;
        System.gc();
    }
}

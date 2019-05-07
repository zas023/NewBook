package com.thmub.newbook.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.thmub.newbook.R;
import com.thmub.newbook.base.BaseActivity;
import com.thmub.newbook.bean.BookSearchBean;
import com.thmub.newbook.model.SearchEngine;
import com.thmub.newbook.model.local.BookSourceRepository;
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

    /****************************Constant*********************************/
    public final static String RESULT_IS_CHANGED = "result_is_changed";
    private final static int REQUEST_CODE = 1;


    /****************************View*********************************/
    @BindView(R.id.search_rv_content)
    ScrollRefreshRecyclerView searchRvContent;
    @BindView(R.id.search_sv)
    SearchView searchView;

    /****************************Variable*********************************/
    private SearchBookAdapter mAdapter;

    private SearchEngine mSearchEngine;
    private String keyword;

    /****************************Initialization*********************************/
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
        //SearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mAdapter.clear();
                searchRvContent.startRefresh();
                mSearchEngine.search(query);
                keyword = query;
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchRvContent.setOnRefreshListener(() -> mSearchEngine.search(searchView.getQuery().toString()));
        //RecyclerView
        mAdapter.setOnItemClickListener((view, pos) -> startActivity(new Intent(mContext, BookDetailActivity.class)
                .putExtra(BookDetailActivity.EXTRA_BOOK, mAdapter.getItem(pos))));
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mSearchEngine.setOnSearchListener(new SearchEngine.OnSearchListener() {

            @Override
            public void loadMoreFinish(Boolean isAll) {
                //ScrollRefreshRecyclerView会被多个线程操作
                //需要加锁
                synchronized (ScrollRefreshRecyclerView.class) {
                    if (searchRvContent != null)
                        searchRvContent.finishRefresh();
                }
            }

            @Override
            public void loadMoreSearchBook(List<BookSearchBean> items) {
                mAdapter.addItems(items, keyword);
            }
        });
    }

    /************************************Event**************************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_book_search, menu);
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
            case R.id.action_source:
                startActivityForResult(new Intent(this, BookSourceActivity.class), REQUEST_CODE);
                break;
            case R.id.action_setting:
                startActivityForResult(new Intent(this, FragmentActivity.class), REQUEST_CODE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            mSearchEngine.initSearchEngine(BookSourceRepository.getInstance().getAllSelectedBookSource());
        }
    }


    /************************************Life Cycle*********************/
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //mSearchEngine.closeSearchEngine();
        mSearchEngine = null;
        //System.gc();
    }
}

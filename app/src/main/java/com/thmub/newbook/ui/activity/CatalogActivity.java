package com.thmub.newbook.ui.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;
import com.thmub.newbook.R;
import com.thmub.newbook.base.BaseActivity;
import com.thmub.newbook.bean.ShelfBookBean;
import com.thmub.newbook.ui.adapter.TabFragmentPageAdapter;
import com.thmub.newbook.ui.fragment.BookMarkFragment;
import com.thmub.newbook.ui.fragment.CatalogFragment;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

/**
 * Created by Zhouas666 on 2019-04-16
 * Github: https://github.com/zas023
 * <p>
 * 书籍目录activity
 */
public class CatalogActivity extends BaseActivity {

    public static final String EXTRA_BOOK = "extra_book";

    @BindView(R.id.catalog_tab)
    TabLayout catalogTab;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.catalog_vp)
    ViewPager viewPager;
    private SearchView searchView;

    private ShelfBookBean mBook;

    private TabFragmentPageAdapter tabAdapter;

    /*******************Public**********************************/

    public ShelfBookBean getShelfBook() {
        return mBook;
    }

    /*********************Initialization****************************/
    @Override
    protected int getLayoutId() {
        return R.layout.activity_catalog;
    }

    @Override
    protected boolean initSwipeBackEnable() {
        return false;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mBook = getIntent().getParcelableExtra(EXTRA_BOOK);
    }

    @Override
    protected void setUpToolbar(Toolbar toolbar) {
        super.setUpToolbar(toolbar);
        tabAdapter = new TabFragmentPageAdapter(getSupportFragmentManager());
        tabAdapter.addFragment(new CatalogFragment(), "目录");
        tabAdapter.addFragment(new BookMarkFragment(), "书签");
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        viewPager.setAdapter(tabAdapter);
        viewPager.setOffscreenPageLimit(2);
        catalogTab.setupWithViewPager(viewPager);
    }


    /*************************************************************************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_view_search, menu);
        MenuItem search = menu.findItem(R.id.action_search);
        searchView = (SearchView) search.getActionView();
        searchView.setMaxWidth(getResources().getDisplayMetrics().widthPixels);
        searchView.onActionViewCollapsed();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                switch (viewPager.getCurrentItem()){
                    case 0:
                        ((CatalogFragment) tabAdapter.getItem(0)).startSearch(newText);
                        break;
                    case 1:
                        ((BookMarkFragment) tabAdapter.getItem(1)).startSearch(newText);
                        break;
                }
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

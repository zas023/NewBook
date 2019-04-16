package com.thmub.newbook.ui.fragment;

import android.os.Bundle;

import com.thmub.newbook.R;
import com.thmub.newbook.base.BaseFragment;
import com.thmub.newbook.bean.ShelfBookBean;
import com.thmub.newbook.ui.activity.CatalogActivity;
import com.thmub.newbook.ui.adapter.DetailCatalogAdapter;
import com.thmub.newbook.widget.DashlineItemDivider;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * Created by Zhouas666 on 2019-04-16
 * Github: https://github.com/zas023
 * <p>
 * 书城fragment
 */
public class BookMarkFragment extends BaseFragment {

    /***************************View*****************************/
    @BindView(R.id.rv_content)
    RecyclerView rvContent;
    /***************************Variable*****************************/
    private ShelfBookBean mBook;
    private DetailCatalogAdapter mAdapter;

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
        mAdapter = new DetailCatalogAdapter();
        //recycler
        rvContent.setLayoutManager(new LinearLayoutManager(mContext));
        rvContent.setAdapter(mAdapter);
        rvContent.addItemDecoration(new DashlineItemDivider());
    }

    @Override
    protected void processLogic() {
        super.processLogic();
    }

    private CatalogActivity getFatherActivity() {
        return (CatalogActivity) getActivity();
    }

    /*****************************Transaction******************************/


}

package com.thmub.newbook.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import com.thmub.newbook.R;
import com.thmub.newbook.base.BaseFragment;
import com.thmub.newbook.bean.zhui.SortBean;
import com.thmub.newbook.ui.activity.BookSortDetailActivity;
import com.thmub.newbook.ui.adapter.BookSortAdapter;

import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;


/**
 * Created by Zhouas666 on 2019-04-18
 * Github: https://github.com/zas023
 */
public class BookSortFragment extends BaseFragment {

    @BindView(R.id.rv_content)
    RecyclerView rvContent;

    private BookSortAdapter mAdapter;

    private String mGender;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_base_list;
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        mAdapter = new BookSortAdapter();
        rvContent.setLayoutManager(new GridLayoutManager(mContext, 2));
        rvContent.setAdapter(mAdapter);
    }

    @Override
    protected void initClick() {
        super.initClick();
        mAdapter.setOnItemClickListener((view, pos) -> {
            startActivity(new Intent(mContext,BookSortDetailActivity.class)
            .putExtra(BookSortDetailActivity.EXTRA_GENDER,mGender)
            .putExtra(BookSortDetailActivity.EXTRA_SORT,mAdapter.getItem(pos).getName()));
        });
    }

    /*****************************Public***************************************/
    public void setSortBeans(String gender,List<SortBean> items) {
        mGender=gender;
        mAdapter.addItems(items);
    }
}

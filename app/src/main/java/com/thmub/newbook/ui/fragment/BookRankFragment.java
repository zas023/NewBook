package com.thmub.newbook.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import com.thmub.newbook.R;
import com.thmub.newbook.base.BaseFragment;
import com.thmub.newbook.bean.zhui.RankBean;
import com.thmub.newbook.ui.activity.BookRankDetailActivity;
import com.thmub.newbook.ui.adapter.BookRankAdapter;
import com.thmub.newbook.widget.DashlineItemDivider;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;


/**
 * Created by Zhouas666 on 2019-04-19
 * Github: https://github.com/zas023
 */
public class BookRankFragment extends BaseFragment {

    @BindView(R.id.rv_content)
    RecyclerView rvContent;

    private BookRankAdapter mAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_base_list;
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        mAdapter = new BookRankAdapter();
        rvContent.setLayoutManager(new LinearLayoutManager(mContext));
        rvContent.setAdapter(mAdapter);
        rvContent.addItemDecoration(new DashlineItemDivider());
    }

    @Override
    protected void initClick() {
        super.initClick();
        mAdapter.setOnItemClickListener((view, pos) -> {
            startActivity(new Intent(mContext, BookRankDetailActivity.class)
                    .putExtra(BookRankDetailActivity.EXTRA_RANK_ID, mAdapter.getItem(pos).get_id())
                    .putExtra(BookRankDetailActivity.EXTRA_RANK_TITLE, mAdapter.getItem(pos).getTitle()));
        });
    }

    /*****************************Public***************************************/
    public void setRankBeans(List<RankBean> items) {
        mAdapter.addItems(items);
    }
}

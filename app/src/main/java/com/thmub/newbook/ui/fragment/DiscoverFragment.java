package com.thmub.newbook.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import com.thmub.newbook.R;
import com.thmub.newbook.base.BaseFragment;
import com.thmub.newbook.bean.type.DiscoverType;
import com.thmub.newbook.ui.activity.BookRankActivity;
import com.thmub.newbook.ui.activity.BookSortActivity;
import com.thmub.newbook.ui.adapter.DiscoverAdapter;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * Created by Zhouas666 on 2019-04-17
 * Github: https://github.com/zas023
 * <p>
 * 主页发现fragment
 */
public class DiscoverFragment extends BaseFragment {

    @BindView(R.id.rv_content)
    RecyclerView rvContent;

    private DiscoverAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_base_list;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        ArrayList<DiscoverType> types = new ArrayList<>();

        for (DiscoverType type : DiscoverType.values()) {
            types.add(type);
        }

        mAdapter = new DiscoverAdapter();
        mAdapter.addItems(types);
    }

    @Override
    protected void initClick() {
        super.initClick();
        mAdapter.setOnItemClickListener((view, pos) -> {
            DiscoverType type = DiscoverType.values()[pos];
            //跳转
            switch (type) {
                case SORT:
                    startActivity(new Intent(getContext(), BookSortActivity.class));
                    break;
                case RANK:
                    startActivity(new Intent(getContext(), BookRankActivity.class));
                    break;
//                case TOPIC:
//                    intent = new Intent(getContext(), BookListActivity.class);
//                    startActivity(intent);
//                    break;
            }
        });
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);

        rvContent.setLayoutManager(new LinearLayoutManager(mContext));
        rvContent.setAdapter(mAdapter);
    }
}

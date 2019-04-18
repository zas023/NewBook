package com.thmub.newbook.ui.adapter;

import com.thmub.newbook.base.adapter.IViewHolder;
import com.thmub.newbook.base.adapter.QuickAdapter;
import com.thmub.newbook.bean.type.DiscoverType;
import com.thmub.newbook.ui.adapter.holder.DiscoverHolder;

/**
 * Created by Zhouas666 on 2019-04-17
 * Github: https://github.com/zas023
 */
public class DiscoverAdapter extends QuickAdapter<DiscoverType> {
    @Override
    protected IViewHolder<DiscoverType> createViewHolder(int viewType) {
        return new DiscoverHolder();
    }
}

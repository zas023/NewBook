package com.thmub.newbook.ui.adapter;

import com.thmub.newbook.base.adapter.IViewHolder;
import com.thmub.newbook.base.adapter.QuickAdapter;
import com.thmub.newbook.bean.zhui.RankBean;
import com.thmub.newbook.ui.adapter.holder.BookRankHolder;

/**
 * Created by Zhouas666 on 2019-04-19
 * Github: https://github.com/zas023
 */
public class BookRankAdapter extends QuickAdapter<RankBean> {
    @Override
    protected IViewHolder<RankBean> createViewHolder(int viewType) {
        return new BookRankHolder();
    }
}

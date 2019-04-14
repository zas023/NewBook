package com.thmub.newbook.ui.adapter;

import com.thmub.newbook.base.adapter.IViewHolder;
import com.thmub.newbook.base.adapter.QuickAdapter;
import com.thmub.newbook.bean.BookSearchBean;
import com.thmub.newbook.ui.adapter.holder.DetailFindHolder;

/**
 * Created by Zhouas666 on 2019-04-14
 * Github: https://github.com/zas023
 */
public class DetailFindAdapter extends QuickAdapter<BookSearchBean> {
    @Override
    protected IViewHolder<BookSearchBean> createViewHolder(int viewType) {
        return new DetailFindHolder();
    }
}

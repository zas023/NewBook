package com.thmub.newbook.ui.adapter;

import com.thmub.newbook.base.adapter.IViewHolder;
import com.thmub.newbook.base.adapter.QuickAdapter;
import com.thmub.newbook.bean.zhui.SortBean;
import com.thmub.newbook.ui.adapter.holder.BookSortHolder;

/**
 * Created by Zhouas666 on 2019-04-18
 * Github: https://github.com/zas023
 * <p>
 * 书籍分类adapter
 */
public class BookSortAdapter extends QuickAdapter<SortBean> {
    @Override
    protected IViewHolder<SortBean> createViewHolder(int viewType) {
        return new BookSortHolder();
    }
}

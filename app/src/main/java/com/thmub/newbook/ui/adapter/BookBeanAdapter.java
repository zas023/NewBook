package com.thmub.newbook.ui.adapter;

import android.content.Context;

import com.thmub.newbook.base.adapter.IViewHolder;
import com.thmub.newbook.base.adapter.QuickAdapter;
import com.thmub.newbook.bean.zhui.BookBean;
import com.thmub.newbook.ui.adapter.holder.BookBeanHolder;

/**
 * Created by Zhouas666 on 2019-04-18
 * Github: https://github.com/zas023
 */
public class BookBeanAdapter extends QuickAdapter<BookBean> {

    public BookBeanAdapter(Context context, Options options) {
        super(context, options);
    }
    @Override
    protected IViewHolder<BookBean> createViewHolder(int viewType) {
        return new BookBeanHolder();
    }
}

package com.thmub.newbook.ui.adapter;

import android.content.Context;

import com.thmub.newbook.base.adapter.IViewHolder;
import com.thmub.newbook.base.adapter.QuickAdapter;
import com.thmub.newbook.bean.zhui.ThemeBean;
import com.thmub.newbook.ui.adapter.holder.BookThemeHolder;

/**
 * Created by Zhouas666 on 2019-04-27
 * Github: https://github.com/zas023
 */
public class BookThemeAdapter extends QuickAdapter<ThemeBean> {

    public BookThemeAdapter(Context mContext, Options options) {
        super(mContext, options);
    }

    @Override
    protected IViewHolder<ThemeBean> createViewHolder(int viewType) {
        return new BookThemeHolder();
    }
}

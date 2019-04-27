package com.thmub.newbook.ui.adapter;

import com.thmub.newbook.base.adapter.IViewHolder;
import com.thmub.newbook.base.adapter.QuickAdapter;
import com.thmub.newbook.bean.zhui.ThemeBookBean;
import com.thmub.newbook.ui.adapter.holder.ThemeBookHolder;

/**
 * Created by Zhouas666 on 2019-04-27
 * Github: https://github.com/zas023
 */
public class ThemeBookAdapter extends QuickAdapter<ThemeBookBean> {
    @Override
    protected IViewHolder<ThemeBookBean> createViewHolder(int viewType) {
        return new ThemeBookHolder();
    }
}

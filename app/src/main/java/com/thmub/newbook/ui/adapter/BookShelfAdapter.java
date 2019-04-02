package com.thmub.newbook.ui.adapter;

import com.thmub.newbook.base.adapter.IViewHolder;
import com.thmub.newbook.base.adapter.QuickAdapter;
import com.thmub.newbook.bean.ShelfBookBean;
import com.thmub.newbook.ui.adapter.holder.BookShelfHolder;

/**
 * Created by Zhouas666 on 2019-04-02
 * Github: https://github.com/zas023
 */
public class BookShelfAdapter extends QuickAdapter<ShelfBookBean> {

    @Override
    protected IViewHolder<ShelfBookBean> createViewHolder(int viewType) {
        return new BookShelfHolder();
    }

}

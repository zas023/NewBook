package com.thmub.newbook.ui.adapter;

import com.thmub.newbook.base.adapter.IViewHolder;
import com.thmub.newbook.base.adapter.QuickAdapter;
import com.thmub.newbook.bean.BookChapterBean;
import com.thmub.newbook.ui.adapter.holder.CatalogHolder;

/**
 * Created by Zhouas666 on 2019-03-27
 * Github: https://github.com/zas023
 */
public class CatalogAdapter extends QuickAdapter<BookChapterBean> {

    private int currentSelected = 0;

    @Override
    protected IViewHolder<BookChapterBean> createViewHolder(int viewType) {
        return new CatalogHolder(currentSelected);
    }

    public void setChapter(int pos){
        currentSelected = pos;
        notifyDataSetChanged();
    }
}

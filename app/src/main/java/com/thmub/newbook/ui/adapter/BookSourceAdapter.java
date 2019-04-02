package com.thmub.newbook.ui.adapter;

import com.thmub.newbook.base.adapter.IViewHolder;
import com.thmub.newbook.base.adapter.QuickAdapter;
import com.thmub.newbook.bean.BookSourceBean;
import com.thmub.newbook.ui.adapter.holder.BookSourceHolder;

import java.util.Collections;

/**
 * Created by Zhouas666 on 2019-03-30
 * Github: https://github.com/zas023
 */
public class BookSourceAdapter extends QuickAdapter<BookSourceBean> {

    @Override
    protected IViewHolder<BookSourceBean> createViewHolder(int viewType) {
        return new BookSourceHolder();
    }

    public void onMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(mList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(mList, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    public void onSwiped(int position) {
        mList.remove(position);
        notifyItemRemoved(position);
    }

}

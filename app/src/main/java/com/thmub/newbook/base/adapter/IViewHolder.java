package com.thmub.newbook.base.adapter;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by zhouas666 on 18-2-17.
 */

public interface IViewHolder<T> {
    View createItemView(ViewGroup parent);
    void initView();
    void onBind(T data, int pos);
    void onClick();
}

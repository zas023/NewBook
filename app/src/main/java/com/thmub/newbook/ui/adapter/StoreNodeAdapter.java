package com.thmub.newbook.ui.adapter;

import android.util.Log;

import com.thmub.newbook.base.adapter.IViewHolder;
import com.thmub.newbook.base.adapter.QuickAdapter;
import com.thmub.newbook.bean.zhui.StoreNodeBean;
import com.thmub.newbook.ui.adapter.holder.StoreNodeHolder;

/**
 * Created by Zhouas666 on 2019-04-13
 * Github: https://github.com/zas023
 */
public class StoreNodeAdapter extends QuickAdapter<StoreNodeBean> {
    @Override
    protected IViewHolder<StoreNodeBean> createViewHolder(int viewType) {
        Log.i("StoreNodeAdapter", String.valueOf(viewType));
        return new StoreNodeHolder();
    }
}

package com.thmub.newbook.ui.adapter;

import com.thmub.newbook.base.adapter.IViewHolder;
import com.thmub.newbook.base.adapter.QuickAdapter;
import com.thmub.newbook.bean.zhui.StoreNodeBookBean;
import com.thmub.newbook.ui.adapter.holder.StoreNodeBookHolder;

/**
 * Created by Zhouas666 on 2019-04-13
 * Github: https://github.com/zas023
 */
public class StoreNodeBookAdapter extends QuickAdapter<StoreNodeBookBean> {
    @Override
    protected IViewHolder<StoreNodeBookBean> createViewHolder(int viewType) {
        return new StoreNodeBookHolder();
    }
}

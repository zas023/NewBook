package com.thmub.newbook.ui.adapter;


import com.thmub.newbook.base.adapter.IViewHolder;
import com.thmub.newbook.base.adapter.QuickAdapter;
import com.thmub.newbook.bean.zhui.BookBean;
import com.thmub.newbook.bean.zhui.StoreNodeBean;
import com.thmub.newbook.ui.adapter.holder.StoreNodeHolder;

/**
 * Created by Zhouas666 on 2019-04-13
 * Github: https://github.com/zas023
 */
public class StoreNodeAdapter extends QuickAdapter<StoreNodeBean> {

    private StoreNodeHolder.OnClickListener onClickListener;

    public StoreNodeAdapter(StoreNodeHolder.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    protected IViewHolder<StoreNodeBean> createViewHolder(int viewType) {
        StoreNodeHolder holder=new StoreNodeHolder();
        holder.setOnClickListener(new StoreNodeHolder.OnClickListener() {
            @Override
            public void onMore(StoreNodeBean item) {
                onClickListener.onMore(item);
            }

            @Override
            public void onItemClick(BookBean book) {
                onClickListener.onItemClick(book);
            }
        });
        return holder;
    }

}

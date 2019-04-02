package com.thmub.newbook.ui.adapter.holder;

import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.thmub.newbook.R;
import com.thmub.newbook.base.adapter.ViewHolderImpl;
import com.thmub.newbook.bean.BookSourceBean;


/**
 * Created by Zhouas666 on 2019-03-30
 * Github: https://github.com/zas023
 */
public class BookSourceHolder extends ViewHolderImpl<BookSourceBean> {


    private CheckBox itemSourceCb;
    private TextView itemSourceTvTitle;
    private ImageView itemSourceIvRoof;

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_book_source;
    }

    @Override
    public void initView() {
        itemSourceCb = findById(R.id.item_source_cb);
        itemSourceTvTitle = findById(R.id.item_source_tv_title);
        itemSourceIvRoof = findById(R.id.item_source_iv_roof);
    }

    @Override
    public void onBind(BookSourceBean data, int pos) {
        //设置排序数
        data.setOrderNumber(pos);
        itemSourceTvTitle.setText(data.getSourceName());
        itemSourceCb.setChecked(data.isSelected());
        //单选框监听
        itemSourceCb.setOnCheckedChangeListener((buttonView, isChecked) -> {
            data.setSelected(isChecked);
        });
    }

}

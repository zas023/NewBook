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
 * <p>
 * 书源holder
 */
public class BookSourceHolder extends ViewHolderImpl<BookSourceBean> {

    CheckBox itemCb;
    TextView itemTvTitle;
    ImageView itemIvEdit;
    ImageView itemIvDelete;

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_book_source;
    }

    @Override
    public void initView() {
        itemCb = findById(R.id.item_cb);
        itemTvTitle = findById(R.id.item_tv_title);
        itemIvEdit = findById(R.id.item_iv_edit);
        itemIvDelete = findById(R.id.item_iv_delete);
    }

    @Override
    public void onBind(BookSourceBean data, int pos) {
        //设置排序数
        data.setOrderNumber(pos);
        itemTvTitle.setText(data.getSourceName());
        itemCb.setChecked(data.isSelected());
        //单选框监听
        itemCb.setOnCheckedChangeListener((buttonView, isChecked) -> data.setSelected(isChecked));
    }

}

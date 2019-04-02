package com.thmub.newbook.ui.adapter.holder;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.thmub.newbook.R;
import com.thmub.newbook.base.adapter.ViewHolderImpl;
import com.thmub.newbook.bean.ShelfBookBean;

/**
 * Created by Zhouas666 on 2019-04-02
 * Github: https://github.com/zas023
 * <p>
 * 书架书籍holder
 */
public class BookShelfHolder extends ViewHolderImpl<ShelfBookBean> {

    ImageView itemIvCover;
    TextView itemTvTitle;
    TextView itemTvAuthor;
    TextView itemTvInfo;
    TextView itemTvSource;

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_book_shelf;
    }

    @Override
    public void initView() {
        itemIvCover = findById((R.id.item_shelf_iv_cover));
        itemTvTitle = findById((R.id.item_shelf_tv_title));
        itemTvAuthor = findById((R.id.item_shelf_tv_author));
        itemTvInfo = findById((R.id.item_shelf_tv_info));
        itemTvSource = findById((R.id.item_shelf_tv_source));
    }

    @Override
    public void onBind(ShelfBookBean data, int pos) {
        Glide.with(getContext()).load(data.getCover()).into(itemIvCover);
        itemTvTitle.setText(data.getTitle());
        itemTvAuthor.setText(data.getAuthor());
        itemTvInfo.setText(data.getDesc());
        itemTvSource.setText(data.getSource());
    }
}

package com.thmub.newbook.ui.adapter.holder;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.thmub.newbook.R;
import com.thmub.newbook.base.adapter.ViewHolderImpl;
import com.thmub.newbook.bean.zhui.BookBean;
import com.thmub.newbook.bean.zhui.StoreNodeBookBean;

/**
 * Created by Zhouas666 on 2019-04-13
 * Github: https://github.com/zas023
 */
public class StoreBookListHolder extends ViewHolderImpl<StoreNodeBookBean> {

    ImageView itemSearchIvCover;
    TextView itemSearchTvTitle;
    TextView itemSearchTvAuthor;
    TextView itemSearchTvInfo;
    TextView itemSearchTvSource;

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_book_search;
    }

    @Override
    public void initView() {
        itemSearchIvCover = findById((R.id.item_search_iv_cover));
        itemSearchTvTitle = findById((R.id.item_search_tv_title));
        itemSearchTvAuthor = findById((R.id.item_search_tv_author));
        itemSearchTvInfo = findById((R.id.item_search_tv_info));
        itemSearchTvSource = findById((R.id.item_search_tv_source));
    }

    @Override
    public void onBind(StoreNodeBookBean data, int pos) {
        BookBean book = data.getBook();
        Glide.with(getContext()).load(book.getCover()).into(itemSearchIvCover);
        itemSearchTvTitle.setText(book.getTitle());
        itemSearchTvAuthor.setText(book.getAuthor());
        itemSearchTvInfo.setText(book.getLongIntro());
        itemSearchTvSource.setText(book.getMajorCate());
    }
}

package com.thmub.newbook.ui.adapter.holder;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.thmub.newbook.R;
import com.thmub.newbook.base.adapter.ViewHolderImpl;
import com.thmub.newbook.bean.BookSearchBean;

/**
 * Created by Zhouas666 on 2019-03-27
 * Github: https://github.com/zas023
 */
public class SearchBookHolder extends ViewHolderImpl<BookSearchBean> {

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
    public void onBind(BookSearchBean data, int pos) {
        Glide.with(getContext()).load(data.getCover())
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(8)))
                .into(itemSearchIvCover);
        itemSearchTvTitle.setText(data.getTitle());
        itemSearchTvAuthor.setText(data.getAuthor());
        itemSearchTvInfo.setText(data.getDesc());
        itemSearchTvSource.setText("共("+data.getSourceUrls().size()+")源："+data.getSourceUrls());
    }
}

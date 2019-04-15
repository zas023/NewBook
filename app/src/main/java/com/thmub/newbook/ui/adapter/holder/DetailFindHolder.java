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
 * Created by Zhouas666 on 2019-04-14
 * Github: https://github.com/zas023
 */
public class DetailFindHolder extends ViewHolderImpl<BookSearchBean> {

    TextView itemTvTitle;
    ImageView itemIvCover;

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_store_node_book;
    }

    @Override
    public void initView() {
        itemTvTitle = findById(R.id.item_tv_title);
        itemIvCover = findById(R.id.item_iv_cover);
    }

    @Override
    public void onBind(BookSearchBean data, int pos) {
        if (data.getCover() != null)
            Glide.with(getContext()).load(data.getCover())
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(8)))
                    .into(itemIvCover);
        itemTvTitle.setText(data.getTitle());
    }
}

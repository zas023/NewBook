package com.thmub.newbook.ui.adapter.holder;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.thmub.newbook.R;
import com.thmub.newbook.base.adapter.ViewHolderImpl;
import com.thmub.newbook.bean.zhui.BookBean;
import com.thmub.newbook.bean.zhui.StoreNodeBookBean;

/**
 * Created by Zhouas666 on 2019-04-13
 * Github: https://github.com/zas023
 */
public class StoreBookListHolder extends ViewHolderImpl<StoreNodeBookBean> {

    ImageView itemIvCover;
    TextView itemTvTitle;
    TextView itemTvAuthor;
    TextView itemTvInfo;
    TextView itemTvSource;

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_book_list;
    }

    @Override
    public void initView() {
        itemIvCover = findById((R.id.item_iv_cover));
        itemTvTitle = findById((R.id.item_tv_title));
        itemTvAuthor = findById((R.id.item_tv_author));
        itemTvInfo = findById((R.id.item_tv_info));
        itemTvSource = findById((R.id.item_tv_source));
    }

    @Override
    public void onBind(StoreNodeBookBean data, int pos) {
        BookBean book = data.getBook();
        Glide.with(getContext()).load(book.getCover())
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(8)))
                .into(itemIvCover);
        itemTvTitle.setText(book.getTitle());
        itemTvAuthor.setText(book.getAuthor());
        itemTvInfo.setText(book.getLongIntro());
        itemTvSource.setText(book.getMajorCate());
    }
}

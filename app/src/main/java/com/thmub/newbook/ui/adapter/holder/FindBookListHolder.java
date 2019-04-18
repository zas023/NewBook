package com.thmub.newbook.ui.adapter.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.thmub.newbook.R;
import com.thmub.newbook.base.adapter.ViewHolderImpl;
import com.thmub.newbook.bean.BookSearchBean;
import com.thmub.newbook.utils.BitmapUtils;


/**
 * Created by Zhouas666 on 2019-04-16
 * Github: https://github.com/zas023
 */
public class FindBookListHolder extends ViewHolderImpl<BookSearchBean> {

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
    public void onBind(BookSearchBean data, int pos) {
        if (data.getCover() != null)
            Glide.with(getContext()).load(data.getCover())
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(8)))
                    .into(itemIvCover);
        else
            itemIvCover.setImageBitmap(BitmapUtils.getTextBitMap(data.getTitle()));
        itemTvTitle.setText(data.getTitle());
        if (data.getAuthor() != null)
            itemTvAuthor.setText(data.getAuthor());
        else
            itemTvAuthor.setVisibility(View.GONE);
        if (data.getDesc() != null)
            itemTvInfo.setText(data.getDesc());
        else
            itemTvInfo.setText(data.getBookLink());
        itemTvSource.setText(data.getSourceTag());
    }
}

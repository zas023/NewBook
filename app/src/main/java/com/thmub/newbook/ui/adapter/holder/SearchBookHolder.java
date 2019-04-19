package com.thmub.newbook.ui.adapter.holder;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.thmub.newbook.R;
import com.thmub.newbook.base.adapter.ViewHolderImpl;
import com.thmub.newbook.bean.BookSearchBean;
import com.thmub.newbook.utils.BitmapUtils;

import butterknife.BindView;

/**
 * Created by Zhouas666 on 2019-03-27
 * Github: https://github.com/zas023
 * <p>
 * 搜索列表holder
 */
public class SearchBookHolder extends ViewHolderImpl<BookSearchBean> {


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
        itemTvAuthor.setText(data.getAuthor());
        itemTvInfo.setText(data.getDesc());
        itemTvSource.setText("共(" + data.getSourceUrls().size() + ")源：" + data.getSourceUrls());
    }
}

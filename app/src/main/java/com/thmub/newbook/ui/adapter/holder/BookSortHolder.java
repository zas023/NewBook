package com.thmub.newbook.ui.adapter.holder;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.thmub.newbook.R;
import com.thmub.newbook.base.adapter.ViewHolderImpl;
import com.thmub.newbook.bean.zhui.SortBean;
import com.thmub.newbook.constant.Constant;
import com.thmub.newbook.utils.UiUtils;


/**
 * Created by Zhouas666 on 2019-04-18
 * Github: https://github.com/zas023
 */
public class BookSortHolder extends ViewHolderImpl<SortBean> {

    ImageView itemIvCover;
    TextView itemTvType;
    TextView itemTvCount;

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_book_sort;
    }

    @Override
    public void initView() {
        itemIvCover = findById(R.id.item_iv_cover);
        itemTvType = findById(R.id.item_tv_type);
        itemTvCount = findById(R.id.item_tv_count);
    }

    @Override
    public void onBind(SortBean data, int pos) {
        Glide.with(getContext()).load(Constant.IMG_BASE_URL + data.getBookCover().get(0)).into(itemIvCover);
        itemTvType.setText(data.getName());
        itemTvCount.setText(String.format(UiUtils.getString(R.string.book_sort_count), data.getBookCount()));
    }
}

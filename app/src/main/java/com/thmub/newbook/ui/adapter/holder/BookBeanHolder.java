package com.thmub.newbook.ui.adapter.holder;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.thmub.newbook.R;
import com.thmub.newbook.base.adapter.ViewHolderImpl;
import com.thmub.newbook.bean.zhui.BookBean;
import com.thmub.newbook.constant.Constant;

import butterknife.BindView;


/**
 * Created by Zhouas666 on 2019-04-18
 * Github: https://github.com/zas023
 */
public class BookBeanHolder extends ViewHolderImpl<BookBean> {

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
        itemIvCover = findById(R.id.item_iv_cover);
        itemTvTitle = findById(R.id.item_tv_title);
        itemTvAuthor = findById(R.id.item_tv_author);
        itemTvInfo = findById(R.id.item_tv_info);
        itemTvSource = findById(R.id.item_tv_source);
    }

    @Override
    public void onBind(BookBean data, int pos) {
        Glide.with(getContext()).load(Constant.IMG_BASE_URL + data.getCover()).into(itemIvCover);
        itemTvTitle.setText(data.getTitle());
        itemTvAuthor.setText(data.getAuthor());
        if (data.getShortIntro() == null)
            itemTvInfo.setText(data.getLongIntro());
        else
            itemTvInfo.setText(data.getShortIntro());
        itemTvSource.setText(data.getMajorCate());
    }

}

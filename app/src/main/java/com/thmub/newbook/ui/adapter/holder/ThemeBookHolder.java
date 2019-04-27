package com.thmub.newbook.ui.adapter.holder;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.thmub.newbook.R;
import com.thmub.newbook.base.adapter.ViewHolderImpl;
import com.thmub.newbook.bean.zhui.ThemeBean;
import com.thmub.newbook.bean.zhui.ThemeBookBean;
import com.thmub.newbook.constant.Constant;
import com.thmub.newbook.utils.UiUtils;


/**
 * Created by Zhouas666 on 2019-04-18
 * Github: https://github.com/zas023
 */
public class ThemeBookHolder extends ViewHolderImpl<ThemeBookBean> {

    ImageView itemIvCover;
    TextView itemTvTitle;
    TextView itemTvAuthor;
    TextView itemTvInfo;
    TextView itemTvSource;

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_book_theme;
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
    public void onBind(ThemeBookBean data, int pos) {
        Glide.with(getContext()).load(Constant.IMG_BASE_URL + data.getBook().getCover()).into(itemIvCover);
        itemTvTitle.setText(data.getBook().getTitle());
        itemTvAuthor.setText(data.getBook().getAuthor());
        itemTvInfo.setText(data.getComment());
        itemTvSource.setText(data.getBook().getMajorCate());
    }

}

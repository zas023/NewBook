package com.thmub.newbook.ui.adapter.holder;

import android.widget.TextView;

import com.thmub.newbook.R;
import com.thmub.newbook.base.adapter.ViewHolderImpl;
import com.thmub.newbook.bean.zhui.RankBean;


/**
 * Created by Zhouas666 on 2019-04-19
 * Github: https://github.com/zas023
 */
public class BookRankHolder extends ViewHolderImpl<RankBean> {

    TextView itemTvTitle;

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_book_rank;
    }

    @Override
    public void initView() {
        itemTvTitle = findById(R.id.item_tv_title);
    }

    @Override
    public void onBind(RankBean data, int pos) {
        itemTvTitle.setText(data.getTitle());
    }
}

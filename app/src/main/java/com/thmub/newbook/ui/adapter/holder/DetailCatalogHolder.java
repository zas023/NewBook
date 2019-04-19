package com.thmub.newbook.ui.adapter.holder;

import android.widget.TextView;

import com.thmub.newbook.R;
import com.thmub.newbook.base.adapter.ViewHolderImpl;
import com.thmub.newbook.bean.BookChapterBean;

/**
 * Created by Zhouas666 on 2019-04-02
 * Github: https://github.com/zas023
 * <p>
 * 详情页目录holder
 */
public class DetailCatalogHolder extends ViewHolderImpl<BookChapterBean> {

    private TextView itemTvBookInfo;

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_book_detail_info;
    }

    @Override
    public void initView() {
        itemTvBookInfo = findById(R.id.item_book_detail_info);
    }

    @Override
    public void onBind(BookChapterBean data, int pos) {

        itemTvBookInfo.setText(data.getChapterTitle());
    }
}

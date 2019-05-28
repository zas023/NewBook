package com.thmub.newbook.ui.adapter.holder;

import android.widget.TextView;

import com.thmub.newbook.R;
import com.thmub.newbook.base.adapter.ViewHolderImpl;
import com.thmub.newbook.bean.BookChapterBean;
import com.thmub.newbook.bean.BookMarkBean;

/**
 * Created by Zhouas666 on 2019-04-02
 * Github: https://github.com/zas023
 * <p>
 * 书签holder
 */
public class BookMarkHolder extends ViewHolderImpl<BookMarkBean> {

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
    public void onBind(BookMarkBean data, int pos) {

        itemTvBookInfo.setText(data.getChapterTitle());
    }
}

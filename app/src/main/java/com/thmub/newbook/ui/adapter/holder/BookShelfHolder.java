package com.thmub.newbook.ui.adapter.holder;

import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.thmub.newbook.R;
import com.thmub.newbook.base.adapter.ViewHolderImpl;
import com.thmub.newbook.bean.ShelfBookBean;

import butterknife.BindView;

/**
 * Created by Zhouas666 on 2019-04-02
 * Github: https://github.com/zas023
 * <p>
 * 书架书籍holder
 */
public class BookShelfHolder extends ViewHolderImpl<ShelfBookBean> {

    ImageView itemIvCover;
    TextView itemTvTitle;
    TextView itemTvAuthor;
    TextView itemTvRecord;
    TextView itemTvLast;
    ProgressBar itemPl;

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_book_shelf;
    }

    @Override
    public void initView() {
        itemIvCover = findById((R.id.item_shelf_iv_cover));
        itemTvTitle = findById((R.id.item_shelf_tv_title));
        itemTvAuthor = findById((R.id.item_shelf_tv_author));
        itemTvRecord = findById((R.id.item_shelf_tv_record));
        itemTvLast = findById((R.id.item_shelf_tv_last));
        itemPl = findById((R.id.item_shelf_pl));
    }

    @Override
    public void onBind(ShelfBookBean data, int pos) {
        Glide.with(getContext()).load(data.getCover()).into(itemIvCover);
        itemTvTitle.setText(data.getTitle());
        itemTvAuthor.setText(data.getAuthor());

        itemTvRecord.setText("第" + (data.getCurChapter() + 1) + "章 " + data.getCurChapterTitle());
        itemTvLast.setText("第" + data.getChapterCount() + "章 " + data.getLatestChapter());

        itemPl.setMax(data.getChapterCount());
        itemPl.setProgress(data.getCurChapter());
    }
}

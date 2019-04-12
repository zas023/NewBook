package com.thmub.newbook.ui.adapter.holder;

import android.graphics.drawable.Drawable;
import android.widget.TextView;

import com.thmub.newbook.R;
import com.thmub.newbook.base.adapter.ViewHolderImpl;
import com.thmub.newbook.bean.BookChapterBean;
import com.thmub.newbook.manager.BookManager;

import androidx.core.content.ContextCompat;

/**
 * Created by Zhouas666 on 2019-03-27
 * Github: https://github.com/zas023
 */
public class CatalogHolder extends ViewHolderImpl<BookChapterBean> {


    private TextView itemCatalogTvTitle;

    private int currentSelected;

    public CatalogHolder(int pos) {
        this.currentSelected = pos;
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_catalog;
    }

    @Override
    public void initView() {
        itemCatalogTvTitle = findById((R.id.item_catalog_tv_title));
    }

    @Override
    public void onBind(BookChapterBean data, int pos) {
        //首先判断是否该章已下载
        Drawable drawable;

        //如果没有链接地址表示是本地文件
        if (data.getBookLink() == null) {
            drawable = ContextCompat.getDrawable(getContext(), R.drawable.selector_category_load);
        } else {
            if (data.getChapterLink() != null && BookManager.isChapterCached(data.getBookLink(), data.getChapterTitle())) {
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.selector_category_load);
            } else {
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.selector_category_unload);
            }
        }

        itemCatalogTvTitle.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        itemCatalogTvTitle.setText(data.getChapterTitle());
        //选中
        if (currentSelected == pos) {
            itemCatalogTvTitle.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
            itemCatalogTvTitle.setSelected(true);
        }else {
            itemCatalogTvTitle.setTextColor(ContextCompat.getColor(getContext(), R.color.textPrimary));
            itemCatalogTvTitle.setSelected(false);
        }
    }
}

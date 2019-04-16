package com.thmub.newbook.ui.adapter;

import com.futuremind.recyclerviewfastscroll.SectionTitleProvider;
import com.thmub.newbook.base.adapter.IViewHolder;
import com.thmub.newbook.base.adapter.QuickAdapter;
import com.thmub.newbook.bean.BookChapterBean;
import com.thmub.newbook.ui.adapter.holder.DetailCatalogHolder;

/**
 * Created by Zhouas666 on 2019-04-02
 * Github: https://github.com/zas023
 * <p>
 * 书籍详情页adapter
 */
public class DetailCatalogAdapter extends QuickAdapter<BookChapterBean> implements SectionTitleProvider {

    @Override
    protected IViewHolder<BookChapterBean> createViewHolder(int viewType) {
        return new DetailCatalogHolder();
    }

    @Override
    public String getSectionTitle(int position) {
        return mList.get(position).getChapterTitle().substring(0, 1);
    }
}

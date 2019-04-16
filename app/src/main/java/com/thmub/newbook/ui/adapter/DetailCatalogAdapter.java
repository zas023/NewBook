package com.thmub.newbook.ui.adapter;

import android.util.Log;
import android.widget.Filter;
import android.widget.Filterable;

import com.futuremind.recyclerviewfastscroll.SectionTitleProvider;
import com.thmub.newbook.base.adapter.IViewHolder;
import com.thmub.newbook.base.adapter.QuickAdapter;
import com.thmub.newbook.bean.BookChapterBean;
import com.thmub.newbook.ui.adapter.holder.DetailCatalogHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhouas666 on 2019-04-02
 * Github: https://github.com/zas023
 * <p>
 * 书籍详情页adapter
 */
public class DetailCatalogAdapter extends QuickAdapter<BookChapterBean> implements SectionTitleProvider, Filterable {

    private List<BookChapterBean> mSourceList = new ArrayList<>();
    private boolean isFirst = true;

    private int selectedIndex;

    public void setSelectedIndex(int index) {
        this.selectedIndex = index;
        notifyItemChanged(selectedIndex, 0);
    }

    @Override
    protected IViewHolder<BookChapterBean> createViewHolder(int viewType) {
        return new DetailCatalogHolder();
    }

    @Override
    public void addItems(List<BookChapterBean> values) {
        super.addItems(values);
        //后面还需要此接口交换数据，防止原始数据被修改
        if (isFirst) {
            mSourceList = values;
            isFirst = false;
        }
    }

    /**
     * 快速滑动模块
     *
     * @param position
     * @return
     */
    @Override
    public String getSectionTitle(int position) {
        return mList.get(position).getChapterTitle().substring(0, 1);
    }

    /**
     * 过滤器，实现搜索
     *
     * @return
     */
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                List<BookChapterBean> mFilterList = new ArrayList<>();
                if (charString.isEmpty()) {
                    //没有过滤的内容，则使用源数据
                    mFilterList = mSourceList;
                } else {
                    for (BookChapterBean chapter : mSourceList) {
                        //这里根据需求，添加匹配规则
                        if (chapter.getChapterTitle().contains(charString)) {
                            mFilterList.add(chapter);
                        }
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilterList;
                return filterResults;
            }

            //把过滤后的值返回出来
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                clear();
                addItems((ArrayList<BookChapterBean>) results.values);
            }
        };
    }
}

package com.thmub.newbook.ui.adapter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;


import com.futuremind.recyclerviewfastscroll.SectionTitleProvider;
import com.thmub.newbook.R;
import com.thmub.newbook.bean.BookChapterBean;
import com.thmub.newbook.bean.ShelfBookBean;
import com.thmub.newbook.manager.BookManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import static com.thmub.newbook.utils.UiUtils.getContext;

/**
 * Created by Zhouas666 on 2019-03-27
 * Github: https://github.com/zas023
 */
public class CatalogAdapter extends RecyclerView.Adapter<CatalogAdapter.ViewHolder>
        implements SectionTitleProvider, Filterable {

    private ShelfBookBean mShelfBook;

    private List<BookChapterBean> mList;

    private OnItemClickListener listener;


    public CatalogAdapter() {
        mList = new ArrayList<>();
    }

    public void setShelfBook(ShelfBookBean mShelfBook) {
        this.mShelfBook = mShelfBook;
        mList = mShelfBook.getBookChapterList();
        notifyDataSetChanged();
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_catalog, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BookChapterBean data = mList.get(position);
        //首先判断是否该章已下载
        Drawable drawable;
        //如果没有链接地址表示是本地文件
        if (data.getBookLink() == null) {
            drawable = ContextCompat.getDrawable(getContext(), R.drawable.selector_category_load);
        } else {
            //判断是否缓存
            if (data.getChapterLink() != null && BookManager.isChapterCached(
                    mShelfBook.getTitle() + File.separator + mShelfBook.getSourceTag(),
                    BookManager.formatFileName(data.getChapterIndex(), data.getChapterTitle()))) {
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.selector_category_load);
            } else {
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.selector_category_unload);
            }
        }

        holder.tvTitle.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        holder.tvTitle.setText(data.getChapterTitle());
        //选中
        if (position == mShelfBook.getCurChapter()) {
            holder.tvTitle.setTextColor(ContextCompat.getColor(getContext(), R.color.md_red_400));
            holder.tvTitle.setSelected(true);
        } else {
            holder.tvTitle.setTextColor(ContextCompat.getColor(getContext(), R.color.textPrimary));
            holder.tvTitle.setSelected(false);
        }
        //注意此处不能传递 position
        holder.tvTitle.setOnClickListener(v -> listener.onItemClick(data.getChapterIndex(), 0));
    }

    @Override
    public int getItemCount() {
        if (mShelfBook == null) return 0;
        return mList.size();
    }


    @Override
    public String getSectionTitle(int position) {
        return String.valueOf(position);
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
                    mFilterList = mShelfBook.getBookChapterList();
                } else {
                    for (BookChapterBean chapter : mShelfBook.getBookChapterList()) {
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
                mList = ((ArrayList<BookChapterBean>) results.values);
                notifyDataSetChanged();
            }
        };
    }

    /****************************************************************************/
    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;

        ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.item_catalog_tv_title);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int index, int page);
    }


}

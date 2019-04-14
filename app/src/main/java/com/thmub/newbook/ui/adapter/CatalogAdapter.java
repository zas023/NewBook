package com.thmub.newbook.ui.adapter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.thmub.newbook.R;
import com.thmub.newbook.bean.BookChapterBean;
import com.thmub.newbook.bean.ShelfBookBean;
import com.thmub.newbook.manager.BookManager;

import java.io.File;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import static com.thmub.newbook.utils.UiUtils.getContext;

/**
 * Created by Zhouas666 on 2019-03-27
 * Github: https://github.com/zas023
 */
public class CatalogAdapter extends RecyclerView.Adapter<CatalogAdapter.ViewHolder> {

    private ShelfBookBean mShelfBook;

    private OnItemClickListener listener;

    private int currentSelected;

    public CatalogAdapter(ShelfBookBean mShelfBook) {
        this.mShelfBook = mShelfBook;
        currentSelected = mShelfBook.getCurChapter();
    }

    public void setSelectedChapter(int currentSelected) {
        this.currentSelected = currentSelected;
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
        BookChapterBean data = mShelfBook.getChapter(position);
        //首先判断是否该章已下载
        Drawable drawable;
        //如果没有链接地址表示是本地文件
        if (data.getBookLink() == null) {
            drawable = ContextCompat.getDrawable(getContext(), R.drawable.selector_category_load);
        } else {
            //判断是否缓存
            if (data.getChapterLink() != null && BookManager.isChapterCached(
                    mShelfBook.getTitle() + File.separator + mShelfBook.getSourceName(),
                    BookManager.formatFileName(data.getChapterIndex(), data.getChapterTitle()))) {
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.selector_category_load);
            } else {
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.selector_category_unload);
            }
        }

        holder.tvTitle.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        holder.tvTitle.setText(getContext().getString(R.string.read_catalog_title
                , position+1, data.getChapterTitle()));
        //选中
        if (currentSelected == position) {
            holder.tvTitle.setTextColor(ContextCompat.getColor(getContext(), R.color.md_red_400));
            holder.tvTitle.setSelected(true);
        } else {
            holder.tvTitle.setTextColor(ContextCompat.getColor(getContext(), R.color.textPrimary));
            holder.tvTitle.setSelected(false);
        }

        holder.tvTitle.setOnClickListener(v -> listener.onItemClick(position, 0));
    }

    @Override
    public int getItemCount() {
        if (mShelfBook == null)
            return 0;
        return mShelfBook.getBookChapterListSize();
    }

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

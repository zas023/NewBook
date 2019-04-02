package com.thmub.newbook.ui.adapter;

import android.app.Activity;

import com.thmub.newbook.base.adapter.IViewHolder;
import com.thmub.newbook.base.adapter.QuickAdapter;
import com.thmub.newbook.bean.BookSearchBean;
import com.thmub.newbook.ui.adapter.holder.SearchBookHolder;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhouas666 on 2019-03-27
 * Github: https://github.com/zas023
 */
public class SearchBookAdapter extends QuickAdapter<BookSearchBean> {

    private WeakReference<Activity> activityRef;

    public SearchBookAdapter(Activity activity) {
        this.activityRef = new WeakReference<>(activity);
    }


    @Override
    protected IViewHolder<BookSearchBean> createViewHolder(int viewType) {
        return new SearchBookHolder();
    }

    @Override
    public void addItems(List<BookSearchBean> values) {
        synchronized (this) {
            List<BookSearchBean> bookSearchBeansAdd = new ArrayList<>();
            //存在
            for (BookSearchBean temp : values) {
                Boolean isExsit = false;
                for (BookSearchBean searchBook : mList) {
                    if (temp.getTitle().equals(searchBook.getTitle())
                            && temp.getAuthor().equals(searchBook.getAuthor())) {
                        searchBook.addSource(temp.getSource());
                        isExsit = true;
                    }
                }
                if (!isExsit) {
                    bookSearchBeansAdd.add(temp);
                }
            }
            //添加
            for (BookSearchBean temp : bookSearchBeansAdd) {
                mList.add(temp);
            }

            activityRef.get().runOnUiThread(()->{
                notifyDataSetChanged();
            });
        }
    }
}

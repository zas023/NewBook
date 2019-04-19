package com.thmub.newbook.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.thmub.newbook.R;
import com.thmub.newbook.bean.BookSearchBean;
import com.thmub.newbook.bean.ShelfBookBean;
import com.thmub.newbook.model.SearchEngine;
import com.thmub.newbook.model.local.BookSourceRepository;
import com.thmub.newbook.ui.adapter.SourceExchangeAdapter;
import com.thmub.newbook.widget.refresh.ScrollRefreshRecyclerView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Zhouas666 on 2019-04-11
 * Github: https://github.com/zas023
 * <p>
 * 换源dialog
 */

public class SourceExchangeDialog extends Dialog {

    private static final String TAG = "SourceExchangeDialog";
    @BindView(R.id.dialog_tv_title)
    TextView dialogTvTitle;
    @BindView(R.id.dialog_rv_content)
    ScrollRefreshRecyclerView dialogRvContent;

    private SearchEngine searchEngine;
    private SourceExchangeAdapter mAdapter;

    private OnSourceChangeListener listener;

    private Activity mActivity;
    private ShelfBookBean mShelfBook;

    private int sourceIndex = -1;

    /***************************************************************************/
    public SourceExchangeDialog(@NonNull Activity activity, ShelfBookBean bookBean) {
        super(activity);
        mActivity = activity;
        mShelfBook = bookBean;
    }

    public void setShelfBook(ShelfBookBean mShelfBook) {
        this.mShelfBook = mShelfBook;
    }

    public void setListener(OnSourceChangeListener listener) {
        this.listener = listener;
    }

    /*****************************Initialization********************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_book_source);
        ButterKnife.bind(this);

        setUpWindow();
        initData();
        initClick();

        //执行业务逻辑
        searchEngine.search(mShelfBook.getTitle(), mShelfBook.getAuthor());
        dialogRvContent.startRefresh();
    }

    /**
     * 设置Dialog显示的位置
     */
    private void setUpWindow() {
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        window.setAttributes(lp);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        dialogTvTitle.setText(mShelfBook.getTitle() + "(" + mShelfBook.getAuthor() + ")");

        mAdapter = new SourceExchangeAdapter(mActivity);
        dialogRvContent.setLayoutManager(new LinearLayoutManager(mActivity));
        dialogRvContent.setAdapter(mAdapter);

        searchEngine = new SearchEngine();
        searchEngine.initSearchEngine(BookSourceRepository.getInstance().getAllSelectedBookSource());
    }

    private void initClick() {
        searchEngine.setOnSearchListener(new SearchEngine.OnSearchListener() {

            @Override
            public void loadMoreFinish(Boolean isAll) {
                synchronized (ScrollRefreshRecyclerView.class) {
                    dialogRvContent.finishRefresh();
                }
            }

            @Override
            public void loadMoreSearchBook(List<BookSearchBean> items) {
                //确保只有一个结果
                if (items != null && items.size() == 1) {
                    BookSearchBean bean = items.get(0);
                    if (bean.getSourceTag().equals(mShelfBook.getSourceTag())) {
                        bean.setSelected(true);
                        sourceIndex = mAdapter.getItemSize();
                    }
                    mAdapter.addItem(items.get(0));
                }
            }
        });

        mAdapter.setOnItemClickListener((view, pos) -> {
            listener.onSourceChanged(mAdapter.getItem(pos));
            mAdapter.getItem(pos).setSelected(true);
            if (sourceIndex > -1)
                mAdapter.getItem(sourceIndex).setSelected(false);
            sourceIndex = pos;
            mAdapter.notifyDataSetChanged();
        });
    }


    /**************************Interface**********************************/
    public interface OnSourceChangeListener {
        void onSourceChanged(BookSearchBean bean);
    }

}

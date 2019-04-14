package com.thmub.newbook.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.thmub.newbook.R;
import com.thmub.newbook.base.BaseMVPFragment;
import com.thmub.newbook.bean.ShelfBookBean;
import com.thmub.newbook.model.local.BookShelfRepository;
import com.thmub.newbook.presenter.BookShelfPresenter;
import com.thmub.newbook.presenter.contract.BookShelfContract;
import com.thmub.newbook.ui.activity.ReadActivity;
import com.thmub.newbook.ui.adapter.BookShelfAdapter;
import com.thmub.newbook.utils.SnackbarUtils;
import com.thmub.newbook.utils.UiUtils;
import com.thmub.newbook.widget.refresh.ScrollRefreshRecyclerView;

import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import butterknife.BindView;

/**
 * Created by Zhouas666 on 2019-03-28
 * Github: https://github.com/zas023
 * <p>
 * 书架fragment
 */
public class BookShelfFragment extends BaseMVPFragment<BookShelfContract.Presenter>
        implements BookShelfContract.View {

    @BindView(R.id.shelf_rv_content)
    ScrollRefreshRecyclerView shelfRvContent;

    private BookShelfAdapter mAdapter;

    /************************initialization*****************************/
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_book_shelf;
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        mAdapter = new BookShelfAdapter();
        shelfRvContent.setLayoutManager(new LinearLayoutManager(mContext));
        shelfRvContent.setAdapter(mAdapter);
        shelfRvContent.startRefresh();
    }

    @Override
    protected void initClick() {
        super.initClick();
        //监听下拉刷新
        shelfRvContent.setOnRefreshListener(() -> {
            mAdapter.clear();
            mPresenter.loadShelfBook();
        });
        //监听recycler点击事件
        mAdapter.setOnItemClickListener((view, pos) -> {
            //greenDao的大坑
            //多表联查需要设置  book__setDaoSession(DaoSession);
            ShelfBookBean book = mAdapter.getItem(pos);
            book.setBookChapterList(BookShelfRepository.getInstance().getChapters(book));
            startActivity(new Intent(mContext, ReadActivity.class)
                    .putExtra(ReadActivity.EXTRA_BOOK, book)
                    .putExtra(ReadActivity.EXTRA_IS_COLLECTED, true));
        });
        //长按弹出管理菜单
        mAdapter.setOnItemLongClickListener(
                (v, pos) -> {
                    //开启Dialog,最方便的Dialog,就是AlterDialog
                    openItemDialog(mAdapter.getItem(pos));
                    return true;
                }
        );
    }

    @Override
    protected void processLogic() {
        super.processLogic();
        mPresenter.loadShelfBook();
    }

    /************************Transaction*****************************/
    @Override
    protected BookShelfContract.Presenter bindPresenter() {
        return new BookShelfPresenter();
    }

    @Override
    public void finishLoadShelfBook(List<ShelfBookBean> items) {
        shelfRvContent.finishRefresh();
        mAdapter.addItems(items);
    }

    @Override
    public void showError() {

    }

    @Override
    public void complete() {

    }

    /*************************Event*************************************/
    private void openItemDialog(ShelfBookBean shelfBook) {
        String[] menus = shelfBook.getIsLocal() ? UiUtils.getStringArray(R.array.menu_local_book)
                : UiUtils.getStringArray(R.array.menu_net_book);
        AlertDialog collBookDialog = new AlertDialog.Builder(mContext)
                .setTitle(shelfBook.getTitle())
                .setAdapter(new ArrayAdapter<>(mContext,
                                android.R.layout.simple_list_item_1, menus),
                        (dialog, which) -> onItemMenuClick(menus[which], shelfBook))
                .create();

        collBookDialog.show();
    }

    private void onItemMenuClick(String which, ShelfBookBean shelfBook) {
        switch (which) {
            case "置顶":
                SnackbarUtils.show(mContext, "此功能尚未完成");
                break;
            case "缓存":

                break;
            case "删除":

                break;
            case "批量管理":
                SnackbarUtils.show(mContext, "此功能尚未完成");
                break;
            default:
                break;
        }
    }

    /**
     * 默认删除本地文件
     *
     * @param shelfBook
     */
    private void deleteBook(ShelfBookBean shelfBook) {

//        if (shelfBook.getIsLocal()) {
//            new AlertDialog.Builder(mContext)
//                    .setTitle("删除文件")
//                    .setPositiveButton(UiUtils.getString(R.string.common_sure), ((dialogInterface, i) -> {
//
//                        S.getInstance().deleteCollBook(collBook);
//                        BookRepository.getInstance().deleteBookRecord(collBook.get_id());
//                        //从Adapter中删除
//                        mCollBookAdapter.removeItem(collBook);
//                    }))
//                    .setNegativeButton(getResources().getString(R.string.common_cancel), null)
//                    .show();
//        } else {
//            RxBusManager.getInstance().post(new DeleteTaskEvent(collBook));
//        }
    }

    /*************************Life Cycle*******************************/
    /**
     * 实现从阅读界面返回时刷新书架
     */
    @Override
    public void onResume() {
        super.onResume();
        mAdapter.clear();
        shelfRvContent.startRefresh();
        mPresenter.loadShelfBook();
    }
}

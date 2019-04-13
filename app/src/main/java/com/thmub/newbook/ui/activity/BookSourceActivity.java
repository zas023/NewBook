package com.thmub.newbook.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import com.thmub.newbook.R;
import com.thmub.newbook.base.BaseMVPActivity;
import com.thmub.newbook.bean.BookSourceBean;
import com.thmub.newbook.model.local.BookSourceRepository;
import com.thmub.newbook.presenter.BookSourcePresenter;
import com.thmub.newbook.presenter.contract.BookSourceContract;
import com.thmub.newbook.ui.adapter.BookSourceAdapter;
import com.thmub.newbook.utils.SnackbarUtils;
import com.thmub.newbook.utils.UiUtils;
import com.thmub.newbook.widget.ItemDragCallback;
import com.thmub.newbook.widget.refresh.ScrollRefreshRecyclerView;

import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import butterknife.BindView;

/**
 * Created by Zhouas666 on 2019-03-30
 * Github: https://github.com/zas023
 * <p>
 * 书源管理activity
 */
public class BookSourceActivity extends BaseMVPActivity<BookSourceContract.Presenter> implements
        BookSourceContract.View {

    /******************************Constant**********************************/
    private final static int REQUEST_CODE = 1; // 返回的结果码

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.source_rv_content)
    ScrollRefreshRecyclerView sourceRvContent;

    /******************************Variable**********************************/

    private BookSourceAdapter mAdapter;

    private boolean ischanged;

    /******************************Initialization**********************************/

    @Override
    protected int getLayoutId() {
        return R.layout.activity_book_source;
    }

    @Override
    protected void setUpToolbar(Toolbar toolbar) {
        super.setUpToolbar(toolbar);
        getSupportActionBar().setTitle(UiUtils.getString(R.string.activity_source));
    }


    @Override
    protected void initWidget() {
        super.initWidget();
        mAdapter = new BookSourceAdapter();
        sourceRvContent.setLayoutManager(new LinearLayoutManager(mContext));
        sourceRvContent.setAdapter(mAdapter);
        //关闭下拉刷新
        sourceRvContent.setRefreshEnabled(false);
    }

    @Override
    protected void initClick() {
        super.initClick();
        ItemDragCallback dragCallback = new ItemDragCallback();
        dragCallback.setOnItemTouchListener(new ItemDragCallback.OnItemTouchListener() {
            @Override
            public void onMove(int fromPosition, int toPosition) {
                mAdapter.onMove(fromPosition, toPosition);
                ischanged = true;
            }

            @Override
            public void onSwiped(int position) {
                ischanged = true;
            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(dragCallback);
        itemTouchHelper.attachToRecyclerView(sourceRvContent.getReyclerView());

        mAdapter.setOnItemClickListener((view, pos) -> {
            startActivityForResult(new Intent(mContext, SourceEditActivity.class)
                    .putExtra(SourceEditActivity.EXTRA_BOOK_SOURCE, mAdapter.getItem(pos)), REQUEST_CODE);
        });
    }

    @Override
    protected void processLogic() {
        super.processLogic();
        mPresenter.loadBookSource();
    }

    /******************************Transaction**********************************/

    @Override
    protected BookSourceContract.Presenter bindPresenter() {
        return new BookSourcePresenter();
    }

    @Override
    public void finishLoadBookSource(List<BookSourceBean> items) {
        mAdapter.addItems(items);
    }

    @Override
    public void showError() {

    }

    @Override
    public void complete() {

    }

    /*******************************Event**************************************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_source, menu);
        return true;
    }

    /**
     * 导航栏菜单点击事件
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:    //左侧返回键
                exit();
            case R.id.action_new:
                startActivityForResult(new Intent(mContext, SourceEditActivity.class), REQUEST_CODE);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            exit();
            return true;
        }
        return false;
    }


    private void exit() {
        BookSourceRepository.getInstance().saveBookSourceWithAsync(mAdapter.getItems());
        setResult(Activity.RESULT_OK, new Intent().putExtra(SearchActivity.RESULT_IS_CHANGED, ischanged));
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE) {
                SnackbarUtils.show(this, "保存成功");
                mAdapter.clear();
                mPresenter.loadBookSource();
                ischanged = true;
            }
        }
    }

}

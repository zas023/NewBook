package com.thmub.newbook.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.thmub.newbook.R;
import com.thmub.newbook.base.BaseMVPActivity;
import com.thmub.newbook.bean.BookSourceBean;
import com.thmub.newbook.model.local.BookSourceRepository;
import com.thmub.newbook.presenter.BookSourcePresenter;
import com.thmub.newbook.presenter.contract.BookSourceContract;
import com.thmub.newbook.ui.adapter.BookSourceAdapter;
import com.thmub.newbook.utils.ProgressUtils;
import com.thmub.newbook.utils.ToastUtils;
import com.thmub.newbook.utils.UiUtils;
import com.thmub.newbook.widget.ItemDragCallback;
import com.thmub.newbook.widget.refresh.ScrollRefreshRecyclerView;

import java.util.List;

import androidx.appcompat.app.AlertDialog;
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

    private boolean isChanged;

    /******************************Initialization**********************************/

    @Override
    protected int getLayoutId() {
        return R.layout.activity_book_source;
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        super.setSwipeBackEnable(false);
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

        //长按拖拽
        ItemDragCallback dragCallback = new ItemDragCallback();
        dragCallback.setOnItemTouchListener(new ItemDragCallback.OnItemTouchListener() {
            @Override
            public void onMove(int fromPosition, int toPosition) {
                mAdapter.onMove(fromPosition, toPosition);
                isChanged = true;
            }

            @Override
            public void onSwiped(int position) {
                isChanged = true;
            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(dragCallback);
        itemTouchHelper.attachToRecyclerView(sourceRvContent.getReyclerView());

        //点击事件
        mAdapter.setOnItemClickListener((view, pos) -> openItemDialog(mAdapter.getItem(pos)));
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
        mAdapter.clear();
        mAdapter.addItems(items);
    }

    @Override
    public void finishImportBookSource(List<BookSourceBean> items) {

        BookSourceRepository.getInstance().saveBookSourceWithAsync(items);
        ProgressUtils.dismiss();
        mPresenter.loadBookSource();
    }

    @Override
    public void showError(Throwable e) {

    }

    @Override
    public void complete() {

    }

    /*******************************Event**************************************/

    /**
     * 书源选择对话框
     *
     * @param bean
     */
    private void openItemDialog(BookSourceBean bean) {
        String[] menus = UiUtils.getStringArray(R.array.menu_book_source);
        AlertDialog collBookDialog = new AlertDialog.Builder(mContext)
                .setTitle(bean.getSourceName())
                .setAdapter(new ArrayAdapter<>(mContext,
                                android.R.layout.simple_list_item_1, menus),
                        (dialog, which) -> onItemMenuClick(menus[which], bean))
                .create();

        collBookDialog.show();
    }

    private void onItemMenuClick(String which, BookSourceBean bean) {
        switch (which) {
            case "编辑":
                startActivityForResult(new Intent(mContext, SourceEditActivity.class)
                        .putExtra(SourceEditActivity.EXTRA_BOOK_SOURCE, bean), REQUEST_CODE);
                break;
            case "删除":
                BookSourceRepository.getInstance().deleteBookSource(bean);
                //删除后刷新列表
                mPresenter.loadBookSource();
                break;
            default:
                break;
        }
    }

    /**
     * 初始化导航栏菜单
     *
     * @param menu
     * @return
     */
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
            case R.id.action_new:   //新建书源
                startActivityForResult(new Intent(mContext, SourceEditActivity.class), REQUEST_CODE);
                break;
            case R.id.action_local_import:  //本地导入
                final EditText localEt = new EditText(this);
                new AlertDialog.Builder(this).setTitle("请输入书源字符串")
                        .setView(localEt)
                        .setPositiveButton("确定", (dialogInterface, i) -> {
                            ProgressUtils.show(mContext);
                            mPresenter.importLocalSource(localEt.getText().toString());
                        }).setNegativeButton("取消", null).show();
                break;
            case R.id.action_net_import:  //网络导入
                final EditText NetEt = new EditText(this);
                new AlertDialog.Builder(this).setTitle("请输入书源地址")
                        .setView(NetEt)
                        .setPositiveButton("确定", (dialogInterface, i) -> {
                            ProgressUtils.show(mContext);
                            mPresenter.importNetSource(NetEt.getText().toString());
                        }).setNegativeButton("取消", null).show();
                break;
            case R.id.action_sort:  //智能排序
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
        setResult(Activity.RESULT_OK, new Intent().putExtra(SearchActivity.RESULT_IS_CHANGED, isChanged));
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE) {
                ToastUtils.showSuccess(mContext, "保存成功");
                mAdapter.clear();
                mPresenter.loadBookSource();
                isChanged = true;
            }
        }
    }

}

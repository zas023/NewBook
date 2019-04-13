package com.thmub.newbook.ui.adapter.holder;

import android.widget.TextView;

import com.thmub.newbook.R;
import com.thmub.newbook.base.adapter.ViewHolderImpl;
import com.thmub.newbook.bean.zhui.StoreNodeBean;
import com.thmub.newbook.model.remote.RemoteRepository;
import com.thmub.newbook.ui.adapter.StoreNodeBookAdapter;
import com.thmub.newbook.utils.RxUtils;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Zhouas666 on 2019-04-13
 * Github: https://github.com/zas023
 */
public class StoreNodeHolder extends ViewHolderImpl<StoreNodeBean> {

    TextView itemTvName;

    RecyclerView itemRvContent;

    StoreNodeBookAdapter itemAdapter;

    boolean hasLoad;

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_store_node;
    }

    @Override
    public void initView() {
        itemTvName = findById(R.id.item_tv_name);
        itemRvContent = findById(R.id.item_rv_content);

        itemAdapter = new StoreNodeBookAdapter();
        itemRvContent.setLayoutManager(new GridLayoutManager(getContext(), 4));
        itemRvContent.setAdapter(itemAdapter);
    }

    @Override
    public void onBind(StoreNodeBean data, int pos) {
        itemTvName.setText(data.getTitle());

        //是否已经加载
        if (!hasLoad) {
            new CompositeDisposable().add(RemoteRepository.getInstance()
                    .getStoreNodeBooks(data.get_id())
                    .compose(RxUtils::toSimpleSingle)
                    .subscribe(beans -> {
                        itemAdapter.addItems(beans.subList(0, 4));
                        hasLoad = true;
                    }));
        }
    }
}

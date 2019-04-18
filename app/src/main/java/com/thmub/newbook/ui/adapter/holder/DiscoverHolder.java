package com.thmub.newbook.ui.adapter.holder;

import android.widget.ImageView;
import android.widget.TextView;

import com.thmub.newbook.R;
import com.thmub.newbook.base.adapter.ViewHolderImpl;
import com.thmub.newbook.bean.type.DiscoverType;

/**
 * Created by Zhouas666 on 2019-04-17
 * Github: https://github.com/zas023
 */
public class DiscoverHolder extends ViewHolderImpl<DiscoverType> {

    private ImageView ivIcon;
    private TextView tvName;

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_discovre;
    }

    @Override
    public void initView() {
        ivIcon = findById(R.id.iv_icon);
        tvName = findById(R.id.tv_name);
    }

    @Override
    public void onBind(DiscoverType data, int pos) {
        ivIcon.setImageResource(data.getIconId());
        tvName.setText(data.getTypeName());
    }
}

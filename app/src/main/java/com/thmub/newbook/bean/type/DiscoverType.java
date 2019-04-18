package com.thmub.newbook.bean.type;

import com.thmub.newbook.R;
import com.thmub.newbook.utils.UiUtils;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

/**
 * 发现类型
 */

public enum DiscoverType {
    //分类
    SORT(R.string.fragment_discover_sort, R.mipmap.ic_discover_sort),
    //排行榜
    RANK(R.string.fragment_discover_rank, R.mipmap.ic_discover_rank),
    //主题书单
    LIST(R.string.fragment_discover_list, R.mipmap.ic_discover_list);

    private String typeName;
    private int iconId;

    DiscoverType(@StringRes int typeNameId, @DrawableRes int iconId) {
        this.typeName = UiUtils.getString(typeNameId);
        this.iconId = iconId;
    }

    public String getTypeName() {
        return typeName;
    }

    public int getIconId() {
        return iconId;
    }
}

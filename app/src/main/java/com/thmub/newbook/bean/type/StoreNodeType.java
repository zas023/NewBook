package com.thmub.newbook.bean.type;

import com.thmub.newbook.R;
import com.thmub.newbook.utils.UiUtils;

import androidx.annotation.StringRes;

/**
 * Created by Zhouas666 on 2019-04-13
 * Github: https://github.com/zas023
 */
public enum StoreNodeType {
    NSRM(R.string.store_node_nsrm, "59128334694d1cda365b8985"),
    NSZA(R.string.store_node_nsza, "5912825ba1dbf3ad33ee7ffe"),
    NSWB(R.string.store_node_nswb, "591283eb8973b2fe3361463d"),
    NSWJ(R.string.store_node_nswj, "591283afa1dbf3ad33ee7fff"),
    NSDSQ(R.string.store_node_nsdsq, "591284376e2e237c36d7b8bd"),
    NSHWQ(R.string.store_node_nshwq, "5912872f8973b2fe3361463f"),
    NPXM(R.string.store_node_npxm, "5a9cfc6bf43ec14c2714a0a2"),
    NVPXM(R.string.store_node_nvpxm, "5a9cfc7df43ec14c2714a0a3");

    private String typeName;
    private String typeId;

    StoreNodeType(@StringRes int typeNameId, String typeId) {
        this.typeName = UiUtils.getResources().getString(typeNameId);
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public String getTypeId() {
        return typeId;
    }
}

package com.thmub.newbook.bean.type;

/**
 * 小说分类类型
 */

public enum BookSortListType {
    HOT("热门","hot"),
    NEW("新书","new"),
    REPUTATION("好评","reputation"),
    OVER("完结","over");

    private String typeName;
    private String netName;
    BookSortListType(String typeName, String netName){
        this.typeName = typeName;
        this.netName = netName;
    }

    public String getTypeName() {
        return typeName;
    }

    public String getNetName() {
        return netName;
    }
}

package com.thmub.newbook.bean.type;

/**
 * 小说分类类型
 */

public enum BookListType {

    HOT("本周最热","last-seven-days","collectorCount"),
    NEW("最新发布","all","created"),
    MOST("最多收藏","all","collectorCount");

    private String typeName;
    private String netName;
    private String sortName;

    BookListType(String typeName, String netName, String sortName) {
        this.typeName = typeName;
        this.netName = netName;
        this.sortName = sortName;
    }

    public String getTypeName() {
        return typeName;
    }

    public String getNetName() {
        return netName;
    }

    public String getSortName() {
        return sortName;
    }
}

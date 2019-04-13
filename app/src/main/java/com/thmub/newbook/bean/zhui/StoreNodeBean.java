package com.thmub.newbook.bean.zhui;

/**
 * Created by Zhouas666 on 2019-04-13
 * Github: https://github.com/zas023
 */
public class StoreNodeBean {
    /**
     * _id : 59128334694d1cda365b8985
     * title : 男生热门
     */

    private String _id;
    private String title;

    public StoreNodeBean() {

    }

    public StoreNodeBean(String _id, String title) {
        this._id = _id;
        this.title = title;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

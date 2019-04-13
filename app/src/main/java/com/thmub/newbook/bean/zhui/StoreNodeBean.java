package com.thmub.newbook.bean.zhui;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Zhouas666 on 2019-04-13
 * Github: https://github.com/zas023
 */
public class StoreNodeBean implements Parcelable {
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

    protected StoreNodeBean(Parcel in) {
        _id = in.readString();
        title = in.readString();
    }

    public static final Creator<StoreNodeBean> CREATOR = new Creator<StoreNodeBean>() {
        @Override
        public StoreNodeBean createFromParcel(Parcel in) {
            return new StoreNodeBean(in);
        }

        @Override
        public StoreNodeBean[] newArray(int size) {
            return new StoreNodeBean[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(title);
    }
}

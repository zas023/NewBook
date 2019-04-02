package com.thmub.newbook.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Zhouas666 on 2019-03-31
 * Github: https://github.com/zas023
 */
public class BookContentBean implements Parcelable {

    private String bookLink; //对应BookInfoBean noteUrl;

    private String chapterLink;

    private int chapterIndex;   //当前章节  （包括番外）

    private String chapterContent; //当前章节内容

    private String source;   //来源  某个网站/本地

    public BookContentBean() {

    }


    public String getBookLink() {
        return bookLink;
    }

    public void setBookLink(String bookLink) {
        this.bookLink = bookLink;
    }

    public String getChapterLink() {
        return chapterLink;
    }

    public void setChapterLink(String chapterLink) {
        this.chapterLink = chapterLink;
    }

    public int getChapterIndex() {
        return chapterIndex;
    }

    public void setChapterIndex(int chapterIndex) {
        this.chapterIndex = chapterIndex;
    }

    public String getChapterContent() {
        return chapterContent;
    }

    public void setChapterContent(String chapterContent) {
        this.chapterContent = chapterContent;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    /****************************************************************************/
    protected BookContentBean(Parcel in) {
        bookLink = in.readString();
        chapterLink = in.readString();
        chapterIndex = in.readInt();
        chapterContent = in.readString();
        source = in.readString();
    }

    public static final Creator<BookContentBean> CREATOR = new Creator<BookContentBean>() {
        @Override
        public BookContentBean createFromParcel(Parcel in) {
            return new BookContentBean(in);
        }

        @Override
        public BookContentBean[] newArray(int size) {
            return new BookContentBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(bookLink);
        dest.writeString(chapterLink);
        dest.writeInt(chapterIndex);
        dest.writeString(chapterContent);
        dest.writeString(source);
    }
}

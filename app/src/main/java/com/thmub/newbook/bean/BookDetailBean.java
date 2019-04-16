package com.thmub.newbook.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Zhouas666 on 2019-04-15
 * Github: https://github.com/zas023
 * <p>
 * 书籍详情bean
 */
public class BookDetailBean implements Parcelable {

    //标题
    private String title;
    //作者
    private String author;
    //简介
    private String desc;
    //封面地址
    private String cover;
    //小说地址(章节地址)
    private String bookLink;
    //发现地址(同类小说)
    private String findLink;
    //目录链接
    private String catalogLink;
    //来源
    private String sourceTag;

    public BookDetailBean() {

    }

    public BookDetailBean(BookSearchBean bean) {
        this.title = bean.getTitle();
        author = bean.getAuthor();
        desc = bean.getDesc();
        cover = bean.getCover();
        bookLink = bean.getBookLink();

        sourceTag = bean.getSourceTag();
    }

    protected BookDetailBean(Parcel in) {
        title = in.readString();
        author = in.readString();
        desc = in.readString();
        cover = in.readString();
        bookLink = in.readString();
        findLink = in.readString();
        catalogLink = in.readString();
        sourceTag = in.readString();
    }

    public static final Creator<BookDetailBean> CREATOR = new Creator<BookDetailBean>() {
        @Override
        public BookDetailBean createFromParcel(Parcel in) {
            return new BookDetailBean(in);
        }

        @Override
        public BookDetailBean[] newArray(int size) {
            return new BookDetailBean[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getBookLink() {
        return bookLink;
    }

    public void setBookLink(String bookLink) {
        this.bookLink = bookLink;
    }

    public String getFindLink() {
        return findLink;
    }

    public void setFindLink(String findLink) {
        this.findLink = findLink;
    }

    public String getCatalogLink() {
        return catalogLink;
    }

    public void setCatalogLink(String catalogLink) {
        this.catalogLink = catalogLink;
    }

    public String getSourceTag() {
        return sourceTag;
    }

    public void setSourceTag(String sourceTag) {
        this.sourceTag = sourceTag;
    }

    /***********************************************************************/
    public ShelfBookBean getShelfBook() {
        ShelfBookBean shelfBookBean = new ShelfBookBean();
        shelfBookBean.setLink(bookLink);
        shelfBookBean.setCatalogLink(catalogLink);
        shelfBookBean.setTitle(title);
        shelfBookBean.setAuthor(author);
        shelfBookBean.setCover(cover);
        shelfBookBean.setDesc(desc);
        shelfBookBean.setSourceTag(sourceTag);
        return shelfBookBean;
    }

    @Override
    public String toString() {
        return "BookDetailBean{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", desc='" + desc + '\'' +
                ", cover='" + cover + '\'' +
                ", bookLink='" + bookLink + '\'' +
                ", findLink='" + findLink + '\'' +
                ", catalogLink='" + catalogLink + '\'' +
                ", sourceTag='" + sourceTag + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(author);
        dest.writeString(desc);
        dest.writeString(cover);
        dest.writeString(bookLink);
        dest.writeString(findLink);
        dest.writeString(catalogLink);
        dest.writeString(sourceTag);
    }
}

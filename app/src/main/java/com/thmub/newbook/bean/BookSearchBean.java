package com.thmub.newbook.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhouas666 on 2019-03-26
 * Github: https://github.com/zas023
 * <p>
 * 网络搜索bean
 */
public class BookSearchBean implements Parcelable {

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
    //分类
    private String kind;
    //最新章节
    private String latestChapter;
    //来源
    private String sourceTag;

    //是否选择当前源（换源标记）
    private boolean selected;

    private List<String> sourceUrls;

    public BookSearchBean() {

    }

    public BookSearchBean(ShelfBookBean bean) {
        title = bean.getTitle();
        author = bean.getAuthor();
        desc = bean.getDesc();
        cover = bean.getCover();
        bookLink = bean.getLink();
        sourceTag = bean.getSourceTag();
    }


    protected BookSearchBean(Parcel in) {
        title = in.readString();
        author = in.readString();
        desc = in.readString();
        cover = in.readString();
        bookLink = in.readString();
        findLink = in.readString();
        catalogLink = in.readString();
        kind = in.readString();
        latestChapter = in.readString();
        sourceTag = in.readString();
        selected = in.readByte() != 0;
        sourceUrls = in.createStringArrayList();
    }

    public static final Creator<BookSearchBean> CREATOR = new Creator<BookSearchBean>() {
        @Override
        public BookSearchBean createFromParcel(Parcel in) {
            return new BookSearchBean(in);
        }

        @Override
        public BookSearchBean[] newArray(int size) {
            return new BookSearchBean[size];
        }
    };

    /*************************************************************/

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

    public void setDesc(String shortIntro) {
        this.desc = shortIntro;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getCatalogLink() {
        return catalogLink;
    }

    public void setCatalogLink(String catalogLink) {
        this.catalogLink = catalogLink;
    }

    public String getBookLink() {
        return bookLink;
    }

    public void setBookLink(String bookLink) {
        this.bookLink = bookLink;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getLatestChapter() {
        return latestChapter;
    }

    public void setLatestChapter(String latestChapter) {
        this.latestChapter = latestChapter;
    }

    public String getFindLink() {
        return findLink;
    }

    public void setFindLink(String findLink) {
        this.findLink = findLink;
    }

    public String getSourceTag() {
        return sourceTag;
    }

    public void setSourceTag(String sourceName) {
        this.sourceTag = sourceName;
        addSource(sourceName);
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public List<String> getSourceUrls() {
        return sourceUrls;
    }

    public String[] getSourceUrlArray() {
        String[] array = new String[sourceUrls.size()];
        for (int i = 0; i < sourceUrls.size(); i++)
            array[i] = sourceUrls.get(i);
        return array;
    }

    public void setSourceUrls(List<String> sourceUrls) {
        this.sourceUrls = sourceUrls;
    }

    public void addSource(String soure) {
        if (sourceUrls == null) sourceUrls = new ArrayList<>();
        if (!this.sourceUrls.contains(soure)) {
            this.sourceUrls.add(soure);
        }
    }

    /*************************************************************/
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
        dest.writeString(kind);
        dest.writeString(latestChapter);
        dest.writeString(sourceTag);
        dest.writeByte((byte) (selected ? 1 : 0));
        dest.writeStringList(sourceUrls);
    }

    /*************************************************************/

    @Override
    public String toString() {
        return "BookSearchBean{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", desc='" + desc + '\'' +
                ", cover='" + cover + '\'' +
                ", bookLink='" + bookLink + '\'' +
                ", findLink='" + findLink + '\'' +
                ", catalogLink='" + catalogLink + '\'' +
                ", kind='" + kind + '\'' +
                ", latestChapter='" + latestChapter + '\'' +
                ", sourceName='" + sourceTag + '\'' +
                ", selected=" + selected +
                ", sourceUrls=" + sourceUrls +
                '}';
    }
}

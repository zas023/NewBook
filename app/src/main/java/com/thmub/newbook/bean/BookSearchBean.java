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
    //小说地址
    private String link;
    //分类
    private String kind;
    //最新章节
    private String latestChapter;
    //来源
    private String source;

    //是否选择当前源（换源标记）
    private boolean selected;

    private List<String> sourceUrls;

    public BookSearchBean() {

    }

    protected BookSearchBean(Parcel in) {
        title = in.readString();
        author = in.readString();
        desc = in.readString();
        cover = in.readString();
        link = in.readString();
        kind = in.readString();
        latestChapter = in.readString();
        source = in.readString();
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
        addSource(source);
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
        shelfBookBean.setLink(link);
        shelfBookBean.setTitle(title);
        shelfBookBean.setAuthor(author);
        shelfBookBean.setCover(cover);
        shelfBookBean.setDesc(desc);
        shelfBookBean.setSource(source);
        return shelfBookBean;
    }

    /*************************************************************/
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
        dest.writeString(link);
        dest.writeString(kind);
        dest.writeString(latestChapter);
        dest.writeString(source);
        dest.writeStringList(sourceUrls);
    }

    @Override
    public String toString() {
        return "BookSearchBean{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", desc='" + desc + '\'' +
                ", cover='" + cover + '\'' +
                ", link='" + link + '\'' +
                ", kind='" + kind + '\'' +
                ", latestChapter='" + latestChapter + '\'' +
                ", source='" + source + '\'' +
                ", selected=" + selected +
                ", sourceUrls=" + sourceUrls +
                '}';
    }
}

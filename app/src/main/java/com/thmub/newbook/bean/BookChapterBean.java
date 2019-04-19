package com.thmub.newbook.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.thmub.newbook.utils.RegexUtils;
import com.thmub.newbook.utils.StringUtils;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

import java.util.regex.Matcher;

/**
 * Created by Zhouas666 on 2019-03-27
 * Github: https://github.com/zas023
 * <p>
 * 目录bean
 */
@Entity
public class BookChapterBean implements Parcelable {

    @Index
    private String bookLink; //对应小说的link,做外键对应ShelfBookBean;
    private String bookTitle;
    private String tag;  //此处记为书源名称
    @Id
    private String chapterLink;  //对应章节的link
    private String chapterTitle;  //章节名称
    private int chapterIndex;  //当前章节数

    //章节内容在文章中的起始位置(本地)
    private Long start;
    //章节内容在文章中的终止位置(本地)
    private Long end;

    private boolean unreadble;

    public BookChapterBean() {
    }

    public BookChapterBean(String title) {
        this.chapterTitle = title;
    }

    protected BookChapterBean(Parcel in) {
        bookLink = in.readString();
        bookTitle = in.readString();
        tag = in.readString();
        chapterLink = in.readString();
        chapterTitle = in.readString();
        chapterIndex = in.readInt();
        if (in.readByte() == 0) {
            start = null;
        } else {
            start = in.readLong();
        }
        if (in.readByte() == 0) {
            end = null;
        } else {
            end = in.readLong();
        }
        unreadble = in.readByte() != 0;
    }

    public static final Creator<BookChapterBean> CREATOR = new Creator<BookChapterBean>() {
        @Override
        public BookChapterBean createFromParcel(Parcel in) {
            return new BookChapterBean(in);
        }

        @Override
        public BookChapterBean[] newArray(int size) {
            return new BookChapterBean[size];
        }
    };

    public String getBookLink() {
        return bookLink;
    }

    public void setBookLink(String bookLink) {
        this.bookLink = bookLink;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getChapterTitle() {
        return chapterTitle == null ? "" : chapterTitle;
    }

    public void setChapterTitle(String chapterTitle) {
        this.chapterTitle = chapterTitle;
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

    public Long getStart() {
        return start;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public Long getEnd() {
        return end;
    }

    public void setEnd(Long end) {
        this.end = end;
    }

    public boolean isUnreadble() {
        return unreadble;
    }

    public void setUnreadble(boolean unreadble) {
        this.unreadble = unreadble;
    }

    public boolean getUnreadble() {
        return this.unreadble;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    @Generated(hash = 2122905696)
    public BookChapterBean(String bookLink, String bookTitle, String tag, String chapterLink, String chapterTitle,
                           int chapterIndex, Long start, Long end, boolean unreadble) {
        this.bookLink = bookLink;
        this.bookTitle = bookTitle;
        this.tag = tag;
        this.chapterLink = chapterLink;
        this.chapterTitle = chapterTitle;
        this.chapterIndex = chapterIndex;
        this.start = start;
        this.end = end;
        this.unreadble = unreadble;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(bookLink);
        dest.writeString(bookTitle);
        dest.writeString(tag);
        dest.writeString(chapterLink);
        dest.writeString(chapterTitle);
        dest.writeInt(chapterIndex);
        if (start == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(start);
        }
        if (end == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(end);
        }
        dest.writeByte((byte) (unreadble ? 1 : 0));
    }

    @Override
    public String toString() {
        return "BookChapterBean{" +
                "bookLink='" + bookLink + '\'' +
                ", bookTitle='" + bookTitle + '\'' +
                ", tag='" + tag + '\'' +
                ", chapterLink='" + chapterLink + '\'' +
                ", chapterTitle='" + chapterTitle + '\'' +
                ", chapterIndex=" + chapterIndex +
                ", start=" + start +
                ", end=" + end +
                ", unreadble=" + unreadble +
                '}';
    }
}

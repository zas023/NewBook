package com.thmub.newbook.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Zhouas666 on 2019-05-18
 * Github: https://github.com/zas023
 */
@Entity
public class BookMarkBean {

    @Id
    private String chapterLink;
    private String chapterTitle;

    private String bookLink;
    private String bookTitle;

    private int chapterIndex;
    private int chapterPage;

    private String content;


    @Generated(hash = 116379132)
    public BookMarkBean(String chapterLink, String chapterTitle, String bookLink,
            String bookTitle, int chapterIndex, int chapterPage, String content) {
        this.chapterLink = chapterLink;
        this.chapterTitle = chapterTitle;
        this.bookLink = bookLink;
        this.bookTitle = bookTitle;
        this.chapterIndex = chapterIndex;
        this.chapterPage = chapterPage;
        this.content = content;
    }

    @Generated(hash = 237936453)
    public BookMarkBean() {
    }


    public String getChapterLink() {
        return chapterLink;
    }

    public void setChapterLink(String chapterLink) {
        this.chapterLink = chapterLink;
    }

    public String getChapterTitle() {
        return chapterTitle;
    }

    public void setChapterTitle(String chapterTitle) {
        this.chapterTitle = chapterTitle;
    }

    public String getBookLink() {
        return bookLink;
    }

    public void setBookLink(String bookLink) {
        this.bookLink = bookLink;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public int getChapterIndex() {
        return chapterIndex;
    }

    public void setChapterIndex(int chapterIndex) {
        this.chapterIndex = chapterIndex;
    }

    public int getChapterPage() {
        return chapterPage;
    }

    public void setChapterPage(int chapterPage) {
        this.chapterPage = chapterPage;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

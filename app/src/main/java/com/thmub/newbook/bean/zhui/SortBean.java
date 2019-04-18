package com.thmub.newbook.bean.zhui;

import java.util.List;

/**
 * Created by Zhouas666 on 2019-04-18
 * Github: https://github.com/zas023
 */
public class SortBean {
    /**
     * name : 玄幻
     * bookCount : 578178
     * monthlyCount : 21707
     * icon : /icon/玄幻_.png
     * bookCover : ["/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F1228859%2F1228859_b7114fbfc2a44969ac2879fcec1d6bea.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F2107590%2F2107590_718bb20f6bba4ee2910cf30d51882112.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F41584%2F41584_873f1d8b2f7a447a8c59f3573094db1b.jpg%2F"]
     */

    private String name;
    private int bookCount;
    private int monthlyCount;
    private String icon;
    private List<String> bookCover;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBookCount() {
        return bookCount;
    }

    public void setBookCount(int bookCount) {
        this.bookCount = bookCount;
    }

    public int getMonthlyCount() {
        return monthlyCount;
    }

    public void setMonthlyCount(int monthlyCount) {
        this.monthlyCount = monthlyCount;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<String> getBookCover() {
        return bookCover;
    }

    public void setBookCover(List<String> bookCover) {
        this.bookCover = bookCover;
    }
}

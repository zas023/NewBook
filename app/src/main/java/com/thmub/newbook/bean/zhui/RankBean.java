package com.thmub.newbook.bean.zhui;

/**
 * Created by Zhouas666 on 2019-04-19
 * Github: https://github.com/zas023
 */
public class RankBean {

    /**
     * _id : 54d43437d47d13ff21cad58b
     * title : 追书最热榜 Top100
     * cover : /ranking-cover/142319314350435
     * collapse : false
     * monthRank : 564d853484665f97662d0810
     * totalRank : 564d85b6dd2bd1ec660ea8e2
     * shortTitle : 最热榜
     */

    private String _id;
    private String title;
    private String cover;
    private boolean collapse;
    private String monthRank;
    private String totalRank;
    private String shortTitle;

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

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public boolean isCollapse() {
        return collapse;
    }

    public void setCollapse(boolean collapse) {
        this.collapse = collapse;
    }

    public String getMonthRank() {
        return monthRank;
    }

    public void setMonthRank(String monthRank) {
        this.monthRank = monthRank;
    }

    public String getTotalRank() {
        return totalRank;
    }

    public void setTotalRank(String totalRank) {
        this.totalRank = totalRank;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }
}

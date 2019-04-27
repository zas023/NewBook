package com.thmub.newbook.bean.zhui;

import java.util.List;

/**
 * Created by Zhouas666 on 2019-04-27
 * Github: https://github.com/zas023
 */
public class ThemeBean {
    /**
     * _id : 5cbfd3775addc50001437acd
     * title : 漫威|超级英雄书单来了！
     * author : 🌸 WANG ·
     * desc :
     * gender : male
     * collectorCount : 483
     * bookCount : 13
     * cover : /agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F1465128%2F1465128_865be7046d724555a12d6ecc1418e184.jpg%2F
     * covers : ["/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F1465128%2F1465128_865be7046d724555a12d6ecc1418e184.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F1229169%2F1229169_a33b674da5fb4d1398c6bb857276f11f.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F1484378%2F1484378_6be874ca05294f4bb111e27205561281.jpg%2F"]
     */

    private String _id;
    private String title;
    private String author;
    private String desc;
    private String gender;
    private int collectorCount;
    private int bookCount;
    private String cover;
    private List<String> covers;

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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getCollectorCount() {
        return collectorCount;
    }

    public void setCollectorCount(int collectorCount) {
        this.collectorCount = collectorCount;
    }

    public int getBookCount() {
        return bookCount;
    }

    public void setBookCount(int bookCount) {
        this.bookCount = bookCount;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public List<String> getCovers() {
        return covers;
    }

    public void setCovers(List<String> covers) {
        this.covers = covers;
    }
}

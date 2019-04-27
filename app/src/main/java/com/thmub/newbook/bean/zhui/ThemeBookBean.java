package com.thmub.newbook.bean.zhui;

/**
 * Created by Zhouas666 on 2019-04-27
 * Github: https://github.com/zas023
 */
public class ThemeBookBean {
    /**
     * book : {"cat":"娱乐明星","_id":"5a589dbae0c2d6148400c0f5","title":"文娱大戏精","author":"何忙忙","longIntro":"","cover":"/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F2205984%2F2205984_97ed4c553b17443a8e5762d25b7e835e.jpg%2F","site":"zhuishuvip","majorCate":"职场","minorCate":"娱乐明星","allowMonthly":false,"banned":0,"latelyFollower":4223,"wordCount":1481520,"retentionRatio":28.610000610351562}
     * comment : 猪脚性格犹如泥石流在娱乐圈里翻江倒海，又不会脱离逻辑～★★★★☆
     */

    private BookBean book;
    private String comment;

    public BookBean getBook() {
        return book;
    }

    public void setBook(BookBean book) {
        this.book = book;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}

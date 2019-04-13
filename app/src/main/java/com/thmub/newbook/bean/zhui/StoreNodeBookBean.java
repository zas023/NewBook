package com.thmub.newbook.bean.zhui;

/**
 * Created by Zhouas666 on 2019-04-13
 * Github: https://github.com/zas023
 */
public class StoreNodeBookBean {
    /**
     * _id : 5caeae12014e93e43fa031f5
     * __v : 0
     * order : 5
     * node : {"_id":"59128334694d1cda365b8985","title":"男生热门"}
     * page : 5910018c8094b1e228e5868f
     * book : {"_id":"5bef85590e4edb8b71a04fbf","longIntro":"刚刚回到阔别五年的家就被强迫签下婚约，就这么莫名其妙有了未婚妻，更莫名其妙的是还是仇家的女儿。方河，学艺五年归来，早已经不是那个可以任人摆布的废物少爷，他会夺回属于自己的一切！","title":"都市逍遥邪少","majorCate":"都市","shortIntro":"刚刚回到阔别五年的家就被强迫签下婚约，就这么莫名其妙有了未婚妻，更莫名其妙的是还是仇家的女儿。方河，学艺五年归来，早已经不是那个可以任人摆布的废物少爷，他会夺回属于自己的一切！","minorCate":"都市生活","author":"鸡汤豆脑","cover":"http://statics.zhuishushenqi.com/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F3348758%2F3348758_8fbd8061f61340ee9fd07da48d83c0b4.jpg%2F","sizetype":-1,"superscript":"","contentType":"txt","allowMonthly":false,"latelyFollower":1365,"wordCount":3624446,"retentionRatio":35,"isSerial":true,"chaptersCount":1721}
     * show : true
     */

    private String _id;
    private int __v;
    private int order;
    private StoreNodeBean node;
    private String page;
    private BookBean book;
    private boolean show;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public StoreNodeBean getNode() {
        return node;
    }

    public void setNode(StoreNodeBean node) {
        this.node = node;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public BookBean getBook() {
        return book;
    }

    public void setBook(BookBean book) {
        this.book = book;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public static class BookBean {
        /**
         * _id : 5bef85590e4edb8b71a04fbf
         * longIntro : 刚刚回到阔别五年的家就被强迫签下婚约，就这么莫名其妙有了未婚妻，更莫名其妙的是还是仇家的女儿。方河，学艺五年归来，早已经不是那个可以任人摆布的废物少爷，他会夺回属于自己的一切！
         * title : 都市逍遥邪少
         * majorCate : 都市
         * shortIntro : 刚刚回到阔别五年的家就被强迫签下婚约，就这么莫名其妙有了未婚妻，更莫名其妙的是还是仇家的女儿。方河，学艺五年归来，早已经不是那个可以任人摆布的废物少爷，他会夺回属于自己的一切！
         * minorCate : 都市生活
         * author : 鸡汤豆脑
         * cover : http://statics.zhuishushenqi.com/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F3348758%2F3348758_8fbd8061f61340ee9fd07da48d83c0b4.jpg%2F
         * sizetype : -1
         * superscript :
         * contentType : txt
         * allowMonthly : false
         * latelyFollower : 1365
         * wordCount : 3624446
         * retentionRatio : 35
         * isSerial : true
         * chaptersCount : 1721
         */

        private String _id;
        private String longIntro;
        private String title;
        private String majorCate;
        private String shortIntro;
        private String minorCate;
        private String author;
        private String cover;
        private int sizetype;
        private String superscript;
        private String contentType;
        private boolean allowMonthly;
        private int latelyFollower;
        private int wordCount;
        private String retentionRatio;
        private boolean isSerial;
        private int chaptersCount;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getLongIntro() {
            return longIntro;
        }

        public void setLongIntro(String longIntro) {
            this.longIntro = longIntro;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMajorCate() {
            return majorCate;
        }

        public void setMajorCate(String majorCate) {
            this.majorCate = majorCate;
        }

        public String getShortIntro() {
            return shortIntro;
        }

        public void setShortIntro(String shortIntro) {
            this.shortIntro = shortIntro;
        }

        public String getMinorCate() {
            return minorCate;
        }

        public void setMinorCate(String minorCate) {
            this.minorCate = minorCate;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public int getSizetype() {
            return sizetype;
        }

        public void setSizetype(int sizetype) {
            this.sizetype = sizetype;
        }

        public String getSuperscript() {
            return superscript;
        }

        public void setSuperscript(String superscript) {
            this.superscript = superscript;
        }

        public String getContentType() {
            return contentType;
        }

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }

        public boolean isAllowMonthly() {
            return allowMonthly;
        }

        public void setAllowMonthly(boolean allowMonthly) {
            this.allowMonthly = allowMonthly;
        }

        public int getLatelyFollower() {
            return latelyFollower;
        }

        public void setLatelyFollower(int latelyFollower) {
            this.latelyFollower = latelyFollower;
        }

        public int getWordCount() {
            return wordCount;
        }

        public void setWordCount(int wordCount) {
            this.wordCount = wordCount;
        }

        public String getRetentionRatio() {
            return retentionRatio;
        }

        public void setRetentionRatio(String retentionRatio) {
            this.retentionRatio = retentionRatio;
        }

        public boolean isIsSerial() {
            return isSerial;
        }

        public void setIsSerial(boolean isSerial) {
            this.isSerial = isSerial;
        }

        public int getChaptersCount() {
            return chaptersCount;
        }

        public void setChaptersCount(int chaptersCount) {
            this.chaptersCount = chaptersCount;
        }
    }
}

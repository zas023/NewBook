package com.thmub.newbook.bean.zhui;

import java.util.List;

/**
 * Created by Zhouas666 on 2019-04-13
 * Github: https://github.com/zas023
 */
public class StoreBannerBean {

    /**
     * _id : 5b63cb37fda9d2dd54faae28
     * __v : 0
     * img : http://statics.zhuishushenqi.com/recommendPage/15543710092511
     * link : 570e3b4a0395ddb57e15b17c
     * type : c-bookdetail
     * order : 1
     * fullDes : 神级修炼系统
     * simpleDes : 神级修炼系统
     * title : 神级修炼系统
     * node : {"_id":"57833232be9f970e3dc4270f","order":2,"sex":"none"}
     * page : 575f74f27a4a60dc78a435a3
     * platforms : ["ios","android"]
     */

    private String _id;
    private int __v;
    private String img;
    private String link;
    private String type;
    private int order;
    private String fullDes;
    private String simpleDes;
    private String title;
    private NodeBean node;
    private String page;
    private List<String> platforms;

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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getFullDes() {
        return fullDes;
    }

    public void setFullDes(String fullDes) {
        this.fullDes = fullDes;
    }

    public String getSimpleDes() {
        return simpleDes;
    }

    public void setSimpleDes(String simpleDes) {
        this.simpleDes = simpleDes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public NodeBean getNode() {
        return node;
    }

    public void setNode(NodeBean node) {
        this.node = node;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public List<String> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(List<String> platforms) {
        this.platforms = platforms;
    }

    public static class NodeBean {
        /**
         * _id : 57833232be9f970e3dc4270f
         * order : 2
         * sex : none
         */

        private String _id;
        private int order;
        private String sex;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }
    }

}

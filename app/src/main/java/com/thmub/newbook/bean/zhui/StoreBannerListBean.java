package com.thmub.newbook.bean.zhui;

import java.util.List;

/**
 * Created by Zhouas666 on 2019-04-13
 * Github: https://github.com/zas023
 */
public class StoreBannerListBean {

    /**
     * data : [{"_id":"5b63cb37fda9d2dd54faae28","__v":0,"img":"http://statics.zhuishushenqi.com/recommendPage/15543710092511","link":"570e3b4a0395ddb57e15b17c","type":"c-bookdetail","order":1,"fullDes":"神级修炼系统","simpleDes":"神级修炼系统","title":"神级修炼系统","node":{"_id":"57833232be9f970e3dc4270f","order":2,"sex":"none"},"page":"575f74f27a4a60dc78a435a3","platforms":["ios","android"]},{"_id":"5bbeb3cd457e86804a53082a","__v":0,"type":"c-bookdetail","link":"57207306e7f0a45d5e9ecf3a","fullDes":"离宫风华惊天下：娘娘万福","simpleDes":"离宫风华惊天下：娘娘万福","img":"http://statics.zhuishushenqi.com/recommendPage/155437105715432","title":"离宫风华惊天下：娘娘万福","order":2,"node":{"_id":"5783321e07d5beac3942baa0","order":1,"sex":"none"},"page":"575f74f27a4a60dc78a435a3","platforms":["android","ios"]},{"_id":"5c04f5a1e303940814a8dc60","__v":0,"type":"c-bookdetail","link":"5c749dd1c974f954eea45acb","fullDes":"我身上有条龙","simpleDes":"我身上有条龙","img":"http://statics.zhuishushenqi.com/recommendPage/15543807947671","title":"我身上有条龙","order":3,"node":{"_id":"5783321e07d5beac3942baa0","order":1,"sex":"none"},"page":"575f74f27a4a60dc78a435a3","platforms":["android","ios"]}]
     * ok : true
     */

    private boolean ok;
    private List<StoreBannerBean> data;

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public List<StoreBannerBean> getData() {
        return data;
    }

    public void setData(List<StoreBannerBean> data) {
        this.data = data;
    }

}

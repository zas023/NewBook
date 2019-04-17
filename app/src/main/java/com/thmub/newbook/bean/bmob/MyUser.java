package com.thmub.newbook.bean.bmob;

import cn.bmob.v3.BmobUser;

public class MyUser extends BmobUser {

    private String portrait; //用户头像
    private String sex;      //用户性别

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}

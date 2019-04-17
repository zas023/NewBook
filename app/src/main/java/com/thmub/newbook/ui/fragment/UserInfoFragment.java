package com.thmub.newbook.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.thmub.newbook.R;
import com.thmub.newbook.base.BaseFragment;
import com.thmub.newbook.bean.bmob.MyUser;
import com.thmub.newbook.widget.CircleImageView;
import com.thmub.newbook.widget.CommonItemLayout;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;

/**
 * Created by Zhouas666 on 2019-04-17
 * Github: https://github.com/zas023
 * <p>
 * 用户信息fragment
 */
public class UserInfoFragment extends BaseFragment {

    @BindView(R.id.info_iv_icon)
    CircleImageView infoIvIcon;
    @BindView(R.id.info_rlt_update_icon)
    RelativeLayout infoRltUpdateIcon;
    @BindView(R.id.info_cil_username)
    CommonItemLayout infoCilUsername;
    @BindView(R.id.info_cil_sex)
    CommonItemLayout infoCilSex;
    @BindView(R.id.info_cil_phone)
    CommonItemLayout infoCilPhone;
    @BindView(R.id.info_cil_email)
    CommonItemLayout infoCilEmail;
    @BindView(R.id.info_btn_logout)
    Button infoBtnLogout;

    private MyUser currentUser;

    /***************************Initialization*******************************/
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user_info;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        currentUser = BmobUser.getCurrentUser(MyUser.class);
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        Glide.with(mContext).load(currentUser.getPortrait()).into(infoIvIcon);
        infoCilUsername.setRightText(currentUser.getUsername());
        infoCilSex.setRightText(currentUser.getSex());
        infoCilPhone.setRightText(currentUser.getMobilePhoneNumber());
        infoCilEmail.setRightText(currentUser.getEmail());
    }

    /*********************************Event********************************/

    @OnClick(R.id.info_btn_logout)
    protected void logout() {
        BmobUser.logOut();
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }
}

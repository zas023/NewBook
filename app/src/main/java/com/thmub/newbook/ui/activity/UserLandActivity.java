package com.thmub.newbook.ui.activity;

import android.app.Activity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.thmub.newbook.R;
import com.thmub.newbook.base.BaseMVPActivity;
import com.thmub.newbook.bean.bmob.MyUser;
import com.thmub.newbook.presenter.UserLandPresenter;
import com.thmub.newbook.presenter.contract.UserLandContract;
import com.thmub.newbook.utils.ProgressUtils;
import com.thmub.newbook.utils.ToastUtils;
import com.thmub.newbook.widget.OwlLoginView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

import static android.text.TextUtils.isEmpty;

/**
 * Created by Zhouas666 on 2019-04-17
 * Github: https://github.com/zas023
 */
public class UserLandActivity extends BaseMVPActivity<UserLandContract.Presenter> implements UserLandContract.View{


    @BindView(R.id.land_owl_view)
    OwlLoginView landOwlView;
    @BindView(R.id.land_til_username)
    TextInputLayout landTilUsername;
    @BindView(R.id.land_til_password)
    TextInputLayout landTilPassword;
    @BindView(R.id.land_btn_login)
    Button landBtnLogin;
    @BindView(R.id.land_tv_forget)
    TextView landTvForget;

    private EditText mEdtUsername;
    private EditText mEdtPassword;

    /***************************Initialization*******************************/
    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_land;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mEdtUsername = landTilUsername.getEditText();
        mEdtPassword = landTilPassword.getEditText();
        //监听密码输入框的聚焦事件
        mEdtPassword.setOnFocusChangeListener((view, b) -> {
            if (b) {
                landOwlView.open();
            } else {
                landOwlView.close();
            }
        });
    }

    /*********************************Transaction**************************/
    @Override
    public void saveSuccess(String s) {
        ToastUtils.showSuccess(mContext, "登录成功");
        setResult(Activity.RESULT_OK);
        finish();
    }

    @Override
    public void showError(Throwable e) {
        ToastUtils.showError(mContext, e.getMessage());
    }

    @Override
    public void complete() {

    }

    @Override
    protected UserLandContract.Presenter bindPresenter() {
        return new UserLandPresenter();
    }

    /*********************************Event********************************/
    @OnClick(R.id.land_btn_login)
    protected void login() {
        String username = mEdtUsername.getText().toString();
        String password = mEdtPassword.getText().toString();
        if (isEmpty(username) || isEmpty(password)) {
            ToastUtils.showInfo(mContext, "用户名或密码不能为空");
            return;
        }

        ProgressUtils.show(this, "正在登陆...");

        MyUser user = new MyUser();
        user.setUsername(username);
        user.setPassword(password);

        BmobUser.loginByAccount(username, password, new LogInListener<MyUser>() {
            @Override
            public void done(MyUser myUser, BmobException e) {
                ProgressUtils.dismiss();
                if (e == null) {
                    ToastUtils.showSuccess(mContext, "登录成功");
                    setResult(Activity.RESULT_OK);
                    finish();
                } else {
                   mPresenter.saveUser(user);
                }
            }
        });
    }
}

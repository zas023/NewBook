package com.thmub.newbook.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;

import com.thmub.newbook.R;
import com.thmub.newbook.base.BaseActivity;
import com.thmub.newbook.utils.PermissionUtils;
import com.thmub.newbook.utils.ToastUtils;
import com.thmub.newbook.utils.UiUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

/**
 * Created by Zhouas666 on 2019-04-13
 * Github: https://github.com/zas023
 * <p>
 * 二维码扫描activity
 */
public class QRScanActivity extends BaseActivity implements QRCodeView.Delegate{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.zxingview)
    ZXingView mQRCodeView;

    private final int REQUEST_CAMERA_PER = 101;
    private final String[] permissions = new String[]{Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE};

    @Override
    protected int getLayoutId() {
        return R.layout.activity_qr_scan;
    }

    @Override
    protected void setUpToolbar(Toolbar toolbar) {
        super.setUpToolbar(toolbar);
        getSupportActionBar().setTitle(UiUtils.getString(R.string.activity_qr_scan));
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mQRCodeView.setDelegate(this);
    }

    @Override
    protected void initWidget() {
        super.initWidget();

        PermissionUtils.checkMorePermissions(this, permissions, new PermissionUtils.PermissionCheckCallback() {
            @Override
            public void onHasPermission() {
                mQRCodeView.startCamera(); // 打开后置摄像头开始预览，但是并未开始识别
                mQRCodeView.startSpotAndShowRect(); // 显示扫描框，并开始识别
            }

            @Override
            public void onUserHasAlreadyTurnedDown(String... permission) {
                ToastUtils.show(mContext, "扫描二维码需相机权限");
            }

            @Override
            public void onAlreadyTurnedDownAndNoAsk(String... permission) {
                PermissionUtils.requestMorePermissions(mContext, permissions, REQUEST_CAMERA_PER);
            }
        });
    }

    /***********************Event***************************/

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtils.checkMorePermissions(this, permissions, new PermissionUtils.PermissionCheckCallback() {
            @Override
            public void onHasPermission() {
                initWidget();
            }

            @Override
            public void onUserHasAlreadyTurnedDown(String... permission) {
                ToastUtils.show(mContext, "扫描二维码需相机权限");
            }

            @Override
            public void onAlreadyTurnedDownAndNoAsk(String... permission) {
                PermissionUtils.toAppSetting(mContext);
            }
        });
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
//        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
//        vibrator.vibrate(200);
        //mQRCodeView.startSpot();
        Log.i(TAG,result);
        setResult(Activity.RESULT_OK, new Intent().putExtra("result", result));
        finish();
    }

    @Override
    public void onCameraAmbientBrightnessChanged(boolean isDark) {

    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        ToastUtils.showError(mContext,"扫描错误");
    }


    /***********************Life Cycle***************************/
    @Override
    protected void onStop() {
        mQRCodeView.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mQRCodeView.onDestroy();
        super.onDestroy();
    }
}

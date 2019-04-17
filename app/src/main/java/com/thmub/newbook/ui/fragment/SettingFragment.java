package com.thmub.newbook.ui.fragment;

import android.os.Bundle;
import android.widget.SeekBar;

import com.thmub.newbook.R;
import com.thmub.newbook.base.BaseFragment;
import com.thmub.newbook.utils.SharedPreUtils;
import com.thmub.newbook.widget.CommonItemLayout;

import butterknife.BindView;


/**
 * Created by Zhouas666 on 2019-04-16
 * Github: https://github.com/zas023
 */
public class SettingFragment extends BaseFragment {

    @BindView(R.id.cil_shelf_sort)
    CommonItemLayout cilShelfSort;
    @BindView(R.id.cil_thread_num)
    CommonItemLayout cilThreadNum;
    @BindView(R.id.cil_clear_store)
    CommonItemLayout cilClearStore;
    @BindView(R.id.seekBar_thread)
    SeekBar seekBarThread;

    private int threadNum;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        threadNum = SharedPreUtils.getInstance().getInt(getString(R.string.pref_thread_num), 6);
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        cilThreadNum.setRightText("" + threadNum);
        seekBarThread.setProgress(threadNum);
    }

    @Override
    protected void initClick() {
        super.initClick();
        seekBarThread.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                threadNum = progress;
                cilThreadNum.setRightText("" + threadNum);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                SharedPreUtils.getInstance().putInt(getString(R.string.pref_thread_num), threadNum);
            }
        });
    }
}

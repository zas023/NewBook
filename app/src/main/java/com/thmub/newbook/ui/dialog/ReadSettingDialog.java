package com.thmub.newbook.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.thmub.newbook.R;
import com.thmub.newbook.manager.ReadSettingManager;
import com.thmub.newbook.ui.activity.FragmentActivity;

import java.text.DecimalFormat;

import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Zhouas666 on 2019-04-01
 * Github: https://github.com/zas023
 * <p>
 * 阅读设置dialog
 */

public class ReadSettingDialog extends Dialog {

    private static final String TAG = "ReadSettingDialog";

    @BindView(R.id.read_setting_tv_font_minus)
    TextView readSettingTvFontMinus;
    @BindView(R.id.read_setting_tv_font)
    TextView readSettingTvFont;
    @BindView(R.id.read_setting_tv_font_plus)
    TextView readSettingTvFontPlus;
    @BindView(R.id.read_setting_tv_font_complex)
    CheckBox readSettingTvFontComplex;
    @BindView(R.id.read_setting_tv_font_body)
    CheckBox readSettingTvFontBody;
    @BindView(R.id.read_setting_tv_font_custom)
    CheckBox readSettingTvFontCustom;
    @BindView(R.id.read_setting_tv_line_minus)
    TextView readSettingTvLineMinus;
    @BindView(R.id.read_setting_tv_line)
    TextView readSettingTvLine;
    @BindView(R.id.read_setting_tv_line_plus)
    TextView readSettingTvLinePlus;
    @BindView(R.id.read_setting_tv_paragraph_minus)
    TextView readSettingTvParagraphMinus;
    @BindView(R.id.read_setting_tv_paragraph)
    TextView readSettingTvParagraph;
    @BindView(R.id.read_setting_tv_paragraph_plus)
    TextView readSettingTvParagraphPlus;
    @BindView(R.id.read_setting_tv_font_type)
    CheckBox readSettingTvFontType;
    @BindView(R.id.read_setting_rb_simulation)
    RadioButton readSettingRbSimulation;
    @BindView(R.id.read_setting_rb_cover)
    RadioButton readSettingRbCover;
    @BindView(R.id.read_setting_rb_slide)
    RadioButton readSettingRbSlide;
    @BindView(R.id.read_setting_rb_scroll)
    RadioButton readSettingRbScroll;
    @BindView(R.id.read_setting_rb_none)
    RadioButton readSettingRbNone;
    @BindView(R.id.read_setting_rg_page_mode)
    RadioGroup readSettingRgPageMode;
    @BindView(R.id.read_setting_tv_bg_custom)
    CheckBox readSettingTvBgCustom;
    @BindView(R.id.read_setting_tv_more)
    TextView readSettingTvMore;
    @BindView(R.id.read_setting_ll_menu)
    LinearLayout readSettingLlMenu;
    @BindView(R.id.read_setting_iv_bg_white)
    ImageView readSettingIvBgWhite;
    @BindView(R.id.read_setting_tv_white)
    TextView readSettingTvWhite;
    @BindView(R.id.read_setting_iv_checked_white)
    ImageView readSettingIvCheckedWhite;
    @BindView(R.id.read_setting_iv_bg_yellow)
    ImageView readSettingIvBgYellow;
    @BindView(R.id.read_setting_tv_yellow)
    TextView readSettingTvYellow;
    @BindView(R.id.read_setting_iv_checked_yellow)
    ImageView readSettingIvCheckedYellow;
    @BindView(R.id.read_setting_iv_bg_green)
    ImageView readSettingIvBgGreen;
    @BindView(R.id.read_setting_tv_green)
    TextView readSettingTvGreen;
    @BindView(R.id.read_setting_iv_checked_green)
    ImageView readSettingIvCheckedGreen;
    @BindView(R.id.read_setting_iv_bg_blue)
    ImageView readSettingIvBgBlue;
    @BindView(R.id.read_setting_tv_blue)
    TextView readSettingTvBlue;
    @BindView(R.id.read_setting_iv_checked_blue)
    ImageView readSettingIvCheckedBlue;
    @BindView(R.id.read_setting_iv_bg_black)
    ImageView readSettingIvBgBlack;
    @BindView(R.id.read_setting_tv_black)
    TextView readSettingTvBlack;
    @BindView(R.id.read_setting_iv_checked_black)
    ImageView readSettingIvCheckedBlack;


    /***************************************************************************/
    private ReadSettingManager mSettingManager = ReadSettingManager.getInstance();
    private Activity mActivity;
    private OnSettingChangeListener mListener;


    public ReadSettingDialog(@NonNull Activity activity) {
        super(activity, R.style.ReadSettingDialog);
        mActivity = activity;
    }

    public void setOnChangeListener(OnSettingChangeListener mListener) {
        this.mListener = mListener;
    }

    /*****************************Initialization********************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_read_setting);
        ButterKnife.bind(this);

        setUpWindow();

        initData();

        initClick();
    }

    /**
     * 设置Dialog显示的位置
     */
    private void setUpWindow() {
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;
        window.setAttributes(lp);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        initBg();
        updateBg(mSettingManager.getTextDrawableIndex());
        updateBoldText(mSettingManager.getTextBold());
        updatePageMode(mSettingManager.getPageMode());
        updateTextSize(mSettingManager.getTextSize());
        updateLineSize(mSettingManager.getLineMultiplier());
        updateParagraphSize(mSettingManager.getParagraphSize());
    }

    /**
     * 初始化背景
     */
    private void initBg() {
        readSettingTvWhite.setTextColor(mSettingManager.getTextColor(0));
        readSettingTvYellow.setTextColor(mSettingManager.getTextColor(1));
        readSettingTvGreen.setTextColor(mSettingManager.getTextColor(2));
        readSettingTvBlue.setTextColor(mSettingManager.getTextColor(3));
        readSettingTvBlack.setTextColor(mSettingManager.getTextColor(4));
        readSettingIvBgWhite.setImageDrawable(mSettingManager.getBgDrawable(0, mActivity, 100, 180));
        readSettingIvBgYellow.setImageDrawable(mSettingManager.getBgDrawable(1, mActivity, 100, 180));
        readSettingIvBgGreen.setImageDrawable(mSettingManager.getBgDrawable(2, mActivity, 100, 180));
        readSettingIvBgBlue.setImageDrawable(mSettingManager.getBgDrawable(3, mActivity, 100, 180));
        readSettingIvBgBlack.setImageDrawable(mSettingManager.getBgDrawable(4, mActivity, 100, 180));

    }

    private void updateBg(int index) {
        readSettingIvCheckedWhite.setVisibility(View.GONE);
        readSettingIvCheckedYellow.setVisibility(View.GONE);
        readSettingIvCheckedGreen.setVisibility(View.GONE);
        readSettingIvCheckedBlue.setVisibility(View.GONE);
        readSettingIvCheckedBlack.setVisibility(View.GONE);
        switch (index) {
            case 0:
                readSettingIvCheckedWhite.setVisibility(View.VISIBLE);
                break;
            case 1:
                readSettingIvCheckedYellow.setVisibility(View.VISIBLE);
                break;
            case 2:
                readSettingIvCheckedGreen.setVisibility(View.VISIBLE);
                break;
            case 3:
                readSettingIvCheckedBlue.setVisibility(View.VISIBLE);
                break;
            case 4:
                readSettingIvCheckedBlack.setVisibility(View.VISIBLE);
                break;
        }
        mSettingManager.setTextDrawableIndex(index);
    }

    private void updateBoldText(Boolean isBold) {
        readSettingTvFontBody.setSelected(isBold);
    }

    private void updatePageMode(int pageModeIndex) {
        switch (pageModeIndex) {
            case 0:
                readSettingRbCover.setChecked(true);
                break;
            case 1:
                readSettingRbSimulation.setChecked(true);
                break;
            case 2:
                readSettingRbSlide.setChecked(true);
                break;
            case 3:
                readSettingRbScroll.setChecked(true);
                break;
            case 4:
                readSettingRbNone.setChecked(true);
                break;
        }
    }

    //字体大小
    private void updateTextSize(int size) {
        //注意不能直接设置整型的size,否则系统会吧整型判断为资源ID
        //需要转换成字符串类型
        readSettingTvFont.setText("" + size);
        mSettingManager.setTextSize(size);
    }

    //行间距
    private void updateLineSize(float size) {
        readSettingTvLine.setText(new DecimalFormat("0.0").format(size));
        mSettingManager.setLineMultiplier(size);
    }

    //行间距
    private void updateParagraphSize(float size) {
        readSettingTvParagraph.setText(new DecimalFormat("0.0").format(size));
        mSettingManager.setParagraphSize(size);
    }


    /**
     * 初始化监听事件
     */
    private void initClick() {
        //监听翻页动画模式选择
        readSettingRgPageMode.setOnCheckedChangeListener((group, checkedId) -> {
            int pageModeIndex = 0;
            switch (checkedId) {
                case R.id.read_setting_rb_cover:
                    pageModeIndex = 0;
                    break;
                case R.id.read_setting_rb_simulation:
                    pageModeIndex = 1;
                    break;
                case R.id.read_setting_rb_slide:
                    pageModeIndex = 2;
                    break;
                case R.id.read_setting_rb_scroll:
                    pageModeIndex = 3;
                    break;
                case R.id.read_setting_rb_none:
                    pageModeIndex = 4;
                    break;
            }
            mSettingManager.setPageMode(pageModeIndex);
            mListener.onPageModeChange();
        });
    }

    @OnClick({R.id.read_setting_tv_white, R.id.read_setting_tv_yellow, R.id.read_setting_tv_green
            , R.id.read_setting_tv_blue, R.id.read_setting_tv_black, R.id.read_setting_tv_font_minus
            , R.id.read_setting_tv_font_plus, R.id.read_setting_tv_line_minus, R.id.read_setting_tv_line_plus
            , R.id.read_setting_tv_paragraph_minus, R.id.read_setting_tv_paragraph_plus, R.id.read_setting_tv_more})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.read_setting_tv_white:  //背景选择
                updateBg(0);
                mListener.onBgChange();
                break;
            case R.id.read_setting_tv_yellow:
                updateBg(1);
                mListener.onBgChange();
                break;
            case R.id.read_setting_tv_green:
                updateBg(2);
                mListener.onBgChange();
                break;
            case R.id.read_setting_tv_blue:
                updateBg(3);
                mListener.onBgChange();
                break;
            case R.id.read_setting_tv_black:
                updateBg(4);
                mListener.onBgChange();
                break;
            case R.id.read_setting_tv_font_minus:  //字号
                updateTextSize(mSettingManager.getTextSize() - 1);
                mListener.OnTextSizeChange();
                break;
            case R.id.read_setting_tv_font_plus:
                updateTextSize(mSettingManager.getTextSize() + 1);
                mListener.OnTextSizeChange();
                break;
            case R.id.read_setting_tv_line_minus:  //行间距
                updateLineSize(mSettingManager.getLineMultiplier() - 0.1f);
                mListener.OnMarginChange();
                break;
            case R.id.read_setting_tv_line_plus:
                updateLineSize(mSettingManager.getLineMultiplier() + 0.1f);
                mListener.OnMarginChange();
                break;
            case R.id.read_setting_tv_paragraph_minus:  //段间距
                updateParagraphSize(mSettingManager.getParagraphSize() - 0.1f);
                mListener.OnMarginChange();
                break;
            case R.id.read_setting_tv_paragraph_plus:
                updateParagraphSize(mSettingManager.getParagraphSize() + 0.1f);
                mListener.OnMarginChange();
                break;
            case R.id.read_setting_tv_more:
                mActivity.startActivity(new Intent(mActivity,FragmentActivity.class)
                        .putExtra(FragmentActivity.EXTRA_FRAGMENT_TYPE,FragmentActivity.FRAGMENT_TYPE_SETTING_READ));
                break;
        }
    }

    /**************************Interface**********************************/
    public interface OnSettingChangeListener {
        void onPageModeChange();

        void OnTextSizeChange();

        void OnMarginChange();

        void onBgChange();

        void refresh();
    }

}

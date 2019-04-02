package com.thmub.newbook.manager;

import com.thmub.newbook.utils.SharedPreUtils;
import com.thmub.newbook.utils.UiUtils;


/**
 * Created by Zhouas666 on 2019-03-29
 * Github: https://github.com/zas023
 * <p>
 * 阅读控制类
 */
public class ReadSettingManager {

    /**************************Constant***********************/
    public static final int READ_BG_DEFAULT = 0;
    public static final int READ_BG_1 = 1;
    public static final int READ_BG_2 = 2;
    public static final int READ_BG_3 = 3;
    public static final int READ_BG_4 = 4;
    public static final int READ_BG_5 = 5;
    public static final int NIGHT_MODE = 6;

    public static final String SHARED_READ_BG = "shared_read_bg";
    public static final String SHARED_READ_BRIGHTNESS = "shared_read_brightness";
    public static final String SHARED_READ_IS_BRIGHTNESS_AUTO = "shared_read_is_brightness_auto";
    public static final String SHARED_READ_TEXT_SIZE = "shared_read_text_size";
    public static final String SHARED_READ_IS_TEXT_DEFAULT = "shared_read_text_default";
    public static final String SHARED_READ_PAGE_MODE = "shared_read_mode";
    public static final String SHARED_READ_NIGHT_MODE = "shared_night_mode";
    public static final String SHARED_READ_VOLUME_TURN_PAGE = "shared_read_volume_turn_page";
    public static final String SHARED_READ_FULL_SCREEN = "shared_read_full_screen";

    private static volatile ReadSettingManager sInstance;

    private SharedPreUtils sharedPreUtils;

    public static ReadSettingManager getInstance() {
        if (sInstance == null) {
            synchronized (ReadSettingManager.class) {
                if (sInstance == null) {
                    sInstance = new ReadSettingManager();
                }
            }
        }
        return sInstance;
    }

    private ReadSettingManager() {
        sharedPreUtils = SharedPreUtils.getInstance();
    }

    public void setReadBackground(int theme) {
        sharedPreUtils.putInt(SHARED_READ_BG, theme);
    }

    public void setBrightness(int progress) {
        sharedPreUtils.putInt(SHARED_READ_BRIGHTNESS, progress);
    }

    public void setAutoBrightness(boolean isAuto) {
        sharedPreUtils.putBoolean(SHARED_READ_IS_BRIGHTNESS_AUTO, isAuto);
    }

    public void setDefaultTextSize(boolean isDefault) {
        sharedPreUtils.putBoolean(SHARED_READ_IS_TEXT_DEFAULT, isDefault);
    }

    public void setTextSize(int textSize) {
        sharedPreUtils.putInt(SHARED_READ_TEXT_SIZE, textSize);
    }

    public void setPageMode(int mode) {
        sharedPreUtils.putInt(SHARED_READ_PAGE_MODE, mode);
    }

    public void setNightMode(boolean isNight) {
        sharedPreUtils.putBoolean(SHARED_READ_NIGHT_MODE, isNight);
    }

    public int getBrightness() {
        return sharedPreUtils.getInt(SHARED_READ_BRIGHTNESS, 40);
    }

    public boolean isBrightnessAuto() {
        return sharedPreUtils.getBoolean(SHARED_READ_IS_BRIGHTNESS_AUTO, false);
    }

    public int getTextSize() {
        return sharedPreUtils.getInt(SHARED_READ_TEXT_SIZE, UiUtils.spToPx(17));
    }

    public boolean isDefaultTextSize() {
        return sharedPreUtils.getBoolean(SHARED_READ_IS_TEXT_DEFAULT, false);
    }

//    public int getPageMode() {
//        return sharedPreUtils.getInt(SHARED_READ_PAGE_MODE, PageView.PAGE_MODE_SIMULATION);
//    }

    public int getReadBgTheme() {
        return sharedPreUtils.getInt(SHARED_READ_BG, READ_BG_DEFAULT);
    }

    public boolean isNightMode() {
        return sharedPreUtils.getBoolean(SHARED_READ_NIGHT_MODE, false);
    }

    public void setVolumeTurnPage(boolean isTurn) {
        sharedPreUtils.putBoolean(SHARED_READ_VOLUME_TURN_PAGE, isTurn);
    }

    public boolean isVolumeTurnPage() {
        return sharedPreUtils.getBoolean(SHARED_READ_VOLUME_TURN_PAGE, false);
    }

    public void setFullScreen(boolean isFullScreen) {
        sharedPreUtils.putBoolean(SHARED_READ_FULL_SCREEN, isFullScreen);
    }

    public boolean isFullScreen() {
        return sharedPreUtils.getBoolean(SHARED_READ_FULL_SCREEN, false);
    }

}

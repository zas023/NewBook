package com.thmub.newbook.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import com.thmub.newbook.App;

import java.lang.reflect.Method;

/**
 * Created by zhouas666 on 2017/12/24.
 * 封装ui相关操作
 */
public class UiUtils {

    private static final String TAG = "UiUtils";

    /**********************************资源相关操作***********************************/

    /**
     * 获取上下文
     */
    public static Context getContext() {
        return App.getContext();
    }

    /**
     * 获取资源操作类
     */
    public static Resources getResources() {
        return getContext().getResources();
    }

    /**
     * 获取字符串资源
     *
     * @param id 资源id
     * @return 字符串
     */
    public static String getString(int id) {
        return getResources().getString(id);
    }

    /**
     * 获取字符串数组资源
     *
     * @param id 资源id
     * @return 字符串数组
     */
    public static String[] getStringArray(int id) {
        return getResources().getStringArray(id);
    }

    /**
     * 获取颜色资源
     */
    public static int getColor(int id) {
        return ContextCompat.getColor(getContext(), id);
    }

    /**
     * 获取颜色资源
     *
     * @param id    资源id
     * @param theme 样式
     * @return
     */
    public static int getColor(int id, Resources.Theme theme) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return getResources().getColor(id, theme);
        }
        return getResources().getColor(id);
    }

    /**
     * 获取drawable资源
     *
     * @param id 资源id
     * @return
     */
    public static Drawable getDrawable(int id) {
        return ContextCompat.getDrawable(getContext(), id);
    }

    /**
     * 加载布局（使用View方式）
     *
     * @param resource 布局资源id
     * @return View
     */
    public static View inflate(int resource) {
        return View.inflate(getContext(), resource, null);
    }

    /** 显示不带 null 的字符 */
    public static String show(String text){
        return text != null ? text : "";
    }


    /**********************************亮度相关操作*****************************/
    /**
     * 判断是否开启了自动亮度调节
     */
    public static boolean isAutoBrightness(Activity activity) {
        boolean isAuto = false;
        try {
            isAuto = Settings.System.getInt(activity.getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS_MODE) == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
        } catch (Settings.SettingNotFoundException e){
            e.printStackTrace();
        }
        return isAuto;
    }

    /**
     * 获取屏幕的亮度
     * 系统亮度模式中，自动模式与手动模式获取到的系统亮度的值不同
     */
    public static int getScreenBrightness(Activity activity) {
        if(isAutoBrightness(activity)){
            return getAutoScreenBrightness(activity);
        }else{
            return getManualScreenBrightness(activity);
        }
    }

    /**
     * 获取手动模式下的屏幕亮度
     * @return value:0~255
     */
    public static int getManualScreenBrightness(Activity activity) {
        int nowBrightnessValue = 0;
        ContentResolver resolver = activity.getContentResolver();
        try {
            nowBrightnessValue = Settings.System.getInt(resolver, Settings.System.SCREEN_BRIGHTNESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nowBrightnessValue;
    }

    /**
     * 获取自动模式下的屏幕亮度
     * @return value:0~255
     */
    public static int getAutoScreenBrightness(Activity activity) {
        float nowBrightnessValue = 0;

        //获取自动调节下的亮度范围在 0~1 之间
        ContentResolver resolver = activity.getContentResolver();
        try {
            //TODO:获取到的值与实际的亮度有差异，没有找到能够获得真正亮度值的方法，希望大佬能够告知。

            nowBrightnessValue = Settings.System.getFloat(resolver, "screen_auto_brightness_adj");
            Log.d(TAG, "getAutoScreenBrightness: " + nowBrightnessValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //转换范围为 (0~255)
        float fValue = nowBrightnessValue * 225.0f;
        Log.d(TAG,"brightness: " + fValue);
        return (int)fValue;
    }

    /**
     * 设置亮度:通过设置 Windows 的 screenBrightness 来修改当前 Windows 的亮度
     * lp.screenBrightness:参数范围为 0~1
     */
    public static void setBrightness(Activity activity, int brightness) {
        try{
            WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
            //将 0~255 范围内的数据，转换为 0~1
            lp.screenBrightness = Float.valueOf(brightness) * (1f / 255f);
            Log.d(TAG, "lp.screenBrightness == " + lp.screenBrightness);
            activity.getWindow().setAttributes(lp);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * 保存亮度设置状态
     */
    public static void saveBrightness(Activity activity, int brightness) {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.System.canWrite(activity)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
            intent.setData(Uri.parse("package:" + activity.getPackageName()));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
        }
        else {
            try{
                Uri uri = Settings.System.getUriFor(Settings.System.SCREEN_BRIGHTNESS);
                Settings.System.putInt(activity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, brightness);
                // resolver.registerContentObserver(uri, true, myContentObserver);
                activity.getContentResolver().notifyChange(uri, null);
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }

    /**
     * 停止自动亮度调节
     *
     * @param activity
     */
    public static void stopAutoBrightness(Activity activity) {
        //动态申请权限
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.System.canWrite(activity)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
            intent.setData(Uri.parse("package:" + activity.getPackageName()));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
        }
        else {
            Settings.System.putInt(activity.getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS_MODE,
                    Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
        }

    }

    /**
     * 开启亮度自动调节
     *
     * @param activity
     */
    public static void startAutoBrightness(Activity activity) {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.System.canWrite(activity)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
            intent.setData(Uri.parse("package:" + activity.getPackageName()));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
        }
        else {
            //有了权限，具体的动作
            Settings.System.putInt(activity.getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS, Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
        }
    }

    /**********************************单位转换*****************************/
    public static int dpToPx(int dp){
        DisplayMetrics metrics = getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,metrics);
    }

    public static int pxToDp(int px){
        DisplayMetrics metrics = getDisplayMetrics();
        return (int) (px / metrics.density);
    }

    public static int spToPx(int sp){
        DisplayMetrics metrics = getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,sp,metrics);
    }

    public static int pxToSp(int px){
        DisplayMetrics metrics = getDisplayMetrics();
        return (int) (px / metrics.scaledDensity);
    }

    /**********************************屏幕相关操作*****************************/
    /**
     * 获取手机显示App区域的大小（头部导航栏+ActionBar+根布局），不包括虚拟按钮
     * @return
     */
    public static int[] getAppSize(){
        int[] size = new int[2];
        DisplayMetrics metrics = getDisplayMetrics();
        size[0] = metrics.widthPixels;
        size[1] = metrics.heightPixels;
        return size;
    }

    /**
     * 获取整个手机屏幕的大小(包括虚拟按钮)
     * 必须在onWindowFocus方法之后使用
     * @param activity
     * @return
     */
    public static int[] getScreenSize(AppCompatActivity activity){
        int[] size = new int[2];
        View decorView = activity.getWindow().getDecorView();
        size[0] = decorView.getWidth();
        size[1] = decorView.getHeight();
        return size;
    }

    /**
     * 获取导航栏的高度
     * @return
     */
    public static int getStatusBarHeight(){
        Resources resources = App.getContext().getResources();
        int resourceId = resources.getIdentifier("status_bar_height","dimen","android");
        return resources.getDimensionPixelSize(resourceId);
    }

    /**
     * 获取虚拟按键的高度
     * @return
     */
    public static int getNavigationBarHeight() {
        int navigationBarHeight = 0;
        Resources rs = App.getContext().getResources();
        int id = rs.getIdentifier("navigation_bar_height", "dimen", "android");
        if (id > 0 && hasNavigationBar()) {
            navigationBarHeight = rs.getDimensionPixelSize(id);
        }
        return navigationBarHeight;
    }

    /**
     * 是否存在虚拟按键
     * @return
     */
    private static boolean hasNavigationBar() {
        boolean hasNavigationBar = false;
        Resources rs = App.getContext().getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {
        }
        return hasNavigationBar;
    }

    public static DisplayMetrics getDisplayMetrics(){
        DisplayMetrics metrics = App
                .getContext()
                .getResources()
                .getDisplayMetrics();
        return metrics;
    }

}


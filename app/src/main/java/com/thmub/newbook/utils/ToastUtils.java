package com.thmub.newbook.utils;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import es.dmoral.toasty.Toasty;

/**
 * Created by Zhouas666 on 2019-04-16
 * Github: https://github.com/zas023
 * <p>
 * Toast工具类：对Toasty的二次封装
 * https://github.com/GrenderG/Toasty
 */
public class ToastUtils {

    public static void show(@NonNull Context context, @NonNull String msg) {
        Toasty.normal(context, "此功能尚未完成").show();
    }

    //红色
    public static void showError(@NonNull Context context, @NonNull String msg) {
        Toasty.error(context, "此功能尚未完成", Toast.LENGTH_SHORT, true).show();
    }

    //绿色
    public static void showSuccess(@NonNull Context context, @NonNull String msg) {
        Toasty.success(context, "此功能尚未完成", Toast.LENGTH_SHORT, true).show();
    }

    //蓝色
    public static void showInfo(@NonNull Context context, @NonNull String msg) {
        Toasty.info(context, "此功能尚未完成", Toast.LENGTH_SHORT, true).show();
    }

    //黄色
    public static void showWarring(@NonNull Context context, @NonNull String msg) {
        Toasty.warning(context, "此功能尚未完成", Toast.LENGTH_SHORT, true).show();
    }
}

package com.thmub.newbook.utils;

import android.app.ProgressDialog;
import android.content.Context;

import com.thmub.newbook.R;

/**
 * Created by Zhouas666 on 2019-04-15
 * Github: https://github.com/zas023
 */
public class ProgressUtils {

    private static ProgressDialog dialog = null;

    public static void show(Context context) {
        show(context, null);
    }

    public static void show(Context context, String msg) {
        dialog = new ProgressDialog(context);
        dialog.setMessage(msg == null ? UiUtils.getString(R.string.common_loading) : msg);
        dialog.setCancelable(false);
        dialog.show();
    }

    public static void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}

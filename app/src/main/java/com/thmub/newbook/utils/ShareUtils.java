package com.thmub.newbook.utils;

import android.content.Context;
import android.content.Intent;

import com.thmub.newbook.R;

/**
 * Created by Zhouas666 on 2019-04-12
 * Github: https://github.com/zas023
 */
public class ShareUtils {
    public static void share(Context context, int stringRes) {
        share(context, context.getString(stringRes));
    }

    public static void share(Context context, String extraText) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.share));
        intent.putExtra(Intent.EXTRA_TEXT, extraText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(intent, context.getString(R.string.share)));
    }
}

package com.thmub.newbook.widget;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Zhouas666 on 2019-04-14
 * Github: https://github.com/zas023
 * <p>
 * https://blog.csdn.net/weizongwei5/article/details/50734586
 */
public class DashlineItemDivider extends RecyclerView.ItemDecoration {

    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            //以下计算主要用来确定绘制的位置
            final int top = child.getBottom() + params.bottomMargin;

            //绘制虚线
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.GRAY);
            Path path = new Path();
            path.moveTo(left, top);
            path.lineTo(right, top);
            //此处单位是像素不是dp  注意 请自行转化为dp
            PathEffect effects = new DashPathEffect(new float[]{15, 15, 15, 15}, 5);
            paint.setPathEffect(effects);
            c.drawPath(path, paint);


        }
    }
}

package com.thmub.newbook.widget;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Zhouas666 on 2019-03-30
 * Github: https://github.com/zas023
 *
 * recycler拖拽效果
 */
public class ItemDragCallback extends ItemTouchHelper.Callback {

    private OnItemTouchListener onItemTouchListener;

    public void ItemDragCallback(OnItemTouchListener onItemTouchListener) {
        this.onItemTouchListener = onItemTouchListener;
    }

    public void setOnItemTouchListener(OnItemTouchListener onItemTouchListener) {
        this.onItemTouchListener = onItemTouchListener;
    }

    /**
     * Item是否可以滑动
     * @return
     */
    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    /**
     * Item是否可以长按拖拽
     * @return
     */
    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }


    /**
     * 判断动作方向
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        //此处不需要进行滑动操作，可设置为除4和8之外的整数，这里设为0
        int flags = makeMovementFlags(dragFlags, 0);
        return flags;
    }

    /**
     * 拖拽回调
     */
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        if (viewHolder.getItemViewType() != target.getItemViewType()) {
            return false;
        }
        int fromPosition = viewHolder.getAdapterPosition();
        int toPosition   = target.getAdapterPosition();
        onItemTouchListener.onMove(fromPosition,toPosition);
        return true;
    }

    /**
     * 侧滑回调
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        //此处是侧滑删除的主要代码
        int position = viewHolder.getAdapterPosition();
        onItemTouchListener.onSwiped(position);
    }


    /*********************************Interface******************************/
    /**
     * 移动交换数据的更新监听
     */
    public interface OnItemTouchListener {
        //拖动Item时调用
        void onMove(int fromPosition, int toPosition);
        //滑动Item时调用
        void onSwiped(int position);
    }

}

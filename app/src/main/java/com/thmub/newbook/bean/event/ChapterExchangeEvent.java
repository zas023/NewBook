package com.thmub.newbook.bean.event;

/**
 * Created by Zhouas666 on 2019-04-16
 * Github: https://github.com/zas023
 */
public class ChapterExchangeEvent {
    private int chapterIndex;
    private int pageIndex;

    public ChapterExchangeEvent(int chapterIndex, int pageIndex) {
        this.chapterIndex = chapterIndex;
        this.pageIndex = pageIndex;
    }

    public int getChapterIndex() {
        return chapterIndex;
    }

    public int getPageIndex() {
        return pageIndex;
    }
}

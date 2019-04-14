package com.thmub.newbook.bean.event;

/**
 * Created by Zhouas666 on 2019-04-14
 * Github: https://github.com/zas023
 */
public class DownloadEvent {

    public String bookLink;

    public String message;

    public int status;

    public DownloadEvent() {
    }

    public DownloadEvent(String bookLink, String message) {
        this.bookLink = bookLink;
        this.message = message;
    }

    public DownloadEvent(String bookLink, String message, int status) {
        this.bookLink = bookLink;
        this.message = message;
        this.status = status;
    }
}

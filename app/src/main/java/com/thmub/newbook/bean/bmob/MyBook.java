package com.thmub.newbook.bean.bmob;

import com.thmub.newbook.bean.ShelfBookBean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Zhouas666 on 2019-05-08
 * Github: https://github.com/zas023
 */
public class MyBook extends BmobObject {

    //userId
    private String userId;
    //链接
    private String link;
    //标题
    private String title;
    //作者
    private String author;
    //简介
    private String desc;
    //封面地址
    private String cover;

    //目录地址
    private String catalogLink;
    //发现地址
    private String findLink;

    //书源
    private String sourceTag;

    //最新章节名称
    private String latestChapter;
    //总章节数量
    private int chapterCount;

    //最新更新日期
    private String updated;
    //最新阅读日期
    private String lastRead;
    //当前阅读章节名称
    private String curChapterTitle;
    //当前章节(包括番外)
    private Integer curChapter = 0;
    //当前章节位置(章节的页码)
    private Integer curChapterPage = 0;

    //是否是本地文件,打开的本地文件，而非指缓存文件
    private boolean isLocal = false;

    //版本号
    private int version;

    public MyBook() {

    }

//    public MyBook(ShelfBookBean bean) {
//        this.link = bean.getLink();
//        this.title = bean.getTitle();
//        this.author = bean.getAuthor();
//        this.desc = bean.getDesc();
//        this.cover = bean.getCover();
//        this.catalogLink = bean.getCatalogLink();
//        this.findLink = bean.getFindLink();
//        this.sourceTag = bean.getSourceTag();
//        this.latestChapter = bean.getLatestChapter();
//        this.chapterCount = bean.getChapterCount();
//        this.updated = bean.getUpdated();
//        this.lastRead = bean.getLastRead();
//        this.curChapterTitle = bean.getCurChapterTitle();
//        this.curChapter = bean.getCurChapter();
//        this.curChapterPage = bean.getCurChapterPage();
//        this.isLocal = bean.getIsLocal();
//        this.version = bean.getVersion();
//    }

    public MyBook(ShelfBookBean bean,String userId) {
        this.userId = userId;
        this.link = bean.getLink();
        this.title = bean.getTitle();
        this.author = bean.getAuthor();
        this.desc = bean.getDesc();
        this.cover = bean.getCover();
        this.catalogLink = bean.getCatalogLink();
        this.findLink = bean.getFindLink();
        this.sourceTag = bean.getSourceTag();
        this.latestChapter = bean.getLatestChapter();
        this.chapterCount = bean.getChapterCount();
        this.updated = bean.getUpdated();
        this.lastRead = bean.getLastRead();
        this.curChapterTitle = bean.getCurChapterTitle();
        this.curChapter = bean.getCurChapter();
        this.curChapterPage = bean.getCurChapterPage();
        this.isLocal = bean.getIsLocal();
        this.version = bean.getVersion();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getCatalogLink() {
        return catalogLink;
    }

    public void setCatalogLink(String catalogLink) {
        this.catalogLink = catalogLink;
    }

    public String getFindLink() {
        return findLink;
    }

    public void setFindLink(String findLink) {
        this.findLink = findLink;
    }

    public String getSourceTag() {
        return sourceTag;
    }

    public void setSourceTag(String sourceTag) {
        this.sourceTag = sourceTag;
    }

    public String getLatestChapter() {
        return latestChapter;
    }

    public void setLatestChapter(String latestChapter) {
        this.latestChapter = latestChapter;
    }

    public int getChapterCount() {
        return chapterCount;
    }

    public void setChapterCount(int chapterCount) {
        this.chapterCount = chapterCount;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getLastRead() {
        return lastRead;
    }

    public void setLastRead(String lastRead) {
        this.lastRead = lastRead;
    }

    public String getCurChapterTitle() {
        return curChapterTitle;
    }

    public void setCurChapterTitle(String curChapterTitle) {
        this.curChapterTitle = curChapterTitle;
    }

    public Integer getCurChapter() {
        return curChapter;
    }

    public void setCurChapter(Integer curChapter) {
        this.curChapter = curChapter;
    }

    public Integer getCurChapterPage() {
        return curChapterPage;
    }

    public void setCurChapterPage(Integer curChapterPage) {
        this.curChapterPage = curChapterPage;
    }

    public boolean isLocal() {
        return isLocal;
    }

    public void setLocal(boolean local) {
        isLocal = local;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }


    /******************************************************************/

    public ShelfBookBean getShelfBook() {

        ShelfBookBean book = new ShelfBookBean();
        book.setLink(link);
        book.setTitle(title);
        book.setAuthor(author);
        book.setDesc(desc);
        book.setCover(cover);
        book.setCatalogLink(catalogLink);
        book.setFindLink(findLink);
        book.setSourceTag(sourceTag);
        book.setLatestChapter(latestChapter);
        book.setLastRead(lastRead);
        book.setCurChapterTitle(curChapterTitle);
        book.setCurChapter(curChapter);
        book.setCurChapterPage(curChapterPage);
        book.setIsLocal(isLocal);
        book.setRid(getObjectId());
        book.setVersion(version);

        return book;
    }
}

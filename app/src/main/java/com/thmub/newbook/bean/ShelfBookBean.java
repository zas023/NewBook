package com.thmub.newbook.bean;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import java.util.List;

import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Transient;

/**
 * Created by Zhouas666 on 2019-03-26
 * Github: https://github.com/zas023
 * <p>
 * 书架bean
 */
@Entity
public class ShelfBookBean implements Parcelable {

    //书籍链接(本地书籍path)
    @Id
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

    //是否更新或未阅读
    private boolean isUpdate = true;
    //是否是本地文件,打开的本地文件，而非指缓存文件
    private boolean isLocal = false;

    //服务端id
    private String rid;
    //版本号
    private int version;

    @Transient
    private boolean isReading;    //正在阅读
    @Transient
    private boolean isCollected;  //是否添加收藏

    //@ToMany(referencedJoinProperty = "bookLink")
    @Transient
    private List<BookChapterBean> bookChapterList;


    @Generated(hash = 2051796434)
    public ShelfBookBean(String link, String title, String author, String desc, String cover, String catalogLink, String findLink,
            String sourceTag, String latestChapter, int chapterCount, String updated, String lastRead, String curChapterTitle,
            Integer curChapter, Integer curChapterPage, boolean isUpdate, boolean isLocal, String rid, int version) {
        this.link = link;
        this.title = title;
        this.author = author;
        this.desc = desc;
        this.cover = cover;
        this.catalogLink = catalogLink;
        this.findLink = findLink;
        this.sourceTag = sourceTag;
        this.latestChapter = latestChapter;
        this.chapterCount = chapterCount;
        this.updated = updated;
        this.lastRead = lastRead;
        this.curChapterTitle = curChapterTitle;
        this.curChapter = curChapter;
        this.curChapterPage = curChapterPage;
        this.isUpdate = isUpdate;
        this.isLocal = isLocal;
        this.rid = rid;
        this.version = version;
    }


    public ShelfBookBean(ShelfBookBean bean) {
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
        this.isUpdate = bean.getIsUpdate();
        this.isLocal = bean.getIsLocal();
        this.rid = bean.getRid();
        this.version = bean.getVersion();
        this.isCollected=bean.isCollected();
        this.isReading=bean.isReading();
    }


    @Generated(hash = 411799995)
    public ShelfBookBean() {
    }

    protected ShelfBookBean(Parcel in) {
        link = in.readString();
        title = in.readString();
        author = in.readString();
        desc = in.readString();
        cover = in.readString();
        catalogLink = in.readString();
        findLink = in.readString();
        sourceTag = in.readString();
        latestChapter = in.readString();
        chapterCount = in.readInt();
        updated = in.readString();
        lastRead = in.readString();
        curChapterTitle = in.readString();
        if (in.readByte() == 0) {
            curChapter = null;
        } else {
            curChapter = in.readInt();
        }
        if (in.readByte() == 0) {
            curChapterPage = null;
        } else {
            curChapterPage = in.readInt();
        }
        isUpdate = in.readByte() != 0;
        isLocal = in.readByte() != 0;
        rid = in.readString();
        version = in.readInt();
        isReading = in.readByte() != 0;
        isCollected = in.readByte() != 0;
        bookChapterList = in.createTypedArrayList(BookChapterBean.CREATOR);
    }

    public static final Creator<ShelfBookBean> CREATOR = new Creator<ShelfBookBean>() {
        @Override
        public ShelfBookBean createFromParcel(Parcel in) {
            return new ShelfBookBean(in);
        }

        @Override
        public ShelfBookBean[] newArray(int size) {
            return new ShelfBookBean[size];
        }
    };

    /*************************************************************************/

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

    public String getSourceTag() {
        return sourceTag;
    }

    public void setSourceTag(String sourceTag) {
        this.sourceTag = sourceTag;
    }


    public String getCatalogLink() {
        return catalogLink;
    }

    public void setCatalogLink(String catalogLink) {
        this.catalogLink = catalogLink;
    }

    public Integer getCurChapter() {
        return curChapter < 0 ? 0 : curChapter;
    }

    public void setCurChapter(Integer curChapter) {
        this.curChapter = curChapter;
    }

    public Integer getCurChapterPage() {
        return curChapterPage < 0 ? 0 : curChapterPage;
    }

    public void setCurChapterPage(Integer curChapterPage) {
        this.curChapterPage = curChapterPage;
    }

    public String getUpdated() {
        return this.updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getLastRead() {
        return this.lastRead;
    }

    public void setLastRead(String lastRead) {
        this.lastRead = lastRead;
    }

    public boolean getIsUpdate() {
        return this.isUpdate;
    }

    public void setIsUpdate(boolean isUpdate) {
        this.isUpdate = isUpdate;
    }

    public boolean getIsLocal() {
        return this.isLocal;
    }

    public void setIsLocal(boolean isLocal) {
        this.isLocal = isLocal;
    }

    public String getLatestChapter() {
        return this.latestChapter;
    }

    public void setLatestChapter(String latestChapter) {
        this.latestChapter = latestChapter;
    }

    public int getChapterCount() {
        return this.chapterCount;
    }

    public void setChapterCount(int chapterCount) {
        this.chapterCount = chapterCount;
    }

    public String getCurChapterTitle() {
        return this.curChapterTitle;
    }

    public void setCurChapterTitle(String curChapterTitle) {
        this.curChapterTitle = curChapterTitle;
    }

    public void setBookChapterList(List<BookChapterBean> bookChapterList) {
        if (bookChapterList == null) return;
        this.bookChapterList = bookChapterList;
        if (bookChapterList.size() > 0) {
            chapterCount = bookChapterList.size();
            latestChapter = bookChapterList.get(chapterCount - 1).getChapterTitle();
        }
    }

    public int getBookChapterListSize() {
        return bookChapterList == null ? 0 : getBookChapterList().size();
    }

    public BookChapterBean getChapter(int index) {
        if (bookChapterList.isEmpty()) {
            BookChapterBean bookChapterBean = new BookChapterBean();
            bookChapterBean.setChapterLink("暂无");
            bookChapterBean.setChapterTitle("暂无");
            return bookChapterBean;
        } else if (0 <= index && index < getBookChapterListSize()) {
            return getBookChapterList().get(index);
        } else {
            curChapter = getBookChapterListSize() - 1;
            return getBookChapterList().get(curChapter);
        }
    }

    public String getFindLink() {
        return findLink;
    }

    public void setFindLink(String findLink) {
        this.findLink = findLink;
    }

    public boolean isUpdate() {
        return isUpdate;
    }

    public void setUpdate(boolean update) {
        isUpdate = update;
    }

    public boolean isLocal() {
        return isLocal;
    }

    public void setLocal(boolean local) {
        isLocal = local;
    }

    public boolean isReading() {
        return isReading;
    }

    public void setReading(boolean reading) {
        isReading = reading;
    }

    public boolean isCollected() {
        return isCollected;
    }

    public void setCollected(boolean collected) {
        isCollected = collected;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public List<BookChapterBean> getBookChapterList() {
        return bookChapterList;
    }

    /***********************************************************/
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(link);
        dest.writeString(title);
        dest.writeString(author);
        dest.writeString(desc);
        dest.writeString(cover);
        dest.writeString(catalogLink);
        dest.writeString(findLink);
        dest.writeString(sourceTag);
        dest.writeString(latestChapter);
        dest.writeInt(chapterCount);
        dest.writeString(updated);
        dest.writeString(lastRead);
        dest.writeString(curChapterTitle);
        if (curChapter == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(curChapter);
        }
        if (curChapterPage == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(curChapterPage);
        }
        dest.writeByte((byte) (isUpdate ? 1 : 0));
        dest.writeByte((byte) (isLocal ? 1 : 0));
        dest.writeString(rid);
        dest.writeInt(version);
        dest.writeByte((byte) (isReading ? 1 : 0));
        dest.writeByte((byte) (isCollected ? 1 : 0));
        dest.writeTypedList(bookChapterList);
    }
}

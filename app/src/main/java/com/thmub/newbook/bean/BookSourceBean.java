package com.thmub.newbook.bean;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.OrderBy;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Zhouas666 on 2019-03-26
 * Github: https://github.com/zas023
 * <p>
 * 书源bean
 */
@Entity
public class BookSourceBean implements Parcelable {

    //书源地址
    @Id
    private String rootLink;    //利用url的唯一性
    //书源名称
    private String sourceName;
    //书源类型（xpath,json)
    private String sourceType;
    //编码格式
    private String encodeType;
    //搜索地址
    private String searchLink;

    //书源规则
    //搜索
    private String ruleSearchBooks;  //搜索结果列表（Json数据时用于定位）
    private String ruleSearchTitle;
    private String ruleSearchAuthor;
    private String ruleSearchDesc;
    private String ruleSearchCover;
    private String ruleSearchLink;  //书籍详情链接

    //详情
    private String ruleDetailBook;  //书籍详情（主要用于Json数据的定位）
    private String ruleDetailTitle;
    private String ruleDetailAuthor;
    private String ruleDetailDesc;
    private String ruleDetailCover;
    private String ruleFindLink;    //发现书籍链接地址
    private String ruleCatalogLink;   //指章节列表链接

    //注：详情规则与搜索有些相同，但简易都匹配，因为从findBook获取的书籍中可能无此字段，一般只有一个书籍链接

    //目录
    private String ruleChapters;
    private String ruleChapterLink;
    private String ruleChapterTitle;

    //章节
    private String ruleChapterContent;

    //发现
    private String ruleFindBooks;
    private String ruleFindCover;
    private String ruleFindBookLink;
    private String ruleFindBookTitle;

    //排序数
    @OrderBy
    private int orderNumber;
    //权重，通过网络延时计算
    @OrderBy
    @NotNull
    private int weight = 0;
    //是否选择
    private boolean isSelected;

    @Generated(hash = 1288396539)
    public BookSourceBean(String rootLink, String sourceName, String sourceType, String encodeType, String searchLink,
                          String ruleSearchBooks, String ruleSearchTitle, String ruleSearchAuthor, String ruleSearchDesc, String ruleSearchCover,
                          String ruleSearchLink, String ruleDetailBook, String ruleDetailTitle, String ruleDetailAuthor, String ruleDetailDesc,
                          String ruleDetailCover, String ruleFindLink, String ruleCatalogLink, String ruleChapters, String ruleChapterLink,
                          String ruleChapterTitle, String ruleChapterContent, String ruleFindBooks, String ruleFindCover, String ruleFindBookLink,
                          String ruleFindBookTitle, int orderNumber, int weight, boolean isSelected) {
        this.rootLink = rootLink;
        this.sourceName = sourceName;
        this.sourceType = sourceType;
        this.encodeType = encodeType;
        this.searchLink = searchLink;
        this.ruleSearchBooks = ruleSearchBooks;
        this.ruleSearchTitle = ruleSearchTitle;
        this.ruleSearchAuthor = ruleSearchAuthor;
        this.ruleSearchDesc = ruleSearchDesc;
        this.ruleSearchCover = ruleSearchCover;
        this.ruleSearchLink = ruleSearchLink;
        this.ruleDetailBook = ruleDetailBook;
        this.ruleDetailTitle = ruleDetailTitle;
        this.ruleDetailAuthor = ruleDetailAuthor;
        this.ruleDetailDesc = ruleDetailDesc;
        this.ruleDetailCover = ruleDetailCover;
        this.ruleFindLink = ruleFindLink;
        this.ruleCatalogLink = ruleCatalogLink;
        this.ruleChapters = ruleChapters;
        this.ruleChapterLink = ruleChapterLink;
        this.ruleChapterTitle = ruleChapterTitle;
        this.ruleChapterContent = ruleChapterContent;
        this.ruleFindBooks = ruleFindBooks;
        this.ruleFindCover = ruleFindCover;
        this.ruleFindBookLink = ruleFindBookLink;
        this.ruleFindBookTitle = ruleFindBookTitle;
        this.orderNumber = orderNumber;
        this.weight = weight;
        this.isSelected = isSelected;
    }


    @Generated(hash = 1512565980)
    public BookSourceBean() {
    }


    protected BookSourceBean(Parcel in) {
        rootLink = in.readString();
        sourceName = in.readString();
        sourceType = in.readString();
        encodeType = in.readString();
        searchLink = in.readString();
        ruleSearchBooks = in.readString();
        ruleSearchTitle = in.readString();
        ruleSearchAuthor = in.readString();
        ruleSearchDesc = in.readString();
        ruleSearchCover = in.readString();
        ruleSearchLink = in.readString();
        ruleDetailBook = in.readString();
        ruleDetailTitle = in.readString();
        ruleDetailAuthor = in.readString();
        ruleDetailDesc = in.readString();
        ruleDetailCover = in.readString();
        ruleFindLink = in.readString();
        ruleCatalogLink = in.readString();
        ruleChapters = in.readString();
        ruleChapterLink = in.readString();
        ruleChapterTitle = in.readString();
        ruleChapterContent = in.readString();
        ruleFindBooks = in.readString();
        ruleFindCover = in.readString();
        ruleFindBookLink = in.readString();
        ruleFindBookTitle = in.readString();
        orderNumber = in.readInt();
        weight = in.readInt();
        isSelected = in.readByte() != 0;
    }

    public static final Creator<BookSourceBean> CREATOR = new Creator<BookSourceBean>() {
        @Override
        public BookSourceBean createFromParcel(Parcel in) {
            return new BookSourceBean(in);
        }

        @Override
        public BookSourceBean[] newArray(int size) {
            return new BookSourceBean[size];
        }
    };

    public String getRootLink() {
        return rootLink;
    }

    public void setRootLink(String rootLink) {
        this.rootLink = rootLink;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getEncodeType() {
        return encodeType;
    }

    public void setEncodeType(String encodeType) {
        this.encodeType = encodeType;
    }

    public String getSearchLink() {
        return searchLink;
    }

    public void setSearchLink(String searchLink) {
        this.searchLink = searchLink;
    }

    public String getRuleSearchBooks() {
        if (ruleSearchBooks == null) return "";
        return ruleSearchBooks;
    }

    public void setRuleSearchBooks(String ruleSearchBooks) {
        this.ruleSearchBooks = ruleSearchBooks;
    }

    public String getRuleSearchTitle() {
        return ruleSearchTitle;
    }

    public void setRuleSearchTitle(String ruleSearchTitle) {
        this.ruleSearchTitle = ruleSearchTitle;
    }

    public String getRuleSearchAuthor() {
        return ruleSearchAuthor;
    }

    public void setRuleSearchAuthor(String ruleSearchAuthor) {
        this.ruleSearchAuthor = ruleSearchAuthor;
    }

    public String getRuleSearchDesc() {
        return ruleSearchDesc;
    }

    public void setRuleSearchDesc(String ruleSearchDesc) {
        this.ruleSearchDesc = ruleSearchDesc;
    }

    public String getRuleSearchCover() {
        return ruleSearchCover;
    }

    public void setRuleSearchCover(String ruleSearchCover) {
        this.ruleSearchCover = ruleSearchCover;
    }

    public String getRuleSearchLink() {
        return ruleSearchLink;
    }

    public void setRuleSearchLink(String ruleSearchLink) {
        this.ruleSearchLink = ruleSearchLink;
    }

    public String getRuleDetailBook() {
        if (ruleDetailBook == null) return "";
        return ruleDetailBook;
    }

    public void setRuleDetailBook(String ruleDetailBook) {
        this.ruleDetailBook = ruleDetailBook;
    }

    public String getRuleDetailTitle() {
        return ruleDetailTitle;
    }

    public void setRuleDetailTitle(String ruleDetailTitle) {
        this.ruleDetailTitle = ruleDetailTitle;
    }

    public String getRuleDetailAuthor() {
        return ruleDetailAuthor;
    }

    public void setRuleDetailAuthor(String ruleDetailAuthor) {
        this.ruleDetailAuthor = ruleDetailAuthor;
    }

    public String getRuleDetailDesc() {
        return ruleDetailDesc;
    }

    public void setRuleDetailDesc(String ruleDetailDesc) {
        this.ruleDetailDesc = ruleDetailDesc;
    }

    public String getRuleDetailCover() {
        return ruleDetailCover;
    }

    public void setRuleDetailCover(String ruleDetailCover) {
        this.ruleDetailCover = ruleDetailCover;
    }

    public String getRuleFindLink() {
        return ruleFindLink;
    }

    public void setRuleFindLink(String ruleFindLink) {
        this.ruleFindLink = ruleFindLink;
    }

    public String getRuleCatalogLink() {
        return ruleCatalogLink;
    }

    public void setRuleCatalogLink(String ruleCatalogLink) {
        this.ruleCatalogLink = ruleCatalogLink;
    }

    public String getRuleChapters() {
        return ruleChapters;
    }

    public void setRuleChapters(String ruleChapters) {
        this.ruleChapters = ruleChapters;
    }

    public String getRuleChapterLink() {
        return ruleChapterLink;
    }

    public void setRuleChapterLink(String ruleChapterLink) {
        this.ruleChapterLink = ruleChapterLink;
    }

    public String getRuleChapterTitle() {
        return ruleChapterTitle;
    }

    public void setRuleChapterTitle(String ruleChapterTitle) {
        this.ruleChapterTitle = ruleChapterTitle;
    }

    public String getRuleChapterContent() {
        return ruleChapterContent;
    }

    public void setRuleChapterContent(String ruleChapterContent) {
        this.ruleChapterContent = ruleChapterContent;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getRuleFindBooks() {
        if (ruleFindBooks==null) return "";
        return ruleFindBooks;
    }

    public void setRuleFindBooks(String ruleFindBooks) {
        this.ruleFindBookLink = ruleFindBooks;
    }

    public void getRuleFindCoversetRuleFindBooks(String ruleFindBooks) {
        this.ruleFindBooks = ruleFindBooks;
    }

    public String getRuleFindBookLink() {
        return ruleFindBookLink;
    }

    public void setRuleFindBookLink(String ruleFindBookLink) {
        this.ruleFindBookLink = ruleFindBookLink;
    }

    public String getRuleFindBookTitle() {
        return ruleFindBookTitle;
    }

    public void setRuleFindBookTitle(String ruleFindBookTitle) {
        this.ruleFindBookTitle = ruleFindBookTitle;
    }

    public String getRuleFindCover() {
        return ruleFindCover;
    }

    public void setRuleFindCover(String ruleFindCover) {
        this.ruleFindCover = ruleFindCover;
    }

    /*************************************************************************/

    public boolean getIsSelected() {
        return this.isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(rootLink);
        dest.writeString(sourceName);
        dest.writeString(sourceType);
        dest.writeString(encodeType);
        dest.writeString(searchLink);
        dest.writeString(ruleSearchBooks);
        dest.writeString(ruleSearchTitle);
        dest.writeString(ruleSearchAuthor);
        dest.writeString(ruleSearchDesc);
        dest.writeString(ruleSearchCover);
        dest.writeString(ruleSearchLink);
        dest.writeString(ruleDetailBook);
        dest.writeString(ruleDetailTitle);
        dest.writeString(ruleDetailAuthor);
        dest.writeString(ruleDetailDesc);
        dest.writeString(ruleDetailCover);
        dest.writeString(ruleFindLink);
        dest.writeString(ruleCatalogLink);
        dest.writeString(ruleChapters);
        dest.writeString(ruleChapterLink);
        dest.writeString(ruleChapterTitle);
        dest.writeString(ruleChapterContent);
        dest.writeString(ruleFindBooks);
        dest.writeString(ruleFindCover);
        dest.writeString(ruleFindBookLink);
        dest.writeString(ruleFindBookTitle);
        dest.writeInt(orderNumber);
        dest.writeInt(weight);
        dest.writeByte((byte) (isSelected ? 1 : 0));
    }
}

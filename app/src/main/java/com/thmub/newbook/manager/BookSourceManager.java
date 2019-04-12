package com.thmub.newbook.manager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thmub.newbook.bean.BookSourceBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhouas666 on 2019-03-28
 * Github: https://github.com/zas023
 */

public class BookSourceManager {
    private static volatile BookSourceManager sInstance;

    private List<BookSourceBean> mSourceList;

    public static BookSourceManager getInstance() {
        if (sInstance == null) {
            synchronized (BookSourceManager.class) {
                if (sInstance == null) {
                    sInstance = new BookSourceManager();
                }
            }
        }
        return sInstance;
    }

    public BookSourceManager() {
        Gson gson = new Gson();
        this.mSourceList = new ArrayList<>();
        String sourceJsonStr = "[{\n" +
                "\t\t\"rootLink\": \"http://api.zhuishushenqi.com\",\n" +
                "\t\t\"sourceName\": \"追书神器\",\n" +
                "\t\t\"searchLink\": \"/book/fuzzy-search?query=%s\",\n" +
                "\t\t\"sourceType\": \"Json\",\n" +
                "\t\t\"encodeType\": \"utf-8\",\n" +
                "\t\t\"ruleSearchBook\": \"books\",\n" +
                "\t\t\"ruleSearchTitle\": \"title\",\n" +
                "\t\t\"ruleSearchAuthor\": \"author\",\n" +
                "\t\t\"ruleSearchDesc\": \"shortIntro\",\n" +
                "\t\t\"ruleSearchCover\": \"cover@http://statics.zhuishushenqi.com%s\",\n" +
                "\t\t\"ruleSearchLink\": \"_id@http://api.zhuishushenqi.com/mix-atoc/%s?view=chapters\",\n" +
                "\t\t\"ruleCatalogChapter\": \"mixToc$chapters\",\n" +
                "\t\t\"ruleCatalogTitle\": \"title\",\n" +
                "\t\t\"ruleCatalogLink\": \"link@UrlEncode#http://chapterup.zhuishushenqi.com/chapter/%s\",\n" +
                "\t\t\"ruleChapterContent\": \"chapter$body\",\n" +
                "\t\t\"orderNumber\": \"0\",\n" +
                "\t\t\"weight\": \"0\",\n" +
                "\t\t\"isSelected\": \"true\"\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"rootLink\": \"https://www.xbiquge6.com\",\n" +
                "\t\t\"sourceName\": \"新笔趣阁\",\n" +
                "\t\t\"searchLink\": \"/search.php?keyword=%s\",\n" +
                "\t\t\"sourceType\": \"Xpath\",\n" +
                "\t\t\"encodeType\": \"utf-8\",\n" +
                "\t\t\"ruleSearchBook\": \"//div[@class='result-game-item-detail']\",\n" +
                "\t\t\"ruleSearchTitle\": \"//div[@class='result-game-item-detail']//h3//a/@title\",\n" +
                "\t\t\"ruleSearchAuthor\": \"//div[@class='result-game-item-detail']//div[@class='result-game-item-info']//p[1]/span[2]/text()\",\n" +
                "\t\t\"ruleSearchDesc\": \"//div[@class='result-game-item-detail']//p[@class='result-game-item-desc']/text()\",\n" +
                "\t\t\"ruleSearchCover\": \"//div[@class='result-game-item-pic']//a//img/@src\",\n" +
                "\t\t\"ruleSearchLink\": \"//div[@class='result-game-item-detail']//h3//a/@href\",\n" +
                "\t\t\"ruleCatalogChapter\": \"//div[@id='list']\",\n" +
                "\t\t\"ruleCatalogTitle\": \"//div[@id='list']//dl//dd//a/text()\",\n" +
                "\t\t\"ruleCatalogLink\": \"//div[@id='list']//dl//dd//a/@href\",\n" +
                "\t\t\"ruleChapterContent\": \"//div[@id='content']\",\n" +
                "\t\t\"orderNumber\": \"0\",\n" +
                "\t\t\"weight\": \"0\",\n" +
                "\t\t\"isSelected\": \"true\"\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"rootLink\": \"https://www.zwdu.com\",\n" +
                "\t\t\"sourceName\": \"八一中文网\",\n" +
                "\t\t\"searchLink\": \"/search.php?keyword=%s\",\n" +
                "\t\t\"sourceType\": \"Xpath\",\n" +
                "\t\t\"encodeType\": \"gbk&utf-8\",\n" +
                "\t\t\"ruleSearchBook\": \"//div[@class='result-game-item-detail']\",\n" +
                "\t\t\"ruleSearchTitle\": \"//div[@class='result-game-item-detail']//h3//a/@title\",\n" +
                "\t\t\"ruleSearchAuthor\": \"//div[@class='result-game-item-detail']//div[@class='result-game-item-info']//p[1]/span[2]/text()\",\n" +
                "\t\t\"ruleSearchDesc\": \"//div[@class='result-game-item-detail']//p[@class='result-game-item-desc']/text()\",\n" +
                "\t\t\"ruleSearchCover\": \"//div[@class='result-game-item-pic']//a//img/@src\",\n" +
                "\t\t\"ruleSearchLink\": \"//div[@class='result-game-item-detail']//h3//a/@href\",\n" +
                "\t\t\"ruleCatalogChapter\": \"//div[@id='list']\",\n" +
                "\t\t\"ruleCatalogTitle\": \"//div[@id='list']//dl//dd//a/text()\",\n" +
                "\t\t\"ruleCatalogLink\": \"//div[@id='list']//dl//dd//a/@href\",\n" +
                "\t\t\"ruleChapterContent\": \"//div[@id='content']\",\n" +
                "\t\t\"orderNumber\": \"0\",\n" +
                "\t\t\"weight\": \"0\",\n" +
                "\t\t\"isSelected\": \"true\"\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"rootLink\": \"https://www.liewen.cc\",\n" +
                "\t\t\"sourceName\": \"猎文\",\n" +
                "\t\t\"searchLink\": \"/search.php?keyword=%s\",\n" +
                "\t\t\"sourceType\": \"Xpath\",\n" +
                "\t\t\"encodeType\": \"gbk&utf-8\",\n" +
                "\t\t\"ruleSearchBook\": \"//div[@class='result-game-item-detail']\",\n" +
                "\t\t\"ruleSearchTitle\": \"//div[@class='result-game-item-detail']//h3//a/@title\",\n" +
                "\t\t\"ruleSearchAuthor\": \"//div[@class='result-game-item-detail']//div[@class='result-game-item-info']//p[1]/span[2]/text()\",\n" +
                "\t\t\"ruleSearchDesc\": \"//div[@class='result-game-item-detail']//p[@class='result-game-item-desc']/text()\",\n" +
                "\t\t\"ruleSearchCover\": \"//div[@class='result-game-item-pic']//a//img/@src\",\n" +
                "\t\t\"ruleSearchLink\": \"//div[@class='result-game-item-detail']//h3//a/@href\",\n" +
                "\t\t\"ruleCatalogChapter\": \"//div[@id='list']\",\n" +
                "\t\t\"ruleCatalogTitle\": \"//div[@id='list']//dl//dd//a/text()\",\n" +
                "\t\t\"ruleCatalogLink\": \"//div[@id='list']//dl//dd//a/@href\",\n" +
                "\t\t\"ruleChapterContent\": \"//div[@id='content']\",\n" +
                "\t\t\"orderNumber\": \"0\",\n" +
                "\t\t\"weight\": \"0\",\n" +
                "\t\t\"isSelected\": \"true\"\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"rootLink\": \"http://www.xszww.com\",\n" +
                "\t\t\"sourceName\": \"小说中文\",\n" +
                "\t\t\"searchLink\": \"/s.php?ie=gbk&s=10385337132858012269&q=%s\",\n" +
                "\t\t\"sourceType\": \"Xpath\",\n" +
                "\t\t\"encodeType\": \"gbk\",\n" +
                "\t\t\"ruleSearchBook\": \"//div[@class='bookinfo']\",\n" +
                "\t\t\"ruleSearchTitle\": \"//div[@class='bookinfo']//h4//a/text()\",\n" +
                "\t\t\"ruleSearchAuthor\": \"//div[@class='author']/text()\",\n" +
                "\t\t\"ruleSearchDesc\": \"//div[@class='bookinfo']//p/text()\",\n" +
                "\t\t\"ruleSearchCover\": \"//div[@class='bookimg']//a//img/@src\",\n" +
                "\t\t\"ruleSearchLink\": \"//div[@class='bookimg']//a/@href\",\n" +
                "\t\t\"ruleCatalogChapter\": \"//div[@class='listmain']\",\n" +
                "\t\t\"ruleCatalogTitle\": \"//div[@class='listmain']/dl/dt[2]/following-sibling::dd/a/text()\",\n" +
                "\t\t\"ruleCatalogLink\": \"///div[@class='listmain']/dl/dt[2]/following-sibling::dd/a/@href\",\n" +
                "\t\t\"ruleChapterContent\": \"//div[@id='content']\",\n" +
                "\t\t\"orderNumber\": \"0\",\n" +
                "\t\t\"weight\": \"0\",\n" +
                "\t\t\"isSelected\": \"true\"\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"rootLink\": \"https://www.bimo.cc\",\n" +
                "\t\t\"sourceName\": \"追书\",\n" +
                "\t\t\"searchLink\": \"/search.aspx?keyword=%s\",\n" +
                "\t\t\"sourceType\": \"Xpath\",\n" +
                "\t\t\"encodeType\": \"utf-8\",\n" +
                "\t\t\"ruleSearchBook\": \"//div[@class='result-game-item-detail']\",\n" +
                "\t\t\"ruleSearchTitle\": \"//div[@class='result-game-item-detail']//h3//a/@title\",\n" +
                "\t\t\"ruleSearchAuthor\": \"//div[@class='result-game-item-detail']//div[@class='result-game-item-info']//p[1]/span[2]/text()\",\n" +
                "\t\t\"ruleSearchDesc\": \"//div[@class='result-game-item-detail']//p[@class='result-game-item-desc']/text()\",\n" +
                "\t\t\"ruleSearchCover\": \"//div[@class='result-game-item-pic']//a//img/@src\",\n" +
                "\t\t\"ruleSearchLink\": \"//div[@class='result-game-item-detail']//h3//a/@href\",\n" +
                "\t\t\"ruleCatalogChapter\": \"//div[@id='list']\",\n" +
                "\t\t\"ruleCatalogTitle\": \"//div[@id='list']//dl//dd//a/text()\",\n" +
                "\t\t\"ruleCatalogLink\": \"//div[@id='list']//dl//dd//a/@href\",\n" +
                "\t\t\"ruleChapterContent\": \"//div[@id='content']\",\n" +
                "\t\t\"orderNumber\": \"0\",\n" +
                "\t\t\"weight\": \"0\",\n" +
                "\t\t\"isSelected\": \"true\"\n" +
                "\t}\n" +
                "]";
        mSourceList.addAll(gson.fromJson(sourceJsonStr, new TypeToken<List<BookSourceBean>>() {
        }.getType()));
    }

    public BookSourceBean getBookSourceById(String sourceId) {
        for (BookSourceBean source : mSourceList) {
            if (sourceId.equals(source.getSourceName()))
                return source;
        }
        return null;
    }

    public List<BookSourceBean> getBookSourceList() {
        return mSourceList;
    }
}

package com.thmub.newbook.model;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.thmub.newbook.bean.BookChapterBean;
import com.thmub.newbook.bean.BookContentBean;
import com.thmub.newbook.bean.BookDetailBean;
import com.thmub.newbook.bean.BookSearchBean;
import com.thmub.newbook.bean.BookSourceBean;
import com.thmub.newbook.bean.ShelfBookBean;
import com.thmub.newbook.utils.OkHttpUtils;
import com.thmub.newbook.utils.StringUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static android.text.TextUtils.isEmpty;

/**
 * Created by Zhouas666 on 2019-04-12
 * Github: https://github.com/zas023
 */
public class JsonSourceModel implements ISourceModel {

    private BookSourceBean bookSourceBean;

    public JsonSourceModel(BookSourceBean bean) {
        this.bookSourceBean = bean;
    }

    public String executeOp(String[] rules, String str) {
        if (rules == null) return str;
        for (int j = 0; j < rules.length; j++) {
            if (rules[j].equals("UrlEncode")) {
                try {
                    str = URLEncoder.encode(str, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            if (rules[j].contains("%s")) {
                str = String.format(rules[j], str);
            }
        }
        return str;
    }

    /**
     * 同类书籍
     *
     * @param findLink
     * @return
     */
    @Override
    public Observable<List<BookSearchBean>> findBook(String findLink) {
        if (isEmpty(findLink)) {
            return Observable.create(emitter -> {
                emitter.onNext(null);
                emitter.onComplete();
            });
        }
        return parseBookList(findLink, bookSourceBean.getRuleSearchBooks());
    }

    /**
     * 搜索书籍
     *
     * @param keyword
     * @return
     */
    @Override
    public Observable<List<BookSearchBean>> searchBook(String keyword) {
        if (isEmpty(bookSourceBean.getSearchLink())) {
            return Observable.create(emitter -> {
                emitter.onNext(null);
                emitter.onComplete();
            });
        }

        String jsonLink = bookSourceBean.getRootLink() + String.format(bookSourceBean.getSearchLink(), keyword);

        return parseBookList(jsonLink, bookSourceBean.getRuleSearchBooks());
    }

    /**
     * 解析书籍详情
     *
     * @param bookBean
     * @return
     */
    public Observable<BookDetailBean> parseBook(BookSearchBean bookBean) {
        Log.i("JsonSourceModel", "parseBook:" + bookBean.toString());
        return Observable.create(emitter -> {
            String jsonStr = null;
            try {
                jsonStr = OkHttpUtils.getHtml(bookBean.getBookLink(), bookSourceBean.getEncodeType());
            } catch (IOException e) {
                e.printStackTrace();
            }

            String bookJson = bookSourceBean.getRuleDetailBook();
            String titleJson = bookSourceBean.getRuleDetailTitle();
            if (titleJson == null)
                titleJson = bookSourceBean.getRuleSearchTitle();
            String authorJson = bookSourceBean.getRuleDetailAuthor();
            if (authorJson == null)
                authorJson = bookSourceBean.getRuleSearchAuthor();
            String coverJson = bookSourceBean.getRuleDetailCover();
            if (coverJson == null)
                coverJson = bookSourceBean.getRuleSearchCover();
            String descJson = bookSourceBean.getRuleDetailDesc();
            if (descJson == null)
                descJson = bookSourceBean.getRuleSearchDesc();
            String catalogLinkJson = bookSourceBean.getRuleCatalogLink();
            String findLinkJson = bookSourceBean.getRuleFindLink();

            JsonParser parser = new JsonParser();  //创建JSON解析器
            JsonObject book = (JsonObject) parser.parse(jsonStr);  //创建JsonObject对象
            //定位到书籍详情字段
            if (bookJson != null && !isEmpty(bookJson)) {
                String[] bookRules = bookJson.split("\\$");
                for (int i = 0; i < bookRules.length; i++) {
                    book = book.get(bookRules[i]).getAsJsonObject();
                }
            }
            BookDetailBean bean = new BookDetailBean(bookBean);
            //书名
            if (titleJson != null) {
                String[] titleRules = titleJson.split("@");
                if (titleRules.length > 1) {
                    titleJson = titleRules[0];
                    titleRules = titleRules[1].split("#");
                }
                bean.setTitle(executeOp(titleRules, book.get(titleJson).getAsString()));
            }
            //封面
            if (coverJson != null) {
                String[] coverRules = coverJson.split("@");
                if (coverRules.length > 1) {
                    coverJson = coverRules[0];
                    coverRules = coverRules[1].split("#");
                }
                bean.setCover(executeOp(coverRules, book.get(coverJson).getAsString()));
            }
            //作者
            if (authorJson != null) {
                String[] authorRules = authorJson.split("@");
                if (authorRules.length > 1) {
                    authorJson = authorRules[0];
                    authorRules = authorRules[1].split("#");
                }
                bean.setAuthor(executeOp(authorRules, book.get(authorJson).getAsString()));
            }
            //简介
            if (descJson != null) {
                String[] descRules = descJson.split("@");
                if (descRules.length > 1) {
                    descJson = descRules[0];
                    descRules = descRules[1].split("#");
                }
                bean.setDesc(executeOp(descRules, book.get(descJson).getAsString()));
            }
            //目录
            if (catalogLinkJson != null) {
                String[] catalogLinkRules = catalogLinkJson.split("@");
                if (catalogLinkRules.length > 1) {
                    catalogLinkJson = catalogLinkRules[0];
                    catalogLinkRules = catalogLinkRules[1].split("#");
                }
                bean.setCatalogLink(executeOp(catalogLinkRules, book.get(catalogLinkJson).getAsString()));
            }
            //发现
            if (findLinkJson != null) {
                String[] findLinkRules = findLinkJson.split("@");
                if (findLinkRules.length > 1) {
                    findLinkJson = findLinkRules[0];
                    findLinkRules = findLinkRules[1].split("#");
                }
                bean.setFindLink(executeOp(findLinkRules, book.get(findLinkJson).getAsString()));
            }

            //来源
            bean.setSourceTag(bookSourceBean.getSourceName());
            Log.i("JsonSourceModel", bean.toString());

            emitter.onNext(bean);
            emitter.onComplete();

        });
    }

    /**
     * 解析书籍列表
     *
     * @param jsonLink
     * @param bookRule
     * @return
     */
    public Observable<List<BookSearchBean>> parseBookList(String jsonLink, String bookRule) {

        return Observable.create(emitter -> {

            String jsonStr = null, encodeType;
            //获取书源搜索地址的编码
            String[] encodes = bookSourceBean.getEncodeType().split("&");
            if (encodes.length == 1)
                encodeType = encodes[0];
            else
                encodeType = encodes[1];

            try {
                jsonStr = OkHttpUtils.getHtml(jsonLink, encodeType);
            } catch (IOException e) {
                e.printStackTrace();
            }

            String bookJson = bookRule;
            String titleJson = bookSourceBean.getRuleSearchTitle();
            String coverJson = bookSourceBean.getRuleSearchCover();
            String bookLinkJson = bookSourceBean.getRuleSearchLink();
            String findLinkJson = bookSourceBean.getRuleFindLink();
            String authorJson = bookSourceBean.getRuleSearchAuthor();
            String descJson = bookSourceBean.getRuleSearchDesc();

            String[] bookRules = null;
            if (bookJson != null) {
                bookRules = bookJson.split("\\$");
            }
            String[] titleRules = null;
            if (titleJson != null) {
                titleRules = titleJson.split("@");
                if (titleRules.length > 1) {
                    titleJson = titleRules[0];
                    titleRules = titleRules[1].split("#");
                }
            }
            String[] coverRules = null;
            if (coverJson != null) {
                coverRules = coverJson.split("@");
                if (coverRules.length > 1) {
                    coverJson = coverRules[0];
                    coverRules = coverRules[1].split("#");
                }
            }
            String[] linkRules = null;
            if (bookLinkJson != null) {
                linkRules = bookLinkJson.split("@");
                if (linkRules.length > 1) {
                    bookLinkJson = linkRules[0];
                    linkRules = linkRules[1].split("#");
                }
            }
            String[] findLinkRules = null;
            if (findLinkJson != null) {
                findLinkRules = findLinkJson.split("@");
                if (findLinkRules.length > 1) {
                    findLinkJson = findLinkRules[0];
                    findLinkRules = findLinkRules[1].split("#");
                }
            }
            String[] authorRules = null;
            if (authorJson != null) {
                authorRules = authorJson.split("@");
                if (authorRules.length > 1) {
                    authorJson = authorRules[0];
                    authorRules = authorRules[1].split("#");
                }
            }
            String[] descRules = null;
            if (descJson != null) {
                descRules = descJson.split("@");
                if (descRules.length > 1) {
                    descJson = descRules[0];
                    descRules = descRules[1].split("#");
                }
            }

            JsonParser parser = new JsonParser();  //创建JSON解析器
            JsonArray books;
            if (bookRules == null) {
                books = (JsonArray) parser.parse(jsonStr);  //创建JsonObject对象
            } else {
                JsonObject object = (JsonObject) parser.parse(jsonStr);  //创建JsonObject对象
                for (int i = 0; i < bookRules.length - 1; i++) {
                    object = object.get(bookRules[i]).getAsJsonObject();
                }
                books = object.get(bookRules[bookRules.length - 1]).getAsJsonArray();
            }
            List<BookSearchBean> bookList = new ArrayList<>();
            for (int i = 0; i < books.size(); i++) {
                BookSearchBean bean = new BookSearchBean();

                JsonObject book = books.get(i).getAsJsonObject();
                //书名
                String title = book.get(titleJson).getAsString();
                bean.setTitle(executeOp(titleRules, title));
                //书籍链接(目录链接)
                String link = book.get(bookLinkJson).getAsString();
                link = executeOp(linkRules, link);
                bean.setBookLink(link);
                bean.setCatalogLink(link);
                //发现
                if (findLinkJson != null) {
                    String findLink = book.get(findLinkJson).getAsString();
                    bean.setFindLink(executeOp(findLinkRules, findLink));
                }
                //封面
                String cover = book.get(coverJson).getAsString();
                bean.setCover(executeOp(coverRules, cover));
                //作者
                String author = book.get(authorJson).getAsString();
                bean.setAuthor(executeOp(authorRules, author));
                //简介
                String desc = book.get(descJson).getAsString();
                bean.setDesc(executeOp(descRules, desc));
                //来源
                bean.setSourceTag(bookSourceBean.getSourceName());

                bookList.add(bean);

                Log.i("JsonSourceModel", bean.toString());
            }

            emitter.onNext(bookList);
            emitter.onComplete();

        });
    }

    private String[] analyseRule(String ruleJson) {
        if (ruleJson == null) return null;
        String[] rules = ruleJson.split("@");
        if (rules.length > 1) {
            ruleJson = rules[0];
            rules = rules[1].split("#");
        }
        return rules;
    }

    /**
     * 解析目录
     *
     * @param book
     * @param num
     * @return
     */
    @Override
    public Observable<List<BookChapterBean>> parseCatalog(ShelfBookBean book, int num) {
        if (book != null && isEmpty(book.getCatalogLink())) {
            return Observable.create(emitter -> {
                emitter.onNext(null);
                emitter.onComplete();
            });
        }
        return Observable.create(emitter -> {
            String jsonStr = null;
            try {
                jsonStr = OkHttpUtils.getHtml(book.getCatalogLink(), bookSourceBean.getEncodeType().split("&")[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }

            String chapterJson = bookSourceBean.getRuleChapters();
            String titleJson = bookSourceBean.getRuleChapterTitle();
            String linkJson = bookSourceBean.getRuleChapterLink();

            String[] chapterRules = chapterJson.split("\\$");
            String[] titleRules = titleJson.split("@");
            if (titleRules.length > 1) {
                titleJson = titleRules[0];
                titleRules = titleRules[1].split("#");
            }
            String[] linkRules = linkJson.split("@");
            if (linkRules.length > 1) {
                linkJson = linkRules[0];
                linkRules = linkRules[1].split("#");
            }

            JsonParser parser = new JsonParser();
            JsonObject object = (JsonObject) parser.parse(jsonStr);
            for (int i = 0; i < chapterRules.length - 1; i++) {
                object = object.get(chapterRules[i]).getAsJsonObject();
            }
            JsonArray chapters = object.get(chapterRules[chapterRules.length - 1]).getAsJsonArray();
            List<BookChapterBean> chapterList = new ArrayList<>();

            if (num < 0)
                //注意i > flag，不能i >= flag
                for (int i = chapters.size() - 1, flag = Math.max(0, chapters.size() + num - 1); i > flag; i--) {
                    BookChapterBean bean = new BookChapterBean();

                    JsonObject chapter = chapters.get(i).getAsJsonObject();

                    //章节名称
                    String title = chapter.get(titleJson).getAsString();
                    bean.setChapterTitle(StringUtils.fullToHalf(executeOp(titleRules, title)));
                    //章节链接
                    String link = chapter.get(linkJson).getAsString();
                    bean.setChapterLink(executeOp(linkRules, link));
                    //章节序号
                    bean.setChapterIndex(i);
                    //章节对应的书籍
                    bean.setBookLink(book.getLink());
                    bean.setBookTitle(book.getTitle());
                    //对应书源
                    bean.setTag(book.getSourceTag());

                    chapterList.add(bean);

                    Log.i("JsonSourceModel", bean.toString());
                }
            else if (num == 0)
                for (int i = 0; i < chapters.size(); i++) {
                    BookChapterBean bean = new BookChapterBean();

                    JsonObject chapter = chapters.get(i).getAsJsonObject();

                    //章节名称
                    String title = chapter.get(titleJson).getAsString();
                    bean.setChapterTitle(StringUtils.fullToHalf(executeOp(titleRules, title)));
                    //章节链接
                    String link = chapter.get(linkJson).getAsString();
                    bean.setChapterLink(executeOp(linkRules, link));
                    //章节序号
                    bean.setChapterIndex(i);
                    //章节对应的书籍
                    bean.setBookLink(book.getLink());
                    bean.setBookTitle(book.getTitle());
                    //对应书源
                    bean.setTag(book.getSourceTag());

                    chapterList.add(bean);

                    Log.i("JsonSourceModel", bean.toString());
                }
            emitter.onNext(chapterList);
            emitter.onComplete();
        });
    }

    /**
     * 章节内容
     *
     * @param chapter
     * @return
     */
    @Override
    public Observable<BookContentBean> parseContent(BookChapterBean chapter) {
        if (isEmpty(chapter.getChapterLink())) {
            return Observable.create(emitter -> {
                emitter.onNext(null);
                emitter.onComplete();
            });
        }
        return Observable.create(emitter -> {
            String jsonStr = null;
            try {
                jsonStr = OkHttpUtils.getHtml(chapter.getChapterLink()
                        , bookSourceBean.getEncodeType().split("&")[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String contentJson = bookSourceBean.getRuleChapterContent();

            String[] contentRules = contentJson.split("\\$");

            JsonParser parser = new JsonParser();
            JsonObject object = (JsonObject) parser.parse(jsonStr);
            for (int i = 0; i < contentRules.length - 1; i++) {
                object = object.get(contentRules[i]).getAsJsonObject();
            }
            String content = object.get(contentRules[contentRules.length - 1]).getAsString();
            content = content
                    // 替换特定标签为换行符
                    .replaceAll("(?i)<(br[\\s/]*|/*p.*?|/*div.*?)>", "\n")
                    // 删除script标签对和空格转义符
                    .replaceAll("<[script>]*.*?>|&nbsp;", "")
                    // 移除空行,并增加段前缩进2个汉字
                    .replaceAll("\\s*\\n+\\s*", "\n　　");
            BookContentBean bookContent = new BookContentBean();
            bookContent.setBookLink(chapter.getBookLink());
            bookContent.setChapterLink(chapter.getChapterLink());
            bookContent.setChapterIndex(chapter.getChapterIndex());
            bookContent.setChapterContent(content);
            emitter.onNext(bookContent);
            emitter.onComplete();
        });
    }

}

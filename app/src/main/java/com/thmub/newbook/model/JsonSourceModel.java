package com.thmub.newbook.model;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.thmub.newbook.bean.BookChapterBean;
import com.thmub.newbook.bean.BookContentBean;
import com.thmub.newbook.bean.BookSearchBean;
import com.thmub.newbook.bean.BookSourceBean;
import com.thmub.newbook.utils.OkHttpUtils;

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

    @Override
    public Observable<List<BookSearchBean>> searchBook(String keyword) {
        if (isEmpty(bookSourceBean.getSearchLink())) {
            return Observable.create(emitter -> {
                emitter.onNext(null);
                emitter.onComplete();
            });
        }
        return Observable.create(emitter -> {
            String jsonStr = null, encodeType;
            //获取书源搜索地址的编码
            String[] encodes = bookSourceBean.getEncodeType().split("&");
            if (encodes.length == 1)
                encodeType = encodes[0];
            else
                encodeType = encodes[1];

            try {
                jsonStr = OkHttpUtils.getHtml(bookSourceBean.getRootLink()
                        + String.format(bookSourceBean.getSearchLink(),keyword)
                        , encodeType);
            } catch (IOException e) {
                e.printStackTrace();
            }

            String bookJson = bookSourceBean.getRuleSearchBook();
            String titleJson =bookSourceBean.getRuleSearchTitle();
            String coverJson =bookSourceBean.getRuleSearchCover();
            String linkJson = bookSourceBean.getRuleSearchLink();
            String authorJson = bookSourceBean.getRuleSearchAuthor();
            String descJson = bookSourceBean.getRuleSearchDesc();

            String[] bookRules = bookJson.split("\\$");
            String[] titleRules = titleJson.split("@");
            if (titleRules.length > 1) {
                titleJson = titleRules[0];
                titleRules = titleRules[1].split("#");
            }
            String[] coverRules = coverJson.split("@");
            if (coverRules.length > 1) {
                coverJson = coverRules[0];
                coverRules = coverRules[1].split("#");
            }
            String[] linkRules = linkJson.split("@");
            if (linkRules.length > 1) {
                linkJson = linkRules[0];
                linkRules = linkRules[1].split("#");
            }
            String[] authorRules = authorJson.split("@");
            if (linkRules.length > 1) {
                authorJson = authorRules[0];
                authorRules = authorRules[1].split("#");
            }
            String[] descRules = descJson.split("@");
            if (linkRules.length > 1) {
                descJson = descRules[0];
                descRules = descRules[1].split("#");
            }

            JsonParser parser = new JsonParser();  //创建JSON解析器
            JsonObject object = (JsonObject) parser.parse(jsonStr);  //创建JsonObject对象
            for (int i = 0; i < bookRules.length - 1; i++) {
                object = object.get(bookRules[i]).getAsJsonObject();
            }
            JsonArray books = object.get(bookRules[bookRules.length - 1]).getAsJsonArray();
            List<BookSearchBean> bookList = new ArrayList<>();
            for (int i = 0; i < books.size(); i++) {
                BookSearchBean bean = new BookSearchBean();

                JsonObject book = books.get(i).getAsJsonObject();

                String title = book.get(titleJson).getAsString();
                bean.setTitle(executeOp(titleRules, title));

                String link = book.get(linkJson).getAsString();
                bean.setLink(executeOp(linkRules, link));

                String cover = book.get(coverJson).getAsString();
                bean.setCover(executeOp(coverRules, cover));

                String author = book.get(authorJson).getAsString();
                bean.setAuthor(executeOp(authorRules, author));

                String desc = book.get(descJson).getAsString();
                bean.setDesc(executeOp(descRules, desc));

                bean.setSource(bookSourceBean.getSourceName());

                bookList.add(bean);

                Log.i("JsonSourceModel",bean.toString());
            }

            emitter.onNext(bookList);
            emitter.onComplete();

        });
    }

    @Override
    public Observable<List<BookChapterBean>> parseCatalog(String catalogLink) {
        if (isEmpty(catalogLink)) {
            return Observable.create(emitter -> {
                emitter.onNext(null);
                emitter.onComplete();
            });
        }
        return Observable.create(emitter -> {
            String jsonStr = null;
            try {
                jsonStr = OkHttpUtils.getHtml(catalogLink
                        , bookSourceBean.getEncodeType().split("&")[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }

            String chapterJson = bookSourceBean.getRuleCatalogChapter();
            String titleJson = bookSourceBean.getRuleCatalogTitle();
            String linkJson = bookSourceBean.getRuleCatalogLink();

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
            for (int i = 0; i < chapters.size(); i++) {
                BookChapterBean bean = new BookChapterBean();

                JsonObject chapter = chapters.get(i).getAsJsonObject();

                String title = chapter.get(titleJson).getAsString();
                bean.setChapterTitle(executeOp(titleRules, title));

                String link = chapter.get(linkJson).getAsString();
                bean.setChapterLink(executeOp(linkRules, link));

                bean.setChapterIndex(i);

                chapterList.add(bean);
            }

            emitter.onNext(chapterList);
            emitter.onComplete();
        });
    }

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

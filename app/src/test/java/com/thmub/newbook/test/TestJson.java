package com.thmub.newbook.test;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.thmub.newbook.bean.BookChapterBean;
import com.thmub.newbook.bean.BookContentBean;
import com.thmub.newbook.bean.BookSearchBean;
import com.thmub.newbook.bean.BookSourceBean;
import com.thmub.newbook.utils.OkHttpUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Zhouas666 on 2019-04-12
 * Github: https://github.com/zas023
 */
public class TestJson {

    public static void main(String[] args) {
        TestJson test = new TestJson();

        test.testGson();

//        test.testSite();

//        test.testCatalog();

//        test.testContent();
    }

    public void testGson(){
        String jsonStr = null;

        try {
            jsonStr = OkHttpUtils.getHtml("https://thmub.com/source.txt", "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(jsonStr);
        Gson gson=new Gson();
        List<BookSourceBean> list=gson.fromJson(jsonStr, new TypeToken<List<BookSourceBean>>() {}.getType());
        System.out.println(list.size());
    }

    public void testSite() {

        String jsonStr = null;

        try {
            jsonStr = OkHttpUtils.getHtml("http://api.zhuishushenqi.com/book/fuzzy-search?query=遮天", "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        String bookJson = "books";
        String titleJson = "title";
        String coverJson = "cover@http://statics.zhuishushenqi.com%s";
        String linkJson = "_id@http://api.zhuishushenqi.com/mix-atoc/%s?view=chapters";
        String authorJson = "author";
        String descJson = "shortIntro";

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
            bean.setCatalogLink(executeOp(linkRules, link));

            String cover = book.get(coverJson).getAsString();
            bean.setCover(executeOp(coverRules, cover));

            String author = book.get(authorJson).getAsString();
            bean.setAuthor(executeOp(authorRules, author));

            String desc = book.get(descJson).getAsString();
            bean.setDesc(executeOp(descRules, desc));

            bookList.add(bean);
            System.out.println(bean.toString());
        }

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


    public void testCatalog() {

        String jsonStr = null;
        try {
            jsonStr = OkHttpUtils.getHtml("http://api.zhuishushenqi.com/mix-atoc/5ad9a71110ef66d628c50ebb?view=chapters", "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        String chapterJson = "mixToc$chapters";
        String titleJson = "title";
        String linkJson = "link@UrlEncode#http://chapterup.zhuishushenqi.com/chapter/%s";

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
        for (int i = 0; i < chapters.size(); i++) {
            BookChapterBean bean = new BookChapterBean();
            JsonObject chapter = chapters.get(i).getAsJsonObject();
            String title = chapter.get(titleJson).getAsString();
            bean.setChapterTitle(executeOp(titleRules, title));
            String link = chapter.get(linkJson).getAsString();
            bean.setChapterLink(executeOp(linkRules, link));
            System.out.println(bean.toString());
        }
    }

    private static void testContent() {
        String url = "http://chapterup.zhuishushenqi.com/chapter/http%3A%2F%2Fbook.my716.com%2FgetBooks.aspx%3Fmethod%3Dcontent%26bookId%3D2409462%26chapterFile%3DU_2409462_201904121641136824_9940_1515.txt";
        String jsonStr = null;
        try {
            jsonStr = OkHttpUtils.getHtml(url, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String contentJson = "chapter$body";
        String[] contentRules = contentJson.split("\\$");

        JsonParser parser = new JsonParser();
        JsonObject object = (JsonObject) parser.parse(jsonStr);
        for (int i = 0; i < contentRules.length-1; i++) {
            object = object.get(contentRules[i]).getAsJsonObject();
        }
        String content=object.get(contentRules[contentRules.length - 1]).getAsString();
        BookContentBean bean =new BookContentBean();
        bean.setBookLink(url);
        bean.setChapterLink(url);
        bean.setChapterContent(content);
        System.out.println(content);
    }
}

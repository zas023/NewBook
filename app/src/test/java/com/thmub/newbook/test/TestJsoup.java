package com.thmub.newbook.test;

import com.thmub.newbook.bean.BookSearchBean;
import com.thmub.newbook.bean.BookChapterBean;
import com.thmub.newbook.utils.OkHttpUtils;

import org.seimicrawler.xpath.JXDocument;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhouas666 on 2019-03-06
 * Github: https://github.com/zas023
 */
public class TestJsoup {

    public static void main(String[] args) {
        TestJsoup test=new TestJsoup();
        test.testSite();
//        test.testCatalog();
//        test.testContent();
    }

    public void testSite(){

        JXDocument jxDocument = null;
        try {
            jxDocument = JXDocument.create(OkHttpUtils.getHtml("https://99lib.net/book/search.php?s=13139900387823019677&type=%E7%AB%99%E5%86%85&q=%E6%B3%95%E5%8C%BB%E7%A7%A6%E6%98%8E","utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String xpath = "//div[@class='bookinfo']";
        String coverXpath = "";
        String titleXpath = "//div[@class='bookinfo']//h4//a/text()";
        String linkXpath = "//div[@class='bookinfo']/h4/a/@href";
//        String authorXpath = "substring-after(.//div[@class='author']/text(),'作者：')";
        String authorXpath = "//div[@class='author']/text()";
        String descXpath = "//*[@class='list_box']/li/div[1]/allText()";

        List<Object> rsBooks = jxDocument.sel(xpath);
        //List<Object> rsCovers = jxDocument.sel(coverXpath);
       // List<Object> rsTitles = jxDocument.sel(titleXpath);
       // List<Object> rsLinks = jxDocument.sel(linkXpath);
//        List<Object> rsAuthors = jxDocument.sel(authorXpath);
        List<Object> rsDescs = jxDocument.sel(descXpath);

        List<BookSearchBean> bookList = new ArrayList<>();
        for (int i = 0, size = rsDescs.size(); i < size; i++) {
            BookSearchBean bean = new BookSearchBean();
            //bean.setTitle(rsTitles.get(i).toString());
            //bean.setCover(root+rsCovers.get(i).toString());
            //bean.setLink(rsLinks.get(i).toString());
//            bean.setAuthor(rsAuthors.get(i).toString().replace("作者：",""));
            bean.setDesc(rsDescs.get(i).toString());
            bookList.add(bean);
            System.out.println(bean.toString());
        }
    }


    public void testCatalog(){

        String root ="https://www.xbiquge6.com";
        JXDocument jxDocument = null;
        try {
            jxDocument = JXDocument.create(OkHttpUtils.getHtml(root+"/72_72817/", "utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String xpath = "//div[@id='list']//dl//dd";
        String titleXpath = "//a/text()";
        String linkXpath = "//a/@href";

        List<Object> rsCatalogs = jxDocument.sel(xpath);
        List<Object> rsTitles = jxDocument.sel(xpath+titleXpath);
        List<Object> rsLinks = jxDocument.sel(xpath+linkXpath);

        List<BookChapterBean> catalogList = new ArrayList<>();
        for (int i = 0, size = rsCatalogs.size(); i < size; i++) {
            BookChapterBean bean = new BookChapterBean();
            bean.setChapterTitle(rsTitles.get(i).toString());
            bean.setChapterLink(root+rsLinks.get(i).toString());
            bean.setBookLink(root);
            bean.setChapterIndex(i+1);
            catalogList.add(bean);
            System.out.println(bean.toString());
        }
    }

    public void testSearch() {
        String url = "https://www.xbiquge6.com/search.php?keyword=%E9%81%AE%E5%A4%A9";
        String xpathSearchBook = "//div[@class='result-item result-game-item']//h3/a/@href";
        String xpathCover = "//div[@class='result-game-item-pic']//a//img/@src";
        JXDocument jxDocument = null;
        try {
            jxDocument = JXDocument.create(OkHttpUtils.getHtml(url,"utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Object> rsBooks = jxDocument.sel(xpathSearchBook);
        for (Object o : rsBooks) {
            System.out.println(o.toString());
        }
        List<Object> rsCovers = jxDocument.sel(xpathCover);
        for (Object o : rsCovers) {
            System.out.println(o.toString());
        }
    }

    private static void testContent() {
        String url = "https://www.xbiquge6.com/82_82960/";
        String xpathTitle = "//*[@id=\"wrapper\"]/div[5]/div/div[2]";
        String xpathContent = "//*[@id=\"content\"]/text()";

        String xpath="//div[@id='info']/p[1]/text()";

        JXDocument jxDocument = JXDocument.createByUrl(url);
        System.out.println(jxDocument.toString());
        System.out.println(jxDocument.selOne(xpathContent).toString());
    }
}

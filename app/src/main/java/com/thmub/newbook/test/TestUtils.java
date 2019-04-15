package com.thmub.newbook.test;

import com.thmub.newbook.bean.BookSourceBean;
import com.thmub.newbook.utils.RegexUtils;

/**
 * Created by Zhouas666 on 2019-03-28
 * Github: https://github.com/zas023
 */
public class TestUtils {

    public static void main(String[] args) {
        //System.out.println(MD5Utils.strToMd5By32("热血兵王"+"最强狂兵"+"都市"));

        System.out.println(RegexUtils.checkURL("https://www.xbiquge6.com/cover/82/82960/82960s.jpg"));
        System.out.println(RegexUtils.checkURL("/cover/82/82960/82960s.jpg"));

        BookSourceBean bean=new BookSourceBean();
        bean.setRootLink("https://www.xbiquge6.com");
        bean.setSourceName("新笔趣阁");
        bean.setEncodeType("utf-8");
        bean.setOrderNumber(0);
        bean.setSearchLink("/search.php?keyword=%s");
        bean.setRuleSearchTitle("//div[@class='result-game-item-detail']//h3//a/@title");
        bean.setRuleSearchLink("//div[@class='result-game-item-detail']//h3//a/@href");
        bean.setRuleSearchAuthor("//div[@class='result-game-item-detail']//div[@class='result-game-item-info']//p[1]/span[2]/text()");
        bean.setRuleSearchDesc("//div[@class='result-game-item-detail']//p[@class='result-game-item-desc']/text()");
        bean.setRuleSearchCover("//div[@class='result-game-item-pic']//a//img/@src");

        bean.setRuleChapterLink("//div[@id='list']//dl//dd//a/text()");
        bean.setRuleCatalogLink("//div[@id='list']//dl//dd//a/@href");

        bean.setRuleChapterContent("//div[@id='content']/text()");

        bean.setWeight(0);
        bean.setSelected(true);

        System.out.println(bean.toString());
    }
}

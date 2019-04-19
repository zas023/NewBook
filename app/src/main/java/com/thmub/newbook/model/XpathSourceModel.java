package com.thmub.newbook.model;

import android.util.Log;

import com.thmub.newbook.bean.BookChapterBean;
import com.thmub.newbook.bean.BookContentBean;
import com.thmub.newbook.bean.BookDetailBean;
import com.thmub.newbook.bean.BookSearchBean;
import com.thmub.newbook.bean.BookSourceBean;
import com.thmub.newbook.bean.ShelfBookBean;
import com.thmub.newbook.utils.OkHttpUtils;
import com.thmub.newbook.utils.RegexUtils;
import com.thmub.newbook.utils.StringUtils;

import org.seimicrawler.xpath.JXDocument;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static android.text.TextUtils.isEmpty;

/**
 * Created by Zhouas666 on 2019-04-12
 * Github: https://github.com/zas023
 */
public class XpathSourceModel implements ISourceModel {

    private BookSourceBean bookSourceBean;

    public XpathSourceModel(BookSourceBean bean) {
        this.bookSourceBean = bean;
    }

    /**
     * 发现书籍
     *
     * @param findLink
     * @return
     */
    @Override
    public Observable<List<BookSearchBean>> findBook(String findLink) {
        Log.i("XpathSourceModel", "findBook:" + findLink);
        if (isEmpty(findLink) || isEmpty(bookSourceBean.getRuleFindBookTitle())
                || isEmpty(bookSourceBean.getRuleFindBookLink())) {
            return Observable.create(emitter -> {
                emitter.onNext(null);
                emitter.onComplete();
            });
        }
        return Observable.create(emitter -> {
            JXDocument jxDocument = null;
            try {
                jxDocument = JXDocument.create(
                        OkHttpUtils.getHtml(findLink, bookSourceBean.getEncodeType().split("&")[0]));
            } catch (IOException e) {
                e.printStackTrace();
            }

            //null+" aa" 会生成 "null aa",需要处理掉
            //返回结果时进行了判断,为空则返回空字符串而非空引用
            String ruleBook = bookSourceBean.getRuleFindBooks();
            //书名（必填）
            List<Object> rsTitles = jxDocument.sel((ruleBook + bookSourceBean.getRuleFindBookTitle()));
            //封面
            List<Object> rsCovers = null;
            if (bookSourceBean.getRuleFindCover() != null)
                rsCovers = jxDocument.sel(ruleBook + bookSourceBean.getRuleFindCover());
            //书籍链接（必填）
            List<Object> rsLinks = jxDocument.sel(ruleBook + bookSourceBean.getRuleFindBookLink());
            List<BookSearchBean> bookList = new ArrayList<>();
            for (int i = 0, size = rsTitles.size(); i < size; i++) {

                BookSearchBean bean = new BookSearchBean();

                //书籍名称
                bean.setTitle(rsTitles.get(i).toString());
                //书籍封面
                if (rsCovers != null) {
                    //检查链接是否完整，这里取了一个巧，因为几乎所有网站均采用网站host加爬取的地址构成一个完整的链接地址
                    String coverUrl = rsCovers.get(i).toString();
                    if (RegexUtils.checkURL(coverUrl))
                        bean.setCover(coverUrl);
                    else
                        bean.setCover(bookSourceBean.getRootLink() + coverUrl);
                }
                //书籍链接
                if (rsLinks != null) {
                    String bookUrl = rsLinks.get(i).toString();
                    if (RegexUtils.checkURL(bookUrl))
                        bean.setBookLink(bookUrl);
                    else
                        bean.setBookLink(bookSourceBean.getRootLink() + bookUrl);
                }
                //对应书源
                bean.setSourceTag(bookSourceBean.getSourceName());

                bookList.add(bean);

                Log.i("XpathSourceModel", bean.toString());
            }

            emitter.onNext(bookList);
            emitter.onComplete();
        });
    }

    /**
     * 搜索书籍
     *
     * @param keyword
     * @return
     */
    @Override
    public Observable<List<BookSearchBean>> searchBook(String keyword) {

        Log.i("XpathSourceModel", "searchBook:" + keyword);
        if (isEmpty(bookSourceBean.getSearchLink()) || isEmpty(bookSourceBean.getRuleSearchTitle())
                || isEmpty(bookSourceBean.getRuleSearchAuthor()) || isEmpty(bookSourceBean.getRuleSearchLink())) {
            return Observable.create(emitter -> {
                emitter.onNext(null);
                emitter.onComplete();
            });
        }
        return Observable.create(emitter -> {
            String htmlStr, encodeType;
            JXDocument jxDocument = null;
            //获取书源搜索地址的编码
            String[] encodes = bookSourceBean.getEncodeType().split("&");
            if (encodes.length == 1)
                encodeType = encodes[0];
            else
                encodeType = encodes[1];
            try {
                htmlStr = OkHttpUtils.getHtml(bookSourceBean.getRootLink() + String.format(bookSourceBean.getSearchLink()
                        , URLEncoder.encode(keyword, encodeType)), encodeType);
                jxDocument = JXDocument.create(htmlStr);
            } catch (IOException e) {
                e.printStackTrace();
            }

            //null+" aa" 会生成 "null aa",需要处理掉
            //返回结果时进行了判断,为空则返回空字符串而非空引用
            String ruleBook = bookSourceBean.getRuleSearchBooks();
            //书名
            List<Object> rsTitles = jxDocument.sel((ruleBook + bookSourceBean.getRuleSearchTitle()));
            //作者
            List<Object> rsAuthors = jxDocument.sel(ruleBook + bookSourceBean.getRuleSearchAuthor());
            //书籍链接
            List<Object> rsLinks = jxDocument.sel(ruleBook + bookSourceBean.getRuleSearchLink());
            //上面三项为必填项，否则搜书无效
            //封面
            List<Object> rsCovers = null;
            if (bookSourceBean.getRuleSearchCover() != null)
                rsCovers = jxDocument.sel(ruleBook + bookSourceBean.getRuleSearchCover());
            //简介
            List<Object> rsDescs = null;
            if (bookSourceBean.getRuleSearchDesc() != null)
                rsDescs = jxDocument.sel(ruleBook + bookSourceBean.getRuleSearchDesc());
            List<BookSearchBean> bookList = new ArrayList<>();
            for (int i = 0, size = rsTitles.size(); i < size; i++) {

                BookSearchBean bean = new BookSearchBean();

                //书籍名称
                bean.setTitle(rsTitles.get(i).toString());
                //作者
                bean.setAuthor(rsAuthors.get(i).toString());
                //简介
                bean.setDesc(rsDescs.get(i).toString());

                //书籍封面
                if (rsCovers != null) {
                    //检查链接是否完整，这里取了一个巧，因为几乎所有网站均采用网站host加爬取的地址构成一个完整的链接地址
                    String coverUrl = rsCovers.get(i).toString();
                    if (RegexUtils.checkURL(coverUrl))
                        bean.setCover(coverUrl);
                    else
                        bean.setCover(bookSourceBean.getRootLink() + coverUrl);
                }
                //书籍链接
                if (rsLinks != null) {
                    String bookUrl = rsLinks.get(i).toString();
                    if (RegexUtils.checkURL(bookUrl))
                        bean.setBookLink(bookUrl);
                    else
                        bean.setBookLink(bookSourceBean.getRootLink() + bookUrl);
                }

                //对应书源
                bean.setSourceTag(bookSourceBean.getSourceName());

                bookList.add(bean);

                Log.i("XpathSourceModel", bean.toString());
            }

            emitter.onNext(bookList);
            emitter.onComplete();
        });
    }

    /**
     * 书籍详情
     *
     * @param bookBean
     * @return
     */
    @Override
    public Observable<BookDetailBean> parseBook(BookSearchBean bookBean) {
        Log.i("XpathSourceModel", "parseBook:" + bookBean.toString());
        String bookLink = bookBean.getBookLink();
        if (isEmpty(bookLink)) {
            return Observable.create(emitter -> {
                emitter.onNext(null);
                emitter.onComplete();
            });
        }
        return Observable.create(emitter -> {
            JXDocument jxDocument = null;
            try {
                jxDocument = JXDocument.create(
                        OkHttpUtils.getHtml(bookLink, bookSourceBean.getEncodeType().split("&")[0]));
            } catch (IOException e) {
                e.printStackTrace();
            }

            BookDetailBean bean = new BookDetailBean(bookBean);
            //null+" aa" 会生成 "null aa",需要处理掉
            //返回结果时进行了判断,为空则返回空字符串而非空引用
            String ruleBook = bookSourceBean.getRuleDetailBook();
            //书名
            if (bookSourceBean.getRuleDetailTitle() != null)
                bean.setTitle(jxDocument.selOne((ruleBook + bookSourceBean.getRuleDetailTitle())).toString());
            //作者
            if (bookSourceBean.getRuleDetailAuthor() != null) {
                String author = jxDocument.selOne((ruleBook + bookSourceBean.getRuleDetailAuthor())).toString();
                //去除有些网站添加的属性
                if (author.contains("：")) {
                    author.replace(" ", "");
                    author = author.split("：")[1];
                }
                bean.setAuthor(author);
            }

            //封面
            String coverLink;
            if (bookSourceBean.getRuleDetailCover() != null) {
                coverLink = jxDocument.selOne((ruleBook + bookSourceBean.getRuleDetailCover())).toString();
                if (RegexUtils.checkURL(coverLink))
                    bean.setCover(coverLink);
                else
                    bean.setCover(bookSourceBean.getRootLink() + coverLink);
            }
            //简介
            if (bookSourceBean.getRuleDetailDesc() != null) {
                bean.setDesc(jxDocument.selOne((ruleBook + bookSourceBean.getRuleDetailDesc())).toString());
            }

            //目录链接,如果没有规则，说明书籍地址就是目录地址
            String catalogLink;
            if (bookSourceBean.getRuleCatalogLink() != null) {
                catalogLink = jxDocument.selOne((ruleBook + bookSourceBean.getRuleCatalogLink())).toString();
                if (RegexUtils.checkURL(catalogLink))
                    bean.setCatalogLink(catalogLink);
                else
                    bean.setCatalogLink(bookSourceBean.getRootLink() + catalogLink);
            } else {
                bean.setCatalogLink(bookLink);
            }
            //发现书籍链接
            String findLink;
            if (bookSourceBean.getRuleFindLink() != null) {
                findLink = jxDocument.selOne((ruleBook + bookSourceBean.getRuleFindLink())).toString();
                if (RegexUtils.checkURL(findLink))
                    bean.setFindLink(findLink);
                else
                    bean.setFindLink(bookSourceBean.getRootLink() + findLink);
            } else {
                bean.setFindLink(bookLink);
            }
            //对应书源
            bean.setSourceTag(bookSourceBean.getSourceName());

            Log.i("XpathSourceModel", bean.toString());

            emitter.onNext(bean);
            emitter.onComplete();
        });
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
        String catalogLink = book.getCatalogLink();
        if (isEmpty(catalogLink)) {
            return Observable.create(emitter -> {
                emitter.onNext(null);
                emitter.onComplete();
            });
        }
        return Observable.create(emitter -> {
            JXDocument jxDocument = null;
            try {
                jxDocument = JXDocument.create(
                        OkHttpUtils.getHtml(catalogLink, bookSourceBean.getEncodeType().split("&")[0]));
            } catch (IOException e) {
                e.printStackTrace();
            }

            String ruleLink = bookSourceBean.getRuleChapterLink();
            boolean isBookLink = false;
            if (ruleLink.contains("bookLink+")) {
                ruleLink = ruleLink.replace("bookLink+", "");
                isBookLink = true;
            }

            List<Object> rsTitles = jxDocument.sel(bookSourceBean.getRuleChapterTitle());
            List<Object> rsLinks = jxDocument.sel(ruleLink);

            List<BookChapterBean> catalogList = new ArrayList<>();

            //正序所有
            if (num == 0) {
                for (int i = 0, size = rsTitles.size(); i < size; i++) {
                    BookChapterBean bean = new BookChapterBean();
                    //章节名称
                    bean.setChapterTitle(StringUtils.fullToHalf(rsTitles.get(i).toString()));
                    //章节链接
                    String chapterLink = rsLinks.get(i).toString();
                    if (RegexUtils.checkURL(chapterLink))
                        bean.setChapterLink(chapterLink);
                    else {
                        if (isBookLink)
                            bean.setChapterLink(book.getLink() + chapterLink);
                        else
                            bean.setChapterLink(bookSourceBean.getRootLink() + chapterLink);
                    }
                    //章节序号
                    bean.setChapterIndex(i);
                    //书籍链接
                    bean.setBookLink(catalogLink);

                    catalogList.add(bean);

                    Log.i("XpathSourceModel", bean.toString());
                }
            } else if (num < 0) {
                for (int i = rsTitles.size() - 1, flag = Math.max(0, rsTitles.size() + num - 1); i > flag; i--) {
                    BookChapterBean bean = new BookChapterBean();
                    //章节名称
                    bean.setChapterTitle(StringUtils.fullToHalf(rsTitles.get(i).toString()));
                    //章节链接
                    String chapterLink = rsLinks.get(i).toString();
                    if (RegexUtils.checkURL(chapterLink))
                        bean.setChapterLink(chapterLink);
                    else {
                        if (isBookLink)
                            bean.setChapterLink(book.getLink() + chapterLink);
                        else
                            bean.setChapterLink(bookSourceBean.getRootLink() + chapterLink);
                    }
                    //章节序号
                    bean.setChapterIndex(i);
                    //书籍链接
                    bean.setBookLink(catalogLink);

                    catalogList.add(bean);

                    Log.i("XpathSourceModel", bean.toString());
                }
            }
            emitter.onNext(catalogList);
            emitter.onComplete();
        });
    }

    @Override
    public Observable<BookContentBean> parseContent(BookChapterBean chapter) {
        Log.i("XpathSourceModel", "parseContent:" + chapter.getChapterLink());
        if (isEmpty(chapter.getChapterLink())) {
            return Observable.create(emitter -> {
                emitter.onNext(null);
                emitter.onComplete();
            });
        }
        return Observable.create(emitter -> {
            JXDocument jxDocument = null;
            try {
                jxDocument = JXDocument.create(
                        OkHttpUtils.getHtml(chapter.getChapterLink()
                                , bookSourceBean.getEncodeType().split("&")[0]));
            } catch (IOException e) {
                e.printStackTrace();
            }

            BookContentBean bookContent = new BookContentBean();
            List<Object> rsContents = jxDocument.sel(bookSourceBean.getRuleChapterContent());
            String content = null;
            for (int i = 0, size = rsContents.size(); i < size; i++) {
                //优化内容
                content = rsContents.get(i).toString()
                        // 替换特定标签为换行符
                        .replaceAll("(?i)<(br[\\s/]*|/*p.*?|/*div.*?)>", "\n")
                        // 删除script标签对和空格转义符
                        .replaceAll("<[script>]*.*?>|&nbsp;", "")
                        // 移除空行,并增加段前缩进2个汉字
                        .replaceAll("\\s*\\n+\\s*", "\n　　");
            }
            //书籍链接
            bookContent.setBookLink(chapter.getBookLink());
            //章节链接
            bookContent.setChapterLink(chapter.getChapterLink());
            //章节序号
            bookContent.setChapterIndex(chapter.getChapterIndex());
            if (content == null)
                //章节内容
                bookContent.setChapterContent("解析章节内容错误");
            else
                bookContent.setChapterContent(content);
            Log.i("XpathSourceModel", content);

            emitter.onNext(bookContent);
            emitter.onComplete();
        });
    }

}

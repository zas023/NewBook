package com.thmub.newbook.model;

import android.util.Log;

import com.thmub.newbook.bean.BookChapterBean;
import com.thmub.newbook.bean.BookContentBean;
import com.thmub.newbook.bean.BookSearchBean;
import com.thmub.newbook.bean.BookSourceBean;
import com.thmub.newbook.utils.OkHttpUtils;
import com.thmub.newbook.utils.RegexUtils;

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

    @Override
    public Observable<List<BookSearchBean>> findBook(String findRule) {
        return Observable.create(emitter -> {
            emitter.onNext(null);
            emitter.onComplete();
        });
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
            String htmlStr, encodeType;
            JXDocument jxDocument = null;
            //获取书源搜索地址的编码
            String[] encodes = bookSourceBean.getEncodeType().split("&");
            if (encodes.length == 1)
                encodeType = encodes[0];
            else
                encodeType = encodes[1];
            try {
                htmlStr = OkHttpUtils.getHtml(bookSourceBean.getRootLink()
                        + String.format(bookSourceBean.getSearchLink()
                        , URLEncoder.encode(keyword, encodeType)), encodeType);
                jxDocument = JXDocument.create(htmlStr);
            } catch (IOException e) {
                e.printStackTrace();
            }

            List<Object> rsTitles = jxDocument.sel(bookSourceBean.getRuleSearchTitle());
            List<Object> rsCovers = jxDocument.sel(bookSourceBean.getRuleSearchCover());
            List<Object> rsLinks = jxDocument.sel(bookSourceBean.getRuleSearchLink());
            List<Object> rsAuthors = jxDocument.sel(bookSourceBean.getRuleSearchAuthor());
            List<Object> rsDescs = jxDocument.sel(bookSourceBean.getRuleSearchDesc());

            List<BookSearchBean> bookList = new ArrayList<>();
            for (int i = 0, size = rsTitles.size(); i < size; i++) {
                BookSearchBean bean = new BookSearchBean();
                bean.setTitle(rsTitles.get(i).toString());
                //检查链接是否完整
                String coverUrl = rsCovers.get(i).toString();
                if (RegexUtils.checkURL(coverUrl))
                    bean.setCover(coverUrl);
                else
                    bean.setCover(bookSourceBean.getRootLink() + coverUrl);
                String bookUrl = rsLinks.get(i).toString();
                if (RegexUtils.checkURL(bookUrl))
                    bean.setLink(bookUrl);
                else
                    bean.setLink(bookSourceBean.getRootLink() + bookUrl);
                bean.setAuthor(rsAuthors.get(i).toString());
                bean.setDesc(rsDescs.get(i).toString());
                bean.setSource(bookSourceBean.getSourceName());
                bookList.add(bean);
                Log.i("XpathSourceModel", bean.toString());
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
            JXDocument jxDocument = null;
            try {
                jxDocument = JXDocument.create(
                        OkHttpUtils.getHtml(catalogLink
                                , bookSourceBean.getEncodeType().split("&")[0]));
            } catch (IOException e) {
                e.printStackTrace();
            }

            List<Object> rsTitles = jxDocument.sel(bookSourceBean.getRuleCatalogTitle());
            List<Object> rsLinks = jxDocument.sel(bookSourceBean.getRuleCatalogLink());

            List<BookChapterBean> catalogList = new ArrayList<>();
            for (int i = 0, size = rsTitles.size(); i < size; i++) {
                BookChapterBean bean = new BookChapterBean();
                bean.setChapterTitle(rsTitles.get(i).toString());
                String chapterLink = rsLinks.get(i).toString();
                if (RegexUtils.checkURL(chapterLink))
                    bean.setChapterLink(chapterLink);
                else
                    bean.setChapterLink(bookSourceBean.getRootLink() + chapterLink);
                bean.setBookLink(catalogLink);
                bean.setChapterIndex(i);
                catalogList.add(bean);
                Log.i("XpathSourceModel", bean.toString());
            }
            emitter.onNext(catalogList);
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
            String content = "";
            for (int i = 0, size = rsContents.size(); i < size; i++) {
                content = content + rsContents.get(i).toString()
                        // 替换特定标签为换行符
                        .replaceAll("(?i)<(br[\\s/]*|/*p.*?|/*div.*?)>", "\n")
                        // 删除script标签对和空格转义符
                        .replaceAll("<[script>]*.*?>|&nbsp;", "")
                        // 移除空行,并增加段前缩进2个汉字
                        .replaceAll("\\s*\\n+\\s*", "\n　　");
            }
            Log.i("XpathSourceModel", content);
            bookContent.setBookLink(chapter.getBookLink());
            bookContent.setChapterLink(chapter.getChapterLink());
            bookContent.setChapterIndex(chapter.getChapterIndex());
            bookContent.setChapterContent(content);
            emitter.onNext(bookContent);
            emitter.onComplete();
        });
    }

}

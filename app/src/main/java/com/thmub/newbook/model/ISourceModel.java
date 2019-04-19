package com.thmub.newbook.model;

import com.thmub.newbook.bean.BookChapterBean;
import com.thmub.newbook.bean.BookContentBean;
import com.thmub.newbook.bean.BookDetailBean;
import com.thmub.newbook.bean.BookSearchBean;
import com.thmub.newbook.bean.ShelfBookBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Zhouas666 on 2019-03-27
 * Github: https://github.com/zas023
 */
public interface ISourceModel {

    Observable<List<BookSearchBean>> findBook(String findLink);

    Observable<List<BookSearchBean>> searchBook(String keyword);

    Observable<BookDetailBean> parseBook(BookSearchBean bookBean);

    Observable<List<BookChapterBean>> parseCatalog(ShelfBookBean book, int num);

    Observable<BookContentBean> parseContent(BookChapterBean chapter);
}

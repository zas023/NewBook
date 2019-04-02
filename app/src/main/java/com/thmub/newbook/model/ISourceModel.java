package com.thmub.newbook.model;

import com.thmub.newbook.bean.BookChapterBean;
import com.thmub.newbook.bean.BookContentBean;
import com.thmub.newbook.bean.BookSearchBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Zhouas666 on 2019-03-27
 * Github: https://github.com/zas023
 */
public abstract class ISourceModel {

    public abstract Observable<List<BookSearchBean>> searchBook(String keyword);

    public abstract Observable<List<BookChapterBean>> parseCatalog(String catalogLink);

    public abstract Observable<BookContentBean> parseContent(BookChapterBean chapter);
}

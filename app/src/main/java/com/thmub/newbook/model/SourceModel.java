package com.thmub.newbook.model;

import com.thmub.newbook.bean.BookContentBean;
import com.thmub.newbook.bean.BookSearchBean;
import com.thmub.newbook.bean.BookSourceBean;
import com.thmub.newbook.bean.BookChapterBean;
import com.thmub.newbook.bean.ShelfBookBean;
import com.thmub.newbook.model.local.BookSourceRepository;

import java.util.List;

import io.reactivex.Observable;


/**
 * Created by Zhouas666 on 2019-03-26
 * Github: https://github.com/zas023
 */
public class SourceModel implements ISourceModel {

    private String name;

    //面向接口编程
    private ISourceModel sourceModel;

    public SourceModel(String sourceId) {
        this.name = sourceId;
    }

    public static SourceModel getInstance(String sourceId) {
        return new SourceModel(sourceId);
    }

    private Boolean initBookSourceBean() {
        if (sourceModel == null) {
            BookSourceBean sourceBean = BookSourceRepository.getInstance().getBookSourceByName(name);
            if (sourceBean != null) {
                if (sourceBean.getSourceType().equals("Xpath"))
                    sourceModel = new XpathSourceModel(sourceBean);
                else if (sourceBean.getSourceType().equals("Json"))
                    sourceModel = new JsonSourceModel(sourceBean);
                else
                    return false;
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    @Override
    public Observable<List<BookSearchBean>> findBook(String findRule) {
        if (!initBookSourceBean()) {
            return Observable.create(emitter -> {
                emitter.onNext(null);
                emitter.onComplete();
            });
        }
        return sourceModel.findBook(findRule);
    }

    @Override
    public Observable<List<BookSearchBean>> searchBook(String keyword) {
        if (!initBookSourceBean()) {
            return Observable.create(emitter -> {
                emitter.onNext(null);
                emitter.onComplete();
            });
        }
        return sourceModel.searchBook(keyword);
    }

    @Override
    public Observable<List<BookChapterBean>> parseCatalog(ShelfBookBean bookBean) {
        if (!initBookSourceBean()) {
            return Observable.create(emitter -> {
                emitter.onNext(null);
                emitter.onComplete();
            });
        }
        return sourceModel.parseCatalog(bookBean);
    }

    @Override
    public Observable<BookContentBean> parseContent(BookChapterBean chapter) {
        if (!initBookSourceBean()) {
            return Observable.create(emitter -> {
                emitter.onNext(null);
                emitter.onComplete();
            });
        }
        return sourceModel.parseContent(chapter);
    }

}

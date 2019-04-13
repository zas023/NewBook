package com.thmub.newbook.model.local;

import android.util.Log;

import com.thmub.newbook.bean.BookSourceBean;
import com.thmub.newbook.model.dao.BookSourceBeanDao;
import com.thmub.newbook.model.dao.DaoDbHelper;
import com.thmub.newbook.model.dao.DaoSession;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Zhouas666 on 2019-03-30
 * Github: https://github.com/zas023
 * <p>
 * 书源管理repo
 */
public class BookSourceRepository {
    private static final String TAG = "BookSourceRepository";
    private static volatile BookSourceRepository sInstance;
    private DaoSession mSession;
    private BookSourceBeanDao mBookSourceDao;

    private BookSourceRepository() {
        mSession = DaoDbHelper.getInstance().getSession();
        mBookSourceDao = mSession.getBookSourceBeanDao();
    }

    public static BookSourceRepository getInstance() {
        if (sInstance == null) {
            //同步处理
            synchronized (BookSourceRepository.class) {
                if (sInstance == null) {
                    sInstance = new BookSourceRepository();
                }
            }
        }
        return sInstance;
    }

    /*****************************Save************************************/

    /**
     * 同步方法保存书源
     *
     * @param bean
     */
    public Long saveBookSource(BookSourceBean bean) {
        return mBookSourceDao.insertOrReplace(bean);
    }

    /**
     * 异步保存书源
     *
     * @param bean
     */
    public Observable<Long> saveBookSourceWithAsync(BookSourceBean bean) {
        return Observable.create(e -> {
            Long id = BookSourceRepository.getInstance().saveBookSource(bean);
            if (id == null)
                e.onError(new Throwable("保存失败"));
            else
                e.onNext(id);
            e.onComplete();
        });
    }

    /**
     * 异步保存书源
     *
     * @param beans
     */
    public void saveBookSourceWithAsync(List<BookSourceBean> beans) {

        Log.i(TAG, "" + beans.size());
        mSession.startAsyncSession()
                .runInTx(() ->
                        mBookSourceDao.insertOrReplaceInTx(beans)
                );
    }

    /*****************************Get*************************************/

    /**
     * 根据所有书源
     *
     * @return
     */
    public List<BookSourceBean> getAllBookSource() {
        return mBookSourceDao.loadAll();
    }

    /**
     * 根据所有选中的书源
     *
     * @return
     */
    public List<BookSourceBean> getAllSelectedBookSource() {
        return mBookSourceDao.queryBuilder()
                .where(BookSourceBeanDao.Properties.IsSelected.eq(true))
                .orderAsc(BookSourceBeanDao.Properties.OrderNumber)
                .list();
    }

    /**
     * 根据名称获取书源
     *
     * @param sourceName
     * @return
     */
    public BookSourceBean getBookSourceByName(String sourceName) {
        return mBookSourceDao.queryBuilder()
                .where(BookSourceBeanDao.Properties.SourceName.eq(sourceName))
                .unique();
    }

    /**
     * 根据名称获取书源
     *
     * @param sourceLink
     * @return
     */
    public BookSourceBean getBookSourceByLink(String sourceLink) {
        return mBookSourceDao.queryBuilder()
                .where(BookSourceBeanDao.Properties.SearchLink.eq(sourceLink))
                .unique();
    }

    /**
     * 根据发现规则获取书源
     *
     * @return
     */
    public BookSourceBean getBookSourceWithFind() {
        return mBookSourceDao.queryBuilder()
                .where(BookSourceBeanDao.Properties.RuleFindBook.isNotNull())
                .unique();
    }

}

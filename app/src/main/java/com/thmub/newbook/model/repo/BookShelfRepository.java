package com.thmub.newbook.model.repo;

import com.thmub.newbook.bean.ShelfBookBean;
import com.thmub.newbook.model.dao.DaoDbHelper;
import com.thmub.newbook.model.dao.DaoSession;
import com.thmub.newbook.model.dao.ShelfBookBeanDao;

/**
 * Created by Zhouas666 on 2019-03-31
 * Github: https://github.com/zas023
 */
public class BookShelfRepository {

    private static final String TAG = "BookShelfRepository";

    private static volatile BookShelfRepository sInstance;
    private DaoSession mSession;
    private ShelfBookBeanDao mShelfBookDao;

    private BookShelfRepository() {
        mSession = DaoDbHelper.getInstance().getSession();
        mShelfBookDao = mSession.getShelfBookBeanDao();
    }

    public static BookShelfRepository getInstance() {
        if (sInstance == null) {
            //同步处理
            synchronized (BookShelfRepository.class) {
                if (sInstance == null) {
                    sInstance = new BookShelfRepository();
                }
            }
        }
        return sInstance;
    }

    /****************************Save********************************/
    /**
     * 保存书籍
     *
     * @param book
     */
    public void saveShelfBook(ShelfBookBean book) {
        if (book.getBookChapterList() != null) {
            // 存储BookChapterBean
            mSession.getBookChapterBeanDao()
                    .insertOrReplaceInTx(book.getBookChapterList());
        }
        //存储BookShelfBean
        mShelfBookDao.insertOrReplace(book);
    }

    /**************************Get************************************/

}

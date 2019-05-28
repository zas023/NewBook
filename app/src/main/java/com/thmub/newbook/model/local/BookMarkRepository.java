package com.thmub.newbook.model.local;

import com.thmub.newbook.bean.BookMarkBean;
import com.thmub.newbook.model.dao.BookMarkBeanDao;
import com.thmub.newbook.model.dao.DaoDbHelper;
import com.thmub.newbook.model.dao.DaoSession;

import java.util.List;

/**
 * Created by Zhouas666 on 2019-03-30
 * Github: https://github.com/zas023
 * <p>
 * 书签管理repo
 */
public class BookMarkRepository {
    private static final String TAG = "BookSourceRepository";
    private static volatile BookMarkRepository sInstance;
    private DaoSession mSession;
    private BookMarkBeanDao mBookMarkBeanDao;

    private BookMarkRepository() {
        mSession = DaoDbHelper.getInstance().getSession();
        mBookMarkBeanDao = mSession.getBookMarkBeanDao();
    }

    public static BookMarkRepository getInstance() {
        if (sInstance == null) {
            //同步处理
            synchronized (BookMarkRepository.class) {
                if (sInstance == null) {
                    sInstance = new BookMarkRepository();
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
    public Long saveBookMark(BookMarkBean bean) {
        return mBookMarkBeanDao.insertOrReplace(bean);
    }


    /*****************************Get*************************************/

    /**
     * 根据书籍主键检索所有书签
     *
     * @return
     */
    public List<BookMarkBean> getBookMarkByLink(String bookLink) {
        return mBookMarkBeanDao.queryBuilder()
                .where(BookMarkBeanDao.Properties.BookLink.eq(bookLink))
                .list();
    }

    /*****************************Delete*************************************/

    /**
     * 删除书签
     *
     * @param bean
     */
    public void deleteBookMark(BookMarkBean bean) {
        mBookMarkBeanDao.delete(bean);
    }

}

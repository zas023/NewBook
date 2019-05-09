package com.thmub.newbook.model.local;

import com.thmub.newbook.bean.BookChapterBean;
import com.thmub.newbook.bean.ShelfBookBean;
import com.thmub.newbook.constant.Constant;
import com.thmub.newbook.model.dao.BookChapterBeanDao;
import com.thmub.newbook.model.dao.DaoDbHelper;
import com.thmub.newbook.model.dao.DaoSession;
import com.thmub.newbook.model.dao.ShelfBookBeanDao;
import com.thmub.newbook.utils.FileUtils;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Zhouas666 on 2019-03-31
 * Github: https://github.com/zas023
 */
public class BookShelfRepository {

    private static final String TAG = "BookShelfRepository";

    private static volatile BookShelfRepository sInstance;
    private DaoSession mSession;
    private ShelfBookBeanDao mShelfBookDao;
    private BookChapterBeanDao mBookChapterDao;

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
        //存储BookShelfBean
        mShelfBookDao.insertOrReplace(book);
        //
        if (book.getBookChapterList() != null) {
            // 存储BookChapterBean
            mSession.getBookChapterBeanDao().insertOrReplaceInTx(book.getBookChapterList());
        }
    }

    /**
     * 保存书籍列表
     *
     * @param books
     */
    public void saveShelfBooks(List<ShelfBookBean> books) {
        //存储BookShelfBean
        mShelfBookDao.insertOrReplaceInTx(books);
    }

    /**************************Get************************************/
    /**
     * 根据书名和作者查找书架
     *
     * @param bookName
     * @param author
     * @return
     */
    public ShelfBookBean getShelfBook(String bookName, String author) {
        return mShelfBookDao.queryBuilder()
                .where(ShelfBookBeanDao.Properties.Title.eq(bookName))
                .where(ShelfBookBeanDao.Properties.Author.eq(author))
                .unique();
    }

    /**
     * 根据书籍链接查找书籍
     *
     * @param bookLink
     * @return
     */
    public ShelfBookBean getShelfBook(String bookLink) {
        return mShelfBookDao.queryBuilder()
                .where(ShelfBookBeanDao.Properties.Link.eq(bookLink))
                .unique();
    }

    /**
     * 根据书籍链接查找书籍
     *
     * @param bookLink
     * @return
     */
    public ShelfBookBean getShelfBookWithChapters(String bookLink) {
        ShelfBookBean book = getShelfBook(bookLink);
        if (book != null)
            book.setBookChapterList(getChapters(book));
        return book;
    }

    /**
     * 获取所有本地图书
     *
     * @return
     */
    public List<ShelfBookBean> getAllShelfBooks() {
        return mShelfBookDao.queryBuilder()
                .orderDesc(ShelfBookBeanDao.Properties.LastRead)
                .list();
    }

    /**
     * 获取书籍所有章节
     *
     * @param book
     * @return
     */
    public List<BookChapterBean> getChapters(ShelfBookBean book) {
        return mSession.getBookChapterBeanDao().queryBuilder()
                .where(BookChapterBeanDao.Properties.BookLink.eq(book.getLink()))
                .list();
    }

    /**************************Delete************************************/

    /**
     * 删除书架书籍
     * 用于换源时更新书籍数据
     *
     * @param book
     */
    public void deleteShelfBook(ShelfBookBean book) {
        mShelfBookDao.delete(book);
    }

    /**
     * 删除书籍
     *
     * @param books
     */
    public void deleteShelfBooks(List<ShelfBookBean> books) {
        for (ShelfBookBean book : books) {
            mShelfBookDao.delete(book);
            removeChapters(book.getLink());
            removeFile(book.getTitle() + File.separator + book.getSourceTag());
        }
    }

    /**
     * 将书籍异步移出书籍
     *
     * @param book
     * @return
     */
    public Observable<Integer> removeShelfBookInRx(ShelfBookBean book) {

        return Observable.create(emitter -> {
            mShelfBookDao.delete(book);
            //
            removeChapters(book.getLink());
            //
            removeFile(book.getTitle() + File.separator + book.getSourceTag());
            emitter.onNext(1);
            emitter.onComplete();
        });
    }

    /**
     * 删除章节目录
     *
     * @param bookLink
     */
    public void removeChapters(String bookLink) {
        mSession.getBookChapterBeanDao()
                .queryBuilder()
                .where(BookChapterBeanDao.Properties.BookLink.eq(bookLink))
                .buildDelete()
                .executeDeleteWithoutDetachingEntities();
    }

    /**
     * 删除书籍缓存文件
     *
     * @param folderName
     */
    public void removeFile(String folderName) {
        FileUtils.deleteFile(Constant.BOOK_CACHE_PATH + folderName);
    }
}

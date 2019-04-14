package com.thmub.newbook.model.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.thmub.newbook.bean.ShelfBookBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "BOOK_SHELF_BEAN".
*/
public class BookShelfBeanDao extends AbstractDao<ShelfBookBean, String> {

    public static final String TABLENAME = "BOOK_SHELF_BEAN";

    /**
     * Properties of entity ShelfBookBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Link = new Property(0, String.class, "link", true, "LINK");
        public final static Property Title = new Property(1, String.class, "title", false, "TITLE");
        public final static Property Author = new Property(2, String.class, "author", false, "AUTHOR");
        public final static Property Desc = new Property(3, String.class, "desc", false, "DESC");
        public final static Property Cover = new Property(4, String.class, "cover", false, "COVER");
        public final static Property Source = new Property(5, String.class, "source", false, "SOURCE");
        public final static Property Updated = new Property(6, String.class, "updated", false, "UPDATED");
        public final static Property LastRead = new Property(7, String.class, "lastRead", false, "LAST_READ");
        public final static Property CurChapter = new Property(8, Integer.class, "curChapter", false, "CUR_CHAPTER");
        public final static Property CurChapterPage = new Property(9, Integer.class, "curChapterPage", false, "CUR_CHAPTER_PAGE");
        public final static Property IsUpdate = new Property(10, boolean.class, "isUpdate", false, "IS_UPDATE");
        public final static Property IsLocal = new Property(11, boolean.class, "isLocal", false, "IS_LOCAL");
    }

    private DaoSession daoSession;


    public BookShelfBeanDao(DaoConfig config) {
        super(config);
    }
    
    public BookShelfBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"BOOK_SHELF_BEAN\" (" + //
                "\"LINK\" TEXT PRIMARY KEY NOT NULL ," + // 0: link
                "\"TITLE\" TEXT," + // 1: title
                "\"AUTHOR\" TEXT," + // 2: author
                "\"DESC\" TEXT," + // 3: desc
                "\"COVER\" TEXT," + // 4: cover
                "\"SOURCE\" TEXT," + // 5: source
                "\"UPDATED\" TEXT," + // 6: updated
                "\"LAST_READ\" TEXT," + // 7: lastRead
                "\"CUR_CHAPTER\" INTEGER," + // 8: curChapter
                "\"CUR_CHAPTER_PAGE\" INTEGER," + // 9: curChapterPage
                "\"IS_UPDATE\" INTEGER NOT NULL ," + // 10: isUpdate
                "\"IS_LOCAL\" INTEGER NOT NULL );"); // 11: isLocal
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"BOOK_SHELF_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, ShelfBookBean entity) {
        stmt.clearBindings();
 
        String link = entity.getLink();
        if (link != null) {
            stmt.bindString(1, link);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(2, title);
        }
 
        String author = entity.getAuthor();
        if (author != null) {
            stmt.bindString(3, author);
        }
 
        String desc = entity.getDesc();
        if (desc != null) {
            stmt.bindString(4, desc);
        }
 
        String cover = entity.getCover();
        if (cover != null) {
            stmt.bindString(5, cover);
        }
 
        String source = entity.getSourceName();
        if (source != null) {
            stmt.bindString(6, source);
        }
 
        String updated = entity.getUpdated();
        if (updated != null) {
            stmt.bindString(7, updated);
        }
 
        String lastRead = entity.getLastRead();
        if (lastRead != null) {
            stmt.bindString(8, lastRead);
        }
 
        Integer curChapter = entity.getCurChapter();
        if (curChapter != null) {
            stmt.bindLong(9, curChapter);
        }
 
        Integer curChapterPage = entity.getCurChapterPage();
        if (curChapterPage != null) {
            stmt.bindLong(10, curChapterPage);
        }
        stmt.bindLong(11, entity.getIsUpdate() ? 1L: 0L);
        stmt.bindLong(12, entity.getIsLocal() ? 1L: 0L);
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, ShelfBookBean entity) {
        stmt.clearBindings();
 
        String link = entity.getLink();
        if (link != null) {
            stmt.bindString(1, link);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(2, title);
        }
 
        String author = entity.getAuthor();
        if (author != null) {
            stmt.bindString(3, author);
        }
 
        String desc = entity.getDesc();
        if (desc != null) {
            stmt.bindString(4, desc);
        }
 
        String cover = entity.getCover();
        if (cover != null) {
            stmt.bindString(5, cover);
        }
 
        String source = entity.getSourceName();
        if (source != null) {
            stmt.bindString(6, source);
        }
 
        String updated = entity.getUpdated();
        if (updated != null) {
            stmt.bindString(7, updated);
        }
 
        String lastRead = entity.getLastRead();
        if (lastRead != null) {
            stmt.bindString(8, lastRead);
        }
 
        Integer curChapter = entity.getCurChapter();
        if (curChapter != null) {
            stmt.bindLong(9, curChapter);
        }
 
        Integer curChapterPage = entity.getCurChapterPage();
        if (curChapterPage != null) {
            stmt.bindLong(10, curChapterPage);
        }
        stmt.bindLong(11, entity.getIsUpdate() ? 1L: 0L);
        stmt.bindLong(12, entity.getIsLocal() ? 1L: 0L);
    }

    @Override
    protected final void attachEntity(ShelfBookBean entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    protected ShelfBookBean readEntity(Cursor cursor, int offset) {
        return null;
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

     
    @Override
    public void readEntity(Cursor cursor, ShelfBookBean entity, int offset) {
        entity.setLink(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setTitle(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setAuthor(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setDesc(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setCover(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setSourceName(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setUpdated(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setLastRead(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setCurChapter(cursor.isNull(offset + 8) ? null : cursor.getInt(offset + 8));
        entity.setCurChapterPage(cursor.isNull(offset + 9) ? null : cursor.getInt(offset + 9));
        entity.setIsUpdate(cursor.getShort(offset + 10) != 0);
        entity.setIsLocal(cursor.getShort(offset + 11) != 0);
     }
    
    @Override
    protected final String updateKeyAfterInsert(ShelfBookBean entity, long rowId) {
        return entity.getLink();
    }
    
    @Override
    public String getKey(ShelfBookBean entity) {
        if(entity != null) {
            return entity.getLink();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(ShelfBookBean entity) {
        return entity.getLink() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}

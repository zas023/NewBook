package com.thmub.newbook.manager;

import com.thmub.newbook.constant.Constant;
import com.thmub.newbook.utils.FileUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Zhouas666 on 2019-04-01
 * Github: https://github.com/zas023
 */
public class BookManager {

    private static final String TAG = "BookManager";

    private String folderName;
    private String chapterName;
    private long chapterLen;
    private long position;
    private Map<String, Cache> cacheMap = new HashMap<>();
    private static volatile BookManager sInstance;

    public static BookManager getInstance() {
        if (sInstance == null) {
            synchronized (BookManager.class) {
                if (sInstance == null) {
                    sInstance = new BookManager();
                }
            }
        }
        return sInstance;
    }

    public boolean openChapter(String folderName, String chapterName) {
        return openChapter(folderName, chapterName, 0);
    }

    public boolean openChapter(String folderName, String chapterName, long position) {
        //如果文件不存在，则打开失败
        File file = new File(Constant.BOOK_CACHE_PATH + folderName
                + File.separator + chapterName + FileUtils.SUFFIX_NB);
        if (!file.exists()) {
            return false;
        }
        this.folderName = folderName;
        this.chapterName = chapterName;
        this.position = position;
        createCache();
        return true;
    }

    private void createCache() {
        //创建Cache
        if (!cacheMap.containsKey(chapterName)) {
            Cache cache = new Cache();
            File file = getBookFile(folderName, chapterName);
            //TODO:数据加载默认utf-8(以后会增加判断),FileUtils采用Reader获取数据的，可能用byte会更好一点
            char[] array = FileUtils.getFileContent(file).toCharArray();
            WeakReference<char[]> charReference = new WeakReference<char[]>(array);
            cache.size = array.length;
            cache.data = charReference;
            cacheMap.put(chapterName, cache);

            chapterLen = cache.size;
        } else {
            chapterLen = cacheMap.get(chapterName).getSize();
        }
    }

    public void setPosition(long position) {
        this.position = position;
    }

    public long getPosition() {
        return position;
    }

    //获取章节的内容
    public char[] getContent() {
        if (cacheMap.size() == 0) {
            return new char[1];
        }
        char[] block = cacheMap.get(chapterName).getData().get();
        if (block == null) {
            File file = getBookFile(folderName, chapterName);
            block = FileUtils.getFileContent(file).toCharArray();
            Cache cache = cacheMap.get(chapterName);
            cache.data = new WeakReference<char[]>(block);
        }
        return block;
    }

    public long getChapterLen() {
        return chapterLen;
    }

    public void clear() {
        cacheMap.clear();
        position = 0;
        chapterLen = 0;
    }

    /**
     * 存储章节内容到本地文件
     */
    public boolean saveChapter(String folderName, int index, String fileName, String content) {
        if (content == null) {
            return false;
        }
        File file = getBookFile(folderName, formatFileName(index, fileName));
        //获取流并存储
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(fileName + "\n\n");
            writer.write(content);
            writer.write("\n\n");
            writer.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 创建或获取存储文件
     *
     * @param folderName
     * @param fileName
     * @return
     */
    public static File getBookFile(String folderName, String fileName) {
        return FileUtils.getFile(Constant.BOOK_CACHE_PATH + folderName
                + File.separator + fileName + FileUtils.SUFFIX_NB);
    }

    public static String formatFileName(int index, String fileName) {
        return String.format("%05d-%s", index, formatFolderName(fileName));
    }

    public static String formatFolderName(String folderName) {
        return folderName.replace("/", "")
                .replace(":", "")
                .replace(".", "");
    }

    /**
     * 获取章节大小
     *
     * @param folderName
     * @return
     */
    public static long getBookChapterSize(String folderName) {
        return FileUtils.getDirSize(FileUtils.getFolder(Constant.BOOK_CACHE_PATH + folderName));
    }

    /**
     * 根据文件名判断是否被缓存过
     * 因为可能数据库显示被缓存过，但是文件中却没有的情况，所以需要根据文件判断是否被缓存过
     *
     * @param folderName : bookName-sourceName
     * @param fileName:  chapterName
     * @return
     */
    public static boolean isChapterCached(String folderName, String fileName) {
        File file = new File(Constant.BOOK_CACHE_PATH + folderName
                + File.separator + fileName + FileUtils.SUFFIX_NB);
        return file.exists();
    }

    /*******************************InterClass************************************/
    public class Cache {
        private long size;
        private WeakReference<char[]> data;

        public WeakReference<char[]> getData() {
            return data;
        }

        public void setData(WeakReference<char[]> data) {
            this.data = data;
        }

        public long getSize() {
            return size;
        }

        public void setSize(long size) {
            this.size = size;
        }
    }
}
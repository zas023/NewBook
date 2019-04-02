package com.thmub.newbook.utils;

import com.thmub.newbook.bean.ShelfBookBean;
import com.thmub.newbook.constant.Constant;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by Zhouas666 on 2019-03-31
 * Github: https://github.com/zas023
 */
public class BookShelfUtils {

    public static final String TAG = "BookShelfUtils";

    private static HashMap<String, HashSet<Integer>> chapterCaches = getChapterCaches();

    private static HashMap<String, HashSet<Integer>> getChapterCaches() {
        HashMap<String, HashSet<Integer>> temp = new HashMap<>();
        File file = FileUtils.getFolder(Constant.BOOK_CACHE_PATH);
        try {
            String[] booksCached = file.list((dir, name) -> new File(dir, name).isDirectory());
            for (String bookPath : booksCached) {
                HashSet<Integer> chapterIndexS = new HashSet<>();
                file = new File(Constant.BOOK_CACHE_PATH + bookPath);
                String[] chapters = file.list((dir, name) -> name.matches("^\\d{5,}-.*" + FileUtils.SUFFIX_CB + "$"));
                for (String chapter : chapters) {
                    chapterIndexS.add(
                            Integer.parseInt(chapter.substring(0, chapter.indexOf('-')))
                    );
                }
                temp.put(bookPath, chapterIndexS);
            }
        } catch (Exception ignored) {
        }
        return temp;
    }


    public static boolean setChapterIsCached(String bookPathName, Integer index, boolean cached) {
        bookPathName = formatFolderName(bookPathName);
        if (!chapterCaches.containsKey(bookPathName))
            chapterCaches.put(bookPathName, new HashSet<>());
        if (cached)
            return chapterCaches.get(bookPathName).add(index);
        else
            return chapterCaches.get(bookPathName).remove(index);
    }

    /**
     * 根据文件名判断是否被缓存过 (因为可能数据库显示被缓存过，但是文件中却没有的情况，所以需要根据文件判断是否被缓存过)
     */
    // be careful to use this method, the storage path (folderName) has been changed
    public static boolean isChapterCached(String folderName, int index, String fileName) {
        File file = getBookFile(folderName, index, fileName);
        boolean cached = file.exists();
        setChapterIsCached(folderName, index, cached);
        return cached;
    }


    /**
     * 存储章节
     */
    public static synchronized boolean saveChapterInfo(String folderName, int index, String fileName, String content) {
        if (content == null) {
            return false;
        }
        File file = getBookFile(folderName, index, fileName);
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
     */
    public static File getBookFile(String folderName, int index, String fileName) {
        return FileUtils.getFile(Constant.BOOK_CACHE_PATH + File.separator + formatFolderName(folderName)
                + File.separator + formatFileName(index, fileName) + FileUtils.SUFFIX_CB);
    }

    public static String formatFileName(int index, String fileName) {
        return String.format("%05d-%s", index, formatFolderName(fileName));
    }

    public static String formatFolderName(String folderName) {
        return folderName.replace("/", "")
                .replace(":", "")
                .replace(".", "");
    }


    public static String getReadProgress(ShelfBookBean bookShelfBean) {
        return getReadProgress(bookShelfBean.getCurChapter(), bookShelfBean.getBookChapterListSize(), 0, 0);
    }

    public static String getReadProgress(int durChapterIndex, int chapterAll, int durPageIndex, int durPageAll) {
        DecimalFormat df = new DecimalFormat("0.0%");
        if (chapterAll == 0 || (durPageAll == 0 && durChapterIndex == 0)) {
            return "0.0%";
        } else if (durPageAll == 0) {
            return df.format((durChapterIndex + 1.0f) / chapterAll);
        }
        String percent = df.format(durChapterIndex * 1.0f / chapterAll + 1.0f / chapterAll * (durPageIndex + 1) / durPageAll);
        if (percent.equals("100.0%") && (durChapterIndex + 1 != chapterAll || durPageIndex + 1 != durPageAll)) {
            percent = "99.9%";
        }
        return percent;
    }
}

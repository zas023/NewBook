package com.thmub.newbook.constant;

import com.thmub.newbook.utils.FileUtils;

import java.io.File;

/**
 * Created by Zhouas666 on 2019-04-01
 * Github: https://github.com/zas023
 */
public class Constant {
    //BookCachePath (因为getCachePath引用了Context，所以必须是静态变量，不能够是静态常量)
    public static String BOOK_CACHE_PATH = FileUtils.getCachePath()+File.separator + "book_cache"+ File.separator ;
    //文件阅读记录保存的路径
    public static String BOOK_RECORD_PATH = FileUtils.getCachePath() + File.separator + "book_record" + File.separator;

    //Book Date Convert Format
    public static final String FORMAT_BOOK_DATE = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String FORMAT_TIME = "HH:mm";
    public static final String FORMAT_FILE_DATE = "yyyy-MM-dd";

    /*URL_BASE*/
    public static final String API_BASE_URL = "http://api.zhuishushenqi.com";
    public static final String IMG_BASE_URL = "http://statics.zhuishushenqi.com";
}

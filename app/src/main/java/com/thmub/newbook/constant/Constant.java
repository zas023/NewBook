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
}

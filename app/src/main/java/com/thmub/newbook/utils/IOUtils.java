package com.thmub.newbook.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by zhouas666 on 18-2-11.
 * IO流工具类
 */

public class IOUtils {

    public static void close(Closeable closeable){
        if (closeable == null) return;
        try {
            closeable.close();
        } catch (IOException e) {
            e.printStackTrace();
            //close error
        }
    }
}

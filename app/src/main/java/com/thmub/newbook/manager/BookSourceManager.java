package com.thmub.newbook.manager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thmub.newbook.bean.BookSourceBean;
import com.thmub.newbook.utils.OkHttpUtils;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Zhouas666 on 2019-03-28
 * Github: https://github.com/zas023
 */

public class BookSourceManager {

    private static volatile BookSourceManager sInstance;

    public static BookSourceManager getInstance() {
        if (sInstance == null) {
            synchronized (BookSourceManager.class) {
                if (sInstance == null) {
                    sInstance = new BookSourceManager();
                }
            }
        }
        return sInstance;
    }

    /**
     * 从本地导入书源
     *
     * @param jsonStr
     * @return
     */
    public Observable<List<BookSourceBean>> importSourceFromLocal(String jsonStr) {
        return Observable.create(emitter -> {
            Gson gson = new Gson();
            List<BookSourceBean> list = gson.fromJson(jsonStr, new TypeToken<List<BookSourceBean>>() {
            }.getType());
            if (list == null || list.size() <= 0) {
                emitter.onError(new Throwable("格式不正确"));
            } else {
                emitter.onNext(list);
                emitter.onComplete();
            }
        });
    }

    /**
     * 从网络导入书源
     *
     * @param url
     * @return
     */
    public Observable<List<BookSourceBean>> importSourceFromNet(String url) {
        return Observable.create(emitter -> {

            String jsonStr = null;
            try {
                jsonStr = OkHttpUtils.getHtml(url, "utf-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
            Gson gson = new Gson();
            List<BookSourceBean> list = gson.fromJson(jsonStr, new TypeToken<List<BookSourceBean>>() {
            }.getType());
            if (list == null || list.size() <= 0) {
                emitter.onError(new Throwable("格式不正确"));
            } else {
                emitter.onNext(list);
                emitter.onComplete();
            }
        });
    }
}

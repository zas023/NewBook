package com.thmub.newbook.test;

import com.thmub.newbook.bean.BookSearchBean;
import com.thmub.newbook.model.SearchEngine;
import com.thmub.newbook.model.ISourceModel;
import com.thmub.newbook.manager.BookSourceManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhouas666 on 2019-03-27
 * Github: https://github.com/zas023
 */
public class TestCrawler {

    String keyWord = "汉乡";

    List<BookSearchBean> copyDataS = new ArrayList<>();

    List<ISourceModel> siteList = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        TestCrawler test = new TestCrawler();
        test.testSearch();

    }

    public void testSearch() {

        SearchEngine searchEngine = new SearchEngine();
//        searchEngine.initSearchEngine(BookSourceManager.getInstance().getBookSourceList());
        searchEngine.setOnSearchListener(new SearchEngine.OnSearchListener() {

            @Override
            public void loadMoreFinish(Boolean isAll) {

            }

            @Override
            public synchronized void loadMoreSearchBook(List<BookSearchBean> newDataS) {
//                for (BookSearchBean bean : newDataS)
//                    System.out.println(bean.toString());
//                List<BookSearchBean> copyDataS = new ArrayList<>();
//                copyDataS.addAll(books);
                List<BookSearchBean> bookSearchBeansAdd = new ArrayList<>();
                //存在
                for (BookSearchBean temp : newDataS) {
                    Boolean isExsit = false;
                    for (BookSearchBean searchBook : copyDataS) {
                        if (temp.getTitle().equals(searchBook.getTitle())
                                && temp.getAuthor().equals(searchBook.getAuthor())) {
                            searchBook.addSource(temp.getSourceTag());
                            isExsit = true;
                        }
                    }
                    if (!isExsit) {
                        bookSearchBeansAdd.add(temp);
                    }
                }
//                for (BookSearchBean bean : bookSearchBeansAdd)
//                    System.out.println(bean.toString());
                //添加
                for (BookSearchBean temp : bookSearchBeansAdd) {
//                    if (temp.getTitle().equals(keyWord)) {
//                        for (int i = 0; i < copyDataS.size(); i++) {
//                            BookSearchBean searchBook = copyDataS.get(i);
//                            if (!searchBook.getTitle().equals(keyWord)) {
//                                copyDataS.add(i, temp);
//                                break;
//                            }
//                        }
//                    } else if (temp.getAuthor().equals(keyWord)) {
//                        for (int i = 0; i < copyDataS.size(); i++) {
//                            BookSearchBean searchBook = copyDataS.get(i);
//                            if (!searchBook.getTitle().equals(keyWord) && !searchBook.getAuthor().equals(keyWord)) {
//                                copyDataS.add(i, temp);
//                                break;
//                            }
//                        }
//                    } else if (temp.getTitle().contains(keyWord) || temp.getAuthor().contains(keyWord)) {
//                        for (int i = 0; i < copyDataS.size(); i++) {
//                            BookSearchBean searchBook = copyDataS.get(i);
//                            if (!searchBook.getTitle().equals(keyWord) && !searchBook.getAuthor().equals(keyWord)) {
//                                copyDataS.add(i, temp);
//                                break;
//                            }
//                        }
//                    } else {
//                        copyDataS.add(temp);
//                    }
                    copyDataS.add(temp);
//                    System.out.println(temp.toString());
                }


//                books = copyDataS;
                for (BookSearchBean bean : copyDataS)
                    System.out.println(bean.toString());
                System.out.println("-------------------------------------------------------");
            }
        });

        searchEngine.search(keyWord);
    }
}

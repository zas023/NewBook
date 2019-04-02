package com.thmub.newbook.widget.page;

import java.util.List;

/**
 * Created by zhouas666 on 18-1-23.
 * 书籍page
 */

public class TxtPage {
    int position;
    String title;
    int titleLines; //当前 lines 中为 title 的行数。
    List<String> lines;

    public String getContent() {
        StringBuilder s = new StringBuilder();
        if (lines != null) {
            for (int i = 0; i < lines.size(); i++) {
                s.append(lines.get(i));
            }
        }
        return s.toString();
    }
}

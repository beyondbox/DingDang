package com.zlkj.dingdangwuyou.entity;

import java.util.List;

/**
 * (没用着)
 * 社会要闻
 * Created by Botx on 2016/10/31.
 */

public class ImportantNews {

    private String newsType;
    private List<String> imgUrlList;
    private String title;
    private String time;

    public String getNewsType() {
        return newsType;
    }

    public void setNewsType(String newsType) {
        this.newsType = newsType;
    }

    public List<String> getImgUrlList() {
        return imgUrlList;
    }

    public void setImgUrlList(List<String> imgUrlList) {
        this.imgUrlList = imgUrlList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

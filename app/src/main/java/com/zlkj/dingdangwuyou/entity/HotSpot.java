package com.zlkj.dingdangwuyou.entity;

import java.util.List;

/**
 * (没用着)
 * 社会热点
 * Created by Botx on 2016/10/28.
 */

public class HotSpot {

    private String title;
    private List<String> imgUrlList;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getImgUrlList() {
        return imgUrlList;
    }

    public void setImgUrlList(List<String> imgUrlList) {
        this.imgUrlList = imgUrlList;
    }
}

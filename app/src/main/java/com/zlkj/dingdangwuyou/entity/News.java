package com.zlkj.dingdangwuyou.entity;

import java.io.Serializable;

/**
 * 新闻
 * Created by Botx on 2016/11/8.
 */

public class News implements Serializable{


    /**
     * content : 10月1日，杭州大街小巷升起了国旗、挂起了灯笼，处处洋溢着浓厚的节日氛围，人们用自己的方式庆祝国庆。
     “阿姨您好，您能为山区的小朋友们捐一元钱吗?谢谢您!我们送您一面小国旗。”10月1日上午，不少人在杭州市民中心附近遇到一支由小朋友组成的“爱心小分队”，这是杭师大附小三年级学生俞浩然和伙伴们一起做公益。一上午，他们送出了100多面小国旗。“我们想通过这种方式，过一个有意义的国庆节。”小朋友们表示。
     离市民中心不远处的杭州图书馆，一大早人就不少，从事设计工作的吕小姐和男友已经安静地坐在图书馆看书。10月1日，他们准备在这里泡一天。“在图书馆过个安安静静的国庆节，也给自己充充电。”吕小姐说。杭州图书馆在国庆期间特意准备了环保小剧场、诗画西湖朗诵会、英文戏剧工坊等，丰富市民们的假日生活。
     G20峰会让杭州惊艳亮相，国庆长假首日，杭州迎来大量海内外游客。
     “在杭州这座历史名城欢度国庆节，更能让孩子多了解中国传统文化。”在西湖景区的放鹤亭边，带着孩子来杭州旅游的上海游客杨子轩这样告诉记者。不少游客跟他有着同样的想法，断桥上、柳树边，许多人手里举着小国旗，西湖边闪耀着点点红色。
     除了西湖，杭州周边其他景区也人潮涌动。今年国庆节恰是湘湖三期第一天开放，位于老虎洞游客中心的初阳台，是整个湘湖观赏日出的最佳位置，不少人在这里欣赏日出。水乡余杭塘栖一片喜庆，水北街张灯结彩，沿街摆出了各式各样的传统米塑。
     截至1日下午5时30分，仅西湖景区已迎客45.68万人次，比去年同期增加了19.77%，其中最热闹的就是湖滨景区。有关部门提醒，近日出行到杭州游玩，可以避开热门景点，合理规划行程。在人流量较大的场合请注意保持秩序，服从现场工作人员指挥。
     * id : 1
     * newsImg : /upload/1.jpg
     * title : “爱心小分队”做公益 杭州市民和游客欢度国庆
     * type : 娱乐
     */

    private String content;
    private String id;
    private String newsImg;
    private String title;
    private String type;
    private CreateDate createDate;
    private ModifyDate modifyDate;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNewsImg() {
        return newsImg;
    }

    public void setNewsImg(String newsImg) {
        this.newsImg = newsImg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public CreateDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(CreateDate createDate) {
        this.createDate = createDate;
    }

    public ModifyDate getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(ModifyDate modifyDate) {
        this.modifyDate = modifyDate;
    }
}

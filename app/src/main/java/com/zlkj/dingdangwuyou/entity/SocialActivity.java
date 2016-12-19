package com.zlkj.dingdangwuyou.entity;

import java.io.Serializable;

/**
 * 公益活动
 * Created by Botx on 2016/11/10.
 */

public class SocialActivity implements Serializable{


    /**
     * g_content : □记者郭启朝通讯员陈新刚文图

     今年5月18日凌晨，南阳市卧龙区光武街道西华村一栋三层居民楼突发火灾，租住在一楼的方城青年王锋发现火情后，义无反顾地三次冲入火海救人，20多名邻居无一伤亡，而英雄王锋却98%面积烧伤，被送往南阳南石医院救治，度过了55天的前期治疗。

     今年7月12日，英雄王锋同志被医疗专机由南阳南石医院转送到北京解放军总医院第一附属医院救治。在这里，王锋在同伤痛顽强的斗争中，度过了他生命的最后81天。10月1日下午4时34分因多脏器衰竭而最终不幸离世。

     10月4日上午，家乡群众在北京八宝山公墓东礼堂为王锋举行了遗体告别仪式。
     * g_faqiren : 王哥
     * g_imgurl : upload/9.jpg
     * g_qq : 904528748
     * g_tel : 15031142492
     * g_title : 河南三入火海救人英雄魂归故里 近万群众送别
     * id : 456
     */

    private String g_content;
    private String g_faqiren;
    private String g_imgurl;
    private String g_qq;
    private String g_tel;
    private String g_title;
    private String id;
    private CreateDate createDate;
    private ModifyDate modifyDate;

    public String getG_content() {
        return g_content;
    }

    public void setG_content(String g_content) {
        this.g_content = g_content;
    }

    public String getG_faqiren() {
        return g_faqiren;
    }

    public void setG_faqiren(String g_faqiren) {
        this.g_faqiren = g_faqiren;
    }

    public String getG_imgurl() {
        return g_imgurl;
    }

    public void setG_imgurl(String g_imgurl) {
        this.g_imgurl = g_imgurl;
    }

    public String getG_qq() {
        return g_qq;
    }

    public void setG_qq(String g_qq) {
        this.g_qq = g_qq;
    }

    public String getG_tel() {
        return g_tel;
    }

    public void setG_tel(String g_tel) {
        this.g_tel = g_tel;
    }

    public String getG_title() {
        return g_title;
    }

    public void setG_title(String g_title) {
        this.g_title = g_title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

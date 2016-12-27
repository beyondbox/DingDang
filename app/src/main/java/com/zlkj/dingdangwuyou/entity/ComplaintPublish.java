package com.zlkj.dingdangwuyou.entity;

/**
 * 被投诉记录（赏金）
 * Created by Botx on 2016/12/27.
 */

public class ComplaintPublish {


    /**
     * createDate : {"date":31,"day":1,"hours":12,"minutes":8,"month":9,"nanos":0,"seconds":38,"time":1477886918000,"timezoneOffset":-480,"year":116}
     * id : f78ad6155818d4ba015818ecbdba0012
     * l_cpid : 1
     * l_jilu : 奖金发放不及时
     * l_userid : 2
     * modifyDate : {"date":31,"day":1,"hours":12,"minutes":8,"month":9,"nanos":0,"seconds":38,"time":1477886918000,"timezoneOffset":-480,"year":116}
     * ren_id : f78ad6155818d4ba015818eb4763000e
     */

    private String id;
    private String l_cpid;
    private String l_jilu;
    private String l_userid;

    private CreateDate createDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getL_cpid() {
        return l_cpid;
    }

    public void setL_cpid(String l_cpid) {
        this.l_cpid = l_cpid;
    }

    public String getL_jilu() {
        return l_jilu;
    }

    public void setL_jilu(String l_jilu) {
        this.l_jilu = l_jilu;
    }

    public String getL_userid() {
        return l_userid;
    }

    public void setL_userid(String l_userid) {
        this.l_userid = l_userid;
    }

    public CreateDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(CreateDate createDate) {
        this.createDate = createDate;
    }
}

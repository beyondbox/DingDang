package com.zlkj.dingdangwuyou.entity;

/**
 * 被投诉记录（招贤纳士——个人）
 * Created by Botx on 2016/12/21.
 */

public class ComplaintHirePer {


    /**
     * createDate : {"date":21,"day":3,"hours":17,"minutes":2,"month":11,"nanos":0,"seconds":29,"time":1482310949000,"timezoneOffset":-480,"year":116}
     * id : f78ad6155818d4ba015818e334330001
     * modifyDate : {"date":21,"day":3,"hours":17,"minutes":2,"month":11,"nanos":0,"seconds":34,"time":1482310954000,"timezoneOffset":-480,"year":116}
     * z_cpid : 2
     * z_jilu : 不守时
     * z_userid : 1
     */

    private String id;
    private String z_cpid;
    private String z_jilu;
    private String z_userid;

    private CreateDate createDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getZ_cpid() {
        return z_cpid;
    }

    public void setZ_cpid(String z_cpid) {
        this.z_cpid = z_cpid;
    }

    public String getZ_jilu() {
        return z_jilu;
    }

    public void setZ_jilu(String z_jilu) {
        this.z_jilu = z_jilu;
    }

    public String getZ_userid() {
        return z_userid;
    }

    public void setZ_userid(String z_userid) {
        this.z_userid = z_userid;
    }

    public CreateDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(CreateDate createDate) {
        this.createDate = createDate;
    }
}

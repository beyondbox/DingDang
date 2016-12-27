package com.zlkj.dingdangwuyou.entity;

/**
 * 被投诉记录（招贤纳士——公司）
 * Created by Botx on 2016/12/27.
 */

public class ComplaintHireCom {


    /**
     * createDate : {"date":22,"day":4,"hours":16,"minutes":3,"month":11,"nanos":0,"seconds":37,"time":1482393817000,"timezoneOffset":-480,"year":116}
     * id : f78ad615592494b50159258e910c0005
     * m_cpid : 2
     * m_jilu : encodeURI
     * m_userid : 1
     * modifyDate : {"date":22,"day":4,"hours":16,"minutes":3,"month":11,"nanos":0,"seconds":37,"time":1482393817000,"timezoneOffset":-480,"year":116}
     */

    private String id;
    private String m_cpid;
    private String m_jilu;
    private String m_userid;

    private CreateDate createDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getM_cpid() {
        return m_cpid;
    }

    public void setM_cpid(String m_cpid) {
        this.m_cpid = m_cpid;
    }

    public String getM_jilu() {
        return m_jilu;
    }

    public void setM_jilu(String m_jilu) {
        this.m_jilu = m_jilu;
    }

    public String getM_userid() {
        return m_userid;
    }

    public void setM_userid(String m_userid) {
        this.m_userid = m_userid;
    }

    public CreateDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(CreateDate createDate) {
        this.createDate = createDate;
    }
}

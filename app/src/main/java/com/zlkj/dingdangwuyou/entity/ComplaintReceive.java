package com.zlkj.dingdangwuyou.entity;

/**
 * 被投诉记录（猎金）
 * Created by Botx on 2016/12/27.
 */

public class ComplaintReceive {


    /**
     * createDate : {"date":31,"day":1,"hours":12,"minutes":4,"month":9,"nanos":0,"seconds":42,"time":1477886682000,"timezoneOffset":-480,"year":116}
     * id : f78ad6155818d4ba015818e9243a000c
     * jietask : {"createDate":{"date":31,"day":1,"hours":12,"minutes":3,"month":9,"nanos":0,"seconds":0,"time":1477886580000,"timezoneOffset":-480,"year":116},"id":"f78ad6155818d4ba015818e7963e000b","jieling":{"createDate":{"date":31,"day":1,"hours":12,"minutes":3,"month":9,"nanos":0,"seconds":0,"time":1477886580000,"timezoneOffset":-480,"year":116},"id":"f78ad6155818d4ba015818e7962f000a","jl_ren_id":"1","jl_time":"2016-10-31","jlarea":"裕华","jlidentity":"个人","jlname":"健健","jlsex":"男","jltai":"2","jltel":"13832385110","modifyDate":{"date":31,"day":1,"hours":12,"minutes":5,"month":9,"nanos":0,"seconds":38,"time":1477886738000,"timezoneOffset":-480,"year":116}},"jl_id":"f78ad6155818d4ba015818e7962f000a","modifyDate":{"date":31,"day":1,"hours":12,"minutes":3,"month":9,"nanos":0,"seconds":0,"time":1477886580000,"timezoneOffset":-480,"year":116},"ren_id":"f78ad6155818d4ba015818e6f83b0009","task":{"ca_id":"3","createDate":{"date":31,"day":1,"hours":12,"minutes":2,"month":9,"nanos":0,"seconds":19,"time":1477886539000,"timezoneOffset":-480,"year":116},"id":"f78ad6155818d4ba015818e6f83b0009","modifyDate":{"date":31,"day":1,"hours":12,"minutes":5,"month":9,"nanos":0,"seconds":38,"time":1477886738000,"timezoneOffset":-480,"year":116},"picture":"/upload/c5325ba1-5ccb-4b1b-865f-49e4eb2231ed.jpg,null","r_id":"2","t_area":"裕华","t_contact":"1358856657","t_content":"要快 急用","t_finish_time":{"date":31,"day":1,"hours":0,"minutes":0,"month":9,"nanos":0,"seconds":0,"time":1477843200000,"timezoneOffset":-480,"year":116},"t_hbnum":"3","t_money":"100","t_name":"修理空调","t_num":"3","t_status":"个人","t_time":null,"t_words":"无","u_id":"2"}}
     * jl_id : f78ad6155818d4ba015818e7962f000a
     * modifyDate : {"date":31,"day":1,"hours":12,"minutes":4,"month":9,"nanos":0,"seconds":42,"time":1477886682000,"timezoneOffset":-480,"year":116}
     * s_cpid : 1
     * s_jilu : 信息虚假
     * s_userid : 2
     */

    private String id;
    private String s_cpid;
    private String s_jilu;
    private String s_userid;

    private CreateDate createDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getS_cpid() {
        return s_cpid;
    }

    public void setS_cpid(String s_cpid) {
        this.s_cpid = s_cpid;
    }

    public String getS_jilu() {
        return s_jilu;
    }

    public void setS_jilu(String s_jilu) {
        this.s_jilu = s_jilu;
    }

    public String getS_userid() {
        return s_userid;
    }

    public void setS_userid(String s_userid) {
        this.s_userid = s_userid;
    }

    public CreateDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(CreateDate createDate) {
        this.createDate = createDate;
    }
}

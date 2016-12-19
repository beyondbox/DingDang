package com.zlkj.dingdangwuyou.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 我接受的任务
 * Created by Botx on 2016/12/14.
 */

public class ReceivedTask implements Serializable{


    /**
     * createDate : {"date":12,"day":1,"hours":11,"minutes":26,"month":11,"nanos":0,"seconds":34,"time":1481513194000,"timezoneOffset":-480,"year":116}
     * id : f78ad615589508d90158f111529000a0
     * jieling : {"createDate":{"date":12,"day":1,"hours":11,"minutes":26,"month":11,"nanos":0,"seconds":34,"time":1481513194000,"timezoneOffset":-480,"year":116},"id":"f78ad615589508d90158f1115290009f","jl_ren_id":"1","jl_time":"","jlarea":"石家庄","jlidentity":"","jlname":"健健","jlsex":"男","jltai":"1","jltel":"15236251525","modifyDate":{"date":12,"day":1,"hours":17,"minutes":52,"month":11,"nanos":0,"seconds":15,"time":1481536335000,"timezoneOffset":-480,"year":116}}
     * jl_id : f78ad615589508d90158f1115290009f
     * modifyDate : {"date":12,"day":1,"hours":11,"minutes":26,"month":11,"nanos":0,"seconds":34,"time":1481513194000,"timezoneOffset":-480,"year":116}
     * ren_id : f78ad6155818d4ba015819cf573a0013
     * task : {"ca_id":"3","createDate":{"date":31,"day":1,"hours":16,"minutes":16,"month":9,"nanos":0,"seconds":8,"time":1477901768000,"timezoneOffset":-480,"year":116},"id":"f78ad6155818d4ba015819cf573a0013","modifyDate":{"date":12,"day":1,"hours":11,"minutes":26,"month":11,"nanos":0,"seconds":34,"time":1481513194000,"timezoneOffset":-480,"year":116},"picture":"","r_id":"1","t_area":"裕华","t_contact":"138658965","t_content":"有关网页直播的资料 越详细越好","t_finish_time":{"date":31,"day":1,"hours":0,"minutes":0,"month":9,"nanos":0,"seconds":0,"time":1477843200000,"timezoneOffset":-480,"year":116},"t_hbnum":"30","t_money":"10","t_name":"网页直播技术","t_num":"5","t_status":"个人","t_time":null,"t_words":"无","u_id":"2"}
     */

    private String id;
    private String jl_id;
    private String ren_id;
    @SerializedName("jieling")
    private Receiver receiver; //接令人信息
    private Task task; //任务信息

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Receiver getReceiver() {
        return receiver;
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    public String getJl_id() {
        return jl_id;
    }

    public void setJl_id(String jl_id) {
        this.jl_id = jl_id;
    }

    public String getRen_id() {
        return ren_id;
    }

    public void setRen_id(String ren_id) {
        this.ren_id = ren_id;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

}

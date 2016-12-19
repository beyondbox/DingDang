package com.zlkj.dingdangwuyou.entity;

import java.io.Serializable;

/**
 * 接令人
 * Created by Botx on 2016/12/9.
 */

public class Receiver implements Serializable{


    /**
     * createDate : {"date":25,"day":5,"hours":14,"minutes":17,"month":10,"nanos":0,"seconds":50,"time":1480054670000,"timezoneOffset":-480,"year":116}
     * id : f78ad615589508d901589a22065c0028
     * jl_ren_id : 1
     * jl_time :
     * jlarea : 石家庄
     * jlidentity : 1
     * jlname : 刘
     * jlsex : 男
     * jltai : 1
     * jltel : 1887474522
     * modifyDate : {"date":25,"day":5,"hours":18,"minutes":4,"month":10,"nanos":0,"seconds":50,"time":1480068290000,"timezoneOffset":-480,"year":116}
     */

    private String id;
    private String jl_ren_id;
    private String jl_time;
    private String jlarea;
    private String jlidentity;
    private String jlname;
    private String jlsex;
    private String jltai;
    private String jltel;

    private ModifyDate modifyDate;
    private CreateDate createDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJl_ren_id() {
        return jl_ren_id;
    }

    public void setJl_ren_id(String jl_ren_id) {
        this.jl_ren_id = jl_ren_id;
    }

    public String getJl_time() {
        return jl_time;
    }

    public void setJl_time(String jl_time) {
        this.jl_time = jl_time;
    }

    public String getJlarea() {
        return jlarea;
    }

    public void setJlarea(String jlarea) {
        this.jlarea = jlarea;
    }

    public String getJlidentity() {
        return jlidentity;
    }

    public void setJlidentity(String jlidentity) {
        this.jlidentity = jlidentity;
    }

    public String getJlname() {
        return jlname;
    }

    public void setJlname(String jlname) {
        this.jlname = jlname;
    }

    public String getJlsex() {
        return jlsex;
    }

    public void setJlsex(String jlsex) {
        this.jlsex = jlsex;
    }

    public String getJltai() {
        return jltai;
    }

    public void setJltai(String jltai) {
        this.jltai = jltai;
    }

    public String getJltel() {
        return jltel;
    }

    public void setJltel(String jltel) {
        this.jltel = jltel;
    }

    public ModifyDate getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(ModifyDate modifyDate) {
        this.modifyDate = modifyDate;
    }

    public CreateDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(CreateDate createDate) {
        this.createDate = createDate;
    }
}

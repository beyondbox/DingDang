package com.zlkj.dingdangwuyou.entity;

import java.io.Serializable;

/**
 * 任务
 * Created by btx on 2016/11/24.
 */

public class Task implements Serializable{


    /**
     * ca_id : 2
     * createDate : {"date":27,"day":4,"hours":16,"minutes":17,"month":9,"nanos":0,"seconds":8,"time":1477556228000,"timezoneOffset":-480,"year":116}
     * id : f78ad6155805322e01580536d0730002
     * modifyDate : {"date":27,"day":4,"hours":16,"minutes":17,"month":9,"nanos":0,"seconds":8,"time":1477556228000,"timezoneOffset":-480,"year":116}
     * picture :
     * r_id : 0
     * t_area : 石家庄
     * t_contact : 13865856657
     * t_content : 质量要保证
     * t_finish_time : {"date":27,"day":4,"hours":0,"minutes":0,"month":9,"nanos":0,"seconds":0,"time":1477497600000,"timezoneOffset":-480,"year":116}
     * t_hbnum : 4
     * t_money : 30
     * t_name : 修锁
     * t_num : 2
     * t_status : 个人
     * t_time : null
     * t_words : 无
     * u_id : f78ad6155805322e0158053580610001
     */

    private String ca_id;
    private String id;
    private String picture;
    private String r_id;
    private String t_area;
    private String t_contact;
    private String t_content;
    private TFinishTimeBean t_finish_time;
    private String t_hbnum;
    private String t_money;
    private String t_name;
    private String t_num;
    private String t_status;
    private String t_time;
    private String t_words;
    private String u_id;

    private CreateDate createDate;
    private ModifyDate modifyDate;

    public String getCa_id() {
        return ca_id;
    }

    public void setCa_id(String ca_id) {
        this.ca_id = ca_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getR_id() {
        return r_id;
    }

    public void setR_id(String r_id) {
        this.r_id = r_id;
    }

    public String getT_area() {
        return t_area;
    }

    public void setT_area(String t_area) {
        this.t_area = t_area;
    }

    public String getT_contact() {
        return t_contact;
    }

    public void setT_contact(String t_contact) {
        this.t_contact = t_contact;
    }

    public String getT_content() {
        return t_content;
    }

    public void setT_content(String t_content) {
        this.t_content = t_content;
    }

    public TFinishTimeBean getT_finish_time() {
        return t_finish_time;
    }

    public void setT_finish_time(TFinishTimeBean t_finish_time) {
        this.t_finish_time = t_finish_time;
    }

    public String getT_hbnum() {
        return t_hbnum;
    }

    public void setT_hbnum(String t_hbnum) {
        this.t_hbnum = t_hbnum;
    }

    public String getT_money() {
        return t_money;
    }

    public void setT_money(String t_money) {
        this.t_money = t_money;
    }

    public String getT_name() {
        return t_name;
    }

    public void setT_name(String t_name) {
        this.t_name = t_name;
    }

    public String getT_num() {
        return t_num;
    }

    public void setT_num(String t_num) {
        this.t_num = t_num;
    }

    public String getT_status() {
        return t_status;
    }

    public void setT_status(String t_status) {
        this.t_status = t_status;
    }

    public String getT_time() {
        return t_time;
    }

    public void setT_time(String t_time) {
        this.t_time = t_time;
    }

    public String getT_words() {
        return t_words;
    }

    public void setT_words(String t_words) {
        this.t_words = t_words;
    }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
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



    public static class TFinishTimeBean implements Serializable{
        private int date;
        private int day;
        private int hours;
        private int minutes;
        private int month;
        private int nanos;
        private int seconds;
        private long time;
        private int timezoneOffset;
        private int year;

        public int getDate() {
            return date;
        }

        public void setDate(int date) {
            this.date = date;
        }

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        public int getHours() {
            return hours;
        }

        public void setHours(int hours) {
            this.hours = hours;
        }

        public int getMinutes() {
            return minutes;
        }

        public void setMinutes(int minutes) {
            this.minutes = minutes;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public int getNanos() {
            return nanos;
        }

        public void setNanos(int nanos) {
            this.nanos = nanos;
        }

        public int getSeconds() {
            return seconds;
        }

        public void setSeconds(int seconds) {
            this.seconds = seconds;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public int getTimezoneOffset() {
            return timezoneOffset;
        }

        public void setTimezoneOffset(int timezoneOffset) {
            this.timezoneOffset = timezoneOffset;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }
    }
}

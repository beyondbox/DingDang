package com.zlkj.dingdangwuyou.entity;

import java.io.Serializable;

/**
 * Created by Botx on 2016/11/9.
 */

public class CreateDate implements Serializable{


    /**
     * date : 30
     * day : 5
     * hours : 16
     * minutes : 50
     * month : 8
     * nanos : 0
     * seconds : 9
     * time : 1475225409000
     * timezoneOffset : -480
     * year : 116
     */

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

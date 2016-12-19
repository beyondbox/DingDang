package com.zlkj.dingdangwuyou.net;

/**
 * 网络请求地址
 * Created by Botx on 2016/11/1.
 */

public class Url {

    public static final String HOST = "http://www.dingdangwuyou.com";
    //public static final String HOST = "http://192.168.1.127:8080/dingdangwuyou";


    /**
     * 个人用户登录
     */
    public static final String URL_LOGIN_PER = HOST + "/mycenter/user!UserLogin.action";
    /**
     * 企业用户登录
     */
    public static final String URL_LOGIN_COM = HOST + "/mycenter/com_pany!CompanyLogin.action";
    /**
     * 个人用户注册
     */
    public static final String URL_REGISTER_PER = HOST + "/mycenter/user!register.action";
    /**
     * 企业用户注册
     */
    public static final String URL_REGISTER_COM = HOST + "/mycenter/com_pany!register.action";


    /**
     * 获取所有新闻
     */
    public static final String URL_GET_NEWS = HOST + "/mycenter/news!getAllnews.action";
    /**
     * 获取所有公益活动
     */
    public static final String URL_GET_SOCIAL_ACTIVITY = HOST + "/mycenter/news!getAllGongyi.action";


    /**
     * 个人-毛遂自荐-个人属性赋值-全职
     */
    public static final String URL_USER_MODIFY_FULLTIME = HOST + "/recruitmagi/user!modifyuser.action";
    /**
     * 个人-毛遂自荐-个人属性赋值-兼职
     */
    public static final String URL_USER_MODIFY_PARTTIME = HOST + "/recruitmagi/user!modifyuser1.action";
    /**
     * 个人-毛遂自荐-首次匹配
     */
    public static final String URL_USER_MATCH_FIRST = HOST + "/recruitmagi/companyrecruit!find.action";
    /**
     * 个人-毛遂自荐-历史匹配
     */
    public static final String URL_USER_MATCH_HISTORY = HOST + "/recruitmagi/user!find3.action";
    /**
     * 个人-毛遂自荐-最新招聘信息
     */
    public static final String URL_USER_HIRE_LATEST = HOST + "/recruitmagi/companyrecruit!find3.action";
    /**
     * 企业-招贤纳士-发布全职
     */
    public static final String URL_COM_MODIFY_FULLTIME = HOST + "/recruitmagi/companyrecruit!add.action";
    /**
     * 企业-招贤纳士-发布兼职
     */
    public static final String URL_COM_MODIFY_PARTTIME = HOST + "/recruitmagi/companyrecruit!add1.action";
    /**
     * 企业-招贤纳士-首次匹配
     */
    public static final String URL_COM_MATCH_FIRST = HOST + "/recruitmagi/user!find.action";
    /**
     * 企业-招贤纳士-历史匹配
     */
    public static final String URL_COM_MATCH_HISTORY = HOST + "/recruitmagi/companyrecruit!find2.action";
    /**
     * 企业-招贤纳士-最新自荐信息
     */
    public static final String URL_COM_COMMEND_LATEST = HOST + "/recruitmagi/user!find4.action";


    /**
     * 赏金-任务发布
     */
    public static final String URL_TASK_RELEASE = HOST + "/moneypenny/reward!taskSave.action";
    /**
     * 赏金-查询我发布的任务
     */
    public static final String URL_TASK_GET_PUBLISHED = HOST + "/moneypenny/reward!task.action";
    /**
     * 赏金-选择接令人
     */
    public static final String URL_TASK_CHOOSE_RECEIVER = HOST + "/moneypenny/jieling!xiugai.action";
    /**
     * 获取接令id
     */
    public static final String URL_TASK_GET_JIELING_ID = HOST + "/moneypenny/reward!zpxx.action";


    /**
     * 猎金-根据类型获取任务
     */
    public static final String URL_TASK_GET_BY_TYPE = HOST + "/moneypenny/reward!taskbycaid.action";
    /**
     * 猎金-申请接任务
     */
    public static final String URL_TASK_APPLY = HOST + "/moneypenny/jieling!upjie.action";
    /**
     * 猎金-获取我接受的任务
     */
    public static final String URL_TASK_GET_RECEIVED = HOST + "/moneypenny/jieling!alllie.action";
    /**
     * 猎金-确认上传
     */
    public static final String URL_TASK_UPLOAD = HOST + "/moneypenny/jieling!queren.action";



    /**
     * 获取个人信息
     */
    public static final String URL_GET_PER_INFO = HOST + "/mycenter/user!showuser.action";
    /**
     * 修改个人信息
     */
    public static final String URL_MODIFY_PER_INFO = HOST + "/mycenter/user!modifyuser.action";
    /**
     * 获取公司信息
     */
    public static final String URL_GET_COM_INFO = HOST + "/mycenter/com_pany!showcompany.action";
    /**
     * 修改公司信息
     */
    public static final String URL_MODIFY_COM_INFO = HOST + "/mycenter/com_pany!modifycompany.action";
}

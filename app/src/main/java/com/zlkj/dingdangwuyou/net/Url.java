package com.zlkj.dingdangwuyou.net;

/**
 * 网络请求地址
 * Created by Botx on 2016/11/1.
 */

public class Url {

    public static final String HOST = "http://www.dingdangwuyou.com";
    //public static final String HOST = "http://192.168.3.117:8080/dingdangwuyou";


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
     * 个人-投诉
     */
    public static final String URL_USER_COMPLAIN = HOST + "/recruitmagi/user!savetousu.action";


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
     * 企业-投诉
     */
    public static final String URL_COM_COMPLAIN = HOST + "/recruitmagi/companyrecruit!savetousu.action";
    /**
     * 企业-获取个人电话——查询是否已付费
     */
    public static final String URL_COM_CONTACT_SELECT = HOST + "/mycenter/com!find.action";
    /**
     * 企业-获取个人电话——付费
     */
    public static final String URL_COM_CONTACT_PAY = HOST + "/recruitmagi/company!oo.action";
    /**
     * 企业-获取个人电话——保存付费状态
     */
    public static final String URL_COM_CONTACT_SAVE = HOST + "/mycenter/com!save.action";




    /**
     * 赏金-任务发布
     */
    public static final String URL_TASK_RELEASE = HOST + "/moneypenny/reward!taskSave.action";
    /**
     * 赏金-查询我发布的任务
     */
    public static final String URL_TASK_GET_PUBLISHED = HOST + "/moneypenny/reward!tasks.action";
    /**
     * 赏金-选择接令人
     */
    public static final String URL_TASK_CHOOSE_RECEIVER = HOST + "/moneypenny/jieling!xiugai.action";
    /**
     * 获取接令id
     */
    public static final String URL_TASK_GET_JIELING_ID = HOST + "/moneypenny/reward!zpxx.action";
    /**
     * 赏金——发放赏金
     */
    public static final String URL_TASK_GIVE_MONEY = HOST + "/moneypenny/reward!tuihui.action";
    /**
     * 赏金——确认完成
     */
    public static final String URL_TASK_CONFIRM_FINISH = HOST + "/moneypenny/reward!qrrw.action";



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
     * 猎金-任务完成中查询
     */
    public static final String URL_TASK_SELECT = HOST + "/moneypenny/reward!taskById.action";




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




    /**
     * 荣誉记录——招贤纳士——个人
     */
    public static final String URL_HONOR_HIRE_PER = HOST + "/mycenter/creditlog!getcredit_z5.action";
    /**
     * 荣誉记录——招贤纳士——公司
     */
    public static final String URL_HONOR_HIRE_COM = HOST + "/mycenter/creditlog!getcredit_m.action";
    /**
     * 荣誉记录——赏金
     */
    public static final String URL_HONOR_PUBLISH = HOST + "/mycenter/creditlog!getcredit_l.action";
    /**
     * 荣誉记录——猎金
     */
    public static final String URL_HONOR_RECEIVE = HOST + "/mycenter/creditlog!getcredit_s.action";




    /**
     * 赏金—塞红包—微信支付
     */
    public static final String URL_TASK_PAY_WECHAT = HOST + "/moneypenny/reward!app.action";
    /**
     * 账户充值—微信支付—个人
     */
    public static final String URL_RECHARGE_PAY_WECHAT_PER = HOST + "/mycenter/user!userApppay.action";
    /**
     * 账户充值—微信支付—企业
     */
    public static final String URL_RECHARGE_PAY_WECHAT_COM = HOST + "/mycenter/com_pany!companyApppay.action";


    /**
     * 赏金塞红包——微信支付结果查询
     */
    public static final String URL_TASK_PAY_WECHAT_QUERY = HOST + "/moneypenny/reward!appc.action";
    /**
     * 账户充值——微信支付结果查询——个人
     */
    public static final String URL_RECHARGE_PAY_WECHAT_QUERY_PER = HOST + "/mycenter/user!appChonguser.action";
    /**
     * 账户充值——微信支付结果查询——企业
     */
    public static final String URL_RECHARGE_PAY_WECHAT_QUERY_COM = HOST + "/mycenter/com_pany!appChongcompany.action";
}

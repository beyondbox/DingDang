package com.zlkj.dingdangwuyou.utils;

/**
 * 常量
 * Created by Botx on 2016/11/2.
 */

public class Const {

    public static final int CONNECT_TIME_OUT = 12000; //网络请求超时时间

    public static final String APP_FILE_PATH = "/dingdangwuyou";
    public static final String APP_IMAGE_PATH = APP_FILE_PATH + "/image";

    public static final String WECHAT_APP_ID = "wx03543bd79ee35fca"; //微信支付APP_ID


    public static final int USER_TYPE_COM = 0; //企业用户
    public static final int USER_TYPE_PER = 1; //个人用户
    public static final String WORK_TYPE_FULL_TIME = "0"; //全职
    public static final String WORK_TYPE_PART_TIME = "1"; //兼职
    public static final int MATCH_TYPE_HISTORY = 10; //历史匹配
    public static final int MATCH_TYPE_LATEST = 11; //最新匹配


    public static final String SP_NAME_USER = "user";


    public static final int RESULT_CODE_LOGIN_SUCCEED = 1000; //登录成功
    public static final int RESULT_CODE_RELEASE_SUCCEED = 1001; //招贤纳士——发布成功
    public static final int RESULT_CODE_APPLY_TASK_SUCCEED = 1002; //申请接任务成功
    public static final int REQUEST_CODE_CAMERA = 1003; //拍照
    public static final int REQUEST_CODE_GALLERY = 1004; //从相册选择
    public static final int RESULT_CODE_REFRESH_AVATAR = 1005; //刷新用户头像
    public static final int RESULT_CODE_SAVE_SUCCEED = 1006; //保存成功
    public static final int RESULT_CODE_RELEASE_TASK_SUCCEED = 1007; //任务——发布成功

    public static final int REQUEST_CODE_HIRE = 2000;


    public static final String KEY_USER_TYPE = "userType";
    public static final String KEY_TITLE = "title";
    public static final String KEY_OBJECT = "object";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_NEED_LOGIN = "needLogin";
    public static final String KEY_NEED_MENU = "needMenu";
    public static final String KEY_MATCH_TYPE = "matchType";
    public static final String KEY_IMAGE_PATH = "imgPath";
    public static final String KEY_USER_NAME = "username";
    public static final String KEY_IDENTITY = "identity";
    public static final String KEY_TARGET_ID = "targetId";
    public static final String KEY_BITMAP = "bitmap";
    public static final String KEY_QUALITY = "quality";
    public static final String KEY_MONEY = "money";


    public static final String ACTION_LOGIN_SUCCESS = "com.zlkj.dingdangwuyou.LOGIN_SUCCESS";
    public static final String ACTION_PAY_SUCCESS = "com.zlkj.dingdangwuyou.PAY_SUCCESS";
    public static final String ACTION_PAY_FAIL = "com.zlkj.dingdangwuyou.PAY_FAIL";


    public static final String TASK_STATUS_RELEASE = "0"; //任务状态——制作完
    public static final String TASK_STATUS_UNDERWAY = "1"; //任务状态——进行中
    public static final String TASK_STATUS_FINISH = "2"; //任务状态——完成

    public static final int JIELING_STATUS_FRESH = 3; //接令状态——新手接任务
    public static final int JIELING_STATUS_UNDERWAY = 1; //接令状态——任务进行中
    public static final int JIELING_STATUS_FINISH = 2; //接令状态——任务已完成

    public static final String TASK_TYPE_WORK = "1"; //任务类型——工作类
    public static final String TASK_TYPE_LIFE = "2"; //任务类型——生活类
    public static final String TASK_TYPE_STUDY = "3"; //任务类型——学习类
    public static final String TASK_TYPE_ALL = "0"; //任务类型——不限

    public static final int TASK_IDENTITY_PUBLISH = 0; //任务-标识-发令者
    public static final int TASK_IDENTITY_RECEIVE = 1; //任务-标识-接令者

}

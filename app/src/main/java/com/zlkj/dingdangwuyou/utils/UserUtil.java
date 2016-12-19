package com.zlkj.dingdangwuyou.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.zlkj.dingdangwuyou.entity.User;

/**
 * User相关操作封装类
 * Created by Botx on 2016/11/15.
 */

public class UserUtil {

    /**
     * 判断用户是否登录
     * @return
     */
    public static boolean isLogin() {
        User user = getUserInfo();
        if (user == null) {
            return false;
        }
        if (TextUtils.isEmpty(user.getId())) {
            return false;
        }
        return true;
    }

    /**
     * 保存用户对象
     * @param user
     */
    public static void saveUserInfo(User user) {
        Gson gson = new Gson();
        SPUtil.putString(Const.SP_NAME_USER, "user", gson.toJson(user));
    }

    /**
     * 获取保存的用户对象
     * @return
     */
    public static User getUserInfo() {
        String jsonStr = SPUtil.getString(Const.SP_NAME_USER, "user", "");
        User user = GsonUtil.getEntity(jsonStr, User.class);
        return user;
    }

    /**
     * 保存用户类型
     * @param userType
     */
    public static void saveUserType(int userType) {
        SPUtil.putInt(Const.SP_NAME_USER, Const.KEY_USER_TYPE, userType);
    }

    /**
     * 获取用户类型
     * @return
     */
    public static int getUserType() {
        return SPUtil.getInt(Const.SP_NAME_USER, Const.KEY_USER_TYPE, 0);
    }

    public static void saveUserPwd(String password) {
        SPUtil.putString(Const.SP_NAME_USER, Const.KEY_PASSWORD, password);
    }

    public static String getUserPwd() {
        return SPUtil.getString(Const.SP_NAME_USER, Const.KEY_PASSWORD, "");
    }

    /**
     * 退出登录，清空用户信息
     */
    public static void logout() {
        SPUtil.clear(Const.SP_NAME_USER);
    }

}

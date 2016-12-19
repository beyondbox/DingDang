package com.zlkj.dingdangwuyou.base;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.zlkj.dingdangwuyou.entity.User;
import com.zlkj.dingdangwuyou.utils.Const;
import com.zlkj.dingdangwuyou.utils.GsonUtil;
import com.zlkj.dingdangwuyou.utils.SPUtil;

import org.w3c.dom.Text;

/**
 * Created by Botx on 2016/10/14.
 */

public class BaseApplication extends Application {

    public static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
    }

}

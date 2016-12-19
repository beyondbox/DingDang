package com.zlkj.dingdangwuyou.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

import butterknife.ButterKnife;

/**
 * Created by Botx on 2016/10/14.
 */

public abstract class BaseActivity extends FragmentActivity {

    protected ProgressDialog pDialog;
    protected Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //强制竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(getContentViewId());
        ButterKnife.bind(this);
        context = this;

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("正在加载，请稍候....");
        pDialog.setCanceledOnTouchOutside(false);

        initData();
    }

    /**
     * 获取ContentView的ID
     * @return
     */
    protected abstract int getContentViewId();

    /**
     * 初始化数据
     */
    protected abstract void initData();

}

package com.zlkj.dingdangwuyou.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.WindowManager;

import com.zlkj.dingdangwuyou.R;
import com.zlkj.dingdangwuyou.base.BaseActivity;
import com.zlkj.dingdangwuyou.entity.User;
import com.zlkj.dingdangwuyou.utils.Const;
import com.zlkj.dingdangwuyou.utils.UserUtil;

/**
 * 启动第一屏
 * Created by Botx on 2016/10/18.
 */

public class SplashActivity extends BaseActivity {

    private CountDownTimer countTimer;

    @Override
    protected int getContentViewId() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.activity_splash;
    }

    @Override
    protected void initData() {
        startCounting();
    }

    /**
     * 开始倒计时
     */
    private void startCounting() {
        countTimer = new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                User user = UserUtil.getUserInfo();
                if (user != null) {
                    intent.putExtra(Const.KEY_NEED_LOGIN, true);
                }
                startActivity(intent);
                finish();
            }
        }.start();
    }

}

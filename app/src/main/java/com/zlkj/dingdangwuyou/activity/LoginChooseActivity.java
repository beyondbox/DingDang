package com.zlkj.dingdangwuyou.activity;

import android.content.Intent;
import android.view.View;

import com.zlkj.dingdangwuyou.R;
import com.zlkj.dingdangwuyou.base.BaseActivity;
import com.zlkj.dingdangwuyou.utils.Const;

import butterknife.OnClick;

/**
 * 登录类型选择
 * Created by Botx on 2016/11/2.
 */

public class LoginChooseActivity extends BaseActivity {

    @Override
    protected int getContentViewId() {
        return R.layout.activity_login_choose;
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.txtPersonLogin, R.id.txtCompanyLogin})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.txtCompanyLogin:
                intent = new Intent(this, LoginActivity.class);
                intent.putExtra(Const.KEY_USER_TYPE, Const.USER_TYPE_COM);
                break;
            case R.id.txtPersonLogin:
                intent = new Intent(this, LoginActivity.class);
                intent.putExtra(Const.KEY_USER_TYPE, Const.USER_TYPE_PER);
                break;
            default:
                break;
        }

        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Const.RESULT_CODE_LOGIN_SUCCEED) {
            setResult(Const.RESULT_CODE_LOGIN_SUCCEED);
            finish();
        }
    }
}

package com.zlkj.dingdangwuyou.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zlkj.dingdangwuyou.R;
import com.zlkj.dingdangwuyou.base.BaseActivity;
import com.zlkj.dingdangwuyou.base.BaseApplication;
import com.zlkj.dingdangwuyou.utils.AppTool;
import com.zlkj.dingdangwuyou.utils.Const;
import com.zlkj.dingdangwuyou.utils.SPUtil;
import com.zlkj.dingdangwuyou.utils.UserUtil;


import butterknife.BindView;
import butterknife.OnClick;

/**
 * 设置
 * Created by Botx on 2016/11/4.
 */

public class SettingActivity extends BaseActivity {
    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.imgViBack)
    ImageView imgViBack;
    @BindView(R.id.txtVersion)
    TextView txtVersion;
    @BindView(R.id.txtServiceTel)
    TextView txtServiceTel;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initData() {
        txtTitle.setText("设置");
        txtVersion.setText(AppTool.getVersion(this));
    }

    @OnClick({R.id.imgViBack, R.id.lLaytServiceTel, R.id.lLaytLogout})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.imgViBack:
                finish();
                break;
            case R.id.lLaytServiceTel:
                String phone = txtServiceTel.getText().toString().trim();
                AppTool.dial(context, phone);
                break;
            case R.id.lLaytLogout:
                if (UserUtil.isLogin()) {
                    showLogoutDialog();
                } else {
                    Toast.makeText(this, "您当前并未登录", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 退出登录对话框
     */
    private void showLogoutDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("\n确定要退出当前帐号吗？\n")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UserUtil.logout();
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("否", null)
                .show();
    }

}

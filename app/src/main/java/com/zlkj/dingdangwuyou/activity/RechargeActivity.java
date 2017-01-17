package com.zlkj.dingdangwuyou.activity;

import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zlkj.dingdangwuyou.R;
import com.zlkj.dingdangwuyou.base.BaseActivity;
import com.zlkj.dingdangwuyou.utils.AppTool;
import com.zlkj.dingdangwuyou.utils.Const;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 账户充值
 * Created by Botx on 2017/1/17.
 */

public class RechargeActivity extends BaseActivity {
    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.txtMoney)
    EditText txtMoney;

    private DecimalFormat df = new DecimalFormat("0.00");

    @Override
    protected int getContentViewId() {
        return R.layout.activity_recharge;
    }

    @Override
    protected void initData() {
        txtTitle.setText("账户充值");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txtMoney.requestFocus();
                AppTool.showSoftInput(context, txtMoney);
            }
        }, 200);

    }

    @OnClick({R.id.imgViBack, R.id.txtConfirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgViBack:
                finish();
                break;
            case R.id.txtConfirm:
                String money = txtMoney.getText().toString().trim();
                if (TextUtils.isEmpty(money)) {
                    Toast.makeText(context, "请输入充值金额", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(context, PayActivity.class);
                intent.putExtra(Const.KEY_MONEY, df.format(Double.valueOf(money)));
                intent.putExtra(Const.KEY_PAY_TYPE, Const.PAY_TYPE_RECHARGE);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }
}

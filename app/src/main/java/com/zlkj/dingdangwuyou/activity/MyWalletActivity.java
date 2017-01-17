package com.zlkj.dingdangwuyou.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zlkj.dingdangwuyou.R;
import com.zlkj.dingdangwuyou.base.BaseActivity;
import com.zlkj.dingdangwuyou.utils.Const;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的钱包
 * Created by Botx on 2016/11/28.
 */

public class MyWalletActivity extends BaseActivity {

    @BindView(R.id.txtTitle)
    TextView txtTitle;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_my_wallet;
    }

    @Override
    protected void initData() {
        txtTitle.setText("我的钱包");
        registerBroadcastReceiver();
    }

    private void registerBroadcastReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Const.ACTION_PAY_SUCCESS);
        context.registerReceiver(payReceiver, filter);
    }

    private BroadcastReceiver payReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Const.ACTION_PAY_SUCCESS)) {
                Toast.makeText(context, "充值成功", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @OnClick({R.id.imgViBack, R.id.txtRecharge})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.imgViBack:
                finish();
                break;
            case R.id.txtRecharge:
                intent = new Intent(context, RechargeActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(payReceiver);
    }
}

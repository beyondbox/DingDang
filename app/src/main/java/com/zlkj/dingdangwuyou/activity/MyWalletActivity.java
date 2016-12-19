package com.zlkj.dingdangwuyou.activity;

import android.view.View;
import android.widget.TextView;

import com.zlkj.dingdangwuyou.R;
import com.zlkj.dingdangwuyou.base.BaseActivity;

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
    }

    @OnClick({R.id.imgViBack})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgViBack:
                finish();
                break;
            default:
                break;
        }
    }
}

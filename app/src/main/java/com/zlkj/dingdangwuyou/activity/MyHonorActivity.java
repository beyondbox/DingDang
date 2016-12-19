package com.zlkj.dingdangwuyou.activity;

import android.view.View;
import android.widget.TextView;

import com.zlkj.dingdangwuyou.R;
import com.zlkj.dingdangwuyou.base.BaseActivity;
import com.zlkj.dingdangwuyou.utils.Const;
import com.zlkj.dingdangwuyou.utils.UserUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的荣誉
 * Created by Botx on 2016/11/28.
 */

public class MyHonorActivity extends BaseActivity {
    @BindView(R.id.txtTitle)
    TextView txtTitle;

    @BindView(R.id.txtHireMouth)
    TextView txtHireMouth;
    @BindView(R.id.txtHireSubject)
    TextView txtHireSubject;

    private int userType;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_my_honor;
    }

    @Override
    protected void initData() {
        userType = UserUtil.getUserType();
        if (userType == Const.USER_TYPE_PER) {
            txtTitle.setText("我的荣誉");
        } else {
            txtTitle.setText("公司荣誉");
            txtHireSubject.setText("招贤纳士-开科招募");
            txtHireMouth.setText("信息虚假");
        }
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

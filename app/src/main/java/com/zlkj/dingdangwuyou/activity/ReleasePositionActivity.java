package com.zlkj.dingdangwuyou.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.zlkj.dingdangwuyou.R;
import com.zlkj.dingdangwuyou.base.BaseActivity;
import com.zlkj.dingdangwuyou.fragment.FullTimeConditionFragment;
import com.zlkj.dingdangwuyou.fragment.FullTimeSelfCommendFragment;
import com.zlkj.dingdangwuyou.fragment.PartTimeConditionFragment;
import com.zlkj.dingdangwuyou.fragment.PartTimeSelfCommendFragment;
import com.zlkj.dingdangwuyou.utils.Const;
import com.zlkj.dingdangwuyou.utils.UserUtil;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * 发布职位 /自荐
 * Created by Botx on 2016/10/26.
 */

public class ReleasePositionActivity extends BaseActivity {

    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.txtSubject)
    TextView txtSubject;
    @BindView(R.id.rdoGroup)
    RadioGroup rdoGroup;

    private FragmentManager fm;
    private FullTimeConditionFragment fullTimeConditionFragment; //全职职位
    private PartTimeConditionFragment partTimeConditionFragment; //兼职职位
    private FullTimeSelfCommendFragment fullTimeSelfCommendFragment; //全职自荐
    private PartTimeSelfCommendFragment partTimeSelfCommendFragment; //兼职自荐

    private int userType; //用户类型

    @Override
    protected int getContentViewId() {
        return R.layout.activity_release_position;
    }

    @Override
    protected void initData() {
        userType = UserUtil.getUserType();

        if (userType == Const.USER_TYPE_PER) {
            txtTitle.setText("发布自荐");
            txtSubject.setText("毛遂自荐");
            fullTimeSelfCommendFragment = new FullTimeSelfCommendFragment();
            partTimeSelfCommendFragment = new PartTimeSelfCommendFragment();
        } else {
            txtTitle.setText("发布职位");
            txtSubject.setText("开科招募");
            fullTimeConditionFragment = new FullTimeConditionFragment();
            partTimeConditionFragment = new PartTimeConditionFragment();
        }

        fm = getSupportFragmentManager();

        rdoGroup.check(R.id.rdoBtnFullTime);
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

    @OnCheckedChanged({R.id.rdoBtnFullTime, R.id.rdoBtnPartTime})
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            switch (buttonView.getId()) {
                case R.id.rdoBtnFullTime: //全职
                    if (userType == Const.USER_TYPE_PER) {
                        fm.beginTransaction().replace(R.id.fLaytCondition, fullTimeSelfCommendFragment).commit();
                    } else {
                        fm.beginTransaction().replace(R.id.fLaytCondition, fullTimeConditionFragment).commit();
                    }
                    break;
                case R.id.rdoBtnPartTime: //兼职
                    if (userType == Const.USER_TYPE_PER) {
                        fm.beginTransaction().replace(R.id.fLaytCondition, partTimeSelfCommendFragment).commit();
                    } else {
                        fm.beginTransaction().replace(R.id.fLaytCondition, partTimeConditionFragment).commit();
                    }
                    break;
                default:
                    break;
            }
        }
    }

}

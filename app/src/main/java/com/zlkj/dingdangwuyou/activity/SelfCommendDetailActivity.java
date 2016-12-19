package com.zlkj.dingdangwuyou.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zlkj.dingdangwuyou.R;
import com.zlkj.dingdangwuyou.base.BaseActivity;
import com.zlkj.dingdangwuyou.entity.SelfCommend;
import com.zlkj.dingdangwuyou.utils.AppTool;
import com.zlkj.dingdangwuyou.utils.Const;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 自荐详情
 * Created by Botx on 2016/11/7.
 */

public class SelfCommendDetailActivity extends BaseActivity {
    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.imgViBack)
    ImageView imgViBack;
    @BindView(R.id.lLaytBottomBar)
    LinearLayout lLaytBottomBar;
    @BindView(R.id.lLaytPhone)
    LinearLayout lLaytPhone;

    /**
     * 共有属性
     */
    @BindView(R.id.txtName)
    TextView txtName;
    @BindView(R.id.txtPhone)
    TextView txtPhone;
    @BindView(R.id.txtSex)
    TextView txtSex;
    @BindView(R.id.txtTargetPosition)
    TextView txtTargetPosition;
    @BindView(R.id.txtArea)
    TextView txtArea;
    @BindView(R.id.txtSalary)
    TextView txtSalary;

    /**
     * 全职属性
     */
    TextView txtAge;
    TextView txtExperience;
    TextView txtEducation;
    TextView txtSpecialty;
    TextView txtCertificate;

    /**
     * 兼职属性
     */
    TextView txtCount;
    TextView txtWorkTime;
    TextView txtEnvironment;
    TextView txtSituation;
    TextView txtPayTime;

    private Intent comeIntent;
    private SelfCommend selfCommend;


    @Override
    protected int getContentViewId() {
        comeIntent = getIntent();
        selfCommend = (SelfCommend) comeIntent.getSerializableExtra(Const.KEY_OBJECT);
        if (selfCommend.getSzjtate().equals(Const.WORK_TYPE_PART_TIME)) {
            return R.layout.activity_commend_parttime_detail;
        } else {
            return R.layout.activity_commend_fulltime_detail;
        }
    }

    @Override
    protected void initData() {
        if (selfCommend.getSzjtate().equals(Const.WORK_TYPE_PART_TIME)) {
            txtTitle.setText("兼职自荐详情");
        } else {
            txtTitle.setText("全职自荐详情");
        }

        if (!comeIntent.getBooleanExtra(Const.KEY_NEED_MENU, true)) {
            lLaytBottomBar.setVisibility(View.GONE);
        }

        initView();
        setData();
    }

    private void initView() {
        if (selfCommend.getSzjtate().equals(Const.WORK_TYPE_PART_TIME)) {
            txtCount = (TextView) findViewById(R.id.txtCount);
            txtWorkTime = (TextView) findViewById(R.id.txtWorkTime);
            txtEnvironment = (TextView) findViewById(R.id.txtEnvironment);
            txtSituation = (TextView) findViewById(R.id.txtSituation);
            txtPayTime = (TextView) findViewById(R.id.txtPayTime);
        } else {
            txtAge = (TextView) findViewById(R.id.txtAge);
            txtExperience = (TextView) findViewById(R.id.txtExperience);
            txtEducation = (TextView) findViewById(R.id.txtEducation);
            txtSpecialty = (TextView) findViewById(R.id.txtSpecialty);
            txtCertificate = (TextView) findViewById(R.id.txtCertificate);
        }
    }

    /**
     * 渲染数据
     */
    private void setData() {
        txtName.setText(selfCommend.getName());
        txtPhone.setText(selfCommend.getTelphone());
        txtSex.setText(selfCommend.getSex());
        txtTargetPosition.setText(selfCommend.getLx_position());
        txtArea.setText(selfCommend.getArea());
        txtSalary.setText(selfCommend.getSalary());

        if (selfCommend.getSzjtate().equals(Const.WORK_TYPE_PART_TIME)) {
            txtCount.setText(selfCommend.getNumber());
            txtWorkTime.setText(selfCommend.getJobtime());
            txtEnvironment.setText(selfCommend.getJobhuanjing());
            txtSituation.setText(selfCommend.getXianzhuang());
            txtPayTime.setText(selfCommend.getSalarytime());
        } else {
            txtAge.setText(selfCommend.getAge());
            txtExperience.setText(selfCommend.getJobexperience());
            txtEducation.setText(selfCommend.getXueli());
            txtSpecialty.setText(selfCommend.getMajor());
            txtCertificate.setText(selfCommend.getCyzgzs());
        }
    }

    @OnClick({R.id.imgViBack, R.id.lLaytPhone})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgViBack:
                finish();
                break;
            case R.id.lLaytPhone:
                String phone = txtPhone.getText().toString().trim();
                if (!TextUtils.isEmpty(phone)) {
                    AppTool.dial(context, phone);
                }
                break;
            default:
                break;
        }
    }
}

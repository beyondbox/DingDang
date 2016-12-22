package com.zlkj.dingdangwuyou.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zlkj.dingdangwuyou.R;
import com.zlkj.dingdangwuyou.base.BaseActivity;
import com.zlkj.dingdangwuyou.entity.HireInfo;
import com.zlkj.dingdangwuyou.utils.AppTool;
import com.zlkj.dingdangwuyou.utils.Const;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 职位详情
 * Created by btx on 2016/11/13.
 */

public class PositionDetailActivity extends BaseActivity {

    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.imgViBack)
    ImageView imgViBack;
    @BindView(R.id.lLaytBottomBar)
    LinearLayout lLaytBottomBar;

    /**
     * 共有属性
     */
    @BindView(R.id.txtNameCom)
    TextView txtNameCom;
    @BindView(R.id.txtSalary)
    TextView txtSalary;
    @BindView(R.id.txtPosition)
    TextView txtPosition;
    @BindView(R.id.txtDate)
    TextView txtDate;
    @BindView(R.id.txtPhone)
    TextView txtPhone;
    @BindView(R.id.txtArea)
    TextView txtArea;
    @BindView(R.id.txtSex)
    TextView txtSex;
    @BindView(R.id.txtPrefer)
    TextView txtPrefer;
    @BindView(R.id.txtCount)
    TextView txtCount;
    @BindView(R.id.txtEnvironment)
    TextView txtEnvironment;
    @BindView(R.id.txtExperience)
    TextView txtExperience;

    /**
     * 全职属性
     */
    TextView txtEducation;
    TextView txtSpecialty;
    TextView txtCertificate;
    TextView txtAge;
    TextView txtWorkTime;

    /**
     * 兼职属性
     */
    TextView txtSituation;
    TextView txtPayTime;

    private Intent comeIntent;
    private HireInfo hireInfo;


    @Override
    protected int getContentViewId() {
        comeIntent = getIntent();
        hireInfo = (HireInfo) comeIntent.getSerializableExtra(Const.KEY_OBJECT);
        if (hireInfo.getSzjtate().equals(Const.WORK_TYPE_PART_TIME)) {
            return R.layout.activity_position_parttime_detail;
        } else {
            return R.layout.activity_position_fulltime_detail;
        }
    }

    @Override
    protected void initData() {
        if (hireInfo.getSzjtate().equals(Const.WORK_TYPE_PART_TIME)) {
            txtTitle.setText("兼职招聘详情");
        } else {
            txtTitle.setText("全职招聘详情");
        }

        if (!comeIntent.getBooleanExtra(Const.KEY_NEED_MENU, true)) {
            lLaytBottomBar.setVisibility(View.GONE);
        }

        initView();
        setData();
    }

    private void initView() {
        if (hireInfo.getSzjtate().equals(Const.WORK_TYPE_PART_TIME)) {
            txtSituation = (TextView) findViewById(R.id.txtSituation);
            txtPayTime = (TextView) findViewById(R.id.txtPayTime);
        } else {
            txtAge = (TextView) findViewById(R.id.txtAge);
            txtEducation = (TextView) findViewById(R.id.txtEducation);
            txtSpecialty = (TextView) findViewById(R.id.txtSpecialty);
            txtCertificate = (TextView) findViewById(R.id.txtCertificate);
            txtWorkTime = (TextView) findViewById(R.id.txtWorkTime);
        }
    }


    /**
     * 渲染数据
     */
    private void setData() {
        txtNameCom.setText(hireInfo.getCompany());
        txtPosition.setText(hireInfo.getPosition());
        txtDate.setText(hireInfo.getPublishtime());
        txtPhone.setText(hireInfo.getContactnumber());
        txtArea.setText(hireInfo.getArea());
        txtSex.setText(hireInfo.getSex());
        txtPrefer.setText(hireInfo.getYouxian());
        txtCount.setText(hireInfo.getNumber());
        txtEnvironment.setText(hireInfo.getJobhuanjing());
        txtExperience.setText(hireInfo.getJobexperience());
        if (TextUtils.isEmpty(hireInfo.getSalary())) {
            txtSalary.setText("面议");
        } else {
            txtSalary.setText(hireInfo.getSalary());
        }

        if (hireInfo.getSzjtate().equals(Const.WORK_TYPE_PART_TIME)) {
            txtSituation.setText(hireInfo.getXianzhuang());
            txtPayTime.setText(hireInfo.getSalarytime());
        } else {
            txtEducation.setText(hireInfo.getXueli());
            txtSpecialty.setText(hireInfo.getMajor());
            txtCertificate.setText(hireInfo.getCyzgzs());
            txtAge.setText(hireInfo.getAge());
            txtWorkTime.setText(hireInfo.getJobtime());
        }
    }

    @OnClick({R.id.imgViBack, R.id.txtPhone, R.id.lLaytShowDetail, R.id.txtComplain})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.imgViBack:
                finish();
                break;
            case R.id.txtPhone:
                String phone = txtPhone.getText().toString().trim();
                if (!TextUtils.isEmpty(phone)) {
                    AppTool.dial(context, phone);
                }
                break;
            case R.id.lLaytShowDetail:
                if (TextUtils.isEmpty(hireInfo.getCpif_id())) {
                    return;
                }
                intent = new Intent(context, CompanyDetailActivity.class);
                intent.putExtra("id", hireInfo.getCpif_id());
                startActivity(intent);
                break;
            case R.id.txtComplain:
                intent = new Intent(context, ComplainHireActivity.class);
                intent.putExtra(Const.KEY_TARGET_ID, hireInfo.getCpif_id());
                startActivity(intent);
                break;
            default:
                break;
        }
    }

}

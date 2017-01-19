package com.zlkj.dingdangwuyou.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zlkj.dingdangwuyou.R;
import com.zlkj.dingdangwuyou.base.BaseActivity;
import com.zlkj.dingdangwuyou.entity.SelfCommend;
import com.zlkj.dingdangwuyou.net.MyHttpClient;
import com.zlkj.dingdangwuyou.net.Url;
import com.zlkj.dingdangwuyou.utils.AppTool;
import com.zlkj.dingdangwuyou.utils.Const;
import com.zlkj.dingdangwuyou.utils.UserUtil;

import org.apache.http.Header;

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
    @BindView(R.id.txtGetPhone)
    TextView txtGetPhone;

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

        if (lLaytBottomBar.getVisibility() == View.VISIBLE) {
            contactSelect();
        } else {
            lLaytPhone.setVisibility(View.VISIBLE);
        }
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

    /**
     * 获取个人电话——查询是否已付费
     */
    private void contactSelect() {
        RequestParams params = new RequestParams();
        params.put("com", UserUtil.getUserInfo().getId());
        params.put("us", selfCommend.getUi_id());

        MyHttpClient.getInstance().post(Url.URL_COM_CONTACT_SELECT, params, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                pDialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String str = new String(responseBody);
                if (str.equals("true")) {
                    lLaytPhone.setVisibility(View.VISIBLE);
                    txtGetPhone.setText("已获取联系方式");
                    txtGetPhone.setEnabled(false);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(context, "获取联系方式超时，请稍候", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                pDialog.dismiss();
            }
        });
    }

    /**
     * 获取个人电话——付费
     */
    private void contactPay() {
        RequestParams params = new RequestParams();
        params.put("com", UserUtil.getUserInfo().getId());
        params.put("us", selfCommend.getUi_id());

        MyHttpClient.getInstance().post(Url.URL_COM_CONTACT_PAY, params, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                pDialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String str = new String(responseBody);
                if (str.equals("true")) {
                    lLaytPhone.setVisibility(View.VISIBLE);
                    txtGetPhone.setText("已获取联系方式");
                    txtGetPhone.setEnabled(false);
                    contactSave();
                } else if (str.equals("false")) {
                    Toast.makeText(context, "余额不足，请充值", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(context, getResources().getString(R.string.request_fail), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                pDialog.dismiss();
            }
        });
    }

    /**
     * 获取个人电话——保存付费状态
     */
    private void contactSave() {
        RequestParams params = new RequestParams();
        params.put("com", UserUtil.getUserInfo().getId());
        params.put("us", selfCommend.getUi_id());

        MyHttpClient.getInstance().post(Url.URL_COM_CONTACT_SAVE, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            }
        });
    }


    @OnClick({R.id.imgViBack, R.id.lLaytPhone, R.id.txtComplain, R.id.txtGetPhone})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.imgViBack: //返回
                finish();
                break;
            case R.id.lLaytPhone: //拨打电话
                String phone = txtPhone.getText().toString().trim();
                if (!TextUtils.isEmpty(phone)) {
                    AppTool.dial(context, phone);
                }
                break;
            case R.id.txtComplain: //投诉
                intent = new Intent(context, ComplainHireActivity.class);
                intent.putExtra(Const.KEY_TARGET_ID, selfCommend.getUi_id());
                startActivity(intent);
                break;
            case R.id.txtGetPhone: //付费获取联系方式
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("\n首次查看对方联系方式需付费0.5元，该费用将从您的账户余额中扣除，是否继续？\n")
                        .setNegativeButton("否", null)
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                contactPay();
                            }
                        })
                        .show();
                break;
            default:
                break;
        }
    }
}

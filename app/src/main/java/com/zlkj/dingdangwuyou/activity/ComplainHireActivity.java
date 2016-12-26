package com.zlkj.dingdangwuyou.activity;

import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zlkj.dingdangwuyou.R;
import com.zlkj.dingdangwuyou.base.BaseActivity;
import com.zlkj.dingdangwuyou.net.MyHttpClient;
import com.zlkj.dingdangwuyou.net.Url;
import com.zlkj.dingdangwuyou.utils.Const;
import com.zlkj.dingdangwuyou.utils.UserUtil;

import org.apache.http.Header;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 投诉——招贤纳士
 * Created by Botx on 2016/12/22.
 */

public class ComplainHireActivity extends BaseActivity {
    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.rdoGroup)
    RadioGroup rdoGroup;
    @BindView(R.id.rdoBtn1)
    RadioButton rdoBtn1;
    @BindView(R.id.rdoBtn2)
    RadioButton rdoBtn2;
    @BindView(R.id.rdoBtn3)
    RadioButton rdoBtn3;
    @BindView(R.id.rdoBtn4)
    RadioButton rdoBtn4;

    private String targetId; //被投诉方的id
    private String content; //投诉内容

    @Override
    protected int getContentViewId() {
        return R.layout.activity_complain_hire;
    }

    @Override
    protected void initData() {
        txtTitle.setText("投诉衙门");
        targetId = getIntent().getStringExtra(Const.KEY_TARGET_ID);

        if (UserUtil.getUserType() == Const.USER_TYPE_COM) {
            rdoBtn3.setText("不告而别");
        }

        rdoGroup.check(R.id.rdoBtn1);
    }

    /**
     * 投诉
     */
    private void complain() {
        switch (rdoGroup.getCheckedRadioButtonId()) {
            case R.id.rdoBtn1:
                content = rdoBtn1.getText().toString().trim();
                break;
            case R.id.rdoBtn2:
                content = rdoBtn2.getText().toString().trim();
                break;
            case R.id.rdoBtn3:
                content = rdoBtn3.getText().toString().trim();
                break;
            case R.id.rdoBtn4:
                content = rdoBtn4.getText().toString().trim();
                break;
            default:
                break;
        }

        RequestParams params = new RequestParams();
        String url = "";
        if (UserUtil.getUserType() == Const.USER_TYPE_PER) {
            url = Url.URL_USER_COMPLAIN;
            params.put("m_cpid", targetId);
            params.put("m_userid", UserUtil.getUserInfo().getId());
            params.put("m_jilu", content);
        } else {
            url = Url.URL_COM_COMPLAIN;
            params.put("z_userid", targetId);
            params.put("z_cpid", UserUtil.getUserInfo().getId());
            params.put("z_jilu", content);
        }

        MyHttpClient.getInstance().post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                pDialog.setMessage("正在提交，请稍候....");
                pDialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Toast.makeText(context, "投诉成功", Toast.LENGTH_SHORT).show();
                finish();
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

    @OnClick({R.id.imgViBack, R.id.txtConfirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgViBack:
                finish();
                break;
            case R.id.txtConfirm:
                complain();
                break;
            default:
                break;
        }
    }

}

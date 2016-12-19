package com.zlkj.dingdangwuyou.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zlkj.dingdangwuyou.R;
import com.zlkj.dingdangwuyou.base.BaseActivity;
import com.zlkj.dingdangwuyou.entity.User;
import com.zlkj.dingdangwuyou.net.MyHttpClient;
import com.zlkj.dingdangwuyou.net.Url;
import com.zlkj.dingdangwuyou.utils.Const;
import com.zlkj.dingdangwuyou.utils.GsonUtil;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 企业详情
 * Created by btx on 2016/11/21.
 */

public class CompanyDetailActivity extends BaseActivity {

    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.txtNameCom)
    TextView txtNameCom;
    @BindView(R.id.txtLegalPerson)
    TextView txtLegalPerson;
    @BindView(R.id.txtCompanyAdd)
    TextView txtCompanyAdd;
    @BindView(R.id.txtRegisterAdd)
    TextView txtRegisterAdd;
    @BindView(R.id.txtType)
    TextView txtType;
    @BindView(R.id.txtIndustry)
    TextView txtIndustry;
    @BindView(R.id.txtIntro)
    TextView txtIntro;

    @BindView(R.id.lLaytLicense)
    LinearLayout lLaytLicense;
    @BindView(R.id.lLaytLegalPerson)
    LinearLayout lLaytLegalPerson;
    @BindView(R.id.lLaytLegalPersonID)
    LinearLayout lLaytLegalPersonID;

    @BindView(R.id.imgViLicense)
    ImageView imgViLicense;
    @BindView(R.id.imgViLegalPerson)
    ImageView imgViLegalPerson;
    @BindView(R.id.imgViLegalPersonID)
    ImageView imgViLegalPersonID;

    private String id = "";
    private User user;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_company_detail;
    }

    @Override
    protected void initData() {
        txtTitle.setText("企业详情");
        id = getIntent().getStringExtra("id");

        getCompanyInfo();
    }

    /**
     * 获取企业详情
     */
    private void getCompanyInfo() {
        RequestParams params = new RequestParams();
        params.put("id", id);
        MyHttpClient.getInstance().post(Url.URL_GET_COM_INFO, params, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                pDialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String jsonStr = new String(responseBody);
                try {
                    JSONArray jsonArr = new JSONArray(jsonStr);
                    if (jsonArr.length() > 0) {
                        user = GsonUtil.getEntity(jsonArr.getJSONObject(0).toString(), User.class);
                        setData();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
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
     * 渲染数据
     */
    private void setData() {
        txtNameCom.setText(user.getCpname());
        txtLegalPerson.setText(user.getCpfaren());
        txtCompanyAdd.setText(user.getCpadress());
        txtRegisterAdd.setText(user.getRegistadress());
        txtType.setText(user.getCptype());
        txtIndustry.setText(user.getIndustry());
        txtIntro.setText(user.getCpcontent());

        //营业执照
        if (!TextUtils.isEmpty(user.getImg_yyzz())) {
            lLaytLicense.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(Url.HOST + user.getImg_yyzz())
                    .placeholder(R.mipmap.image_loading)
                    .error(R.mipmap.image_error)
                    .into(imgViLicense);
        }

        //法人近期照片
        if (!TextUtils.isEmpty(user.getImg_frjqzp())) {
            lLaytLegalPerson.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(Url.HOST + user.getImg_frjqzp())
                    .placeholder(R.mipmap.image_loading)
                    .error(R.mipmap.image_error)
                    .into(imgViLegalPerson);
        }

        //法人身份证扫描件
        if (!TextUtils.isEmpty(user.getImg_frsfz())) {
            lLaytLegalPersonID.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(Url.HOST + user.getImg_frsfz())
                    .placeholder(R.mipmap.image_loading)
                    .error(R.mipmap.image_error)
                    .into(imgViLegalPersonID);
        }
    }

    @OnClick({R.id.imgViBack, R.id.imgViLicense, R.id.imgViLegalPerson, R.id.imgViLegalPersonID})
    public void onClick(View view) {
        Intent intent = new Intent(context, ImageBrowseActivity.class);
        switch (view.getId()) {
            case R.id.imgViBack:
                finish();
                break;
            case R.id.imgViLicense:
                if (user != null) {
                    intent.putExtra(Const.KEY_IMAGE_PATH, Url.HOST + user.getImg_yyzz());
                    startActivity(intent);
                }
                break;
            case R.id.imgViLegalPerson:
                if (user != null) {
                    intent.putExtra(Const.KEY_IMAGE_PATH, Url.HOST + user.getImg_frjqzp());
                    startActivity(intent);
                }
                break;
            case R.id.imgViLegalPersonID:
                if (user != null) {
                    intent.putExtra(Const.KEY_IMAGE_PATH, Url.HOST + user.getImg_frsfz());
                    startActivity(intent);
                }
                break;
            default:
                break;
        }
    }

}

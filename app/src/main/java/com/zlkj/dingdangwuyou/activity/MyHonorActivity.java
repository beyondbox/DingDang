package com.zlkj.dingdangwuyou.activity;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zlkj.dingdangwuyou.R;
import com.zlkj.dingdangwuyou.base.BaseActivity;
import com.zlkj.dingdangwuyou.entity.Complaint;
import com.zlkj.dingdangwuyou.net.MyHttpClient;
import com.zlkj.dingdangwuyou.net.Url;
import com.zlkj.dingdangwuyou.utils.Const;
import com.zlkj.dingdangwuyou.utils.GsonUtil;
import com.zlkj.dingdangwuyou.utils.UserUtil;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

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
    private String userId = "";

    private List<Complaint> hireList = new ArrayList<Complaint>(); //招贤纳士投诉记录

    @Override
    protected int getContentViewId() {
        return R.layout.activity_my_honor;
    }

    @Override
    protected void initData() {
        userType = UserUtil.getUserType();
        userId = UserUtil.getUserInfo().getId();
        if (userType == Const.USER_TYPE_PER) {
            txtTitle.setText("我的荣誉");
        } else {
            txtTitle.setText("公司荣誉");
            txtHireSubject.setText("招贤纳士-开科招募");
            txtHireMouth.setText("信息虚假");
        }
    }

    /**
     * 获取招贤纳士荣誉信息
     */
    private void getHireHonor() {
        String url = "";
        RequestParams params = new RequestParams();
        if (userType == Const.USER_TYPE_PER) {
            url = Url.URL_HONOR_HIRE_PER;
            params.put("z_userid", userId);
        } else {
            url = Url.URL_HONOR_HIRE_COM;
            params.put("m_cpid", userId);
        }

        MyHttpClient.getInstance().post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String jsonStr = new String(responseBody);
                try {
                    JSONArray jsonArr = new JSONArray(jsonStr);
                    if (jsonArr.length() > 0) {
                        hireList = GsonUtil.getEntityList(jsonArr.toString(), Complaint.class);

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
     * 解析招贤纳士荣誉信息
     */
    private void parseHireHonor() {
        int hire1 = 0;
        int hire2 = 0;
        int hire3 = 0;
        int hire4 = 0;

        for (Complaint complaint : hireList) {
            if (complaint.getZ_jilu().equals("不守时")) {
                hire1++;
            } else if (complaint.getZ_jilu().equals("不守约")) {
                hire2++;
            } else if (complaint.getZ_jilu().equals("信息虚假")) {
                hire3++;
            } else if (complaint.getZ_jilu().equals("不告而别")) {
                hire3++;
            } else if (complaint.getZ_jilu().equals("信息过期")) {
                hire4++;
            }
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

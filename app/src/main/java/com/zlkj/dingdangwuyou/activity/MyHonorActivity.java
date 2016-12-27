package com.zlkj.dingdangwuyou.activity;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zlkj.dingdangwuyou.R;
import com.zlkj.dingdangwuyou.base.BaseActivity;
import com.zlkj.dingdangwuyou.entity.ComplaintHireCom;
import com.zlkj.dingdangwuyou.entity.ComplaintHirePer;
import com.zlkj.dingdangwuyou.entity.ComplaintPublish;
import com.zlkj.dingdangwuyou.entity.ComplaintReceive;
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

    @BindView(R.id.txtHire1)
    TextView txtHire1;
    @BindView(R.id.txtHire2)
    TextView txtHire2;
    @BindView(R.id.txtHire3)
    TextView txtHire3;
    @BindView(R.id.txtHire4)
    TextView txtHire4;
    @BindView(R.id.txtPublish1)
    TextView txtPublish1;
    @BindView(R.id.txtPublish2)
    TextView txtPublish2;
    @BindView(R.id.txtReceive1)
    TextView txtReceive1;
    @BindView(R.id.txtReceive2)
    TextView txtReceive2;
    @BindView(R.id.txtReceive3)
    TextView txtReceive3;
    @BindView(R.id.txtReceive4)
    TextView txtReceive4;
    @BindView(R.id.txtReceive5)
    TextView txtReceive5;

    private int userType;
    private String userId = "";
    private int threadCount = 3;

    private List<ComplaintHirePer> hireListPer = new ArrayList<ComplaintHirePer>(); //招贤纳士被投诉记录(个人)
    private List<ComplaintHireCom> hireListCom = new ArrayList<ComplaintHireCom>(); //招贤纳士被投诉记录(公司)
    private List<ComplaintPublish> publishList = new ArrayList<ComplaintPublish>(); //赏金被投诉记录
    private List<ComplaintReceive> receiveList = new ArrayList<ComplaintReceive>(); //猎金被投诉记录

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


        pDialog.show();
        getHireHonor();
        getPublishHonor();
        getReceiveHonor();
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
                        if (userType == Const.USER_TYPE_PER) {
                            hireListPer = GsonUtil.getEntityList(jsonArr.toString(), ComplaintHirePer.class);
                        } else {
                            hireListCom = GsonUtil.getEntityList(jsonArr.toString(), ComplaintHireCom.class);
                        }

                        parseHireHonor();
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
                threadCount--;
                if (threadCount == 0) {
                    pDialog.dismiss();
                }
            }
        });
    }

    /**
     * 获取赏金荣誉信息
     */
    private void getPublishHonor() {
        RequestParams params = new RequestParams();
        params.put("l_cpid", userId);

        MyHttpClient.getInstance().post(Url.URL_HONOR_PUBLISH, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String jsonStr = new String(responseBody);
                try {
                    JSONArray jsonArr = new JSONArray(jsonStr);
                    if (jsonArr.length() > 0) {
                        publishList = GsonUtil.getEntityList(jsonArr.toString(), ComplaintPublish.class);
                        parsePublishHonor();
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
                threadCount--;
                if (threadCount == 0) {
                    pDialog.dismiss();
                }
            }
        });
    }

    /**
     * 获取猎金荣誉信息
     */
    private void getReceiveHonor() {
        RequestParams params = new RequestParams();
        params.put("s_cpid", userId);

        MyHttpClient.getInstance().post(Url.URL_HONOR_RECEIVE, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String jsonStr = new String(responseBody);
                try {
                    JSONArray jsonArr = new JSONArray(jsonStr);
                    if (jsonArr.length() > 0) {
                        receiveList = GsonUtil.getEntityList(jsonArr.toString(), ComplaintReceive.class);
                        parseReceiveHonor();
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
                threadCount--;
                if (threadCount == 0) {
                    pDialog.dismiss();
                }
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

        if (userType == Const.USER_TYPE_PER) {
            for (ComplaintHirePer complaint : hireListPer) {
                if (complaint.getZ_jilu().equals("不守时")) {
                    hire1++;
                } else if (complaint.getZ_jilu().equals("不守约")) {
                    hire2++;
                } else if (complaint.getZ_jilu().equals("不告而别")) {
                    hire3++;
                } else if (complaint.getZ_jilu().equals("信息过期")) {
                    hire4++;
                }
            }
        } else {
            for (ComplaintHireCom complaint : hireListCom) {
                if (complaint.getM_jilu().equals("不守时")) {
                    hire1++;
                } else if (complaint.getM_jilu().equals("不守约")) {
                    hire2++;
                } else if (complaint.getM_jilu().equals("信息虚假")) {
                    hire3++;
                } else if (complaint.getM_jilu().equals("信息过期")) {
                    hire4++;
                }
            }
        }


        txtHire1.setText(hire1 + "次");
        txtHire2.setText(hire2 + "次");
        txtHire3.setText(hire3 + "次");
        txtHire4.setText(hire4 + "次");
    }

    /**
     * 解析赏金荣誉信息
     */
    private void parsePublishHonor() {
        int publish1 = 0;
        int publish2 = 0;

        for (ComplaintPublish complaint : publishList) {
            if (complaint.getL_jilu().equals("奖金发放不及时")) {
                publish1++;
            } else if (complaint.getL_jilu().equals("完成任务未获得奖金")) {
                publish2++;
            }
        }


        txtPublish1.setText(publish1 + "次");
        txtPublish2.setText(publish2 + "次");
    }

    /**
     * 解析猎金荣誉信息
     */
    private void parseReceiveHonor() {
        int receive1 = 0;
        int receive2 = 0;
        int receive3 = 0;
        int receive4 = 0;
        int receive5 = 0;

        for (ComplaintReceive complaint : receiveList) {
            if (complaint.getS_jilu().equals("有始无终")) {
                receive1++;
            } else if (complaint.getS_jilu().equals("完成超时")) {
                receive2++;
            } else if (complaint.getS_jilu().equals("信息虚假")) {
                receive3++;
            } else if (complaint.getS_jilu().equals("信息过期")) {
                receive4++;
            } else if (complaint.getS_jilu().equals("领取赏金未完成任务")) {
                receive5++;
            }
        }


        txtReceive1.setText(receive1 + "次");
        txtReceive2.setText(receive2 + "次");
        txtReceive3.setText(receive3 + "次");
        txtReceive4.setText(receive4 + "次");
        txtReceive5.setText(receive5 + "次");
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

package com.zlkj.dingdangwuyou.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zlkj.dingdangwuyou.R;
import com.zlkj.dingdangwuyou.base.BaseActivity;
import com.zlkj.dingdangwuyou.entity.User;
import com.zlkj.dingdangwuyou.net.MyHttpClient;
import com.zlkj.dingdangwuyou.net.Url;
import com.zlkj.dingdangwuyou.utils.Const;
import com.zlkj.dingdangwuyou.utils.GsonUtil;
import com.zlkj.dingdangwuyou.utils.LogHelper;
import com.zlkj.dingdangwuyou.utils.UserUtil;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的钱包
 * Created by Botx on 2016/11/28.
 */

public class MyWalletActivity extends BaseActivity {

    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.txtBalance)
    TextView txtBalance;

    private DecimalFormat df = new DecimalFormat("0.00");


    @Override
    protected int getContentViewId() {
        return R.layout.activity_my_wallet;
    }

    @Override
    protected void initData() {
        txtTitle.setText("我的钱包");
        registerBroadcastReceiver();

        getBalance();
    }

    private void registerBroadcastReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Const.ACTION_PAY_SUCCESS);
        context.registerReceiver(payReceiver, filter);
    }

    private BroadcastReceiver payReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Const.ACTION_PAY_SUCCESS)) {
                Toast.makeText(context, "充值成功", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getBalance();
                    }
                }, 1000);
            }
        }
    };

    /**
     * 获取账户余额
     */
    private void getBalance() {
        RequestParams params = new RequestParams();
        params.put("id", UserUtil.getUserInfo().getId());

        String url = "";
        if (UserUtil.getUserType() == Const.USER_TYPE_PER) {
            url = Url.URL_GET_PER_INFO;
        } else {
            url = Url.URL_GET_COM_INFO;
        }

        MyHttpClient.getInstance().post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                pDialog.setMessage("正在获取账户余额....");
                pDialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String jsonStr = new String(responseBody);
                try {
                    JSONArray jsonArr = new JSONArray(jsonStr);
                    if (jsonArr.length() > 0) {
                        JSONObject jsonObj = jsonArr.getJSONObject(0);
                        User user = GsonUtil.getEntity(jsonObj.toString(), User.class);

                        double balance = 0.00;
                        if (UserUtil.getUserType() == Const.USER_TYPE_PER) {
                            balance = user.getBalance();
                        } else {
                            balance = user.getCbalance();
                        }
                        LogHelper.e("Balance", balance + "");
                        txtBalance.setText("¥ " + df.format(balance));
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

    @OnClick({R.id.imgViBack, R.id.txtRecharge})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.imgViBack:
                finish();
                break;
            case R.id.txtRecharge:
                intent = new Intent(context, RechargeActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(payReceiver);
    }
}

package com.zlkj.dingdangwuyou.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.zlkj.dingdangwuyou.R;
import com.zlkj.dingdangwuyou.base.BaseActivity;
import com.zlkj.dingdangwuyou.net.MyHttpClient;
import com.zlkj.dingdangwuyou.net.Url;
import com.zlkj.dingdangwuyou.utils.Const;
import com.zlkj.dingdangwuyou.utils.UserUtil;
import com.zlkj.dingdangwuyou.wxapi.WXPayEntryActivity;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 支付界面
 * Created by Botx on 2017/1/6.
 */

public class PayActivity extends BaseActivity {
    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.txtMoney)
    TextView txtMoney;

    private final IWXAPI msgApi = WXAPIFactory.createWXAPI(this, null);
    private String money; //支付金额
    private int payType; //支付类型

    @Override
    protected int getContentViewId() {
        return R.layout.activity_pay;
    }

    @Override
    protected void initData() {
        txtTitle.setText("支付");
        msgApi.registerApp(Const.WECHAT_APP_ID);

        Intent intent = getIntent();
        money = intent.getStringExtra(Const.KEY_MONEY);
        payType = intent.getIntExtra(Const.KEY_PAY_TYPE, Const.PAY_TYPE_TASK);
        WXPayEntryActivity.PAY_TYPE = payType;

        txtMoney.setText("¥" + money + " 元");
        pDialog.setCancelable(false);
        registerBroadcastReceiver();
    }

    /**
     * 获取微信支付订单信息——任务塞红包
     */
    private void getWeChatOrderTask() {
        RequestParams params = new RequestParams();
        params.put("body", "叮当无忧");
        params.put("total_fee", money);
        params.put("id", UserUtil.getUserInfo().getId());

        MyHttpClient.getInstance().post(Url.URL_TASK_PAY_WECHAT, params, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                pDialog.setMessage("正在提交，请稍候....");
                pDialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String jsonStr = new String(responseBody);
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    String result = jsonObj.getString("result");
                    if (result.equals("true")) {
                        JSONObject data = jsonObj.getJSONObject("data");
                        PayReq request = new PayReq();
                        request.appId = data.getString("appid");
                        request.partnerId = data.getString("partnerid");
                        request.prepayId= data.getString("prepayid");
                        request.packageValue = data.getString("package");
                        request.nonceStr= data.getString("noncestr");
                        request.timeStamp= data.getString("timestamp");
                        request.sign= data.getString("sign");
                        msgApi.sendReq(request);
                    } else {
                        Toast.makeText(context, "请求信息失败", Toast.LENGTH_SHORT).show();
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
     * 获取微信支付订单信息——充值
     */
    private void getWeChatOrderRecharge() {
        RequestParams params = new RequestParams();
        params.put("body", "叮当无忧");
        params.put("total_fee", money);
        params.put("request", "request");
        params.put("id", UserUtil.getUserInfo().getId());

        String url = "";
        if (UserUtil.getUserType() == Const.USER_TYPE_PER) {
            url = Url.URL_RECHARGE_PAY_WECHAT_PER;
        } else {
            url = Url.URL_RECHARGE_PAY_WECHAT_COM;
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
                String jsonStr = new String(responseBody);
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    String result = jsonObj.getString("result");
                    if (result.equals("true")) {
                        JSONObject data = jsonObj.getJSONObject("data");
                        PayReq request = new PayReq();
                        request.appId = data.getString("appid");
                        request.partnerId = data.getString("partnerid");
                        request.prepayId= data.getString("prepayid");
                        request.packageValue = data.getString("package");
                        request.nonceStr= data.getString("noncestr");
                        request.timeStamp= data.getString("timestamp");
                        request.sign= data.getString("sign");
                        msgApi.sendReq(request);
                    } else {
                        Toast.makeText(context, "请求信息失败", Toast.LENGTH_SHORT).show();
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
     * 检测微信是否安装
     *
     * @param api
     * @return
     */
    private boolean isWXAppInstalledAndSupported(IWXAPI api) {
        boolean result = api.isWXAppInstalled() && api.isWXAppSupportAPI();
        return result;
    }

    private void registerBroadcastReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Const.ACTION_PAY_SUCCESS);
        filter.addAction(Const.ACTION_PAY_FAIL);
        context.registerReceiver(payReceiver, filter);
    }

    private BroadcastReceiver payReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Const.ACTION_PAY_SUCCESS) || action.equals(Const.ACTION_PAY_FAIL)) {
                finish();
            }
        }
    };

    @OnClick({R.id.imgViBack, R.id.txtConfirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgViBack:
                finish();
                break;
            case R.id.txtConfirm: //确认支付
                if (isWXAppInstalledAndSupported(msgApi)) {
                    if (payType == Const.PAY_TYPE_TASK) {
                        getWeChatOrderTask();
                    } else if (payType == Const.PAY_TYPE_RECHARGE) {
                        getWeChatOrderRecharge();
                    }
                } else {
                    Toast.makeText(context, "您还没有安装微信", Toast.LENGTH_SHORT).show();
                }
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

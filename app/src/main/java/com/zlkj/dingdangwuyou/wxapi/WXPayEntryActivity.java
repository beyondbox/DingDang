package com.zlkj.dingdangwuyou.wxapi;

import android.content.Intent;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.zlkj.dingdangwuyou.R;
import com.zlkj.dingdangwuyou.base.BaseActivity;
import com.zlkj.dingdangwuyou.net.Url;
import com.zlkj.dingdangwuyou.utils.Const;
import com.zlkj.dingdangwuyou.utils.LogHelper;
import com.zlkj.dingdangwuyou.utils.UserUtil;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 微信支付结果回调
 * Created by Botx on 2017/1/6.
 */
public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler{

	public static int PAY_TYPE = Const.PAY_TYPE_TASK; //支付类型，默认为任务塞红包

    private IWXAPI api;

	@Override
	protected int getContentViewId() {
		return R.layout.activity_wechat_pay_result;
	}

	@Override
	protected void initData() {
		pDialog.setCancelable(false);
		api = WXAPIFactory.createWXAPI(this, Const.WECHAT_APP_ID);
		api.handleIntent(getIntent(), this);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		LogHelper.e("WXPayResult", "onPayFinish, errCode = " + resp.errCode);

		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			switch (resp.errCode) {
				case 0: //成功
					if (PAY_TYPE == Const.PAY_TYPE_TASK) {
						getResultTask();
					} else if (PAY_TYPE == Const.PAY_TYPE_RECHARGE) {
						getResultRecharge();
					}
					break;
				case -1: //失败
					sendBroadcast(new Intent(Const.ACTION_PAY_FAIL));
					Toast.makeText(context, "支付失败", Toast.LENGTH_SHORT).show();
					finish();
					break;
				case -2: //取消支付
					//Toast.makeText(context, "您取消了支付", Toast.LENGTH_SHORT).show();
					finish();
					break;
				default:
					break;
			}
		}
	}

	/**
	 * 获取服务端支付结果——任务塞红包
	 */
	private void getResultTask() {
		RequestParams params = new RequestParams();
		params.put("id", UserUtil.getUserInfo().getId());

		AsyncHttpClient client = new AsyncHttpClient();
		client.setTimeout(30000);
		client.post(Url.URL_TASK_PAY_WECHAT_QUERY, params, new AsyncHttpResponseHandler() {
			@Override
			public void onStart() {
				super.onStart();
				pDialog.show();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				String jsonStr = new String(responseBody);
				try {
					JSONObject jsonObj = new JSONObject(jsonStr);
					boolean result = jsonObj.getBoolean("result");
					if (result) {
						sendBroadcast(new Intent(Const.ACTION_PAY_SUCCESS));
					} else {
						sendBroadcast(new Intent(Const.ACTION_PAY_FAIL));
						Toast.makeText(context, "支付失败", Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
					sendBroadcast(new Intent(Const.ACTION_PAY_FAIL));
					Toast.makeText(context, "支付失败", Toast.LENGTH_SHORT).show();
				}

				finish();
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
				sendBroadcast(new Intent(Const.ACTION_PAY_SUCCESS));
				finish();
			}

			@Override
			public void onFinish() {
				super.onFinish();
				pDialog.dismiss();
			}
		});
	}


	/**
	 * 获取服务端支付结果——账户充值
	 */
	private void getResultRecharge() {
		RequestParams params = new RequestParams();
		params.put("id", UserUtil.getUserInfo().getId());

		String url = "";
		if (UserUtil.getUserType() == Const.USER_TYPE_PER) {
			url = Url.URL_RECHARGE_PAY_WECHAT_QUERY_PER;
		} else {
			url = Url.URL_RECHARGE_PAY_WECHAT_QUERY_COM;
		}

		AsyncHttpClient client = new AsyncHttpClient();
		client.setTimeout(30000);
		client.post(url, params, new AsyncHttpResponseHandler() {
			@Override
			public void onStart() {
				super.onStart();
				pDialog.show();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				String jsonStr = new String(responseBody);
				try {
					JSONObject jsonObj = new JSONObject(jsonStr);
					boolean result = jsonObj.getBoolean("result");
					if (result) {
						sendBroadcast(new Intent(Const.ACTION_PAY_SUCCESS));
					} else {
						sendBroadcast(new Intent(Const.ACTION_PAY_FAIL));
						Toast.makeText(context, "支付失败", Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
					sendBroadcast(new Intent(Const.ACTION_PAY_FAIL));
					Toast.makeText(context, "支付失败", Toast.LENGTH_SHORT).show();
				}

				finish();
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
				sendBroadcast(new Intent(Const.ACTION_PAY_SUCCESS));
				finish();
			}

			@Override
			public void onFinish() {
				super.onFinish();
				pDialog.dismiss();
			}
		});
	}
}
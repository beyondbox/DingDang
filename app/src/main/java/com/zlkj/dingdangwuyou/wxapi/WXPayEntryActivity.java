package com.zlkj.dingdangwuyou.wxapi;

import android.content.Intent;
import android.widget.Toast;

import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.zlkj.dingdangwuyou.R;
import com.zlkj.dingdangwuyou.base.BaseActivity;
import com.zlkj.dingdangwuyou.utils.Const;
import com.zlkj.dingdangwuyou.utils.LogHelper;

/**
 * 微信支付结果回调
 * Created by Botx on 2017/1/6.
 */
public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler{

    private IWXAPI api;

	@Override
	protected int getContentViewId() {
		return R.layout.activity_wechat_pay_result;
	}

	@Override
	protected void initData() {
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
					sendBroadcast(new Intent(Const.ACTION_PAY_SUCCESS));
					finish();
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
}
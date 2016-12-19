package com.zlkj.dingdangwuyou.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zlkj.dingdangwuyou.R;
import com.zlkj.dingdangwuyou.activity.CompanyInfoActivity;
import com.zlkj.dingdangwuyou.activity.ImageBrowseActivity;
import com.zlkj.dingdangwuyou.activity.LoginChooseActivity;
import com.zlkj.dingdangwuyou.activity.MyHonorActivity;
import com.zlkj.dingdangwuyou.activity.MyWalletActivity;
import com.zlkj.dingdangwuyou.activity.PublishedTaskActivity;
import com.zlkj.dingdangwuyou.activity.ReceivedTaskActivity;
import com.zlkj.dingdangwuyou.activity.SettingActivity;
import com.zlkj.dingdangwuyou.activity.UserInfoActivity;
import com.zlkj.dingdangwuyou.base.BaseFragment;
import com.zlkj.dingdangwuyou.entity.User;
import com.zlkj.dingdangwuyou.net.MyHttpClient;
import com.zlkj.dingdangwuyou.net.Url;
import com.zlkj.dingdangwuyou.utils.Const;
import com.zlkj.dingdangwuyou.utils.GsonUtil;
import com.zlkj.dingdangwuyou.utils.UserUtil;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 个人中心
 * Created by Botx on 2016/10/19.
 */

public class MyCenterFragment extends BaseFragment {

    @BindView(R.id.txtNotLogin)
    TextView txtNotLogin;
    @BindView(R.id.txtUserName)
    TextView txtUserName;
    @BindView(R.id.imgViAvatar)
    ImageView imgViAvatar;

    private int userType;

    @Override
    protected int getLayoutId() {
        return R.layout.frag_my_center;
    }

    @Override
    protected void initData() {
        if (UserUtil.isLogin()) {
            loadUserInfo();
        }

        registerBroadcastReceiver();
    }


    @OnClick({R.id.txtNotLogin, R.id.lLaytSetting, R.id.lLaytUserInfo, R.id.lLaytMyHonor, R.id.lLaytMyWallet, R.id.lLaytPublishedTask, R.id.lLaytReceivedTask, R.id.imgViAvatar})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.txtNotLogin: //未登录
                intent = new Intent(context, LoginChooseActivity.class);
                startActivity(intent);
                break;
            case R.id.lLaytUserInfo: //我的信息
                if (UserUtil.isLogin()) {
                    if (userType == Const.USER_TYPE_PER)
                        intent = new Intent(context, UserInfoActivity.class);
                    else
                        intent = new Intent(context, CompanyInfoActivity.class);
                } else {
                    intent = new Intent(context, LoginChooseActivity.class);
                }
                startActivityForResult(intent, 0);
                break;
            case R.id.lLaytMyHonor: //我的荣誉
                if (UserUtil.isLogin()) {
                    intent = new Intent(context, MyHonorActivity.class);
                } else {
                    intent = new Intent(context, LoginChooseActivity.class);
                }
                startActivity(intent);
                break;
            case R.id.lLaytMyWallet: //我的钱包
                if (UserUtil.isLogin()) {
                    intent = new Intent(context, MyWalletActivity.class);
                } else {
                    intent = new Intent(context, LoginChooseActivity.class);
                }
                startActivity(intent);
                break;
            case R.id.lLaytPublishedTask: //我发布的任务
                if (UserUtil.isLogin()) {
                    intent = new Intent(context, PublishedTaskActivity.class);
                } else {
                    intent = new Intent(context, LoginChooseActivity.class);
                }
                startActivity(intent);
                break;
            case R.id.lLaytReceivedTask: //我接受的任务
                if (UserUtil.isLogin()) {
                    intent = new Intent(context, ReceivedTaskActivity.class);
                } else {
                    intent = new Intent(context, LoginChooseActivity.class);
                }
                startActivity(intent);
                break;
            case R.id.lLaytSetting: //设置
                intent = new Intent(context, SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.imgViAvatar: //头像
                if (UserUtil.isLogin()) {
                    String imgPath = "";
                    if (userType == Const.USER_TYPE_PER) {
                        imgPath = UserUtil.getUserInfo().getImgurl();
                    } else {
                        imgPath = UserUtil.getUserInfo().getImg_frjqzp();
                    }
                    if (!TextUtils.isEmpty(imgPath)) {
                        intent = new Intent(context, ImageBrowseActivity.class);
                        intent.putExtra(Const.KEY_IMAGE_PATH, Url.HOST + imgPath);
                        startActivity(intent);
                    }
                }
                break;
            default:
                break;
        }
    }


    /**
     * 载入用户头像和姓名
     */
    private void loadUserInfo() {
        userType = UserUtil.getUserType();
        User user = UserUtil.getUserInfo();
        String name = "";
        String imgUrl = "";
        if (userType == Const.USER_TYPE_PER) {
            name = user.getName();
            imgUrl = Url.HOST + user.getImgurl();
        } else {
            name = user.getCpname();
            imgUrl = Url.HOST + user.getImg_frjqzp();
        }

        Glide.with(context)
                .load(imgUrl)
                .dontAnimate()
                .placeholder(R.mipmap.touxiang)
                .error(R.mipmap.touxiang)
                .into(imgViAvatar);

        //有姓名则显示，没有则显示帐号名
        if (TextUtils.isEmpty(name)) {
            if (userType == Const.USER_TYPE_PER) {
                txtUserName.setText(user.getUsername());
            } else {
                txtUserName.setText(user.getCusername());
            }
        } else {
            txtUserName.setText(name);
        }

        txtNotLogin.setVisibility(View.GONE);
        txtUserName.setVisibility(View.VISIBLE);
    }

    /**
     * 获取用户信息
     */
    private void getUserInfo() {
        RequestParams params = new RequestParams();
        params.put("id", UserUtil.getUserInfo().getId());

        String url = "";
        if (userType == Const.USER_TYPE_PER) {
            url = Url.URL_GET_PER_INFO;
        } else {
            url = Url.URL_GET_COM_INFO;
        }

        MyHttpClient.getInstance().post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String jsonStr = new String(responseBody);
                try {
                    JSONArray jsonArr = new JSONArray(jsonStr);
                    if (jsonArr.length() > 0) {
                        JSONObject jsonObj = jsonArr.getJSONObject(0);
                        User user = GsonUtil.getEntity(jsonObj.toString(), User.class);
                        UserUtil.saveUserInfo(user);
                        loadUserInfo();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            }
        });
    }

    private void registerBroadcastReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Const.ACTION_LOGIN_SUCCESS);
        context.registerReceiver(myCenterReceiver, filter);
    }

    private BroadcastReceiver myCenterReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Const.ACTION_LOGIN_SUCCESS)) {
                loadUserInfo();
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Const.RESULT_CODE_SAVE_SUCCEED) {
            getUserInfo();
        }
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (getView() != null) {
            getView().setVisibility(menuVisible ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void onDestroy() {
        context.unregisterReceiver(myCenterReceiver);
        super.onDestroy();
    }
}

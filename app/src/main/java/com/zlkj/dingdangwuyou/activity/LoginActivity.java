package com.zlkj.dingdangwuyou.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zlkj.dingdangwuyou.R;
import com.zlkj.dingdangwuyou.base.BaseActivity;
import com.zlkj.dingdangwuyou.base.BaseApplication;
import com.zlkj.dingdangwuyou.entity.User;
import com.zlkj.dingdangwuyou.net.MyHttpClient;
import com.zlkj.dingdangwuyou.net.Url;
import com.zlkj.dingdangwuyou.utils.AppTool;
import com.zlkj.dingdangwuyou.utils.Const;
import com.zlkj.dingdangwuyou.utils.GsonUtil;
import com.zlkj.dingdangwuyou.utils.JsonUtil;
import com.zlkj.dingdangwuyou.utils.LogHelper;
import com.zlkj.dingdangwuyou.utils.SPUtil;
import com.zlkj.dingdangwuyou.utils.UserUtil;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 登录
 * Created by Botx on 2016/11/2.
 */

public class LoginActivity extends BaseActivity {

    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.edtTxtUserName)
    EditText edtTxtUserName;
    @BindView(R.id.edtTxtPassword)
    EditText edtTxtPassword;
    @BindView(R.id.txtLogin)
    TextView txtLogin;

    private int userType;
    private String password;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {
        userType = getIntent().getIntExtra(Const.KEY_USER_TYPE, 0);
        if (userType == Const.USER_TYPE_COM) {
            txtTitle.setText("企业用户登录");
        } else if (userType == Const.USER_TYPE_PER) {
            txtTitle.setText("个人用户登录");
        }

        LoginWatcher loginWatcher = new LoginWatcher();
        edtTxtUserName.addTextChangedListener(loginWatcher);
        edtTxtPassword.addTextChangedListener(loginWatcher);
    }

    @OnClick({R.id.imgViBack, R.id.txtLogin, R.id.txtRegister})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.imgViBack:
                finish();
                break;
            case R.id.txtLogin:
                login();
                break;
            case R.id.txtRegister:
                intent = new Intent(context, RegisterActivity.class);
                if (userType == Const.USER_TYPE_COM) {
                    intent.putExtra(Const.KEY_USER_TYPE, Const.USER_TYPE_COM);
                } else {
                    intent.putExtra(Const.KEY_USER_TYPE, Const.USER_TYPE_PER);
                }
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    /**
     * 登录
     */
    private void login() {
        password = edtTxtPassword.getText().toString().trim();

        RequestParams params = new RequestParams();
        if (userType == Const.USER_TYPE_PER) {
            params.put("username", edtTxtUserName.getText().toString().trim());
            params.put("password", password);
        } else {
            params.put("cusername", edtTxtUserName.getText().toString().trim());
            params.put("cpassword", password);
        }

        String url = "";
        if (userType == Const.USER_TYPE_COM) {
            url = Url.URL_LOGIN_COM;
        } else if(userType == Const.USER_TYPE_PER) {
            url = Url.URL_LOGIN_PER;
        }

        MyHttpClient.getInstance().post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                pDialog.setMessage("正在登录，请稍后....");
                pDialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String temp = new String(responseBody);
                String [] arr = temp.split("\\}\\]\\}");
                String jsonStr = arr[0];
                if (!jsonStr.endsWith("}")) {
                    jsonStr = jsonStr + "}]}";
                }

                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    boolean result = Boolean.valueOf(jsonObj.getString("result"));
                    if (result) { //登录成功
                        UserUtil.saveUserInfo(GsonUtil.getEntity(JsonUtil.getDataJsonObj(jsonObj).toString(), User.class));
                        UserUtil.saveUserType(userType);
                        UserUtil.saveUserPwd(password);

                        Intent intent = new Intent(Const.ACTION_LOGIN_SUCCESS);
                        context.sendBroadcast(intent);

                        Toast.makeText(context, "登录成功", Toast.LENGTH_SHORT).show();
                        setResult(Const.RESULT_CODE_LOGIN_SUCCEED);
                        finish();

                    } else { //登录失败
                        Toast.makeText(context, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(context, context.getResources().getString(R.string.request_fail), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish() {
                pDialog.dismiss();
            }
        });
    }


    private class LoginWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String username = edtTxtUserName.getText().toString().trim();
            String password = edtTxtPassword.getText().toString().trim();

            String nameStr = AppTool.stringFilter(username);
            String pwdStr = AppTool.stringFilter(password);

            if (!username.equals(nameStr)) {
                edtTxtUserName.setText(nameStr);
                edtTxtUserName.setSelection(nameStr.length());
            }
            if (!password.equals(pwdStr)) {
                edtTxtPassword.setText(pwdStr);
                edtTxtPassword.setSelection(pwdStr.length());
            }

            if (TextUtils.isEmpty(nameStr) || TextUtils.isEmpty(pwdStr)) {
                txtLogin.setEnabled(false);
            } else {
                txtLogin.setEnabled(true);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    }


}

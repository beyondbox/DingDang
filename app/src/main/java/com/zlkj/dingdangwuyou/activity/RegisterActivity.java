package com.zlkj.dingdangwuyou.activity;

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
import com.zlkj.dingdangwuyou.net.MyHttpClient;
import com.zlkj.dingdangwuyou.net.Url;
import com.zlkj.dingdangwuyou.utils.AppTool;
import com.zlkj.dingdangwuyou.utils.Const;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 注册
 * Created by Botx on 2016/11/3.
 */

public class RegisterActivity extends BaseActivity {
    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.edtTxtUserName)
    EditText edtTxtUserName;
    @BindView(R.id.edtTxtPassword)
    EditText edtTxtPassword;
    @BindView(R.id.edtTxtConfirmPwd)
    EditText edtTxtConfirmPwd;
    @BindView(R.id.txtRegister)
    TextView txtRegister;

    private int userType;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initData() {
        userType = getIntent().getIntExtra(Const.KEY_USER_TYPE, 0);
        if (userType == Const.USER_TYPE_COM) {
            txtTitle.setText("企业用户注册");
        } else if (userType == Const.USER_TYPE_PER) {
            txtTitle.setText("个人用户注册");
        }

        RegisterWatcher registerWatcher= new RegisterWatcher();
        edtTxtUserName.addTextChangedListener(registerWatcher);
        edtTxtPassword.addTextChangedListener(registerWatcher);
        edtTxtConfirmPwd.addTextChangedListener(registerWatcher);
    }

    @OnClick({R.id.imgViBack, R.id.txtRegister})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgViBack:
                finish();
                break;
            case R.id.txtRegister:
                register();
                break;
            default:
                break;
        }
    }

    /**
     * 注册
     */
    private void register() {
        String username = edtTxtUserName.getText().toString().trim();
        String password = edtTxtPassword.getText().toString().trim();
        String confirmPwd = edtTxtConfirmPwd.getText().toString().trim();
        if (!password.equals(confirmPwd)) {
            Toast.makeText(this, "两次输入的密码不一致，请重新输入", Toast.LENGTH_LONG).show();
            edtTxtPassword.setText("");
            edtTxtConfirmPwd.setText("");
            edtTxtPassword.requestFocus();
            return;
        }

        RequestParams params = new RequestParams();
        if (userType == Const.USER_TYPE_PER) {
            params.put("username", username);
            params.put("password", password);
        } else {
            params.put("cusername", username);
            params.put("cpassword", password);
        }

        String url = "";
        if (userType == Const.USER_TYPE_COM) {
            url = Url.URL_REGISTER_COM;
        } else if(userType == Const.USER_TYPE_PER) {
            url = Url.URL_REGISTER_PER;
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
                    boolean result = jsonObj.optBoolean("result");
                    if (result) {
                        Toast.makeText(context, "注册成功，请登录", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(context, "用户名已存在", Toast.LENGTH_SHORT).show();
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


    private class RegisterWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String username = edtTxtUserName.getText().toString().trim();
            String password = edtTxtPassword.getText().toString().trim();
            String confirmPwd = edtTxtConfirmPwd.getText().toString().trim();

            String nameStr = AppTool.stringFilter(username);
            String pwdStr = AppTool.stringFilter(password);
            String rePwdStr = AppTool.stringFilter(confirmPwd);

            if (!username.equals(nameStr)) {
                edtTxtUserName.setText(nameStr);
                edtTxtUserName.setSelection(nameStr.length());
            }
            if (!password.equals(pwdStr)) {
                edtTxtPassword.setText(pwdStr);
                edtTxtPassword.setSelection(pwdStr.length());
            }
            if (!confirmPwd.equals(rePwdStr)) {
                edtTxtConfirmPwd.setText(rePwdStr);
                edtTxtConfirmPwd.setSelection(rePwdStr.length());
            }

            if (TextUtils.isEmpty(nameStr) || pwdStr.length() < 6 || rePwdStr.length() < 6) {
                txtRegister.setEnabled(false);
            } else {
                txtRegister.setEnabled(true);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    }

}

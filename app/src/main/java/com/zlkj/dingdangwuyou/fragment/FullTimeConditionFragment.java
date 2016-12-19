package com.zlkj.dingdangwuyou.fragment;

import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zlkj.dingdangwuyou.R;
import com.zlkj.dingdangwuyou.adapter.PopMenuStringAdapter;
import com.zlkj.dingdangwuyou.base.BaseFragment;
import com.zlkj.dingdangwuyou.entity.User;
import com.zlkj.dingdangwuyou.net.MyHttpClient;
import com.zlkj.dingdangwuyou.net.Url;
import com.zlkj.dingdangwuyou.utils.Const;
import com.zlkj.dingdangwuyou.utils.UserUtil;
import com.zlkj.dingdangwuyou.widget.PopMenu;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 全职招募条件
 * Created by Botx on 2016/10/27.
 */

public class FullTimeConditionFragment extends BaseFragment {

    @BindView(R.id.txtNameCom)
    TextView txtNameCom;
    @BindView(R.id.txtPrefer)
    EditText txtPrefer;
    @BindView(R.id.txtContact)
    EditText txtContact;
    @BindView(R.id.txtPhone)
    EditText txtPhone;
    @BindView(R.id.txtSex)
    TextView txtSex;
    @BindView(R.id.txtIndustry)
    EditText txtIndustry;
    @BindView(R.id.txtAge)
    EditText txtAge;
    @BindView(R.id.txtPosition)
    EditText txtPosition;
    @BindView(R.id.txtArea)
    EditText txtArea;
    @BindView(R.id.txtSalary)
    EditText txtSalary;
    @BindView(R.id.txtCount)
    EditText txtCount;
    @BindView(R.id.txtEducation)
    EditText txtEducation;
    @BindView(R.id.txtExperience)
    EditText txtExperience;
    @BindView(R.id.txtSpecialty)
    EditText txtSpecialty;
    @BindView(R.id.txtEnvironment)
    EditText txtEnvironment;
    @BindView(R.id.txtCertificate)
    EditText txtCertificate;
    @BindView(R.id.txtWorkTime)
    EditText txtWorkTime;

    private User user;
    private List<String> sexList;
    private PopMenu popMenuSex;

    @Override
    protected int getLayoutId() {
        return R.layout.frag_fulltime_condition;
    }

    @Override
    protected void initData() {
        user = UserUtil.getUserInfo();
        txtNameCom.setText(user.getCpname());
        txtContact.setText(user.getContacts());
        txtPhone.setText(user.getContactnumber());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initSexPopMenu();
            }
        }, 200);
    }

    private void initSexPopMenu() {
        sexList = new ArrayList<String>();
        sexList.add("不限");
        sexList.add("男");
        sexList.add("女");

        View contentView = LayoutInflater.from(context).inflate(R.layout.commen_popmenu_content, null);
        ListView lvData = (ListView) contentView.findViewById(R.id.lvData);
        popMenuSex = new PopMenu(txtSex.getMeasuredWidth(), ViewGroup.LayoutParams.WRAP_CONTENT, contentView,
                lvData, new PopMenuStringAdapter(context, sexList));

        lvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                popMenuSex.dismiss();
                txtSex.setText(sexList.get(position));
            }
        });
    }


    @OnClick({R.id.txtRelease, R.id.txtSex})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtRelease:
                saveInfo();
                break;
            case R.id.txtSex:
                popMenuSex.showAsDropDown(txtSex, 0, 10);
                break;
            default:
                break;
        }
    }

    private RequestParams getParams() {
        RequestParams params = new RequestParams();
        params.put("cpif_id", user.getId());
        params.put("contacts", txtContact.getText().toString().trim());
        params.put("contactnumber", txtPhone.getText().toString().trim());
        params.put("industry", txtIndustry.getText().toString().trim());
        params.put("position", txtPosition.getText().toString().trim());
        params.put("number", txtCount.getText().toString().trim());
        params.put("salary", txtSalary.getText().toString().trim());
        params.put("xueli", txtEducation.getText().toString().trim());
        params.put("major", txtSpecialty.getText().toString().trim());
        params.put("sex", txtSex.getText().toString().trim());
        params.put("age", txtAge.getText().toString().trim());
        //params.put("publishtime", "");
        params.put("youxian", txtPrefer.getText().toString().trim());
        params.put("cyzgzs", txtCertificate.getText().toString().trim());
        params.put("jobtime", txtWorkTime.getText().toString().trim());
        params.put("jobexperience", txtExperience.getText().toString().trim());
        params.put("area", txtArea.getText().toString().trim());
        params.put("jobhuanjing", txtEnvironment.getText().toString().trim());
        params.put("szjtate", Const.WORK_TYPE_FULL_TIME);

        return params;
    }

    private RequestParams getParams2() {
        RequestParams params = new RequestParams();
        params.put("industry", txtIndustry.getText().toString().trim());
        params.put("position", txtPosition.getText().toString().trim());
        params.put("number", txtCount.getText().toString().trim());
        params.put("salary", txtSalary.getText().toString().trim());
        params.put("xueli", txtEducation.getText().toString().trim());
        params.put("major", txtSpecialty.getText().toString().trim());
        params.put("age", txtAge.getText().toString().trim());
        params.put("cyzgzs", txtCertificate.getText().toString().trim());
        params.put("jobtime", txtWorkTime.getText().toString().trim());
        params.put("jobexperience", txtExperience.getText().toString().trim());
        params.put("area", txtArea.getText().toString().trim());
        params.put("jobhuanjing", txtEnvironment.getText().toString().trim());
        params.put("salarytime", "");
        params.put("szjtate", Const.WORK_TYPE_FULL_TIME);

        return params;
    }

    /**
     * 保存信息
     */
    private void saveInfo() {
        if (TextUtils.isEmpty(txtPhone.getText().toString().trim())) {
            Toast.makeText(context, "请输入联系电话", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(txtPosition.getText().toString().trim())) {
            Toast.makeText(context, "请输入招聘职位", Toast.LENGTH_SHORT).show();
            return;
        }

        MyHttpClient.getInstance().post(Url.URL_COM_MODIFY_FULLTIME, getParams(), new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                pDialog.setMessage("正在提交，请稍候....");
                pDialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                release();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(context, context.getResources().getString(R.string.request_fail), Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * 发布匹配
     */
    private void release() {
        MyHttpClient.getInstance().post(Url.URL_COM_MATCH_FIRST, getParams2(), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String jsonStr = new String(responseBody);
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    if (jsonObj.has("items")) {
                        Toast.makeText(context, "发布成功", Toast.LENGTH_SHORT).show();
                        context.setResult(Const.RESULT_CODE_RELEASE_SUCCEED);
                        context.finish();
                    } else {
                        Toast.makeText(context, "发布失败", Toast.LENGTH_SHORT).show();
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
                super.onFinish();
                pDialog.dismiss();
            }
        });
    }

}

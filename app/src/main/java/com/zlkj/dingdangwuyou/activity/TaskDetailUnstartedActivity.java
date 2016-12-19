package com.zlkj.dingdangwuyou.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zlkj.dingdangwuyou.R;
import com.zlkj.dingdangwuyou.base.BaseActivity;
import com.zlkj.dingdangwuyou.entity.Task;
import com.zlkj.dingdangwuyou.entity.TaskTypeList;
import com.zlkj.dingdangwuyou.net.MyHttpClient;
import com.zlkj.dingdangwuyou.net.Url;
import com.zlkj.dingdangwuyou.utils.AppTool;
import com.zlkj.dingdangwuyou.utils.Const;
import com.zlkj.dingdangwuyou.utils.UserUtil;
import com.zlkj.dingdangwuyou.widget.ApplyTaskDialog;

import org.apache.http.Header;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 任务详情(还未进行的)
 * Created by btx on 2016/11/25.
 */

public class TaskDetailUnstartedActivity extends BaseActivity {

    @BindView(R.id.txtTitle)
    TextView txtTitle;

    @BindView(R.id.txtType)
    TextView txtType;
    @BindView(R.id.txtName)
    EditText txtName;
    @BindView(R.id.txtContent)
    EditText txtContent;
    @BindView(R.id.txtFinishTime)
    TextView txtFinishTime;
    @BindView(R.id.txtIdentity)
    EditText txtIdentity;
    @BindView(R.id.txtArea)
    EditText txtArea;
    @BindView(R.id.txtCount)
    EditText txtCount;
    @BindView(R.id.txtPacketMoney)
    EditText txtPacketMoney;
    @BindView(R.id.txtPacketNum)
    EditText txtPacketNum;
    @BindView(R.id.txtPhone)
    EditText txtPhone;
    @BindView(R.id.txtMessage)
    EditText txtMessage;

    private Task task;
    private ApplyTaskDialog applyDialog;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_task_detail_unstarted;
    }

    @Override
    protected void initData() {
        txtTitle.setText("任务详情");
        applyDialog = new ApplyTaskDialog(context);

        task = (Task) getIntent().getSerializableExtra(Const.KEY_OBJECT);
        setData();
    }

    /**
     * 渲染数据
     */
    private void setData() {
        txtType.setText(getTaskType());
        txtName.setText(task.getT_name());
        txtContent.setText(task.getT_content());
        txtFinishTime.setText(AppTool.dateFormat(task.getT_finish_time().getTime(), "yyyy-MM-dd"));
        txtIdentity.setText(task.getT_status());
        txtArea.setText(task.getT_area());
        txtCount.setText(task.getT_num());
        txtPacketMoney.setText(task.getT_money() + "     (单位:元)");
        txtPacketNum.setText(task.getT_hbnum());
        txtPhone.setText(task.getT_contact());
        txtMessage.setText(task.getT_words());
    }

    /**
     * 根据任务类型id获取类型名称
     * @return
     */
    private String getTaskType() {
        String result = "";
        for (TaskTypeList.TaskType taskType : TaskTypeList.list) {
            if (taskType.getId().equals(task.getCa_id())) {
                result = taskType.getName();
                break;
            }
        }

        return result;
    }

    @OnClick({R.id.imgViBack, R.id.txtApplyTask})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgViBack: //返回
                finish();
                break;
            case R.id.txtApplyTask: //我要接单
                if (!UserUtil.isLogin()) {
                    Intent intent = new Intent(context, LoginChooseActivity.class);
                    startActivityForResult(intent, 0);
                    return;
                }
                applyDialog.show();
                applyDialog.setOnConfirmListener(new ApplyTaskDialog.OnConfirmListener() {
                    @Override
                    public void onConfirm(String name, String sex, String phone, String identity, String area) {
                        applyDialog.dismiss();
                        applyTask(name, sex, phone, identity, area);
                    }
                });
                break;
            default:
                break;
        }
    }

    /**
     * 申请接任务
     */
    private void applyTask(String name, String sex, String phone, String identity, String area) {
        RequestParams params = new RequestParams();
        params.put("jlname", name);
        params.put("jlsex", sex);
        params.put("jlarea", area);
        params.put("jltel", phone);
        params.put("ren_id", task.getId());
        params.put("jl_ren_id", UserUtil.getUserInfo().getId());

        MyHttpClient.getInstance().post(Url.URL_TASK_APPLY, params, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                pDialog.setMessage("正在提交，请稍候....");
                pDialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Toast.makeText(context, "申请接单成功，请等待对方确认", Toast.LENGTH_LONG).show();
                setResult(Const.RESULT_CODE_APPLY_TASK_SUCCEED);
                finish();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Const.RESULT_CODE_LOGIN_SUCCEED) {
            applyDialog = new ApplyTaskDialog(context);
        }
    }

}

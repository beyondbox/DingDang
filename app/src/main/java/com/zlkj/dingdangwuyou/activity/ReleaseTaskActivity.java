package com.zlkj.dingdangwuyou.activity;

import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zlkj.dingdangwuyou.R;
import com.zlkj.dingdangwuyou.adapter.PopMenuTaskTypeAdapter;
import com.zlkj.dingdangwuyou.base.BaseActivity;
import com.zlkj.dingdangwuyou.entity.TaskTypeList;
import com.zlkj.dingdangwuyou.net.MyHttpClient;
import com.zlkj.dingdangwuyou.net.Url;
import com.zlkj.dingdangwuyou.utils.Const;
import com.zlkj.dingdangwuyou.utils.UserUtil;
import com.zlkj.dingdangwuyou.widget.PopMenu;

import org.apache.http.Header;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 发布任务
 * Created by Botx on 2016/11/23.
 */

public class ReleaseTaskActivity extends BaseActivity {
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

    private PopMenu popMenuType;
    private String taskTypeId = "";
    private List<TaskTypeList.TaskType> typeList;

    private DatePickerDialog dateDialog;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_release_task;
    }

    @Override
    protected void initData() {
        txtTitle.setText("发布任务");

        //去掉“不限”类型
        typeList = TaskTypeList.list.subList(0, TaskTypeList.list.size() - 1);

        //初始化日期选择器
        Calendar c = Calendar.getInstance();
        txtFinishTime.setText(c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH)+1) + "-" + c.get(Calendar.DAY_OF_MONTH));
        dateDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                txtFinishTime.setText(year + "-" + (month+1) + "-" + dayOfMonth);
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initPopMenu();
            }
        }, 200);

        registerBroadcastReceiver();
    }

    private void initPopMenu() {
        View contentView = LayoutInflater.from(context).inflate(R.layout.commen_popmenu_content, null);
        ListView lvData = (ListView) contentView.findViewById(R.id.lvData);
        popMenuType = new PopMenu(txtType.getMeasuredWidth(), ViewGroup.LayoutParams.WRAP_CONTENT, contentView,
                lvData, new PopMenuTaskTypeAdapter(context, typeList));

        lvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                popMenuType.dismiss();
                TaskTypeList.TaskType taskType = typeList.get(position);
                txtType.setText(taskType.getName());
                taskTypeId = taskType.getId();
            }
        });
    }

    /**
     * 发布任务
     */
    private void release() {
        if (!isRequiredOk()) {
            return;
        }

        RequestParams params = new RequestParams();
        params.put("ca_id", taskTypeId);
        params.put("u_id", UserUtil.getUserInfo().getId());
        params.put("t_name", txtName.getText().toString().trim());
        params.put("t_content", txtContent.getText().toString().trim());
        params.put("t_finish_time", txtFinishTime.getText().toString().trim());
        params.put("t_status", txtIdentity.getText().toString().trim());
        params.put("t_area", txtArea.getText().toString().trim());
        params.put("t_num", txtCount.getText().toString().trim());
        params.put("t_money", txtPacketMoney.getText().toString().trim());
        params.put("t_hbnum", txtPacketNum.getText().toString().trim());
        params.put("t_words", txtMessage.getText().toString().trim());
        params.put("t_contact", txtPhone.getText().toString().trim());
        params.put("r_id", Const.TASK_STATUS_RELEASE);

        MyHttpClient.getInstance().post(Url.URL_TASK_RELEASE, params, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                pDialog.setMessage("正在提交，请稍候....");
                pDialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Toast.makeText(context, "发布成功", Toast.LENGTH_SHORT).show();
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

    /**
     * 必填项判断
     * @return
     */
    private boolean isRequiredOk() {
        if (TextUtils.isEmpty(txtName.getText().toString().trim())) {
            txtName.setError("请输入任务名称");
            txtName.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(taskTypeId)) {
            Toast.makeText(context, "请选择任务类别", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(txtContent.getText().toString().trim())) {
            txtContent.setError("请输入任务内容");
            txtContent.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(txtPacketMoney.getText().toString().trim())) {
            txtPacketMoney.setError("请输入红包金额");
            txtPacketMoney.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(txtPacketNum.getText().toString().trim())) {
            txtPacketNum.setError("请输入红包数量");
            txtPacketNum.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(txtPhone.getText().toString().trim())) {
            txtPhone.setError("请输入联系方式");
            txtPhone.requestFocus();
            return false;
        }

        return true;
    }

    /**
     * 去支付
     */
    private void goToPay() {
        String packetMoney = txtPacketMoney.getText().toString().trim();
        String packetNum = txtPacketNum.getText().toString().trim();
        if (TextUtils.isEmpty(packetMoney)) {
            txtPacketMoney.setError("请输入红包金额");
            txtPacketMoney.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(packetNum)) {
            txtPacketNum.setError("请输入红包数量");
            txtPacketNum.requestFocus();
            return;
        }

        Intent intent = new Intent(context, PayActivity.class);
        int money = Integer.valueOf(packetMoney) * Integer.valueOf(packetNum);
        intent.putExtra(Const.KEY_MONEY, money + "");
        startActivity(intent);
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
                Toast.makeText(context, "支付成功", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @OnClick({R.id.imgViBack, R.id.txtType, R.id.txtRelease, R.id.txtFinishTime, R.id.txtPay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgViBack:
                finish();
                break;
            case R.id.txtType:
                popMenuType.showAsDropDown(txtType, 0, 10);
                break;
            case R.id.txtRelease:
                release();
                break;
            case R.id.txtFinishTime:
                dateDialog.show();
                break;
            case R.id.txtPay:
                goToPay();
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

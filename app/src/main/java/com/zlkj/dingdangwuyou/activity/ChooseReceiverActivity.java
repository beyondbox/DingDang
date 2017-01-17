package com.zlkj.dingdangwuyou.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zlkj.dingdangwuyou.R;
import com.zlkj.dingdangwuyou.adapter.ReceiverAdapter;
import com.zlkj.dingdangwuyou.base.BaseActivity;
import com.zlkj.dingdangwuyou.base.MyBaseAdapter;
import com.zlkj.dingdangwuyou.entity.Receiver;
import com.zlkj.dingdangwuyou.entity.Task;
import com.zlkj.dingdangwuyou.net.MyHttpClient;
import com.zlkj.dingdangwuyou.net.Url;
import com.zlkj.dingdangwuyou.utils.AppTool;
import com.zlkj.dingdangwuyou.utils.Const;
import com.zlkj.dingdangwuyou.utils.GsonUtil;
import com.zlkj.dingdangwuyou.utils.LogHelper;
import com.zlkj.dingdangwuyou.utils.UserUtil;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * 选择接令人
 * Created by Botx on 2016/12/9.
 */

public class ChooseReceiverActivity extends BaseActivity implements MyBaseAdapter.OnWhichClickListener{
    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.ptrLayt)
    PtrClassicFrameLayout ptrLayt;
    @BindView(R.id.lvData)
    ListView lvData;

    private Task task;
    private List<Receiver> receiverList;
    private ReceiverAdapter receiverAdapter;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_choose_receiver;
    }

    @Override
    protected void initData() {
        txtTitle.setText("接令人");
        pDialog.setCancelable(false);

        task = (Task) getIntent().getSerializableExtra(Const.KEY_OBJECT);
        receiverList = new ArrayList<Receiver>();
        receiverAdapter = new ReceiverAdapter(context, receiverList);
        receiverAdapter.setOnWhichClickListener(this);
        lvData.setAdapter(receiverAdapter);

        setRefreshLayout();
        pDialog.show();
        getReceiver();
    }

    /**
     * 设置下拉刷新
     */
    private void setRefreshLayout() {
        ptrLayt.setLastUpdateTimeRelateObject(this);
        ptrLayt.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getReceiver();
            }
        });
    }

    /**
     * 获取接令人列表
     */
    private void getReceiver() {
        RequestParams params = new RequestParams();
        params.put("id", task.getId());
        params.put("page", 1);
        params.put("pageSize", 500);

        MyHttpClient.getInstance().post(Url.URL_TASK_GET_JIELING_ID, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String jsonStr = new String(responseBody);
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray jsonArr = jsonObj.getJSONArray("items");
                    List<Receiver> list = GsonUtil.getEntityList(jsonArr.toString(), Receiver.class);
                    filterReceivers(list);
                    receiverAdapter.notifyDataSetChanged();

                    txtTitle.setText("接令人 (" + getChooseReceiverNum() + "/" + receiverList.size() + ")");
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
                ptrLayt.refreshComplete();
            }
        });
    }

    /**
     * 选择接令人
     */
    private void chooseReceiver(int position) {
        RequestParams params = new RequestParams();
        params.put("id", receiverList.get(position).getId());
        MyHttpClient.getInstance().post(Url.URL_TASK_CHOOSE_RECEIVER, params, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                pDialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                LogHelper.e("ChooseReceiver", result);
                if (result.equals("true")) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pDialog.show();
                            getReceiver();
                        }
                    }, 300);
                } else {
                    Toast.makeText(context, "选择接令人失败", Toast.LENGTH_SHORT).show();
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
     * 发放赏金
     */
    private void giveMoney(int money, String jl_id) {
        RequestParams params = new RequestParams();
        params.put("u_id", UserUtil.getUserInfo().getId());
        params.put("name", money);
        params.put("jl_id", jl_id);

        MyHttpClient.getInstance().post(Url.URL_TASK_GIVE_MONEY, params, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                pDialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

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
     * 获取已选择的接令人数量
     * @return
     */
    private int getChooseReceiverNum() {
        int result = 0;
        for (Receiver receiver : receiverList) {
            int status = Integer.valueOf(receiver.getJltai());
            if (status == Const.JIELING_STATUS_UNDERWAY) {
                result++;
            }
        }

        return result;
    }

    /**
     * 过滤接令人，去掉状态为已完成的
     * @param list
     */
    private void filterReceivers(List<Receiver> list) {
        receiverList.clear();
        for (Receiver receiver : list) {
            int status = Integer.valueOf(receiver.getJltai());
            if (status == Const.JIELING_STATUS_FRESH || status == Const.JIELING_STATUS_UNDERWAY) {
                receiverList.add(receiver);
            }
        }
    }

    /**
     * 发放赏金对话框
     */
    private void showGiveMoneyDialog(final int position) {
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_give_money, null);
        dialog.setView(view);

        final EditText txtMoney = (EditText) view.findViewById(R.id.txtMoney);
        TextView txtConfirm = (TextView) view.findViewById(R.id.txtConfirm);
        txtConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String money = txtMoney.getText().toString().trim();
                if (TextUtils.isEmpty(money)) {
                    Toast.makeText(context, "请输入发放金额", Toast.LENGTH_SHORT).show();
                    return;
                }

                dialog.dismiss();
                giveMoney(Integer.valueOf(money), receiverList.get(position).getId());
            }
        });

        dialog.show();
    }

    @OnItemClick(R.id.lvData)
    public void onItemClick(int position) {
        Receiver receiver = receiverList.get(position);
        int status = Integer.valueOf(receiver.getJltai());
        switch (status) {
            case Const.JIELING_STATUS_UNDERWAY:
                String phone = receiver.getJltel().toString().trim();
                if (!TextUtils.isEmpty(phone)) {
                    AppTool.dial(context, phone);
                }
                break;
            default:
                break;
        }
    }

    @OnClick(R.id.imgViBack)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgViBack:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onWhichClick(View view, final int position, int tag) {
        switch (tag) {
            case ReceiverAdapter.TAG_CHOOSE_RECEIVER: //选择接令人
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("\n确定要选择他来接受任务吗？\n")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                chooseReceiver(position);
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                break;
            case ReceiverAdapter.TAG_GIVE_MONEY: //发放赏金
                showGiveMoneyDialog(position);
                break;
            default:
                break;
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.top_to_bottom);
    }
}

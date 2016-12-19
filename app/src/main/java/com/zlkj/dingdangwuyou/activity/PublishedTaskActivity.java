package com.zlkj.dingdangwuyou.activity;

import android.content.Intent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zlkj.dingdangwuyou.R;
import com.zlkj.dingdangwuyou.adapter.TaskAdapter;
import com.zlkj.dingdangwuyou.base.BaseActivity;
import com.zlkj.dingdangwuyou.entity.Task;
import com.zlkj.dingdangwuyou.net.MyHttpClient;
import com.zlkj.dingdangwuyou.net.Url;
import com.zlkj.dingdangwuyou.utils.Const;
import com.zlkj.dingdangwuyou.utils.GsonUtil;
import com.zlkj.dingdangwuyou.utils.UserUtil;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * 我发布的任务
 * Created by Botx on 2016/11/28.
 */

public class PublishedTaskActivity extends BaseActivity {
    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.lvData)
    ListView lvData;
    @BindView(R.id.rdoGroup)
    RadioGroup rdoGroup;
    @BindView(R.id.ptrLaytTask)
    PtrClassicFrameLayout ptrLaytTask;

    private List<Task> releasedList; //已发布的任务
    private List<Task> underwayList; //进行中的任务
    private List<Task> finishedList; //已完成的任务

    private TaskAdapter taskAdapter;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_published_task;
    }

    @Override
    protected void initData() {
        txtTitle.setText("我发布的任务");
        setListView();
        setRefreshLayout();

        rdoGroup.check(R.id.rdoBtnReleased);

        pDialog.show();
        getData();
    }

    private void setListView() {
        releasedList = new ArrayList<Task>();
        underwayList = new ArrayList<Task>();
        finishedList = new ArrayList<Task>();

        taskAdapter = new TaskAdapter(context, releasedList);
        lvData.setAdapter(taskAdapter);
    }

    /**
     * 设置下拉刷新
     */
    private void setRefreshLayout() {
        ptrLaytTask.setLastUpdateTimeRelateObject(this);
        ptrLaytTask.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getData();
            }
        });
    }

    /**
     * 获取数据
     */
    private void getData() {
        RequestParams params = new RequestParams();
        params.put("u_id", UserUtil.getUserInfo().getId());

        MyHttpClient.getInstance().post(Url.URL_TASK_GET_PUBLISHED, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String jsonStr = new String(responseBody);
                try {
                    JSONArray jsonArr = new JSONArray(jsonStr);
                    List<Task> list = GsonUtil.getEntityList(jsonArr.toString(), Task.class);
                    dataFilter(list);
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
                ptrLaytTask.refreshComplete();
            }
        });
    }

    /**
     * 数据筛选
     */
    private void dataFilter(List<Task> list) {
        releasedList.clear();
        underwayList.clear();
        finishedList.clear();

        for (Task task : list) {
            if (task.getR_id().equals(Const.TASK_STATUS_RELEASE)) {
                releasedList.add(task);
            } else if (task.getR_id().equals(Const.TASK_STATUS_UNDERWAY)) {
                underwayList.add(task);
            } else if (task.getR_id().equals(Const.TASK_STATUS_FINISH)) {
                finishedList.add(task);
            }
        }

        switch (rdoGroup.getCheckedRadioButtonId()) {
            case R.id.rdoBtnReleased:
                taskAdapter.refresh(releasedList);
                break;
            case R.id.rdoBtnUnderway:
                taskAdapter.refresh(underwayList);
                break;
            case R.id.rdoBtnFinished:
                taskAdapter.refresh(finishedList);
                break;
            default:
                break;
        }
    }

    @OnClick({R.id.imgViBack})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgViBack:
                finish();
                break;
            default:
                break;
        }
    }

    @OnCheckedChanged({R.id.rdoBtnReleased, R.id.rdoBtnUnderway, R.id.rdoBtnFinished})
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            switch (buttonView.getId()) {
                case R.id.rdoBtnReleased: //已发布
                    taskAdapter.refresh(releasedList);
                    break;
                case R.id.rdoBtnUnderway: //进行中
                    taskAdapter.refresh(underwayList);
                    break;
                case R.id.rdoBtnFinished: //已完成
                    taskAdapter.refresh(finishedList);
                    break;
                default:
                    break;
            }

            lvData.setSelection(0);
        }
    }

    @OnItemClick(R.id.lvData)
    public void onItemClick(int position) {
        Intent intent = null;
        switch (rdoGroup.getCheckedRadioButtonId()) {
            case R.id.rdoBtnReleased:
                intent = new Intent(context, TaskDetailUnstartedActivity.class);
                intent.putExtra(Const.KEY_OBJECT, releasedList.get(position));
                startActivityForResult(intent, 0);
                break;
            case R.id.rdoBtnUnderway:
                intent = new Intent(context, TaskDetailPublishedUnderwayActivity.class);
                intent.putExtra(Const.KEY_OBJECT, underwayList.get(position));
                startActivity(intent);
                break;
            case R.id.rdoBtnFinished:
                /*intent = new Intent(context, TaskDetailPublishedUnderwayActivity.class);
                intent.putExtra(Const.KEY_OBJECT, finishedList.get(position));
                startActivity(intent);*/
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Const.RESULT_CODE_APPLY_TASK_SUCCEED) {
            pDialog.show();
            getData();
        }
    }
}

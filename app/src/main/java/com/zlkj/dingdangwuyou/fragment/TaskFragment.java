package com.zlkj.dingdangwuyou.fragment;

import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zlkj.dingdangwuyou.R;
import com.zlkj.dingdangwuyou.activity.LoginChooseActivity;
import com.zlkj.dingdangwuyou.activity.ReleaseTaskActivity;
import com.zlkj.dingdangwuyou.activity.TaskDetailUnstartedActivity;
import com.zlkj.dingdangwuyou.adapter.TaskAdapter;
import com.zlkj.dingdangwuyou.adapter.PopMenuTaskTypeAdapter;
import com.zlkj.dingdangwuyou.base.BaseFragment;
import com.zlkj.dingdangwuyou.entity.Task;
import com.zlkj.dingdangwuyou.entity.TaskTypeList;
import com.zlkj.dingdangwuyou.net.MyHttpClient;
import com.zlkj.dingdangwuyou.net.Url;
import com.zlkj.dingdangwuyou.utils.Const;
import com.zlkj.dingdangwuyou.utils.GsonUtil;
import com.zlkj.dingdangwuyou.utils.UserUtil;
import com.zlkj.dingdangwuyou.widget.PopMenu;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * 生活帮帮
 * Created by Botx on 2016/10/19.
 */

public class TaskFragment extends BaseFragment {

    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.txtRight)
    TextView txtRight;
    @BindView(R.id.imgViBack)
    ImageView imgViBack;
    @BindView(R.id.ptrLaytTask)
    PtrClassicFrameLayout ptrLaytTask;
    @BindView(R.id.lvData)
    ListView lvData;
    @BindView(R.id.txtType)
    TextView txtType;

    private List<Task> taskList;
    private TaskAdapter taskAdapter;

    private PopMenu popMenuType;
    //任务类型，默认不限
    private String taskTypeId = Const.TASK_TYPE_ALL;

    @Override
    protected int getLayoutId() {
        return R.layout.frag_task;
    }

    @Override
    protected void initData() {
        txtTitle.setText("生活帮帮");
        txtRight.setText("发布任务");
        imgViBack.setVisibility(View.INVISIBLE);

        taskList = new ArrayList<Task>();
        taskAdapter = new TaskAdapter(context, taskList);
        lvData.setAdapter(taskAdapter);

        setRefreshLayout();

        pDialog.show();
        getTaskData();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initPopMenu();
            }
        }, 200);
    }

    /**
     * 设置下拉刷新
     */
    private void setRefreshLayout() {
        ptrLaytTask.setLastUpdateTimeRelateObject(this);
        ptrLaytTask.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getTaskData();
            }
        });
    }

    private void initPopMenu() {
        View contentView = LayoutInflater.from(context).inflate(R.layout.common_popmenu_content, null);
        ListView lvData = (ListView) contentView.findViewById(R.id.lvData);
        popMenuType = new PopMenu(txtType.getMeasuredWidth(), ViewGroup.LayoutParams.WRAP_CONTENT, contentView,
                lvData, new PopMenuTaskTypeAdapter(context, TaskTypeList.list));

        lvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                popMenuType.dismiss();
                TaskTypeList.TaskType taskType = TaskTypeList.list.get(position);
                txtType.setText(taskType.getName());
                taskTypeId = taskType.getId();

                pDialog.show();
                getTaskData();
            }
        });
    }

    /**
     * 获取任务数据
     */
    private void getTaskData() {
        RequestParams params = new RequestParams();
        if (!taskTypeId.equals(Const.TASK_TYPE_ALL)) {
            params.put("ca_id", taskTypeId);
        }

        MyHttpClient.getInstance().post(Url.URL_TASK_GET_BY_TYPE, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String jsonStr = new String(responseBody);
                try {
                    JSONArray jsonArr = new JSONArray(jsonStr);
                    List<Task> list = GsonUtil.getEntityList(jsonArr.toString(), Task.class);
                    taskList.clear();
                    taskList = getAvailableTask(list);
                    taskAdapter.refresh(taskList);
                    lvData.setSelection(0);
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
                ptrLaytTask.refreshComplete();
            }
        });
    }

    /**
     * 过滤出所有未完成的任务（先按倒序排序，再筛选）
     * @return
     */
    private List<Task> getAvailableTask(List<Task> data) {
        Collections.reverse(data);

        List<Task> availableList = new ArrayList<Task>();
        for (Task task : data) {
            if (task.getR_id().equals(Const.TASK_STATUS_RELEASE) ||
                    task.getR_id().equals(Const.TASK_STATUS_UNDERWAY)) {
                availableList.add(task);
            }
        }

        return availableList;
    }

    @OnClick({R.id.txtRight, R.id.lLaytTaskType})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtRight:
                if (UserUtil.isLogin()) {
                    Intent intent = new Intent(context, ReleaseTaskActivity.class);
                    startActivityForResult(intent, 0);
                } else {
                    Intent intent = new Intent(context, LoginChooseActivity.class);
                    startActivityForResult(intent, 0);
                }
                break;
            case R.id.lLaytTaskType:
                popMenuType.showAsDropDown(txtType, 0, 15);
                break;
            default:
                break;
        }
    }

    @OnItemClick(R.id.lvData)
    public void onItemClick(int position) {
        Intent intent = new Intent(context, TaskDetailUnstartedActivity.class);
        intent.putExtra(Const.KEY_OBJECT, taskList.get(position));
        intent.putExtra(Const.KEY_NEED_MENU, true);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Const.RESULT_CODE_LOGIN_SUCCEED) {
            Intent intent = new Intent(context, ReleaseTaskActivity.class);
            startActivityForResult(intent, 0);
        } else if (resultCode == Const.RESULT_CODE_APPLY_TASK_SUCCEED) {
            getTaskData();
        } else if (resultCode == Const.RESULT_CODE_RELEASE_TASK_SUCCEED) {
            getTaskData();
        }
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (getView() != null) {
            getView().setVisibility(menuVisible ? View.VISIBLE : View.GONE);
        }
    }

}

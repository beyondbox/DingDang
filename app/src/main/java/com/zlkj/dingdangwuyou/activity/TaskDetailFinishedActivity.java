package com.zlkj.dingdangwuyou.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zlkj.dingdangwuyou.R;
import com.zlkj.dingdangwuyou.adapter.TaskImageAdapter;
import com.zlkj.dingdangwuyou.base.BaseActivity;
import com.zlkj.dingdangwuyou.entity.Task;
import com.zlkj.dingdangwuyou.entity.TaskTypeList;
import com.zlkj.dingdangwuyou.utils.AppTool;
import com.zlkj.dingdangwuyou.utils.Const;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 已完成的任务详情
 * Created by btx on 2016/11/25.
 */

public class TaskDetailFinishedActivity extends BaseActivity {

    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.lLaytBottomBar)
    LinearLayout lLaytBottomBar;

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
    @BindView(R.id.gridViImg)
    GridView gridViImg;

    private Task task;
    private List<String> imgList;
    private TaskImageAdapter imgAdapter;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_task_detail_published_underway;
    }

    @Override
    protected void initData() {
        txtTitle.setText("任务详情");
        lLaytBottomBar.setVisibility(View.GONE);

        task = (Task) getIntent().getSerializableExtra(Const.KEY_OBJECT);
        setList();
        setData();
    }

    private void setList() {
        imgList = new ArrayList<String>();
        imgAdapter = new TaskImageAdapter(context, imgList);
        gridViImg.setAdapter(imgAdapter);
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

        if (!TextUtils.isEmpty(task.getPicture())) {
            String[] data = task.getPicture().split(",");
            imgList.clear();
            imgList.addAll(Arrays.asList(data));
            imgAdapter.notifyDataSetChanged();
        }

        txtPhone.requestFocus();
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



    @OnClick({R.id.imgViBack, R.id.txtPhone})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.imgViBack: //返回
                finish();
                break;
            case R.id.txtPhone:
                String phone = txtPhone.getText().toString().trim();
                if (!TextUtils.isEmpty(phone)) {
                    AppTool.dial(context, phone);
                }
                break;
            default:
                break;
        }
    }


}

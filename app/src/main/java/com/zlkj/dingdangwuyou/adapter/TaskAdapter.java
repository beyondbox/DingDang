package com.zlkj.dingdangwuyou.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zlkj.dingdangwuyou.R;
import com.zlkj.dingdangwuyou.base.MyBaseAdapter;
import com.zlkj.dingdangwuyou.entity.Task;
import com.zlkj.dingdangwuyou.utils.AppTool;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 任务列表Adapter
 * Created by Botx on 2016/10/31.
 */

public class TaskAdapter extends MyBaseAdapter<Task> {

    public TaskAdapter(Context context, List<Task> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_task, null);
            vh = new ViewHolder();
            ButterKnife.bind(vh, convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        Task task = list.get(position);
        vh.txtName.setText(task.getT_name());
        vh.txtPacketMoney.setText("￥" + task.getT_money());
        vh.txtDate.setText(AppTool.dateFormat(task.getCreateDate().getTime(), "MM-dd"));
        vh.txtArea.setText(task.getT_area());

        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.txtName)
        TextView txtName;
        @BindView(R.id.txtPacketMoney)
        TextView txtPacketMoney;
        @BindView(R.id.txtJoinNum)
        TextView txtJoinNum;
        @BindView(R.id.txtDate)
        TextView txtDate;
        @BindView(R.id.txtArea)
        TextView txtArea;
    }

}

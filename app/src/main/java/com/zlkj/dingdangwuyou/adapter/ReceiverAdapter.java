package com.zlkj.dingdangwuyou.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zlkj.dingdangwuyou.R;
import com.zlkj.dingdangwuyou.base.MyBaseAdapter;
import com.zlkj.dingdangwuyou.entity.Receiver;
import com.zlkj.dingdangwuyou.utils.AppTool;
import com.zlkj.dingdangwuyou.utils.Const;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 接令人列表Adapter
 * Created by Botx on 2016/12/9.
 */

public class ReceiverAdapter extends MyBaseAdapter<Receiver> {
    public static final int TAG_CHOOSE_RECEIVER = 2000;

    public ReceiverAdapter(Context context, List<Receiver> list) {
        super(context, list);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_choose_receiver, null);
            vh = new ViewHolder();
            ButterKnife.bind(vh, convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        Receiver receiver = list.get(position);
        vh.txtName.setText(receiver.getJlname());
        vh.txtSex.setText(receiver.getJlsex());
        vh.txtArea.setText(receiver.getJlarea());
        vh.txtDate.setText(AppTool.dateFormat(receiver.getCreateDate().getTime(), "yyyy-MM-dd"));
        vh.txtPhone.setText(receiver.getJltel());

        int status = Integer.valueOf(receiver.getJltai());
        switch (status) {
            case Const.JIELING_STATUS_UNDERWAY: //任务进行中
                vh.imgViChoose.setImageResource(R.mipmap.check);
                vh.imgViChoose.setEnabled(false);
                vh.lLaytPhone.setVisibility(View.VISIBLE);
                break;
            case Const.JIELING_STATUS_FRESH: //新手接任务
                vh.imgViChoose.setImageResource(R.mipmap.uncheck);
                vh.imgViChoose.setEnabled(true);
                vh.lLaytPhone.setVisibility(View.GONE);
                vh.imgViChoose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onWhichClickListener != null) {
                            onWhichClickListener.onWhichClick(v, position, TAG_CHOOSE_RECEIVER);
                        }
                    }
                });
                break;
            default:
                break;
        }

        return convertView;
    }

    class ViewHolder{
        @BindView(R.id.txtName)
        TextView txtName;
        @BindView(R.id.txtSex)
        TextView txtSex;
        @BindView(R.id.txtArea)
        TextView txtArea;
        @BindView(R.id.lLaytPhone)
        LinearLayout lLaytPhone;
        @BindView(R.id.txtPhone)
        TextView txtPhone;
        @BindView(R.id.txtDate)
        TextView txtDate;
        @BindView(R.id.imgViChoose)
        ImageView imgViChoose;
    }
}

package com.zlkj.dingdangwuyou.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zlkj.dingdangwuyou.R;
import com.zlkj.dingdangwuyou.base.MyBaseAdapter;
import com.zlkj.dingdangwuyou.entity.HireInfo;
import com.zlkj.dingdangwuyou.entity.SelfCommend;
import com.zlkj.dingdangwuyou.utils.AppTool;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 自荐列表Adapter
 * Created by Botx on 2016/10/31.
 */

public class SelfCommendListAdapter extends MyBaseAdapter<SelfCommend> {

    public SelfCommendListAdapter(Context context, List<SelfCommend> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_hire, null);
            vh = new ViewHolder();
            ButterKnife.bind(vh, convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        SelfCommend selfCommend = list.get(position);

        Picasso.with(context)
                .load(R.mipmap.hire_avatar)
                .into(vh.imgViAvatar);

        vh.txtPosition.setText(selfCommend.getLx_position());
        vh.txtName.setText(selfCommend.getName());
        vh.txtDate.setText(AppTool.dateFormat(selfCommend.getModifyDate().getTime(), "MM-dd"));

        if (TextUtils.isEmpty(selfCommend.getArea())) {
            vh.txtLocation.setVisibility(View.GONE);
        } else {
            vh.txtLocation.setVisibility(View.VISIBLE);
            vh.txtLocation.setText(selfCommend.getArea());
        }

        if (TextUtils.isEmpty(selfCommend.getXueli())) {
            vh.txtEducation.setVisibility(View.GONE);
        } else {
            vh.txtEducation.setVisibility(View.VISIBLE);
            vh.txtEducation.setText(selfCommend.getXueli());
        }

        if (TextUtils.isEmpty(selfCommend.getSalary())) {
            vh.txtWage.setText("面议");
        } else {
            vh.txtWage.setText(selfCommend.getSalary());
        }

        if (selfCommend.getSzjtate().equals("0")) {
            vh.txtHireType.setText("全职");
            vh.txtHireType.setTextColor(context.getResources().getColor(R.color.green));
        } else {
            vh.txtHireType.setText("兼职");
            vh.txtHireType.setTextColor(context.getResources().getColor(R.color.homeSection));
        }

        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.imgViAvatar)
        ImageView imgViAvatar;
        @BindView(R.id.txtPosition)
        TextView txtPosition;
        @BindView(R.id.txtName)
        TextView txtName;
        @BindView(R.id.txtLocation)
        TextView txtLocation;
        @BindView(R.id.txtEducation)
        TextView txtEducation;
        @BindView(R.id.txtDate)
        TextView txtDate;
        @BindView(R.id.txtWage)
        TextView txtWage;
        @BindView(R.id.txtHireType)
        TextView txtHireType;
    }

}

package com.zlkj.dingdangwuyou.adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zlkj.dingdangwuyou.R;
import com.zlkj.dingdangwuyou.base.MyBaseAdapter;
import com.zlkj.dingdangwuyou.entity.SocialActivity;
import com.zlkj.dingdangwuyou.net.Url;
import com.zlkj.dingdangwuyou.utils.AppTool;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 公益活动ListView Adapter
 * Created by Botx on 2016/11/11.
 */

public class SocialActivityListAdapter extends MyBaseAdapter<SocialActivity> {

    public SocialActivityListAdapter(Context context, List<SocialActivity> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_social_activity, null);
            vh = new ViewHolder();
            ButterKnife.bind(vh, convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        SocialActivity socialActivity = list.get(position);
        vh.txtTitle.setText(socialActivity.getG_title());
        vh.txtTime.setText(AppTool.dateFormat(socialActivity.getModifyDate().getTime(), "yyyy-MM-dd"));

        Glide.with(context)
                .load(Url.HOST + "/" + socialActivity.getG_imgurl())
                .placeholder(new ColorDrawable(context.getResources().getColor(R.color.grayLightBg)))
                .error(new ColorDrawable(context.getResources().getColor(R.color.grayLightBg)))
                .into(vh.imageView);

        return convertView;
    }

    class ViewHolder{
        @BindView(R.id.imageView)
        ImageView imageView;
        @BindView(R.id.txtTitle)
        TextView txtTitle;
        @BindView(R.id.txtTime)
        TextView txtTime;
    }
}

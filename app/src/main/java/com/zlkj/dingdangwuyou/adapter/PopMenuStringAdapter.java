package com.zlkj.dingdangwuyou.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zlkj.dingdangwuyou.R;
import com.zlkj.dingdangwuyou.base.MyBaseAdapter;

import java.util.List;

/**
 * Created by btx on 2016/11/23.
 */

public class PopMenuStringAdapter extends MyBaseAdapter<String> {
    public PopMenuStringAdapter(Context context, List<String> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_popmenu, null);
            viewHolder = new ViewHolder();
            viewHolder.txtItem = (TextView) convertView.findViewById(R.id.txtItem);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.txtItem.setText(list.get(position));

        return convertView;
    }

    private class ViewHolder {
        TextView txtItem;
    }
}

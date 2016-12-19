package com.zlkj.dingdangwuyou.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.zlkj.dingdangwuyou.R;
import com.zlkj.dingdangwuyou.base.MyBaseAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * (没用着)
 * Created by Botx on 2016/10/28.
 */

public class HotSpotImageAdapter extends MyBaseAdapter<String> {

    private int columnWidth = 0; //列的宽度

    public HotSpotImageAdapter(Context context, List list, int columnWidth) {
        super(context, list);
        this.columnWidth = columnWidth;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_grid_img, null);
            vh = new ViewHolder();
            ButterKnife.bind(vh, convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        //设置ImageView的高度
        if (columnWidth > 0) {
            ViewGroup.LayoutParams params = vh.imgView.getLayoutParams();
            params.height = columnWidth * 3 / 5;
            vh.imgView.setLayoutParams(params);
        }

        Picasso.with(context)
                .load(R.mipmap.hot_spot)
                .into(vh.imgView);

        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.imgView)
        ImageView imgView;
    }
}

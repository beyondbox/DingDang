package com.zlkj.dingdangwuyou.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.zlkj.dingdangwuyou.R;
import com.zlkj.dingdangwuyou.base.BaseApplication;
import com.zlkj.dingdangwuyou.base.MyBaseAdapter;
import com.zlkj.dingdangwuyou.entity.HotSpot;
import com.zlkj.dingdangwuyou.utils.DisplayUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * (没用着)
 * Created by Botx on 2016/10/28.
 */

public class HotSpotAdapter extends MyBaseAdapter<HotSpot> {

    private int phoneWidth = 0; //屏幕的宽度

    public HotSpotAdapter(Context context, List list) {
        super(context, list);
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        phoneWidth = dm.widthPixels;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_hotspot, null);
            vh = new ViewHolder();
            ButterKnife.bind(vh, convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        HotSpot hotSpot = list.get(position);
        vh.txtTitle.setText(hotSpot.getTitle());

        List<String> imgUrlList = hotSpot.getImgUrlList();
        if (imgUrlList.size() > 0) {
            int columnWidth = 0; //列的宽度
            int padding = DisplayUtil.dip2px(context, 20); //两边的边距
            int space = DisplayUtil.dip2px(context, 3); //列之间的距离

            switch (imgUrlList.size()) {
                case 1:
                    vh.gridViImg.setNumColumns(1);
                    columnWidth = phoneWidth - padding;
                    break;
                case 2:
                    vh.gridViImg.setNumColumns(2);
                    columnWidth = (phoneWidth - padding - space) / 2;
                    break;
                case 3:
                    vh.gridViImg.setNumColumns(3);
                    columnWidth = (phoneWidth - padding - (space * 2)) / 3;
                    break;
                case 4:
                    vh.gridViImg.setNumColumns(2);
                    columnWidth = (phoneWidth - padding - space) / 2;
                    break;
                default:
                    vh.gridViImg.setNumColumns(3);
                    columnWidth = (phoneWidth - padding - (space * 2)) / 3;
                    break;
            }

            HotSpotImageAdapter imgAdapter = new HotSpotImageAdapter(context, imgUrlList, columnWidth);
            vh.gridViImg.setAdapter(imgAdapter);
            vh.gridViImg.setClickable(false);
            vh.gridViImg.setPressed(false);
            vh.gridViImg.setEnabled(false);
        }

        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.txtTitle)
        TextView txtTitle;
        @BindView(R.id.gridViImg)
        GridView gridViImg;
    }

}

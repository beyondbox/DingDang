package com.zlkj.dingdangwuyou.adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zlkj.dingdangwuyou.R;
import com.zlkj.dingdangwuyou.base.MyBaseAdapter;
import com.zlkj.dingdangwuyou.entity.News;
import com.zlkj.dingdangwuyou.net.Url;
import com.zlkj.dingdangwuyou.utils.AppTool;

import java.util.List;

/**
 * 新闻Adapter
 * Created by Botx on 2016/11/9.
 */

public class NewsAdapter extends MyBaseAdapter<News> {

    public static final int ITEM_VIEW_HOTSPOT = R.layout.item_list_hotspot;
    public static final int ITEM_VIEW_NEWS = R.layout.item_list_important_news;

    private int itemViewId;
    private int displayNum = 0; //listview显示的条数，不设置的情况下显示全部

    public NewsAdapter(Context context, List<News> list, int itemViewId) {
        super(context, list);
        this.itemViewId = itemViewId;
    }

    public void setDisplayNum(int displayNum) {
        this.displayNum = displayNum;
    }

    @Override
    public int getCount() {
        if (displayNum == 0) {
            return list.size();
        }
        return displayNum;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(itemViewId, null);
            vh = new ViewHolder();
            vh.txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
            vh.imageView = (ImageView) convertView.findViewById(R.id.imageView);

            if (itemViewId == ITEM_VIEW_NEWS) {
                vh.txtNewsType = (TextView) convertView.findViewById(R.id.txtNewsType);
                vh.txtTime = (TextView) convertView.findViewById(R.id.txtTime);
                vh.dashLine = convertView.findViewById(R.id.dashLine);
            }

            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        News news = list.get(position);

        vh.txtTitle.setText(news.getTitle());
        if (!TextUtils.isEmpty(news.getNewsImg())) {
            Glide.with(context)
                    .load(Url.HOST + news.getNewsImg())
                    .placeholder(new ColorDrawable(context.getResources().getColor(R.color.grayLightBg)))
                    .error(new ColorDrawable(context.getResources().getColor(R.color.grayLightBg)))
                    .into(vh.imageView);
        }

        if (itemViewId == ITEM_VIEW_NEWS) {
            vh.txtNewsType.setText("社会-" + news.getType());
            vh.txtTime.setText(AppTool.dateFormat(news.getModifyDate().getTime(), "yyyy-MM-dd"));
            vh.dashLine.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        return convertView;
    }

    class ViewHolder {
        TextView txtTitle;
        ImageView imageView;
        TextView txtNewsType;
        TextView txtTime;
        View dashLine;
    }

}

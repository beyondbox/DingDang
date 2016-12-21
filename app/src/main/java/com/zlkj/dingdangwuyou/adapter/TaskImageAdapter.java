package com.zlkj.dingdangwuyou.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zlkj.dingdangwuyou.R;
import com.zlkj.dingdangwuyou.activity.ImageBrowseActivity;
import com.zlkj.dingdangwuyou.base.MyBaseAdapter;
import com.zlkj.dingdangwuyou.net.Url;
import com.zlkj.dingdangwuyou.utils.Const;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 任务回传图片Adapter
 * Created by Botx on 2016/12/20.
 */

public class TaskImageAdapter extends MyBaseAdapter<String> {

    public TaskImageAdapter(Context context, List<String> list) {
        super(context, list);
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

        String imgUrl = Url.HOST + list.get(position);

        Glide.with(context)
                .load(imgUrl)
                .placeholder(R.mipmap.image_loading)
                .error(R.mipmap.image_error)
                .into(vh.imgView);

        vh.imgView.setOnClickListener(new ImageClickListener(imgUrl));

        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.imgView)
        ImageView imgView;
    }

    private class ImageClickListener implements View.OnClickListener {
        private String imgUrl;

        public ImageClickListener(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, ImageBrowseActivity.class);
            intent.putExtra(Const.KEY_IMAGE_PATH, imgUrl);
            context.startActivity(intent);
        }
    }
}

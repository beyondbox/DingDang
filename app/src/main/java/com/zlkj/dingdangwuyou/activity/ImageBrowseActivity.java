package com.zlkj.dingdangwuyou.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zlkj.dingdangwuyou.R;
import com.zlkj.dingdangwuyou.base.BaseActivity;
import com.zlkj.dingdangwuyou.utils.Const;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 图片浏览
 * Created by btx on 2016/11/27.
 */

public class ImageBrowseActivity extends BaseActivity {

    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.imageView)
    ImageView imageView;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_image_browse;
    }

    @Override
    protected void initData() {
        txtTitle.setText("图片浏览");

        String imgPath = getIntent().getStringExtra(Const.KEY_IMAGE_PATH);
        Glide.with(context)
                .load(imgPath)
                .placeholder(R.mipmap.image_loading)
                .error(R.mipmap.image_error)
                .into(imageView);
    }

    @OnClick(R.id.imgViBack)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgViBack:
                finish();
                break;
            default:
                break;
        }
    }
}

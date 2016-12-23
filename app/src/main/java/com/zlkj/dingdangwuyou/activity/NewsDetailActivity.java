package com.zlkj.dingdangwuyou.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zlkj.dingdangwuyou.R;
import com.zlkj.dingdangwuyou.base.BaseActivity;
import com.zlkj.dingdangwuyou.entity.News;
import com.zlkj.dingdangwuyou.net.Url;
import com.zlkj.dingdangwuyou.utils.AppTool;
import com.zlkj.dingdangwuyou.utils.Const;
import com.zlkj.dingdangwuyou.utils.DisplayUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 新闻详情
 * Created by Botx on 2016/11/7.
 */

public class NewsDetailActivity extends BaseActivity {
    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.txtHeadline)
    TextView txtHeadline;
    @BindView(R.id.txtContent)
    TextView txtContent;
    @BindView(R.id.txtTime)
    TextView txtTime;
    @BindView(R.id.imageView)
    ImageView imageView;

    private News news;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_news_detail;
    }

    @Override
    protected void initData() {
        txtTitle.setText("新闻");

        Intent intent = getIntent();
        news = (News) intent.getSerializableExtra(Const.KEY_OBJECT);

        txtHeadline.setText(news.getTitle());
        txtContent.setText(news.getContent());
        txtTime.setText(AppTool.dateFormat(news.getModifyDate().getTime(), "yyyy-MM-dd"));

        Picasso.with(context)
                .load(Url.HOST + news.getNewsImg())
                .resize(DisplayUtil.dip2px(context, 320), DisplayUtil.dip2px(context, 320))
                .centerInside()
                .into(imageView);
    }

    @OnClick({R.id.imgViBack, R.id.imageView})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgViBack:
                finish();
                break;
            case R.id.imageView:
                if (!TextUtils.isEmpty(news.getNewsImg())) {
                    Intent intent = new Intent(context, ImageBrowseActivity.class);
                    intent.putExtra(Const.KEY_IMAGE_PATH, Url.HOST + news.getNewsImg());
                    startActivity(intent);
                }
                break;
            default:
                break;
        }
    }
}

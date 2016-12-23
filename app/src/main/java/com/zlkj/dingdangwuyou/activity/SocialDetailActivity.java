package com.zlkj.dingdangwuyou.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zlkj.dingdangwuyou.R;
import com.zlkj.dingdangwuyou.base.BaseActivity;
import com.zlkj.dingdangwuyou.entity.SocialActivity;
import com.zlkj.dingdangwuyou.net.Url;
import com.zlkj.dingdangwuyou.utils.AppTool;
import com.zlkj.dingdangwuyou.utils.Const;
import com.zlkj.dingdangwuyou.utils.DisplayUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 公益活动详情
 * Created by Botx on 2016/11/7.
 */

public class SocialDetailActivity extends BaseActivity {
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
    @BindView(R.id.txtSponsor)
    TextView txtSponsor;
    @BindView(R.id.txtQQ)
    TextView txtQQ;
    @BindView(R.id.txtTel)
    TextView txtTel;

    private SocialActivity social;


    @Override
    protected int getContentViewId() {
        return R.layout.activity_social_detail;
    }

    @Override
    protected void initData() {
        txtTitle.setText("公益报名");

        Intent intent = getIntent();
        social = (SocialActivity) intent.getSerializableExtra(Const.KEY_OBJECT);

        txtHeadline.setText(social.getG_title());
        txtContent.setText(social.getG_content());
        txtTime.setText(AppTool.dateFormat(social.getModifyDate().getTime(), "yyyy-MM-dd"));

        Picasso.with(context)
                .load(Url.HOST + "/" + social.getG_imgurl())
                .resize(DisplayUtil.dip2px(context, 320), DisplayUtil.dip2px(context, 320))
                .centerInside()
                .into(imageView);

        txtSponsor.setText(social.getG_faqiren());
        txtQQ.setText(social.getG_qq());
        txtTel.setText(social.getG_tel());
    }

    @OnClick({R.id.imgViBack, R.id.imageView})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgViBack:
                finish();
                break;
            case R.id.imageView:
                if (!TextUtils.isEmpty(social.getG_imgurl())) {
                    Intent intent = new Intent(context, ImageBrowseActivity.class);
                    intent.putExtra(Const.KEY_IMAGE_PATH, Url.HOST + "/" + social.getG_imgurl());
                    startActivity(intent);
                }
                break;
            default:
                break;
        }
    }
}

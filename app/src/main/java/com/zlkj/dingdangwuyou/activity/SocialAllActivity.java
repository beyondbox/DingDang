package com.zlkj.dingdangwuyou.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.zlkj.dingdangwuyou.R;
import com.zlkj.dingdangwuyou.adapter.SocialActivityListAdapter;
import com.zlkj.dingdangwuyou.base.BaseActivity;
import com.zlkj.dingdangwuyou.entity.SocialActivity;
import com.zlkj.dingdangwuyou.utils.Const;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * 全部公益活动
 * Created by Botx on 2016/11/11.
 */

public class SocialAllActivity extends BaseActivity {

    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.lvData)
    ListView lvData;

    private List<SocialActivity> socialList;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_social_all;
    }

    @Override
    protected void initData() {
        txtTitle.setText("公益报名");

        socialList = (List<SocialActivity>) getIntent().getSerializableExtra(Const.KEY_OBJECT);
        SocialActivityListAdapter socialAdapter = new SocialActivityListAdapter(context, socialList);
        lvData.setAdapter(socialAdapter);
    }

    @OnItemClick(R.id.lvData)
    public void onItemClick(int position) {
        Intent intent = new Intent(context, SocialDetailActivity.class);
        intent.putExtra(Const.KEY_OBJECT, socialList.get(position));
        startActivity(intent);
    }

    @OnClick(R.id.imgViBack)
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgViBack:
                finish();
                break;
            default:
                break;
        }
    }
}


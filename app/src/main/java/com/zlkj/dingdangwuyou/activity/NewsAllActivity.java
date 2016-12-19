package com.zlkj.dingdangwuyou.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.zlkj.dingdangwuyou.R;
import com.zlkj.dingdangwuyou.adapter.NewsAdapter;
import com.zlkj.dingdangwuyou.base.BaseActivity;
import com.zlkj.dingdangwuyou.entity.News;
import com.zlkj.dingdangwuyou.net.Url;
import com.zlkj.dingdangwuyou.utils.Const;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * 全部新闻
 * Created by Botx on 2016/11/9.
 */

public class NewsAllActivity extends BaseActivity {
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.rLaytHead)
    RelativeLayout rLaytHead;
    @BindView(R.id.imgViHead)
    ImageView imgViHead;
    @BindView(R.id.txtHeadNewsTitle)
    TextView txtHeadNewsTitle;
    @BindView(R.id.lvNews)
    ListView lvNews;

    private List<News> newsList; //新闻集合
    private News headNews; //第一个新闻

    private NewsAdapter newsAdapter;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_news_all;
    }

    @Override
    protected void initData() {
        txtTitle.setText("新闻");

        Intent intent = getIntent();
        newsList = (List<News>) intent.getSerializableExtra(Const.KEY_OBJECT);
        if (newsList.size() > 0) {
            setNewsData();
        } else {
            rLaytHead.setVisibility(View.GONE);
            Toast.makeText(context, "暂无新闻数据", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 渲染数据
     */
    private void setNewsData() {
        headNews = newsList.get(0);
        txtHeadNewsTitle.setText(headNews.getTitle());
        Glide.with(context)
                .load(Url.HOST + headNews.getNewsImg())
                .placeholder(new ColorDrawable(context.getResources().getColor(R.color.grayLightBg)))
                .error(new ColorDrawable(context.getResources().getColor(R.color.grayLightBg)))
                .into(imgViHead);

        newsList.remove(0); //移除headNews后，作为接下来listview的数据
        newsAdapter = new NewsAdapter(context, newsList, NewsAdapter.ITEM_VIEW_NEWS);
        lvNews.setAdapter(newsAdapter);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollView.scrollTo(0, 0);
            }
        }, 100);
    }

    @OnClick({R.id.imgViBack, R.id.rLaytHead})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.imgViBack:
                finish();
                break;
            case R.id.rLaytHead:
                intent = new Intent(context, NewsDetailActivity.class);
                intent.putExtra(Const.KEY_TITLE, "新闻");
                intent.putExtra(Const.KEY_OBJECT, headNews);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @OnItemClick(R.id.lvNews)
    public void onItemClick(int position) {
        Intent intent = new Intent(context, NewsDetailActivity.class);
        intent.putExtra(Const.KEY_TITLE, "新闻");
        intent.putExtra(Const.KEY_OBJECT, newsList.get(position));
        startActivity(intent);
    }

}

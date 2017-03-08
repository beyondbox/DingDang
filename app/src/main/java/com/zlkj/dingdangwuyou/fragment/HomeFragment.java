package com.zlkj.dingdangwuyou.fragment;

import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zlkj.dingdangwuyou.R;
import com.zlkj.dingdangwuyou.activity.NewsAllActivity;
import com.zlkj.dingdangwuyou.activity.NewsDetailActivity;
import com.zlkj.dingdangwuyou.activity.SocialAllActivity;
import com.zlkj.dingdangwuyou.activity.SocialDetailActivity;
import com.zlkj.dingdangwuyou.adapter.NewsAdapter;
import com.zlkj.dingdangwuyou.adapter.SocialActivityGridAdapter;
import com.zlkj.dingdangwuyou.base.BaseFragment;
import com.zlkj.dingdangwuyou.entity.News;
import com.zlkj.dingdangwuyou.entity.SocialActivity;
import com.zlkj.dingdangwuyou.net.MyHttpClient;
import com.zlkj.dingdangwuyou.net.Url;
import com.zlkj.dingdangwuyou.utils.Const;
import com.zlkj.dingdangwuyou.utils.DisplayUtil;
import com.zlkj.dingdangwuyou.utils.GsonUtil;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * 首页
 * Created by Botx on 2016/10/19.
 */

public class HomeFragment extends BaseFragment {

    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.imgViBack)
    ImageView imgViBack;
    @BindView(R.id.ptrLaytHome)
    PtrClassicFrameLayout ptrLaytHome;
    @BindView(R.id.scrollViHome)
    ScrollView scrollViHome;
    @BindView(R.id.lvHotspot)
    ListView lvHotspot;
    @BindView(R.id.lvImportantNews)
    ListView lvImportantNews;
    @BindView(R.id.gridViSocial)
    GridView gridViSocial;

    private List<News> newsList; //所有新闻
    private List<News> hotSpotList; //社会热点
    private List<News> importantNewsList; //社会要闻
    private NewsAdapter hotSpotAdapter;
    private NewsAdapter importantNewsAdapter;

    private List<SocialActivity> socialList; //所有公益活动
    private List<SocialActivity> subSocialList; //公益活动子集
    private SocialActivityGridAdapter socialAdapter;

    private int phoneWidth; //屏幕的宽度
    private int threadCount = 2; //网络请求线程数

    @Override
    protected int getLayoutId() {
        return R.layout.frag_home;
    }

    @Override
    protected void initData() {
        txtTitle.setText("首页");
        imgViBack.setVisibility(View.INVISIBLE);

        setRefreshLayout();
        setListView();

        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        phoneWidth = dm.widthPixels;

        pDialog.show();
        getNewsData();
        getSocialActivity();
    }

    /**
     * 设置下拉刷新
     */
    private void setRefreshLayout() {
        ptrLaytHome.setLastUpdateTimeRelateObject(this);
        ptrLaytHome.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                threadCount = 2;
                getNewsData();
                getSocialActivity();
            }
        });
    }


    private void setListView() {
        newsList = new ArrayList<News>();
        socialList = new ArrayList<SocialActivity>();

        hotSpotList = new ArrayList<News>();
        hotSpotAdapter = new NewsAdapter(context, hotSpotList, NewsAdapter.ITEM_VIEW_HOTSPOT);
        lvHotspot.setAdapter(hotSpotAdapter);

        importantNewsList = new ArrayList<News>();
        importantNewsAdapter = new NewsAdapter(context, importantNewsList, NewsAdapter.ITEM_VIEW_NEWS);
        lvImportantNews.setAdapter(importantNewsAdapter);

        subSocialList = new ArrayList<SocialActivity>();
        socialAdapter = new SocialActivityGridAdapter(context, subSocialList);
        gridViSocial.setAdapter(socialAdapter);
    }

    /**
     * 获取新闻
     */
    private void getNewsData() {
        MyHttpClient.getInstance().post(Url.URL_GET_NEWS, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String jsonStr = new String(responseBody);
                try {
                    JSONArray jsonArr = new JSONArray(jsonStr);
                    newsList = GsonUtil.getEntityList(jsonArr.toString(), News.class);
                    int totalSize = newsList.size();

                    /*
                     *动态设置社会热点和社会要闻的条数
                     */
                    if (totalSize > 0) {
                        switch (totalSize) {
                            case 1:
                                hotSpotList = newsList;
                                break;
                            case 2:
                                hotSpotList = newsList.subList(0, 1);
                                importantNewsList = newsList.subList(1, 2);
                                break;
                            default:
                                filterNewsList();
                                totalSize = newsList.size();

                                hotSpotList = newsList.subList(0, 2);
                                importantNewsList = newsList.subList(2, totalSize);
                                if (importantNewsList.size() > 3) {
                                    importantNewsAdapter.setDisplayNum(3);
                                }
                                break;
                        }

                        hotSpotAdapter.refresh(hotSpotList);
                        importantNewsAdapter.refresh(importantNewsList);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(context, context.getResources().getString(R.string.request_fail), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                synchronized (HomeFragment.this) {
                    threadCount--;
                    if (threadCount == 0) {
                        pDialog.dismiss();
                        ptrLaytHome.refreshComplete();
                    }
                }
            }
        });
    }

    /**
     * 获取公益活动数据
     */
    private void getSocialActivity() {
        MyHttpClient.getInstance().post(Url.URL_GET_SOCIAL_ACTIVITY, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String jsonStr = new String(responseBody);
                try {
                    JSONArray jsonArr = new JSONArray(jsonStr);
                    socialList = GsonUtil.getEntityList(jsonArr.toString(), SocialActivity.class);
                    filterSocialList();

                    int totalSize = socialList.size();
                    if (totalSize > 0) {
                        int columnWidth = 0; //列的宽度
                        int padding = DisplayUtil.dip2px(context, 20); //两边的边距
                        int space = DisplayUtil.dip2px(context, 8); //列之间的距离

                        /*
                         *动态设置GridView，首页最多显示3条公益活动
                         */
                        switch (totalSize) {
                            case 1:
                                subSocialList = socialList;
                                gridViSocial.setNumColumns(1);
                                columnWidth = phoneWidth - padding;
                                break;
                            case 2:
                                subSocialList = socialList;
                                gridViSocial.setNumColumns(2);
                                columnWidth = (phoneWidth - padding - space) / 2;
                                break;
                            default:
                                subSocialList = socialList.subList(0, 3);
                                gridViSocial.setNumColumns(3);
                                columnWidth = (phoneWidth - padding - (space * 2)) / 3;
                                break;
                        }

                        socialAdapter.setColumnWidth(columnWidth);
                        socialAdapter.refresh(subSocialList);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(context, context.getResources().getString(R.string.request_fail), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                synchronized (HomeFragment.this) {
                    threadCount--;
                    if (threadCount == 0) {
                        pDialog.dismiss();
                        ptrLaytHome.refreshComplete();
                    }
                }
            }
        });
    }


    /**
     * 社会新闻过滤
     */
    private void filterNewsList() {
        for (int i = 0; i < newsList.size(); i++) {
            News news = newsList.get(i);
            if (news.getNewsImg().equals("/upload/44bd3b12-a309-47d4-91cc-a941fb2d61a6.jpg")) {
                newsList.remove(i);
                break;
            }
        }
    }

    /**
     * 公益报名过滤
     */
    private void filterSocialList() {
        if (socialList.size() == 0) {
            return;
        }

        for (int i = 0; i < socialList.size(); i++) {
            SocialActivity socialActivity = socialList.get(i);
            if (socialActivity.getG_imgurl().equals("/upload/9b1f7344-c617-4109-846a-e11fc9b19073.jpg")) {
                socialList.remove(i);
                break;
            }
        }
    }


    @OnItemClick({R.id.lvHotspot, R.id.lvImportantNews})
    public void onNewsItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(context, NewsDetailActivity.class);
        switch (parent.getId()) {
            case R.id.lvHotspot: //社会热点
                intent.putExtra(Const.KEY_OBJECT, hotSpotList.get(position));
                break;
            case R.id.lvImportantNews: //社会要闻
                intent.putExtra(Const.KEY_OBJECT, importantNewsList.get(position));
                break;
            default:
                break;
        }
        startActivity(intent);
    }


    @OnItemClick(R.id.gridViSocial)
    public void onSocialItemClick(int position) {
        Intent intent = new Intent(context, SocialDetailActivity.class);
        intent.putExtra(Const.KEY_OBJECT, subSocialList.get(position));
        startActivity(intent);
    }


    @OnClick({R.id.txtShowAllNews1, R.id.txtShowAllNews2, R.id.txtShowAllSocial})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.txtShowAllNews1: //查看全部新闻
                intent = new Intent(context, NewsAllActivity.class);
                intent.putExtra(Const.KEY_OBJECT, (Serializable) newsList);
                startActivity(intent);
                break;
            case R.id.txtShowAllNews2: //查看全部新闻
                intent = new Intent(context, NewsAllActivity.class);
                intent.putExtra(Const.KEY_OBJECT, (Serializable) newsList);
                startActivity(intent);
                break;
            case R.id.txtShowAllSocial: //查看全部公益活动
                intent = new Intent(context, SocialAllActivity.class);
                intent.putExtra(Const.KEY_OBJECT, (Serializable)socialList);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (getView() != null) {
            getView().setVisibility(menuVisible ? View.VISIBLE : View.GONE);
        }
    }

}

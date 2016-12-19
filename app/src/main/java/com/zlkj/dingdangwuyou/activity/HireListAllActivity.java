package com.zlkj.dingdangwuyou.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zlkj.dingdangwuyou.R;
import com.zlkj.dingdangwuyou.adapter.HireListAdapter;
import com.zlkj.dingdangwuyou.adapter.SelfCommendListAdapter;
import com.zlkj.dingdangwuyou.base.BaseActivity;
import com.zlkj.dingdangwuyou.entity.HireInfo;
import com.zlkj.dingdangwuyou.entity.SelfCommend;
import com.zlkj.dingdangwuyou.fragment.HireFragment;
import com.zlkj.dingdangwuyou.net.MyHttpClient;
import com.zlkj.dingdangwuyou.net.Url;
import com.zlkj.dingdangwuyou.utils.Const;
import com.zlkj.dingdangwuyou.utils.GsonUtil;
import com.zlkj.dingdangwuyou.utils.UserUtil;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * 招聘列表全部
 * Created by btx on 2016/11/17.
 */

public class HireListAllActivity extends BaseActivity {
    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.ptrLayt)
    PtrClassicFrameLayout ptrLayt;
    @BindView(R.id.lvData)
    ListView lvData;

    private  List<SelfCommend> commendHistoryList; //自荐历史
    private List<HireInfo> hireLatestList; //最新招聘
    private List<HireInfo> hireHistoryList; //招聘历史
    private List<SelfCommend> commendLatestList; //最新自荐

    private SelfCommendListAdapter commendHistoryAdapter;
    private HireListAdapter hireLatestAdapter;
    private HireListAdapter hireHistoryAdapter;
    private SelfCommendListAdapter commendLatestAdapter;

    private int matchType; //匹配类型
    private int userType; //用户类型

    private int pageIndex = 1;
    private int pageSize = 12;

    private boolean hasMore = false;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_hire_list_all;
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        txtTitle.setText(intent.getStringExtra(Const.KEY_TITLE));
        matchType = intent.getIntExtra(Const.KEY_MATCH_TYPE, Const.MATCH_TYPE_LATEST);
        userType = UserUtil.getUserType();

        setRefreshLayout();
        setListView();

        pDialog.show();
        getData();
    }

    private void setListView() {
        commendHistoryList = new ArrayList<SelfCommend>();
        hireLatestList = new ArrayList<HireInfo>();
        hireHistoryList = new ArrayList<HireInfo>();
        commendLatestList = new ArrayList<SelfCommend>();

        commendHistoryAdapter = new SelfCommendListAdapter(context, commendHistoryList);
        hireLatestAdapter = new HireListAdapter(context, hireLatestList);
        hireHistoryAdapter = new HireListAdapter(context, hireHistoryList);
        commendLatestAdapter = new SelfCommendListAdapter(context, commendLatestList);

        if (matchType == Const.MATCH_TYPE_HISTORY) {
            if (userType == Const.USER_TYPE_PER) {
                lvData.setAdapter(commendHistoryAdapter);
            } else {
                lvData.setAdapter(hireHistoryAdapter);
            }
        } else {
            if (userType == Const.USER_TYPE_PER) {
                lvData.setAdapter(hireLatestAdapter);
            } else {
                lvData.setAdapter(commendLatestAdapter);
            }
        }
    }

    /**
     * 设置下拉刷新控件
     */
    private void setRefreshLayout() {
        ptrLayt.setLastUpdateTimeRelateObject(this);
        ptrLayt.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                pageIndex = 1;
                getData();
            }
        });
        ptrLayt.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void loadMore() {
                pageIndex++;
                getData();
            }
        });
    }

    /**
     * 加载数据
     */
    private void getData(){
        if (matchType == Const.MATCH_TYPE_HISTORY) {
            getHistoryData();
        } else {
            getLatestData();
        }
    }

    /**
     * 获取历史信息
     */
    private void getHistoryData() {
        RequestParams params = new RequestParams();
        String url = "";
        if (userType == Const.USER_TYPE_PER) {
            params.put("ui_id", UserUtil.getUserInfo().getId());
            url = Url.URL_USER_MATCH_HISTORY;
        } else {
            params.put("cpif_id", UserUtil.getUserInfo().getId());
            url = Url.URL_COM_MATCH_HISTORY;
        }
        params.put("pageIndex", pageIndex);
        params.put("pageSize", pageSize);

        MyHttpClient.getInstance().post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String jsonStr = new String(responseBody);
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    if (jsonObj.has("items")) {
                        parseJsonHistory(jsonObj);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(context, context.getResources().getString(R.string.request_fail), Toast.LENGTH_SHORT).show();
                if (pageIndex > 1) {
                    pageIndex--;
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                pDialog.dismiss();
                ptrLayt.refreshComplete();
                if (ptrLayt.isLoadMoreEnable()) {
                    ptrLayt.loadMoreComplete(hasMore);
                }
            }
        });
    }

    /**
     * 获取最新信息
     */
    private void getLatestData() {
        RequestParams params = new RequestParams();
        String url = "";
        if (userType == Const.USER_TYPE_PER) {
            url = Url.URL_USER_HIRE_LATEST;
        } else {
            url = Url.URL_COM_COMMEND_LATEST;
        }
        params.put("pageIndex", pageIndex);
        params.put("pageSize", pageSize);

        MyHttpClient.getInstance().post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String jsonStr = new String(responseBody);
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    if (jsonObj.has("items")) {
                        parseJsonLatest(jsonObj);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(context, context.getResources().getString(R.string.request_fail), Toast.LENGTH_SHORT).show();
                if (pageIndex > 1) {
                    pageIndex--;
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                pDialog.dismiss();
                ptrLayt.refreshComplete();
                if (ptrLayt.isLoadMoreEnable()) {
                    ptrLayt.loadMoreComplete(hasMore);
                }
            }
        });
    }

    /**
     * 解析json数据（历史信息）
     */
    private void parseJsonHistory(JSONObject jsonObj) {
        try {
            JSONArray jsonArr = jsonObj.getJSONArray("items");
            //个人用户
            if (userType == Const.USER_TYPE_PER) {
                List<SelfCommend> list = GsonUtil.getEntityList(jsonArr.toString(), SelfCommend.class);
                int total = jsonObj.optInt("total");
                if (pageIndex == 1) {
                    commendHistoryList.clear();
                    if (list.size() < total) {
                        ptrLayt.setLoadMoreEnable(true);
                        hasMore = true;
                    } else {
                        ptrLayt.setLoadMoreEnable(false);
                        hasMore = false;
                    }
                }

                commendHistoryList.addAll(list);
                commendHistoryAdapter.refresh(commendHistoryList);

                if (commendHistoryList.size() == total) {
                    hasMore = false;
                }

            //企业用户
            } else {
                List<HireInfo> list = GsonUtil.getEntityList(jsonArr.toString(), HireInfo.class);
                int total = jsonObj.optInt("total");
                if (pageIndex == 1) {
                    hireHistoryList.clear();
                    if (list.size() < total) {
                        ptrLayt.setLoadMoreEnable(true);
                        hasMore = true;
                    } else {
                        ptrLayt.setLoadMoreEnable(false);
                        hasMore = false;
                    }
                }

                hireHistoryList.addAll(list);
                hireHistoryAdapter.refresh(hireHistoryList);

                if (hireHistoryList.size() == total) {
                    hasMore = false;
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析json数据（最新信息）
     */
    private void parseJsonLatest(JSONObject jsonObj) {
        try {
            JSONArray jsonArr = jsonObj.getJSONArray("items");
            //个人用户
            if (userType == Const.USER_TYPE_PER) {
                List<HireInfo> list = GsonUtil.getEntityList(jsonArr.toString(), HireInfo.class);
                int total = jsonObj.optInt("total");
                if (pageIndex == 1) {
                    hireLatestList.clear();
                    if (list.size() < total) {
                        ptrLayt.setLoadMoreEnable(true);
                        hasMore = true;
                    } else {
                        ptrLayt.setLoadMoreEnable(false);
                        hasMore = false;
                    }
                }

                hireLatestList.addAll(list);
                hireLatestAdapter.refresh(hireLatestList);

                if (hireLatestList.size() == total) {
                    hasMore = false;
                }

            //企业用户
            } else {
                List<SelfCommend> list = GsonUtil.getEntityList(jsonArr.toString(), SelfCommend.class);
                int total = jsonObj.optInt("total");
                if (pageIndex == 1) {
                    commendLatestList.clear();
                    if (list.size() < total) {
                        ptrLayt.setLoadMoreEnable(true);
                        hasMore = true;
                    } else {
                        ptrLayt.setLoadMoreEnable(false);
                        hasMore = false;
                    }
                }

                commendLatestList.addAll(list);
                commendLatestAdapter.refresh(commendLatestList);

                if (commendLatestList.size() == total) {
                    hasMore = false;
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
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

    @OnItemClick(R.id.lvData)
    public void onItemClick(int position) {
        Intent intent = null;
        if (matchType == Const.MATCH_TYPE_HISTORY) {
            if (userType == Const.USER_TYPE_PER) {
                intent = new Intent(context, SelfCommendDetailActivity.class);
                intent.putExtra(Const.KEY_OBJECT, commendHistoryList.get(position));
                intent.putExtra(Const.KEY_NEED_MENU, false);
            } else {
                intent = new Intent(context, PositionDetailActivity.class);
                intent.putExtra(Const.KEY_OBJECT, hireHistoryList.get(position));
                intent.putExtra(Const.KEY_NEED_MENU, false);
            }

        } else {
            if (userType == Const.USER_TYPE_PER) {
                intent = new Intent(context, PositionDetailActivity.class);
                intent.putExtra(Const.KEY_OBJECT, hireLatestList.get(position));
                intent.putExtra(Const.KEY_NEED_MENU, true);
            } else {
                intent = new Intent(context, SelfCommendDetailActivity.class);
                intent.putExtra(Const.KEY_OBJECT, commendLatestList.get(position));
                intent.putExtra(Const.KEY_NEED_MENU, true);
            }
        }

        startActivity(intent);
    }

}

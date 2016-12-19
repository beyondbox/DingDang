package com.zlkj.dingdangwuyou.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zlkj.dingdangwuyou.R;
import com.zlkj.dingdangwuyou.activity.HireListAllActivity;
import com.zlkj.dingdangwuyou.activity.PositionDetailActivity;
import com.zlkj.dingdangwuyou.activity.ReleasePositionActivity;
import com.zlkj.dingdangwuyou.activity.SelfCommendDetailActivity;
import com.zlkj.dingdangwuyou.adapter.HireListAdapter;
import com.zlkj.dingdangwuyou.adapter.SelfCommendListAdapter;
import com.zlkj.dingdangwuyou.base.BaseFragment;
import com.zlkj.dingdangwuyou.entity.HireInfo;
import com.zlkj.dingdangwuyou.entity.SelfCommend;
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
 * 招贤纳士
 * Created by Botx on 2016/10/19.
 */

public class HireFragment extends BaseFragment {

    @BindView(R.id.imgViBack)
    ImageView imgViBack;
    @BindView(R.id.txtRight)
    TextView txtRight;
    @BindView(R.id.txtHistoryName)
    TextView txtHistoryName;
    @BindView(R.id.txtLatestName)
    TextView txtLatestName;
    @BindView(R.id.ptrLaytHire)
    PtrClassicFrameLayout ptrLaytHire;
    @BindView(R.id.rdoGroup)
    RadioGroup rdoGroup;
    @BindView(R.id.lvHistory)
    ListView lvHistory;
    @BindView(R.id.lvLatest)
    ListView lvLatest;

    private List<HireInfo> hireLatestList; //最新招聘
    private List<SelfCommend> commendHistoryList; //历史自荐
    private List<HireInfo> hireHistoryList; //历史招聘
    private List<SelfCommend> commendLatestList; //最新自荐

    private HireListAdapter hirelatestAdapter;
    private SelfCommendListAdapter commendHistoryAdapter;
    private HireListAdapter hireHistoryAdapter;
    private SelfCommendListAdapter commendLatestAdapter;

    private int userType;
    private int threadCount = 2;

    @Override
    protected int getLayoutId() {
        return R.layout.frag_hire;
    }

    @Override
    protected void initData() {
        imgViBack.setVisibility(View.INVISIBLE);
        userType = UserUtil.getUserType();
        if (userType == Const.USER_TYPE_PER) {
            txtRight.setText("发布自荐");
            txtHistoryName.setText("历史自荐信息");
            txtLatestName.setText("最新招募信息");
        }

        setRefreshLayout();
        setListView();

        //rdoGroup.check(R.id.rdoBtnFullTime);

        pDialog.show();
        getHistoryData();
        getLatestData();
    }

    private void setListView() {
        hireLatestList = new ArrayList<HireInfo>();
        commendHistoryList = new ArrayList<SelfCommend>();
        hireHistoryList = new ArrayList<HireInfo>();
        commendLatestList = new ArrayList<SelfCommend>();

        hirelatestAdapter = new HireListAdapter(context, hireLatestList);
        commendHistoryAdapter = new SelfCommendListAdapter(context, commendHistoryList);
        hireHistoryAdapter = new HireListAdapter(context, hireHistoryList);
        commendLatestAdapter = new SelfCommendListAdapter(context, commendLatestList);

        if (userType == Const.USER_TYPE_PER) {
            lvHistory.setAdapter(commendHistoryAdapter);
            lvLatest.setAdapter(hirelatestAdapter);
        } else {
            lvHistory.setAdapter(hireHistoryAdapter);
            lvLatest.setAdapter(commendLatestAdapter);
        }
    }

    /**
     * 设置下拉刷新
     */
    private void setRefreshLayout() {
        ptrLaytHire.setLastUpdateTimeRelateObject(this);
        ptrLaytHire.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                threadCount = 2;
                getHistoryData();
                getLatestData();
            }
        });
    }

    @OnClick({R.id.txtRight, R.id.txtShowAllHistory, R.id.txtShowAllLatest})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.txtRight:
                intent = new Intent(context, ReleasePositionActivity.class);
                startActivityForResult(intent, 0);
                break;
            case R.id.txtShowAllHistory:
                intent = new Intent(context, HireListAllActivity.class);
                intent.putExtra(Const.KEY_MATCH_TYPE, Const.MATCH_TYPE_HISTORY);
                if (userType == Const.USER_TYPE_PER) {
                    intent.putExtra(Const.KEY_TITLE, "历史自荐信息");
                } else {
                    intent.putExtra(Const.KEY_TITLE, "历史招募信息");
                }
                startActivity(intent);
                break;
            case R.id.txtShowAllLatest:
                intent = new Intent(context, HireListAllActivity.class);
                intent.putExtra(Const.KEY_MATCH_TYPE, Const.MATCH_TYPE_LATEST);
                if (userType == Const.USER_TYPE_PER) {
                    intent.putExtra(Const.KEY_TITLE, "最新招募信息");
                } else {
                    intent.putExtra(Const.KEY_TITLE, "最新自荐信息");
                }
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @OnItemClick({R.id.lvHistory, R.id.lvLatest})
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = null;
        switch (parent.getId()) {
            case R.id.lvHistory:
                if (userType == Const.USER_TYPE_PER) {
                    intent = new Intent(context, SelfCommendDetailActivity.class);
                    intent.putExtra(Const.KEY_OBJECT, commendHistoryList.get(position));
                    intent.putExtra(Const.KEY_NEED_MENU, false);
                } else {
                    intent = new Intent(context, PositionDetailActivity.class);
                    intent.putExtra(Const.KEY_OBJECT, hireHistoryList.get(position));
                    intent.putExtra(Const.KEY_NEED_MENU, false);
                }
                break;
            case R.id.lvLatest:
                if (userType == Const.USER_TYPE_PER) {
                    intent = new Intent(context, PositionDetailActivity.class);
                    intent.putExtra(Const.KEY_OBJECT, hireLatestList.get(position));
                    intent.putExtra(Const.KEY_NEED_MENU, true);
                } else {
                    intent = new Intent(context, SelfCommendDetailActivity.class);
                    intent.putExtra(Const.KEY_OBJECT, commendLatestList.get(position));
                    intent.putExtra(Const.KEY_NEED_MENU, true);
                }
                break;
            default:
                break;
        }

        startActivity(intent);
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
            params.put("pageIndex", 1);
            params.put("pageSize", 3);
            url = Url.URL_COM_MATCH_HISTORY;
        }

        MyHttpClient.getInstance().post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String jsonStr = new String(responseBody);
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    if (jsonObj.has("items")) {
                        JSONArray jsonArr = jsonObj.getJSONArray("items");
                        if (userType == Const.USER_TYPE_PER) {
                            commendHistoryList = GsonUtil.getEntityList(jsonArr.toString(), SelfCommend.class);
                            commendHistoryAdapter.refresh(commendHistoryList);
                        } else {
                            hireHistoryList = GsonUtil.getEntityList(jsonArr.toString(), HireInfo.class);
                            hireHistoryAdapter.refresh(hireHistoryList);
                        }
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
                synchronized (HireFragment.this) {
                    threadCount--;
                    if (threadCount == 0) {
                        pDialog.dismiss();
                        ptrLaytHire.refreshComplete();
                    }
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
            params.put("pageIndex", 1);
            params.put("pageSize", 3);
            url = Url.URL_USER_HIRE_LATEST;
        } else {
            url = Url.URL_COM_COMMEND_LATEST;
        }

        MyHttpClient.getInstance().post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String jsonStr = new String(responseBody);
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    if (jsonObj.has("items")) {
                        JSONArray jsonArr = jsonObj.getJSONArray("items");
                        if (userType == Const.USER_TYPE_PER) {
                            hireLatestList = GsonUtil.getEntityList(jsonArr.toString(), HireInfo.class);
                            hirelatestAdapter.refresh(hireLatestList);
                        } else {
                            commendLatestList = GsonUtil.getEntityList(jsonArr.toString(), SelfCommend.class);
                            commendLatestAdapter.refresh(commendLatestList);
                        }
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
                synchronized (HireFragment.this) {
                    threadCount--;
                    if (threadCount == 0) {
                        pDialog.dismiss();
                        ptrLaytHire.refreshComplete();
                    }
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Const.RESULT_CODE_RELEASE_SUCCEED) {
            pDialog.show();
            threadCount = 2;
            getHistoryData();
            getLatestData();
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

package com.zlkj.dingdangwuyou.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zlkj.dingdangwuyou.R;
import com.zlkj.dingdangwuyou.base.BaseActivity;
import com.zlkj.dingdangwuyou.entity.User;
import com.zlkj.dingdangwuyou.fragment.TaskFragment;
import com.zlkj.dingdangwuyou.fragment.HireFragment;
import com.zlkj.dingdangwuyou.fragment.HomeFragment;
import com.zlkj.dingdangwuyou.fragment.MyCenterFragment;
import com.zlkj.dingdangwuyou.net.MyHttpClient;
import com.zlkj.dingdangwuyou.net.Url;
import com.zlkj.dingdangwuyou.utils.Const;
import com.zlkj.dingdangwuyou.utils.GsonUtil;
import com.zlkj.dingdangwuyou.utils.JsonUtil;
import com.zlkj.dingdangwuyou.utils.UserUtil;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Botx on 2016/10/14.
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.rLaytHome)
    RelativeLayout rLaytHome; //首页
    @BindView(R.id.rLaytHire)
    RelativeLayout rLaytHire; //招贤纳士
    @BindView(R.id.rLaytGold)
    RelativeLayout rLaytGold; //日进斗金
    @BindView(R.id.rLaytMyCenter)
    RelativeLayout rLaytMyCenter; //个人中心

    @BindView(R.id.fLaytContent)
    FrameLayout fLaytContent; //Fragment容器

    private List<RelativeLayout> tabList;
    private List<Fragment> fragList;
    private FragAdapter fragAdapter;
    private int currIndex = 0;
    private int lastIndex = -1;

    private long lastClickTime = 0;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        boolean needLogin = intent.getBooleanExtra(Const.KEY_NEED_LOGIN, false);
        if (needLogin) {
            login();
        }

        tabList = new ArrayList<RelativeLayout>();
        tabList.add(rLaytHome);
        tabList.add(rLaytHire);
        tabList.add(rLaytGold);
        tabList.add(rLaytMyCenter);

        fragList = new ArrayList<Fragment>();
        fragList.add(new HomeFragment());
        fragList.add(new HireFragment());
        fragList.add(new TaskFragment());
        fragList.add(new MyCenterFragment());

        fragAdapter = new FragAdapter(getSupportFragmentManager());

        //默认显示首页
        rLaytHome.performClick();
    }

    @OnClick({R.id.rLaytHome, R.id.rLaytHire, R.id.rLaytGold, R.id.rLaytMyCenter})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rLaytHome:
                currIndex = 0;
                break;
            case R.id.rLaytHire:
                currIndex = 1;
                break;
            case R.id.rLaytGold:
                currIndex = 2;
                break;
            case R.id.rLaytMyCenter:
                currIndex = 3;
                break;
        }

        //招贤纳士需要先登录
        if (currIndex == 1) {
            if (!UserUtil.isLogin()) {
                Intent intent = new Intent(context, LoginChooseActivity.class);
                startActivityForResult(intent, Const.REQUEST_CODE_HIRE);
                return;
            }
        }

        if (lastIndex != currIndex) {
            if (lastIndex != -1) {
                tabList.get(lastIndex).setSelected(false);
            }
            tabList.get(currIndex).setSelected(true);
            switchFragment(currIndex);
            lastIndex = currIndex;
        }
    }

    /**
     * Fragment管理适配器
     */
    private class FragAdapter extends FragmentPagerAdapter {

        public FragAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragList.get(position);
        }

        @Override
        public int getCount() {
            return fragList.size();
        }
    }

    /**
     * 切换Fragment
     * @param position
     */
    private void switchFragment(int position) {
        Fragment fragment = (Fragment) fragAdapter.instantiateItem(fLaytContent, position);
        fragAdapter.setPrimaryItem(fLaytContent, position, fragment);
        fragAdapter.finishUpdate(fLaytContent);
    }

    @Override
    public void onBackPressed() {
        long currTime = System.currentTimeMillis();
        if ((currTime - lastClickTime) > 2000) {
            Toast.makeText(this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
            lastClickTime = currTime;
        } else {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消所有网络请求
        MyHttpClient.getInstance().cancelAllRequests(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Const.REQUEST_CODE_HIRE && resultCode == Const.RESULT_CODE_LOGIN_SUCCEED) {
            rLaytHire.performClick();
        }
    }

    /**
     * 登录
     */
    private void login() {
        RequestParams params = new RequestParams();
        int userType = UserUtil.getUserType();

        if (userType == Const.USER_TYPE_PER) {
            params.put("username", UserUtil.getUserInfo().getUsername());
            params.put("password", UserUtil.getUserPwd());
        } else {
            params.put("cusername", UserUtil.getUserInfo().getCusername());
            params.put("cpassword", UserUtil.getUserPwd());
        }

        String url = "";
        if (userType == Const.USER_TYPE_COM) {
            url = Url.URL_LOGIN_COM;
        } else if(userType == Const.USER_TYPE_PER) {
            url = Url.URL_LOGIN_PER;
        }

        MyHttpClient.getInstance().post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String temp = new String(responseBody);
                String [] arr = temp.split("\\}\\]\\}");
                String jsonStr = arr[0];
                if (!jsonStr.endsWith("}")) {
                    jsonStr = jsonStr + "}]}";
                }

                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    boolean result = Boolean.valueOf(jsonObj.getString("result"));
                    if (result) { //登录成功
                        UserUtil.saveUserInfo(GsonUtil.getEntity(JsonUtil.getDataJsonObj(jsonObj).toString(), User.class));
                        Intent intent = new Intent(Const.ACTION_LOGIN_SUCCESS);
                        context.sendBroadcast(intent);
                    } else { //登录失败
                        UserUtil.logout();
                        Toast.makeText(context, "登录失败，请重新登录", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(context, LoginChooseActivity.class));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                UserUtil.logout();
                Toast.makeText(context, "登录失败，请重新登录", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(context, LoginChooseActivity.class));
            }

            @Override
            public void onFinish() {
            }
        });
    }


}

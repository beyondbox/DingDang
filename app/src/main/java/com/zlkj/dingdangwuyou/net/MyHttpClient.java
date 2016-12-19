package com.zlkj.dingdangwuyou.net;

import com.loopj.android.http.AsyncHttpClient;
import com.zlkj.dingdangwuyou.utils.Const;

/**
 * Created by Botx on 2016/11/2.
 */

public class MyHttpClient {

    private static AsyncHttpClient aHttpClient = new AsyncHttpClient();

    private MyHttpClient() {};

    public static AsyncHttpClient getInstance() {
        aHttpClient.setTimeout(Const.CONNECT_TIME_OUT);
        return aHttpClient;
    }

}

package com.zlkj.dingdangwuyou.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Botx on 2016/11/4.
 */

public class JsonUtil {

    /**
     * 取出“data”数组中的第一个JsonObject
     * @param jsonObj
     * @return
     */
    public static JSONObject getDataJsonObj(JSONObject jsonObj) {
        JSONArray jsonArr = new JSONArray();
        JSONObject dataObj = new JSONObject();
        try {
            jsonArr = jsonObj.getJSONArray("data");
            if (jsonArr.length() > 0) {
                dataObj = jsonArr.getJSONObject(0);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataObj;
    }

}

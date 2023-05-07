package com.example.garbagesorting.util.translate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TransApi {
    private static final String TRANS_API_HOST = "http://api.fanyi.baidu.com/api/trans/vip/translate";

    private static String appid = "20220323001136298";
    private static String securityKey ="YGGBj4nOcQyF0gVcCsN4";

 /*   public TransApi(String appid, String securityKey) {
        this.appid = appid;
        this.securityKey = securityKey;
    }*/


    /**
     * 转英语
     * @param query
     * @return
     */
    public static String getTransResultToEn(String query) {
        Map<String, String> params = buildParams(query, "auto", "en");
        return HttpGet.get(TRANS_API_HOST, params);
    }

    /**
     * 转中文
     * @param query
     * @return
     */
    public static String getTransResultToZh(String query) {
        Map<String, String> params = buildParams(query, "auto", "zh");
        return HttpGet.get(TRANS_API_HOST, params);
    }

    public static String getTransResultToEn(String query, String from, String to) {
        Map<String, String> params = buildParams(query, from, to);
        return HttpGet.get(TRANS_API_HOST, params);
    }

    private static Map<String, String> buildParams(String query, String from, String to) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("q", query);
        params.put("from", from);
        params.put("to", to);

        params.put("appid", appid);

        // 随机数
        String salt = String.valueOf(System.currentTimeMillis());
        params.put("salt", salt);

        // 签名
        String src = appid + query + salt + securityKey; // 加密前的原文
        params.put("sign", MD5.md5(src));

        return params;
    }


    public static Map<String, Object> buildParams2(String query, String from, String to) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("q", query);
        params.put("from", from);
        params.put("to", to);

        params.put("appid", appid);

        // 随机数
        String salt = String.valueOf(System.currentTimeMillis());
        params.put("salt", salt);

        // 签名
        String src = appid + query + salt + securityKey; // 加密前的原文
        params.put("sign", MD5.md5(src));

        return params;
    }

}

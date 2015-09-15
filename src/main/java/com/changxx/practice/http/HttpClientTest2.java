package com.changxx.practice.http;

import com.changxx.practice.util.MD5Util;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author changxiangxiang
 * @date 2014年8月6日 下午5:02:44
 * @description
 * @since sprint2
 */
public class HttpClientTest2 {

    public static void main(String[] args) throws IOException {
        String requestPath = "http://pay.m.jd.care/index.action?functionId=baitiao4MobileCharges";
        String appId = "jd_m_chongzhi";
        String appkey = "Wadfh34eruip";

        Map<String, String> query = new HashMap<String, String>();
        query.put("appid", appId);
        query.put("pin", "cxx");
        query.put("orderId", "10095405731");
        query.put("orderType", "37");
        query.put("payAmount", "0.01");
        // 时间戳
        query.put("timestamp", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

        String reqPara = JSONUtils.objectToJson(query);
        String signPara = reqPara + appkey;
        // 签名 获取sign
        String sign = MD5Util.md5Hex(signPara, "GBK");
        // System.out.println(sign);
        query.put("sign", sign);

        String param = JSONUtils.objectToJson(query);

        System.out.println(param);

        HttpClient httpClient = HttpClientSupport.getHttpClient();
        HttpPost post = new HttpPost(requestPath);

        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("body", param));

        post.addHeader("Content-Type", "application/json");

        post.setEntity(new UrlEncodedFormEntity(params));

        // 创建响应处理器处理服务器响应内容
        // 执行请求并获取结果
        String responseBody = httpClient.execute(post, new BasicResponseHandler());
        System.out.println("----------------------------------------");
        System.out.println(responseBody);
        System.out.println("----------------------------------------");
    }

}



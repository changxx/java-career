package com.changxx.practice.http;

import com.changxx.practice.util.MD5Util;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author changxiangxiang
 * @date 2014年8月6日 下午5:02:44
 * @description
 * @since sprint2
 */
public class HttpClientTest {

    @Test
    public void test1() throws ClientProtocolException, IOException {
        HttpClient httpClient = HttpClientSupport.getHttpClient();

        HttpPost post = new HttpPost("http://passport.mop.com/");

        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("appid", "jd_m_chongzhi"));
        params.add(new BasicNameValuePair("pin", "翔宇vs天下"));
        params.add(new BasicNameValuePair("orderId", "10095405731"));
        params.add(new BasicNameValuePair("orderType", "37"));
        params.add(new BasicNameValuePair("payAmount", "0.01"));
        params.add(new BasicNameValuePair("timestamp", String.valueOf(System.currentTimeMillis())));
        params.add(new BasicNameValuePair("sign", "jd_m_chongzhi"));

        post.addHeader("Content-Type", "application/json");

        post.setEntity(new UrlEncodedFormEntity(params));

        HttpPostRequest request = new HttpPostRequest(httpClient, post);

        // ClientContext.COOKIE_STORE

        // 创建响应处理器处理服务器响应内容
        ResponseHandler<String> responseHandler = new CustomerBasicResponseHandler();
        // 执行请求并获取结果
        String responseBody = httpClient.execute(post, responseHandler);
        System.out.println("----------------------------------------");
        System.out.println(responseBody);
        System.out.println("----------------------------------------");

    }

    @Test
    public void test2() throws ClientProtocolException, IOException {
        HttpClient httpClient = HttpClientSupport.getHttpClient();

        HttpPost post = new HttpPost("http://hi.mop.com/");

        post.addHeader("Cookie", "_ml=751033200452421839621");

        HttpPostRequest request = new HttpPostRequest(httpClient, post);

        // ClientContext.COOKIE_STORE

        // 创建响应处理器处理服务器响应内容
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        // 执行请求并获取结果
        String responseBody = httpClient.execute(post, responseHandler);
        System.out.println("----------------------------------------");
        System.out.println(responseBody);
        System.out.println("----------------------------------------");
    }

    public void test3() throws ClientProtocolException, IOException {
        String requestPath = "http://pay.m.jd.care/index.action?functionId=baitiao4MobileCharges";
        String appId = "jd_m_chongzhi";
        String appkey = "Wadfh34eruip";

        Map<String, String> query = new HashMap<String, String>();
        query.put("appid", appId);
        query.put("pin", "翔宇vs天下");
        query.put("orderId", "10095405731");
        query.put("orderType", "37");
        query.put("payAmount", "0.01");
        // 时间戳
        query.put("timestamp", String.valueOf(System.currentTimeMillis()));

        String reqPara = JSONUtils.objectToJson(query);
        String signPara = reqPara + appkey;
        // 签名 获取sign
        String sign = MD5Util.md5Hex(signPara, "GBK");
        // System.out.println(sign);
        query.put("sign", sign);

        HttpClient httpClient = HttpClientSupport.getHttpClient();
        HttpPost post = new HttpPost(requestPath);

        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        for (String key : query.keySet()) {
            params.add(new BasicNameValuePair(key, query.get(key)));
        }

        post.addHeader("Content-Type", "application/json");

        post.setEntity(new UrlEncodedFormEntity(params));

        // 创建响应处理器处理服务器响应内容
        ResponseHandler<String> responseHandler = new CustomerBasicResponseHandler();
        // 执行请求并获取结果
        String responseBody = httpClient.execute(post, responseHandler);
        System.out.println("----------------------------------------");
        System.out.println(responseBody);
        System.out.println("----------------------------------------");
    }
}



package com.changxx.practice.http;

import com.changxx.practice.http.ms.*;
import org.apache.commons.io.FileUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.joda.time.DateTime;
import org.junit.Test;

import java.io.File;
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
        params.add(new BasicNameValuePair("appid", "xx"));
        params.add(new BasicNameValuePair("pin", "翔宇vs天下"));
        params.add(new BasicNameValuePair("orderId", "10095405731"));
        params.add(new BasicNameValuePair("orderType", "37"));
        params.add(new BasicNameValuePair("payAmount", "0.01"));
        params.add(new BasicNameValuePair("timestamp", String.valueOf(System.currentTimeMillis())));
        params.add(new BasicNameValuePair("sign", "xx"));

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

    public static void main(String[] args) throws JSONException {
        Map<String, Integer> map = new HashMap<String, Integer>();
        String s = "";
        JSONObject jsonObject = new JSONObject(s);
        JSONObject obj = (JSONObject) ((jsonObject.getJSONObject("result").getJSONArray("lines").get(0)));
        JSONArray jsonArray = obj.getJSONArray("data");
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONArray a = (JSONArray) (jsonArray.get(i));
                String day = new DateTime(a.get(0)).toString("yyyy-MM-dd");
                if (a.get(1) == null) {
                    continue;
                }
                Integer num = (Integer) a.get(1);
                if (map.get(day) == null) {
                    map.put(day, num);
                } else {
                    map.put(day, num + map.get(day));
                }
            } catch (Exception e) {

            }
        }
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + "\t" + entry.getValue());

        }
    }

    @Test
    public void test2() throws ClientProtocolException, IOException, JSONException {
        HttpClient httpClient = HttpClientSupport.getHttpClient();

        HttpPost post = new HttpPost("http://xxxxxxx/card/queryVipUser");

        String cookie = "";

        post.addHeader("Cookie", cookie);
        post.addHeader("Content-Type", "application/json;charset=UTF-8");
        post.addHeader("Accept", "application/json, text/plain, */*");

        List<String> userNames = FileUtils.readLines(new File("/Users/changxx/Downloads/error.txt"));

        for (String userName : userNames) {
            JSONObject jsonObjec1 = new JSONObject();
            jsonObjec1.put("userName", userName);
            jsonObjec1.put("status", 1);

            //设置请求体
            post.setEntity(new StringEntity(jsonObjec1.toString(), "UTF-8"));

            // 执行请求并获取结果
            String responseBody1 = httpClient.execute(post, new BasicResponseHandler());

            JSONObject jsonObject = new JSONObject(responseBody1);
            JSONArray obj = jsonObject.getJSONArray("data");
            if (obj.length() == 0) {
                continue;
            }
            String virtualOrderId = ((JSONObject) obj.get(0)).get("virtualOrderId").toString();
            if (!virtualOrderId.startsWith("rcd")) {
                System.out.println(virtualOrderId);
                continue;
            }

            HttpPost post2 = new HttpPost("http://xxxxxx/card/invalidCard");

            post2.addHeader("Cookie", cookie);
            post2.addHeader("Content-Type", "application/json;charset=UTF-8");
            post2.addHeader("Accept", "application/json, text/plain, */*");

            JSONObject jsonObject2 = new JSONObject();
            jsonObject2.put("userName", userName);
            jsonObject2.put("virtualOrderId", virtualOrderId);

            //设置请求体
            post2.setEntity(new StringEntity(jsonObject2.toString(), "UTF-8"));

            // 创建响应处理器处理服务器响应内容
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            // 执行请求并获取结果
            String responseBody = httpClient.execute(post2, responseHandler);
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
            System.out.println("----------------------------------------");
        }
    }
}



package com.changxx.practice.http;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * @author changxiangxiang
 * @date 2014年8月6日 下午5:02:44
 * @description
 * @since sprint2
 */
public class HttpClientTest {

    public static void main(String[] args) throws ClientProtocolException, IOException {

        HttpClient httpClient = HttpClientSupport.getHttpClient();

        HttpPost post = new HttpPost("http://passport.mop.com/");

        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("loginName", "joymall"));
        params.add(new BasicNameValuePair("loginPasswd", "981188321chang"));

        post.setEntity(new UrlEncodedFormEntity(params));

        HttpPostRequest request = new HttpPostRequest(httpClient, post);

        // 创建响应处理器处理服务器响应内容
        ResponseHandler<String> responseHandler = new ss();
        // 执行请求并获取结果
        String responseBody = httpClient.execute(post, responseHandler);
        System.out.println("----------------------------------------");
        System.out.println(responseBody);
        System.out.println("----------------------------------------");

    }

}

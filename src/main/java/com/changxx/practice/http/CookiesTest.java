package com.changxx.practice.http;

import java.io.IOException;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.cookie.CookieSpec;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class CookiesTest {

    public static void main(String[] args) {
        HttpClient client = new HttpClient();
        NameValuePair[] nameValuePairs = { new NameValuePair("username", "aaa"),
                new NameValuePair("passwd", "123456") };
        PostMethod postMethod = new PostMethod("登录url");
        postMethod.setRequestBody(nameValuePairs);
        int stats = 0;
        try {
            stats = client.executeMethod(postMethod);
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        postMethod.releaseConnection();// 这里最好把之前的资源放掉
        CookieSpec cookiespec = CookiePolicy.getDefaultSpec();
        Cookie[] cookies = cookiespec.match("域名", 80/* 端口 */, "/", false, client.getState().getCookies());
        for (Cookie cookie : cookies) {
            System.out.println(cookie.getName() + "##" + cookie.getValue());
        }

        HttpMethod method = null;
        String encode = "utf-8";// 页面编码,按访问页面改动
        String referer = "http://域名";// http://www.163.com
        method = new GetMethod("url2");// 后续操作
        method.getParams().setParameter("http.useragent", "Mozilla/4.0 (compatible; MSIE 5.5; Windows 98)");
        method.setRequestHeader("Referer", referer);
        
        client.getParams().setContentCharset(encode);
        client.getParams().setSoTimeout(300000);
        client.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                new DefaultHttpMethodRetryHandler(10, true));

        try {
            stats = client.executeMethod(method);
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (stats == HttpStatus.SC_OK) {
            System.out.println("提交成功!");

        }
    }
}

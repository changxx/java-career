package com.changxx.practice.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;

/**
 * @author changxiangxiang
 * @date 2014年8月6日 下午5:02:44
 * @description
 * @since sprint2
 */
public class HttpClientTest {
	
	@Test
	public void test1() throws ClientProtocolException, IOException{
		HttpClient httpClient = HttpClientSupport.getHttpClient();

		HttpPost post = new HttpPost("http://passport.mop.com/");

		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("loginName", "joymall"));
		params.add(new BasicNameValuePair("loginPasswd", "981188321chang"));

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
	public void test2() throws ClientProtocolException, IOException{
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

}

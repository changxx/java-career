package com.changxx.practice.http.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class WeiBo {

	public static void main(String[] args) {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		// httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,proxy);
		HttpPost httpost = new HttpPost(CommonConst.loginUrl);
		setPostHeader(httpost);
		// All the parameters post to the web site
		List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
		nvps.add(new BasicNameValuePair("check", "1"));
		nvps.add(new BasicNameValuePair("uname", "changxx"));
		nvps.add(new BasicNameValuePair("backURL", "/"));
		nvps.add(new BasicNameValuePair("autoLogin", "1"));
		nvps.add(new BasicNameValuePair("pwd", "chang19901121"));

		try {
			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			HttpHost targetHost = new HttpHost(CommonConst.host);
			HttpResponse httpResponse = httpclient.execute(targetHost, httpost);
			System.out.println(EntityUtils.toString(httpResponse.getEntity()));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpost.abort();
		}
	}

	/**
	 * pretend to be a browser quietly
	 */
	private static void setPostHeader(HttpPost post) {
		post.setHeader(CommonConst.UserAgent, CommonConst.HttpAgent);
		post.setHeader("Origin", CommonConst.weiboUrl);
		post.setHeader("Cache-Control", "max-age=0");
		post.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		post.setHeader("Accept-Encoding", "gzip,deflate,sdch");
		post.setHeader("Accept-Language", "en-US,en;q=0.8");
		post.setHeader("Accept-Charset", "ISO-8859-1,utf-8;q=0.7,*;q=0.3");
		post.setHeader("Accept-Encoding", "gzip,deflate,sdch");
		post.setHeader("Referer", CommonConst.loginUrl);
	}
}

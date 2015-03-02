package com.changxx.practice.http;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

/**
 * @author changxiangxiang
 * @date 2014年8月6日 下午5:02:44
 * @description
 * @since sprint2
 */
public class HttpClientTest {

    private final static String JONE_LIST_SYSTEM_URL = "http://jone.jd.com/production_api/list_system";

    private final static String TOKEN                = "26FFE547-B28C-499A-8E9B-PRODUC";

    public static void main(String[] args) {

        HttpClient httpClient = HttpClientSupport.getHttpClient();

        HttpPost post = new HttpPost(JONE_LIST_SYSTEM_URL);
        post.setHeader("token", TOKEN);

        List<BasicNameValuePair> params = HttpClientTest.getParams(1, 20, null);

        HttpPostRequest request = new HttpPostRequest(httpClient, post);
        String json = request.request(params);

        System.out.println(json);

        Result result = JSONUtils.jsonToObject(json, Result.class);

        for (JoneSystem system : result.getBody().getData().getSystem()) {
            System.out.println("level" + system.getLevel());
            System.out.println("level_name" + system.getLevelName());
        }
    }

    private static List<BasicNameValuePair> getParams(Integer page, Integer pageSize, List<Integer> ids) {
        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        if (null != page) {
            params.add(new BasicNameValuePair("page", String.valueOf(page)));
        }
        if (null != pageSize) {
            params.add(new BasicNameValuePair("page_size", String.valueOf(pageSize)));
        }
        if (ids != null && ids.size() > 0) {
            params.add(new BasicNameValuePair("id", Arrays.toString(ids.toArray(new Integer[] { ids.size() }))));
        }
        return params;
    }

}

package com.changxx.practice.http;

import com.changxx.practice.http.ms.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    @Test
    public void test3() throws ClientProtocolException, IOException {
        String content = "{\"Name\": \"kaolat\",\"Container\": \"mp4\",\"Audio\": {\"Codec\": \"aac\"},\"Thumbnail\": {\"Offset\": \"15\"}}";
        String result = HttpClientSupport.postStream("http://nts.netease.com:8081/presets/create", null);
        System.out.println(result);
    }

    @Test
    public void testMS() throws Exception {
        int page = 1;
        String cookie = "_ntes_nnid=7a2ab2a4395c4e4bd5cea514e9515dbc,1501053535791; _ga=GA1.2.110885681.1499913884; route=e8419becc80413c8bfe2c95400921a8b; NTESwebSI=70DF4359375A2EE718ADA521209D2FE5.hzadg-haitao-dubbo5.server.163.org-8081; CAS_U=CAS_U_ST-31345-rd64hR2yLcFh5wJcA16d";
        List<AppResult> appResults = new ArrayList<AppResult>();
        // 有调用的
        List<AppResult> intInvokes = new ArrayList<AppResult>();

        Application application = this.getApplication(page, cookie);

        if (application != null && application.getPage() != null) {
            if (application.getPage().getResult() != null) {
                appResults.addAll(application.getPage().getResult());
            }
            if (application.getPage().getTotalPages() > page) {
                for (int i = page + 1; i <= application.getPage().getTotalPages(); i++) {
                    Application applicationTemp = this.getApplication(i, cookie);
                    if (applicationTemp.getPage().getResult() != null) {
                        appResults.addAll(applicationTemp.getPage().getResult());
                    }
                }
            }
        }

        for (AppResult appResult : appResults) {
            if (!notInvoke(appResult)) {
                intInvokes.add(appResult);
            }
        }

        System.out.println(intInvokes.size());

        for (AppResult appResult : intInvokes) {
            // 200
            String str = print(appResult.getName(), 200, "-");
            System.out.println(str);
            InteStat inteStat = this.getInterfaceStat(appResult.getName(), cookie);
            for (MethodStat methodStat : inteStat.getList()) {
                if (methodStat.getConsumerSuccess() > 0) {
                    MethodDist methodDist = this.getMethodDist(appResult.getName(), methodStat.getMethod(), cookie);
                    // 100 20 80
                    String str1 = print(methodStat.getMethod(), 100, " ") + print(methodStat.getConsumerSuccess() + "", 10, " ") + print(JSONUtils.objectToJson(methodDist.getApps()), 80, " ");
                    System.out.println(str1);
                }
            }
        }
    }

    private Application getApplication(int page, String cookie) throws Exception {
        String url = "http://soa.hz.netease.com/manage/service/getServiceListNoGroupversion?p=" + page + "&ps=50&favorite=false&application=haitao-ms";
        HttpClient httpClient = HttpClientSupport.getHttpClient();

        HttpGet post = new HttpGet(url);
        post.addHeader("Cookie", cookie);

        // 创建响应处理器处理服务器响应内容
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        // 执行请求并获取结果
        String body = httpClient.execute(post, responseHandler);

        return JSONUtils.jsonToObject(body, Application.class);
    }

    private InteStat getInterfaceStat(String inte, String cookie) throws Exception {
        String url = "http://soa.hz.netease.com/services/ajaxStatistics?service=" + inte + "&p=1&ps=50";
        HttpClient httpClient = HttpClientSupport.getHttpClient();

        HttpGet post = new HttpGet(url);
        post.addHeader("Cookie", cookie);

        // 创建响应处理器处理服务器响应内容
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        // 执行请求并获取结果
        String body = httpClient.execute(post, responseHandler);

        return JSONUtils.jsonToObject(body, InteStat.class);
    }

    private MethodDist getMethodDist(String inte, String method, String cookie) throws Exception {
        String url = "http://soa.hz.netease.com/services/method/sourceChart?service=" + inte + "&method=" + method + "&from=1507478400000&to=1507564799000";
        HttpClient httpClient = HttpClientSupport.getHttpClient();

        HttpGet post = new HttpGet(url);
        post.addHeader("Cookie", cookie);

        // 创建响应处理器处理服务器响应内容
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        // 执行请求并获取结果
        String body = httpClient.execute(post, responseHandler);

        return JSONUtils.jsonToObject(body, MethodDist.class);
    }

    /**
     * 接口无调用
     */
    private boolean notInvoke(AppResult appResult) {
        if (appResult == null) {
            return true;
        }
        if (appResult.getTags() != null) {
            for (AppTag appTag : appResult.getTags()) {
                if (appTag.getTagName().equals("无调用")) {
                    return true;
                }
            }
        }
        return false;
    }

    private String print(String name, int length, String split) {
        for (int i = 0; i < length - name.length(); i++) {
            name = name.concat(split);
        }
        return name;
    }
}



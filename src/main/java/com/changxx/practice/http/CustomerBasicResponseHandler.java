package com.changxx.practice.http;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpResponseException;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.util.EntityUtils;

public class CustomerBasicResponseHandler extends BasicResponseHandler {

	private static final String PREFIX = "<a href=";// 前缀

	public String handleResponse(final HttpResponse response) throws HttpResponseException, IOException {
		StatusLine statusLine = response.getStatusLine();
		HttpEntity entity = response.getEntity();
		String url = EntityUtils.toString(entity);
		if (statusLine.getStatusCode() == 302 && url.indexOf(PREFIX) != -1) {
			String href = url.substring(url.indexOf(PREFIX) + PREFIX.length() + 1, url.indexOf(">here</a>") - 1);
			CookieStore cookieStore = new BasicCookieStore();
			String setCookie = response.getFirstHeader("Set-Cookie").getValue();
			
			for(org.apache.http.HeaderElement element : response.getFirstHeader("Set-Cookie").getElements()){
				System.out.println(element);
			}
			System.out.println(setCookie);

			String JSESSIONID = setCookie.substring("JSESSIONID=".length(), setCookie.indexOf(";"));
			System.out.println("JSESSIONID:" + JSESSIONID);
			// 新建一个Cookie
			BasicClientCookie cookie = new BasicClientCookie("JSESSIONID", JSESSIONID);
			cookie.setVersion(0);
			cookie.setDomain("127.0.0.1");
			cookie.setPath("/CwlProClient");
			// cookie.setAttribute(ClientCookie.VERSION_ATTR, "0");
			// cookie.setAttribute(ClientCookie.DOMAIN_ATTR, "127.0.0.1");
			// cookie.setAttribute(ClientCookie.PORT_ATTR, "8080");
			// cookie.setAttribute(ClientCookie.PATH_ATTR, "/CwlProWeb");
			cookieStore.addCookie(cookie);
		}

		
		return entity == null ? null : EntityUtils.toString(entity);
	}
}

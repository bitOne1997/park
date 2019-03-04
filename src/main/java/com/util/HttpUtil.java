package com.util;



import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;

public class HttpUtil {
	public static Map<String, Object> post(String url, Map<String, Object> argMap){
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		post.addHeader("token", "f9e08257652deaa76de81c8225801482");
		String json = new Gson().toJson(argMap);
		StringEntity se = new StringEntity(json.toString(), "UTF-8"); 
		se.setContentEncoding(new BasicHeader(HTTP.CONTENT_ENCODING, "UTF-8"));
		se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json;charset=utf-8"));
		post.setEntity(se);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
		HttpResponse response = httpClient.execute(post); 
		StatusLine status = response.getStatusLine(); 
		int code = status.getStatusCode();
		resultMap.put("status", code);
		if(code < 200 || code >=300){
		resultMap.put("message", "request fail");
		}else{
		HttpEntity entity = response.getEntity();
		String body = EntityUtils.toString(entity);
		resultMap.put("body", body);
		resultMap.put("message", "request success");
		} 
		} catch (ClientProtocolException e) {
		e.printStackTrace();
		post.abort();
		} catch (IOException e) {
		e.printStackTrace();
		post.abort();
		} finally{
		httpClient.getConnectionManager().shutdown();
		}
		return resultMap;
		}
	public static String postS(String url, Map<String, Object> argMap){
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		String body = "";
		post.addHeader("token", "f9e08257652deaa76de81c8225801482");
		String json = new Gson().toJson(argMap);
		StringEntity se = new StringEntity(json.toString(), "UTF-8"); 
		se.setContentEncoding(new BasicHeader(HTTP.CONTENT_ENCODING, "UTF-8"));
		se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json;charset=utf-8"));
		post.setEntity(se);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
		HttpResponse response = httpClient.execute(post); 
		StatusLine status = response.getStatusLine(); 
		int code = status.getStatusCode();
		resultMap.put("status", code);
		if(code < 200 || code >=300){
		resultMap.put("message", "request fail");
		}else{
		HttpEntity entity = response.getEntity();
		body = EntityUtils.toString(entity);
		resultMap.put("body", body);
		resultMap.put("message", "request success");
		} 
		} catch (ClientProtocolException e) {
		e.printStackTrace();
		post.abort();
		} catch (IOException e) {
		e.printStackTrace();
		post.abort();
		} finally{
		httpClient.getConnectionManager().shutdown();
		}
		return body;
		}

		public static Map<String, Object> get(String url){
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);
		get.setHeader("token", "f9e08257652deaa76de81c8225801482");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
		HttpResponse response = httpClient.execute(get); 
		StatusLine status = response.getStatusLine(); 
		int code = status.getStatusCode();
		resultMap.put("status", code);
		if(code < 200 || code >=300){
		resultMap.put("message", "request fail");
		}else{
		HttpEntity entity = response.getEntity();
		String body = EntityUtils.toString(entity);
		resultMap.put("body", body);
		resultMap.put("message", "request success");
		} 
		} catch (ClientProtocolException e) {
		e.printStackTrace();
		get.abort();
		} catch (IOException e) {
		e.printStackTrace();
		get.abort();
		} finally{
		httpClient.getConnectionManager().shutdown();
		}
		return resultMap;
		}
		public static String gettest(String url){
			HttpClient httpClient = new DefaultHttpClient();
			String body = "";
			HttpGet get = new HttpGet(url);
			get.setHeader("token", "f9e08257652deaa76de81c8225801482");
			Map<String, Object> resultMap = new HashMap<String, Object>();
			try {
			HttpResponse response = httpClient.execute(get); 
			StatusLine status = response.getStatusLine(); 
			int code = status.getStatusCode();
			resultMap.put("status", code);
			if(code < 200 || code >=300){
			resultMap.put("message", "request fail");
			}else{
			HttpEntity entity = response.getEntity();
			body = EntityUtils.toString(entity);
			resultMap.put("body", body);
			resultMap.put("message", "request success");
			} 
			} catch (ClientProtocolException e) {
			e.printStackTrace();
			get.abort();
			} catch (IOException e) {
			e.printStackTrace();
			get.abort();
			} finally{
			httpClient.getConnectionManager().shutdown();
			}
			return body;
			}
}

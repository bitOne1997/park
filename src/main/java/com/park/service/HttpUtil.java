package com.park.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtil {
	private static Log logger = LogFactory.getLog(HttpUtil.class);
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

	public static Map<String, Object> postNanyang(String url, Map<String, Object> argMap){
		HttpClient httpClient = new DefaultHttpClient();	
		HttpPost post = new HttpPost(url);
		post.addHeader("APIKey", "QBOELHiW4BH7J5LYEORHWLTD1PB2NCV3");
		post.addHeader("LicKey", "2EF47C829072E97C32CCEC9376FF86BD");
		String json = new Gson().toJson(argMap);
		StringEntity se = new StringEntity(json.toString(), "UTF-8"); 
		se.setContentEncoding(new BasicHeader(HTTP.CONTENT_ENCODING, "UTF-8"));
		se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json;charset=utf-8"));
		post.setEntity(se);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
		HttpResponse response = httpClient.execute(post); 
		logger.info(response.toString());
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
	
	public static Map<String, Object> posts(String url, Map<String, String> argMap){
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		
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
		
		public void okHttpGet(String url){
			OkHttpClient okHttpClient = new OkHttpClient();
			final Request request = new Request.Builder()
			        .url(url)
			        .get()//默认就是GET请求，可以不写
			        .build();
			Call call = okHttpClient.newCall(request);
			call.enqueue(new Callback() {
				
				@Override
				public void onResponse(Call call, Response response) throws IOException {
					// TODO Auto-generated method stub
					logger.info("返回数据"+response.toString());
				}
				
				@Override
				public void onFailure(Call call, IOException e) {
					// TODO Auto-generated method stub
					logger.info("okHttpPost 请求错误:"+e.getMessage());
				}
			});
			
		}
		
		public static void okHttpPost(String url,Map<String, Object> argMap){
			String json = new Gson().toJson(argMap);
			MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
			Request request = new Request.Builder()
			        .url(url)
			        .post(RequestBody.create(mediaType, json))
			        .build();
			OkHttpClient okHttpClient = new OkHttpClient();
			Call call = okHttpClient.newCall(request);
			call.enqueue(new Callback() {
				
				@Override
				public void onResponse(Call call, Response response) throws IOException {
					// TODO Auto-generated method stub
					logger.info("返回数据"+response.toString());
					
				}
				
				@Override
				public void onFailure(Call call, IOException e) {
					// TODO Auto-generated method stub
					logger.info("okHttpPost 请求错误:"+e.getMessage());
				}
			});
			

		}
}

package com.jwBlog.utils;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class HttpClientUtil {

    private static int default_timeOut=60000;
    
    public static String doPost(String url, Map<String, String> params) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		for (Entry<String, String> entry : params.entrySet()) {
			nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}

		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			writeErrorLog("", "http post url has error ", e);
		}
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(default_timeOut).setConnectTimeout(default_timeOut).build();
		httpPost.setConfig(requestConfig);
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpPost);

			int status = response.getStatusLine().getStatusCode();
			System.out.println("status: " + status);
			if (status >= 200 && status < 300) {
				HttpEntity entity = response.getEntity();
				return entity != null ? EntityUtils.toString(entity,"UTF-8") : null;
			} else {
				throw new ClientProtocolException("Unexpected response status: " + status);
			}

		} catch (IOException e) {
			writeErrorLog("", "http post execute has error ",e);
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					writeErrorLog("", "",e);
				}
			}
		}

		return "";
	}



	/**
	 * NLP登陆获取
	 * @param url
	 * @param param
	 * @param header
     * @return
     */
	public String login(String url,Map param,Map header){
		String result = "";
		try {
			result = doPost(url,param,header);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String doPost(String url, Map<String, String> params,Map<String,String> headers) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		if(null!=params&&params.size()>0){
			for (Entry<String, String> entry : params.entrySet()) {
				nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
		}
		
		if(null!=headers&&headers.size()>0){
			for (Entry<String, String> entry : headers.entrySet()) {
				httpPost.addHeader(entry.getKey(),entry.getValue());
			}
		}
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			writeErrorLog("", "http post url has error ", e);
		}
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(default_timeOut).setConnectTimeout(default_timeOut).build();
		httpPost.setConfig(requestConfig);
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpPost);
			int status = response.getStatusLine().getStatusCode();
			System.out.println("status: " + status);
			if (status >= 200 && status < 300) {
				HttpEntity entity = response.getEntity();
				return entity != null ? EntityUtils.toString(entity,"UTF-8") : null;
			} else {
				throw new ClientProtocolException("Unexpected response status: " + status);
			}

		} catch (IOException e) {
			writeErrorLog("", "http post execute has error ",e);
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					writeErrorLog("", "",e);
				}
			}
		}

		return "";
	}

	public static String doPut(String url, Map<String, String> params,Map<String,String> headers) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPut httpPut = new HttpPut(url);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		if(null!=params&&params.size()>0){
			for (Entry<String, String> entry : params.entrySet()) {
				nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
		}
		
		if(null!=headers&&headers.size()>0){
			for (Entry<String, String> entry : headers.entrySet()) {
				httpPut.addHeader(entry.getKey(),entry.getValue());
			}
		}
		try {
			httpPut.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			writeErrorLog("", "http post url has error ", e);
		}
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(default_timeOut).setConnectTimeout(default_timeOut).build();
		httpPut.setConfig(requestConfig);
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpPut);
			int status = response.getStatusLine().getStatusCode();
			System.out.println("status: " + status);
			if (status >= 200 && status < 300) {
				HttpEntity entity = response.getEntity();
				return entity != null ? EntityUtils.toString(entity) : null;
			} else {
				throw new ClientProtocolException("Unexpected response status: " + status);
			}

		} catch (IOException e) {
			writeErrorLog("", "http post execute has error ",e);
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					writeErrorLog("", "",e);
				}
			}
		}

		return "";
	}
	
	public static String doDelete(String url, Map<String, String> params,Map<String, String> headers) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		if(null!=params&&params.size()>0){
			for (Entry<String, String> entry : params.entrySet()) {
				url += "/" + entry.getKey() + "=" + entry.getValue();
			}
		}
		HttpDelete httpDelete = new HttpDelete(url);
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(default_timeOut).setConnectTimeout(default_timeOut).build();
		httpDelete.setConfig(requestConfig);
		CloseableHttpResponse response = null;
		if(null!=headers&&headers.size()>0){
			for (Entry<String, String> entry : headers.entrySet()) {
				httpDelete.addHeader(entry.getKey(),entry.getValue());
			}
		}
		try {
			response = httpClient.execute(httpDelete);

			int status = response.getStatusLine().getStatusCode();
			if (status >= 200 && status < 300) {
				HttpEntity entity = response.getEntity();
				return entity != null ? EntityUtils.toString(entity,"UTF-8") : null;
			} else {
				throw new ClientProtocolException("Unexpected response status: " + status);
			}

		} catch (IOException e) {
			writeErrorLog("", "http post execute has error ",e);
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					writeErrorLog("", "",e);
				}
			}
		}
		return "";
	}
	public static String doGet(String url, Map<String, String> params) {
		CloseableHttpClient httpClient = HttpClients.createDefault();

		for (Entry<String, String> entry : params.entrySet()) {
			url += "/" + entry.getKey() + "=" + entry.getValue();
		}
		HttpGet httpGet = new HttpGet(url);
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(default_timeOut).setConnectTimeout(default_timeOut).build();
		httpGet.setConfig(requestConfig);
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpGet);

			int status = response.getStatusLine().getStatusCode();
			if (status >= 200 && status < 300) {
				HttpEntity entity = response.getEntity();
				return entity != null ? EntityUtils.toString(entity) : null;
			} else {
				throw new ClientProtocolException("Unexpected response status: " + status);
			}

		} catch (IOException e) {
			writeErrorLog("", "http post execute has error ",e);
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					writeErrorLog("", "",e);
				}
			}
		}

		return "";
	}
	public static String doGet(String url, Map<String, String> params, Map<String, String> Header) {
		CloseableHttpClient httpClient = HttpClients.createDefault();

		if(null!=params&&params.size()>0){
			url += "?";
			String tmpUrl = "";
			for (Entry<String, String> entry : params.entrySet()) {
				tmpUrl += "&" + entry.getKey() + "=" + entry.getValue();
			}
			url += (tmpUrl.length()>0?tmpUrl.substring(1):"");
		}
		HttpGet httpGet = new HttpGet(url);
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(default_timeOut).setConnectTimeout(default_timeOut).build();
		httpGet.setConfig(requestConfig);
		CloseableHttpResponse response = null;
		if(null!=Header&&Header.size()>0){
			for (Entry<String, String> entry : Header.entrySet()) {
				httpGet.addHeader(entry.getKey(),entry.getValue());
			}
		}
		try {
			response = httpClient.execute(httpGet);

			int status = response.getStatusLine().getStatusCode();
			if (status >= 200 && status < 300) {
				HttpEntity entity = response.getEntity();
				return entity != null ? EntityUtils.toString(entity,"UTF-8") : null;
			} else {
				throw new ClientProtocolException("Unexpected response status: " + status);
			}

		} catch (IOException e) {
			writeErrorLog("", "http post execute has error ",e);
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					writeErrorLog("", "",e);
				}
			}
		}
		return "";
	}
	/**
	 * 向指定URL发送GET方法的请求
	 *
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String sendGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();

			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			writeErrorLog("", "发送GET请求出现异常",e);
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				writeErrorLog("", "",e2);
			}
		}
		return result;
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 *
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */

//	static class MyAuthenticator extends Authenticator{
//
//		public PasswordAuthentication getPasswordAuthentication(){
//
//			return (new PasswordAuthentication(username,password.toCharArray()));
//		}
//	}

	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			String authoRization  = "WJ6rAkO4r0iKWtsm2dtmFVySlxHccIt90M47uWO4:pe843MRWfCylspXhveCygCMuzAcInpQboQf6a8PRp03Bg7DMTZQe";
			conn.setRequestProperty("Authorization", "Basic " + authoRization);
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("content-type", "text/html");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
//			httpURLConnection.setDoInput(true);
//			httpURLConnection.setDoOutput(true);
//			httpURLConnection.setConnectTimeout(10000);
//			httpURLConnection.setRequestProperty("content-type", "text/html;charset=utf-8");
//			httpURLConnection.setRequestMethod("POST");
//			其中setRequestProperty("content-type", "text/html;charset=utf-8");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			writeErrorLog("", "发送 POST 请求出现异常",e);
		}
		//使用finally块来关闭输出流、输入流
		finally{
			try{
				if(out!=null){
					out.close();
				}
				if(in!=null){
					in.close();
				}
			}
			catch(IOException ex){
				writeErrorLog("","",ex);
			}
		}
		return result;
	}
   
	private static void writeErrorLog(String method,String content,Exception e){
	}

}
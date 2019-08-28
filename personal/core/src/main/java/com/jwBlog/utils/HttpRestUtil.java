package com.jwBlog.utils;

import com.jwBlog.base.entity.MyResponseEntity;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


public class HttpRestUtil {
    private static int default_timeOut=60000;

	public static String doPostEntity(String url, Map<String, String> params){
		RestTemplate client = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		HttpMethod method = HttpMethod.POST;
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity httpEntity = new HttpEntity(params,headers);
		ResponseEntity<String> response = client.exchange(url,method,httpEntity, String.class);
		return response.getBody();
	}
	public static String doPutEntity(String url, Map<String, String> params){
		RestTemplate client = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		HttpMethod method = HttpMethod.PUT;
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity httpEntity = new HttpEntity(params,headers);
		ResponseEntity<String> response = client.exchange(url,method,httpEntity, String.class);
		return response.getBody();
	}
	public static MyResponseEntity doPostEntity2(String url, Map<String, String> params){
		RestTemplate client = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		HttpMethod method = HttpMethod.POST;
		// 以表单的方式提交
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity httpEntity = new HttpEntity(params,headers);
		ResponseEntity<MyResponseEntity> response = client.exchange(url,method,httpEntity, MyResponseEntity.class);
//		ResponseEntity<MyResponseEntity> response = client.postForEntity(url,requestEntity, MyResponseEntity.class);
		return response.getBody();
	}
	public static MyResponseEntity doPutEntity2(String url, Map<String, String> params){
		RestTemplate client = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		HttpMethod method = HttpMethod.PUT;
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity httpEntity = new HttpEntity(params,headers);
		ResponseEntity<MyResponseEntity> response = client.exchange(url,method,httpEntity, MyResponseEntity.class);
		return response.getBody();
	}
    public static MyResponseEntity doPost(String url, MultiValueMap<String, String> params){
		RestTemplate client = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		HttpMethod method = HttpMethod.POST;
		// 以表单的方式提交
//		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//		headers.add("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
		ResponseEntity<MyResponseEntity> response = client.exchange(url,method,requestEntity, MyResponseEntity.class);
//		ResponseEntity<MyResponseEntity> response = client.postForEntity(url,requestEntity, MyResponseEntity.class);
		return response.getBody();
    }

	public static MyResponseEntity doPut(String url, MultiValueMap<String, String> params){
		RestTemplate client = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		HttpMethod method = HttpMethod.PUT;
		// 以表单的方式提交
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
		ResponseEntity<MyResponseEntity> response = client.exchange(url, method, requestEntity, MyResponseEntity.class);
		return response.getBody();
	}

	public static MyResponseEntity doGet(String url, MultiValueMap<String, String> params){
		RestTemplate client = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		HttpMethod method = HttpMethod.GET;
		// 以表单的方式提交
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
		ResponseEntity<MyResponseEntity> response = client.exchange(url, method, requestEntity, MyResponseEntity.class);
		return response.getBody();
	}
}
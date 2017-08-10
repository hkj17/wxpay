package com.IotCloud.wxpay.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.IotCloud.wxpay.client.TenpayHttpClient;
import com.IotCloud.wxpay.constant.ParameterKeys;
import com.IotCloud.wxpay.constant.ParameterValues;
import com.IotCloud.wxpay.util.ResponseHandler;

public class AccessTokenService extends RequestHandler {

	public AccessTokenService(HttpServletRequest request, HttpServletResponse response) {
		super(request, response);
		requestUrl = ParameterValues.URL_ACCESS_TOKEN;
		setAppId(ParameterValues.APP_ID);
		setAppSecret(ParameterValues.APP_SECRET);
	}

	private static String access_token = "";

	/**
	 * 获取凭证access_token
	 * 
	 * @return
	 * @throws Exception 
	 */
	public String getAccessToken() throws Exception {
		if ("".equals(access_token)) {// 如果为空直接获取
			return getTokenReal();
		}

		return access_token;
	}

	/**
	 * 实际获取access_token的方法
	 * 
	 * @return
	 * @throws Exception 
	 */
	protected String getTokenReal() throws Exception {
		requestUrl = requestUrl.replaceFirst("APPID", super.getParameter(ParameterKeys.APP_ID));
		requestUrl = requestUrl.replaceFirst("APPSECRET", super.getParameter(ParameterKeys.APP_SECRET));
		String resContent = "";
		TenpayHttpClient httpClient = new TenpayHttpClient();
		httpClient.setMethod("GET");
		httpClient.setReqContent(requestUrl);
		if (httpClient.call()) {
			resContent = httpClient.getResContent();
			ResponseHandler responseHandler = new ResponseHandler(resContent, 0);
			responseHandler.getAllParameters();
			if (resContent.indexOf(ParameterKeys.ACCESS_TOKEN) > 0) {
				access_token = responseHandler.getParamValue(ParameterKeys.ACCESS_TOKEN);
			} else {
				System.out.println("获取access_token值返回错误！！！");
			}
		} else {
			System.out.println("后台调用通信失败");
			System.out.println(httpClient.getResponseCode());
			System.out.println(httpClient.getErrInfo());
			// 有可能因为网络原因，请求已经处理，但未收到应答。
		}

		return access_token;
	}

	public void setAppId(String appId) {
		super.setParameter(ParameterKeys.APP_ID, appId);
	}

	public void setAppSecret(String appSecret) {
		super.setParameter(ParameterKeys.APP_SECRET, appSecret);
	}

	@Override
	public boolean isRequestValid() {
		Map<String, String> param = getAllParameters();
		return param.get(ParameterKeys.APP_ID) != null && param.get(ParameterKeys.APP_SECRET) != null;
	}

}
